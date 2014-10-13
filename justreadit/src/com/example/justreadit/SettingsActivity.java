package com.example.justreadit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

public class SettingsActivity extends Activity implements OnClickListener{

	RadioButton us,uk;
	SharedPreferences s;
	Editor editor;
	AlertDialog alert,alert3,alert2;
	SeekBar seek,seek3;
	
	Button pitch,speech,accent;
	LinearLayout linear,linear2,linear3;
	@SuppressWarnings("deprecation")
	protected void onCreate(Bundle savedInstanceState) {
		s=PreferenceManager.getDefaultSharedPreferences(this);
		editor=s.edit();
		alert= new AlertDialog.Builder(this).create();
		alert2= new AlertDialog.Builder(this).create();
		alert3= new AlertDialog.Builder(this).create();
		linear=new LinearLayout(this);
		linear3=new LinearLayout(this);
		linear2=new LinearLayout(this);
		seek=new SeekBar(this);
		seek3=new SeekBar(this);
	    us=new RadioButton(this);
	   uk=new RadioButton(this);
		
		us.setText("US");
		uk.setText("UK");
		
		RadioGroup rg=new RadioGroup(this);
		
		rg.addView(us);
		us.setId(1);
		rg.addView(uk);

		us.setOnCheckedChangeListener(null);
		
		linear2.addView(rg);
		linear2.setOrientation(1);
		
		linear.setOrientation(1);
		linear.addView(seek);
		
		linear3.setOrientation(1);
		linear3.addView(seek3);
		
		alert2.setTitle("Set Accent");
		 
		alert.setButton( "OK",new DialogInterface.OnClickListener() 
		{public void onClick(DialogInterface dialog,int id) {
		  editor.putInt("Pitch",seek.getProgress());
		  editor.commit();
		}
	         });
		
		alert3.setButton( "OK",new DialogInterface.OnClickListener() 
		{public void onClick(DialogInterface dialog,int id) {
			editor.putInt("Speech",seek3.getProgress());
		    editor.commit();  
		    
		}
	         });
	
		
		alert2.setButton( "OK",new DialogInterface.OnClickListener() 
		{public void onClick(DialogInterface dialog,int id) {
			if(us.isChecked()==true)
			editor.putString("Accent","US");
			  else 
			 editor.putString("Accent","UK");
			editor.commit();
		}
	         });
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		pitch=(Button)findViewById(R.id.button3);
		speech=(Button)findViewById(R.id.Button01);
		accent=(Button)findViewById(R.id.button2);
		pitch.setOnClickListener(this);
		speech.setOnClickListener(this);	
		accent.setOnClickListener(this);	
		}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id=v.getId();
		switch(id)
		{case R.id.button3: 
			seek.setMax(20);
			seek.setProgress(s.getInt("Pitch",10));
			alert.setView(linear);
			alert.setTitle("Set Pitch");
			alert.show();	    
		    break;
		
		 case R.id.Button01 : 
			seek3.setMax(30);
			seek3.setProgress(s.getInt("Speech",10));
			alert3.setView(linear3);
			alert3.setTitle("Set Speech Rate");
			alert3.show();
       		break;
       		
		 case R.id.button2 :			 
				if(s.getString("Accent","US").equals("US"))
				us.setChecked(true);
				else uk.setChecked(true);
		     	alert2.setView(linear2);		
		        alert2.show();
		        break;
		
		
		}
   }

}