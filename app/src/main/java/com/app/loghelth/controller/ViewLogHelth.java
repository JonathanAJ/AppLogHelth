package com.app.loghelth.controller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class ViewLogHelth extends View{

    private Drawable coracaoImg;

    public Drawable getCoracaoImg() {
        return coracaoImg;
    }

    public void setCoracaoImg(Drawable coracaoImg) {
        this.coracaoImg = coracaoImg;
    }

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

    }

    double fator = 0.5;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        desenhaCoracao(canvas, getCoracaoImg(), fator);
        if(fator > 0){
            fator = fator - 0.01;
        }else {
            fator = 0.5;
        }
        invalidate();
    }

    public void desenhaCoracao(Canvas canvas, Drawable d, double fator){
        // calcula centro
        double larguraCanvas = canvas.getWidth();
        double fatorLargura = larguraCanvas * getFatorDeLargura(fator);

        float larguraImagem = d.getIntrinsicWidth();
        float alturaImagem = d.getIntrinsicHeight();
        /**
         * retorna o aspect ratio da imagem para calcular sua altura,
         * multiplicando ele pela largura e arredondando.
         */
        float aspectRatio = getAspectRatio(alturaImagem, larguraImagem);
        int baixo = Math.round((float)fatorLargura * aspectRatio);

        // left, top, right, bottom.
        d.setBounds(0, 0, (int) fatorLargura, baixo);
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