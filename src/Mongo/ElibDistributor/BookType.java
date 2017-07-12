package Mongo.ElibDistributor;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class BookType 
{
	private String Name;
	private Integer ID;
	
	public String getName() {
		return Name;
	}
	public void setName(String name) 
	{
		Name = name;
	}
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	
	
}
