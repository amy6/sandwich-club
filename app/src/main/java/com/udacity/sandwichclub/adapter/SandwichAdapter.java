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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.ImageLoadCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


//custom ArrayAdapter to populate custom listview/gridview
public class SandwichAdapter extends ArrayAdapter<Sandwich> {

    public SandwichAdapter(@NonNull Context context, @NonNull List<Sandwich> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Sandwich sandwich = getItem(position);
        SandwichViewHolder holder;
        //inflate a new view if no reusable view exists
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_sandwich_item, parent, false);
            holder = new SandwichViewHolder(convertView);
            convertView.setTag(holder);
        }
        //reuse an existing view if it exists
        holder = (SandwichViewHolder) convertView.getTag();
        //make the progressbar visible to indicate the progress of loading the view
        holder.progressBar.setVisibility(View.VISIBLE);
        if (sandwich != null) {
            Picasso.with(getContext())
                    .load(sandwich.getImage())
                    //set error image
                    .error(R.drawable.ic_error_outline)
                    //implement callback to handle error from getting data to listview/gridview
                    .into(holder.imageView, new ImageLoadCallback(holder.progressBar, holder.imageView, false));
            holder.name.setText(sandwich.getMainName());

            //set sandwich name text to switch alignment alternatively
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                holder.name.setGravity(position % 2 == 0 ? Gravity.START : Gravity.END);
            }
        }
        return convertView;
    }

    //ViewHolder class to help reduce the calls to findViewById method
    class SandwichViewHolder {

        @BindView(R.id.image_iv)
        ImageView imageView;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.progressBar)
        ProgressBar progressBar;

        SandwichViewHolder (View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}
