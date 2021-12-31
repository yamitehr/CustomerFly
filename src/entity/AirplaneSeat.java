package entity;

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
	
	
}
