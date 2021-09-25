package com.bitaam.gyani;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.bitaam.gyani.ui.blogs.BlogFragment;
import com.bitaam.gyani.ui.community.CommunityFragment;
import com.bitaam.gyani.ui.course.CourseFragment;
import com.bitaam.gyani.ui.mycourse.MyCourseFragment;
import com.bitaam.gyani.ui.post.PostFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView navView;
    Fragment active;
    boolean homeFlag = true;
    Toolbar toolbar;
    AlertDialog.Builder builder;
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser user;
    boolean logoutFlag = false;
    String msg="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        builder = new AlertDialog.Builder(this);

        toolbar = findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Gyani");

        navView = findViewById(R.id.nav_view);
        active  = new CourseFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment,active,"TAG").commit();
        navView.setSelectedItemId(R.id.navigation_course);

        onClickActivities();

    }

    private void onClickActivities(){

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = new CourseFragment();


                switch(item.getItemId()){

                    case R.id.navigation_course:
                        fragment = new CourseFragment();
                        homeFlag = true;
                        toolbar.setTitle("Gyani");
                        break;
                    case R.id.navigation_mycourse:
                        fragment = new MyCourseFragment();
                        homeFlag = false;
                        toolbar.setTitle("My Courses");
                        //if (interstitialAd.isLoaded())
                        //interstitialAd.show();
                        break;
                    case R.id.navigation_community:
                        fragment = new CommunityFragment();
                        homeFlag = false;
                        toolbar.setTitle("Community");
                        //if (interstitialAd.isLoaded())
                        //interstitialAd.show();
                        break;
                    case R.id.navigation_posts:
                        fragment = new PostFragment();
                        homeFlag = false;
                        toolbar.setTitle("Posts");
                        //if (interstitialAd.isLoaded())
                        //interstitialAd.show();
                        break;
                    case R.id.navigation_blogs:
                        fragment = new BlogFragment();
                        homeFlag = false;
                        toolbar.setTitle("Blogs");
                        //if (interstitialAd.isLoaded())
                        //interstitialAd.show();
                        break;

                }

//                Bundle bundle = new Bundle();
//                bundle.putSerializable("UserInfo",userModal);
//                //assert fragment != null;
//                fragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,fragment,"TAG").remove(active).commit();
                active = fragment;//.hide(active)
                return true;
            }
        });

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        navView.setSelectedItemId(R.id.navigation_course);

    }

    @Override
    public void onBackPressed() {

        if (homeFlag)
        {
            //if (interstitialAd.isLoaded())
            //   interstitialAd.show();
            finish();
        }else{
            navView.setSelectedItemId(R.id.navigation_course);
            //if (interstitialAd.isLoaded())
            //interstitialAd.show();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menumain,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.log_out)
        {
            AlertExit();
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }else if(id == R.id.profile){
            startActivity(new Intent(getApplicationContext(),CreateProfileActivity.class));
        }else if(id == R.id.share){
//            shareApp();
        }else if(id == R.id.feedback){
//            startActivity(new Intent(getApplicationContext(),FeedbackActivity.class));

        }

        return true;
    }

//    private void shareApp(){
//        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
//        intent.setType("text/plain");
//        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share MyUpchaar");
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("share");
//        reference.keepSynced(true);
//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                intent.putExtra(android.content.Intent.EXTRA_TEXT, snapshot.getValue(String.class));
//
//                startActivity(Intent.createChooser(intent, "Select app to share"));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//    }

    public void AlertExit(){

        builder.setMessage("Do you really want to Logout?").setTitle("Confirmation")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(user!=null){
                            mAuth.signOut();
                            finish();
                            startActivity(new Intent(getApplicationContext(),SignInActivity.class));
                        }

                    }
                }).setNegativeButton("Cancel",null).setCancelable(false);



    }

    private void progressDialogShow(String title,String Msg){

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(title);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(Msg);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //progressDialog.setMax(100);
        progressDialog.show();

    }




}