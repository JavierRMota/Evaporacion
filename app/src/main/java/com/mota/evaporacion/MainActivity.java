package com.mota.evaporacion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout l = (LinearLayout) findViewById(R.id.results);
        l.setVisibility(View.INVISIBLE);
    }
    public void calcularTodo(View v)
    {
        EditText temperaturaAlimentacionET = (EditText) findViewById(R.id.tempAlEditText);
        EditText pEVAET = (EditText) findViewById(R.id.pEVAET);
        EditText calorEspecificoAlET = (EditText) findViewById(R.id.calEspAlEditText);
        EditText concentracionBrixInET = (EditText) findViewById(R.id.brixInEditText);
        EditText concentracionBrixFinET = (EditText) findViewById(R.id.brixFinEditText);
        EditText alimentacionET = (EditText) findViewById(R.id.alEditText);
        EditText aPEET = (EditText) findViewById(R.id.aPEEditText);
        EditText presionVServET = (EditText) findViewById(R.id.presionVServ);
        EditText pATMET = (EditText) findViewById(R.id.presionAtmos);

        try {
            double temperaturaAl = Double.parseDouble(temperaturaAlimentacionET.getText().toString());
            double pEva = Double.parseDouble(pEVAET.getText().toString());
            double calorE = Double.parseDouble(calorEspecificoAlET.getText().toString());
            double brixIn = Double.parseDouble(concentracionBrixInET.getText().toString());
            double brixFin = Double.parseDouble(concentracionBrixFinET.getText().toString());
            double al = Double.parseDouble(alimentacionET.getText().toString());
            double ape;
            if (!aPEET.getText().toString().equalsIgnoreCase(""))
                ape = Double.parseDouble(aPEET.getText().toString());
            else ape = 0.0;
            double pvSR = Double.parseDouble(presionVServET.getText().toString());
            double pATM = Double.parseDouble(pATMET.getText().toString());

            //Balance de masa
            double concentrado = (brixIn * al) / brixFin;
            double vaporAl = al - concentrado;

            //Balance de energía
            //Presión real
            double pRE = pATM - pEva;
            double pRVS = pATM + pvSR;

            //Temperaturas
            double tRE = 1730.630/(8.0713 - Math.log10(pRE)) - 233.426;
            double tRVS = 1730.630/(8.0713 - Math.log10(pRVS)) - 233.426;

            //Entalpía
            //Alimentación
            double entalpiaAl = calorE * (temperaturaAl-0);


            //Concentrado
            double entalpiaCon = calorE * (tRE + ape-0);


            //Vapor de servicio
            double hVV;
            if(tRVS < 11) hVV = 2501.5 + 1.85*tRVS;
            else if(tRVS < 25) hVV = 2501.5 + 1.84*tRVS;
            else if(tRVS < 35) hVV = 2501.5 + 1.831*tRVS;
            else if(tRVS < 41) hVV = 2501.5 + 1.825*tRVS;
            else if(tRVS < 47) hVV = 2501.5 + 1.82*tRVS;
            else if(tRVS < 51) hVV = 2501.5 + 1.815*tRVS;
            else if(tRVS < 75) hVV = 2504.16 + 1.75*tRVS;
            else if(tRVS < 79) hVV = 2522 + 1.5*tRVS;
            else if(tRVS < 83 || (138.0 <= tRVS && tRVS <= 142.0)) hVV = 2523 + 1.5*tRVS;
            else if(tRVS < 93 || (135.0 <= tRVS && tRVS <= 137.0)) hVV = 2524 + 1.5*tRVS;
            else if(tRVS < 97 || (128.0 <= tRVS && tRVS <= 134.0) ) hVV = 2525 + 1.5*tRVS;
            else if(tRVS < 127) hVV = 2526 + 1.5*tRVS;
            else if(tRVS < 159) hVV = 2567.28 + 1.187*tRVS;
            else if(tRVS < 180) hVV = 2600.89 + 0.976*tRVS;
            else if(tRVS < 199) hVV = 2641.11 + 0.75*tRVS;
            else hVV = 2698.54 + 0.46*tRVS;


            double hVA = 4.2*tRVS;
            if(tRVS < 15) hVA +=0;
            else if(tRVS < 21) hVA -=0.1;
            else if(tRVS < 26) hVA -=0.2;
            else if(tRVS < 31) hVA -=0.3;
            else if(tRVS < 36) hVA -=0.4;
            else if(tRVS < 41) hVA -=0.5;
            else if(tRVS < 46) hVA -=0.6;
            else if(tRVS < 53) hVA -=0.7;
            else if(tRVS < 60) hVA -=0.8;
            else if(tRVS < 66) hVA -=0.9;
            else if(tRVS < 77) hVA -=1;
            else if(tRVS == 78) hVA -=1.2;
            else if(tRVS < 90) hVA -=1.1;
            else if(tRVS < 97) hVA -=1;
            else hVA -=0.9;

            double hVS = hVV-hVA;

            double hVAL;
            if(tRE < 11) hVAL = 2501.5 + 1.85*tRE;
            else if(tRE < 25) hVAL = 2501.5 + 1.84*tRE;
            else if(tRE < 35) hVAL = 2501.5 + 1.831*tRE;
            else if(tRE < 41) hVAL = 2501.5 + 1.825*tRE;
            else if(tRE < 47) hVAL = 2501.5 + 1.82*tRE;
            else if(tRE < 51) hVAL = 2501.5 + 1.815*tRE;
            else if(tRE < 75) hVAL = 2504.16 + 1.75*tRE;
            else if(tRE < 79) hVAL = 2522 + 1.5*tRE;
            else if(tRE < 83 || (138.0 <= tRE && tRE <= 142.0)) hVAL = 2523 + 1.5*tRE;
            else if(tRE < 93 || (135.0 <= tRE && tRE <= 137.0)) hVAL = 2524 + 1.5*tRE;
            else if(tRE < 97 || (128.0 <= tRE && tRE <= 134.0) ) hVAL = 2525 + 1.5*tRE;
            else if(tRE < 127) hVAL = 2526 + 1.5*tRE;
            else if(tRE < 159) hVAL = 2567.28 + 1.187*tRE;
            else if(tRE < 180) hVAL = 2600.89 + 0.976*tRE;
            else if(tRE < 199) hVAL = 2641.11 + 0.75*tRE;
            else hVAL = 2698.54 + 0.46*tRE;

            double vapServ = ((entalpiaCon * concentrado) + (hVAL * vaporAl) - (entalpiaAl * al)) / hVS;



            //Otros
            //Economía
            double economia = (vaporAl / vapServ) * 100;

            EditText con = (EditText) findViewById(R.id.concentradoEditText);
            EditText vaporALIM = (EditText) findViewById(R.id.vaporAlEditText);
            EditText entalpAl = (EditText) findViewById(R.id.entalpiaAlEditText);
            EditText entalpCon = (EditText) findViewById(R.id.entalpiaConEditText);
            EditText vserv = (EditText) findViewById(R.id.vaporServEditText);
            EditText eco = (EditText) findViewById(R.id.economiaEditText);
            LinearLayout l = (LinearLayout) findViewById(R.id.results);
            EditText enVAET = (EditText) findViewById(R.id.eVAeditText);
            EditText enVSET = (EditText) findViewById(R.id.eVSeditText);
            l.setVisibility(View.VISIBLE);
            con.setText(String.format("%.4f", concentrado));
            vaporALIM.setText(String.format("%.4f", vaporAl));
            entalpAl.setText(String.format("%.4f", entalpiaAl));
            entalpCon.setText(String.format("%.4f", entalpiaCon));
            vserv.setText(String.format("%.4f", vapServ));
            eco.setText(String.format("%.4f", economia));
            enVAET.setText(String.format("%.4f", hVAL));
            enVSET.setText(String.format("%.4f", hVS));

            Button limpiar = (Button) findViewById(R.id.button2);
            limpiar.setVisibility(View.VISIBLE);
        }
        catch (NumberFormatException e)
        {
            Toast.makeText(this, "Revisa los campos.", Toast.LENGTH_LONG).show();
        }

    }
    public void abilitarAPE(View v)
    {
        EditText ape = (EditText) findViewById(R.id.aPEEditText);
        CheckBox apeSi = (CheckBox) v;
        if(apeSi.isChecked()) ape.setVisibility(View.VISIBLE);
        else
        {
            ape.setVisibility(View.INVISIBLE);
            ape.setText("");
        }

    }
    public void limpiarTodo (View v)
    {
        Button limpiar = (Button) findViewById(R.id.button2);
        limpiar.setVisibility(View.INVISIBLE);
        LinearLayout l = (LinearLayout) findViewById(R.id.results);
        l.setVisibility(View.INVISIBLE);
        EditText temperaturaAlimentacionET = (EditText) findViewById(R.id.tempAlEditText);
        EditText temperaturaEvaporadorET = (EditText) findViewById(R.id.pEVAET);
        EditText calorEspecificoAlET = (EditText) findViewById(R.id.calEspAlEditText);
        EditText concentracionBrixInET = (EditText) findViewById(R.id.brixInEditText);
        EditText concentracionBrixFinET = (EditText) findViewById(R.id.brixFinEditText);
        EditText alimentacionET = (EditText) findViewById(R.id.alEditText);
        EditText aPEET = (EditText) findViewById(R.id.aPEEditText);
        EditText entalpiaVapAlET = (EditText) findViewById(R.id.presionVServ);
        EditText presAT = (EditText) findViewById(R.id.presionAtmos);
        EditText enVAET = (EditText) findViewById(R.id.eVAeditText);
        EditText enVSET = (EditText) findViewById(R.id.eVSeditText);
        temperaturaAlimentacionET.setText("");
        temperaturaEvaporadorET.setText("");
        calorEspecificoAlET.setText("");
        concentracionBrixInET.setText("");
        concentracionBrixFinET.setText("");
        alimentacionET.setText("");
        aPEET.setText("");
        entalpiaVapAlET.setText("");
        presAT.setText("");
        enVAET.setText("");
        enVSET.setText("");

    }


}
