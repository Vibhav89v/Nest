package Mongo.ElibDistributor;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class AvailableFormats 
{
	private Integer FormatID;
	private String Name;
	private Integer SizeInBytes;
	
	public Integer getFormatID() {
		return FormatID;
	}
	public void setFormatID(Integer formatID) {
		FormatID = formatID;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public Integer getSizeInBytes() {
		return SizeInBytes;
	}
	public void setSizeInBytes(Integer sizeInBytes) {
		SizeInBytes = sizeInBytes;
	}
	
	
}
