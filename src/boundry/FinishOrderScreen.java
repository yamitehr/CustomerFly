package boundry;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;

import control.CustomersControl;
import control.LoginControl;
import control.ProductSupplierControl;
import entity.Customer;
import entity.EntertainProduct;
import entity.Flight;
import entity.FlightTicket;
import entity.Order;
import entity.PremiumTicket;
import entity.ProductOfSupplierInFlight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.PaymentMethod;

public class FinishOrderScreen {

	@FXML
    private AnchorPane orderDetails;
	
	@FXML
    private TextField passportNum;
	
	@FXML
    private TextField passportNum1;
	
	@FXML
    private TextField passportNum2;
	
	@FXML
    private TextField passportNum3;
	
	@FXML
    private TextField firstName;
	
	@FXML
    private TextField lastName;
	
	@FXML
    private TextField flightNum;
	
	@FXML
    private TextField departureTime;
	
	@FXML
    private TextField departureAirport;
	
	@FXML
    private TextField destinationTime;
	
	@FXML
    private TextField destinationAirport;
	
	@FXML
    private TextField totalPrice;
	
	@FXML
    private ComboBox<PaymentMethod> payment;
	
	@FXML
    private ListView<String> customersList;
	
	@FXML
    private ListView<EntertainProduct> productsInFlight;
	
	@FXML
    private CheckBox confirmation;
	
	@FXML
    private Button save;
	
	@FXML
    private Text totalPriceText;
	
	 double priceTicket = CustomersControl.getInstance().calcPrice(BuyFlightTicket.chooseFlight, BuyFlightTicket.mealT, BuyFlightTicket.type);
	 double pricePremiumTicket = CustomersControl.getInstance().calcPricePremium(BuyFlightTicket.chooseFlight, BuyFlightTicket.mealT, BuyFlightTicket.type, BuyPrimumeTicketFlight.weightSuitcase);
	
	   
	
	 @FXML
	public void initialize() {
		save.setDisable(true);
		ObservableList<PaymentMethod> types = FXCollections.observableArrayList(PaymentMethod.values());
		payment.setItems(FXCollections.observableArrayList(types));
		Flight f = BuyFlightTicket.chooseFlight;
		flightNum.setText(f.getFlightID().toString());
		flightNum.setDisable(true);
		departureTime.setText(f.getDepartureDateTime().toString());
		departureTime.setDisable(true);
		departureAirport.setText(f.getDepartureAirport().toString());
		departureAirport.setDisable(true);
		destinationTime.setText(f.getDestinationDateTime().toString());
		destinationTime.setDisable(true);
		destinationAirport.setText(f.getDestinationAirport().toString());
		destinationAirport.setDisable(true);

    	for(ProductOfSupplierInFlight ep : ProductSupplierControl.getInstance().getProductsSupplieInFlight(BuyFlightTicket.chooseFlight.getFlightID())) {
    		for(EntertainProduct product : ProductSupplierControl.getInstance().getProducts()) {
    			if(ep.getProductId() == product.getProductID()) {
    				productsInFlight.getItems().add(product);
    			}
    		}
    	}
	}
	 
	 public void showPrice(ActionEvent event) throws Exception {
		 if(confirmation.isSelected()){
	 		totalPrice.setVisible(true);
	 		totalPriceText.setText("Total Price: ");
	 		save.setDisable(false);
	 		if(BuyPrimumeTicketFlight.flag == true) {
	 			String pricePremium =  String.valueOf(pricePremiumTicket);
	 			totalPrice.setText(pricePremium);
	 		}
	 		else {
	 			String priceFlightTicket =  String.valueOf(priceTicket);
	 			totalPrice.setText(priceFlightTicket);
	 		}
	 	}
	 	else {
	 		totalPrice.setVisible(false);
	 		totalPriceText.setText("");
	 		save.setDisable(true);
	 	}
	 }
		 
	 
	 public void backToFightTicketPremium(ActionEvent event) throws Exception {
			Parent newRoot = FXMLLoader.load(getClass().getResource("/boundry/BuyPrimumeTicketFlight.fxml"));
			Stage primaryStage = (Stage) orderDetails.getScene().getWindow();
			primaryStage.getScene().setRoot(newRoot);
			primaryStage.show();
	}
	 
	 
	 public void saveOrderAndFlightTicket(ActionEvent event) throws Exception {
		 int id  = CustomersControl.getInstance().getMaxOrderId()+1;
		 Date currentDate = Date.valueOf(LocalDateTime.now().toLocalDate());
		 Order order = new Order(id,currentDate,payment.getValue().toString());
		 CustomersControl.getInstance().addOrder(order);
		 int ticketId = 1;
		
		 for(String customer : customersList.getItems()) {
			 String[] customerDetails = customer.split(" ");
			 if(BuyPrimumeTicketFlight.flag == true) {
				 PremiumTicket premiumTicket = new PremiumTicket(order, ticketId++,BuyFlightTicket.type ,priceTicket, new Customer(customerDetails[0]), 
						 BuyFlightTicket.chooseFlight, null, BuyFlightTicket.chooseFlight.getAirplane(),BuyFlightTicket.mealT , false, BuyPrimumeTicketFlight.weightSuitcase,
						 BuyPrimumeTicketFlight.customerRequest1, BuyPrimumeTicketFlight.customerRequest2, BuyPrimumeTicketFlight.customerRequest3);
				 CustomersControl.getInstance().addTicket(premiumTicket);
				 CustomersControl.getInstance().addPremiunmTicket(premiumTicket);
			}
			 else {
				 FlightTicket ticket = new FlightTicket(order, ticketId++,BuyFlightTicket.type ,priceTicket, new Customer(customerDetails[0]), 
						 BuyFlightTicket.chooseFlight, null, BuyFlightTicket.chooseFlight.getAirplane(),BuyFlightTicket.mealT , false);
				 CustomersControl.getInstance().addTicket(ticket);
			 }			
		}
		 Alert alert = new Alert(AlertType.INFORMATION,"Order Succees!");
			alert.setHeaderText("Your order is success");
			alert.setTitle("Succes");
			alert.showAndWait();
			Parent newRoot = FXMLLoader.load(getClass().getResource("/boundry/HomeScreenCustomer.fxml"));
			Stage primaryStage = (Stage) orderDetails.getScene().getWindow();
			primaryStage.getScene().setRoot(newRoot);
			primaryStage.show();
			
	 }
	 
	 public void addCustomerToList(ActionEvent event) throws Exception {
		 customersList.getItems().clear();
		 for(Customer c : CustomersControl.getInstance().getCustomers()) {
				 if(!passportNum.getText().equals("") && passportNum.getText().equals(c.getPassportID())) {
					 customersList.getItems().add(passportNum.getText()+" "+c.getFirstName()+" "+c.getLastName());
				 }
				 if(!passportNum1.getText().equals("") && passportNum1.getText().equals(c.getPassportID())) {
					 customersList.getItems().add(passportNum1.getText()+" "+c.getFirstName()+" "+c.getLastName());
				 }
				 if(!passportNum2.getText().equals("") && passportNum2.getText().equals(c.getPassportID())) {
					 customersList.getItems().add(passportNum2.getText()+" "+c.getFirstName()+" "+c.getLastName());
				 }
				 if(!passportNum3.getText().equals("") && passportNum3.getText().equals(c.getPassportID())) {
					 customersList.getItems().add(passportNum3.getText()+" "+c.getFirstName()+" "+c.getLastName());
				 }
			 }
			 
			
			 
		 }
		 
}

	 
	 
	 
	 
	 
	 

