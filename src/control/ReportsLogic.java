package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.OrderDetail;
import util.Consts;

public class ReportsLogic {

	private static ReportsLogic _instance;

	private ReportsLogic() {
	}

	public static ReportsLogic getInstance() {
		if (_instance == null)
			_instance = new ReportsLogic();
		return _instance;
	}
	
	public static ArrayList<OrderDetail> getOrderDetails() {
		ArrayList<OrderDetail> results = new ArrayList<OrderDetail>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_ORDER_DETAILS_REPORT);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					String pID = rs.getString(i++);
					String Fname = rs.getString(i++);
					String Lname = rs.getString(i++);
					Integer morning = rs.getInt(i++);
					morning = (morning == null)? 0:morning;
					Integer noon = rs.getInt(i++);
					noon = (noon == null)? 0:noon;
					Integer evening = rs.getInt(i++);
					evening = (evening == null)? 0:evening;
					Integer night = rs.getInt(i++);
					night = (night == null)? 0:night;
					results.add(new OrderDetail(pID,Fname,Lname,morning,noon,evening,night));
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<OrderDetail> od = getOrderDetails();
		for(OrderDetail o :od) {
			System.out.println(o);
		}
	}
}
