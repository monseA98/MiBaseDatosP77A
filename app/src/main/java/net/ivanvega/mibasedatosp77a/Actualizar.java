package net.ivanvega.mibasedatosp77a;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Actualizar extends AppCompatActivity implements View.OnClickListener {

    Contacto contacto;
    EditText txtUsuario;
    EditText txtEmail;
    EditText txtTel;
    EditText txtFecha;
    Button btnActualizar, btnRegre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar);

        contacto = (Contacto)getIntent().getExtras().getSerializable("contacto");

        txtUsuario = (EditText)findViewById(R.id.txtUsuario);
        txtUsuario.setText(contacto.getUsuario().toString());
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtEmail.setText(contacto.getEmail().toString());
        txtTel = (EditText)findViewById(R.id.txtTel);
        txtTel.setText(contacto.getTel().toString());

        txtFecha = findViewById(R.id.editText4);
        txtFecha.setVisibility(View.INVISIBLE);

        btnActualizar = (Button)findViewById(R.id.btnActualizar);
        btnActualizar.setOnClickListener(this);
        btnRegre = (Button)findViewById(R.id.btnRegresar);
        btnRegre.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if (view == btnActualizar) {
            contacto.setUsuario(txtUsuario.getText().toString());
            contacto.setEmail(txtEmail.getText().toString());
            contacto.setTel(txtTel.getText().toString());

            DAOContactos dao = new DAOContactos(this);

            switch (view.getId()) {
                case R.id.btnActualizar:
                    dao.update(contacto);
                    finish();
            }
        }
        if (view == btnRegre){
            finish();
        }
    }
}
