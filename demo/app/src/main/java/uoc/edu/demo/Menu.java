package uoc.edu.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Menu extends AppCompatActivity {
		public static final String AI_ENABLED = "AI_ENABLED";
		public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
		Switch switchIA;
		ImageView imgTitle;
		EditText id;
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
				Button userProfile = findViewById(R.id.userProfile);
				Button viewUser = findViewById(R.id.viewUser);
				Button editUser = findViewById(R.id.editUser);
				id = findViewById(R.id.userId);
				imgTitle.setImageResource(R.drawable.tictactoe_title);


				//Expense Manager
				Expense expense = new Expense("rest. ca la pagesa", 100, "01/03/2010");
				expense.addPayer("Enric", 50);
				expense.addPayer("Jose Miguel", 50);

				User user1 =  new User("Enric", "evergaraca@uoc.edu", null);
				User user2 =  new User("Jose miguel", "jm@uoc.edu", null);
				Trip newTrip = new Trip(null, "01/02/2010", "La volta al món");
				newTrip.addUser(user1);
				newTrip.addUser(user2);
				newTrip.addExpense(expense);

				ObjectMapper mapper = new ObjectMapper();
				String jsonStr = null;
				try {
					jsonStr = mapper.writeValueAsString(newTrip);
				} catch (JsonProcessingException e){
					e.printStackTrace();
				}

				try {
					Trip tripAux = mapper.readValue(jsonStr, Trip.class);
				} catch (IOException e){
					e.printStackTrace();
				}



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
			userProfile.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					int usertID = 0;
					try {
						usertID = Integer.parseInt(id.getText().toString());
					} catch (NumberFormatException e){
						Toast.makeText(getApplicationContext(),"El user id debe ser numérico!", Toast.LENGTH_SHORT).show();
					}
					Intent intent = new Intent(Menu.this, UserProfile.class);
					intent.putExtra(UserProfile.NEW_USER, true);
					intent.putExtra(UserProfile.EDIT_MODE, true);
					intent.putExtra(UserProfile.USER_ID, usertID);
					startActivity(intent);
				}
			});

			viewUser.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					int usertID = 0;
					try {
						usertID = Integer.parseInt(id.getText().toString());
					} catch (NumberFormatException e){
						Toast.makeText(getApplicationContext(),"El user id debe ser numérico!", Toast.LENGTH_SHORT).show();
					}
					Intent intent = new Intent(Menu.this, UserProfile.class);
					intent.putExtra(UserProfile.NEW_USER, false);
					intent.putExtra(UserProfile.EDIT_MODE, false);
					intent.putExtra(UserProfile.USER_ID, usertID);
					startActivity(intent);
				}
			});
			editUser.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					int usertID = 0;
					try {
						usertID = Integer.parseInt(id.getText().toString());
					} catch (NumberFormatException e){
						Toast.makeText(getApplicationContext(),"El user id debe ser numérico!", Toast.LENGTH_SHORT).show();
					}
					Intent intent = new Intent(Menu.this, UserProfile.class);
					intent.putExtra(UserProfile.NEW_USER, false);
					intent.putExtra(UserProfile.EDIT_MODE, true);
					intent.putExtra(UserProfile.USER_ID, usertID);
					startActivity(intent);
				}
			});

		}

	private void chooseImage(Context context) {
		final CharSequence[] optionsMenu = {"Take Photo", "Choose from Gallery", "Exit" }; // create a menuOption Array
		// create a dialog for showing the optionsMenu
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		// set the items in builder
		builder.setItems(optionsMenu, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				if(optionsMenu[i].equals("Take Photo")){
					// Open the camera and get the photo
					Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
					startActivityForResult(takePicture, 0);
				}
				else if(optionsMenu[i].equals("Choose from Gallery")){
					// choose from  external storage
					Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(pickPhoto , 1);
				}
				else if (optionsMenu[i].equals("Exit")) {
					dialogInterface.dismiss();
				}
			}
		});
		builder.show();
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

	@Override
	public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		switch (requestCode) {
			case REQUEST_ID_MULTIPLE_PERMISSIONS:
				if (ContextCompat.checkSelfPermission(Menu.this,
						Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
					Toast.makeText(getApplicationContext(),
							"FlagUp Requires Access to Camara.", Toast.LENGTH_SHORT)
							.show();
				} else if (ContextCompat.checkSelfPermission(Menu.this,
						Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
					Toast.makeText(getApplicationContext(),
							"FlagUp Requires Access to Your Storage.",
							Toast.LENGTH_SHORT).show();
				} else {
					chooseImage(Menu.this);
				}
				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_CANCELED) {
			switch (requestCode) {
				case 0:
					if (resultCode == RESULT_OK && data != null) {
						Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
						imgTitle.setImageBitmap(selectedImage);
					}
					break;
				case 1:
					if (resultCode == RESULT_OK && data != null) {
						Uri selectedImage = data.getData();
						String[] filePathColumn = { MediaStore.Images.Media.DATA };
						if (selectedImage != null) {
							Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
							if (cursor != null) {
								cursor.moveToFirst();
								int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
								String picturePath = cursor.getString(columnIndex);
								imgTitle.setImageBitmap(BitmapFactory.decodeFile(picturePath));
								cursor.close();
							}
						}
					}
					break;
			}
		}
	}

}