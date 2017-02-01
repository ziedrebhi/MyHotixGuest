package com.hotix.myhotixguest.updater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.entities.ItemActiviteModel;

import java.util.ArrayList;

/**
 * Created by ziedrebhi on 30/01/2017.
 */

public class ActivitesViewAdapter extends RecyclerView
        .Adapter<ActivitesViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "ActivitesViewAdapter";
    private static MyClickListener myClickListener;
    Context context;
    private ArrayList<ItemActiviteModel> mDataset;

    public ActivitesViewAdapter(ArrayList<ItemActiviteModel> myDataset, Context context1) {
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
                .inflate(R.layout.activite_view_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.label.setText(mDataset.get(position).getNom());
        holder.dateTime.setText(mDataset.get(position).getDescription());
        holder.price.setText(String.valueOf(mDataset.get(position).getPrix()));
        holder.dateDeb.setText(mDataset.get(position).getDateDebut());
        holder.dateFin.setText(mDataset.get(position).getDateFin());
    }

    public void addItem(ItemActiviteModel dataObj, int index) {
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
        TextView label;
        TextView dateTime;
        TextView price;
        TextView dateDeb;
        TextView dateFin;

        public DataObjectHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.textView);
            dateTime = (TextView) itemView.findViewById(R.id.textView2);
            price = (TextView) itemView.findViewById(R.id.textView5);
            dateDeb = (TextView) itemView.findViewById(R.id.textView7);
            dateFin = (TextView) itemView.findViewById(R.id.textView8);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}