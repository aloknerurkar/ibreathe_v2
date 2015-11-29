package com.ibreathe.ibreathe_v2.models;

import java.io.Serializable;

/**
 * Created by aloknerurkar on 6/29/15.
 */
public class EventType implements Serializable {
    public String event_uid;
    public String event_name;
    public String event_url;
    public String event_start;
    public String event_end;
    public VenueType venue;
    public String domain;
    public String domain_id;
    public String domain_owner_id;
    public String desc;
    public int sport_category;
    public int event_category;
    public OrgType org;
    public GrpType grp;
    public EventsMetaActive ema;
    public EventsMetaMeetup emm;

    public String getEvent_uid() {
        return event_uid;
    }

    public void setEvent_uid(String event_uid) {
        this.event_uid = event_uid;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_url() {
        return event_url;
    }

    public void setEvent_url(String event_url) {
        this.event_url = event_url;
    }

    public String getEvent_start() {
        return event_start;
    }

    public void setEvent_start(String event_start) {
        this.event_start = event_start;
    }

    public String getEvent_end() {
        return event_end;
    }

    public void setEvent_end(String event_end) {
        this.event_end = event_end;
    }

    public VenueType getVenue() {
        return venue;
    }

    public void setVenue(VenueType venue) {
        this.venue = venue;
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

    public String getDomain_owner_id() {
        return domain_owner_id;
    }

    public void setDomain_owner_id(String domain_owner_id) {
        this.domain_owner_id = domain_owner_id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public OrgType getOrg() {
        return org;
    }

    public void setOrg(OrgType org) {
        this.org = org;
    }

    public GrpType getGrp() {
        return grp;
    }

    public void setGrp(GrpType grp) {
        this.grp = grp;
    }

    public EventsMetaActive getEma() {
        return ema;
    }

    public void setEma(EventsMetaActive ema) {
        this.ema = ema;
    }

    public EventsMetaMeetup getEmm() {
        return emm;
    }

    public void setEmm(EventsMetaMeetup emm) {
        this.emm = emm;
    }

    public int getEvent_category() {
        return event_category;
    }

    public void setEvent_category(int event_category) {
        this.event_category = event_category;
    }

    public int getSport_category() {
        return sport_category;
    }

    public void setSport_category(int sport_category) {
        this.sport_category = sport_category;
    }
}