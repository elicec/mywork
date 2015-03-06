package com.elicec.maincode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import com.special.ResideMenuDemo.R;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MySqlLiteDb {
	
	private static final String DATABASE_NAME = "joke.db";
	  private static final String DATABASE_PATH = "/data/data/com.special.ResideMenuDemo/databases";
	  private static final int DATABASE_VERSION = 0;
	  private static final String TAG = "tag_joke";
	  private static String outFileName = "/data/data/com.special.ResideMenuDemo/databases/joke.db";
	  private Context context;
	  private SQLiteDatabase database;

	  public MySqlLiteDb(Context paramContext)
	  {
		  this.context = paramContext;
			
			File file = new File(outFileName);
			if (file.exists()) {
				database = SQLiteDatabase.openOrCreateDatabase(outFileName, null);
				if (database.getVersion() != DATABASE_VERSION) {
					database.close();
					file.delete();	
				}
			}
			try {
				buildDatabase();
			} catch (Exception e) {
				e.printStackTrace();
			}
	  }

	  private void buildDatabase() throws Exception{
			InputStream myInput = context.getResources().openRawResource(R.raw.joke);
			File file = new File(outFileName);
			
			File dir = new File(DATABASE_PATH);
			if (!dir.exists()) {
				if (!dir.mkdir()) {
					throw new Exception("创建文件夹失败");
				}
			}
			
			if (!file.exists()) {			
				try {
					OutputStream myOutput = new FileOutputStream(outFileName);
					
					byte[] buffer = new byte[1024];
			    	int length;
			    	while ((length = myInput.read(buffer))>0){
			    		myOutput.write(buffer, 0, length);
			    	}
			    	myOutput.close();
			    	myInput.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			}
		}

	  private int update(String paramString1, String paramString2)
	  {
	    return 0;
	  }

	  public void close()
	  {
	    this.database.close();
	  }

	  public String getJokeContent()
	  {
	    Cursor localCursor = select();
	    Random localRandom = new Random();
	    boolean bool = true;
	    int i = localCursor.getCount();
	    if (!localCursor.moveToPosition(localRandom.nextInt(i)))
	      bool = localCursor.moveToFirst();
	    Log.i("tag_joke", "the number of joke is" + i);
	    if (bool)
	      return localCursor.getString(localCursor.getColumnIndex("CONTENT"));
	    return "error";
	  }

	  public long insert(String paramString1, String paramString2)
	  {
	    this.database = SQLiteDatabase.openOrCreateDatabase(outFileName, null);
	    ContentValues localContentValues = new ContentValues();
	    localContentValues.put("CONTENT", paramString1);
	    localContentValues.put("STYLES", paramString2);
	    return this.database.insert("myjoke", null, localContentValues);
	  }

	  public Cursor select()
	  {
	    this.database = SQLiteDatabase.openOrCreateDatabase(outFileName, null);
	    return this.database.rawQuery("select * from myjoke", null);
	  }
}
