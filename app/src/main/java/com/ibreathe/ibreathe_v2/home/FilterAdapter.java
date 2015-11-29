package com.ibreathe.ibreathe_v2.home;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

/**
 * Created by aloknerurkar on 8/9/15.
 */
public class FilterAdapter extends ArrayAdapter<HomeDrawerItem> {

    Context mContext;
    int layoutResourceId;
    HomeDrawerItem data[] = null;

    public FilterAdapter(Context mContext, int layoutResourceId, HomeDrawerItem[] data) {

        super(mContext, layoutResourceId, data);
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = convertView;

        //LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        //listItem = inflater.inflate(layoutResourceId, parent, false);

        HomeDrawerItem folder = data[position];
        ImageView imageView = new ImageView(mContext);
        imageView.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), folder.icon));

        listItem = imageView;

        return listItem;
    }
}
