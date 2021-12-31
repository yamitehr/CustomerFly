package boundry;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import control.ImportControl;
import entity.Flight;
import entity.FlightTicket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import util.SeatClass;

public class FrmImportUpdates 
{

	@FXML
	ListView<Flight> affectedFlights;
	@FXML
	ListView<FlightTicket> affectedTickets;
	
	@FXML
	Button importJson;
	
	@FXML
	Button btnSend;
	
	@FXML
	Button notifyButton;
	
	@FXML
	Label messageLbl;
	
	@FXML
	Button btnMail;
	
	private HashMap<Flight, HashMap<SeatClass, List<FlightTicket>>> allFlightsMap;

	
	
	@FXML
	public void initialize() 
	{
		btnMail.setVisible(false);
	}
	
	//this method will happen after pressing the import JSON button
	@FXML
	public void onButtonClick(MouseEvent event)
	{
		ArrayList<Flight> flights = new ArrayList<Flight>();
		allFlightsMap = ImportControl.getAllUpdatedFlightsAndTickets();
		for(Map.Entry<Flight, HashMap<SeatClass, List<FlightTicket>>> entry : allFlightsMap.entrySet()){
			flights.add(entry.getKey());
		}
		ObservableList<Flight> flt = FXCollections.observableList(flights);
		affectedFlights.setItems(FXCollections.observableArrayList(flt));
		
		ArrayList<FlightTicket> flightTickets = new ArrayList<FlightTicket>();
		for(FlightTicket ticket :  ImportControl.getCustmersCantSeat()) {
			flightTickets.add(ticket);
		}
		ObservableList<FlightTicket> flttk = FXCollections.observableList(flightTickets);
		affectedTickets.setItems(FXCollections.observableArrayList(flttk));
			
	}
}
