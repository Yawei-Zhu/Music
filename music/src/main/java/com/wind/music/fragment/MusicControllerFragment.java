package com.wind.music.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wind.music.R;
import com.wind.music.bean.BillBoardBean;
import com.wind.music.presenter.ImagePresenter;
import com.wind.music.presenter.PresenterFactory;
import com.wind.music.util.MusicPlayer;

/**
 * Created by Wind on 2017/8/27.
 */

public class MusicControllerFragment extends BaseFragment implements com.wind.music.view.ImageView {
    private final String TAG = MusicControllerFragment.class.getSimpleName();

    private ProgressBar sbProgress;
    private ImageButton btPlay;
    private ImageButton btList;
    private TextView tvTitle;
    private ImageView ivPic;
    private MusicPlayer mPlayer;
    private ImagePresenter mImagePresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMusicListShowListener) {
            mOnMusicListShowListener = (OnMusicListShowListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_music_controller, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mImagePresenter = PresenterFactory.createImagePresenter(this);
        initLayout(view);
    }

    @Override
    public void onDestroyView() {
        setPlayer(null);
        finishLayout();
        mImagePresenter.setView(null);
        mImagePresenter = null;
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        mOnMusicListShowListener = null;
        super.onDetach();
    }

    private void initLayout(View view) {
        sbProgress = (ProgressBar) view.findViewById(R.id.sb_progress);

        btList = (ImageButton) view.findViewById(R.id.bt_list);
        if (btList != null) {
            btList.setOnClickListener(mOnListClickListener);
        }
        ivPic = (ImageView) view.findViewById(R.id.iv_pic);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);

        btPlay = (ImageButton) view.findViewById(R.id.bt_play);
        if (btPlay != null) {
            btPlay.setOnClickListener(pauseListener);
        }
    }

    private void finishLayout() {
        sbProgress = null;

        if (btList != null) {
            btList.setOnClickListener(null);
            btList = null;
        }
        ivPic = null;
        tvTitle = null;

        if (btPlay != null) {
            btPlay.setOnClickListener(null);
            btPlay = null;
        }
    }

    private final View.OnClickListener mOnListClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnMusicListShowListener != null) {
                mOnMusicListShowListener.onMusicListShow(MusicControllerFragment.this);
            }
        }
    };

    private final View.OnClickListener pauseListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mPlayer != null && mPlayer.getData() != null) {
                if (mPlayer.isPlaying()) {
                    mPlayer.pause();
                } else {
                    mPlayer.play();
                }
            }
        }
    };

    private MusicPlayer.OnPlayInfoListener onPlayInfoListener = new MusicPlayer.OnPlayInfoListener() {
        BillBoardBean.Song mOldSong = null;

        @Override
        public void onPlayInfo(BillBoardBean.Song song, int position) {
            if (mOldSong != song) {
                BillBoardBean.Song oldSong = mOldSong;
                updateSong(oldSong, song);
                mOldSong = song;
            }
            updatePosition(position);
            updatePlaying(mPlayer != null && mPlayer.isPlaying());
        }
    };

    public void updatePlaying(boolean isPlaying) {
        if (btPlay != null) {
            btPlay.setActivated(isPlaying);
        }
    }

    private void updateSong(BillBoardBean.Song oldSong, BillBoardBean.Song newSong) {
        if (tvTitle != null) {
            tvTitle.setText(newSong == null ? "" : newSong.getTitle());
        }

        if (sbProgress != null) {
            if (newSong == null) {
                sbProgress.setMax(100);
            } else if (newSong.isIs_local()) {
                sbProgress.setMax(newSong.getFile_duration());
            } else {
                sbProgress.setMax(newSong.getFile_duration() * 1000);
            }
        }

        if (oldSong != null && oldSong.getPic_small() != null) {
            mImagePresenter.cancel(oldSong.getPic_small());
        }

        if (newSong != null && newSong.getPic_small() != null) {
            mImagePresenter.load(newSong.getPic_small());
        }
    }

    private void updatePosition(int position) {
        if (sbProgress != null) {
            sbProgress.setProgress(position);
        }
    }

    public void setPlayer(MusicPlayer player) {
        if (mPlayer != player) {
            if (mPlayer != null) {
                mPlayer.removeOnPlayInfoListener(onPlayInfoListener);
            }
            mPlayer = player;
            if (mPlayer != null) {
                mPlayer.addOnPlayInfoListener(onPlayInfoListener);
            } else {
                onPlayInfoListener.onPlayInfo(null, 0);
            }
        }
    }

    private OnMusicListShowListener mOnMusicListShowListener;

    @Override
    public void onLoaded(String path, Bitmap bitmap) {
        ivPic.setImageBitmap(bitmap);
    }

    public interface OnMusicListShowListener {
        void onMusicListShow(MusicControllerFragment musicController);
    }
}
