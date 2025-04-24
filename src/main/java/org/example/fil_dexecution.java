package org.example;

public class fil_dexecution extends Thread {

    Tor lemans;
    int numer_samochodu;

    int iloscOkrazenDoZrobienia;

    boolean przenoszenie = false;

    public fil_dexecution(Tor lemans, int numer_samochodu) {
        this.lemans = lemans;
        this.numer_samochodu = numer_samochodu;
        this.iloscOkrazenDoZrobienia = lemans.iloscOkrazenDoZrobienia;

        this.lemans.poruszaSie = true;
    }

    public void stopThread() {
        lemans.poruszaSie = false;
    }

    @Override
    public void run() {
        try {
            while (lemans.poruszaSie && lemans.zbior_samochod.elementAt(numer_samochodu).iloscPrzejechnychOkrazen() < iloscOkrazenDoZrobienia) {
                lemans.zbior_samochod.elementAt(numer_samochodu).obliczane_pozycji();

                //lemans.repaint();
                Thread.sleep(100);
            }

            if(lemans.poruszaSie){

                int przesuniecieX = lemans.ileSkonczylo/20;
                int przesuniecieY = lemans.ileSkonczylo%20;

                lemans.zbior_samochod.elementAt(numer_samochodu).przeniesAuto(400+przesuniecieX*20, 100 + przesuniecieY*20);
                lemans.ileSkonczylo++;
            }


        } catch (InterruptedException e) {
            // You can print error here if needed
        }
    }
}