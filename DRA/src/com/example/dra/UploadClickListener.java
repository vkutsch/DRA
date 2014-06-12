package com.example.dra;

import java.util.Date;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class UploadClickListener implements View.OnClickListener {

	Button theButton;
	Upload theActivity;

	String theUserId;
	String theNeedOrOffer;
	Date theExpirationDate;

	/*
	 * 0== Victim
	 * 1== Offer
	 */
	int theUserType;

	public static final int NEED = 0;
	public static final int HAVE = 1;

	@Override
	public void onClick(View v) {


		double etLatDouble = Double.parseDouble(theActivity.etLat.getText().toString());
		double etLngDouble = Double.parseDouble(theActivity.etLng.getText().toString());
		theNeedOrOffer = (String) theActivity.spin.getSelectedItem();

		LatLng aLatLng = new LatLng(etLatDouble ,etLngDouble);


		if(theUserType==NEED) {
			MapHelper.addNeedMarker(aLatLng, theNeedOrOffer, theUserId, theExpirationDate);
		} else {
			MapHelper.addOfferMarker(aLatLng, theNeedOrOffer, theUserId, theExpirationDate);
			}
		//Update the map.  For now use a null camera position...
		MapHelper.updateMap();

	}

	

	//Splash cod

	//theActivity.setContentView(R.layout.camera);


	//Thread logoTimer= new Thread(){
	//public void run(){ 
	//try {
	//	sleep(2000);
	//	Intent menuIntent = new Intent("com.example.dra.upload");
	//	theActivity.startActivity(menuIntent);
	//	} catch (InterruptedException e) {
	//		// TODO Auto-generated catch block
	//	e.printStackTrace();
	//	}

	//	finally {
	//		theActivity.finish();
	//	}
	//	}
	//}; 
	//	logoTimer.start(); 

	//}


	/*
	 * This gets called when we make a new uplaod listener;
	 * aButton:  the Button you are listening to
	 * aIntent:  The Intent you want to redirect to after (or null)
	 * aActivity: The parent activity where you are using the upload button (this)
	 * aLat/aLong:  The lat long of the need or want
	 * aMapHelper:  The map helper from the parent activity
	 * aTypeOfUser:  The user type;  0==Victim; 1==Offer
	 * aUserId:  The id of the user
	 * aneedOrOffer:  A text decription of the need or offer (e.g. "Water")
	 * aExpirationDate:  The date the need or offer expires..
	 */
	public UploadClickListener(Button aButton, Upload aActivity, int aTypeOfUser, String aUserId, String aNeedOrOffer, Date aExpirationDate) {
		theButton = aButton;

		theActivity = aActivity;
		theUserType = aTypeOfUser;
		theNeedOrOffer = aNeedOrOffer;
		theUserId = aUserId;
		theExpirationDate = aExpirationDate;


	}



}
