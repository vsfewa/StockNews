package com.example.stocknews;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.example.stocknews.MainActivity.HotnewsList;


public class HotnewsPageFragment extends Fragment {

    private static final String POSITION = "position";

    public static HotnewsPageFragment newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        HotnewsPageFragment fragment = new HotnewsPageFragment();
        fragment.setArguments(args);
        return fragment;
    }



    private List<Integer> colors = new ArrayList<>();
    {
        colors.add(R.color.white);
        colors.add(android.R.color.holo_blue_bright);
    }
    private int mPosition;
    private List<HotNews> newsList = HotnewsList;

    private View view;//定义view用来设置fragment的layout
    public RecyclerView HotnewsRecyclerView;
    private HotNewsAdapter hotNewsAdapter;
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
        container.setBackgroundResource(colors.get(mPosition));;
    }
    private void initRecyclerview(){
        HotnewsRecyclerView = view.findViewById(R.id.Hotnewsview);
        Log.d("see it", String.valueOf(newsList));
        hotNewsAdapter = new HotNewsAdapter(getActivity(),newsList);
        hotNewsAdapter.setOnItemClickListener(new HotNewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView Parent, View view, int position, HotNews data) {
                data = newsList.get(position);
                Intent i = new Intent(view.getContext(),WebBrowserActivity.class);
                i.putExtra(POSITION,position);
                view.getContext().startActivity(i);
            }
        });

                HotnewsRecyclerView.setAdapter(hotNewsAdapter);
        HotnewsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        HotnewsRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
    }
    static class ViewPagerFragmentStateAdapter extends FragmentStateAdapter {
        private List<Fragment> fragmentList = new ArrayList<>() ;
        private final int COLUMN_LENGTH = 2;
        public ViewPagerFragmentStateAdapter(@NonNull FragmentActivity fragmentManager) throws InterruptedException {
            super(fragmentManager);
            /**
             * 等待数据
             */
            Thread.currentThread().sleep(2000);
            fragmentList.add(HotnewsPageFragment.newInstance(0));
            fragmentList.add(HotnewsPageFragment.newInstance(1));
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