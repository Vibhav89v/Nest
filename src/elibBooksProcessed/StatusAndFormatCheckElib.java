package elibBooksProcessed;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.bson.Document;
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

import Mongo.ProductCollection.Product;
import common.AutomationConstants;
import generics.AddDate;
import generics.MongoDBMorphia;

public class StatusAndFormatCheckElib implements AutomationConstants
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
	  log.info("--------------Checking book's 'Status' and 'Format'--------------------");
	  
	          DBCollection prodQuery = ds1.getDB().getCollection("product");            
	          DBCursor prodCursor = prodQuery.find(new BasicDBObject("productstatus","ACTIVE"));
	          
	          while( prodCursor.hasNext() )
	          {
	        	  DBObject mObj = prodCursor.next();
	          
	        	  DBObject mObj1 = (DBObject) mObj.get("publisher"); 
	        	  int prodId=(int) mObj.get("provider_productid");
	          
	        	if(mObj1.get("distributorname").equals("ELIB"))
	        	{
	        	  if(!(mObj.get("formats")==null) )
	        	  {
	        		  BasicDBList dbList = (BasicDBList) mObj.get("formats");
	        		  BasicDBObject[] dbArr = dbList.toArray(new BasicDBObject[0]);
	        		  if(dbArr.length==0)
	        		  {
	        			  log.info("_id -> "+mObj.get("_id")+"|| provider_productid -> "+mObj.get("provider_productid")+"|| isbn -> "+mObj.get("isbn")+"|| publishername -> "+ mObj1.get("publishername")+" || iscontractavailable -> "+mObj1.get("iscontractavailable")+" || distributorname -> "+mObj1.get("distributorname")+" || productstatus -> "+mObj.get("productstatus")+" || formats -> "+mObj.get("formats"));
	        			  System.setProperty(CHROME_KEY, DRIVER_PATH+CHROME_FILE);
	        			  driver=new ChromeDriver();
	        			  driver.get("http://130.211.74.42:8083/nestver2/monitor/elib/api/product/present/"+prodId);
	        			  log.info("");
	        			  driver.close();
	        		  }
	        	  }
	           
	        	  if((mObj.get("formats")==null  )  )
	        	  {
	        		  log.info("_id -> "+mObj.get("_id")+"|| provider_productid -> "+mObj.get("provider_productid")+"|| isbn -> "+mObj.get("isbn")+"|| publishername -> "+ mObj1.get("publishername")+" || iscontractavailable -> "+mObj1.get("iscontractavailable")+" || distributorname -> "+mObj1.get("distributorname")+" || productstatus -> "+mObj.get("productstatus")+" || formats -> "+mObj.get("formats"));
	        		  System.setProperty(CHROME_KEY, DRIVER_PATH+CHROME_FILE);
	        		  driver=new ChromeDriver();
	        		  driver.get("http://130.211.74.42:8083/nestver2/monitor/elib/api/product/present/"+prodId);
	        		  log.info("");
	        		  driver.close();
	        	  }
	        	}
	          }
	 	}
}
