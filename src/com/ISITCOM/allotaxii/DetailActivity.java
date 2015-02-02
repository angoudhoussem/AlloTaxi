package com.ISITCOM.allotaxii;

import com.ISITCOM.allotaxii.Bean.Taxi;
import com.ISITCOM.allotaxii.Service.Constant;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class DetailActivity extends Activity {

	Taxi txi = Constant.t;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		 TextView numv=(TextView)findViewById(R.id.textView2);
		 TextView tel=(TextView)findViewById(R.id.textView4);
		
		 numv.setText(txi.getNumerovehicule());
		 //tel.setText(txi.get);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail, menu);
		return true;
	}

}
