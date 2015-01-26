package com.dam.datos;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;


public class MainActivity extends ListActivity {
    private SQLiteDatabase mDB = null;
    private DataBaseHelper mDbHelper;
    private SimpleCursorAdapter mAdapter;

    private static final String TAG = "Datos";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // CReamos un nuevo DataBase
        mDbHelper = new DataBaseHelper(this);
        // Abrimos la DB para su escritura
        mDB = mDbHelper.getWritableDatabase();

        // borramos todos los registros para nuestro ejemplo
        clearAll();
        // reseteamos los registros
        insertArtists();

        //Leemos los registros
        Cursor c = readArtists();
        int columnas_layout[]={ R.id._id, R.id.nombre };
        mAdapter = new SimpleCursorAdapter(this, R.layout.list_layout, c,
                DataBaseHelper.columns,columnas_layout ,
               0);
        //Los mostramos por pantalla
        setListAdapter(mAdapter);

        //Insertamos las acciones del boton
        Button fixButton = (Button) findViewById(R.id.fix_button);
        fixButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // arreglamos los campos erroneos
                Log.v(TAG, "Arreglando");
                fix();
                Log.v(TAG, "Actualizando");
                // Repintamos los datos
                mAdapter.notifyDataSetChanged();
            }
        });

    }



    // Borrar todos los registros
    private void clearAll() {
        mDB.delete(DataBaseHelper.TABLE_NAME, null, null);
    }

    // Insert several artist records
    private void insertArtists() {

        ContentValues values = new ContentValues();

        values.put(DataBaseHelper.ARTIST_NAME, "Frank Sinatra");
        mDB.insert(DataBaseHelper.TABLE_NAME, null, values);
        values.clear();

        values.put(DataBaseHelper.ARTIST_NAME, "Lady Gaga");
        mDB.insert(DataBaseHelper.TABLE_NAME, null, values);
        values.clear();

        values.put(DataBaseHelper.ARTIST_NAME, "Jawny Cash");
        mDB.insert(DataBaseHelper.TABLE_NAME, null, values);
        values.clear();

        values.put(DataBaseHelper.ARTIST_NAME, "Ludwig von Beethoven");
        mDB.insert(DataBaseHelper.TABLE_NAME, null, values);
    }

    // Devuelve los registros de la base de datos
    private Cursor readArtists() {
        return mDB.query(DataBaseHelper.TABLE_NAME,
                DataBaseHelper.columns, null, new String[] {}, null, null,
                null);
    }

    // Modify the contents of the database
    private void fix() {

        // Borrando
        mDB.delete(DataBaseHelper.TABLE_NAME,
                DataBaseHelper.ARTIST_NAME + "=?",
                new String[] { "Lady Gaga" });

        // Actualizando
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.ARTIST_NAME, "Johnny Cash");

        mDB.update(DataBaseHelper.TABLE_NAME, values,
                DataBaseHelper.ARTIST_NAME + "=?",
                new String[] { "Jawny Cash" });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    // Close database
    @Override
    protected void onDestroy() {

        mDB.close();
        mDbHelper.deleteDatabase();

        super.onDestroy();

    }
}
