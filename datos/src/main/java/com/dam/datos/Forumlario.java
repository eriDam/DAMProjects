package com.dam.datos;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


public class Forumlario extends Activity {

    private TextView labelId;
    private EditText nombre;

    //Identificador de la base de datos
    private DataBaseHelper mDbHelper;
    private SQLiteDatabase db;
    private long id ;

    //
    // Modo del formulario
    //
    private int modo ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forumlario);

        //CApturamos los datos enviados
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();

        if (extra == null) return;

        // Consultamos la base de datos
        mDbHelper = new DataBaseHelper(this);
        db = mDbHelper.getWritableDatabase();

        //
        // Obtenemos los elementos de la vista
        //
        labelId = (TextView) findViewById(R.id.label_id);
        nombre  = (EditText) findViewById(R.id.nombre);

        //
        // Obtenemos el identificador del registro si viene indicado
        //
        if (extra.containsKey(DataBaseHelper.ID))
        {
            id = extra.getLong(DataBaseHelper.ID);
            consultar(id);
        }

        //
        // Establecemos el modo del formulario
        //
        establecerModo(extra.getInt(mDbHelper.C_MODO));
    }

    private void consultar(long id)
    {
        //
        // Consultamos el centro por el identificador
        //
        Cursor cursor = mDbHelper.getRegistro(id);
        labelId.setText(labelId.getText()+cursor.getString(cursor.getColumnIndex(DataBaseHelper.ID)));
        nombre.setText(cursor.getString(cursor.getColumnIndex(DataBaseHelper.ARTIST_NAME)));
    }

    private void establecerModo(int m)
    {
        this.modo = m ;

        if (modo == mDbHelper.C_VISUALIZAR)
        {
            this.nombre.setEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_forumlario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
