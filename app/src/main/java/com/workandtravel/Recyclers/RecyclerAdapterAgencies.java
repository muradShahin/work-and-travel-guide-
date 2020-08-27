package com.workandtravel.Recyclers;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.workandtravel.Classes.Agencies;
import com.workandtravel.Classes.agencyIndex;
import com.workandtravel.Classes.CurrentAgency;
import com.workandtravel.R;

import java.util.ArrayList;


public class RecyclerAdapterAgencies extends    RecyclerView.Adapter<RecyclerAdapterAgencies.viewitem> {



    ArrayList<Agencies> items;
    Context context;

    public RecyclerAdapterAgencies(Context c, ArrayList<Agencies> item)
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
        TextView name;
        ImageView image;
        CardView cardView;



        //initialize
        public viewitem(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            image=itemView.findViewById(R.id.cover);
            cardView=itemView.findViewById(R.id.cv);





        }
    }



    //onCreateViewHolder used to HAndle on Clicks
    @Override
    public viewitem onCreateViewHolder(final ViewGroup parent, int viewType) {



        //the itemView now is the row
        //We will add 2 onClicks


        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row3, parent, false);






        return new viewitem(itemView);
    }


    //to fill each item with data from the array depending on position
    @Override
    public void onBindViewHolder(viewitem holder, final int position) {
        setFadeAnimation(holder.itemView);
        Glide.with(context).load(items.get(position).getImg()).into(holder.image);
        holder.name.setText(items.get(position).getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentAgency.name=items.get(position).getName();
                CurrentAgency.address=items.get(position).getAddress();
                CurrentAgency.facebookPage=items.get(position).getFacebookPage();
                CurrentAgency.img=items.get(position).getImg();
                CurrentAgency.overview=items.get(position).getOverview();
                CurrentAgency.phone=items.get(position).getPhone();
                CurrentAgency.loc1=items.get(position).getLoc1();
                CurrentAgency.loc2=items.get(position).getLoc2();
                agencyIndex.index=position;
                Navigation.findNavController(v).navigate(R.id.action_nav_tools_to_agency);
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
