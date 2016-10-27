package com.example.hongguangjin.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hongguang.jin on 2016/10/27.
 */
public class RecyclerDataAdapter extends RecyclerView.Adapter<RecyclerDataAdapter.MyViewHolder> {
    private Context context;
    private List<String> data;

    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public RecyclerDataAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public RecyclerDataAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.search_item, parent, false), listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerDataAdapter.MyViewHolder holder, int position) {
        holder.tv.setText(data.get(position));
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private OnItemClickListener mListener;
        private TextView tv;

        public MyViewHolder(View view, OnItemClickListener mListener) {
            super(view);
            this.mListener = mListener;
            tv = (TextView) view.findViewById(R.id.id_num);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getAdapterPosition());
            }
        }
    }
}
