
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private Button loginButton;

	@FXML
	private ResourceBundle resources;

	// Add a public no-args constructor
	public LoginController() {
	}

	@FXML
	private void initialize() {
	}

	@FXML
	private void loginButton(ActionEvent event) {
		System.out.println("Clicked");
		SQL.getInstance().setUsername(username.getText());
		SQL.getInstance().setPassword(password.getText());
		SQL.getInstance().connect();
		if (SQL.getInstance().authSuccessfull()) {
			System.out.println("Auth successfull");
			Parent ttr;
			try {
				ttr = FXMLLoader.load(getClass().getResource("TtrFXML.fxml"));
				Scene scene = new Scene(ttr);
				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				stage.setScene(scene);
				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Authentication failed");
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}
}
