package com.example.diego.roomnet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;

public class RoomNet extends AppCompatActivity {

    Button btnLamp, btnVent, btnNatal;
    TextView txtResultado;

    final Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_net);

        /*lmpOn = (Button)findViewById(R.id.lmpOn);
        lmpOff = (Button)findViewById(R.id.lmpOff);
        ventOn = (Button)findViewById(R.id.ventOn);
        ventOff = (Button)findViewById(R.id.ventOff);
        natalOn = (Button)findViewById(R.id.natalOn);
        natalOff = (Button)findViewById(R.id.natalOff);*/
        btnVent = (Button)findViewById(R.id.btnVent);
        btnLamp = (Button)findViewById(R.id.btnLamp);
        btnNatal = (Button)findViewById(R.id.btnNatal);

        txtResultado = (TextView)findViewById(R.id.txtResultado);

        handler.postDelayed(atualizaStatus, 0);

        /*lmpOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(RoomNet.this, "Botão Lâmpada funcionando", Toast.LENGTH_SHORT).show();
                solicita("luzOn");

            }
        });

        ventOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(RoomNet.this, "Botão Lâmpada funcionando", Toast.LENGTH_SHORT).show();
                solicita("ventOn");

            }
        });

        natalOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(RoomNet.this, "Botão Lâmpada funcionando", Toast.LENGTH_SHORT).show();
                solicita("natalOn");

            }
        });

        lmpOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(RoomNet.this, "Botão Lâmpada funcionando", Toast.LENGTH_SHORT).show();
                solicita("luzOff");

            }
        });

        ventOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(RoomNet.this, "Botão Lâmpada funcionando", Toast.LENGTH_SHORT).show();
                solicita("ventOff");

            }
        });

        natalOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(RoomNet.this, "Botão Lâmpada funcionando", Toast.LENGTH_SHORT).show();
                solicita("natalOff");

            }
        });


        btnLamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(RoomNet.this, "Botão Lâmpada funcionando", Toast.LENGTH_SHORT).show();
                solicita("luz");

            }
        });
        btnVent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(RoomNet.this, "Botão Lâmpada funcionando", Toast.LENGTH_SHORT).show();
                solicita("ventilador");

            }
        });
        btnNatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(RoomNet.this, "Botão Lâmpada funcionando", Toast.LENGTH_SHORT).show();
                solicita("natal");

            }
        });*/
    }

    public void solicita(String comando){

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo NetworkInfo = connMgr.getActiveNetworkInfo();

        String url = "http://192.168.15.14:8090/" + comando;

        if(NetworkInfo != null && NetworkInfo.isConnected()){
            new DownloadWebpageTask().execute(url);
        } else {
            txtResultado.setText("Nenhuma conexão detectada");
        }

    }

    private Runnable atualizaStatus = new Runnable() {
        @Override
        public void run() {

            solicita("");
            handler.postDelayed(this, 1000);
        }
    };

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String ... urls){

            Conexao conexao = new Conexao();{

                return Conexao.GetArduino(urls[0]);
            }
        }
        @Override
        protected void onPostExecute(String Result){

            if(Result != null){
                //txtResultado.setText(Result);

                if(Result.contains("Lampada - Ligado")){

                    //lmp.setText("Lâmpada ON");
                    btnLamp.setBackgroundResource(R.drawable.lamp_on);
                }
                if(Result.contains("Lampada - Desligado")){

                    //lmp.setText("Lâmpada OFF");
                    btnLamp.setBackgroundResource(R.drawable.lamp_off);
                }

                if(Result.contains("Ventilador - Desligado")){

                    //vent.setText("Ventilador OFF");
                    btnVent.setBackgroundResource(R.drawable.vent_off);
                }
                if(Result.contains("Ventilador - Ligado")){

                    //vent.setText("Ventilador ON");
                    btnVent.setBackgroundResource(R.drawable.vent_on);
                }

                if(Result.contains("Pisca - Desligado")){

                    //natal.setText("Pisca-Pisca OFF");
                    btnNatal.setBackgroundResource(R.drawable.lights_off);
                }
                if(Result.contains("Pisca - Ligado")){

                    //natal.setText("Pisca-Pisca ON");
                    btnNatal.setBackgroundResource(R.drawable.lights_on);
                }
            } else {
                txtResultado.setText("Ocorreu um erro");
            }
        }
    }
}
