package com.example.exercisetrackerapp.ui.exerciseRoutine;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.data.model.Date;
import com.example.exercisetrackerapp.data.model.Exercise;
import com.example.exercisetrackerapp.data.model.Type;
import com.example.exercisetrackerapp.ui.home.HomeFragment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ExerciseRoutineFragment extends Fragment implements DatePickerDialog.OnDateSetListener, View.OnClickListener{
    List<Exercise> lstExercise;
    Date date;
    private EditText tiempo;
    private TextView fechaInicio;
    private TextView fechaFinal;
    private Button mBtCancelar;
    private Button mRegistrar;
    ImageButton button;
    ImageView btn_calendar;
    ImageView btn_calendar2;

    //-------------------------------------------
    //FALTA CONECTAR A LA BASE DE DATOS Y GUARDAR
    //-------------------------------------------

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_exercise, container, false);

        // 1. get a reference to recyclerView
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_id);
        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Data from recycler view
        lstExercise = new ArrayList<>();
        lstExercise.add(new Exercise("Correr", Type.RUNNING,R.drawable.correr));
        lstExercise.add(new Exercise("Abdominales", Type.FOOTBALL,R.drawable.futbol));
        lstExercise.add(new Exercise("Correr", Type.SWIMMING,R.drawable.natacion));
        lstExercise.add(new Exercise("Correr", Type.RUNNING,R.drawable.correr));
        lstExercise.add(new Exercise("Abdominales", Type.FOOTBALL,R.drawable.futbol));
        lstExercise.add(new Exercise("Correr", Type.SWIMMING,R.drawable.natacion));
        lstExercise.add(new Exercise("Correr", Type.RUNNING,R.drawable.correr));
        lstExercise.add(new Exercise("Abdominales", Type.FOOTBALL,R.drawable.futbol));
        lstExercise.add(new Exercise("Correr", Type.SWIMMING,R.drawable.natacion));
        lstExercise.add(new Exercise("Correr", Type.RUNNING,R.drawable.correr));
        lstExercise.add(new Exercise("Abdominales", Type.FOOTBALL,R.drawable.futbol));
        lstExercise.add(new Exercise("Correr", Type.SWIMMING,R.drawable.natacion));

        // 3. create an adapter
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(lstExercise);
        // 4. set adapter
        recyclerView.setLayoutManager(new GridLayoutManager(container.getContext(), 3));
        recyclerView.setAdapter(myAdapter);


        fechaInicio = (TextView) rootView.findViewById(R.id.textViewDate);
        fechaFinal = (TextView) rootView.findViewById(R.id.textViewDate2);

        mBtCancelar = (Button) rootView.findViewById(R.id.botonCancelar);
        mBtCancelar.setOnClickListener(this);

        btn_calendar = (ImageView) rootView.findViewById(R.id.botonCalendario);
        btn_calendar.setOnClickListener(this);

        btn_calendar2 = (ImageView) rootView.findViewById(R.id.botonCalendario2);
        btn_calendar2.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        TextView textView = (TextView) getView().findViewById(R.id.textViewDate);
        textView.setText(currentDateString);
        date = new Date(dayOfMonth,month,year);
    }

    private void limpiarTextBox(){
        tiempo.setText("");
        fechaInicio.setText("");
        fechaFinal.setText("");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.botonCalendario:
            case R.id.botonCalendario2:
                showDatePicker();
                break;
            case R.id.botonCancelar:
                //getActivity().finish();
                Fragment fragment = null;
                fragment = new HomeFragment();
                replaceFragment(fragment);
                break;
        }
    }

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /* Set Up Current Date Into dialog */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);

        /*Set Call back to capture selected date*/
        //date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
