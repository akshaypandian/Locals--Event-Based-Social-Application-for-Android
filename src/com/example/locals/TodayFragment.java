/*Final Project
 * 
 * TodayFragment.java
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
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class TodayFragment extends ListFragment {

	ProgressDialog pd;
	String locationName;
	ListView list;
	View rootView;

	public TodayFragment() {
		// Required empty public constructor
	}

	public TodayFragment(String locationName) {
		this.locationName = locationName;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_today, container, false);
		list = (ListView) rootView.findViewById(android.R.id.list);

		// Inflate the layout for this fragment
		return rootView;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		new GetParsedData()
		.execute("http://api.eventful.com/json/events/search?app_key=pz2tGMjJcr4WhZT8&l="
				+ locationName.toLowerCase() + "&date=today");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		
		super.onAttach(activity);
	}

/*	@Override
	public void onResume() {
		new GetParsedData()
				.execute("http://api.eventful.com/json/events/search?app_key=pz2tGMjJcr4WhZT8&l="
						+ locationName.toLowerCase() + "&date=today");
		super.onResume();
	}*/

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
			pd = new ProgressDialog(getActivity(),
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
				ListViewAdapter adapter = new ListViewAdapter(getActivity(),
						R.layout.listview_adapter, result);
				list.setAdapter(adapter);
				list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub

					}
				});
			}
		}
	}

}
