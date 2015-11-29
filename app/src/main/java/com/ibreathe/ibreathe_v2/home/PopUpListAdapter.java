package com.ibreathe.ibreathe_v2.home;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ibreathe.ibreathe_v2.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by aloknerurkar on 6/30/15.
 */

public class PopUpListAdapter extends ArrayAdapter<ListGroup> {

    Context mContext;
    int layoutResourceId;
    ListGroup data[] = null;

    public PopUpListAdapter(Context mContext, int layoutResourceId, ListGroup[] data) {

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

//        ImageView icon = (ImageView) listItem.findViewById(R.id.popup_list_icon);
        TextView title = (TextView) listItem.findViewById(R.id.sm_event_name);
        TextView desc1 = (TextView) listItem.findViewById(R.id.sm_event_datetime);
        TextView desc2 = (TextView) listItem.findViewById(R.id.sm_event_status_msg);


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat day = new SimpleDateFormat("EEEE");
        SimpleDateFormat showdate = new SimpleDateFormat("dd-MMM hh:mm aa");
        ListGroup folder = data[position];
        //icon.setImageResource(folder.icon);
        title.setText(folder.string);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date d = null;
        try {
            d = dateFormat.parse(folder.string2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (d != null){
            desc1.setText(day.format(d));
            desc2.setText(showdate.format(d));
        }
        return listItem;
    }
}