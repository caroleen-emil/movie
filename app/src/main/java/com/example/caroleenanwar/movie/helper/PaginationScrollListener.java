package com.example.caroleenanwar.movie.helper;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

    LinearLayoutManager layoutManager;

    public PaginationScrollListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = 0;
        int totalItemCount = 0;
        int firstVisibleItemPosition = 0;
        visibleItemCount = recyclerView.getChildCount();
        if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layout = (StaggeredGridLayoutManager) recyclerView
                    .getLayoutManager();

            totalItemCount = layout.getItemCount();
            firstVisibleItemPosition = layout.findFirstVisibleItemPositions(null)[0];
        } else {
            LinearLayoutManager layout = (LinearLayoutManager) recyclerView
                    .getLayoutManager();
            firstVisibleItemPosition = layout.findFirstVisibleItemPosition();
        }
        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0) {
                loadMoreItems();
            }
        }
    }

    protected abstract void loadMoreItems();

    public abstract int getTotalPageCount();

    public abstract boolean isLastPage();

    public abstract boolean isLoading();
}