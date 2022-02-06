package boundry;

import java.io.IOException;


import control.FlightsControl;
import control.ProductSupplierControl;
import entity.EntertainProduct;
import entity.Flight;
import entity.ProductOfSupplier;
import entity.ProductOfSupplierInFlight;
import entity.Supplier;
import exceptions.InvalidInputException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import util.Alerts;
import util.ProductType;

public class AddProductToFlight {
	
	@FXML
    private Pane feedbackPane;
	
	@FXML
    private ComboBox<Flight> flight;
	
	@FXML
    private ListView<ProductOfSupplier> allProd;
	@FXML
    private ListView<ProductOfSupplierInFlight> currentProd;

	@FXML
    private TextArea feed;
	
	  @FXML
	  private Button addFeed;
    
    @FXML
    private Button saveFeed;
    
    @FXML
    private Button editFeed;
    
    @FXML
    private Button add;
    
    @FXML
    private TextField fax;
    
    @FXML
    private Button saveProdcuts;
    private Alert a;
    private Flight currentFlight;
    
    private ProductSupplierControl productSupplierIns = ProductSupplierControl.getInstance();
    
    @FXML
 	public void initialize() 
 	{
    	initProducts();
    	initFlights();
    	initFeedback();
    	
    	currentProd.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ProductOfSupplierInFlight>() {

			@Override
			public void changed(ObservableValue<? extends ProductOfSupplierInFlight> arg0,
					ProductOfSupplierInFlight arg1, ProductOfSupplierInFlight arg2) {
				if(currentProd.getSelectionModel().getSelectedItem() != null) {
		       		 feed.setText(currentProd.getSelectionModel().getSelectedItem().getFeedback());
		       	 }
				
			}
    	});
 	}
     private void initFeedback() {
    	 feed.setDisable(true);
    	 saveFeed.setDisable(true);
     }
     private void initProducts() {
     	ObservableList<ProductOfSupplier> types = FXCollections.observableArrayList(productSupplierIns.getProductsOfSupplier());
     	allProd.setItems(FXCollections.observableArrayList(types));
     }
     private void initFlights() {
      	ObservableList<Flight> types = FXCollections.observableArrayList(FlightsControl.getInstance().getFlights());
      	flight.setItems(FXCollections.observableArrayList(types));
      }
     
     @FXML
     private void currentFlight() {
    	 currentFlight = flight.getValue();
    	 initCurrentProd();
     }
     private void initCurrentProd() {
    	 if(currentFlight != null) {
    		 ObservableList<ProductOfSupplierInFlight> types = FXCollections.observableArrayList(productSupplierIns.getProductsSupplieInFlight(currentFlight.getFlightID()));
    		 currentProd.setItems(FXCollections.observableArrayList(types));
    	 }
     }
     
     @FXML
     private void addToCurrentProducts() {
    	 if(currentFlight == null) {
    		 a = Alerts.errorAlert("Please Select a Flight!");
     		a.show();
    	 }
    	 else if(allProd.getSelectionModel().getSelectedItem() != null) {
    		 ProductOfSupplier ps = allProd.getSelectionModel().getSelectedItem();
    		 
    		ObservableList<ProductOfSupplierInFlight> currentPOSF = currentProd.getItems();
 			ProductOfSupplierInFlight posf = new ProductOfSupplierInFlight(currentFlight.getFlightID(), ps.getSupplierID(), ps.getProductId(), null);
 			currentPOSF.add(posf);
 			currentProd.setItems(currentPOSF);
 			productSupplierIns.addProductSupplierInFlight(posf);
    	 }
     }
     
     @FXML
     private void visiFeedback() {
    	 editFeed.setDisable(true);
    	 feed.setDisable(false);
    	 saveFeed.setDisable(false);
     }
     
     
     @FXML
     private void saveFeedback() {
    	 editFeed.setDisable(false);
    	 feed.setDisable(true);
    	 saveFeed.setDisable(true);
    	 try {
    	 String feedBack = feed.getText();
    	 ProductOfSupplierInFlight posif = currentProd.getSelectionModel().getSelectedItem();
    	 	if(feedBack.isEmpty()) {
				throw new InvalidInputException("Please fill a Feedback");
			}
			if(posif == null) {
				throw new InvalidInputException("Please select a Product In Flight to fill the feedback on");
			}
			if(productSupplierIns.updateFeedback(new ProductOfSupplierInFlight(currentFlight.getFlightID(), posif.getSupplierID(), posif.getProductId(), feedBack))) {
				a = Alerts.infoAlert("Added Feedback Successfully!");
	    		a.show();
			} else {
				throw new InvalidInputException("something went wrong while adding a feedback.");
			}
			
		}  catch(InvalidInputException ipe) {
			a = Alerts.errorAlert(ipe.getMessage());
			a.show();
		} catch(Exception exc) {
			a = Alerts.errorAlert("an error has accured please try again");
 		a.show();
		}
     }
     /*
    public void moveToList(ActionEvent event) throws Exception{
    	Parent newRoot = FXMLLoader.load(getClass().getResource("/boundry/Suppliers.fxml"));
   		Stage primaryStage = (Stage) homeAdmin.getScene().getWindow();
   		primaryStage.getScene().setRoot(newRoot);
   		primaryStage.show();
    }
    */
  
}
