package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entity.Customer;
import entity.Flight;
import entity.FlightTicket;
import entity.Order;
import entity.PremiumTicket;
import util.Consts;
import util.MealType;
import util.SeatClass;
import util.TicketPrice;

public class CustomersControl {

	private static CustomersControl _instance;
	
	public static CustomersControl getInstance() {
		if (_instance == null)
			_instance = new CustomersControl();
		return _instance;
	}
	
	/**
	 * fetches all customers from DB file.
	 * @return ArrayList of customers.
	 */
	public ArrayList<Customer> getCustomers() {
		ArrayList<Customer> results = new ArrayList<Customer>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_GET_ALL_CUSTOMERS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					Customer customer = new Customer(rs.getString(i++),rs.getString(i++),rs.getString(i++),
							rs.getString(i++),rs.getDate(i++),rs.getString(i++));
					results.add(customer);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	/**
	 * add a new customer to the DB
	 * @return true if added the customer, else returns false
	 */
	public boolean addCustomer(Customer cust) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try { 
				    Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_INSERT_CUSTOMERS);
					int i = 1;
					stmt.setString(i++, cust.getPassportID());
					stmt.setString(i++, cust.getFirstName());
					stmt.setString(i++, cust.getLastName());
					stmt.setString(i++, cust.getEmail());
					stmt.setDate(i++, cust.getBirthDate());
					stmt.setString(i++, cust.getCitizenship());
					stmt.executeUpdate();
					return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * fetches all orders from DB file.
	 * @return ArrayList of customers.
	 */
	public ArrayList<Order> getOrders() {
		ArrayList<Order> results = new ArrayList<Order>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_GET_ALL_ORDERS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					Order order = new Order(rs.getInt(i++),rs.getDate(i++),rs.getString(i++));
					results.add(order);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	/**
	 * fetches max order id from DB file.
	 * @return ArrayList of customers.
	 */
	public int getMaxOrderId() {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_GET_MAX_ORDER_ID);
					ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	/**
	 * add a new order to the DB
	 * @return true if added the order, else returns false
	 */
	public boolean addOrder(Order or) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try { 
				    Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_INSERT_ORDER);
					int i = 1;
					stmt.setInt(i++,or.getOrderID());
					stmt.setDate(i++, or.getOrderDate());
					stmt.setString(i++, or.getPaymentMethod().toString());
					stmt.executeUpdate();
					return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * add a new ticket to the DB
	 * @return true if added the ticket, else returns false
	 */
	public boolean addTicket(FlightTicket ft) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try { 
				    Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_ADD_TICKET);
					int i = 1;
					stmt.setInt(i++,ft.getOrder().getOrderID());
					stmt.setInt(i++,ft.getTicketID());
					stmt.setString(i++, ft.getSeatClass().toString());
					stmt.setDouble(i++, ft.getPrice());
					stmt.setString(i++, ft.getCustomer().getPassportID());
					stmt.setString(i++, ft.getFlight().getFlightID());
					if (ft.getSeat() != null)
						stmt.setInt(i++,ft.getSeat().getRow());
					else
						stmt.setNull(i++, java.sql.Types.INTEGER);
					if (ft.getSeat() != null)
						stmt.setString(i++,ft.getSeat().getSeat());
					else
						stmt.setNull(i++, java.sql.Types.VARCHAR);
					stmt.setString(i++,ft.getAirplane().getTailNumber());
					stmt.setString(i++,ft.getMealType().toString());
					stmt.setBoolean(i++,ft.isCanceled());
					stmt.executeUpdate();
					return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Double calcPrice(Flight flight, MealType mealType, SeatClass classType) {
		double diff = (flight.getDestinationDateTime().getTime() - flight.getDepartureDateTime().getTime()) / (60*60*1000) % 24;
		if(diff < 0 ) {
			diff = diff*(-1);
		}
		int meal = 1;
		if(mealType == MealType.NoMeal)
		{
			meal = 0;
		}
		int seatClass = 0;
		if(classType.equals(SeatClass.Buisness)) {
			seatClass = 1;
		}
		else if(classType.equals(SeatClass.FirstClass)) {
			seatClass = 2;
		}
		return diff*TicketPrice.getT() + meal*TicketPrice.getM() + seatClass*TicketPrice.getT();
	}
	
	
	
	/**
	 * add a new premium ticket to the DB
	 * @return true if added the ticket, else returns false
	 */
	public boolean addPremiunmTicket(PremiumTicket pt) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try { 
				    Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_ADD_PREMIUNM_TICKET);
					int i = 1;
					stmt.setInt(i++,pt.getOrder().getOrderID());
					stmt.setInt(i++,pt.getTicketID());
					stmt.setDouble(i++,pt.getLuggageWeight());
					stmt.setString(i++,pt.getRequest1());
					stmt.setString(i++,pt.getRequest2());
					stmt.setString(i++,pt.getRequest3());
					stmt.executeUpdate();
					return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public Double calcPricePremium(Flight flight, MealType mealType, SeatClass classType, double weight) {
		double diff = (flight.getDestinationDateTime().getTime() - flight.getDepartureDateTime().getTime()) / (60*60*1000) % 24;
		if(diff < 0 ) {
			diff = diff*(-1);
		}
		int meal = 1;
		if(mealType == MealType.NoMeal)
		{
			meal = 0;
		}
		int seatClass = 0;
		if(classType.equals(SeatClass.Buisness)) {
			seatClass = 1;
		}
		else if(classType.equals(SeatClass.FirstClass)) {
			seatClass = 2;
		}
		return diff*TicketPrice.getT() + meal*TicketPrice.getM() + seatClass*TicketPrice.getT() + TicketPrice.getPremium()*weight;
	}
	
	/**
	 * update the price of premium ticket to the DB
	 * @return true if updated, else returns false
	 */

}
