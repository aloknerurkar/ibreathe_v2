package com.ibreathe.ibreathe_v2.home;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ibreathe.ibreathe_v2.R;

import com.ibreathe.ibreathe_v2.global.GlobalVariable;
import com.ibreathe.ibreathe_v2.global.InfoJsonSend;
import com.ibreathe.ibreathe_v2.global.SessionManager;
import com.ibreathe.ibreathe_v2.home.dummy.DummyContent;
import com.ibreathe.ibreathe_v2.models.EventType;
import com.ibreathe.ibreathe_v2.models.OrgType;

import org.json.JSONException;

import java.util.ArrayList;

public class MyEventsFragment extends Fragment {

    ListGroup[] my_events;
    InfoJsonSend jsonRequest;
    EventType[] eventListResults;
    private ProgressDialog pDialog;
    EventListSimpleAdapter adapter;
    SessionManager sessionManager;
    ListView listView;
    OrgType org;

    GlobalVariable gv = GlobalVariable.getInstance();
    private final String serverIP = gv.getGetMyEvents();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MyEventsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new EventListSimpleAdapter(getActivity(),
                R.layout.list_item_card_big,
                my_events);

        sessionManager = new SessionManager(getActivity());
        org = new OrgType();
        org.setOrg_name(sessionManager.getFBDetails().get("fb_name"));
        org.setContact_name(sessionManager.getFBDetails().get("fb_name"));
        org.setContact_email(sessionManager.getFBDetails().get("fb_email"));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myevents, container, false);
        listView = (ListView) view.findViewById(R.id.myEventsList);
        listView.setAdapter(adapter);
        MyEventsRequestTask mEventsTask = new MyEventsRequestTask(org);
        mEventsTask.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(),EventHome.class);
                i.putExtra("event", eventListResults[position]);
                startActivity(i);
            }
        });
        return view;
    }

    public class MyEventsRequestTask extends AsyncTask<Void, Void, String> {
        final OrgType orgType;
        MyEventsRequestTask(OrgType orgType) {
            this.orgType = orgType;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Getting your events...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            jsonRequest = new InfoJsonSend(getActivity(), serverIP);
            try {
                eventListResults = jsonRequest.getEvents(orgType);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            my_events = new ListGroup[eventListResults.length];

            for (int i = 0; i<eventListResults.length; i++){
                my_events[i] = new ListGroup(R.drawable.ic_event_black_48dp,
                        eventListResults[i].event_name,
                        eventListResults[i].event_start,
                        eventListResults[i].desc);
            }

            adapter.notifyDataSetChanged();
        }
    }

}
