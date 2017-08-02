package elibBooksProcessed;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.joda.time.DateTime;
import org.mongodb.morphia.Datastore;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import Mongo.ElibDistributor.Elib;
import Mongo.ProductCollection.Product;
import Mongo.ProductCollection.Publisher;
import common.AutomationConstants;
import generics.AddDate;
import generics.MongoDBMorphia;


public class CountPIDProduct implements AutomationConstants
{
	MongoDBMorphia mongoutil = new MongoDBMorphia();
	Datastore ds = mongoutil.getMorphiaDatastoreForNestVer2();
	Datastore ds1=mongoutil.getMorphiaDatastoreForProduct();
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  //yyyy-MM-dd'T'HH:mm:ss.SSS'Z'

	 Product product=new Product();
	 public Logger log;
	 public static WebDriver driver;
	 int count=0;
	 
	 public CountPIDProduct()
	 {
	  log = Logger.getLogger(this.getClass());
	  Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	 }
	 
	 @Test(enabled=true, priority=1, groups={"All"})
	 public void gettingCountPIDsElib() throws InterruptedException, SQLException
	 {
	  log.info("--------------In 'PRODUCT' Collection Count the Provider Product ID's------------------");
	  
	  log.info("Fetching all the Product Id's from the url");
	  
	  System.setProperty(CHROME_KEY, DRIVER_PATH+CHROME_FILE);
	  
   	    driver=new ChromeDriver();
		String date=AddDate.addingDays(-1);
	    driver.get("https://xdapi.elib.se/v1.0/products?ServiceID=2238&From="+date+"T13:00&Checksum=c5b42fb8f285b45983bc5325eebd23021a75626bd7edcf4103dce80e71457a50d48dda42ea571dda91511f51854deb9d99a0841c9f36f82ab54f6d19dd5ec6a6");
        String products = driver.findElement(By.id("content")).getText();
	    
        products=products.replaceAll(",","");
        
        StringTokenizer t = new StringTokenizer(products);
        String Provider_productid ="";
      
        while(t.hasMoreTokens())
        {
        	Provider_productid = t.nextToken();
        	log.info("");
        	int result = Integer.parseInt(Provider_productid);
        	
        	DBCollection proQuery = ds1.getDB().getCollection("product");  
            DBCursor	proCursor = proQuery.find(new BasicDBObject("provider_productid", result));
            log.info("'PROVIDER PRODUCT ID' IN PRODUCT COLLECTION: "+result);
        	count++;
            
            while( proCursor.hasNext() )
            {
            	DBObject mObj = proCursor.next();
    			DBObject mObj2 = (DBObject) mObj.get("publisher");
            	product.setProvider_productid( (Integer) mObj.get("provider_productid") );
            	//log.info();
            	//log.info("'PRODUCT STATUS' in PRODUCT Collection : "+mObj.get("productstatus"));
            	if(((String) mObj.get("productstatus")).equalsIgnoreCase("Active"))
            	{
            		log.info("_id -> "+mObj.get("_id")+"|| provider_productid -> "+mObj.get("provider_productid")+"|| isbn -> "+mObj.get("isbn")+"|| publisher_publishername -> "+ mObj2.get("publishername")+" || iscontractavailable -> "+mObj2.get("iscontractavailable")+" || productstatus -> "+mObj.get("productstatus")+" || statusatpublisher -> "+mObj.get("statusatpublisher"));
            		log.info("");
            	}
            	else if(!((String) mObj.get("productstatus")).equalsIgnoreCase("Active"))
            	{
            		log.info("_id -> "+mObj.get("_id")+"|| provider_productid -> "+mObj.get("provider_productid")+"|| isbn -> "+mObj.get("isbn")+"|| publisher_publishername -> "+ mObj2.get("publishername")+" || iscontractavailable -> "+mObj2.get("iscontractavailable")+" || productstatus -> "+mObj.get("productstatus")+" || statusatpublisher -> "+mObj.get("statusatpublisher"));
            		log.info("");
            	}
            }
        }
        log.info("Total number of Books Processed by NEST : "+count);
        driver.close();
     }
}

