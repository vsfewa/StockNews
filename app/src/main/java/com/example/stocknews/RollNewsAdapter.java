package com.example.stocknews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.ArrayList;
import java.util.List;



public class RollNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_CONTENT = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int START_REFRESH = 1;
    private Context context;
    private List<RollNews> newsList = new ArrayList<>();
    private RecyclerView rvParent;
    public RollNewsAdapter(Context context, List<RollNews> newsList) {
        this.context = context;
        this.newsList = newsList;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        rvParent = (RecyclerView) parent;
        if (viewType == TYPE_CONTENT) {
            View itemView = View.inflate(parent.getContext(), R.layout.roll_news_adapter, null);
            return new myViewHolder(itemView);
        }
        else if (viewType == TYPE_FOOTER) {
            return new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer, parent, false));
        }
       return null;
    }




    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (holder instanceof myViewHolder) {
            RollNews rollnews = newsList.get(position);
            ((myViewHolder) holder).title.setText(rollnews.title);
            ((myViewHolder) holder).time.setText(rollnews.getCtime());
            ((myViewHolder) holder).Digest.setText(rollnews.digest);
        } else if (type == TYPE_FOOTER) {
            ProgressBar pbLoading = ((FooterViewHolder) holder).pbLoading;
            TextView tvLoadMore = ((FooterViewHolder) holder).tvLoadMore;
        }
    }
    @Override
    public int getItemCount() {
        return newsList.size()+1;
    }
    public int getListSize() {
        return newsList.size();
    }
    @Override
    public int getItemViewType(int position) {
        if (position + START_REFRESH == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_CONTENT;
        }
    }
   public class myViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView time;
        private TextView Digest;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.Title2);
            time = itemView.findViewById(R.id.Time2);
            Digest = itemView.findViewById(R.id.Digest);
        }

    }
    public static class FooterViewHolder extends ViewHolder {
        private TextView tvLoadMore;
        public ProgressBar pbLoading;

        public FooterViewHolder(View itemView) {
            super(itemView);
            tvLoadMore = itemView.findViewById(R.id.tv_item_footer_load_more);
            pbLoading = itemView.findViewById(R.id.pb_item_footer_loading);
        }
    }
}
