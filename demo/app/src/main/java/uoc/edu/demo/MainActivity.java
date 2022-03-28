package uoc.edu.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
	private EditText user;
	private EditText pass;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button checkCredentials = findViewById(R.id.checkCredential);
		user = findViewById(R.id.user);
		pass = findViewById(R.id.password);
		//user.setText("hola pepito");
		String info = user.getText().toString();


		checkCredentials.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String s_pwd = pass.getText().toString();

				if (s_pwd.length() <= 5) {
					Toast.makeText(getApplicationContext(), "Error: El password debe tener al menos 5 caracteres", Toast.LENGTH_LONG).show(); //display the text of button1
				}

				//boolean yes = hasMayus(s_pwd);
			}
		});

	}

	public boolean hasMayus(String p) {
		char clave;
		boolean res = false;
		for (byte i = 0; i < p.length(); i++) {
			clave = p.charAt(i);
			String passValue = String.valueOf(clave);
			res = passValue.matches("[A-Z].*");
		}

		return res;
	}

}