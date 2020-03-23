package com.wind.music.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wind.music.Application;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/8.
 */

public class Network {
    private static final String TAG = Network.class.getSimpleName();

    public static final int CODE_SUCCESS = 22000;
    public static final int CODE_PARSE_FAIL = 100;
    public static final int CODE_VOLLEY_ERROR = 99;
    public static final int CODE_NETWORK_UNAVAILABLE = 97;

    public static final String MSG_SUCCESS = "成功";
    public static final String MSG_ERROR_UNKNOWN = "未知错误";
    public static final String MSG_PARSE_FAIL = "数据错误";
    public static final String MSG_VOLLEY_ERROR = "网络错误";
    public static final String MSG_NETWORK_UNAVAILABLE = "网络不可用";


    private static RequestQueue mQueue;
    private static Context mContext;


    /**
     * init network in {@link Application#onCreate()}  Application.onCreate()}.
     *
     * @param context a instance of Application
     */
    public static void init(Context context) {
        mContext = context;
        mQueue = Volley.newRequestQueue(context);
    }


    /**
     * release network in {@link Application#onTerminate() Application.onTerminate()}.
     */
    public static void release() {
        mContext = null;
        if (mQueue != null) {
            mQueue.stop();
            mQueue = null;
        }
    }

    public static void load(String url, NetworkListener listener) {
        load(Request.Method.GET, url, listener);
    }

    public static void load(int method,
                            final String url,
                            final NetworkListener listener) {

        int type = getNetworkType();
        if (type == 0) {
            listener.onRespond(CODE_NETWORK_UNAVAILABLE, MSG_NETWORK_UNAVAILABLE, "");
            return;
        }

        StringRequest request = new StringRequest(
                method,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        deliveryResponse(url, response, listener);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TAG, url + " --> " + error.toString());
                        if (listener != null)
                            listener.onRespond(
                                    CODE_VOLLEY_ERROR,
                                    MSG_VOLLEY_ERROR,
                                    error.toString());
                    }
                });

        if (mQueue == null) {
            throw new IllegalAccessError("Please call Network.init(Context) first!");
        }

        mQueue.add(request);
    }

    /**
     * get the type of the current connecting network
     *
     * @return 0 for no-network; 1 for mobile; 2 for wifi; 3 for other
     */
    public static int getNetworkType() {

        if (mContext == null) {
            throw new IllegalAccessError("Please call Network.init(Context) first!");
        }

        ConnectivityManager manager = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        if (networkinfo == null || !networkinfo.isAvailable()) return 0;
        if (networkinfo.getType() == ConnectivityManager.TYPE_MOBILE) return 1;
        if (networkinfo.getType() == ConnectivityManager.TYPE_WIFI) return 2;
        return 3;
    }

    private static void deliveryResponse(String url, String response, NetworkListener listener) {
        Log.i(TAG, url + " --> " + response);
        JSONObject obj;
        try {
            obj = new JSONObject(response);
            int code = obj.getInt("error_code");
            Log.i(TAG, "code=" + code);
            if (code == CODE_SUCCESS) {
                if (listener != null)
                    listener.onRespond(CODE_SUCCESS, MSG_SUCCESS, response);
            } else {
                if (listener != null)
                    listener.onRespond(code, MSG_ERROR_UNKNOWN, response);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            if (listener != null)
                listener.onRespond(CODE_PARSE_FAIL, MSG_PARSE_FAIL, e.toString());
        }
    }

}
