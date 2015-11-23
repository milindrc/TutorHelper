package com.matrixdev.tutorhelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by MRC2 on 24-Nov-15.
 */
public class ListAdapter extends BaseAdapter {

    ArrayList<info> obj;

    ListAdapter(ArrayList<info> obj)
    {
        this.obj = obj;

    }

    @Override
    public int getCount() {
        return obj.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule,parent,false);
        ((TextView)v.findViewById(R.id.T1)).setText(obj.get(position).HOUR);
        ((TextView)v.findViewById(R.id.T2)).setText(":"+obj.get(position).MINUTE);
        ((TextView)v.findViewById(R.id.T3)).setText(obj.get(position).CLASS);
        ((TextView)v.findViewById(R.id.T4)).setText("Subject : "+obj.get(position).SUBJECT);
        ((TextView)v.findViewById(R.id.T5)).setText("Room : "+obj.get(position).ROOM);



        return v;
    }
}
