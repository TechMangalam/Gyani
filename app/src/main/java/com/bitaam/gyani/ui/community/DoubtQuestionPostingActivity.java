package com.bitaam.gyani.ui.community;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bitaam.gyani.R;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;

public class DoubtQuestionPostingActivity extends AppCompatActivity {

    TextInputEditText questionEdt;
    ImageButton postQuestionBtn,attachImageBtn,cameraBtn,tagBtn,removeAttachedImageBtn;
    TextView rotateImgBtn;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doubt_question_posting);

        questionEdt = findViewById(R.id.post_text_text_input_field);
        attachImageBtn = findViewById(R.id.imageAttachImageBtn);
        cameraBtn = findViewById(R.id.cameraAttachImageBtn);
        tagBtn = findViewById(R.id.tagAttachImageBtn);
        postQuestionBtn = findViewById(R.id.post_question_btn);
        removeAttachedImageBtn = findViewById(R.id.removeAttachedImageBtn);
        rotateImgBtn = findViewById(R.id.rotateImageTv);

        onClickAndTouchEvents();


    }

    private void onClickAndTouchEvents() {

        attachImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1000);
            }
        });

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
//                    try {
//                        photoFile = createImageFile();
//                    } catch (IOException ex) {
//                        // Error occurred while creating the File
//                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
                                "com.bitaam.gyani.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, 1111);
                    }
                }
            }
        });

        tagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        removeAttachedImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        rotateImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        postQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}