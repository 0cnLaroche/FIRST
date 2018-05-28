package controler;

import java.util.Date;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		
		DataLayer manager = new DataLayer();
		
		//ArrayList list = manager.queryRuns("générale");
		//System.out.println(manager.getCostCenter("206410").toString());
		System.out.println(manager.getProject("I-0130").toString());
		
		// manager.importCost("res/export.csv");
		
		manager.disconnect();
		
		
	}

}
