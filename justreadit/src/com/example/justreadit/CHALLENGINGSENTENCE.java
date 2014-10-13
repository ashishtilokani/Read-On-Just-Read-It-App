

package com.example.justreadit;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class CHALLENGINGSENTENCE extends Fragment implements OnClickListener,OnItemClickListener{

	EditText wordEditText,data;
	
	
	ImageButton wordAdd;
	DataBaseHandler db;
	ListView wordList;
	String c;
	List<String> values;
	AlertDialog.Builder alert;
	AlertDialog.Builder alert1;
	LinearLayout linear;
	String a[]={"Edit","Delete"};
	ListView l;
	String selectedFromList;
	View rootView1;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		alert=new AlertDialog.Builder(getActivity());
		alert1=new AlertDialog.Builder(getActivity());
		linear=new LinearLayout(getActivity());
		
		ArrayAdapter<String> file= new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, a);
		
		l=new ListView(getActivity());
		
		
		l.setAdapter(file);
		
		linear.addView(l);
		linear.setOrientation(1);
		//alert.setView(linear);
		
		l.setId(1);
		l.setOnItemClickListener(this);
		
		
		View rootView = inflater.inflate(R.layout.activity_word, container, false);
		
		wordEditText=(EditText)rootView.findViewById(R.id.wordEditText);
		wordEditText.setHint("Add CHALLENGING sentence");
		wordAdd=(ImageButton)rootView.findViewById(R.id.wordAdd);
		wordAdd.setOnClickListener(this);
		wordList=(ListView)rootView.findViewById(R.id.wordlist);
		
		 rootView1 = inflater.inflate(R.layout.edit,container,false);
	//	 ((LinearLayout)rootView1.getParent()).removeView(rootView1);
  		data=(EditText)rootView1.findViewById(R.id.wordEditText);
		 db=new DataBaseHandler(getActivity());

		values = db.getContent(Constants.CHALLENGING_SENTENCES_TABLE);
		ArrayAdapter<String> files = new ArrayAdapter<String>(getActivity(), 
	            android.R.layout.simple_list_item_1, 
	            values);
	            
	    wordList.setAdapter(files);
	    
	    wordList.setOnItemClickListener(this);
		return rootView;
	}
	
	
	
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.wordAdd)
		{	
			
		String [] a=wordEditText.getText().toString().split(" ");
		
		if(a.length>1)
		{
		    db.insertContent(wordEditText.getText().toString(),Constants.CHALLENGING_SENTENCES_TABLE);
			
		    values = db.getContent(Constants.CHALLENGING_SENTENCES_TABLE);
			
         ArrayAdapter<String> files = new ArrayAdapter<String>(getActivity(), 
           android.R.layout.simple_list_item_1, 
           values);
        
        wordList.setAdapter(files);
	    }
		
		else Toast.makeText(getActivity(), "Enter atleast two words", Toast.LENGTH_SHORT).show();
		
		
		}
	
	}



	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int myItemInt, long arg3) {
		if(v.getId()==1)
		{
			
			
		}
		
		else 
		{  selectedFromList =(String) (wordList.getItemAtPosition(myItemInt));
		 alert.setTitle(selectedFromList); 
		 alert.setItems(a, new DialogInterface.OnClickListener() {
			 public void onClick(DialogInterface dialog, int item) {
			      // Do something with the selection
			      if(item==0)
			      {
			    	  
			    	  LayoutInflater inflater = getActivity().getLayoutInflater();
			    	  rootView1 = inflater.inflate(R.layout.edit,null);
			    	//  alert1.setView(rootView1);
			    	//alert1.setView(selectedFromList);
			    	  data=(EditText)rootView1.findViewById(R.id.wordEditText);
			    	  data.setText(selectedFromList);
				  		alert1.setView(rootView1);
			    	  alert1.setTitle("Edit");
			    	
			    	  alert1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			              public void onClick(DialogInterface dialog, int id) {
			                  // User clicked OK button
			            	//  String value = user.getText().toString();
			            	  	
			          			
			          		String [] a=data.getText().toString().split(" ");
			          		
			          		if(a.length>1)
			          		{
			          			db.updateContent(data.getText().toString(),selectedFromList,Constants.CHALLENGING_SENTENCES_TABLE);
			          		    //db.insertContent(wordEditText.getText().toString(),Constants.CHALLENGING_SENTENCES_TABLE);
			          			
			          		    values = db.getContent(Constants.CHALLENGING_SENTENCES_TABLE);
			          			
			                   ArrayAdapter<String> files = new ArrayAdapter<String>(getActivity(), 
			                     android.R.layout.simple_list_item_1, 
			                     values);
			                  
			                  wordList.setAdapter(files);
			          	    }
			          		
			          		else Toast.makeText(getActivity(), "Enter atleast two words", Toast.LENGTH_SHORT).show();
			          		
			          		
			          		
			              }
			          });
			   alert1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			              public void onClick(DialogInterface dialog, int id) {
			                  // User cancelled the dialog
			            	 
			              }
			          });
			   AlertDialog builder1 = alert1.create();
		 		 builder1.show();
			      }
			      if(item==1)
			    	  {
			    	  db.delete(selectedFromList, Constants.CHALLENGING_SENTENCES_TABLE);
			    	  values = db.getContent(Constants.CHALLENGING_SENTENCES_TABLE);
			    	  ArrayAdapter<String> files = new ArrayAdapter<String>(getActivity(), 
		                      android.R.layout.simple_list_item_1, 
		                      values);
		                      
		                     wordList.setAdapter(files);
			    	  }
			 }
			 });
		 AlertDialog builder = alert.create();
		 builder.show();
		
		}
		
	}
}