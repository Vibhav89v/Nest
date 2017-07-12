package Mongo.ElibDistributor;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class Contributors 
{
	private String FirstName;
	private String LastName;
	private String Role;
	
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getRole() {
		return Role;
	}
	public void setRole(String role) {
		Role = role;
	}
	
	
}
