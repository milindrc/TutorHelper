package com.matrixdev.tutorhelper;



import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.conn.scheme.LayeredSocketFactory;



import MyDatabasePkg.MyDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnLayoutChangeListener;
import android.view.View.OnTouchListener;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class ADDActivity extends Activity {

	private Context context = this;
	TableLayout TL;
	MyDatabase mydb;
	Cursor rs,rs2;
	EditText TV[];
	TableRow Ctr,editRow;
	String data[];
	int colWidth[];
	
	void createDB()
	{
		
		mydb.openDatabase();
		
		//mydb.sqldb.execSQL("insert into tb1 values(null,'A','X' );");
		
		rs.moveToFirst();		
		
		Ctr = new TableRow(context);
		for(int i =0; i < rs.getColumnCount() ; i++)
		{	
			TV[i] =new EditText(context);
			TV[i].addOnLayoutChangeListener(new myWidth(i));
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
		
	} //Creating Table View Function

	
	
	void addListen()
	{
		TableRow r;
		
		for(int i =1; i< TL.getChildCount() ; i++)
		{	r= (TableRow) TL.getChildAt(i);
			for(int j = 0;j<r.getChildCount() ; j++)
			{
				r.getChildAt(j).setOnClickListener(new eListen(i));
				
				
			}
			
		}
		
			
	}
	
	void setMyWidth()
	{
		for(int i =0 ; i<editRow.getChildCount();i++)
		{
			
			((EditText)editRow.getChildAt(i)).setMinWidth(colWidth[i]);
			((EditText)editRow.getChildAt(i)).setMaxWidth(colWidth[i]);
			
		}
		
	}
	
	
	void setId()
	{
		int id;
		if(rs.getCount()>0)
		{
		 rs.moveToPosition(rs.getCount()-1); 
		 id = rs.getInt(0)+1;
		}
		 else 
		 id=1;
			 
		editRow.getChildAt(0).setEnabled(false);
			 ((EditText) editRow.getChildAt(0)).setText(""+id);
		
				
	}
	
	
	String ValidateDate(EditText et)
	{
		
		String s =et.getText().toString();
		
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
    	Date d;
		try {
			d = format.parse(s);
			return "";
		} catch (ParseException e) {
			e.printStackTrace();
			return "Syntax for due date : DD-MM-YYYY \n";
						
		}

		
	}
	
	
	
	String ValidateTime(EditText et)
	{
		
		String s =et.getText().toString();
		
		SimpleDateFormat format1 = new SimpleDateFormat("hh:mmaa");
		SimpleDateFormat format2 = new SimpleDateFormat("hh:mm aa");
		SimpleDateFormat format3 = new SimpleDateFormat("hhaa");
			
		String flag="";
			
		try {
				 format1.parse(s);

			   flag="Valid";
		} catch (Exception e) {
			
			if(flag.equals(""))
			   flag="Invalid";
	
		}
		
		
		try {
		
		format2.parse(s);
		
		   flag="Valid";
		} catch (Exception e) {
		
		if(flag.equals(""))
			   flag="Invalid";
				
		}
	
		try {
			 format3.parse(s);
			   flag="Valid";
		} catch (Exception e) {
		
		if(flag.equals(""))
			   flag="Invalid";
			
		}
	
		if(flag.equals("Valid"))
			return "";
		else
			return "Syntax for Time : hh:mmpm  ,  hh:mm pm , hhpm \n";

		
	}
	
	String ValidateNumber(EditText et)
	{
		
		String s =et.getText().toString();
		
		
		try {
						
			long fee = Long.parseLong(s);
						
			return "";
		} catch (Exception e) {
		
			return "Syntax for Students : <should only consist of numbers> \n";
						
		}

		
	}
	
	String Validate()
	{
		
		String err = "";
		
		for(int i =0;i<editRow.getChildCount();i++)
		{
			
			switch(i)
			{
			
			case 2: err += ValidateTime((EditText) editRow.getChildAt(i));
				break;
			case 4: err += ValidateNumber((EditText) editRow.getChildAt(i));
				break;
			case 7: err += ValidateDate((EditText) editRow.getChildAt(i));
			
			
			
			
			}
		}
		
		return err;
	}
	
	void applyEditRowFeatures()
	{
		
		for(int i=0;i<editRow.getChildCount();i++)
		{
			switch(i)
			{
			case 2 : editRow.getChildAt(i).setOnClickListener(new inputTime());
			 		 editRow.getChildAt(i).setFocusable(false);
			 		 break;
			case 7 : editRow.getChildAt(i).setOnClickListener(new inputDate());
					 editRow.getChildAt(i).setFocusable(false);
					 break;
			
			
			}
			
		}
		
		
	}
	
	//__________________________________________________________________________________
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		
		
		TL = (TableLayout)   findViewById(R.id.TB1);
		editRow = (TableRow)   findViewById(R.id.tableRow5);

		
		mydb =  new MyDatabase(context);
		mydb.openDatabase();
		
		rs=  mydb.sqldb.rawQuery("select * from batches;", null);
		rs.moveToFirst();
		
		TV = new EditText[rs.getColumnCount()];
		colWidth = new int[rs.getColumnCount()];
		//_________________________________________________________________________________________
		
		createDB();
		
		addListen();
		
		//______________________________________________________________________________________________
		
		
		for(int i =0 ; i < rs.getColumnCount() ; i++)
		{
			TV[i] = new EditText(context);
			
		//	EditText temp =(EditText)((TableRow)TL.getChildAt(0)).getChildAt(i); 
			
			TV[i].setSingleLine();				
			
			editRow.addView(TV[i]);
					
		}
		
		setId();
		applyEditRowFeatures();
		
		//_____________________________________________________________________________________________
		
				
		
		Button B = (Button) findViewById(R.id.ADD);
		
		B.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				String V =	Validate();
				
				if(V.equals(""))
					Toast.makeText(getBaseContext(), "Data Ok", Toast.LENGTH_LONG).show();
				else
				{
					AlertDialog ad =  new AlertDialog.Builder(ADDActivity.this).create();
					ad.setTitle("Syntax Errorr");
					ad.setMessage(V);					
					ad.show();
					return;
				}
				
				
				mydb.openDatabase();
				
				
								
				
				data  = new String[rs.getColumnCount()];
								
				for(int i =0; i < editRow.getChildCount() ; i++)
				{
				data[i] =((EditText)editRow.getChildAt(i)).getText().toString();
				
				if(data[i].equals(""))
					data[i]="null";
				else
				{
				  data[i] = "'" + data[i] + "'";
			
				}//else
				
				
				}//for
				
	//______________________________________________________________________________			
				
				String query= "insert into batches values(";
				
				query+=data[0];
				for(int i =1;i<data.length;i++)
				{
					query += "," + data[i];
								
				}
				
				query+=");";
				
				System.out.println(query);
				
                mydb.sqldb.execSQL(query);  
				mydb.sqldb.execSQL("CREATE TABLE stu"+data[0].substring(1, data[0].length()-1)+" ('ID' integer primary key autoincrement,name TEXT, phn_no TEXT, Due_date TEXT, last_pay TEXT, fee_type TEXT, fee TEXT); ");


                Toast.makeText(ADDActivity.this, "Record added successfully", Toast.LENGTH_LONG).show();
					
				TL.removeAllViews();
				
				rs=  mydb.sqldb.rawQuery("select * from batches;", null);
				createDB();
				
				
				
				for(int i =0; i < editRow.getChildCount() ; i++)
				{
				((EditText)editRow.getChildAt(i)).setText("");
				}
				
				setId();
				
				addListen();
				
			}//onClick
		});//Listener
		
		
		
		//________________________________________________________________________________________
		
		//Syncing scrollbars
		
				final HorizontalScrollView scrollView1 = (HorizontalScrollView) findViewById(R.id.scroll1);
				final HorizontalScrollView scrollView2 = (HorizontalScrollView) findViewById(R.id.scroll2);
				
				
				
				scrollView1.setOnTouchListener(new OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
						
						 switch (event.getAction()) {
					        case MotionEvent.ACTION_MOVE:
					        	
					        	scrollView2.scrollTo(scrollView1.getScrollX(), 0);
					        	
						 }
						
						return false;
					}
				});
				
				
				
				
				scrollView2.setOnTouchListener(new OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
						
						 switch (event.getAction()) {
					        case MotionEvent.ACTION_MOVE:
					        	
					        	scrollView1.scrollTo(scrollView2.getScrollX(), 0);
					        	
						 }
						
						return false;
					}
				});
		
		//________________________________________________________________________________________
 	
	}
	
	class myWidth implements OnLayoutChangeListener
	{
		int index;
		
		myWidth(int index)
		{this.index=index;
		}
		
		
		@Override
		public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop,
				int oldRight, int oldBottom) {
			// TODO Auto-generated method stub
			
			System.out.println("*" + index +"-"+(right-left));
			colWidth[index]=right-left;
		
			setMyWidth();
		}
		
	}
	
	
	class eListen implements OnClickListener{

		int pos;
		eListen(int pos)
		{
			this.pos=pos;
		}
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			String a = ((EditText)((TableRow)TL.getChildAt(pos)).getChildAt(0)).getText().toString().trim();
			
			
			Toast.makeText(ADDActivity.this,"Id"+a, Toast.LENGTH_LONG).show();
			
			Intent i = new Intent(ADDActivity.this,ADD2Activity.class);
			i.putExtra("ID", a);
			startActivity(i);
			
		}
		
	}
	
	class inputDate implements OnClickListener
	{

		@Override
		public void onClick(final View v) {
			// TODO Auto-generated method stub
			
			int y,m,d;
			
			Calendar c = Calendar.getInstance();
			
			y=c.get(Calendar.YEAR);
			m=c.get(Calendar.MONTH);
			d=c.get(Calendar.DAY_OF_MONTH);
			
			DatePickerDialog dpd = new DatePickerDialog(ADDActivity.this, new OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
					// TODO Auto-generated method stub
					EditText et = (EditText) v;
					
					et.setText("" + dayOfMonth +"-" + monthOfYear + "-" + (year%100));
					
				}
			}, y, m, d); 
			
			dpd.show();
			
			
			
		}
		
				
	}
	class inputTime implements OnClickListener
	{

		@Override
		public void onClick(final View v) {
			// TODO Auto-generated method stub
			
			int t,m; 
			
			Calendar c = Calendar.getInstance();
			
			t=c.get(Calendar.HOUR);
			m=c.get(Calendar.MINUTE);
			
			TimePickerDialog dpd = new TimePickerDialog(ADDActivity.this, new OnTimeSetListener() {

				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					// TODO Auto-generated method stub
					EditText et = (EditText) v;
					
					String x="am"; 
					
					if(hourOfDay>12)
						x="pm";
					
					et.setText(""+hourOfDay%12 + ":" + minute+x);
				}
			}
			,t,m,false);		
			dpd.show();
			
			
			
		}
		
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add, menu);
		return true;
	}
	
	
	
}
