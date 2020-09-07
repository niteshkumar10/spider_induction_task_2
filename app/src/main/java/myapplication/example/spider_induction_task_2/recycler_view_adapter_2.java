package myapplication.example.spider_induction_task_2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recycler_view_adapter_2 extends RecyclerView.Adapter<recycler_view_adapter_2.ExampleViewHolder> {
    private ArrayList<String> mExampleList;
    private onoptionclicklistner mListener;

    public interface onoptionclicklistner {
        void onItemClick(int position);
    }
    public void setonoptionclicklistener( onoptionclicklistner listener) {
        mListener = listener;
    }
    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView textview;

        public ExampleViewHolder( View itemView ) {
            super(itemView);
            textview = itemView.findViewById(R.id.textView_for_option);
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
    public recycler_view_adapter_2(ArrayList<String> exampleList ) {
        mExampleList = exampleList;
    }

    @Override
    public recycler_view_adapter_2.ExampleViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.option_bar, parent, false);
        return new recycler_view_adapter_2.ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder( recycler_view_adapter_2.ExampleViewHolder holder, int position ) {
        String currentItem = mExampleList.get(position).toString();
        holder.textview.setText(currentItem);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

}
