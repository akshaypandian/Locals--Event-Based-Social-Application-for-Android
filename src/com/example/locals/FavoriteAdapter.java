/*Final Project
 * 
 * FavoriteAdapter.java
 * 
 * Akshay Pandian
 * Swathi Balasubramanya Ayas
 */
package com.example.locals;

import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/*
 * Adapter to support favorite activity
 */
public class FavoriteAdapter extends ArrayAdapter<Events> {
	Context mContext;
	int mResource;
	List<Events> mList;

	public FavoriteAdapter(Context mContext, int mResource, List<Events> mList) {
		super(mContext, mResource, mList);
		this.mContext = mContext;
		this.mResource = mResource;
		this.mList = mList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(mResource, parent, false);
		}
		ImageView image = (ImageView) convertView.findViewById(R.id.fav_image);
		TextView title = (TextView) convertView.findViewById(R.id.fav_title);
		if (mList.get(position).getImage() != null
				&& !mList.get(position).getImage().isEmpty()) {
			Picasso.with(mContext).load(mList.get(position).getImage())
					.into(image);
		} else {
			image.setImageResource(R.drawable.loca);
		}
		title.setText(mList.get(position).getTitle());

		return convertView;
	}

}
