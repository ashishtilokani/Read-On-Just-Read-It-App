package com.example.justreadit;

import java.util.Locale;

import android.app.Activity;
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

public class MainMenu extends Activity implements OnClickListener{

	
	Button manageSentence,justr,manageWord;
	private TextToSpeech tts;
	private static int TTS_DATA_CHECK = 1;
	private boolean isTTSInitialized = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		confirmTTSData();
		 initializeTTS();
		
		 manageSentence=(Button)findViewById(R.id.manageSentence);
		 manageSentence.setOnClickListener(this);
		
		 manageWord=(Button)findViewById(R.id.manageWord);
		 manageWord.setOnClickListener(this);
		 
		 justr=(Button)findViewById(R.id.justr);
		 justr.setOnClickListener(this);
		 
		 }
	
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {    
		Intent i=new Intent(this,SettingsActivity.class);
	    startActivity(i);
	    return true;
	}

	@Override
	public void onClick(View v) {
		int id=v.getId();
		//initializeTTS();
		switch(id)
		{case R.id.manageWord:
			readItBaby(manageWord.getText().toString());
			Intent i=new Intent(this,EnterWordsSentences.class);
		    i.putExtra("wordSentence", "word");
			startActivity(i);
			break;
			
		case R.id.manageSentence:
			readItBaby("manage sentence list");
			Intent i1=new Intent(this,EnterWordsSentences.class);
		    i1.putExtra("wordSentence", "sentence");
			startActivity(i1);
			break;	
		
	     case R.id.justr:
	    	 readItBaby("just read it");
			Intent j=new Intent(this,Menuscreen.class);
		    startActivity(j);
			 
		}
		
	}
	
	private void confirmTTSData()  {
    	Intent intent = new Intent(Engine.ACTION_CHECK_TTS_DATA);
    	startActivityForResult(intent, TTS_DATA_CHECK);
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
	
	
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    	 if (requestCode == TTS_DATA_CHECK) {
	    		if (resultCode == Engine.CHECK_VOICE_DATA_PASS) {
	    			//Voice data exists		
	    			initializeTTS();
	    		}
	    		else {
	    			Intent installIntent = new Intent(Engine.ACTION_INSTALL_TTS_DATA);
	    			startActivity(installIntent);
	    		}
	    	}
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
