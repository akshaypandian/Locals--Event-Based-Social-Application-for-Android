/*Final Project
 * 
 * MainActivity.java
 * 
 * Akshay Pandian
 * Swathi Balasubramanya Ayas
 */
package com.example.locals;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/*
 * Enables the user to either sign in or sign up. Authentication is done using
 * Parse.com which stores all user related information to provide access to
 * the concerned individual
 */
public class MainActivity extends Activity {
	Button signUp, signIn;
	EditText username, password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		signUp = (Button) findViewById(R.id.signUp_button);
		signIn = (Button) findViewById(R.id.signIn_button);
		username = (EditText) findViewById(R.id.editText_username);
		password = (EditText) findViewById(R.id.editText_password);
		ParseUser user = ParseUser.getCurrentUser();
		if (user != null) {
			Intent intent = new Intent(MainActivity.this, HomeActivity.class);
			startActivity(intent);
			finish();
		} else {
			signUp.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(MainActivity.this,
							SignUpActivity.class);
					startActivity(intent);
				}
			});

			signIn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					if (username.getEditableText().toString().isEmpty()
							|| password.getEditableText().toString().isEmpty()) {
						Toast.makeText(MainActivity.this,
								"Check login details!", Toast.LENGTH_SHORT)
								.show();
					} else {
						ParseUser.logInInBackground(username.getEditableText()
								.toString(), password.getEditableText()
								.toString(), new LogInCallback() {

							@Override
							public void done(ParseUser user, ParseException e) {
								if (user != null) {
									Intent intent = new Intent(
											MainActivity.this,
											HomeActivity.class);
									startActivity(intent);
									finish();
								} else {
									Toast.makeText(MainActivity.this,
											"Login Unsuccessful!",
											Toast.LENGTH_SHORT).show();
								}
							}
						});
					}

				}
			});
		}
	}
}
