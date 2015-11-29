package com.ibreathe.ibreathe_v2.models;

import java.io.Serializable;

/**
 * Created by aloknerurkar on 6/29/15.
 */
public class VenueType implements Serializable {
    public String venue_uid;
    public String venue_name;
    public String venue_address_1;
    public String venue_address_2;
    public String venue_city;
    public String venue_state;
    public String zip;
    public String latitude;
    public String longitude;
    public String domain;
    public String domain_id;
    public long sport_cat_map;

    public String getVenue_uid() {
        return venue_uid;
    }

    public void setVenue_uid(String venue_uid) {
        this.venue_uid = venue_uid;
    }

    public String getVenue_name() {
        return venue_name;
    }

    public void setVenue_name(String venue_name) {
        this.venue_name = venue_name;
    }

    public String getVenue_address_1() {
        return venue_address_1;
    }

    public void setVenue_address_1(String venue_address_1) {
        this.venue_address_1 = venue_address_1;
    }

    public String getVenue_address_2() {
        return venue_address_2;
    }

    public void setVenue_address_2(String venue_address_2) {
        this.venue_address_2 = venue_address_2;
    }

    public String getVenue_city() {
        return venue_city;
    }

    public void setVenue_city(String venue_city) {
        this.venue_city = venue_city;
    }

    public String getVenue_state() {
        return venue_state;
    }

    public void setVenue_state(String venue_state) {
        this.venue_state = venue_state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDomain_id() {
        return domain_id;
    }

    public void setDomain_id(String domain_id) {
        this.domain_id = domain_id;
    }

    public long getSport_cat_map() {
        return sport_cat_map;
    }

    public void setSport_cat_map(long sport_cat_map) {
        this.sport_cat_map = sport_cat_map;
    }
}