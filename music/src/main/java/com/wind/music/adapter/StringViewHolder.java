package com.wind.music.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/6/23.
 */

public class StringViewHolder extends ListAdapter.ViewHolder<String> {

    private final TextView text;

    public StringViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        super(inflater.inflate(android.R.layout.simple_list_item_1, parent, false), viewType);

        // initView
        text = (TextView) findViewById(android.R.id.text1);
    }

    @Override
    protected void bindData(String itemData, int position) {
        super.bindData(itemData, position);

        text.setText(itemData);
    }
}
