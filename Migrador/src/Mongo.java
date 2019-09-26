import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.collections4.functors.AndPredicate;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;

public class Mongo {

	private String database1Name = "SID2";
	// private String database2Name = "grupo23";
	private String collectionName = "Sensor";

	MongoClient mongoClient1 = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
	MongoDatabase database = mongoClient1.getDatabase(database1Name);
	MongoCollection<Document> collection = database.getCollection(collectionName);

	private ArrayList<Mensagem> ParaEnviar = new ArrayList<Mensagem>();

	public ArrayList<Mensagem> getParaEnviar() {
		return ParaEnviar;
	}

	public Mongo() {

	}

	public void readCollection(MongoCollection<Document> collection) {
		System.out.println("listCollection() called");
		FindIterable<Document> iterable = collection.find();

		iterable.forEach(new Block<Document>() {

			@Override
			public void apply(Document t) {
				System.out.println(t);
			}
		});
	}

	public void readDB() {

		Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
		mongoLogger.setLevel(Level.SEVERE);
		mongoClient1 = new MongoClient("localhost", 27017);

		try {
			System.out.println("1");

			MongoDatabase db = mongoClient1.getDatabase(database1Name);
			System.out.println(db.getName() + " leleel");
			for (String name : db.listCollectionNames()) {
				System.out.println(name + "  boraaaaaa");
			}
		} catch (Exception e) {
		}
	}

	public void retrieveData() {
		List<Document> documents = (List<Document>) collection.find().into(new ArrayList<Document>());
		for (Document doc : documents) {
			try {
				Mensagem m= new Mensagem(new String(doc.getObjectId("_id").toString()),Double.parseDouble(doc.getString("tmp")),
						Double.parseDouble(doc.getString("hum")), Double.parseDouble(doc.getString("cell")),
						doc.getString("dat"),doc.getString("tim"), doc.getString("sens"));
				
				ParaEnviar.add(m);
			} catch (NumberFormatException e) {
				System.err.println("MENSAGEM INVALIDA");
			}
			catch(NullPointerException e) {
				System.err.println("MENSAGEM INVALIDA----NULL");
			}

		}

	}



	public void eliminar(Mensagem msg) {
		Bson filter = com.mongodb.client.model.Filters.and(com.mongodb.client.model.Filters.eq("dat", msg.getDat()),com.mongodb.client.model.Filters.eq("tim", msg.getTim()));
		DeleteResult result = collection.deleteOne(filter);
	}

}
