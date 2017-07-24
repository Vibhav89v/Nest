package publitBooksProcessed;

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
import generics.Excel;
import generics.MongoDBMorphia;
import generics.Sample;
import mongoclient.AppMongoClientImpl;
import restClientForPublit.AbstractRestClient;
import vo.Datum;
import vo.PublitVO;

public class NestStatusCurrentDayPublit 
{
	MongoDBMorphia mongoutil = new MongoDBMorphia();
	Datastore ds1 = mongoutil.getMorphiaDatastoreForProduct();
	
	 Publit publit=new Publit();
	 Product product=new Product();
	 public Logger log;
	 public static String date;
	 public static long count;
	 public static long countActive;
	 public static long countDeleted;
	 public static long countA_INACTIVE;
	 public static long countP_INACTIVE;
	 public static long countA_OMITTED;
	 public static long countPARKED;
	 public static long countL_INACTIVE;
	 public static long countERROR;
	 public static long countP_DEFERRED;
	 public static long countUPCOMING;
	 public static long countHIGH_PRICE;
	 public static String path;
	 
	 
	 

	 public NestStatusCurrentDayPublit()
	 {
	  log = Logger.getLogger(this.getClass());
	  Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	 }
	 
	 @Test(enabled=true, priority=3, groups={"All"})
	 public void statusCurrentDayNest() throws InterruptedException, SQLException
	 {
	  log.info("--------------In PRODUCT Collection checking 'STATUS' of the Current day--------------------");
	  
	  count  = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("publisher.distributorname","PUBLIT"));
	  countActive  = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","ACTIVE").append("publisher.distributorname","PUBLIT" ));
	  countDeleted  = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","DELETED").append("publisher.distributorname","PUBLIT" ));
	   countA_INACTIVE  = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","A_INACTIVE").append("publisher.distributorname","PUBLIT" ));
	   countP_INACTIVE = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","P_INACTIVE").append("publisher.distributorname","PUBLIT" ));
	   countA_OMITTED  = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","A_OMITTED").append("publisher.distributorname","PUBLIT" ));
	   countPARKED  = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","PARKED").append("publisher.distributorname","PUBLIT" ));
	   countL_INACTIVE = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","L_INACTIVE").append("publisher.distributorname","PUBLIT" ));
	   countERROR  = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","ERROR").append("publisher.distributorname","PUBLIT" ));
	   countP_DEFERRED  = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","P_DEFERRED").append("publisher.distributorname","PUBLIT" ));
	   countUPCOMING = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","UPCOMING").append("publisher.distributorname","PUBLIT" ));
	   countHIGH_PRICE = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","HIGH_PRICE").append("publisher.distributorname","PUBLIT" ));
	
    		 System.out.println("=========FINAL STATUS==========");
    		 System.out.println("Total COUNT for 'PUBLIT' : "+count);
    		 System.out.println("publisher.distributorname : 'PUBLIT'|| productstatus :'ACTIVE'    || count : " + countActive);
    		 System.out.println("publisher.distributorname : 'PUBLIT'|| productstatus :'DELETED'   || count : " + countDeleted);
    		 System.out.println("publisher.distributorname : 'PUBLIT'|| productstatus :'A_INACTIVE'|| count : " + countA_INACTIVE);
    		 System.out.println("publisher.distributorname : 'PUBLIT'|| productstatus :'P_INACTIVE'|| count : " + countP_INACTIVE);
    		 System.out.println("publisher.distributorname : 'PUBLIT'|| productstatus :'A_OMITTED' || count : " + countA_OMITTED);
    		 System.out.println("publisher.distributorname : 'PUBLIT'|| productstatus :'PARKED'    || count : " + countPARKED);
    		 System.out.println("publisher.distributorname : 'PUBLIT'|| productstatus :'L_INACTIVE'|| count : " + countL_INACTIVE);
    		 System.out.println("publisher.distributorname : 'PUBLIT'|| productstatus :'ERROR'     || count : " + countERROR);
    		 System.out.println("publisher.distributorname : 'PUBLIT'|| productstatus :'P_DEFERRED'|| count : " + countP_DEFERRED);
    		 System.out.println("publisher.distributorname : 'PUBLIT'|| productstatus :'UPCOMING'  || count : " + countUPCOMING);
    		 System.out.println("publisher.distributorname : 'PUBLIT'|| productstatus :'HIGH_PRICE'|| count : " + countHIGH_PRICE);
    		 
    		 Excel.setExcelDataNest("./data/StatusCheck.xlsx","PUBLIT", date, countActive, countDeleted, countA_INACTIVE, countP_INACTIVE, countA_OMITTED, countPARKED, countL_INACTIVE, countERROR, countP_DEFERRED, countUPCOMING, countHIGH_PRICE);
    		 
    		
	 }
	
  }

