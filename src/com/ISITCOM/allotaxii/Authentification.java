package com.ISITCOM.allotaxii;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ISITCOM.allotaxii.Menu.MainActivity;
import com.ISITCOM.allotaxii.Service.Constant;
import com.ISITCOM.allotaxii.Service.ServiceRetrofit;

public class Authentification extends Activity {

	EditText login, password;
	TextView text;
	String Login, Password;
	Button valider, ins;
	private RadioGroup radioSexGroup;
	private RadioButton radioSexButton;
	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		inisialization();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void inisialization() {

		login = (EditText) findViewById(R.id.editText1);
		password = (EditText) findViewById(R.id.editText2);
		valider = (Button) findViewById(R.id.button1);
		text = (TextView) findViewById(R.id.textView1);
		ins = (Button) findViewById(R.id.button2);

		radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
		radioSexGroup.clearCheck();

		addListenerOnButton();

		ins.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				vider();

			}
		});
		text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent ii = new Intent(getApplicationContext(),
						Inscription.class);
				startActivity(ii);
				Toast.makeText(getApplicationContext(), "hello2",
						Toast.LENGTH_LONG).show();

			}
		});

	}

	private void vider() {

		login.setText("");
		password.setText("");

	}

	public void addListenerOnButton() {

		radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);

		valider.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Login = login.getText().toString();
				Password = password.getText().toString();
				Constant.Email = Login;
				Constant.Password = Password;
				int selectedId = radioSexGroup.getCheckedRadioButtonId();
				if (selectedId != -1) {
					System.out.println("selectedId" + selectedId);
					// find the radiobutton by returned id
					radioSexButton = (RadioButton) findViewById(selectedId);
					System.out.println("radioSexButton" + radioSexButton);
					switch (radioSexButton.getId()) {
					case R.id.id_client:

						if (Login.length() != 0 && Password.length() != 0) {

							RestAdapter restAdapter = new RestAdapter.Builder()
									.setEndpoint(
											Constant.URL + "/entity.client")
									.build();
							ServiceRetrofit rest = restAdapter
									.create(ServiceRetrofit.class);
							rest.authentification(Login, Password,
									new Callback<String>() {

										@Override
										public void failure(RetrofitError arg0) {

											System.out.println("failure"
													+ arg0.getLocalizedMessage());

											Toast.makeText(
													getApplicationContext(),
													" les Champs  Invalid",
													Toast.LENGTH_LONG).show();

										}

										@Override
										public void success(String arg0,
												Response arg1) {

											System.out.println("success"
													+ arg0.toString());
											if (arg0.toString().equals("yes")) {
												Intent i = new Intent(
														getApplicationContext(),
														MainActivity.class);
												startActivity(i);

											} else {

												Toast.makeText(
														getApplicationContext(),
														"Erreur",
														Toast.LENGTH_SHORT)
														.show();
											}
										}
									});

						} else {

							login.setError("");
							password.setError("");
						}

						break;

					case R.id.id_admin:

						if (Login.length() != 0 && Password.length() != 0) {

							Toast.makeText(getApplicationContext(),
									radioSexButton.getText().toString(),
									Toast.LENGTH_SHORT).show();
						} else {

							login.setError("");
							password.setError("");

						}
						break;

					default:
						break;
					}
				} else {

					alert.showAlertDialog(Authentification.this,
							"Choisir Client ou Administrateur",
							"Vous n'avez pas choisir  ", false);
				}

			}

		});

	}
}
