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
public class Materia {

    static int lastID = 0;
    int    idMateria;
    String nome;
    String sigla;
    int    hFont;
    Color  colore;

    Materia(int id, String n, String s, Color c) {
        idMateria = id;
        nome = n;
        sigla = s;
        hFont = 20;
        colore = c;
    }

    Materia(String n, String s, Color c) {
        idMateria = lastID;
        nome = n;
        sigla = s;
        hFont = 20;
        colore = c;
    }

    public void paint (Graphics g, int x, int y, boolean tutte) {
        g.setColor(colore);
        if (tutte)
            g.drawString("ID: "+idMateria+" Nome: "+sigla+"-"+nome,  x, y);
        else
            g.drawString(idMateria+" Materia:"+nome,  x, y);
    }

    public int hPaint(boolean tutte) {
        if (tutte)
            return hFont;
        else
            return hFont;
    }

}
