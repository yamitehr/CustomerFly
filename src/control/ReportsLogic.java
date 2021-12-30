package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.AirPort;
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
	
	public ArrayList<OrderDetail> getOrderDetails() {
		ArrayList<OrderDetail> results = new ArrayList<OrderDetail>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_ORDER_DETAILS_REPORT);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
}
