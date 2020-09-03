package myapplication.example.spider_induction_task_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    TextView selected_date;
    Button enter,select_date,change_date,search;
    String date_selected;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = (Button)findViewById(R.id.search);
        select_date = (Button)findViewById(R.id.select_date);
        selected_date = (TextView)findViewById(R.id.selected_date);
        change_date = (Button)findViewById(R.id.change);
        enter = (Button)findViewById(R.id.enter);
        change_date.setVisibility(View.INVISIBLE);
        enter.setVisibility(View.INVISIBLE);
        select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                DialogFragment datePicker = new dateselection();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        change_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                enter.setVisibility(View.INVISIBLE);
                select_date.setVisibility(View.VISIBLE);
                change_date.setVisibility(View.INVISIBLE);
                search.setVisibility(View.VISIBLE);
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                search.setVisibility(View.INVISIBLE);
                select_date.setVisibility(View.INVISIBLE);
                change_date.setVisibility(View.INVISIBLE);
                enter.setVisibility(View.INVISIBLE);
                Intent first_intent = new Intent(getApplicationContext(),for_video_image.class);
                first_intent.putExtra("date",date_selected);
                startActivity(first_intent);


            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent going_search = new Intent(getApplicationContext(),for_search.class);
                startActivity(going_search);
            }
        });
    }


    @Override
    public void onDateSet( DatePicker view, int year, int month, int dayOfMonth ) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        date_selected = year+"-"+singledigittaker(month+1)+"-"+singledigittaker(dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        selected_date.setText(currentDateString);
        enter.setVisibility(View.VISIBLE);
        change_date.setVisibility(View.VISIBLE);
        select_date.setVisibility(View.INVISIBLE);
        search.setVisibility(View.INVISIBLE);
    }

    public String singledigittaker(int num){
        String s;
        if(num < 10){
            s = "0"+num;
            return s;
        }
        else{
            s = num + "";
            return s;
        }
    }
}