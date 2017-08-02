package elibBooksProcessed;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.testng.annotations.Test;

import Mongo.ProductCollection.Product;
import common.AutomationConstants;
import generics.Excel;
import mongoclient.AppMongoClientImpl;

public class DifferencePerDayElib implements AutomationConstants
{
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
	 
	 public DifferencePerDayElib()
	 {
	  log = Logger.getLogger(this.getClass());
	  Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	 }
	 
	 @Test(enabled=true, priority=3, groups={"All"})
	 public void differncePerDayELIB() throws InterruptedException, SQLException
	 {
		  log.info("--------------In PRODUCT Collection checking 'STATUS' of the Current day--------------------");
		  
		  count  = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("publisher.distributorname","ELIB"));
		  countActive  = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","ACTIVE").append("publisher.distributorname","ELIB" ));
		  countDeleted  = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","DELETED").append("publisher.distributorname","ELIB" ));
		   countA_INACTIVE  = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","A_INACTIVE").append("publisher.distributorname","ELIB" ));
		   countP_INACTIVE = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","P_INACTIVE").append("publisher.distributorname","ELIB" ));
		   countA_OMITTED  = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","A_OMITTED").append("publisher.distributorname","ELIB" ));
		   countPARKED  = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","PARKED").append("publisher.distributorname","ELIB" ));
		   countL_INACTIVE = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","L_INACTIVE").append("publisher.distributorname","ELIB" ));
		   countERROR  = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","ERROR").append("publisher.distributorname","ELIB" ));
		   countP_DEFERRED  = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","P_DEFERRED").append("publisher.distributorname","ELIB" ));
		   countUPCOMING = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","UPCOMING").append("publisher.distributorname","ELIB" ));
		   countHIGH_PRICE = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","HIGH_PRICE").append("publisher.distributorname","ELIB" ));
		
	    		 log.info("=========FINAL STATUS==========");
	    		 log.info("Total COUNT for 'ELIB' : "+count);
	    		 log.info("publisher.distributorname : 'ELIB'|| productstatus :'ACTIVE'    || count : " + countActive);
	    		 log.info("publisher.distributorname : 'ELIB'|| productstatus :'DELETED'   || count : " + countDeleted);
	    		 log.info("publisher.distributorname : 'ELIB'|| productstatus :'A_INACTIVE'|| count : " + countA_INACTIVE);
	    		 log.info("publisher.distributorname : 'ELIB'|| productstatus :'P_INACTIVE'|| count : " + countP_INACTIVE);
	    		 log.info("publisher.distributorname : 'ELIB'|| productstatus :'A_OMITTED' || count : " + countA_OMITTED);
	    		 log.info("publisher.distributorname : 'ELIB'|| productstatus :'PARKED'    || count : " + countPARKED);
	    		 log.info("publisher.distributorname : 'ELIB'|| productstatus :'L_INACTIVE'|| count : " + countL_INACTIVE);
	    		 log.info("publisher.distributorname : 'ELIB'|| productstatus :'ERROR'     || count : " + countERROR);
	    		 log.info("publisher.distributorname : 'ELIB'|| productstatus :'P_DEFERRED'|| count : " + countP_DEFERRED);
	    		 log.info("publisher.distributorname : 'ELIB'|| productstatus :'UPCOMING'  || count : " + countUPCOMING);
	    		 log.info("publisher.distributorname : 'ELIB'|| productstatus :'HIGH_PRICE'|| count : " + countHIGH_PRICE);
	    		 
	    		Excel.setExcelDataNest(INPUT_PATH,"ELIB", date, countActive, countDeleted, countA_INACTIVE, countP_INACTIVE, countA_OMITTED, countPARKED, countL_INACTIVE, countERROR, countP_DEFERRED, countUPCOMING, countHIGH_PRICE);
	    		double activeDiff = Excel.comparingCells(INPUT_PATH,"ELIB", 2);
	    		log.info("THE DIFFERENCE BETWEEN THE ACTIVE COUNTS : "+activeDiff);
	    		
	    		double a_InactiveDiff = Excel.comparingCells(INPUT_PATH,"ELIB", 3);
	    		log.info("THE DIFFERENCE BETWEEN THE A_INACTIVE COUNTS : "+a_InactiveDiff);
	    		
	    		double p_InactiveDiff = Excel.comparingCells(INPUT_PATH,"ELIB", 4);
	    		log.info("THE DIFFERENCE BETWEEN THE P_ACTIVE COUNTS : "+p_InactiveDiff);
	    		
	    		double deletedDiff = Excel.comparingCells(INPUT_PATH,"ELIB", 5);
	    		log.info("THE DIFFERENCE BETWEEN THE DELETED COUNTS : "+deletedDiff);
	    		
	    		double parkedDiff = Excel.comparingCells(INPUT_PATH,"ELIB", 6);
	    		log.info("THE DIFFERENCE BETWEEN THE PARKED COUNTS : "+parkedDiff);
	    		
	    		double upcomingDiff = Excel.comparingCells(INPUT_PATH,"ELIB", 7);
	    		log.info("THE DIFFERENCE BETWEEN THE UPCOMING COUNTS : "+upcomingDiff);
	    		
	    		double a_OmittedDiff = Excel.comparingCells(INPUT_PATH,"ELIB", 8);
	    		log.info("THE DIFFERENCE BETWEEN THE A_OMITTED COUNTS : "+a_OmittedDiff);
	    		
	    		double deleteDiff = Excel.comparingCells(INPUT_PATH,"ELIB", 9);
	    		log.info("THE DIFFERENCE BETWEEN THE DELETED COUNTS : "+deleteDiff);
	    		
	    		double l_InactiveDiff = Excel.comparingCells(INPUT_PATH,"ELIB", 10);
	    		log.info("THE DIFFERENCE BETWEEN THE L_INACTIVE COUNTS : "+l_InactiveDiff);
	    		
	    		double errorDiff = Excel.comparingCells(INPUT_PATH,"ELIB", 11);
	    		log.info("THE DIFFERENCE BETWEEN THE ERROR COUNTS : "+errorDiff);
	    		
	    		double p_DefferedDiff = Excel.comparingCells(INPUT_PATH,"ELIB", 12);
	    		log.info("THE DIFFERENCE BETWEEN THE P_DEFFERED COUNTS : "+p_DefferedDiff);
	    		
	    		double highPriceDiff = Excel.comparingCells(INPUT_PATH,"ELIB", 13);
	    		log.info("THE DIFFERENCE BETWEEN THE HIGHPRICE COUNTS : "+highPriceDiff);
		 }
}
