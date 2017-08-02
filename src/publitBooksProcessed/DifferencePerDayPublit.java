package publitBooksProcessed;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.mongodb.morphia.Datastore;
import org.testng.annotations.Test;

import com.sun.jersey.api.client.ClientResponse;

import Mongo.ProductCollection.Product;
import Mongo.PublitDistributor.Publit;
import common.AutomationConstants;
import generics.AddDate;
import generics.Excel;
import generics.MongoDBMorphia;
import mongoclient.AppMongoClientImpl;
import restClientForPublit.AbstractRestClient;
import valueObject.Datum;
import valueObject.PublitVO;

public class DifferencePerDayPublit implements AutomationConstants
{
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
	 
	 public DifferencePerDayPublit()
	 {
	  log = Logger.getLogger(this.getClass());
	  Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	 }
	 
	 @Test(enabled=true, priority=3, groups={"All"})
	 public void differncePerDayPublit() throws InterruptedException, SQLException
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
		
	    		 log.info("=========FINAL STATUS==========");
	    		 log.info("Total COUNT for 'PUBLIT' : "+count);
	    		 log.info("publisher.distributorname : 'PUBLIT'|| productstatus :'ACTIVE'    || count : " + countActive);
	    		 log.info("publisher.distributorname : 'PUBLIT'|| productstatus :'DELETED'   || count : " + countDeleted);
	    		 log.info("publisher.distributorname : 'PUBLIT'|| productstatus :'A_INACTIVE'|| count : " + countA_INACTIVE);
	    		 log.info("publisher.distributorname : 'PUBLIT'|| productstatus :'P_INACTIVE'|| count : " + countP_INACTIVE);
	    		 log.info("publisher.distributorname : 'PUBLIT'|| productstatus :'A_OMITTED' || count : " + countA_OMITTED);
	    		 log.info("publisher.distributorname : 'PUBLIT'|| productstatus :'PARKED'    || count : " + countPARKED);
	    		 log.info("publisher.distributorname : 'PUBLIT'|| productstatus :'L_INACTIVE'|| count : " + countL_INACTIVE);
	    		 log.info("publisher.distributorname : 'PUBLIT'|| productstatus :'ERROR'     || count : " + countERROR);
	    		 log.info("publisher.distributorname : 'PUBLIT'|| productstatus :'P_DEFERRED'|| count : " + countP_DEFERRED);
	    		 log.info("publisher.distributorname : 'PUBLIT'|| productstatus :'UPCOMING'  || count : " + countUPCOMING);
	    		 log.info("publisher.distributorname : 'PUBLIT'|| productstatus :'HIGH_PRICE'|| count : " + countHIGH_PRICE);
	    		 
	    		Excel.setExcelDataNest(INPUT_PATH,"PUBLIT", date, countActive, countDeleted, countA_INACTIVE, countP_INACTIVE, countA_OMITTED, countPARKED, countL_INACTIVE, countERROR, countP_DEFERRED, countUPCOMING, countHIGH_PRICE);
	    		double activeDiff = Excel.comparingCells(INPUT_PATH,"PUBLIT", 2);
	    		log.info("THE DIFFERENCE BETWEEN THE ACTIVE COUNTS : "+activeDiff);
	    		
	    		double a_InactiveDiff = Excel.comparingCells(INPUT_PATH,"PUBLIT", 3);
	    		log.info("THE DIFFERENCE BETWEEN THE A_INACTIVE COUNTS : "+a_InactiveDiff);
	    		
	    		double p_InactiveDiff = Excel.comparingCells(INPUT_PATH,"PUBLIT", 4);
	    		log.info("THE DIFFERENCE BETWEEN THE P_ACTIVE COUNTS : "+p_InactiveDiff);
	    		
	    		double deletedDiff = Excel.comparingCells(INPUT_PATH,"PUBLIT", 5);
	    		log.info("THE DIFFERENCE BETWEEN THE DELETED COUNTS : "+deletedDiff);
	    		
	    		double parkedDiff = Excel.comparingCells(INPUT_PATH,"PUBLIT", 6);
	    		log.info("THE DIFFERENCE BETWEEN THE PARKED COUNTS : "+parkedDiff);
	    		
	    		double upcomingDiff = Excel.comparingCells(INPUT_PATH,"PUBLIT", 7);
	    		log.info("THE DIFFERENCE BETWEEN THE UPCOMING COUNTS : "+upcomingDiff);
	    		
	    		double a_OmittedDiff = Excel.comparingCells(INPUT_PATH,"PUBLIT", 8);
	    		log.info("THE DIFFERENCE BETWEEN THE A_OMITTED COUNTS : "+a_OmittedDiff);
	    		
	    		double deleteDiff = Excel.comparingCells(INPUT_PATH,"PUBLIT", 9);
	    		log.info("THE DIFFERENCE BETWEEN THE DELETED COUNTS : "+deleteDiff);
	    		
	    		double l_InactiveDiff = Excel.comparingCells(INPUT_PATH,"PUBLIT", 10);
	    		log.info("THE DIFFERENCE BETWEEN THE L_INACTIVE COUNTS : "+l_InactiveDiff);
	    		
	    		double errorDiff = Excel.comparingCells(INPUT_PATH,"PUBLIT", 11);
	    		log.info("THE DIFFERENCE BETWEEN THE ERROR COUNTS : "+errorDiff);
	    		
	    		double p_DefferedDiff = Excel.comparingCells(INPUT_PATH,"PUBLIT", 12);
	    		log.info("THE DIFFERENCE BETWEEN THE P_DEFFERED COUNTS : "+p_DefferedDiff);
	    		
	    		double highPriceDiff = Excel.comparingCells(INPUT_PATH,"PUBLIT", 13);
	    		log.info("THE DIFFERENCE BETWEEN THE HIGHPRICE COUNTS : "+highPriceDiff);
		 }
}
