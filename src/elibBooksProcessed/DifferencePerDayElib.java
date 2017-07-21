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
import generics.AddDate;
import generics.MongoDBMorphia;

public class DifferencePerDayElib
{
	MongoDBMorphia mongoutil = new MongoDBMorphia();
	Datastore ds = mongoutil.getMorphiaDatastoreForNestVer2();
	Datastore ds1=mongoutil.getMorphiaDatastoreForProduct();
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  //yyyy-MM-dd'T'HH:mm:ss.SSS'Z'

	 Product product=new Product();
	 public Logger log;
	 public static WebDriver driver;
	 public static WebDriver driver1;
	 int count1=0;
	 int count2=0;
	 
	 public DifferencePerDayElib()
	 {
	  log = Logger.getLogger(this.getClass());
	  Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	 }
	 
	 @Test(enabled=true, priority=1, groups={"All"})
	 public void diffCalculationElib() throws InterruptedException, SQLException
	 {
	  System.out.println("--------------Difference in PID's being fetched-----------------");
	  
	  System.out.println("Fetching all the Product Id's from the url");
	  
	  System.setProperty("webdriver.chrome.driver", "./Drivers/chromedriver.exe");
	  System.setProperty("webdriver.gecko.driver", "./Drivers/geckodriver.exe" );
	  
   	    driver=new ChromeDriver();
   	    driver1=new FirefoxDriver();
   	    driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
   	    driver1.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
		String date1=AddDate.addingDays(-1);
		String date2=AddDate.addingDays(-2);
	    driver.get("https://xdapi.elib.se/v1.0/products?ServiceID=2238&From="+date1+"T13:00&Checksum=c5b42fb8f285b45983bc5325eebd23021a75626bd7edcf4103dce80e71457a50d48dda42ea571dda91511f51854deb9d99a0841c9f36f82ab54f6d19dd5ec6a6");
	    driver1.get("https://xdapi.elib.se/v1.0/products?ServiceID=2238&From="+date2+"T13:00&Checksum=c5b42fb8f285b45983bc5325eebd23021a75626bd7edcf4103dce80e71457a50d48dda42ea571dda91511f51854deb9d99a0841c9f36f82ab54f6d19dd5ec6a6");
        String products = driver.findElement(By.id("content")).getText();
        String products1 = driver1.findElement(By.id("content")).getText();
        
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
            	System.out.println("'PROVIDER_PRODUCT ID' OF "+date1+" PRODUCT COLLECTION : "+result1);
            	System.out.println();
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
            	System.out.println("'PROVIDER_PRODUCT ID' OF" +date2+" PRODUCT COLLECTION : "+result2);
            	System.out.println();
            }
        }
        System.out.println("Total number of Books Processed by NEST on "+date1+" : "+count1);
        System.out.println("Total number of Books Processed by NEST on "+date2+": "+count2);
        System.out.println("Difference of the PID's being processed : "+(count2-count1));
        driver.close();
        driver1.close();
     }
}
