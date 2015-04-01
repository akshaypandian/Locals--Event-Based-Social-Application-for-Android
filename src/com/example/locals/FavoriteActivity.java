/*Final Project
 * 
 * FavoriteActivity.java
 * 
 * Akshay Pandian
 * Swathi Balasubramanya Ayas
 */
package com.example.locals;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
/*
 * Activity to display all events that the user has saved into his
 * favorite's list. This way he can keep track of the events he 
 * wants to attend and one's he is looking forward to
 */
public class FavoriteActivity extends Activity {
	ListView list;
	ArrayList<Events> favlist;
	FavoriteAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorite);
		list = (ListView) findViewById(R.id.fav_list);
		ParseUser user = ParseUser.getCurrentUser();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("FavoriteEvents");
		query.whereEqualTo("Owner", user.getObjectId());
		favlist = new ArrayList<Events>();
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null) {
					for (int i = 0; i < objects.size(); i++) {
						favlist.add(new Events(objects.get(i).getString(
								"Latitude"), objects.get(i).getString(
								"Longitude"),
								objects.get(i).getString("Title"), objects.get(
										i).getString("VenueName"), objects.get(
										i).getString("Date"), objects.get(i)
										.getString("VenueAddress"), objects
										.get(i).getString("Performers"),
								objects.get(i).getString("Description"),
								objects.get(i).getString("City"), null, objects
										.get(i).getString("URL"), objects
										.get(i).getString("Image")));
					}
					adapter = new FavoriteAdapter(FavoriteActivity.this,
							R.layout.fav_row_layout, favlist);
					list.setAdapter(adapter);
					list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							Intent intent = new Intent(FavoriteActivity.this,
									EventsDetailedActivity.class);
							intent.putExtra("Latitude", favlist.get(position)
									.getLatitude());
							intent.putExtra("Longitude", favlist.get(position)
									.getLongitude());
							intent.putExtra("Title", favlist.get(position)
									.getTitle());
							intent.putExtra("VenueAddress",
									favlist.get(position).getVenueAddress());
							intent.putExtra("VenueName", favlist.get(position)
									.getVenueName());
							intent.putExtra("Date", favlist.get(position)
									.getDate());
							intent.putExtra("Performers", favlist.get(position)
									.getPerformers());
							intent.putExtra("Description", favlist
									.get(position).getDescription());
							intent.putExtra("City", favlist.get(position)
									.getCity());
							intent.putExtra("URL", favlist.get(position)
									.getUrl());
							intent.putExtra("Image", favlist.get(position)
									.getImage());

							startActivity(intent);

						}

					});
				} else {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.menu_logout:
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
			invalidateOptionsMenu();
			Intent intent1 = new Intent(FavoriteActivity.this,
					MainActivity.class);
			startActivity(intent1);
			break;

		case R.id.menudelete_all: {
			ParseUser userDel = ParseUser.getCurrentUser();
			ParseQuery<ParseObject> queryDel = ParseQuery
					.getQuery("FavoriteEvents");
			queryDel.whereEqualTo("Owner", userDel.getObjectId());

			queryDel.findInBackground(new FindCallback<ParseObject>() {

				@Override
				public void done(List<ParseObject> objects, ParseException e) {
					if (e == null) {
						for (int i = 0; i < objects.size(); i++) {
							objects.get(i).deleteInBackground();
						}
					} else {
						e.printStackTrace();

					}
				}
			});
			for (int j = 0; j < favlist.size(); j++) {
				favlist.remove(j);
			}
			adapter.clear();

			break;
		}

		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_favs_activity, menu);
		return true;
	}

}
