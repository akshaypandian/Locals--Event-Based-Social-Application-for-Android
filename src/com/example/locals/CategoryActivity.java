/*Final Project
 * 
 * CategoryActivity.java
 * 
 * Akshay Pandian
 * Swathi Balasubramanya Ayas
 */
package com.example.locals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

/*
 * Activity which downloads the event data related to a particular category
 * and shows it in listview. The user can select any event and view the
 * corresponding details. He can RSVP it. Doing so will send a push message 
 * to all his friends.
 */
public class CategoryActivity extends Activity {

	String category;
	ProgressDialog pd;
	String locationName;
	ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);

		category = getIntent().getExtras().getString("Category");
		locationName = getIntent().getExtras().getString("Location");
		list = (ListView) findViewById(R.id.list_category_activity);
		new GetParsedData()
				.execute("http://api.eventful.com/json/events/search?app_key=pz2tGMjJcr4WhZT8&l="
						+ locationName.toLowerCase()
						+ "&category="
						+ category
						+ "&date=today");
	}

	public class GetParsedData extends
			AsyncTask<String, Void, ArrayList<Events>> {

		@Override
		protected ArrayList<Events> doInBackground(String... params) {
			try {
				URL url = new URL(params[0]);
				HttpURLConnection con = (HttpURLConnection) url
						.openConnection();
				con.setRequestMethod("GET");
				con.connect();

				int statusCode = con.getResponseCode();
				if (statusCode == HttpURLConnection.HTTP_OK) {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(con.getInputStream()));
					StringBuilder sb = new StringBuilder();
					String line = reader.readLine();
					while (line != null) {
						sb.append(line);
						line = reader.readLine();
					}
					return EventsUtil.EventParser.parseEvent(sb.toString());
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			pd = new ProgressDialog(CategoryActivity.this,
					ProgressDialog.THEME_HOLO_DARK);
			pd.setCancelable(false);
			pd.setMessage("Loading events...");
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.show();
		}

		@Override
		protected void onPostExecute(ArrayList<Events> result) {
			if (result != null) {
				pd.dismiss();
				ListViewAdapter adapter = new ListViewAdapter(
						CategoryActivity.this, R.layout.listview_adapter,
						result);
				list.setAdapter(adapter);
			}
		}
	}
}
