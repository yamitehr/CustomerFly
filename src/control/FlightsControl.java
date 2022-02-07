package control;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entity.Airplane;
import entity.AirplaneSeat;
import entity.Airport;
import entity.Customer;
import entity.Flight;
import entity.FlightTicket;
import entity.Order;
import javafx.scene.control.Alert;
import util.Consts;
import util.FlightStatus;
import util.MealType;
import util.SeatClass;

public class FlightsControl {
	private static FlightsControl _instance;
	private Alert a;
//rotem
	public static FlightsControl getInstance() {
		if (_instance == null)
			_instance = new FlightsControl();
		return _instance;
	}
	//--------GET---------//
	/**
	 * fetches all flights from DB file.
	 * @return ArrayList of flights.
	 */
	public ArrayList<Flight> getFlights() {
		ArrayList<Flight> results = new ArrayList<Flight>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_FLIGHT);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					Flight flight = new Flight(rs.getString(i++),
							new Airport(rs.getString(i++)),
							rs.getTimestamp(i++),
						     new Airport(rs.getString(i++)),
						     rs.getTimestamp(i++),
						     FlightStatus.valueOf(rs.getString(i++)),
						     new Airplane(rs.getString(i++)));
					results.add(flight);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	//Check
	public boolean isExistAirport(Airport airport)
	{
		String id = airport.getAirportCode();
		String airportCode = "";
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement callst = conn.prepareCall(Consts.SQL_AIRPORT_EXIST))
					{
					int i=1;
					callst.setString(i++, id);
					
					ResultSet rs = callst.executeQuery();
					while (rs.next()) 
						airportCode = rs.getString(1);
					
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if(airportCode.isEmpty()) {
			return false;
		}
		return true;
	}
	
	public boolean isExistAirplane(Airplane airplane)
	{
		String id = airplane.getTailNumber();
		String tailNumber = "";
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement callst = conn.prepareCall(Consts.SQL_AIRPLANE_EXIST))
					{
					int i=1;
					callst.setString(i++, id);
					
					ResultSet rs = callst.executeQuery();
					while (rs.next()) 
						tailNumber = rs.getString(1);
					
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if(tailNumber.isEmpty()) {
			return false;
		}
		return true;
		
	}
	
	public HashMap<SeatClass, Integer> getAllSeatsCounts(Airplane airplane){
		HashMap<SeatClass, Integer> seatsCounts = new HashMap<SeatClass, Integer>();
		int economyCount=0;
		int buisnessCount=0;
		int firstCount=0;
		
		economyCount = getNumOfSeatsByClass(airplane, Consts.SQL_COUNT_ECONOMY_SEATS);
		buisnessCount = getNumOfSeatsByClass(airplane, Consts.SQL_COUNT_BUISNESS_SEATS);
		firstCount = getNumOfSeatsByClass(airplane, Consts.SQL_COUNT_FIRST_SEATS);
		seatsCounts.put(SeatClass.Economy, economyCount);
		seatsCounts.put(SeatClass.Buisness, buisnessCount);
		seatsCounts.put(SeatClass.FirstClass, firstCount);
		
		return seatsCounts;
	}
	
	private int getNumOfSeatsByClass(Airplane airplane, String query) {
		int count = 0;
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement callst = conn.prepareCall(query))
					{
					int k=1;
					callst.setString(k++, airplane.getTailNumber());
					ResultSet rs = callst.executeQuery();
					while (rs.next()) 
					{
						int i =1;
						count = (rs.getInt(i++));
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return count;	
	}
	
	public HashMap<SeatClass, List<FlightTicket>>getAllTicketsByClasses(Flight flight) {
		HashMap<SeatClass, List<FlightTicket>> toReturn = new HashMap<SeatClass, List<FlightTicket>>();
		List<FlightTicket> economyTickets = getTicketsByClass(flight, Consts.SQL_GET_FLIGHT_TICKETS_ECONOMY);
		List<FlightTicket> buisnessTickets = getTicketsByClass(flight, Consts.SQL_GET_FLIGHT_TICKETS_BUISNESS);
		List<FlightTicket> firstTickets = getTicketsByClass(flight, Consts.SQL_GET_FLIGHT_TICKETS_FIRST);
		
		toReturn.put(SeatClass.Economy, economyTickets);
		toReturn.put(SeatClass.Buisness, buisnessTickets);
		toReturn.put(SeatClass.FirstClass, firstTickets);
		
		return toReturn;
	}
	
	public HashMap<SeatClass, List<FlightTicket>>getAllTicketsByClassesCheckIn(Flight flight) {
		HashMap<SeatClass, List<FlightTicket>> toReturn = new HashMap<SeatClass, List<FlightTicket>>();
		List<FlightTicket> economyTickets = getTicketsByClass(flight, Consts.SQL_GET_FLIGHT_TICKETS_ECONOMY_CHECKIN);
		List<FlightTicket> buisnessTickets = getTicketsByClass(flight, Consts.SQL_GET_FLIGHT_TICKETS_BUISNESS_CHECKIN);
		List<FlightTicket> firstTickets = getTicketsByClass(flight, Consts.SQL_GET_FLIGHT_TICKETS_FIRST_CHECKIN);
		
		toReturn.put(SeatClass.Economy, economyTickets);
		toReturn.put(SeatClass.Buisness, buisnessTickets);
		toReturn.put(SeatClass.FirstClass, firstTickets);
		
		return toReturn;
	}
	
	public HashMap<SeatClass, List<FlightTicket>>getAllTicketsByClassesNotCheckIn(Flight flight) {
		HashMap<SeatClass, List<FlightTicket>> toReturn = new HashMap<SeatClass, List<FlightTicket>>();
		List<FlightTicket> economyTickets = getTicketsByClass(flight, Consts.SQL_GET_FLIGHT_TICKETS_ECONOMY_NOT_CHECKIN);
		List<FlightTicket> buisnessTickets = getTicketsByClass(flight, Consts.SQL_GET_FLIGHT_TICKETS_BUISNESS_NOT_CHECKIN);
		List<FlightTicket> firstTickets = getTicketsByClass(flight, Consts.SQL_GET_FLIGHT_TICKETS_FIRST_NOT_CHECKIN);
		
		toReturn.put(SeatClass.Economy, economyTickets);
		toReturn.put(SeatClass.Buisness, buisnessTickets);
		toReturn.put(SeatClass.FirstClass, firstTickets);
		
		return toReturn;
	}
	private ArrayList<FlightTicket> getTicketsByClass(Flight flight, String query) {
		ArrayList<FlightTicket> results = new ArrayList<FlightTicket>();
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement callst = conn.prepareCall(query))
					{
					int k=1;
					callst.setString(k++, flight.getFlightID());
					ResultSet rs = callst.executeQuery();
					while (rs.next()) 
					{
						int i = 1;
						FlightTicket flightTicket = new FlightTicket(new Order(rs.getInt(i++)),
								rs.getInt(i++),
								SeatClass.valueOf(rs.getString(i++)),
								rs.getDouble(i++),
							     new Customer(rs.getString(i++)),
							     new Flight(rs.getString(i++)),
							     new AirplaneSeat(rs.getInt(i++), rs.getString(i++), new Airplane(flight.getAirplane().getTailNumber())),
							     new Airplane(rs.getString(i++)),
							     MealType.valueOf(rs.getString(i++)),
							    		 false);
						results.add(flightTicket);
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	//--------ADD---------//
	public boolean addFlight(Flight flight) {
		
				try {
					Class.forName(Consts.JDBC_STR);
					try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
							CallableStatement callst = conn.prepareCall(Consts.SQL_INS_FLIGHT)){
						
						int i = 1;
						callst.setString(i++, flight.getFlightID()); // can't be null
						callst.setString(i++, flight.getDepartureAirport().getAirportCode());
						callst.setTimestamp(i++, flight.getDepartureDateTime());
						callst.setString(i++, flight.getDestinationAirport().getAirportCode());
						callst.setTimestamp(i++, flight.getDestinationDateTime());
						callst.setString(i++, flight.getStatus().toString());
						callst.setString(i++, flight.getAirplane().getTailNumber());
						callst.setDate(i++, Date.valueOf(LocalDate.now()));
						callst.executeUpdate();
						return true;
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
		
		return false;
	}

		public boolean addAirport(Airport airport) {
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						CallableStatement stmt = conn.prepareCall(Consts.SQL_INS_AIRPORT)){			
					int i = 1;
					
					stmt.setString(i++, airport.getAirportCode()); // can't be null
					stmt.setString(i++, airport.getCountry());
					stmt.setString(i++, airport.getCity());
					stmt.executeUpdate();
					return true;
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return false;
		}
		
		public boolean addAirplane(Airplane airplane) {
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						CallableStatement stmt = conn.prepareCall(Consts.SQL_INS_AIRPLANE)){			
					int i = 1;
					
					stmt.setString(i++, airplane.getTailNumber());
					stmt.executeUpdate();
					return true;
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return false;
		}
		
		public boolean addFlightSeat(int row, String col , String tailNum ,String type) {
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						CallableStatement stmt = conn.prepareCall(Consts.SQL_INS_FLIGHTSEATS)){			
					int i = 1;
					stmt.setInt(i++, row);
					stmt.setString(i++, col);
					stmt.setString(i++, tailNum);
					stmt.setString(i++, type);
					stmt.executeUpdate();
					return true;
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return false;
		}
		
		
		public ArrayList<AirplaneSeat> getFlightSeat() {
			ArrayList<AirplaneSeat> results = new ArrayList<AirplaneSeat>();
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_FLIGHTSEATS);
						ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {
						int i = 1;
						AirplaneSeat flight = new AirplaneSeat(
								rs.getInt(i++),
								rs.getString(i++),
								rs.getString(4),
							    new Airplane(rs.getString(3)));
						results.add(flight);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return results;
		}
		//-----UPDATE-----//
		public boolean updateFlight(Flight flight)
		{
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
						CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_UPD_FLIGHT)){
					int i = 1;
					stmt.setString(i++,flight.getDepartureAirport().getAirportCode());
					stmt.setTimestamp(i++,flight.getDepartureDateTime());
					stmt.setString(i++, flight.getDestinationAirport().getAirportCode());
					stmt.setTimestamp(i++, flight.getDestinationDateTime());
					stmt.setString(i++, flight.getStatus().toString());
					stmt.setString(i++, flight.getAirplane().getTailNumber());
					stmt.setDate(i++, Date.valueOf(LocalDate.now()));
					stmt.setString(i++, flight.getFlightID());
					stmt.executeUpdate();
				}
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		return false;
			
		}
		
		public boolean removeAirplaneSeat(Airplane airplane) {
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						CallableStatement stmt = conn.prepareCall(Consts.SQL_DELETE_SEATS)) {
					
					stmt.setString(1, airplane.getTailNumber());
					stmt.executeUpdate();
					return true;
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return false;
		}
}
