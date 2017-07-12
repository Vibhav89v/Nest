package Mongo.PublitDistributor;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

@Entity(value="publit_meta_info",noClassnameStored=true)
public class Publit 
{
   private String retailer_id;
   private String product_id;
   private String manifestation_type;
   private String title;
   private String isbn;
   private String updated_at;
   private String lastupdatedon_nest;
   
   @Embedded
   private Manifestation manifestation;
   
   @Embedded
   private Publisher publisher;
   
   @Embedded
   private Currency currency;
    
   @Embedded
   private Files files;
   
   public String getRetailer_id() 
   {
	return retailer_id;
   }

   public void setRetailer_id(String retailer_id) {
	this.retailer_id = retailer_id;
   }

   public String getProduct_id() {
	return product_id;
   }

   public void setProduct_id(String product_id) {
	this.product_id = product_id;
   }

   public String getManifestation_type() {
	return manifestation_type;
   }

   public void setManifestation_type(String manifestation_type) {
	this.manifestation_type = manifestation_type;
   }

   public String getTitle() {
	return title;
   }

   public void setTitle(String title) {
	this.title = title;
   }

   public String getIsbn() {
	return isbn;
   }

   public void setIsbn(String isbn) {
	this.isbn = isbn;
   }

   public String getUpdated_at() {
	return updated_at;
   }

   public void setUpdated_at(String updated_at) {
	this.updated_at = updated_at;
   }

   public String getLastupdatedon_nest() {
	return lastupdatedon_nest;
   }

   public void setLastupdatedon_nest(String lastupdatedon_nest) {
	this.lastupdatedon_nest = lastupdatedon_nest;
   }
}
