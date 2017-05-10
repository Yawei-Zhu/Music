package com.wind.music.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wind.music.R;
import com.wind.music.bean.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/24.
 */

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {

    private Context mContext;
    private List<Song> mData;

    public SongAdapter(Context context, List<Song> data) {
        setContext(context);
        setData(data);
    }

    public void setContext(Context context) {
        if (context == null) {
            throw new IllegalAccessError("context == null");
        }
        this.mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    public void setData(List<Song> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        this.mData = data;
    }

    public List<Song> getData() {
        return mData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = View.inflate(getContext(), R.layout.item_song_billboard, null);
        v.setOnClickListener(_OnClickListener);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Song song = getData().get(position);
        holder.bindData(song, position + 1);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSequence;
        TextView tvTitle;
        TextView tvName;
        Song song;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setTag(this);
            tvSequence = (TextView) itemView.findViewById(R.id.tv_sequence);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
        }

        public void bindData(Song song, int sequence) {
            this.song = song;
            tvSequence.setText(String.valueOf(sequence));
            tvTitle.setText(song.title);
            tvName.setText(song.artist_name);
        }
    }

    private View.OnClickListener _OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ViewHolder holder = (ViewHolder) v.getTag();
            if (holder != null && mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(holder.song);
            }
        }
    };

    private OnItemClickListener mOnItemClickListener;

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        this.mOnItemClickListener = l;
    }

    public interface OnItemClickListener {
        public void onItemClick(Song item);
    }
}
