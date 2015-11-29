package com.ibreathe.ibreathe_v2.models;

import java.io.Serializable;

/**
 * Created by aloknerurkar on 6/29/15.
 */
public class EventsMetaActive implements Serializable {
    public String event_uuid;
    public String sales_start;
    public String sales_end;
    public String contact_email;
    public String contact_ph;
    public String contact_name;
    public String total_capacity;
    public String sold;
    public String available;
    public String cost;
    public String org_uuid;
    public String venue_uuid;
    public String min_age;
    public String max_age;
    public String freq_name;

    public String getEvent_uuid() {
        return event_uuid;
    }

    public void setEvent_uuid(String event_uuid) {
        this.event_uuid = event_uuid;
    }

    public String getSales_start() {
        return sales_start;
    }

    public void setSales_start(String sales_start) {
        this.sales_start = sales_start;
    }

    public String getSales_end() {
        return sales_end;
    }

    public void setSales_end(String sales_end) {
        this.sales_end = sales_end;
    }

    public String getContact_email() {
        return contact_email;
    }

    public void setContact_email(String contact_email) {
        this.contact_email = contact_email;
    }

    public String getContact_ph() {
        return contact_ph;
    }

    public void setContact_ph(String contact_ph) {
        this.contact_ph = contact_ph;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getTotal_capacity() {
        return total_capacity;
    }

    public void setTotal_capacity(String total_capacity) {
        this.total_capacity = total_capacity;
    }

    public String getSold() {
        return sold;
    }

    public void setSold(String sold) {
        this.sold = sold;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getOrg_uuid() {
        return org_uuid;
    }

    public void setOrg_uuid(String org_uuid) {
        this.org_uuid = org_uuid;
    }

    public String getVenue_uuid() {
        return venue_uuid;
    }

    public void setVenue_uuid(String venue_uuid) {
        this.venue_uuid = venue_uuid;
    }

    public String getMin_age() {
        return min_age;
    }

    public void setMin_age(String min_age) {
        this.min_age = min_age;
    }

    public String getMax_age() {
        return max_age;
    }

    public void setMax_age(String max_age) {
        this.max_age = max_age;
    }

    public String getFreq_name() {
        return freq_name;
    }

    public void setFreq_name(String freq_name) {
        this.freq_name = freq_name;
    }
}