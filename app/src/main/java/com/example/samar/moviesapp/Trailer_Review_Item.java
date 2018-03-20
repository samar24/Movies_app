package com.example.samar.moviesapp;

/**
 * Created by Samar on 12/26/2015.
 */
public class Trailer_Review_Item {
    String Video_Url;
    String title="";
    String Video_title="";
    String Author="";
    String Content="";
    String VideoId="";
    String type="";
    public Trailer_Review_Item(){

    }
    public Trailer_Review_Item(String title){ // constructor of Videos
        this.title = title;

    }
    public String get_title_Videos(){

        return Video_title;
    }
    public String GetAuthor(){

        return Author;
    }
    public String Getcontent(){

        return Content;
    }
    public String GetVideoID(){

        return VideoId;
    }
    public void Set_title_videos(String VideoName){
       this.Video_title=VideoName;

    }
    public void Set_ID_videos(String VideoID){
        this.VideoId=VideoID;

    }
    public void Set_review_author_reviews(String Author){
        this.Author=Author;

    }
    public void Set_review_content_reviews(String Content){
        this.Content=Content;

    }
    public void Set_type(String type){
        this.type=type;
    }
    public String getType(){
        return type;
    }
}
