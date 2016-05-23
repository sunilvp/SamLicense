package com.example.suvp.resttest.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.suvp.resttest.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by suvp on 5/20/2016.
 */
public class LicensePointsListAdapter  extends ArrayAdapter<JSONObject> {

    private final Context context;
    private final List<JSONObject> values;

    public LicensePointsListAdapter(Context aInContext, List<JSONObject> aInValues){
        super(aInContext, -1, aInValues);
        context = aInContext;
        values = aInValues;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View rowView = inflater.inflate(R.layout.item_license_points_list, parent, false);
        TextView pointsRemaining = (TextView)(rowView.findViewById(R.id.editTextPointsRemaining));
        TextView itemName = (TextView)(rowView.findViewById(R.id.editTextItemName));
        JSONObject lJsonObject = values.get(position);

        try {
            itemName.setText((String)lJsonObject.get("licensePointsDisplayName"));
            pointsRemaining.setText(lJsonObject.get("remainingCount").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rowView;
    }
}


