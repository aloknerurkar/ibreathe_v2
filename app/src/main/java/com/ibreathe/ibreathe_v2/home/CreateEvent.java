package com.ibreathe.ibreathe_v2.home;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.GraphRequestAsyncTask;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ibreathe.ibreathe_v2.R;
import com.ibreathe.ibreathe_v2.global.GlobalVariable;
import com.ibreathe.ibreathe_v2.global.InfoJsonSend;
import com.ibreathe.ibreathe_v2.global.SessionManager;
import com.ibreathe.ibreathe_v2.models.EventType;
import com.ibreathe.ibreathe_v2.models.OrgType;
import com.ibreathe.ibreathe_v2.models.VenueType;

import org.json.JSONObject;

import java.util.Calendar;

public class CreateEvent extends Activity {

    private int mYear, mMonth, mDay, mHour, mMinute;
    private ProgressDialog pDialog;
    InfoJsonSend jsonRequest;
    GlobalVariable gv = GlobalVariable.getInstance();
    private final String serverIP = gv.getCreateEvent();
    EventCreateTask mEventCreate;
    SessionManager sessionManager;
    MapFragment mapFragment;
    GraphRequestAsyncTask userInfo;
    private AccessTokenTracker accessTokenTracker;
    private CallbackManager callbackManager;

    JSONObject user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);

        sessionManager = new SessionManager(getApplicationContext());
        String tog, gtc, gt, sp_sp, minim, maxim;
        int cost = 0;

        Intent i = getIntent();
        final VenueType venue = (VenueType)i.getSerializableExtra("venue");
        final OrgType organizer = (OrgType)i.getSerializableExtra("org");

        final EditText event_name = (EditText)findViewById(R.id.event_name);
        final EditText event_desc = (EditText)findViewById(R.id.event_desc);
        final EditText date = (EditText)findViewById(R.id.date);
        final EditText time = (EditText)findViewById(R.id.time);

        LinearLayout mapView = (LinearLayout)findViewById(R.id.mapViewCreateEvent);

        mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.mapCreate);
        GoogleMap map = mapFragment.getMap();
        if (venue != null) {
            LatLng eventMarker = new LatLng(
                    Double.parseDouble(venue.latitude),
                    Double.parseDouble(venue.longitude));
            Marker eventLoc = map.addMarker(new MarkerOptions().position(eventMarker)
                    .title(venue.venue_name)
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.soccer)));

            // Move the camera instantly to hamburg with a zoom of 15.
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(eventMarker, 15));

            // Zoom in, animating the camera.
            map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        } else {
            mapView.setVisibility(View.GONE);
        }

        // Launch Date Picker Dialog
        final DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Display Selected date in textbox
                        date.setText(dayOfMonth + "-"
                                + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);

        final TimePickerDialog tpd = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        // Display Selected time in textbox
                        time.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);

        date.setClickable(true);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                dpd.show();
            }
        });

        time.setClickable(true);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                // Launch Time Picker Dialog
                tpd.show();
            }
        });

        Spinner gameTypeCost = (Spinner)findViewById(R.id.gameTypeCost);
        final Spinner typeOfGame = (Spinner)findViewById(R.id.typeOfGame);
        Spinner gameType = (Spinner)findViewById(R.id.gameType);
        Spinner sport_spinner = (Spinner)findViewById(R.id.sport_spinner);
        Spinner min = (Spinner)findViewById(R.id.MinPlayers);
        Spinner max = (Spinner)findViewById(R.id.MaxPLayers);

        Button create = (Button)findViewById(R.id.createEvent);


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_1 = ArrayAdapter.createFromResource(this,
                R.array.gameType, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter_1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        gameType.setAdapter(adapter_1);

        ArrayAdapter<CharSequence> adapter_2 = ArrayAdapter.createFromResource(this,
                R.array.typeOfGame, android.R.layout.simple_spinner_item);
        adapter_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeOfGame.setAdapter(adapter_2);

        ArrayAdapter<CharSequence> adapter_3 = ArrayAdapter.createFromResource(this,
                R.array.gameTypeCost, android.R.layout.simple_spinner_item);
        adapter_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gameTypeCost.setAdapter(adapter_3);

        ArrayAdapter<CharSequence> adapter_4 = ArrayAdapter.createFromResource(this,
                R.array.Min, android.R.layout.simple_spinner_item);
        adapter_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        min.setAdapter(adapter_4);

        ArrayAdapter<CharSequence> adapter_5 = ArrayAdapter.createFromResource(this,
                R.array.Max, android.R.layout.simple_spinner_item);
        adapter_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        max.setAdapter(adapter_5);

        ArrayAdapter<CharSequence> adapter_6 = ArrayAdapter.createFromResource(this,
                R.array.sport_spinner, android.R.layout.simple_spinner_item);
        adapter_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sport_spinner.setAdapter(adapter_6);

        //If user is not a registered organizer, grey out relevant fields

        typeOfGame.setEnabled(false);
        gameTypeCost.setEnabled(false);


        tog = typeOfGame.getSelectedItem().toString();
        gt = gameType.getSelectedItem().toString();
        gtc = gameTypeCost.getSelectedItem().toString();
        sp_sp = sport_spinner.getSelectedItem().toString();
        minim = min.getSelectedItem().toString();
        maxim = max.getSelectedItem().toString();

        if(gtc.equals("Free")){
            cost = 0;
        }

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EventType event = new EventType();
                event.event_name = event_name.getText().toString();
                event.event_start = date.getText().toString()+time.getText().toString();
                event.venue = venue;
                event.desc = event_desc.getText().toString();
                event.org = organizer;
                mEventCreate = new EventCreateTask(event);
                mEventCreate.execute();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_event, menu);
        return true;
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
        return super.onOptionsItemSelected(item);
    }

    public class EventCreateTask extends AsyncTask<Void, Void, String> {
        private final EventType event;
        int result_req;

        EventCreateTask(EventType event) {
            this.event = event;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(CreateEvent.this);
            pDialog.setMessage("Creating event....");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(Void... voids) {
            jsonRequest = new InfoJsonSend(CreateEvent.this, serverIP);
            result_req = jsonRequest.postEvent(event);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            if (result_req == 1) {
                finish();
            }
        }
    }
}
