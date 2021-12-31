package entity;

import java.util.List;
import java.util.Objects;

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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tailNumber == null) ? 0 : tailNumber.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Airplane other = (Airplane) obj;
		if (tailNumber == null) {
			if (other.tailNumber != null)
				return false;
		} else if (!tailNumber.equals(other.tailNumber))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Airplane [tailNumber=" + tailNumber +"]";
	}
	
	
	
}
