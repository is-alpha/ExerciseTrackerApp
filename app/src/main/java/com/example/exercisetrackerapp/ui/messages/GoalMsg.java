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

public class GoalMsg extends IntentService {
    public static String mot0, mot1, mot2, mot3, mot4, mot5, mot6, mot7, mot8, mot9;
    public static final int not_id=1;
    DatabaseReference mDatabase;

    public  GoalMsg(){
        super("GoalsMsg");
        mDatabase= FirebaseDatabase.getInstance().getReference();
        mDatabase.child("messagesGoal").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    mot0= dataSnapshot.child("msg1").getValue().toString();
                    mot1= dataSnapshot.child("msg2").getValue().toString();
                    mot2= dataSnapshot.child("msg3").getValue().toString();
                    mot3= dataSnapshot.child("msg4").getValue().toString();
                    mot4= dataSnapshot.child("msg5").getValue().toString();
                    mot5= dataSnapshot.child("msg6").getValue().toString();
                    mot6= dataSnapshot.child("msg7").getValue().toString();
                    mot7= dataSnapshot.child("msg8").getValue().toString();
                    mot8= dataSnapshot.child("msg9").getValue().toString();
                    mot9= dataSnapshot.child("msg10").getValue().toString();
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

        String text = intent.getStringExtra(mot0);
        showText(text);

    }

    private void showText(final String text){

        NotificationCompat.Builder builder=
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.sym_def_app_icon)
                        .setContentTitle(getString(R.string.welcome))
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
