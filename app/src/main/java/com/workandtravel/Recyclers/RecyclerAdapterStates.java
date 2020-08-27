package com.workandtravel.Recyclers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.workandtravel.Classes.State;
import com.workandtravel.R;

import java.util.ArrayList;




public class RecyclerAdapterStates extends    RecyclerView.Adapter<RecyclerAdapterStates.viewitem> {



    ArrayList<State> items;
    Context context;

    public RecyclerAdapterStates(Context c, ArrayList<State> item)
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
        TextView name,tax;
        ImageView image;
        CardView cardView;


        //initialize
        public viewitem(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            image=itemView.findViewById(R.id.img);
            tax=itemView.findViewById(R.id.tax);
            cardView=itemView.findViewById(R.id.cv);




        }
    }



    //onCreateViewHolder used to HAndle on Clicks
    @Override
    public viewitem onCreateViewHolder(final ViewGroup parent, int viewType) {



        //the itemView now is the row
        //We will add 2 onClicks


        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row4, parent, false);






        return new viewitem(itemView);
    }


    //to fill each item with data from the array depending on position
    @Override
    public void onBindViewHolder(viewitem holder, final int position) {
        setFadeAnimation(holder.itemView);
        Glide.with(context).load(items.get(position).getImg()).into(holder.image);
        holder.name.setText(items.get(position).getName());
        holder.tax.setText(items.get(position).getTax());



    }




    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1500);
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
