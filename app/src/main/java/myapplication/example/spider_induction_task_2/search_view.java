package myapplication.example.spider_induction_task_2;

import android.media.Image;
import android.widget.ImageView;
import android.widget.VideoView;

public class search_view  {
    String URL,media_type,title,description;

    public search_view( String view_url, String mediatype,String title, String description ){
        URL = view_url;
        media_type = mediatype;
        this.title = title;
        this.description = description;
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

    public String getDescription() {
        return description;
    }
}
