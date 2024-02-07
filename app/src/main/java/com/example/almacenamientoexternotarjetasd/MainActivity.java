package com.example.almacenamientoexternotarjetasd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/*
Autor: Juan Francisco Sánchez González
Fecha: 20/01/2024
Clase: Actividad que almacena en la tarjeta de almacenamiento externo del dispositivo los datos del campo
de texto. Cada vez que se inicia la aplicación coloca en el control la última informnación ingresada. Se
dispone de un botón para almacenar los datos y finalizar el programa.
*/

public class MainActivity extends AppCompatActivity {

    private Button boton;
    private EditText mensajeEt;
    // Nombre fichero
    private final String NOMBRE_FICHERO = "datos.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instancia componentes de la interfaz
        boton = (Button) findViewById(R.id.buttonGuardar);
        mensajeEt = (EditText) findViewById(R.id.editTextMensaje);

        // Comprobamos estado de la tarjeta SD para leer sus datos y mostrarlos
        if (comprobarTarjeta()) {
            leerDatosFich();
        } else {
            Toast.makeText(this, getString(R.string.test_sd_text), Toast.LENGTH_LONG).show();
        }

        // Listener del botón
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Comprobamos estado de la tarjeta para escribir los datos del control
                if (comprobarTarjeta()) {
                    guardarDarosFich();
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.test_sd_text), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    // Método para guardar en la tarjeta SD y salimos del aplicativo
    private void guardarDarosFich() {
        try {
            String rutaSD = getExternalFilesDir(null).getAbsolutePath();
            File miFich = new File(rutaSD, NOMBRE_FICHERO);
            OutputStreamWriter wFich = new OutputStreamWriter(new FileOutputStream(miFich));
            wFich.write(mensajeEt.getText().toString());
            wFich.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finish();
    }

    // Método para leer en la tarjeta SD y asignarlo al control de texto
    private void leerDatosFich() {
        try {
            String rutaSD = getExternalFilesDir(null).getAbsolutePath();
            File miFich = new File(rutaSD, NOMBRE_FICHERO);
            BufferedReader rFich = new BufferedReader(new InputStreamReader(new FileInputStream(miFich)));
            String texto = rFich.readLine();
            mensajeEt.setText(texto);
            rFich.close();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    // Método para comprobar el estado de la tarjeta
    private boolean comprobarTarjeta() {
        boolean testSD = false;
        String sdEstado = Environment.getExternalStorageState();
        return sdEstado.equals(Environment.MEDIA_MOUNTED);
    }
}