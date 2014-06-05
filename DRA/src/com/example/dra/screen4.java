package com.example.dra;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class screen4  extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen4);
	

		Button Need=(Button)findViewById(R.id.need);
		Button Have=(Button)findViewById(R.id.have);
		
		
		
		//2. set the click listener to run code//
		
		Need.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//TODO Auto-generated method stub
				Intent intent = new Intent ("com.example.dra.screen2");
				startActivity(intent);	
				
			}
		});
		
	Have.setOnClickListener(new View.OnClickListener() {	
		@Override 		
		 public void onClick(View v) {
			//TODO Auto-generated method stub
			Intent intent2 = new Intent ("com.example.dra.screen2");
			startActivity(intent2);	
			
		}
		});
		
		

}}