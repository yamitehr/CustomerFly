package boundry;

import java.text.ParseException;

import javax.swing.JFrame;

import control.ImportControl;
import control.ReportsLogic;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application{
	
	static Stage stg;
	
	public void start(Stage primaryStage) throws Exception {
		
		try {
			stg  = primaryStage;
			Parent root = FXMLLoader.load(getClass().getResource("/boundry/LoginScreen.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("app.css").toExternalForm());	
			primaryStage.setScene(scene);
			primaryStage.setTitle("Customer-Fly");
			primaryStage.setResizable(false);
			primaryStage.initStyle(StageStyle.DECORATED);
			primaryStage.show();
		}
		
		
	 catch(Exception e) {
		e.printStackTrace();
	}
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	//	JFrame report = ReportsLogic.getInstance().makeOrderDetailsReport();
	//	report.setVisible(true);
	//	ImportControl imp = ImportControl.getInstance();
	//	imp.getAllUpdatedFlightsAndTickets();
		launch(args);
	}



}
