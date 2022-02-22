package entity;

import java.util.Objects;

public class AirplaneSeat {
	private int row;
	private String seat;
	private String seatClass;
	private Airplane airplane;
	
	public AirplaneSeat(int row, String seat, String seatClass, Airplane airplane) {
		super();
		this.row = row;
		this.seat = seat;
		this.seatClass = seatClass;
		this.airplane = airplane;
	}
	
	

	public AirplaneSeat(int row, String seat, Airplane airplane) {
		super();
		this.row = row;
		this.seat = seat;
		this.airplane = airplane;
	}



	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public String getSeat() {
		return seat;
	}

	public void setSeat(String seat) {
		this.seat = seat;
	}

	public String getSeatClass() {
		return seatClass;
	}

	public void setSeatClass(String seatClass) {
		this.seatClass = seatClass;
	}

	public Airplane getAirplane() {
		return airplane;
	}

	public void setAirplane(Airplane airplane) {
		this.airplane = airplane;
	}



	@Override
	public int hashCode() {
		return Objects.hash(airplane, row, seat);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AirplaneSeat other = (AirplaneSeat) obj;
		return Objects.equals(airplane, other.airplane) && row == other.row && Objects.equals(seat, other.seat);
	}



	@Override
	public String toString() {
		return "row=" + row + ", seat=" + seat + ", seatClass=" + seatClass + ", airplane=" + airplane;
	}
	
	
}
