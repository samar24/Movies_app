package com.example.samar.moviesapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Samar on 12/30/2015.
 */

public class TrailerViewsAdapter extends ArrayAdapter<Trailer_Review_Item> {
    private Context mContext;
    private int layoutResourceId;
    private ArrayList<Trailer_Review_Item> mYMovieData = new ArrayList<Trailer_Review_Item>();
    Trailer_Review_Item item;
    public TrailerViewsAdapter(Context mContext, int layoutResourceId, ArrayList<Trailer_Review_Item> mYMovieData) {
        super(mContext, layoutResourceId, mYMovieData);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.mYMovieData = mYMovieData;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        ViewHolder  holder = new ViewHolder();
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        if(mYMovieData.get(position).getType().equals("0")&&!mYMovieData.get(position).get_title_Videos().equals("")||mYMovieData.get(position).get_title_Videos().equals(null)) {

            if (row == null) {

                row = inflater.inflate(R.layout.trailerview, parent, false);

                holder.titleTextView = (TextView) row.findViewById(R.id.textView);
                holder.YoutubeImage = (ImageView) row.findViewById(R.id.YoutubeImage);

                row.setTag(holder);
            } else {

                holder = (ViewHolder) row.getTag();
            }

            item = mYMovieData.get(position);
            holder.titleTextView.setText(item.get_title_Videos());
            String yt_thumbnail_url = "http://img.youtube.com/vi/" + item.GetVideoID() + "/0.jpg";
            Picasso.with(mContext).load(yt_thumbnail_url).into(holder.YoutubeImage);
            holder.titleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,"Clicked", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + item.GetVideoID()));
                    mContext.startActivity(intent);
                }
            });
        }
        else if(mYMovieData.get(position).getType().equals("1")&&!mYMovieData.get(position).GetAuthor().equals("")||mYMovieData.get(position).GetAuthor().equals(null)) {


        row = inflater.inflate(R.layout.review_item_view, parent, false);

        holder.titleTextView_author = (TextView) row.findViewById(R.id.AuthorName);
        holder.content = (TextView) row.findViewById(R.id.ReviewContent);

        row.setTag(holder);


    Trailer_Review_Item item = mYMovieData.get(position);
    holder.titleTextView_author.setText(item.GetAuthor());
    holder.content.setText(item.Getcontent());
}
        return row;
    }

    static class ViewHolder { // for trailers and reviews
        TextView titleTextView_author;
        ImageView YoutubeImage;
        TextView titleTextView;
        TextView content;
    }

}
