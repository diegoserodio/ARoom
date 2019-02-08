package com.example.diego.roomnet;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class modoSliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;

    public modoSliderAdapter(Context context) {
        this.context = context;
    }

    public int[] lst_images_modo = {
            R.drawable.room,
            R.drawable.netflix,
            R.drawable.party,
            R.drawable.sleeping
    };


    public String[] lst_titles_modo = {
            "Tudo",
            "Netflix",
            "Festa",
            "Coma"
    };

    @Override
    public int getCount() {
        return lst_titles_modo.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout) object);
    }

    @Override
    public Object instantiateItem (ViewGroup container, int position){
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.modo_slide, container, false);
        LinearLayout layoutslide = view.findViewById(R.id.modoSliderLinearLayout);
        ImageView imgslide = view.findViewById(R.id.modoSliderImage);
        TextView txtslide = view.findViewById(R.id.modoSliderTxt);
        imgslide.setBackgroundResource(lst_images_modo[position]);
        txtslide.setText(lst_titles_modo[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem (ViewGroup container,int position, Object object){
        container.removeView((LinearLayout) object);
    }
}
