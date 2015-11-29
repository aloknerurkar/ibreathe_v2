package com.ibreathe.ibreathe_v2.models;

import java.io.Serializable;

/**
 * Created by aloknerurkar on 6/29/15.
 */
public class GrpType implements Serializable {
    public int group_id;
    public String group_name;
    public String group_url;
    public String group_lat;
    public String group_lon;

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_url() {
        return group_url;
    }

    public void setGroup_url(String group_url) {
        this.group_url = group_url;
    }

    public String getGroup_lat() {
        return group_lat;
    }

    public void setGroup_lat(String group_lat) {
        this.group_lat = group_lat;
    }

    public String getGroup_lon() {
        return group_lon;
    }

    public void setGroup_lon(String group_lon) {
        this.group_lon = group_lon;
    }
}