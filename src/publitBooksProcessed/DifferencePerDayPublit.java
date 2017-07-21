package publitBooksProcessed;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.testng.annotations.Test;

import com.sun.jersey.api.client.ClientResponse;

import Mongo.ProductCollection.Product;
import Mongo.PublitDistributor.Publit;
import generics.AddDate;
import generics.MongoDBMorphia;
import restClientForPublit.AbstractRestClient;
import vo.Datum;
import vo.PublitVO;

public class DifferencePerDayPublit 
{

	static String userid = "nextory_api_user";
	static String password = "tos559ntio8ge9ep";
	static String date1 = AddDate.addingDays(-1);
	static String date2 = AddDate.addingDays(-2);
	static String URL1 = "https://api.publit.com/trade/v2.0/products?only=isbn,updated_at&updated_at=" + date1+ "&updated_at_args=greater_equal;combinator";
	static String URL2 = "https://api.publit.com/trade/v2.0/products?only=isbn,updated_at&updated_at=" + date2+ "&updated_at_args=greater_equal;combinator";
	MongoDBMorphia mongoutil = new MongoDBMorphia();
	Datastore ds1=mongoutil.getMorphiaDatastoreForProduct();
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  //yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
	
	ClientResponse clientReponse1,clientReponse2;
	AbstractRestClient abstractRestClient = new AbstractRestClient();
	
	 Publit publit=new Publit();
	 Product product=new Product();
	 public Logger log;
	 
	 public DifferencePerDayPublit()
	 {
	  log = Logger.getLogger(this.getClass());
	  Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	 }
	 
	 @Test(enabled=true, priority=3, groups={"All"})
	 public void differncePerDayPublit() throws InterruptedException, SQLException
	 {
	  log.info("--------------In Publit Calculating ISBN per day--------------------");
	  
	  log.info("Fetching all the ISBN's from the url");
	  
	    abstractRestClient.setHTTPBasicAuthFilter(userid, password);
	    clientReponse1 = abstractRestClient.get(URL1, null, null);
	    clientReponse2 = abstractRestClient.get(URL2, null, null);
	    
		PublitVO vo1 = abstractRestClient.getEntity(clientReponse1, PublitVO.class);
		PublitVO vo2 = abstractRestClient.getEntity(clientReponse2, PublitVO.class);
		//=============================System.out.println("Complete Information : "+vo);===================================
		
		List<Datum> data1 = vo1.getData();
		List<Datum> data2 = vo2.getData();
		List<String> isbnList1 = new ArrayList<>();
		List<String> isbnList2 = new ArrayList<>();
		for (Datum datum : data1)
		{
			isbnList1.add(datum.getIsbn());
		}
		for (Datum datum : data2)
		{
			isbnList2.add(datum.getIsbn());
		}
		//==============================================ISBN List========================================================
		System.out.println("ISBN being fetched for " + date1+ ": " + isbnList1);
		System.out.println("ISBN being fetched for " + date2+ ": " + isbnList2);
		System.out.println("ISBN List Size : "+isbnList1.size());
		System.out.println("ISBN List Size : "+isbnList2.size());
		System.out.println("Difference in number of ISBN being fetched : "+(isbnList1.size() - isbnList2.size()));
	 }
}
