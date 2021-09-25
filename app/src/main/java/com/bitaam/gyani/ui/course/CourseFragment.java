package com.bitaam.gyani.ui.course;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.bitaam.gyani.R;
import com.bitaam.gyani.adapters.TrendingCourseAdapter;
import com.bitaam.gyani.modals.CourseItemModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.Objects;

public class CourseFragment extends Fragment {

    RecyclerView trendingCourseRecycler,latestCourseRecycler,creativityCourseRecycler;
    CarouselView carouselView;
    View parentView;
    ScrollView homeScroll;
    ArrayList<String> infoImgUrls;

    SharedPreferences sharedPreferences;

    public CourseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course, container, false);

        trendingCourseRecycler = view.findViewById(R.id.trendingCourseRecycler);
        trendingCourseRecycler.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false));

        latestCourseRecycler = view.findViewById(R.id.recentCourseRecycler);
        latestCourseRecycler.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false));

        creativityCourseRecycler = view.findViewById(R.id.creativityCourseRecycler);
        creativityCourseRecycler.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false));


        infoImgUrls = new ArrayList<>();
        sharedPreferences = requireActivity().getSharedPreferences("HomeInfo", Context.MODE_PRIVATE);

        infoImgUrls.add(sharedPreferences.getString("img1","https://cdn.dnaindia.com/sites/default/files/styles/full/public/2019/10/04/873153-ayurveda-istock.jpg"));
        infoImgUrls.add(sharedPreferences.getString("img2","https://www.kindpng.com/picc/m/103-1032545_nature-of-patient-and-doctor-hd-png-download.png"));
        infoImgUrls.add(sharedPreferences.getString("img3","https://akm-img-a-in.tosshub.com/indiatoday/sunsetyoga-2_647_062115121022.jpg?Q7x3aPFYhLV6E2CgD7oXmSdjoh5wnAiq&size=1200:675"));
        infoImgUrls.add(sharedPreferences.getString("img4","https://innohealthmagazine.com/wp-content/uploads/2017/11/Ayurveda-Mothe-of-all-healings.jpg"));


        carouselView = view.findViewById(R.id.carouselView);
        carouselView.setPageCount(infoImgUrls.size());
        carouselView.setImageListener(imageListener);

        getHomeInfoCarouselData();


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        dataBaseTrendingCoursesUpdate();
    }

    public void dataBaseTrendingCoursesUpdate(){

        TrendingCourseAdapter trendingCourseAdapter = new TrendingCourseAdapter(trendingCourseRecycler,getContext(),new ArrayList<CourseItemModel>());
        TrendingCourseAdapter trendingCourseAdapter1 = new TrendingCourseAdapter(trendingCourseRecycler,getContext(),new ArrayList<CourseItemModel>());
        TrendingCourseAdapter trendingCourseAdapter2 = new TrendingCourseAdapter(trendingCourseRecycler,getContext(),new ArrayList<CourseItemModel>());

        //trendingCourses.setLayoutManager(gridLayoutManager);
        trendingCourseRecycler.setAdapter(trendingCourseAdapter);
        creativityCourseRecycler.setAdapter(trendingCourseAdapter2);
        latestCourseRecycler.setAdapter(trendingCourseAdapter1);

        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("MoreCourses");
        databaseReference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.hasChild("trending")){
                    CourseItemModel model = dataSnapshot.getValue(CourseItemModel.class);

                    //String courseId = dataSnapshot.getKey().toString();
                    ((TrendingCourseAdapter) Objects.requireNonNull(trendingCourseRecycler.getAdapter())).update(model);
                    ((TrendingCourseAdapter) Objects.requireNonNull(latestCourseRecycler.getAdapter())).update(model);
                    ((TrendingCourseAdapter) Objects.requireNonNull(creativityCourseRecycler.getAdapter())).update(model);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                String remKey = dataSnapshot.getKey();


            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void getHomeInfoCarouselData(){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("InfoImages");
        databaseReference.keepSynced(true);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    //Log.e("info", Objects.requireNonNull(dataSnapshot.getValue(String.class)));
                    infoImgUrls.add(Objects.requireNonNull(dataSnapshot.getValue(String.class)));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            Picasso.get().load(infoImgUrls.get(position)).into(imageView);

        }
    };

    @Override
    public void onPause() {
        super.onPause();
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("img1",infoImgUrls.get(4));
        editor.putString("img2",infoImgUrls.get(5));
        editor.putString("img3",infoImgUrls.get(6));
        editor.putString("img4",infoImgUrls.get(7));
        editor.apply();
    }


}