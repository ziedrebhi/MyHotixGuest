package com.hotix.myhotixguest.updater;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.entities.ItemLoginModel;

import java.util.ArrayList;

/**
 * Created by ziedrebhi on 30/01/2017.
 */

public class UsersViewAdapter extends RecyclerView
        .Adapter<UsersViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "UsersViewAdapter";
    private static MyClickListener myClickListener;
    Context context;
    private ArrayList<ItemLoginModel> mDataset;

    public UsersViewAdapter(ArrayList<ItemLoginModel> myDataset, Context context1) {
        mDataset = myDataset;
        context = context1;
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.details_item_user_view, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.Name.setText(mDataset.get(position).getName());
        holder.Qualite.setText(mDataset.get(position).getQualite());
        if (mDataset.get(position).isMaster()) {
            holder.card.setBackgroundResource(R.color.colorPrimaryLeger);
        }
    }

    public void addItem(ItemLoginModel dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView Name;
        TextView Qualite;
        CardView card;

        public DataObjectHolder(View itemView) {
            super(itemView);
            Name = (TextView) itemView.findViewById(R.id.tvNumber1);
            Qualite = (TextView) itemView.findViewById(R.id.qualite);
            card = (CardView) itemView.findViewById(R.id.card_view);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}