package publitBooksProcessed;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.testng.annotations.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sun.jersey.api.client.ClientResponse;

import Mongo.ProductCollection.Product;
import Mongo.PublitDistributor.Publit;
import PublitToProductTransformation.Isbn;
import generics.AddDate;
import generics.MongoDBMorphia;
import restClientForPublit.AbstractRestClient;
import vo.Datum;
import vo.PublitVO;

public class PublitBooksActiveStatusAndNetPriceZeroNest 
{
	/*static String userid = "nextory_api_user";
	static String password = "tos559ntio8ge9ep";
	static String URL1 = "https://api.publit.com/trade/v2.0/products?with=publisher,files&updated_at=&updated_at_args=greater_equal;combinator";*/
	
	MongoDBMorphia mongoutil = new MongoDBMorphia();
	Datastore ds1=mongoutil.getMorphiaDatastoreForProduct();
	
	Publit publit=new Publit();
	Product product=new Product();
	int count=0;
	public Logger log;
	 
	 public PublitBooksActiveStatusAndNetPriceZeroNest()
	 {
	  log = Logger.getLogger(this.getClass());
	  Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	 }
	 
	 @Test(enabled=true, priority=3, groups={"All"})
	 public void gettingCountISBN() throws InterruptedException, SQLException
	 {
	  log.info("--------------Information of Publit ISBN's with STATUS='ACTIVE' and NET PRICE='0' in NEST-------");
	  
	  /*  PublitVO vo=Isbn.getCompleteInfoPublit(URL1,userid, password); 
	    
		List<Datum> data = vo.getData();
		List<String> isbnList = new ArrayList<>();
		for (Datum datum : data)
		{
			isbnList.add(datum.getIsbn());
		}
		
		for(int i=0;i<isbnList.size();i++)
		{
			String result=isbnList.get(i);
			System.out.println("ISBN "+(i+1)+" = " +isbnList.get(i));
			System.out.println();*/
			
			 DBCollection query = ds1.getDB().getCollection("product");           
	         DBCursor	prodCursor = query.find(new BasicDBObject("productstatus", "ACTIVE"));
	         
	         while( prodCursor.hasNext() )
	          {
	           DBObject mObj = prodCursor.next();
	           product.setProvider_productid( (Integer) mObj.get("provider_productid"));
	           DBObject mObj1 = (DBObject)mObj.get("publisher");
	           
	           if(mObj1.get("distributorname").equals("PUBLIT") && (mObj.get("netprice").equals(0.0) || mObj.get("netprice")==null))
	           {
	        	   System.out.println("_id -> "+mObj.get("_id")+"|| provider_productid -> "+mObj.get("provider_productid")+"|| isbn -> "+mObj.get("isbn")+" || productstatus -> "+mObj.get("productstatus")+" || statusatpublisher -> "+mObj.get("statusatpublisher")+" || netprice -> "+mObj.get("netprice")+" || publishername -> "+mObj1.get("publishername")+" || distributorname -> "+mObj1.get("distributorname")+" || iscontractavailable -> "+mObj1.get("iscontractavailable")+" || updateddate -> "+mObj.get("updateddate"));
	               System.out.println();
	           }
	          }
		}
	}
