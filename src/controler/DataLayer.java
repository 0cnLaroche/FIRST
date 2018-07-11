/**
 * Manage all the data stored in the database. At start up, all the data is pulled from the DB and loaded locally 
 * in order to reduce traffic and enhance performance. 
 * @author samuel Laroche
 */

package controler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.*;

public class DataLayer {
	
	private static Connection con = null;
	private final String user = "webadmin";
	private final String password = "Pa$$w0rd";
	private final String url = "jdbc:mysql://10.54.223.154:3306/costing";
	private final String stuff = "useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	
	private static HashMap<String,Run> runs;
	private static HashMap<String, Project> projects;
	private static HashMap<String, Wbs> wbs;
	private static HashMap<String, Network> networks;
	private static HashMap<String, CostCenter> costcenters;
	
	public DataLayer() throws DatabaseCommunicationsException {
		
		runs = new HashMap<String,Run>();
		wbs = new HashMap<String, Wbs>();
		networks = new HashMap<String, Network>();
		projects = new HashMap<String, Project>();
		costcenters = new HashMap<String, CostCenter>();
		
		this.connect();
		this.loadCostCenters();
		this.loadRuns();
		this.loadProjects();
		
	}
	public static ArrayList<String[]> queryFromFile(String path) { // Tested 04/07/2018 SL
		
		ClassLoader classLoader = DataLayer.class.getClassLoader();
		File file = new File(classLoader.getResource(path).getFile());
		ResultSet rs = null;
		ArrayList<String[]> result = new ArrayList<String[]>();
		
		
		String query = "";
		
		try (BufferedReader reader = new BufferedReader(new FileReader(file))){
			String line;
			while ((line = reader.readLine()) != null){
				query += line;
			}
			
		} catch (FileNotFoundException e) {
			System.err.println("File not Found");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		try {
			Statement statement = con.createStatement();
			rs = statement.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			final int columnCount = rsmd.getColumnCount();
			String[] line = new String[columnCount];
			
			for (int i = 1; i <= columnCount; i++) {
				line[i-1] = rsmd.getColumnLabel(i);
			}
			result.add(line); // insert header or column names
			
			while (rs.next()) {
				line = new String[columnCount];
				for (int i = 1; i <= columnCount; i++) {
					line[i-1] = rs.getString(i);
				}
				result.add(line);
			}
			
			
		} catch (SQLException e) {
			System.err.println("SQL Exception for query : " + query);
		}
		
		return result;
		
	}
	public static CostCenter getCostCenter(String id) throws NotFoundException {
		if (costcenters.containsKey(id)) {
			return costcenters.get(id);
		} else {
			throw new NotFoundException();
		}
	}
	/**
	 * Inserts a new RUN code into the database.
	 * @param run
	 */
	public static void addRun(Run run) {
		
		PreparedStatement insert = null;
		String query = "INSERT INTO RUN (ID, Name, NameFR, Type, Responsible, "
				+ "CostCenter_Responsible, CostCenter_Requesting, EffectiveDate, ClosingDate, ReplacedBy, Status) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?);";
		try {
			insert = con.prepareStatement(query);
			
			insert.setInt(1, Integer.parseInt(run.getId()));
			insert.setString(2, run.getNameEN());
			insert.setString(3, run.getNameFR());
			insert.setString(4, run.getType());
			insert.setString(5, run.getResponsible());
			
			insert.setInt(6, Integer.parseInt(run.getCostcenter().getId()));
			insert.setInt(7, Integer.parseInt(run.getCostcenter().getId()));
			insert.setObject(8, run.getEffectiveDate());
			insert.setObject(9, run.getClosingDate());
			insert.setString(10, run.getReplacedBy());
			insert.setString(11, run.getStatusString());
			
			insert.execute();
	
			runs.put(run.getId(), run);

		} catch (SQLException e) {
			
		}
	}
	public static void updateProject(Network nw) {
		PreparedStatement update = null;
		String queryNw = "UPDATE Network SET Name = ?, NameFR = ?, EffectiveDate = ?, "
				+ "ClosingDate = ?, ReplacedBy = ?, Status = ?, Stage = ? "
				+ "WHERE ID = ?;";
		
		try {
			
			update = con.prepareStatement(queryNw);
			
			update.setString(1, nw.getNameEN());
			update.setString(2, nw.getNameFR());
			if (nw.getEffectiveDate() != null) {
				update.setDate(3, Date.valueOf(nw.getEffectiveDate()));
			} else {
				update.setNull(3, java.sql.Types.DATE);
			}
			if (nw.getClosingDate() != null) {
				update.setDate(4, Date.valueOf(nw.getClosingDate()));
			} else {
				update.setNull(4, java.sql.Types.DATE);
			}
			update.setString(5, nw.getReplacedBy());
			update.setString(6, nw.getStatusString());
			update.setInt(7, nw.getWbs().getStage());
			update.setString(8, nw.getId()); // WHERE
			
			update.execute();
			System.out.println("Update to database sucessful for network : " + nw.toString());
			
			networks.put(nw.getId(), nw);
		
		} catch (SQLException e) {
			
		}
		String queryWbs = "UPDATE WBS SET Name = ?, NameFR = ?, ResponsibleCostCenter = ?, "
				+ "RequestingCostCenter = ?, "
				+ "Approver = ?, "
				+ "ProjectDefinition = ?, "
				+ "Status = ?,  "
				+ "Stage = ? "
				+ "WHERE ID = ?";
		
		try {
			update = con.prepareStatement(queryWbs);
			
			update.setString(1, nw.getNameEN());
			update.setString(2, nw.getNameFR());
			update.setInt(3, Integer.parseInt(nw.getWbs().getCostcenter().getId()));
			update.setInt(4, Integer.parseInt(nw.getWbs().getCostcenter().getId()));
			update.setString(5, nw.getWbs().getApprover());
			update.setString(6, nw.getWbs().getProject().getId());
			update.setString(7,nw.getStatusString());
			update.setInt(8,nw.getWbs().getStage());
			update.setString(9, nw.getWbs().getId());
			
			update.execute();
			
			System.out.println("Update to database sucessful for WBS : " + nw.getWbs().toString());
			
			wbs.put(nw.getWbs().getId(), nw.getWbs());
			projects.get(nw.getWbs().getProject().getId()).addWbs(nw.getWbs()); // Ajouter le WBS au projet
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
	}
	public static Run getRun(String id) throws NotFoundException {
		if (runs.containsKey(id)) {
			return runs.get(id);
		} else {
			throw new NotFoundException();
		}		
	}
	public static Network getNetwork(String id) throws NotFoundException {
		if (networks.containsKey(id)) {
			return networks.get(id);
		} else {
			throw new NotFoundException();
		}
	}
	public static Wbs getWbs(String id) throws NotFoundException {
		if (wbs.containsKey(id)) {
			return wbs.get(id);
		} else {
			throw new NotFoundException();
		}
		
	}
	public static Project getProject(String id) throws NotFoundException {
		if (projects.containsKey(id)) {
			return projects.get(id);
		} else {
			throw new NotFoundException();
		}
	}
	public static ArrayList<Run> getRunList(){
		ArrayList<Run> list = new ArrayList<Run>(runs.values());
		/*for(String key : runs.keySet()) {
			list.add(runs.get(key));
		}*/
		return list;
		
	}
	public static ArrayList<Run> queryRuns(String keyword, String approver, String costcenter) throws NotFoundException {
		// TODO : Find a way to sort results by description
		ArrayList<Run> list = new ArrayList<Run>();
		ResultSet rs = null;
		PreparedStatement selectStatement = null;
		String querystring = "SELECT DISTINCT ID FROM RUN WHERE" + 
				" Name LIKE ? OR NameFR LIKE ?" + 
				" OR IF( ? = '',0, Responsible LIKE ? ) " +
				" OR CostCenter_Responsible = ?" ;
		
		if (keyword.equals("") && approver.equals("") && costcenter.equals("")) {
			// Don"t fetch anything from db, just load every run from local hashmap
			for (String key : runs.keySet()) {
				list.add(runs.get(key));
			}
		} else {
			// fetch from db
			try {
				
				selectStatement = con.prepareStatement(querystring);

				if (!keyword.equals("")) {
					keyword = "%" + keyword + "%";
				}
				selectStatement.setString(1, keyword);
				selectStatement.setString(2, keyword);
				if (!approver.equals("")) {
					approver = "%" + approver + "%";

				}
				selectStatement.setString(3, approver);
				selectStatement.setString(4, approver);
				
				if (!costcenter.equals("")) {
					int cc = Integer.parseInt(costcenter);
					selectStatement.setInt(5, cc);
				} else {
					selectStatement.setNull(5, java.sql.Types.INTEGER);
				}

				System.out.println(selectStatement.toString());
				rs = selectStatement.executeQuery();
				
				while(rs.next()) {
					list.add(runs.get(rs.getString(1)));
				}
				if (list.isEmpty())
					throw new NotFoundException();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	
	}
	
	public void connect() throws DatabaseCommunicationsException {

		try {
			con = DriverManager.getConnection(url + "?" + stuff, user, password);
			System.out.println("Connected to database");
		} catch (CommunicationsException e) {
			throw new DatabaseCommunicationsException();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	private void loadCostCenterRelationships(ArrayList<String[]> index, String parent){

		for (String[] entry: index) {
			if (entry[1].equals(parent)) {

				costcenters.get(parent).addChild(costcenters.get(entry[0])); // Ajoute référence entre enfant et parent
				costcenters.get(entry[0]).setParent(costcenters.get(parent));
				loadCostCenterRelationships(index, entry[0]);
			}
		}
	}
	private void loadCostCenters() {

		try {

			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT ID, ReportsTo, Name, Manager, Directorate, EffectiveDate, ClosingDate, Comments, Level FROM CostCenter");
			
			ArrayList<String[]> index = new ArrayList<String[]>();
			
			
			while (rs.next()) {
				String[] entry = {"" +rs.getInt(1),"" + rs.getInt(2)}; // pos 0 : ID ; pos 1 : ReportsTo
				index.add(entry);
				
				CostCenter cc = new CostCenter();
				cc.setId(rs.getString(1));
				cc.setNameEN(rs.getString(3));
				try {
					cc.setEffectiveDate(rs.getDate(6).toLocalDate());
				} catch (NullPointerException e) {
					cc.setEffectiveDate(null);
				}
				try {
					cc.setClosingDate(rs.getDate(7).toLocalDate());
				} catch (NullPointerException e) {
					cc.setClosingDate(null);
				}
				costcenters.put(cc.getId(), cc);
				//Other fields to be added

			}
			loadCostCenterRelationships(index, "103100"); // 103100 as root
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void loadRuns() {

		try {

			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT ID, Name, NameFR, Type, Responsible, CostCenter_Responsible, EffectiveDate, ClosingDate, ReplacedBy, Status, Comments FROM RUN");

			while (rs.next()) {
				Run run = new Run();
				run.setId(rs.getString(1));
				run.setNameEN(rs.getString(2));
				run.setNameFR(rs.getString(3));
				run.setType(rs.getString(4));
				if (rs.getString(4) != null) {
					run.setType(rs.getString(4));
				} else {
					run.setType("N/A");
				}
				if (rs.getString(5) != null) {
					run.setResponsible(rs.getString(5));
				} else {
					run.setResponsible("blank");
				}
				
				if (costcenters.containsKey(rs.getString(6))) {
					run.setCostcenter(costcenters.get(rs.getString(6)));
				} else {
					CostCenter cc = new CostCenter();
					cc.setId(rs.getString(6));
					run.setCostcenter(cc);
				}
				if (rs.getDate(7) != null) {
					run.setEffectiveDate(rs.getDate(7).toLocalDate());
				} else {
					run.setEffectiveDate(null);
				}
				if (rs.getDate(8) != null) {
					run.setClosingDate(rs.getDate(8).toLocalDate());
				} else {
					run.setClosingDate(null);
				}

				run.setReplacedBy(rs.getString(9));
				
				switch (rs.getString(10)) {
				case "Active":
					run.setStatus(Run.ACTIVE);
					break;
				case "Closed":
					run.setStatus(Run.CLOSED);
					break;
				case "Unreleased":
					run.setStatus(Run.UNRELEASED);
					break;
				default:
					run.setStatus(Run.ACTIVE);
					break;
				}
				//Other fields to be added
				
				runs.put(run.getId(), run);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private void loadProjects() {
		
		ArrayList<String[]> wbsToProject = new ArrayList<String[]>();
		ArrayList<String[]> networkToWbs = new ArrayList<String[]>();
		
		try {
			
			
			// NETWORKS

			Statement statement = null;
			String queryString = "SELECT ID, Name, NameFR, WBS, Project, Stage, EffectiveDate, ClosingDate, Status, ReplacedBy, Comments FROM Network";

			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(queryString);

			while (rs.next()) {
				String[] entry = { rs.getString(1), rs.getString(4) }; // {ID, WBS} pair
				networkToWbs.add(entry);
				Network nw = new Network();
				nw.setId(rs.getString(1));
				nw.setNameEN(rs.getString(2));
				nw.setNameFR(rs.getString(3));
				if (rs.getDate(7) != null) {
					nw.setEffectiveDate(rs.getDate(7).toLocalDate());
				} else {
					nw.setEffectiveDate(null);
				}
				if (rs.getDate(8) != null) {
					nw.setClosingDate(rs.getDate(8).toLocalDate());
				} else {
					nw.setClosingDate(null);
				}
				

				
				switch (rs.getString(9) ) {
				case "Closed":
					nw.setStatus(Network.CLOSED);
					break;
				case "Active": 
					nw.setStatus(Network.ACTIVE);
					break;
				case "Unreleased":
					nw.setStatus(Network.UNRELEASED);
					break;
				default:
					nw.setStatus(Network.ACTIVE);
					break;
				}
				nw.setReplacedBy(rs.getString(10));
				
				// Other fields to be added
				networks.put(nw.getId(), nw);
			}
			
			// WBS
		
			queryString = "SELECT ID, Name, NameFR, ResponsibleCostCenter, Approver, Stage, ParentWBS, ProjectDefinition, ClosingDate, Status, ReplacedBy, Comments FROM WBS";
			statement = con.createStatement();
			rs = statement.executeQuery(queryString);

			while (rs.next()) {
				String[] entry = { rs.getString(1), rs.getString(8) }; // {ID, ProjectDefinition} pair
				wbsToProject.add(entry);
				Wbs wbs = new Wbs();
				wbs.setId(rs.getString(1));
				wbs.setNameEN(rs.getString(2));
				wbs.setNameFR(rs.getString(3));
				if (costcenters.containsKey(rs.getString(4))) {
					wbs.setCostcenter(costcenters.get(rs.getString(4)));
				} else {
					CostCenter cc = new CostCenter();
					cc.setId(rs.getString(4));
					wbs.setCostcenter(cc);
				}
				wbs.setApprover(rs.getString(5));
				wbs.setStage(rs.getByte(6));
				// Other fields to be added
				this.wbs.put(wbs.getId(), wbs);
				mapNetwork(networkToWbs, wbs.getId());

			}
			
			// PROJECT DEFINITION
			statement = con.createStatement();
			rs = statement.executeQuery(
					"SELECT ID, Name, NameFR, Model, Proposal, ProjectManager, ClosingDate, Status, Comments FROM ProjectDefinition");

			while (rs.next()) {
				Project project = new Project();
				project.setId(rs.getString(1));
				project.setNameEN(rs.getString(2));
				project.setNameFR(rs.getString(3));
				project.setModel(rs.getString(4));
				project.setProposal(rs.getString(5));
				// Other fields to be added
				projects.put(project.getId(), project);
				mapWbs(wbsToProject, project.getId());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void mapWbs(ArrayList<String[]> wbsToProject, String parent) {
		
		for (String[] entry : wbsToProject) {
			if (entry[1].equals(parent)) {
				projects.get(entry[1]).addWbs(wbs.get(entry[0]));
				wbs.get(entry[0]).setProject(projects.get(entry[1]));
			}
		}

	}
	private void mapNetwork(ArrayList<String[]> networkToWbs, String parent) {
		for (String[] entry : networkToWbs) {
			if (entry[1].equals(parent)) {
				wbs.get(entry[1]).addNetwork(networks.get(entry[0]));
				networks.get(entry[0]).setWbs(wbs.get(entry[1]));
			}

		}
	}
	public static void importCost(String path) {
		// Ça fonctionne mais trop long
		
		ArrayList<String[]> list = new ArrayList<String[]>();
		File file = new File (path);
		int eIndex = 0;
		
		try (BufferedReader reader = new BufferedReader(new FileReader(file))){
			String str;
			while ((str = reader.readLine()) != null) {
				list.add(str.split(","));
			}
			
			
			String query = 
					"INSERT INTO Direct (PostingDate, DocumentNumber, Period, CostElement, Amount, Fund, "
					+ "FunctionalArea, CostCenter, PRI, Object, NetworkActivity, Quantity, FiscalYear)"
					+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			PreparedStatement insert = null;
			insert = con.prepareStatement(query);
			
			final int batchSize = 1000;

			for (int i = 0; i < list.size(); i++) {
				eIndex++;
				String row[] = list.get(i);
				
				// PostingDate
				java.util.Date date = new SimpleDateFormat("dd/MM/yyyy").parse(row[0]);
				insert.setObject(1, date);
				
				// DocumentNumber
				insert.setString(2, row[1]);
				
				// Period
				insert.setInt(3, Integer.parseInt(row[2]));
				
				// CostElement
				insert.setInt(4, Integer.parseInt(row[3]));
				
				// Amount
				try {
				insert.setFloat(5, Float.parseFloat(row[4]));
				} catch (NumberFormatException e) {
					System.out.println(row.toString());
					insert.setObject(5, (Double)null);
					System.out.println("null insert at Amount line: " + i);
				}
				
				// Fund
				insert.setString(6, row[5]);
				
				// FunctionalArea
				insert.setString(7, row[6]);
				
				// CostCenter
				if(row[7].length()>9) {
					String cc = row[7].substring(4, 10);
					insert.setInt(8, Integer.parseInt(cc));
				} else if(row[7].length() == 6) {
					insert.setInt(8, Integer.parseInt(row[7]));
				} else {
					insert.setObject(8, (Integer)null);
					//System.out.println("null insert at CostCenter line: " + i);
				}
				
				// PRI
				insert.setString(9, row[8]);
				
				// Object
				insert.setString(10, row[9]);
				
				// NetworkActivity
				if (row[10].length() > 5) {
					String act = row[10].substring(6, 10);
					insert.setString(11, act);
				} else {
					insert.setObject(11, (String)null);
				}
				
				// Quantity
				insert.setDouble(12, Double.parseDouble(row[11]));
				
				// FiscalYear
				insert.setInt(13, Integer.parseInt(row[12]));
				
				// Adding line to batch

				insert.addBatch();
				
				if ((i + 1) % batchSize == 0) {
					insert.executeBatch();
					System.out.println("batch " + (i + 1) / batchSize + " of " + ((list.size() / batchSize) + 1));
				}
			}
			
			insert.executeBatch();
			
			System.out.println("batch " + ((list.size() / batchSize) + 1) + " of " + ((list.size() / batchSize) + 1));
			System.out.println("update successful");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("At line : " + eIndex);
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("At line : " + eIndex);
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("At line : " + eIndex);
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("At line : " + eIndex);
			e.printStackTrace();
		}
		
	}
	public void disconnect() {
		
		try {
			con.close();
			System.out.println("Connection closed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
