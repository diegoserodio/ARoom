package com.example.diego.roomnet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SliderFita extends AppCompatActivity {

    Button btnFitaNext, btnFitaPrevious, btnFitaOn, btnFitaOff;
    ViewPager fitaSlideViewPager;
    int fitaPos;
    TextView txtResultado;
    final Handler Fitahandler = new Handler();
    private fitaSliderAdapter myadaptor;
    ImageView fitaSlideImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_fita);

        btnFitaNext = findViewById(R.id.btnFitaNext);
        btnFitaPrevious = findViewById(R.id.btnFitaPrevious);
        btnFitaOn = findViewById(R.id.btnFitaOn);
        btnFitaOff = findViewById(R.id.btnFitaOff);
        fitaSlideViewPager = findViewById(R.id.fitaSliderViewPager);
        myadaptor = new fitaSliderAdapter(this);
        fitaSlideViewPager.setAdapter(myadaptor);
        txtResultado = findViewById(R.id.txtResultado);
        Fitahandler.postDelayed(atualizaFitaStatus, 0);
        fitaSlideImage = findViewById(R.id.fitaSliderImage);

        fitaSlideViewPager.addOnPageChangeListener(viewListener);

        btnFitaPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fitaSlideViewPager.setCurrentItem(fitaPos - 1);
            }
        });

        btnFitaNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fitaSlideViewPager.setCurrentItem(fitaPos + 1);
            }
        });

        btnFitaOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fitaPos == 0){
                    solicita("fitaOn");
                }
                else if(fitaPos == 1){
                    solicita("fadeOn");
                }
                else if(fitaPos == 2){
                    solicita("stroboOn");
                }
                else if(fitaPos == 3){
                    solicita("vijuOn");
                }
            }
        });

        btnFitaOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fitaPos == 0){
                    solicita("fitaOff");
                }
                else if(fitaPos == 1){
                    solicita("fadeOff");
                }
                else if(fitaPos == 2){
                    solicita("stroboOff");
                }
                else if(fitaPos == 3){
                    solicita("vijuOff");
                }
            }
        });
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            fitaPos = position;

            if(position == 0){
                btnFitaPrevious.setVisibility(View.INVISIBLE);
            }
            else if(position == 3){
                btnFitaNext.setVisibility(View.INVISIBLE);
            }
            else{
                btnFitaPrevious.setVisibility(View.VISIBLE);
                btnFitaNext.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void solicita(String comando){

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo NetworkInfo = connMgr.getActiveNetworkInfo();

        String url = "http://192.168.15.14:8090/" + comando;

        if(NetworkInfo != null && NetworkInfo.isConnected()){
            new SliderFita.DownloadWebpageTask().execute(url);
        } else {
            txtResultado.setText("Nenhuma conexão detectada");
        }

    }

    private Runnable atualizaFitaStatus = new Runnable() {
        @Override
        public void run() {

            solicita("");
            Fitahandler.postDelayed(this, 1000);
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
            if (Result == null) {
                txtResultado.setText("Ocorreu um erro");
            }
        }
    }
}
