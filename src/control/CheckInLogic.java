package control;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import entity.FlightTicket;
import entity.PremiumTicket;
import util.Consts;
import util.SeatClass;
import util.TicketPrice;

public class CheckInLogic {
	private static CheckInLogic _instance;
		
		public static CheckInLogic getInstance() {
			if (_instance == null)
				_instance = new CheckInLogic();
			return _instance;
		}

		/**
		 * update the price of premium ticket to the DB
		 * @return true if updated, else returns false
		 */
		public boolean updateTicketSeat(int row, String seat, int order, int ticket) {
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try { 
					    Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						PreparedStatement stmt = conn.prepareStatement(Consts.SQL_UPDATE_SEAT_TAKEN);
						stmt.setInt(1, row);
						stmt.setString(2, seat);
						stmt.setInt(3, order);
						stmt.setInt(4, ticket);
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
