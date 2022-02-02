package boundry;

import entity.Order;
import entity.Supplier;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

public class Suppliers {

	@FXML
    private AnchorPane suppliersScreen;
	
	@FXML
    private ListView<Supplier> suppliersList;
	
	@FXML
    private Button newSupplier;
	
	@FXML
    private Button removeSupplier;
    
    @FXML
    private Button editSupplier;
    
    @FXML
    private Button showProducts;
}
