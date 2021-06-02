package com.example.stocknews;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HotNewsAdapter extends RecyclerView.Adapter<HotNewsAdapter.myViewHolder> implements View.OnClickListener{
    private Context context;
    private List<HotNews> newsList = new ArrayList<>();
    private RecyclerView rvParent;

    public HotNewsAdapter(Context context, List<HotNews> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public HotNewsAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        rvParent = (RecyclerView) parent;
        View itemView = View.inflate(context, R.layout.hot_news_adapter, null);
        itemView.setOnClickListener(this);
        return new myViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull HotNewsAdapter.myViewHolder holder, int position) {
        HotNews hotnews = newsList.get(position);
        holder.title.setText(hotnews.title);
        holder.time.setText(hotnews.getCtime());
        holder.summary.setText(hotnews.summary);
        if(hotnews.stocks != null) {
            holder.stocks.setText("相关证券: " + hotnews.stocks);
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
    /*
    点击进入网页
     */
    @Override
    public void onClick(View view) {
        int position = rvParent.getChildAdapterPosition(view);
        if (onItemClickListener!=null){
            onItemClickListener.onItemClick(rvParent,view,position,newsList.get(position));
        }
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView time;
        private TextView summary;
        private TextView stocks;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.Title1);
            time = itemView.findViewById(R.id.Time1);
            summary = itemView.findViewById(R.id.Summary1);
            stocks = itemView.findViewById(R.id.Stocks);
        }

    }


    /**
     * item点击接口
     */
    public interface OnItemClickListener {

        void onItemClick(RecyclerView Parent, View view, int position,HotNews data);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}



