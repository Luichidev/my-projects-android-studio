package code;

public class User {
	private String name;
	private String email;
	private String image;
	private int amount;
	
	
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
		info += " Name: " + name;
		info += ", Email: " + email;
		info += " }";
		return info;
	}
}
