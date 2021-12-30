package boundry;

import java.text.ParseException;

import javax.swing.JFrame;

import control.ImportControl;
import control.ReportsLogic;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame report = ReportsLogic.getInstance().makeOrderDetailsReport();
		report.setVisible(true);
		ImportControl imp = ImportControl.getInstance();
		imp.getAllNeedToCall();
	}

}
