package elibBooksProcessed;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
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

public class DifferenceInPIDBeingFetchedPrevCurrentDay extends SuperTestScript implements AutomationConstants
{
	MongoDBMorphia mongoutil = new MongoDBMorphia();
	Datastore ds = mongoutil.getMorphiaDatastoreForNestVer2();
	Datastore ds1=mongoutil.getMorphiaDatastoreForProduct();
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  //yyyy-MM-dd'T'HH:mm:ss.SSS'Z'

	 Product product=new Product();
	 public Logger log;
	 public static WebDriver chromeDriver;
	 public static WebDriver mozillaDriver;
	 int count1=0;
	 int count2=0;
	 
	 public DifferenceInPIDBeingFetchedPrevCurrentDay()
	 {
	  log = Logger.getLogger(this.getClass());
	  Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	 }
	 
	 @Test
	 public void diffCalculationElib() throws InterruptedException, SQLException
	 {
	  log.info("--------------Difference in PID's being fetched-----------------");
	  
	  log.info("Fetching all the Product Id's from the url");
	  
	  System.setProperty(CHROME_KEY, DRIVER_PATH+CHROME_FILE);
	  System.setProperty(GECKO_KEY, DRIVER_PATH+GECKO_FILE );
	  
   	    chromeDriver=new ChromeDriver();
   	    mozillaDriver=new FirefoxDriver();
   	    chromeDriver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
   	    mozillaDriver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
		String date1=AddDate.addingDays(-1);
		String date2=AddDate.addingDays(-2);
		chromeDriver.get("https://xdapi.elib.se/v1.0/products?ServiceID=2238&From="+date1+"T13:00&Checksum=c5b42fb8f285b45983bc5325eebd23021a75626bd7edcf4103dce80e71457a50d48dda42ea571dda91511f51854deb9d99a0841c9f36f82ab54f6d19dd5ec6a6");
	    mozillaDriver.get("https://xdapi.elib.se/v1.0/products?ServiceID=2238&From="+date2+"T13:00&Checksum=c5b42fb8f285b45983bc5325eebd23021a75626bd7edcf4103dce80e71457a50d48dda42ea571dda91511f51854deb9d99a0841c9f36f82ab54f6d19dd5ec6a6");
        String products = chromeDriver.findElement(By.id("content")).getText();
        String products1 = mozillaDriver.findElement(By.id("content")).getText();
        
        products=products.replaceAll(",","");
        products1=products1.replaceAll(",","");
        
        StringTokenizer t = new StringTokenizer(products);
        StringTokenizer t1 = new StringTokenizer(products1);
        String ProductID ="";
        String ProductID1 ="";
      
        while(t.hasMoreTokens())
        {
        	ProductID = t.nextToken();
        	int result1 = Integer.parseInt(ProductID);
        	count1++;
        	
            DBCollection query1 = ds1.getDB().getCollection("product");            
            DBCursor	cursor1 = query1.find(new BasicDBObject("provider_productid", result1));
            
            while( cursor1.hasNext())
            {
            	DBObject mObj1 = cursor1.next();
            	log.info("'PROVIDER_PRODUCT ID' OF "+date1+" PRODUCT COLLECTION : "+result1);
            	log.info("");
            }
        }
        while(t1.hasMoreTokens())
        {
        	ProductID1 =t1.nextToken();
        	int result2 = Integer.parseInt(ProductID1);
        	count2++;
        	
            DBCollection query = ds1.getDB().getCollection("product");            
            DBCursor	cursor2 = query.find(new BasicDBObject("provider_productid", result2));
            
            while(cursor2.hasNext())
            {
            	DBObject mObj2 = cursor2.next();
            	log.info("'PROVIDER_PRODUCT ID' OF" +date2+" PRODUCT COLLECTION : "+result2);
            	log.info("");
            }
        }
        log.info("Total number of Books Processed by NEST on "+date1+" : "+count1);
        log.info("Total number of Books Processed by NEST on "+date2+": "+count2);
        log.info("Difference of the PID's being processed : "+(count2-count1));
        chromeDriver.close();
        mozillaDriver.close();
     }
}
