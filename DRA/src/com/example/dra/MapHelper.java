package com.example.dra;

import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


/*
 * This class is used to track all the markers on a map
 */
public class MapHelper {

	/*
	 * The google map we are managing
	 */
	GoogleMap nMap;	
	
	/*
	 * This method updates the map based on the current location.
	 * Returns a list of markers that are within the zoom of the camera.
	 * This avoids the problem of loading the nMap with a ton of markers that will never be seen
	 * 
	 * We may need to control the zoom of the camera so we can never see above a certain zoom
	 * (avoids clutter)
	 */
	public void updateMap(CameraPosition cp) {
		
		//TODO:  Get the extent of the camera
		//- Query parse and get a list of markers in that extent
		//- Ensure query factors latency/expiration
		//- For each item in the list, ensure its on the nMap
		//- For items in the nMap, ensure they are in the list
		
		//For now: just plot and leave everything on map
		
	}
	
	/*
	 * Add a new offer marker at prescribed location, with title, and associated with ownerId
	 */
	public void addOfferMarker(LatLng aLocation, String aTitle, String ownerId, Date expiration) {
	
		
		
		MarkerOptions newMarkerOption = new MarkerOptions().position(aLocation).title(aTitle).snippet("by " + ownerId);
	
		// GREEN color icon
		newMarkerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

		//Add to map
		Marker newMarker = nMap.addMarker(newMarkerOption);
		
		//TODO: Add to parse.com  
		
	}
	
	/*
	 * Add a new need marker at prescribed location, with title, and associated with ownerId
	 */
	public void addNeedMarker(LatLng aLocation, String aTitle, String ownerId, Date expiration) {
		
		MarkerOptions newMarkerOption = new MarkerOptions().position(aLocation).title(aTitle).snippet("by " + ownerId);
		
		// Changing marker icon
		newMarkerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.caution));
		
		//Add to map
		Marker newMarker = nMap.addMarker(newMarkerOption);
		
		//TODO: Add to parse.com  
	}
	
	
	/*
	 * Add a new need marker at prescribed location, with title, and associated with ownerId
	 */
	public void removeMarker(Marker theDeletedMarker) {
		
		//TODO:  Check ownership
		
		theDeletedMarker.remove();
		
		//TODO:  Remove from parse
	}
	
	/**
	 * Create a new MapHelper.
	 * 
	 * @param theMap:  The google map object in the layout.
	 */
	public MapHelper(GoogleMap theMap) {
		nMap = theMap;
		
		//Add default need marker...
		this.addNeedMarker(new LatLng(40.69847032728747, -73.9514422416687), "Test Need NYC", "thirsty1", new Date() );
		
		this.addOfferMarker(new LatLng(41.324, -74.187), "Water Monroe NY", "hendo", new Date() );
		
		
	}
	
	
	
	
	
}
