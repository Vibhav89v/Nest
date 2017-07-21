package Mongo.ProductCollection;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value="product",noClassnameStored=true)
public class Product 
{
   @Id
   private Integer	id;
   private Integer provider_productid;
   private String isbn;
   private String title;
   private String subtitle;
   private String language;
   
   @Embedded
   private Publisher publisher;
    
   @Embedded
   private BookType booktype;
   		
   private String productstatus;
   private String statusatpublisher;
   private String filedownloadsource;
   private String filedownloaddate;
   private String fileredownloadsource;
   private String fileredownloaddate;
   private String publisheddate;
   private Integer filesize;
   private String createddate;
   private String updateddate;
   
   @Embedded
   private Contributors contributors;
   		
   @Embedded
   private Pricelog pricelog;
   		
   private Boolean adult;
   private Integer aptusid;

   public Integer getId() 
   {
		return id;
   }
   public void setId(Integer id) 
   {
		this.id = id;
   }  
   
   public String getStatusAtPublisher()
   {
	return statusatpublisher;
   }
   public void setStatusAtPublisher(String statusatpublisher)
   {
	this.statusatpublisher = statusatpublisher;
   }
public Integer getProvider_productid() {
	return provider_productid;
}
public void setProvider_productid(Integer provider_productid) {
	this.provider_productid = provider_productid;
}
public String getIsbn() {
	return isbn;
}
public void setIsbn(String string) {
	this.isbn = string;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getSubtitle() {
	return subtitle;
}
public void setSubtitle(String subtitle) {
	this.subtitle = subtitle;
}
public String getLanguage() {
	return language;
}
public void setLanguage(String language) {
	this.language = language;
}

public String getProductstatus() {
	return productstatus;
}
public void setProductstatus(String productstatus) {
	this.productstatus = productstatus;
}
public String getFiledownloadsource() {
	return filedownloadsource;
}
public void setFiledownloadsource(String filedownloadsource) {
	this.filedownloadsource = filedownloadsource;
}
public String getFiledownloaddate() {
	return filedownloaddate;
}
public void setFiledownloaddate(String filedownloaddate) {
	this.filedownloaddate = filedownloaddate;
}
public String getFileredownloadsource() {
	return fileredownloadsource;
}
public void setFileredownloadsource(String fileredownloadsource) {
	this.fileredownloadsource = fileredownloadsource;
}
public String getFileredownloaddate() {
	return fileredownloaddate;
}
public void setFileredownloaddate(String fileredownloaddate) {
	this.fileredownloaddate = fileredownloaddate;
}
public String getPublisheddate() {
	return publisheddate;
}
public void setPublisheddate(String publisheddate) {
	this.publisheddate = publisheddate;
}
public Integer getFilesize() {
	return filesize;
}
public void setFilesize(Integer filesize) {
	this.filesize = filesize;
}
public String getCreateddate() {
	return createddate;
}
public void setCreateddate(String createddate) {
	this.createddate = createddate;
}
public String getUpdateddate() {
	return updateddate;
}
public void setUpdateddate(String updateddate) {
	this.updateddate = updateddate;
}
public Boolean getAdult() {
	return adult;
}
public void setAdult(Boolean adult) {
	this.adult = adult;
}
public Integer getAptusid() {
	return aptusid;
}
public void setAptusid(Integer aptusid) {
	this.aptusid = aptusid;
}
}
