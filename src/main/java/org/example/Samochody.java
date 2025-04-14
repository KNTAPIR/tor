package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Samochody {
    Random rand = new Random();
    int R;
    int G;
    int B;
    int x;
    int y;
    int radius;
    double kat;

    double startowyKat;


    public Samochody (int x, int y, int radius, double kat,int R, int G, int B){
        this.R = R;
        this.G = G;
        this.B = B;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.kat = kat;

        this.startowyKat = kat;
    }


    public Samochody (int x, int y, int radius, double kat){
        this.R = rand.nextInt(256);
        this.G = rand.nextInt(256);
        this.B = rand.nextInt(256);
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.kat = kat;

        this.startowyKat = kat;

    }

    public Color Daj_kolor(){
        return new Color(R, G, B);
    }


    public int iloscPrzejechnychOkrazen() {
        return (int)((kat - startowyKat)/20*Math.PI);
    }

    public void obliczane_pozycji(){
        double delta1 = rand.nextDouble()*0.1;
        this.kat = this.kat + delta1;


        this.x = (int) (75 *Math.sin(this.kat) + 150);
        this.y = (int) (75 * Math.cos(this.kat) + 150);
//        System.out.print("x :"+this.x);
//        System.out.println("---y :"+this.y);
    }

    public void przeniesAuto(int x, int y){
        this.x = x;
        this.y = y;
    }



}
