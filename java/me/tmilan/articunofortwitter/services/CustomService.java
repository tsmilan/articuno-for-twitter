package me.tmilan.articunofortwitter.services;

import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/* compiled from: MyTwitterApiClient */
public interface CustomService {
    @GET("/1.1/users/show.json")
    Call<User> show(@Query("screen_name") String str);
    @GET("/1.1/statuses/user_timeline.json")
    Call<Tweet> user_timeline(@Query("screen_name") String str);
}
