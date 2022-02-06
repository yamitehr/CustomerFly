package boundry;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddSupplier {
	
	@FXML
    private AnchorPane addSupplier;
	
	@FXML
    private TextField supplierID;

	@FXML
    private TextField name;
    
    @FXML
    private TextField email;
    
    @FXML
    private TextField fax;
    
    @FXML
    private Button saveSupplier;
    
    public void moveToList(ActionEvent event) throws Exception{
    	Parent newRoot = FXMLLoader.load(getClass().getResource("/boundry/Suppliers.fxml"));
   		Stage primaryStage = (Stage) addSupplier.getScene().getWindow();
   		primaryStage.getScene().setRoot(newRoot);
   		primaryStage.show();
 }
}
