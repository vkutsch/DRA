package com.example.dra;

import java.util.Date;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.os.Build;

public class Home extends ActionBarActivity {
	
	GoogleMap nMap;
	
	@SuppressLint({ "NewApi", "CutPasteId" })
	@Override
	protected void onResume() {
		super.onResume();
		//setContentView(R.layout.activity_home);
		
		//nMap= ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		//SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);



		nMap.setMyLocationEnabled(true);
		MapHelper.setMap(nMap);
		MapHelper.updateMap();
		
	}
	
	@SuppressLint({ "NewApi", "CutPasteId" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		//super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
		
		//nMap= ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		//SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
		
		
		if (savedInstanceState == null) {
	        // First incarnation of this activity.
	        mapFragment.setRetainInstance(true);
	        nMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
	    } else {
	        // Reincarnated activity. The obtained map is the same map instance in the previous
	        // activity life cycle. There is no need to reinitialize it.
	        nMap = mapFragment.getMap();
	    }
		
		nMap.setMyLocationEnabled(true);
		MapHelper.setMap(nMap);
		MapHelper.updateMap();
		//MapHelper.addNeedMarker(new LatLng(45.76, 4.84), "Food", "Victim3", new Date());
		//MapHelper.addOfferMarker(new LatLng(44.84, 0.58), "Food", "Philpe5", new Date());
		
		
		
		Button Profile=(Button)findViewById(R.id.Profile);
		Profile.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//TODO Auto-generated method stub
				Intent intent = new Intent ("com.example.dra.profile");
				startActivity(intent);	
				
			}
		});
		
		Button Tutorials=(Button)findViewById(R.id.Tutorials);
		Tutorials.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//TODO Auto-generated method stub
				Intent intent2 = new Intent ("com.example.dra.tutorials");
				startActivity(intent2);	
				
			}
		});
			
		Button Upload=(Button)findViewById(R.id.Upload);
		Upload.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//TODO Auto-generated method stub
				Intent intent3 = new Intent ("com.example.dra.upload");
				startActivity(intent3);	
			}
		});
	
	}  

	private MarkerOptions position(LatLng latLng) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
		