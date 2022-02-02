package boundry;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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
}
