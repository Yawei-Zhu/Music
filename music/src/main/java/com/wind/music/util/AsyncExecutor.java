package com.wind.music.util;

import android.os.Handler;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

public final class AsyncExecutor {
    private static AsyncExecutor mAsyncExecutor;

    private ScheduledExecutorService mExecutorService;
    private Handler mHandler;

    public static AsyncExecutor of() {
        if (mAsyncExecutor == null) {
            mAsyncExecutor = new AsyncExecutor();
        }

        return mAsyncExecutor;
    }

    private AsyncExecutor() {
        mExecutorService = Executors.newScheduledThreadPool(4);
        mHandler = new Handler();
    }

    public <P, R> void execute(P param, Executable<P, R> executable) {
        mExecutorService.execute(new ExecutableRunnable<>(param, executable));
    }

    public <P, R> Future<?> submit(P param, Executable<P, R> executable) {
        return mExecutorService.submit(new ExecutableRunnable<>(param, executable));
    }

    public interface Executable<P, R> {
        R onLoading(P param);
        void onLoaded(P param, R result);
    }

    private final class ExecutableRunnable<P, R>  implements Runnable {
        private final Executable<P, R> mExecutable;
        private final P mParam;
        private R result;

        private ExecutableRunnable(P param, Executable<P, R> executable) {
            mParam = param;
            mExecutable = executable;
        }

        @Override
        public void run() {
            result = mExecutable.onLoading(mParam);
            mHandler.post(mRunnable);
        }

        private final Runnable mRunnable = new Runnable() {
            @Override
            public void run() {
                mExecutable.onLoaded(mParam, result);
            }
        };
    }
}
