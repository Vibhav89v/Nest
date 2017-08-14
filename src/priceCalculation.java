
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import Mongo.ProductCollection.Product;
import generics.AddDate;
import generics.MongoDBMorphia;

public class priceCalculation 
{
	MongoDBMorphia mongoutil = new MongoDBMorphia();
	Datastore ds1 = mongoutil.getMorphiaDatastoreForProduct();
	Product product=new Product();
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	public Logger log;
	String publishedDate;
	String curdate=AddDate.currentDate();
	String currentdate;
	double netprice;
	long age;
	String subprice;
   
	 public priceCalculation()
	 {
	  log = Logger.getLogger(this.getClass());
	  Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	 }
	 
	 @Test(enabled=true, priority=3, groups={"All"})
	 public void statusCurrentDayNest() throws InterruptedException, SQLException, ParseException
	 {
		 List<Integer> list = new ArrayList<Integer>();
	    	int x;
	    	
		DBCollection prodQuery = ds1.getDB().getCollection("product");            
 		DBCursor prodCursor = prodQuery.find(new BasicDBObject("productstatus","ACTIVE"));
 		DBCursor prodCursor1 = prodQuery.find(new BasicDBObject("productstatus","PARKED"));
 		
 		while(prodCursor.hasNext() || prodCursor1.hasNext() )
 		{
 	     if(prodCursor.hasNext())
 	        {
 	    	
 	      try
 	      {
 	    	  DBObject mObj = prodCursor.next();
 	    	  publishedDate = dateFormat.format((Date) mObj.get("publisheddate"));
 	    	  netprice = (double) mObj.get("netprice");
 	           
 	    	  age = AddDate.calculateNumberOfDays(publishedDate, curdate);
 	    	  subprice = (String) mObj.get("subprice");
 	    	  log.info("Provider_productid : " +mObj.get("provider_productid")+ " || ProductStatus : "+mObj.get("productstatus")+ " || publisheddate : "+mObj.get("publisheddate")+" || netprice : "+mObj.get("netprice")+" || age : "+age+" || subprice : "+mObj.get("subprice"));
 	    	 
 	    	  
 	    	  
 	    	  
 	    	  if(age<=180)
 	    	  {
 	    		  if(subprice.equals("STANDARD"))
 	    		  //Assert.assertEquals(subprice, "STANDARD");
 	    		  log.info("For age <= 180,,Subprice is STANDARD"); 
 	    		  
 	    		  else
 	    		  {
 	    			  x= (int) mObj.get("provider_productid");
 	    			 list.add(x);
 	    			 log.info("For age <= 180,, Subprice is not STANDARD");
 	    		  }
 	    	  }
 	       
 	    	  else if( age > 180 && netprice <= 46.0 )
 	    	  {
 	    		 if(subprice.equals("BASE"))
 	    		 // Assert.assertEquals(subprice, "BASE");
 	    		  log.info("For age > 180 and NetPrice <= 46,,Subprice is BASE");
 	    		 
 	    		 
 	    		 else
	    		  {
	    			  x= (int) mObj.get("provider_productid");
	    			 list.add(x);
	    			 log.info("For age > 180 and NetPrice <=46,, Subprice is not BASE");
	    		  }
 	    	  }
 	      
 	    	  else if( age > 180 && netprice > 46.0 && netprice <= 128.0)
 	    	  {
 	    		 if(subprice.equals("STANDARD"))
 	    		 // Assert.assertEquals(subprice, "STANDARD");
 	    		  log.info("For age > 180 and NetPrice > 46 and netprice <=128.0 ,,Subprice is STANDARD"); 
 	    		 else
	    		  {
 	    			log.info("For age > 180 and Netprice between 46 and 128,, Subprice is not STANDARD");
	    			  x= (int) mObj.get("provider_productid");
	    			 list.add(x);
	    		  }
 	    		 
 	    	  }
 	    	  System.out.println();
 	      }
 	      
 	      catch(Exception e)
 	      {
 	    	 e.printStackTrace();
 	      }
 	       
 	     
 	    } 
 	      
 	        
 	     	else if(prodCursor1.hasNext())
 	        {
 	          try
 	          {
 	          
 	           DBObject mObj1 = prodCursor1.next();
 	           publishedDate = dateFormat.format((Date) mObj1.get("publisheddate"));
 	           netprice = (double) mObj1.get("netprice");
 	           
 	           age = AddDate.calculateNumberOfDays(publishedDate, curdate);
 	           subprice = (String) mObj1.get("subprice");
 	           log.info("Provider_productid : " +mObj1.get("provider_productid")+ " || ProductStatus : "+mObj1.get("productstatus")+ " || publisheddate : "+mObj1.get("publisheddate")+" || netprice : "+mObj1.get("netprice")+" || age : "+age+" || subprice : "+mObj1.get("subprice"));
 	          
 	          if(age<=180)
 	    	  {
 	    		  if(subprice.equals("STANDARD"))
 	    		  //Assert.assertEquals(subprice, "STANDARD");
 	    		  log.info("For age <= 180,,Subprice is STANDARD"); 
 	    		  
 	    		  else
 	    		  {
 	    			  x= (int) mObj1.get("provider_productid");
 	    			 list.add(x);
 	    			 log.info("For age <= 180,, Subprice is not STANDARD");
 	    		  }
 	    	  }
 	       
 	    	  else if( age > 180 && netprice <= 46.0 )
 	    	  {
 	    		 if(subprice.equals("BASE"))
 	    		 // Assert.assertEquals(subprice, "BASE");
 	    		  log.info("For age > 180 and NetPrice <= 46,,Subprice is BASE");
 	    		 
 	    		 
 	    		 else
	    		  {
	    			  x= (int) mObj1.get("provider_productid");
	    			 list.add(x);
	    			 log.info("For age > 180 and NetPrice <=46,, Subprice is not BASE");
	    		  }
 	    	  }
 	      
 	    	  else if( age > 180 && netprice > 46.0 && netprice <= 128.0)
 	    	  {
 	    		 if(subprice.equals("STANDARD"))
 	    		 // Assert.assertEquals(subprice, "STANDARD");
 	    		  log.info("For age > 180 and NetPrice > 46 and netprice <=128.0 ,,Subprice is STANDARD"); 
 	    		 else
	    		  {
 	    			log.info("For age > 180 and Netprice between 46 and 128,, Subprice is not STANDARD");
	    			  x= (int) mObj1.get("provider_productid");
	    			 list.add(x);
	    		  }
 	    		 
 	    	  }
 	         System.out.println();
 	          }
 	          
 	          catch(Exception e)
 	          {
 	        	  e.printStackTrace();
 	          }
 	        }
 	           
 	        
// 	           age = AddDate.calculateNumberOfDays(publishedDate, curdate);
// 	           subprice = (String) mObj.get("subprice");
// 	           log.info("publisheddate : "+mObj.get("publisheddate")+" || netprice : "+mObj.get("netprice")+" || age : "+age+" || subprice : "+mObj.get("subprice"));
 		 
	
   }
 		log.info("Total Number of 'provider_productid' which did not pass the Validations are: " +list.size()+ " and are listed below:");	 
 		
 		for(int i=0;i<list.size();i++)
 		{
 			
 			System.out.print(list.get(i)+", ");
 		}
}
}