package org.example;

import org.example.Tor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static boolean jedzie = false;
    static boolean przedStart = true;

    public static void main(String[] args) {

        Tor lemans = new Tor();
        JFrame frame = new JFrame("Tor");
        frame.add(lemans);
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Dodanie panela kontrolnego
        JPanel panel_kontrolny = new JPanel();
        panel_kontrolny.setLayout(new FlowLayout());

        JLabel okrazenia_napis = new JLabel("Okrążenia:");
        JTextField okrazenia = new JTextField(Integer.toString(5), 5);

        JLabel samochody_napis = new JLabel("Samochody:");
        JTextField samochody = new JTextField(Integer.toString(4), 5);

        JButton start_stop = new JButton("Start");
        JButton reset_button = new JButton("Reset");

        panel_kontrolny.add(okrazenia_napis);
        panel_kontrolny.add(okrazenia);
        panel_kontrolny.add(samochody_napis);
        panel_kontrolny.add(samochody);
        panel_kontrolny.add(start_stop);
        panel_kontrolny.add(reset_button);

        // Layout calosci
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(lemans, BorderLayout.CENTER);
        mainPanel.add(panel_kontrolny, BorderLayout.SOUTH);

        frame.setContentPane(mainPanel);
        frame.setVisible(true);

        //dodanie aut "o tak"
//        lemans.dodanie_samochod(75,150,5,1.5*Math.PI,0,0,255);
//        lemans.dodanie_samochod(75,150,5,1.5*Math.PI,255,0,0);
//        lemans.dodanie_samochod(75,150,5,1.5*Math.PI,0,255,0);
//        lemans.dodanie_samochod(75,150,5,1.5*Math.PI,155,100,0);
//        lemans.dodanie_samochod(75,150,5,1.5*Math.PI,100,0,155);
//        lemans.dodanie_samochod(75,150,5,1.5*Math.PI,155,0,100);
//        lemans.dodanie_samochod(75,150,5,1.5*Math.PI,0,100,155);
//        lemans.dodanie_samochod(75,150,5,1.5*Math.PI,55,200,0);

        List<fil_dexecution> watki = new ArrayList<>();  // Make this a field if needed


        // klasa odpowiedzialna za odswiezanie ekranu
        repainterThread watekRysowniczy = new repainterThread(lemans, 60);
        watekRysowniczy.start();

        //Przycisk start-stop
        start_stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int liczba_okrazen = Integer.parseInt(okrazenia.getText());

                lemans.iloscOkrazenDoZrobienia = liczba_okrazen;

                int liczba_samochodow = Integer.parseInt(samochody.getText());

                okrazenia.setEditable(false);
                samochody.setEditable(false);

                if(przedStart){
                    przedStart = false;

                    for(int i=0; i< liczba_samochodow; i++) {
                        lemans.dodanie_samochod_losowy_kolor(75,150,5,1.5*Math.PI);
                    }
                }

                //auta zaczynaja jechac po wcsinieciu przycisku sztart
                if (!jedzie) {
                    jedzie = true;
                    start_stop.setText("Stop");

                    // Start new threads
                    watki.clear();  // Optional: clear old references
                    for (int i = 0; i < liczba_samochodow; i++) {
                        fil_dexecution watek_samochodu = new fil_dexecution(lemans, i);
                        watki.add(watek_samochodu);
                        watek_samochodu.start();
                    }

                //auta zatrzymuja sie po wcsinieciu przycisku sztop
                } else {
                    jedzie = false;

                    start_stop.setText("Start");

                    // Stop all threads
                    for (fil_dexecution watek : watki) {
                        watek.stopThread();
                    }
                    System.out.println(Thread.activeCount());
                }
            }
        });

        //Przycisk reset
        reset_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int liczba_okrazen = Integer.parseInt(okrazenia.getText());
                int liczba_samochodow = Integer.parseInt(samochody.getText());
                przedStart = true;

                lemans.ileSkonczylo = 0;

                okrazenia.setEditable(true);
                samochody.setEditable(true);

                for (fil_dexecution watek : watki) {
                    watek.stopThread();
                }
                for (int i = 0; i < liczba_samochodow; i++) {
//                    lemans.zbior_samochod.elementAt(i).kat = Math.PI*1.5;
//                    lemans.zbior_samochod.elementAt(i).x = 75;
//                    lemans.zbior_samochod.elementAt(i).y = 150;

                    lemans.zbior_samochod.clear();

                }

                lemans.repaint();
                jedzie = false;
                start_stop.setText("Start");
            }
        });
    }
}
