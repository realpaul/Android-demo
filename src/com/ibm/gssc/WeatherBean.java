package com.ibm.gssc;

public class WeatherBean {
	
	protected String temperature;
	
	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	protected String humidity;
	
	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	
	protected String condition;

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	protected String Icon;
	
	public String getIcon() {
		return Icon;
	}

	public void setIcon(String icon) {
		Icon = icon;
	}

	protected String wind_condition;
	
	public String getWind_condition() {
		return wind_condition;
	}

	public void setWind_condition(String wind_condition) {
		this.wind_condition = wind_condition;
	}
	
}
