package com.challenge.cities.util.DTO;

public class DistanceInfo {
    private String firstCity;
    private String secondCity;
    private Double distance;

    public DistanceInfo(String firstCity, String secondCity, Double distance) {
        this.firstCity = firstCity;
        this.secondCity = secondCity;
        this.distance = distance;
    }

    public String getFirstCity() {
        return firstCity;
    }

    public void setFirstCity(String firstCity) {
        this.firstCity = firstCity;
    }

    public String getSecondCity() {
        return secondCity;
    }

    public void setSecondCity(String secondCity) {
        this.secondCity = secondCity;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
