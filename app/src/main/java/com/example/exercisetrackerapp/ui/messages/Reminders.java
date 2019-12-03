package com.example.exercisetrackerapp.ui.messages;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.ui.exerciseRoutine.TrackExerciseActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Reminders extends IntentService {

    public static String msg0, msg1, msg2, msg3, msg4, msg5;
    public static final int not_id=0;
    DatabaseReference mDatabase;



    public  Reminders(){
        super("Reminders");

        mDatabase= FirebaseDatabase.getInstance().getReference();
        mDatabase.child("reminders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    msg0= dataSnapshot.child("rem1").getValue().toString();
                    msg1= dataSnapshot.child("rem2").getValue().toString();
                    msg2= dataSnapshot.child("rem3").getValue().toString();
                    msg3= dataSnapshot.child("rem4").getValue().toString();
                    msg4= dataSnapshot.child("rem5").getValue().toString();
                    msg5= dataSnapshot.child("rem6").getValue().toString();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        synchronized (this){

            try {
                wait(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        String text = intent.getStringExtra(msg0);
        showText(text);

    }

    private void showText(final String text){

      NotificationCompat.Builder builder=
                new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setContentTitle("Recordatorio ExerciseTracker")
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[]{0,100})
                .setAutoCancel(true);

        Intent actionIntent = new Intent(this, TrackExerciseActivity.class);
        PendingIntent actionPendingIntent=PendingIntent.getActivity(this,0,actionIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(actionPendingIntent);

        NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(not_id,builder.build());

    }
}
