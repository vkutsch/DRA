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



public class MainActivity  extends Activity {{}{}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		
		
		EditText et= (EditText)findViewById(R.id.editText3);
		EditText et2= (EditText)findViewById(R.id.editText4);

     
     Button Skip=(Button)findViewById(R.id.button2);
	 Button Sign=(Button)findViewById(R.id.button3);
	 Button Enter=(Button)findViewById(R.id.button1);
		
		Skip.setOnClickListener(new View.OnClickListener() {	
			@Override 		
			 public void onClick(View v) {
				//TODO Auto-generated method stub
				Intent intent2 = new Intent ("com.example.dra.screen4");
				startActivity(intent2);
			
		Sign.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
						//TODO Auto-generated method stub
						Intent intent3 = new Intent ("com.example.dra.screen3");
						startActivity(intent3);
						
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
					
				
					Intent intent = new Intent ("com.example.dra.screen4");
					startActivity(intent);	
		
				
			
				
				}else{
					
					
					AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
					builder.setTitle("Error");
					builder.setMessage("Invalid Username or Password");
					builder.setPositiveButton("ok", null);
					AlertDialog dialog = builder.show();
					
				
				
					
							
	
		
			
				
			}
			}});

      };});};{;;};});};}


				
				
