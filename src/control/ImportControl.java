package control;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import entity.Airplane;
import entity.AirplaneSeat;
import entity.Airport;
import entity.Flight;
import entity.FlightTicket;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import util.FlightStatus;
import util.SeatClass;

public class ImportControl {
		
	private static ImportControl _instance;

	public static ImportControl getInstance() {
		if (_instance == null)
			_instance = new ImportControl();
		return _instance;
	}
	private static FlightsControl flightsControl = FlightsControl.getInstance();
	public static ArrayList<FlightTicket> custmersCantSeat = new ArrayList<FlightTicket>();

	public static  ArrayList<FlightTicket> getCustmersCantSeat()
	{
		return custmersCantSeat;
	}
	
	private static ArrayList<Flight> importFlightsFromJson()
	{
		ArrayList<Flight> ourJsonResult = new ArrayList<Flight>();

		try 
		{
			JSONObject obj = null;
			try {
				obj = (JSONObject) new JSONParser().parse(new FileReader("json/flights.json"));
			} catch (org.json.simple.parser.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JSONArray jo = (JSONArray) obj.get("Flights") ; 
			Iterator<JSONObject> flightIterator = jo.iterator();
			while(flightIterator.hasNext())
			{
				JSONObject flightItem = flightIterator.next();
				
				//Flight details
				String flightID = (String) flightItem.get("FlightID");	
				Timestamp departureDateTime = Timestamp.valueOf((String) flightItem.get("DepartureDateTime"));
				Timestamp destinationDateTime = Timestamp.valueOf((String) flightItem.get("DestinationDateTime"));
				String status = (String)flightItem.get("Status");

				//Airport1
				String departureAirportCode = (String)flightItem.get("DepartureAirportCode");
				String departureCountry = (String)flightItem.get("DepartureCountry");
				String departureCity = (String)flightItem.get("DepartureCity");
				Airport departureAirport = new Airport(departureAirportCode, departureCountry, departureCity);
				
				
				//Airport2
				String destinationAirportCode = (String)flightItem.get("DestinationAirportCode");
				String destinationCountry = (String)flightItem.get("DestinationCountry");
				String destinationCity = (String)flightItem.get("DestinationCity");
				Airport destinationAirport = new Airport(destinationAirportCode, destinationCountry, destinationCity);
				
				//Airplane and Seats
				String airplaneID = (String)flightItem.get("AirplaneID");
				Airplane airplane = new Airplane(airplaneID);
				
				//iterate over seats
				JSONArray joSeat = (JSONArray) flightItem.get("Seats");
				Iterator<JSONObject> seatIterator = joSeat.iterator();
				List<AirplaneSeat> seatsInAirplane = new ArrayList<AirplaneSeat>();
				while(seatIterator.hasNext())
				{
					JSONObject seatItem = seatIterator.next();
					int rowNum = Integer.parseInt((String)seatItem.get("Row"));
					String seatNum = (String)seatItem.get("Seat");	
					String seatClass = (String)seatItem.get("Class");	
					AirplaneSeat seat = new AirplaneSeat(rowNum, seatNum, seatClass, airplane);
					seatsInAirplane.add(seat);
				}
				
				airplane.setSeats(seatsInAirplane);
				
				//New imported flight
				Flight flight = new Flight(flightID, departureAirport, departureDateTime, destinationAirport,
											destinationDateTime, FlightStatus.valueOf(status), airplane);
				ourJsonResult.add(flight);		
			}
			
			return ourJsonResult;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return ourJsonResult;		
	}
	
	//This method gives us all flight tickets related to flights with changes
		public static HashMap<Flight, HashMap<SeatClass, List<FlightTicket>>> getAllUpdatedFlightsAndTickets() {
			ArrayList<Flight> ourJsonResult = importFlightsFromJson();
			ArrayList<Flight> toUpdate = new ArrayList<>();
			ArrayList<Flight> allFlights = flightsControl.getFlights();
			int counterUpdate = 0;
			int counterInsert = 0;

			for(Flight value : ourJsonResult)
			{
				Boolean isExist = allFlights.contains(value);
				Flight oldFlight = null;
				if(isExist) {
					oldFlight = allFlights.stream().filter(f -> f.equals(value)).findFirst().get();
					
					if(!value.getDepartureAirport().equals(oldFlight.getDepartureAirport())) {
						if(!flightsControl.isExistAirport(value.getDepartureAirport())) {
							flightsControl.addAirport(value.getDepartureAirport());
						}
					}
					if(!value.getDestinationAirport().equals(oldFlight.getDestinationAirport())) {
						if(!flightsControl.isExistAirport(value.getDestinationAirport())) {
							flightsControl.addAirport(value.getDestinationAirport());
						}
					}
					if(!value.getAirplane().equals(oldFlight.getAirplane())) {
						if(!flightsControl.isExistAirplane(value.getAirplane())) {
							flightsControl.addAirplane(value.getAirplane());
							for(AirplaneSeat seat : value.getAirplane().getSeats()) {
								flightsControl.addFlightSeat(seat.getRow(), seat.getSeat(), seat.getAirplane().getTailNumber(), seat.getSeatClass().toString());
							}
						}
					}/*else {//update seats in existing airplane 
						flightsControl.removeAirplaneSeat(value.getAirplane());
						for(AirplaneSeat seat : value.getAirplane().getSeats()) {
							flightsControl.addFlightSeat(seat.getRow(), seat.getSeat(), seat.getAirplane().getTailNumber(), seat.getSeatClass().toString());
						}
					}*/
					flightsControl.updateFlight(value);
					toUpdate.add(value);
					counterUpdate++;
					findTicketsNeedToCancel(value);
				}
				else {
					if(!flightsControl.isExistAirport(value.getDepartureAirport())) {
						flightsControl.addAirport(value.getDepartureAirport());
					}
					if(!flightsControl.isExistAirport(value.getDestinationAirport())) {
						flightsControl.addAirport(value.getDepartureAirport());
					}
					if(!flightsControl.isExistAirplane(value.getAirplane())) {
						flightsControl.addAirplane(value.getAirplane());
						for(AirplaneSeat seat : value.getAirplane().getSeats()) {
							flightsControl.addFlightSeat(seat.getRow(), seat.getSeat(), seat.getAirplane().getTailNumber(), seat.getSeatClass().toString());
						}
					}
					flightsControl.addFlight(value);
					counterInsert++;
				}
			}

			Alert alert = new Alert(AlertType.INFORMATION, "We updated "+ counterUpdate+"\nWe inserted " + counterInsert);
			alert.setHeaderText("Success");
			alert.setTitle("Success");
			alert.showAndWait();
			
			HashMap<Flight, HashMap<SeatClass, List<FlightTicket>>> tb = new HashMap<Flight, HashMap<SeatClass, List<FlightTicket>>>();
			for(Flight updFlight: toUpdate)
			{
				HashMap<SeatClass, List<FlightTicket>> ticketsMap = flightsControl.getAllTicketsByClasses(updFlight);
				tb.put(updFlight, ticketsMap);
			}
			
			return tb;
		}
		
		private static void findTicketsNeedToCancel(Flight flight) {
			HashMap<SeatClass, Integer> seatsInPlame = flightsControl.getAllSeatsCounts(flight.getAirplane());
			/*
			seatsInPlame.put(SeatClass.Economy, 0);
			seatsInPlame.put(SeatClass.Buisness, 0);
			seatsInPlame.put(SeatClass.FirstClass, 0);
			for (AirplaneSeat seat : flight.getAirplane().getSeats()) {
				int num = seatsInPlame.get(SeatClass.valueOf(seat.getSeatClass()));
				num = num +1;
				seatsInPlame.put(SeatClass.valueOf(seat.getSeatClass()), num);
			}*/
			
			HashMap<SeatClass, List<FlightTicket>> currentTicketsInFlight = flightsControl.getAllTicketsByClasses(flight);
			//cancel all tickets if the flight is cancelled
			if(flight.getStatus().equals(FlightStatus.Cancelled)) {
				custmersCantSeat.addAll(currentTicketsInFlight.get(SeatClass.Economy));
				custmersCantSeat.addAll(currentTicketsInFlight.get(SeatClass.Buisness));
				custmersCantSeat.addAll(currentTicketsInFlight.get(SeatClass.FirstClass));
			}else { //cancel tickets if there is no capacity in the plane for each class
				if(seatsInPlame.get(SeatClass.Economy) < currentTicketsInFlight.get(SeatClass.Economy).size()) {
					int delta = currentTicketsInFlight.get(SeatClass.Economy).size() - seatsInPlame.get(SeatClass.Economy);
					int i = 0;
					while(i < delta) {
						custmersCantSeat.add(currentTicketsInFlight.get(SeatClass.Economy).get(i));
						i++;
					}
				}
				if(seatsInPlame.get(SeatClass.Buisness) < currentTicketsInFlight.get(SeatClass.Buisness).size()) {
					int delta = currentTicketsInFlight.get(SeatClass.Buisness).size() - seatsInPlame.get(SeatClass.Buisness);
					int i = 0;
					while(i < delta) {
						custmersCantSeat.add(currentTicketsInFlight.get(SeatClass.Buisness).get(i));
						i++;
					}
				}
				if(seatsInPlame.get(SeatClass.FirstClass) < currentTicketsInFlight.get(SeatClass.FirstClass).size()) {
					int delta = currentTicketsInFlight.get(SeatClass.FirstClass).size() - seatsInPlame.get(SeatClass.FirstClass);
					int i = 0;
					while(i < delta) {
						custmersCantSeat.add(currentTicketsInFlight.get(SeatClass.FirstClass).get(i));
						i++;
					}
				}
			}
			
		}
}
