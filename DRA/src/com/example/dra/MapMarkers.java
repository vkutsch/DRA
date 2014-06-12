package com.example.dra;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MapMarkers extends FragmentActivity implements
GoogleMap.OnInfoWindowClickListener {

	private static final LatLng map_center = null;
	private GoogleMap map;
	private final LatLng WestPoint = new LatLng(32.8,-85.18);
	

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapmarkers);  

		// Get a handle to the Map Fragment
		map = ((MapFragment) getFragmentManager()
				.findFragmentById(R.id.markers_map)).getMap();

		if(map !=null){
			initializeMap();

			addMapMarkers();

			// Add marker info window click listener
			map.setOnInfoWindowClickListener(this);
		} else {
			Toast.makeText(this, getString(R.string.nomap_error), 
					Toast.LENGTH_LONG).show();
		}

	}


	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.showmap_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		// Toggle traffic overlay
		case R.id.traffic:
			map.setTrafficEnabled(!map.isTrafficEnabled());  
			return true;
			// Toggle satellite overlay
		case R.id.satellite:
			final int mt = map.getMapType();
			if(mt == GoogleMap.MAP_TYPE_NORMAL){
				map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			} else {
				map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			}
			return true;
			// Toggle 3D building display (best when showing map instead of satellite)
		case R.id.building:
			map.setBuildingsEnabled(!map.isBuildingsEnabled());
			// Change camera tilt to view from angle if 3D
			if(map.isBuildingsEnabled()){
				changeCamera(map, map.getCameraPosition().target, 
						map.getCameraPosition().zoom, 
						map.getCameraPosition().bearing, 45);
			} else {
				changeCamera(map, map.getCameraPosition().target, 
						map.getCameraPosition().zoom,
						map.getCameraPosition().bearing, 0);
			}
			return true;
			// Toggle whether indoor maps displayed
		case R.id.indoor:
			map.setIndoorEnabled(!map.isIndoorEnabled());
			return true;
			// Settings page	
		case R.id.action_settings:
			// Actions for settings page
			final Intent j = new Intent(this, Settings.class);
			startActivity(j);
			return true;  	
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	// Method to animate camera properties change

	private void changeCamera(final GoogleMap map, final LatLng center, final float zoom, 
			final float bearing, final float tilt) {

		// Change properties of camera
		final CameraPosition cameraPosition = new CameraPosition.Builder()
		.target(center)         // Sets the center of the map
		.zoom(zoom)             // Sets the zoom
		.bearing(bearing)       // Sets the orientation of the camera 
		.tilt(tilt)             // Sets the tilt of the camera relative to nadir
		.build();               // Creates a CameraPosition from the builder
		if(map != null){
			map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
		} else {
			Toast.makeText(this, getString(R.string.nomap_error), 
					Toast.LENGTH_LONG).show();
		}
	}

	// Method to add map markers. See
	//     http://developer.android.com/reference/com/google/android/gms/maps/model
	//      /BitmapDescriptorFactory.html
	// for additional marker color options.

	private void addMapMarkers(){	

		// Add some location markers
		map.addMarker(new MarkerOptions()
		.title("West Point")
		.snippet("United States Military Academy")
		.position(WestPoint)
		).setDraggable(true);

		

	}

	// Method to initialize the map

	private void initializeMap(){

		// Enable or disable current location
		map.setMyLocationEnabled(true);

		// Move camera view and zoom to location
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(map_center, 13));

		// Initialize type of map
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

		// Initialize 3D buildings enabled for map view
		map.setBuildingsEnabled(false);

		// Initialize whether indoor maps are shown if available
		map.setIndoorEnabled(false);

		// Initialize traffic overlay
		map.setTrafficEnabled(false);

		// Enable rotation gestures
		map.getUiSettings().setRotateGesturesEnabled(true);

	}


	@Override
	public void onInfoWindowClick(final Marker marker) {

		String address = null;
		final String title = marker.getTitle();
		if(title.equals("West Point")){
			address = "http://www.usma.edu";		
		//} else if (title.equals("")) {
			//address = "";
		
		}

		marker.hideInfoWindow();

		final Intent link = new Intent(Intent.ACTION_VIEW);
		link.setData(Uri.parse(address));
		startActivity(link);

	}

}
