package net.ivanvega.mibasedatosp77a;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.SpannableString;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnCreateContextMenuListener {
    ListView lv;
    Button boton;
    Button botonRegresar;
    ArrayAdapter<Contacto> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DAOContactos dao = new DAOContactos(this);
        dao.insert(new Contacto(0, "perronegro",
                "perronegro@","445"));
        dao.insert(new Contacto(0, "perroblanco",
                "perroblanco@","544"));
         for (Contacto c : dao.getAll()){
             Toast.makeText(this,
                     c.usuario,
                     Toast.LENGTH_SHORT).show();
         }

         lv = findViewById(R.id.lv);


        String[] usuario = {""};

        ArrayList<Contacto> contactos = dao.buscarporNombre(usuario);

        adapter = new ArrayAdapter<Contacto>(this, android.R.layout.simple_list_item_1, contactos);

        lv.setAdapter(adapter);

        boton = (Button) findViewById(R.id.btnBuscar);

        boton.setOnClickListener(this);

        botonRegresar = (Button) findViewById(R.id.btn_Regresar);

        botonRegresar.setOnClickListener(this);

        registerForContextMenu(lv);


    }

    @Override
    protected void onResume() {
        super.onResume();

        DAOContactos dao1 = new DAOContactos(this);

        String[] usuario1 = {""};

        ArrayList<Contacto> contactos = dao1.buscarporNombre(usuario1);

        adapter = new ArrayAdapter<Contacto>(this, android.R.layout.simple_list_item_1, contactos);

        lv.setAdapter(adapter);

    }

    @Override
    public void onClick(View view){

        switch (view.getId()){

            case R.id.btnBuscar:
                EditText text = (EditText)findViewById(R.id.txtUsuario);
                String[] usuario = {text.getText().toString()};

                DAOContactos dao = new DAOContactos(this);

                ArrayList<Contacto> contactos = dao.buscarporNombre(usuario);

                adapter = new ArrayAdapter<Contacto>(this, android.R.layout.simple_list_item_1, contactos);

                lv.setAdapter(adapter);
                break;
            case R.id.btn_Regresar:
                text = (EditText)findViewById(R.id.txtUsuario);
                text.setText("");

                DAOContactos dao1 = new DAOContactos(this);

                String[] usuario1 = {""};

                contactos = dao1.buscarporNombre(usuario1);

                adapter = new ArrayAdapter<Contacto>(this, android.R.layout.simple_list_item_1, contactos);

                lv.setAdapter(adapter);
                break;
            case R.id.btnInsert:
                Intent intentw = new Intent(MainActivity.this, Insertar.class);
                startActivity(intentw);

        }

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View lv, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, lv, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    public boolean onContextItemSelected(MenuItem item){

        DAOContactos dao = new DAOContactos(this);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        lv = findViewById(R.id.lv);

        Contacto contacto = (Contacto) lv.getItemAtPosition(info.position);

        switch (item.getItemId()) {
            case R.id.eliminar:
                dao.eliminar(contacto.getId());

                EditText text = (EditText)findViewById(R.id.txtUsuario);
                String[] usuario = {text.getText().toString()};

                ArrayList<Contacto> contactos = dao.buscarporNombre(usuario);

                adapter = new ArrayAdapter<Contacto>(this, android.R.layout.simple_list_item_1, contactos);

                lv.setAdapter(adapter);
                return true;
            case R.id.actualizar:
                Intent intent = new Intent(MainActivity.this, Actualizar.class);
                intent.putExtra("contacto", contacto);
                startActivity(intent);
            default:
                return super.onContextItemSelected(item);
        }
    }

}
