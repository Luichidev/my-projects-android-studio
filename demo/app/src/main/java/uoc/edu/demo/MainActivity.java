package uoc.edu.demo;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.Nullable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

		private static final int REQUEST_ACTIVITY2 = 0;
	private EditText user;
	private EditText pass;
	private TextView errorUser;
	private TextView errorPwd;
	private Button checkCredentials;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		checkCredentials = findViewById(R.id.checkCredential);
		user = findViewById(R.id.user);
		pass = findViewById(R.id.password);

		errorUser = findViewById(R.id.error_user);
		errorPwd = findViewById(R.id.error_pwd);

		errorUser.setVisibility(View.INVISIBLE);
		errorPwd.setVisibility(View.INVISIBLE);

		user.setOnFocusChangeListener(new View.OnFocusChangeListener() {
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
						if (!hasFocus) {
								checkEmail();
						}
				}
		});

		pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
				@Override
				public void onFocusChange(View view, boolean hasFocus) {
						if (!hasFocus) {
								checkPassword();
						}
				}
		});


			checkCredentials.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
					checkPassword();
					checkEmail();
					if( checkEmail() && checkPassword() ){
							Intent intent = new Intent(MainActivity.this, MainActivity2.class);
							intent.putExtra("user", user.getText().toString());
							intent.putExtra("password", pass.getText().toString());

							//startActivityForResult(intent, REQUEST_ACTIVITY2);
							someActivityResultLauncher.launch(intent);
					}
			}
		});

	}

		// You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
		ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
				new ActivityResultContracts.StartActivityForResult(),
				new ActivityResultCallback<ActivityResult>() {
						@Override
						public void onActivityResult(ActivityResult result) {
								if (result.getResultCode() == Activity.RESULT_OK) {
										// There are no request codes
										Intent data = result.getData();

										float value = data.getFloatExtra("ResultData", -1);
										checkCredentials.setText("Valor " + value);
								}
						}
				});

	private boolean hasMayus(String p) {
		char clave;
		boolean res = false;
		for (byte i = 0; i < p.length(); i++) {
			clave = p.charAt(i);
			String passValue = String.valueOf(clave);
			if(passValue.matches("[A-Z]")){
					res = true;
			}

		}

		return res;
	}

	private boolean isEmail(String email) {
			String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
		return email.matches(emailPattern);
	}

	private boolean checkPassword(){
			//check password
			String s_pwd = pass.getText().toString().trim();

			char[] chars = s_pwd.toCharArray();
			boolean containsDigit = false;
			for(char c : chars){
					if(Character.isDigit(c)){
							containsDigit = true;
					}
			}

			boolean allIsCorrect = false;
			if (s_pwd.length() <= 5) {
					errorPwd.setVisibility(View.VISIBLE);
					errorPwd.setText("El password debe estar formado por más de 5 caracteres");
			}else if (!containsDigit) {
					errorPwd.setVisibility(View.VISIBLE);
					errorPwd.setText("El password debe contener al menos un número");
			} else if(!hasMayus(s_pwd)) {
					errorPwd.setVisibility(View.VISIBLE);
					errorPwd.setText("El password debe contener al menos un letra mayúscula");
			}else {
					errorPwd.setVisibility(View.INVISIBLE);
					allIsCorrect = true;
			}

			return allIsCorrect;
	}

	private boolean checkEmail(){
			//check email
			String s_user = user.getText().toString().trim();
			boolean allIsCorrect = false;

			if(!isEmail(s_user)){
					errorUser.setVisibility(View.VISIBLE);
					errorUser.setText("Debe ser un email con formato válido!");
			} else {
					errorUser.setVisibility(View.INVISIBLE);
					allIsCorrect = true;
			}

			return allIsCorrect;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
			super.onActivityResult(requestCode, resultCode, data);

			if ( requestCode == REQUEST_ACTIVITY2){
					float value = data.getFloatExtra("ResultData", 0);
					checkCredentials.setText("Valor " + value);
			}
	}
}