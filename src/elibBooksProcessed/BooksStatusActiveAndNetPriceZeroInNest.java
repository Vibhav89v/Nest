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
import common.SuperTestScript;
import generics.AddDate;
import generics.MongoDBMorphia;

public class BooksStatusActiveAndNetPriceZeroInNest extends SuperTestScript
{
	 MongoDBMorphia mongoutil = new MongoDBMorphia();
	 Datastore ds1=mongoutil.getMorphiaDatastoreForProduct();
	 Product product=new Product();
	 public Logger log;
	 public static WebDriver driver;
	  
	 public BooksStatusActiveAndNetPriceZeroInNest()
	  {
	  log = Logger.getLogger(this.getClass());
	  Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	  }
	  
	  @Test
	  public void detailOfActiveStatusAndNetPriceZeroInNest() throws InterruptedException, SQLException
	   {
		  
		  List<Integer> zeroList = new ArrayList<Integer>();
			int a;
		    	
			List<Integer> nullList = new ArrayList<Integer>();
			int b;
		  
		  
		log.info("--------Details of 'Active' PID's and 'NET PRICE = 0' in NEST------------------");
		
	  /*  System.setProperty("webdriver.chrome.driver", "./Drivers/chromedriver.exe");
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
	    }
	     */
	          DBCollection prodQuery = ds1.getDB().getCollection("product");            
	          DBCursor prodCursor = prodQuery.find(new BasicDBObject("productstatus","ACTIVE"));   //.append("netprice",0)
	            
	          while( prodCursor.hasNext() )
	          {
	           DBObject mObj = prodCursor.next();
	           DBObject mObj1=(DBObject) mObj.get("publisher");
	           
	           if(mObj1.get("distributorname").equals("ELIB") && (mObj.get("netprice")==null))
	           {
	        	   log.info("_id -> "+mObj.get("_id")+"|| provider_productid -> "+mObj.get("provider_productid")+"|| isbn -> "+mObj.get("isbn")+" || productstatus -> "+mObj.get("productstatus")+" || statusatpublisher -> "+mObj.get("statusatpublisher")+" || netprice -> "+mObj.get("netprice")+" || publishername -> "+mObj1.get("publishername")+" || distributorname -> "+mObj1.get("distributorname")+" || iscontractavailable -> "+mObj1.get("iscontractavailable")+" || updateddate -> "+mObj.get("updateddate"));
	               
	               b= (int) mObj.get("provider_productid");
	        	   nullList.add(b);
	           }
	           
	           else if((mObj1.get("distributorname").equals("ELIB")) && (mObj.get("netprice").equals(0.0)))
	           {
	        	   log.info("_id -> "+mObj.get("_id")+"|| provider_productid -> "+mObj.get("provider_productid")+"|| isbn -> "+mObj.get("isbn")+" || productstatus -> "+mObj.get("productstatus")+" || statusatpublisher -> "+mObj.get("statusatpublisher")+" || netprice -> "+mObj.get("netprice")+" || publishername -> "+mObj1.get("publishername")+" || distributorname -> "+mObj1.get("distributorname")+" || iscontractavailable -> "+mObj1.get("iscontractavailable")+" || updateddate -> "+mObj.get("updateddate"));
	               
	               a= (int) mObj.get("provider_productid");
	        	   zeroList.add(a);
	               
	           }
	           
	           
	    }
	          
	        log.info("PID with distributor name as ELIB and Netprice equals to NULL are " + nullList.size()+ " and are mentioned below: ");
	  	    log.info(nullList);
	  	    
	  	    log.info("PID with distributor name as ELIB and Netprice equals to ZERO are " + zeroList.size()+ " and are mentioned below: ");
	  	    log.info(zeroList);
	  }
}

