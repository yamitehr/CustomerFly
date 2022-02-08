package boundry;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.JFrame;

import control.AlternativeFlightsLogic;
import control.ImportControl;
import control.ReportsLogic;
import entity.Flight;
import entity.FlightTicket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.SeatClass;

public class FrmImportUpdates 
{

	@FXML
	ListView<Flight> affectedFlights;
	@FXML
	ListView<FlightTicket> affectedTickets;
	private ArrayList<Flight> flights = new ArrayList<Flight>();
	private ArrayList<FlightTicket> flightTickets = new ArrayList<FlightTicket>();
	
	@FXML
	AnchorPane importUpdates;
	
	@FXML
	Button importJson;
	
	@FXML
	Button btnSend;
	
	@FXML
	Button notifyButton;
	
	@FXML
	Label messageLbl;
	
	@FXML
	Button btnReport;
	
	private HashMap<Flight, HashMap<SeatClass, List<FlightTicket>>> allFlightsMap;
	AlternativeFlightsLogic alterFlightLogic = AlternativeFlightsLogic.getInstance();

	
	
	@FXML
	public void initialize() 
	{
	}
	
	//this method will happen after pressing the import JSON button
	@FXML
	public void importFlights(Event event)
	{
		allFlightsMap = ImportControl.getAllUpdatedFlightsAndTickets();
		for(Map.Entry<Flight, HashMap<SeatClass, List<FlightTicket>>> entry : allFlightsMap.entrySet()){
			flights.add(entry.getKey());
		}
		ObservableList<Flight> flt = FXCollections.observableArrayList(flights);
		affectedFlights.setItems(FXCollections.observableArrayList(flt));
		
		for(FlightTicket ticket :  ImportControl.getCustmersCantSeat()) {
			flightTickets.add(ticket);
		}
		ObservableList<FlightTicket> flttk = FXCollections.observableArrayList(flightTickets);
		affectedTickets.setItems(FXCollections.observableArrayList(flttk));
			
	}
	
	@FXML
	public void notify(Event event) {
		if(affectedTickets.getSelectionModel().getSelectedItem() != null) {
			FlightTicket flightTicket = affectedTickets.getSelectionModel().getSelectedItem();
			String selectedFlightID = flightTicket.getFlight().getFlightID();
			Flight currentFlight= null;
			for(Flight flight : flights) {
				if(flight.getFlightID().equals(selectedFlightID)) {
					currentFlight = flight;
				}
			}
			alterFlightLogic.cancelFlightTicket(flightTicket);
			if(currentFlight != null) {
				alterFlightLogic.getAlternativeRecomandedFlights(currentFlight.getFlightID(), currentFlight.getDepartureDateTime(),
						currentFlight.getDepartureAirport().getCity(),
						currentFlight.getDestinationAirport().getCity(), currentFlight.getDepartureAirport().getCountry(),
						currentFlight.getDestinationAirport().getCountry(), flightTicket.getSeatClass(),affectedTickets.getSelectionModel().getSelectedItem().getCustomer());
			}
		}
	}
	//Aviv
	@FXML
	public void report(Event event) {
		ReportsLogic reportLogic = ReportsLogic.getInstance();
		JFrame frame = reportLogic.makeOrderDetailsReport();
		frame.setVisible(true);
	}
	
	 public void moveToHome(ActionEvent event) throws Exception{
	    	Parent newRoot = FXMLLoader.load(getClass().getResource("/boundry/HomeScreenAdmin.fxml"));
	   		Stage primaryStage = (Stage) importUpdates.getScene().getWindow();
	   		primaryStage.getScene().setRoot(newRoot);
	   		primaryStage.show();
	    }
}
