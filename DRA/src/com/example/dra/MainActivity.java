package com.example.dra;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class MainActivity  extends Activity {

	//Put these two lines at top of any class where you need to access global info/preferences
	SharedPreferences sharedPreferences;
	Editor editor;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		EditText et= (EditText)findViewById(R.id.editText3);
		EditText et2= (EditText)findViewById(R.id.editText4);


		Button Skip=(Button)findViewById(R.id.button2);
		DRAClickListener skipCL = new DRAClickListener(Skip, new Intent ("com.example.dra.screen4"), this);
		Skip.setOnClickListener(skipCL);


		Button Sign=(Button)findViewById(R.id.button3);
		DRAClickListener signCL = new DRAClickListener(Skip, new Intent ("com.example.dra.screen3"), this);
		Sign.setOnClickListener(signCL);


		Button Enter=(Button)findViewById(R.id.button1);

		
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		editor = sharedPreferences.edit();

		Enter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//TODO Auto-generated method stub
				EditText et = (EditText)findViewById(R.id.editText3);
				String username= et.getText().toString();
				EditText et2= (EditText)findViewById(R.id.editText4);
				String password= et2.getText().toString();
				et2.getEditableText().toString();
				if ((username.equals("cadet1se")) && (password.equals("password"))){

					
					loginOK(username);
				}else{
					AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
					builder.setTitle("Error");
					builder.setMessage("Invalid Username or Password");
					builder.setPositiveButton("ok", null);
					AlertDialog dialog = builder.show();
				}
			}
		});
		
		

	}
	
	private void loginOK(String aUsername) {
		
		//Store off the aUsername
		editor.putString("userId", aUsername);
		editor.commit();
		
		Intent intent = new Intent ("com.example.dra.screen4");
		startActivity(intent);	
		
		

	
	}
}