package uoc.edu.demo;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
	@JsonProperty("name")
	String name;
	@JsonProperty("email")
	String email;
	@JsonProperty("image")
	String image;
	@JsonProperty("amount")
	int amount;
	
	
	User(String name, String email, String img){
		this.name = name;
		this.email = email;
		this.image = img;
		this.amount = 0;
	}
	
	public void ResetAmount() {
		amount = 0;
	}
	
	public void AddAmountSpent(int amount) {
		this.amount += amount;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		String info = "";
		
		info += "{ ";
		info += " Name: " + this.name;
		info += ", Email: " + this.email;
		info += " }";
		return info;
	}
}
