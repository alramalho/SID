import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginJFX extends Application {
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		AnchorPane root = FXMLLoader.load(getClass().getResource("LoginFXML.fxml"));
		primaryStage.setTitle("Login on MySQL");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

	public void updateText(String message) {
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}