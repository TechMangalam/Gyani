package com.bitaam.gyani;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.firebase.database.FirebaseDatabase;

public class Gyani extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
