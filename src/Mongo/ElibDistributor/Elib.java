package Mongo.ElibDistributor;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value="elib_webshop_meta",noClassnameStored=true)
public class Elib 
{
 
 private String id;
 /*@Id*/
 private Integer ProductID;
 private String UpdatedDate;
 private String Title;
 private String Publisher; 
 
 @Embedded
 private BookType BookType;
   
 @Embedded
 private Statuses Statuses;
   
 private String Language;
 
 @Embedded
 private Prices Prices;
   
 @Embedded
 private AvailableFormats AvailableFormats;
   
 private String DistributionRegions;
 
 @Embedded
 private Contributors Contributors;
   
 private String PublishedDate;
 private String lastupdatedon;
 
 public Integer getProductId() {
  return ProductID;
 }
 public void setProductId(Integer ProductID) {
  this.ProductID = ProductID;
 }
 public String getUpdatedDate() {
  return UpdatedDate;
 }
 public void setUpdatedDate(String UpdatedDate) {
  this.UpdatedDate = UpdatedDate;
 }
 public String getTitle() {
  return Title;
 }
 public void setTitle(String Title) {
  this.Title = Title;
 }
 public String getPublisher() {
  return Publisher;
 }
 public void setPublisher(String Publisher) {
  this.Publisher = Publisher;
 }
 
 public String getLanguage() {
  return Language;
 }
 public void setLanguage(String Language) {
  this.Language = Language;
 }
 
 public String getDistributionRegions() {
  return DistributionRegions;
 }
 public void setDistributionRegions(String DistributionRegions) {
  this.DistributionRegions = DistributionRegions;
 }

 public String getPublishedDate() {
  return PublishedDate;
 }
 public void setPublishedDate(String PublishedDate) {
  this.PublishedDate = PublishedDate;
 }
 public String getLastupdatedon() {
  return lastupdatedon;
 }
 public void setLastupdatedon(String Lastupdatedon) {
  this.lastupdatedon = Lastupdatedon;
 }
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}

}