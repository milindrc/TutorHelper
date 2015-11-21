package com.matrixdev.tutorhelper;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

import MyDatabasePkg.MyDatabase;

public class AttandanceActivity extends Activity {
MyDatabase mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attandance);
        mydb=new MyDatabase(this);
        mydb.openDatabase();
        String Id="Att"+getIntent().getIntExtra("ID",0);
        Calendar c = Calendar.getInstance();
        int y= c.get(Calendar.YEAR);
        String yr= new Integer(y).toString();
        attan(Id,yr);
     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_attandance, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void attan(String batch,String yr)
    {
        mydb.sqldb.execSQL("create table "+batch+"(Year Varchar(20) DEFAULT "+yr+",month varchar(20),day varchar(3))");
        int y=0;
        int ya=(Integer.parseInt(yr));
        if (ya%4==0&&ya%400==0&&ya%100!=0)
        { y=1;
            Toast.makeText(getApplicationContext(), "leap", Toast.LENGTH_SHORT).show();}
        else
            y=0;
        String mon="";
        for(int i=0;  i<1 ;i++)
        {
            for (int j=1;j<=12;j++)
            {if (j==1)
            {
                mon="January";}
                if (j==2)
                    mon="February";
                if (j==3)
                    mon="March";
                if (j==4)
                    mon="April";
                if (j==5)
                    mon="May";
                if (j==6)
                    mon="June";
                if (j==7)
                    mon="July";
                if (j==8)
                    mon="August";
                if (j==9)
                    mon="September";
                if (j==10)
                    mon="October";
                if (j==11)
                    mon="November";
                if (j==12)
                    mon="December";



                for (int k=1;k<=31;k++)
                {
                    if (y==1)
                    {
                        if (j==2&&k>29)
                            continue;
                        if (j==4&&k>30)
                            continue;
                        if (j==6&&k>30)
                            continue;
                        if (j==9&&k>30)
                            continue;
                        if (j==11&&k>30)
                            continue;
                    }
                    if (y==0)
                    {
                        if (j==2&&k>28)
                            continue;
                        if (j==4&&k>30)
                            continue;
                        if (j==6&&k>30)
                            continue;
                        if (j==9&&k>30)
                            continue;
                        if (j==11&&k>30)
                            continue;

                    }
                    mydb.sqldb.execSQL("Insert into "+batch+yr+"('month','day')values('"+mon+"','"+k+"')");
                }
            }

        }
    }
}
