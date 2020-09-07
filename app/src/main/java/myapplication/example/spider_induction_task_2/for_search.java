package myapplication.example.spider_induction_task_2;

import androidx.appcompat.app.ActionBar;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
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

public class for_search extends AppCompatActivity implements search_view_adapter.OnItemClickListener, recycler_view_adapter_2.onoptionclicklistner {

    EditText search_box;
    Button for_search,retry,scroll_down,clear_constrain;
    RecyclerView search_view,option_bar;
    ArrayList<search_view> mExampleList;
    String url,media_type,web_url,from_edittext,cateogary;
    search_view_adapter mExampleAdapter;
    String title,descreption;
    TextView errortextview2;
    ImageView errorimageview2;
    ProgressBar progressBar;
    ArrayList<String> keywords = new ArrayList<>();
    ArrayList<String> keywords_complete_list = new ArrayList<>();
    recycler_view_adapter_2 option_bar_adapter;
    ArrayList<String> result;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_for_search);

        scroll_down = (Button)findViewById(R.id.more_option);
        option_bar = (RecyclerView)findViewById(R.id.recyclerview_2);
        progressBar = (ProgressBar)findViewById(R.id.progressBar2);
        search_box = (EditText)findViewById(R.id.search_box);
        search_view = (RecyclerView)findViewById(R.id.search_view);
        for_search = (Button)findViewById(R.id.to_search);
        errortextview2 = (TextView)findViewById(R.id.errortextview2);
        errorimageview2 = (ImageView)findViewById(R.id.error_imageview2);
        retry = (Button)findViewById(R.id.retry_forsearch);
        search_view = findViewById(R.id.search_view);
        clear_constrain = (Button)findViewById(R.id.clear_constraint);
        progressBar.setVisibility(View.INVISIBLE);
        clear_constrain.setVisibility(View.INVISIBLE);
        search_view.setVisibility(View.VISIBLE);
        errorimageview2.setVisibility(View.INVISIBLE);
        errortextview2.setVisibility(View.INVISIBLE);
        retry.setVisibility(View.INVISIBLE);
        search_view.setVisibility(View.INVISIBLE);
        option_bar.setVisibility(View.INVISIBLE);
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
        mExampleList = new ArrayList<>();
        search_view.setLayoutManager(new LinearLayoutManager(this));
        for_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                    if(search_box.getText().length()== 0)search_box.setError("please enter thing to search");
                    else{
                        keywords_complete_list.clear();
                        web_url = "https://images-api.nasa.gov/search?q=";
                        from_edittext = search_box.getText().toString();
                        web_url = web_url+from_edittext;
                        from_edittext = "";
                        progressBar.setMax(100);
                        progressBar.setIndeterminate(true);
                        progressBar.setVisibility(View.VISIBLE);
                        UIUtil.hideKeyboard(for_search.this);
                        search_view.setVisibility(View.VISIBLE);
                        parseJSON();
                    }

            }
        });


        scroll_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                option_bar.setVisibility(View.VISIBLE);
                search_view.setVisibility(View.INVISIBLE);
                for_search.setVisibility(View.INVISIBLE);
                search_box.setVisibility(View.INVISIBLE);
                scroll_down.setVisibility(View.INVISIBLE);
                clear_constrain.setVisibility(View.VISIBLE);
                setting_option_bar();
            }
        });

        clear_constrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                option_bar.setVisibility(View.INVISIBLE);
                clear_constrain.setVisibility(View.INVISIBLE);
                search_box.setVisibility(View.VISIBLE);
                for_search.setVisibility(View.VISIBLE);
                search_view.setVisibility(View.VISIBLE);
                scroll_down.setVisibility(View.VISIBLE);
                mExampleAdapter.getFilter().filter(null);
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
                                    mExampleList.clear();
                                    JSONObject obj_1 = response.getJSONObject("collection");
                                    JSONArray array_1 = obj_1.getJSONArray("items");
                                    if(array_1.length() != 0) {
                                        for (int i = 0; i < array_1.length(); i++) {
                                            JSONObject hit = array_1.getJSONObject(i);
                                            if (hit.has("links")) {
                                                JSONArray array_2 = hit.getJSONArray("links");
                                                JSONArray array_3 = hit.getJSONArray("data");
                                                JSONObject obj_2 = array_3.getJSONObject(0);
                                                JSONObject obj = array_2.getJSONObject(0);
                                                media_type = obj.getString("render");
                                                url = obj.getString("href");
                                                if (obj_2.has("description"))
                                                    descreption = obj_2.getString("description");
                                                else if (obj_2.has("description_508"))
                                                    descreption = obj_2.getString("description_508");
                                                else descreption = "";
                                                title = obj_2.getString("title");
                                                JSONArray array_4 = obj_2.getJSONArray("keywords");
                                                for (int j = 0; j < array_4.length(); j++) {
                                                    keywords.add(array_4.getString(j));
                                                }
                                                mExampleList.add(new search_view(url, media_type, title, descreption, keywords));
                                                keywords_complete_list.addAll(keywords);
                                                keywords.clear();
                                            }
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

    public ArrayList<String> set_the_unique(){
        result = new ArrayList<>();
        result.add(keywords_complete_list.get(0));
        Boolean matched = false;
        for(int i = 1; i < (keywords_complete_list.size()); i++){
            for(int j = 0; j < result.size(); j++){
                if((keywords_complete_list.get(i).equalsIgnoreCase(result.get(j))))matched = true;
            }
            if(!matched){
                result.add(keywords_complete_list.get(i));
            }
            matched = false;
        }
        return result;
    }

    public void setting_option_bar(){
        option_bar.setHasFixedSize(true);
        option_bar.setLayoutManager(new LinearLayoutManager(this));
        option_bar_adapter= new recycler_view_adapter_2(set_the_unique());
        option_bar.setAdapter(option_bar_adapter);
        option_bar.setVisibility(View.VISIBLE);
        option_bar_adapter.setonoptionclicklistener(for_search.this);
        option_bar_adapter.setonoptionclicklistener(new recycler_view_adapter_2.onoptionclicklistner() {
            @Override
            public void onItemClick( int position ) {
                option_bar.setVisibility(View.INVISIBLE);
                search_view.setVisibility(View.VISIBLE);
                for_search.setVisibility(View.VISIBLE);
                search_box.setVisibility(View.VISIBLE);
                scroll_down.setVisibility(View.VISIBLE);
                clear_constrain.setVisibility(View.INVISIBLE);
                cateogary = result.get(position);
                mExampleAdapter.getFilter().filter(cateogary);
            }
        });
    }
}
