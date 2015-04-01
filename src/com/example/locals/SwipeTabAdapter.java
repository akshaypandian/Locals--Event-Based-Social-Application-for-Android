/*Final Project
 * 
 * SwipeTabAdapter.java
 * 
 * Akshay Pandian
 * Swathi Balasubramanya Ayas
 */
package com.example.locals;

/*
 * Adapter to support swipe actions to change fragments in EventActivity
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SwipeTabAdapter extends FragmentPagerAdapter {

	String locationName;
	public SwipeTabAdapter(FragmentManager fm, String locationName) {
		super(fm);
		this.locationName = locationName;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = new Fragment();
		switch (position) {
		case 0:
			fragment = new TodayFragment(locationName);
			break;
		case 1:
			fragment = new UpcomingFragment(locationName);
			break;
		case 2:
			fragment = new CategoryFragment(locationName);
			break;
		}
		return fragment;
	}

	@Override
	public int getCount() {
		return 3;
	}
}
