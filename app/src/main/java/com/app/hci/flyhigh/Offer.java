package com.app.hci.flyhigh;

/**
 * Created by leo on 25/06/17.
 */

public class Offer {

    String name;
    double latitude;
    double longitude;
    Double price;

    public Offer(String name, double price, double latitude, double longitude) {
        this.name = name;
        this.price = price;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getPriceString() {
        return "$ " + price.toString();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
