package me.tmilan.articunofortwitter.services;

import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;

import me.tmilan.articunofortwitter.services.CustomService;

class MyTwitterApiClientsd extends TwitterApiClient {
    public MyTwitterApiClientsd(TwitterSession session) {
        super(session);
    }

    /**
     * Provide CustomService with defined endpoints
     */
    public CustomService getCustomService() {
        return getService(CustomService.class);
    }
}