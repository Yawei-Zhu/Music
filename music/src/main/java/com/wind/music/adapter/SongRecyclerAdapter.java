package com.wind.music.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wind.music.R;
import com.wind.music.bean.BillBoardBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/6/28.
 */

public class SongRecyclerAdapter extends CommonAdapter<BillBoardBean.Song> {

    public SongRecyclerAdapter(Context context, List<BillBoardBean.Song> data) {
        super(context, R.layout.item_song_local, data);
    }

    @Override
    protected void convert(ViewHolder holder, final BillBoardBean.Song song, final int position) {
        TextView tvTitle = holder.getView(R.id.tv_title);
        tvTitle.setText(song.getTitle());

        TextView tvName = holder.getView(R.id.tv_name);
        tvName.setText(song.getArtist_name());

        TextView tvAlbum = holder.getView(R.id.tv_album);
        tvAlbum.setText(song.getAlbum());

        ImageView ivPic = holder.getView(R.id.iv_pic);
        Bitmap bm = BitmapFactory.decodeFile(song.getPic_small());
        ivPic.setImageBitmap(bm);

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
        public void onItemClick(BillBoardBean.Song item, int position);
    }
}
