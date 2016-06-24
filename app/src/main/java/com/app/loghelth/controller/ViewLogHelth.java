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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        desenhaCoracao(canvas, coracaoImg, fator);
        if(fator > 0){
            fator = fator - 0.01;
        }else {
            fator = 0.5;
        }
        invalidate();
    }

    public void desenhaCoracao(Canvas canvas, Drawable d, double fator){

        double larguraCanvas = canvas.getWidth();
        double fatorLargura = larguraCanvas * getFatorDeLargura(fator);

        double alturaCanvas = canvas.getHeight();
        double fatorAltura = alturaCanvas * getFatorDeLargura(fator);

        float larguraImagem = d.getIntrinsicWidth();
        float alturaImagem = d.getIntrinsicHeight();
        /**
         * retorna o aspect ratio da imagem para calcular sua altura,
         * multiplicando ele pela largura e arredondando.
         */
        double aspectRatio = getAspectRatio(alturaImagem, larguraImagem);
        double baixo = fatorAltura * aspectRatio;

        double centro = (larguraCanvas/2) - (fatorLargura/2);

        // left, top, right, bottom.
        d.setBounds((int) centro, (int) (100 - baixo), (int) (fatorLargura + centro), (int) (90 + baixo));
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