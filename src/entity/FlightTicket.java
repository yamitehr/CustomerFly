package entity;

import util.MealType;
import util.SeatClass;

public class FlightTicket {
	private Order order;
	private int ticketID;
	private SeatClass seatClass;
	private double price;
	private Customer customer;
	private Flight flight;
	private AirplaneSeat seat;
	private Airplane airplane;
	private MealType mealType;
	private boolean isCanceled;
	
	public FlightTicket(Order order, int ticketID, SeatClass seatClass, double price, Customer customer, Flight flight,
			AirplaneSeat seat, Airplane airplane, MealType mealType,boolean isCanceled) {
		super();
		this.order = order;
		this.ticketID = ticketID;
		this.seatClass = seatClass;
		this.price = price;
		this.customer = customer;
		this.flight = flight;
		this.seat = seat;
		this.airplane = airplane;
		this.mealType = mealType;
		this.isCanceled = isCanceled;
	}
	
	
	
	public FlightTicket(Order order, int ticketID, double price,Customer customer) {
		super();
		this.order = order;
		this.ticketID = ticketID;
		this.price = price;
		this.customer = customer;
	}



	public FlightTicket(Order order, int ticketID, SeatClass seatClass, double price, Customer customer, Flight flight,
			AirplaneSeat seat, Airplane airplane, MealType mealType) {
		super();
		this.order = order;
		this.ticketID = ticketID;
		this.seatClass = seatClass;
		this.price = price;
		this.customer = customer;
		this.flight = flight;
		this.seat = seat;
		this.airplane = airplane;
		this.mealType = mealType;
	}


	public FlightTicket(Order order, int ticketID) {
		super();
		this.order = order;
		this.ticketID = ticketID;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public int getTicketID() {
		return ticketID;
	}
	public void setTicketID(int ticketID) {
		this.ticketID = ticketID;
	}
	public SeatClass getSeatClass() {
		return seatClass;
	}
	public void setSeatClass(SeatClass seatClass) {
		this.seatClass = seatClass;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Flight getFlight() {
		return flight;
	}
	public void setFlight(Flight flight) {
		this.flight = flight;
	}
	public AirplaneSeat getSeat() {
		return seat;
	}
	public void setSeat(AirplaneSeat seat) {
		this.seat = seat;
	}
	public Airplane getAirplane() {
		return airplane;
	}
	public void setAirplane(Airplane airplane) {
		this.airplane = airplane;
	}
	public MealType getMealType() {
		return mealType;
	}
	public void setMealType(MealType mealType) {
		this.mealType = mealType;
	}
	@Override
	public String toString() {
		String toReturn;
		if(seat.getRow() == 0) {
			toReturn = "Ticket number " + ticketID + ", of OrderID " + order.getOrderID() + ", Class " + seatClass + ", customer passport " + customer.getPassportID() + ", flight number " + flight.getFlightID();
		} else {
			toReturn = "Ticket number " + ticketID + ", of OrderID " + order.getOrderID() + ", Class " + seatClass + ", customer passport " + customer.getPassportID() + ", flight number " + flight.getFlightID() + ", seat " + seat.getRow() + seat.getSeat();
		}
		return toReturn;
	}
	public boolean isCanceled() {
		return isCanceled;
	}
	public void setCanceled(boolean isCanceled) {
		this.isCanceled = isCanceled;
	}
	
	
}
