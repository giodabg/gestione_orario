/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorario;

import java.awt.*;  //grafica
import java.util.Vector;

    /**
 *
 * @author Gio
 */
public class Aula {

    static int lastID = 0;
    int    idAula;
    String nome;
    int    edificio;                // 1=ITC 2=ITIS 3=INF
    int    piano;
    int    lato;                    // 0=nord 1=est 2=sud 3=ovest
    int    tipo;                    // 0=normale 1=lab 2=palestra
    Vector materie;                 // materie a cui l'aula è adatta
    ListaOre    listaOre;
    int    hFont;
    Color  colore;

    Aula(String n) {
        idAula = lastID; lastID++;
        nome = n;
        edificio = 2;
        piano = 1;
        lato = 2;
        tipo = 0;
        materie = new Vector();
        listaOre = new ListaOre();
        hFont = 20;
        colore = Color.darkGray;
    }

    Aula(int id, String n, int e, int p, int l, int t, String m, Color c) {
        idAula = id;
        nome = n;
        edificio = e;
        piano = p;
        lato = l;
        tipo = t;
        materie = new Vector();
        materie.add(m);
        listaOre = new ListaOre();
        /*
        for (int g = 1; g <= GestOrarioApplet.maxNumGiorni; g++) {
        for (int s = 1; s <= GestOrarioApplet.maxNumSpazi; s++) {
        listaOre.addOra(new Ora(g, s, null, GestOrarioApplet.d0,
        GestOrarioApplet.m0, GestOrarioApplet.a0, GestOrarioApplet.c0));
        }
        }
        */
        hFont = 20;
        colore = c;
    }

    public void addMateria(String m) {
        if (!findMateria(m))
            materie.add(m);
    }

    public boolean usoUguale(Vector ma) {
        if (materie == ma) {
            return true;
        }
        else
            return false;
    }

    public boolean findMateria(String m) {
        int i;
        boolean trovato = false;
        String s = null;
        for (i = 0; i < materie.size() && !trovato; i++) {
            s = (String) materie.get(i);
            if (s.equalsIgnoreCase(m))
                trovato = true;
        }
        return trovato;
    }

    public void addOra(Ora o) {
        Ora oo = listaOre.get(o.giorno, o.spazio);
        if (oo != null)
            listaOre.remove(oo);
        oo = null;
        listaOre.add(o);
    }

    public void paint (Graphics g, int x, int y, boolean tutte) {
        g.setColor(colore);
        hFont = g.getFont().getSize();

        if (tutte) {
            g.drawString("AULA ID: "+idAula+ " Nome: "+nome,                 x, y);
            g.drawString("Ed:"+edificio+" Piano:"+piano+" Lato:"+lato,  x, y+hFont);
            g.drawString("Tipo: "+tipo+" Uso: "+materie.toString(),     x, y+2*hFont);
        }
        else {
            g.drawString("AULA:"+nome+"-"+tipo2String(tipo),  x, y);
        }
    }

    String tipo2String(int t) {
        switch (t) {
            case 0: return "aula";
            case 1: return "LAB";
            case 2: return "pal";
            default: return "Err";
        }
    }
    public int hPaint(boolean tutte) {
        if (tutte)
            return hFont* 3;
        else
            return hFont;
    }

}
