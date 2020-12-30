package com.cjluhz.curriculum.newsbrowserforxinhua.ui.news;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cjluhz.curriculum.newsbrowserforxinhua.R;
import com.cjluhz.curriculum.newsbrowserforxinhua.RecyclerOnScrollListener;
import com.cjluhz.curriculum.newsbrowserforxinhua.ui.Browser.DefaultBrowser;
import com.cjluhz.curriculum.newsbrowserforxinhua.ui.Browser.WebViewBrowser;

import java.util.ArrayList;
import java.util.List;


public class NewsFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private BaseNewsAdapter adapter;
    private RecyclerView recyclerView;

    private String lookwhich;
    private String lookTitle;

    private RecyclerOnScrollListener scrollListener;

    public NewsFragment(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ShowToast")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_news, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.NewsView);
        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipeLayout);
        adapter = new BaseNewsAdapter(new ArrayList<>());

        LinearLayoutManager manager = new LinearLayoutManager(getContext());

        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);

        adapter.setAnimationEnable(true);
        adapter.setAnimationFirstOnly(true);

        //启动DefaultBrowser Activity
        initClickListener();

        //启动WebView Activity
        initLongListener();

        recyclerView.setAdapter(adapter);
        new load().execute(1);

        scrollListener = new RecyclerOnScrollListener(manager, getContext()) {
            @Override
            public void onLoadMore(int currentPage) {
                Toast.makeText(getContext(), "没有更多" + lookTitle, Toast.LENGTH_SHORT).show();
//                new load().execute(currentPage);
            }
        };
        recyclerView.addOnScrollListener(scrollListener);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                scrollListener.resetCurrentPage();
                new load().execute(1);
            }
        });

        return root;
    }

    public void remountData(String WhatUWantLook, String title){
        this.lookwhich = WhatUWantLook;
        this.lookTitle = title;
        scrollListener.resetCurrentPage();
        new load().execute(1);
    }


    class load extends AsyncTask<Object, Void, List<XINHUAPoliticsItem>> {

        @Override
        protected List<XINHUAPoliticsItem> doInBackground(Object... objects) {
            try {
                int pagenum = (int ) objects[0];
                XINHUAPoliticsParser parser = new XINHUAPoliticsParser(lookwhich, getContext());
                parser.access();
                if (pagenum == 1){
                    adapter.getData().clear();
                }
                return parser.getNews();
            } catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<XINHUAPoliticsItem> politics) {
            super.onPostExecute(politics);
            for (XINHUAPoliticsItem item : politics)
                adapter.addData(item);
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
            recyclerView.smoothScrollToPosition(0);
        }
    }

    public void initClickListener(){
        adapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            XINHUAPoliticsItem i = (XINHUAPoliticsItem) adapter.getItem(position);

            bundle.putString("date", i.getDate());
            bundle.putString("subtitle", i.getTitle());
            bundle.putString("title", this.lookTitle);
            bundle.putString("newsLink", i.getHref());

            Intent intent = new Intent(getContext(), DefaultBrowser.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }

    public void initLongListener(){
        adapter.setOnItemLongClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            XINHUAPoliticsItem i = (XINHUAPoliticsItem) adapter.getItem(position);

            bundle.putString("date", i.getDate());
            bundle.putString("subtitle", i.getTitle());
            bundle.putString("title", this.lookTitle);
            bundle.putString("newsLink", i.getHref());

            Intent intent = new Intent(getContext(), WebViewBrowser.class);
            intent.putExtras(bundle);
            startActivity(intent);
            return true;
        });
    }

}