package com.ibreathe.ibreathe_v2.home;

/**
 * Created by aloknerurkar on 3/27/15.
 */

import com.ibreathe.ibreathe_v2.models.EventType;

import java.util.ArrayList;
import java.util.List;

public class ListGroup {

    public int icon;
    public String string;
    public String string2;
    public String string3;
    public String string4;
    public EventType event;
    public final List<String> children = new ArrayList<String>();

    public ListGroup(int icon, String string, String string2) {
        this.icon = icon;
        this.string = string;
        this.string2 = string2;
    }

    public ListGroup(int icon, String string, String string2, String string3) {
        this.icon = icon;
        this.string = string;
        this.string2 = string2;
        this.string3 = string3;
    }

    public ListGroup(String string, String string2, String string3, String string4) {
        this.string = string;
        this.string2 = string2;
        this.string3 = string3;
        this.string4 = string4;
    }

    public ListGroup(String string, String string2, String string3, String string4, EventType event) {
        this.string = string;
        this.string2 = string2;
        this.string3 = string3;
        this.string4 = string4;
        this.event = new EventType();
        this.event = event;
    }

}