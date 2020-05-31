package com.wind.music.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.wind.music.R;
import com.wind.music.adapter.BriefSongAdapter;
import com.wind.music.bean.Song;
import com.wind.music.util.MusicPlayer;

import java.util.ArrayList;
import java.util.List;

public class MusicListDialog extends BaseDialog {
    public static final String TAG = MusicListDialog.class.getSimpleName();
    private MusicPlayer mMusicPlayer;
    private BriefSongAdapter mSongAdapter;
    private List<Song> mSongs = new ArrayList<>();
    private Button btMode;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity = Gravity.BOTTOM;
            window.setAttributes(layoutParams);
        }
        return inflater.inflate(R.layout.dialog_music_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState );
        initLayout(view);
        initData(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void initLayout(View view) {
        btMode = (Button) view.findViewById(R.id.bt_mode);
        btMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMusicPlayer != null) {
                    int mode = mMusicPlayer.changePlayMode();
                    updateMode(mode);
                }
            }
        });

        ImageButton ibClose = (ImageButton) view.findViewById(R.id.ib_close);
        ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().cancel();
            }
        });

        RecyclerView rvMusicList = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rvMusicList.setLayoutManager(lm);
        mSongAdapter = new BriefSongAdapter(getContext(), mSongs);
        mSongAdapter.setOnItemClickListener(new BriefSongAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int viewId, Song item, int position) {
                if (mMusicPlayer == null) {
                    return;
                }
                switch (viewId) {
                    case R.id.ib_delete:
                        mMusicPlayer.remove(position);
                        mSongs.remove(position);
                        mSongAdapter.notifyDataSetChanged();
                        break;
                    case R.id.item_song:
                        if (position == mMusicPlayer.whatIsPlaying() && mMusicPlayer.isPlaying()) {
                            mMusicPlayer.pause();
                        } else {
                            if (position == mMusicPlayer.whatIsPlaying()) {
                                mMusicPlayer.play();
                            } else {
                                mMusicPlayer.play(position);
                            }
                        }
                        break;
                }
            }
        });
        rvMusicList.setAdapter(mSongAdapter);
    }

    private void initData(Bundle savedInstanceState) {
        mSongs.clear();
        if (mMusicPlayer != null && mMusicPlayer.getData() != null) {
            mSongs.addAll(mMusicPlayer.getData());
        }
        if (mSongAdapter != null) {
            mSongAdapter.notifyDataSetChanged();
        }

        updateMode(mMusicPlayer == null ? MusicPlayer.MODE_CYCLE : mMusicPlayer.getPlayMode());
    }

    public void updateMode(int mode) {
        int s = 0;
        switch (mode) {
            case MusicPlayer.MODE_CYCLE:
                s = R.string.music_player_mode_cycle;
                break;
            case MusicPlayer.MODE_RANDOM:
                s = R.string.music_player_mode_random;
                break;
            case MusicPlayer.MODE_SINGLE:
                s = R.string.music_player_mode_single;
                break;
        }

        if (btMode != null && s != 0) {
            btMode.setText(s);
        }
    }

    public void setPlayer(MusicPlayer player) {
        if (mMusicPlayer != player) {
            mMusicPlayer = player;
            initData(null);
        }
    }
}
