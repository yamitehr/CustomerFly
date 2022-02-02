package boundry;

import entity.Customer;
import entity.EntertainProduct;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

public class EntertainProducts {

	@FXML
    private AnchorPane entertainProductsScreen;
	
	@FXML
    private ListView<EntertainProduct> productsList;
	
	@FXML
    private Button newProduct;
	
	@FXML
    private Button removeProduct;
    
    @FXML
    private Button editProduct;
}
