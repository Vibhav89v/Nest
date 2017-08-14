package ExcelDistributor;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.testng.annotations.Test;
import Mongo.ProductCollection.Product;
import common.SuperTestScript;
import generics.Excel;
import mongoclient.AppMongoClientImpl;

public class DifferencePerDayExcel extends SuperTestScript
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
	 
	 public DifferencePerDayExcel()
	 {
	  log = Logger.getLogger(this.getClass());
	  Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	 }
	 
	 @Test
	 public void differncePerDayEXCEL() throws InterruptedException, SQLException
	 {
		  log.info("--------------In PRODUCT Collection checking 'STATUS' of the Current day--------------------");
		  
		  count  = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("publisher.distributorname","EXCEL"));
		  countActive  = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","ACTIVE").append("publisher.distributorname","EXCEL" ));
		  countDeleted  = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","DELETED").append("publisher.distributorname","EXCEL" ));
		   countA_INACTIVE  = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","A_INACTIVE").append("publisher.distributorname","EXCEL" ));
		   countP_INACTIVE = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","P_INACTIVE").append("publisher.distributorname","EXCEL" ));
		   countA_OMITTED  = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","A_OMITTED").append("publisher.distributorname","EXCEL" ));
		   countPARKED  = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","PARKED").append("publisher.distributorname","EXCEL" ));
		   countL_INACTIVE = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","L_INACTIVE").append("publisher.distributorname","EXCEL" ));
		   countERROR  = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","ERROR").append("publisher.distributorname","EXCEL" ));
		   countP_DEFERRED  = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","P_DEFERRED").append("publisher.distributorname","EXCEL" ));
		   countUPCOMING = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","UPCOMING").append("publisher.distributorname","EXCEL" ));
		   countHIGH_PRICE = AppMongoClientImpl.getCollectionByDB("nextory", "product").count(new Document("productstatus","HIGH_PRICE").append("publisher.distributorname","EXCEL" ));
		
	    		 log.info("=========FINAL STATUS==========");
	    		 log.info("Total COUNT for 'EXCEL' : "+count);
	    		 log.info("publisher.distributorname : 'EXCEL'|| productstatus :'ACTIVE'    || count : " + countActive);
	    		 log.info("publisher.distributorname : 'EXCEL'|| productstatus :'DELETED'   || count : " + countDeleted);
	    		 log.info("publisher.distributorname : 'EXCEL'|| productstatus :'A_INACTIVE'|| count : " + countA_INACTIVE);
	    		 log.info("publisher.distributorname : 'EXCEL'|| productstatus :'P_INACTIVE'|| count : " + countP_INACTIVE);
	    		 log.info("publisher.distributorname : 'EXCEL'|| productstatus :'A_OMITTED' || count : " + countA_OMITTED);
	    		 log.info("publisher.distributorname : 'EXCEL'|| productstatus :'PARKED'    || count : " + countPARKED);
	    		 log.info("publisher.distributorname : 'EXCEL'|| productstatus :'L_INACTIVE'|| count : " + countL_INACTIVE);
	    		 log.info("publisher.distributorname : 'EXCEL'|| productstatus :'ERROR'     || count : " + countERROR);
	    		 log.info("publisher.distributorname : 'EXCEL'|| productstatus :'P_DEFERRED'|| count : " + countP_DEFERRED);
	    		 log.info("publisher.distributorname : 'EXCEL'|| productstatus :'UPCOMING'  || count : " + countUPCOMING);
	    		 log.info("publisher.distributorname : 'EXCEL'|| productstatus :'HIGH_PRICE'|| count : " + countHIGH_PRICE);
	    		 
	    		Excel.setExcelDataNest("./data/StatusCheck.xlsx","EXCEL", date, countActive, countDeleted, countA_INACTIVE, countP_INACTIVE, countA_OMITTED, countPARKED, countL_INACTIVE, countERROR, countP_DEFERRED, countUPCOMING, countHIGH_PRICE);
	    		double activeDiff = Excel.comparingCells("./data/StatusCheck.xlsx","EXCEL", 2);
	    		log.info("THE DIFFERENCE BETWEEN THE ACTIVE COUNTS : "+activeDiff);
	    		
	    		double a_InactiveDiff = Excel.comparingCells("./data/StatusCheck.xlsx","EXCEL", 3);
	    		log.info("THE DIFFERENCE BETWEEN THE A_INACTIVE COUNTS : "+a_InactiveDiff);
	    		
	    		double p_InactiveDiff = Excel.comparingCells("./data/StatusCheck.xlsx","EXCEL", 4);
	    		log.info("THE DIFFERENCE BETWEEN THE P_ACTIVE COUNTS : "+p_InactiveDiff);
	    		
	    		double deletedDiff = Excel.comparingCells("./data/StatusCheck.xlsx","EXCEL", 5);
	    		log.info("THE DIFFERENCE BETWEEN THE DELETED COUNTS : "+deletedDiff);
	    		
	    		double parkedDiff = Excel.comparingCells("./data/StatusCheck.xlsx","EXCEL", 6);
	    		log.info("THE DIFFERENCE BETWEEN THE PARKED COUNTS : "+parkedDiff);
	    		
	    		double upcomingDiff = Excel.comparingCells("./data/StatusCheck.xlsx","EXCEL", 7);
	    		log.info("THE DIFFERENCE BETWEEN THE UPCOMING COUNTS : "+upcomingDiff);
	    		
	    		double a_OmittedDiff = Excel.comparingCells("./data/StatusCheck.xlsx","EXCEL", 8);
	    		log.info("THE DIFFERENCE BETWEEN THE A_OMITTED COUNTS : "+a_OmittedDiff);
	    		
	    		double deleteDiff = Excel.comparingCells("./data/StatusCheck.xlsx","EXCEL", 9);
	    		log.info("THE DIFFERENCE BETWEEN THE DELETED COUNTS : "+deleteDiff);
	    		
	    		double l_InactiveDiff = Excel.comparingCells("./data/StatusCheck.xlsx","EXCEL", 10);
	    		log.info("THE DIFFERENCE BETWEEN THE L_INACTIVE COUNTS : "+l_InactiveDiff);
	    		
	    		double errorDiff = Excel.comparingCells("./data/StatusCheck.xlsx","EXCEL", 11);
	    		log.info("THE DIFFERENCE BETWEEN THE ERROR COUNTS : "+errorDiff);
	    		
	    		double p_DefferedDiff = Excel.comparingCells("./data/StatusCheck.xlsx","EXCEL", 12);
	    		log.info("THE DIFFERENCE BETWEEN THE P_DEFFERED COUNTS : "+p_DefferedDiff);
	    		
	    		double highPriceDiff = Excel.comparingCells("./data/StatusCheck.xlsx","EXCEL", 13);
	    		log.info("THE DIFFERENCE BETWEEN THE HIGHPRICE COUNTS : "+highPriceDiff);
		 }
}
