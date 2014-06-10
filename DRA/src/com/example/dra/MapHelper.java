package com.example.dra;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
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
	 * The google map we are currently rendering to...
	 */
	static GoogleMap nMap;	
	
	/*
	 * This is a data structure that stores all of the objects  
	 */
	static HashMap<String, MarkerOptions> theRealMap;
	
	/*
	 * This method updates the map based on the current location.
	 * Returns a list of markers that are within the zoom of the camera.
	 * This avoids the problem of loading the nMap with a ton of markers that will never be seen
	 * 
	 * We may need to control the zoom of the camera so we can never see above a certain zoom
	 * (avoids clutter)
	 */
	public static void updateMap() {
		
		
		if(theRealMap == null) return;
		//TODO:  Get the extent of the camera
		//- Query parse and get a list of markers in that extent
		//- Ensure query factors latency/expiration
		//- For each item in the list, ensure its on the nMap
		//- For items in the nMap, ensure they are in the list
		
		//For now: just plot and leave everything on map
		
		//Iterate over the HashMap and replot everything!
		for (Entry<String, MarkerOptions> entry : theRealMap.entrySet()) {
		    String key = entry.getKey();
		    MarkerOptions value = (MarkerOptions) entry.getValue();
		    nMap.addMarker(value);
		    
		}
		
	}
	
	/*
	 * Make a key for the hashmap so its the same recipe every time
	 */
	private static String makeKey(LatLng aLocation, String aTitle, String ownerId){
		return ""+ aLocation.latitude + aLocation.longitude + aTitle + ownerId;
	}
	
	/*
	 * Add a new offer marker at prescribed location, with title, and associated with ownerId
	 */
	public static void addOfferMarker(LatLng aLocation, String aTitle, String ownerId, Date expiration) {
	
		if(theRealMap == null) {
			theRealMap = new HashMap<String, MarkerOptions>();
		}
		
		
		MarkerOptions newMarkerOption = new MarkerOptions().position(aLocation).title(aTitle).snippet("by " + ownerId + " (expires:" + expiration.toString() +")");
		
		// GREEN color icon
		newMarkerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

		//Add to map
		Marker newMarker = nMap.addMarker(newMarkerOption);
		
		theRealMap.put(makeKey(aLocation, aTitle, ownerId), newMarkerOption);
		
		//TODO: Add to parse.com  
		
	}
	
	/*
	 * Add a new need marker at prescribed location, with title, and associated with ownerId
	 */
	public static void addNeedMarker(LatLng aLocation, String aTitle, String ownerId, Date expiration) {
		
		if(theRealMap == null) {
			theRealMap = new HashMap<String, MarkerOptions>();
		}
		
		
		MarkerOptions newMarkerOption = new MarkerOptions().position(aLocation).title(aTitle).snippet("by " + ownerId + " (expires:" + expiration.toString() +")");
		
		// Changing marker icon
		newMarkerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.caution));
		
		//Add to map
		Marker newMarker = nMap.addMarker(newMarkerOption);

		theRealMap.put(makeKey(aLocation, aTitle, ownerId), newMarkerOption);

		//TODO: Add to parse.com  
	}
	
	
	/*
	 * Add a new need marker at prescribed location, with title, and associated with ownerId
	 */
	public static void removeMarker(Marker theDeletedMarker) {
		
		//TODO:  Check ownership
		
		theDeletedMarker.remove();
		
		//TODO:  Remove from parse
	}
	
	
	public static void setMap(GoogleMap theMap) {
		nMap = theMap;
		
	}
	
	/**
	 * Static class..disable
	 * @param theMap:  The google map object in the layout.
	 */
	private MapHelper(GoogleMap theMap) {
		
		
	}
	
	
	
	
	
}
