/*Final Project
 * 
 * CategoryFragment.java
 * 
 * Akshay Pandian
 * Swathi Balasubramanya Ayas
 */
package com.example.locals;

/*
 * Fragment to display all available categories and seting up an intent based on which category is selected
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CategoryFragment extends Fragment {

	String locationName;
	View rootView;

	public CategoryFragment() {
		// Required empty public constructor
	}

	public CategoryFragment(String locationName) {
		this.locationName = locationName;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_category, container,
				false);
		rootView.findViewById(R.id.imagearts).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getActivity(),
								CategoryActivity.class);
						intent.putExtra("Category", "arts");
						intent.putExtra("Location", locationName);
						startActivity(intent);
					}
				});
		rootView.findViewById(R.id.imageconcerts).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getActivity(),
								CategoryActivity.class);
						intent.putExtra("Category", "music");
						intent.putExtra("Location", locationName);
						startActivity(intent);
					}
				});
		rootView.findViewById(R.id.imageConference).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getActivity(),
								CategoryActivity.class);
						intent.putExtra("Category", "conference");
						intent.putExtra("Location", locationName);
						startActivity(intent);
					}
				});
		rootView.findViewById(R.id.imageEducation).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getActivity(),
								CategoryActivity.class);
						intent.putExtra("Category", "education");
						intent.putExtra("Location", locationName);
						startActivity(intent);
					}
				});
		rootView.findViewById(R.id.imageFamily).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getActivity(),
								CategoryActivity.class);
						intent.putExtra("Category", "family");
						intent.putExtra("Location", locationName);
						startActivity(intent);
					}
				});
		rootView.findViewById(R.id.imageFestival).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getActivity(),
								CategoryActivity.class);
						intent.putExtra("Category", "festival");
						intent.putExtra("Location", locationName);
						startActivity(intent);
					}
				});
		rootView.findViewById(R.id.imageHoliday).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getActivity(),
								CategoryActivity.class);
						intent.putExtra("Category", "holiday");
						intent.putExtra("Location", locationName);
						startActivity(intent);
					}
				});
		rootView.findViewById(R.id.imageRestaurant).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getActivity(),
								CategoryActivity.class);
						intent.putExtra("Category", "restaurant");
						intent.putExtra("Location", locationName);
						startActivity(intent);
					}
				});
		rootView.findViewById(R.id.imageGallery).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getActivity(),
								CategoryActivity.class);
						intent.putExtra("Category", "gallery");
						intent.putExtra("Location", locationName);
						startActivity(intent);
					}
				});
		// Inflate the layout for this fragment
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
}
