package boundry;

import java.io.IOException;

import control.ProductSupplierControl;
import entity.Supplier;
import exceptions.InvalidInputException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.Alerts;

public class AddSupplier {
	
	@FXML
    private AnchorPane addSupplier;
	
	@FXML
    private TextField supplierID;

	@FXML
    private TextField name;
	
	  @FXML
	  private TextField phone;
    
    @FXML
    private TextField email;
    
    @FXML
    private TextField fax;
    
    @FXML
    private Button saveSupplier;
    private Alert a;
    
    private ProductSupplierControl productSupplierIns = ProductSupplierControl.getInstance();
    
    public void moveToList(ActionEvent event) throws Exception{
    	Parent newRoot = FXMLLoader.load(getClass().getResource("/boundry/Suppliers.fxml"));
   		Stage primaryStage = (Stage) addSupplier.getScene().getWindow();
   		primaryStage.getScene().setRoot(newRoot);
   		primaryStage.show();
    }
    
    @FXML
    private void addSupplier() throws IOException {
    	try {
    		Supplier newSupplier;
			String supID = supplierID.getText();
			String supplierName = name.getText();
			String supplierEmail = email.getText();
			String supplierPhone = phone.getText();
			String supplierFax = fax.getText();
			if(supID.isEmpty()) {
				throw new InvalidInputException("Please fill Supplier ID");
			}
			if(supplierName.isEmpty()) {
				throw new InvalidInputException("Please fill Supplier Name");
			}
			if(supplierEmail.isEmpty()) {
				throw new InvalidInputException("Please fill Supplier Email");
			}
			if(supplierPhone.isEmpty()) {
				throw new InvalidInputException("Please fill Supplier Phone");
			}
			if(supplierFax.isEmpty()) {
				newSupplier = new Supplier(Integer.valueOf(supID), supplierName, supplierPhone, supplierEmail, null);
			}else {
				newSupplier = new Supplier(Integer.valueOf(supID), supplierName, supplierPhone, supplierEmail, supplierFax);
			}
			if(productSupplierIns.addSupplier(newSupplier)) {
				a = Alerts.infoAlert("Added Supplier Successfully!");
	    		a.show();
	    		supplierID.setText("");;
				name.setText("");
				email.setText("");
				phone.setText("");
				fax.setText("");
			} else {
				throw new InvalidInputException("something went wrong while adding a new supplier. This ID might be taken");
			}
			
		}  catch(InvalidInputException ipe) {
			a = Alerts.errorAlert(ipe.getMessage());
			a.show();
		} catch(Exception exc) {
			a = Alerts.errorAlert("an error has accured please try again");
    		a.show();
		}
		
    }
}
