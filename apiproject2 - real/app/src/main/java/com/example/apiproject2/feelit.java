package com.example.apiproject2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class feelit extends AppCompatActivity {
    RequestQueue queue;
     Button button;
     Button b1;
    String url="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feelit);
        Intent intent = getIntent();
        String text = intent.getStringExtra(Intent.EXTRA_TEXT);
        double mm=intent.getDoubleExtra("add",0.00);
        String newmag=formatMagnitude(mm);
        int a=intent.getIntExtra("aa",0);
        TextView textView=findViewById(R.id.title);
        TextView magg=findViewById(R.id.perceived_magnitude);
        TextView np=findViewById(R.id.number_of_people);
        textView.setText(text);
        button=findViewById(R.id.button);
        b1=findViewById(R.id.button2);
        String uurl=intent.getStringExtra("aaa");
        magg.setText(newmag );
        queue= Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray f = response.getJSONArray("features");
                            int i = a;
                            JSONObject pos = f.getJSONObject(i);
                            JSONObject prop = pos.getJSONObject("properties");
                            int cc = prop.getInt("felt");
                            String ppl=Integer.toString(cc);
                                np.setText(ppl + " people felt");
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );
        queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri earthquakeUri = Uri.parse(uurl);

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri earthquakeUri = Uri.parse("https://earthquake.usgs.gov/earthquakes/eventpage/tellus");

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
    }

    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }


}