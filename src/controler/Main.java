package controler;

import java.time.LocalDate;

import view.ProjectModule;

import java.util.ArrayList;

import model.Run;

public class Main {

	public static void main(String[] args) {
		
		DataLayer manager;
		try {
			manager = new DataLayer();
			QueryWriter qw = new QueryWriter();
			qw.exportNetworkList("test.xlsx");
			
			manager.disconnect();
		} catch (DatabaseCommunicationsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
		
	}

}
