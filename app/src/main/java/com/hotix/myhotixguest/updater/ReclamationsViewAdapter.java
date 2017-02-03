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
import com.hotix.myhotixguest.entities.ItemReclamationModel;

import java.util.ArrayList;

/**
 * Created by ziedrebhi on 30/01/2017.
 */

public class ReclamationsViewAdapter extends RecyclerView
        .Adapter<ReclamationsViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "ReclamationsViewAdapter";
    private static MyClickListener myClickListener;
    Context context;
    private ArrayList<ItemReclamationModel> mDataset;

    public ReclamationsViewAdapter(ArrayList<ItemReclamationModel> myDataset, Context context2) {
        mDataset = myDataset;
        context = context2;
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reclamation_view_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.description.setText(mDataset.get(position).getDescription());

        holder.objet.setText(context.getResources().getString(R.string.object) + String.valueOf(mDataset.get(position).getObject()));
        holder.date.setText(context.getResources().getString(R.string.date_decl) + mDataset.get(position).getDateCreation());
        if (mDataset.get(position).isTraite()) {
            holder.card.setBackgroundResource(R.color.radial_gray_light);
        }

    }

    public void addItem(ItemReclamationModel dataObj, int index) {
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
        TextView objet;
        TextView description;
        TextView date;
        CardView card;

        public DataObjectHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.card_view);
            objet = (TextView) itemView.findViewById(R.id.textView);
            description = (TextView) itemView.findViewById(R.id.textView2);
            date = (TextView) itemView.findViewById(R.id.textView6);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}