/*Final Project
 * 
 * EventsUtil.java
 * 
 * Akshay Pandian
 * Swathi Balasubramanya Ayas
 */
package com.example.locals;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/*
 * Utility class to parse JSON string related to event details
 */
public class EventsUtil {

	static public class EventParser {
		static ArrayList<Events> parseEvent(String in) throws JSONException {
			ArrayList<Events> eventList = new ArrayList<Events>();

			JSONObject root = new JSONObject(in);
			JSONObject events = root.getJSONObject("events");
			JSONArray event = events.getJSONArray("event");
			for (int i = 0; i < event.length(); i++) {
				JSONObject singleEvent = event.getJSONObject(i);
				Events e = new Events();
				e.setLatitude(singleEvent.getString("latitude"));
				e.setLongitude(singleEvent.getString("longitude"));
				e.setCity(singleEvent.getString("city_name"));
				e.setUrl(singleEvent.getString("url"));
				e.setRegion(singleEvent.getString("region_name"));
				String html = singleEvent.getString("description");
				e.setDescription(android.text.Html.fromHtml(html).toString());
				e.setDate(singleEvent.getString("start_time"));
				e.setTitle(singleEvent.getString("title"));
				e.setVenueName(singleEvent.getString("venue_name"));
				e.setVenueAddress(singleEvent.getString("venue_address"));

				if (!singleEvent.isNull("image")) {
					JSONObject image = singleEvent.getJSONObject("image");
					JSONObject medium = image.getJSONObject("medium");
					e.setImage(medium.getString("url"));
				}

				if (!singleEvent.isNull("performers")) {
					JSONObject perfomers = singleEvent
							.getJSONObject("performers");
					if (perfomers.optJSONObject("performer") != null) {
						JSONObject performer = perfomers
								.getJSONObject("performer");
						e.setPerformers(performer.getString("name"));
					} else {
						JSONArray performer = perfomers
								.getJSONArray("performer");
						JSONObject ind = performer.getJSONObject(0);
						e.setPerformers(ind.getString("name"));
					}
				}
				eventList.add(e);
			}

			return eventList;

		}
	}
}
