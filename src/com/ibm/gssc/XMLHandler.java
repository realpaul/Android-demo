package com.ibm.gssc;

import org.xml.sax.helpers.*;
import org.xml.sax.*;

public class XMLHandler extends DefaultHandler {
    private static final String TAG = "XMLHandler";
    private WeatherBean currentWeather;
    private boolean currentFlag;
    
    public void setCurrentWeather(WeatherBean currentWeather){
    	this.currentWeather = currentWeather;
    }
    public WeatherBean getCurrentWeather(){
    	return currentWeather;
    }
  
    public XMLHandler() {
    	currentFlag = false;        
    }
    
    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {    	
        
        String tagName = localName.length() != 0 ? localName : qName;
        tagName = tagName.toLowerCase();
       
        //Log.d(TAG,"tagName = "+tagName);
        if(tagName.equals("current_conditions")){
        	currentFlag = true;
        	currentWeather = new WeatherBean();        	
        }
        
        if(currentFlag){
        	if(tagName.equals("condition")){
        		//Log.d(TAG,"condition = "+attributes.getValue("data"));
        		currentWeather.setCondition(attributes.getValue("data"));
        	}else if(tagName.equals("temp_c")){
        		//Log.d(TAG,"temp_c--------");
        		currentWeather.setTemperature(attributes.getValue("data"));
        	}else if(tagName.equals("humidity")){
        		currentWeather.setHumidity(attributes.getValue("data"));
        	}else if(tagName.equals("icon")){
        		currentWeather.setIcon(attributes.getValue("data"));
        	}else if(tagName.equals("wind_condition")){
        		currentWeather.setWind_condition(attributes.getValue("data"));
        	}
        }
        
        
    }
    
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {    	
        String tagName = localName.length() != 0 ? localName : qName;
        tagName = tagName.toLowerCase();
        
        if(tagName.equals("current_conditions")) {        	
            currentFlag = false;           
        }
    }
}