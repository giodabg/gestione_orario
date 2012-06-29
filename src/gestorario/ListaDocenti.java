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
public class ListaDocenti {
    List scrollList;
    Vector docenti;

    ListaDocenti(ListaInfoMatDocClassAule materieDocenti) {
        scrollList = new List();
        docenti = new Vector();
    }

    public void add(Docente d) {
        docenti.add(d);
        scrollList.add(d.toString());
    }

    public Docente get(String nomeDocente){

        int i = docenti.size()-1;
        if (i == -1)
            return null;

        Docente d  = (Docente) docenti.get(i);
        while ((i >= 0) && (d.nome.compareToIgnoreCase(nomeDocente) != 0)) {
            d  = (Docente) docenti.get(i);
            i--;
        }
        if (d.nome.compareToIgnoreCase(nomeDocente)== 0) {
            return d;
        }
        else
            return null;
    }

    public void paint(Graphics g, int x, int y, boolean tutte) {
        int i;
        Docente d;

        for (i=0; i< docenti.size(); i++) {
            d = (Docente) docenti.get(i);
            if (d != null) {
                d.paint(g, x, y, tutte);
                y += d.hPaint(tutte) + 10;
            }
        }
    }

    public int hPaint(boolean tutte) {
        int i, h = 0;
        Docente d;

        for (i=0; i < docenti.size(); i++) {
            d = (Docente) docenti.get(i);
            if (d != null)
                h += d.hPaint(tutte) + 10;
        }
        return h;
    }
}
