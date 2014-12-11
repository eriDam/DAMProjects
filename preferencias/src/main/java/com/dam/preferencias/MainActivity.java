package com.dam.preferencias;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Capturamos las dos cajas de texto
        final EditText editName = (EditText) findViewById(R.id.editName);
        final EditText editAge = (EditText) findViewById(R.id.editAge);

        //Capturamos las preferencias de Usuario
        final SharedPreferences pref = getPreferences(MODE_PRIVATE);
        editAge.setText(String.valueOf(pref.getInt("age",0)));
        editName.setText(pref.getString("name",""));

        //Programamos botón
        final Button guardar=(Button) findViewById(R.id.button);

        guardar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor prefEd = pref.edit();

                        prefEd.putInt("age",Integer.parseInt(editAge.getText().toString()));
                        prefEd.putString("name",editName.getText().toString());

                        prefEd.commit();
                    }
                }
        );

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
