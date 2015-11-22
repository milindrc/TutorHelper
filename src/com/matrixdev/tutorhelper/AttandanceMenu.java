package com.matrixdev.tutorhelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import MyDatabasePkg.MyDatabase;

public class AttandanceMenu extends Activity {

    private Context context = this;
    TableLayout TL;
    MyDatabase mydb;
    Cursor rs,rs2;
    EditText TV[];
    TableRow Ctr;

    int spinFlag[];
    TableRow savedState[][];
    int pos;

    EditText recreateData[][];

    int counter=0;

    void createDB()
    {

        //mydb.sqldb.execSQL("insert into tb1 values(null,'A','X' );");

        rs.moveToFirst();

        Ctr = new TableRow(context);
        for(int i =0; i < rs.getColumnCount() ; i++)
        {
            TV[i] =new EditText(context);
            TV[i].setFocusable(false);

            TV[i].setPadding(1, 2, 2, 2);
            TV[i].setText("\t"+rs.getColumnName(i).toUpperCase().trim()+"\t");
            TV[i].setTextColor(Color.parseColor("#ffffff"));
            TV[i].setBackgroundColor(Color.parseColor("#000000"));
            TV[i].setGravity(Gravity.CENTER);


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


        mydb.sqldb.close();

        //_____________________________________________________________________________________________

        System.out.println("exit createDB");

    } //Creating Table View Function

    void deleteDB(int x)
    {



        if(x==1 && savedState[pos]==null )
        {   System.out.println("S delete");
            savedState[pos] = new TableRow[TL.getChildCount()];

            for(int i = 1; i<TL.getChildCount();i++)
            {

                savedState[pos][i] = (TableRow)TL.getChildAt(i);

            }
        }

        int i=1;
        if(x==2)
            i = 2;
        for( ; i < TL.getChildCount() ; i++)
        {
            TL.removeViewAt(i);
            i--;
        }

    }

    void createDBfromSave()
    {
        System.out.println("S create" + savedState[pos].length);


        for(int i=1; i< savedState[pos].length;i++)
        {
            TL.addView(savedState[pos][i]);

        }

        addListen();
    }

    String[] toUpdateQuery(String Tname)
    {

        String[] Query  = new String[TL.getChildCount()-2];


        TableRow tr;

        for(int i =0 ; i<TL.getChildCount()-2 ; i++)
        {
            tr = (TableRow) TL.getChildAt(i+2);

            Query[i]="update " + Tname +" set days ='"+((EditText)tr.getChildAt(1)).getText().toString().trim()+"',";
            Query[i]+="time ='"+((EditText)tr.getChildAt(2)).getText().toString().trim()+"',";
            Query[i]+="room ='"+((EditText)tr.getChildAt(3)).getText().toString().trim()+"',";
            Query[i]+="students ='"+((EditText)tr.getChildAt(4)).getText().toString().trim()+"',";
            Query[i]+="class ='"+((EditText)tr.getChildAt(5)).getText().toString().trim()+"',";
            Query[i]+="subject ='"+((EditText)tr.getChildAt(6)).getText().toString().trim()+"',";
            Query[i]+="start_date ='"+((EditText)tr.getChildAt(7)).getText().toString().trim()+"'";

            Query[i]+=" where id = " +((EditText)tr.getChildAt(0)).getText().toString();

            Query[i]+=";";


        }

        for(int i =0; i<Query.length;i++)
        {
            System.out.println(Query[i]);

        }

        return Query;
    }

    void modify(int flag )
    {


        for(int i=2;i<TL.getChildCount();i++)
        {
            TableRow tr = (TableRow)TL.getChildAt(i);
            for(int j=1;j<tr.getChildCount();j++)
            {
                if(flag==0)
                {((EditText)tr.getChildAt(j)).setFocusable(true);
                    ((EditText)tr.getChildAt(j)).setFocusableInTouchMode(true);
                    ((EditText)tr.getChildAt(j)).setBackgroundResource(android.R.drawable.editbox_background);
                    ((EditText)tr.getChildAt(j)).setPadding(0, 2, 0, 2);
                }
                else
                {((EditText)tr.getChildAt(j)).setFocusable(false);
                    ((EditText)tr.getChildAt(j)).setBackgroundColor(Color.parseColor("#00000000"));
                }
            }

        }

        addListen();

    }


    public void addListen()
    {

        TableRow r;

        for(int i =2; i< TL.getChildCount() ; i++)
        {	r= (TableRow) TL.getChildAt(i);
            for(int j = 0;j<r.getChildCount() ; j++)
            {
                r.getChildAt(j).setOnClickListener(new aListen(i));


            }

        }


        System.out.println("exit addListener");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attandance_menu);


        SharedPreferences shp = getSharedPreferences("login", 0);
        if(shp.getString("user", "").equals("guest"))
        {

            ((Button) findViewById(R.id.VB1)).setVisibility(View.INVISIBLE);

            ((Button) findViewById(R.id.VB2)).setVisibility(View.INVISIBLE);


        }






        TL = (TableLayout) findViewById(R.id.TB1);

        mydb =  new MyDatabase(context);
        mydb.openDatabase();

        rs=  mydb.sqldb.rawQuery("select * from batches;", null);
        rs.moveToFirst();

        if(rs.getCount()==0)
        {
            Toast.makeText(getBaseContext(), "Empty Database", Toast.LENGTH_LONG).show();

            return;
        }

        spinFlag = new int[rs.getColumnCount()];
        savedState = new TableRow[rs.getColumnCount()][];

        TV = new EditText[rs.getColumnCount()];


        //_____________________________________________________________________________________________
        // 									Making Spinner Data


        String S[][] = new String[rs.getColumnCount()][];

        for(int i =0 ; i < rs.getColumnCount() ; i++)
        {

            rs2 = mydb.sqldb.rawQuery("select distinct("+ rs.getColumnName(i) +") from batches;",null);

            S[i] = new String[rs2.getCount()+1];
            S[i][0] = rs.getColumnName(i).toUpperCase().trim();

            for(int j = 0 ; j< rs2.getCount();j++)
            { 	rs2.moveToPosition(j);
                S[i][j+1]= rs2.getString(0).trim();

            }

        }



        for(int i = 0;i<S.length;i++)
        {
            for(int j=0;j< S[i].length;j++)
            {
                System.out.print(S[i][j]+" ");
            }
            System.out.print("\n");
        }//logg

        //__________________________________________________________________________________________
        //									Creating spinner and adding data

        Spinner sp[] = new Spinner[rs.getColumnCount()];


        Ctr = new TableRow(context);

        for(int i =0; i < rs.getColumnCount() ; i++)
        {
            sp[i] = new Spinner(context);
            Ctr.addView(sp[i]);
        }
        TL.addView(Ctr); // Adding Spinners In first row

        for(int i=0 ; i< rs.getColumnCount();i++)
        {ArrayAdapter<String> ad = new ArrayAdapter<String>(context,R.layout.spinner_layout,S[i]);
            sp[i].setAdapter(ad);
        }
        // __________________________________________________________________________________________


        createDB();


        //__________________________________________________________________________________________
        //									Adding listeners to spinners

        TableRow gtr = (TableRow)TL.getChildAt(0);
        Spinner gsp[] = new Spinner[gtr.getChildCount()];
        for(int i=0; i < gtr.getChildCount() ; i++)
        { gsp[i]  = (Spinner)gtr.getChildAt(i);

            gsp[i].setOnItemSelectedListener(new spinAction());
        }

        //__________________________________________________________________________________________


        Button B1 = (Button) findViewById(R.id.VB1);
        B1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                if(counter==0)
                {addListen();
                    modify(0);
                    counter=1;
                }
                else
                {
                    modify(1);
                    counter=0;

                }

            }
        });

        Button B2 = (Button) findViewById(R.id.VB2);
        B2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                modify(1);

                rs.moveToFirst();

                String[] Query= new String[rs.getCount()];

                Query = toUpdateQuery("batches");

                mydb.openDatabase();


                for(int i =0;i<Query.length;i++)
                {
                    mydb.sqldb.execSQL(Query[i]);

                }

                rs = mydb.sqldb.rawQuery("select * from batches;", null);

                deleteDB(0);
                createDB();

            }
        });


        Handler h = new Handler();
        h.postDelayed(r, 1000);

    }//ON CREATE


    Runnable r  =  new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub

            addListen();
        }
    };

    class aListen implements View.OnClickListener {

        int pos;
        aListen(int pos)
        {
            this.pos=pos;
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub


            int ID = Integer.parseInt(((EditText)((TableRow)TL.getChildAt(pos)).getChildAt(0)).getText().toString().trim());



            Intent i = new Intent(AttandanceMenu.this,AttandanceActivity.class);
            i.putExtra("ID", ID);
            startActivity(i);

        }

    }


    class spinAction implements AdapterView.OnItemSelectedListener
    {

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1,
                                   int arg2, long arg3) {
            // TODO Auto-generated method stub


            pos=0;

            for(int i=0;i<((TableRow)TL.getChildAt(0)).getChildCount();i++)
            {
                if(arg0==((TableRow)TL.getChildAt(0)).getChildAt(i))
                {
                    pos = i;
                    break;
                }

            }

            int flag=0;
            for(int i =0 ; i < rs.getColumnCount();i++)
            {
                if(spinFlag[i]==1 & i!=pos)
                {	flag=1;
                    deleteDB(1);
                    createDBfromSave();
                    break;
                }

            }

            if(flag==0)
            {deleteDB(0);
                createDB();
            }


            String def = arg0.getItemAtPosition(0).toString();
            System.out.println("def" + def);

            TextView gTV = (TextView) arg1;

            TableRow gtr2;
            TextView gTV2 ;


            String temp = gTV.getText().toString().trim();
            String temp2;

            System.out.println("temp"+temp);

            for(int i =2 ; i< TL.getChildCount() ; i++)
            {


                gtr2 = (TableRow)TL.getChildAt(i);
                gTV2 = (TextView)gtr2.getChildAt(pos);

                temp2= gTV2.getText().toString().trim();
                if(!temp.equals(temp2)&& !temp.equals(def))
                {
                    System.out.println("temp2"+temp2);
                    TL.removeViewAt(i);
                    i--;
                }
            }


            if(temp.equals(def))
            {spinFlag[pos]=0;
                savedState[pos]=null;
            }
            else
                spinFlag[pos]=1;

            addListen();

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }


    }// custom spinner listener


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view, menu);
        return true;
    }

}
