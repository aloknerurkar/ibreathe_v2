package com.ibreathe.ibreathe_v2.home;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ibreathe.ibreathe_v2.R;
import com.ibreathe.ibreathe_v2.global.GlobalVariable;
import com.ibreathe.ibreathe_v2.global.SessionManager;
import com.ibreathe.ibreathe_v2.models.EventType;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class EventHome extends FragmentActivity {

    private GoogleMap map;
    private ShareActionProvider mShareActionProvider;
    public SessionManager sessionManager;
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_home);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        Date ev_dt = null;
        SimpleDateFormat dateFormat = null;
        SimpleDateFormat day = null;
        SimpleDateFormat showdate = null;
        GlobalVariable gv = GlobalVariable.getInstance();
        sessionManager = new SessionManager(getApplicationContext());
        user = sessionManager.getFBDetails().get("fb_name");

        Intent i = getIntent();
        final EventType event = (EventType)i.getSerializableExtra("event");

        LatLng eventMarker = new LatLng(Double.parseDouble(event.venue.getLatitude()),
                                        Double.parseDouble(event.venue.getLongitude()));

        map = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.map2)).getMap();
        int icon = gv.getIcon(event.sport_category);
        Marker eventLoc = map.addMarker(new MarkerOptions().position(eventMarker)
                .title("Event")
                .icon(BitmapDescriptorFactory.fromResource(icon)));

        // Move the camera instantly to hamburg with a zoom of 15.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(eventMarker, 15));

        // Zoom in, animating the camera.
        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

        if (event.domain.equals("ACTIVE")){
            dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            day = new SimpleDateFormat("EEEE");
            showdate = new SimpleDateFormat("dd-MMM hh:mm aa");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
                ev_dt = dateFormat.parse(event.event_start);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        TextView eventDateTime = (TextView)findViewById(R.id.event_dt);
        if (ev_dt != null) {
            eventDateTime.setText(day.format(ev_dt)+"\n"+showdate.format(ev_dt));
        }


        ExpandableTextView desc = (ExpandableTextView)findViewById(R.id.expand_text_view);
        if (event.desc != null) {
            desc.setText(event.desc);
        }


        final TextView eventMainTitle = (TextView)findViewById(R.id.eventMainTitle);
        TextView eventNoOfPlayers = (TextView)findViewById(R.id.noOfPlayers);
        TextView eventAddress = (TextView)findViewById(R.id.eventAddress);
        TextView contactText = (TextView)findViewById(R.id.contactText);
        eventMainTitle.setText(event.event_name);
        eventNoOfPlayers.setText(event.event_start+" people going");
        TextView eventPrice = (TextView)findViewById(R.id.eventPrice);
        eventPrice.setText("Free");
        eventAddress.setText(event.venue.getVenue_address_1()+"\n"+event.venue.getVenue_city()+" "+event.venue.getVenue_state()+" "+event.venue.getZip());
        contactText.setText(event.org.getOrg_name()+"\n"+event.org.contact_name+"\n"+event.org.contact_email);

        ImageButton addToCalendar = (ImageButton)findViewById(R.id.addToCalendar);
        addToCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calIntent = new Intent(Intent.ACTION_INSERT);
                calIntent.setType("vnd.android.cursor.item/event");
                calIntent.putExtra(Events.TITLE, eventMainTitle.getText());
                calIntent.putExtra(Events.EVENT_LOCATION, event.venue.getVenue_address_1());
                calIntent.putExtra(Events.DESCRIPTION, "FooBar");

                GregorianCalendar calDate = new GregorianCalendar(2012, 7, 15);
                calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
                calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                        calDate.getTimeInMillis());
                calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                        calDate.getTimeInMillis());

                startActivity(calIntent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.event_home, menu);
        MenuItem item = menu.findItem(R.id.menu_item_share);
        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) item.getActionProvider();
        mShareActionProvider.setShareIntent(getDefaultShareIntent());
        // Return true to display menu
        return true;
    }

    /** Returns a share intent */
    private Intent getDefaultShareIntent(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Join "+user+"for an event...");
        intent.putExtra(Intent.EXTRA_TEXT,user+" has invited you to join him for an event.");
        return intent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        googleMap.addMarker(new MarkerOptions()
//                .position(new LatLng(, 0))
//                .title("Marker"));
//    }
}
