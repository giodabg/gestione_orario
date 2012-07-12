/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorario;

import java.awt.*;  //grafica

 /**
 *
 * @author Gio
 */
public class Materia {

    static int lastID = 0;      // ultimo id utilizzato unico per tutte le aule
    int    idMateria;           // identificatore univoco della materia
    String nome;                // nome della materia
    String sigla;               // abbreviazione
    int    hFont;               // carattere per le visualizzazioni
    Color  colore;              // colore per le visualizzazioni

    Materia() {
        idMateria = lastID; lastID++;
        nome = "";
        sigla = "";
        hFont = 20;
        colore = Color.darkGray;
    }

    Materia(String n, String s, Color c) {
        idMateria = lastID; lastID++;
        nome = n;
        sigla = s;
        hFont = 20;
        colore = c;
    }

    Materia(int id, String n, String s, Color c) {
        idMateria = id;
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
