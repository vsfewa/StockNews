package com.example.stocknews;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import static com.example.stocknews.MainActivity.RECENT_URL;
import static com.example.stocknews.OkHttpSSL.RefreshTime;
import static com.example.stocknews.OkHttpSSL.RollNewsPost;
import static com.example.stocknews.OkHttpSSL.RollNewsPre;;
import static com.example.stocknews.OkHttpSSL.getHotnewswithOkHttp;
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
    private List<RollNews> newsList;
    private RollNewsAdapter rollNewsAdapter;
    private Handler mHandler;
    private static final int REFRESH = 1;

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
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 2:
                        rollNewsAdapter = new RollNewsAdapter(getActivity(), newsList);
                        RollnewsRecyclerView.setAdapter(rollNewsAdapter);
                        LinearLayoutManager RollnewsviewLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        RollnewsRecyclerView.setLayoutManager(RollnewsviewLayoutManager);
                        DividerItemDecoration divider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
                        divider.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divide));
                        RollnewsRecyclerView.addItemDecoration(divider);
                        RollnewsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                            int lastVisibleItem = 0;
                            @Override
                            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                super.onScrollStateChanged(recyclerView, newState);
                                if (rollNewsAdapter != null && newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 ==
                                        rollNewsAdapter.getItemCount()) {
                                    RefreshTime = RefreshTime + REFRESH;
                                    //RefreshTime ++;
                                    String url = RollNewsPre + RefreshTime + RollNewsPost;
                                    Log.d("checktime", String.valueOf(RefreshTime));
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            newsList = getRollnewswithOkHttp(newsList,url);
                                            mHandler.sendEmptyMessage(3);
                                        }
                                    }).start();
                                }
                            }
                            @Override
                            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                super.onScrolled(recyclerView, dx, dy);
                                lastVisibleItem = RollnewsviewLayoutManager.findLastVisibleItemPosition();
                            }
                        });
                        break;
                    case 3:
                        rollNewsAdapter.setData(newsList);
                        rollNewsAdapter.notifyDataSetChanged();
                    case 4:
                        rollNewsAdapter.setData(newsList);
                        swipeRefreshLayout.setRefreshing(false);
                        rollNewsAdapter.notifyDataSetChanged();
                }
                return true;
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                newsList = getRollnewswithOkHttp(newsList,RECENT_URL);
                mHandler.sendEmptyMessage(2);
            }
        }).start();
        Log.d("see it", String.valueOf(newsList));

        swipeRefreshLayout = view.findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);
            newsList.clear();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    newsList = getRollnewswithOkHttp(newsList,RECENT_URL);
                    mHandler.sendEmptyMessage(4);
                }
            }).start();

        });
    }

}

