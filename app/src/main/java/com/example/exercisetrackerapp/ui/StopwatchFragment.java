package com.example.exercisetrackerapp.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.exercisetrackerapp.R;
import java.util.Locale;

public class StopwatchFragment extends Fragment implements View.OnClickListener{
    private int seconds=0;
    private boolean isRunning;
    private boolean wasRunning;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            seconds = savedInstanceState.getInt("seconds");
            isRunning = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View layout = inflater.inflate(R.layout.fragment_stopwatch,container,false);
        runTimer(layout);
        Button stopButton = (Button)layout.findViewById(R.id.btnStop);
        Button startButton = (Button)layout.findViewById(R.id.btnStart);
        Button resetButton = (Button)layout.findViewById(R.id.btnEnd);
        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        return layout;
    }

    @Override
    public void onPause(){
        super.onPause();
        wasRunning=isRunning;
        isRunning=false;
    }

    @Override
    public void onResume(){
        super.onResume();
        if(wasRunning)
            isRunning=true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){

        savedInstanceState.putInt("seconds",seconds);
        savedInstanceState.putBoolean("running",isRunning);
        savedInstanceState.putBoolean("wasRunning",wasRunning);

    }

    private void onClickStart(){
        isRunning=true;
    }

    private void onClickStop(){
        isRunning=false;
    }

    private void onClickEnd(){

        isRunning=false;
        seconds=0;
    }

    public void runTimer(View view){

        final TextView timeView= (TextView)view.findViewById(R.id.time_view);
        final Handler handler= new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours=seconds/3600;
                int minutes=(seconds%3600)/60;
                int secs=seconds%60;
                String time = String.format(Locale.getDefault(),"%d:%02d:%02d",hours,minutes,secs);

                timeView.setText(time);

                if(isRunning)
                    seconds++;

                handler.postDelayed(this,1000);
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnStart:
                onClickStart();
                break;

            case R.id.btnStop:
                onClickStop();
                break;

            case R.id.btnEnd:
                onClickEnd();
                break;
        }

    }


}