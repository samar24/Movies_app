package com.example.samar.moviesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;

/**
 * Created by Samar on 1/27/2016.
 */
public class DatabaseHandler1 extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Movies";


    private static final String TABLE_Movies = "MoviesDetails";


    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "Title";
    private static final String KEY_Img = "Image";
    private static final String KEY_Favourite = "Fav";
    private static final String KEY_Overview = "Overview";
    public DatabaseHandler1(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_Movies + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_Img + " TEXT," + KEY_Favourite+" TEXT,"+ KEY_Overview+" TEXT"+")";

        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Movies);

        // Create tables again
        onCreate(db);
    }
    public void addMovie(GridItem Movie) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, Movie.Get_movie_id());
        values.put(KEY_NAME, Movie.getTitle());
        values.put(KEY_Img, Movie.getImage());
        values.put(KEY_Favourite, Movie.GetFavourte());
        values.put(KEY_Overview, Movie.Get_Overview());
        // Inserting Row
        db.insert(TABLE_Movies, null, values);
        db.close(); // Closing database connection
    }
    public ArrayList<GridItem> GetFavMovies() {
        ArrayList<GridItem> MovieList = new ArrayList<GridItem>();
        //String quotes_ids="";
        String selectQuery = "SELECT* "+" FROM " + TABLE_Movies +" WHERE "+KEY_Favourite+"=1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     if(!selectQuery.equals("")||!selectQuery.equals(null)) {

         if (cursor.moveToFirst()) {
             do {
                 GridItem NewItemFav = new GridItem();
                 //  quotes_ids+=cursor.getString(0)+",";
                 String Id = cursor.getString(0);
                 String Title = cursor.getString(1);
                 String Image_link = cursor.getString(2);
                 String Fav=cursor.getString(3);
                 String OV=cursor.getString(4);
                 NewItemFav.SetMovie_id(Id);
                 NewItemFav.setTitle(Title);

                 NewItemFav.setImage(Image_link);
                 NewItemFav.SetFav(Fav);
                 NewItemFav.setOverview(OV);
                 MovieList.add(NewItemFav);

             } while (cursor.moveToNext());

         }
     }
        return MovieList;
    }
    public  void Update(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_Favourite, "0");
        // updating row
        db.update(TABLE_Movies, values, KEY_ID + " IN(" + id + ")",null);

    }
    public  void Update2(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_Favourite, "1");
        // updating row
        db.update(TABLE_Movies, values, KEY_ID + " IN(" + id + ")",null);

    }
     public boolean CheckIsDataAlreadyInDBorNot(String fieldValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "Select * from " + TABLE_Movies + " where " + KEY_ID + " = " + fieldValue;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
    public GridItem GetMovie(String fieldValue) {
        GridItem Item=new GridItem();
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "Select * from " + TABLE_Movies + " where " + KEY_ID + " = " + fieldValue;
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.moveToFirst()) {

                String Id = cursor.getString(0);
                String Title = cursor.getString(1);
                String Image_link = cursor.getString(2);
                String Fav = cursor.getString(3);
            String OverView = cursor.getString(4);
                Item.SetMovie_id(Id);
                Item.setTitle(Title);
                Item.setImage(Image_link);
                Item.SetFav(Fav);
            Item.setOverview(OverView);

        }
        else{
            return null;
        }
        cursor.close();
        return Item;
    }
}
