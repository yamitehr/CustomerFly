package boundry;

import java.util.ArrayList;

import org.jfree.ui.RefineryUtilities;

import control.FlightsControl;
import entity.Airport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class ProductsInFlightRep {
	
	 @FXML
	 AnchorPane productsInFlightReport;
	 
	 @FXML
	 ComboBox<String> citiesList;
	 
	 @FXML
	    public void initialize() {
	    	ArrayList<Airport> airports = FlightsControl.getInstance().getAirports();
	    	ArrayList<String> cities = new ArrayList<String>();
	    	for(Airport ap : airports) {
	    		if(!cities.contains(ap.getCity())) {
	    			cities.add(ap.getCity());
	    		}
	    	}
	    	ObservableList<String> citiesObs = FXCollections.observableArrayList(cities);	
	    	citiesList.setItems(FXCollections.observableArrayList(citiesObs));
	    }
	 
	 /**
	     * creating the report
	     * @param event
	     */
	    @FXML
	    void callProductsInFlightsReport(ActionEvent event) {

    		ProductsInFlightReport demo = new ProductsInFlightReport(citiesList.getValue());
    		demo.setSize(700,700);    
    		RefineryUtilities.centerFrameOnScreen( demo );    
    		demo.setVisible( true );
	    }

	
	 
	 
	 
	 
}
