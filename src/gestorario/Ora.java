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
public class Ora {
            int     giorno;
            int     spazio;  // 8-9 = 1°spazio
            Classe  classe;
    private Docente  docente;
    private Docente  docenteCom;
            boolean bloccata;
            Materia materia;
            Aula    aula;
    private Ora     succ; // se != null non deve separarsi dalla successiva
    private Ora     prec; // se != null non deve separarsi dalla precedente
            int     selezionata; // -2: no scambio -1: si scambio 0:no 1:prima 2:seconda
            Docente docenteConProblemi;
    private int     hFont;
    Color   colore;

    Ora(int g, int sp, boolean blocc) {
        giorno  = g;
        spazio  = sp;
        classe  = null;
        docente = null;
        docenteCom = null;
        bloccata = blocc;
        materia = null;
        aula    = null;
        succ    = null;
        prec    = null;
        selezionata = 0;
        docenteConProblemi = null;
        hFont   = 20;
        colore  = Color.GRAY;
    }

    Ora(int g, int sp, Classe cl, Materia m, Aula a, Color c) {
        giorno  = g;
        spazio  = sp;
        classe  = cl;
        classe.listaOre.add(this);
        docente = null;
        docenteCom = null;
        bloccata = false;
        materia = m;
        aula    = a;
        aula.listaOre.add(this);
        succ    = null;
        prec    = null;
        selezionata = 0;
        docenteConProblemi = null;
        hFont   = 20;
        colore  = c;
    }

    public void setSelezionata(int val) {
        selezionata = val;
    }

    public int getSelezionata() {
        return selezionata;
    }

    public void setSucc(Ora o) {
        succ = o;
    }

    public void setPrec(Ora o) {
        prec = o;
    }

    public Ora getSucc() {
        return succ;
    }

    public boolean setDoc(Docente d) {
        if (docente == null) {
            d.listaOre.add(this);            
            docente = d;
            return true;
        }
        else
            return false;
    }

    public Docente getDoc() {
        return docente;
    }

    public boolean setDocComp(Docente d) {
        if (docenteCom == null) {
            d.listaOre.add(this);
            docenteCom = d;
            return true;
        }
        else
            return false;
    }

    public Docente getDocCom() {
        return docenteCom;
    }

    public Ora getPrec() {
        return prec;
    }

    static public void paintNoScambio(Graphics g, int x, int y, int alt, int largh, int sel){
        int [] xpoints = new int[4];
        int [] ypoints = new int[4];
        int meno;
        if (sel == -2) {
            meno = 15;
            g.setColor(new Color(125, 125, 125, 175));
            xpoints[0] = x+meno;           xpoints[1] = x+alt-meno;
            ypoints[0] = y+meno;           ypoints[1] = y+meno;
            xpoints[2] = x+(largh+meno)/2;
            ypoints[2] = y+(alt)/2;
            g.fillPolygon(xpoints, ypoints, 3);
        }
        else if (sel == -1) {
            meno = 10;
            g.setColor(new Color(75, 75, 75, 175));
            xpoints[0] = x+meno;           xpoints[1] = x+alt-meno;
            ypoints[0] = y+meno;           ypoints[1] = y+meno;
            xpoints[2] = x+meno;           xpoints[3] = x+alt-meno;
            ypoints[2] = y+largh-meno+1;   ypoints[3] = y+largh-meno+1;
            g.fillPolygon(xpoints, ypoints, 4);
        }
    }
    // info = 0 vuota
    // info = 1 solo classe
    public void paint (Graphics g, int x, int y, int alt, int largh, int info) {
        if (docente != null)
            g.setColor(docente.colore);
        else
            g.setColor(colore);
        g.fillRect(x, y, alt, largh);
        g.setColor(new Color(classe.colore.getRed(),
                             classe.colore.getGreen(),
                             classe.colore.getBlue(),
                             120));
        g.fillRect(x, y, alt, largh);

        g.setColor(Color.black);
        g.drawRect(x, y, alt, largh);

        if (info == 1) {
            g.setColor(Color.BLACK);
            g.drawString(classe.nome, x+6 ,y+2+g.getFont().getSize());
        }


        if (selezionata == -2) {
            // evidenzio la cella come non adatta allo scambio (vedi anche GraphTable)
            paintNoScambio(g, x, y, alt, largh, selezionata);
        }
        if (selezionata == -1) {
            // evidenzio la cella come non adatta allo scambio (vedi anche GraphTable)
            paintNoScambio(g, x, y, alt, largh, selezionata);
        }
        if (selezionata == 1) {
            g.drawRect(x+1, y+1, alt-2, largh-2);
//            g.setColor(new Color(255, 150, 0, 125));
            g.setColor(new Color(colore.getRed(), colore.getGreen(), colore.getBlue(), 125));
            g.fill3DRect(x+2, y+2, alt-4, largh-4, true);
        }
        if (selezionata == 2) {
            g.drawRect(x+1, y+1, alt-2, largh-2);
//            g.setColor(new Color(255, 150, 0, 125));
            g.setColor(new Color(colore.getRed(), colore.getGreen(), colore.getBlue(), 125));
            g.fill3DRect(x+2, y+2, alt-4, largh-4, true);
        }
        // disegno linee per ore di compresenza
        // prima di due
        if  (succ != null) {
            g.setColor(Color.BLACK);
            g.drawLine(x+2, y+8, x+2, y+largh);
            g.setColor(Color.BLUE);
            g.drawLine(x+4, y+10,x+4, y+largh);
        }
        // seconda di due
        if  (prec != null) {
            g.setColor(Color.BLACK);
            g.drawLine(x+2, y,   x+2, y+largh-8);
            g.setColor(Color.BLUE);
            g.drawLine(x+4, y,   x+4, y+largh-10);
        }
        // unica 
        if ( (docente != null) && (docenteCom != null) )  {
            g.setColor(Color.BLACK);
            g.drawLine(x+2, y+8, x+2, y+largh-8);
            g.setColor(Color.BLUE);
            g.drawLine(x+4, y+10,x+4, y+largh-10);
        }
    }

    public void paintInfo (Graphics g, int x, int y, boolean tutte) {
        g.setColor(colore);
        g.drawString("GG: "+giorno+"-"+spazio+"ora", x, y);
//        y += hFont;
        int shiftx = 100;
        x += shiftx;
        if (classe != null) {
            classe.paint(g, x, y, tutte);
            y += classe.hPaint(tutte);
        }
        x -= shiftx;
        if (docente != null) {
            docente.paint(g, x, y, tutte);
            y += docente.hPaint(tutte);
        }
        if (docenteCom != null) {
            docenteCom.paint(g, x, y, tutte);
            y += docenteCom.hPaint(tutte);
        }
/************
        if (docenti != null) {
            for (int i = 0; i < docenti.size(); i++) {
                Docente d  = (Docente) docenti.get(i);
                if (d != null) {
                    d.paint(g, x, y, tutte);
                    y += d.hPaint(tutte);
                }
            }
        }
*****************/
        if (materia != null) {
            materia.paint(g, x, y, tutte);
            y += materia.hPaint(tutte);
        }
        if (aula != null)
            aula.paint(g, x, y, tutte);
    }

    public int hPaintInfo(boolean tutte) {
        int y = 0;

        y += classe.hPaint(tutte);
        if (docente != null) {
            y += docente.hPaint(tutte);
        }
        if (docenteCom != null) {
            y += docenteCom.hPaint(tutte);
        }
/**************
        for (int i = 0;i < docenti.size(); i++) {
            Docente d  = (Docente) docenti.get(i);
            y += d.hPaint(tutte);
        }
****/
        y += materia.hPaint(tutte);
        y += aula.hPaint(tutte);
 
        return y;
    }

    public Color colore() {
        return colore;
    }

    public void setColore(Color c) {
        colore = c;
    }
}
