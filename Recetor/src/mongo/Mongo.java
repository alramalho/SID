package mongo;

import java.util.Arrays;

import org.bson.Document;
import org.bson.json.JsonParseException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCommandException;
import com.mongodb.MongoCredential;
import com.mongodb.MongoSecurityException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import util.Mensagem;
import util.MessagesController;

public class Mongo {

	private static Mongo instance = null;

	private String user;
	private char[] password;
	private String host_name;
	private String databaseString = "SID2";
	private String collectionString = "Sensor";

	private static final String PORT = "27017";

	private MongoClient mongoClient;
	private MongoDatabase database;
	private MongoCollection<Document> collection;

	public Mongo(String user, String password, String host) {
		instance = this;
		this.user = user;
		this.password = password.toCharArray();
		this.host_name = host;
		this.mongoClient = new MongoClient(new MongoClientURI(
				"mongodb://" + user + ":" + password.toString() + "@" + host_name + "/?authSource=" + databaseString));
		this.database = mongoClient.getDatabase(databaseString);
		this.collection = database.getCollection(collectionString);
	}

	public void write(String message) {
		try {
			String temp = new Mensagem(message.toString()).getDados();

			Document doc = Document.parse(temp);
			collection.insertOne(doc);
			MessagesController.getInstance().write(temp.toString());
			System.out.println("Message written to database");
		} catch (JsonParseException e) {
			MessagesController.getInstance().discard(message.toString());
			System.err.println("Mensagem a ser descartada : " + message);
		}
	}

	public static Mongo getInstance() {
		return instance;
	}

}
