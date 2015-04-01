/*Final Project
 * 
 * AllUsersActivity.java
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
import android.widget.ListView;

/*
 * Activity to set up user's friends and show it in listview.
 */
public class AllUsersActivity extends Activity {

	List<FriendsClass> userList;
	ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_users);
		userList = new ArrayList<FriendsClass>();
		final ParseUser user = ParseUser.getCurrentUser();
		list = (ListView) findViewById(R.id.list_alluser);
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.whereNotEqualTo("objectId", user.getObjectId());
		query.findInBackground(new FindCallback<ParseUser>() {

			@Override
			public void done(List<ParseUser> objects, ParseException e) {
				if (e == null) {
					for (int i = 0; i < objects.size(); i++) {
						userList.add(new FriendsClass(objects.get(i).getString(
								"Name"), objects.get(i).getObjectId()));

						// AllUserAdapter adapter = new AllUserAdapter(
						// AllUsersActivity.this,
						// R.layout.alluser_row_layout, userList);
						// list.setAdapter(adapter);
					}
					ParseQuery<ParseObject> query = ParseQuery
							.getQuery("FriendsList");
					query.whereEqualTo("Owner", user.getObjectId());
					query.findInBackground(new FindCallback<ParseObject>() {

						@Override
						public void done(List<ParseObject> objects,
								ParseException e) {
							for (int i = 0; i < objects.size(); i++) {
								userList.remove(objects.get(i).getString(
										"Friends"));
								AllUserAdapter adapter = new AllUserAdapter(
										AllUsersActivity.this,
										R.layout.alluser_row_layout, userList);
								list.setAdapter(adapter);
							}
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
			Intent intent1 = new Intent(AllUsersActivity.this,
					MainActivity.class);
			startActivity(intent1);
			break;
		case R.id.menu_favourites:
			Intent intent2 = new Intent(AllUsersActivity.this,
					FavoriteActivity.class);
			startActivity(intent2);
			break;

		case R.id.menu_friends:
			Intent intent4 = new Intent(AllUsersActivity.this,
					FriendsActivity.class);
			startActivity(intent4);
			break;

		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_allusers_activity, menu);
		return true;
	}

}
