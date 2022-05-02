package luichi.dev.pokedle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
	TextView name;
	TextView title;
	TextView tries;
	private Button btn_ask;
	private EditText word;
	String pokemon = "";
	private Button button;
	private LinearLayout linearLayout;
	private Boolean win = false;
	private Boolean errorMsg = false;
	private ArrayList<String> previousGuesses;
	private ArrayList<Word> globalResult;
	private int max_tries;
	private final int THERE_ARENOT_WORDS = 1;
	private final int  THERE_ARE_WORDS = 0;
	private int containerId = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		name = findViewById(R.id.name);
		title = findViewById(R.id.infoTitle);
		tries = findViewById(R.id.tries);
		btn_ask = findViewById(R.id.btn_ask);
		word = findViewById(R.id.word);
		btn_ask.setVisibility(View.INVISIBLE);
		word.setVisibility(View.INVISIBLE);
		previousGuesses = new ArrayList<>();
		globalResult = new ArrayList<Word>();
		max_tries = 6;

		//Para mayor usavilidad iniciamos el teclado al entrar
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

		btn_ask.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String s_word = word.getText().toString();
				s_word = s_word.toUpperCase();
				String regla = "(^[a-zA-Z]*$)";
				Pattern regex = Pattern.compile(regla, Pattern.CASE_INSENSITIVE);
				Matcher name = regex.matcher(s_word);

				if(pokemon.compareTo(s_word) == 0){
					win = true;
				}

				if (!win) {
					if (s_word.compareTo("") == 0) {
						Toast.makeText(MainActivity.this, "💬 You must provide a possible pokemon name.", Toast.LENGTH_SHORT).show();
						errorMsg = true;
					} else if (s_word.length() != pokemon.length()) {
						Toast.makeText(MainActivity.this, "💬 The pokemon name must be " + pokemon.length() + " characters long.", Toast.LENGTH_SHORT).show();
						errorMsg = true;
					} else if(previousGuesses.contains(s_word)){
						Toast.makeText(MainActivity.this, "💬 You already tried this pokemon name.", Toast.LENGTH_SHORT).show();
						errorMsg = true;
					} else if (!name.find()) {
						errorMsg = true;
						Toast.makeText(MainActivity.this, "💬 The pokemon name must contain only letters.", Toast.LENGTH_SHORT).show();
					} else {
						errorMsg = false;
					}

					if (!errorMsg) {
						startGame(s_word);
					}
				} else {
					if(linearLayout.getChildCount() > 0 && max_tries == 6){
						linearLayout.removeAllViews();
					}
					draw(s_word, THERE_ARE_WORDS);
					btn_ask.setEnabled(false);
					Toast.makeText(MainActivity.this, "\uD83C\uDF89 You won! The pokemon was " + pokemon + ".", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void LeerWs(int min, int max) {
		Random random = new Random();
		int newId = random.nextInt( max + 1 - min) + min;
		String url = "https://pokeapi.co/api/v2/pokemon/" + newId;

		StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {
					JSONObject jsonObject = new JSONObject(response);
					String pokemonName = jsonObject.getString("name");
					name.setText(pokemonName);
					pokemon = pokemonName.toUpperCase();
					draw(pokemon, THERE_ARENOT_WORDS); //Pintamos los botones vacios
					containerId = 0;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e("Error", error.getMessage());
			}
		});

		Volley.newRequestQueue(this).add(postRequest);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		int id = item.getItemId();
		switch (id){
			case R.id.generacionI:
				LeerWs(1, 151);
				askInit();
				break;
			case R.id.generacionII:
				LeerWs(152, 251);
				askInit();
				break;
			case R.id.generacionIII:
				LeerWs(252, 386);
				askInit();
				break;
			case R.id.generacionIV:
				LeerWs(387, 493);
				askInit();
				break;
			case R.id.generacionV:
				LeerWs(494, 649);
				askInit();
				break;
			case R.id.generacionVI:
				LeerWs(650, 721);
				askInit();
				break;
			case R.id.generacionVII:
				LeerWs(722, 809);
				askInit();
				break;
		}
		return true;
	}

	private void askInit() {
		title.setVisibility(View.INVISIBLE);
		btn_ask.setVisibility(View.VISIBLE);
		word.setVisibility(View.VISIBLE);
	}

	private void setLetters(String word) {
		String[] letters = word.split("");
		String[] pokename = pokemon.split("");
		Word auxWord  = new Word();

		for (int i = 1; i < letters.length; i++){
			if(letters[i].compareTo(pokename[i]) == 0){
				Letter l = new Letter(letters[i], "#6aaa64");
				auxWord.setWords(l.getLetter(), l.getColor());
			} else if(includes(pokemon, letters[i])){
				Letter l = new Letter(letters[i], "#cab557");
				auxWord.setWords(l.getLetter(), l.getColor());
			} else {
				Letter l = new Letter(letters[i], "#cccccc");
				auxWord.setWords(l.getLetter(), l.getColor());
			}
		}

		globalResult.add(auxWord);
	}

	private void drawBlank(String word, LinearLayout linearLayout) {
		for (int i = 1; i <= word.length(); i++){
			button = new Button(this);
			button.setLayoutParams( new ViewGroup.LayoutParams(100, LinearLayout.LayoutParams.WRAP_CONTENT));
			linearLayout.addView(button);
		}
	}

	private void startGame(String word){
		if(max_tries > 0){
			max_tries--;
			previousGuesses.add(word);
			draw(word, THERE_ARE_WORDS);
			this.word.setText("");
			tries.setText(Integer.toString(max_tries));
		}

		if(max_tries == 0){
			btn_ask.setEnabled(false);
			Toast.makeText(this, "\uD83D\uDCA5 You lost! The pokemon was "+ pokemon + ".", Toast.LENGTH_SHORT).show();
		}
	}

	private void draw(String word, int blank){
		containerId++;
		String layoutId = "container"+containerId;
		int resID = getResources().getIdentifier(layoutId, "id", getPackageName());
		linearLayout = findViewById(resID);
		if(blank > 0){
			drawBlank(word, linearLayout);
		} else {
			if(linearLayout.getChildCount() > 0){
				linearLayout.removeAllViews();
			}
			setLetters(word);
			drawLetters(word, linearLayout);
		}
	}

	private boolean includes(String pokemon, String letter) {
		boolean res = false;
		String[] aux  = pokemon.split("");
		for (int i = 0; i < aux.length; i++){
				if(aux[i].compareTo(letter) == 0){
					res = true;
				}
		}
		return res;
	}

	private void drawLetters(String word, LinearLayout linearLayout){
		for (Word w : globalResult){
			drawButtons(w, word, linearLayout);
		}

		globalResult.clear();
	}

	private void drawButtons(Word words, String word, LinearLayout linearLayout) {
		for (int i = 1; i <= word.length(); i++){
			button = new Button(this);
			Letter l = words.getLetter(i-1);
			button.setText(l.getLetter());
			String color = l.getColor();
			button.setBackgroundColor(Color.parseColor(color));
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100,
							 LinearLayout.LayoutParams.WRAP_CONTENT);
			layoutParams.setMargins(5, 0, 0, 10);
			button.setLayoutParams(layoutParams);
			//button.setLayoutParams(new ViewGroup.LayoutParams(100, LinearLayout.LayoutParams.WRAP_CONTENT));
			linearLayout.addView(button);
		}

	}
}