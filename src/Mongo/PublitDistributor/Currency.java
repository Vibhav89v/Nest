package Mongo.PublitDistributor;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class Currency
{
	private Integer id;
	private String name;
    private Integer iso3;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getIso3() {
		return iso3;
	}
	public void setIso3(Integer iso3) {
		this.iso3 = iso3;
	}
    
    
}
