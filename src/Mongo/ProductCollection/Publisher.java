package Mongo.ProductCollection;

public class Publisher
{
	private Integer publisherid;
	private String publishername;
	private Integer distributorid;
	private String distributorname;
	private String iscontractavailable;
	
	public Integer getPublisherid() {
		return publisherid;
	}
	public void setPublisherid(Integer publisherid) {
		this.publisherid = publisherid;
	}
	public String getPublishername() {
		return publishername;
	}
	public void setPublishername(String publishername) {
		this.publishername = publishername;
	}
	public Integer getDistributorid() {
		return distributorid;
	}
	public void setDistributorid(Integer distributorid) {
		this.distributorid = distributorid;
	}
	public String getDistributorname() {
		return distributorname;
	}
	public void setDistributorname(String distributorname) {
		this.distributorname = distributorname;
	}
	public String getIscontractavailable() {
		return iscontractavailable;
	}
	public void setIscontractavailable(String iscontractavailable) {
		this.iscontractavailable = iscontractavailable;
	}
	
}
