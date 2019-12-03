package com.example.exercisetrackerapp.ui.results;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.ui.results.AverageCalories.AverageCaloriesActivity;

public class ResultsFragment extends Fragment {

    private ResultsViewModel resultsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        resultsViewModel =
                ViewModelProviders.of(this).get(ResultsViewModel.class);
       // View root = inflater.inflate(R.layout.fragment_results, container, false);
        View root = inflater.inflate(R.layout.activity_results, container, false);

        /*final TextView textView = root.findViewById(R.id.text_slideshow);
        resultsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        Button btnBurnedC = (Button) root.findViewById(R.id.btnBurnedC);
        btnBurnedC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCalorieComparisonActivity();
            }
        });

        Button btnExerciseC = (Button) root.findViewById(R.id.btnExerciseC);
        btnExerciseC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCalorieCountExerciseActivity();
            }
        });

        Button btnAverageC = (Button) root.findViewById(R.id.btnAverageC);
        btnAverageC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchAverageCaloriesActivity();
            }
        });

        Button btnWeight = (Button) root.findViewById(R.id.btnWeight);
        btnWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchWeightLossActivity();
            }
        });

        Button btnIMC = (Button) root.findViewById(R.id.btnIMC);
        btnIMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCalculateIMCActivity();
            }
        });

        Button btnPerformance = (Button) root.findViewById(R.id.btnPerformance);
        btnPerformance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchPerformanceActivity();
            }
        });

        return root;
    }

    private void launchCalorieComparisonActivity() {
        Intent intent = new Intent(getActivity(), CalorieComparisonActivity.class);
        startActivity(intent);
    }

    private void launchCalorieCountExerciseActivity() {
        Intent intent = new Intent(getActivity(), CalorieCountExerciseActivity.class);
        startActivity(intent);
    }

    private void launchAverageCaloriesActivity() {
        Intent intent = new Intent(getActivity(), AverageCaloriesActivity.class);
        startActivity(intent);
    }

    private void launchWeightLossActivity() {
        Intent intent = new Intent(getActivity(), WeightLossActivity.class);
        startActivity(intent);
    }

    private void launchCalculateIMCActivity() {
        Intent intent = new Intent(getActivity(), CalculateIMCActivity.class);
        startActivity(intent);
    }

    private void launchPerformanceActivity() {
        Intent intent = new Intent(getActivity(), PerformanceActivity.class);
        startActivity(intent);
    }
}