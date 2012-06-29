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
public class ListaAule {
    List scrollList;
    Vector aule;

    ListaAule() {
        scrollList = new List();
        aule = new Vector();
    }

    public void add(Aula a) {
        aule.add(a);
        scrollList.add(a.toString());
    }

    public Aula get(String aula){

        int i = aule.size()-1;
        if (i == -1)
            return null;

        Aula a  = (Aula) aule.get(i);
        while ((i >= 0) && (a.nome.compareToIgnoreCase(aula) != 0)) {
            a  = (Aula) aule.get(i);
            i--;
        }
        if (a.nome.compareToIgnoreCase(aula)== 0) {
            return a;
        }
        else
            return null;
    }


    public void paint(Graphics g, int x, int y, boolean tutte) {
        int i;
        Aula a;

        for (i=0; i< aule.size(); i++) {
            a = (Aula) aule.get(i);
            if (a != null) {
                a.paint(g, x, y, tutte);
                y += a.hPaint(tutte) + 10;
            }
        }
    }

    public int hPaint(boolean tutte) {
        int i, h = 0;
        Aula a;

        for (i=0; i < aule.size(); i++) {
            a = (Aula) aule.get(i);
            if (a != null)
                h += a.hPaint(tutte) + 10;
        }
        return h;
    }
}
