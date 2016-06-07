package com.bradleybossard.androidprogramabdemo.Actividades;
import android.content.Context;


import net.sourceforge.jFuzzyLogic.*;
import net.sourceforge.jFuzzyLogic.rule.Variable;

import java.io.InputStream;
/**
 * Created by Gachi on 7/6/2016.
 */
public class FuzzyActivity {
    private Context context;
    private double nivelTecno;
    public FuzzyActivity(Context context) {
        this.context = context;
    }

    public double FuzzyEngine(Integer edad, float educacion) throws Exception {
        // Load from 'FCL' file
        InputStream inputStream = context.getAssets().open("chat.fcl");
        FIS fis = FIS.load(inputStream, true);

        // Error while loading?
        if (fis == null) {
            System.err.println("Error al cargar el archivo del controlador difuso '" + inputStream + "'");
            return nivelTecno;
        }
        // Show
//        JFuzzyChart.get().chart(fis);

        // Set inputs
        fis.setVariable("Edad", edad);
        fis.setVariable("Educacion", educacion);
        // Evaluate
        fis.evaluate();

        // Show output variable's chart
        Variable nivelT = fis.getVariable("Nivel_Tecnologico");
        nivelTecno = nivelT.getValue();
        System.out.println("Nivel Tecnologico es:" + nivelT);
        return nivelTecno;
    }
}
