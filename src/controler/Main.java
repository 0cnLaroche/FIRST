package controler;

import java.awt.Toolkit;
import java.awt.datatransfer.*;
import java.security.NoSuchAlgorithmException;

import javax.swing.table.DefaultTableModel;

public class Main {
	

	public static void main(String[] args) {
		
		/*DataLayer manager;
		try {
			manager = new DataLayer();
			
			manager.disconnect();
		} catch (Exception e) {

			e.printStackTrace();
		}*/
		
		try {
			System.out.println(Admin.hash("Abcmb"));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
   
}


