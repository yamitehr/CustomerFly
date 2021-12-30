package entity;

import util.MealType;
import util.SeatClass;

public class PremiumTicket extends FlightTicket {
	private double luggageWeight;
	private String request1;
	private String request2;
	private String request3;
	
	public PremiumTicket(Order order, int ticketID) {
		super(order, ticketID);
	}
	public PremiumTicket(Order order, int ticketID, SeatClass seatClass, double price, Customer customer, Flight flight,
			AirplaneSeat seat, Airplane airplane, MealType mealType, double luggageWeight, String request1,
			String request2, String request3) {
		super(order, ticketID, seatClass, price, customer, flight, seat, airplane, mealType);
		this.luggageWeight = luggageWeight;
		this.request1 = request1;
		this.request2 = request2;
		this.request3 = request3;
	}
	public double getLuggageWeight() {
		return luggageWeight;
	}
	public void setLuggageWeight(double luggageWeight) {
		this.luggageWeight = luggageWeight;
	}
	public String getRequest1() {
		return request1;
	}
	public void setRequest1(String request1) {
		this.request1 = request1;
	}
	public String getRequest2() {
		return request2;
	}
	public void setRequest2(String request2) {
		this.request2 = request2;
	}
	public String getRequest3() {
		return request3;
	}
	public void setRequest3(String request3) {
		this.request3 = request3;
	}
	
}
