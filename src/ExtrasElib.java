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


public class ExtrasElib
{
	MongoDBMorphia mongoutil = new MongoDBMorphia();
	Datastore ds = mongoutil.getMorphiaDatastoreForNestVer2();
	Datastore ds1=mongoutil.getMorphiaDatastoreForProduct();
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  //yyyy-MM-dd'T'HH:mm:ss.SSS'Z'

	 Elib elib=new Elib();
	 Product product=new Product();
	 String elibDate;
	 String productDate;
	 public Logger log;
	 public static WebDriver driver;
	 int count=0;
	 
	 public ExtrasElib()
	 {
	  log = Logger.getLogger(this.getClass());
	  Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	 }
	 
	 @Test(enabled=true, priority=5, groups={"All"})
	 public void elibToProductTransformation() throws InterruptedException, SQLException
	 {
	  System.out.println("--------------In Elib to Product Transformation flow Meta--------------------");
	  
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
        Elib prodList=null;
        Product proCollection=null;
        Publisher publish=null;
    
        while(t.hasMoreTokens())
        {
        	ProductID = t.nextToken();
        	int result = Integer.parseInt(ProductID);
            System.out.println("'ProductID' IN ELIB_WEBSHOP_META COLLECTION: "+result);
            DateTime gte = null;
            try
            {
            	 gte = new DateTime(AddDate.currentStringToDate(AddDate.currentDate()));
            	 //System.out.println(gte);
            }
            catch(Exception e)
            {
            	e.printStackTrace();
            }
             //-------------------------------Fetching data from elib_webshop_meta---------------------------------------------
            
            gte				= gte.withTimeAtStartOfDay();
            gte				= gte.minusDays(1);
            //System.out.println(gte);
            Date	start = new Date(gte.withTimeAtStartOfDay().getMillis());
            gte	= gte.plusDays(1);
            Date	end  = new Date(gte.withTimeAtStartOfDay().getMillis());
            
 //-----------------------------------------ELIB INFORMATION-----------------------------------------------------------
            DBCollection query = ds.getDB().getCollection("elib_webshop_meta");            
            //Document	gt = new Document("lastupdatedon", new Document("$gte", start));
            //Document	lt = new Document("lastupdatedon", new Document("$lt", end)); 
            DBCursor	cursor = query.find(new BasicDBObject("ProductID", result));
            		//.append("$and", Arrays.asList(gt, lt)));

 //------------------------------Accessing the Members and Inner Members in a collection---------------------------------------
            List<Elib> list = new ArrayList<Elib>();
            
            while( cursor.hasNext() )
            {
             DBObject mObj = cursor.next();
            // System.out.println(mObj.toString());
             elib.setProductId( (Integer) mObj.get("ProductID") );
             //System.out.println(ProductID);
             
             BasicDBList dbList = (BasicDBList) mObj.get("Statuses");
             BasicDBObject[] dbArr = dbList.toArray(new BasicDBObject[0]);
             for(BasicDBObject dbObj : dbArr) 
             {
              //System.out.println(dbObj);
              System.out.println("'STATUS' IN ELIB_WEBSHOP_META COLLECTION : "+ dbObj.get("Name") );
              System.out.println("'LAST_UPDATED_ON' IN ELIB_WEBSHOP_META COLLECTION : "+mObj.get("lastupdatedon"));
              System.out.println("");
         
     elibDate = dateFormat.format((Date) mObj.get("lastupdatedon"));
     System.out.println("ELIB DATE = "+elibDate);
     //--------------------------------PRODUCT INFORMATION------------------------------------------------------------
     
              //db.product.find({"provider_productid":ProductID,"updateddate":updateddate,}).pretty();
              DBCollection query1 = ds1.getDB().getCollection("product");
              DBCursor cursor1 = query1.find(new BasicDBObject("provider_productid", result));
              List<Product> list1 = new ArrayList<Product>();
              while( cursor1.hasNext() )
              {
               DBObject mObj1 = cursor1.next();
              // System.out.println(mObj1.toString());
               product.setProvider_productid((Integer) mObj1.get("provider_productid"));//setProductId( (Integer) mObj1.get("provider_productid"));
               System.out.println("'ISBN' IN PRODUCT COLLECTION : "+mObj1.get("isbn"));
               System.out.println("'TITLE' IN PRODUCT COLLECTION : "+mObj1.get("title"));
               System.out.println("'UPDATED_DATE' IN PRODUCT COLLECTION :"+mObj1.get("updateddate"));
               System.out.println("'PRODUCT STATUS' IN PRODUCT COLLECTION : "+mObj1.get("productstatus"));
               System.out.println("'FILE SIZE' IN PRODUCT COLLECTION : "+mObj1.get("filesize"));
               System.out.println("'NET PRICE' IN PRODUCT COLLECTION : "+mObj1.get("netprice"));
               System.out.println("'ADULT STATUS' IN PRODUCT COLLECTION : "+mObj1.get("adult"));
               System.out.println("'SUB PRICE' IN PRODUCT COLLECTION : "+mObj1.get("subprice"));
               System.out.println("'BOOK LENGTH' IN PRODUCT COLLECTION : "+mObj1.get("booklength"));
       
     productDate = dateFormat.format((Date)mObj1.get("updateddate"));
     System.out.println("PRODUCT DATE = "+productDate);
     
               DBObject mObj2 = (DBObject) mObj1.get("publisher");
               System.out.println("'IS_CONTRACT_AVAILABLE' IN PRODUCT COLLECTION : "+mObj2.get("iscontractavailable"));
               System.out.println("'PUBLISHER NAME' IN PRODUCT COLLECTIPON : "+mObj2.get("publishername"));
               System.out.println("'DISTRIBUTOR NAME' IN PRODUCT COLLECTION : "+mObj2.get("distributorname"));
               
               DBObject mObj3=(DBObject)mObj1.get("booktype");
               System.out.println("'BOOK_TYPE' IN PRODUCT COLLLECTION : "+mObj3.get("book_type"));
               
               DBObject mObj4=(DBObject)mObj1.get("contributors");
               System.out.println("'FIRST NAME' IN PRODUCT COLLECTION : "+mObj3.get("firstname"));
               System.out.println("'LAST NAME' IN PRODUCT COLLECTION : "+mObj3.get("lastname"));
               System.out.println("'ROLE' IN PRODUCT COLLECTION : "+mObj3.get("role"));
               System.out.println("'LAST UPDATED ON' IN PRODUCT COLLECTION : "+mObj3.get("lastupdatedon"));
               
               DBObject mObj5=(DBObject)mObj1.get("pricelog");
               System.out.println("'CURRENCY' IN PRODUCT COLLECTION : "+mObj5.get("currency"));
               System.out.println("'IS_ACTIVE_PRICE' IN PRODUCT COLLECTION : "+mObj5.get("isactiveprice"));
             
               DBObject mObj6=(DBObject)mObj1.get("pricecalculation");
               if(mObj1.get("pricecalculation") != null)
               {
            	   System.out.println("'PRICE MATRIX ID' IN PRODUCT COLLECTION : "+mObj6.get("pricematrixid"));
            	   System.out.println("'PRICE MATRIX NAME' IN PRODUCT COLLECTION : "+mObj6.get("pricematrixname"));
               }
               else
               {
            	   System.out.println("PRICE CALCULATION FIELDS ARE NULL");
               }
               BasicDBList dbList2 = (BasicDBList) mObj6.get("publisherpaymentmodel");
               BasicDBObject[] dbArr2 = dbList2.toArray(new BasicDBObject[0]);
               for(BasicDBObject dbObj2 : dbArr2)
               {
            	   System.out.println("'PAYMENT MODEL NAME' IN PRODUCT COLLECTION : "+dbObj2.get("paymentmodelname"));
            	   System.out.println("'STATUS' IN PRODUCT COLLECTION : "+dbObj2.get("status"));
               }
               
               BasicDBList dbList1 = (BasicDBList) mObj1.get("productlist");
               BasicDBObject[] dbArr1 = dbList1.toArray(new BasicDBObject[0]);
               for(BasicDBObject dbObj1 : dbArr1) 
               {
                //System.out.println(dbObj1);
                System.out.println("'STATUS' IN PRODUCT COLLECTION : "+ dbObj1.get("name") );
               }
               
               
               
             }
           }
        }
            if(elibDate.equals(productDate))
            {
          	  System.out.println("'lastupdatedon' of 'Elib' and 'updateddate' of 'Product' collection MATCHED");
            }
            else
            {
          	  System.out.println("'lastupdatedon' of 'Elib' and 'updateddate' of 'Product' collection DIDN'T MATCHED");
            }
            System.out.println();
            count++;
   }
        System.out.println("==================Total number of 'ProductID's' being fetched================= : "+count);
        driver.close();
  }
 }


