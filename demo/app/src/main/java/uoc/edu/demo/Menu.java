package uoc.edu.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class Menu extends AppCompatActivity {
		Switch onIA;
		static final String AI_ENABLED = "AI_ENABLED";
		boolean withIA = false;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_menu);
				onIA = findViewById(R.id.onIA);
				onIA.setChecked(false);
				Button register = findViewById(R.id.goRgister);
				Button tresEnRaya = findViewById(R.id.go3EnRaya);

				register.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
								Intent intent = new Intent(Menu.this, MainActivity.class);
								intent.putExtra("AI_ENABLED", onIA.isChecked());
								startActivity(intent);

						}
				});
				tresEnRaya.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
								Intent intent = new Intent(Menu.this, TresEnRaya.class);
								startActivity(intent);
						}
				});

		}
}