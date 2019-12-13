package com.wind.music.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
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
import com.wind.music.bean.BillBoardBean;
import com.wind.music.service.PlayerService;
import com.wind.music.util.MusicPlayer;

import java.util.ArrayList;
import java.util.List;

public class MusicListDialog extends AppCompatDialogFragment {

    private MusicPlayer mMusicPlayer;
    private BriefSongAdapter mSongAdapter;
    private List<BillBoardBean.Song> mSongs = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        super.onViewCreated(view, savedInstanceState);
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
        final Button btMode = (Button) view.findViewById(R.id.bt_mode);
        btMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mode = mMusicPlayer.changePlayMode();
                updateMode((Button) view, mode);
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
            public void onItemClick(int viewId, BillBoardBean.Song item, int position) {
                switch (viewId) {
                    case R.id.ib_delete:
                        mMusicPlayer.getData().remove(position);
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
        mSongs.addAll(mMusicPlayer.getData());
        mSongAdapter.notifyDataSetChanged();
    }

    public void updateMode(Button button, int mode) {
        int s = 0;
        switch (mode) {
            case PlayerService.MODE_CYCLE:
                s = R.string.music_player_mode_cycle;
                break;
            case PlayerService.MODE_RANDOM:
                s = R.string.music_player_mode_random;
                break;
            case PlayerService.MODE_SINGLE:
                s = R.string.music_player_mode_single;
                break;
        }

        if (button != null && s != 0) {
            button.setText(s);
        }
    }

    public MusicPlayer getMusicPlayer() {
        return mMusicPlayer;
    }

    public void setMusicPlayer(MusicPlayer musicPlayer) {
        this.mMusicPlayer = musicPlayer;
    }
}
