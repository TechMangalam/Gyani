package com.bitaam.gyani;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitaam.gyani.modals.ProfileModal;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreateProfileActivity extends AppCompatActivity {

    AutoCompleteTextView genderExposedDropdown,interest1ExposedDropdown,interest2ExposedDropdown,interest3ExposedDropdown;
    TextInputEditText bioEdt,nameEdt,ageEdt;
    SimpleDraweeView rotateImgView,changeImgView;
    ImageView profileImageCiv;
    Button submitBtn,addLocationBtn;
    Uri profileImageUri;
    byte[] filesInBytes;
    Bitmap qImg;
    String imgLink="na";
    ProgressDialog progressDialog;
    StorageReference storageReference;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        String[] type = new String[] {"Male", "Female", "Others"};
        String[] hobbies = new String[] {"Science","Mathematics","Commerce","Technology","Programming","Politics","Crypto","Share Market","Android Development","Web Development","Entertainment","Music", "Dance", "Cricket"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, type);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, hobbies);


        genderExposedDropdown = findViewById(R.id.filled_exposed_dropdown);
        genderExposedDropdown.setAdapter(adapter);
        interest1ExposedDropdown = findViewById(R.id.hobby1_input);
        interest1ExposedDropdown.setAdapter(adapter1);
        interest2ExposedDropdown = findViewById(R.id.hobby2_input);
        interest2ExposedDropdown.setAdapter(adapter1);
        interest3ExposedDropdown = findViewById(R.id.hobby3_input);
        interest3ExposedDropdown.setAdapter(adapter1);

        nameEdt = findViewById(R.id.name_input);
        ageEdt = findViewById(R.id.age_input);
        bioEdt = findViewById(R.id.bio_input);
        rotateImgView = findViewById(R.id.photo_rotate_icon);
        changeImgView = findViewById(R.id.photo_selector_icon);
        profileImageCiv = findViewById(R.id.profile_image);
        submitBtn = findViewById(R.id.submitBtn);
        addLocationBtn = findViewById(R.id.addLocationBtn);

        storageReference = FirebaseStorage.getInstance().getReference("UserProfileImages");
        firebaseFirestore = FirebaseFirestore.getInstance();

        onClickActivities();

    }

    private void onClickActivities() {

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImageToFirebase(filesInBytes);
                progressDialogShow("Creating Profile","Please wait...");
            }
        });

        changeImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1000);
                submitBtn.setEnabled(false);
            }
        });

        rotateImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qImg = rotateImage(qImg,90);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                assert data != null;
                Uri imgUri = data.getData();
                profileImageUri = imgUri;
                Bitmap bmp = null;
                try{
                    bmp = MediaStore.Images.Media.getBitmap(getContentResolver(),imgUri);
                    qImg = bmp;
                }catch (Exception e){}
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                bmp.compress(Bitmap.CompressFormat.JPEG,25,baos);
//                byte[] fileInBytes = baos.toByteArray();

                filesInBytes = compressImg(qImg);
                rotateImgView.setVisibility(View.VISIBLE);
                profileImageCiv.setImageURI(profileImageUri);
                submitBtn.setEnabled(true);

                //uploadImageToFirebase(fileInBytes);
            }
        }

    }

    private Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        filesInBytes = null;
        filesInBytes = compressImg(rotatedImg);
        profileImageCiv.setImageBitmap(rotatedImg);
        return rotatedImg;
    }

    private byte[] compressImg(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,25,baos);
        byte[] fileInBytes = baos.toByteArray();
        //filesInByte = baos.toByteArray();
        return fileInBytes;
    }

    private void uploadImageToFirebase(byte[] fileInBytes) {

        final StorageReference fileRef = storageReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()+".jpg");
        fileRef.putBytes(fileInBytes).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(),"Image Uploaded",Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        profileImageUri = uri;
                        imgLink = uri.toString();
                        rotateImgView.setVisibility(View.GONE);
                        getDataAndUpload();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Failed Uploading Image",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void getDataAndUpload() {

        ProfileModal modal = new ProfileModal();
        String authId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        String poDat = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
        modal.setProfileDate(poDat);
        modal.setLocation("na");
        modal.setType(ProfileModal.TYPE_NORMAL_USER);

        String name,bio,interest1,interest2,interest3,gender;
        List<String> interests = new ArrayList<>();
        int age;

        name = Objects.requireNonNull(nameEdt.getText()).toString();
        age = Integer.parseInt(Objects.requireNonNull(ageEdt.getText()).toString());
        bio = Objects.requireNonNull(bioEdt.getText()).toString();
        gender = Objects.requireNonNull(genderExposedDropdown.getText()).toString();
        interest1 = Objects.requireNonNull(interest1ExposedDropdown.getText()).toString();
        interest2 = Objects.requireNonNull(interest2ExposedDropdown.getText()).toString();
        interest3 = Objects.requireNonNull(interest3ExposedDropdown.getText()).toString();
        
        if(profileImageUri == null){
            Toast.makeText(getApplicationContext(), "Select profile image", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
            return;
        }

        if (name.isEmpty()){
            nameEdt.setError("Enter name");
            nameEdt.requestFocus();
            progressDialog.dismiss();
            return;
        }else {
            modal.setName(name);
        }
        if (age == 0){
            ageEdt.setError("Enter age");
            ageEdt.requestFocus();
            progressDialog.dismiss();
            return;
        }else {
            modal.setAge(age);
        }
        if (bio.isEmpty()){
            bioEdt.setError("Enter bio");
            bioEdt.requestFocus();
            progressDialog.dismiss();
            return;
        }else {
            modal.setBio(bio);
        }
        if (gender.isEmpty()){
            genderExposedDropdown.setError("Enter gender");
            genderExposedDropdown.requestFocus();
            progressDialog.dismiss();
            return;
        }else {
            modal.setGender(gender);
        }
        if (interest1.isEmpty()){
            interest1ExposedDropdown.setError("Enter 1st interest");
            interest1ExposedDropdown.requestFocus();
            progressDialog.dismiss();
            return;
        }else {
            interests.add(interest1);
        }
        if (interest2.isEmpty()){
            interest2ExposedDropdown.setError("Enter 2nd hobby");
            interest2ExposedDropdown.requestFocus();
            progressDialog.dismiss();
            return;
        }else {
            interests.add(interest2);
        }
        if (interest3.isEmpty()){
            interest3ExposedDropdown.setError("Enter 3rd hobby");
            interest3ExposedDropdown.requestFocus();
            progressDialog.dismiss();
            return;
        }else {
            interests.add(interest3);
            modal.setInterests(interests);
        }

        uploadToFirestoreDatabase(modal,authId);


    }


    private void uploadToFirestoreDatabase(ProfileModal modal, String uid) {

        firebaseFirestore.collection("Users").document(uid).set(modal).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(@NonNull Void unused) {
                updateProfile(modal.getName(),profileImageUri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(CreateProfileActivity.this, "Some error occurred try again", Toast.LENGTH_SHORT).show();
            }
        });

    }
    
    private void updateProfile(String name,Uri profileImageUri){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name).setPhotoUri(profileImageUri).build();

        assert user != null;
        user.updateProfile(profileUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                setUserExistence(user.getUid(),user.getEmail());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();

            }
        });
        
    }

    private void setUserExistence(String uid,String email){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("RegisteredUsers");
        databaseReference.child(uid).setValue(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(@NonNull Void unused) {
                progressDialog.dismiss();
                Toast.makeText(CreateProfileActivity.this, "Profile Created Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });

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