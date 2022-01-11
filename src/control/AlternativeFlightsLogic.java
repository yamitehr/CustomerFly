package control;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import entity.Airplane;
import entity.Airport;
import entity.Customer;
import entity.Flight;
import entity.FlightTicket;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import util.Consts;
import util.FlightStatus;
import util.OrderDetail;
import util.SeatClass;
import util.TakenSeatsClass;
import util.possibleAlternateFlights;

public class AlternativeFlightsLogic {

	private static AlternativeFlightsLogic _instance;

	private AlternativeFlightsLogic() {
	}

	public static AlternativeFlightsLogic getInstance() {
		if (_instance == null)
			_instance = new AlternativeFlightsLogic();
		return _instance;
	}
	
	private static HashMap<Airplane,ArrayList<Integer>> getSeatsOfplane() {
		HashMap<Airplane,ArrayList<Integer>> results = new HashMap<Airplane,ArrayList<Integer>>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEATS_NUM_BY_CLASS_PER_PLANE);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					Airplane p = new Airplane(rs.getString(i++));
					ArrayList<Integer> seatsByClass = new ArrayList<Integer>();
					seatsByClass.add(rs.getInt(i++));seatsByClass.add(rs.getInt(i++));seatsByClass.add(rs.getInt(i++));
					results.put(p,seatsByClass);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	private static  ArrayList<Integer> getTakenSeatsOfClassInFlight(TakenSeatsClass tsc) {
		ArrayList<Integer> results = new ArrayList<Integer>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.takenSeatsofClassOfFlight(tsc.ID, tsc.Fclass));
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					results.add(rs.getInt(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	private static ArrayList<Flight> getPossibleAltFlights(possibleAlternateFlights paf) {
		ArrayList<Flight> results = new ArrayList<Flight>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.possibleAltFlights(paf.oldFlightDate, paf.cityFrom, paf.cityTo, paf.countryFrom, paf.countryTo, paf.FlightID));
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					Flight flight = new Flight(rs.getString(i++),null,				
							rs.getTimestamp(i++),
						     null,
						    null,
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
	
	private static OrderDetail getCustomerFlightsHistory(Customer cust) {
		OrderDetail results = null ;
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.customerFlightsHistory(cust.getPassportID()));
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					String Passport = rs.getString(i++);
					Integer morning = rs.getInt(i++);
					morning = (morning == null)?null:morning;
					Integer noon = rs.getInt(i++);
					noon = (noon == null)?null:noon;
					Integer evening = rs.getInt(i++);
					evening = (evening == null)?null:evening;
					Integer night = rs.getInt(i++);
					evening = (evening == null)?null:evening;
					
					results = new OrderDetail(Passport,morning, noon, evening, night);
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
	 * send message to customer which his/her order was cancelled, the message contains details of alternative flights
	 * @param cancelledFlightID = cancelled order's flightID
	 * @param CancelledFlightDepDate = cancelled order's flight departure date
	 * @param cityFrom = cancelled order's flight departure city
	 * @param cityTo = cancelled order's flight landing city
	 * @param counteyFrom = cancelled order's flight departure country
	 * @param countryTo = cancelled order's flight landing country
	 * @param seatClass = cancelled order's chosen  class 
	 */
	public static void getAlternativeRecomandedFlights(String cancelledFlightID, Timestamp CancelledFlightDepDate, String cityFrom, String cityTo, String counteyFrom, String countryTo, SeatClass seatClass,Customer cust) {
		
		HashMap<Airplane,ArrayList<Integer>> results = getSeatsOfplane();
		LocalDate cancelledDate = CancelledFlightDepDate.toLocalDateTime().toLocalDate();
		String cancelledDateString = cancelledDate.getMonthValue()+"/"+cancelledDate.getDayOfMonth()+"/"+cancelledDate.getYear();
		ArrayList<Flight> alternativeFlights = getPossibleAltFlights(new possibleAlternateFlights(cancelledDateString,cityFrom,cityTo,counteyFrom,countryTo,cancelledFlightID));
		ArrayList<Flight> flightsToRecommend = new ArrayList<Flight>();
		
		if(alternativeFlights == null || alternativeFlights.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Order to flight " + cancelledFlightID+ " of Customer " + cust.getPassportID() + " was cancelled\n" + "No alternative flights were found :(");
		}
		else {
			int classType = (seatClass.equals(SeatClass.FirstClass)?0:seatClass.equals(SeatClass.Buisness)?1:2);
			
			for(Flight f: alternativeFlights) {
				ArrayList<Integer> takenSeats = getTakenSeatsOfClassInFlight(new TakenSeatsClass(f.getFlightID(),seatClass.toString()));
				
				if(results.get(f.getAirplane()).get(classType) - takenSeats.get(0) > 0) {
					flightsToRecommend.add(f);
				}
			}
			if(flightsToRecommend.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Order to flight " + cancelledFlightID+ " of Customer " + cust.getPassportID() + " was cancelled\n" + "No alternative flights were found :(");
			}
			else {
				OrderDetail od = getCustomerFlightsHistory(cust);
				flightsToRecommend.sort((Flight f1, Flight f2) -> {
					int flight1H = getDepTime(f1);
					int flight2H = getDepTime(f2);
					if(flight1H == flight2H)
						return 0;
					else {
						return -1 * comparingHourse(flight1H, flight1H,od);
					}
				});
				String message = "Order to flight " + cancelledFlightID+ " of Customer " + cust.getPassportID() + " was cancelled\n"
						+ "message was sent to the customer with those alternative flights:\n"
						+ " FLIGHT ID  PLANE ID   DEPARTURE DATE    FLIGHT STATUS \n";
				for(Flight f: flightsToRecommend) {
					message += f.getFlightID() + "      " + f.getAirplane().getTailNumber() + "       " + f.getDepartureDateTime() + "      " + f.getStatus() + ".\n";
				}
		
				JOptionPane.showMessageDialog(null, message,"Alternative Flights", JOptionPane.INFORMATION_MESSAGE);
				
			}
		}
	}
	
	
	public static boolean cancelFlightTicket(FlightTicket ticket) {
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_CANCEL_TICKET)){
				int i = 1;
				stmt.setInt(i++, ticket.getOrder().getOrderID());
				stmt.setInt(i++, ticket.getTicketID());

				
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
	
	
	private static int getDepTime(Flight f) {
		int hour = f.getDepartureDateTime().toLocalDateTime().getHour();
		return (hour >= 5 && hour < 11)?0:(hour >= 11 && hour < 17)?1:(hour >= 17 && hour < 23)?2:3;
	}
	
	private static int comparingHourse(int h1, int h2, OrderDetail od) {
		
		if(h1 == 0) {
			if(h2 == 1)
				return od.morning - od.noon;
			else if(h2 == 2)
				return od.morning -od.evening;
			return od.morning-od.night;
		}
		else if(h1 == 1) {
			if(h2 == 0)
				return od.noon - od.morning;
			else if(h2 == 2)
				return od.noon -od.evening;
			return od.noon-od.night;
		}
		else if(h1 == 2) {
			if(h2 == 0)
				return od.evening - od.morning;
			else if(h2 == 1)
				return od.evening -od.noon;
			return od.evening-od.night;
		}
		else {
			if(h2 == 0)
				return od.night - od.morning;
			else if(h2 == 1)
				return od.night -od.noon;
			return od.night-od.evening;
		}
	}
}
