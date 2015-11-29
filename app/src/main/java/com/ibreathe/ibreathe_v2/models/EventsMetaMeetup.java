package com.ibreathe.ibreathe_v2.models;

import java.io.Serializable;

/**
 * Created by aloknerurkar on 6/29/15.
 */
public class EventsMetaMeetup implements Serializable {
    public String event_id;
    public int rsvp_limit;
    public int yes_rsvp;
    public int maybe_rsvp;
    public int duration;

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public int getRsvp_limit() {
        return rsvp_limit;
    }

    public void setRsvp_limit(int rsvp_limit) {
        this.rsvp_limit = rsvp_limit;
    }

    public int getYes_rsvp() {
        return yes_rsvp;
    }

    public void setYes_rsvp(int yes_rsvp) {
        this.yes_rsvp = yes_rsvp;
    }

    public int getMaybe_rsvp() {
        return maybe_rsvp;
    }

    public void setMaybe_rsvp(int maybe_rsvp) {
        this.maybe_rsvp = maybe_rsvp;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}