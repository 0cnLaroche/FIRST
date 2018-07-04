package controler;

import java.time.LocalDate;

import view.ProjectReport;

import java.util.ArrayList;

import model.Run;

public class Main {

	public static void main(String[] args) {
		
		DataLayer manager = new DataLayer();
		
		QueryWriter qw = new QueryWriter();
		qw.exportNetworkList("test.xlsx");
		
		manager.disconnect();
		
		
	}

}
