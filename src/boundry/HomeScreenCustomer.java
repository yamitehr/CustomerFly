package boundry;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HomeScreenCustomer {

	@FXML
    private AnchorPane homeCustomer;
	
	//for moving window
			public void movePage(String toMove) throws Exception
			{
				Parent newRoot = FXMLLoader.load(getClass().getResource("/boundry/"+toMove+".fxml"));
				Stage primaryStage = (Stage) homeCustomer.getScene().getWindow();
				primaryStage.getScene().setRoot(newRoot);
				primaryStage.show();
			}
	
	@FXML
	public void loginMove(MouseEvent event) throws Exception
	{
		movePage("LoginScreen");
	}
	
	@FXML
	public void personalInfoMove(MouseEvent event) throws Exception
	{
		movePage("PersonalInformation");
	}
	
	@FXML
	public void logout(MouseEvent event) throws Exception
	{
		movePage("LoginScreen");
	}

}
