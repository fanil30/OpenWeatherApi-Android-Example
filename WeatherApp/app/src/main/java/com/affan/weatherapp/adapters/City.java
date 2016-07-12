package com.affan.weatherapp.adapters;

import android.graphics.Bitmap;

/**
 * Created by Affan on 29/06/2016.
 */
public class City {
    private String location;
    private Bitmap thumbnailUrl;
    private String cond;
    private String icon;

    public City() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String name) {
        this.location = name;
    }

    public Bitmap getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(Bitmap thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getCond() {
        return cond;
    }

    public void setCond(String con) {
        this.cond = con;
    }

    public void setIconText(String icon) {
        this.icon = icon;
    }

    public String getIconText() {
        return icon;
    }
}