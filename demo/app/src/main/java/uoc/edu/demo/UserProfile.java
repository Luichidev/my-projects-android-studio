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
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class UserProfile extends AppCompatActivity {
	public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
	public static final String EDIT_MODE = "USER_PROFILE_EDIT_MODE";
	public static final String NEW_USER = "USER_PROFILE_NEW_USER";
	public static final String USER_ID = "USER_ID";
	private final int INFO_USER_NAME = 0;
	private final int INFO_USER_EMAIL = 1;
	private final int INFO_USER_PICTURE = 2;
	private static final String KEY_INFO_USER_NAME = "INFO_USER_NAME";
	private static final String KEY_INFO_USER_EMAIL = "INFO_USER_EMAIL";
	private static final String KEY_INFO_USER_PICTURE = "INFO_USER_PICTURE";
	private static final String ID_USER_PROFILE = "ID_USER_PROFILE";
	private String pictureAux = null;
	private ImageView userAvatar;
	private ImageView editAvatar;
	private EditText userName;
	private EditText userEmail;
	private TextView errorUser;
	private TextView errorEmail;
	private Button btn_save;
	private final String EMPTY = "";
	private Integer id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);
		Intent intent = getIntent();
		Boolean editMode = intent.getBooleanExtra(EDIT_MODE, false);
		Boolean newUser = intent.getBooleanExtra(NEW_USER, false);
		id = intent.getIntExtra(USER_ID, 0);
		ImageView avatar = findViewById(R.id.userAvatar);
		avatar.setImageResource(R.drawable.no_avatar);
		editAvatar = findViewById(R.id.editAvatar);
		userAvatar = findViewById(R.id.userAvatar);
		btn_save = findViewById(R.id.btn_save);


		userName = findViewById(R.id.editUserName);
		userEmail = findViewById(R.id.editEmail);
		errorUser = findViewById(R.id.errorUser);
		errorEmail = findViewById(R.id.errorEmail);
		errorUser.setVisibility(View.INVISIBLE);
		errorEmail.setVisibility(View.INVISIBLE);

		setEditMode(editMode || newUser);

		if (!newUser){
			//get data from info provider (webservice, from disk)...
			String[] userInfo = new String[3];

			getUserInfo(userInfo);
			setInfoOnWidgets(userInfo);
		}
		editAvatar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(checkAndRequestPermissions(UserProfile.this)){
					chooseImage(UserProfile.this);
				}
			}
		});

		btn_save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String finalFile = ID_USER_PROFILE + "_" + id;
				SharedPreferences pref = getSharedPreferences(finalFile, Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = pref.edit();
				editor.putString(KEY_INFO_USER_NAME, userName.getText().toString());
				editor.putString(KEY_INFO_USER_EMAIL, userEmail.getText().toString());

				//Convert from bitmap to base64 string
				editor.putString(KEY_INFO_USER_PICTURE, pictureAux);

				editor.commit();
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

	private void setEditMode(boolean editMode){
		if (!editMode){
			editAvatar.setVisibility(View.INVISIBLE);
			btn_save.setVisibility(View.INVISIBLE);
			userName.setEnabled(false);
			userEmail.setEnabled(false);
		}else{
			editAvatar.setVisibility(View.VISIBLE);
			btn_save.setVisibility(View.VISIBLE);
			userName.setEnabled(true);
			userEmail.setEnabled(true);
		}
	}

	private void getUserInfo(String[] userInfo){
		String finalFile = ID_USER_PROFILE + "_" + id;
		SharedPreferences pref = getSharedPreferences(finalFile, Context.MODE_PRIVATE);
		userInfo[INFO_USER_NAME] = pref.getString(KEY_INFO_USER_NAME,"");
		userInfo[INFO_USER_EMAIL] = pref.getString(KEY_INFO_USER_EMAIL, "");
		userInfo[INFO_USER_PICTURE] = pref.getString(KEY_INFO_USER_PICTURE, "");
	}
	private void setInfoOnWidgets(String[] userInfo) {
		userName.setText(userInfo[INFO_USER_NAME]);
		userEmail.setText(userInfo[INFO_USER_EMAIL]);
		if(userInfo[INFO_USER_PICTURE].compareTo(EMPTY) != 0){
			byte[] decodedString = Base64.decode(userInfo[INFO_USER_PICTURE], Base64.DEFAULT);
			Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,decodedString.length);
			userAvatar.setImageBitmap(decodedByte);
		}
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
						userAvatar.setImageBitmap(selectedImage);
						ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
						selectedImage.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
						byte[] byteArray = byteArrayOutputStream .toByteArray();
						String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
						pictureAux = encoded;
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
								userAvatar.setImageBitmap(BitmapFactory.decodeFile(picturePath));

								ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
								BitmapFactory.decodeFile(picturePath).compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
								byte[] byteArray = byteArrayOutputStream .toByteArray();
								String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
								pictureAux = encoded;
								cursor.close();
							}
						}
					}
					break;
			}
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
		}

	}

	private void checkName(){
		String s_name = userName.getText().toString();
		if(s_name.length() < 5){
			errorUser.setVisibility(View.VISIBLE);
			errorUser.setText("El nombre debe tener minimo 5 caracteres.");
		} else {
			errorUser.setVisibility(View.INVISIBLE);
		}
	}
}