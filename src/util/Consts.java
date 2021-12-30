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
	
	/*----------------------------------------- FLIGHTS QUERIES -----------------------------------------*/
	public static final String SQL_SEL_FLIGHT = "SELECT * FROM FlightTbl";
	public static final String SQL_DEL_FLIGHT = "{ call qryDelFlight(?) }";
	public static final String SQL_INS_FLIGHT = "{ call qryInsFlight(?,?,?,?,?,?,?,?,?) }";
	public static final String SQL_UPD_FLIGHT = "{ call qryUpdFlight(?,?,?,?) }";

	/*----------------------------------------- AIRPORTS QUERIES -----------------------------------------*/
	public static final String SQL_SEL_AIRPORT = "SELECT * FROM AirPortTbl";
	public static final String SQL_INS_AIRPORT = "{ call qryInsAirPort(?,?,?,?) }";
	
	/*----------------------------------------- AIRPLANES QUERIES -----------------------------------------*/
	public static final String SQL_SEL_AIRPLANE = "SELECT * FROM AirPlaneTbl";
	public static final String SQL_INS_AIRPLANE = "{ call qryInsAirPlane(?,?) }";
	
	/*------------------------------------------FLIGHT SEATS QUERIES ---------------------------------------*/
	public static final String SQL_SEL_FLIGHTSEATS = "SELECT * FROM FlightSeatTbl";
	public static final String SQL_INS_FLIGHTSEATS = "{ call qryInsFlightSeat(?,?,?,?,?) }";
}

