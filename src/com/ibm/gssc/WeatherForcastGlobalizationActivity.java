package com.ibm.gssc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class WeatherForcastGlobalizationActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button idSubmitPlace = (Button) findViewById(R.id.idSubmitPlace);
        idSubmitPlace.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
		        Spinner idPlace = (Spinner) findViewById(R.id.idPlace);
				TextView idWeatherInfo = (TextView) findViewById(R.id.idWeatherInfo);
				idWeatherInfo.setText(idPlace.getSelectedItem().toString() + "Weather is: " + "Sunny!");
			}
		});
    }
}