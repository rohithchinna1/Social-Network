package datamodels;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class Model {
	final String HOST = "localhost";
	final int PORT = 27017;
	final String DBNAME = "smallworld";
	public static Model instance;
	public MongoClient connection;
	public DB database;

	private Model() throws UnknownHostException {
		this.connection = new MongoClient(this.HOST, this.PORT);
		this.database = this.connection.getDB(this.DBNAME);
	}

	public static Model createInstance() {
		if (Model.instance == null) {
			try {
				Model.instance = new Model();
			} catch (UnknownHostException e) {
			}
		}
		return Model.instance;
	}

	public DBCollection getCollection(String name) {
		return this.database.getCollection(name);
	}
}
