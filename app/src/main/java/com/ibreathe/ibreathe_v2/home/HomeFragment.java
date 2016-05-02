package com.ibreathe.ibreathe_v2.home;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ibreathe.ibreathe_v2.R;
import com.ibreathe.ibreathe_v2.global.GlobalVariable;
import com.ibreathe.ibreathe_v2.global.InfoJsonSend;
import com.ibreathe.ibreathe_v2.global.SessionManager;
import com.ibreathe.ibreathe_v2.models.OrgType;
import com.ibreathe.ibreathe_v2.models.ResultsByVenue;
import com.ibreathe.ibreathe_v2.models.VenueType;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

public class HomeFragment extends Fragment implements LocationListener{

    LinearLayout mapFrag;
    ScrollView scrl;
    SupportMapFragment mapFragment;
    InfoJsonSend jsonRequest;
    LinearLayout mapView;
    private SlidingUpPanelLayout mLayout;
    FeatureCoverFlow mCoverFlow;
    private static final String TAG = "iBreathe";
    private GoogleMap mapIcons;
    PopupWindow eventsPopUp;
    EventListSimpleAdapter adapter;
    PopUpListAdapter popup_adapter;
    ResultsByVenue[] results;
    private ProgressDialog pDialog;
    ListEventsTask mListEvents;
    ListGroup[] main_list;
    ListGroup[] filter_list;
    ListGroup[] popup_list;
    GlobalVariable gv = GlobalVariable.getInstance();
    private final String serverIP = gv.getLoc_IP();
    Location current_location;
    LocationManager mLocationMgr;
    ListView listView;
    SessionManager sessionManager;
    Spinner play_type;
    Spinner play_cost;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View main_view = inflater.inflate(R.layout.fragment_home, container, false);
        mapFrag = (LinearLayout) main_view.findViewById(R.id.mapFrag);
        mapView = (LinearLayout) main_view.findViewById(R.id.mapView);
        scrl = (ScrollView) main_view.findViewById(R.id.scroll_view_list);

        final FloatingActionButton map_switch = (FloatingActionButton) main_view.findViewById(R.id.mapSwitch);
        final FloatingActionButton list_switch = (FloatingActionButton) main_view.findViewById(R.id.listSwitch);


        if (mapFrag.getVisibility() == View.VISIBLE) {
            map_switch.setVisibility(View.GONE);
        } else if (scrl.getVisibility() == View.VISIBLE) {
            list_switch.setVisibility(View.GONE);
        }

        map_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapFrag.getVisibility() == View.GONE) {
                    scrl.setVisibility(View.GONE);
                    mapFrag.setVisibility(View.VISIBLE);
                    if (list_switch.getVisibility() == View.GONE) {
                        list_switch.setVisibility(View.VISIBLE);
                    }
                    v.setVisibility(View.GONE);
                }
            }
        });

        list_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scrl.getVisibility() == View.GONE) {
                    mapFrag.setVisibility(View.GONE);
                    scrl.setVisibility(View.VISIBLE);
                    if (map_switch.getVisibility() == View.GONE) {
                        map_switch.setVisibility(View.VISIBLE);
                    }
                    v.setVisibility(View.GONE);
                }
            }
        });

        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        listView = (ListView)main_view.findViewById(R.id.venue_list_home);
        mLayout = (SlidingUpPanelLayout) main_view.findViewById(R.id.sliding_layout);
        mCoverFlow = (FeatureCoverFlow) main_view.findViewById(R.id.coverflow);
        play_type = (Spinner)main_view.findViewById(R.id.type);
        play_cost = (Spinner)main_view.findViewById(R.id.cost);

        return main_view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mapIcons = googleMap;
                mapIcons.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        if (eventsPopUp != null){
                            if (eventsPopUp.isShowing()){
                                eventsPopUp.dismiss();
                            }
                        }
                        int i;
                        String check = marker.getTitle();

                        for (i = 0; i<results.length; i++) {
                            if (results[i].mVenue.venue_name.equals(check)){
                                break;
                            }
                        }

                        if (results[i].mEvents != null){
                            final int finalI = i;
                            LayoutInflater inflater = (LayoutInflater)getActivity().getBaseContext().
                                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View v = inflater.inflate(R.layout.event_list, null);
                            TextView loc_name = (TextView) v.findViewById(R.id.popup_loc_name);
                            loc_name.setText(results[i].mVenue.venue_name);
                            ListView list = (ListView) v.findViewById(R.id.event_list);

                            View footerView =  ((LayoutInflater)getActivity().getBaseContext().
                                    getSystemService(Context.LAYOUT_INFLATER_SERVICE)).
                                    inflate(R.layout.create_button, null, false);
                            Button create = (Button)footerView.findViewById(R.id.create_button_list);
                            create.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent createI = new Intent(getActivity(), CreateEvent.class);
                                    createI.putExtra("venue",results[finalI].mVenue);
                                    OrgType orgType = new OrgType();
                                    JSONObject user = new JSONObject();

                                    try {
                                        user.put("name", "Rohan Mukherji");
                                        user.put("email", "rohan@ibreathe.com");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        if (user != null) {
                                            orgType.org_name = user.getString("name");
                                            orgType.contact_name = user.getString("name");
                                            orgType.contact_email = user.getString("email");
                                            createI.putExtra("org",orgType);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    startActivity(createI);
                                }
                            });
                            list.addFooterView(footerView);
                            popup_list = new ListGroup[results[i].mEvents.length];
                            for (int j = 0; j<results[i].mEvents.length; j++) {
                                popup_list[j] = new ListGroup(R.drawable.ic_event_black_48dp,
                                        results[i].mEvents[j].event_name,
                                        results[i].mEvents[j].event_start,
                                        results[i].mEvents[j].event_url);
                            }

                            popup_adapter = new PopUpListAdapter(getActivity(),
                                    R.layout.list_item_card_small,
                                    popup_list);
                            list.setAdapter(popup_adapter);
                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent eventHome = new Intent(getActivity(), EventHome.class);
                                    eventHome.putExtra("event",results[finalI].mEvents[position]);
                                    startActivity(eventHome);
                                }
                            });
                            eventsPopUp = new PopupWindow(v);
                            eventsPopUp.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
                            if (popup_list.length > 3) {
                                View item = popup_adapter.getView(0, null, list);
                                item.measure(0, 0);
                                eventsPopUp.setHeight((4 * item.getMeasuredHeight()));
                            } else {
                                eventsPopUp.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
                            }
                            eventsPopUp.setTouchable(true);
                            eventsPopUp.setFocusable(false);
                            eventsPopUp.setOutsideTouchable(false);
                            eventsPopUp.setAnimationStyle(R.style.PopupAnimation);
                            eventsPopUp.showAtLocation(mapView, Gravity.BOTTOM,0,0);
                        }
                        return true;
                    }
                });
            }
        });

        mLocationMgr = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        // Creating an empty criteria object
        Criteria criteria = new Criteria();

        // Getting the name of the provider that meets the criteria
        String provider = mLocationMgr.getBestProvider(criteria, false);

        if(provider!=null && !provider.equals("")) {
            // Get the location from the given provider
            current_location = mLocationMgr.getLastKnownLocation(provider);
            mLocationMgr.requestLocationUpdates(provider, 20000, 1, this);
        }

        if (current_location != null){
            current_location.setLatitude(37.405700);
            current_location.setLongitude(-121.925578);
            mListEvents = new ListEventsTask(current_location);
            mListEvents.execute();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(),EventHome.class);
                i.putExtra("event",main_list[position].event);
                startActivity(i);
            }
        });

        mLayout.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i(TAG, "onPanelSlide, offset " + slideOffset);
            }
            @Override
            public void onPanelExpanded(View panel) {
                Log.i(TAG, "onPanelExpanded");

            }
            @Override
            public void onPanelCollapsed(View panel) {
                Log.i(TAG, "onPanelCollapsed");

            }
            @Override
            public void onPanelAnchored(View panel) {
                Log.i(TAG, "onPanelAnchored");
            }

            @Override
            public void onPanelHidden(View panel) {
                Log.i(TAG, "onPanelHidden");
            }
        });

        HomeDrawerItem[] filters = new HomeDrawerItem[4];

        filters[0] = new HomeDrawerItem(R.drawable.soccer, "Soccer");
        filters[1] = new HomeDrawerItem(R.drawable.basketball, "Basketball");
        filters[2] = new HomeDrawerItem(R.drawable.tennis, "Tennis");
        filters[3] = new HomeDrawerItem(R.drawable.all_sports_selector, "All Sports");

        FilterAdapter mAdapter = new FilterAdapter(getActivity().getBaseContext(),R.layout.drawer_list_item,filters);

        String[] typeValues = getResources().getStringArray(R.array.gameType);
        String[] costValues = getResources().getStringArray(R.array.gameTypeCost);

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(),
                android.R.layout.simple_spinner_item,typeValues);
        ArrayAdapter<String> costAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(),
                android.R.layout.simple_spinner_item,costValues);
        play_type.setAdapter(typeAdapter);
        play_cost.setAdapter(costAdapter);

        mCoverFlow.setAdapter(mAdapter);


        mCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO CoverFlow item clicked
            }
        });

        mCoverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
            @Override
            public void onScrolledToPosition(int position) {
                //TODO CoverFlow stopped to position
                filterMap(position+1);
            }

            @Override
            public void onScrolling() {
                //TODO CoverFlow began scrolling
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    if (eventsPopUp.isShowing()) {
                        eventsPopUp.dismiss();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.remove(mapFragment);
        ft.commit();
    }

    @Override
    public void onLocationChanged(Location location) {
        //current_location = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private Bitmap writeTextOnDrawable(int drawableId, String text) {

        Bitmap bm = BitmapFactory.decodeResource(getResources(), drawableId)
                .copy(Bitmap.Config.ARGB_8888, true);

        Typeface tf = Typeface.create("sans-serif-condensed", Typeface.BOLD);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTypeface(tf);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(convertToPixels(getActivity().getBaseContext(), 16));

        Rect textRect = new Rect();
        paint.getTextBounds(text, 0, text.length(), textRect);

        Canvas canvas = new Canvas(bm);

        //If the text is bigger than the canvas , reduce the font size
        if(textRect.width() >= (canvas.getWidth() - 4))     //the padding on either sides is considered as 4, so as to appropriately fit in the text
            paint.setTextSize(convertToPixels(getActivity().getBaseContext(), 7));        //Scaling needs to be used for different dpi's

        //Calculate the positions
        int xPos = (canvas.getWidth() / 2) - 2;     //-2 is for regulating the x position offset

        //"- ((paint.descent() + paint.ascent()) / 2)" is the distance from the baseline to the center.
        int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2)) ;

        canvas.drawText(text, xPos, yPos, paint);

        return  bm;
    }



    public static int convertToPixels(Context context, int nDP)
    {
        final float conversionScale = context.getResources().getDisplayMetrics().density;

        return (int) ((nDP * conversionScale) + 0.5f) ;

    }

    public class ListEventsTask extends AsyncTask<Void, Void, String> {
        private final Location location;

        String currentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        ListEventsTask(Location location) {
            this.location = location;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Getting nearby events for you!");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(Void... voids) {

            jsonRequest = new InfoJsonSend(getActivity(), serverIP);
            try {
                results = jsonRequest.getEvents(location);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            LatLng loc = null;
            int total_events = 0;
            if (pDialog.isShowing())
                pDialog.dismiss();

            if (results != null) {
                for (int k = 0; k < results.length; k++) {
                    if(results[k].mEvents != null) {
                        total_events += results[k].mEvents.length;
                    }
                }

                main_list = new ListGroup[total_events];

                for (int i = 0, k = 0; i<results.length; i++){
                    loc = new LatLng(Double.parseDouble(results[i].mVenue.latitude),
                            Double.parseDouble(results[i].mVenue.longitude));


                    int iconValue = results[i].mEvents == null ? 0:results[i].mEvents.length;

                    int icon = gv.getIcon(results[i].mVenue.sport_cat_map);

                    Marker eventLoc = mapIcons.addMarker(new MarkerOptions().position(loc)
                            .title(results[i].mVenue.venue_name)
                            .icon(BitmapDescriptorFactory
                                    .fromBitmap(writeTextOnDrawable(
                                            icon,
                                            "" + iconValue))));

                    if (results[i].mEvents != null){
                        for (int j = 0; j < results[i].mEvents.length; j++) {

                            main_list[k] = new ListGroup(results[i].mEvents[j].event_start,
                                    results[i].mEvents[j].event_name,
                                    results[i].mEvents[j].venue.venue_name,
                                    results[i].mEvents[j].domain,
                                    results[i].mEvents[j]);
                            k++;
                        }
                    }

                }

                adapter = new EventListSimpleAdapter(getActivity(),
                        R.layout.list_item_card_big,
                        main_list);

                listView.setAdapter(adapter);

                // Move the camera instantly to hamburg with a zoom of 15.
                if (current_location != null) {
                    mapIcons.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(current_location.getLatitude(),
                            current_location.getLongitude()), 40));
                    Marker myloc = mapIcons.addMarker(new MarkerOptions().position(new LatLng(current_location.getLatitude(),
                            current_location.getLongitude()))
                            .title("Your location")
                            .icon(BitmapDescriptorFactory
                                    .fromResource(R.drawable.ic_accessibility_black_36dp)));

                    // Zoom in, animating the camera.
                    mapIcons.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
                }
            } else {
                if (current_location != null) {
                    mapIcons.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(current_location.getLatitude(),
                            current_location.getLongitude()), 40));
                    Marker myloc = mapIcons.addMarker(new MarkerOptions().position(new LatLng(current_location.getLatitude(),
                            current_location.getLongitude()))
                            .title("Your location")
                            .icon(BitmapDescriptorFactory
                                    .fromResource(R.drawable.ic_accessibility_black_36dp)));

                    // Zoom in, animating the camera.
                    mapIcons.animateCamera(CameraUpdateFactory.zoomTo(12), 2000, null);
                }
                Toast.makeText(getActivity(),"Please check your internet connection",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public void filterMap(int filter) {

        LatLng loc = null;
        int total_events = 0;

        if (mapIcons == null)
            return;

        mapIcons.clear();
        if (results != null) {
            for (int k = 0; k < results.length; k++) {
                if(results[k].mEvents != null) {
                    for (int z = 0; z < results[k].mEvents.length; z++) {
                        if (results[k].mEvents[z].sport_category == filter ||
                                filter == 4) {
                            total_events += 1;
                        }
                    }
                }
            }

            if (filter_list != null) {
                filter_list = null;
            }
            filter_list = new ListGroup[total_events];

            for (int i = 0, k = 0; i<results.length; i++){
                if(results[i].mVenue.sport_cat_map == 4) {
                    results[i].mVenue.sport_cat_map = 3;
                }
                if (results[i].mVenue.sport_cat_map == filter ||
                        filter == 4){
                    loc = new LatLng(Double.parseDouble(results[i].mVenue.latitude),
                            Double.parseDouble(results[i].mVenue.longitude));


                    int iconValue = results[i].mEvents == null ? 0:results[i].mEvents.length;
                    int icon = gv.getIcon(results[i].mVenue.sport_cat_map);

                    Marker eventLoc = mapIcons.addMarker(new MarkerOptions().position(loc)
                            .title(results[i].mVenue.venue_name)
                            .icon(BitmapDescriptorFactory
                                    .fromBitmap(writeTextOnDrawable(
                                            icon,
                                            ""+iconValue))));

                    if (results[i].mEvents != null){
                        for (int j = 0; j < results[i].mEvents.length; j++) {

                            if (results[i].mEvents[j].sport_category == filter || filter == 4) {
                                filter_list[k] = new ListGroup(results[i].mEvents[j].event_start,
                                        results[i].mEvents[j].event_name,
                                        results[i].mEvents[j].venue.venue_name,
                                        results[i].mEvents[j].domain,
                                        results[i].mEvents[j]);
                                k++;
                            }
                        }
                    }
                }

            }

            adapter = new EventListSimpleAdapter(getActivity(),
                    R.layout.list_item_card_big,
                    filter_list);

            listView.setAdapter(adapter);
            if (current_location != null) {
                Marker myloc = mapIcons.addMarker(new MarkerOptions().position(new LatLng(current_location.getLatitude(),
                        current_location.getLongitude()))
                        .title("Your location")
                        .icon(BitmapDescriptorFactory
                                .fromResource(R.drawable.ic_accessibility_black_36dp)));
            }
        } else {
            if (current_location != null) {
                Marker myloc = mapIcons.addMarker(new MarkerOptions().position(new LatLng(current_location.getLatitude(),
                        current_location.getLongitude()))
                        .title("Your location")
                        .icon(BitmapDescriptorFactory
                                .fromResource(R.drawable.ic_accessibility_black_36dp)));
            }
            if (filter != 4) {
                Toast.makeText(getActivity(), "No events matching that criteria",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
