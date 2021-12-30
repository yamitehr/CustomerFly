package boundry;

import java.text.ParseException;

import control.ImportControl;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ImportControl imp = ImportControl.getInstance();
		try {
			imp.importFlightsFromJson();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
