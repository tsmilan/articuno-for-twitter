package me.tmilan.articunofortwitter.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import me.tmilan.articunofortwitter.R;

public class LoginActivity extends AppCompatActivity {
    private static SharedPreferences mSharedPreferences;
    TwitterLoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);
        setContentView(R.layout.activity_login);
        mSharedPreferences = getApplicationContext().getSharedPreferences("PREF_KEY_TWITTER_LOGIN", Context.MODE_PRIVATE);

        if (isLoggedIn()) {
            Intent intent = new Intent(getBaseContext(), HomeActivity.class);
            intent.putExtra("username", mSharedPreferences.getString("username", ""));
            intent.putExtra("userId", mSharedPreferences.getLong("userId", 0));
            startActivity(intent);
            finish();
            return;
        }

        loginButton = (TwitterLoginButton) findViewById(R.id.login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls

                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;

                SharedPreferences.Editor e = LoginActivity.mSharedPreferences.edit();
                e.putString("PREF_KEY_OAUTH_TOKEN", token);
                e.putString("PREF_KEY_OAUTH_SECRET", secret);
                e.putBoolean("PREF_KEY_TWITTER_LOGIN", true);
                e.putString("username", session.getUserName());
                e.putLong("userId", Long.valueOf(session.getId()).longValue());
                e.commit();
                //Calling login method and passing twitter session
                login(session);
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });
    }


    public void login(TwitterSession session)
    {
        String username = session.getUserName();
        Long userId = Long.valueOf(session.getId());

        Bundle bundle = new Bundle();
        bundle.putString("username", username);

        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("userId", userId);
        startActivity(intent);

    }

    private boolean isLoggedIn() {
        return mSharedPreferences.getBoolean("PREF_KEY_TWITTER_LOGIN", false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }
}
