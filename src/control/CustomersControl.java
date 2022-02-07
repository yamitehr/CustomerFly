package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entity.Customer;
import entity.FlightTicket;
import entity.Order;
import entity.PremiumTicket;
import util.Consts;
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
	 * add a new order to the DB
	 * @return true if added the customer, else returns false
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
					long diff = ft.getFlight().getDestinationDateTime().getTime() - ft.getFlight().getDepartureDateTime().getTime();
					int meal = 1;
					if(ft.getMealType() == null)
					{
						meal = 0;
					}
					int seatClass = 0;
					if(ft.getSeatClass().equals(SeatClass.Buisness)) {
						seatClass = 1;
					}
					else if(ft.getSeatClass().equals(SeatClass.FirstClass)) {
						seatClass = 2;
					}
					double price = diff*TicketPrice.getT() + meal*TicketPrice.getM() + seatClass*TicketPrice.getT();
					stmt.setDouble(i++, price);
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
					return updateTicketPrice(pt);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * update the price of premium ticket to the DB
	 * @return true if updated, else returns false
	 */
	public boolean updateTicketPrice(PremiumTicket ft) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try { 
				    Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_UPDATE_TICKET_PRICE);
					long diff = ft.getFlight().getDestinationDateTime().getTime() - ft.getFlight().getDepartureDateTime().getTime();
					int meal = 1;
					if(ft.getMealType() == null)
					{
						meal = 0;
					}
					int seatClass = 0;
					if(ft.getSeatClass().equals(SeatClass.Buisness)) {
						seatClass = 1;
					}
					else if(ft.getSeatClass().equals(SeatClass.FirstClass)) {
						seatClass = 2;
					}
					double price = diff*TicketPrice.getT() + meal*TicketPrice.getM() + seatClass*TicketPrice.getT() + TicketPrice.getPremium();
					stmt.setDouble(1, price);
					stmt.setInt(2, ft.getOrder().getOrderID());
					stmt.setInt(3, ft.getTicketID());
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
}
