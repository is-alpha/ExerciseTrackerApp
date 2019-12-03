package com.example.exercisetrackerapp.ui.exerciseRoutine;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exercisetrackerapp.R;
import com.example.exercisetrackerapp.data.model.Date;
import com.example.exercisetrackerapp.data.model.Exercise;
import com.example.exercisetrackerapp.data.model.Type;
import com.example.exercisetrackerapp.ui.home.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


//--------------------------------------------------
//FALTA VALIDAR LOS RANGOS DE FECHA Y GUARDAR EN BD
//--------------------------------------------------

public class ExerciseRoutineFragment extends Fragment implements DatePickerDialog.OnDateSetListener, View.OnClickListener{

    List<Exercise> lstExercise;
    private EditText tiempo;
    private Date date1, date2;
    private TextView fechaInicio, fechaFinal;
    private Button mBtCancelar,mRegistrar;
    private int idtext, idDate;
    private ImageView btn_calendar, btn_calendar2;
    private String currentDateString = " ";
    private String exerciseName;
    private RecyclerViewAdapter myAdapter;
    private String email="",uid, id;
    private RecyclerView recyclerView;
    private ViewGroup container;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); //user reference


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_exercise, container, false);

        this.container = container;
        // user sesion
        inicializarFirebase();

        if (user != null) {
            email = user.getEmail();
            uid = user.getUid();
        }
        id = databaseReference.push().getKey();

        // 1. get a reference to recyclerView
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_id);
        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // Data from recycler view
        lstExercise = new ArrayList<>();
        lstExercise.add(new Exercise("Correr", Type.RUNNING,R.drawable.correr));
        lstExercise.add(new Exercise("Fútbol", Type.FOOTBALL,R.drawable.futbol));
        lstExercise.add(new Exercise("Natación", Type.SWIMMING,R.drawable.natacion));
        lstExercise.add(new Exercise("Abdominales", Type.ABS,R.drawable.img_abdominales));
        lstExercise.add(new Exercise("Yoga", Type.YOGA,R.drawable.yoga));
        lstExercise.add(new Exercise("Ciclismo", Type.BICYCLING,R.drawable.bicicleta));
        lstExercise.add(new Exercise("Correr", Type.RUNNING,R.drawable.correr));
        lstExercise.add(new Exercise("Fútbol", Type.FOOTBALL,R.drawable.futbol));
        lstExercise.add(new Exercise("Natación", Type.SWIMMING,R.drawable.natacion));
        lstExercise.add(new Exercise("Abdominales", Type.ABS,R.drawable.img_abdominales));
        lstExercise.add(new Exercise("Yoga", Type.YOGA,R.drawable.yoga));
        lstExercise.add(new Exercise("Ciclismo", Type.BICYCLING,R.drawable.bicicleta));

        // 3. create an adapter
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(lstExercise, adapterInterface);
        // 4. set adapter
        recyclerView.setLayoutManager(new GridLayoutManager(container.getContext(), 3));
        recyclerView.setAdapter(myAdapter);

        fechaInicio = (TextView) rootView.findViewById(R.id.textViewDate);
        fechaFinal = (TextView) rootView.findViewById(R.id.textViewDate2);
        tiempo = (EditText) rootView.findViewById(R.id.tiempo);

        mBtCancelar = (Button) rootView.findViewById(R.id.botonCancelar);
        mBtCancelar.setOnClickListener(this);

        mRegistrar = rootView.findViewById(R.id.botonGuardar);
        mRegistrar.setOnClickListener(this);

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
        currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        TextView textView = (TextView) getView().findViewById(idtext);
        textView.setText(currentDateString);
        if(idDate==1)
            date1 = new Date(dayOfMonth,month,year);
        else
            date2 = new Date(dayOfMonth,month,year);
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;

        switch (v.getId()) {
            case R.id.botonCalendario:
                idtext = R.id.textViewDate;
                idDate = 1;
                showDatePicker();
                break;
            case R.id.botonCalendario2:
                idtext = R.id.textViewDate2;
                idDate = 2;
                showDatePicker();
                break;
            case R.id.botonGuardar:
                recordExerciseRoutine();
                break;
            case R.id.botonCancelar:
                //getActivity().finish();
                fragment = new HomeFragment();
                replaceFragment(fragment);
                break;
        }
    }

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /* Set Up Current Date Into dialog */
        //CalendarPickerView
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);

        /*Set Call back to capture selected date*/
        //date.setCallBack(ondateSet);
        date.setCallBack(this);
        date.show(getFragmentManager(), "Date Picker");
        //date.setM
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void inicializarFirebase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void recordExerciseRoutine(){

        String sStartDate = fechaInicio.getText().toString();
        String sFinalDate = fechaFinal.getText().toString();
        String time = tiempo.getText().toString();
        //String sSelectedExercise = "";//myAdapter.getExcersice();

        if(validation(sStartDate,sFinalDate,exerciseName,time)==1) {
            //String usuario, int tiempo, Date dInicial, Date dFinal, String ejercicio)
            ExerciseRoutine exerciseRoutine = new ExerciseRoutine(email, Integer.parseInt(time), date1, date2, exerciseName);
            //Toast.makeText(getActivity(), "fecha: "+ sStartDate+ " - " + sFinalDate + "->" + exerciseName + "tiempo : " + time, Toast.LENGTH_LONG).show();
            databaseReference.child("rutinas").child(id).setValue(exerciseRoutine);
            limpiarTextBox();
            Toast.makeText(getActivity(), "Registro Exitoso! ", Toast.LENGTH_LONG).show();
        }
    }

    private void limpiarTextBox(){
        tiempo.setText("");
        fechaInicio.setText("");
        fechaFinal.setText("");

        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(lstExercise, adapterInterface);
        recyclerView.setLayoutManager(new GridLayoutManager(container.getContext(), 3));
        recyclerView.setAdapter(myAdapter);
    }

    // Interface declared inside your adapter.
    RecyclerViewAdapter.InfoAdapterInterface adapterInterface = new RecyclerViewAdapter.InfoAdapterInterface() {
        @Override
        public void OnItemClicked(String exercise) {
            // Do whatever you wants to do with this data that is coming from your adapter
            exerciseName = exercise;
        }
    };

    private int validation(String sStartDate,String sFinalDate, String exercise, String time){
        if(TextUtils.isEmpty(sStartDate)){
            Toast.makeText(getActivity(),"Ingrese Fecha de Inicio",Toast.LENGTH_LONG).show();
            return 0;
        }
        if(TextUtils.isEmpty(sFinalDate)){
            Toast.makeText(getActivity(),"Ingrese Fecha de Finalización",Toast.LENGTH_LONG).show();
            return 0;
        }
        if(TextUtils.isEmpty(exercise)){
            Toast.makeText(getActivity(),"Seleccione ejercicio",Toast.LENGTH_LONG).show();
            return 0;
        }
        if(TextUtils.isEmpty(time)){
            Toast.makeText(getActivity(),"Ingrese Tiempo",Toast.LENGTH_LONG).show();
            return 0;
        }
        return 1;
    }
}
