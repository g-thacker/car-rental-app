package edu.txstate.get26.carrentalapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomListViewAdaptor extends ArrayAdapter<Car> {

    Context context;

    public CustomListViewAdaptor(Context context, int resourceId, List<Car> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtCar;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Car thisCar = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_menu,null);
            holder = new ViewHolder();

            holder.imageView = convertView.findViewById(R.id.imgCar);
            holder.txtCar = convertView.findViewById(R.id.txtList);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        assert thisCar != null;
        holder.imageView.setImageResource(thisCar.getImage());
        holder.txtCar.setText(String.format("%s %s", thisCar.getMake(), thisCar.getModel()));

        return convertView;
    }
}
