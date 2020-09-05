package myapplication.example.spider_induction_task_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class search_view_adapter extends RecyclerView.Adapter<search_view_adapter.ExampleViewHolder> {
    private ArrayList<search_view> mExampleList;
    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView textview;

        public ExampleViewHolder( View itemView ) {
            super(itemView);
            textview = itemView.findViewById(R.id.textview_for_search);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
    public search_view_adapter(ArrayList<search_view> exampleList ) {
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_view, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder( ExampleViewHolder holder, int position ) {
        search_view currentItem = mExampleList.get(position);
        String title = currentItem.getTitle();
        holder.textview.setText(title);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
