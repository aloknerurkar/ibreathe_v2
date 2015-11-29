package com.ibreathe.ibreathe_v2.models;

import java.io.Serializable;

/**
 * Created by aloknerurkar on 4/28/15.
 */
public class EventObject implements Serializable {

    int id;
    String venue_name;
    String venue_address;
    Double venue_latitude;
    Double venue_longitude;
    String dateTime;
    int sport_cat;
    int min_players;
    int max_players;
    double cost;

    public EventObject(int id, String venue_name,
                       String venue_address,
                       Double venue_latitude,
                       Double venue_longitude,
                       String dateTime, int sport_cat,
                       int min_players, int max_players, double cost) {
        this.id = id;
        this.venue_name = venue_name;
        this.venue_address = venue_address;
        this.venue_latitude = venue_latitude;
        this.venue_longitude = venue_longitude;
        this.dateTime = dateTime;
        this.sport_cat = sport_cat;
        this.min_players = min_players;
        this.max_players = max_players;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVenue_name() {
        return venue_name;
    }

    public void setVenue_name(String venue_name) {
        this.venue_name = venue_name;
    }

    public String getVenue_address() {
        return venue_address;
    }

    public void setVenue_address(String venue_address) {
        this.venue_address = venue_address;
    }

    public Double getVenue_latitude() {
        return venue_latitude;
    }

    public void setVenue_latitude(Double venue_latitude) {
        this.venue_latitude = venue_latitude;
    }

    public Double getVenue_longitude() {
        return venue_longitude;
    }

    public void setVenue_longitude(Double venue_longitude) {
        this.venue_longitude = venue_longitude;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getSport_cat() {
        return sport_cat;
    }

    public void setSport_cat(int sport_cat) {
        this.sport_cat = sport_cat;
    }

    public int getMin_players() {
        return min_players;
    }

    public void setMin_players(int min_players) {
        this.min_players = min_players;
    }

    public int getMax_players() {
        return max_players;
    }

    public void setMax_players(int max_players) {
        this.max_players = max_players;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
