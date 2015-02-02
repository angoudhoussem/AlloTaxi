package com.ISITCOM.allotaxii.Menu;

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
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.ISITCOM.allotaxii.R;
import com.ISITCOM.allotaxii.Bean.Position;
import com.ISITCOM.allotaxii.Map.DirectionsJSONParser;
import com.ISITCOM.allotaxii.Map.GPSTracker;
import com.ISITCOM.allotaxii.Service.Constant;
import com.ISITCOM.allotaxii.Service.ServiceRetrofit;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class Map extends Fragment {

	private GoogleMap Gmap;
	private static LatLng Myposition, position;
	Marker marker, myplace;
	ArrayList<LatLng> markerPoints;

	private Double latitude;
	private Double longitude;
	private Double t1, t2;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragmentmap, null, false);
		Gmap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		Gmap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		markerPoints = new ArrayList<LatLng>();
		GPSTracker gps = new GPSTracker(getActivity());
		// check if GPS enabled
		if (gps.canGetLocation()) {
			latitude = gps.getLatitude();
			longitude = gps.getLongitude();
			System.out.println("latitude" + latitude);
			System.out.println("longitude" + longitude);
		}
	
		Myposition = new LatLng(latitude, longitude);
		myplace = Gmap.addMarker(new MarkerOptions().position(Myposition)
				.title("My Location").snippet("Ma position")
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map)));
		Gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(Myposition, 35));
		Gmap.animateCamera(CameraUpdateFactory.zoomTo(12), 2000, null);
		myplace.showInfoWindow();
		markerPoints.add(Myposition);

		RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(
				Constant.URL + "/entity.position").build();
		System.out.println("url" + Constant.URL + "/entity.position");
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
					t1 = Double.parseDouble(ListMgs.get(i).getLatitude());
					System.out.println("Position1" + t1);
					t2 = Double.parseDouble(ListMgs.get(i).getLangitude());
					System.out.println("Position2" + t2);
					position = new LatLng(t1, t2);
					
					markerPoints.add(position);

					marker = Gmap.addMarker(new MarkerOptions()
							.position(position)
							.title("Taxi")
							.snippet("Taxi")
							.icon(BitmapDescriptorFactory
									.fromResource(R.drawable.ic_taximap)));
					marker.showInfoWindow();
					if (markerPoints.size() >= 2) {

						String url = getDirectionsUrl(position, Myposition);

						DownloadTask downloadTask = new DownloadTask();

						// Start downloading json data from Google Directions API
						downloadTask.execute(url);
					} else {
						
						Toast.makeText(getActivity(), "Une seul Position", Toast.LENGTH_LONG).show();
					}
				}

			}

		});

			

		
		return v;
	}

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
				Toast.makeText(getActivity(), "No Points", Toast.LENGTH_SHORT)
						.show();
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
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("Taxi");
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
			Gmap.addPolyline(lineOptions);
		}
	}

	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	// switch (keyCode) {
	// case KeyEvent.KEYCODE_BACK:
	// startActivity(new Intent(getActivity(),
	// MainActivity.class));
	//
	// }
	//
	// return super.onKeyDown(keyCode, event);
	// }
}
