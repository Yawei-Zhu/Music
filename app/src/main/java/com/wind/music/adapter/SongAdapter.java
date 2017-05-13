package com.wind.music.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        View v = View.inflate(getContext(), R.layout.item_song_local, null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(params);
        v.setOnClickListener(_OnClickListener);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Song song = getData().get(position);
        holder.bindData(song, position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPic;
        TextView tvTitle;
        TextView tvName;
        Song song;
        int position;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setTag(this);
            ivPic = (ImageView) itemView.findViewById(R.id.iv_pic);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
        }

        public void bindData(Song song, int position) {
            this.position = position;
            this.song = song;
            tvTitle.setText(song.title);
            tvName.setText(song.artist);
            if (song.album_art != null) {
                loadImage(ivPic, song.album_art);
            }
        }

        private void loadImage(final ImageView iv, String uri) {
            iv.setImageBitmap(null);
            new AsyncTask<String, Void, Bitmap>() {
                @Override
                protected Bitmap doInBackground(String... params) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(params[0], options);
                    int h = options.outHeight;
                    int w = options.outWidth;
                    int multipe = Math.min(h / 200, w / 200);
                    options.inSampleSize = multipe;
                    options.inJustDecodeBounds = false;
                    return BitmapFactory.decodeFile(params[0], options);
                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    iv.setImageBitmap(bitmap);
                }
            }.execute(uri);
        }
    }

    private View.OnClickListener _OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ViewHolder holder = (ViewHolder) v.getTag();
            if (holder != null && mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(holder.song, holder.position);
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
        public void onItemClick(Song item, int position);
    }
}
