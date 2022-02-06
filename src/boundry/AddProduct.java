package boundry;

import java.io.IOException;
import java.time.LocalDateTime;

import control.ProductSupplierControl;
import entity.EntertainProduct;
import entity.ProductOfSupplier;
import entity.Supplier;
import exceptions.InvalidInputException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.Alerts;
import util.ProductType;

public class AddProduct {

	@FXML
    private AnchorPane addProduct;
	
	@FXML
    private TextField productID;

	@FXML
    private TextField name;
    
    @FXML
    private TextField description;
    @FXML
    private TextField supID;

	@FXML
    private TextField supName;
    
    @FXML
    private TextField supEmail;
    @FXML
    private TextField supFax;
    
    @FXML
    private ComboBox<ProductType> type;
    @FXML
    private ComboBox<Supplier> suppliers;
    @FXML
    private Button addSup;
    @FXML
    private TitledPane newSup;
    
    @FXML
    private Button saveProduct;
    private Alert a;
    
    private ProductSupplierControl productSupplierIns = ProductSupplierControl.getInstance();
    
    @FXML
	public void initialize() 
	{
    	initTypes();
    	initSuppliers();
    	newSup.setExpanded(false);
	}
    
    private void initTypes() {
    	ObservableList<ProductType> types = FXCollections.observableArrayList(ProductType.values());
    	type.setItems(FXCollections.observableArrayList(types));
    }
    
    private void initSuppliers() {
    	ObservableList<Supplier> sup = FXCollections.observableArrayList(productSupplierIns.getSuppliers());
    	suppliers.setItems(FXCollections.observableArrayList(sup));
    }
    
    @FXML
    private void saveProduct() {
    	try {
			String productId = productID.getText();
			String productName = name.getText();
			String descriptionStr = description.getText();
			if(productId.isEmpty()) {
				throw new InvalidInputException("Please fill Product ID");
			}
			if(productName.isEmpty()) {
				throw new InvalidInputException("Please fill Product Name");
			}
			if(descriptionStr.isEmpty()) {
				throw new InvalidInputException("Please fill Product Description");
			}
			if(type.getValue() == null) {
				throw new InvalidInputException("Please select Product Type");
			}
			if(suppliers.getValue() == null) {
				throw new InvalidInputException("Please select a Supplier or add a new one");
			}
			if(productSupplierIns.addProduct(new EntertainProduct(Integer.valueOf(productId), productName, descriptionStr, type.getValue().toString()))
					&& productSupplierIns.addProductSupplier(new ProductOfSupplier(suppliers.getValue().getSupplierID(), Integer.valueOf(productId)))) {
				a = Alerts.infoAlert("Added Product Successfully!");
	    		a.show();
			} else {
				throw new InvalidInputException("something went wrong while adding a new product. This ID might be taken");
			}
			
		}  catch(InvalidInputException ipe) {
			a = Alerts.errorAlert(ipe.getMessage());
			a.show();
		} catch(Exception exc) {
			a = Alerts.errorAlert("an error has accured please try again");
    		a.show();
		}
    }
    
    @FXML
    private void addSupplier() throws IOException {
    	try {
			String supplierID = supID.getText();
			String supplierName = supName.getText();
			String supplierEmail = supEmail.getText();
			String supplierPhone = supFax.getText();
			if(supplierID.isEmpty()) {
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
			Supplier newSupplier = new Supplier(Integer.valueOf(supplierID), supplierName, supplierPhone, supplierEmail, null);
			if(productSupplierIns.addSupplier(newSupplier)) {
				a = Alerts.infoAlert("Added Supplier Successfully!");
	    		a.show();
	    		initSuppliers();
	    		suppliers.setValue(newSupplier);
	    		newSup.setExpanded(false);
	    		supID.setText("");;
				supName.setText("");
				supEmail.setText("");
				supFax.setText("");
			} else {
				throw new InvalidInputException("something went wrong while adding a new supplier.");
			}
			
		}  catch(InvalidInputException ipe) {
			a = Alerts.errorAlert(ipe.getMessage());
			a.show();
		} catch(Exception exc) {
			a = Alerts.errorAlert("an error has accured please try again");
    		a.show();
		}
		
    }
    
    public void moveToList(ActionEvent event) throws Exception{
    	Parent newRoot = FXMLLoader.load(getClass().getResource("/boundry/EntertainProducts.fxml"));
   		Stage primaryStage = (Stage) addProduct.getScene().getWindow();
   		primaryStage.getScene().setRoot(newRoot);
   		primaryStage.show();
    }
}
