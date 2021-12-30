package control;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import entity.Airplane;
import entity.Airport;
import entity.Flight;
import util.Consts;
import util.FlightStatus;

public class FlightsControl {
	private static FlightsControl _instance;

	public static FlightsControl getInstance() {
		if (_instance == null)
			_instance = new FlightsControl();
		return _instance;
	}
	
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
	
	/**
	 * Adding a new Flight with the parameters received from the form.
	 * return true if the insertion was successful, else - return false
     * @return 
	 * @throws InvalidInputException 
	 */
	public boolean addFlight(String flightNum, Timestamp depatureTime, Timestamp landingTime, Airport depatureAirport,
			Airport destinationAirport, Airplane airplane, String cheifPilotID, String coPilotID, String flightStatus) {
		
				try {
					Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
					try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
							CallableStatement stmt = conn.prepareCall(Consts.SQL_INS_FLIGHT)){
						
						int i = 1;
						stmt.setString(i++, flightNum); // can't be null
						stmt.setString(i++, depatureAirport.getAirportCode());
						stmt.setTimestamp(i++, depatureTime);
						stmt.setString(i++, destinationAirport.getAirportCode());
						stmt.setTimestamp(i++, landingTime);
						stmt.setString(i++, flightStatus);
						stmt.setString(i++, airplane.getTailNumber());
						stmt.executeUpdate();
						return true;
						
					} catch (SQLException e) {
					}
				} catch (ClassNotFoundException e) {
				}
		
		return false;
	}

	/**
	 * add a new airport to the data base
	 * @param airPortCode = primary key of the airport
	 * @param city = city which the airport is locate at
	 * @param country = country which the airport is locate at
	 * @param timeZone = time zone of the place according to GMT {in range of -12 -> 12}
	 * @return true if added successfully 
	 */
		public boolean addAirפort(int id, String city, String country) {
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						CallableStatement stmt = conn.prepareCall(Consts.SQL_INS_AIRPORT)){			
					int i = 1;
					
					stmt.setInt(i++, id); // can't be null
					stmt.setString(i++, country);
					stmt.setString(i++, city);
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
		
		
		/**
		 * add a new airplane to the data base
		 * @param tailNum = the id of the airplane example: A-345X
		 * @param attendantsNum = the amount of necessary air attendants for the flight 
		 * @return true if added successfully
		 */
		public boolean addAirplane(String tailNum) {
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						CallableStatement stmt = conn.prepareCall(Consts.SQL_INS_AIRPLANE)){			
					int i = 1;
					
					stmt.setString(i++, tailNum);
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
		
		
		/**
		 * add a new flight seat to the data base
		 * @param id = id of the seat
		 * @param row = row index
		 * @param col = col index
		 * @param type = type of the seat {"first class", "business","tourists"}
		 * @param tailNum = the id of the airplane which the seat belongs to
		 * @return true if added successfully
		 */
		public boolean addFlightSeat(int id , int row, String col , String type ,String tailNum) {
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						CallableStatement stmt = conn.prepareCall(Consts.SQL_INS_FLIGHTSEATS)){			
					int i = 1;
					stmt.setInt(i++, id);
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
	
}
