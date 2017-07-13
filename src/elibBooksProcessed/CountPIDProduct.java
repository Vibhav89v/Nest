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
import generics.AddDate;
import generics.MongoDBMorphia;


public class CountPIDProduct
{
	MongoDBMorphia mongoutil = new MongoDBMorphia();
	Datastore ds = mongoutil.getMorphiaDatastoreForNestVer2();
	Datastore ds1=mongoutil.getMorphiaDatastoreForProduct();
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  //yyyy-MM-dd'T'HH:mm:ss.SSS'Z'

	Elib elib=new Elib();
	 Product product=new Product();
	 String elibDate;
	 String productDate;
	 String eDate;
	 String pDate;
	 public Logger log;
	 public static WebDriver driver;
	 int count=0;
	 
	 public CountPIDProduct()
	 {
	  log = Logger.getLogger(this.getClass());
	  Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	 }
	 
	 @Test(enabled=true, priority=1, groups={"All"})
	 public void elibToProductTransformation() throws InterruptedException, SQLException
	 {
	  System.out.println("--------------In Elib to Product Transformation flow Meta--------------------");
	  
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
            
            DBCollection query = ds1.getDB().getCollection("product");            
            DBCursor	cursor = query.find(new BasicDBObject("provider_productid", result));
            
            while( cursor.hasNext() )
            {
            	DBObject mObj = cursor.next();
            	System.out.println("'PROVIDER_PRODUCT ID' OF PRODUCT COLLECTION : "+result);
            	System.out.println();
            	count++;
               
            }
        }
        System.out.println("Total number of Books Processed by NEST : "+count);
        driver.close();
     }
}

