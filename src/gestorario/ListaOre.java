/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorario;

import java.awt.*;  //grafica
import java.util.Vector;
import java.util.ArrayList;

/**
 *
 * @author Gio
 */
public class ListaOre {
    List    scrollList;
    Vector  listaOre;

    ListaOre() {
        scrollList = new List();
        listaOre = new Vector();
    }


    public void add(Ora d) {
        listaOre.add(d);
        scrollList.add(d.toString());
    }

    public void remove(Ora d) {
        listaOre.remove(d);
        scrollList.remove(d.toString());
        d = null;
    }

    public Ora get(int g, int s) {
        int i;
        boolean trovato = false;
        Ora o = null;
        for (i = 0; i < listaOre.size() && !trovato; i++) {
            o = (Ora) listaOre.get(i);
            if ((o.giorno == g) &&
                (o.spazio == s))
                trovato = true;
        }
        if (trovato)
            return o;
        else
            return null;
    }

    public void paint(Graphics g, int x, int y, boolean tutte) {
        int i;
        Ora o;

        for (i=0; i< listaOre.size(); i++) {
            o = (Ora) listaOre.get(i);
            o.paintInfo(g, x, y, tutte);
            y += o.hPaintInfo(tutte) + 10;
        }
    }

    public int hPaint(boolean tutte) {
        int i, h = 0;
        Ora o;

        for (i=0; i < listaOre.size(); i++) {
            o = (Ora) listaOre.get(i);
            h += o.hPaintInfo(tutte) + 10;
        }
        return h;
    }


/***************************************************
 *
    public void add(Ora o) {
        this.addOra(o);
        if (d != null)
            d.addOra(o);
        if (dLab != null)
            dLab.listaOre.addOra(o);
        if (a != null)
            a.listaOre.addOra(o);
    }

    public void init(Classe cl, Aula a, ListaMaterieDocenti materieDocenti) {
        // classe di cui si crea l'orario
        classe      = cl;

        if (cl.idClasse == 1) {
            init1A(cl, a, materieDocenti);
        } else if (cl.idClasse == 2) {
            init1B(cl, a, materieDocenti);
        }
    }


    private void init1A(Classe cl, Aula a, ListaMaterieDocenti materieDocenti) {
        Aula    aLab;
        Color   c, cLab;
        Materia m, mLab;
        Docente d, dLab;
        Ora     o, oColl;

        aLab          = new Aula(11, "Info LAB 1", 3, 1, 0, 1, "informatica", Color.YELLOW);

        // Lunedì
        c   = materieDocenti.getMateriaColor("Tecnologie informatiche");
        m   = materieDocenti.getMateria("Tecnologie informatiche");
        d   = materieDocenti.getDocente("Breviario Giovanni");

        o = new Ora(1, 1, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        o = new Ora(1, 2, classe, d, m, a, c);
        updateListeOre(o, d, null, a);

        cLab = materieDocenti.getMateriaColor("Laboratorio Tecnologie informatiche");
        mLab = materieDocenti.getMateria("Laboratorio Tecnologie informatiche");
        dLab = materieDocenti.getDocente("Comparin Giuseppe");

        o = new Ora(1, 3, classe, d, mLab, aLab, cLab);
        o.docenti.add(dLab);
        updateListeOre(o, d, dLab, a);
        o = new Ora(1, 4, classe, GestOrarioApplet.d0, GestOrarioApplet.m0,
                GestOrarioApplet.a0, GestOrarioApplet.c0);
        updateListeOre(o, null, null, null);
        o = new Ora(1, 5, classe, GestOrarioApplet.d0, GestOrarioApplet.m0,
                GestOrarioApplet.a0, GestOrarioApplet.c0);
        updateListeOre(o, null, null, null);
        o = new Ora(1, 6, classe, GestOrarioApplet.d0, GestOrarioApplet.m0,
                GestOrarioApplet.a0, GestOrarioApplet.c0);
        updateListeOre(o, null, null, null);


        // Martedì
        c           = materieDocenti.getMateriaColor("Lingua e letteratura italiana");
        m           = materieDocenti.getMateria("Lingua e letteratura italiana");
        d           = materieDocenti.getDocente("De Sisto Lucio");
        o = new Ora(2, 1, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        o = new Ora(2, 2, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        o = new Ora(2, 3, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        o = new Ora(2, 4, classe, d, m, a, c);
        updateListeOre(o, d, null, a);

        c           = materieDocenti.getMateriaColor("Storia, Cittadinanza e Costituzione");
        m           = materieDocenti.getMateria("Storia, Cittadinanza e Costituzione");
        oColl = new Ora(2, 5, classe, d, m, a, c);
        updateListeOre(oColl, d, null, a);
        o.setSucc(oColl);
        oColl.setPrec(o);
        o = new Ora(2, 6, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        o.setPrec(oColl);
        oColl.setSucc(o);

        // Meroledì
        c           = materieDocenti.getMateriaColor("Scienze integrate (Chimica)");
        m           = materieDocenti.getMateria("Scienze integrate (Chimica)");
        d           = materieDocenti.getDocente("Cereda Valeria");
        cLab        = materieDocenti.getMateriaColor("Laboratorio Scienze integrate (Chimica)");
        mLab        = materieDocenti.getMateria("Laboratorio Scienze integrate (Chimica)");
        dLab        = materieDocenti.getDocente("Marzolo Dario");
        o = new Ora(3, 1, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        o = new Ora(3, 2, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        o = new Ora(3, 3, classe, d, mLab, aLab, cLab);
        o.docenti.add(dLab);
        updateListeOre(o, d, dLab, aLab);

        c           = materieDocenti.getMateriaColor("Lingua Inglese");
        m           = materieDocenti.getMateria("Lingua Inglese");
        d           = materieDocenti.getDocente("Borgonovo Alessandra");
        o = new Ora(3, 4, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        o = new Ora(3, 5, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        o = new Ora(3, 6, classe, d, m, a, c);
        updateListeOre(o, d, null, a);

        // Giovedì

        c           = materieDocenti.getMateriaColor("Scienze motorie e sportive");
        m           = materieDocenti.getMateria("Scienze motorie e sportive");
        d           = materieDocenti.getDocente("Gadina Paolo");
        o = new Ora(4, 1, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        oColl = new Ora(4, 2, classe, d, m, a, c);
        updateListeOre(oColl, d, null, a);
        o.setSucc(oColl);
        oColl.setPrec(o);
 
        c           = materieDocenti.getMateriaColor("Matematica");
        m           = materieDocenti.getMateria("Matematica");
        d           = materieDocenti.getDocente("Colombo Simona");
        o = new Ora(4, 3, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        oColl = new Ora(4, 4, classe, d, m, a, c);
        updateListeOre(oColl, d, null, a);
        o.setSucc(oColl);
        oColl.setPrec(o);
        o = new Ora(4, 5, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        o = new Ora(4, 6, classe, d, m, a, c);
        updateListeOre(o, d, null, a);

        // Venerdì

        c           = materieDocenti.getMateriaColor("Diritto ed economia");
        m           = materieDocenti.getMateria("Diritto ed economia");
        d           = materieDocenti.getDocente("Monti Emanulela");
        o = new Ora(5, 1, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        o = new Ora(5, 2, classe, d, m, a, c);
        updateListeOre(o, d, null, a);

        c           = materieDocenti.getMateriaColor("Scienze integrate (Scienze della Terra e Biologia)");
        m           = materieDocenti.getMateria("Scienze integrate (Scienze della Terra e Biologia)");
        d           = materieDocenti.getDocente("Rossi Paolo");
        o = new Ora(5, 3, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        o = new Ora(5, 4, classe, d, m, a, c);
        updateListeOre(o, d, null, a);

        c           = materieDocenti.getMateriaColor("Religione Cattolica o attività alternative");
        m           = materieDocenti.getMateria("Religione Cattolica o attività alternative");
        d           = materieDocenti.getDocente("Don Adriano");
        o = new Ora(5, 5, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        o = new Ora(5, 6, classe, GestOrarioApplet.d0, GestOrarioApplet.m0,
                GestOrarioApplet.a0, GestOrarioApplet.c0);
        updateListeOre(o, null, null, null);


        // Sabato
        c           = materieDocenti.getMateriaColor("Scienze integrate (Fisica)");
        m           = materieDocenti.getMateria("Scienze integrate (Fisica)");
        d           = materieDocenti.getDocente("Fratta Donato");
        cLab        = materieDocenti.getMateriaColor("Laboratorio Scienze integrate (Fisica)");
        mLab        = materieDocenti.getMateria("Laboratorio Scienze integrate (Fisica)");
        dLab        = materieDocenti.getDocente("Liveriero Francesca");
        o = new Ora(6, 1, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        o = new Ora(6, 2, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        o = new Ora(6, 3, classe, d, mLab, aLab, cLab);
        o.docenti.add(dLab);
        updateListeOre(o, d, dLab, aLab);

        c           = materieDocenti.getMateriaColor("Tecnologie e tecniche di rappresentazione grafica");
        m           = materieDocenti.getMateria("Tecnologie e tecniche di rappresentazione grafica");
        d           = materieDocenti.getDocente("Arnaboldi");
        cLab        = materieDocenti.getMateriaColor("Laboratorio Tecnologie e tecniche di rappresentazione grafica");
        mLab        = materieDocenti.getMateria("Laboratorio Tecnologie e tecniche di rappresentazione grafica");

        o = new Ora(6, 4, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        o = new Ora(6, 5, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        o = new Ora(6, 6, classe, d, mLab, aLab, cLab);
        updateListeOre(o, d, null, aLab);
    }

    private void init1B(Classe cl, Aula a, ListaMaterieDocenti materieDocenti) {
        int     idDoc = 0;
        Aula    aLab;
        Color   c, cLab;
        Materia m, mLab;
        Docente d, dLab;
        Ora     o, oColl;

        aLab          = new Aula(12, "Info LAB 2", 3, 1, 0, 1, "sistemi", Color.YELLOW);

        // Lunedì
        c           = materieDocenti.getMateriaColor("Scienze integrate (Fisica)");
        m           = materieDocenti.getMateria("Scienze integrate (Fisica)");
        d           = materieDocenti.getDocente("Fratta Donato");
        cLab        = materieDocenti.getMateriaColor("Laboratorio Scienze integrate (Fisica)");
        mLab        = materieDocenti.getMateria("Laboratorio Scienze integrate (Fisica)");
        dLab        = materieDocenti.getDocente("Liveriero Francesca");
        o = new Ora(1, 1, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        o = new Ora(1, 2, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        o = new Ora(1, 3, classe, d, mLab, aLab, cLab);
        o.docenti.add(dLab);
        updateListeOre(o, d, dLab, aLab);

        c           = materieDocenti.getMateriaColor("Tecnologie e tecniche di rappresentazione grafica");
        m           = materieDocenti.getMateria("Tecnologie e tecniche di rappresentazione grafica");
        d           = materieDocenti.getDocente("Arnaboldi");
        cLab        = materieDocenti.getMateriaColor("Laboratorio Tecnologie e tecniche di rappresentazione grafica");
        mLab        = materieDocenti.getMateria("Laboratorio Tecnologie e tecniche di rappresentazione grafica");

        o = new Ora(1, 4, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        o = new Ora(1, 5, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        o = new Ora(1, 6, classe, d, mLab, aLab, cLab);
        updateListeOre(o, d, null, aLab);

        // Martedì
        c   = materieDocenti.getMateriaColor("Tecnologie informatiche");
        m   = materieDocenti.getMateria("Tecnologie informatiche");
        d   = materieDocenti.getDocente("Breviario Giovanni");

        o = new Ora(2, 1, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        o = new Ora(2, 2, classe, d, m, a, c);
        updateListeOre(o, d, null, a);

        cLab = materieDocenti.getMateriaColor("Laboratorio Tecnologie informatiche");
        mLab = materieDocenti.getMateria("Laboratorio Tecnologie informatiche");
        dLab = materieDocenti.getDocente("Comparin Giuseppe");

        o = new Ora(2, 3, classe, d, mLab, aLab, cLab);
        o.docenti.add(dLab);
        updateListeOre(o, d, dLab, a);
        o = new Ora(2, 4, classe, GestOrarioApplet.d0, GestOrarioApplet.m0,
                GestOrarioApplet.a0, GestOrarioApplet.c0);
        updateListeOre(o, null, null, null);
        o = new Ora(2, 5, classe, GestOrarioApplet.d0, GestOrarioApplet.m0,
                GestOrarioApplet.a0, GestOrarioApplet.c0);
        updateListeOre(o, null, null, null);
        o = new Ora(2, 6, classe, GestOrarioApplet.d0, GestOrarioApplet.m0,
                GestOrarioApplet.a0, GestOrarioApplet.c0);
        updateListeOre(o, null, null, null);


        // Mercoledì
        c           = materieDocenti.getMateriaColor("Lingua e letteratura italiana");
        m           = materieDocenti.getMateria("Lingua e letteratura italiana");
        d           = materieDocenti.getDocente("De Sisto Lucio");
        o = new Ora(3, 1, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        o = new Ora(3, 2, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        o = new Ora(3, 3, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        o = new Ora(3, 4, classe, d, m, a, c);
        updateListeOre(o, d, null, a);

        c           = materieDocenti.getMateriaColor("Storia, Cittadinanza e Costituzione");
        m           = materieDocenti.getMateria("Storia, Cittadinanza e Costituzione");
        oColl = new Ora(3, 5, classe, d, m, a, c);
        updateListeOre(oColl, d, null, a);
        o.setSucc(oColl);
        oColl.setPrec(o);
        o = new Ora(3, 6, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        o.setPrec(oColl);
        oColl.setSucc(o);

        // Giovedì
        c           = materieDocenti.getMateriaColor("Scienze integrate (Chimica)");
        m           = materieDocenti.getMateria("Scienze integrate (Chimica)");
        d           = materieDocenti.getDocente("Cereda Valeria");
        cLab        = materieDocenti.getMateriaColor("Laboratorio Scienze integrate (Chimica)");
        mLab        = materieDocenti.getMateria("Laboratorio Scienze integrate (Chimica)");
        dLab        = materieDocenti.getDocente("Marzolo Dario");
        o = new Ora(4, 1, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        o = new Ora(4, 2, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        o = new Ora(4, 3, classe, d, mLab, aLab, cLab);
        o.docenti.add(dLab);
        updateListeOre(o, d, dLab, aLab);

        c           = materieDocenti.getMateriaColor("Lingua Inglese");
        m           = materieDocenti.getMateria("Lingua Inglese");
        d           = materieDocenti.getDocente("Borgonovo Alessandra");
        o = new Ora(4, 4, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        o = new Ora(4, 5, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        o = new Ora(4, 6, classe, d, m, a, c);
        updateListeOre(o, d, null, a);

        // Venerdì

        c           = materieDocenti.getMateriaColor("Scienze motorie e sportive");
        m           = materieDocenti.getMateria("Scienze motorie e sportive");
        d           = materieDocenti.getDocente("Gadina Paolo");
        o = new Ora(5, 1, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        oColl = new Ora(5, 2, classe, d, m, a, c);
        updateListeOre(oColl, d, null, a);
        o.setSucc(oColl);
        oColl.setPrec(o);

        c           = materieDocenti.getMateriaColor("Matematica");
        m           = materieDocenti.getMateria("Matematica");
        d           = materieDocenti.getDocente("Colombo Simona");
        o = new Ora(5, 3, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        oColl = new Ora(5, 4, classe, d, m, a, c);
        updateListeOre(oColl, d, null, a);
        o.setSucc(oColl);
        oColl.setPrec(o);
        o = new Ora(5, 5, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        o = new Ora(5, 6, classe, d, m, a, c);
        updateListeOre(o, d, null, a);

        // Sabato

        c           = materieDocenti.getMateriaColor("Diritto ed economia");
        m           = materieDocenti.getMateria("Diritto ed economia");
        d           = materieDocenti.getDocente("Monti Emanulela");
        o = new Ora(6, 1, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        o = new Ora(6, 2, classe, d, m, a, c);
        updateListeOre(o, d, null, a);

        c           = materieDocenti.getMateriaColor("Scienze integrate (Scienze della Terra e Biologia)");
        m           = materieDocenti.getMateria("Scienze integrate (Scienze della Terra e Biologia)");
        d           = materieDocenti.getDocente("Rossi Paolo");
        o = new Ora(6, 3, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        o = new Ora(6, 4, classe, d, m, a, c);
        updateListeOre(o, d, null, a);

        c           = materieDocenti.getMateriaColor("Religione Cattolica o attività alternative");
        m           = materieDocenti.getMateria("Religione Cattolica o attività alternative");
        d           = materieDocenti.getDocente("Don Adriano");
        o = new Ora(6, 5, classe, d, m, a, c);
        updateListeOre(o, d, null, a);
        o = new Ora(6, 6, classe, GestOrarioApplet.d0, GestOrarioApplet.m0,
                GestOrarioApplet.a0, GestOrarioApplet.c0);
        updateListeOre(o, null, null, null);
    }
 * @param o
 */
}
