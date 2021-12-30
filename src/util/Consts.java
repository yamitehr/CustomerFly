package util;

import java.net.URLDecoder;

public class Consts {

	private Consts() {
		
		throw new AssertionError();
	}
	
	protected static final String DB_FILEPATH = getDBPath();
	public static final String CONN_STR = "jdbc:ucanaccess://" + DB_FILEPATH + ";COLUMNORDER=DISPLAY";
	public static final String JDBC_STR = "net.ucanaccess.jdbc.UcanaccessDriver";
	/*----------------------------------------- CONSTS VALUES -----------------------------------------*/
	
	public static final String SQL_ORDER_DETAILS_REPORT  = "SELECT AllCustomersOfUpdatedFlightsQry.PassportID, AllCustomersOfUpdatedFlightsQry.FirstName, AllCustomersOfUpdatedFlightsQry.LastName, AllMorningFlightsQry.CountOfFlightID AS Morning, AllNoonFlightsQry.CountOfFlightID AS Noon, AllEvningFlightsQry.CountOfFlightID AS Evening, AllNightFlightsQry.CountOfFlightID AS Night\r\n"
			+ "FROM (((AllCustomersOfUpdatedFlightsQry LEFT JOIN AllNightFlightsQry ON AllCustomersOfUpdatedFlightsQry.PassportID = AllNightFlightsQry.CustPassportID) LEFT JOIN AllEvningFlightsQry ON AllCustomersOfUpdatedFlightsQry.PassportID = AllEvningFlightsQry.CustPassportID) LEFT JOIN AllNoonFlightsQry ON AllCustomersOfUpdatedFlightsQry.PassportID = AllNoonFlightsQry.CustPassportID) LEFT JOIN AllMorningFlightsQry ON AllCustomersOfUpdatedFlightsQry.PassportID = AllMorningFlightsQry.CustPassportID\r\n"
			+ "GROUP BY AllCustomersOfUpdatedFlightsQry.PassportID, AllCustomersOfUpdatedFlightsQry.FirstName, AllCustomersOfUpdatedFlightsQry.LastName, AllMorningFlightsQry.CountOfFlightID, AllNoonFlightsQry.CountOfFlightID, AllEvningFlightsQry.CountOfFlightID, AllNightFlightsQry.CountOfFlightID;\r\n"
			+ ""; 
			
	
	/*------------------------------------------ASSIGN TO SHIFTS QUERIES ---------------------------------------*/
	/**
	 * find the correct path of the DB file
     * @return the path of the DB file (from eclipse or with runnable file)
	 */
	private static String getDBPath() {
		try {
			String path = Consts.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			String decoded = URLDecoder.decode(path, "UTF-8");
			// System.out.println(decoded) - Can help to check the returned path
			if (decoded.contains(".jar")) {
				decoded = decoded.substring(0, decoded.lastIndexOf('/'));
				return decoded + "/src/entity/DB_CustomerFly.accdb";
			} else {
				decoded = decoded.substring(0, decoded.lastIndexOf("bin/"));
				System.out.println(decoded);
				return decoded + "src/entity/DB_CustomerFly.accdb";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

