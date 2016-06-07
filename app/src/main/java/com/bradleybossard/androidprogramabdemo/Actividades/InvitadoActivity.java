package com.bradleybossard.androidprogramabdemo.Actividades;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.bradleybossard.androidprogramabdemo.R;

/**
 * Created by Gachi on 7/6/2016.
 */
public class InvitadoActivity extends AppCompatActivity{
    private FuzzyActivity f;
    private Integer edad;
    private Integer educ;
    private String nivel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        f= new FuzzyActivity(this);
    }

    /*
    EVENTO DEL BOTON ACEPTAR
        nivel = obtenerNivel(edad,educ);
        if(nivel==Bajo){
        }
        if(nivel==Medio){
        }
        if(nivel==Alto){
        }
     */

    public String obtenerNivel(int edad, int educ){
        String resultado="";
        try {
            double r = f.FuzzyEngine(edad,educ);

            if(r<=1){
                resultado = "Bajo";
            }else
            {
                if(1<r && r<=2){
                    resultado = "Medio";
                }else{
                    if(r>2) {
                        resultado = "Alto";
                    }else{
                        resultado ="No se pudo obtener el nivel";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }
}
