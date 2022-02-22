package control;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Airplane;
import entity.Customer;
import entity.EntertainProduct;
import entity.PremiumTicket;
import entity.ProductOfSupplier;
import entity.ProductOfSupplierInFlight;
import entity.Supplier;
import util.Consts;
import util.SeatClass;
import util.TicketPrice;

public class ProductSupplierControl {

	private static ProductSupplierControl _instance;

	public static ProductSupplierControl getInstance() {
		if (_instance == null)
			_instance = new ProductSupplierControl();
		return _instance;
	}

	/**
	 * fetches all suppliers from DB file.
	 * 
	 * @return ArrayList of customers.
	 */
	public ArrayList<Supplier> getSuppliers() {
		ArrayList<Supplier> results = new ArrayList<Supplier>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_GET_SUPPLIERS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					Supplier supplier = new Supplier(rs.getInt(i++), rs.getString(i++), rs.getString(i++),
							rs.getString(i++), rs.getString(i++));
					results.add(supplier);
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
	 * fetches all products from DB file.
	 * 
	 * @return ArrayList of customers.
	 */
	public ArrayList<EntertainProduct> getProducts() {
		ArrayList<EntertainProduct> results = new ArrayList<EntertainProduct>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_GET_PRODUCTS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					EntertainProduct product = new EntertainProduct(rs.getInt(i++), rs.getString(i++),
							rs.getString(i++), rs.getString(i++));
					results.add(product);
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
	 * fetches all products from DB file.
	 * 
	 * @return ArrayList of customers.
	 */
	public ArrayList<ProductOfSupplier> getProductsOfSupplier() {
		ArrayList<ProductOfSupplier> results = new ArrayList<ProductOfSupplier>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_GET_PRODUCTS_OF_SUPPLIERS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					ProductOfSupplier product = new ProductOfSupplier(rs.getInt(i++), rs.getInt(i++));
					results.add(product);
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
	 * fetches all products from DB file.
	 * 
	 * @return ArrayList of customers.
	 */
	public ArrayList<ProductOfSupplierInFlight> getProductsSupplieInFlight(String flightNum) {
		ArrayList<ProductOfSupplierInFlight> results = new ArrayList<ProductOfSupplierInFlight>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try {
				Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				PreparedStatement stmt = conn.prepareStatement(Consts.SQL_GET_PRODUCTS_SUPPLIER_BY_FLIGHT);
				stmt.setString(1, flightNum);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					int i = 1;
					ProductOfSupplierInFlight product = new ProductOfSupplierInFlight(rs.getString(i++), rs.getInt(i++),
							rs.getInt(i++), rs.getString(i++));
					results.add(product);
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
	 * add a new supplier to the DB
	 * 
	 * @return true if added, else returns false
	 */
	public boolean addSupplier(Supplier s) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try {
				Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				PreparedStatement stmt = conn.prepareStatement(Consts.SQL_INSERT_SUPPLIER);
				int i = 1;
				stmt.setInt(i++, s.getSupplierID());
				stmt.setString(i++, s.getName());
				stmt.setString(i++, s.getPhoneNumber());
				stmt.setString(i++, s.getEmail());
				stmt.setString(i++, s.getFax());
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
	 * add a new product to the DB
	 * 
	 * @return true if added, else returns false
	 */
	public boolean addProduct(EntertainProduct ep) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try {
				Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				PreparedStatement stmt = conn.prepareStatement(Consts.SQL_INSERT_PRODUCT);
				int i = 1;
				stmt.setInt(i++, ep.getProductID());
				stmt.setString(i++, ep.getName());
				stmt.setString(i++, ep.getDescription());
				stmt.setString(i++, ep.getType().toString());
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
	 * add a new supplier of product to the DB
	 * 
	 * @return true if added, else returns false
	 */
	public boolean addProductSupplier(ProductOfSupplier pos) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try {
				Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				PreparedStatement stmt = conn.prepareStatement(Consts.SQL_INSERT_SUPPLIER_PRODUCT);
				int i = 1;
				stmt.setInt(i++, pos.getSupplierID());
				stmt.setInt(i++, pos.getSupplierID());
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
	 * add a new supplier of product in flight to the DB
	 * 
	 * @return true if added, else returns false
	 */
	public boolean addProductSupplierInFlight(ProductOfSupplierInFlight pos) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try {
				Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				PreparedStatement stmt = conn.prepareStatement(Consts.SQL_INSERT_SUPPLIER_PRODUCT_FLIGHT);
				int i = 1;
				stmt.setString(i++, pos.getFlightId());
				stmt.setInt(i++, pos.getSupplierID());
				stmt.setInt(i++, pos.getProductId());
				stmt.setString(i++, pos.getFeedback());
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
	 * update the feedback of product of supplier in flight to the DB
	 * 
	 * @return true if updated, else returns false
	 */
	public boolean updateFeedback(ProductOfSupplierInFlight pos) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try {
				Connection conn = DriverManager.getConnection(Consts.CONN_STR);
				PreparedStatement stmt = conn.prepareStatement(Consts.SQL_UPDATE_SUPPLIER_PRODUCT_FLIGHT_FEEDBACK);
				int i = 1;
				stmt.setString(i++, pos.getFeedback());
				stmt.setString(i++, pos.getFlightId());
				stmt.setInt(i++, pos.getSupplierID());
				stmt.setInt(i++, pos.getSupplierID());
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

	// -----UPDATE-----//
	public boolean updateSupplier(Supplier supplier) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(util.Consts.SQL_UPDA_SUPPLIER)) {
				int i = 1;
				stmt.setString(i++, supplier.getName());
				stmt.setString(i++, supplier.getPhoneNumber());
				stmt.setString(i++, supplier.getEmail());
				stmt.setString(i++, supplier.getFax());
				stmt.setInt(i++, supplier.getSupplierID());
				stmt.executeUpdate();
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;

	}
	
	public boolean updateProduct(EntertainProduct product) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(util.Consts.SQL_UPDA_PRODUCT)) {
				int i = 1;
				stmt.setString(i++, product.getName());
				stmt.setString(i++, product.getDescription());
				stmt.setString(i++, product.getType().toString());
				stmt.setInt(i++,product.getProductID());
				stmt.executeUpdate();
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;

	}
	
	public boolean removeSupplier(Supplier supplier) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_DELETE_SUPPLIER)) {
				
				stmt.setInt(1, supplier.getSupplierID());
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
