package com.example.sonu.rateit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Collections;
import java.util.List;

/**
 * Created by sonu on 4/6/17.
 */

public class commentsAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<commentsData> data= Collections.emptyList();
    commentsData current;
    int currentPos = 0;
    public commentsAdapter(Context context, List<commentsData> data){
        this.context=context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.comments_recycler_layout, parent,false);
        commentsAdapter.MyHolder holder=new commentsAdapter.MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        commentsAdapter.MyHolder myHolder= (commentsAdapter.MyHolder) holder;
        commentsData current = data.get(position);
        myHolder.comments.setText(current.commentText);
        myHolder.nameOfComments.setText(current.nameOfUser);

    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    class MyHolder extends RecyclerView.ViewHolder{
        TextView comments;
        TextView nameOfComments;
        public MyHolder(View itemView) {
            super(itemView);
            comments= (TextView) itemView.findViewById(R.id.commentsTextView);
            nameOfComments = (TextView) itemView.findViewById(R.id.nameofCommenter);
        }

    }

}