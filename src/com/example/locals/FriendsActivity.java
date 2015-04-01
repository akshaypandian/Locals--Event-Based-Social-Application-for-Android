/*Final Project
 * 
 * FriendsActivity.java
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
/*
 * Shows all users of the application. The user can add them and 
 * socialize with them by attending events together.
 */
public class FriendsActivity extends Activity {

	ArrayList<String> list;
	List<String> names;
	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friends);

		ParseUser user = ParseUser.getCurrentUser();
		list = new ArrayList<String>();
		names = new ArrayList<String>();
		listView = (ListView) findViewById(R.id.friends_list);
		ParseQuery<ParseObject> query = ParseQuery.getQuery("FriendsList");
		Log.d("friends", user.getObjectId());
		query.whereEqualTo("Owner", user.getObjectId());

		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null) {
					Log.d("friends", objects.size() + "");
					for (int i = 0; i < objects.size(); i++) {
						list.add(objects.get(i).getString("Friends"));
					}
					Log.d("friends", list.toString());
					for (int j = 0; j < list.size(); j++) {
						ParseQuery<ParseUser> query = ParseUser.getQuery();
						query.whereEqualTo("objectId", list.get(j));
						query.findInBackground(new FindCallback<ParseUser>() {

							@Override
							public void done(List<ParseUser> objects,
									ParseException e) {
								if (e == null) {
									names.add(objects.get(0).getString("Name"));

									ArrayAdapter<String> adapter = new ArrayAdapter<String>(
											FriendsActivity.this,
											android.R.layout.simple_list_item_1,
											names);
									listView.setAdapter(adapter);
								} else {
									Toast.makeText(
											FriendsActivity.this,
											"Experiencing technical difficulties...",
											Toast.LENGTH_SHORT).show();
								}
							}
						});
					}
				} else {
					Toast.makeText(FriendsActivity.this,
							"Experiencing technical difficulties...",
							Toast.LENGTH_SHORT).show();
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
			Intent intent1 = new Intent(FriendsActivity.this,
					MainActivity.class);
			startActivity(intent1);
			break;
		case R.id.menu_favourites:
			Intent intent2 = new Intent(FriendsActivity.this,
					FavoriteActivity.class);
			startActivity(intent2);
			break;
		case R.id.menu_allUsers:
			Intent intent3 = new Intent(FriendsActivity.this,
					AllUsersActivity.class);
			startActivity(intent3);
			break;

		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_friends_activity, menu);
		return true;
	}
}
