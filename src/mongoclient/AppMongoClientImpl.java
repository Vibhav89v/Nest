package mongoclient;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class AppMongoClientImpl
{
	private static int PORT = 27017 ;
	private static MongoClient mongoClient;
	
	private AppMongoClientImpl()
	{ }
	
	public static MongoClient getInstance()
	{
		return mongoClient = new MongoClient("localhost");
	}
	
	public static MongoClient getInstanceByDefaultPort()
	{
		return mongoClient = new MongoClient("localhost",PORT);
	}
	
	public static MongoClient getInstanceByPort(Integer port)
	{
		if(port == null)
		{
			mongoClient = new MongoClient( "localhost" , PORT );
		}
		else
			mongoClient = new MongoClient( "localhost" , port );
		return mongoClient;
	}
	
	public static MongoDatabase getDBbyName(String dbName)
	{
		 MongoDatabase database = mongoClient.getDatabase("mydb" );
		 return database;
	}
	
	public static MongoCollection getCollectionByDB(String dbName, String collectionName)
	{
		if(mongoClient == null)
		{
			getInstanceByDefaultPort();
		}
		MongoDatabase database  = mongoClient.getDatabase(dbName);
		MongoCollection<Document> collection = database.getCollection(collectionName);
		return collection;
	}
	
	public static void main(String[] args) 
	{
		AppMongoClientImpl.getCollectionByDB("nextory", "product").find();
		long count  = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","ACTIVE").append("publisher.distributorname","PUBLIT" ));
		System.out.println(count);
		//AppMongoClientImpl.getCollectionByDB("nest_ver2", "elib_webshop_meta").find();
		long count1 = AppMongoClientImpl.getCollectionByDB("nest_ver2","elib_webshop_meta").count();
		System.out.println(count1);
	}
}
