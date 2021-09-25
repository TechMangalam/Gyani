package com.bitaam.gyani.adapters;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bitaam.gyani.modals.CourseItemModel;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import com.bitaam.gyani.R;


public class TrendingCourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    RecyclerView recyclerView;
    ArrayList<CourseItemModel> courseItemModels = new ArrayList<>();
    Context context;


    public TrendingCourseAdapter(RecyclerView recyclerView, Context context, ArrayList<CourseItemModel> courseItemModels) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.courseItemModels = courseItemModels;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        //View view = LayoutInflater.from(context).inflate(R.layout.more_course_item,parent,false);
        if (viewType == 1){
            View view = LayoutInflater.from(context).inflate(R.layout.trending_course_item,parent,false);
            return new ViewHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.trending_course_item,parent,false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder.getItemViewType() == 0){
            //ViewHolderAds viewHolderAds = (ViewHolderAds)holder;
            //viewHolderAds.recyclerAds.loadAd(adRequest);
        }else{
            ViewHolder viewHolder = (ViewHolder)holder;
            viewHolder.tipsImg.setImageURI(Uri.parse(courseItemModels.get(position).getUrl()));
            viewHolder.cTitle.setText(courseItemModels.get(position).getTitle());
            viewHolder.cTopic.setText(courseItemModels.get(position).getTopic());
            viewHolder.cOrg.setText(courseItemModels.get(position).getOrganisation());
            viewHolder.cAuthor.setText(courseItemModels.get(position).getAuthor());
        }


    }

    @Override
    public int getItemViewType(int position) {
        //if (position%4==0){
        // return 0;
        //}
        return 1;
    }

    @Override
    public int getItemCount() {
        return courseItemModels.size();
    }

    public void update(CourseItemModel mod){

        courseItemModels.add(mod);
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        SimpleDraweeView tipsImg;
        TextView cTitle,cTopic,cOrg,cAuthor;

        ArrayList<String> bundles = new ArrayList<>();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tipsImg= itemView.findViewById(R.id.tipsImg);
            cTitle = itemView.findViewById(R.id.course_title);
            cTopic = itemView.findViewById(R.id.course_topic);
            cOrg = itemView.findViewById(R.id.organisation);
            cAuthor = itemView.findViewById(R.id.course_Author);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    int position = recyclerView.getChildLayoutPosition(v);
//                    Intent intent = new Intent(context, UserCreatedCourseActivity.class);
//                    intent.putExtra("courseId",courseItemModels.get(position).getCourseId());
//                    intent.putExtra("authId",courseItemModels.get(position).getAuthId());
//
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(intent);

                }
            });


        }


    }

}

