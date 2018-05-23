package me.tmilan.articunofortwitter.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.models.Tweet;
import java.util.List;

import me.tmilan.articunofortwitter.services.DownloadImageTask;
import me.tmilan.articunofortwitter.R;

public class CustomTweetTimelineRecyclerViewAdapter extends Adapter<CustomTweetTimelineRecyclerViewAdapter.ViewHolder> {
    Context context;
    private Result<List<Tweet>> tweetList;
    ViewHolder viewHolder;


    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        public TextView row_name;
        public TextView row_post;
        public ImageView row_profile_photo;
        public TextView row_screen_name;

        public ViewHolder(View itemView) {
            super(itemView);
            this.row_profile_photo = (ImageView) itemView.findViewById(R.id.profile_photo);
            this.row_name = (TextView) itemView.findViewById(R.id.name);
            this.row_screen_name = (TextView) itemView.findViewById(R.id.screen_name);
            this.row_post = (TextView) itemView.findViewById(R.id.post);
        }
    }

    public CustomTweetTimelineRecyclerViewAdapter(Context context, Result<List<Tweet>> tweetList) {
        this.context = context;
        this.tweetList = tweetList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_row_layout, parent, false));
        return this.viewHolder;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        Tweet tweet = (Tweet) ((List) this.tweetList.data).get(position);
        holder.row_screen_name.setText("@" + tweet.user.screenName);
        holder.row_name.setText(tweet.user.name);
        holder.row_post.setText(tweet.text);
        new DownloadImageTask(holder.row_profile_photo).execute(new String[]{tweet.user.profileImageUrl});
    }

    public int getItemCount() {
        return ((List) this.tweetList.data).size();
    }
}
