package com.example.dra;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;


public class screen4  extends Activity {
	
	SharedPreferences sharedPreferences2;
	Editor editor;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen4);
	

		Button Need=(Button)findViewById(R.id.need);
		Button Have=(Button)findViewById(R.id.have);
		
		sharedPreferences2 = PreferenceManager.getDefaultSharedPreferences(this);
		editor = sharedPreferences2.edit();
		
		//2. set the click listener to run code//
		
		Need.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//TODO Auto-generated method stub
			
				editor.putInt("type", UploadClickListener.NEED); 
				editor.commit();
				
				Intent intent = new Intent ("com.example.dra.home");
				startActivity(intent);	
				
			}
		});
		
	Have.setOnClickListener(new View.OnClickListener() {	
		@Override 		
		 public void onClick(View v) {
			//TODO Auto-generated method stub
			editor.putInt("type", UploadClickListener.HAVE); 
			editor.commit();
			
			Intent intent2 = new Intent ("com.example.dra.home");
			startActivity(intent2);	
			
			
			
		}
		});
		
		

}}