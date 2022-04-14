package uoc.edu.demo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

public class Trip {
	@JsonProperty("image")
	String image;
	@JsonProperty("initDate")
	String initDate;
	@JsonProperty("description")
	String description;
	@JsonProperty("users")
	ArrayList<User> users;
	@JsonProperty("expenses")
	ArrayList<Expense> expenses;
	
	Trip(String image, String initDate, String description) {
		this.image = image;
		this.initDate = initDate;
		this.description = description;
		this.users = new ArrayList<User>();
		this.expenses = new ArrayList<Expense>();
	}
	
	public ArrayList<String> getUsersNames(){
		ArrayList<String> nameUsers = new ArrayList<String>();
		
		for (User u : users) {
			nameUsers.add(u.getName());
		}
		
		return nameUsers;
	}

	public String toString() {
		String info = "{\n";
		
		info += "\n - Description : " + this.description;
		info += "\n - Init Date : " + this.initDate;
		info += "\n - Users: ";
		for (User u : users) {
			info += "\n\t" + u.toString();
		}
		info += "\n - Expenses: ";
		for (Expense e : expenses) {
			info += "\n\t" + e.toString();
		}
		info += "\n}";
		return info;
	}
	
	public void addUser(User u) {
		users.add(u);
	}
	
	public void addExpense(Expense e) {
		expenses.add(e);
	}
	
	public int CalculateSummary() {
		int sum = 0;
		for (Expense expense : expenses) {
			sum += expense.getTotalAmount();
			for (User user : users) {
				Integer amount =  expense.getAmountSpentByUser(user.getName());
				if(amount != null) {
					user.AddAmountSpent(amount);
				}
			}
		}
		return sum;
	}
}
