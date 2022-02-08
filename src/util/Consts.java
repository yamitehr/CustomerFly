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
	public static final String SQL_GET_FLIGHT_TICKETS_ECONOMY_CHECKIN = " { call qryGetEconomyTicketByFlightCheckIn(?) }";
	public static final String SQL_GET_FLIGHT_TICKETS_BUISNESS_CHECKIN = " { call qryGetBuisnessTicketByFlightCheckIn(?) }";
	public static final String SQL_GET_FLIGHT_TICKETS_FIRST_CHECKIN = " { call qryGetFirstClassTicketByFlightCheckIn(?) }";
	public static final String SQL_GET_FLIGHT_TICKETS_ECONOMY_NOT_CHECKIN = " { call qryGetEconomyTicketByFlightNotCheckIn(?) }";
	public static final String SQL_GET_FLIGHT_TICKETS_BUISNESS_NOT_CHECKIN = " { call qryGetBuisnessTicketByFlightNotCheckIn(?) }";
	public static final String SQL_GET_FLIGHT_TICKETS_FIRST_NOT_CHECKIN = " { call qryGetFirstClassTicketByFlightNotCheckIn(?) }";
	public static final String SQL_CANCEL_TICKET = "{ call qryCancelTicket(?,?) }";
	public static final String SQL_FLIGHT_TICKETS_BY_FLIGHT_ID = "{ call qryGetTicketsByFlight(?,?) }";
	public static final String SQL_UPDATE_SEAT_TAKEN = "UPDATE FlightTicketTbl SET RowSeatNumber=?, SeatNumber=? WHERE OrderID=? AND TicketID=?;";
	
	//getting all flight tickets
			public static final String SQL_GET_ALL_FLIGHT_TICKETS = "SELECT FlightTicketTbl.OrderID, FlightTicketTbl.TicketID, FlightTicketTbl.Class, FlightTicketTbl.Price, FlightTicketTbl.CustPassportID ,FlightTicketTbl.FlightID, FlightTicketTbl.RowSeatNumber, FlightTicketTbl.SeatNumber, FlightTicketTbl.AirplaneID, FlightTicketTbl.MealType\r\n"
					+ "FROM FlightTicketTbl ";
	
	//getting all customers
		public static final String SQL_SEL_ALL_CUSTOMERS = "SELECT CustomerTbl.passportID, CustomerTbl.FirstName, CustomerTbl.LastName, CustomerTbl.Email, CustomerTbl.BirthDate ,CustomerTbl.CitizenShip, CustomerTbl.Password\r\n"
				+ "FROM CustomerTbl ";
		
	//for Register//
	public static final String SQL_INS_CUSTOMER=" { call qryInsCustomer(?,?,?,?,?,?,?) }";
	public static final String SQL_UPD_CUSTOMER = "{ call qryUpdCustomer(?,?,?,?,?,?,?) }";
	

	public static final String SQL_CANCEL_FLIGHT = "UPDATE FlightTbl SET FlightStatus='Canceled' WHERE FlightID=?;";
	public static final String SQL_GET_ALL_CUSTOMERS = "SELECT * FROM CustomerTbl;";
	public static final String SQL_INSERT_CUSTOMERS = "INSERT INTO CustomerTbl VALUES(?,?,?,?,?,?);";
	public static final String SQL_GET_ALL_ORDERS = "SELECT * FROM OrderTbl;";
	public static final String SQL_INSERT_ORDER = "INSERT INTO OrderTbl VALUES(?,?,?);";
	public static final String SQL_GET_FLIGHT = "SELECT * FROM FlightTbl WHERE FlightID=?;";
	public static final String SQL_ADD_TICKET = "INSERT INTO FlightTicketTbl VALUES(?,?,?,?,?,?,?,?,?,?,?);";
	public static final String SQL_ADD_PREMIUNM_TICKET = "INSERT INTO PremFlightTicketTbl VALUES(?,?,?,?,?,?);";
	public static final String SQL_UPDATE_TICKET_PRICE = "UPDATE FlightTicketTbl SET Price=? WHERE OrderID=? AND TicketID=?;";
	
	public static final String SQL_GET_SUPPLIERS = "SELECT * FROM SupplierTbl;";
	public static final String SQL_GET_PRODUCTS = "SELECT * FROM EntertainProductTbl;";
	public static final String SQL_GET_PRODUCTS_OF_SUPPLIERS = "SELECT * FROM ProductOfSupplierTbl;";
	public static final String SQL_GET_PRODUCTS_SUPPLIER_BY_FLIGHT = "SELECT * FROM ProductOfSupplierInFlightTbl WHERE FlightID=?;";
	public static final String SQL_INSERT_SUPPLIER = "INSERT INTO SupplierTbl VALUES(?,?,?,?,?);";
	public static final String SQL_INSERT_PRODUCT = "INSERT INTO EntertainProductTbl VALUES(?,?,?,?);";
	public static final String SQL_INSERT_SUPPLIER_PRODUCT = "INSERT INTO ProductOfSupplierTbl VALUES(?,?);";
	public static final String SQL_INSERT_SUPPLIER_PRODUCT_FLIGHT = "INSERT INTO ProductOfSupplierInFlightTbl VALUES(?,?,?,?);";
	public static final String SQL_UPDATE_SUPPLIER_PRODUCT_FLIGHT_FEEDBACK = "UPDATE ProductOfSupplierInFlightTbl SET Feedback=? WHERE FlightID=? AND SupplierID=? AND ProductID=?;";
	public static final String SQL_PRODUCTS_REPORT = "SELECT EntertainProductTbl.Name, Count(EntertainProductTbl.ProductID) AS num\n"
			+ "FROM FlightTbl INNER JOIN (EntertainProductTbl INNER JOIN ProductOfSupplierInFlightTbl ON EntertainProductTbl.ProductID = ProductOfSupplierInFlightTbl.ProductID) ON FlightTbl.FlightID = ProductOfSupplierInFlightTbl.FlightID\n"
			+ "WHERE [FlightTbl].[DepartureAirport] = ? OR [FlightTbl].[DestinationAirport] = ? AND (((Month([FlightTbl].[DeaprtureDateTime]))=Month(Date()))) OR (((Month([FlightTbl].[DestinationDateTime]))=Month(Date())))\n"
			+ "GROUP BY EntertainProductTbl.Name;";

}

