package com.ISITCOM.allotaxii;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ISITCOM.allotaxii.Bean.Client;
import com.ISITCOM.allotaxii.Service.Constant;
import com.ISITCOM.allotaxii.Service.ServiceRetrofit;

public class Inscription extends Activity {

	EditText nom, prenom, email, password, tel, conf, adresse;
	String Nom, Prenom, Email, Password, Tel, Conf, Adresse;
	Button Valider, Annuler;
	int time = 5000;
	TextView t;
	String result = null;
	ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inscription);
		// appel les elements dans layout //
		Valider = (Button) findViewById(R.id.button1);
		Annuler = (Button) findViewById(R.id.button2);

		nom = (EditText) findViewById(R.id.id_nom);
		prenom = (EditText) findViewById(R.id.id_prenom);
		email = (EditText) findViewById(R.id.id_emaiil);
		password = (EditText) findViewById(R.id.id_passwordd);
		tel = (EditText) findViewById(R.id.id_tell);
		conf = (EditText) findViewById(R.id.id_conff);
		adresse = (EditText) findViewById(R.id.editText1);
		t = (TextView) findViewById(R.id.textView1);

		Valider.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Nom = nom.getText().toString();
				Prenom = prenom.getText().toString();
				Email = email.getText().toString();
				Password = password.getText().toString();
				Tel = tel.getText().toString();
				Conf = conf.getText().toString();
				Adresse = adresse.getText().toString();
				if (isValidEmail(Email)) {

					if (isValidPassword(Password)) {

						if (Password.equals(Conf)) {

							Client c = new Client(Adresse, Email, Password,
									Nom, Prenom, Tel);
							Toast.makeText(getApplicationContext(), "oui",
									Toast.LENGTH_LONG).show();

							t.setText("Le meme");

							RestAdapter restAdapter = new RestAdapter.Builder()
									.setEndpoint(
											Constant.URL + "/entity.client")
									.build();
							ServiceRetrofit rest = restAdapter
									.create(ServiceRetrofit.class);
							rest.createUser(Adresse, Email, Password, Nom,
									Prenom, Tel, new Callback<String>() {

										@Override
										public void failure(RetrofitError arg0) {

											System.out.println("failure"
													+ arg0.getLocalizedMessage());

										}

										@Override
										public void success(String arg0,
												Response arg1) {

											System.out.println("success"
													+ arg0.toString());

										}
									});

						} else {

							conf.setError("Erreur");

							t.setText("n'est pas le  meme");
						}

					} else {

						password.setError("Erreur");

					}
				}

				else {

					email.setError("Erreur");
				}
			}
		});
		Annuler.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				nom.setText("");
				prenom.setText("");
				email.setText("");
				password.setText("");
				tel.setText("");
				conf.setText("");
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.page2, menu);
		return true;
	}

	// validating email id
	private boolean isValidEmail(String email) {
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	// validating password with retype password
	private boolean isValidPassword(String pass) {
		if (pass != null && pass.length() > 6) {
			return true;
		}
		return false;
	}

}
