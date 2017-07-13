package elibBooksProcessed;

import java.sql.SQLException;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import Mongo.ElibDistributor.Elib;
import generics.AddDate;
import generics.MongoDBMorphia;

public class CountPIDElib 
{
	MongoDBMorphia mongoutil = new MongoDBMorphia();
	Datastore ds = mongoutil.getMorphiaDatastoreForNestVer2();

	 Elib elib=new Elib();
	 public Logger log;
	 public static WebDriver driver;
	 int count=0;
	 
	 public CountPIDElib()
	 {
	  log = Logger.getLogger(this.getClass());
	  Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	 }
	 
	 @Test(enabled=true, priority=1, groups={"All"})
	 public void elibToProductTransformation() throws InterruptedException, SQLException
	 {
	  System.out.println("--------------From Elib Counting ProductID being Fetched--------------------");
	  
	  System.out.println("Fetching all the Product Id's from the url");
	  
	  System.setProperty("webdriver.chrome.driver", "./Drivers/chromedriver.exe");
	  
	  driver=new ChromeDriver();
		//----------------------------------ELIB---------------------------------------------------------------
		String date=AddDate.addingDays(-1);
	    driver.get("https://xdapi.elib.se/v1.0/products?ServiceID=2238&From="+date+"T13:00&Checksum=c5b42fb8f285b45983bc5325eebd23021a75626bd7edcf4103dce80e71457a50d48dda42ea571dda91511f51854deb9d99a0841c9f36f82ab54f6d19dd5ec6a6");
        String products = driver.findElement(By.id("content")).getText();
	    
        products=products.replaceAll(",","");
        
        StringTokenizer t = new StringTokenizer(products);
        String ProductID ="";
      
        while(t.hasMoreTokens())
        {
        	ProductID = t.nextToken();
        	System.out.println();
        	count++;
        	int result = Integer.parseInt(ProductID);
            System.out.println("'ProductID' IN ELIB_WEBSHOP_META COLLECTION: "+result);
        }
        System.out.println();
        System.out.println("==================Total number of 'ProductID's' being fetched================= : "+count);
        driver.close();
  }
}
