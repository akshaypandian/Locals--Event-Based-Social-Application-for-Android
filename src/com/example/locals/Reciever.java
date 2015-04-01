/*Final Project
 * 
 * Reciever.java
 * 
 * Akshay Pandian
 * Swathi Balasubramanya Ayas
 */
package com.example.locals;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.parse.ParsePushBroadcastReceiver;
/*
 * Class which opens the push notification to view details of the event that
 * the particular friend is going to
 */
public class Reciever extends ParsePushBroadcastReceiver {
	JSONObject json;

	@Override
	protected void onPushOpen(Context context, Intent intent) {
		Log.e("Push", "Clicked");
		Intent i = new Intent(context, EventsDetailedActivity.class);
		try {
			json = new JSONObject(intent.getExtras()
					.getString("com.parse.Data"));
			Log.d("pushmsg", json.toString());
			i.putExtra("Latitude", json.getString("latitude"));
			i.putExtra("Longitude", json.getString("longitude"));
			i.putExtra("Title", json.getString("title"));
			i.putExtra("Description", json.getString("description"));
			i.putExtra("VenueAddress", json.getString("venueaddress"));
			i.putExtra("VenueName", json.getString("venuename"));
			i.putExtra("Date", json.getString("date"));
			i.putExtra("Performers", json.getString("performers"));
			i.putExtra("City", json.getString("city"));
			i.putExtra("URL", json.getString("url"));
			i.putExtra("Image", json.getString("image"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
	}
}
