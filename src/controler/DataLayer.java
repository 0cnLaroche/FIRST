package controler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import model.*;

public class DataLayer {
	
	private Connection con = null;
	private final String user = "webadmin";
	private final String password = "Pa$$w0rd";
	private final String url = "jdbc:mysql://10.54.223.154:3306/costing";
	private final String stuff = "useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	
	private HashMap<String,Run> runs;
	private HashMap<String, Project> projects;
	private HashMap<String, Wbs> wbs;
	private HashMap<String, Network> networks;
	private HashMap<String, CostCenter> costcenters;
	
	public DataLayer() {
		
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
	public CostCenter getCostCenter(String id) {
		return costcenters.get(id);
	}
	public Run getRun(String id) {
		return runs.get(id);
	}
	public Network getNetwork(String id) {
		return networks.get(id);
	}
	public Wbs getWbs(String id) {
		return wbs.get(id);
	}
	public Project getProject(String id) {
		return projects.get(id);
	}
	public ArrayList<Run> queryRuns(String keyword, String approver, String costcenter){
		
		ArrayList<Run> list = new ArrayList<Run>();
		ResultSet rs = null;
		PreparedStatement selectStatement = null;
		String querystring = "SELECT DISTINCT ID FROM RUN WHERE " + 
				"Name LIKE ? OR NameFR LIKE ?" + 
				"OR Responsible LIKE ?" +
				"OR CostCenter_Responsible = ?" ;
		

		
		try {
			
			selectStatement = con.prepareStatement(querystring);
			
			if (keyword != "") {
				keyword = "%" + keyword + "%";
				selectStatement.setString(1, keyword);
				selectStatement.setString(2, keyword);
			}
			if (approver != "") {
				approver = "%" + approver + "%";
				selectStatement.setString(3, approver);
			}
			if (costcenter != "") {
				int cc = Integer.parseInt(costcenter);
				selectStatement.setInt(4, cc);
			}

			rs = selectStatement.executeQuery();
			
			while(rs.next()) {
				System.out.println(runs.get(rs.getString(1)).getId());
				list.add(runs.get(rs.getString(1)));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	
	}
	
	public void connect() {

		try {
			con = DriverManager.getConnection(url + "?" + stuff, user, password);
			System.out.println("Connected to database");

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
			
			//HashMap<String, CostCenter> list = new HashMap<String, CostCenter>();
			ArrayList<String[]> index = new ArrayList<String[]>();
			
			
			while (rs.next()) {
				String[] entry = {"" +rs.getInt(1),"" + rs.getInt(2)}; // pos 0 : ID ; pos 1 : ReportsTo
				//System.out.println(entry[0] + ";" + entry[1]);
				index.add(entry);
				
				CostCenter cc = new CostCenter();
				cc.setId(rs.getString(1));
				cc.setNameEN(rs.getString(3));
				
				cc.setEffectiveDate(rs.getDate(6)); //Pas certain ça va marcher
				cc.setClosingDate(rs.getDate(7));
				
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
					"SELECT ID, Name, NameFR, Type, Responsible, CostCenter_Responsible, EffectiveDate, ClosingDate, ReplacedBy, Comments FROM RUN");

			while (rs.next()) {
				Run run = new Run();
				run.setId(rs.getString(1));
				run.setNameEN(rs.getString(2));
				run.setNameFR(rs.getString(3));
				run.setType(rs.getString(4));
				run.setResponsible(rs.getString(5));
				run.setCostcenter(costcenters.get(rs.getString(6)));
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
			String queryString = "SELECT ID, Name, NameFR, WBS, Project, Stage, ClosingDate, EffectiveDate, Status, ReplacedBy, Comments FROM Network";

			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(queryString);

			while (rs.next()) {
				String[] entry = { rs.getString(1), rs.getString(4) }; // {ID, WBS} pair
				networkToWbs.add(entry);
				Network nw = new Network();
				nw.setId(rs.getString(1));
				nw.setNameEN(rs.getString(2));
				nw.setNameFR(rs.getString(3));
				// load Network here
				// Other fields to be added
				this.networks.put(nw.getId(), nw);
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
			//for (String[] entry : networkToWbs) {
				
			//}
			
			// PROJECT DEFINITION
			statement = con.createStatement();
			rs = statement.executeQuery(
					"SELECT ID, Name, NameFR, Model, Proposal, ProjectManager, ClosingDate, Status, Comments FROM ProjectDefinition");

			while (rs.next()) {
				Project project = new Project();
				project.setId(rs.getString(1));
				project.setNameEN(rs.getString(2));
				project.setNameFR(rs.getString(3));
				// System.out.println(project.getId());
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
	public void importCost(String path) {
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
