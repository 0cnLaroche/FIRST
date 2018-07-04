package controler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class QueryWriter {
	
	public void exportNetworkList(String path) {
		File file = new File(path);
		ArrayList<String[]> data = new ArrayList<String[]>();
		data = DataLayer.queryFromFile("sql/network_by_approver.sql");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Networks by Approver");
        
		
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			//BufferedWriter bwriter = new BufferedWriter(new FileWriter(file));
			FileOutputStream out = new FileOutputStream(file);
			
			for (int i = 0; i<data.size() -1; i++) {
		        Row row = sheet.createRow(i);
		        String[] line = data.get(i);
				for (int j = 0; j<line.length-1;j++) {
			        Cell cell = row.createCell(j);
			        cell.setCellValue(line[j]);
				}
				//bwriter.write("\n");
			}
			workbook.write(out);
			out.close();
			//bwriter.close();
			
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
