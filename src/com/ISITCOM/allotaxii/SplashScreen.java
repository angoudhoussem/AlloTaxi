package com.ISITCOM.allotaxii;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {

	protected boolean _active = true;
	protected int _splashTime = 6000;
	// private ProgressBar progress;
	private int progressStatus = 0;
	private Handler handler = new Handler();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		// progress = (ProgressBar) findViewById(R.id.progressBar1);

		new Thread(new Runnable() {
			public void run() {
				while (progressStatus < 100) {
					progressStatus += 5;
					// Update the progress bar and display the
					// current value in the text view
					handler.post(new Runnable() {
						public void run() {
							// progress.setProgress(progressStatus);
						}
					});
					try {
						// Sleep for 200 milliseconds.
						// Just to display the progress slowly
						Thread.sleep(200);

					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
				Intent i = new Intent(SplashScreen.this, Authentification.class);
				startActivity(i);
				finish();
			}
		}).start();

		//

	}

	@Override
	protected void onDestroy() {

		super.onDestroy();

	}
}
