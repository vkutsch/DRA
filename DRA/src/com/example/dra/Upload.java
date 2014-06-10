package com.example.dra;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
 

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
 
public class Upload extends FragmentActivity {
 
    public EditText etLng;
    public EditText etLat;
    GoogleMap nMap;
	MapHelper mapHelper;
	Spinner spin;
	DatePicker datePicker;
	
 
    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload);
        //nMap= ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		
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
		
		//Tell the map Helper to render on a new canvas
		MapHelper.setMap(nMap);
		
		//Update the map.  For now use a null camera position...
		MapHelper.updateMap();
		
		nMap.setMyLocationEnabled(true);
		
		
        etLat = (EditText)findViewById(R.id.et_lat);
        //TODO:  Do we need to check these??
        etLng = (EditText)findViewById(R.id.et_lng);
        @SuppressWarnings("unused")
        
 
       // EditText et = (EditText)findViewById(R.id.editText3);
		//String username= et.getText().toString();
		
       SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
       String username = sharedPreferences.getString("userId", "UNSET");
       SharedPreferences sharedPreferences2 = PreferenceManager.getDefaultSharedPreferences(this);
       int profile = sharedPreferences2.getInt("type", UploadClickListener.NEED);
            
        
        Button bUpload=(Button)findViewById(R.id.upload1);
        spin = (Spinner) findViewById(R.id.spinner1);
        datePicker= (DatePicker)findViewById(R.id.datePicker1);
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        Date theDate = calendar.getTime();
        
        //Hide calendar view
        
        
        UploadClickListener uploadCL = new UploadClickListener(bUpload, this,profile, username, "spinner-see below",theDate);
        bUpload.setOnClickListener(uploadCL);
        
    
    }
    
    
    
   //all the code below here does not matter.      
  //class Upload extends Activity {

        	//protected void onCreate1(Bundle savedInstanceState) {
        		//super.onCreate(savedInstanceState);
        		//setContentView(R.layout.camera);
        		
        		
        		//Thread logoTimer= new Thread(){
        			//public void run(){ 
        				//try {
        					//sleep(2000);
        				//	Intent menuIntent = new Intent("com.example.dra.upload");
        					//startActivity(menuIntent);
        				//} catch (InterruptedException e) {
        					// TODO Auto-generated catch block
        					//e.printStackTrace();
        				//}
        				
        			//	finally {
        				//	finish();
        			//	}
        		//	}
        	//	}; 
        	//	logoTimer.start(); 
       // /
        
        
  //}
 
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
        
        //Spinner spinner1;
        
        //public void addListenerOnSpinnerItemSelection() {
        	
       //.spinner1 = (Spinner) findViewById(R.id.spinner1);
        
        //spinner1.setOnItemSelectedListener(new MyOnItemSelectedListener());
        
        
        
        //public void addListenerOnButton() {
        	    //spinner1 = (Spinner) findViewById(R.id.spinner1);

        	
        	  }

    
  //start the spinner code 
    AdapterView.OnItemSelectedListener  {
    	TextView selection;
    	
    	String[] items = {"food", "water", "emergency" };
    	
    	public void onCreate1 (Bundle icicle)   {
    		super.onCreate(icicle);
    		setContentView(R.layout.upload)
    		selection= (TextView) findViewbyId(R.id.selection);
    		Spinner spin = (Spinner) findViewById(R.id.spinner1);
    		spin.setOnItemSelectedListener((OnItemSelectedListener) this);
    		
    		@SuppressWarnings("rawtypes")
			ArrayAdapter aa= new ArrayAdapter (this, android.R.layout.simple_spinner_item);
    		spin.setAdapter (aa);
    		
    	}
    	
    	
    	
    	public void onItemSelected (AdapterView<?>) parent, View v, int position, long id) {
    		selection.setText(items[position]);
    	}
    	
    	public void onNothingSelected(AdapterView<?> parent) { 
    		selection.setText("");
    	}
    	
    		
    		
    	
    	}
	   
        
        
    }
    */
{}}