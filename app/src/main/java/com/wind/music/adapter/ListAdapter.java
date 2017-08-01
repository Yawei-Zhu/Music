package com.wind.music.adapter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ListAdapter<E> extends android.widget.BaseAdapter {

    private final Class<? extends ViewHolder<E>> viewHolderClass;
    private final Context context;
    private final LayoutInflater inflater;
    private final HashMap<View, ViewHolder<E>> holders = new HashMap<>();
    private List<E> data;

    public ListAdapter(Context context, List<E> data, Class<? extends ViewHolder<E>> cls) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.viewHolderClass = cls;
        setData(data);
    }

    public Context getContext() {
        return context;
    }

    public List<E> getData() {
        return data;
    }

    public void setData(List<E> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public E getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (itemViewSorter != null)
            return itemViewSorter.getItemViewType(getItem(position), position);
        else
            return 0;
    }

    @Override
    public int getViewTypeCount() {
        if (itemViewSorter != null)
            return itemViewSorter.getViewTypeCount();
        else
            return 1;
    }

    private ItemViewSorter<E> itemViewSorter;

    public ItemViewSorter<E> getItemViewSorter() {
        return itemViewSorter;
    }

    public void setItemViewSorter(ItemViewSorter<E> sorter) {
        this.itemViewSorter = sorter;
    }

    /**
     * this is a item view sorter, if you want a multi type adapter.
     * @param <T> same as E of ListAdapter
     */
    public interface ItemViewSorter<T> {

        public int getItemViewType(T item, int position);

        public int getViewTypeCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder<E> holder = null;
        if (convertView == null) {
            Constructor<? extends ViewHolder<E>> constructor = null;
            try {
                constructor = viewHolderClass.getConstructor(
                        LayoutInflater.class, ViewGroup.class, Integer.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            if (constructor != null) {
                try {
                    holder = constructor.newInstance(
                            inflater, parent, getItemViewType(position));
                } catch (InvocationTargetException | InstantiationException
                        | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            if (holder != null) {
                convertView = holder.getItemView();
                holders.put(convertView, holder);
            }
        } else {
            holder = holders.get(convertView);
        }

        if (holder != null) {
            holder.bindData(getItem(position), position);
        }

        return convertView;
    }


    /**
     * the subclass mast have constructor :
     * <p/>
     * <code>
     * public Example(LayoutInflater inflater, ViewGroup parent, int viewType) {<br/>
     * super(inflater.inflate(layoutId, parent, false), viewType);<br/>
     * or  super(inflater, parent, layoutId, viewType);<br/>
     * // initLayout, can use findViewById(id)<br/>
     * }
     * </code>
     * <p/>
     * {@link ViewHolder#ViewHolder(View, int) ViewHolder.ViewHolder(itemView, viewType)}<br/>
     * {@link ViewHolder#findViewById(int) ViewHolder.findViewById(id)}
     */
    public static class ViewHolder<E> {
        private final View itemView;
        private final int viewType;
        private int position;
        private E itemData;

        /**
         * @see ViewHolder
         */
        protected ViewHolder(LayoutInflater inflater, ViewGroup parent,
                             @LayoutRes int resId, int viewType) {
            this(inflater.inflate(resId, parent, false), viewType);
        }

        /**
         * @see ViewHolder
         */
        protected ViewHolder(View itemView, int viewType) {
            this.itemView = itemView;
            this.viewType = viewType;
        }

        protected final View findViewById(@IdRes int id) {
            return itemView.findViewById(id);
        }


        protected void bindData(E itemData, int position) {
            this.itemData = itemData;
            this.position = position;
        }

        public View getItemView() {
            return itemView;
        }

        public int getViewType() {
            return viewType;
        }

        public int getPosition() {
            return position;
        }

        public E getItemData() {
            return itemData;
        }
    }
}
