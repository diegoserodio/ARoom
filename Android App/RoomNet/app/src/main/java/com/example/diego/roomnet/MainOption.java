package com.example.diego.roomnet;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainOption extends AppCompatActivity implements View.OnClickListener{

    //Button btnLamp, lmpOn, lmpOff, btnVent, ventOn, ventOff, btnNatal, natalOn, natalOff;
    TextView txtResultado;

    final Handler handler = new Handler();

    Dialog myDialog;
    private CardView releCard,modoCard, fitaCard, tvCard, settingsCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_option);

        releCard = (CardView)findViewById(R.id.releCardId);
        modoCard = (CardView)findViewById(R.id.modoCardId);
        fitaCard = (CardView)findViewById(R.id.fitaCardId);
        tvCard = (CardView)findViewById(R.id.tvCardId);
        settingsCard = (CardView)findViewById(R.id.settingsCardId);

        releCard.setOnClickListener(this);
        modoCard.setOnClickListener(this);
        fitaCard.setOnClickListener(this);
        tvCard.setOnClickListener(this);
        settingsCard.setOnClickListener(this);

        handler.postDelayed(atualizaStatus, 0);

        myDialog = new Dialog(this);
    }

    @Override
    public void onClick(View v){
        Intent t;

        switch (v.getId()){
            case R.id.releCardId : t = new Intent(this, Slider_rele.class); startActivity(t); break;

            case R.id.modoCardId : t = new Intent(this, SliderModo.class); startActivity(t); break;

            case R.id.fitaCardId : t = new Intent(this, MainStrip.class); startActivity(t); break;
            default:break;
        }

    }
  /*  public void releCard (View v){

        Button btnClose;
        myDialog.setContentView(R.layout.activity_room_net);
        btnClose = myDialog.findViewById(R.id.btnErro);


        lmpOn = myDialog.findViewById(R.id.lmpOn);
        lmpOff = myDialog.findViewById(R.id.lmpOff);
        ventOn = myDialog.findViewById(R.id.ventOn);
        ventOff = myDialog.findViewById(R.id.ventOff);
        natalOn = myDialog.findViewById(R.id.natalOn);
        natalOff = myDialog.findViewById(R.id.natalOff);
        btnVent = myDialog.findViewById(R.id.btnVent);
        btnLamp = myDialog.findViewById(R.id.btnLamp);
        btnNatal = myDialog.findViewById(R.id.btnNatal);

        txtResultado = myDialog.findViewById(R.id.txtResultado);

        handler.postDelayed(atualizaStatus, 0);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        }
        );

        lmpOn.setOnClickListener(new View.OnClickListener() {
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


        /*btnLamp.setOnClickListener(new View.OnClickListener() {
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
        });

        //myDialog.show();
    }*/

    public void solicita(String comando){

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo NetworkInfo = connMgr.getActiveNetworkInfo();

        String url = "http://192.168.15.14:8090/" + comando;

        if(NetworkInfo != null && NetworkInfo.isConnected()){
            new MainOption.DownloadWebpageTask().execute(url);
        } else {
            openErrorDialog();
        }

    }

    private Runnable atualizaStatus = new Runnable() {
        @Override
        public void run() {

            solicita("");
            handler.postDelayed(this, 1000);
        }
    };

    public void openErrorDialog(){
        Button btnOk;
        btnOk = myDialog.findViewById(R.id.btnOk);
        myDialog.setContentView(R.layout.error_message);

        /*btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });*/
        myDialog.show();
    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String ... urls){

            Conexao conexao = new Conexao();{

                return Conexao.GetArduino(urls[0]);
            }
        }
        /*@Override
        protected void onPostExecute(String Result){

            if(Result == null){
                Button btnOk;
                btnOk = myDialog.findViewById(R.id.btnOk);
                myDialog.setContentView(R.layout.error_message);

                btnOk.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    myDialog.dismiss();
                                                }
                                            }
                );
                myDialog.show();
            }
        }*/
    }
}
