package code;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Expense {
	private String description;
	private int amount;
	private Date date;
	//private Position pos;
	private Map<String, Integer> payers;
	
	Expense(String description, int amount, Date date){
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
	public String getDate() {
	    String date = "";
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	    date = formatter.format(this.date);
	    return date;
	}
	
	public void removePayer(String name) {
		payers.remove(name);
	}
	
	public String toString() {
		String info = "{";
		info += "\n - Expense: " + description;
		info += "\n - Amount: " + amount;
		info += "\n - Date: " + getDate();
		info += "\n - Payers: " + payers;
		info += "\n }";
		return info;
	}
}
