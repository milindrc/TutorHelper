package com.matrixdev.tutorhelper;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import MyDatabasePkg.MyDatabase;

public class AttandanceActivity extends Activity {

    private Context context = this;
    TableLayout TL;
    MyDatabase mydb;
    Cursor rs,rs2,rs3;
    EditText TV[];
    TableRow Ctr,editRow;
    int colWidth[];
    Spinner sp,sp2,sp3;
    Button b1;

    void createDB()
    {


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

                if (rs.getString(j).trim().equals("0") && j>3)
                {
                    CheckBox box=new CheckBox(this);
                    tr[i].addView(box);
                }
                else if (rs.getString(j).trim().equals("1")&&j>3)
                {
                    CheckBox box=new CheckBox(this);
                    box.setChecked(true);
                    tr[i].addView(box);
                }
                else {


                    TV[j] = new EditText(context);
                    TV[j].setBackgroundColor(Color.parseColor("#00000000"));
                    TV[j].setPadding(1, 2, 2, 2);
                    TV[j].setFocusable(false);
                    TV[j].setText("\t" + rs.getString(j).trim() + "\t");
                    TV[j].setGravity(Gravity.CENTER);

                    tr[i].addView(TV[j]);
                }
            }

            TL.addView(tr[i]);

            rs.moveToNext();
        }
        // Adding Data rows into table





        //_____________________________________________________________________________________________

    }
    public void attan(String batch,String yr)
    {



        mydb.sqldb.execSQL("create table " + batch + "(Id integer primary key  autoincrement,Year Varchar(20) DEFAULT " + yr + ",month varchar(20),day varchar(3))");
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
                    mydb.sqldb.execSQL("Insert into "+batch+"('Id','month','day')values(null,'"+mon+"','"+k+"')");

                }
            }

        }
    }
    public void getname(int student)
     {
        rs2=mydb.sqldb.rawQuery("select name from stu"+student+";", null);
        rs3=mydb.sqldb.rawQuery("select ID from stu"+student+";",null);
        String arr[]=new String[rs2.getCount()];
        int id[]=new int[rs3.getCount()];
        for (int i=0;i<rs2.getCount();i++)
        {   rs3.moveToPosition(i);
            rs2.moveToPosition(i);
            arr[i]=rs2.getString(0);
            id[i]=rs3.getInt(0);
            try {
                mydb.sqldb.execSQL("Alter table Att" + student +" add "+ arr[i]+"_"+id[i]+ " varchar(20)");
            }
            catch (Exception e) {
                Log.d("Database Exist", " Database Exist");
            }
        }


            }
      public void zeronull(String Id)
      {
          rs=mydb.sqldb.rawQuery("Select * from "+Id+" ;",null);
          for (int i=3;i<rs.getColumnCount();i++) {
              String name = rs.getColumnName(i);
              mydb.sqldb.execSQL("Update "+Id+" set "+name+"=0 where "+ name+" is null;");
          }
      }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attandance);



        sp=(Spinner) findViewById(R.id.spinner);
        sp2=(Spinner) findViewById(R.id.spinner2);


        TL = (TableLayout)   findViewById(R.id.TB1);
        editRow = (TableRow)   findViewById(R.id.tableRow5);

        mydb=new MyDatabase(this);
        mydb.openDatabase();
         final String Id="Att"+getIntent().getIntExtra("ID",0);
        int id=getIntent().getIntExtra("ID", 0);

        Calendar c = Calendar.getInstance();
        int y= c.get(Calendar.YEAR);



        String m= c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
        final String mn= new String(m).toString();

        String yr= new Integer(y).toString();
         try {
            attan(Id, yr);

        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(),"Database found",Toast.LENGTH_SHORT).show();
        }
        rs2=mydb.sqldb.rawQuery("select distinct year from "+Id+";",null);
        String yea[]=new String[rs2.getCount()+1];
        yea[0]="Year";
        for (int i=1;i<=rs2.getCount();i++)
        {
            rs2.moveToPosition(i-1);
            yea[i]=rs2.getString(0);

        }
        rs2=mydb.sqldb.rawQuery("select distinct month from "+Id+";",null);
        String mnt[]=new String[rs2.getCount()+1];
        mnt[0]="MONTH";
        for (int i=1;i<=rs2.getCount();i++)
        {
            rs2.moveToPosition(i-1);
            mnt[i]=rs2.getString(0);

        }


        ArrayAdapter <String> ar1=new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_layout,yea);
        ArrayAdapter <String> ar2=new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_layout,mnt);

        sp.setAdapter(ar1);
        sp2.setAdapter(ar2);


        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                TextView view1 = (TextView) view;
                String a = view1.getText().toString();
                if (!a.equals("MONTH")){
                    rs = mydb.sqldb.rawQuery("select * from " + Id + " where month='" + a + "' ;", null);
                    TL.removeAllViewsInLayout();
                    createDB();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                TextView view1=(TextView)view;
                String a=view1.getText().toString();
                if (!a.equals("Year")){
                    rs=mydb.sqldb.rawQuery("select * from "+Id+" where year='"+a+"';",null);
                   TL.removeAllViewsInLayout();
                   createDB();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getname(id);
        zeronull(Id);

        rs=  mydb.sqldb.rawQuery("select * from "+Id+" where month='"+mn+"' ;", null);
        rs.moveToFirst();



        TV = new EditText[rs.getColumnCount()];
       colWidth = new int[rs.getColumnCount()];


        //_________________________________________________________________________________________

        createDB();

        b1=(Button)findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=1;i<TL.getChildCount();i++)
                {TableRow tr=(TableRow)TL.getChildAt(i);

                    for (int j=4;j<tr.getChildCount();j++)
                    {mydb.openDatabase();
                        CheckBox box=(CheckBox)tr.getChildAt(j);
                        if (box.isChecked())
                        {
                            String row =((EditText)(tr.getChildAt(0))).getText().toString();
                            row.trim();
                            String col=rs.getColumnName(j);
                            mydb.sqldb.execSQL("Update "+Id+" set "+col+"=1 where id ="+row+";");
                        }
                        else {
                            String row =((EditText)(tr.getChildAt(0))).getText().toString();
                            String col=rs.getColumnName(j);
                            mydb.sqldb.execSQL("Update "+Id+" set "+col+"=0 where id ="+row+";");
                        }
                    }
                }
                Toast.makeText(getApplicationContext(),"Changes Applied",Toast.LENGTH_LONG).show();

            }
        });

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
