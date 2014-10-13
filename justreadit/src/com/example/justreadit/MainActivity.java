package com.example.justreadit;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
//import android.view.Menu;

public class MainActivity extends Activity implements OnClickListener{

    ImageButton b;
 DataBaseHandler db;    	
 SharedPreferences s;
 Editor e;
	
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
	
	String easySentence[] = {"An apple a day keeps doctor away","Some students like to study in the mornings","Early to rise and early to bed makes a male healthy and wealthy and dead","Expect nothing. Live frugally on surprise","Children are all foreigners","Atheism is a non-prophet organization","The dog howled and barked","A student who is hungry would never pass up a hamburger","We visited the museum before it closed","Because he was so small Stuart was often hard to find around the house"};
	String interSentence[]={"Even in the middle of the summer Jan and her dog liked to stay outdoors all afternoon long","Though the music was quite long, it was absorbing","Writing evolved when picture symbols changed to letters","Consumers buy fewer goods when prices rise"};

 String chalSentence[]={"Barbara Kingsolver and Amy Tan are two of my sister’s favorite novelists","The captain’s brusque manner offended the passengers","The column buttresses the roof above the statue","Fred’s buddies cajoled him into attending the bachelor party","His credulity made him an easy target for con men","Lord Emsworth adjusted his pince-nez and sought inspiration from the wall-paper","The strong winds buffeted the ships threatening to capsize them","His mother asked him to burnish the silverware before setting the table","The conflagration consumed the entire building","His cupidity made him enter the abandoned gold mine despite the obvious dangers"};
			

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    s=PreferenceManager.getDefaultSharedPreferences(this);
	    e=s.edit();
	    super.onCreate(savedInstanceState);
		db=new DataBaseHandler(this);
		setContentView(R.layout.activity_main);
		b=(ImageButton)findViewById(R.id.imageButton1);
	    b.setOnClickListener(this);

	    
if(s.getInt("db", 0)==0)
			
	    {
	    int j=0;
		while(j<easy.length)
	    {db.insertContent(easy[j++], Constants.EASY_WORDS_TABLE);}
		
		j=0;
		while(j<inter.length)
	    {db.insertContent(inter[j++], Constants.INTERMEDIATE_WORDS_TABLE);}
		
		j=0;
		while(j<chal.length)
	    {db.insertContent(chal[j++], Constants.CHALLENGING_WORDS_TABLE);}
	    
		j=0;
		while(j<easySentence.length)
	    {db.insertContent(easySentence[j++], Constants.EASY_SENTENCES_TABLE);}
		
		j=0;
		while(j<interSentence.length)
	    {db.insertContent(interSentence[j++], Constants.INTERMEDIATE_SENTENCES_TABLE);}
		
		j=0;
		while(j<chalSentence.length)
	    {db.insertContent(chalSentence[j++], Constants.CHALLENGING_SENTENCES_TABLE);}
	   
	    e.putInt("db",1);
	    
	    e.commit();
	    }
	    
	    
	}

	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent i= new Intent(this,MainMenu.class);
		
	    startActivity(i);
	    
		overridePendingTransition(R.layout.fade_in, R.layout.fade_out);
	    
		this.finish();	
	}
}