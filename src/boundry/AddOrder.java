package boundry;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.PaymentMethod;

public class AddOrder {

	@FXML
    private AnchorPane addOrder;
	
	@FXML
    private TextField orderNum;
    
    @FXML
    private DatePicker dateOfOrder;
    
    @FXML
    private ComboBox<PaymentMethod> payment;
    
    @FXML
    private Button saveOrder;
    
    public void moveToList(ActionEvent event) throws Exception{
    	Parent newRoot = FXMLLoader.load(getClass().getResource("/boundry/Orders.fxml"));
   		Stage primaryStage = (Stage) addOrder.getScene().getWindow();
   		primaryStage.getScene().setRoot(newRoot);
   		primaryStage.show();
    }

}
