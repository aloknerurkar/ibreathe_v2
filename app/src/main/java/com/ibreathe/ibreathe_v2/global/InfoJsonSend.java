package com.ibreathe.ibreathe_v2.global;

import android.content.Context;
import android.location.Location;
import android.telephony.TelephonyManager;

import com.ibreathe.ibreathe_v2.models.EventObject;
import com.ibreathe.ibreathe_v2.models.EventType;
import com.ibreathe.ibreathe_v2.models.EventsMetaActive;
import com.ibreathe.ibreathe_v2.models.EventsMetaMeetup;
import com.ibreathe.ibreathe_v2.models.GrpType;
import com.ibreathe.ibreathe_v2.models.OrgType;
import com.ibreathe.ibreathe_v2.models.ResultsByVenue;
import com.ibreathe.ibreathe_v2.models.UserType;
import com.ibreathe.ibreathe_v2.models.VenueType;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;


/**
 * Class used to send JSON messages to the server. Implement other messages here as required.
 */

public class InfoJsonSend {

	// Latitude value
	double Latitude;
	// Longitude value
	double Longitude;
	// Storing jsonOutput to send
	String jsonOutput;
	TelephonyManager cellInfo;
	// Storing IMEI no
	String IMEINumber;

    String URL;



	/**
	 * Constructor for InfoJsonSend
	 * @param context   Application Context
	 * @param URL   URL to send to
     */

	public InfoJsonSend(Context context, String URL) {
		cellInfo = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		IMEINumber = cellInfo.getDeviceId();
        this.URL = URL;

	}

    public EventObject[] getEvents(){

        DefaultHttpClient client = new DefaultHttpClient();
        HttpEntity httpEntity = null;
        HttpResponse httpResponse = null;
        URI allEventsURL;
        HttpGet getAll;
        String response = null;
        JSONObject jsonObj;
        JSONArray events = null;
        EventObject[] eventList;

        try {
            allEventsURL = new URI(URL);
            getAll = new HttpGet(allEventsURL);
            httpResponse = client.execute(getAll);

            httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);

        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        try {
            events = new JSONArray(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int id = 0;
        String venue_name = null;
        String venue_address = null;
        Double venue_latitude = null;
        Double venue_longitude = null;
        String dateTime = null;
        int sport_cat = 0;
        int min_players = 0;
        int max_players = 0;
        Double cost = null;

        eventList = new EventObject[events.length()];

        for (int i = 0; i < events.length(); i++){
            try {
                jsonObj = events.getJSONObject(i);
                id = jsonObj.getInt("id");
                venue_name = jsonObj.getString("venue_name");
                venue_address = jsonObj.getString("venue_address");
                venue_latitude = jsonObj.getDouble("venue_latitude");
                venue_longitude = jsonObj.getDouble("venue_longitude");
                dateTime = jsonObj.getString("date");
                sport_cat = jsonObj.getInt("sport");
                min_players = jsonObj.getInt("min_players");
                max_players = jsonObj.getInt("max_players");
                cost = jsonObj.getDouble("cost");

                EventObject n_event = new EventObject(id, venue_name, venue_address,
                        venue_latitude,venue_longitude,dateTime,
                        sport_cat,min_players,max_players,cost);
                eventList[i] = n_event;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return eventList;
    }

    public UserType createUser(UserType u){

        DefaultHttpClient httpClient = new DefaultHttpClient();

        HttpResponse response = null;
        String createUserURL = URL;

        JSONObject user = new JSONObject();
        JSONObject userResp = new JSONObject();
        UserType userObj;

        try {
            user.put("name",u.user_name);
            user.put("email",u.email);
            user.put("mobile",u.mobile);
            user.put("gcm_reg_id",u.gcm_reg_id);

            jsonOutput = user.toString();

        } catch (JSONException e) {
            return null;
        }


        URI theURI;
        try {
            theURI = new URI(createUserURL);
        } catch (URISyntaxException e) {
            return null;
        }

        HttpPost post = new HttpPost(theURI);

        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonOutput);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        post.setEntity(entity);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");
        try {
            response = httpClient.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response != null) {
            try {
                userResp = new JSONObject(response.getEntity().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        userObj = new UserType();

            try {
                userObj.user_id = userResp.getString("user_uid");
                userObj.org_id = userResp.getString("organizer_id");
                userObj.user_name = userResp.getString("name");
                userObj.email = userResp.getString("email");
                userObj.mobile = userResp.getString("mobile");
                userObj.gcm_reg_id = userResp.getString("gcm_reg_id");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        return userObj;
    }

    public ResultsByVenue[] getEvents(Location location) throws JSONException {

        DefaultHttpClient client = new DefaultHttpClient();
        HttpEntity httpEntity = null;
        HttpResponse httpResponse = null;
        URI locationEventsURL;
        HttpGet getLocation;
        String response = null;
        JSONObject jsonObj;
        JSONArray results = null;
        ResultsByVenue[] resultList = null;
        String locURL = URL+"?";
        List<NameValuePair> params = new LinkedList<NameValuePair>();

        if (location.getLatitude() != 0.0 && location.getLongitude() != 0.0){
            params.add(new BasicNameValuePair("latitude", String.valueOf(location.getLatitude())));
            params.add(new BasicNameValuePair("longitude", String.valueOf(location.getLongitude())));
        }

        String paramString = URLEncodedUtils.format(params, "utf-8");

        locURL += paramString;

        try {
            //locationEventsURL = new URI(locURL);
            getLocation = new HttpGet(locURL);
            httpResponse = client.execute(getLocation);

            httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        try {
            JSONObject temp = new JSONObject(response);
            results = temp.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (results != null) {
            resultList = new ResultsByVenue[results.length()];

            for (int i = 0; i < results.length(); i++ ) {
                jsonObj = results.getJSONObject(i);
                resultList[i] = new ResultsByVenue();
                resultList[i].mVenue = fillVenue(jsonObj.getJSONObject("venue"));
                if (!jsonObj.getJSONObject("events").isNull("events")) {
                    resultList[i].mEvents = fillEvents(jsonObj.
                            getJSONObject("events").
                            getJSONArray("events"),resultList[i].mVenue);
                }

            }
        }

        return resultList;
    }

    public EventType[] getEvents(OrgType orgType) throws JSONException {

        DefaultHttpClient client = new DefaultHttpClient();
        HttpEntity httpEntity = null;
        HttpResponse httpResponse = null;
        HttpGet getOrg;
        String response = null;
        JSONObject jsonObj;
        JSONArray results = null;
        EventType[] resultList = null;
        String orgURL = URL+"?";
        List<NameValuePair> params = new LinkedList<NameValuePair>();

        params.add(new BasicNameValuePair("org_name", orgType.getOrg_name()));
        params.add(new BasicNameValuePair("contact_name", orgType.getContact_name()));

        String paramString = URLEncodedUtils.format(params, "utf-8");

        orgURL += paramString;

        try {
            getOrg = new HttpGet(orgURL);
            httpResponse = client.execute(getOrg);

            httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        try {
            JSONObject temp = new JSONObject(response);
            results = temp.getJSONArray("events");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (results != null) {
            resultList = new EventType[results.length()];

            for (int i = 0; i < results.length(); i++ ) {
                jsonObj = results.getJSONObject(i);
                VenueType v = fillVenue(jsonObj.getJSONObject("venue"));
                resultList[i] = new EventType();
                resultList[i] = fillEvent(jsonObj,v);
            }
        }

        return resultList;
    }

    public VenueType fillVenue(JSONObject venue) {

        VenueType return_venue = new VenueType();

        try {
            return_venue.venue_uid = venue.getString("venue_uid");
            return_venue.venue_name = venue.getString("venue_name");
            return_venue.venue_address_1 = venue.getString("address_1");
            return_venue.venue_address_2 = venue.getString("address_2");
            return_venue.venue_city = venue.getString("city");
            return_venue.venue_state = venue.getString("state");
            return_venue.zip = venue.getString("zip");
            return_venue.latitude = venue.getString("latitude");
            return_venue.longitude = venue.getString("longitude");
            return_venue.domain = venue.getString("domain");
            return_venue.domain_id = venue.getString("domain_id");
            return_venue.sport_cat_map = venue.getInt("sport_cat_map");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return return_venue;
    }

    public EventType[] fillEvents(JSONArray events, VenueType venue){

        EventType[] return_events = new EventType[events.length()];

        for (int i = 0; i < events.length(); i++){
            try {
                return_events[i] = new EventType();
                return_events[i].event_uid = events.getJSONObject(i).getString("event_uid");
                return_events[i].event_name = events.getJSONObject(i).getString("event_name");
                return_events[i].event_url = events.getJSONObject(i).getString("event_url");
                return_events[i].event_start = events.getJSONObject(i).getString("event_start");
                return_events[i].event_end = events.getJSONObject(i).getString("event_end");
                return_events[i].venue = venue;
                return_events[i].domain = events.getJSONObject(i).getString("domain");
                return_events[i].domain_id = events.getJSONObject(i).getString("domain_id");
                return_events[i].org = fillOrg(events.getJSONObject(i).getJSONObject("org"));
                return_events[i].grp = fillGrp(events.getJSONObject(i).getJSONObject("group"));
                return_events[i].desc = events.getJSONObject(i).getString("desc");
                return_events[i].sport_category = events.getJSONObject(i).getInt("sport_category");
                return_events[i].event_category = events.getJSONObject(i).getInt("event_category");
                return_events[i].ema = fillEMA(events.getJSONObject(i).getJSONObject("ema"));
                return_events[i].emm = fillEMM(events.getJSONObject(i).getJSONObject("emm"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return return_events;
    }

    public EventType fillEvent(JSONObject event, VenueType venue){

        EventType return_event = new EventType();


        try {
            return_event.event_uid = event.getString("event_uid");
            return_event.event_name = event.getString("event_name");
            return_event.event_url = event.getString("event_url");
            return_event.event_start = event.getString("event_start");
            return_event.event_end = event.getString("event_end");
            return_event.venue = venue;
            return_event.domain = event.getString("domain");
            return_event.domain_id = event.getString("domain_id");
            return_event.org = fillOrg(event.getJSONObject("org"));
            return_event.grp = fillGrp(event.getJSONObject("group"));
            return_event.desc = event.getString("desc");
            return_event.sport_category = event.getInt("sport_category");
            return_event.event_category = event.getInt("event_category");
            return_event.ema = fillEMA(event.getJSONObject("ema"));
            return_event.emm = fillEMM(event.getJSONObject("emm"));

        } catch (JSONException e) {
                e.printStackTrace();
        }

        return return_event;
    }

    public OrgType fillOrg(JSONObject org){

        OrgType return_org = new OrgType();

        try {
            return_org.org_uid = org.getString("organization_uuid");
            return_org.org_name = org.getString("org_name");
            return_org.org_url = org.getString("org_url");
            return_org.address_1 = org.getString("address_1");
            return_org.address_2 = org.getString("address_2");
            return_org.city = org.getString("city");
            return_org.state = org.getString("state");
            return_org.zip = org.getString("zip");
            return_org.contact_email = org.getString("contact_email");
            return_org.contact_ph = org.getString("contact_ph");
            return_org.contact_name = org.getString("contact_name");
        } catch (JSONException e){
            e.printStackTrace();
        }
        return return_org;
    }

    public GrpType fillGrp(JSONObject grp){

        GrpType return_grp = new GrpType();

        try {
            return_grp.group_id = grp.getInt("group_id");
            return_grp.group_name = grp.getString("group_name");
            return_grp.group_lat = grp.getString("group_lat");
            return_grp.group_lon = grp.getString("group_lon");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return return_grp;
    }

    public EventsMetaActive fillEMA(JSONObject ema){

        EventsMetaActive return_ema = new EventsMetaActive();

        try {
            return_ema.event_uuid = ema.getString("event_uuid");
            return_ema.sales_start = ema.getString("sales_start");
            return_ema.sales_end = ema.getString("sales_end");
            return_ema.contact_email = ema.getString("primary_contact_email");
            return_ema.contact_ph = ema.getString("primary_contact_ph");
            return_ema.contact_name = ema.getString("primary_contact_name");
            return_ema.total_capacity = ema.getString("event_uuid");
            return_ema.sold = ema.getString("event_uuid");
            return_ema.available = ema.getString("event_uuid");
            return_ema.cost = ema.getString("event_uuid");
            return_ema.min_age = ema.getString("event_uuid");
            return_ema.max_age = ema.getString("event_uuid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return return_ema;
    }

    public EventsMetaMeetup fillEMM(JSONObject emm){

        EventsMetaMeetup return_emm = new EventsMetaMeetup();

        try {
            return_emm.event_id = emm.getString("event_id");
            return_emm.rsvp_limit = emm.getInt("rsvp_limit");
            return_emm.yes_rsvp = emm.getInt("yes_rsvp");
            return_emm.maybe_rsvp = emm.getInt("maybe_rsvp");
            return_emm.duration = emm.getInt("duration");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return return_emm;
    }

    public int postEvent(EventType eventType) {

        HttpResponse response = null;
        String postEventURL = URL;

        JSONObject event = new JSONObject();
        JSONObject venue = new JSONObject();
        JSONObject org = new JSONObject();

        try {
            venue.put("venue_uid",eventType.venue.venue_uid);
            venue.put("venue_name",eventType.venue.venue_name);
            venue.put("address_1",eventType.venue.venue_address_1);
            venue.put("address_2",eventType.venue.venue_address_2);
            venue.put("city",eventType.venue.venue_city);
            venue.put("state",eventType.venue.venue_state);
            venue.put("zip",eventType.venue.zip);
            venue.put("latitude",eventType.venue.latitude);
            venue.put("longitude",eventType.venue.longitude);
            venue.put("domain",eventType.venue.domain);
            venue.put("domain_id",eventType.venue.domain_id);
            org.put("organization_uuid","");
            org.put("org_name",eventType.org.org_name);
            org.put("contact_email",eventType.org.contact_email);
            org.put("contact_name",eventType.org.contact_name);
            org.put("contact_ph",eventType.org.contact_ph);
            event.put("event_name", eventType.event_name);
            event.put("event_start", eventType.event_start);
            event.put("venue_uid", eventType.venue.venue_uid);
            event.put("desc", eventType.event_name);
            event.put("sport_category", eventType.sport_category);
            event.put("event_category", eventType.event_category);
            event.put("venue", venue);
            event.put("org", org);

            jsonOutput = event.toString();

        } catch (JSONException e) {
            return -1;
        }


        URI theURI;
        try {
            theURI = new URI(postEventURL);
        } catch (URISyntaxException e) {
            return -1;
        }

        HttpPost post = new HttpPost(theURI);

        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonOutput);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        post.setEntity(entity);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");
        DefaultHttpClient httpClient = new DefaultHttpClient();
        try {
            response = httpClient.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response != null)
            System.out.println(response.getStatusLine().toString());

        return 1;
    }
}