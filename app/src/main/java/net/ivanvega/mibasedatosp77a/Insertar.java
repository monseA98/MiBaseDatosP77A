package net.ivanvega.mibasedatosp77a;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class Insertar extends AppCompatActivity implements View.OnClickListener{

    Contacto contacto;
    private EditText etUsuario;
    private EditText etEmail;
    private EditText etTel;
    Button btnfecha, btninsertar, btnReg;
    EditText efecha;
    private int day, month, year;
    String m,d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar);

        etUsuario =  findViewById(R.id.txtUsuario);
        etEmail = findViewById(R.id.txtEmail);
        etTel = findViewById(R.id.txtTel);

        Button btnInsertar = (Button)findViewById(R.id.btnInsertar);
        btnInsertar.setOnClickListener(this);

        btnfecha = (Button) findViewById(R.id.btnFecha);
        btninsertar = (Button) findViewById(R.id.btnInsertar);
        btnReg = (Button) findViewById(R.id.btnRegresarI);
        efecha = (EditText) findViewById(R.id.txtFecha);

        //efecha.setEnabled(false);

        btnfecha.setOnClickListener(this);
        btnReg.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btninsertar){
            contacto = new Contacto(0, etUsuario.getText().toString(), etEmail.getText().toString(), etTel.getText().toString());

        DAOContactos dao = new DAOContactos(this);

            switch (view.getId()) {
                case R.id.btnInsertar:
                    dao.insert(contacto);
                    finish();
            }
        }
        if(view == btnfecha){
            final Calendar c = Calendar.getInstance();
            day = c.get(Calendar.DAY_OF_MONTH);
            month = c.get(Calendar.MONTH);
            year = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    if((month+1)<10){
                        m = "0"+""+(month+1);
                    }else{
                        m = ""+(month+1);
                    }
                    if(dayOfMonth<10){
                        d = "0"+""+dayOfMonth;
                    }else{
                        d= ""+dayOfMonth;
                    }
                    //efecha.setText(dayOfMonth+"/"+(month + 1)+"/"+year);
                    efecha.setText(year+"/"+m+"/"+d);
                }
            }
            ,day,month,year);
            datePickerDialog.show();
        }
        if(view == btnReg){
            finish();
        }
    }

}
