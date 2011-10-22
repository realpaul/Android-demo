package com.ibm.gssc;

import java.io.*;
import java.net.*;
import javax.xml.parsers.*;
import org.xml.sax.*;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class WeatherForcastGlobalizationActivity extends Activity {
	
	public static final String LOG_TAG = "com.ibm.gssc.wfg";  
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button idSubmitPlace = (Button) findViewById(R.id.idSubmitPlace);
        idSubmitPlace.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
		        Spinner idPlace = (Spinner) findViewById(R.id.idPlace);
				TextView idWeatherInfo = (TextView) findViewById(R.id.idWeatherInfo);
				String city = idPlace.getSelectedItem().toString();
				
				Log.v(LOG_TAG, "abc");
				
				
				SAXParserFactory spf = SAXParserFactory.newInstance(); 
				try {  
		            SAXParser sp = spf.newSAXParser();  
		            XMLReader reader = sp.getXMLReader();  
		              
		            XMLHandler  handler = new XMLHandler();  
		            reader.setContentHandler(handler);              
		      
		            URL url = new URL("http://www.google.com/ig/api?hl=zh-cn&weather=" + URLEncoder.encode(city));
		            Log.v(LOG_TAG, url.toString());
		            InputStream is = url.openStream();  
		            InputStreamReader isr = new InputStreamReader(is,"GBK");
		            InputSource source = new InputSource(isr);  
		      
		            reader.parse(source);
		            WeatherBean currentWeather = handler.getCurrentWeather();
					idWeatherInfo.setText(idPlace.getSelectedItem().toString() + "Weather is: " + currentWeather.getCondition());
		            
		            /*
		            CurrentEntity currentWeather = handler.getCurrentWeather();  
		            Log.d(TAG,"------tempc = "+currentWeather.getTempc()+"    condition = "+currentWeather.getCondition());  
		              
		            ContentValues values = new ContentValues();             
		            values.put(Weathers.CONDITION, currentWeather.getCondition());  
		            values.put(Weathers.TEMPC,currentWeather.getTempc());  
		            values.put(Weathers.HUMIDITY,currentWeather.getHumidity());  
		            values.put(Weathers.ICON,currentWeather.getIcon());  
		            values.put(Weathers.WINDCONDITION,currentWeather.getWindcondition());  
		              
		            getContentResolver().update(currentUri, values, null, null);  
		              
		            WeatherWidgetProvider.UpdateWeather(this,currentUri);  
		            */
		        } catch (Exception e) {  
		            e.printStackTrace();
		            Log.v(LOG_TAG, e.toString());
		        }
				
			}
		});
    }
}