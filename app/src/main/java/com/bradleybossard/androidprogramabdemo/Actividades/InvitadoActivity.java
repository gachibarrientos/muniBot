package com.bradleybossard.androidprogramabdemo.Actividades;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.bradleybossard.androidprogramabdemo.Modelo.User;
import com.bradleybossard.androidprogramabdemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Gachi on 7/6/2016.
 */
public class InvitadoActivity extends AppCompatActivity{

    private FuzzyActivity f;
    private Integer edad;
    private Float educ;
    private String nivel;

    @Bind(R.id.radioEducacion)
    RadioGroup radioEducacion;
    @Bind(R.id.radioEduAno)
    RadioGroup radioEduAno;

    @Bind(R.id.input_Edad)
    EditText input_Edad;

    @Bind(R.id.btn_Invitado)
    Button btn_LoginInvitado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitado);
        ButterKnife.bind(this);

        f= new FuzzyActivity(this);

        radioEducacion.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radioEducacionNin) {
                    educ = (float) 0;
                } else {
                    if (i == R.id.radioEducacionPri) {
                        educ = (float) 1;
                    } else {
                        if (i == R.id.radioEducacionSec) {
                            educ = (float) 2;
                        } else {
                            educ = (float) 3;
                        }
                    }
                }
            }
        });

       radioEduAno.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radioEduAnoUno) {
                    educ += (float) 0.16 ;
                } else {
                    if (i == R.id.radioEduAnoDos) {
                        educ += (float) 0.33;
                    } else {
                        if (i == R.id.radioEduAnoTre) {
                            educ += (float) 0.49;
                        } else {
                            if(i == R.id.radioEduAnoCua){
                                educ += (float) 0.66;
                            }else{
                                if(i == R.id.radioEduAnoCin){
                                    educ += (float) 0.82;
                                }else{
                                    educ += (float) 0.98;
                                }
                            }

                        }
                    }
                }
            }
        });

        btn_LoginInvitado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edad = Integer.valueOf(input_Edad.getText().toString().trim()).intValue();
                nivel = obtenerNivel(edad, educ);
                System.out.println("Nivel tecnológico: "+nivel);
            }
        });
    }

    public String obtenerNivel(int edad, float educ){
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
