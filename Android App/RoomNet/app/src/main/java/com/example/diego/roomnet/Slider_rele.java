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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Slider_rele extends AppCompatActivity {

    Button btnNext, btnPrevious, btnOn, btnOff;
    ViewPager releSlideViewPager;
    int pos;
    TextView txtResultado;
    final Handler handler = new Handler();
    private releSliderAdapter myadaptor;
    ImageView releSlideImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_rele);

        btnNext = findViewById(R.id.btnNext);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnOn = findViewById(R.id.btnOn);
        btnOff = findViewById(R.id.btnOff);
        releSlideViewPager = findViewById(R.id.releSliderViewPager);
        myadaptor = new releSliderAdapter(this);
        releSlideViewPager.setAdapter(myadaptor);
        txtResultado = findViewById(R.id.txtResultado);
        handler.postDelayed(atualizaStatus, 0);
        releSlideImage = findViewById(R.id.releSliderImage);

        releSlideViewPager.addOnPageChangeListener(viewListener);

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                releSlideViewPager.setCurrentItem(pos - 1);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                releSlideViewPager.setCurrentItem(pos + 1);
            }
        });

        btnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pos == 0){
                    solicita("luzOn");
                }
                else if(pos == 1){
                    solicita("ventOn");
                }
                else if(pos == 2){
                    solicita("natalOn");
                }
                else if(pos == 3){
                    solicita("telaOn");
                }
            }
        });

        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pos == 0){
                    solicita("luzOff");
                }
                else if(pos == 1){
                    solicita("ventOff");
                }
                else if(pos == 2){
                    solicita("natalOff");
                }
                else if(pos == 3){
                    solicita("telaOff");
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

            pos = position;

            if(position == 0){
                btnPrevious.setVisibility(View.INVISIBLE);
            }
            else if(position == 3){
                btnNext.setVisibility(View.INVISIBLE);
            }
            else{
                btnPrevious.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
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
            new Slider_rele.DownloadWebpageTask().execute(url);
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

    public class DownloadWebpageTask extends AsyncTask<String, Void, String> {

        @Override
        public String doInBackground(String ... urls){

            Conexao conexao = new Conexao();{

                return Conexao.GetArduino(urls[0]);
            }
        }
        @Override
        public void onPostExecute(String Result){
            if(Result == null){
                txtResultado.setText("Ocorreu um erro");
            }
        }
    }
}
