package elibBooksProcessed;

import java.sql.SQLException;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import Mongo.ProductCollection.Product;
import common.AutomationConstants;
import generics.AddDate;
import generics.MongoDBMorphia;

public class ProcessingInformationElib implements AutomationConstants
{
	MongoDBMorphia mongoutil = new MongoDBMorphia();
	 Datastore ds1=mongoutil.getMorphiaDatastoreForProduct();
	 Product product=new Product();
	 public Logger log;
	 public static WebDriver driver;
	 
	 int countActive=0;
	 int countParked=0;
	 int countUpcoming=0;
	 int countA_Inactive=0;
	 int countA_Omitted=0;
	 int countL_Inactive=0;
	 int countP_Inactive=0;
	 int countP_Deferred=0;
	 int countHighPrice=0;
	 int countError=0;
	  
	 public ProcessingInformationElib()
	  {
	  log = Logger.getLogger(this.getClass());
	  Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	  }
	  
	  @Test(enabled=true, priority=1, groups={"All"})
	  public void detailOfActiveStatusAndNetPriceZeroInNest() throws InterruptedException, SQLException
	   {
		log.info("--------Details of Active PID's NEST------------------");
		System.setProperty(CHROME_KEY, DRIVER_PATH+CHROME_FILE);
	   
	    driver=new ChromeDriver();
	  
	    String date=AddDate.addingDays(-1);
	    driver.get("https://xdapi.elib.se/v1.0/products?ServiceID=2238&From="+date+"T13:00&Checksum=c5b42fb8f285b45983bc5325eebd23021a75626bd7edcf4103dce80e71457a50d48dda42ea571dda91511f51854deb9d99a0841c9f36f82ab54f6d19dd5ec6a6");
	    String products = driver.findElement(By.id("content")).getText();
	     
	    products=products.replaceAll(",","");
	        
	    StringTokenizer t = new StringTokenizer(products);
	    String ProductID ="";
	    while(t.hasMoreTokens())
	    {
	    	  ProductID = t.nextToken();
	          int result = Integer.parseInt(ProductID);
	         
	          DBCollection prodQuery = ds1.getDB().getCollection("product");            
	          DBCursor prodCursor = prodQuery.find(new BasicDBObject("provider_productid", result));
	            
	          while( prodCursor.hasNext() )
	          {
	           DBObject mObj = prodCursor.next();
	           product.setProvider_productid( (Integer) mObj.get("provider_productid"));
	           
	           DBObject mObj1 = (DBObject) mObj.get("publisher");
	           if(((String) mObj.get("productstatus")).equalsIgnoreCase("Active") && mObj1.get("distributorname") !=null)
	             {
	        	   log.info("'PRODUCT STATUS' IN PRODUCT COLLECTION IS ==> "+mObj.get("productstatus"));
	        	   log.info("'DISTRIBUTORNAME' under 'Publisher' in PRODUCT COLLECTION ==> "+mObj1.get("distributorname"));
	        	   log.info("Count with status 'ACTIVE' : "+ ++countActive);
	        	   log.info("");
	             }
	             else if(((String) mObj.get("productstatus")).equalsIgnoreCase("Parked") && mObj1.get("distributorname") !=null)
	             {
	            	 log.info("'PRODUCT STATUS' IN PRODUCT COLLECTION IS ==> "+mObj.get("productstatus"));
		        	 log.info("'DISTRIBUTORNAME' under 'Publisher' in PRODUCT COLLECTION ==> "+mObj1.get("distributorname"));
		        	 log.info("Count with status 'PARKED' : "+ ++countParked);
		        	 log.info("");
	             } 
	             else if(((String) mObj.get("productstatus")).equalsIgnoreCase("Upcoming") && mObj1.get("distributorname") !=null)
	             {
	            	 log.info("'PRODUCT STATUS' IN PRODUCT COLLECTION IS ==> "+mObj.get("productstatus"));
		        	 log.info("'DISTRIBUTORNAME' under 'Publisher' in PRODUCT COLLECTION ==> "+mObj1.get("distributorname"));
		        	 log.info("Count with status 'UPCOMING' : "+ ++countUpcoming);
		        	 log.info("");
	             }
	             else if(((String) mObj.get("productstatus")).equalsIgnoreCase("A_Inactive") && mObj1.get("distributorname") !=null)
	             {
	            	 log.info("'PRODUCT STATUS' IN PRODUCT COLLECTION IS ==> "+mObj.get("productstatus"));
		        	 log.info("'DISTRIBUTORNAME' under 'Publisher' in PRODUCT COLLECTION ==> "+mObj1.get("distributorname"));
		        	 log.info("Count with status 'A_Inactive' : "+ ++countA_Inactive);
		        	 log.info("");
	             }
	             else if(((String) mObj.get("productstatus")).equalsIgnoreCase("A_Omitted") && mObj1.get("distributorname") !=null)
	             {
	            	 log.info("'PRODUCT STATUS' IN PRODUCT COLLECTION IS ==> "+mObj.get("productstatus"));
		        	 log.info("'DISTRIBUTORNAME' under 'Publisher' in PRODUCT COLLECTION ==> "+mObj1.get("distributorname"));
		        	 log.info("Count with status 'A_Omitted' : "+ ++countA_Omitted);
		        	 log.info("");
	             }
	             else if(((String) mObj.get("productstatus")).equalsIgnoreCase("L_Inactive") && mObj1.get("distributorname") !=null)
	             {
	            	 log.info("'PRODUCT STATUS' IN PRODUCT COLLECTION IS ==> "+mObj.get("productstatus"));
		        	 log.info("'DISTRIBUTORNAME' under 'Publisher' in PRODUCT COLLECTION ==> "+mObj1.get("distributorname"));
		        	 log.info("Count with status 'L_Inactive' : "+ ++countL_Inactive);
		        	 log.info("");
	             }
	             else if(((String) mObj.get("productstatus")).equalsIgnoreCase("P_Inactive") && mObj1.get("distributorname") !=null)
	             {
	            	 log.info("'PRODUCT STATUS' IN PRODUCT COLLECTION IS ==> "+mObj.get("productstatus"));
		        	 log.info("'DISTRIBUTORNAME' under 'Publisher' in PRODUCT COLLECTION ==> "+mObj1.get("distributorname"));
		        	 log.info("Count with status 'P_Inactive' : "+ ++countP_Inactive);
		        	 log.info("");
	             }
	             else if(((String) mObj.get("productstatus")).equalsIgnoreCase("P_Deferred") && mObj1.get("distributorname") !=null)
	             {
	            	 log.info("'PRODUCT STATUS' IN PRODUCT COLLECTION IS ==> "+mObj.get("productstatus"));
		        	 log.info("'DISTRIBUTORNAME' under 'Publisher' in PRODUCT COLLECTION ==> "+mObj1.get("distributorname"));
		        	 log.info("Count with status 'P_Deferred' : "+ ++countP_Deferred);
		        	 log.info("");
	             }
	             else if(((String) mObj.get("productstatus")).equalsIgnoreCase("HighPrice") && mObj1.get("distributorname") !=null)
	             {
	            	 log.info("'PRODUCT STATUS' IN PRODUCT COLLECTION IS ==> "+mObj.get("productstatus"));
		        	 log.info("'DISTRIBUTORNAME' under 'Publisher' in PRODUCT COLLECTION ==> "+mObj1.get("distributorname"));
		        	 log.info("Count with status 'HighPrice' : "+ ++countHighPrice);
		        	 log.info("");
	             }
	             else if(((String) mObj.get("productstatus")).equalsIgnoreCase("Error") && mObj1.get("distributorname") !=null)
	             {
	            	 log.info("'PRODUCT STATUS' IN PRODUCT COLLECTION IS ==> "+mObj.get("productstatus"));
		        	 log.info("'DISTRIBUTORNAME' under 'Publisher' in PRODUCT COLLECTION ==> "+mObj1.get("distributorname"));
		        	 log.info("Count with status 'Error' : "+ ++countError);
		        	 log.info("");
	             }
	             else
	            	 log.info("Invalid Status");
               }
          }
	    log.info("=========FINAL STATUS==========");
	    log.info("'ACTIVE : '"+countActive);
	    log.info("'PARKED' : "+countParked);
	    log.info("'UPCOMING' : "+countUpcoming);
	    log.info("'A_INACTIVE' : "+countA_Inactive);
	    log.info("'P_INACTIVE' : " +countP_Inactive);      
	    log.info("'A_OMITTED' : "+countA_Omitted);
	    log.info("'L_INACTIVE' : "+countL_Inactive);
	    log.info("'P_DEFERRED' : "+countP_Deferred);
	    log.info("'HIGH PRICE' : "+countHighPrice);
	    log.info("'ERROR' : "+countError);
	    driver.close();
	   }
}