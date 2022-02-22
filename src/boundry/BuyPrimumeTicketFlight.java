package boundry;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BuyPrimumeTicketFlight {

	@FXML
	private AnchorPane premiumTicket;

	@FXML
	private Button readMoreBtn;
	
	@FXML
	private  CheckBox updatePremium;
	
	@FXML
	private Text fill;
	
	@FXML
	private Text one;
	
	@FXML
	private Text two;
	
	@FXML
	private Text three;
	
	@FXML
	private Text weight;
	
	@FXML
	private TextField request1;
	
	@FXML
	private TextField request2;
	
	@FXML
	private TextField request3;
	
	@FXML
	private TextField maxWeight;

	private String massage = "";
	
	static public boolean flag = false;
	
	static public double weightSuitcase;
	
	static public String customerRequest1;
	static public String customerRequest2;
	static public String customerRequest3;

	/**
	 * info about the premium ticket flight
	 */
	private String biggestFlightsInfo = "Premium card gives the customer special rights:\n"
			+ "- Suitcase for the belly of the plane\n" + "- Up to three personal requests\n"
			+ "Premium ticket Makes the flight ticket NIS 20 per kilogram\n";

	private Alert a = new Alert(AlertType.NONE);

	@FXML
	void CallreadMoreICn(MouseEvent event) {

		a.setAlertType(AlertType.INFORMATION);
		a.setHeaderText("PREMIUME TICKET INFO");
		a.setContentText(massage + biggestFlightsInfo);
		a.setTitle("Info");
		a.setHeight(450);
		a.setWidth(450);
		a.show();
	}

	public void backToFightTicket(ActionEvent event) throws Exception {
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundry/BuyFlightTicket.fxml"));
		Stage primaryStage = (Stage) premiumTicket.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}

	@FXML
	public void showDetails (ActionEvent event) throws Exception {
		if(updatePremium.isSelected()) {
			fill.setText("Please fill your requests:");
			one.setText("1.");
			two.setText("2.");
			three.setText("3.");
			weight.setText("Weight of the suitcase");
			request1.setVisible(true);
			request2.setVisible(true);
			request3.setVisible(true);
			maxWeight.setVisible(true);
			
		}
		else {
			fill.setText("");
			one.setText("");
			two.setText("");
			three.setText("");
			weight.setText("");
			request1.setVisible(false);
			request2.setVisible(false);
			request3.setVisible(false);
			maxWeight.setVisible(false);
		}
	}
	
	public void moveToOrderDetails(ActionEvent event) throws Exception {
		if(updatePremium.isSelected()) {
			 flag = true;
		}
		if(!maxWeight.getText().equals("")){
			 weightSuitcase = Double.valueOf(maxWeight.getText());
		}
		if(!request1.getText().equals("")) {
			customerRequest1 = request1.getText();
		}
		if(!request2.getText().equals("")) {
			 customerRequest2 = request2.getText();
		}
		if(request3.getText().equals("")) {
			 customerRequest3 = request3.getText();
		}
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundry/FinishOrderScreen.fxml"));
		Stage primaryStage = (Stage) premiumTicket.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
}
