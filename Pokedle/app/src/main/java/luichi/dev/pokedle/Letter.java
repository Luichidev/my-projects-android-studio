package luichi.dev.pokedle;

public class Letter {
	private String letter;
	private String color;

	Letter(String letter, String color){
		this.letter = letter;
		this.color = color;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
