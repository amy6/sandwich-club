package com.udacity.sandwichclub.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SandwichAdapter extends ArrayAdapter<Sandwich> {

    public SandwichAdapter(@NonNull Context context, @NonNull List<Sandwich> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Sandwich sandwich = getItem(position);
        SandwichViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_sandwich_item, parent, false);
            holder = new SandwichViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (SandwichViewHolder) convertView.getTag();
        if (sandwich != null) {
            Picasso.with(getContext())
                    .load(sandwich.getImage())
                    .placeholder(R.drawable.placeholder)
                    .into(holder.imageView);
            holder.name.setText(sandwich.getMainName());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                holder.name.setGravity(position % 2 == 0 ? Gravity.START : Gravity.END);
            }
        }
        return convertView;
    }

    class SandwichViewHolder {

        @BindView(R.id.image)
        ImageView imageView;
        @BindView(R.id.name)
        TextView name;

        SandwichViewHolder (View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}
