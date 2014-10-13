package com.example.justreadit;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import android.app.Activity;
import android.content.ActivityNotFoundException;
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

public class Second extends Activity implements OnClickListener{

TextView t;
String word;

private TextToSpeech tts;
private static int TTS_DATA_CHECK = 1;
private boolean isTTSInitialized = false;
Button spell,read,next;
ImageButton check;
int difficulty,r;	
Random randomGenerator;
DataBaseHandler db;
List<String> a;
int prevRandom;

protected static final int RESULT_SPEECH = 1;

	String[] easy={"this","aims","their",
			"time",	"with",	"there",
			"fact",	"word",	"these",
			"kept",	"such",	"world",
			"above",	"many","zoo",
			"age",	"likely",	"record",
			"date",	"main"	,"space",
			"eggs",	"order",	"people",
			"more",	"small",	"sold",
			"idea",	"press",	"large",
			"became",	"because",	"become",
			"enter",	"family",	"great",
			"burn",	"first",	"horse",
			"built",	"longest",	"person",
			"along",	"earl",	"itself",
			"belongs",	"early",	"events",
			"another",	"could",	"ever",
			"clock",	"bring",	"colour",
			"house",	"match",	"speed",
			"little",	"sheep",	"farmer","accept","central","energy","meaning","school","slang","known","total","widely","upheld","view","until","third","reject","affairs","depth","final","speak","money","occur","lacking","law","range","group","habit","admit","dancing","agree","except","quest","degree","joint","poet","edition","affect","current","borrow","effect","bright","force","apply","special","however","impact","interesting","moment","sign","twice","present","usage","attempt","binder","edited","field","blunt","bound","bring","chicken","focus","general","choose","who","stoop","chose","were","distress","choice","where","fainted","motion","why","remain","respect","which","royal","method","whereas","success","salary","south","style","sister","mount","second","search","strong","stated","chinese","chains","sung"};
	
	String inter[]={"social","transform","industry","instead","collapsed","account","adventure","married","science","publish","project","endear","gallery","correction","absolute","connect","enough","modern","hence","grammar","grew","consists","advertising","lodge","length","embrace","defeated","dictionary","exotic","manual","original","proofread","obscure","including","marketing","necessary","discovery","despite","country","elements","disgrace","million","gentleman","labour","attesting","continue","annual","governed","fundamental","experience","depend","employs","currency","armour","apparent","probably","series","standard","lifestyle","essential","height","understood","tenant","weight","harvest","isolated","heavy","coverage","century","catholic","classical","complete","casual","dense","limited","trace","sense","contained","conversion","dismissed","position","concept","measure","phrase","models","natural","required","military","exclude","curved","conclusion","developed","infancy","impressive","knowledge","liberty","revised","since","conditions","critical","aunt","decade","embarking","uncle","uniform","victory","cousin","thus","technical","niece","understanding","solve","nephew","structure","source","current","dispute","elephant","cultural","promoted","policy","objects","relative","separate","wealth","rapidly","whether","deduce","weather","observers","official","references","register","satisfy","produced","widowed","distinct","devoted","denied","describe"};
	
	String chal[]={"complexity","venture","servant","velocity","university","dissatisfied","contradictory","geography","museum","lyrics","machine","megabytes","influence","literature","analytic","comprising","dominated","manufacturing","nuclear","honour","contraction","appropriate","calculation","constitution","minister","language","depot","romance","supplement","sequence","religious","concerning","experimental","alphabetical","combination","elementary","european","mechanics","illustrate","accessible","commander","descended","composition","community","financial","analysis","brought","conjecture","description","execution","perception","resource","portrait","scholar","vacuum","taught","ushering","volume","conversation","applicable","controversial","principle","resembled","varieties","parliament","representative","conversely","identified","stomach","siblings","variant","severe","astronomy","undefined","technique","pioneer","migrated","premier","caught","comprehensive","society","surprising","theory","signature","intensely","candidates","commonplace","interaction","perceived","poverty","independent","pregnant","predictions","significant","streamlined","relevant","physics","satisfied","premature","parish","aisle","hypothesis","gravitation","isle","astrophysics","campaign","daughter","lieutenant","extraordinary","conceived","soldier","characteristics","sociological","sergeant","substantial","psychology","colonel","incomprehensible","inertia","monastery","foreign","dimension","elucidate","approximately","committee","arithmetic","derivative","fervently","pronounce","republican","ineffectual","philosophy","resurgence","phenomena","simultaneous","temporarily","vocabulary","ploughman","yacht","empirical","deficient","charismatic","alias","borough","circumstance","ethos","inheritance","monarchy","consequence","archives","conquered","obsolete","tolerance","electronically","dialect","neutron","initially","archaic","inspiration","contemporary"};
	
	SharedPreferences s1;
	
	int listsize;
//	http://www.beatingdyslexia.com/spelling-word-lists.html
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		s1=PreferenceManager.getDefaultSharedPreferences(this);
		
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);	  		
		db=new DataBaseHandler(this);
		
		difficulty=getIntent().getIntExtra("difficulty",1);
	    switch(difficulty)
	    {case 1: a=db.getContent(Constants.EASY_WORDS_TABLE);
	     listsize=a.size();	
	    break;
	    case 2: a=db.getContent(Constants.INTERMEDIATE_WORDS_TABLE);
	    listsize=a.size();	
	    break;
	    case 3:a=db.getContent(Constants.CHALLENGING_WORDS_TABLE);
	    listsize=a.size();
	    }
	   
		t=(TextView)findViewById(R.id.textView1);
	    spell=(Button)findViewById(R.id.button1);
	    spell.setOnClickListener(this);
	    read=(Button)findViewById(R.id.button2);
	    read.setOnClickListener(this);
	    next=(Button)findViewById(R.id.button3);
	    next.setOnClickListener(this);
	    check=(ImageButton)findViewById(R.id.btnSpeak);
	    check.setOnClickListener(this);
	    confirmTTSData();
	    randomGenerator = new Random();
	    prevRandom=-1;
	    setword();
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
	
	void setword()
	{r = randomGenerator.nextInt(listsize);
	 
	if(r==prevRandom && listsize>1)
	 setword();
     
	 else 
	 {word=a.get(r);
	  t.setText(word);}
	 
	}
	
	private void initializeTTS() {
    	tts = new TextToSpeech(this, new OnInitListener() {
    		public void onInit(int status) {
    			if (status == TextToSpeech.SUCCESS) {
    				isTTSInitialized = true;
    			}
    			else {
    				isTTSInitialized = false;
    			}
    		}
    	});
    }


    private void confirmTTSData()  {
    	Intent intent = new Intent(Engine.ACTION_CHECK_TTS_DATA);
    	startActivityForResult(intent, TTS_DATA_CHECK);
    }
    
    void prepare(){
		tts.setPitch(s1.getInt("Pitch",0)/10);
		tts.setSpeechRate(s1.getInt("Speech",0)/10);	
		if(s1.getString("Accent","US")=="US")
    		tts.setLanguage(Locale.US);
    		else tts.setLanguage(Locale.UK);
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

				if(text.get(0).equals(word))
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
    			initializeTTS();
    		}
    		else {
    			Intent installIntent = new Intent(Engine.ACTION_INSTALL_TTS_DATA);
    			startActivity(installIntent);
    		}
    	}
    }
    
    private void read() {
    	if(isTTSInitialized) {
    	prepare();
    	tts.speak(word, TextToSpeech.QUEUE_ADD, null);
    }}

		
	void spell()
	{	if(isTTSInitialized) {
		prepare();
		for(int i=0;i<word.length();i++)
		tts.speak(Character.toString(word.charAt(i)), TextToSpeech.QUEUE_ADD, null);	
	}}
	
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
       	{case R.id.button1:spell();
       	 break;
       	
       	case R.id.button2:read();
       	break;
       		
       	case R.id.button3:setword();   
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