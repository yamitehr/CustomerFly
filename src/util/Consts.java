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
	/*----------------------------------------- ALTERNSTIVE FLIGHTS QUERIES------------------------------*/	
	public static final String SQL_SEATS_NUM_BY_CLASS_PER_PLANE = "SELECT AirplaneTbl.TailNumber, Sum(IIF((FlightSeatTbl.Class Like 'FirstClass'),1,0)) AS FirstClassSeats, Sum(IIF((FlightSeatTbl.Class Like 'Buisness'),1,0)) AS BuisnessClassSeats, Sum(IIF((FlightSeatTbl.Class Like 'Economy'),1,0)) AS EconomyClassSeats\r\n"
			+ "FROM AirplaneTbl INNER JOIN FlightSeatTbl ON AirplaneTbl.TailNumber = FlightSeatTbl.AirplaneID\r\n"
			+ "GROUP BY AirplaneTbl.TailNumber;";
	
	public static String takenSeatsofClassOfFlight(String FlightID, String Class) {
		return "SELECT Count(FlightTicketTbl.FlightID) AS CountOfFlightID\r\n"
				+ "FROM FlightTicketTbl\r\n"
				+ "WHERE (((FlightTicketTbl.FlightID) Like '" + FlightID + "' ) AND ((FlightTicketTbl.Class) Like '" + Class + "' ));";
	}
	
	public static String possibleAltFlights(String date, String cityFrom, String cityTo, String countryFrom, String countryTo, String FlightID) {
		
		return "SELECT FlightTbl.FlightID, FlightTbl.DeaprtureDateTime, FlightTbl.FlightStatus, FlightTbl.AirplaneID\r\n"
				+ "FROM AirportTbl AS AirportTbl_1 INNER JOIN (AirportTbl INNER JOIN FlightTbl ON AirportTbl.AirportCode = FlightTbl.DepartureAirport) ON AirportTbl_1.AirportCode = FlightTbl.DestinationAirport\r\n"
				+ "WHERE (((DateDiff('d',Date(),FlightTbl.DeaprtureDateTime))>=14) And ((DateDiff('d', #" + date +"#,FlightTbl.DeaprtureDateTime)) Between -14 And 14) And ((AirportTbl.City) Like '" + cityFrom + "' ) And ((AirportTbl_1.City) Like '" + cityTo + "' ) And ((AirportTbl.Country) Like '" + countryFrom + "' ) And ((AirportTbl_1.Country) Like '" + countryTo + "' ) And ((FlightTbl.FlightID) Not Like '"+ FlightID +"' ) And ((FlightTbl.FlightStatus) Like 'OnTime'))\r\n"
				+ "GROUP BY FlightTbl.FlightID, FlightTbl.DeaprtureDateTime, FlightTbl.FlightStatus, FlightTbl.AirplaneID;";
	}
	
	public static String customerFlightsHistory(String passport) {
		
		return "SELECT OrdersDetailsReportQry.PassportID, OrdersDetailsReportQry.Morning, OrdersDetailsReportQry.Noon, OrdersDetailsReportQry.Evening, OrdersDetailsReportQry.Night\r\n"
				+ "FROM OrdersDetailsReportQry\r\n"
				+ "WHERE (((OrdersDetailsReportQry.PassportID) Like '" + passport +"' ))\r\n"
				+ "GROUP BY OrdersDetailsReportQry.PassportID, OrdersDetailsReportQry.Morning, OrdersDetailsReportQry.Noon, OrdersDetailsReportQry.Evening, OrdersDetailsReportQry.Night;\r\n"
				+ "";
	}
	
	/*----------------------------------------- FLIGHTS QUERIES -----------------------------------------*/
	public static final String SQL_SEL_FLIGHT = "SELECT * FROM FlightTbl";
	public static final String SQL_DEL_FLIGHT = "{ call qryDelFlight(?) }";
	public static final String SQL_UPD_FLIGHT = "{ call qryUpdFlight(?,?,?,?,?,?,?,?) }";
	public static final String SQL_INS_FLIGHT = "{ call qryInsertFlight(?,?,?,?,?,?,?,?) }";

	/*----------------------------------------- AIRPORTS QUERIES -----------------------------------------*/
	public static final String SQL_SEL_AIRPORT = "SELECT * FROM AirPortTbl";
	public static final String SQL_INS_AIRPORT = "{ call qryInsAirport(?,?,?) }";
	public static final String SQL_AIRPORT_EXIST ="{ call qryGetAirport(?) }";
	
	/*----------------------------------------- AIRPLANES QUERIES -----------------------------------------*/
	public static final String SQL_SEL_AIRPLANE = "SELECT * FROM AirPlaneTbl";
	public static final String SQL_INS_AIRPLANE = "{ call qryInsAirplane(?) }";
	public static final String SQL_AIRPLANE_EXIST ="{ call qryGetAirplane(?) }";
	
	/*------------------------------------------FLIGHT SEATS QUERIES ---------------------------------------*/
	public static final String SQL_SEL_FLIGHTSEATS = "SELECT * FROM FlightSeatTbl";
	public static final String SQL_INS_FLIGHTSEATS = "{ call qryInsFlightSeat(?,?,?,?) }";
	public static final String SQL_COUNT_ECONOMY_SEATS = "{ call qryCntEconomyClass(?) }";
	public static final String SQL_COUNT_BUISNESS_SEATS = "{ call qryCntBuisnessClass(?) }";
	public static final String SQL_COUNT_FIRST_SEATS = "{ call qryCntFirstClass(?) }";
	public static final String SQL_DELETE_SEATS = "{ call qryDeleteSeat(?) }";
	
	
	/*------------------------------------------FLIGHT TICKETS QUERIES ---------------------------------------*/
	public static final String SQL_GET_FLIGHT_TICKETS_ECONOMY = " { call qryGetEconomyTicketByFlight(?) }";
	public static final String SQL_GET_FLIGHT_TICKETS_BUISNESS = " { call qryGetBuisnessTicketByFlight(?) }";
	public static final String SQL_GET_FLIGHT_TICKETS_FIRST = " { call qryGetFirstTicketByFlight(?) }";
}

