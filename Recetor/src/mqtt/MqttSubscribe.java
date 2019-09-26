package mqtt;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import mongo.Mongo;
import util.Mensagem;
import util.MessagesController;

public class MqttSubscribe implements MqttCallback {

	private static MqttSubscribe instance;

	private MqttClient client;
	private static final String broker = "tcp://broker.mqtt-dashboard.com:1883";
//	private static final String broker = "tcp://iot.eclipse.org:1883";
	private static String clientID = "RECETORALEX";
	private static String topic = "/sid_lab_2019_2";

	int counter = 0;

	ArrayList<String> list = new ArrayList<String>();
	
	
	
	public MqttSubscribe() throws IOException {
		instance = this;
	}

	public void doDemo(String username, String password, String host) {
		try {
			new Mongo(username, password, host);
			System.out.println("MqttConstrutor iniciado");
			client = new MqttClient(broker, clientID);
			client.connect();
			client.setCallback(this);
//			client.subscribe("/sid_lab_2019_2");
			client.subscribe(topic);

		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void connectionLost(Throwable cause) {
		System.out.println("Connection Lost");
		System.exit(0);
		
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println("SENSOR: " + message.toString());

		
		list.add(message.toString());
		MessagesController.getInstance().show(message.toString());
	}

	public boolean isJSONValid(String test) {

		return true;
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		System.out.println("Delivery_Complete");
	}

	public static String getBroker() {
		return broker;
	}

	public ArrayList<String> getMessagesReceived() {
		return list;
	}

	public static MqttSubscribe getInstance() {
		return instance;
	}

}
