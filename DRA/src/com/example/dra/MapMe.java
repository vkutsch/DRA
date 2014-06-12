package com.example.dra;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;


public class MapMe extends FragmentActivity implements
   GooglePlayServicesClient.ConnectionCallbacks,
   GooglePlayServicesClient.OnConnectionFailedListener, 
   com.google.android.gms.location.LocationListener, 
   com.google.android.gms.maps.GoogleMap.OnMapClickListener,
   OnMapLongClickListener, OnMarkerClickListener,
   GoogleMap.OnInfoWindowClickListener {

	// Update interval in milliseconds for location services
	private static final long UPDATE_INTERVAL = 5000;
	// Fastest update interval in milliseconds for location services
	private static final long FASTEST_INTERVAL = 1000;	
	// Google Play diagnostics constant
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;	
	// Speed threshold for orienting map in direction of motion (m/s) 
	private static final double SPEED_THRESH = 1;

	private static final String TAG = "Mapper";
	private LocationClient locationClient;
	private Location currentLocation;
	private double currentLat;
	private double currentLon;
	private GoogleMap map;
	private LatLng map_center;
	private int zoomOffset = 5;
	private float currentZoom;
	private float bearing;
	private float speed;
	private float acc;
	private Circle localCircle;

	private double lon;
	private double lat;
	static final int numberOptions = 10;
	String [] optionArray = new String[numberOptions];

	// Define an object that holds accuracy and frequency parameters
	LocationRequest locationRequest;

	// Set up shared preferences to persist data.  We will use it later
	// to save the current zoom level if user leaves this activity, and
	// restore it when she returns.

	SharedPreferences prefs;
	SharedPreferences.Editor prefsEditor;


	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapme);

		// Get a handle to the Map Fragment
		map = ((MapFragment) getFragmentManager()
				.findFragmentById(R.id.mapme_map)).getMap();

		if(map != null){

			// Set the initial zoom level of the map
			currentZoom = map.getMaxZoomLevel()-zoomOffset;

			// Add a click listener to the map
			map.setOnMapClickListener(this);

			// Add a long-press listener to the map
			map.setOnMapLongClickListener(this);

			// Add Marker click listener to the map
			map.setOnMarkerClickListener(this);

			// Add marker info window click listener
			map.setOnInfoWindowClickListener(this);

		} else {
			Toast.makeText(this, getString(R.string.nomap_error), 
					Toast.LENGTH_LONG).show();
		}

		/* Create new location client. The first 'this' in args is the present
		 * context; the next two 'this' args indicate that this class will handle 
		 * callbacks associated with connection and connection errors, respectively 
		 * (see the onConnected, onDisconnected, and onConnectionError callbacks below).  
		 * You cannot use the location client until the onConnected callback 
		 * fires, indicating a valid connection.  At that point you can access location
		 * services such as present position and location updates. 
		 */

		locationClient = new LocationClient(this, this, this);

		// Create the LocationRequest object
		locationRequest = LocationRequest.create();
		// Set request for high accuracy
		locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		// Set update interval
		locationRequest.setInterval(UPDATE_INTERVAL);
		// Set fastest update interval that we can accept
		locationRequest.setFastestInterval(FASTEST_INTERVAL);

		// Get a shared preferences
		prefs = getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
		// Get a SharedPreferences editor
		prefsEditor = prefs.edit();

		// Keep screen on while this map location tracking activity is running
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

	}

	// Following two methods display and handle the top bar options menu for maps

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if present.
		getMenuInflater().inflate(R.menu.mapme_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if(map == null) {
			Toast.makeText(this, getString(R.string.nomap_error), 
					Toast.LENGTH_LONG).show();
			return false;
		}

		// Handle item selection
		switch (item.getItemId()) {

		// Toggle traffic overlay
		case R.id.traffic_mapme:
			map.setTrafficEnabled(!map.isTrafficEnabled());  
			return true;

			// Toggle satellite overlay
		case R.id.satellite_mapme:
			int mt = map.getMapType();
			if(mt == GoogleMap.MAP_TYPE_NORMAL){
				map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			} else {
				map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			}
			return true;

			// Toggle 3D building display
		case R.id.building_mapme:
			map.setBuildingsEnabled(!map.isBuildingsEnabled());
			// Change camera tilt to view from angle if 3D
			if(map.isBuildingsEnabled()){
				changeCamera(map, map.getCameraPosition().target, currentZoom, 
						map.getCameraPosition().bearing, 45, true);
			} else {
				changeCamera(map, map.getCameraPosition().target, currentZoom, 
						map.getCameraPosition().bearing, 0, true);
			}
			return true;

			// Toggle whether indoor maps displayed
		case R.id.indoor_mapme:
			map.setIndoorEnabled(!map.isIndoorEnabled());
			return true;

			// Toggle tracking enabled
		case R.id.track_mapme:
			if(locationClient != null){
				if(locationClient.isConnected()){
					stopTracking();
				} else {
					startTracking();
				}
			}
			return true;
			// Settings page	
		case R.id.action_settings:
			Intent j = new Intent(this, Settings.class);
			startActivity(j);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	// Save the current zoom level when going into the background

	@Override
	protected void onPause() {

		// Store the current map zoom level
		if(map != null){
			currentZoom = map.getCameraPosition().zoom;
			prefsEditor.putFloat("KEY_ZOOM",currentZoom);
			prefsEditor.commit();  
		}
		super.onPause();
		Log.i(TAG,"onPause: Zoom="+currentZoom);
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Restore previous zoom level (default to max zoom level if
		// no prefs stored)

		if (prefs.contains("KEY_ZOOM") && map != null){
			currentZoom = prefs.getFloat("KEY_ZOOM", map.getMaxZoomLevel());
		}
		Log.i(TAG,"onResume: Zoom="+currentZoom);

		// Keep screen on while this map location tracking activity is running
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}


	/* The following two lifecycle methods conserve resources by ensuring that
	 * location services are connected when the map is visible and disconnected when
	 * it is not.
	 */

	// Called by system when Activity becomes visible, so connect location client.

	@Override
	protected void onStart() {
		super.onStart();
		locationClient.connect();
	}

	// Called by system when Activity is no longer visible, so disconnect location
	// client, which invalidates it.

	@Override
	protected void onStop() {

		// If the client is connected, remove location updates and disconnect
		if (locationClient.isConnected()) {
			locationClient.removeLocationUpdates(this);
		}
		locationClient.disconnect();

		// Turn off the screen-always-on request
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		super.onStop();
	}

	// The following three callbacks indicate connections, disconnections, and 
	// connection errors, respectively.


	/* Called by Location Services when the request to connect the
	 * client finishes successfully. At this point, you can
	 * request current location or begin periodic location updates.
	 */

	@Override
	public void onConnected(Bundle dataBundle) {

		// Indicate that a connection has been established
		Toast.makeText(this, getString(R.string.connected_toast), 
				Toast.LENGTH_SHORT).show();

		currentLocation = locationClient.getLastLocation();
		currentLat = currentLocation.getLatitude();
		currentLon = currentLocation.getLongitude();

		// Center map on current location
		map_center = new LatLng(currentLat,currentLon);

		if(map != null) {
			initializeMap();
		} else {
			Toast.makeText(this, getString(R.string.nomap_error), 
					Toast.LENGTH_LONG).show();
		}

		// Start periodic updates.  This version of requestLocationUpdates is 
		// suitable for foreground activities when connected to a LocationClient. 
		// The second argument is the LocationListener, which is "this" since the 
		// present class implements the LocationListener interface.

		locationClient.requestLocationUpdates(locationRequest, this);
	}

	// Called by Location Services if the connection to location client fails

	@Override
	public void onDisconnected() {
		Toast.makeText(this, getString(R.string.disconnected_toast),
				Toast.LENGTH_SHORT).show();
	}

	// Called by Location Services if the attempt to connect to
	// Location Services fails.

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {

		/* Google Play services can resolve some errors it detects.
		 * If the error has a resolution, try sending an Intent to
		 * start a Google Play services activity that can resolve the error.
		 */

		if (connectionResult.hasResolution()) {
			try {
				// Start an Activity that tries to resolve the error
				connectionResult.startResolutionForResult(
						this, CONNECTION_FAILURE_RESOLUTION_REQUEST);

				// Thrown if Google Play services canceled the original PendingIntent

			} catch (IntentSender.SendIntentException e) {
				e.printStackTrace();
			}
		} else {
			// If no resolution is available, display a dialog with the error.
			showErrorDialog(connectionResult.getErrorCode());
		}
	}

	public void showErrorDialog(int errorCode){
		Log.e(TAG, "Error_Code ="+errorCode);
		// Create an error dialog display here
	}

	// Method to initialize the map.  Check that map != null before calling.

	private void initializeMap(){

		// Enable or disable current location
		map.setMyLocationEnabled(true);

		// Move camera view and zoom to location
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(map_center,currentZoom));

		// Initialize type of map to normal
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

		// Initialize 3D buildings enabled for map view
		map.setBuildingsEnabled(false);

		// Initialize whether indoor maps are shown if available
		map.setIndoorEnabled(false);

		// Initialize traffic overlay
		map.setTrafficEnabled(false);

		// Disable rotation gestures
		map.getUiSettings().setRotateGesturesEnabled(false);
	}

	// Starts location tracking
	private void startTracking(){
		locationClient.connect();
		Toast.makeText(this, "Location tracking started", Toast.LENGTH_SHORT).show(); 

	}

	// Stops location tracking
	private void stopTracking(){
		if (locationClient.isConnected()) {
			locationClient.removeLocationUpdates(this);
		}
		locationClient.disconnect();   
		Toast.makeText(this, "Location tracking halted", Toast.LENGTH_SHORT).show();
	}

	/* Method to add map marker at give latitude and longitude.  The third arg is a float
	 * variable defining color for the marker.  Pre-defined marker colors may be found at
	 *     http://developer.android.com/reference/com/google/android/gms/maps/model
	 *     /BitmapDescriptorFactory.html
	 * and should be specified in the format BitmapDescriptorFactory.HUE_RED, which is
	 * the default color, but various other ones are defined there such as HUE_ORANGE, 
	 * HUE_BLUE, HUE_GREEN, ... The arguments title and snippet are for the window that
	 * will open if one clicks on the marker.
	 */	

	private void addMapMarker (double lat, double lon, float markerColor,
			String title, String snippet){

		if(map != null){
			Marker marker = map.addMarker(new MarkerOptions()
			.title(title)
			.snippet(snippet)
			.position(new LatLng(lat,lon))
			.icon(BitmapDescriptorFactory.defaultMarker(markerColor))
			);
			marker.setDraggable(false);
			marker.showInfoWindow();
		} else {
			Toast.makeText(this, getString(R.string.nomap_error), 
					Toast.LENGTH_LONG).show();
		}

	}

	// Decimal output formatting class that uses Java DecimalFormat. See
	// http://developer.android.com/reference/java/text/DecimalFormat.html.
	// The string "formatPattern" specifies the output formatting pattern for
	// the float or double. For example, 35.8577877288 will be returned 
	// as the string "35.85779" if formatPattern = "0.00000", and as
	// the string "3.586E01" if formatPattern = "0.000E00".

	public String formatDecimal(double number, String formatPattern){

		DecimalFormat df = new DecimalFormat(formatPattern);

		// The method format(number) with a single argument is inherited by 
		// DecimalFormat from NumberFormat.

		return df.format(number);

	}

	/* Method to change properties of camera. If your GoogleMaps instance is called map, 
	 * you can use 
	 * 
	 * map.getCameraPosition().target
	 * map.getCameraPosition().zoom
	 * map.getCameraPosition().bearing
	 * map.getCameraPosition().tilt
	 * 
	 * to get the current values of the camera position (target, which is a LatLng), 
	 * zoom, bearing, and tilt, respectively.  This permits changing only a subset of
	 * the camera properties by passing the current values for all arguments you do not
	 * wish to change.
	 * 
	 * */

	private void changeCamera(GoogleMap map, LatLng center, float zoom, 
			float bearing, float tilt, boolean animate) {

		CameraPosition cameraPosition = new CameraPosition.Builder()
		.target(center)         // Sets the center of the map
		.zoom(zoom)             // Sets the zoom
		.bearing(bearing)       // Sets the bearing of the camera 
		.tilt(tilt)             // Sets the tilt of the camera relative to nadir
		.build();               // Creates a CameraPosition from the builder

		// Move (if variable animate is false) or animate (if animate is true) to new 
		// camera properties. 

		if(animate){
			map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
		} else {
			map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
		}
	}


	// Following callback associated with implementing LocationListener.
	// It fires when a location change is detected, passing in the new
	// location as the variable "newLocation".

	@Override
	public void onLocationChanged(Location newLocation) {

		bearing = newLocation.getBearing();
		speed = newLocation.getSpeed();
		acc = newLocation.getAccuracy();

		// Get latitude and longitude of updated location	
		double lat = newLocation.getLatitude();
		double lon = newLocation.getLongitude();
		LatLng currentLatLng = new LatLng(lat, lon);

		// Return a bundle of additional location information, if available.
		// This will return null if no extras are available, so check for
		// null before using this Bundle.

		Bundle locationExtras = newLocation.getExtras();
		// If there is no satellite info, return -1 for number of satellites
		int numberSatellites = -1;
		if(locationExtras != null){
			Log.i(TAG, "Extras:"+locationExtras.toString());
			if(locationExtras.containsKey("satellites")){
				numberSatellites = locationExtras.getInt("satellites");
			}
		}

		// Log some basic location information
		Log.i(TAG,"Lat="+formatDecimal(lat,"0.00000")
				+" Lon="+formatDecimal(lon,"0.00000")
				+" Bearing="+formatDecimal(bearing, "0.0")
				+" deg Speed="+formatDecimal(speed, "0.0")+" m/s"
				+" Accuracy="+formatDecimal(acc, "0.0")+" m"
				+" Sats="+numberSatellites);

		if(map != null) {

			// Animate camera to reflect location update. Orient the view in the
			// direction of motion, but only if the velocity is above a threshold
			// to prevent random rotations of view when velocity direction is
			// not well defined.  

			if(speed < SPEED_THRESH) {

				// Smoothly move the camera view to center on the updated location
				// without changing bearing, tilt, or zoom of camera.

				map.animateCamera(CameraUpdateFactory.newLatLng(currentLatLng));

			} else {

				// Animate motion to the updated position and also orient camera in 
				// direction of motion using current bearing, keeping the same tilt 
				// and zoom.  Note: bearing is the horizontal direction of travel 
				// for the device; it has nothing to do with orientation of the device.

				changeCamera(map, currentLatLng, map.getCameraPosition().zoom,
						bearing, map.getCameraPosition().tilt, true);
			}


		} else {
			Toast.makeText(this, getString(R.string.nomap_error), 
					Toast.LENGTH_LONG).show();
		}
	}


	// Method to reverse-geocode location passed as latitude and longitude. It returns a string which
	// is the first reverse-geocode location in the returned list.  (The full list is output to the
	// logcat stream.) This method returns null if no geocoder backend is available.

	@SuppressLint("NewApi")
	private String reverseGeocodeLocation(double latitude, double longitude){

		// Use to suppress country in returned address for brevity
		boolean omitCountry = true;

		// String to hold single address that will be returned
		String returnString = "";

		Geocoder gcoder = new Geocoder(this);

		// Note that the Geocoder uses synchronous network access, so in a serious application
		// it would be best to put it on a background thread to prevent blocking the main UI if network
		// access is slow. Here we are just giving an example of how to use it so, for simplicity, we
		// don't put it on a separate thread.  See the class RouteMapper in this package for an example
		// of making a network access on a background thread. Geocoding is implemented by a backend
		// that is not part of the core Android framework, so it is not guaranteed to be present on
		// every device.  Thus we use the static method Geocoder.isPresent() to test for presence of 
		// the required backend on the given platform.

		try{
			List<Address> results = null;
			if(Geocoder.isPresent()){
				results = gcoder.getFromLocation(latitude, longitude, numberOptions);
			} else {
				Log.i(TAG,"No geocoder accessible on this device");
				return null;
			}
			Iterator<Address> locations = results.iterator();
			String raw = "\nRaw String:\n";
			String country;
			int opCount = 0;
			while(locations.hasNext()){
				Address location = locations.next();
				if(opCount==0 && location != null){
					lat = location.getLatitude();
					lon = location.getLongitude();
				}
				country = location.getCountryName();
				if(country == null) {
					country = "";
				} else {
					country =  ", "+country;
				}
				raw += location+"\n";
				optionArray[opCount] = location.getAddressLine(0)+", "
				+location.getAddressLine(1)+country+"\n";
				if(opCount == 0){
					if(omitCountry){
						returnString = location.getAddressLine(0)+", "
						+location.getAddressLine(1)+"\n";
					} else {
						returnString = optionArray[opCount];
					}
				}
				opCount ++;
			}
			Log.i(TAG, raw);
			Log.i(TAG,"\nOptions:\n");
			for(int i=0; i<opCount; i++){
				Log.i(TAG,"("+(i+1)+") "+optionArray[i]);
			}
			Log.i(TAG,"lat="+lat+" lon="+lon);

			//Toast.makeText(this, optionArray[0], Toast.LENGTH_LONG).show();

		} catch (IOException e){
			Log.e(TAG, "I/O Failure",e);
		}

		// Return the first location entry in the list.  A more sophisticated implementation 
		// would present all location entries in optionArray to the user for choice when more 
		// than one is returned by the geodecoder.

		return returnString;	

	}


	// Callback that fires when map is tapped, passing in the latitude
	// and longitude coordinates of the tap (actually the point on the ground 
	// projected from the screen tap).  This will be invoked only if no overlays 
	// on the map intercept the click first.  Here we will just issue a Toast
	// displaying the map coordinates that were tapped.  See the onMapLongClick
	// handler for an example of additional actions that could be taken.

	@Override
	public void onMapClick(LatLng latlng) {

		String f = "0.0000";
		double lat = latlng.latitude;
		double lon = latlng.longitude;
		Toast.makeText(this, "Latitude="+formatDecimal(lat,f)+" Longitude="
				+formatDecimal(lon,f), Toast.LENGTH_LONG).show();
	}

	// This callback fires for long clicks on the map, passing in the LatLng coordinates

	@Override
	public void onMapLongClick(LatLng latlng) {

		double lat = latlng.latitude;
		double lon = latlng.longitude;

		String title = reverseGeocodeLocation(latlng.latitude, latlng.longitude);

		String snippet="Tap marker to delete; tap window for Street View";

		// Add an orange marker on map at position of tap (default marker color is red).
		addMapMarker(lat, lon, BitmapDescriptorFactory.HUE_ORANGE, title, snippet);

		// Add a circle centered on the marker given the current position uncertainty
		// Keep a reference to the returned circle so we can remove it later.

		localCircle = addCircle (lat, lon, acc, "#00000000", "#40ff9900");

	}


	/* Add a circle at (lat, lon) with specified radius. Stroke and fill colors are specified
	 * as strings. Valid strings are those valid for the argument of Color.parseColor(string):
	 * for example, "#RRGGBB", "#AARRGGBB", "red", "blue", ...
	 */

	private Circle addCircle(double lat, double lon, float radius, 
			String strokeColor, String fillColor){

		if(map == null){
			Toast.makeText(this, getString(R.string.nomap_error), Toast.LENGTH_LONG).show();
			return null;
		}

		CircleOptions circleOptions = new CircleOptions()
		.center( new LatLng(lat, lon) )
		.radius( radius )
		.strokeWidth(1)
		.fillColor(Color.parseColor(fillColor))
		.strokeColor(Color.parseColor(strokeColor));

		// Add circle to map and return reference to the Circle for possible later use
		return map.addCircle(circleOptions);	

	}


	// Process clicks on markers

	@Override
	public boolean onMarkerClick(Marker marker) {

		// Remove the marker and its info window and circle if marker clicked
		marker.remove();
		localCircle.remove();
		// Return true to prevent default behavior of opening info window
		return true;

	}

	// Process clicks on the marker info window

	@Override
	public void onInfoWindowClick(Marker marker) {

		double lat = marker.getPosition().latitude;
		double lon = marker.getPosition().longitude;

		// Launch a StreetView on current location
		showStreetView(lat,lon);

		// Remove marker and circle
		marker.remove();
		localCircle.remove();

	}

	/* Open a Street View, if available. 
	 * The user will have the choice of getting the Street View
	 * in a browser, or with the StreetView app if it is installed.
	 * If no Street View exists for a given location, this will present 
	 * a blank page.
	 */	

	private void showStreetView(double lat, double lon ){		
		String uriString = "google.streetview:cbll="+lat+","+lon;
		Intent streetView = new Intent(android.content.Intent.ACTION_VIEW,Uri.parse(uriString));
		startActivity(streetView);	
	}
	
}

