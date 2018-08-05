package com.yseleshi.earthquake;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by dell laptop on 8/1/2017.
 */

public class EarthquakeDialogFragment extends DialogFragment {

    ArrayList<Earthquake> earthquakes;
    public int position;



    public static EarthquakeDialogFragment newInstance (int position, ArrayList<Earthquake> earthquakes){
      EarthquakeDialogFragment frag = new EarthquakeDialogFragment();
        Earthquake selectedEarthquake = earthquakes.get(position);
        Date dateInf = new Date(selectedEarthquake.getTime());
        String address = selectedEarthquake.getPlace();
        String date = dateInf.toString();
        String mag = String.valueOf(selectedEarthquake.getMag());
        String coordinates = String.valueOf(selectedEarthquake.getCoordinates());
        String detail = String.valueOf(selectedEarthquake.getDetail());


        Bundle args = new Bundle();
        args.putString("address", address);
        args.putString("date", date);
        args.putString("mag", mag);
        args.putString("coordinates", coordinates);
        args.putString("detail", detail);

        frag.setArguments(args);

        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String address = getArguments().getString("address");
        String date = getArguments().getString("date");
        String mag = getArguments().getString("mag");
        String detail = getArguments().getString("detail");
        String coordinates = getArguments().getString("coordinates");



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View mView = inflater.inflate(R.layout.dialog_layout, null);

        TextView diaAddress = (TextView) mView.findViewById(R.id.addressDialog);
        TextView diaDate = (TextView) mView.findViewById(R.id.dateDialog);
        TextView diaMag = (TextView) mView.findViewById(R.id.magDialog);
        TextView diaCoordinates = (TextView) mView.findViewById(R.id.coordinates);
        TextView diaDetail = (TextView) mView.findViewById(R.id.detail);




        diaAddress.setText(address);
        diaDate.setText(date);
        diaMag.setText(mag);
        diaCoordinates.setText(coordinates);
        diaDetail.setText(detail);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(mView)
                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EarthquakeDialogFragment.this.getDialog().cancel();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EarthquakeDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
