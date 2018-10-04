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
        DobbeltLenketListe<Integer> liste2 = new DobbeltLenketListe<>(new Integer[] {1,6,33,31,43,42,2});

            System.out.println(liste2.omvendtString());
    }
}
