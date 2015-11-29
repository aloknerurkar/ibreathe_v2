package com.ibreathe.ibreathe_v2.home;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ibreathe.ibreathe_v2.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by aloknerurkar on 4/22/15.
 */
public class EventListSimpleAdapter extends ArrayAdapter<ListGroup> {

    Context mContext;
    int layoutResourceId;
    ListGroup data[] = null;

    public EventListSimpleAdapter(Context mContext, int layoutResourceId, ListGroup[] data) {

        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = convertView;

        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        listItem = inflater.inflate(layoutResourceId, parent, false);

        TextView eventTime = (TextView) listItem.findViewById(R.id.event_datetime);
        TextView eventTitle = (TextView) listItem.findViewById(R.id.event_name);
        TextView eventDetails = (TextView) listItem.findViewById(R.id.loc_name);
        TextView descList = (TextView) listItem.findViewById(R.id.domain);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat showdate = new SimpleDateFormat("hh:mm aa");

        ListGroup folder = data[position];

        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date d = null;
        try {
            d = dateFormat.parse(folder.string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (d != null){
            eventTime.setText(showdate.format(d));
        }
        eventTitle.setText(folder.string2);
        eventDetails.setText(folder.string3);
        descList.setText(folder.string4);

        return listItem;
    }
}