package elibBooksProcessed;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import generics.AddDate;
import generics.MongoDBMorphia;

public class StatusAndFormatCheckElib 
{
	MongoDBMorphia mongoutil = new MongoDBMorphia();
	Datastore ds1=mongoutil.getMorphiaDatastoreForProduct();
	Datastore ds = mongoutil.getMorphiaDatastoreForNestVer2();
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  //yyyy-MM-dd'T'HH:mm:ss.SSS'Z'

	 Product product=new Product();
	 public Logger log;
	 public static WebDriver driver;
	 
	 public StatusAndFormatCheckElib()
	 {
	  log = Logger.getLogger(this.getClass());
	  Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	 }
	 
	 @Test(enabled=true, priority=1, groups={"All"})
	 public void statusFormatCheckElib() throws InterruptedException, SQLException
	 {
	  System.out.println("--------------Checking book's 'Status' and 'Format'--------------------");
	  
	  System.out.println("Fetching all the Product Id's from the url");
	  
	  System.setProperty("webdriver.chrome.driver", "./Drivers/chromedriver.exe");
	  
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
	          
	          driver.get("http://130.211.74.42:8083/nestver2/monitor/elib/api/product/present/"+result);
	            
	          while( prodCursor.hasNext() )
	          {
	           DBObject mObj = prodCursor.next();
	           product.setProvider_productid( (Integer) mObj.get("provider_productid"));
	           
	           
	           DBObject mObj1 = (DBObject) mObj.get("formats");
	           
	           if(((String) mObj.get("productstatus")).equalsIgnoreCase("Active") || mObj.get("formats") != null)
	           {
	        	   System.out.println("'PRODUCT STATUS' IN PRODUCT COLLECTION IS 'ACTIVE' and 'FORMAT' IS PRESENT ");
	        	   System.out.println("'FORMAT' IN PRODUCT COLLECTION :"+mObj.get("formats"));
	        	   System.out.println();
	           }
	           else
	           {
	        	   System.out.println("'PRODUCT STATUS' IN PRODUCT COLLECTION IS 'IN-ACTIVE' and 'FORMAT' IS 'NULL'");
	        	   System.out.println();
	           }
	          }
	    }
	    driver.close();
	        
	 }
}
