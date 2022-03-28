package uoc.edu.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button checkCredentials = findViewById(R.id.checkCredential);
		EditText user = findViewById(R.id.user);
		EditText pass = findViewById(R.id.password);

		String info = user.getText().toString();
		/*
		checkCredentials.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(getApplicationContext(), "Simple Button 1", Toast.LENGTH_LONG).show(); //display the text of button1
			}
		});
		*/
	}
}