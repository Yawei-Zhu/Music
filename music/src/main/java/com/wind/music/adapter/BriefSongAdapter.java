package com.wind.music.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.wind.music.R;
import com.wind.music.bean.Song;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/6/28.
 */

public class BriefSongAdapter extends CommonAdapter<Song> {

    public BriefSongAdapter(Context context, List<Song> data) {
        super(context, R.layout.item_song_brief, data);
    }

    @Override
    protected void convert(ViewHolder holder, final Song song, final int position) {
        TextView tvTitle = holder.getView(R.id.tv_title);
        tvTitle.setText(song.getTitle());

        TextView tvSubtitle = holder.getView(R.id.tv_subtitle);
        tvSubtitle.setText(song.getAuthor());

        holder.setOnClickListener(R.id.ib_delete, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(R.id.ib_delete, song, position);
                }
            }
        });

        holder.setOnClickListener(R.id.item_song, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(R.id.item_song, song, position);
                }
            }
        });
    }

    private OnItemClickListener mOnItemClickListener;

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        this.mOnItemClickListener = l;
    }

    public interface OnItemClickListener {
        public void onItemClick(int viewId, Song item, int position);
    }
}
