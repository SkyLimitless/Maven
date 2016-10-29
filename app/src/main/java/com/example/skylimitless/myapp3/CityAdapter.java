package com.example.skylimitless.myapp3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.skylimitless.myapp3.CityOutput;
import com.example.skylimitless.myapp3.R;

import java.util.List;

/**
 * Created by Aakash on 3/21/2016.
 */
public class CityAdapter extends ArrayAdapter<CityOutput> {
    public CityAdapter(Context context, List<CityOutput> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Create our view if it is not recycled
        CityOutput cityout = getItem(position);
        if( convertView == null ) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.cityresourcelist, parent, false);
        }

        // Get a reference to our views
        TextView Rname = (TextView)convertView.findViewById(R.id.name);
        TextView Rcat = (TextView)convertView.findViewById(R.id.category);
        TextView Raddr = (TextView)convertView.findViewById(R.id.address);
        RatingBar Rrate = (RatingBar)convertView.findViewById(R.id.rating);

        // Get the current course
        //Course course = getItem(position);

        // Set the view text
        Rname.setText(cityout.getName());
        Rcat.setText(cityout.getCategory());
        Raddr.setText(cityout.getAddr());
        Rrate.setRating(Float.parseFloat(cityout.getRating()));
        return convertView;
    }
}
