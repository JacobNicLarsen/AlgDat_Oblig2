package no.oslomet.cs.algdat;

public class Main {

    public static void main(String[] args) {
        /*
        DobbeltLenketListe<Integer> liste = new DobbeltLenketListe<>();
        System.out.println(liste.toString() + ""+ liste.omvendtString());
        for(int i = 1; i <= 3; i++){
            liste.leggInn(i);
            System.out.println(liste.toString() + ""+ liste.omvendtString());
        }
        */
        DobbeltLenketListe<Integer> liste2 = new DobbeltLenketListe<>();
        liste2.leggInn(1);
        liste2.leggInn(2);
        liste2.leggInn(3);
        liste2.leggInn(4);
        liste2.leggInn(5);
        liste2.leggInn(6);
        liste2.leggInn(7);
        liste2.leggInn(8);
        liste2.leggInn(9);
        liste2.leggInn(10);
        liste2.leggInn(11);
        liste2.leggInn(12);
        liste2.leggInn(13);
        liste2.leggInn(14);


            System.out.println(liste2.hent(13));
    }
}
