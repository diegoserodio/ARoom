package com.example.diego.roomnet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class releSliderAdapter extends PagerAdapter{
    Context context;
    LayoutInflater inflater;


    public releSliderAdapter(Context context) {
            this.context = context;
        }


            public int[] lst_images = {
                    R.drawable.lamp_off,
                    R.drawable.lamp_off,
                    R.drawable.vent_off,
                    R.drawable.lights_off,
                    R.drawable.ic_live_help_black_24dp
            };

            public int[] lst_images2 = {
                    R.drawable.lamp_on,
                    R.drawable.vent_on,
                    R.drawable.lights_on,
                    R.drawable.screen_on
            };


            public String[] lst_titles = {
                    "Lâmpada",
                    "Ventilador",
                    "Pisca-Pisca",
                    "Monitor"
            };
        @Override
        public int getCount () {
            return lst_titles.length;
        }

        @Override
        public boolean isViewFromObject (View view, Object object){
            return (view == (LinearLayout) object);
        }

        @Override
        public Object instantiateItem (ViewGroup container,int position){
            inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.rele_slide, container, false);
            LinearLayout layoutslide = view.findViewById(R.id.releSliderLinearLayout);
            ImageView imgslide = view.findViewById(R.id.releSliderImage);
            TextView txtslide = view.findViewById(R.id.releSliderTxt);
            imgslide.setBackgroundResource(lst_images2[position]);
            txtslide.setText(lst_titles[position]);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem (ViewGroup container,int position, Object object){
            container.removeView((LinearLayout) object);
        }

}
