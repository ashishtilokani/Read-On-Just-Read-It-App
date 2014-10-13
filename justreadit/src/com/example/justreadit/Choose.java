package com.example.justreadit;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.Engine;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Choose extends Activity implements OnClickListener{

TextView t1,t2;
String word1,word2,word3;
 
private TextToSpeech tts;
private static int TTS_DATA_CHECK = 1;
private boolean isTTSInitialized = false;
Button help,next;
ImageButton check;
int r1,r2,r3;	
TextView overleft,overright;
Random randomGenerator = new Random();
protected static final int RESULT_SPEECH = 1;

	String[] a={"p","n","m","d","b","t","b",Character.toString((char)(592)),Character.toString((char)(596)),"9",Character.toString((char)(670)),Character.toString((char)(623)),Character.toString((char)(633)),Character.toString((char)(647)),Character.toString((char)(652)),Character.toString((char)(653)),Character.toString((char)(654)),Character.toString((char)(8704)),Character.toString((char)(1508)),Character.toString((char)(383)),Character.toString((char)(1280)),Character.toString((char)(9524)),Character.toString((char)(8745)),Character.toString((char)(923)),Character.toString((char)(12581)),"q","u","w","q","p","f","d"};
	String[] b={"q","u","w","q","p","f","d","a","c","6","k","m","r","t","v","w","y","A","G","J","P","T","U","V","7","p","n","m","d","b","t","b"};
	
	SharedPreferences s;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		s=PreferenceManager.getDefaultSharedPreferences(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose);	  		
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		
		t1=(TextView)findViewById(R.id.textView1);
	    t2=(TextView)findViewById(R.id.textView2);
	    overleft=(TextView)findViewById(R.id.overleft);
	    overright=(TextView)findViewById(R.id.overright);
	    overleft.setVisibility(View.GONE);
	    overright.setVisibility(View.GONE);
	    setword();
	    confirmTTSData();
	    t1.setOnClickListener(this);
	    t2.setOnClickListener(this);
	    help=(Button)findViewById(R.id.button1);
	    help.setOnClickListener(this);
	    next=(Button)findViewById(R.id.button2);
	    next.setOnClickListener(this);
	    
	    check=(ImageButton)findViewById(R.id.btnSpeak);
	    check.setOnClickListener(this);
	    
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {    
		Intent i=new Intent(this,SettingsActivity.class);
	    startActivity(i);
	    return true;
	}
	
	void prepare(){
    	
    		if(s.getString("Accent","US")=="US")
    		tts.setLanguage(Locale.US);
    		else tts.setLanguage(Locale.UK);
    		tts.setPitch(s.getInt("Pitch",10)/10);
    		tts.setSpeechRate(s.getInt("Speech",10)/10);
    	
    }
	
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	void setword()
	{
		r1 = randomGenerator.nextInt(32);
     r2=randomGenerator.nextInt(2);
	
     if(r2==0)
	 {   word1=a[r1];
	  word2=b[r1];}
	  else
	 { word1=b[r1];
	word2=a[r1];
	}
	
	  word3=b[r1];
	   overleft.setText(word1);
	   overright.setText(word2);
	   t1.setText(word1);
	   t2.setText(word2);
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

	private void confirmTTSData()  {
    	Intent intent = new Intent(Engine.ACTION_CHECK_TTS_DATA);
    	startActivityForResult(intent, TTS_DATA_CHECK);
    }

    
    private void read() {
    	if(isTTSInitialized)
    	{  prepare();
    		tts.speak(word3, TextToSpeech.QUEUE_ADD, null);
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
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode== RESULT_SPEECH)
    		if (resultCode == RESULT_OK && null != data) {

				ArrayList<String> text = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

				if(text.get(0).equals(word3))
					{Toast.makeText(this,"Correct",Toast.LENGTH_SHORT).show();
					readItBaby("Correct");
					}
				else
					{Toast.makeText(this,"Not Correct",Toast.LENGTH_SHORT).show();
					readItBaby("Not Correct");
					}
    		}
    	
    	else if (requestCode == TTS_DATA_CHECK) {
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
    
		
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
       	int id=v.getId();
		switch(id)
       	{case R.id.button1:
       		if(word3==word1)
       		overleft.setVisibility(View.VISIBLE);
       		else overright.setVisibility(View.VISIBLE);		
       	    break;
       	
       	case R.id.btnSpeak:
                  read();
                  break;
       		
       	case R.id.textView1:
       		if(word3.equals(word1))
       			{Toast.makeText(this,"Correct",Toast.LENGTH_SHORT).show();
       			readItBaby("Correct");
       			}
       			else		
       		    {Toast.makeText(this,"Not Correct",Toast.LENGTH_SHORT).show();
       		 readItBaby("Not Correct");
       		    }
       		    break;
        case R.id.textView2:
       		if(word3.equals(word2))
       		{Toast.makeText(this,"Correct",Toast.LENGTH_SHORT).show();
       		readItBaby("Correct");
       		}
       		else
       		{Toast.makeText(this,"No Correct",Toast.LENGTH_SHORT).show();
       		readItBaby("Not Correct");
       		}
       		break;
    	case R.id.button2:
    		overleft.setVisibility(View.GONE);
    		overright.setVisibility(View.GONE);
    		setword();
    		break;
       	}
	}

	@Override
    public void onDestroy() {
    	if (tts != null) {
    		tts.stop();
    		tts.shutdown();
    	}
    	super.onDestroy();
    }

}