package com.app.loghelth.controller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.View;

import com.app.loghelth.R;

public class ViewLogHelth extends View{

    private Drawable coracaoImg;

    public ViewLogHelth(Context context) {
        super(context);
        init(context);
    }

    public ViewLogHelth(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ViewLogHelth(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){

        coracaoImg = ResourcesCompat.getDrawable(getResources(), R.drawable.heart_icon, null);

    }

    double fator = 0.5;
    double i = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.scale((float) fator, (float) fator, canvas.getWidth()/2, 100);

        desenhaCoracao(canvas, coracaoImg);
        if(fator > 0){
            fator = fator - 0.01;
        }else {
            fator = 0.5;
        }

        invalidate();
    }

    public void desenhaCoracao(Canvas canvas, Drawable d){

        int larguraCanvas = canvas.getWidth();

        int larguraImagem = d.getIntrinsicWidth();
        int alturaImagem = d.getIntrinsicHeight();
        /**
         * retorna o aspect ratio da imagem para calcular sua altura,
         * multiplicando ele pela largura e arredondando.
         */
        float aspectRatio = getAspectRatio(alturaImagem, larguraImagem);
        float baixo = larguraCanvas * aspectRatio;

        // left, top, right, bottom.
        d.setBounds(0, 0, larguraCanvas, (int) baixo);
        d.draw(canvas);
    }

    public float getAspectRatio(float alturaImage, float larguraImagem){
        /**
         * Calcula AspectRatio da imagem
         */
        return alturaImage/larguraImagem;
    }

    public double getFatorDeLargura(double fatorLargura){
        return fatorLargura;
    }
}