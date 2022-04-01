package uoc.edu.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity {
		public static final String AI_ENABLED = "AI_ENABLED";
		public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
		Switch switchIA;
		ImageView imgTitle;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_menu);

				switchIA = findViewById(R.id.onIA);
				switchIA.setChecked(false);
				imgTitle = findViewById(R.id.imageTitle);
				Button register = findViewById(R.id.goRgister);
				Button tresEnRaya = findViewById(R.id.go3EnRaya);
				Button photo = findViewById(R.id.photo);
				imgTitle.setImageResource(R.drawable.tictactoe_title);

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
				photo.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if(checkAndRequestPermissions(Menu.this)){
							chooseImage(Menu.this);
						}
					}
				});

		}

	private void chooseImage(Context context) {

	}

	// function to check permission
		public static boolean checkAndRequestPermissions(final Activity context) {
			int WExtstorePermission = ContextCompat.checkSelfPermission(context,
							Manifest.permission.WRITE_EXTERNAL_STORAGE);
			int cameraPermission = ContextCompat.checkSelfPermission(context,
							Manifest.permission.CAMERA);

			List<String> listPermissionsNeeded = new ArrayList<>();
			if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
				listPermissionsNeeded.add(Manifest.permission.CAMERA);
			}
			if (WExtstorePermission != PackageManager.PERMISSION_GRANTED) {
				listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
			}
			if (!listPermissionsNeeded.isEmpty()) {
				ActivityCompat.requestPermissions(context, listPermissionsNeeded
												.toArray(new String[listPermissionsNeeded.size()]),
								REQUEST_ID_MULTIPLE_PERMISSIONS);
				return false;
			}
			return true;
		}


}