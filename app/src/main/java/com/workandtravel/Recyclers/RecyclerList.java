package com.workandtravel.Recyclers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.workandtravel.Classes.DoList;
import com.workandtravel.R;

import java.util.ArrayList;

import io.paperdb.Paper;


public class RecyclerList extends    RecyclerView.Adapter<RecyclerList.viewitem> {



    ArrayList<DoList> items;
    Context context;
    CheckBox checkBox;
    public RecyclerList(Context c, ArrayList<DoList> item)
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
        TextView title,text1,text2;



        //initialize
        public viewitem(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.taskName);
            text1=itemView.findViewById(R.id.t1);
            text2=itemView.findViewById(R.id.t2);
            checkBox=itemView.findViewById(R.id.checkBox);




        }
    }



    //onCreateViewHolder used to HAndle on Clicks
    @Override
    public viewitem onCreateViewHolder(final ViewGroup parent, int viewType) {



        //the itemView now is the row
        //We will add 2 onClicks


        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row5, parent, false);






        return new viewitem(itemView);
    }


    //to fill each item with data from the array depending on position
    @Override
    public void onBindViewHolder(viewitem holder, final int position) {
        holder.title.setText(items.get(position).getTitle());
        holder.text1.setText(items.get(position).getText1());
        holder.text2.setText(items.get(position).getText2());
        Paper.init(context);
        try {
            if (Paper.book().read(position + "")) {
                checkBox.setChecked(true);

            } else {
                checkBox.setChecked(false);
            }
        }catch (Exception e){
            Paper.book().write(position+"",false);

        }
        //.......
      checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              if(buttonView.isChecked()){
                  Paper.book().write(position+"",true);


              }else{
                  Paper.book().write(position+"",false);
              }
          }
      });


    }




    @Override
    public int getItemCount() {
        return items.size();
    }



    // private final View.OnClickListener mOnClickListener = new MyOnClickListener();



//    @Override
//    public void onClick(final View view) {
//        int itemPosition = mRecyclerView.getChildLayoutPosition(view);
//        String item = mList.get(itemPosition);
//        Toast.makeText(mContext, item, Toast.LENGTH_LONG).show();
//    }


}
