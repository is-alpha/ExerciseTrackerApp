package com.example.exercisetrackerapp.ui.home;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.ui.burnedCalories.BurnedCaloriesActivity;
import com.example.exercisetrackerapp.ui.profile.ProfileActivity;
import com.example.exercisetrackerapp.ui.sleep.RegisterSleepManualActivity;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    //Button botonCaloriasQuemadas;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        /*final TextView textView = root.findViewById(R.id.text_share);
        goalsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        ImageButton botonCaloriasQuemadas = (ImageButton) root.findViewById(R.id.botonCaloriasQuemadas);
        botonCaloriasQuemadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchBurnedCaloriesRegistry();
            }
        });

        ImageButton botonCaloriasQuemadas2 = (ImageButton) root.findViewById(R.id.botonCaloriasQuemadas2);
        botonCaloriasQuemadas2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchBurnedCaloriesRegistry();
            }
        });
        /*
        ImageButton botonRegistroSuenoM = (ImageButton) root.findViewById(R.id.botonRegistroSuenoM);
        botonRegistroSuenoM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchRegisterSleepManualActivity();
            }
        });
       */

        return root;
    }

    private void launchBurnedCaloriesRegistry() {
        Intent intent = new Intent(getActivity(), BurnedCaloriesActivity.class);
        startActivity(intent);
    }

    private void launchRegisterSleepManualActivity() {
        Intent intent = new Intent(getActivity(), RegisterSleepManualActivity.class);
        startActivity(intent);
    }
}