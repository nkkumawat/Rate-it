package com.example.sonu.rateit.gridView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sonu.rateit.R;
import com.example.sonu.rateit.saparateView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by sonu on 6/6/17.
 */

public class GridViewAdapter extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private ArrayList data = new ArrayList();

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) row.findViewById(R.id.text);
            holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        final ImageItem item = (ImageItem) data.get(position);
        holder.imageTitle.setText(item.title);
//        holder.image.setImageBitmap(item.getImage());
        Picasso.with(context)
                .load(item.image)
                .fit().centerInside()
                .placeholder(R.drawable.loading)
                .error(R.drawable.placeholder)
                .into(holder.image);

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , saparateView.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id" , item.id);
                intent.putExtra("name" ,  item.teacherName);
                intent.putExtra("email" ,  item.email);
//                    intent.putExtra("phone" ,  phoneNo.getText().toString());
//                    intent.putExtra("picture" ,  picture.get().toString());
//                    intent.putExtra("about" ,  about.getText().toString());
//                    intent.putExtra("experience" ,  experience.getText().toString());
                intent.putExtra("qualification" ,  item.qualification);
                intent.putExtra("points" ,  item.points);
                intent.putExtra("department" ,  item.department);
                context.startActivity(intent);
            }
        });

        return row;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }
}