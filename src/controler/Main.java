package controler;

import java.awt.Toolkit;
import java.awt.datatransfer.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import view.ProjectModule;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import model.Run;

public class Main {
	


	public static void main(String[] args) {
		/*
		DataLayer manager;
		try {
			manager = new DataLayer();
			
			manager.disconnect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		DefaultTableModel model = new DefaultTableModel(
                new Object[][]{
            {"A1", "A2", "A3", "A4", "A5"},
            {"B1", "B2", "B3", "B4", "B5"},
            {"C1", "C2", "C3", "C4", "C5"},
            {"D1", "D2", "D3", "D4", "D5"},
            {"E1", "E2", "E3", "E4", "E5"},
            {"F1", "F2", "F3", "F4", "F5"}
        },
                new Object[]{"1", "2", "3", "4", "5"});

        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        cb.setContents(new TableTransferable(model), new ClipboardOwner() {
            @Override
            public void lostOwnership(Clipboard clipboard, Transferable contents) {
                System.out.println("You lose :(");
            }
        });

    }
   
}


