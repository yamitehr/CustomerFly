package boundry;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import control.FlightsControl;
import control.ProductSupplierControl;
import entity.Airport;
import entity.EntertainProduct;
import entity.Flight;
import entity.FlightTicket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.MealType;
import util.SeatClass;

public class BuyFlightTicket {

	@FXML
    private AnchorPane buyTicket;
	
	@FXML
    private DatePicker from;
	
	@FXML
    private DatePicker until;
	
	@FXML
    private ComboBox<String> fromCountry;
	
	@FXML
    private ComboBox<String> toCountry;
	
	@FXML
    private ComboBox<MealType> mealType;
	
	@FXML
    private ComboBox<SeatClass> classType;
	
	@FXML
    private ListView<Flight> flightsList;
	
	@FXML
    private TextField numOfTickets;
	
	public static Flight chooseFlight = null;
	
	public static SeatClass type;
	
	public static MealType mealT;
	
	
	@FXML 
   	public void flightChoose(MouseEvent arg0) throws Exception{
		chooseFlight = flightsList.getSelectionModel().getSelectedItem();
   	}
	
	public void chooseBtn(ActionEvent event) throws Exception {
		Alert alert = new Alert(AlertType.INFORMATION,"Your flight is saved");
		alert.setHeaderText("You are Succseed to choose");
		alert.setTitle("Choose Sucssed");
		alert.showAndWait();
	}
	
	public void findFlightsBtn(ActionEvent event) throws Exception {
		if(!(from.getValue()==null) && !(until.getValue()==null) && !(fromCountry.getValue()==null)
				&& !(toCountry.getValue()==null) && !(classType.getValue()==null) && !numOfTickets.getText().equals("")) {
			flightsList.getItems().clear();
			LocalDate f = from.getValue();
			LocalDate u = until.getValue();
			Date dateFrom = Date.from(f.atStartOfDay(ZoneId.systemDefault()).toInstant());
			Date dateUntil = Date.from(u.atStartOfDay(ZoneId.systemDefault()).toInstant());
			for(Flight flight : FlightsControl.getInstance().getFlights()) {
				if((flight.getDepartureDateTime().after(dateFrom) && flight.getDepartureDateTime().before(dateUntil)) && 
						(flight.getDepartureAirport().getCountry().equals(fromCountry.getSelectionModel().getSelectedItem())) &&
						(flight.getDestinationAirport().getCountry().equals(toCountry.getSelectionModel().getSelectedItem())) &&
						(FlightsControl.getInstance().getAllSeatsCounts(flight.getAirplane()).get(classType.getValue()) >= Integer.parseInt(numOfTickets.getText()))) {
					flightsList.getItems().add(flight);
				}
			}
		}
		else if(from.getValue()== null|| until.getValue().equals(null) || fromCountry.getValue() == null 
				|| toCountry.getValue() == null || classType.getValue() == null || numOfTickets.getText().equals("")) {
			Alert alert = new Alert(AlertType.ERROR,"ERROR!");
			alert.setHeaderText("You must fill all the fields");
			alert.setTitle("Empty fields");
			alert.showAndWait();
		}
		if(flightsList.getItems().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR,"No flight found");
			alert.setHeaderText("Please choose another details");
			alert.setTitle("Error");
			alert.showAndWait();
		}
	}
	
	
	@FXML
	private void initialize() {
    	ObservableList<MealType> meal = FXCollections.observableArrayList(MealType.values());
    	mealType.setItems(FXCollections.observableArrayList(meal));
    	ObservableList<SeatClass> seat = FXCollections.observableArrayList(SeatClass.values());
    	classType.setItems(FXCollections.observableArrayList(seat));
    	
    	ArrayList<Airport> airports = FlightsControl.getInstance().getAirports();
    	ArrayList<String> countries = new ArrayList<String>();
    	for(Airport ap : airports) {
    		if(!countries.contains(ap.getCountry())) {
    			countries.add(ap.getCountry());
    		}
    	}
    	ObservableList<String> countriesObs = FXCollections.observableArrayList(countries);	
    	fromCountry.setItems(FXCollections.observableArrayList(countriesObs));
    	toCountry.setItems(FXCollections.observableArrayList(countriesObs));
    	

    }
	
	public void moveTicketPremium(ActionEvent event) throws Exception {
		if(chooseFlight == null) {
			Alert alert = new Alert(AlertType.ERROR,"ERROR!");
			alert.setHeaderText("You must choose flight");
			alert.setTitle("No flight selected");
			alert.showAndWait();
		}
		if(mealType.getValue() == null){
			Alert alert = new Alert(AlertType.ERROR,"ERROR!");
			alert.setHeaderText("You must choose meal");
			alert.setTitle("No meal selected");
			alert.showAndWait();
		}
		else {
			Parent newRoot = FXMLLoader.load(getClass().getResource("/boundry/BuyPrimumeTicketFlight.fxml"));
			Stage primaryStage = (Stage) buyTicket.getScene().getWindow();
			primaryStage.getScene().setRoot(newRoot);
			primaryStage.show();
	    	type = classType.getValue();
	    	mealT = mealType.getValue();
		}
		
	}
	
	public void backToHomeScreen(ActionEvent event) throws Exception {
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundry/HomeScreenCustomer.fxml"));
		Stage primaryStage = (Stage) buyTicket.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	
}
