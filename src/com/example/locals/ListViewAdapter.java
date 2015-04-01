/*Final Project
 * 
 * ListViewAdapter.java
 * 
 * Akshay Pandian
 * Swathi Balasubramanya Ayas
 */
package com.example.locals;

import java.util.ArrayList;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ListViewAdapter extends ArrayAdapter<Events> {
	Context mContext;
	ArrayList<Events> mList;
	int mResource;
	FrameLayout fm;
	boolean switchView = false;

	public ListViewAdapter(Context context, int resource, ArrayList<Events> list) {
		super(context, resource, list);
		mContext = context;
		mResource = resource;
		mList = list;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(mResource, parent, false);
		}
		fm = (FrameLayout) convertView.findViewById(R.id.frameLayout);
		final ImageView imageEvent = (ImageView) fm
				.findViewById(R.id.image_event);
		final ImageView fav = (ImageView) fm.findViewById(R.id.image_favorite);
		final ImageView details = (ImageView) fm
				.findViewById(R.id.image_details);
		final TextView title = (TextView) convertView
				.findViewById(R.id.textview_title);
		if (mList.get(position).getImage() != null) {
			Picasso.with(mContext).load(mList.get(position).getImage())
					.into(imageEvent);
		} else {
			imageEvent.setImageResource(R.drawable.loca);
		}
		fav.setVisibility(View.INVISIBLE);
		details.setVisibility(View.INVISIBLE);
		title.setTextColor(Color.BLACK);
		title.setText(mList.get(position).getTitle());
		convertView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (switchView == false) {
					fav.setVisibility(View.INVISIBLE);
					details.setVisibility(View.INVISIBLE);
					fav.setClickable(false);
					details.setClickable(false);
					title.setTextColor(Color.BLACK);
					switchView = true;
				} else if (switchView == true) {
					fav.setVisibility(View.VISIBLE);
					details.setVisibility(View.VISIBLE);
					fav.setClickable(true);
					details.setClickable(true);
					title.setTextColor(Color.GRAY);
					switchView = false;
				}
			}
		});
		fav.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ParseUser user = ParseUser.getCurrentUser();
				ParseObject events = new ParseObject("FavoriteEvents");
				if (mList.get(position).getLatitude() != null) {
					events.put("Latitude", mList.get(position).getLatitude());
				}else{
					events.put("Latitude","");
				}
				if (mList.get(position).getLongitude() != null) {
					events.put("Longitude", mList.get(position).getLongitude());
				}else{
					events.put("Longitude","");
				}
				if (mList.get(position).getTitle() != null) {
					events.put("Title", mList.get(position).getTitle());
				}else{
					events.put("Title","");
				}
				if (mList.get(position).getVenueAddress() != null) {
					events.put("VenueAddress", mList.get(position)
							.getVenueAddress());
				}else{
					events.put("VenueAddress","");
				}
				if (mList.get(position).getVenueName() != null) {
					events.put("VenueName", mList.get(position).getVenueName());
				}else{
					events.put("VenueName","");
				}
				if (mList.get(position).getDate() != null) {
					events.put("Date", mList.get(position).getDate());
				}else{
					events.put("Date","");
				}
				if (mList.get(position).getPerformers() != null) {
					events.put("Performers", mList.get(position)
							.getPerformers());
				}else{
					events.put("Performers","");
				}
				if (mList.get(position).getDescription() != null) {
					events.put("Description", mList.get(position)
							.getDescription());
				}else{
					events.put("Description","");
				}
				if (mList.get(position).getCity() != null) {
					events.put("City", mList.get(position).getCity());
				}else{
					events.put("City","");
				}
				if (mList.get(position).getUrl() != null) {
					events.put("URL", mList.get(position).getUrl());
				}else{
					events.put("URL","");
				}
				if (mList.get(position).getImage() != null) {
					events.put("Image", mList.get(position).getImage());
				}else{
					events.put("Image","");
				}
				events.put("Owner", user.getObjectId());
				events.saveInBackground(new SaveCallback() {

					@Override
					public void done(ParseException e) {
						if (e == null) {
							Toast.makeText(mContext, "Saved to Favorites!",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(mContext,
									"Experiencing Technical Difficulty!!",
									Toast.LENGTH_SHORT).show();
						}
					}
				});
			}
		});

		details.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext,
						EventsDetailedActivity.class);

				intent.putExtra("Latitude", mList.get(position).getLatitude());
				intent.putExtra("Longitude", mList.get(position).getLongitude());
				intent.putExtra("Title", mList.get(position).getTitle());
				intent.putExtra("VenueAddress", mList.get(position)
						.getVenueAddress());
				intent.putExtra("VenueName", mList.get(position).getVenueName());
				intent.putExtra("Date", mList.get(position).getDate());
				intent.putExtra("Performers", mList.get(position)
						.getPerformers());
				intent.putExtra("Description", mList.get(position)
						.getDescription());
				intent.putExtra("City", mList.get(position).getCity());
				intent.putExtra("URL", mList.get(position).getUrl());
				intent.putExtra("Image", mList.get(position).getImage());

				mContext.startActivity(intent);
			}
		});
		return convertView;
	}

}
