package com.example.dra;

import java.util.Date;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.os.Build;

public class Home extends ActionBarActivity {
	
	GoogleMap nMap;
	MapHelper mapHelper;
	
	
	@SuppressLint({ "NewApi", "CutPasteId" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		//super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
		
		nMap= ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		nMap.setMyLocationEnabled(true);
		
		mapHelper = new MapHelper(nMap);
		mapHelper.addNeedMarker(new LatLng(45.76, 4.84), "Need Food in Lyon", "FrenchDude", new Date());
		mapHelper.addOfferMarker(new LatLng(44.84, 0.58), "Got Cheese in Bordeaux", "Philpe", new Date());
		
		
		
		//end of one marker 
		
		        //.position(new LatLng(45, 111))
		        //.title("Bozeman"))
		        //.setSnippet("Needs Electricity: Input 1300 5 June 14");;
		        	
		        	
	//nMap = ((MapFragment) Home().findViewById(R.id.map)).getMap();
		//nMap.addMarker=(new MarkerOptions()
		      //  .position(new LatLng(32, 96))
		       // .title("Dallas"));//
		
		
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
		
	}


	private MarkerOptions position(LatLng latLng) {
		// TODO Auto-generated method stub
		return null;
	}}
		