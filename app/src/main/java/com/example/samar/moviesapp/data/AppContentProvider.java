package com.example.samar.moviesapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * Created by Samar on 16/04/2018.
 */

public class AppContentProvider extends ContentProvider {
    private static final String LOG_TAG = AppContentProvider.class.getSimpleName();
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DatabaseHandler1 mOpenHelper;

    private static final int movie = 100;
    private static final int movie_WITH_ID = 200;

    private static UriMatcher buildUriMatcher(){

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = Contract.AUTHORITY;

        matcher.addURI(authority, Contract.MovieEntry.Table_Movie, movie);
        matcher.addURI(authority, Contract.MovieEntry.Table_Movie + "/#", movie_WITH_ID);

        return matcher;
    }
    @Override
    public boolean onCreate() {
        mOpenHelper =new DatabaseHandler1(getContext());;
        return false;
    }


    @Override
    public Cursor query( Uri uri,  String[] projection,  String selection,  String[] selectionArgs,  String sortOrder) {
        Cursor retCursor;
        switch(sUriMatcher.match(uri)){
            case movie:{
                retCursor = mOpenHelper.getReadableDatabase().query(
                        Contract.MovieEntry.Table_Movie,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }

            case movie_WITH_ID:{
                retCursor = mOpenHelper.getReadableDatabase().query(
                        Contract.MovieEntry.Table_Movie,
                        projection,
                        Contract.MovieEntry._ID + " = ?",
                        new String[] {String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
            default:{
                // By default, we assume a bad URI
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }

    }


    @Override
    public String getType( Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match){
            case movie:{
                return Contract.MovieEntry.CONTENT_DIR_TYPE;
            }
            case movie_WITH_ID:{
                return Contract.MovieEntry.CONTENT_ITEM_TYPE;
            }
            default:{
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Override
    public Uri insert( Uri uri,  ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri returnUri;
        switch (sUriMatcher.match(uri)) {
            case movie: {
                long _id = db.insert(Contract.MovieEntry.Table_Movie, null, values);
                // insert unless it is already contained in the database
                if (_id > 0) {
                    returnUri = Contract.MovieEntry.buildMovieURi(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            }

            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);

            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;

    }

    @Override
    public int delete( Uri uri,  String selection,  String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int numDeleted;
        switch(match){
            case movie:
                numDeleted = db.delete(
                        Contract.MovieEntry.Table_Movie, selection, selectionArgs);
                // reset _ID
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        Contract.MovieEntry.Table_Movie + "'");
                break;
            case movie_WITH_ID:
                numDeleted = db.delete(Contract.MovieEntry.Table_Movie,
                        Contract.MovieEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                // reset _ID
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        Contract.MovieEntry.Table_Movie + "'");

                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return numDeleted;

    }
    @Override
    public int bulkInsert(Uri uri, ContentValues[] values){
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch(match){
            case movie:
                // allows for multiple transactions
                db.beginTransaction();

                // keep track of successful inserts
                int numInserted = 0;
                try{
                    for(ContentValues value : values){
                        if (value == null){
                            throw new IllegalArgumentException("Cannot have null content values");
                        }
                        long _id = -1;
                        try{
                            _id = db.insertOrThrow(Contract.MovieEntry.Table_Movie,
                                    null, value);
                        }catch(SQLiteConstraintException e) {
                            Log.w(LOG_TAG, "Attempting to insert " +
                                    value.getAsString(
                                            Contract.MovieEntry.KEY_NAME)
                                    + " but value is already in database.");
                        }
                        if (_id != -1){
                            numInserted++;
                        }
                    }
                    if(numInserted > 0){
                        // If no errors, declare a successful transaction.
                        // database will not populate if this is not called
                        db.setTransactionSuccessful();
                    }
                } finally {
                    // all transactions occur at once
                    db.endTransaction();
                }
                if (numInserted > 0){
                    // if there was successful insertion, notify the content resolver that there
                    // was a change
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return numInserted;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public int update( Uri uri,  ContentValues values,  String selection,  String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int numUpdated = 0;

        if (values == null){
            throw new IllegalArgumentException("Cannot have null content values");
        }

        switch(sUriMatcher.match(uri)){
            case movie:{
                numUpdated = db.update(Contract.MovieEntry.Table_Movie,
                        values,
                        selection,
                        selectionArgs);
                break;
            }
            case movie_WITH_ID: {
                numUpdated = db.update(Contract.MovieEntry.Table_Movie,
                        values,
                        Contract.MovieEntry._ID + " = ?",
                        new String[] {String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            default:{
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }

        if (numUpdated > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numUpdated;
    }
}
