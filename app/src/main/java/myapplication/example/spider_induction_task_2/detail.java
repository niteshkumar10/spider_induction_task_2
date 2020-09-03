package myapplication.example.spider_induction_task_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;

public class detail extends AppCompatActivity {

    ImageView detail_image_view;
    ProgressBar progressBar;
    String url;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        progressBar = (ProgressBar)findViewById(R.id.progressBar3);
        detail_image_view = (ImageView)findViewById(R.id.detail_image_view);
        progressBar.setMax(100);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
        url = getIntent().getStringExtra("image_url");
        Picasso.with(getApplicationContext()).load(url).fit().centerInside().into(detail_image_view);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        },10000);
    }
}