package com.example.diego.roomnet;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import static com.example.diego.roomnet.R.color.red;

public class MainStrip extends AppCompatActivity {

    Button btnStripOn, btnEffect, btnEffectMinus, btnEffectPlus, btnMusic;
    SeekBar seekRed, seekGreen,seekBlue;
    String stripRedCommand = "red:";
    String stripGreenCommand = "green:";
    String stripBlueCommand = "blue:";
    final Handler stripHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_strip);

        seekRed = (SeekBar)findViewById(R.id.seekRed);
        seekGreen = (SeekBar)findViewById(R.id.seekGreen);
        seekBlue = (SeekBar)findViewById(R.id.seekBlue);
        btnStripOn = (Button)findViewById(R.id.btnStripOn);
        btnEffectMinus = (Button)findViewById(R.id.btnEffectMinus);
        btnEffect = (Button)findViewById(R.id.btnEffect);
        btnEffectPlus = (Button)findViewById(R.id.btnEffectPlus);
        btnMusic = (Button)findViewById(R.id.btnMusic);
        stripHandler.postDelayed(atualizaStripStatus, 0);

        seekRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int redValue = 0;
            String seekRedValue = "";
            String redCommand = "";
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                redValue = i+100;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekRedValue = Integer.toString(redValue);
                redCommand = stripRedCommand + seekRedValue;
                solicita(redCommand);
            }
        });
        seekGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int greenValue = 0;
            String seekGreenValue = "";
            String greenCommand = "";
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                greenValue = i+100;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekGreenValue = Integer.toString(greenValue);
                greenCommand = stripGreenCommand + seekGreenValue;
                solicita(greenCommand);
            }
        });
        seekBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int blueValue = 0;
            String seekBlueValue = "";
            String blueCommand = "";
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                blueValue = i+100;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBlueValue = Integer.toString(blueValue);
                blueCommand = stripBlueCommand + seekBlueValue;
                solicita(blueCommand);
            }
        });

        btnStripOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solicita("strip");
            }
        });

        btnEffectMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solicita("effectminus");
            }
        });
        btnEffectPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solicita("effectplus");
            }
        });
        btnMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solicita("music");
            }
        });
    }

    public void solicita(String comando){

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo NetworkInfo = connMgr.getActiveNetworkInfo();

        String url = "http://192.168.15.14:8090/" + comando;

        if(NetworkInfo != null && NetworkInfo.isConnected()){
            new MainStrip.DownloadWebpageTask().execute(url);
        }

    }

    private Runnable atualizaStripStatus = new Runnable() {
        @Override
        public void run() {

            solicita("");
            stripHandler.postDelayed(this, 1000);
        }
    };

    public class DownloadWebpageTask extends AsyncTask<String, Void, String> {

        @Override
        public String doInBackground(String ... urls){

            Conexao conexao = new Conexao();{

                return Conexao.GetArduino(urls[0]);
            }
        }
        @Override
        public void onPostExecute(String Result) {
            if (Result != null) {
                if(Result.contains("Fita - Ligado")){
                    btnStripOn.setBackgroundResource(R.drawable.ic_power_settings_new_black_24dp_on);
                }
                if(Result.contains("NAEffect - Ligado")){
                    btnEffect.setText("Nenhum");
                }
                if(Result.contains("Efeito 1 - Ligado")){
                    btnEffect.setText("Efeito 1");
                }
                if(Result.contains("Efeito 2 - Ligado")){
                    btnEffect.setText("Efeito 2");
                }
                if(Result.contains("Efeito 3 - Ligado")){
                    btnEffect.setText("Efeito 3");
                }
                if(Result.contains("Music - Ligado")){
                    btnMusic.setBackgroundColor(Color.LTGRAY);
                }
                if(Result.contains("Fita - Desligado")){
                    btnStripOn.setBackgroundResource(R.drawable.ic_power_settings_new_black_24dp);
                }
                if(Result.contains("Music - Desligado")){
                    btnMusic.setBackgroundColor(getColor(R.color.gray));
                }
            }
        }
    }
}
