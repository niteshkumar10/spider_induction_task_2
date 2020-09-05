package myapplication.example.spider_induction_task_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventKt;
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class for_search extends AppCompatActivity implements search_view_adapter.OnItemClickListener {

    EditText search_box;
    Button for_search,retry;
    RecyclerView search_view;
    ArrayList<search_view> mExampleList;
    String url,media_type,web_url,from_edittext;
    search_view_adapter mExampleAdapter;
    String title,descreption;
    TextView errortextview2;
    ImageView errorimageview2;
    ProgressBar progressBar;
    Boolean checked_once = false;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_search);

        progressBar = (ProgressBar)findViewById(R.id.progressBar2);
        search_box = (EditText)findViewById(R.id.search_box);
        search_view = (RecyclerView)findViewById(R.id.search_view);
        for_search = (Button)findViewById(R.id.to_search);
        errortextview2 = (TextView)findViewById(R.id.errortextview2);
        errorimageview2 = (ImageView)findViewById(R.id.error_imageview2);
        retry = (Button)findViewById(R.id.retry_forsearch);
        search_view = findViewById(R.id.search_view);
        progressBar.setVisibility(View.INVISIBLE);
        search_view.setVisibility(View.VISIBLE);
        errorimageview2.setVisibility(View.INVISIBLE);
        errortextview2.setVisibility(View.INVISIBLE);
        retry.setVisibility(View.INVISIBLE);
        search_view.setVisibility(View.INVISIBLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork == null){
            errortextview2.setText("ERROR!!"+"\n"+"Internet Connection not available");
            errorimageview2.setVisibility(View.VISIBLE);
            search_box.setVisibility(View.INVISIBLE);
            for_search.setVisibility(View.INVISIBLE);
            search_view.setVisibility(View.VISIBLE);
            retry.setVisibility(View.VISIBLE);
        }
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent retry_same = new Intent(getApplicationContext(),for_search.class);
                startActivity(retry_same);
            }
        });



        search_view.setHasFixedSize(true);
        search_view.setLayoutManager(new LinearLayoutManager(this));
        mExampleList = new ArrayList<>();
        web_url = "https://images-api.nasa.gov/search?q=";
        for_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                if(search_box.getText().length()== 0)search_box.setError("please enter thing to search");
                else{
                    from_edittext = search_box.getText().toString();
                    web_url = web_url+from_edittext;
                    progressBar.setMax(100);
                    progressBar.setIndeterminate(true);
                    progressBar.setVisibility(View.VISIBLE);
                    UIUtil.hideKeyboard(for_search.this);
                    search_box.setEnabled(false);
                    search_view.setVisibility(View.VISIBLE);
                    checked_once = true;
                    parseJSON();
                }
            }
        });
        search_box.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange( View v, boolean hasFocus ) {
                if(hasFocus){
                    if(checked_once){
                        Intent next_search = new Intent(getApplicationContext(),for_search.class);
                        startActivity(next_search);
                    }
                }
            }
        });

    }
    private void parseJSON(){
        new  AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground( Void... voids ) {
                RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, web_url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject obj_1 = response.getJSONObject("collection");
                                    JSONArray array_1 = obj_1.getJSONArray("items");
                                    for (int i = 0; i < array_1.length(); i++) {
                                        JSONObject hit = array_1.getJSONObject(i);
                                        if(hit.has("links")){
                                            JSONArray array_2 = hit.getJSONArray("links");
                                            JSONArray array_3 = hit.getJSONArray("data");
                                            JSONObject obj_2 = array_3.getJSONObject(0);
                                            JSONObject obj = array_2.getJSONObject(0);
                                            media_type = obj.getString("render");
                                            if(obj_2.has("description")) descreption = obj_2.getString("description");
                                            else if(obj_2.has("description_508"))descreption = obj_2.getString("description_508");
                                            else descreption = "";
                                            Log.d("status",descreption);
                                            title = obj_2.getString("title");
                                            url = obj.getString("href");
                                            mExampleList.add(new search_view(url,media_type,title,descreption));
                                        }
                                    }
                                    mExampleAdapter = new search_view_adapter(mExampleList);
                                    search_view.setAdapter(mExampleAdapter);
                                    progressBar.setVisibility(View.GONE);
                                    mExampleAdapter.setOnItemClickListener(for_search.this);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        errortextview2.setText("No Content Found");
                        error.printStackTrace();
                    }
                });
                mRequestQueue.add(jsonObjectRequest);
                return  null;
            }
            @Override
            protected void onPostExecute( Void aVoid ) {
                super.onPostExecute(aVoid);
                search_box.setEnabled(true);
            }
        }.execute();
    }

    @Override
    public void onItemClick( int position ) {
        Intent detailIntent = new Intent(getApplicationContext(), detail.class);
        search_view clickedItem = mExampleList.get(position);
        detailIntent.putExtra("image_url", clickedItem.getURL());
        detailIntent.putExtra("description", clickedItem.getDescription());
        startActivity(detailIntent);
    }

}
