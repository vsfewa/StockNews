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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;



import static com.example.stocknews.OkHttpSSL.getHotnewswithOkHttp;


public class HotnewsPageFragment extends Fragment {

    private static final String POSITION = "position";

    public static HotnewsPageFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        HotnewsPageFragment fragment = new HotnewsPageFragment();
        fragment.setArguments(args);
        return fragment;
    }



    private int mPosition;
    private List<HotNews> newsList ;

    private View view;//定义view用来设置fragment的layout
    public RecyclerView HotnewsRecyclerView;
    private HotNewsAdapter hotNewsAdapter;
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
        view =  inflater.inflate(R.layout.hot_news, container, false);
        initRecyclerview();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RelativeLayout container = view.findViewById(R.id.container);
    }
    private void initRecyclerview(){
        HotnewsRecyclerView = view.findViewById(R.id.Hotnewsview);
        /*
        获取数据后设置recyclerview
         */
        Handler mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        hotNewsAdapter = new HotNewsAdapter(getActivity(),newsList);
                        hotNewsAdapter.setOnItemClickListener(new HotNewsAdapter.OnItemClickListener() {
                            @Override
                            /*
                            点击进入对应的网页
                             */
                            public void onItemClick(RecyclerView Parent, View view, int position, HotNews data) {
                                data = newsList.get(position);
                                Intent i = new Intent(view.getContext(),WebBrowserActivity.class);
                                i.putExtra(POSITION,position);
                                i.putExtra("url",data.url);
                                view.getContext().startActivity(i);
                            }
                        });
                        HotnewsRecyclerView.setAdapter(hotNewsAdapter);
                        HotnewsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        DividerItemDecoration divider = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
                        divider.setDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.divide));
                        HotnewsRecyclerView.addItemDecoration(divider);
                        break;
                    default:
                        break;
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                newsList = getHotnewswithOkHttp();
                Message msg = new Message();
                msg.what = 1;
                mHandler.sendMessage(msg);
            }
        }).start();

        swipeRefreshLayout = view.findViewById(R.id.Refresh);
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    public static class ViewPagerFragmentStateAdapter extends FragmentStateAdapter {

        private static List<Fragment> fragmentList = new ArrayList<>() ;
        private final int COLUMN_LENGTH = 2;
        public ViewPagerFragmentStateAdapter(@NonNull FragmentActivity fragmentManager) throws InterruptedException {
            super(fragmentManager);
            fragmentList.add(HotnewsPageFragment.newInstance(0));
            fragmentList.add(RollnewsPageFragment.newInstance(1));

        }


        @NonNull
        @Override
        public int getItemCount() {
            return COLUMN_LENGTH;
        }


        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragmentList.get(position);
        }
    }
}