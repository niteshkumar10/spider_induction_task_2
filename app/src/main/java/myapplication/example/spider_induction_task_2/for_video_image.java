package myapplication.example.spider_induction_task_2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class for_video_image extends AppCompatActivity {

    ImageView imageView,imageview3;
    String date,image_url,searching_url;
    TextView no_content;
    Button another_search,retry;
    ProgressBar progressBar;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_video_image);
        imageview3 = (ImageView)findViewById(R.id.imageView3);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        imageView = (ImageView)findViewById(R.id.imageView);
        no_content = (TextView)findViewById(R.id.no_content);
        another_search = (Button)findViewById(R.id.another_search);
        retry = (Button)findViewById(R.id.retry);
        retry.setVisibility(View.INVISIBLE);
        imageview3.setVisibility(View.INVISIBLE);
        date = getIntent().getStringExtra("date");
        searching_url = "https://api.nasa.gov/planetary/apod?api_key=iKErN6aQvDm48q8bQnRTzZl8mFC5ocXeIvMiLHgx"+"&date="+date;
        progressBar.setMax(100);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
        another_search.setVisibility(View.INVISIBLE);
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork == null){
            progressBar.setVisibility(View.GONE);
            imageview3.setVisibility(View.VISIBLE);
            no_content.setText("ERROR!!"+"\n"+"Internet Connection not available");
            retry.setVisibility(View.VISIBLE);
            another_search.setVisibility(View.VISIBLE);
        }
        else{
            update_data();
        }
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                retry.setVisibility(View.INVISIBLE);
                Intent restart_activity = new Intent(getApplicationContext(),for_video_image.class);
                restart_activity.putExtra("date",date);
                startActivity(restart_activity);
            }
        });

        another_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                retry.setVisibility(View.INVISIBLE);
                another_search.setVisibility(View.INVISIBLE);
                Intent back_to_selectdate = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(back_to_selectdate);
            }
        });
    }

    public void update_data(){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground( Void... voids ) {
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, searching_url, null, new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse( JSONObject response ) {
                        try {
                            if (response.getString("media_type").equalsIgnoreCase("image")) {
                                image_url = response.getString("url");
                                Picasso.with(getApplicationContext()).load(image_url).fit().centerInside().into(imageView);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setVisibility(View.GONE);
                                    }
                                },10000);
                            } else if (response.getString("media_type").equalsIgnoreCase("video")) {
                                progressBar.setVisibility(View.GONE);
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(image_url)));
                            } else {
                                no_content.setText(response.getString("media_type"));
                                progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse( VolleyError error ) {
                        Toast.makeText(getApplicationContext(), "Some error occured", Toast.LENGTH_LONG);
                    }
                });
                requestQueue.add(jsonObjectRequest);
                return null;
            }
            @Override
            protected void onPostExecute( Void aVoid ) {
                super.onPostExecute(aVoid);
                another_search.setVisibility(View.VISIBLE);
            }
        }.execute();
    }
}