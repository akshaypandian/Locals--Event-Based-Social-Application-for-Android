/*Final Project
 * 
 * CategoryListAdapter.java
 * 
 * Akshay Pandian
 * Swathi Balasubramanya Ayas
 */
package com.example.locals;

/*
 * Adapter to hold the listview components of CategoryFragment
 */
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CategoryListAdapter extends ArrayAdapter<String> {

	Context mContext;
	int mResource;
	List<String> mList;

	public CategoryListAdapter(Context context, int resource, List<String> list) {
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

		TextView category = (TextView) convertView.findViewById(R.id.cat_text);
		category.setText(mList.get(position));
		return convertView;
	}
}
