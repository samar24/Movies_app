package com.example.samar.moviesapp.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Samar on 16/04/2018.
 */

public class Contract {
    private Contract() {}
    public static final String AUTHORITY = "com.example.samar.moviesapp.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final class MovieEntry implements BaseColumns {
        // table name
        public static final String Table_Movie = "movie";
        // columns
        public static final String KEY_ID = "_id";
        public static final String KEY_NAME = "Title";
        public static final String KEY_Img = "Image";
        public static final String KEY_Favourite = "Fav";
        public static final String KEY_Overview = "Overview";

        // create content uri
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(Table_Movie).build();
        // create cursor of base type directory for multiple entries
        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + Table_Movie;
        // create cursor of base type item for single entry
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE +"/" + AUTHORITY + "/" + Table_Movie;

        // for building URIs on insertion
        public static Uri buildMovieURi(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
