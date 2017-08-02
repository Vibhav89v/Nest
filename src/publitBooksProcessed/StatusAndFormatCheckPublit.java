package publitBooksProcessed;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sun.jersey.api.client.ClientResponse;

import Mongo.ProductCollection.Product;
import Mongo.PublitDistributor.Publit;
import generics.AddDate;
import generics.MongoDBMorphia;
import restClientForPublit.AbstractRestClient;
import valueObject.Datum;
import valueObject.PublitVO;

public class StatusAndFormatCheckPublit 
{
	MongoDBMorphia mongoutil = new MongoDBMorphia();
	Datastore ds1=mongoutil.getMorphiaDatastoreForProduct();
	Publit publit=new Publit();
	Product product=new Product();
	 int count=0;
	 public Logger log;
	 public static WebDriver driver;
	 
	 public StatusAndFormatCheckPublit()
	 {
	  log = Logger.getLogger(this.getClass());
	  Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	 }
	 
	 @Test(enabled=true, priority=3, groups={"All"})
	 public void statusFormatCheckPublit() throws InterruptedException, SQLException
	 {
	  log.info("--------------In Publit checking 'STATUS' and 'FORMAT'--------------------");
	  
		//=============================log.info("Complete Information : "+vo);===================================
		
			 DBCollection query = ds1.getDB().getCollection("product");           
	         DBCursor	prodCursor = query.find(new BasicDBObject("productstatus","ACTIVE"));
	         
	         while( prodCursor.hasNext() )
	          {
	        	  DBObject mObj = prodCursor.next();
	          
	        	  DBObject mObj1 = (DBObject) mObj.get("publisher"); 
	        	  int prodId=(int) mObj.get("provider_productid");
	          
	        	if(mObj1.get("distributorname").equals("PUBLIT"))
	        	{
	        	  if(!(mObj.get("formats")==null) )
	        	  {
	        		  BasicDBList dbList = (BasicDBList) mObj.get("formats");
	        		  BasicDBObject[] dbArr = dbList.toArray(new BasicDBObject[0]);
	        		  if(dbArr.length==0)
	        		  {
	        			  log.info("_id -> "+mObj.get("_id")+"|| provider_productid -> "+mObj.get("provider_productid")+"|| isbn -> "+mObj.get("isbn")+"|| publishername -> "+ mObj1.get("publishername")+" || iscontractavailable -> "+mObj1.get("iscontractavailable")+" || distributorname -> "+mObj1.get("distributorname")+" || productstatus -> "+mObj.get("productstatus")+" || formats -> "+mObj.get("formats"));
	        			  System.setProperty("webdriver.chrome.driver", "./Drivers/chromedriver.exe");
	        			  driver=new ChromeDriver();
	        			  driver.get("http://130.211.74.42:8083/nestver2/monitor/elib/api/product/present/"+prodId);
	        			  log.info("");
	        			  driver.close();
	        		  }
	        	  }
	           
	        	  if((mObj.get("formats")==null  )  )
	        	  {
	        		  log.info("_id -> "+mObj.get("_id")+"|| provider_productid -> "+mObj.get("provider_productid")+"|| isbn -> "+mObj.get("isbn")+"|| publishername -> "+ mObj1.get("publishername")+" || iscontractavailable -> "+mObj1.get("iscontractavailable")+" || distributorname -> "+mObj1.get("distributorname")+" || productstatus -> "+mObj.get("productstatus")+" || formats -> "+mObj.get("formats"));
	        		  System.setProperty("webdriver.chrome.driver", "./Drivers/chromedriver.exe");
	        		  driver=new ChromeDriver();
	        		  driver.get("http://130.211.74.42:8083/nestver2/monitor/elib/api/product/present/"+prodId);
	        		  log.info("");
	        		  driver.close();
	        	  }
	        	}
	          }
	 	}
}
