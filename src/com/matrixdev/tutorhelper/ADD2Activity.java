package com.matrixdev.tutorhelper;




import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import com.matrixdev.tutorhelper.ADDActivity.inputDate;
import com.matrixdev.tutorhelper.ADDActivity.inputTime;

import MyDatabasePkg.MyDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.View.OnTouchListener;
import android.widget.AbsoluteLayout;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ADD2Activity extends Activity {

	private Context context = this;
	TableLayout TL;
	MyDatabase mydb;
	Cursor rs,rs2;
	EditText TV[];
	TableRow Ctr,editRow;
	String data[];
	String id;
	int colWidth[];
	AlertDialog ad;
	
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
			TV[i].setText("\t"+rs.getColumnName(i).toUpperCase()+"\t");		
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
				TV[j].setText("\t"+rs.getString(j)+"\t");
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
	
	String ValidatePhn(EditText et)
	{
		
		String s =et.getText().toString();
		
		
		try {
			if(s.length()>10)
				throw new Exception();
			
			long phn = Long.parseLong(s);
						
			return "";
		} catch (Exception e) {
		
			return "Syntax for Phn no : 9876543210 \n";
						
		}

		
	}
	
	String ValidateNumber(EditText et)
	{
		
		String s =et.getText().toString();
		
		
		try {
						
			long fee = Long.parseLong(s);
						
			return "";
		} catch (Exception e) {
		
			return "Syntax for Fee : <should only consist of numbers> \n";
						
		}

		
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
			return "Syntax for date : DD-MM-YYYY \n";
						
		}

		
	}
	String Validate()
	{
		
		String err = "";
		
		for(int i =0;i<editRow.getChildCount();i++)
		{
			
			switch(i)
			{
			
			case 2: err += ValidatePhn((EditText) editRow.getChildAt(i));
				break;
			case 3:	
			case 4: err += ValidateDate((EditText) editRow.getChildAt(i));
			break;
			case 6: err += ValidateNumber((EditText) editRow.getChildAt(i));
			
			
			
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
			case 3 :
			case 4 : editRow.getChildAt(i).setOnClickListener(new inputDate());
					 editRow.getChildAt(i).setFocusable(false);
					 break;
			case 5 :editRow.getChildAt(i).setOnClickListener(new inputFeeType());
					editRow.getChildAt(i).setFocusable(false);
			
			
			}
			
		}
		
		
	}
	
	//__________________________________________________________________________________
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);

		id = getIntent().getStringExtra("ID");
		id="stu"+id;
		
		TL = (TableLayout)   findViewById(R.id.TB1);
		editRow = (TableRow)   findViewById(R.id.tableRow5);

		
		mydb =  new MyDatabase(context);
		mydb.openDatabase();
		
		rs=  mydb.sqldb.rawQuery("select * from "+id+";", null);
		rs.moveToFirst();
		
		TV = new EditText[rs.getColumnCount()];
		colWidth = new int[rs.getColumnCount()];
		
		//_________________________________________________________________________________________
		
		createDB();
		
		
		//______________________________________________________________________________________________
		
		
		for(int i =0 ; i < rs.getColumnCount() ; i++)
		{
			TV[i] = new EditText(context);
			
			TV[i].setSingleLine();		
			
			editRow.addView(TV[i]);
			
		
		}
		
		setId();
		
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
					AlertDialog ad =  new AlertDialog.Builder(ADD2Activity.this).create();
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
				
				String query= "insert into "+id+" values(";
				
				query+=data[0];
				for(int i =1;i<data.length;i++)
				{
					query += "," + data[i];
								
				}
				
				query+=");";
				
				System.out.println(query);
				
                mydb.sqldb.execSQL(query);  
				mydb.sqldb.execSQL("create table fee"+id.substring(3)+"_"+((EditText)editRow.getChildAt(0)).getText().toString().trim()+"(\n" +
                        "sno integer primary key autoincrement,\n" +
                        "Submitted_On date,\n" +
                        "Amount float ); ");

                Toast.makeText(ADD2Activity.this, "Record added successfully", Toast.LENGTH_LONG).show();
					
				TL.removeAllViews();
				
				rs=  mydb.sqldb.rawQuery("select * from "+id+";", null);
				createDB();
				
				
				for(int i =0; i < editRow.getChildCount() ; i++)
				{
				((EditText)editRow.getChildAt(i)).setText("");
				}
				
				setId();
				
				
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

		
		applyEditRowFeatures();
	
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
			
			DatePickerDialog dpd = new DatePickerDialog(ADD2Activity.this, new OnDateSetListener() {
				
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
	
	class inputFeeType implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			final EditText et = (EditText) v;
			
			
			final AlertDialog.Builder Build =  new AlertDialog.Builder(ADD2Activity.this);
			
			Build.setTitle("Select A Fee Type");
			
			Build.setIcon(R.drawable.ic_launcher);
			
			
			final ArrayAdapter<String> aa = new ArrayAdapter<String>(ADD2Activity.this, android.R.layout.select_dialog_singlechoice); 
			aa.add("Monthly");
			aa.add("Quaterly");
			aa.add("Half-Yearly");
			aa.add("Annually");
						
			Build.setAdapter(aa, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
					
					
					String txt = aa.getItem(which);
					
					ad.setTitle(txt);
					
					et.setText(txt);
					
				}
			});
			
			
			
			 ad = Build.create();
			
			 ad.show();
		}
		
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add, menu);
		return true;
	}

}
