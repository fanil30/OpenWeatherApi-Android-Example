package com.affan.weatherapp.parser;

import android.content.Context;

import com.affan.weatherapp.R;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherHttpClient {

	private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
	private static String IMG_URL = "http://openweathermap.org/img/w/";

	
	public String getWeatherData(String location, Context c) {
		HttpURLConnection con = null ;
		InputStream is = null;

		try {
			con = (HttpURLConnection) ( new URL(BASE_URL + location)).openConnection();
			con.addRequestProperty("x-api-key", c.getString(R.string.open_weather_maps_app_id));
			con.setRequestMethod("GET");
			con.setDoInput(true);
			con.setDoOutput(true);
			con.connect();

			StringBuffer buffer = new StringBuffer();
			is = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while (  (line = br.readLine()) != null )
				buffer.append(line + "\r\n");
			
			is.close();
			con.disconnect();
			return buffer.toString();
	    }
		catch(Throwable t) {
			t.printStackTrace();
		}
		finally {
			try { is.close(); } catch(Throwable t) {}
			try { con.disconnect(); } catch(Throwable t) {}
		}

		return null;
				
	}
	
	public byte[] getImage(String code, Context c) {
		HttpURLConnection con = null ;
		InputStream is = null;
		try {
			con = (HttpURLConnection) ( new URL(IMG_URL + code + ".png")).openConnection();
			con.addRequestProperty("x-api-key", c.getString(R.string.open_weather_maps_app_id));
			con.setRequestMethod("GET");
			con.setDoInput(true);
			con.setDoOutput(true);
			con.connect();
			is = con.getInputStream();
			byte[] buffer = new byte[1024];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			while ( is.read(buffer) != -1)
				baos.write(buffer);
			
			return baos.toByteArray();
	    }
		catch(Throwable t) {
			t.printStackTrace();
		}
		finally {
			try { is.close(); } catch(Throwable t) {}
			try { con.disconnect(); } catch(Throwable t) {}
		}
		
		return null;
		
	}
}
