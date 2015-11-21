package com.matrixdev.tutorhelper;





import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	Intent music ;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        
        Button B1 = (Button) findViewById(R.id.ADD);
		final TextView T1 = (TextView) findViewById(R.id.text1);
		final TextView T2 = (TextView) findViewById(R.id.text2);
	
    	
		final CheckBox C1 = (CheckBox) findViewById(R.id.SC1);
		
		
        
        
		    final SharedPreferences admin =  getSharedPreferences("admin", 0);
	        final SharedPreferences guest =  getSharedPreferences("guest", 0);
	        final SharedPreferences shp = getSharedPreferences("login",0);
	        
	        shp.edit().putString("user", "").commit();  //admin or guest
        
        if(shp.getString("Username", "")!=null && shp.getString("Username","").length()>0 ) //remember me
        {
        	
        T1.setText(shp.getString("Username", ""));	
        T2.setText(shp.getString("Password", ""));	
        
        C1.setChecked(shp.getBoolean("State", false));
                
        
        }
        
        
       
		//############################################### first time ###############################################
        
        final SharedPreferences.Editor ced = admin.edit();
        
        
        if(admin.getString("username", "")==null || admin.getString("username","").length()==0 )
        {
        	
        final Dialog d = new Dialog(MainActivity.this);
        
        d.setCancelable(false);
        
        d.setTitle("Create Credentials");
        
        d.setContentView(R.layout.firstsign);
        
        final EditText CT1 = (EditText) d.findViewById(R.id.ST1);
        final EditText CT2 = (EditText) d.findViewById(R.id.ST2);
        Button CB1 = (Button) d.findViewById(R.id.CB1);
        CB1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String AUsername =CT1.getText().toString();
				String APassword =CT2.getText().toString();
				
				ced.putString("username", AUsername);
				ced.putString("password", APassword);
				
				ced.commit();
				
				Intent i = new Intent(MainActivity.this,MenuActivity.class);
				shp.edit().putString("user", "admin").commit();
				startActivity(i);
				Toast.makeText(getBaseContext(), "Welcome Admin", Toast.LENGTH_LONG).show();
				d.dismiss();
			}
		});
        
        d.show();             
        
        }//if
        
        
        
      //############################################### first time ###############################################
        
        
    	
		
		
    	B1.setOnClickListener(new View.OnClickListener(){
			
			public void onClick(View v)
			{
				Intent i = new Intent(MainActivity.this,MenuActivity.class);
				
				
				String a = T1.getText().toString();
				String b = T2.getText().toString();
				
				
				
		
				
				
				SharedPreferences.Editor ed = shp.edit();
				
				
				if(a.equals(admin.getString("username",""))  &&  b.equals(admin.getString("password", "")))
					{
					
					if (C1.isChecked()==true)
					{ed.putString("Username", T1.getText().toString());
					ed.putString("Password", T2.getText().toString());
					ed.putBoolean("State", true);
					ed.commit();
					}
					else
					{	
						T1.setText("");
						T2.setText("");
						
						ed.clear();
						ed.commit();
					}
					
				shp.edit().putString("user", "admin").commit();
					
					startActivity(i);
					Toast.makeText(getBaseContext(), "Welcome Admin", Toast.LENGTH_LONG).show();
					}
				else if(guest.getString(a, "").equals(b)  &&  guest.getString(a, "").length()>0)
				{
					if (C1.isChecked()==true)
					{ed.putString("Username", T1.getText().toString());
					ed.putString("Password", T2.getText().toString());
					ed.putBoolean("State", true);
					ed.commit();
					}
					else
					{
						T1.setText("");
						T2.setText("");
												
						ed.clear();
						ed.commit();
					}
					
					shp.edit().putString("user", "guest").commit();
					
					startActivity(i);
					Toast.makeText(getBaseContext(), "Welcome Guest", Toast.LENGTH_LONG).show();
					
				}
				else
					{
					ed.clear();
					ed.commit();
					Toast.makeText(MainActivity.this, "Invalid id or password", Toast.LENGTH_LONG).show();
					}
			
			
				
			}
			
			
			
		} );//Listener
    	
    	
    	
  
    	

  
    	
    	
    
    
    }//onCreate

    
    

    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
