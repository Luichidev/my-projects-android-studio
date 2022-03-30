package uoc.edu.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
	private TextView userLabel;
	private TextView passLabel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);
		Button finished = findViewById(R.id.finished);
		Intent intent = getIntent();
		String s_user = intent.getStringExtra("user");
		String s_pass = intent.getStringExtra("password");

		userLabel = findViewById(R.id.outputLabel1);
		passLabel = findViewById(R.id.outputLabel2);

		userLabel.setText(s_user);
		passLabel.setText(s_pass);
		Log.i("Demo-MainActivity2", s_user + "/" + s_pass);

			finished.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
							Intent data = new Intent();
							Float value = new Float(10.5);
							data.putExtra("ResultData", value);
							setResult(Activity.RESULT_OK, data);
							finish();
					}
			});

	}
}
