package com.example.samar.moviesapp;

public class GridItem {
    private String image;
    private String title;
   private String MovieID;
    private String Overview;
    private String Rate;
    private String FavouriteState;
    private String VoteAverage;
    private String ReleaseDate;

    public GridItem() {
        super();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void SetFav(String Favourite) {
        this.FavouriteState = Favourite;
    } // for a fav movies
    public void setOverview(String Overview) {
        this.Overview = Overview;
    }
    public void setVoteAverage(String VoteAverage) {
        this.VoteAverage = VoteAverage;
    }
    public void SetReleaseDate(String ReleaseDate) {
        this.ReleaseDate = ReleaseDate;
    }
    public String Get_Overview() {
        return Overview;
    }
    public String Get_movie_id() {
        return MovieID;
    }
    public String GetFavourte() {
        return FavouriteState;
    }
    public String GetVoteAverage() {
        return VoteAverage;
    }
    public String GetReleaseDate() {
        return ReleaseDate;
    }
    public void SetMovie_id(String Movie_id) {
        this.MovieID = Movie_id;
    }
}
