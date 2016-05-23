package com.example.suvp.resttest.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.suvp.resttest.R;

public class MainMenuActivity extends AppCompatActivity {

    private final Context context_ = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button lLicenseDetailsButton  = (Button)findViewById(R.id.buttonLicenseDetails);

        lLicenseDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lLicenseDetailsActivity = new Intent(context_, LicenseDetailsActivity.class);
                startActivity(lLicenseDetailsActivity);
            }
        });

        Button lLicensePointsButton  = (Button)findViewById(R.id.buttonLicensePoints);

        lLicensePointsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lLicensePointsActivity = new Intent(context_, LicensePointsListActivity.class);
                startActivity(lLicensePointsActivity);
            }
        });
    }

}
