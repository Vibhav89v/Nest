import java.util.StringTokenizer;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import Mongo.ElibDistributor.Elib;
import common.SuperTestScript;
import generics.AddDate;
import generics.MongoDBMorphia;
public class Sample 
{
	
 
 public static void main(String[] args) 
	

  {

	 MongoDBMorphia mongoutil = new MongoDBMorphia();
	 Datastore ds = mongoutil.getMorphiaDatastoreForNlob();
	 String iso="ISODate";
	  System.setProperty("webdriver.chrome.driver", "./Drivers/chromedriver.exe");
	  WebDriver driver= new ChromeDriver();
  
  
	 driver.get("https://xdapi.elib.se/v1.0/products?ServiceID=2238&From="+AddDate.addingDays(-1)+"T13:00&Checksum=c5b42fb8f285b45983bc5325eebd23021a75626bd7edcf4103dce80e71457a50d48dda42ea571dda91511f51854deb9d99a0841c9f36f82ab54f6d19dd5ec6a6");
     String products = driver.findElement(By.id("content")).getText();
	    
     products=products.replaceAll(",", "");
     
     StringTokenizer t = new StringTokenizer(products);
     String word ="";
     while(t.hasMoreTokens())
     {
         word = t.nextToken();
         System.out.println(word);
     }
     try
     {
     	Query query = ds.createQuery(Elib.class);
     	query.criteria("provider_productid").notEqual(null);
     	query.criteria("updateddate").equal(iso+("2017-06-26T18:42:06.835Z"));
     }
     catch(Exception e)
     {
     	
     }
   }
}
