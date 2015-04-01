/*Final Project
 * 
 * MapActivity.java
 * 
 * Akshay Pandian
 * Swathi Balasubramanya Ayas
 */
package com.example.locals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
/*
 * Activity to show the location of the event on map and draws the route from user's current location
 * to the said destination.
 */
public class MapActivity extends Activity {
	String destLat;
	String destLong;
	LocationManager locManager;
	String provider;
	Criteria criteria;
	GoogleMap gMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		gMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		destLat = getIntent().getExtras().getString("Latitude");
		destLong = getIntent().getExtras().getString("Longitude");
		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		criteria = new Criteria();
		criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		criteria.setSpeedRequired(true);
		criteria.setBearingRequired(true);
		provider = locManager.getBestProvider(criteria, true);
		final Location location = locManager.getLastKnownLocation(provider);

		LatLngBounds.Builder markerPoints;
		markerPoints = new LatLngBounds.Builder();
		markerPoints.include(new LatLng(Double.parseDouble(destLat), Double
				.parseDouble(destLong)));
		gMap.addMarker(new MarkerOptions().position(new LatLng(Double
				.parseDouble(destLat), Double.parseDouble(destLong))));
		if (location != null) {
			markerPoints.include(new LatLng(location.getLatitude(), location
					.getLongitude()));
			gMap.addMarker(new MarkerOptions().position(new LatLng(location
					.getLatitude(), location.getLongitude())));
		}
		final LatLngBounds bounds = markerPoints.build();
		gMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {

			@Override
			public void onMapLoaded() {
				gMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds,
						150));
				String mapURL = makeURL(location.getLatitude(),
						location.getLongitude(), Double.parseDouble(destLat),
						Double.parseDouble(destLong));
				// JSONParser jParser = new JSONParser();
				// String json = jParser.getJSONFromUrl(mapURL);
				// drawPath(json);
				new connectAsyncTask(mapURL).execute();
			}
		});

	}

	/*
	 * Function which constructs the required URL to download data from Google Maps API
	 */
	public String makeURL(double sourcelat, double sourcelog, double destlat,
			double destlog) {
		StringBuilder urlString = new StringBuilder();
		urlString.append("http://maps.googleapis.com/maps/api/directions/json");
		urlString.append("?origin=");// from
		urlString.append(Double.toString(sourcelat));
		urlString.append(",");
		urlString.append(Double.toString(sourcelog));
		urlString.append("&destination=");// to
		urlString.append(Double.toString(destlat));
		urlString.append(",");
		urlString.append(Double.toString(destlog));
		urlString.append("&sensor=false&mode=driving&alternatives=true");
		return urlString.toString();
	}
	
	/*
	 * Async task to download location data and pass it onto a Utility class to parse JSON
	 * string and store it in appropriate objects
	 */
	public class JSONParser {

		InputStream is = null;
		final JSONObject jObj = null;
		String json = "";

		// constructor
		public JSONParser() {
		}

		public String getJSONFromUrl(String url) throws MalformedURLException {

			URL urlMap = new URL(url);
			HttpURLConnection con;
			try {
				con = (HttpURLConnection) urlMap.openConnection();
				con.setRequestMethod("GET");
				con.connect();

				int statusCode = con.getResponseCode();
				if (statusCode == HttpURLConnection.HTTP_OK) {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(con.getInputStream()));
					StringBuilder sb = new StringBuilder();
					String line = reader.readLine();
					while (line != null) {
						sb.append(line);
						line = reader.readLine();
					}
					json = sb.toString();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return json;
		}
	}
	
	/*
	 * Function to draw the route from user's current location to the event's location
	 */

	public void drawPath(String result) {

		try {
			// Tranform the string into a json object
			final JSONObject json = new JSONObject(result);
			JSONArray routeArray = json.getJSONArray("routes");
			JSONObject routes = routeArray.getJSONObject(0);
			JSONObject overviewPolylines = routes
					.getJSONObject("overview_polyline");
			String encodedString = overviewPolylines.getString("points");
			List<LatLng> list = decodePoly(encodedString);

			for (int z = 0; z < list.size() - 1; z++) {
				LatLng src = list.get(z);
				LatLng dest = list.get(z + 1);
				Polyline line = gMap.addPolyline(new PolylineOptions()
						.add(new LatLng(src.latitude, src.longitude),
								new LatLng(dest.latitude, dest.longitude))
						.width(2).color(Color.BLUE).width(3));
			}

		} catch (JSONException e) {

		}
	}

	private List<LatLng> decodePoly(String encoded) {

		List<LatLng> poly = new ArrayList<LatLng>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;

		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;

			LatLng p = new LatLng((((double) lat / 1E5)),
					(((double) lng / 1E5)));
			poly.add(p);
		}

		return poly;
	}

	private class connectAsyncTask extends AsyncTask<Void, Void, String> {
		private ProgressDialog progressDialog;
		String url;

		connectAsyncTask(String urlPass) {
			url = urlPass;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(MapActivity.this);
			progressDialog.setMessage("Fetching route, Please wait...");
			progressDialog.setIndeterminate(true);
			progressDialog.show();
		}

		@Override
		protected String doInBackground(Void... params) {
			JSONParser jParser = new JSONParser();
			String json = null;
			try {
				json = jParser.getJSONFromUrl(url);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return json;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			progressDialog.hide();
			if (result != null) {
				drawPath(result);
			}
		}
	}
}
