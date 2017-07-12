package Mongo.PublitDistributor;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class Manifestation 
{
	private String id;
	private String work_id;
	private String product_id;
	private String type;
	private String isbn_id;
	private String status;
	private String published_at;
	private String format;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWork_id() {
		return work_id;
	}
	public void setWork_id(String work_id) {
		this.work_id = work_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIsbn_id() {
		return isbn_id;
	}
	public void setIsbn_id(String isbn_id) {
		this.isbn_id = isbn_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPublished_at() {
		return published_at;
	}
	public void setPublished_at(String published_at) {
		this.published_at = published_at;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
}
