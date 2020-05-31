package com.wind.music.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wind.music.R;
import com.wind.music.bean.BillBoardBean;
import com.wind.music.bean.Song;
import com.wind.music.presenter.ImagePresenter;
import com.wind.music.presenter.PresenterFactory;

import java.util.List;

/**
 * Created by Administrator on 2017/4/24.
 */

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    private final String TAG = SongAdapter.class.getSimpleName();

    private List<Song> mData;
    private int count = 0;

    public SongAdapter(List<Song> data) {
        setData(data);
    }

    public void setData(List<Song> data) {
        this.mData = data;
    }

    public List<Song> getData() {
        return mData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        count++;
        Log.d(TAG, "onCreateViewHolder: count=" + count);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_song, parent, false);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(params);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Song song = mData.get(position);
        holder.onBind(song, position);
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        holder.onRecycled();
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        int count = 0;
        try {
            count = mData.size();
        } catch (NullPointerException e) {
        }
        return count;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements com.wind.music.view.ImageView {
        private final String TAG = ViewHolder.class.getSimpleName();
        ImagePresenter mPresenter;
        ImageView ivPic;
        TextView tvTitle;
        TextView tvSubtitle;
        Song mSong;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvSubtitle = (TextView) itemView.findViewById(R.id.tv_subtitle);
            ivPic = (ImageView) itemView.findViewById(R.id.iv_pic);
        }

        public void onBind(Song song, int position) {
            mPresenter = PresenterFactory.createImagePresenter(this);
            mSong = song;
            tvTitle.setText(mSong.getTitle());
            tvSubtitle.setText(mSong.getAuthor());
            if (mSong.getPic_small() != null) {
                mPresenter.load(mSong.getPic_small());
            }
        }

        public void onRecycled() {
            tvTitle.setText(null);
            tvSubtitle.setText(null);
            ivPic.setImageResource(R.mipmap.icon_music);
            mPresenter.cancel(mSong.getPic_small());
            mSong = null;
            mPresenter.setView(null);
            mPresenter = null;
        }

        @Override
        public void onLoaded(String path, Bitmap bitmap) {
            ivPic.setImageBitmap(bitmap);
        }
    }
}
