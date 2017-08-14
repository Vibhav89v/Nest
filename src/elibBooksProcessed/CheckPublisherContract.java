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

public class CheckPublisherContract extends SuperTestScript 
{
	 MongoDBMorphia mongoutil = new MongoDBMorphia();
	 Datastore ds1=mongoutil.getMorphiaDatastoreForProduct();
	 Product product=new Product();
	 public Logger log;
	 public static WebDriver driver;
	  
	 public CheckPublisherContract()
	  {
	  log = Logger.getLogger(this.getClass());
	  Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	  }
	  
	  @Test
	  public void checkPublisherContractNest() throws InterruptedException, SQLException
	   {
		  
		  List<Integer> aList = new ArrayList<Integer>();
			 int a;
			 
		log.info("--------Checking Publisher's Contract In NEST------------------");
	    System.setProperty(AutomationConstants.CHROME_KEY,AutomationConstants.DRIVER_PATH+AutomationConstants.CHROME_FILE);
	   
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
	           
	           if(((String) mObj1.get("iscontractavailable")).equalsIgnoreCase("true"))
	           {
	               log.info("'PUBLISHER STATUS' IN PRODUCT COLLECTION : "+mObj1.get("iscontractavailable"));
	           }
	           else
	           {
	        	   log.info("'PUBLISHER STATUS' IN PRODUCT COLLECTION : "+mObj1.get("iscontractavailable"));
	        	   a = (int) mObj.get("provider_productid");
	        	   aList.add(a);
	           }
	          }
	    }
	    log.info("PID for IsContactAvailable other than TRUE are "+aList.size()+" and as follows:");
	    log.info(aList);
	    driver.close();
	   }
}
