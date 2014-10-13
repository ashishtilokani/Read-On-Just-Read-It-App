package com.example.justreadit;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.Engine;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Para extends Activity implements OnClickListener{

TextView t;
String word;
List<String> a;
DataBaseHandler db;

private TextToSpeech tts;
private static int TTS_DATA_CHECK = 1;
private boolean isTTSInitialized = false;
Button spell,read,next;
ImageButton check;
int difficulty,r;	
Random randomGenerator = new Random();
protected static final int RESULT_SPEECH = 1;

   int l;
    String[] words;
     
	int i;
	
	String s2[] = {"An apple a day keeps doctor away","Some students like to study in the mornings","Early to rise and early to bed makes a male healthy and wealthy and dead","Expect nothing. Live frugally on surprise","Children are all foreigners"};
	String s3[]={"Atheism is a non-prophet organization","The dog howled and barked","A student who is hungry would never pass up a hamburger","We visited the museum before it closed","Because he was so small Stuart was often hard to find around the house"};
	String s4[]={"Even in the middle of the summer Jan and her dog liked to stay outdoors all afternoon long","Though the music was quite long, it was absorbing","Writing evolved when picture symbols changed to letters","Consumers buy fewer goods when prices rise"};

	String s5[]={"Lord Emsworth adjusted his pince-nez and sought inspiration from the wall-paper","The strong winds buffeted the ships threatening to capsize them","His mother asked him to burnish the silverware before setting the table","The conflagration consumed the entire building","His cupidity made him enter the abandoned gold mine despite the obvious dangers"};
		String s6[]={"Barbara Kingsolver and Amy Tan are two of my sister’s favorite novelists","The captain’s brusque manner offended the passengers","The column buttresses the roof above the statue","Fred’s buddies cajoled him into attending the bachelor party","His credulity made him an easy target for con men"};	

	
//	http://www.beatingdyslexia.com/spelling-word-lists.html
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);	  		
		db=new DataBaseHandler(this);
		Intent i=getIntent();
		difficulty=i.getIntExtra("difficulty",1);
		switch(difficulty)
		{case 1:a=db.getContent(Constants.EASY_SENTENCES_TABLE);
		break;
		case 2:a=db.getContent(Constants.INTERMEDIATE_SENTENCES_TABLE);
		break;
		case 3:a=db.getContent(Constants.CHALLENGING_SENTENCES_TABLE);
		}
		
	    t=(TextView)findViewById(R.id.textView1);
	    setnew(); 
	    setpara();
	    spell=(Button)findViewById(R.id.button1);
	    spell.setOnClickListener(this);
	    read=(Button)findViewById(R.id.button2);
	    read.setOnClickListener(this);
	    next=(Button)findViewById(R.id.button3);
	    next.setOnClickListener(this);
	    check=(ImageButton)findViewById(R.id.btnSpeak);
	    check.setOnClickListener(this);
	    confirmTTSData();
	
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {    
		Intent i=new Intent(this,SettingsActivity.class);
	    startActivity(i);
	    return true;
	}

	void prepare(){
		SharedPreferences s1=PreferenceManager.getDefaultSharedPreferences(this);
    		if(s1.getString("Accent","US")=="US")
    		tts.setLanguage(Locale.US);
    		else tts.setLanguage(Locale.UK);
    		tts.setPitch(s1.getInt("Pitch",10)/10);
    		tts.setSpeechRate(s1.getInt("Speech",10)/10);
    }
	
	
	void setnew()
	{r = randomGenerator.nextInt(a.size());		
	  i=0; 
		   
		if(r>0)
	  words=a.get(r).split(" ");
		
	}
	
	void setpara()
	{ String w ="";
	   
	   for(l=0;l<words.length;l++)
		   if(l==i)
			   w = w + " " + "<font color='#EE0000'>" + words[l] +  "</font>";
		   else
			   w = w + " " + words[l];
	   
	   t.setText(Html.fromHtml(w));
	   
	   if( i<words.length)
			i++;
	   else {setnew();setpara(); 
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


    private void confirmTTSData()  {
    	Intent intent = new Intent(Engine.ACTION_CHECK_TTS_DATA);
    	startActivityForResult(intent, TTS_DATA_CHECK);
    }
    
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode== RESULT_SPEECH)
    		if (resultCode == RESULT_OK && null != data) {

				ArrayList<String> text = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

				if(text.get(0).equals(words[i-1]))
					Toast.makeText(this,"Correct",Toast.LENGTH_SHORT).show();
			
				else
					Toast.makeText(this,"Not Correct",Toast.LENGTH_SHORT).show();
				
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
    
    private void read() {
    	if(isTTSInitialized)
    	 {prepare();	
    	  tts.speak(words[i-1], TextToSpeech.QUEUE_ADD, null);
    	 }
    	}
    

		
	void spell()
	{  if(isTTSInitialized){
		prepare();
		for(int j=0;j<words[i-1].length();j++)
		tts.speak(Character.toString(words[i-1].charAt(j)), TextToSpeech.QUEUE_ADD, null);
	    }	   	
	}
	

	void check()
	{Intent intent = new Intent(
			RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

	intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

	try {
		startActivityForResult(intent, RESULT_SPEECH);
		
	} catch (ActivityNotFoundException a) {
		Toast t = Toast.makeText(getApplicationContext(),
				"Oops! Your device doesn't support Speech to Text",
				Toast.LENGTH_SHORT);
		t.show();
	}
}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
       	int id=v.getId();
		switch(id)
       	{case R.id.button1:
       		spell();	
       	 break;
       	case R.id.button2:
       		read();
       	break;
       	case R.id.button3:
       		setpara();   
       	break;
       	case R.id.btnSpeak: check();        
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