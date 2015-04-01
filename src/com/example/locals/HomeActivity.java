/*Final Project
 * 
 * HomeActivity.java
 * 
 * Akshay Pandian
 * Swathi Balasubramanya Ayas
 */
package com.example.locals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/*
 * The main home activity where the user can either type in his location or get location
 * phone data. The location of phone is retrieved as a passive as well as active service,
 * preference given to passive first
 */
public class HomeActivity extends Activity {
	EditText location;
	TextView search, friends;
	Button locButton;
	LocationManager locManager;
	Location mLocation;
	LocationListener mListener;
	Criteria criteria;
	String provider;
	String locationName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		location = (EditText) findViewById(R.id.editText_location);
		search = (TextView) findViewById(R.id.textView_search);
		friends = (TextView) findViewById(R.id.textView_friends);
		locButton = (Button) findViewById(R.id.button_location);
		ParseUser user = ParseUser.getCurrentUser();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("FriendsList");
		query.whereEqualTo("Owner", user.getObjectId());
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null) {
					for (int i = 0; i < objects.size(); i++) {
						ParseInstallation installation = ParseInstallation
								.getCurrentInstallation();
						installation.addUnique("channels", objects.get(i)
								.getString("Friends"));
						installation.saveInBackground();
					}
				}
			}
		});

		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		locButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mLocation == null) {
					Toast.makeText(HomeActivity.this,
							"Waiting for location...", Toast.LENGTH_SHORT)
							.show();
				} else {
					if (Geocoder.isPresent()) {
						new GeoTask(HomeActivity.this).execute();
					} else {
						Toast.makeText(HomeActivity.this, "No geocoder!",
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		search.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (location.getEditableText().toString().isEmpty()) {
					Toast.makeText(
							HomeActivity.this,
							"Enter location or click on the location button provided!",
							Toast.LENGTH_SHORT).show();
				} else {
					Intent intent = new Intent(HomeActivity.this,
							EventActivity.class);
					String locationString = location.getEditableText()
							.toString().replace("\\s+", "");
					intent.putExtra("Location", locationString);
					startActivity(intent);
				}
			}
		});

		friends.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this,
						FriendsActivity.class);
				startActivity(intent);
			}
		});

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.menu_logout) {
			invalidateOptionsMenu();
			ParseUser user = ParseUser.getCurrentUser();
			ParseQuery<ParseObject> query = ParseQuery.getQuery("FriendsList");
			query.whereEqualTo("Owner", user.getObjectId());
			query.findInBackground(new FindCallback<ParseObject>() {

				@Override
				public void done(List<ParseObject> objects, ParseException e) {
					List<String> list = new ArrayList<String>();
					for (int i = 0; i < objects.size(); i++) {
						list.add(objects.get(i).getString("Friends"));
					}
					ParseInstallation installation = ParseInstallation
							.getCurrentInstallation();
					installation.removeAll("channels", list);
					installation.saveInBackground();
				}
			});
			ParseUser.logOut();
			Intent intent = new Intent(HomeActivity.this, MainActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_action, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			AlertDialog.Builder b = new AlertDialog.Builder(this);
			b.setTitle("GPS Not Enabled!")
					.setMessage("Would you like to enable GPS settings?")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Intent intent = new Intent(
											Settings.ACTION_LOCATION_SOURCE_SETTINGS);
									startActivity(intent);
								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
									finish();
								}
							});
			AlertDialog alert = b.create();
			alert.show();
		} else {
			criteria = new Criteria();
			criteria.setAccuracy(Criteria.ACCURACY_COARSE);
			criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
			criteria.setAltitudeRequired(true);
			criteria.setBearingRequired(true);
			provider = locManager.getBestProvider(criteria, true);
			mListener = new LocationListener() {

				@Override
				public void onStatusChanged(String provider, int status,
						Bundle extras) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onProviderEnabled(String provider) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onProviderDisabled(String provider) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onLocationChanged(Location location) {
					mLocation = location;
				}
			};
			locManager
					.requestLocationUpdates(provider, 10000, 10000, mListener);
		}
	}

	public class GeoTask extends AsyncTask<Void, Void, List<Address>> {
		Context mContext;

		public GeoTask(Context mContext) {
			this.mContext = mContext;
		}

		@Override
		protected List<Address> doInBackground(Void... params) {
			List<Address> addressList = null;
			Geocoder geoCoder = new Geocoder(mContext);
			try {
				addressList = geoCoder.getFromLocation(mLocation.getLatitude(),
						mLocation.getLongitude(), 1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return addressList;
		}

		@Override
		protected void onPostExecute(List<Address> result) {
			if (result == null) {
				Toast.makeText(HomeActivity.this,
						"Could not get location information!",
						Toast.LENGTH_SHORT).show();
			} else {
				location.setText(result.get(0).getLocality().toString());
				locationName = result.get(0).getLocality().toString();
			}
		}

	}
}
