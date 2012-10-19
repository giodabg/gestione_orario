/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorario;

import java.awt.*;  //grafica
import java.util.Vector;
import javax.swing.*; // dialog box

/**
 *
 * @author Gio
 */
public class Docente {
    public final static int TEORICO = 0;
    public final static int ITP     = 1;

    static int  lastID = 0;
    int         idDocente;
    String      nome;
    int         tipo;       // 0=teorico, 1=ITP
    Vector      materie;    // materie insegnate
    Vector      oreLibere;  // ore libere comprensive delle ore a disposizione
                            // ma non comprensive di eventuali ore libere bloccate
                            // e delle ore del giorno libero
    ListaOre    listaOre;   // ore di lezione comprensive di eventuali ore di lezione bloccate
    ListaOre    listaOreBloccate;   // ore di lezione o libere bloccate escluse quelle del
                                    // giorno libero
    int         giornoLibero;

    int         hFont;
    Color       colore;

    Docente() {
        idDocente = lastID; lastID++;
        nome      = "";
        tipo      = 0;
        materie   = new Vector();
        oreLibere = new Vector();
        listaOre  = new ListaOre();
        listaOreBloccate = new ListaOre();
        /*
        for (int g=1; g <= GestOrarioApplet.maxNumGiorni; g++)
        for (int s=1; s <= GestOrarioApplet.maxNumSpazi; s++)
        listaOre.addOra(new OraGraph(g, s, null, GestOrarioApplet.d0,
        GestOrarioApplet.m0, GestOrarioApplet.a0, GestOrarioApplet.c0));
        */
        giornoLibero = -1;
        hFont = 20;
        colore = Color.BLACK;
    }

    Docente(String n, Materia m, int t, Color c) {
        idDocente = lastID; lastID++;
        nome      = n;
        tipo      = t;
        materie   = new Vector();
        materie.add(m);
        oreLibere = new Vector();
        listaOre  = new ListaOre();
        listaOreBloccate = new ListaOre();
        /*
        for (int g=1; g <= GestOrarioApplet.maxNumGiorni; g++)
        for (int s=1; s <= GestOrarioApplet.maxNumSpazi; s++)
        listaOre.addOra(new OraGraph(g, s, null, GestOrarioApplet.d0,
        GestOrarioApplet.m0, GestOrarioApplet.a0, GestOrarioApplet.c0));
        */
        giornoLibero = -1;
        hFont = 20;
        colore = c;
    }

    Docente(int id, String n, Materia m, int t, int gl, Color c) {
        idDocente = id;
        nome = n;
        tipo = t;
        materie = new Vector();
        materie.add(m);
        oreLibere = new Vector();
        listaOre = new ListaOre();
        listaOreBloccate = new ListaOre();

        giornoLibero = gl;

        hFont = 20;
        colore = c;
    }

    public void addOraBloccata(int g, int sp) {
        OraGraph o = new OraGraph(g, sp, true, false);
        if (!listaOreBloccate.add(o)) {
            String str = "Per "+nome+" nella classe "+o.classe.nome+", nel giorno "+g+" l'ora "+sp+" è già bloccata.";
            System.out.println(str);
            JOptionPane.showMessageDialog(null, str);
        }
    }

    public OraGraph getBloccata(int g, int sp) {
        return listaOreBloccate.get(g, sp);
    }

    public void setGiornoLibero(int gl) {
        giornoLibero = gl;
        for (int s=1; s <= GestOrarioApplet.maxNumSpazi; s++) {
            OraLibera ol;
            ol = (OraLibera) GestOrarioApplet.infoMatDocAule.infoOreLibere.get(
                    (gl-1)*GestOrarioApplet.maxNumSpazi+(s-1));
            ol.removeDocente(this);
            oreLibere.remove(ol);
        }
    }

    public int getGiornoLibero() {
        return giornoLibero;
    }

    public void addMateria(Materia m) {
        materie.add(m);
    }

    public Materia getMateria() {
        return (Materia) materie.get(0);
    }

    public Materia getMateriaLab() {
        Materia m = null;
        try {
            m = (Materia) materie.get(1);
            return m;
        } catch (Exception e) {
            return (Materia) materie.get(0);
        }         
    }

    public void addOraLibera(int g, int s) {
        OraLibera ol;
        ol = (OraLibera) GestOrarioApplet.infoMatDocAule.infoOreLibere.get(
                (g-1)*GestOrarioApplet.maxNumSpazi+(s-1));
        if (ol != null) {
            ol.addDocente(this);
            oreLibere.add(ol);
        }
        else {
            String str = nome+" non può avere nel giorno "+g+" l'ora "+s+" come libera.";
            System.out.println(str);
            JOptionPane.showMessageDialog(null, str);
        }
    }

    public void addOraDisposizione(int g, int s) {
        OraLibera ol;
        ol = (OraLibera) GestOrarioApplet.infoMatDocAule.infoOreLibere.get(
                (g-1)*GestOrarioApplet.maxNumSpazi+(s-1));
        if (ol != null) {
            ol.addDocente(this);
            oreLibere.add(ol);
        }
        else {
            String str = nome+" non può avere nel giorno "+g+" l'ora "+s+" come a disposizione.";
            System.out.println(str);
            JOptionPane.showMessageDialog(null, str);
        }
    }

    public void paint (Graphics g, int x, int y, boolean tutte) {
        g.setColor(colore);
        hFont = g.getFont().getSize();
        if (tutte) {
            g.drawString("IDDoc: "+idDocente+" Nome: "+nome, x, y);
//            g.drawString("GiornoLibero: "
//                    +GestOrarioApplet.giorno2Str(giornoLibero, tutte), x, y+hFont);
            g.drawString("Materia: "+materie.toString(),     x, y+2*hFont);
        }
        else {
            g.drawString(idDocente+" Nome: "+nome, x, y);
        }
    }

    public int hPaint(boolean tutte) {
        if (tutte)
            return hFont* 3;
        else
            return hFont;
    }

    public String exportToCSV() {
       String line1 = "";
       String line2 = "";
       boolean aggiungiLine2 = false;
       OraGraph o;
       line1 += nome;
       line2 += "           ";
       for (int g=1; g <= 6; g++) {
           for (int s=1; s <= 6; s++) {
               if (g == giornoLibero) {
                   line1 += ", - ";
                   line2 += ",   ";
               }
               else {
                    o = listaOre.get(g, s);
                    if (o == null) {
                        if (listaOreBloccate.get(g, s) != null) {
                            line1 += ",   ";
                            line2 += ",   ";
                        }
                        else {
                            line1 += ", . ";
                            line2 += ",   ";
                        }
                    }
                    else {
                        line1 += ","+o.classe.nome;
                        if (o.classe.nome.equalsIgnoreCase(o.aula.nome)) {
                            line2 += ",   ";
                        }
                        else {
                            line2 += ","+o.aula.nome;
                            aggiungiLine2 = true;
                        }
                    }
               }
           }
        }
        if (aggiungiLine2)
            return line1+"\r\n"+line2;
        else
            return line1;
    }
}
