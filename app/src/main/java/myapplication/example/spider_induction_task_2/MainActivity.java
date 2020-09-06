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
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    TextView selected_date,error;
    Button enter,select_date,change_date,search;
    String date_selected;
    Calendar calendar;
    Integer today_date,today_month,today_year;
    Boolean error_visible = false;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        error =(TextView)findViewById(R.id.error);
        search = (Button)findViewById(R.id.search);
        select_date = (Button)findViewById(R.id.select_date);
        selected_date = (TextView)findViewById(R.id.selected_date);
        change_date = (Button)findViewById(R.id.change);
        enter = (Button)findViewById(R.id.enter);
        change_date.setVisibility(View.INVISIBLE);
        enter.setVisibility(View.INVISIBLE);
        error.setVisibility(View.INVISIBLE);
        calendar = Calendar.getInstance();
        today_date = calendar.get(Calendar.DATE);
        today_month = calendar.get(Calendar.MONTH);
        today_year = calendar.get(Calendar.YEAR);
        date_selected = calendar.get(Calendar.YEAR) + "-" + singledigittaker(calendar.get((Calendar.MONTH)))+"-"+singledigittaker(calendar.get(Calendar.DATE));
        selected_date.setText(day_of_week(calendar.get(Calendar.DAY_OF_WEEK)) + ", " +month_getter(calendar.get(Calendar.MONTH)) + " "+singledigittaker(calendar.get(Calendar.DATE))+ " ,"+calendar.get(Calendar.YEAR));
        select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                search.setVisibility(View.INVISIBLE);
                if(error_visible)error.setVisibility(View.INVISIBLE);
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
        if((year <= today_year) && (month <= today_month) && (dayOfMonth <= today_date )){
            date_selected = year+"-"+singledigittaker(month+1)+"-"+singledigittaker(dayOfMonth);
            String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
            selected_date.setText(currentDateString);
            enter.setVisibility(View.VISIBLE);
            change_date.setVisibility(View.VISIBLE);
            select_date.setVisibility(View.INVISIBLE);
            search.setVisibility(View.INVISIBLE);
        }
        else
        error.setVisibility(View.VISIBLE);
        error.setText("Date must be between Jun 16, 1995 and "+month_getter(today_month)+" "+singledigittaker(today_date)+", "+today_year);
        error_visible = true;
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

    public String day_of_week(Integer num){
        switch (num){
            case 2: return "Monday";
            case 3: return "Tuesday";
            case 4: return "Wednesday";
            case 5: return "Thursday";
            case 6: return "Friday";
            case 7: return "Saturday";
            case 1: return "Sunday";
        }
        return "";
    }

    public String month_getter(Integer num){
        switch (num){
            case 0:return "January";
            case 1: return "Febuarary";
            case 2:return  "March";
            case 3:return "April";
            case 4: return"May";
            case 5: return  "June";
            case 6: return  "July";
            case 7: return  "August";
            case 8: return  "September";
            case 9: return  "October";
            case 10: return  "November";
            case 11: return  "December";
        }
        return "";
    }
}