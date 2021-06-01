package com.example.stocknews;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import static com.example.stocknews.MainActivity.RECENT_URL;
import static com.example.stocknews.OkHttpSSL.RefreshTime;
import static com.example.stocknews.OkHttpSSL.RollNewsPost;
import static com.example.stocknews.OkHttpSSL.RollNewsPre;
import static com.example.stocknews.OkHttpSSL.RollnewsList;
import static com.example.stocknews.OkHttpSSL.getRollnewswithOkHttp;

public class RollnewsPageFragment  extends Fragment {

    private static final String POSITION = "position";

    public static RollnewsPageFragment newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        RollnewsPageFragment fragment = new RollnewsPageFragment();
        fragment.setArguments(args);
        return fragment;
    }


    private int mPosition;
    private List<RollNews> newsList = RollnewsList;
    private RollNewsAdapter rollNewsAdapter;

    private View view;//定义view用来设置fragment的layout
    public RecyclerView RollnewsRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPosition = getArguments().getInt(POSITION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.roll_news, container, false);
        initRecyclerview();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initRecyclerview() {
        RollnewsRecyclerView = view.findViewById(R.id.roll_news_view);
        Log.d("see it", String.valueOf(newsList));
        rollNewsAdapter = new RollNewsAdapter(getActivity(), newsList);
        RollnewsRecyclerView.setAdapter(rollNewsAdapter);
        LinearLayoutManager RollnewsviewLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        RollnewsRecyclerView.setLayoutManager(RollnewsviewLayoutManager);
        RollnewsRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        RollnewsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem = 0;
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (rollNewsAdapter != null && newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 ==
                        rollNewsAdapter.getItemCount()) {
                        RefreshTime = RefreshTime + 1;
                        String url = RollNewsPre + RefreshTime + RollNewsPost ;
                        newsList = getRollnewswithOkHttp(url);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    /*
                    刷新界面
                     */
                    rollNewsAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = RollnewsviewLayoutManager.findLastVisibleItemPosition();
            }
        });
        swipeRefreshLayout = view.findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);
            newsList.clear();
            newsList = getRollnewswithOkHttp(RECENT_URL);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            swipeRefreshLayout.setRefreshing(false);
            rollNewsAdapter.notifyDataSetChanged();
        });
    }
}
