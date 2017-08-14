package priceCalculation;

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
import common.SuperTestScript;
import generics.AddDate;
import generics.MongoDBMorphia;

public class PriceCalculateScript extends SuperTestScript
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
   
	 public PriceCalculateScript()
	 {
	  log = Logger.getLogger(this.getClass());
	  Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	 }
	 
	 @Test
	 public void statusCurrentDayNest() throws InterruptedException, SQLException, ParseException
	 {
		 List<Integer> aList = new ArrayList<Integer>();
		 int a;
	    	
		 List<Integer> bList = new ArrayList<Integer>();
		 int b;
		 
		 List<Integer> cList = new ArrayList<Integer>();
		 int c;
		 
		 List<Integer> xList = new ArrayList<Integer>();
		 int x;
	    	
		 List<Integer> yList = new ArrayList<Integer>();
		 int y;
		 
		 List<Integer> zList = new ArrayList<Integer>();
		 int z;
	    	
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
 	    	 // log.info("Provider_productid : " +mObj.get("provider_productid")+ " || ProductStatus : "+mObj.get("productstatus")+ " || publisheddate : "+mObj.get("publisheddate")+" || netprice : "+mObj.get("netprice")+" || age : "+age+" || subprice : "+mObj.get("subprice"));
 	    	  
 	    	  if(age<=180)
 	    	  {
 	    		  if(subprice.equals("STANDARD"))
 	    		  {
 	    		  
 	    		  //log.info("For age <= 180,,Subprice is STANDARD"); 
 	    		  }
 	    		  else
 	    		  {
 	    			 a= (int) mObj.get("provider_productid");
 	    			 aList.add(a);
 	    			 log.info("Provider_productid : " +mObj.get("provider_productid")+ " || ProductStatus : "+mObj.get("productstatus")+ " || publisheddate : "+mObj.get("publisheddate")+" || netprice : "+mObj.get("netprice")+" || age : "+age+" || subprice : "+mObj.get("subprice"));
 	    			 log.info("For age <= 180,, Subprice is not STANDARD but :" +subprice);
 	    			 System.out.println();
 	    		  }
 	    	  }
 	       
 	    	  else if( age > 180 && netprice <= 46.0 )
 	    	  {
 	    		 if(subprice.equals("BASE"))
 	    		 {
 	    		 // Assert.assertEquals(subprice, "BASE");
 	    		  //log.info("For age > 180 and NetPrice <= 46,,Subprice is BASE");
 	    		 }
 	    		 
 	    		 else
	    		  {
	    			  b= (int) mObj.get("provider_productid");
	    			  bList.add(b);
	    			  log.info("Provider_productid : " +mObj.get("provider_productid")+ " || ProductStatus : "+mObj.get("productstatus")+ " || publisheddate : "+mObj.get("publisheddate")+" || netprice : "+mObj.get("netprice")+" || age : "+age+" || subprice : "+mObj.get("subprice"));
	    			  log.info("For age > 180 and NetPrice <=46,, Subprice is not BASE but :" +subprice);
	    			  System.out.println();
	    		  }
 	    	  }
 	      
 	    	  else if( age > 180 && netprice > 46.0 && netprice <= 128.0)
 	    	  {
 	    		 if(subprice.equals("STANDARD"))
 	    		 {
 	    		 // Assert.assertEquals(subprice, "STANDARD");
 	    		  //log.info("For age > 180 and NetPrice > 46 and netprice <=128.0 ,,Subprice is STANDARD"); 
 	    		 }
 	    		  else
	    		  {
 	    			 log.info("Provider_productid : " +mObj.get("provider_productid")+ " || ProductStatus : "+mObj.get("productstatus")+ " || publisheddate : "+mObj.get("publisheddate")+" || netprice : "+mObj.get("netprice")+" || age : "+age+" || subprice : "+mObj.get("subprice"));
 	    			log.info("For age > 180 and Netprice between 46 and 128,, Subprice is not STANDARD but :" +subprice);
	    			  c= (int) mObj.get("provider_productid");
	    			 cList.add(c);
	    			 System.out.println();
	    		  }
 	    		 
 	    	  }
 	    	  //System.out.println();
 	      }
 	      catch(Exception e)
 	      {
 	    	 //e.printStackTrace();
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
 	           //log.info("Provider_productid : " +mObj1.get("provider_productid")+ " || ProductStatus : "+mObj1.get("productstatus")+ " || publisheddate : "+mObj1.get("publisheddate")+" || netprice : "+mObj1.get("netprice")+" || age : "+age+" || subprice : "+mObj1.get("subprice"));
 	          
 	          if(age<=180)
 	    	  {
 	    		  if(subprice.equals("STANDARD"))
 	    		  {
 	    		  //Assert.assertEquals(subprice, "STANDARD");
 	    		  //log.info("For age <= 180,,Subprice is STANDARD"); 
 	    		  }
 	    		  
 	    		  else
 	    		  {
 	    			  x= (int) mObj1.get("provider_productid");
 	    			 xList.add(x);
 	    			log.info("Provider_productid : " +mObj1.get("provider_productid")+ " || ProductStatus : "+mObj1.get("productstatus")+ " || publisheddate : "+mObj1.get("publisheddate")+" || netprice : "+mObj1.get("netprice")+" || age : "+age+" || subprice : "+mObj1.get("subprice"));
 	    			 log.info("For age <= 180,, Subprice is not STANDARD but :" +subprice);
 	    			System.out.println();
 	    		  }
 	    	  }
 	       
 	    	  else if( age > 180 && netprice <= 46.0 )
 	    	  {
 	    		 if(subprice.equals("BASE"))
 	    		 {
 	    			// Assert.assertEquals(subprice, "BASE");
 	 	    		 // log.info("For age > 180 and NetPrice <= 46,,Subprice is BASE");
 	 	    	 }
 	    		 
 	    		 else
	    		  {
	    			  y= (int) mObj1.get("provider_productid");
	    			 yList.add(y);
	    			 log.info("Provider_productid : " +mObj1.get("provider_productid")+ " || ProductStatus : "+mObj1.get("productstatus")+ " || publisheddate : "+mObj1.get("publisheddate")+" || netprice : "+mObj1.get("netprice")+" || age : "+age+" || subprice : "+mObj1.get("subprice"));
	    			 log.info("For age > 180 and NetPrice <=46,, Subprice is not BASE but :" +subprice);
	    			 System.out.println();
	    		  }
 	    	  }
 	      
 	    	  else if( age > 180 && netprice > 46.0 && netprice <= 128.0)
 	    	  {
						if (subprice.equals("STANDARD"))
						{
						// Assert.assertEquals(subprice, "STANDARD");
						//log.info("For age > 180 and NetPrice > 46 and netprice <=128.0 ,,Subprice is STANDARD");
						}
 	    		 else
	    		  {
 	    			log.info("Provider_productid : " +mObj1.get("provider_productid")+ " || ProductStatus : "+mObj1.get("productstatus")+ " || publisheddate : "+mObj1.get("publisheddate")+" || netprice : "+mObj1.get("netprice")+" || age : "+age+" || subprice : "+mObj1.get("subprice"));
						log.info("For age > 180 and Netprice between 46 and 128,, Subprice is not STANDARD but :" +subprice);
						z = (int) mObj1.get("provider_productid");
						zList.add(z);
						System.out.println();
	    		  }
 	    	  }
 	         //System.out.println();
 	          }
 	          
 	          catch(Exception e)
 	          {
 	        	 // e.printStackTrace();
 	          }
 	        }
 		}
 		
 		
 		log.info("If the productStatus='ACTIVE'; 'provider_productid' for age <= 180 and  Subprice is not STANDARD are "+aList.size()+" and mentioned Below: ");
		log.info(aList);
		log.info("If the productStatus='ACTIVE'; 'provider_productid' for (age > 180 AND netprice <= 46.0) and  Subprice is not BASE are "+bList.size()+" and mentioned Below: ");
		log.info(bList);
		log.info("If the productStatus='ACTIVE'; 'provider_productid' for (age > 180 && netprice between 46.0 AND 128.0) and  Subprice is not STANDARD are "+cList.size()+" and mentioned Below: ");
 		log.info(cList);
 		
 		log.info("If the productStatus='PARKED'; 'provider_productid' for age <= 180 and  Subprice is not STANDARD are "+xList.size()+" and mentioned Below: ");
		log.info(xList);
		log.info("If the productStatus='PARKED'; 'provider_productid' for (age > 180 AND netprice <= 46.0) and  Subprice is not BASE are "+yList.size()+" and mentioned Below: ");
		log.info(yList);
		log.info("If the productStatus='PARKED'; 'provider_productid' for (age > 180 && netprice between 46.0 AND 128.0) and  Subprice is not STANDARD are "+zList.size()+" and mentioned Below: ");
 		log.info(zList);
}
}