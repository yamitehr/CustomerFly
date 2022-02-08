package boundry;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HomeScreenAdmin {
	
	@FXML
	private AnchorPane homeAdmin;

	//for moving window
		public void movePage(String toMove) throws Exception
		{
			Parent newRoot = FXMLLoader.load(getClass().getResource("/boundry/"+toMove+".fxml"));
			Stage primaryStage = (Stage) homeAdmin.getScene().getWindow();
			primaryStage.getScene().setRoot(newRoot);
			primaryStage.show();
		}
		
		@FXML
		public void ordersMove(MouseEvent event) throws Exception
		{
			movePage("Orders");
		}
		
		@FXML
		public void suppliersMove(MouseEvent event) throws Exception
		{
			movePage("Suppliers");
		}
		
		@FXML
		public void productsMove(MouseEvent event) throws Exception
		{
			movePage("EntertainProducts");
		}
		
		@FXML
		public void logout(MouseEvent event) throws Exception
		{
			movePage("LoginScreen");
		}
		
		@FXML
		public void productInFlightMove(MouseEvent event) throws Exception
		{
			movePage("AddProductToFlight");
		}
		
		@FXML
		public void handleFlightsUpdatesMove(MouseEvent event) throws Exception
		{
			movePage("FrmImport");
		}
		
		@FXML
		public void productsInFlightReportMove(MouseEvent event) throws Exception
		{
			movePage("ProductsInFlightReport");
		}
		
		
}
