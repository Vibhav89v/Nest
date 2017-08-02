package elibBooksProcessed;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.mongodb.morphia.Datastore;
import org.testng.annotations.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sun.jersey.api.client.ClientResponse;

import Mongo.ProductCollection.Product;
import Mongo.PublitDistributor.Publit;
import generics.AddDate;
import generics.MongoDBMorphia;
import mongoclient.AppMongoClientImpl;
import restClientForPublit.AbstractRestClient;
import valueObject.Datum;
import valueObject.PublitVO;

public class NestStatusCurrentDayElib 
{
	MongoDBMorphia mongoutil = new MongoDBMorphia();
	Datastore ds1 = mongoutil.getMorphiaDatastoreForProduct();
	
	 Publit publit=new Publit();
	 Product product=new Product();
	 public Logger log;

	 public NestStatusCurrentDayElib()
	 {
	  log = Logger.getLogger(this.getClass());
	  Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	 }
	 
	 @Test(enabled=true, priority=3, groups={"All"})
	 public void statusCurrentDayNest() throws InterruptedException, SQLException
	 {
	  log.info("--------------In PRODUCT Collection checking 'STATUS' of the Current day--------------------");
	  
	  long count  = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("publisher.distributorname","ELIB"));
	  long count1  = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","ACTIVE").append("publisher.distributorname","ELIB" ));
	  long count2  = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","DELETED").append("publisher.distributorname","ELIB" ));
	  long count3  = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","A_INACTIVE").append("publisher.distributorname","ELIB" ));
	  long count4  = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","P_INACTIVE").append("publisher.distributorname","ELIB" ));
	  long count5  = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","A_OMITTED").append("publisher.distributorname","ELIB" ));
	  long count6  = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","PARKED").append("publisher.distributorname","ELIB" ));
	  long count7  = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","L_INACTIVE").append("publisher.distributorname","ELIB" ));
	  long count8  = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","ERROR").append("publisher.distributorname","ELIB" ));
	  long count9  = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","P_DEFERRED").append("publisher.distributorname","ELIB" ));
	  long count10 = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","UPCOMING").append("publisher.distributorname","ELIB" ));
	  long count11 = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","HIGH_PRICE").append("publisher.distributorname","ELIB" ));
	
    		 log.info("=========FINAL STATUS==========");
    		 log.info("Total COUNT for 'ELIB' : "+count);
    		 log.info("publisher.distributorname : 'ELIB'|| productstatus :'ACTIVE'    || count : " + count1);
    		 log.info("publisher.distributorname : 'ELIB'|| productstatus :'DELETED'   || count : " + count2);
    		 log.info("publisher.distributorname : 'ELIB'|| productstatus :'A_INACTIVE'|| count : " + count3);
    		 log.info("publisher.distributorname : 'ELIB'|| productstatus :'P_INACTIVE'|| count : " + count4);
    		 log.info("publisher.distributorname : 'ELIB'|| productstatus :'A_OMITTED' || count : " + count5);
    		 log.info("publisher.distributorname : 'ELIB'|| productstatus :'PARKED'    || count : " + count6);
    		 log.info("publisher.distributorname : 'ELIB'|| productstatus :'L_INACTIVE'|| count : " + count7);
    		 log.info("publisher.distributorname : 'ELIB'|| productstatus :'ERROR'     || count : " + count8);
    		 log.info("publisher.distributorname : 'ELIB'|| productstatus :'P_DEFERRED'|| count : " + count9);
    		 log.info("publisher.distributorname : 'ELIB'|| productstatus :'UPCOMING'  || count : " + count10);
    		 log.info("publisher.distributorname : 'ELIB'|| productstatus :'HIGH_PRICE'|| count : " + count11);
	 }
	
  }

