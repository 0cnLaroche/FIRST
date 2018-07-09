package controler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class QueryWriter {
	
	public void export(String exportToPath, String queryLocation, String title) {
		File file = new File(exportToPath);
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
	public void exportNetworkList(String path) {
		this.export(path, "sql/network_by_approver.sql", "Network by Approver");
	}
	public void exportRunList(String path) {
		this.export(path, "sql/run.sql", "RUN");
	}

}
