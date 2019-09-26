import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttPublish {

	private static MqttPublish instance;

	private String topic = "topicoze";
	private int qos = 0;
	private String broker = "tcp://iot.eclipse.org:1883";
	private String clientId = "Demo28";
	private MemoryPersistence persistence = new MemoryPersistence();
	private MqttClient sampleClient;

	public MqttPublish() {
		connect();
	}

	public void connect() {
		try {
			sampleClient = new MqttClient(broker, clientId, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			System.out.println("Connecting to broker: " + broker);
			sampleClient.connect(connOpts);
			System.out.println("Connected");


		} catch (MqttException me) {
			System.out.println("reason " + me.getReasonCode());
			System.out.println("msg " + me.getMessage());
			System.out.println("loc " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("excep " + me);
			me.printStackTrace();
		}
	}

	public void disconnect() {
		try {
			sampleClient.disconnect();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Disconnected");
		System.exit(0);
	}
	
	public void publish(String content) {
		try {
			System.out.println("Publishing message: " + content);
			MqttMessage message = new MqttMessage(content.getBytes());
			message.setQos(qos);
			sampleClient.publish(topic, message);
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static MqttPublish getInstance() {
		if (instance == null) {
			instance = new MqttPublish();
		}
		return instance;
	}
}