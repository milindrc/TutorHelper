package com.matrixdev.tutorhelper;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.sql.Time;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import MyDatabasePkg.MyDatabase;

public class AttandanceActivity extends Activity {

    private Context context = this;
    TableLayout TL;
    MyDatabase mydb;
    Cursor rs,rs2;
    EditText TV[];
    TableRow Ctr,editRow;
    int colWidth[];

    void createDB(int id)
    {



        //mydb.sqldb.execSQL("insert into tb1 values(null,'A','X' );");



        rs.moveToFirst();



        Ctr = new TableRow(context);
        for(int i =0; i < rs.getColumnCount() ; i++)
        {
            TV[i] =new EditText(context);
            //TV[i].addOnLayoutChangeListener(new myWidth(i));
            TV[i].setFocusable(false);
            TV[i].setPadding(1, 2, 2, 2);
            TV[i].setText("\t"+rs.getColumnName(i).toUpperCase().trim()+"\t");
            TV[i].setTextColor(Color.parseColor("#ffffff"));
            TV[i].setBackgroundColor(Color.parseColor("#000000"));
            TV[i].setGravity(Gravity.CENTER);
            TV[i].setPaddingRelative(12, 0, 12, 0);

            Ctr.addView(TV[i]);
        }

        TL.addView(Ctr);  // Adding Column names in second row

        //____________________________________________________________________________________________

        TableRow tr[]=new TableRow[rs.getCount()] ;

        for(int i = 0; i<rs.getCount() ; i++)
        {
            tr[i] = new TableRow(context);

            for(int j = 0; j < rs.getColumnCount() ; j++)
            {


                if (rs.getString(j)==null)
                    continue;



                TV[j] = new EditText(context);
                TV[j].setBackgroundColor(Color.parseColor("#00000000"));
                TV[j].setPadding(1, 2, 2, 2);
                TV[j].setFocusable(false);
                TV[j].setText("\t"+rs.getString(j).trim()+"\t");
                TV[j].setGravity(Gravity.CENTER);

                tr[i].addView(TV[j]);
            }

            TL.addView(tr[i]);

            rs.moveToNext();
        }
        // Adding Data rows into table

       /* try {
            String name[] = getname(id);
            for (int i = 0; i < name.length; i++) {
                mydb.sqldb.execSQL("Alter table Att" + id + " add " + name[i] + " varchar(20)");
            }
        }
        catch (Exception e)
        {Toast.makeText(getApplicationContext(),"Database Exist",Toast.LENGTH_SHORT).show();
        }*/

        mydb.sqldb.close();

        //_____________________________________________________________________________________________

    }
    public void attan(String batch,String yr)
    {


        Toast.makeText(getApplicationContext(),batch,Toast.LENGTH_SHORT).show();
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
                    mydb.sqldb.execSQL("Insert into "+batch+"('month','day')values('"+mon+"','"+k+"')");

                }
            }

        }
    }
    public void getname(int student)
    {
        rs2=mydb.sqldb.rawQuery("select name from stu"+student+";", null);
        String arr[]=new String[rs2.getCount()];
        for (int i=0;i<rs2.getCount();i++)
        {
            rs2.moveToPosition(i);
            arr[i]=rs2.getString(0);
            try {
                mydb.sqldb.execSQL("Alter table Att" + student + " add " + arr[i] + " varchar(20)");
            }
            catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Database Exist", Toast.LENGTH_SHORT).show();
            }
        }

         /* for (int i = 0; i < arr.length; i++) {
           try {
                mydb.sqldb.execSQL("Alter table Att" + student + " add " + arr[i] + " varchar(20)");
            }
            catch (Exception e)
            { Toast.makeText(getApplicationContext(),"Database Exist",Toast.LENGTH_SHORT).show();
                break;

            }*/
            }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attandance);

        TL = (TableLayout)   findViewById(R.id.TB1);
        editRow = (TableRow)   findViewById(R.id.tableRow5);

        mydb=new MyDatabase(this);
        mydb.openDatabase();
         String Id="Att"+getIntent().getIntExtra("ID",0);
        int id=getIntent().getIntExtra("ID",0);

        Calendar c = Calendar.getInstance();
        int y= c.get(Calendar.YEAR);
        String yr= new Integer(y).toString();
        try {
            attan(Id, yr);

        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(),"Database found",Toast.LENGTH_SHORT).show();
        }

        getname(id);

        rs=  mydb.sqldb.rawQuery("select * from "+Id+";", null);
        rs.moveToFirst();



        TV = new EditText[rs.getColumnCount()];
       colWidth = new int[rs.getColumnCount()];


        //_________________________________________________________________________________________

        createDB(id);


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

}
