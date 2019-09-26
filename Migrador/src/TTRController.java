
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class TTRController implements Initializable{
	@FXML
	private TextField ttr;
	@FXML
	private TextField margTemp;
	@FXML
	private TextField margLum;
	@FXML
	private TextField limiteMensagens;
	
	@FXML
	private Button ok;

	@FXML
	private ResourceBundle resources;

	public TTRController()	{
	}

	@FXML
	private void initialize()	{
	}

	@FXML
	private void ok(ActionEvent event)	{
		SQL.getInstance().setTTR(Long.parseLong(ttr.getText()));
		int margT = Integer.parseInt(margTemp.getText());
		int margL = Integer.parseInt(margLum.getText());
		int lim = Integer.parseInt(limiteMensagens.getText());
		SQL.getInstance().setSpecs(margT,margL,lim);
		SQL.getInstance().setTTR(Long.parseLong(ttr.getText()));
		Platform.exit();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ttr.setText(String.valueOf(SQL.getInstance().getTTR()));
	}
}
