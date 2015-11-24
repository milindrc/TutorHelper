package com.matrixdev.tutorhelper;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import MyDatabasePkg.MyDatabase;

public class TodayActivity extends Activity {

    RecyclerView RV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);

        RV =(RecyclerView) findViewById(R.id.RV);

        MyDatabase mydb = new MyDatabase(this);
        mydb.openDatabase();

        Cursor cr = mydb.sqldb.rawQuery("select * from batches order by time;", null);


        ArrayList<info> arr= new ArrayList<info>();

        for(int i=0;i<cr.getCount();i++) {

            cr.moveToPosition(i);
            String time = cr.getString(2);


            SimpleDateFormat format1 = new SimpleDateFormat("hh:mmaa");
            SimpleDateFormat format2 = new SimpleDateFormat("hh:mm aa");
            SimpleDateFormat format3 = new SimpleDateFormat("hhaa");

            String flag = "";

            Date d = null;

            try {
                d = format1.parse(time);

                flag = "Valid";
            } catch (Exception e) {

                if (flag.equals(""))
                    flag = "Invalid";

            }


            try {

                d = format2.parse(time);

                flag = "Valid";
            } catch (Exception e) {

                if (flag.equals(""))
                    flag = "Invalid";

            }

            try {
                d = format3.parse(time);
                flag = "Valid";
            } catch (Exception e) {

                if (flag.equals(""))
                    flag = "Invalid";

            }

            Calendar c = Calendar.getInstance();

            Date d2 = c.getTime();

            System.out.println("----" + d2.getTime() + "," + d.getTime());

            if (d2.getHours()<d.getHours() || (d2.getHours()==d.getHours()&&d2.getMinutes()<d.getMinutes())) {
                info obj = new info();
                String postfix="AM";

                DecimalFormat formatter = new DecimalFormat("00");


                if(d.getHours()>12) {
                    obj.HOUR = new Integer(d.getHours() % 12).toString();
                    postfix="PM";
                }
                else
                obj.HOUR = new Integer(d.getHours()).toString();


                obj.MINUTE = formatter.format(d.getMinutes())+postfix;
                obj.ROOM = cr.getString(3);
                obj.CLASS = cr.getString(5);
                obj.SUBJECT = cr.getString(6);
                arr.add(obj);

            }


        }

        System.out.println("----" + arr.size());

       // ListAdapter la = new ListAdapter(arr);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);


        RV.setLayoutManager(lm);

        RecAdapter ra = new RecAdapter(arr);

        RV.setAdapter(ra);

        }
}
