package boundry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import control.CheckInLogic;
import control.CustomersControl;
import control.FlightsControl;
import entity.Airplane;
import entity.AirplaneSeat;
import entity.Customer;
import entity.Flight;
import entity.FlightTicket;
import entity.Order;
import entity.ProductOfSupplierInFlight;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import util.Alerts;
import util.MealType;
import util.SeatClass;

public class CheckIn {
	@FXML
	private Button seatTheCustomer;
	@FXML
	private ComboBox<Flight> flights;
	@FXML
	private ListView<FlightTicket> economyList;
	@FXML
	private ListView<FlightTicket> buisnessList;
	@FXML
	private ListView<FlightTicket> firstList;
	@FXML
	private ComboBox<AirplaneSeat> availableSeats;
	private FlightsControl flightsControl = FlightsControl.getInstance();
	private Flight currentFlight;
	private Alert a;
	
	@FXML
 	public void initialize() 
 	{
		initFlights();
		/*
		FlightTicket ft = new FlightTicket(new Order(11223), 123456, SeatClass.Economy, 12, new Customer("254766345"), FlightsControl.getInstance().getFlights().get(0),
				null, FlightsControl.getInstance().getFlights().get(0).getAirplane(), MealType.Kosher,false);
		CustomersControl.getInstance().addTicket(ft);
		
		FlightTicket ft1 = new FlightTicket(new Order(11223), 1234567, SeatClass.Buisness, 12, new Customer("254766345"), FlightsControl.getInstance().getFlights().get(0),
				null, FlightsControl.getInstance().getFlights().get(0).getAirplane(), MealType.Kosher,false);
		CustomersControl.getInstance().addTicket(ft1);
		
		FlightTicket ft2 = new FlightTicket(new Order(11223), 12345678, SeatClass.FirstClass, 12, new Customer("254766345"), FlightsControl.getInstance().getFlights().get(0),
				null, FlightsControl.getInstance().getFlights().get(0).getAirplane(), MealType.Kosher,false);
		CustomersControl.getInstance().addTicket(ft2);*/
 	}
	
	 @FXML
     private void currentFlight() {
    	 currentFlight = flights.getValue();
     }
	
	public void initFlights() {
		ObservableList<Flight> types = FXCollections.observableArrayList(FlightsControl.getInstance().getFlights());
		flights.setItems(FXCollections.observableArrayList(types));
		
		flights.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Flight>() {

			@Override
			public void changed(ObservableValue<? extends Flight> arg0,
					Flight arg1, Flight arg2) {
				if(currentFlight != null) {
					HashMap<SeatClass, List<FlightTicket>> ticketsMap = flightsControl.getAllTicketsByClassesCheckIn(currentFlight);
					
					ObservableList<FlightTicket> ec = FXCollections.observableArrayList(ticketsMap.get(SeatClass.Economy));
					economyList.setItems(FXCollections.observableArrayList(ec));
					
					ObservableList<FlightTicket> bs = FXCollections.observableArrayList(ticketsMap.get(SeatClass.Buisness));
					buisnessList.setItems(FXCollections.observableArrayList(bs));
					
					ObservableList<FlightTicket> fc = FXCollections.observableArrayList(ticketsMap.get(SeatClass.FirstClass));
					firstList.setItems(FXCollections.observableArrayList(fc));
					
					ObservableList<AirplaneSeat> as = FXCollections.observableArrayList(emptySeats());
					availableSeats.setItems(FXCollections.observableArrayList(as));
					
		       	 }
				
			}
    	});
	}
	
	public void clearFields() {
		if(currentFlight != null) {
			HashMap<SeatClass, List<FlightTicket>> ticketsMap = flightsControl.getAllTicketsByClassesCheckIn(currentFlight);
			
			ObservableList<FlightTicket> ec = FXCollections.observableArrayList(ticketsMap.get(SeatClass.Economy));
			economyList.setItems(FXCollections.observableArrayList(ec));
			
			ObservableList<FlightTicket> bs = FXCollections.observableArrayList(ticketsMap.get(SeatClass.Buisness));
			buisnessList.setItems(FXCollections.observableArrayList(bs));
			
			ObservableList<FlightTicket> fc = FXCollections.observableArrayList(ticketsMap.get(SeatClass.FirstClass));
			firstList.setItems(FXCollections.observableArrayList(fc));
			
			ObservableList<AirplaneSeat> as = FXCollections.observableArrayList(emptySeats());
			availableSeats.setItems(FXCollections.observableArrayList(as));
			
       	 }
	}
	
	public ArrayList<AirplaneSeat> emptySeats() {
		ArrayList<AirplaneSeat> allSeats = FlightsControl.getInstance().getFlightSeat();
		ArrayList<AirplaneSeat> seatsInPlane = new ArrayList<AirplaneSeat>();
		ArrayList<AirplaneSeat> temp = new ArrayList<AirplaneSeat>();
		HashMap<SeatClass, List<FlightTicket>> ticketsMap = flightsControl.getAllTicketsByClassesNotCheckIn(currentFlight);
		
		for(AirplaneSeat aps : allSeats) {
			if(aps.getAirplane().equals(currentFlight.getAirplane())) {
				seatsInPlane.add(aps);
			}
		}
		
		ArrayList<AirplaneSeat> toSeat = new ArrayList<AirplaneSeat>();
		
		for(FlightTicket ft : ticketsMap.get(SeatClass.Economy)) {
			temp.add(ft.getSeat());
		}
		
		for(FlightTicket ft : ticketsMap.get(SeatClass.Buisness)) {
			temp.add(ft.getSeat());
		}
		
		for(FlightTicket ft : ticketsMap.get(SeatClass.FirstClass)) {
			temp.add(ft.getSeat());
		}
		
		for(AirplaneSeat sip : seatsInPlane) {
			
			if(!temp.contains(sip)) {
				toSeat.add(sip);
			}
		}
		
		return toSeat;
	}
	@FXML
	public void seatTheCustomer() {
		FlightTicket fte = economyList.getSelectionModel().getSelectedItem();
		FlightTicket ftb = buisnessList.getSelectionModel().getSelectedItem();
		FlightTicket ftf = firstList.getSelectionModel().getSelectedItem();
		
		if(fte != null) {
			AirplaneSeat aps = availableSeats.getSelectionModel().getSelectedItem();
			if(aps != null) {
				CheckInLogic.getInstance().updateTicketSeat(aps.getRow(), aps.getSeat(), fte.getOrder().getOrderID(), fte.getTicketID());
				a = Alerts.infoAlert("Flight ticket number " + fte.getTicketID() + "of Order number " + fte.getOrder().getOrderID() + "was seates in seat "
						+ aps.getRow()+aps.getSeat() + "in class " + fte.getClass());
				a.show();
			}else {
				a = Alerts.errorAlert("Please select a seat first");
				a.show();
			}
		}
		else if(ftb != null) {
			AirplaneSeat aps = availableSeats.getSelectionModel().getSelectedItem();
			if(aps != null) {
				CheckInLogic.getInstance().updateTicketSeat(aps.getRow(), aps.getSeat(), ftb.getOrder().getOrderID(), ftb.getTicketID());
				a = Alerts.infoAlert("Flight ticket number " + ftb.getTicketID() + "of Order number " + ftb.getOrder().getOrderID() + "was seates in seat "
						+ aps.getRow()+aps.getSeat() + "in class " + ftb.getClass());
				a.show();
			}else {
				a = Alerts.errorAlert("Please select a seat first");
				a.show();
			}
		}
		else if(ftf != null) {
			AirplaneSeat aps = availableSeats.getSelectionModel().getSelectedItem();
			if(aps != null) {
				CheckInLogic.getInstance().updateTicketSeat(aps.getRow(), aps.getSeat(), ftf.getOrder().getOrderID(), ftf.getTicketID());
				a = Alerts.infoAlert("Flight ticket number " + ftf.getTicketID() + "of Order number " + ftf.getOrder().getOrderID() + "was seates in seat "
						+ aps.getRow()+aps.getSeat() + "in class " + ftf.getClass());
				a.show();
			} else {
				a = Alerts.errorAlert("Please select a seat first");
				a.show();
			}
		} else if((fte == null && ftb == null && ftf == null) || availableSeats.getSelectionModel().getSelectedItem() == null) {
			a = Alerts.errorAlert("Please select a customer and a seat !");
			a.show();
		}
		
		economyList.getSelectionModel().clearSelection();
		buisnessList.getSelectionModel().clearSelection();
		firstList.getSelectionModel().clearSelection();
		
		clearFields();
	}
	public void moveHomeScreen(ActionEvent event) throws Exception {
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundry/HomeScreenAdmin.fxml"));
		Stage primaryStage = (Stage) flights.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
}
