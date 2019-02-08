package com.example.diego.roomnet;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class fitaSliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;

    public fitaSliderAdapter(Context context) {
        this.context = context;
    }

    public int[] lst_images_fita = {
            R.drawable.lights,
            R.drawable.settings,
            R.drawable.lightning,
            R.drawable.music_player
    };


    public String[] lst_titles_fita = {
            "Fita de Led",
            "Fade",
            "Estrobo",
            "Reativo à música"
    };

    @Override
    public int getCount() {
        return lst_titles_fita.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout) object);
    }

    @Override
    public Object instantiateItem (ViewGroup container, int position){
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fita_slide, container, false);
        LinearLayout layoutslide = view.findViewById(R.id.fitaSliderLinearLayout);
        ImageView imgslide = view.findViewById(R.id.fitaSliderImage);
        TextView txtslide = view.findViewById(R.id.fitaSliderTxt);
        imgslide.setBackgroundResource(lst_images_fita[position]);
        txtslide.setText(lst_titles_fita[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem (ViewGroup container,int position, Object object){
        container.removeView((LinearLayout) object);
    }
}
