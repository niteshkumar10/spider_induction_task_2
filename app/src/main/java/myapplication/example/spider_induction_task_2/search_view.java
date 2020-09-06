package myapplication.example.spider_induction_task_2;

import android.media.Image;
import android.widget.ImageView;
import android.widget.VideoView;

import java.util.ArrayList;

public class search_view  {
    String URL,media_type,title,description;
    ArrayList<String> keywords;

    public search_view( String view_url, String mediatype,String title, String description, ArrayList<String> keyword ){
        URL = view_url;
        media_type = mediatype;
        this.title = title;
        this.description = description;
        keywords = new ArrayList<>(keyword);
    }

    public String getTitle() {
        return title;
    }
    public String getURL(){
        return URL;
    }
    public String getmedia_type(){
        return media_type;
    }
    public ArrayList getkeywords(){
        return keywords;
    }
    public String getDescription() {
        return description;
    }
}
