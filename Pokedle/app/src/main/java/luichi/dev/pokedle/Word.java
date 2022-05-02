package luichi.dev.pokedle;

import android.widget.Button;
import java.util.ArrayList;

public class Word {
	private ArrayList<Letter> words;
	private Button button;

	public Word(){
		words = new ArrayList<Letter>();
	}

	public ArrayList<Letter> getWords() {
		return words;
	}

	public void setWords(String letter, String color){
		this.words.add(new Letter(letter, color));
	}

	public Letter getLetter(int index){
		return words.get(index);
	}
}
