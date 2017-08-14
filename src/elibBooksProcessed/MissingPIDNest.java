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

import Mongo.ProductCollection.Product;
import common.AutomationConstants;
import common.SuperTestScript;
import generics.AddDate;
import generics.MongoDBMorphia;

public class MissingPIDNest extends SuperTestScript implements AutomationConstants
{
 MongoDBMorphia mongoutil = new MongoDBMorphia();
 Datastore ds1=mongoutil.getMorphiaDatastoreForProduct();
 Product product=new Product();
 public Logger log;
 public static WebDriver driver;
 
  
  
 public MissingPIDNest()
  {
  log = Logger.getLogger(this.getClass());
  Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
  }
  
  @Test
  public void missingPIDsNest() throws InterruptedException, SQLException
   {
	log.info("------Missing PIDs Nest----------");
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
            
          if(prodCursor.count()==0)   
          {
           log.info("PID not processsed by the Nest :"+result);
          }
           
          while( prodCursor.hasNext() )
          {
           prodCursor.next();
           //log.info("PID processsed by the Nest :" +result);
          }
           
    }
    driver.close();
        
   }
  
}