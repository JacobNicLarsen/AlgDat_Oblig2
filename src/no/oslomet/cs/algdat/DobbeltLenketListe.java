package no.oslomet.cs.algdat;

import java.util.*;

public class DobbeltLenketListe<T> implements Liste<T>
{
    private static final class Node<T>   // en indre nodeklasse
    {
        // instansvariabler
        private T verdi;
        private Node<T> forrige, neste;

        private Node(T verdi, Node<T> forrige, Node<T> neste)  // konstruktør
        {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        protected Node(T verdi)  // konstruktør
        {
            this(verdi, null, null);
        }

    } // Node

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;   // antall endringer i listen

    // hjelpemetode
    private Node<T> finnNode(int indeks)
    {
        Node<T> p;
        if(indeks < antall/2){
            p = hode;
            for(int i = 0; i < indeks; i++) p = p.neste;

        }
        else{
             p = hale;
            for (int i = antall - 1; i > indeks ; i--) p = p.forrige;
        }

        return p;
    }

    // konstruktør
    public DobbeltLenketListe()
    {
        hode = hale = null;
        antall = 0;
        endringer = 0;
    }

    // konstruktør
    public DobbeltLenketListe(T[] a)
    {
        this();  // alle variabelene er nullet

        // Finner den første i a som ikke er null
        int i = 0; for (; i < a.length && a[i] == null; i++);

        if (i < a.length)
        {
            Node<T> p = hode = hale = new Node<>(a[i], null,null);  // den første noden
            antall = 1;                                 // vi har minst en node

            for (i++; i < a.length; i++)
            {
                if (a[i] != null)
                {
                    p = p.neste  =  new Node<>(a[i],p, null);   // en ny node

                    antall++;
                }
                //System.out.println("Legger til node " + a[i] + " Neste node er: "  + " forrige node er: " + p.forrige.verdi);
            }
            hale = p;
        }


    }

    // subliste
    public Liste<T> subliste(int fra, int til)
    {
        fratilKontroll(antall,fra,til);
        Node<T> p = finnNode(fra);
        Liste<T> h = new DobbeltLenketListe<T>();
        for (int i = fra; i < til; i++) {
            h.leggInn(p.verdi);
            p = p.neste;
        }
        return h;
    }

    private void fratilKontroll(int antall, int fra, int til)
    {
        if (fra < 0)                                  // fra er negativ
            throw new IndexOutOfBoundsException
                    ("fra(" + fra + ") er negativ!");

        if (til > antall)                          // til er utenfor tabellen
            throw new IndexOutOfBoundsException
                    ("til(" + til + ") > antall(" + antall + ")");

        if (fra > til)                                // fra er større enn til
            throw new IllegalArgumentException
                    ("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
    }


    @Override
    public int antall()
    {
        return antall;
    }

    @Override
    public boolean tom()
    {
        return antall == 0;
    }

    @Override
    public boolean leggInn(T verdi)
    {
        Objects.requireNonNull(verdi);

        if(tom()) hode = hale = new Node<>(verdi, null, null);
        else hale = hale.neste = new Node<>(verdi,hale, null);
        antall ++;
        endringer++;
        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi)
    {
        Objects.requireNonNull(verdi);
        indeksKontroll(indeks,true);

        if(indeks==0){
            if (antall == 0) hode = hale = new Node<>(verdi,null,null);
            else {
                Node<T> p = hode;
                hode = new Node<>(verdi, null, hode);
                p.forrige = hode;
            }

        }
        else if(indeks == antall){
            hale = hale.neste = new Node<>(verdi,hale, null);
        }
        else{
            Node<T> p = hode;
            Node<T> q = hode;
            for (int i = 1; i < indeks; i++)p = p.neste;
            for(int i = 1; i < indeks + 1; i++) q = q.neste;

            Node<T> r = new Node<T>(verdi,p,q);
            p.neste = q.forrige = r;
        }

        endringer++;
        antall++;
    }

    @Override
    public boolean inneholder(T verdi)
    {

        return indeksTil(verdi) != -1;
    }

    @Override
    public T hent(int indeks)
    {
        indeksKontroll(indeks,false);

        return  finnNode(indeks).verdi;

    }

    @Override
    public int indeksTil(T verdi)
    {
        if(verdi == null) return -1;
        Node<T> p = hode;
        for (int i = 0; i < antall; i++) {
            if(p.verdi.equals(verdi)) return i;
            p = p.neste;
        }
        return -1;
    }

    @Override
    public T oppdater(int indeks, T nyverdi)
    {
        indeksKontroll(indeks,false);
        Objects.requireNonNull(nyverdi);

        Node<T> p = finnNode(indeks);
        T gammelverdi = p.verdi;

        p.verdi = nyverdi;

        endringer++;
        return gammelverdi;
    }

    @Override
    public boolean fjern(T verdi)
    {
        if(verdi == null) return false;

        Node<T> p = hode, q = null, r = null;



        while (p != null){
            if(p.verdi.equals(verdi)) break;
            q = r;
            r = p;
            p = p.neste;

        }
        if(p == null) return false;

        if(antall == 1){
            hode = hale = null;
            antall--;
            return true;
        }


        if(p == hode){
            hode = hode.neste;
            hode.forrige = null;
        }
        else if(p == hale){
            hale = hale.forrige;
            hale.neste = null;
        }
        else{
            q = r;
            r = p;
            p = p.neste;

            q.neste = p;
            p.forrige = q;
            r.neste = r.forrige = null;
        }



        antall--;
        endringer++;

        return true;
    }

    @Override
    public T fjern(int indeks)
    {
        T temp;
        indeksKontroll(indeks,false);


        if(indeks == 0){

            temp = hode.verdi;
            if(antall == 1){
                hode = hale = null;
                endringer++;
                antall --;
                return temp;
            }
            //System.out.println("Hei hei");
            hode = hode.neste;
            hode.forrige = null;
        }
        else{

            Node<T> p = finnNode(indeks - 1), r = p.neste;
            temp = r.verdi;
            if(r == hale){
                hale = hale.forrige;
                p.neste = null;
                antall--;
                endringer++;
                return temp;
            }
            Node<T> q = r.neste;
            p.neste = q;
            q.forrige = p;
            r.neste = r.forrige = null;
        }

        endringer++;
        antall--;

        return temp;
    }

    @Override
    public void nullstill()
    {

        Node<T> p = hode, q;

        for (int i = 0; i < antall-1; i++) {
            q=p.neste;
            p.neste = q.forrige = null;
            p.verdi = null;
            p = q;
        }

        hode = hale = null;
        antall = 0;
        endringer++;

    }

    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();

        s.append('[');

        if(!tom()){
            Node<T> p = hode;
            s.append(p.verdi);

            p = p.neste;



            while (p!=null){
                s.append(',').append(' ').append(p.verdi);
                p = p.neste;
            }
        }
        s.append(']');


        return s.toString();

    }

    public String omvendtString()
    {
        StringBuilder s = new StringBuilder();

        s.append('[');

        if(!tom()){
            Node<T> p = hale;
            s.append(p.verdi);

            p = p.forrige;


            while (p!=null){
                s.append(',').append(' ').append(p.verdi);
                p = p.forrige;
            }
        }
        s.append(']');


        return s.toString();

    }

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c)
    {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public Iterator<T> iterator()
    {
        return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks)
    {
        indeksKontroll(indeks,false);
        return new DobbeltLenketListeIterator(indeks);
    }

    private class DobbeltLenketListeIterator implements Iterator<T>
    {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator()
        {
            denne = hode;     // denne starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks)
        {
            denne = finnNode(indeks);
            fjernOK = false;
            iteratorendringer = endringer;
        }

        @Override
        public boolean hasNext()
        {
            return denne != null;  // denne koden skal ikke endres!
        }

        @Override
        public T next()
        {
            if(iteratorendringer != endringer)
                throw new ConcurrentModificationException("Listen er endret");

            if(!hasNext())
                throw new NoSuchElementException("Har ikke neste");

            T denneVerdi = denne.verdi;
            fjernOK = true;
            denne = denne.neste;

            return denneVerdi;

        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

    } // DobbeltLenketListeIterator

} // DobbeltLenketListe
