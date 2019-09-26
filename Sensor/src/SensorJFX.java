


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SensorJFX extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		AnchorPane root = FXMLLoader.load(getClass().getResource("Sensor.fxml"));
		primaryStage.setTitle("Sensor simulator");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}