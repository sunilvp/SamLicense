package com.example.suvp.resttest.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suvp.resttest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by suvp on 5/20/2016.
 */
public class LicensePointsDetailActivity  extends AppCompatActivity {

    private final Context context_ = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.license_points_details);

        try {
            JSONObject jsonObj = new JSONObject(getIntent().getStringExtra(LicensePointsListActivity.SERIALIZED_PRODUCT));
            TextView lproducts = (TextView)findViewById(R.id.editTextLicensedProducts);
            TextView lPoints = (TextView)findViewById(R.id.editTextLicensedPoints);
            TextView lConsumed = (TextView)findViewById(R.id.editTextLicenseConsumed);
            TextView lRemaining = (TextView)findViewById(R.id.editTextLicenseRemaining);

            lproducts.setText((String)jsonObj.get("licensePointsDisplayName"));
            lPoints.setText(jsonObj.get("maximumCount").toString());
            lConsumed.setText(jsonObj.get("currentCount").toString());
            lRemaining.setText(jsonObj.get("remainingCount").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}