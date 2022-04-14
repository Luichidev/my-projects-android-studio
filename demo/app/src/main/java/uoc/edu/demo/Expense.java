package uoc.edu.demo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.Map;

public class Expense {
	@JsonProperty("description")
	String description;
	@JsonProperty("amount")
	int amount;
	@JsonProperty("date")
	String date;
	@JsonProperty("payers")
	Map<String, Integer> payers;
	
	Expense(String description, int amount, String date){
		this.description = description;
		this.amount = amount;
		this.date = date;
		payers = new HashMap<String, Integer>();
	}
	
	public void addPayer(String name, int amount) {
		payers.put(name, amount);
	}
	
	public Integer getAmountSpentByUser(String name) {
		return payers.get(name);
	}
	
	public int getTotalAmount() {
		return this.amount;
	}

	public void removePayer(String name) {
		payers.remove(name);
	}
	
	public String toString() {
		String info = "{";
		info += "\n - Expense: " + this.description;
		info += "\n - Amount: " + this.amount;
		info += "\n - Date: " + this.date;
		info += "\n - Payers: " + this.payers;
		info += "\n }";
		return info;
	}
}
