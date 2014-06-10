package com.example.dra;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class screen3  extends Activity {

	public void onCreate(Bundle  savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen3);


    


       Button Enter=(Button)findViewById(R.id.enter);
   

       Enter.setOnClickListener(new View.OnClickListener() {
    	   
	@Override
	public void onClick(View v) {
		//TODO Auto-generated method stub
		Intent intent = new Intent ("com.example.dra.home");
		startActivity(intent);	
		
	
		
			
			
		}
	
	
		
	;});}{;
	;}}