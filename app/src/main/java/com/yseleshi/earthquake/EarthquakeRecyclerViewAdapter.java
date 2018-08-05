package com.yseleshi.earthquake;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;


public class EarthquakeRecyclerViewAdapter extends RecyclerView.Adapter<EarthquakeRecyclerViewAdapter.ViewHolder>  {



    public interface OnAdapterItemInteraction {
        void onItemSelected(ViewHolder holder, Integer position, ArrayList<Earthquake> earthquakeList);



    }


    public ArrayList<Earthquake> earthquakes = new ArrayList<>();
    private final OnAdapterItemInteraction mListener;

    public EarthquakeRecyclerViewAdapter(ArrayList<Earthquake> earthquakes, OnAdapterItemInteraction listener) {
        this.earthquakes = earthquakes;
        mListener = listener;
    }

    @Override
    public EarthquakeRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final EarthquakeRecyclerViewAdapter.ViewHolder holder, final int position) {

        Earthquake earthquake = earthquakes.get(position);
        Date date = new Date(earthquake.getTime());
        holder.addressView.setText(earthquake.getPlace());
        holder.dateView.setText(date.toString());
        holder.magView.setText(String.valueOf(earthquake.getMag()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                   // Earthquake selectedQuake = earthquakes.get(position);
                    mListener.onItemSelected(holder, position, earthquakes);
                }

            }
        });
    }

    public void add(Earthquake item) {
        earthquakes.add(item);
        notifyItemInserted(earthquakes.size()-1);

    }


    @Override
    public int getItemCount() {
        return earthquakes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView dateView;
        public final TextView addressView;
        public final TextView magView;



        public ViewHolder(View view) {
            super(view);
            mView = view;
            dateView = (TextView)mView.findViewById(R.id.timeTxt);
            addressView = (TextView)mView.findViewById(R.id.placeTxt);
            magView = (TextView)mView.findViewById(R.id.magTxt);
        }
    }


}
