package boundry;

import entity.Order;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

public class Orders {

	@FXML
    private AnchorPane ordersScreen;
	
	@FXML
    private ListView<Order> ordersList;
	
	@FXML
    private Button newOrder;
	
	@FXML
    private Button removeOrder;
    
    @FXML
    private Button editOrder;
    
    @FXML
    private Button showFlightTickets;
}
