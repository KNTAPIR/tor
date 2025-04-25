package org.example;

public class RunnableCars implements Runnable {


    private Tor lemans;
    private int numer_samochodu;
    private final Object lockProvider2 = new Object();

    private int iloscOkrazenDoZrobienia;

    private boolean przenoszenie = false;

    public RunnableCars(Tor lemans, int numer_samochodu) {
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
            while (lemans.poruszaSie &&
                    lemans.zbior_samochod.elementAt(numer_samochodu).iloscPrzejechnychOkrazen() < iloscOkrazenDoZrobienia) {

                lemans.zbior_samochod.elementAt(numer_samochodu).obliczane_pozycji();
                Thread.sleep(100);
            }

            if (lemans.poruszaSie) {
                int przesuniecieX = lemans.ileSkonczylo / 20;
                int przesuniecieY = lemans.ileSkonczylo % 20;

                lemans.zbior_samochod.elementAt(numer_samochodu).przeniesAuto(400 + przesuniecieX * 20, 100 + przesuniecieY * 20);
                synchronized(lockProvider2) {
                    lemans.ileSkonczylo++;
                }

            }

        } catch (InterruptedException e) {
            System.out.println("Lubie jesc pieprz");
        }
    }



}
