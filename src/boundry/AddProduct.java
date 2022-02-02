package boundry;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import util.ProductType;

public class AddProduct {

	@FXML
    private AnchorPane addProduct;
	
	@FXML
    private TextField productID;

	@FXML
    private TextField productName;
    
    @FXML
    private TextField description;
    
    @FXML
    private ComboBox<ProductType> type;
    
    @FXML
    private Button saveSupplier;
}
