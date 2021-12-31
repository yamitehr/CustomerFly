package control;

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
import entity.Flight;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import util.Consts;
import util.FlightStatus;
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
	public static void getAlternativeRecomandedFlights(String cancelledFlightID, Timestamp CancelledFlightDepDate, String cityFrom, String cityTo, String counteyFrom, String countryTo, SeatClass seatClass) {
		
		HashMap<Airplane,ArrayList<Integer>> results = getSeatsOfplane();
		LocalDate cancelledDate = CancelledFlightDepDate.toLocalDateTime().toLocalDate();
		String cancelledDateString = cancelledDate.getMonthValue()+"/"+cancelledDate.getDayOfMonth()+"/"+cancelledDate.getYear();
		System.out.println(cancelledDateString);
		ArrayList<Flight> alternativeFlights = getPossibleAltFlights(new possibleAlternateFlights(cancelledDateString,cityFrom,cityTo,counteyFrom,countryTo,cancelledFlightID));
		ArrayList<Flight> flightsToRecommend = new ArrayList<Flight>();
		
		if(alternativeFlights == null || alternativeFlights.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No alternative flights were found :(");
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
				JOptionPane.showMessageDialog(null, "No alternative flights were found :(");
			}
			else {
				String message = "FLIGHT ID  PLANE ID   DEPARTURE DATE    FLIGHT STATUS \n";
				for(Flight f: flightsToRecommend) {
					message += f.getFlightID() + "      " + f.getAirplane().getTailNumber() + "       " + f.getDepartureDateTime() + "      " + f.getStatus() + ".\n";
				}
		
				JOptionPane.showMessageDialog(null, message,"Alternative Flights", JOptionPane.INFORMATION_MESSAGE);
				
			}
		}
	}
	
	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		
		System.out.println(getTakenSeatsOfClassInFlight(new TakenSeatsClass("543234","Economy")));
		HashMap<Airplane,ArrayList<Integer>> results = getSeatsOfplane();
		results.forEach((key,value) -> System.out.println(key.getTailNumber() + " " + value.get(0) + " " + value.get(1) + " " + value.get(2)));
		ArrayList<Flight>alternativeFlights = getPossibleAltFlights(new possibleAlternateFlights("12/14/2022","Tel Aviv","Berlin","Israel","Germany","112233"));
		for(Flight f: alternativeFlights) {
			System.out.println(f.getFlightID() + " " + f.getAirplane().getTailNumber() + " " + f.getDepartureDateTime() + " " + f.getStatus());
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		java.util.Date date = sdf.parse("2022/12/14");
		Timestamp Date = new Timestamp(date.getTime());
		getAlternativeRecomandedFlights("112233", Date, "Tel Aviv","Berlin","Israel","Germany", SeatClass.Economy);
	}
}
