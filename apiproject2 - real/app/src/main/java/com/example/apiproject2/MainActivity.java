package com.example.apiproject2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity {
    RequestQueue queue;
    recadapter recadapter;
    ArrayList<Earthquake> earthquakes;
    ProgressBar progressBar;
    public static final String LOG_TAG = MainActivity.class.getName();
    private static final String SAMPLE_JSON_RESPONSE = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        call();
        SwipeRefreshLayout layout=findViewById(R.id.sp);

        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                earthquakes.clear();
                call();

                recadapter.notifyDataSetChanged();
                layout.setRefreshing(false);
            }
        });

        // Find a reference to the {@link ListView} in the layout



        // Create a new {@link ArrayAdapter} of earthquakes



        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        // Create a new adapter that takes the list of earthquakes as input

        // Set the adapter on the {@link ListView}interface

  }
  public void call(){

      progressBar=findViewById(R.id.progressbar);
      enableProgressBar();
      queue= Volley.newRequestQueue(this);
      earthquakes=new ArrayList<>();
      // Create a fake list of earthquake locations.
      // Create a fake list of earthquakes.
      RecyclerView recyclerView=findViewById(R.id.recyView);


      JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
              Request.Method.GET,
              SAMPLE_JSON_RESPONSE,
              null,
              new Response.Listener<JSONObject>() {
                  @Override
                  public void onResponse(JSONObject response) {
                      try {
                          JSONArray f = response.getJSONArray("features");
                          for (int i = 0; i < f.length(); i++) {
                              JSONObject pos =f.getJSONObject(i);
                              JSONObject prop= pos.getJSONObject("properties");
                              double mag = prop.getDouble("mag");
                              String place =prop.getString("place");
                              Long date = prop.getLong("time");
                              String url =prop.getString("url");

                              Earthquake earthquake = new Earthquake(mag, place, date, url);
                              earthquakes.add(earthquake);
                              recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                              recadapter=new recadapter(getApplicationContext(),earthquakes);
                              recyclerView.setAdapter(recadapter);

                          }
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
        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                hideProgressDialog();
            }
        });
  }

    private void hideProgressDialog() {
        progressBar=findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
    }

    private void enableProgressBar() {
        progressBar=findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
    }

}