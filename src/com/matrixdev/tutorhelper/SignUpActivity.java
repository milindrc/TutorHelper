package com.matrixdev.tutorhelper;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		
		final EditText T1 = (EditText) findViewById(R.id.ST1);
		final EditText T2 = (EditText) findViewById(R.id.ST2);
		final EditText T3 = (EditText) findViewById(R.id.ST3);
		final EditText T4 = (EditText) findViewById(R.id.ST4);
		
		
		final SharedPreferences admin = getSharedPreferences("admin", 0);
		final SharedPreferences guest = getSharedPreferences("guest", 0);
		
		String a= admin.getString("username", "");
		String b= admin.getString("password", "");
		
		T1.setText(a);
		T2.setText(b);
		
		
		final Button B1 = (Button) findViewById(R.id.SB1);
		B1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String a = T1.getText().toString();
				String b = T2.getText().toString();
				
				SharedPreferences.Editor eadmin = admin.edit(); 
				
				eadmin.putString("username", a);
				eadmin.putString("password", b);
				
				eadmin.commit();
				
				T1.setEnabled(false);
				T2.setEnabled(false);
				B1.setEnabled(false);
				
				
				Toast.makeText(getBaseContext(), "Admin ID, Password modified", Toast.LENGTH_LONG).show();
				
				
			}
		});
		
		final Button B2 = (Button) findViewById(R.id.Register);
		B2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String a = T3.getText().toString();
				String b = T4.getText().toString();
				
				
				SharedPreferences.Editor eguest = guest.edit();
				
				eguest.putString(a, b);
				
				eguest.commit();
				
				T3.setText("");
				T4.setText("");
				
				Toast.makeText(getBaseContext(), "guest created :" +a, Toast.LENGTH_LONG).show();;
							
				
			}
		});
		
		
		final CheckBox C1 = (CheckBox) findViewById(R.id.SC1);
		C1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				
				if(C1.isChecked()==true)
				{T1.setEnabled(true);
				T2.setEnabled(true);
				B1.setEnabled(true);
				}
				else
				{T1.setEnabled(false);
				T2.setEnabled(false);
				B1.setEnabled(false);
				}
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
