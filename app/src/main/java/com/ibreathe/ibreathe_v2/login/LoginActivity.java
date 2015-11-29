package com.ibreathe.ibreathe_v2.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.ibreathe.ibreathe_v2.R;
import com.ibreathe.ibreathe_v2.gcm.GCMClientManager;
import com.ibreathe.ibreathe_v2.global.GlobalVariable;
import com.ibreathe.ibreathe_v2.global.InfoJsonSend;
import com.ibreathe.ibreathe_v2.global.SessionManager;
import com.ibreathe.ibreathe_v2.home.Home;
import com.ibreathe.ibreathe_v2.models.UserType;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.digits.sdk.android.Digits;

public class LoginActivity extends Activity {
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    SharedPreferences sharedPref;
    public GCMClientManager pushClientManager;
    String PROJECT_NUMBER = "940547999207";
    GlobalVariable gv = GlobalVariable.getInstance();
    private final String serverIP = gv.getLoc_IP();

    // Views for UI references.
    private AutoCompleteTextView mUserNameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    static final int SUCCESS = 1;
    CallbackManager callbackManager;
    SessionManager sessionManager;
    String IMEINumber;
    public UserType user = new UserType();
    private TwitterLoginButton loginButtonTwitter;
    AccessToken mToken;
    JSONObject user_obj;
    ProgressDialog mProgressRegister;
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String FIELDS = "fields";

    private static final String REQUEST_FIELDS =
            TextUtils.join(",", new String[]{NAME, EMAIL});

    Intent mainActivity = null;
    InfoJsonSend jsonRequest;
    private UserLoginRegisterTask mRegisterTask = null;

    //Request Code.
    static final int REGISTER_USER = 1;

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "8GWTzscCniOPr3Q5yYas8Xhsj";
    private static final String TWITTER_SECRET = "s3dkzwf0fdKRPmUK1fKoFryzHpy583AIWq8vok1t2dXeMjSQse";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.login_screen);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits());

        mainActivity = new Intent(LoginActivity.this, Home.class);
        callbackManager = CallbackManager.Factory.create();

        pushClientManager = new GCMClientManager(this, PROJECT_NUMBER);
        pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {
//                Toast.makeText(LoginActivity.this, registrationId,
//                        Toast.LENGTH_SHORT).show();
                user.gcm_reg_id = registrationId;
            }

            @Override
            public void onFailure(String ex) {
                super.onFailure(ex);
                Toast.makeText(LoginActivity.this, ex,
                        Toast.LENGTH_SHORT).show();
            }
        });

        /* Facebook login then register*/
        final LoginButton loginButton = (LoginButton) findViewById(R.id.fb_signin_main);
        loginButton.setReadPermissions("user_friends");
        loginButton.setReadPermissions("public_profile");
        loginButton.setReadPermissions("email");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                if (loginResult.getAccessToken() != null) {

                    sessionManager = new SessionManager(getApplicationContext());
                    AccessToken.setCurrentAccessToken(loginResult.getAccessToken());
                    mToken = AccessToken.getCurrentAccessToken();

                    if(sessionManager.isRegistered()){
                        startActivity(mainActivity);
                        finish();
                    } else {
                        mProgressRegister = new ProgressDialog(LoginActivity.this);
                        mProgressRegister.setCancelable(false);
                        mProgressRegister.setMessage("Getting user information..");
                        mProgressRegister.show();

                        if (mToken != null) {
                            GraphRequest request = GraphRequest.newMeRequest(
                                    mToken, new GraphRequest.GraphJSONObjectCallback() {
                                        @Override
                                        public void onCompleted(JSONObject me, GraphResponse response) {
                                            user_obj = me;
                                            try {
                                                sessionManager.put_fb_info(user_obj.getString("name"),
                                                        user_obj.getString("email"));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            if (mProgressRegister.isShowing()) {
                                                mProgressRegister.dismiss();
                                                mRegisterTask = new UserLoginRegisterTask();
                                                mRegisterTask.execute();

                                            }
                                        }
                                    });
                            Bundle parameters = new Bundle();
                            parameters.putString(FIELDS, REQUEST_FIELDS);
                            request.setParameters(parameters);
                            GraphRequest.executeBatchAsync(request);
                        } else {
                            user = null;
                        }
                    }
                    sessionManager.put_fb_access(loginResult.getAccessToken());
                }
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Cancelled",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(LoginActivity.this, "ERROR " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

        DigitsAuthButton digitsButton = (DigitsAuthButton) findViewById(R.id.ph_signin_main);
        digitsButton.setCallback(new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                // TODO: associate the session userID with your user model
                Toast.makeText(getApplicationContext(), "Authentication successful for "
                        + phoneNumber, Toast.LENGTH_LONG).show();
                startActivity(mainActivity);
                finish();
            }

            @Override
            public void failure(DigitsException exception) {
                Log.d("Digits", "Sign in with Digits failure", exception);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SUCCESS) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                AppEventsLogger.activateApp(this);
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Logs 'install' and 'app activate' App Events.
        //AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Logs 'app deactivate' App Event.
        //AppEventsLogger.deactivateApp(this);
    }



    /**
     * Override the back button to avoid user bypassing the login process.
     */
    @Override
    public void onBackPressed() {
    }

    public class UserLoginRegisterTask extends AsyncTask<Void, Void, String> {

        String result = null;
        UserLoginRegisterTask() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            mProgressRegister = new ProgressDialog(LoginActivity.this);
            mProgressRegister.setMessage("Please wait");
            mProgressRegister.setCancelable(false);
            mProgressRegister.show();

        }

        @Override
        protected String doInBackground(Void... voids) {

            jsonRequest = new InfoJsonSend(LoginActivity.this, serverIP);
            user = jsonRequest.createUser(user);
            sessionManager.put_user_info(user);
            result = "SUCCESS";

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (mProgressRegister.isShowing())
                mProgressRegister.dismiss();

            if (result.equals("SUCCESS")) {
                startActivity(mainActivity);
                finish();
            }

        }
    }

}
