package com.example.sqllite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class DBController extends SQLiteOpenHelper {

    public DBController(Context context) {
        super(context, "ProdiTI", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table teman (id integer primary key, nama text, telpon text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists teman");
        onCreate(sqLiteDatabase);
    }

    public void insertData(HashMap<String,String> queryValues){
        SQLiteDatabase basisdata = this.getWritableDatabase();
        ContentValues nilai =  new ContentValues();
        nilai.put("nama",queryValues.get("nama"));
        nilai.put("telpon", queryValues.get("telpon"));
        basisdata.insert("teman",null,nilai);
        basisdata.close();
    }

    public ArrayList<HashMap<String,String>> getAllTeman(){
        ArrayList<HashMap<String,String>> daftarTeman = new ArrayList<HashMap<String, String>>() ;
        String selectQuery = "Select * from teman";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            do {
                HashMap<String,String> map = new HashMap<>();
                map.put("id", cursor.getString(0));
                map.put("nama", cursor.getString(1));
                map.put("telpon", cursor.getString(2));
                daftarTeman.add(map);
            } while (cursor.moveToNext());
        }
        db.close();
        return daftarTeman;
    }
    public void updateData(HashMap<String, String> queryValues) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nama", queryValues.get("nama"));
        values.put("telpon", queryValues.get("telpon"));

        sqLiteDatabase.update("teman", values, "id = ?", new String[]{queryValues.get("id")});
        sqLiteDatabase.close();
    }

    public void deleteData(String id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete("teman", "id = ?",new String[] {id});
        sqLiteDatabase.close();
    }
}