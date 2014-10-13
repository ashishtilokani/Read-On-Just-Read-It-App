package com.example.justreadit;

import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.Engine;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Menuscreen extends Activity implements OnClickListener{

    Button words,para,choose;
	int word_para;
    SharedPreferences s;
    AlertDialog alert;
    LinearLayout linear;
    RadioButton easy,inter,chal;
    Intent i;
	Intent i1;
	RadioGroup rg;
    DataBaseHandler db;
    private TextToSpeech tts;
	private static int TTS_DATA_CHECK = 1;
	private boolean isTTSInitialized = false;
    
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		db=new DataBaseHandler(this);
		
		s=PreferenceManager.getDefaultSharedPreferences(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		words=(Button)findViewById(R.id.button1);
	    para=(Button)findViewById(R.id.button2);
		choose=(Button)findViewById(R.id.button3);
	    words.setOnClickListener(this);
	    para.setOnClickListener(this);
	    choose.setOnClickListener(this);
	    alert= new AlertDialog.Builder(this).create();
	    linear=new LinearLayout(this);
		i=new Intent(this,Second.class);
		i1=new Intent(this,Para.class);
		 confirmTTSData();
		
rg=new RadioGroup(this);
	
easy=new RadioButton(this);	
inter= new RadioButton(this);
chal = new RadioButton(this);
easy.setText("Easy");
inter.setText("Intermediate");
chal.setText("Challenging");
easy.setId(1);
inter.setId(2);
chal.setId(3);

rg.addView(easy);
rg.addView(inter);
rg.addView(chal);

		
easy.setChecked(true);

		linear.addView(rg);
		linear.setOrientation(1);
		
		alert.setView(linear);
		
		alert.setTitle("Set Difficulty");
		
		alert.setButton( "OK",new DialogInterface.OnClickListener() 
		{public void onClick(DialogInterface dialog,int id) {
		    	
		   if(word_para==0 )
			{
			   
			   switch(rg.getCheckedRadioButtonId())
			   { case 1:
				   if(db.getContent(Constants.EASY_WORDS_TABLE).size()==0)
				   Toast.makeText(getApplicationContext(), "No words in this difficulty level", Toast.LENGTH_SHORT).show();
				   else {i.putExtra("difficulty",rg.getCheckedRadioButtonId());
				     startActivity(i);}
				   break;
			   case 2:if(db.getContent(Constants.INTERMEDIATE_WORDS_TABLE).size()==0)
				   Toast.makeText(getApplicationContext(), "No words in this difficulty level", Toast.LENGTH_SHORT).show();
			   else {i.putExtra("difficulty",rg.getCheckedRadioButtonId());
			     startActivity(i);   }
			   break;
			   case 3:if(db.getContent(Constants.CHALLENGING_WORDS_TABLE).size()==0)
				   Toast.makeText(getApplicationContext(), "No words in this difficulty level", Toast.LENGTH_SHORT).show();
			   else { i.putExtra("difficulty",rg.getCheckedRadioButtonId());
			     startActivity(i);}
			   }
			}
		    else
		    {switch(rg.getCheckedRadioButtonId())
				   { case 1:
					   if(db.getContent(Constants.EASY_SENTENCES_TABLE).size()==0)
					   Toast.makeText(getApplicationContext(), "No sentences in this difficulty level", Toast.LENGTH_SHORT).show();
				   else {i1.putExtra("difficulty",rg.getCheckedRadioButtonId());
					     startActivity(i1);}
					   break;
				   case 2:if(db.getContent(Constants.INTERMEDIATE_SENTENCES_TABLE).size()==0)
					   Toast.makeText(getApplicationContext(), "No sentences in this difficulty level", Toast.LENGTH_SHORT).show();
				   else { i1.putExtra("difficulty",rg.getCheckedRadioButtonId());
				     startActivity(i1);   }
				   break;
				   case 3:if(db.getContent(Constants.CHALLENGING_SENTENCES_TABLE).size()==0)
					   Toast.makeText(getApplicationContext(), "No sentences in this difficulty level", Toast.LENGTH_SHORT).show();
				   else { i1.putExtra("difficulty",rg.getCheckedRadioButtonId());
				     startActivity(i1);}
				   
				   }
		    }
		}	
		   
		});

	}
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {    
	
		Intent i=new Intent(this,SettingsActivity.class);
	    startActivity(i);
	    return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id=v.getId();
		switch(id)
		{case R.id.button1:
			readItBaby("Read Words");
			word_para=0;
            alert.show();    
			break;
			
		 case R.id.button2:
			 readItBaby("Read Sentences");
			 word_para=1;
			 alert.show();
			 break;
		 
		 case R.id.button3:
			 readItBaby("Pick correct letters");
			 Intent k=new Intent(this,Choose.class);
			 startActivity(k);
		}		
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	
	private void confirmTTSData()  {
    	Intent intent = new Intent(Engine.ACTION_CHECK_TTS_DATA);
    	startActivityForResult(intent, TTS_DATA_CHECK);
    }
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    	
	    	
	    	
	    		if (resultCode == Engine.CHECK_VOICE_DATA_PASS) {
	    			initializeTTS();
	    		}
	    		else {
	    			Intent installIntent = new Intent(Engine.ACTION_INSTALL_TTS_DATA);
	    			startActivity(installIntent);
	    		}
	    	
	    }
	
	 
	private void initializeTTS() {
    	tts = new TextToSpeech(this, new OnInitListener() {
    		public void onInit(int status) {
    			if (status == TextToSpeech.SUCCESS) {
    				isTTSInitialized = true;
    			}
    			else {
    			//Handle initialization error here
    				isTTSInitialized = false;
    			}
    		}
    	});
    }
	

	void prepare(){
		SharedPreferences s1=PreferenceManager.getDefaultSharedPreferences(this);
    		if(s1.getString("Accent","US")=="US")
    		tts.setLanguage(Locale.US);
    		else tts.setLanguage(Locale.UK);
    		tts.setPitch(s1.getInt("Pitch",10)/10);
    		tts.setSpeechRate(s1.getInt("Speech",10)/10);
    }
	
	private void readItBaby(String string) {
		// TODO Auto-generated method stub
		if(isTTSInitialized)
   	 {
			prepare();
			tts.speak(string, TextToSpeech.QUEUE_ADD, null);
   	 }
	}
}