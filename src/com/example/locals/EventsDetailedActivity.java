/*Final Project
 * 
 * EventsDetailedActivity.java
 * 
 * Akshay Pandian
 * Swathi Balasubramanya Ayas
 */
package com.example.locals;

import org.json.JSONException;
import org.json.JSONObject;

import com.parse.ParsePush;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
/*
 * Activity to display all details of the event that the user selects.
 * Has the option to purchase tickets, view the location on map, 
 * get route from his current location to the event's location. Can RSVP
 * on an event and let his friends know. This is done automatically as a push message
 * to all his friends. They can join him if they wish.
 */
public class EventsDetailedActivity extends Activity {

	String latitude;
	String longitude;
	String title;
	String description;
	String venueAddress;
	String venueName;
	String date;
	String performers;
	String city;
	String url;
	String image;

	TextView titleDetailed;
	TextView venueNm;
	TextView venueAd;
	TextView cityDetailed;
	TextView dateDetailed;
	TextView perform;
	TextView des;
	ImageView eventImage;
	ImageView map;
	ImageView ticket;
	Button going;
	TextView eventDateField;
	TextView eventsPerformerField;
	TextView eventDesField;

	@Override
	protected void onCreate(Bundle savedInstanceState)
			throws NullPointerException {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events_detailed);

		try {
			latitude = getIntent().getExtras().getString("Latitude");
			longitude = getIntent().getExtras().getString("Longitude");
			title = getIntent().getExtras().getString("Title");
			description = getIntent().getExtras().getString("Description");
			venueAddress = getIntent().getExtras().getString("VenueAddress");
			venueName = getIntent().getExtras().getString("VenueName");
			date = getIntent().getExtras().getString("Date");
			performers = getIntent().getExtras().getString("Performers");
			city = getIntent().getExtras().getString("City");
			url = getIntent().getExtras().getString("URL");
			image = getIntent().getExtras().getString("Image");
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		eventDateField = (TextView) findViewById(R.id.textView7);
		eventsPerformerField = (TextView) findViewById(R.id.textview_title);
		eventDesField = (TextView) findViewById(R.id.textView5);
		titleDetailed = (TextView) findViewById(R.id.title_detailed);
		venueNm = (TextView) findViewById(R.id.venuename_detailed);
		venueAd = (TextView) findViewById(R.id.venuaddress_detailed);
		cityDetailed = (TextView) findViewById(R.id.city_detailed);
		dateDetailed = (TextView) findViewById(R.id.date_detailed);
		perform = (TextView) findViewById(R.id.performers_detailed);
		des = (TextView) findViewById(R.id.description_detailed);

		eventImage = (ImageView) findViewById(R.id.image_detailed_event);
		map = (ImageView) findViewById(R.id.maps_detailed);
		ticket = (ImageView) findViewById(R.id.tickets_detailed);

		going = (Button) findViewById(R.id.going_detailed);

		if (title != null) {
			titleDetailed.setText(title);
		}
		if (venueName != null) {
			venueNm.setText(venueName);
		}
		if (venueAddress != null) {
			venueAd.setText(venueAddress);
		}
		if (city != null) {
			cityDetailed.setText(city);
		}
		if (date != null && !date.isEmpty()) {
			dateDetailed.setText(date);
		} else {
			eventDateField.setVisibility(View.INVISIBLE);
		}
		if (performers != null && !performers.isEmpty()) {
			perform.setText(performers);
		} else {
			perform.setVisibility(View.INVISIBLE);
			eventsPerformerField.setVisibility(View.INVISIBLE);
		}
		if (description != null && !description.isEmpty()) {
			des.setText(description);
		} else {
			des.setVisibility(View.INVISIBLE);
			eventDesField.setVisibility(View.INVISIBLE);
		}
		if (image != null && !image.isEmpty()) {
			Picasso.with(EventsDetailedActivity.this).load(image)
					.into(eventImage);
		} else {
			eventImage.setImageResource(R.drawable.loca);
		}

		ticket.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!url.isEmpty()) {
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri
							.parse(url));
					startActivity(intent);
				}
			}
		});
		map.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(EventsDetailedActivity.this,
						MapActivity.class);
				intent.putExtra("Latitude", latitude);
				intent.putExtra("Longitude", longitude);
				startActivity(intent);
			}
		});
		going.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ParsePush push = new ParsePush();
				ParseUser user = ParseUser.getCurrentUser();
				String message = user.getString("Name") + " " + "is going to "
						+ title + ".";
				push.setChannel(user.getObjectId());
				JSONObject json = new JSONObject();
				try {
					json.put("alert", message);
					json.put("latitude", latitude);
					json.put("longitude", longitude);
					json.put("title", title);
					json.put("description", description);
					json.put("venueaddress", venueAddress);
					json.put("venuename", venueName);
					json.put("date", date);
					json.put("performers", performers);
					json.put("city", city);
					json.put("url", url);
					json.put("image", image);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				push.setData(json);
				// push.setMessage(message);
				push.sendInBackground();
			}
		});
	}
}
