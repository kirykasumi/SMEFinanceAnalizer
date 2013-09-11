package com.hksri.smefinanceanalizer;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseAccess extends Activity{
	private final SQLiteDatabase db;
	private final String PACKAGE_NAME = "com.hksri.smefinanceanalizer";

	public DatabaseAccess(){
		String str = "data/data/" + PACKAGE_NAME + "/Sample.db";
		db = SQLiteDatabase.openOrCreateDatabase(str, null);
	}

	public void execSQL(String strSQL){
		db.execSQL(strSQL);
	}

	public Cursor getSQLResultCursor(String strSQL){
		Cursor cr = db.rawQuery(strSQL, null);
		startManagingCursor(cr);
		return cr;
	}

	public String getSQLFirstResultValue(String strResultColumnName, String strSQL){
		Cursor cr = db.rawQuery(strSQL, null);
		startManagingCursor(cr);
		if (cr.moveToNext()){
			return cr.getString(cr.getColumnIndex(strResultColumnName));
		}else{
			return "";
		}
	}

	protected void finalize(){
		db.close();
	}
}
