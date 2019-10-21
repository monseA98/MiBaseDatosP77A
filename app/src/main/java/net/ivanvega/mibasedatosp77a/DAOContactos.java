package net.ivanvega.mibasedatosp77a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DAOContactos {
    SQLiteDatabase _sqLiteDatabase;
    Context ctx;

    public DAOContactos(Context ctx) {
        this.ctx = ctx;
        _sqLiteDatabase =
                new MiDB(ctx).getWritableDatabase();
    }

    public long insert(Contacto contacto){
        ContentValues contentValues
                = new ContentValues();

        contentValues.put(MiDB.COLUMNS_NAME_CONTACTO[1],
                contacto.getUsuario());
        contentValues.put(MiDB.COLUMNS_NAME_CONTACTO[2],
                contacto.getEmail());
        contentValues.put(MiDB.COLUMNS_NAME_CONTACTO[3],
                contacto.getTel());

        return  _sqLiteDatabase.insert(MiDB.TABLE_NAME_CONTACTOS,
                null, contentValues);

    }

    public ArrayList<Contacto> getAll (){
        ArrayList<Contacto> lst = new ArrayList<Contacto>();
        String[] columnasAConsultar = {MiDB.COLUMNS_NAME_CONTACTO[0], MiDB.COLUMNS_NAME_CONTACTO[1], MiDB.COLUMNS_NAME_CONTACTO[2], MiDB.COLUMNS_NAME_CONTACTO[3]};
        Cursor c = _sqLiteDatabase.query(MiDB.TABLE_NAME_CONTACTOS,
                columnasAConsultar,
                null,
                null,
                null,
                null,
                null,
                null);

           if (c.moveToFirst() ){
               //lst = new ArrayList<Contacto>();
               do {
                  Contacto contacto =
                          new Contacto(c.getInt(0), c.getString(1),
                                  c.getString(2), c.getString(3));
                   lst.add(contacto);

               }while(c.moveToNext());
           }
           return  lst;

    }

    public Cursor getAllCursor (){


        Cursor c = _sqLiteDatabase.query(MiDB.TABLE_NAME_CONTACTOS,
                MiDB.COLUMNS_NAME_CONTACTO,
                null,
                null,
                null,
                null,
                null,
                null);


        return  c;

    }
    public Cursor getAllByUsuario(String criterio){

        Cursor c = _sqLiteDatabase.query(
                MiDB.TABLE_NAME_CONTACTOS,
                MiDB.COLUMNS_NAME_CONTACTO,
                "usuario like %?%",
                new String[]{criterio},
                null,
                null,null

        );

        return c;
    }

    public ArrayList<Contacto> buscarporNombre(String[] nombre){
        ArrayList<Contacto> contactos = new ArrayList<>();

        String[] columnasAConsultar = {MiDB.COLUMNS_NAME_CONTACTO[0], MiDB.COLUMNS_NAME_CONTACTO[1], MiDB.COLUMNS_NAME_CONTACTO[2], MiDB.COLUMNS_NAME_CONTACTO[3]};
        Cursor cursor = _sqLiteDatabase.query(MiDB.TABLE_NAME_CONTACTOS, columnasAConsultar, "usuario = ?", nombre, null, null, null);

        if(nombre[0].equals("")){

            cursor = _sqLiteDatabase.query(MiDB.TABLE_NAME_CONTACTOS, columnasAConsultar, null, null, null, null, null);

        }

        if (cursor == null){

            return contactos;

        }

        if (!cursor.moveToFirst()) return contactos;

        do {

            int idObtenidoDeBD = cursor.getInt(0);
            String usuarioObtenidoDeBD = cursor.getString(1);
            String emailObtenidoDeBD = cursor.getString(2);
            String telObtenidoDeBD = cursor.getString(3);

            Contacto contactoObtenidoDeBD = new Contacto(idObtenidoDeBD, usuarioObtenidoDeBD, emailObtenidoDeBD, telObtenidoDeBD);
            contactos.add(contactoObtenidoDeBD);

        } while (cursor.moveToNext());

        cursor.close();
        return contactos;

    }

    public int update (Contacto contacto){

        ContentValues valoresParaActualizar =  new ContentValues();
        valoresParaActualizar.put(MiDB.COLUMNS_NAME_CONTACTO[1], contacto.getUsuario());
        valoresParaActualizar.put(MiDB.COLUMNS_NAME_CONTACTO[2], contacto.getEmail());
        valoresParaActualizar.put(MiDB.COLUMNS_NAME_CONTACTO[3], contacto.getTel());
        String campoParaActualizar = "_id = ?";
        String[] argumentosParaActualizar = {String.valueOf(contacto.getId())};
        return _sqLiteDatabase.update(MiDB.TABLE_NAME_CONTACTOS, valoresParaActualizar, campoParaActualizar, argumentosParaActualizar);

    }

    public int eliminar (int id){

        String[] argumentos = {String.valueOf(id)};
        return _sqLiteDatabase.delete(MiDB.TABLE_NAME_CONTACTOS, "_id = ?", argumentos);

    }

}
