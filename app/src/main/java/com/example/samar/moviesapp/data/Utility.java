package com.example.samar.moviesapp.data;

import android.content.Context;
import android.database.Cursor;

import com.example.samar.moviesapp.GridItem;

import java.util.ArrayList;

/**
 * Created by Samar on 19/04/2018.
 */

public class Utility {
    public static GridItem GetMovie(Context context, String id) {
        GridItem Item=new GridItem();
        Cursor cursor = context.getContentResolver().query(
                Contract.MovieEntry.CONTENT_URI,
                null,   // projection
                Contract.MovieEntry.KEY_ID + " = ?", // selection
                new String[] { id },   // selectionArgs
                null    // sort order
        );
        int numRows = cursor.getCount();

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
    public static boolean CheckIsDataAlreadyInDBorNot(Context context,String id) {
        Cursor cursor = context.getContentResolver().query(
                Contract.MovieEntry.CONTENT_URI,
                null,   // projection
                Contract.MovieEntry.KEY_ID + " = ?", // selection
                new String[] { id },   // selectionArgs
                null    // sort order
        );
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
    public static String buildImageUrl(int width, String fileName) {
        return "http://image.tmdb.org/t/p/w" + Integer.toString(width) + fileName;
    }
    public static ArrayList<GridItem> GetFavMovies(Context context) {
        ArrayList<GridItem> MovieList = new ArrayList<GridItem>();
        GridItem Item=new GridItem();
        Cursor cursor = context.getContentResolver().query(
                Contract.MovieEntry.CONTENT_URI,
                null,   // projection
                Contract.MovieEntry.KEY_Favourite + " = ?", // selection
                new String[] { "1" },   // selectionArgs
                null    // sort order
        );
        int numRows = cursor.getCount();
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

        return MovieList;
    }
}
