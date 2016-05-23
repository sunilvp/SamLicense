package com.example.suvp.resttest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.suvp.resttest.Activity.MainMenuActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    private final String LOG_TAG = getClass().getSimpleName();
    private final MainActivity context_ = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new LoginTask(this).execute();
        Button lLoginButton = (Button)findViewById(R.id.buttonLogin);
        lLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoginTask(context_).execute();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSuccessLogin(String aInToken){

        SharedPreferences sharedPref = getSharedPreferences(
                "prefFiles", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("token", aInToken);
        editor.commit();

        Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();

        Intent lMainMenuctivity = new Intent(context_, MainMenuActivity.class);
        startActivity(lMainMenuctivity);
    }

    public void onError()
    {
        Toast.makeText(this, "Login Failure", Toast.LENGTH_SHORT).show();
    }
}

class LoginTask extends AsyncTask<Void,Void,String> {
    public static String URL_AUTHENTICATION = "http://135.250.28.108:8686/auth/api/v1";
    private final String LOG_TAG = getClass().getSimpleName();
    private MainActivity _mainActivity;

    public LoginTask() {
    }

    public LoginTask(MainActivity aInLoginAction) {
        _mainActivity = aInLoginAction;
    }

    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(Void... params) {
        int resCode = -1;
        InputStream in = null;
        String aOutResponseString = null;
        try {

            URL url = new URL(URL_AUTHENTICATION);
            URLConnection urlConn = url.openConnection();
            HttpURLConnection httpConn = (HttpURLConnection) urlConn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("Authorization", "Basic YWRtaW46NTYyMFNhbSE=");
            httpConn.setRequestProperty("Content-Type", "application/json");
            httpConn.setRequestProperty("User-Agent", "test");

            OutputStream writer = httpConn.getOutputStream();

            writer.write("{\"grant_type\" : \"client_credentials\"}".getBytes());
            writer.flush();

            httpConn.connect();

            resCode = httpConn.getResponseCode();

            if (resCode == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }

            BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            JSONObject lJson = new JSONObject(responseStrBuilder.toString());
            Log.i(LOG_TAG, "Response AccesToken :" + lJson.get("access_token"));
            aOutResponseString = (String) lJson.get("access_token");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return aOutResponseString;
    }

    protected void onPostExecute(String result) {
        if(result == null || result.isEmpty())
        {
            _mainActivity.onError();
        }
        else {
            _mainActivity.onSuccessLogin(result);
        }
    }
}


