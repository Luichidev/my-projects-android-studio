package luichi.dev.pokedle;

import android.widget.Button;
import java.util.ArrayList;

public class Word {
	private ArrayList<Letters> words;
	private Button button;

	public Word(){
		words = new ArrayList<Letters>();
	}

	public ArrayList<Letters> getWords() {
		return words;
	}

	public void setWords(String letter, String color){
		this.words.add(new Letters(letter, color));
	}

	public Letters getLetter(int index){
		return words.get(index);
	}
}
