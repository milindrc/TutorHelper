package com.matrixdev.tutorhelper;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.Toast;

public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
	SharedPreferences shp = getSharedPreferences("login", 0);
		if(shp.getString("user", "").equals("guest"))
			{
			
			((Button) findViewById(R.id.Register)).setVisibility(View.INVISIBLE);
			
			((Button) findViewById(R.id.ADD)).setVisibility(View.INVISIBLE);
			
			((Button) findViewById(R.id.Del)).setVisibility(View.INVISIBLE);
			}
		
		
		
	}

	public void add(View view)
	{
		Intent i = new Intent(MenuActivity.this,ADDActivity.class);
		
		startActivity(i);
		
	}
	
	public void signup(View view)
	{
		Intent i = new Intent(MenuActivity.this,SignUpActivity.class);
		
		startActivity(i);
		
	}
	
	public void view(View view)
	{
		Intent i = new Intent(MenuActivity.this,ViewActivity.class);
		
		startActivity(i);
		
	}
	
	public void delete(View view)
	{
		Intent i = new Intent(MenuActivity.this,DeleteActivity.class);
		
		startActivity(i);
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch (item.getItemId()) {
		case R.id.sdbackup:
			
			sdBackup();
			
			return true;
			
		case R.id.sdrestore:
			
			sdRestore();
			
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
		
		
	
	}

	
	void sdBackup()
	{
		
		try
		{	
			File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) 
            {
                String currentDBPath = "/data/com.matrixdev.tutorhelper/databases/Student.sqlite";
                String backupDBPath = "Student.sqlite";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
             
                    Toast.makeText(getBaseContext(), "SD Card Backup Successful ", Toast.LENGTH_SHORT).show();
        			
                
               
            }else
            {
                Toast.makeText(getBaseContext(), "SD Card not found! ", Toast.LENGTH_SHORT).show();

            }


			
				
		}catch(Exception e){
			Log.w("Settings Backup", e);
			
			Toast.makeText(getBaseContext(), "SD Card Backup Unsuccessful "+e, Toast.LENGTH_SHORT).show();
		}
		
	}


	void sdRestore()
	{
		
		try
		{	
			File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) 
            {
                String currentDBPath = "/data/com.matrixdev.tutorhelper/databases/Student.sqlite";
                String backupDBPath = "Student.sqlite";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                
                    FileChannel src = new FileInputStream(backupDB).getChannel();
                    FileChannel dst = new FileOutputStream(currentDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
             
                    Toast.makeText(getBaseContext(), "SD Card Restore Successful ", Toast.LENGTH_SHORT).show();
        			
                
               
            }
            else
            {
                Toast.makeText(getBaseContext(), "SD Card not found! ", Toast.LENGTH_SHORT).show();

            }


			
				
		}catch(Exception e){
			Log.w("Settings Backup", e);
			
			Toast.makeText(getBaseContext(), "SD Card Restore Unsuccessful "+e, Toast.LENGTH_SHORT).show();
		}
		
	}

	
}
