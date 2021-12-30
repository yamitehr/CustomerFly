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
	
	
	//--------ADD---------//
	public boolean addFlight(Flight flight) {
		
				try {
					Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
					try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
							CallableStatement stmt = conn.prepareCall(Consts.SQL_INS_FLIGHT)){
						
						int i = 1;
						stmt.setString(i++, flight.getFlightID()); // can't be null
						stmt.setString(i++, flight.getDepartureAirport().getAirportCode());
						stmt.setTimestamp(i++, flight.getDepartureDateTime());
						stmt.setString(i++, flight.getDestinationAirport().getAirportCode());
						stmt.setTimestamp(i++, flight.getDestinationDateTime());
						stmt.setString(i++, flight.getStatus().toString());
						stmt.setString(i++, flight.getAirplane().getTailNumber());
						stmt.setDate(i++, Date.valueOf(LocalDate.now()));
						stmt.executeUpdate();
						return true;
						
					} catch (SQLException e) {
					}
				} catch (ClassNotFoundException e) {
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
}
