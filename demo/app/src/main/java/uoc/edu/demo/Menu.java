package uoc.edu.demo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class Menu extends AppCompatActivity {
		public static final String AI_ENABLED = "AI_ENABLED";
		Switch switchIA;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_menu);

				switchIA = findViewById(R.id.onIA);
				switchIA.setChecked(false);
				Button register = findViewById(R.id.goRgister);
				Button tresEnRaya = findViewById(R.id.go3EnRaya);

				register.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
								Intent intent = new Intent(Menu.this, MainActivity.class);
								startActivity(intent);

						}
				});
				tresEnRaya.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
								Intent intent = new Intent(Menu.this, TresEnRaya.class);
								intent.putExtra("AI_ENABLED", switchIA.isChecked());
								startActivity(intent);
						}
				});

		}
}