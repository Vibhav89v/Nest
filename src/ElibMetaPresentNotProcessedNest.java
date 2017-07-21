

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
import com.mongodb.DBObject;

import Mongo.ElibDistributor.Elib;
import Mongo.ProductCollection.Product;
import generics.AddDate;
import generics.MongoDBMorphia;

public class ElibMetaPresentNotProcessedNest 
{
	 MongoDBMorphia mongoutil = new MongoDBMorphia();
	 Datastore ds=mongoutil.getMorphiaDatastoreForNestVer2();
	 Datastore ds1=mongoutil.getMorphiaDatastoreForProduct();
	 Product product=new Product();
	 Elib elib = new Elib();
	 public Logger log;
	 public static WebDriver driver;
	  
	  
	 public ElibMetaPresentNotProcessedNest()
	  {
	  log = Logger.getLogger(this.getClass());
	  Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	  }
	  
	  @Test(enabled=true, priority=1, groups={"All"})
	  public void missingPIDsNest() throws InterruptedException, SQLException
	   {
		//System.out.println("------Missing PIDs Nest----------");
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
	    		if(result == 1050202)
	    		{
	    			System.out.println();
	    		}
	          
	    		DBCollection prodQuery = ds1.getDB().getCollection("product");            
	    		DBCursor prodCursor = prodQuery.find(new BasicDBObject("provider_productid", result));
	            
	          //System.out.println(prodCursor.count());
	    		if(prodCursor.count()==0)   
	    		{
	    			System.out.println("PID not processsed by the Nest :"+result);
         
	    			DBCollection elibQuery = ds.getDB().getCollection("elib_webshop_meta");
	    			DBCursor elibCursor = elibQuery.find(new BasicDBObject("ProductID",result));
		       
	    			while( elibCursor.hasNext() )
	    			{
	    				DBObject mObj = elibCursor.next();
	    				// System.out.println(mObj.toString());
	    				elib.setProductId( (Integer) mObj.get("ProductID") );
	    				System.out.println("_id -> "+mObj.get("_id")+"|| ProductID -> "+mObj.get("ProductID")+"|| isbn -> "+mObj.get("isbn")+"|| Publisher -> "+ mObj.get("Publisher")+" || Title -> "+mObj.get("Title")+" || UpdatedDate -> "+mObj.get("UpdatedDate"));
	             
	           
	    			}
	           
	    			while( prodCursor.hasNext() )
	    			{
	    				prodCursor.next();
	         
	    			}
	     
	    		}
	    		
	   
	    }
	    driver.close();
	    }
	  }
	 
