package boundry;

import java.sql.SQLException;

import control.FlightsControl;
import control.ProductSupplierControl;
import entity.EntertainProduct;
import entity.FlightTicket;
import entity.ProductOfSupplier;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

public class FlightTicketsOfOrder {

	@FXML
	Text title;

	@FXML
	ListView<FlightTicket> ticketsList;
	
	@FXML
	public void initialize() throws ClassNotFoundException, SQLException {
		title.setText("Flight Tickets Of Order: "+ Orders.chooseOrder.getOrderID()+ ", "+ Orders.chooseOrder.getOrderDate());
		for(FlightTicket ft : FlightsControl.getInstance().getFlightTickets()) {
			if(ft.getOrder().getOrderID() == Orders.chooseOrder.getOrderID()) {
				ticketsList.getItems().add(ft);
			}
		}
		
	}
}
