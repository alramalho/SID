package util;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
import mqtt.MqttSubscribe;

public class LoginController implements Initializable{
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private TextField host_name;
	@FXML
	private Button loginButton;


	@FXML
	private ResourceBundle resources;

	// Add a public no-args constructor
	public LoginController()	{
	}

	@FXML
	private void initialize()	{
	}

	@FXML
	private void loginButton(ActionEvent event)	{
		Parent messages;
		try {
			messages = FXMLLoader.load(getClass().getResource("Messages.fxml"));
			Scene scene = new Scene(messages);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();
			new MqttSubscribe().doDemo(username.getText(), password.getText(), host_name.getText());
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}
