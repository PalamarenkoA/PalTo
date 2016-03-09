package com.geekhub.palto.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by andrey on 09.03.16.
 */
public class MovieTouchHelper extends ItemTouchHelper.SimpleCallback {
    private RVadapter mMovieAdapter;

    public MovieTouchHelper(RVadapter movieAdapter) {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.mMovieAdapter = movieAdapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mMovieAdapter.swap(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int i = direction;
        mMovieAdapter.remove(viewHolder.getAdapterPosition());
    }
}
