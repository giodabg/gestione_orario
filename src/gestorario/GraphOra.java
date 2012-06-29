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
public class GraphOra extends Ora {

    private   GraphOra     succ; // se != null non deve separarsi dalla successiva
    private   GraphOra     prec; // se != null non deve separarsi dalla precedente
    private int     x;
    private int     y;
    private int     alt;
    private int     largh;
    private int     hFont;
            Color   colore;

    GraphOra() {
        succ    = null;
        prec    = null;
        x       = 0;
        y       = 0;
        alt     = 0;
        largh   = 0;
        hFont   = 20;
        colore  = Color.GRAY;
    }

    GraphOra(int g, int sp, boolean blocc) {
        succ    = null;
        prec    = null;
        giorno  = g;
        spazio  = sp;
        bloccata = blocc;
    }

    GraphOra(int g, int sp, Classe cl, Materia m, Aula a, Color c) {
        succ    = null;
        prec    = null;
        giorno  = g;
        spazio  = sp;
        classe  = cl;
        classe.addOra(this);
        materia = m;
        aula    = a;
        aula.addOra(this);
        x       = 0;
        y       = 0;
        alt     = 0;
        largh   = 0;
        hFont   = 20;
        colore  = c;
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


    public void setSucc(GraphOra o) {
        succ = o;
    }

    public void setPrec(GraphOra o) {
        prec = o;
    }

    public GraphOra getSucc() {
        return succ;
    }

    public GraphOra getPrec() {
        return prec;
    }


    static public void paintScambio(Graphics g, int x, int y, int alt, int largh, int sel){
        int [] xpoints = new int[4];
        int [] ypoints = new int[4];
        int meno;
        if (sel == NOSCAMBIO) {
            g.setColor(new Color(45, 45, 45, 140));
            g.fillRect(x+6, y+12, alt-12, largh-18);
            g.setColor(Color.WHITE);
            g.drawRect(x+6, y+12, alt-12, largh-18);

//            meno = 15;
//            g.setColor(Color.BLACK);
//            xpoints[0] = x+meno;           xpoints[1] = x+alt-meno;
//            ypoints[0] = y+meno;           ypoints[1] = y+meno;
//            xpoints[2] = x+(largh+meno)/2;
//            ypoints[2] = y+(alt)/2;
//            g.fillPolygon(xpoints, ypoints, 3);
        }
        else if (sel == SISCAMBIO) {
            g.setColor(Color.BLACK);
            g.fillRect(x+6, y+12, alt-12, largh-18);
            g.setColor(Color.WHITE);
            g.drawRect(x+6, y+12, alt-12, largh-18);

//            meno = 10;
//            g.setColor(Color.PINK);
//            xpoints[0] = x+meno;           xpoints[1] = x+alt-meno;
//            ypoints[0] = y+meno;           ypoints[1] = y+meno;
//            xpoints[2] = x+meno;           xpoints[3] = x+alt-meno;
//            ypoints[2] = y+largh-meno+1;   ypoints[3] = y+largh-meno+1;
//            g.fillPolygon(xpoints, ypoints, 4);
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

        if (classe != null) {
            g.setColor(new Color(classe.colore.getRed(),
                                 classe.colore.getGreen(),
                                 classe.colore.getBlue(),
                                 120));
            g.fillRect(x+5, y+5, alt-10, largh-10);
        }

        // disegno contorno della cella
        g.setColor(Color.black);
        g.drawRect(x, y, alt, largh);

        if (adattaScambio == NOSCAMBIO) {
            // evidenzio la cella come non adatta allo scambio (vedi anche GraphTable)
            paintScambio(g, x, y, alt, largh, NOSCAMBIO);
        }
        else if (adattaScambio == SISCAMBIO) {
            // evidenzio la cella come adatta allo scambio (vedi anche GraphTable)
            paintScambio(g, x, y, alt, largh, SISCAMBIO);
        }

        if (selezionata) {
            g.drawRect(x+3, y+3, alt-6, largh-6);
//            g.setColor(new Color(colore.getRed(), colore.getGreen(), colore.getBlue(), 125));
//            g.setColor(new Color(255, 150, 0, 125));
            g.setColor(Color.ORANGE);
            g.fill3DRect(x+4, y+4, alt-7, largh-7, true);
        }
        if (candidata) {
            g.drawRect(x+3, y+3, alt-6, largh-6);
//            g.setColor(new Color(colore.getRed(), colore.getGreen(), colore.getBlue(), 125));
//            g.setColor(new Color(255, 0, 150, 125));
            g.setColor(Color.YELLOW);
            g.fill3DRect(x+4, y+4, alt-7, largh-7, false);
        }
        if (corrente) {
//            g.drawRect(x+3, y+3, alt-6, largh-6);
//            g.setColor(new Color(colore.getRed(), colore.getGreen(), colore.getBlue(), 125));
//            g.setColor(new Color(255, 150, 0, 125));
            g.setColor(Color.GREEN);
            g.drawRect(x+1, y+1, alt-2, largh-2);
            g.drawRect(x+2, y+2, alt-4, largh-4);
            g.drawRect(x+3, y+3, alt-6, largh-6);
//            g.fill3DRect(x+4, y+4, alt-7, largh-7, true);
        }
        // disegno linee per ore di compresenza
        // prima di due
        if  (getSucc() != null) {
            g.setColor(Color.WHITE);
            g.drawLine(x+2, y+8, x+2, y+largh);
            g.setColor(Color.GREEN);
            g.drawLine(x+4, y+10,x+4, y+largh);
        }
        // seconda di due
        if  (getPrec() != null) {
            g.setColor(Color.WHITE);
            g.drawLine(x+2, y,   x+2, y+largh-8);
            g.setColor(Color.GREEN);
            g.drawLine(x+4, y,   x+4, y+largh-10);
        }
        // unica
        if ( (docente != null) && (docenteCom != null) )  {
            g.setColor(Color.WHITE);
            g.drawLine(x+2, y+8, x+2, y+largh-8);
            g.setColor(Color.GREEN);
            g.drawLine(x+4, y+10,x+4, y+largh-10);
        }
        // visualizza nome della classe associata all'ora
        if (info == 1) {
            g.setColor(Color.BLACK);
            g.drawString(classe.nome, x+6 ,y+2+g.getFont().getSize());
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
