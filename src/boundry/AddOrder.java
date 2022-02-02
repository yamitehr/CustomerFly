package boundry;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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

}
