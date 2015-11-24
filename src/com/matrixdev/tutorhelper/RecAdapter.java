package com.matrixdev.tutorhelper;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by MRC2 on 24-Nov-15.
 */
public class RecAdapter extends RecyclerView.Adapter<RecAdapter.VH> {


    ArrayList<info> obj;

    RecAdapter(ArrayList<info> obj)
    {
        this.obj = obj;

    }

    class VH extends RecyclerView.ViewHolder{

        TextView T1;
        TextView T2;
        TextView T3;
        TextView T4;
        TextView T5;

        public VH(View itemView) {
            super(itemView);

            T1 = (TextView)itemView.findViewById(R.id.T1);
            T2 = (TextView)itemView.findViewById(R.id.T2);
            T3 = (TextView)itemView.findViewById(R.id.T3);
            T4 = (TextView)itemView.findViewById(R.id.T4);
            T5 = (TextView)itemView.findViewById(R.id.T5);
        }
    }
    
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule,parent,false);
        
       
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {

        holder.T1.setText(obj.get(position).HOUR);
        holder.T2.setText(":" + obj.get(position).MINUTE);
        holder.T3.setText(obj.get(position).CLASS);
        holder.T4.setText("Subject : " + obj.get(position).SUBJECT);
        holder.T5.setText("Room : "+obj.get(position).ROOM);

    }

    @Override
    public int getItemCount() {
        return obj.size();
    }

    
}
