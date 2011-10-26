package com.ibm.gssc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GPSDetectActivity extends Activity {
	public static final String LOG_TAG = "com.ibm.gssc.wfg";
	
	private double latitude;
	private double longtitude;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gps_detect);
		
		Button idOK = (Button)findViewById(R.id.idOK);
		idOK.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("latitude", latitude);
				intent.putExtra("longtitude", longtitude);
				setResult(Activity.RESULT_OK,intent);
				finish();
			}
		});
		
		Button idReturn = (Button) findViewById(R.id.idReturn);
		idReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setResult(Activity.RESULT_CANCELED);
				finish();
			}
		});

		LocationManager locationManager;
		String contextService = Context.LOCATION_SERVICE;
		locationManager = (LocationManager) getSystemService(contextService);

		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		
		//String gpsProvider = LocationManager.GPS_PROVIDER;
		String gpsProvider = locationManager.getBestProvider(criteria, true);
		Location location = locationManager.getLastKnownLocation(gpsProvider);
		if(location !=null)
			UpdateLocation(location);

		locationManager.requestLocationUpdates(gpsProvider, 2000, 10, new LocationListener() {
			public void onLocationChanged(Location location) {
				if(location !=null)
					UpdateLocation(location);				
			}

			public void onProviderDisabled(String provider) {
			}

			public void onProviderEnabled(String provider) {
			}

			public void onStatusChanged(String provider, int status, Bundle extras) {
			}
		});
	}

	protected void UpdateLocation(Location location) {
		TextView idLocationInformation = (TextView) findViewById(R.id.idLocationInformation);
		latitude = location.getLatitude();
		longtitude = location.getLongitude();
		idLocationInformation.setText("Latitude: " + location.getLatitude() + "\n" + "Longtitude: "
				+ location.getLongitude());
	}
}
