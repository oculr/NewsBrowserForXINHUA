package com.cjluhz.curriculum.newsbrowserforxinhua;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class RecyclerOnScrollListener  extends RecyclerView.OnScrollListener {
    private int currentPage = 1;
    private int previousTotal = 0;
    private LinearLayoutManager mLinearLayoutManager;
    private Context context;
    private boolean loading = true;
    int firstVisibleItem, visibleItemCount, totalItemCount;

    public RecyclerOnScrollListener(LinearLayoutManager manager, Context c){
        this.mLinearLayoutManager = manager;
        this.context = c;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();


        if (loading) {
            if (totalItemCount - previousTotal != 0) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading
                && (totalItemCount - visibleItemCount) <= firstVisibleItem) {
            onLoadMore(++currentPage);
            loading = true;
            Toast.makeText(context, "加载中", Toast.LENGTH_SHORT).show();
        }
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void resetCurrentPage() {
        this.currentPage = 1;
    }

    public abstract void onLoadMore(int currentPage);
}
