package controler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.CostCenter;

/**Provides methods to format data and write them into excel files 
 * @author samuel.laroche
 *
 */
public class QueryWriter {
	
	/** Creates an excel file from the result of a given SQL query to FIRST database. This shouldn't be used directly 
	 * in the UI.
	 * @param file where to export
	 * @param queryLocation text file where is contained the SQL.
	 * @param title to give the export. Will be used to name the excel sheet.
	 */
	public void export(File file, String queryLocation, String title) {
		
		ArrayList<String[]> data = new ArrayList<String[]>();
		data = DataLayer.queryFromFile(queryLocation);
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(title);
        
		
		try {
			if (!file.exists()) {
				file.createNewFile();
			}

			FileOutputStream out = new FileOutputStream(file);
			
			for (int i = 0; i<data.size() -1; i++) {
		        Row row = sheet.createRow(i);
		        String[] line = data.get(i);
				for (int j = 0; j<line.length;j++) {
			        Cell cell = row.createCell(j);
			        cell.setCellValue(line[j]);
				}

			}
			workbook.write(out);
			out.close();

			
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
	/**Exports Network codes list with their parent project
	 * @param file where to export excel
	 * @throws NullPointerException
	 */
	public void exportNetworkList(File file) throws NullPointerException {
		
		if (file != null) {
			this.export(file, "/sql/network_by_approver.sql", "Network by Approver");
		} else {
			throw new NullPointerException();
		}

	}
	public void exportCSDMapping(File file) throws NullPointerException {
		
		if (file != null) {
			this.export(file, "/sql/csd_map.sql", "CSD-RUN Mapping");
		} else {
			throw new NullPointerException();
		}

	}
	/** Exports RUN list as excel
	 * @param file where to export
	 * @throws NullPointerException
	 */
	public void exportRunList(File file) throws NullPointerException {
		if (file != null) {
			this.export(file, "/sql/run.sql", "RUN");
		} else {
			throw new NullPointerException();
		}

	}
	public void exportProjectList(File file) throws NullPointerException {
		if (file != null) {

			this.export(file, "/sql/projects.sql", "Projects");
		} else {
			throw new NullPointerException();
		}
	}
	/**Exports cost centers in the format :
	 * 	[ID, Description, Manager, Parent Level 1; Level 2; Level 3; ... ; Level 6].
	 * @param file where to export
	 */
	public void exportCostCenterByLevel(File file) {

		
		ArrayList<String[]> data = new ArrayList<String[]>();
		//data = DataLayer.queryFromFile(queryLocation);
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("CostCenters_by_level");
        
        String[] header = {"Cost Center #", "Description", "Manager", "Level 1", "Level 2", "Level 3", "Level 4", "Level 5", "Level 6"};    
        data.add(header);
        
        for (CostCenter cc : DataLayer.getCostCenterList().values()) {
        	data.add(familyToArray(cc));
        	
        }
		
		try {
			if (!file.exists()) {
				file.createNewFile();
			}

			FileOutputStream out = new FileOutputStream(file);
			
			for (int i = 0; i<data.size() -1; i++) {
		        Row row = sheet.createRow(i);
		        String[] line = data.get(i);
				for (int j = 0; j<line.length-1;j++) {
			        Cell cell = row.createCell(j);
			        cell.setCellValue(line[j]);
				}

			}
			workbook.write(out);
			out.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
	}
	/**Function to create an array of the cost center parents. The first element is root followed by every child
	 * down the cost center given in parameter. The other elements of the array will be filled with the cost center
	 * 
	 * @param costcenter
	 * @return cost center parents
	 */
	private String[] familyToArray(CostCenter cc) {
		
		CostCenter parent = cc;
		String[] line = new String[10];
		ArrayList<String> family = new ArrayList<String>();
		int size,gap;
		
		do {
			family.add(parent.getId());
			parent = parent.getParent();
			
		} while (parent != null);
		
		size = family.size();
		gap = 3;
		line[0] = cc.getId();
		line[1] = cc.getNameEN();
		line[2] = cc.getManager();
		
		for (int i = 0; i < size;i++) {
			line[i+gap] = family.get(size-1-i);
		}
		try {
			for (int j = 0 + size+gap; j < 6+gap; j++) {
				line[j] = cc.getId();
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(family.toString());
		}
		return line;
		
	}

}
