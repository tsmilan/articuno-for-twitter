package me.tmilan.articunofortwitter.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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


public class CustomTweetTimelineAdapter extends RecyclerView.Adapter<CustomTweetTimelineAdapter.CustomTweetTimelineViewHolder> {
    Context context;
    private Result<List<Tweet>> tweetList;

    public CustomTweetTimelineAdapter(Context context, Result<List<Tweet>> tweetList) {
        this.context = context;
        this.tweetList = tweetList;
    }

    @Override
    public CustomTweetTimelineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tweet_row_layout, parent, false);
        CustomTweetTimelineViewHolder viewHolder = new CustomTweetTimelineViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomTweetTimelineViewHolder holder, int position) {
        holder.bindTweet(this.tweetList.data.get(position));
    }

    @Override
    public int getItemCount() {
        return ((List) this.tweetList.data).size();
    }

    public class CustomTweetTimelineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView row_name;
        public TextView row_screen_name;
        public TextView row_tweet_date;
        public TextView row_post;
        public TextView row_retweeted_by;
        public ImageView row_profile_photo;
        public ImageView row_entity_photo;



        public CustomTweetTimelineViewHolder(View itemView) {
            super(itemView);

            this.row_name = (TextView) itemView.findViewById(R.id.name);
            this.row_screen_name = (TextView) itemView.findViewById(R.id.screen_name);
            this.row_tweet_date = (TextView) itemView.findViewById(R.id.tweetDate);
            this.row_post = (TextView) itemView.findViewById(R.id.post);
            this.row_retweeted_by = (TextView)itemView.findViewById(R.id.retweetedBy);
            this.row_profile_photo = (ImageView) itemView.findViewById(R.id.profile_photo);
            this.row_entity_photo = (ImageView) itemView.findViewById(R.id.entity);

            itemView.setOnClickListener(this);
        }

        public void bindTweet(Tweet tweet) {

            boolean retweeted = tweet.retweeted;

            String name = (retweeted) ? tweet.retweetedStatus.user.name : tweet.user.name;
            String screenName = (retweeted) ? tweet.retweetedStatus.user.screenName : tweet.user.screenName;
            String date =  tweet.createdAt;
            String text = (retweeted) ? tweet.retweetedStatus.text : tweet.text;
            int mediaSize = (retweeted) ? tweet.retweetedStatus.entities.media.size() : tweet.entities.media.size();
            String profilePhoto = (retweeted) ? tweet.retweetedStatus.user.profileImageUrl : tweet.user.profileImageUrl;
            String retweetedBy = "Retweeted by " + tweet.user.name;


            row_name.setText(name);
            row_screen_name.setText("@" + screenName);
            row_tweet_date.setText(date.substring(4, 10));
            row_post.setText(text);

            if (retweeted) {
                row_retweeted_by.setText(retweetedBy);
            }
            else {
                row_retweeted_by.setHeight(0);
                row_retweeted_by.setVisibility(View.GONE);
            }

            if (mediaSize > 0) {
                String imageUrl = (retweeted) ? tweet.retweetedStatus.entities.media.get(0).mediaUrlHttps : tweet.entities.media.get(0).mediaUrlHttps;
                new DownloadImageTask(row_entity_photo).execute(imageUrl);
            } else {
                row_entity_photo.setImageBitmap(null);
            }

            new DownloadImageTask(row_profile_photo).execute(profilePhoto);
        }

        @Override
        public void onClick(View view) {
            // Do something
        }
    }


}
