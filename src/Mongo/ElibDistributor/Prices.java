package Mongo.ElibDistributor;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class Prices
{
	private String Currency;
	private Integer Amount;
	private String ValidFrom;
	private String ValidUntil;
	
	public String getCurrency() {
		return Currency;
	}
	public void setCurrency(String currency) {
		Currency = currency;
	}
	public Integer getAmount() {
		return Amount;
	}
	public void setAmount(Integer amount) {
		Amount = amount;
	}
	public String getValidFrom() {
		return ValidFrom;
	}
	public void setValidFrom(String validFrom) {
		ValidFrom = validFrom;
	}
	public String getValidUntil() {
		return ValidUntil;
	}
	public void setValidUntil(String validUntil) {
		ValidUntil = validUntil;
	}
	
	
}
