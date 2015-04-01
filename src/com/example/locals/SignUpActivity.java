/*Final Project
 * 
 * SignUpActivity.java
 * 
 * Akshay Pandian
 * Swathi Balasubramanya Ayas
 */
package com.example.locals;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/*
 * Sign up activity that allows users to sign up in order to use the app
 */
public class SignUpActivity extends Activity {

	EditText name;
	EditText email;
	EditText username;
	EditText password;
	EditText confPassword;
	Button submit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);

		name = (EditText) findViewById(R.id.editText_name);
		email = (EditText) findViewById(R.id.editText_email);
		username = (EditText) findViewById(R.id.editText_usernames);
		password = (EditText) findViewById(R.id.editText_passwords);
		confPassword = (EditText) findViewById(R.id.editText_confirm);

		submit = (Button) findViewById(R.id.button_submit);

		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (name.getEditableText().toString().isEmpty()
						|| email.getEditableText().toString().isEmpty()
						|| username.getEditableText().toString().isEmpty()
						|| password.getEditableText().toString().isEmpty()
						|| confPassword.getEditableText().toString().isEmpty()) {
					if (name.getEditableText().toString().isEmpty()) {
						Toast.makeText(SignUpActivity.this, "Enter name!",
								Toast.LENGTH_SHORT).show();
					}
					if (email.getEditableText().toString().isEmpty()) {
						Toast.makeText(SignUpActivity.this, "Enter email!",
								Toast.LENGTH_SHORT).show();
					}
					if (username.getEditableText().toString().isEmpty()) {
						Toast.makeText(SignUpActivity.this, "Enter username!",
								Toast.LENGTH_SHORT).show();
					}
					if (password.getEditableText().toString().isEmpty()
							|| confPassword.getEditableText().toString()
									.isEmpty()) {
						Toast.makeText(SignUpActivity.this,
								"Enter password/confirm password field!",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					if (!password.getEditableText().toString()
							.equals(confPassword.getEditableText().toString())) {
						Toast.makeText(SignUpActivity.this,
								"Both passwords entered do not match!",
								Toast.LENGTH_SHORT).show();
					} else {
						ParseUser user = new ParseUser();
						user.put("Name", name.getEditableText().toString());
						user.put("Email", email.getEditableText().toString());
						user.setUsername(username.getEditableText().toString());
						user.setPassword(password.getEditableText().toString());

						user.signUpInBackground(new SignUpCallback() {

							@Override
							public void done(ParseException e) {
								if (e == null) {
									Toast.makeText(SignUpActivity.this,
											"Sign-Up Successful!",
											Toast.LENGTH_SHORT).show();
									Intent intent = new Intent(
											SignUpActivity.this,
											HomeActivity.class);
									startActivity(intent);
									finish();
								} else {
									Toast.makeText(SignUpActivity.this,
											"Sign-Up Unsuccessful!",
											Toast.LENGTH_SHORT).show();
								}
							}
						});
					}
				}
			}
		});
	}
}
