package elibBooksProcessed;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
import common.SuperTestScript;
import generics.AddDate;
import generics.MongoDBMorphia;

public class DetailsOfActivePIDsInNest extends SuperTestScript implements AutomationConstants
{
	 MongoDBMorphia mongoutil = new MongoDBMorphia();
	 Datastore ds1=mongoutil.getMorphiaDatastoreForProduct();
	 Product product=new Product();
	 public Logger log;
	 public static WebDriver driver;
	  
	 public DetailsOfActivePIDsInNest()
	  {
	  log = Logger.getLogger(this.getClass());
	  Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	  }
	  
	  @Test
	  public void detailOfActiveStatusNest() throws InterruptedException, SQLException
	   {
		  	List<Integer> presentList = new ArrayList<Integer>();
			
			List<Integer> absentList = new ArrayList<Integer>();
			 
			List<Integer> activeList = new ArrayList<Integer>();
			 
			 
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
	          
	          if(prodCursor.count() == 0)
	          {
	        	  absentList.add(result);
	          }
	            
	          while( prodCursor.hasNext() )
	          {
	            DBObject mObj = prodCursor.next();
	            DBObject mObj1 = (DBObject) mObj.get("publisher");
	            product.setProvider_productid( (Integer) mObj.get("provider_productid"));
	           
	            if(((String) mObj.get("productstatus")).equalsIgnoreCase("ACTIVE"))
	           {
	        	   log.info("_id -> "+mObj.get("_id")+"|| provider_productid -> "+mObj.get("provider_productid")+"|| isbn -> "+mObj.get("isbn")+"|| publisher_publishername -> "+ mObj1.get("publishername")+" || iscontractavailable -> "+mObj1.get("iscontractavailable")+" || productstatus -> "+mObj.get("productstatus")+" || statusatpublisher -> "+mObj.get("statusatpublisher"));
	               log.info("");
	               activeList.add(result);
	           }
	            else if(!((String) mObj.get("productstatus")).equalsIgnoreCase("Active"))
	           {
	            	log.info("_id -> "+mObj.get("_id")+"|| provider_productid -> "+mObj.get("provider_productid")+"|| isbn -> "+mObj.get("isbn")+"|| publisher_publishername -> "+ mObj1.get("publishername")+" || iscontractavailable -> "+mObj1.get("iscontractavailable")+" || productstatus -> "+mObj.get("productstatus")+" || statusatpublisher -> "+mObj.get("statusatpublisher"));
	            	presentList.add(result);
	            	log.info("");
	           }
	          }
	           
	    }
	    log.info("PID present in NEST with product status as ACTIVE are " +activeList.size()+ " and as follows: " +activeList);
	    log.info("PID present in NEST with product status other than ACTIVE are " +presentList.size()+ " and as follows: " +presentList);
	    log.info("PID absent in NEST are " +absentList.size()+ " and as follows: " +absentList);
	    
	    driver.close();
	   }
}
