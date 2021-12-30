package entity;

import java.util.Objects;

public class Airport {
	private String airportCode;
	private String country;
	private String city;
	
	public Airport(String airportCode, String country, String city) {
		super();
		this.airportCode = airportCode;
		this.country = country;
		this.city = city;
	}
	public Airport(String airportCode) {
		super();
		this.airportCode = airportCode;
	}
	public String getAirportCode() {
		return airportCode;
	}
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Airport other = (Airport) obj;
		return Objects.equals(airportCode, other.airportCode);
	}
	
	
}
