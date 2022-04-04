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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UserProfile extends AppCompatActivity {
	public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
	private ImageView avatarImage;
	private EditText userName;
	private EditText userEmail;
	private TextView errorUser;
	private TextView errorEmail;
	private Button save;
	private final String EMPTY = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);
		ImageView avatar = findViewById(R.id.userAvatar);
		avatar.setImageResource(R.drawable.no_avatar);
		ImageView editAvatar = findViewById(R.id.editAvatar);
		avatarImage = findViewById(R.id.userAvatar);
		save = findViewById(R.id.btn_save);
		save.setEnabled(false);

		userName = findViewById(R.id.editUserName);
		userEmail = findViewById(R.id.editEmail);
		errorUser = findViewById(R.id.errorUser);
		errorEmail = findViewById(R.id.errorEmail);
		errorUser.setVisibility(View.INVISIBLE);
		errorEmail.setVisibility(View.INVISIBLE);

		editAvatar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(checkAndRequestPermissions(UserProfile.this)){
					chooseImage(UserProfile.this);
				}
			}
		});

		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(getApplicationContext(), "💾 Guardado!", Toast.LENGTH_LONG).show();
			}
		});

		userName.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				checkName();
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
				checkName();
			}
		});

		userEmail.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				checkEmail();
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
				checkEmail();
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
				if (ContextCompat.checkSelfPermission(UserProfile.this,
						Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
					Toast.makeText(getApplicationContext(),
							"FlagUp Requires Access to Camara.", Toast.LENGTH_SHORT)
							.show();
				} else if (ContextCompat.checkSelfPermission(UserProfile.this,
						Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
					Toast.makeText(getApplicationContext(),
							"FlagUp Requires Access to Your Storage.",
							Toast.LENGTH_SHORT).show();
				} else {
					chooseImage(UserProfile.this);
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
						avatarImage.setImageBitmap(selectedImage);
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
								avatarImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
								cursor.close();
							}
						}
					}
					break;
			}
		}
	}

	private void enabledButton(){
		String textName = userName.getText().toString();
		String textEmail = userEmail.getText().toString();

		if( (textName.compareTo(EMPTY) != 0) && (textEmail.compareTo(EMPTY) != 0)){
			save.setEnabled(true);
		} else {
			save.setEnabled(false);
		}
	}

	private boolean isEmail(String email) {
		String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
		return email.matches(emailPattern);
	}

	private void checkEmail(){
		//check email
		String s_email = userEmail.getText().toString().trim();
		if(!isEmail(s_email)){
			errorEmail.setVisibility(View.VISIBLE);
			errorEmail.setText("Debe ser un email con formato válido!");
		} else {
			errorEmail.setVisibility(View.INVISIBLE);
			enabledButton();
		}

	}

	private void checkName(){
		String s_name = userName.getText().toString();
		if(s_name.length() > 5){
			enabledButton();
		} else {
			errorUser.setVisibility(View.VISIBLE);
			errorUser.setText("El nombre debe tener minimo 5 caracteres.");
		}
	}
}