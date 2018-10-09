package no.oslomet.cs.algdat;

import java.util.Iterator;

public class Main {

    public static void main(String[] args) {


        DobbeltLenketListe<String> liste =new DobbeltLenketListe<>(new String[]{"Birger","Lars","Anders","Bodil","Kari","Per","Berit"});liste.fjernHvis(navn -> navn.charAt(0) == 'B');
        // fjerner navn som starter med B
        System.out.println(liste + ""+ liste.omvendtString());
        // Utskrift: [Lars, Anders, Kari, Per] [Per,Kari, Anders, Lars]

    }
}
