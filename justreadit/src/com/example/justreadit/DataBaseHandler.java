package com.example.justreadit;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHandler extends SQLiteOpenHelper {

	
	public DataBaseHandler(Context context) 
	{	super(context,Constants.DATABASE_NAME,null,Constants.DATABASE_VERSION );
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(Constants.CREATE_EASY_WORDS);
		db.execSQL(Constants.CREATE_EASY_SENTENCES);
		db.execSQL(Constants.CREATE_INTERMEDIATE_WORDS);
		db.execSQL(Constants.CREATE_INTERMEDIATE_SENTENCES);
		db.execSQL(Constants.CREATE_CHALLENGING_WORDS);
		db.execSQL(Constants.CREATE_CHALLENGING_SENTENCES);
		
		}
	
	public void insertContent(String word,String table)
	{	SQLiteDatabase db=this.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put(Constants.CONTENT,word);
		db.insert(table, null, values);
		db.close();
	}
	
	
	public void updateContent(String newword,String oldword,String table)
	{   SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
	    values.put(Constants.CONTENT,newword);
        db.update(table,values, Constants.CONTENT + "=" + "'" + oldword +"'" ,null);
	}
	public void delete(String deleteword,String table)
	{
		SQLiteDatabase db=this.getWritableDatabase();
		db.delete(table, Constants.CONTENT + "=" + "'" + deleteword +"'" , null);
		
	}
	
	public List<String> getContent(String table) {
		SQLiteDatabase db=this.getReadableDatabase();
		String select="SELECT " + Constants.CONTENT + " FROM " + table;
		Cursor cursor=db.rawQuery(select, null);
		List<String> data = new ArrayList<String>();
		
		if(cursor.moveToFirst())
		 {
		  do{
			data.add(cursor.getString(cursor.getColumnIndex(Constants.CONTENT)));
		    }
		  while(cursor.moveToNext());
		
	     }
		db.close();
		return data;
		}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub	
	/*	db.execSQL("DROP TABLE IF EXISTS" + Constants.);
		db.execSQL("DROP TABLE IF EXISTS" + Constants.SENTENCESTABLE);
	    onCreate(db);
	*/
	}
}
