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
public class OraGraph extends Ora {

    protected OraGraph     succ; // se != null non deve separarsi dalla successiva
    protected OraGraph     prec; // se != null non deve separarsi dalla precedente
    private int     x;
    private int     y;
    private int     alt;
    private int     largh;
    private int     hFont;
            Color   colore;

    OraGraph() {
        succ        = null;
        prec        = null;
        x       = 0;
        y       = 0;
        alt     = 0;
        largh   = 0;
        hFont   = 20;
        colore  = Color.GRAY;
    }

    OraGraph(int g, int sp, boolean blocc, boolean dispo) {
        succ        = null;
        prec        = null;
        giorno  = g;
        spazio  = sp;
        bloccata = blocc;
        disposizione = dispo;
        x       = 0;
        y       = 0;
        alt     = 0;
        largh   = 0;
        hFont   = 20;
        colore  = Color.GRAY;
    }

    OraGraph(int g, int sp, Classe cl, Materia m, Aula a, Color c) {
        succ        = null;
        prec        = null;
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


    public void setSucc(OraGraph o) {
        succ = o;
    }

    public void setPrec(OraGraph o) {
        prec = o;
    }

    public OraGraph getSucc() {
        return succ;
    }

    public OraGraph getPrec() {
        return prec;
    }


    static public void paintScambio(Graphics g, int x, int y, int alt, int largh, int sel){
        int [] xpoints = new int[4];
        int [] ypoints = new int[4];
        int inc = 1;
        if (sel == NOSCAMBIO) {
            g.setColor(new Color(45, 45, 45, 140));
            g.fillRect(x+6, y+12, alt-12, largh-12);
            g.setColor(Color.WHITE);
            g.drawRect(x+6, y+12, alt-12, largh-12);

//            meno = 15;
//            g.setColor(Color.BLACK);
//            xpoints[0] = x+meno;           xpoints[1] = x+alt-meno;
//            ypoints[0] = y+meno;           ypoints[1] = y+meno;
//            xpoints[2] = x+(largh+meno)/2;
//            ypoints[2] = y+(alt)/2;
//            g.fillPolygon(xpoints, ypoints, 3);
        }
        else if (sel == SISCAMBIO) {
            inc = 6;
            g.setColor(Color.WHITE);
            g.drawRect(x+inc, y+inc, alt-inc*2, largh-inc*2);

            inc++;
            g.setColor(Color.LIGHT_GRAY);
            g.drawRect(x+inc, y+inc, alt-inc*2, largh-inc*2);

            inc++;
            g.setColor(Color.GRAY);
            g.drawRect(x+inc, y+inc, alt-inc*2, largh-inc*2);

            inc++;
            g.setColor(Color.DARK_GRAY);
            g.drawRect(x+inc, y+inc, alt-inc*2, largh-inc*2);

            inc++;
            g.setColor(Color.GREEN);
            g.fillRect(x+inc, y+inc, alt-inc*2, largh-inc*2);


        }
    }
    // info = 0 vuota
    // info = 1 solo classe
    public void paint (Graphics g, int x, int y, int alt, int largh,
           boolean corrente, boolean selezionata, boolean candidata, 
           boolean intermedia, int adattaScambio, int info) {

        int inc = 0;

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
            // paintScambio(g, x, y, alt, largh, NOSCAMBIO);
        }
        else if (adattaScambio == SISCAMBIO) {
            // evidenzio la cella come adatta allo scambio (vedi anche GraphTable)
            paintScambio(g, x, y, alt, largh, SISCAMBIO);
        }

        if (selezionata) {
//            g.setColor(new Color(255, 150, 0, 125));
//            g.fill3DRect(x+4, y+4, alt-7, largh-7, true);
//            g.draw3DRect(x+4, y+4, alt-7, largh-7, true);
            inc = 0;
            g.setColor(Color.WHITE);
            g.drawRect(x+inc, y+inc, alt-inc*2, largh-inc*2);

            inc++;
            g.setColor(Color.LIGHT_GRAY);
            g.drawRect(x+inc, y+inc, alt-inc*2, largh-inc*2);

            inc++;
            g.setColor(Color.GRAY);
            g.drawRect(x+inc, y+inc, alt-inc*2, largh-inc*2);

            inc++;
            g.setColor(Color.DARK_GRAY);
            g.drawRect(x+inc, y+inc, alt-inc*2, largh-inc*2);

            inc++;
            g.setColor(Color.YELLOW);
            g.fill3DRect(x+inc, y+inc, alt-inc*2, largh-inc*2, true);
        }
        if (candidata) {
            inc = 0;
            g.setColor(Color.WHITE);
            g.drawRect(x+inc, y+inc, alt-inc*2, largh-inc*2);
            inc++;
            g.setColor(Color.LIGHT_GRAY);
            g.drawRect(x+inc, y+inc, alt-inc*2, largh-inc*2);
            inc++;
            g.setColor(Color.GRAY);
            g.drawRect(x+inc, y+inc, alt-inc*2, largh-inc*2);
            inc++;
            g.setColor(Color.DARK_GRAY);
            g.drawRect(x+inc, y+inc, alt-inc*2, largh-inc*2);

            inc++;
//            g.setColor(new Color(colore.getRed(), colore.getGreen(), colore.getBlue(), 125));
            g.setColor(Color.YELLOW);
            g.fill3DRect(x+inc, y+inc, alt-inc*2, largh-inc*2, true);
        }
        if (corrente) {
            inc = 0;
            g.setColor(Color.WHITE);
            g.drawRect(x+inc, y+inc, alt-inc*2, largh-inc*2);
            inc++;
            g.setColor(Color.LIGHT_GRAY);
            g.drawRect(x+inc, y+inc, alt-inc*2, largh-inc*2);
            inc++;
            g.setColor(Color.GRAY);
            g.drawRect(x+inc, y+inc, alt-inc*2, largh-inc*2);
            inc++;
            g.setColor(Color.DARK_GRAY);
            g.drawRect(x+inc, y+inc, alt-inc*2, largh-inc*2);

            inc++;
//            g.setColor(new Color(colore.getRed(), colore.getGreen(), colore.getBlue(), 125));
            g.setColor(Color.YELLOW);
            g.fill3DRect(x+inc, y+inc, alt-inc*2, largh-inc*2, true);
        }

        if (intermedia) {
            inc = 1;
            g.setColor(Color.BLACK);
            g.drawRect(x+inc, y+inc, alt-inc*2, largh-inc*2);
            inc++;
            g.setColor(Color.LIGHT_GRAY);
            g.drawRect(x+inc, y+inc, alt-inc*2, largh-inc*2);
            inc++;
            g.setColor(Color.GRAY);
            g.drawRect(x+inc, y+inc, alt-inc*2, largh-inc*2);
            inc++;
            g.setColor(Color.DARK_GRAY);
            g.drawRect(x+inc, y+inc, alt-inc*2, largh-inc*2);

            inc++;
//            g.setColor(new Color(colore.getRed(), colore.getGreen(), colore.getBlue(), 125));
            g.setColor(Color.YELLOW);
            g.fill3DRect(x+inc, y+inc, alt-inc*2, largh-inc*2, true);
        }
        // disegno linee per ore di compresenza
        // prima di due
        if  (getSucc() != null) {
            g.setColor(Color.DARK_GRAY);
            g.drawLine(x+2, y+8, x+2, y+largh);
            g.setColor(Color.GREEN);
            g.drawLine(x+3, y+10,x+3, y+largh);
            g.drawLine(x+4, y+10,x+4, y+largh);
            g.setColor(Color.DARK_GRAY);
            g.drawLine(x+5, y+10,x+5, y+largh);
        }
        // seconda di due
        if  (getPrec() != null) {
            g.setColor(Color.DARK_GRAY);
            g.drawLine(x+2, y,   x+2, y+largh-8);
            g.setColor(Color.GREEN);
            g.drawLine(x+3, y,   x+3, y+largh-10);
            g.drawLine(x+4, y,   x+4, y+largh-10);
            g.setColor(Color.DARK_GRAY);
            g.drawLine(x+5, y,   x+5, y+largh-10);
        }
        // unica
        if ( (docente != null) && (docenteCom != null) )  {
            g.setColor(Color.DARK_GRAY);
            g.drawLine(x+2, y+8, x+2, y+largh-8);
            g.setColor(Color.GREEN);
            g.drawLine(x+3, y+10,x+3, y+largh-10);
            g.drawLine(x+4, y+10,x+4, y+largh-10);
            g.setColor(Color.DARK_GRAY);
            g.drawLine(x+5, y+10,x+5, y+largh-8);
        }
        // visualizza nome della classe associata all'ora
        if (info == 1) {
            g.setColor(Color.BLACK);
            g.drawString(classe.nome, x+6 ,y+2+g.getFont().getSize());
            g.drawString("("+aula.nome+")", x+6 ,y+2+2*g.getFont().getSize());
        }

    }

    public void paintInfo (Graphics g, int x, int y, boolean tutte) {
        g.setColor(Color.BLACK);
        g.drawString("GG: "+giorno+" gg - "+spazio+" ora", x, y);
//        y += hFont;
        int shiftx = 180;
        if (classe != null) {
            classe.paint(g, x+shiftx, y, tutte);
            y += classe.hPaint(tutte);
        }
        if (aula != null)
            aula.paint(g, x+shiftx, y, tutte);
        if (docente != null) {
            docente.paint(g, x, y, tutte);
            y += docente.hPaint(tutte);
        }
        if (docenteCom != null) {
            docenteCom.paint(g, x, y, tutte);
            y += docenteCom.hPaint(tutte);
        }
        if (materia != null) {
            materia.paint(g, x, y, tutte);
            y += materia.hPaint(tutte);
        }
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
