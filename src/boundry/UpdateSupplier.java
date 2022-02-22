package boundry;

import java.sql.Date;

import control.LoginControl;
import control.ProductSupplierControl;
import entity.Customer;
import entity.Supplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class UpdateSupplier {

	@FXML
    private AnchorPane updateSupplier;
	
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
    
    public void moveToList(ActionEvent event) throws Exception{
    	Parent newRoot = FXMLLoader.load(getClass().getResource("/boundry/Suppliers.fxml"));
   		Stage primaryStage = (Stage) updateSupplier.getScene().getWindow();
   		primaryStage.getScene().setRoot(newRoot);
   		primaryStage.show();
    }
    
    public void saveDetailsChanges(Event event) throws Exception {
    	int id = Integer.valueOf(supplierID.getText());
		Supplier updateSupplier = new Supplier(id, name.getText(), phone.getText(), email.getText(), fax.getText());
		ProductSupplierControl.getInstance().updateSupplier(updateSupplier);
		Alert alert = new Alert(AlertType.INFORMATION,"Your details are updates");
		alert.setHeaderText("You are Succseed to update");
		alert.setTitle("Update Sucssed");
		alert.showAndWait();
	}
    
    @FXML
   	public void initialize() 
   	{
    	supplierID.setText(String.valueOf(Suppliers.chooseSupplier.getSupplierID()));
    	supplierID.setDisable(true);
    	name.setText(Suppliers.chooseSupplier.getName());
    	phone.setText(Suppliers.chooseSupplier.getPhoneNumber());
    	email.setText(Suppliers.chooseSupplier.getFax());
    	if(fax.getText() != null) {
    		fax.setText(Suppliers.chooseSupplier.getFax());
    	}
   	}
}
