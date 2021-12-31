package util;

public class possibleAlternateFlights {

	public String oldFlightDate;
	public String cityFrom; 
	public String cityTo;
	public String countryFrom; 
	public String countryTo; 
	public String FlightID;
	public possibleAlternateFlights(String oldFlightDate, String cityFrom, String cityTo, String countryFrom,
			String countryTo, String flightID) {
		super();
		this.oldFlightDate = oldFlightDate;
		this.cityFrom = cityFrom;
		this.cityTo = cityTo;
		this.countryFrom = countryFrom;
		this.countryTo = countryTo;
		FlightID = flightID;
	}
}
