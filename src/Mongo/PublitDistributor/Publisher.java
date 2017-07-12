package Mongo.PublitDistributor;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class Publisher 
{
	private Integer id;
	private String company_name;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	
	
}
