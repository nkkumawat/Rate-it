package com.example.sonu.rateit;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by sonu on 20/5/17.
 */

public class teachersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<teachersData> data= Collections.emptyList();
    teachersData current;
    int currentPos = 0;

    // create constructor to innitilize context and data sent from MainActivity
    public teachersAdapter(Context context, List<teachersData> data){
        this.context=context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.teacher_data_list, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        teachersData current=data.get(position);
        myHolder.teacherName.setText(current.nameOfTeacher);
        myHolder.qualification.setText(current.qualification);
        myHolder.department.setText(current.department);
//        myHolder.phoneNo.setText(current.phone);
        myHolder.email.setText(current.email);
////        myHolder.experience.setText(current.experience);
        myHolder.points.setText(current.points);
////        myHolder.about.setText(current.about);
        myHolder.id.setText(current.id);

        Picasso.with(context)
                .load(current.picture)
                .placeholder(R.drawable.loading)
                .error(R.drawable.placeholder)
                .into(myHolder.picture);
        Picasso.with(context)
                .load(R.drawable.star)
                .placeholder(R.drawable.star)
                .error(R.drawable.star)
                .into(myHolder.star);

    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    class MyHolder extends RecyclerView.ViewHolder{

        TextView teacherName;
        ImageView picture , star;
        TextView qualification;
        TextView phoneNo;
        TextView email;
        TextView experience;
        TextView points;
        TextView about , id , department;
        RelativeLayout relativeLayout;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            teacherName= (TextView) itemView.findViewById(R.id.teacherName);
            picture= (ImageView) itemView.findViewById(R.id.profilePic);
            star= (ImageView) itemView.findViewById(R.id.star);
            qualification = (TextView) itemView.findViewById(R.id.qualification);
//            phoneNo = (TextView) itemView.findViewById(R.id.phoneNo);
            email = (TextView) itemView.findViewById(R.id.email);
//            experience = (TextView) itemView.findViewById(R.id.experience);
            points = (TextView) itemView.findViewById(R.id.points);
//            about = (TextView) itemView.findViewById(R.id.about);
            id = (TextView) itemView.findViewById(R.id.id);
            department = (TextView) itemView.findViewById(R.id.department);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.container);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context , current.about, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context , saparateView.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("id" , id.getText().toString());
                    intent.putExtra("name" ,  teacherName.getText().toString());
                    intent.putExtra("email" ,  email.getText().toString());
//                    intent.putExtra("phone" ,  phoneNo.getText().toString());
//                    intent.putExtra("picture" ,  picture.get().toString());
//                    intent.putExtra("about" ,  about.getText().toString());
//                    intent.putExtra("experience" ,  experience.getText().toString());
                    intent.putExtra("qualification" ,  qualification.getText().toString());
                    intent.putExtra("points" ,  points.getText().toString());
                    intent.putExtra("department" ,  department.getText().toString());
                    context.startActivity(intent);
                }
            });
//            textPrice = (TextView) itemView.findViewById(R.id.textPrice);
        }

    }

}