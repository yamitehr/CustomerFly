package boundry;

import java.sql.SQLException;

import control.FlightsControl;
import control.ImportControl;
import control.ProductSupplierControl;
import entity.EntertainProduct;
import entity.Flight;
import entity.ProductOfSupplier;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

public class ProductsBySupplier {
	
	@FXML
	Text title;

	@FXML
	ListView<EntertainProduct> productsList;
	
	@FXML
	public void initialize() throws ClassNotFoundException, SQLException {
		title.setText("Entertain Products Of Supplier: "+ Suppliers.chooseSupplier.getName());
		for(ProductOfSupplier product : ProductSupplierControl.getInstance().getProductsOfSupplier()) {
			if(product.getSupplierID() == Suppliers.chooseSupplier.getSupplierID()) {
				for(EntertainProduct ep : ProductSupplierControl.getInstance().getProducts()) {
					if(product.getProductId() == ep.getProductID()) {
						productsList.getItems().add(ep);
					}
				}	
			}
		}
		
	}
}
