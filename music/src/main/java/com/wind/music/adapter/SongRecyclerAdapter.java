package com.wind.music.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wind.music.R;
import com.wind.music.bean.Song;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/6/28.
 */

public class SongRecyclerAdapter extends CommonAdapter<Song> {

    public SongRecyclerAdapter(Context context, List<Song> data) {
        super(context, R.layout.item_song_local, data);
    }

    @Override
    protected void convert(ViewHolder holder, final Song song, final int position) {
        TextView tvTitle = holder.getView(R.id.tv_title);
        tvTitle.setText(song.title);

        TextView tvName = holder.getView(R.id.tv_name);
        tvName.setText(song.artist);

        TextView tvAlbum = holder.getView(R.id.tv_album);
        tvAlbum.setText(song.album);

        ImageView ivPic = holder.getView(R.id.iv_pic);
        ivPic.setImageURI(Uri.parse(song.album_art));

        holder.setOnClickListener(R.id.item_song_local, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(song, position);
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
        public void onItemClick(Song item, int position);
    }
}
