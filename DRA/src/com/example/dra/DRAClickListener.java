package com.example.dra;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class DRAClickListener implements View.OnClickListener {

	Button theButton;
	Intent theIntent;
	Activity theActivity;
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//TODO Auto-generated method stub
		theActivity.startActivity(theIntent);
		
	}
	
	
	/*
	 * This gets called when we make a new one of these DRAClickListeners
	 */
	public DRAClickListener(Button aButton, Intent aIntent, Activity aActivity) {
		theButton = aButton;
		theIntent = aIntent;
		theActivity = aActivity;
		
		
	}

	
	
}
