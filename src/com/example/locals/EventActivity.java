/*Final Project
 * 
 * EventActivity.java
 * 
 * Akshay Pandian
 * Swathi Balasubramanya Ayas
 */
package com.example.locals;

/*
 * Activity consisting of three main tabs implemented as fragments - Today, Upcoming and Categories
 * The user can see all genre of events in Today and Upcoming tabs
 * whereas in Categories, can select the category he/she wishes to view from 
 */
import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

public class EventActivity extends FragmentActivity implements TabListener {

	ActionBar actionBar;
	private String[] tabs = { "Today", "Upcoming", "Categories" };
	ViewPager viewPager;
	String locationName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event);

		locationName = getIntent().getExtras().getString("Location");
		viewPager = (ViewPager) findViewById(R.id.pager);
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
		viewPager.setAdapter(new SwipeTabAdapter(getSupportFragmentManager(),
				locationName));
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				actionBar.setSelectedNavigationItem(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		for (int i = 0; i < 3; i++) {
			actionBar.addTab(actionBar.newTab().setText(tabs[i])
					.setTabListener(this));
		}

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

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
			Intent intent1 = new Intent(EventActivity.this, MainActivity.class);
			startActivity(intent1);
			break;
		case R.id.menu_favourites:
			Intent intent2 = new Intent(EventActivity.this,
					FavoriteActivity.class);
			startActivity(intent2);
			break;
		case R.id.menu_allUsers:
			Intent intent3 = new Intent(EventActivity.this,
					AllUsersActivity.class);
			startActivity(intent3);
			break;
		case R.id.menu_friends:
			Intent intent4 = new Intent(EventActivity.this,
					FriendsActivity.class);
			startActivity(intent4);
			break;

		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_for_other_activities, menu);
		return true;
	}

}
