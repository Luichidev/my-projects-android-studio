package luichi.dev.startplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
	MultitouchView myTouchView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myTouchView = new MultitouchView(this);
		//setContentView(R.layout.activity_main);
		setContentView(myTouchView);
		getSupportActionBar().hide();
	}
}