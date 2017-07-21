package elibBooksProcessed;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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

public class BookInactiveOrActiveElib 
{
	MongoDBMorphia mongoutil = new MongoDBMorphia();
	Datastore ds = mongoutil.getMorphiaDatastoreForNestVer2();
	Datastore ds1=mongoutil.getMorphiaDatastoreForProduct();
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	 Elib elib=new Elib();
	 Product product=new Product();
	 public Logger log;
	 public static WebDriver driver;
	 public String iso="ISODate";
	 
	 public BookInactiveOrActiveElib()
	 {
	  log = Logger.getLogger(this.getClass());
	  Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	 }
	 
	 @Test(enabled=true, priority=1, groups={"All"})
	 public void bookStatusCheck() throws InterruptedException, SQLException
	 {
		System.out.println("--------------Book Status Check--------------------");

		DBCollection prodQuery = ds1.getDB().getCollection("product");
		DBCursor prodCursor = prodQuery.find(new BasicDBObject("productstatus", "ACTIVE"));
		// .append( "statusatpublisher",value));
		// .append("$and", Arrays.asList(gt, lt)));
		
		List<Elib> list = new ArrayList<Elib>();

		while (prodCursor.hasNext()) 
		{
			DBObject mObj = prodCursor.next();
			DBObject mObj2 = (DBObject) mObj.get("publisher");

			if (!mObj.get("statusatpublisher").equals("ACTIVE"))
             {
             /*   
              System.out.println("'provider_productid' IN ELIB_WEBSHOP_META COLLECTION : "+mObj.get("provider_productid"));
              System.out.println("'publishername' IN ELIB_WEBSHOP_META COLLECTION : "+ mObj2.get("publishername") );
              System.out.println("'distributorname' IN ELIB_WEBSHOP_META COLLECTION : "+ mObj2.get("distributorname") );
              System.out.println("'iscontractavailable' IN ELIB_WEBSHOP_META COLLECTION : "+ mObj2.get("iscontractavailable") );
              System.out.println("'productstatus' IN ELIB_WEBSHOP_META COLLECTION : "+mObj.get("productstatus"));
              System.out.println("'statusatpublisher' IN ELIB_WEBSHOP_META COLLECTION : "+mObj.get("statusatpublisher"));
              System.out.println("'isbn' IN ELIB_WEBSHOP_META COLLECTION : "+mObj.get("isbn"));
              System.out.println("'_id' IN ELIB_WEBSHOP_META COLLECTION : "+mObj.get("_id"));
              */
              int prodId=(int) mObj.get("provider_productid");
              DBCollection elibQuery = ds.getDB().getCollection("elib_webshop_meta");  
              DBCursor	elibCursor = elibQuery.find(new BasicDBObject("ProductID", prodId));
              
              while( elibCursor.hasNext() )
              {
               DBObject mObj1 = elibCursor.next();
               elib.setProductId( (Integer) mObj1.get("ProductID") );
               
               BasicDBList dbList = (BasicDBList) mObj1.get("Statuses");
               BasicDBObject[] dbArr = dbList.toArray(new BasicDBObject[0]);
               for(BasicDBObject dbObj : dbArr) 
               {
                if(dbObj.get("Name").equals("Active"))
                {
                	System.out.println("_id -> "+mObj.get("_id")+"|| provider_productid -> "+mObj.get("provider_productid")+"|| isbn -> "+mObj.get("isbn")+"|| publisher_publishername -> "+ mObj2.get("publishername")+" || iscontractavailable -> "+mObj2.get("iscontractavailable")+" || productstatus -> "+mObj.get("productstatus")+" || statusatpublisher -> "+mObj.get("statusatpublisher")+"|| ELIB STATUS -> PRESENT");
                }
                else if(!dbObj.get("Name").equals("Active"))
                {
                	System.out.println("_id -> "+mObj.get("_id")+"|| provider_productid -> "+mObj.get("provider_productid")+"|| isbn -> "+mObj.get("isbn")+"|| publisher_publishername -> "+ mObj2.get("publishername")+" || iscontractavailable -> "+mObj2.get("iscontractavailable")+" || productstatus -> "+mObj.get("productstatus")+" || statusatpublisher -> "+mObj.get("statusatpublisher")+"|| ELIB STATUS -> NOT-PRESENT");
                }
                System.out.println();
               }
              }
             }
		  }
	   }
	}