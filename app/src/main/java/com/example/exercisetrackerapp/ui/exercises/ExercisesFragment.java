package com.example.exercisetrackerapp.ui.exercises;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.exercisetrackerapp.R;

public class ExercisesFragment extends Fragment {

    private ExercisesViewModel exercisesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        exercisesViewModel =
                ViewModelProviders.of(this).get(ExercisesViewModel.class);
       // View root = inflater.inflate(R.layout.fragment_exercises, container, false);
        View root = inflater.inflate(R.layout.activity_mark_exercises, container, false);
       /* final TextView textView = root.findViewById(R.id.text_gallery);
        exercisesViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }
}