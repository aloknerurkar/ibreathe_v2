package com.ibreathe.ibreathe_v2.models;

import java.io.Serializable;

/**
 * Created by aloknerurkar on 6/29/15.
 */
public class OrgType implements Serializable {
    public String org_uid;
    public String org_name;
    public String org_url;
    public String address_1;
    public String address_2;
    public String city;
    public String state;
    public String zip;
    public String contact_email;
    public String contact_ph;
    public String contact_name;

    public String getOrg_uid() {
        return org_uid;
    }

    public void setOrd_uid(String org_uid) {
        this.org_uid = org_uid;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public String getOrg_url() {
        return org_url;
    }

    public void setOrg_url(String org_url) {
        this.org_url = org_url;
    }

    public String getAddress_1() {
        return address_1;
    }

    public void setAddress_1(String address_1) {
        this.address_1 = address_1;
    }

    public String getAddress_2() {
        return address_2;
    }

    public void setAddress_2(String address_2) {
        this.address_2 = address_2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
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
}