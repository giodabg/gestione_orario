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
public class ListaClassi {
    List scrollList;
    Vector classi;

    ListaClassi() {
        scrollList = new List();
        classi = new Vector();
    }

    public void add(Classe c) {
        classi.add(c);
        scrollList.add(c.toString());
    }

    public Classe get(String classe){

        int i = classi.size()-1;
        if (i == -1)
            return null;
        
        Classe c  = (Classe) classi.get(i);
        while ((i >= 0) && (c.nome.compareToIgnoreCase(classe) != 0)) {
            c  = (Classe) classi.get(i);
            i--;
        }
        if (c.nome.compareToIgnoreCase(classe)== 0) {
            return c;
        }
        else
            return null;
    }

    public void paint(Graphics g, int x, int y, boolean tutte) {
        int i;
        Classe c;

        for (i=0; i< classi.size(); i++) {
            c = (Classe) classi.get(i);
            if (c != null) {
                c.paint(g, x, y, tutte);
                y += c.hPaint(tutte) + 10;
            }
        }
    }

    public int hPaint(boolean tutte) {
        int i, h = 0;
        Classe c;

        for (i=0; i < classi.size(); i++) {
            c = (Classe) classi.get(i);
            if (c != null)
                h += c.hPaint(tutte) + 10;
        }
        return h;
    }
}
