package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    static boolean jedzie = false;
    static boolean przedStart = true;
    static int arabianPeninsulaWater = 0;
    static ExecutorService executorService = null;

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

        //dodanie panela dla wyboru metody tworzenia wątków
        JPanel panel_metod = new JPanel();
        panel_metod.setLayout(new FlowLayout());

        JLabel wybor_metody = new JLabel("Wybór metody:");
        JButton dieWaleFaben = new JButton("Thread");

        panel_metod.add(wybor_metody);
        panel_metod.add(dieWaleFaben);


        // Layout calosci
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(lemans, BorderLayout.CENTER);
        mainPanel.add(panel_kontrolny, BorderLayout.SOUTH);
        mainPanel.add(panel_metod, BorderLayout.NORTH);

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
        List<Thread> watki_chinskie = new ArrayList<>();

        // klasa odpowiedzialna za odswiezanie ekranu
        repainterThread watekRysowniczy = new repainterThread(lemans, 60);
        watekRysowniczy.start();

        dieWaleFaben.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (przedStart) {
                    switch (arabianPeninsulaWater) {
                        case 0:
                            dieWaleFaben.setText("Biegaczor_" +
                                    "coureur");
                            arabianPeninsulaWater = 1;
                            break;
                        case 1:
                            dieWaleFaben.setText("Baseniorwontki");
                            arabianPeninsulaWater = 2;
                            break;
                        case 2:

                            dieWaleFaben.setText("Threadzior");
                            arabianPeninsulaWater = 0;
                            break;
                    }
                }
            }
        });

        //Przycisk start-stop
        start_stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int liczba_okrazen = Integer.parseInt(okrazenia.getText());

                lemans.iloscOkrazenDoZrobienia = liczba_okrazen;

                int liczba_samochodow = Integer.parseInt(samochody.getText());
                 List<Future<?>> executorTasks = new ArrayList<>();



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
                    switch(arabianPeninsulaWater){
                        case 0:
                            start_stop.setText("Stop");
                            break;
                        case 1:

                            break;
                        case 2:

                            break;

                    }

                    switch(arabianPeninsulaWater){
                        case 0:
                            watki.clear();
                            for (int i = 0; i < liczba_samochodow; i++) {

                                fil_dexecution watek_samochodu = new fil_dexecution(lemans, i);
                                watki.add(watek_samochodu);
                                watek_samochodu.start();

                            }
                            break;
                        case 1:
                            watki_chinskie.clear();
                            for (int i = 0; i < liczba_samochodow; i++) {
                                //komenatrz tutaj
                                RunnableCars watek_samochodu1 = new RunnableCars(lemans, i);
                                Thread watek_samochodu = new Thread(watek_samochodu1);

                                watki_chinskie.add(watek_samochodu);
                                watek_samochodu.start();
                            }
                            break;
                        case 2:
                            lemans.poruszaSie = true;
                            if (executorService != null && !executorService.isShutdown()) {
                                executorService.shutdownNow(); // zatrzymaj poprzednie
                            }

                            executorService = Executors.newFixedThreadPool(10000);
                            executorTasks.clear();

                            for (int i = 0; i < liczba_samochodow; i++) {
                                int finalI = i;
                                Future<?> task = executorService.submit(() -> {
                                    try {
                                        while (lemans.poruszaSie &&
                                                lemans.zbior_samochod.elementAt(finalI).iloscPrzejechnychOkrazen() < lemans.iloscOkrazenDoZrobienia) {
                                            lemans.zbior_samochod.elementAt(finalI).obliczane_pozycji();
                                            Thread.sleep(100);
                                        }

                                        if (lemans.poruszaSie) {
                                            int przesuniecieX = lemans.ileSkonczylo / 20;
                                            int przesuniecieY = lemans.ileSkonczylo % 20;
                                            lemans.zbior_samochod.elementAt(finalI)
                                                    .przeniesAuto(400 + przesuniecieX * 20, 100 + przesuniecieY * 20);
                                            lemans.ileSkonczylo++;
                                            if(lemans.ileSkonczylo >= liczba_samochodow){
                                                lemans.poruszaSie = false;
                                                System.out.println("ZABILIŚMY GO 🔥");
                                                executorService.shutdownNow();

                                            }
                                        }

                                        if(lemans.ileSkonczylo == liczba_samochodow){
                                            lemans.poruszaSie = false;
                                            executorService.shutdownNow();
                                            System.out.println("ZABILIŚMY GO 🔥");
                                        }

                                    } catch (InterruptedException e1) {
                                        Thread.currentThread().interrupt(); // oznacz, że wątek przerwany
                                    }
                                });

                                executorTasks.add(task);
                            }


                        break;
                    }


                //auta zatrzymuja sie po wcsinieciu przycisku sztop
                } else {
                    switch(arabianPeninsulaWater){
                        case 0:
                            jedzie = false;

                            start_stop.setText("Start");

                            // Stop all threads

                            switch(arabianPeninsulaWater){
                                case 0:
                                    for (fil_dexecution watek : watki) {
                                        watek.stopThread();
                                    }
                                    break;
                                case 1:
                                    for (Thread watek : watki_chinskie) {
                                        try {
                                            watek.wait();
                                        } catch (InterruptedException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                    }
                                    break;
                                case 2:
                                    break;
                            }
                            break;
                        case 1:

                            break;
                        case 2:

                            break;
                    }


                    System.out.println(Thread.activeCount());
                    System.out.println("liczbasamohodow: "+lemans.ileSkonczylo);
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
