package com.ibm.gssc;

import java.io.*;
import java.net.*;
import java.util.Locale;

import javax.xml.parsers.*;
import org.xml.sax.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class WeatherForcastGlobalizationActivity extends Activity {

	public static final String LOG_TAG = "com.ibm.gssc.wfg";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		com.ibm.icu.text.Bidi bidi = new com.ibm.icu.text.Bidi();

		Button idSubmitPlace = (Button) findViewById(R.id.idSubmitPlace);
		idSubmitPlace.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Spinner idPlace = (Spinner) findViewById(R.id.idPlace);
				TextView idWeatherInfo = (TextView) findViewById(R.id.idWeatherInfo);
				ImageView idWeatherIcon = (ImageView) findViewById(R.id.idWeatherIcon);
				String city = idPlace.getSelectedItem().toString();
				String cityInEnglish = v.getResources().getStringArray(R.array.place_in_English)[(int) idPlace
						.getSelectedItemId()];

				SAXParserFactory spf = SAXParserFactory.newInstance();
				try {
					SAXParser sp = spf.newSAXParser();
					XMLReader reader = sp.getXMLReader();

					XMLHandler handler = new XMLHandler();
					reader.setContentHandler(handler);

					String currentLocale = Locale.getDefault().getCountry().toString();
					Log.v(LOG_TAG, currentLocale);

					URL url = new URL("http://www.google.com/ig/api?hl=" + currentLocale
							+ "&oe=UTF-8&weather=" + cityInEnglish);
					Log.v(LOG_TAG, url.toString());
					InputStream is = url.openStream();
					// InputStreamReader isr = new InputStreamReader(is);
					//InputStreamReader isr = new InputStreamReader(is, "UTF-8");
					InputStreamReader isr = new InputStreamReader(is);
					InputSource source = new InputSource(isr);

					reader.parse(source);
					WeatherBean beanCurrentWeather = handler.getCurrentWeather();

					String[] current_weather = v.getResources().getStringArray(R.array.current_weather);
					String weather_output = String.format(current_weather[0], city) + "\n"
							+ String.format(current_weather[1], beanCurrentWeather.getCondition()) + "\n"
							+ String.format(current_weather[2], beanCurrentWeather.getTemperature()) + "\n"
							+ String.format(current_weather[3], beanCurrentWeather.getHumidity()) + "\n"
							+ String.format(current_weather[4], beanCurrentWeather.getWind_condition());
					
					com.ibm.icu.text.NumberFormat nf =com.ibm.icu.text.NumberFormat.getNumberInstance(Locale.getDefault());
					String output = nf.format(12345);
					Log.v(LOG_TAG, "aaaaa" + output);
					weather_output = "\n" + weather_output + output; 
					
					idWeatherInfo.setText(weather_output);
					idWeatherIcon.setImageBitmap(getHttpBitmap(("http://www.google.com")
							+ beanCurrentWeather.getIcon()));
					idWeatherIcon.setVisibility(View.VISIBLE);
				} catch (Exception e) {
					e.printStackTrace();
					Log.v(LOG_TAG, e.toString());
				}
			}
		});
		
		Spinner idPlace = (Spinner) findViewById(R.id.idPlace);
		idPlace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				TextView idWeatherInfo = (TextView) findViewById(R.id.idWeatherInfo);
				idWeatherInfo.setText("");
				
				ImageView idWeatherIcon = (ImageView) findViewById(R.id.idWeatherIcon);
				idWeatherIcon.setVisibility(View.GONE);
			}
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
		Button idShowGPSPage = (Button) findViewById(R.id.idShowGPSPage);
		idShowGPSPage.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivityForResult(new Intent(getBaseContext(), GPSDetectActivity.class), R.layout.gps_detect);
				//startActivity(new Intent(getBaseContext(), GPSDetectActivity.class));
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == R.layout.gps_detect){
			if(resultCode == Activity.RESULT_OK){
				double latitude = data.getExtras().getDouble("latitude");
				double longtitude = data.getExtras().getDouble("longtitude");
				
				AlertDialog dialog = new AlertDialog.Builder(this).create();
				dialog.setMessage("OK\n" + ((int)latitude)*1000000 + "\n" + ((int)longtitude)*1000000);
				dialog.setButton (AlertDialog.BUTTON_POSITIVE, this.getResources().getString(R.string.ok),new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				//dialog.show();
				
				TextView idWeatherInfo = (TextView) findViewById(R.id.idWeatherInfo);
				ImageView idWeatherIcon = (ImageView) findViewById(R.id.idWeatherIcon);
				SAXParserFactory spf = SAXParserFactory.newInstance();
				try {
					SAXParser sp = spf.newSAXParser();
					XMLReader reader = sp.getXMLReader();

					XMLHandler handler = new XMLHandler();
					reader.setContentHandler(handler);

					String currentLocale = Locale.getDefault().toString();
					Log.v(LOG_TAG, currentLocale);

					URL url = new URL("http://www.google.com/ig/api?hl=" + currentLocale
							+ "&oe=UTF-8&weather=,,," + ((int)latitude)*1000000 + "," +((int)longtitude)*1000000);
					Log.v(LOG_TAG, url.toString());
					InputStream is = url.openStream();
					// InputStreamReader isr = new InputStreamReader(is);
					InputStreamReader isr = new InputStreamReader(is, "UTF-8");
					InputSource source = new InputSource(isr);

					reader.parse(source);
					WeatherBean beanCurrentWeather = handler.getCurrentWeather();

					String[] current_weather = this.getResources().getStringArray(R.array.current_weather);
					String weather_output = String.format(current_weather[0], (int)longtitude + ", " + (int)latitude) + "\n"
							+ String.format(current_weather[1], beanCurrentWeather.getCondition()) + "\n"
							+ String.format(current_weather[2], beanCurrentWeather.getTemperature()) + "\n"
							+ String.format(current_weather[3], beanCurrentWeather.getHumidity()) + "\n"
							+ String.format(current_weather[4], beanCurrentWeather.getWind_condition());
					idWeatherInfo.setText(weather_output);
					idWeatherIcon.setImageBitmap(getHttpBitmap(("http://www.google.com")
							+ beanCurrentWeather.getIcon()));
					idWeatherIcon.setVisibility(View.VISIBLE);
				} catch (Exception e) {
					e.printStackTrace();
					Log.v(LOG_TAG, e.toString());
				}

			}
			if(resultCode == Activity.RESULT_CANCELED){
				AlertDialog dialog = new AlertDialog.Builder(this).create();
				dialog.setMessage("Return");
				dialog.setButton (AlertDialog.BUTTON_POSITIVE, this.getResources().getString(R.string.ok),new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				dialog.show();
			}
		}
	}



	public static Bitmap getHttpBitmap(String url) {
		URL myFileUrl = null;
		Bitmap bitmap = null;
		try {
			Log.d(LOG_TAG, url);
			myFileUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
			conn.setConnectTimeout(0);
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}
}