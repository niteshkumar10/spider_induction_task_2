package myapplication.example.spider_induction_task_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class detail extends AppCompatActivity {

    ImageView detail_image_view;
    String url,title;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        detail_image_view = (ImageView)findViewById(R.id.detail_image_view);
        url = getIntent().getStringExtra("url");
        Picasso.with(getApplicationContext()).load(url).fit().centerInside().into(detail_image_view);
    }
}