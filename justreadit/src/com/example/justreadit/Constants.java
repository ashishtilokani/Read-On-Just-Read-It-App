package com.example.justreadit;

public class Constants {
	public static final String CONTENT = "content";
	
	
	public static final String DATABASE_NAME="JustReadIt.db";
	public static final int DATABASE_VERSION=1;
	
	public static final String EASY_WORDS_TABLE="EasyWordsTable";
	public static final String INTERMEDIATE_WORDS_TABLE="IntermediateWordsTable";
	public static final String CHALLENGING_WORDS_TABLE="ChallengingWordsTable";
	
	
	public static final String EASY_SENTENCES_TABLE="EasySentencesTable";
	public static final String INTERMEDIATE_SENTENCES_TABLE="IntermediateSentencesTable";
	public static final String CHALLENGING_SENTENCES_TABLE="ChallengingSentencesTable";
	
	
	public static final String CREATE_EASY_WORDS="CREATE TABLE IF NOT EXISTS "
	       + EASY_WORDS_TABLE + " ( "
	       + CONTENT + " TEXT );";
	
	public static final String CREATE_INTERMEDIATE_WORDS="CREATE TABLE IF NOT EXISTS "
		       + INTERMEDIATE_WORDS_TABLE + " ( "
		       + CONTENT + " TEXT );";
	
	public static final String CREATE_CHALLENGING_WORDS="CREATE TABLE IF NOT EXISTS "
		       + CHALLENGING_WORDS_TABLE +  " ( "
		       + CONTENT + " TEXT );";
	
	public static final String CREATE_EASY_SENTENCES="CREATE TABLE IF NOT EXISTS "
		       + EASY_SENTENCES_TABLE + " ( "
		       + CONTENT + " TEXT );";
		
		public static final String CREATE_INTERMEDIATE_SENTENCES="CREATE TABLE IF NOT EXISTS "
			       + INTERMEDIATE_SENTENCES_TABLE + " ( "
			       + CONTENT + " TEXT );";
		
		public static final String CREATE_CHALLENGING_SENTENCES="CREATE TABLE IF NOT EXISTS "
			       + CHALLENGING_SENTENCES_TABLE +  " ( "
			       + CONTENT + " TEXT );";
	
}
