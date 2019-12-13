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

import com.bumptech.glide.Glide;
import com.wind.music.R;
import com.wind.music.bean.BillBoardBean;

/**
 * Created by Administrator on 2017/4/24.
 */

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {

    private Context mContext;
    private BillBoardBean mData;

    public SongAdapter(Context context, BillBoardBean data) {
        setContext(context);
        setData(data);
    }

    public void setContext(Context context) {
        if (context == null) {
            throw new IllegalAccessError("context is null");
        }
        this.mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    public void setData(BillBoardBean data) {
        this.mData = data;
    }

    public BillBoardBean getData() {
        return mData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = View.inflate(getContext(), R.layout.item_song, null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(params);
        v.setOnClickListener(_OnClickListener);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BillBoardBean.Song song = getData().getSong_list().get(position);
        holder.tvTitle.setText(song.getTitle());
        holder.tvSubtitle.setText(song.getArtist_name());
        Glide.with(getContext()).load(song.getPic_small()).into(holder.ivPic);
    }

    @Override
    public int getItemCount() {
        int count = 0;
        try {
            count = mData.getSong_list().size();
        } catch (NullPointerException e) {
        }
        return count;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPic;
        TextView tvTitle;
        TextView tvSubtitle;
        BillBoardBean.Song song;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setTag(this);
            ivPic = (ImageView) itemView.findViewById(R.id.iv_pic);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvSubtitle = (TextView) itemView.findViewById(R.id.tv_subtitle);
        }

        public void bindData(BillBoardBean.Song song, int position) {
            this.song = song;
            tvTitle.setText(song.getTitle());
            tvSubtitle.setText(song.getArtist_name());
            if (song.getPic_small() != null) {
                loadImage(ivPic, song.getPic_small());
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
                    options.inSampleSize = Math.min(h / 200, w / 200);
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
                mOnItemClickListener.onItemClick(holder.song, holder.getAdapterPosition());
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
        public void onItemClick(BillBoardBean.Song item, int position);
    }
}
