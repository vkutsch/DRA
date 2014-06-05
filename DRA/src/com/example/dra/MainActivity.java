package com.example.dra;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class MainActivity  extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//hello
		
		EditText et= (EditText)findViewById(R.id.editText3);
		EditText et2= (EditText)findViewById(R.id.editText4);

		Button Enter=(Button)findViewById(R.id.button1);
		Button Skip=(Button)findViewById(R.id.button2);
		Button Sign=(Button)findViewById(R.id.button3);
		
		
		//2. set the click listener to run code//
		
		Enter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//TODO Auto-generated method stub
				
				EditText et = (EditText)findViewById(R.id.editText3);
			    String password= et.getText().toString();
				
				et.getEditableText().toString();
				if (password.equals("cadet1se")) {
				Intent intent = new Intent ("com.example.dra.screen4");
				startActivity(intent);	
				
				}else{
					
					
					AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
					builder.setTitle("Please try again");
					builder.setMessage("Please try again!");
					builder.setPositiveButton("ok", null);
					AlertDialog dialog = builder.show();
						
					
				}
			
				
				//Intent intent = new Intent ("com.example.dra.screen4");
				//startActivity(intent);	
				
			}
		});
		
		Skip.setOnClickListener(new View.OnClickListener() {	
		@Override 		
		 public void onClick(View v) {
			//TODO Auto-generated method stub
			Intent intent2 = new Intent ("com.example.dra.screen4");
			startActivity(intent2);	
			
		}
		});
		
		Sign.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
					//TODO Auto-generated method stub
					Intent intent = new Intent ("com.example.dra.screen3");
					startActivity(intent);	
					
				}
			});

}}