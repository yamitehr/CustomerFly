package control;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import entity.Customer;
import entity.Flight;
import util.Consts;

public class LoginControl {

	public static LoginControl instance;
	public static LoginControl getInstance() 
	{
		if (instance == null)
			instance = new LoginControl();
		return instance;
	}
	
	private Customer loginCustumer;
	
	public Customer getLoginCustumer() {
		return loginCustumer;
	}

	public void setLoginCustumer(Customer loginCustumer) {
		this.loginCustumer = loginCustumer;
	}
	
	//This method get all customers from DB
		public HashMap<String, Customer> getAllCustumers()
		{
			HashMap<String, Customer> myCutstumers = new HashMap<String, Customer>();
			try {
				Class.forName(Consts.JDBC_STR);
				try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
						PreparedStatement stmt = conn.prepareStatement(util.Consts.SQL_SEL_ALL_CUSTOMERS);
						ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {
						int i = 1;
						Customer c = new Customer(rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getDate(i++), rs.getString(i++),rs.getString(i++));
						myCutstumers.put(c.getPassportID(), c);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return myCutstumers;	
			
		}
		
		//This method return true if the passport number is exist, else false
		public boolean checkPassportNum(Customer c)
		{
			HashMap<String, Customer> allCustumers = getAllCustumers();
			for(Customer cus : allCustumers.values())
			{
				if(c.getPassportID()== cus.getPassportID())
				{
					return true;
				}
			}
			return false;
		}
		
		//this method gets Customer and return true if succeed to add to DB
		public boolean addNewCutsomerToDB(Customer c) throws ClassNotFoundException, SQLException
		{
			//inserting 
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_INS_CUSTOMER)){
				int i = 1;
				stmt.setString(i++,c.getPassportID());
				stmt.setString(i++, c.getFirstName());
				stmt.setString(i++,c.getLastName());
				stmt.setString(i++,c.getEmail());
				stmt.setDate(i++,c.getBirthDate());
				stmt.setString(i++,c.getCitizenship());
				stmt.setString(i++,c.getPassword());
				stmt.executeUpdate();
			}
			return true;	
		}
		
		public boolean updateCustomer(Customer customer)
		{
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
						CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_UPD_CUSTOMER)){
					int i = 1;
					stmt.setString(i++,customer.getFirstName());
					stmt.setString(i++,customer.getLastName());
					stmt.setString(i++, customer.getEmail());
					stmt.setDate(i++, customer.getBirthDate());
					stmt.setString(i++, customer.getCitizenship());
					stmt.setString(i++, customer.getPassword());
					stmt.setString(i++, customer.getPassportID());
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
		
		
	
	
}
