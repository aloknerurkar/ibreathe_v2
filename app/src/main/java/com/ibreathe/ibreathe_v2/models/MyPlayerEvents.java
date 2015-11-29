package com.ibreathe.ibreathe_v2.models;

/**
 * Created by aloknerurkar on 3/22/15.
 */
public class MyPlayerEvents {

    String event_name;
    String event_type_game;
    String event_type_sport;
    String event_type_visibility;
    String location;
    int event_type_cost;
    String event_date_time;
    String created_at;
    long id;

    // constructors
    public MyPlayerEvents() {
    }

    public MyPlayerEvents(String event_name, String event_type_game,
                          String event_type_sport, String event_type_visibility,
                          String location, int event_type_cost,
                          String event_date_time) {
        this.event_name = event_name;
        this.event_type_game = event_type_game;
        this.event_type_sport = event_type_sport;
        this.event_type_visibility = event_type_visibility;
        this.location = location;
        this.event_type_cost = event_type_cost;
        this.event_date_time = event_date_time;
    }



    // setters
    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public void setEvent_type_game(String event_type_game) {
        this.event_type_game = event_type_game;
    }

    public void setEvent_type_sport(String event_type_sport) {
        this.event_type_sport = event_type_sport;
    }

    public void setEvent_type_visibility(String event_type_visibility) {
        this.event_type_visibility = event_type_visibility;
    }

    public void setEvent_date_time(String event_date_time) {
        this.event_date_time = event_date_time;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setEvent_type_cost(int event_type_cost) {
        this.event_type_cost = event_type_cost;
    }

    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }

    // getters
    public String getEvent_name() {
        return event_name;
    }

    public String getEvent_type_game() {
        return event_type_game;
    }

    public String getEvent_type_sport() {
        return event_type_sport;
    }

    public String getEvent_type_visibility() {
        return event_type_visibility;
    }

    public int getEvent_type_cost() {
        return event_type_cost;
    }

    public String getLocation() {
        return location;
    }

    public String getEvent_date_time() {
        return event_date_time;
    }

    public String getCreated_at() {
        return created_at;
    }


    public long getId() {
        return id;
    }
}