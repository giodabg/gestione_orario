/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorario;

import java.util.Vector;

/**
 *
 * @author Gio
 */
public class OraLibera extends OraInt {
    protected Vector    classi;     // classi che hanno questa ora libera
    protected Vector    docenti;    // docenti che hanno questa ora libera
    protected Vector    aule;       // aule che hanno questa ora libera

    OraLibera() {
        classi      = new Vector();
        docenti     = new Vector();
        aule        = new Vector();
    }

    OraLibera(int g, int sp) {
        this.set(g, sp);
        classi      = new Vector();
        docenti     = new Vector();
        aule        = new Vector();
    }

    public boolean addClasse(Classe c) {
            if ((c != null) && (classi.indexOf(c) != -1))
                return classi.add(c);
            else
                return false;
    }

    public boolean addDocente(Docente d) {
            if ((d != null) && (docenti.indexOf(d) != -1))
                return docenti.add(d);
            else
                return false;
    }

    public boolean addAula(Aula a) {
            if ((a != null) && (aule.indexOf(a) != -1))
                return aule.add(a);
            else
                return false;
    }

    public boolean add(Ora o) {
        if ((o.giorno == this.giorno() && (o.spazio == this.spazio()))) {
            if ((o.classe != null) && (classi.indexOf(o.classe) != -1))
                classi.add(o.classe);
            if ((o.docente != null) && (docenti.indexOf(o.docente) != -1))
                docenti.add(o.docente);
            if ((o.aula != null) && (aule.indexOf(o.aula) != -1))
                aule.add(o.aula);

            return true;
        }
        else
            return false;
    }

    public boolean removeClasse(Classe c) {
        return classi.remove(c);
    }

    public boolean removeDocente(Docente d) {
        return docenti.remove(d);
    }

    public boolean removeAula(Aula a) {
        return aule.remove(a);
    }

    public Vector getClassiLibereOra() {
        return classi;
    }

    public Vector getDocentiLiberiOra() {
        return classi;
    }

    public Vector getAuleLibereOra() {
        return aule;
    }

    public boolean classeLibera(Classe c) {
            int i = classi.indexOf(c);
            if (i != -1)
                return true;
            else
                return false;
    }

    public boolean docenteLibero(Docente d) {
            int i = docenti.indexOf(d);
            if (i != -1)
                return true;
            else
                return false;
    }

    public boolean aulaLibera(Aula a) {
            int i = aule.indexOf(a);
            if (i != -1)
                return true;
            else
                return false;
    }
}
