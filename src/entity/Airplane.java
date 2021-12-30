package entity;

import java.util.List;

public class Airplane {
	private String tailNumber;
	private List<AirplaneSeat> seats;
	
	public Airplane(String tailNumber, List<AirplaneSeat> seats) {
		super();
		this.tailNumber = tailNumber;
		this.seats = seats;
	}
	public Airplane(String tailNumber) {
		super();
		this.tailNumber = tailNumber;
	}
	public String getTailNumber() {
		return tailNumber;
	}
	public void setTailNumber(String tailNumber) {
		this.tailNumber = tailNumber;
	}
	public List<AirplaneSeat> getSeats() {
		return seats;
	}
	public void setSeats(List<AirplaneSeat> seats) {
		this.seats = seats;
	}
	
	
}
