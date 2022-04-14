package code;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Trip {

	private String image;
	private Date initDate;
	private String description;
	private ArrayList<User> users;
	private ArrayList<Expense> expenses;
	
	Trip(String image, Date initDate, String description) {
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
		
		info += "\n - Description : " + description;
		info += "\n - Init Date : " + getDate();
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
	
	
	public String getDate() {
	    String date = "";
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	    date = formatter.format(this.initDate);
	    return date;
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
