package com.app.loghelth.controller;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.app.loghelth.R;

public class Principal extends AppCompatActivity {

    private ViewLogHelth telaLogHelth;
    private Drawable coracaoImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        telaLogHelth = (ViewLogHelth) findViewById(R.id.telaLogHelth);
        coracaoImg = ResourcesCompat.getDrawable(getResources(), R.drawable.heart_icon, null);
        telaLogHelth.setCoracaoImg(coracaoImg);
    }


}