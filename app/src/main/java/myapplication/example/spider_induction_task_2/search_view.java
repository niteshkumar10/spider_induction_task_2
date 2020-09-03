package myapplication.example.spider_induction_task_2;

import android.media.Image;
import android.widget.ImageView;
import android.widget.VideoView;

public class search_view  {
    String URL,media_type,title;

    public search_view( String view_url, String mediatype,String title ){
        URL = view_url;
        media_type = mediatype;
        this.title = title;
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
}
