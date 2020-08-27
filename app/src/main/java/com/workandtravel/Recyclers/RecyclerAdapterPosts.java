package com.workandtravel.Recyclers;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.workandtravel.Classes.Posts;
import com.workandtravel.R;

import java.util.ArrayList;


public class RecyclerAdapterPosts  extends    RecyclerView.Adapter<RecyclerAdapterPosts .viewitem> {



    ArrayList<Posts> items;
    Context context;
    public RecyclerAdapterPosts (Context c, ArrayList<Posts> item)
    {
        items=item;
        context=c;

    }

    //The View Item part responsible for connecting the row.xml with
    // each item in the RecyclerView
    //make declare and initalize
    class  viewitem extends RecyclerView.ViewHolder
    {

        //Declare
        TextView name,state,country,contents;
        ImageView image;

        //initialize
        public viewitem(View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.img);
            name=itemView.findViewById(R.id.name);
            state=itemView.findViewById(R.id.state);
            country=itemView.findViewById(R.id.country);
            contents=itemView.findViewById(R.id.contents);




        }
    }



    //onCreateViewHolder used to HAndle on Clicks
    @Override
    public viewitem onCreateViewHolder(final ViewGroup parent, int viewType) {



        //the itemView now is the row
        //We will add 2 onClicks


        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row2, parent, false);






        return new viewitem(itemView);
    }


    //to fill each item with data from the array depending on position
    @Override
    public void onBindViewHolder(final viewitem holder, final int position) {
        setFadeAnimation(holder.itemView);
        Glide.with(context).load(items.get(position).getImage()).into(holder.image);
        holder.country.setText(items.get(position).getCountry());
        holder.name.setText(items.get(position).getName());
        holder.state.setText(items.get(position).getState());
        String content=items.get(position).getContent();
        String forShow;

       if(content.length()>110){
           forShow=content.substring(0,110)+"....see more";
           holder.contents.setText(forShow);

       }else{
           holder.contents.setText(content);

       }
       holder.contents.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

                   holder.contents.setText(items.get(position).getContent());

           }
       });




    }




    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1000);
        view.startAnimation(anim);
    }

    // private final View.OnClickListener mOnClickListener = new MyOnClickListener();



//    @Override
//    public void onClick(final View view) {
//        int itemPosition = mRecyclerView.getChildLayoutPosition(view);
//        String item = mList.get(itemPosition);
//        Toast.makeText(mContext, item, Toast.LENGTH_LONG).show();
//    }


}
