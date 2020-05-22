package com.example.projectb;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class StringList extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] array;


    StringList(Activity context,
               String[] array) {
        super(context, R.layout.artist_row, array);
        this.context = context;
        this.array = array;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.artist_row, null, true);
        TextView txtTitle = rowView.findViewById(R.id.txt);
        txtTitle.setText(array[position]);
        txtTitle.setTextSize(20);
        return rowView;
    }
}