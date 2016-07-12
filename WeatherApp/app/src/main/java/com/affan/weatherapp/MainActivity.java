package com.affan.weatherapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.affan.weatherapp.adapters.City;
import com.affan.weatherapp.adapters.CustomListAdapter;
import com.affan.weatherapp.parser.JSONWeatherParser;
import com.affan.weatherapp.parser.WeatherHttpClient;
import com.affan.weatherapp.weatherutils.Weather;

import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<City> cityList = new ArrayList<City>();
    private ListView listView;
    Toolbar toolbar;
    private ProgressDialog pDialog;
    int dataCheck = 0;
    private CustomListAdapter adapter;
    private String[] cities = new String[]{"Barcelona,Spain", "Madrid,Spain" ,"Islamabad,Pakistan", "Dubai,UAE", "AbuDhabi,UAE" , "London,UK", "NewYork,USA", "Chicago,USA" , "Toronto,Canada", "Sydney,Australia"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
        listView = (ListView) findViewById(R.id.list);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Fetching Forecast Data");
        pDialog.show();
        if(isOnline())
        {
            if (isInternetWorking()) {
                adapter = new CustomListAdapter(this, cityList);
                listView.setAdapter(adapter);
                JSONWeatherTask task;
                for (String city : cities) {
                    task = new JSONWeatherTask();
                    task.execute((new String[]{city}));
                }
            }
            else
            {
                hidePDialog();
                Snackbar.make(listView, "Unable to fetch forecast data", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
        else
        {
            hidePDialog();
            Snackbar.make(listView, "No Internet Connection", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Weather doInBackground(String... params) {
            Weather weather = new Weather();
            String data = ( (new WeatherHttpClient()).getWeatherData(params[0], MainActivity.this));

            try {
                weather = JSONWeatherParser.getWeather(data);
                weather.iconData = ( (new WeatherHttpClient()).getImage(weather.currentCondition.getIcon(), MainActivity.this));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weather;

        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);
            City city = new City();
            if (weather.iconData != null && weather.iconData.length > 0) {
                Bitmap img = BitmapFactory.decodeByteArray(weather.iconData, 0, weather.iconData.length);
                city.setThumbnailUrl(img);
            }
            else
            {
                if (weather.currentCondition.getIcon() != null)
                {
                    String icon = weather.currentCondition.getIcon();
                    city.setIconText(icon);
                }
            }
            String data = weather.location.getCity() + "," + weather.location.getCountry();
            city.setLocation(data);
            data = weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescr() + ")";
            city.setCond(data);
            cityList.add(city);
            adapter.notifyDataSetChanged();
            dataCheck++;
            if (dataCheck == cities.length)
            {
                hidePDialog();
                listView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }

    public static boolean isInternetWorking() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        boolean success = false;
        try {
            URL url = new URL("https://google.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(10000);
            connection.connect();
            success = connection.getResponseCode() == 200;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
