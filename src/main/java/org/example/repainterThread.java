package org.example;

public class repainterThread extends Thread{

    Tor torRysowany;
    int docelowyKlatkarz;
    int iloscSpania;
    public repainterThread(Tor lemans, int  iloscKlatek){
        this.torRysowany = lemans;
        this.docelowyKlatkarz = iloscKlatek;
        this.iloscSpania = 1000/iloscKlatek;
    }

    public void run() {
        try {
            while (true) {
                torRysowany.repaint();
                Thread.sleep(iloscSpania);
            }
        } catch (InterruptedException e) {
            // You can print error here if needed
        }
    }
}
