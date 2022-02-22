package entity;

import java.sql.Timestamp;
import java.util.Objects;

import util.FlightStatus;

public class Flight {
	private String flightID;
	private Airport departureAirport;
	private Timestamp departureDateTime;
	private Airport destinationAirport;
	private Timestamp destinationDateTime;
	private FlightStatus status;
	private Airplane airplane;
	
	public Flight(String flightID, Airport departureAirport, Timestamp departureDateTime, Airport destinationAirport,
			Timestamp destinationDateTime, FlightStatus status, Airplane airplane) {
		super();
		this.flightID = flightID;
		this.departureAirport = departureAirport;
		this.departureDateTime = departureDateTime;
		this.destinationAirport = destinationAirport;
		this.destinationDateTime = destinationDateTime;
		this.status = status;
		this.airplane = airplane;
	}
	public Flight(String flightID) {
		super();
		this.flightID = flightID;
	}
	public String getFlightID() {
		return flightID;
	}
	public void setFlightID(String flightID) {
		this.flightID = flightID;
	}
	public Airport getDepartureAirport() {
		return departureAirport;
	}
	public void setDepartureAirport(Airport departureAirport) {
		this.departureAirport = departureAirport;
	}
	public Timestamp getDepartureDateTime() {
		return departureDateTime;
	}
	public void setDepartureDateTime(Timestamp departureDateTime) {
		this.departureDateTime = departureDateTime;
	}
	public Airport getDestinationAirport() {
		return destinationAirport;
	}
	public void setDestinationAirport(Airport destinationAirport) {
		this.destinationAirport = destinationAirport;
	}
	public Timestamp getDestinationDateTime() {
		return destinationDateTime;
	}
	public void setDestinationDateTime(Timestamp destinationDateTime) {
		this.destinationDateTime = destinationDateTime;
	}
	public FlightStatus getStatus() {
		return status;
	}
	public void setStatus(FlightStatus status) {
		this.status = status;
	}
	public Airplane getAirplane() {
		return airplane;
	}
	public void setAirplane(Airplane airplane) {
		this.airplane = airplane;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Flight other = (Flight) obj;
		return Objects.equals(flightID, other.flightID);
	}
	@Override
	public String toString() {
		return "Flight Number: " + flightID + ", Departue at " + departureAirport.getCity() + " "+ departureAirport.getCountry() + ", Departure time "
				+ departureDateTime + ", Destination is " + destinationAirport.getCity() + " " + destinationAirport.getCountry() + ", Landing time "
				+ destinationDateTime + ", Status=" + status;
	}
	
}
