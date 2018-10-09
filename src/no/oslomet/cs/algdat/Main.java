package no.oslomet.cs.algdat;

import java.util.Iterator;

public class Main {

    public static void main(String[] args) {


        DobbeltLenketListe<String> liste =new DobbeltLenketListe<>(new String[]{"Birger","Lars","Anders","Bodil","Kari","Per","Berit"});
        Iterator<String> p = liste.iterator(3);
        System.out.println(p.next());
        System.out.print(liste + " ");
        System.out.println(liste.omvendtString());

        p.remove();

        System.out.print(liste + " ");
        System.out.println(liste.omvendtString());

        //System.out.println(liste + ""+ liste.omvendtString());


    }
}
