
package com.affan.weatherapp.parser;

import com.affan.weatherapp.weatherutils.Location;
import com.affan.weatherapp.weatherutils.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Affan on 29/06/2016.
 */
public class JSONWeatherParser {

	public static Weather getWeather(String data) throws JSONException  {
		Weather weather = new Weather();

		JSONObject jObj = new JSONObject(data);
		Location loc = new Location();
		
		JSONObject sysObj = getObject("sys", jObj);
		loc.setCountry(getString("country", sysObj));
		loc.setCity(getString("name", jObj));
		weather.location = loc;
		JSONArray jArr = jObj.getJSONArray("weather");
		JSONObject JSONWeather = jArr.getJSONObject(0);
		weather.currentCondition.setDescr(getString("description", JSONWeather));
		weather.currentCondition.setCondition(getString("main", JSONWeather));
		weather.currentCondition.setIcon(getString("icon", JSONWeather));
		
		
		return weather;
	}
	
	
	private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
		JSONObject subObj = jObj.getJSONObject(tagName);
		return subObj;
	}
	
	private static String getString(String tagName, JSONObject jObj) throws JSONException {
		return jObj.getString(tagName);
	}
	
}
