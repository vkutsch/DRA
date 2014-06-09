package com.example.dra;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class screen2  extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen2);
	
	
		
		Button Tutorials=(Button)findViewById(R.id.Tutorials);
		Button Care =(Button)findViewById(R.id.Care);
		Button Water =(Button)findViewById(R.id.water);
		Button Food =(Button)findViewById(R.id.food);
		Button Electrical =(Button)findViewById(R.id.electrical);
		Button Structural =(Button)findViewById(R.id.structural);
		Button Labor =(Button)findViewById(R.id.labor);
		Button Money =(Button)findViewById(R.id.Money);
		Button Medical =(Button)findViewById(R.id.Medical);
		Button Emergency =(Button)findViewById(R.id.Emergency);
		Button Skip =(Button)findViewById(R.id.skip2);
		Button Upload=(Button)findViewById(R.id.upload);
		Button Profile=(Button)findViewById(R.id.myprofile);
	
		//2. set the click listener to run code//
		
		Tutorials.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//TODO Auto-generated method stub
				Intent intent = new Intent ("com.example.dra.tutorials");
				startActivity(intent);	
				
			}
		});
		
		Care.setOnClickListener(new View.OnClickListener() {	
		@Override 		
		 public void onClick(View v) {
			//TODO Auto-generated method stub
			Intent intent2 = new Intent ("com.example.dra.home");
			startActivity(intent2);	
		}
		});
		
		Water.setOnClickListener(new View.OnClickListener() {	
		@Override 		
		 public void onClick(View v) {
			//TODO Auto-generated method stub
			Intent intent3 = new Intent ("com.example.dra.home");
			startActivity(intent3);	
			
		}
		});
		
		Food.setOnClickListener(new View.OnClickListener() {	
		@Override 		
		 public void onClick(View v) {
			//TODO Auto-generated method stub
			Intent intent4 = new Intent ("com.example.dra.home");
			startActivity(intent4);	
		}
		});
		
	Electrical.setOnClickListener(new View.OnClickListener() {	
		@Override 		
		 public void onClick(View v) {
			//TODO Auto-generated method stub
			Intent intent5 = new Intent ("com.example.dra.home");
			startActivity(intent5);	
			
		}
	});
	
	Structural.setOnClickListener(new View.OnClickListener() {	
	@Override 		
	 public void onClick(View v) {
		//TODO Auto-generated method stub
		Intent intent6 = new Intent ("com.example.dra.home");
		startActivity(intent6);	
		
		
	}
	});
	
	Labor.setOnClickListener(new View.OnClickListener() {	
	@Override 		
	 public void onClick(View v) {
		//TODO Auto-generated method stub
		Intent intent7 = new Intent ("com.example.dra.home");
		startActivity(intent7);	
		
		
	}
	});
	
	Money.setOnClickListener(new View.OnClickListener() {	
	@Override 		
	 public void onClick(View v) {
		//TODO Auto-generated method stub
		Intent intent8 = new Intent ("com.example.dra.home");
		startActivity(intent8);	
		
	}
	});
	
	Medical.setOnClickListener(new View.OnClickListener() {	
	@Override 		
	 public void onClick(View v) {
		//TODO Auto-generated method stub
		Intent intent9 = new Intent ("com.example.dra.home");
		startActivity(intent9);	
		
		
	}
	});
	
	Emergency.setOnClickListener(new View.OnClickListener() {	
	@Override 		
	 public void onClick(View v) {
		//TODO Auto-generated method stub
		Intent intent10 = new Intent ("com.example.dra.home");
		startActivity(intent10);	
		
	}
	});
	
	Skip.setOnClickListener(new View.OnClickListener() {	
	@Override 		
	 public void onClick(View v) {
		//TODO Auto-generated method stub
		Intent intent11 = new Intent ("com.example.dra.home");
		startActivity(intent11);	
		
		
		}
		});
		
		Upload.setOnClickListener(new View.OnClickListener() {	
		@Override 		
		 public void onClick(View v) {
			//TODO Auto-generated method stub
			Intent intent12 = new Intent ("com.example.dra.upload");
			startActivity(intent12);	
			
		}
		});
		
		Profile.setOnClickListener(new View.OnClickListener() {	
		@Override 		
		 public void onClick(View v) {
			//TODO Auto-generated method stub
			Intent intent13 = new Intent ("com.example.dra.profile");
			startActivity(intent13);	
}});}};
