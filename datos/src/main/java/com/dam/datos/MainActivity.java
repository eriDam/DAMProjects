package com.dam.datos;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class MainActivity extends ListActivity {

    private DataBaseHelper mDbHelper;
    private SQLiteDatabase db;
    private SimpleCursorAdapter mAdapter;

    private static final String TAG = "Datos";

    public static final String C_MODO  = "modo" ;
    public static final int C_VISUALIZAR = 551 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Creamos una nueva DataBase
        mDbHelper = new DataBaseHelper(this);
        db = mDbHelper.getWritableDatabase();


        //Leemos la base de datos y mostramos la informacion
        Cursor c = mDbHelper.readArtistas(db);
        mAdapter = new SimpleCursorAdapter(this, R.layout.list_layout, c,
                DataBaseHelper.columns, new int[] { R.id._id, R.id.nombre },
                0);
        setListAdapter(mAdapter);

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

        mDbHelper.deleteDatabase();
        super.onDestroy();

    }

    //Visualizar un dato en Formulario
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        // Llamamos a la Actividad Formulario indicando el modo visualización y el identificador del registro
        Intent i = new Intent(MainActivity.this, Forumlario.class);
        i.putExtra(C_MODO, C_VISUALIZAR);
        i.putExtra(mDbHelper.ID, id);

        startActivityForResult(i, C_VISUALIZAR);
    }
}
