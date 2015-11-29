package com.ibreathe.ibreathe_v2.models;

import java.io.Serializable;

/**
 * Created by aloknerurkar on 6/29/15.
 */
public class ResultsByVenue implements Serializable {

    public VenueType mVenue;
    public EventType[] mEvents;


    public VenueType getmVenue() {
        return mVenue;
    }

    public void setmVenue(VenueType mVenue) {
        this.mVenue = mVenue;
    }

    public EventType[] getmEvents() {
        return mEvents;
    }

    public void setmEvents(EventType[] mEvents) {
        this.mEvents = mEvents;
    }
}
