package control;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import entity.Airplane;
import entity.AirplaneSeat;
import entity.Airport;
import entity.Flight;
import entity.FlightTicket;
import util.FlightStatus;

public class ImportControl {
		
	private static ImportControl _instance;

	public static ImportControl getInstance() {
		if (_instance == null)
			_instance = new ImportControl();
		return _instance;
	}
	private static FlightsControl flightsControl = FlightsControl.getInstance();
	
	public static ArrayList<Flight> importFlightsFromJson()
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
			//System.out.println(e.getMessage());
		} 
		return ourJsonResult;		
	}
	
	//This method will give us all people we need to call them because of updates
		public static ArrayList<FlightTicket> getAllNeedToCall() {
			ArrayList<Flight> ourJsonResult = importFlightsFromJson();
			ArrayList<Flight> toUpdate = new ArrayList<>();
			ArrayList<Flight> toInsert = new ArrayList<>();
			ArrayList<Flight> allFlights = flightsControl.getFlights(); //the ids that exist
			int counterUpdate = 0;
			int counterInsert = 0;

			for(Flight value : ourJsonResult)
			{
				Boolean isExist = allFlights.contains(value);
			}
			return null;
		}

}
