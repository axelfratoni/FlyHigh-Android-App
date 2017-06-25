package com.app.hci.flyhigh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.app.Activity;
import java.util.List;


public class OfferAdapter extends BaseAdapter {

    Context context;
    List<Offer> offers;

    OfferAdapter(Context context, List<Offer> offers) {
        this.context = context;
        this.offers = offers;
    }

    @Override
    public int getCount() { return offers.size(); }

    @Override
    public Offer getItem(int position) { return offers.get(position); }

    @Override
    public long getItemId(int position) { return offers.indexOf(getItem(position)); }

    private class ViewHolder {
        TextView name;
        TextView price;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.offer_layout, null);
            holder = new ViewHolder();

            holder.name = (TextView) convertView.findViewById(R.id.offer_name);

            holder.price = (TextView) convertView.findViewById(R.id.offer_price);

            Offer offer_pos = offers.get(position);

            holder.name.setText(offer_pos.getName());

            holder.price.setText(offer_pos.getPriceString());
        }

        return convertView;

    }


}
