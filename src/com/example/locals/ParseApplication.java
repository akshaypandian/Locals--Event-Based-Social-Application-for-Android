/*Final Project
 * 
 * ParseApplication.java
 * 
 * Akshay Pandian
 * Swathi Balasubramanya Ayas
 */
package com.example.locals;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;

import android.app.Application;
/*
 * Configuration setting to use Parse.com db services
 */
public class ParseApplication extends Application {
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Parse.initialize(this, "OWQyMdwfkhEp4tlqt3XQp2AIYvP9f6ANBZex5KZP",
				"BQUaSPZ4OVHV5XllTrORwEPsM5otMA1KecwOP30h");

		ParseACL defaultACL = new ParseACL();

		// If you would like all objects to be private by default, remove this
		// line.
		
		ParseInstallation.getCurrentInstallation().saveInBackground();
		//PushService.setDefaultPushCallback(this, HomeActivity.class, R.drawable.ic_launcher);
		defaultACL.setPublicReadAccess(true);
		// ParseObject testObject = new ParseObject("TestObject");
		// testObject.put("foo", "bar");
		// testObject.saveInBackground();
		ParseACL.setDefaultACL(defaultACL, true);
	}

}
