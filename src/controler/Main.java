package controler;

import java.io.File;
import java.time.LocalDate;

import view.ProjectModule;

import java.util.ArrayList;

import model.Run;

public class Main {

	public static void main(String[] args) {
		
		DataLayer manager;
		try {
			manager = new DataLayer();
			Admin.login("admin", "abcmb");
			System.out.println(Admin.isAdmin());
			
			// DataLayer.importCost(new File("C://dev/2019.csv"));
			
			//qw.exportNetworkList("test.xlsx");
			
			manager.disconnect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
		
	}

}
