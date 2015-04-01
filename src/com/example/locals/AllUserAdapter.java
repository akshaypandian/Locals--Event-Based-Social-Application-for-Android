/*Final Project
 * 
 * AllUserAdapter.java
 * 
 * Akshay Pandian
 * Swathi Balasubramanya Ayas
 */
package com.example.locals;

import java.util.List;

import com.parse.ParseObject;
import com.parse.ParseUser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/*
 * Adapter class for showing all user's friends in listview. 
 * This data is got from Parse.com which stores the database related aspects of the application
 */
public class AllUserAdapter extends ArrayAdapter<FriendsClass> {

	Context mContext;
	int mResource;
	List<FriendsClass> mList;

	public AllUserAdapter(Context mContext, int mResource,
			List<FriendsClass> mList) {
		super(mContext, mResource, mList);
		this.mContext = mContext;
		this.mResource = mResource;
		this.mList = mList;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(mResource, parent, false);
		}

		TextView name = (TextView) convertView.findViewById(R.id.alluser_text);
		ImageView image = (ImageView) convertView
				.findViewById(R.id.alluser_image);
		name.setText(mList.get(position).getName());
		image.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ParseUser user = ParseUser.getCurrentUser();
				ParseObject friends = new ParseObject("FriendsList");
				friends.put("Owner", user.getObjectId());
				friends.put("Friends", mList.get(position).getObjectID());
				friends.saveInBackground();
				ParseObject otherFriend = new ParseObject("FriendsList");
				otherFriend.put("Owner", mList.get(position).getObjectID());
				otherFriend.put("Friends", user.getObjectId());
				otherFriend.saveInBackground();
				Toast.makeText(mContext, "Added as Friend!", Toast.LENGTH_SHORT)
						.show();
			}
		});
		return convertView;
	}

}
