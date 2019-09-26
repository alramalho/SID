
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class SensorController implements Initializable {

	@FXML
	private CheckBox keepSending;

	@FXML
	private TextField incTemp;
	@FXML
	private TextField incLum;

	@FXML
	private TextField Temp;
	@FXML
	private TextField Lum;
	@FXML
	private TextField resendTime;

	@FXML
	private Button send;

	@FXML
	private ResourceBundle resources;

	public SensorController() {
	}

	@FXML
	private void initialize() {

	}

	@FXML
	private void send(ActionEvent event) {
		Thread t1 = new Thread() {
			@Override
			public void run() {
				send.setDisable(true);
				while (keepSending.isSelected()) {
					try {
						sleep(1000 * Integer.parseInt(resendTime.getText()));
						sendAction(updateTemp(), updateLum());
					} catch (NumberFormatException | InterruptedException e) {
						e.printStackTrace();
					}
				}
				send.setDisable(false);
				sendAction(updateTemp(), updateLum());
			}
		};
		t1.start();

	}

	protected double updateLum() {
		double l = Double.parseDouble(Lum.getText());
		l = Double.parseDouble(Lum.getText()) + Double.parseDouble(incLum.getText());
		Lum.setText(String.valueOf(l));
		return l;
	}

	protected double updateTemp() {
		double t = Double.parseDouble(Temp.getText());
		t = Double.parseDouble(Temp.getText()) + Double.parseDouble(incTemp.getText());
		Temp.setText(String.valueOf(t));
		return t;
	}

	protected void sendAction(double Temp, double Lum) {

		String data = getCurrentData();
		String time = getCurrentTime();
		MqttPublish.getInstance().publish("{\"tmp\":\"" + Temp + "\",\"hum\":\"65.50\",\"dat\":\"" + data
				+ "\",\"tim\":\"" + time + "\",\"cell\":\"" + Lum + "\"\"sens\":\"wifi\"}\r\n" + "");

	}

	public String getCurrentTime() {
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		String formattedDate = dateFormat.format(date);
		return formattedDate;
	}

	public String getCurrentData() {
		String pattern = "dd-MM-yyyy";
		String data = new SimpleDateFormat(pattern).format(new Date());
		return data;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}
}
