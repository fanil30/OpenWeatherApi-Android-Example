package com.affan.weatherapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.affan.weatherapp.R;

import java.util.List;

/**
 * Created by Affan on 29/06/2016.
 */
public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<City> cityItems;

    public CustomListAdapter(Activity activity, List<City> cItems) {
        this.activity = activity;
        this.cityItems = cItems;
    }

    @Override
    public int getCount() {
        return cityItems.size();
    }

    @Override
    public Object getItem(int location) {
        return cityItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);


        ImageView imageView = (ImageView) convertView
                .findViewById(R.id.thumbnail);
        Typeface weatherFont = Typeface.createFromAsset(activity.getAssets(), "weather.ttf");

        TextView icon = (TextView) convertView.findViewById(R.id.txtIcon);
        TextView title = (TextView) convertView.findViewById(R.id.location);
        TextView genre = (TextView) convertView.findViewById(R.id.condition);

        title.setTypeface(weatherFont);
        genre.setTypeface(weatherFont);

        City m = cityItems.get(position);
        if (m.getThumbnailUrl() !=  null)
        {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageBitmap(m.getThumbnailUrl());
            icon.setVisibility(View.GONE);
        }
        else
        {
            icon.setTypeface(weatherFont);

            icon.setText(getWeatherFont(m.getIconText(), activity));
            imageView.setVisibility(View.GONE);
            icon.setVisibility(View.VISIBLE);
        }
        title.setText(m.getLocation());
        genre.setText("Weather: " + String.valueOf(m.getCond()));

        return convertView;
    }

    private String getWeatherFont(String des, Context c) {
        String icon = null;
        String f = Character.toString(des.charAt(0));
        String s = Character.toString(des.charAt(1));
        String l = Character.toString(des.charAt(2));
        if (f.equals("0") && s.equals("1"))
        {
            if (l.equals("n"))
                icon = c.getString(R.string.weather_clear_night);
            else
                icon = c.getString(R.string.weather_sunny);
        }
        else
        {
            f = f + s;
            switch(f) {
                case "02" : icon = c.getString(R.string.weather_cloudy);
                    break;
                case "03" : icon = c.getString(R.string.weather_cloudy);
                    break;
                case "04" : icon = c.getString(R.string.weather_cloudy);
                    break;
                case "09" : icon = c.getString(R.string.weather_rainy);
                    break;
                case "10" : icon = c.getString(R.string.weather_rainy);
                    break;
                case "11" : icon = c.getString(R.string.weather_thunder);
                    break;
                case "13" : icon = c.getString(R.string.weather_snowy);
                    break;
                case "50" : icon = c.getString(R.string.weather_foggy);
                    break;
            }
        }
        return icon;
    }

}