package edu.txstate.get26.carrentalapp;

import org.json.JSONException;
import org.json.JSONObject;

public class Car {
    private int id;
    private String make;
    private String model;
    private String color;
    private double dailyRate;
    private String url;
    private int image;

    public Car() {
    }

    public Car(int id, String make, String model, String color, double dailyRate, String url, int image) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.color = color;
        this.dailyRate = dailyRate;
        this.url = url;
        this.image = image;
    }

    Car(JSONObject object) {
        try {
            this.id = object.getInt("Id");
            this.make = object.getString("Make");
            this.model = object.getString("Model");
            this.color = object.getString("Color");
            this.dailyRate = object.getDouble("DailyRate");
            this.url = object.getString("Url");
            this.image = object.getInt("Img");
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return this.make + " " + this.model;
    }
}
