package com.example.diego.roomnet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SliderModo extends AppCompatActivity {

    Button btnModoNext, btnModoPrevious, btnModoOn, btnModoOff;
    ViewPager modoSlideViewPager;
    int modoPos;
    TextView txtResultado;
    final Handler Modohandler = new Handler();
    private modoSliderAdapter myadaptor;
    ImageView modoSlideImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_modos);

        btnModoNext = findViewById(R.id.btnModoNext);
        btnModoPrevious = findViewById(R.id.btnModoPrevious);
        btnModoOn = findViewById(R.id.btnModoOn);
        btnModoOff = findViewById(R.id.btnModoOff);
        modoSlideViewPager = findViewById(R.id.modoSliderViewPager);
        myadaptor = new modoSliderAdapter(this);
        modoSlideViewPager.setAdapter(myadaptor);
        txtResultado = findViewById(R.id.txtResultado);
        Modohandler.postDelayed(atualizaModoStatus, 0);
        modoSlideImage = findViewById(R.id.modoSliderImage);

        modoSlideViewPager.addOnPageChangeListener(viewListener);

        btnModoPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modoSlideViewPager.setCurrentItem(modoPos - 1);
            }
        });

        btnModoNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modoSlideViewPager.setCurrentItem(modoPos + 1);
            }
        });

        btnModoOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(modoPos == 0){
                    solicita("tudoOn");
                }
                else if(modoPos == 1){
                    solicita("netflixOn");
                }
                else if(modoPos == 2){
                    solicita("festaOn");
                }
                else if(modoPos == 3){
                    solicita("comaOn");
                }
            }
        });

        btnModoOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(modoPos == 0){
                    solicita("tudoOff");
                }
                else if(modoPos == 1){
                    solicita("netflixOff");
                }
                else if(modoPos == 2){
                    solicita("festaOff");
                }
                else if(modoPos == 3){
                    solicita("comaOff");
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
            modoPos = position;

            if(position == 0){
                btnModoPrevious.setVisibility(View.INVISIBLE);
            }
            else if(position == 3){
                btnModoNext.setVisibility(View.INVISIBLE);
            }
            else{
                btnModoPrevious.setVisibility(View.VISIBLE);
                btnModoNext.setVisibility(View.VISIBLE);
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
            new SliderModo.DownloadWebpageTask().execute(url);
        } else {
            txtResultado.setText("Nenhuma conexão detectada");
        }

    }

    private Runnable atualizaModoStatus = new Runnable() {
        @Override
        public void run() {

            solicita("");
            Modohandler.postDelayed(this, 1000);
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
