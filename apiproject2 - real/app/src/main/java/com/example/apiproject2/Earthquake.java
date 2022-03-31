package com.example.apiproject2;

public class Earthquake {

    private Double mmagnitude;
    private String mlocation;
    private Long mDate;
    private String mUrl;


    public Earthquake(double magnitude, String location, Long date, String url){
        mmagnitude=magnitude;
        mlocation=location;
        mDate=date;
        mUrl = url;

    }

    public Earthquake(double mag, String place, Long date) {
    }

    public double getMmagnitude() {
        return mmagnitude;
    }

    public String getMlocation() {
        return mlocation;
    }

    public Long getmDate() {
        return mDate;
    }
    public String getUrl() {
        return mUrl;
    }

}
