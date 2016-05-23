package com.example.suvp.resttest.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suvp.resttest.R;

import org.json.JSONArray;
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

/**
 * Created by suvp on 5/19/2016.
 */
public class LicenseDetailsActivity extends AppCompatActivity {

    private final Context context_ = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.license_view);
        new LicenseDetailsTask(this).execute();
    }

    public void onError(){
        Toast.makeText(this, "License Details Not found", Toast.LENGTH_SHORT).show();
    }

    public void onSuccessRetrival(JSONObject aInObject)
    {
        Toast.makeText(this, "License Details found", Toast.LENGTH_SHORT).show();
        try {
            JSONArray lDataArray = (JSONArray)((JSONObject)aInObject.get("response")).get("data");
            JSONObject lDataObject = (JSONObject)lDataArray.get(0);

            TextView lCustomerNameTextview = (TextView)findViewById(R.id.editTextCustomerName);
            TextView lPackage = (TextView)findViewById(R.id.editTextPackage);
            TextView lKeyType = (TextView)findViewById(R.id.editTextKeyType);
            TextView lRelease = (TextView)findViewById(R.id.editTextRelease);

            lCustomerNameTextview.setText((String)lDataObject.get("customerName"));
            lPackage.setText((String)lDataObject.get("packageType"));
            lKeyType.setText((String)lDataObject.get("keyType"));
            lRelease.setText((String)lDataObject.get("version"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

class LicenseDetailsTask extends AsyncTask<Void,Void,JSONObject> {
    public static String URL_AUTHENTICATION = "http://135.250.28.108:8686/MyDemo/rest/api/v1/sam/license";
    private final String LOG_TAG = getClass().getSimpleName();
    private LicenseDetailsActivity _activity;

    public LicenseDetailsTask() {
    }

    public LicenseDetailsTask(LicenseDetailsActivity aInLoginAction) {
        _activity = aInLoginAction;
    }

    protected void onPreExecute() {
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        int resCode = -1;
        InputStream in = null;
        JSONObject aOutResponseString = null;
        SharedPreferences sharedPref = _activity.getSharedPreferences("prefFiles", Context.MODE_PRIVATE);
        String lToken = sharedPref.getString("token", "");
        try {

            URL url = new URL(URL_AUTHENTICATION);
            URLConnection urlConn = url.openConnection();
            HttpURLConnection httpConn = (HttpURLConnection) urlConn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.setRequestProperty("Authorization", "Bearer " + lToken);
            httpConn.setRequestProperty("Content-Type", "application/json");
            httpConn.setRequestProperty("User-Agent", "test");

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
            aOutResponseString = new JSONObject(responseStrBuilder.toString());
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

    protected void onPostExecute(JSONObject result) {
        if(result == null)
        {
            _activity.onError();
        }
        else {
            _activity.onSuccessRetrival(result);
        }
    }
}