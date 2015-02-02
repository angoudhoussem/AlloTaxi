package com.ISITCOM.allotaxii.Map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.ISITCOM.allotaxii.R;
import com.ISITCOM.allotaxii.Adapter.CustomAdapter;
import com.ISITCOM.allotaxii.Bean.Position;
import com.ISITCOM.allotaxii.Bean.Taxi;
import com.ISITCOM.allotaxii.Service.Constant;
import com.ISITCOM.allotaxii.Service.ServiceRetrofit;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends FragmentActivity {
	private static LatLng Myposition, position;
	Marker marker, myplace;
	GoogleMap map;
	ArrayList<LatLng> markerPoints;

	private Double latitude;
	private Double longitude;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		markerPoints = new ArrayList<LatLng>();

		SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);

		map = fm.getMap();
		map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		GPSTracker gps = new GPSTracker(MainActivity.this);
		// check if GPS enabled
		if (gps.canGetLocation()) {
			latitude = gps.getLatitude();
			longitude = gps.getLongitude();
			System.out.println("latitude" + latitude);
			System.out.println("longitude" + longitude);
		}
		System.out.println("latitude" + latitude);
		System.out.println("longitude" + longitude);
		Myposition = new LatLng(latitude, longitude);
		myplace = map.addMarker(new MarkerOptions().position(Myposition)
				.title("My Location").snippet("Ma position")
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map)));
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(Myposition, 35));
		map.animateCamera(CameraUpdateFactory.zoomTo(12), 2000, null);
		myplace.showInfoWindow();
		markerPoints.add(Myposition);

		RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(
				Constant.URL + "/entity.position").build();
		final ServiceRetrofit rest = restAdapter.create(ServiceRetrofit.class);

		rest.listPosition(100, 100, new Callback<List<Position>>() {

			@Override
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				System.out.println("failer" + arg0.getLocalizedMessage());
			}

			@Override
			public void success(List<Position> ListMgs, Response arg1) {
				// TODO Auto-generated method stub
				System.out.println("successPosition");

				System.out.println("list" + ListMgs.size());
				for (int i = 0; i < ListMgs.size(); i++) {
					System.out.println("getLatitude"
							+ ListMgs.get(i).getLatitude());
					System.out.println("getLangitude"
							+ ListMgs.get(i).getLangitude());
					double t1 = Double.parseDouble(ListMgs.get(i)
							.getLangitude());
					System.out.println("Position1" + t1);
					double t2 = Double.parseDouble(ListMgs.get(i)
							.getLangitude());
					System.out.println("Position2" + t2);
					position = new LatLng(t1, t2);
				}

			}

		});

		marker = map.addMarker(new MarkerOptions()
				.position(position)
				.title("Taxi")
				.snippet("Sousse")
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.ic_taximap)));
		marker.showInfoWindow();
		markerPoints.add(position);

		if (map != null) {

			// Enable MyLocation Button in the Map
			map.setMyLocationEnabled(true);

			// Checks, whether start and end locations are captured
			if (markerPoints.size() >= 2) {

				String url = getDirectionsUrl(position, Myposition);

				DownloadTask downloadTask = new DownloadTask();

				// Start downloading json data from Google Directions API
				downloadTask.execute(url);
			}

		}
		// });
	}

	// }

	private String getDirectionsUrl(LatLng origin, LatLng dest) {

		// Origin of route
		String str_origin = "origin=" + origin.latitude + ","
				+ origin.longitude;

		// Destination of route
		String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

		// Sensor enabled
		String sensor = "sensor=false";

		// Building the parameters to the web service
		String parameters = str_origin + "&" + str_dest + "&" + sensor;

		// Output format
		String output = "json";

		// Building the url to the web service
		String url = "https://maps.googleapis.com/maps/api/directions/"
				+ output + "?" + parameters;

		return url;
	}

	/** A method to download json data from url */
	private String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);

			// Creating an http connection to communicate with url
			urlConnection = (HttpURLConnection) url.openConnection();

			// Connecting to url
			urlConnection.connect();

			// Reading data from url
			iStream = urlConnection.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream));

			StringBuffer sb = new StringBuffer();

			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			data = sb.toString();

			br.close();

		} catch (Exception e) {
			Log.d("Exception while downloading url", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}
		return data;
	}

	// Fetches data from url passed
	private class DownloadTask extends AsyncTask<String, Void, String> {

		// Downloading data in non-ui thread
		@Override
		protected String doInBackground(String... url) {

			// For storing data from web service
			String data = "";

			try {
				// Fetching the data from web service
				data = downloadUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		// Executes in UI thread, after the execution of
		// doInBackground()
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			ParserTask parserTask = new ParserTask();

			// Invokes the thread for parsing the JSON data
			parserTask.execute(result);

		}
	}

	/** A class to parse the Google Places in JSON format */
	private class ParserTask extends
			AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

		// Parsing the data in non-ui thread
		@Override
		protected List<List<HashMap<String, String>>> doInBackground(
				String... jsonData) {

			JSONObject jObject;
			List<List<HashMap<String, String>>> routes = null;

			try {
				jObject = new JSONObject(jsonData[0]);
				DirectionsJSONParser parser = new DirectionsJSONParser();

				// Starts parsing data
				routes = parser.parse(jObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return routes;
		}

		// Executes in UI thread, after the parsing process
		@Override
		protected void onPostExecute(List<List<HashMap<String, String>>> result) {
			ArrayList<LatLng> points = null;
			PolylineOptions lineOptions = null;
			MarkerOptions markerOptions = new MarkerOptions();
			String distance = "";
			String duration = "";

			if (result.size() < 1) {
				Toast.makeText(getBaseContext(), "No Points",
						Toast.LENGTH_SHORT).show();
				return;
			}

			// Traversing through all the routes
			for (int i = 0; i < result.size(); i++) {
				points = new ArrayList<LatLng>();
				lineOptions = new PolylineOptions();

				// Fetching i-th route
				List<HashMap<String, String>> path = result.get(i);

				// Fetching all the points in i-th route
				for (int j = 0; j < path.size(); j++) {
					HashMap<String, String> point = path.get(j);

					if (j == 0) { // Get distance from the list
						distance = (String) point.get("dis");
						System.out.println("dis" + distance);
						continue;
					} else if (j == 1) { // Get duration from the list
						duration = (String) point.get("dur");
						System.out.println("dur" + duration);
						continue;
					}

					double lat = Double.parseDouble(point.get("lat"));
					double lng = Double.parseDouble(point.get("lng"));
					LatLng position = new LatLng(lat, lng);

					points.add(position);
				}

				// Adding all the points in the route to LineOptions
				lineOptions.addAll(points);
				lineOptions.width(2);
				lineOptions.color(Color.RED);

			}

			// t.setText("Distance:" + distance + ", Duration:" + duration);
			AlertDialog.Builder builder = new AlertDialog.Builder(
					MainActivity.this);
			builder.setTitle("Magasin");
			builder.setIcon(R.drawable.ic_action_star);
			builder.setMessage("Distance:" + distance + ", Duration:"
					+ duration);

			builder.setNeutralButton("Non",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
			builder.setNegativeButton("OUI",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
			AlertDialog dialog = builder.create();
			dialog.show();
			// Drawing polyline in the Google Map for the i-th route
			map.addPolyline(lineOptions);
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			startActivity(new Intent(getApplicationContext(),
					MainActivity.class));

		}

		return super.onKeyDown(keyCode, event);
	}
}
