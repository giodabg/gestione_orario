/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorario;

import java.awt.*;  //grafica
import java.awt.event.*; //grafica
import javax.swing.*; // dialog box

//problema scambio due ore collegate con quella appena sotto Si fa, ma le celle evidenziate
//        fanno sembrare uno scambio errato!!!!
/**
 *
 * @author Gio
 */
public class GraphTable {

//    private OraGraph tabOre[][];
    private Frame       ancestorFrame;
    private GestOrarioApplet       gestApplet;
    private Graphics    g;
    private int         idTable;
    private GraphTable  succTableRig;
    private GraphTable  succTableRigInterm;
    private GraphTable  succTableCol;
    private boolean     visible;
    private int         tipoObj;
    private Classe      classe;
    private Docente     docente;
    private Aula        aula;
            ListaOre    listaOre;
            SelCorOre   selCor;      // punta all'oggetto nella window
    private int         b;
    private int         h;
    private int         righe;
    private int         colonne;
            int         punteggio = 0;
    private int         shiftX;
    private int         shiftY;
    private int         lastGiorno;
    private int         lastSpazio;
            int         selx;
            int         sely;
            int         curx;
            int         cury;


    int statoRicerca;
    private int scambi = 0;
    // has the user clicked on the applet yet? initially, no.
    private boolean hasBeenClicked = false;

    public GraphTable(int id, int Righe2, int Colonne2, int b2, int h2,
        int shiftX, int shiftY, GestOrarioApplet window, int objWin,
        Frame f, 
        SelCorOre sl) {

        gestApplet      = window;
        ancestorFrame   = f;
        idTable         = id;
        succTableRig    = null;
        succTableRigInterm = null;
        succTableCol    = null;
        visible         = false;
        resetStatoRicerca();
        classe          = null;
        docente         = null;
        aula            = null;
        selCor          = sl;   // punta all'oggetto presente in applet o window
//        selCor.setSelected(o, 0);
        righe           = Righe2;
        colonne         = Colonne2;
        b               = b2;
        h               = h2;
        selx            = 0;
        sely            = 0;
        curx            = 0;
        cury            = 0;
        this.shiftX     = shiftX;
        this.shiftY     = shiftY;
    }

    public void setSelCor(SelCorOre selC) {
        selCor = selC;
    }

    public SelCorOre getSelCor() {
        return selCor;
    }

    public void setGraphic(Graphics gr) {
        g = gr;
    }

    public void setSuccTableRig(GraphTable succ) {
        succTableRig = succ;
    }
    
    public void setSuccTableRigInterm(GraphTable succ) {
        succTableRigInterm = succ;
    }

    public void setSuccTableCol(GraphTable succ) {
        succTableCol = succ;
    }

    public void setList(Object obj, int tObj, ListaInfoMatDocClassAule materieDocenti) {

        tipoObj = tObj;
//        tabOre = new OraGraph[righe][colonne];
        if (tipoObj == GestOrarioApplet.TIPOCLASSE) {
            classe = (Classe) obj;
            docente = null;
            aula = null;
            listaOre = classe.listaOre;
        }
        else if (tipoObj == GestOrarioApplet.TIPODOCENTE) {
            classe = null;
            docente = (Docente) obj;
            aula = null;
            listaOre = docente.listaOre;
        }
        else if (tipoObj == GestOrarioApplet.TIPOAULA) {
            classe = null;
            docente = null;
            aula = (Aula) obj;
            listaOre = aula.listaOre;
        }
//        initColorTab(listaOre, infoMatDocAule);
    }

    //   corr sel1 tab1 succ1
    // 0  no   no  doc    5          - invisibile
    // 1  no   no  doc    5          - visibile senza info
    // 2  si   no  doc    6 con corr - visibile con info corr
    // 3  no   si  doc    7 con sel  - visibile scelto prima ora visualizzo info sel
    // 4  si   si  doc    7 con sel  - visibile scelto prima ora visualizzo info sel
    // 5  no   no  cl     0          - invisibile
    // 6  si   no  cl     0          - visibile senza info
    // 7  no   si  cl     0          - scelto prima ora senza info
    // 8  si   si  cl     1          - scelto prima ora visualizzo info corr


    // 3 no cor si sel  - scelto prima ora visualizzo info sel. succ: 1
    // 4 si cor si sel - scelta prima ora visualizzo info corr e sel
    // 5 scelto seconda ora visualizzo info (si corrente 2 selezioni)
    // 6 scelta seconda ora  effettuo scambio (si corrente 2 selezioni)

    public void resetStatoRicerca() {
    // 0  no   no  doc    5          - invisibile
    // 5  no   no  cl     0          - invisibile
        if (docente != null) {
            statoRicerca = 0;
            setVisible(false);
            if (succTableRig != null)
              succTableRig.resetStatoRicerca();
            if (succTableRigInterm != null)
              succTableRigInterm.resetStatoRicerca();
            if (succTableCol != null)
              succTableCol.resetStatoRicerca();
        }
        if (aula != null) {
            statoRicerca = 0;
            setVisible(false);
            if (succTableRig != null)
              succTableRig.resetStatoRicerca();
            if (succTableRigInterm != null)
              succTableRigInterm.resetStatoRicerca();
            if (succTableCol != null)
              succTableCol.resetStatoRicerca();
        }
        if (classe != null) {
            statoRicerca = 5;
            setVisible(false);
            if (succTableRig != null)
              succTableRig.resetStatoRicerca();
            if (succTableRigInterm != null)
              succTableRigInterm.resetStatoRicerca();
            if (succTableCol != null)
              succTableCol.resetStatoRicerca();
        }
    }

    public void setNewStatoRicerca(Docente doc) {
        //   corr sel1 tab1 succ1
        // 1  no   no  doc    5          - visibile senza info
        if ( (!selCor.esisteCurrent())
          && (!selCor.esisteSelected())
          && ( (docente != null) || (aula != null) ) ) {
            statoRicerca = 1;
            if (succTableRig != null) {
                succTableRig.resetStatoRicerca();
            }
            if (succTableCol != null) {
                succTableCol.resetStatoRicerca();
            }
        }
        //   corr sel1 tab1 succ1
        // 2  si   no  doc    6 con corr - visibile con info corr
        if ( (selCor.esisteCurrent())
          && (!selCor.esisteSelected())
          && ( (docente != null) || (aula != null) ) ) {
            statoRicerca = 2;
            OraGraph ora = listaOre.get(selCor.curGiorno(), selCor.curSpazio());
            if (succTableRig != null) {
                if (ora != null) {
                    // imposto tabella 3 con classe ora corrente
                    succTableRig.setList(ora.classe, GestOrarioApplet.TIPOCLASSE, null);
                    succTableRig.setSelCor(selCor);
                    succTableRig.setNewStatoRicerca(doc);
                }
            }
            if (succTableCol != null) {
                if ( (ora != null) && (docente != null) ) {
                    if (ora.getDoc() == docente) {
                        // se tab 1 docente in tab 2 docCom
                        if (ora.getDocCom() != null) {
                            succTableCol.setList(ora.getDocCom(), GestOrarioApplet.TIPODOCENTE, null);
                            succTableCol.setSelCor(selCor);
                            succTableCol.setNewStatoRicerca(doc);
                        }
                        else {
                            succTableCol.resetStatoRicerca();
                        }
                    } else if (ora.getDocCom() == docente) {
                        // se tab 1 docCom in tab 2 docente
                        if (ora.getDoc() != null) {
                            succTableCol.setList(ora.getDoc(), GestOrarioApplet.TIPODOCENTE, null);
                            succTableCol.setSelCor(selCor);
                            succTableCol.setNewStatoRicerca(doc);
                        }
                        else {
                            succTableCol.resetStatoRicerca();
                        }
                    }
                }
            }
        }
        //   corr sel1 tab1 succ1
        // 3  no   si  doc    7 con sel  - visibile scelto prima ora visualizzo info sel
        if ( (!selCor.esisteCurrent()) 
          && (selCor.esisteSelected())
          && ( (docente != null) || (aula != null) ) ) {
            statoRicerca = 3;
            OraGraph ora = listaOre.get(selCor.sel1Giorno(), selCor.sel1Spazio());
            if ((succTableRig != null) && (ora != null)) {
                if (idTable == 1)
                    succTableRig.setList(ora.classe, GestOrarioApplet.TIPOCLASSE, null);
                else
                    succTableRig.setList(ora.aula, GestOrarioApplet.TIPOAULA, null);

                succTableRig.setSelCor(selCor);
                succTableRig.setNewStatoRicerca(doc);
            }

            if (succTableCol != null) {
                if ( (ora != null) && (docente != null) ) {
                    if (ora.getDoc() == docente) {
                        // se tab 1 docente in tab 2 docCom
                        if (ora.getDocCom() != null) {
                            succTableCol.setList(ora.getDocCom(), GestOrarioApplet.TIPODOCENTE, null);
                            succTableCol.setSelCor(selCor);
                            succTableCol.setNewStatoRicerca(doc);
                        }
                        else {
                            succTableCol.resetStatoRicerca();
                        }
                    } else if (ora.getDocCom() == docente) {
                        // se tab 1 docCom in tab 2 docente
                        if (ora.getDoc() != null) {
                            succTableCol.setList(ora.getDoc(), GestOrarioApplet.TIPODOCENTE, null);
                            succTableCol.setSelCor(selCor);
                            succTableCol.setNewStatoRicerca(doc);
                        }
                        else {
                            succTableCol.resetStatoRicerca();
                        }
                    }
                }
            }
        }
        //   corr sel1 tab1 succ1
        // 4  si   si  doc    7 con sel  - visibile scelto prima ora visualizzo info sel
        if ( (selCor.esisteCurrent()) 
          && (selCor.esisteSelected())
          && ( (docente != null) || (aula != null) ) ) {
            statoRicerca = 4;
            // esattamente come lo stato 2 nel senso che
            // l'ora corrente ha la precedenza sull'ora selezionata
            OraGraph ora = listaOre.get(selCor.interm1Giorno(), selCor.interm1Spazio());
            if (succTableRig != null) {
                if (ora != null) {
                    // imposto tabella 7 docente intermedio
                    succTableRig.setList(ora.getDoc(), GestOrarioApplet.TIPODOCENTE, null);
                    succTableRig.setSelCor(selCor);
                    succTableRig.setNewStatoRicerca(doc);
                }
            }
            if (succTableCol != null) {
                if (idTable == 5)
                    ora = listaOre.get(selCor.can1Giorno(), selCor.can1Spazio());
                if ( ((idTable == 5) || (idTable == 7))
                  && ((ora != null) && (ora.getDocCom() == null)) ) {
                    // nella tabella 7 quella del docente intermedio ci possono
                    // essere doc di compresenza sia nell'ora intermedia
                    // che nell'ora di arrivo (la selezione)
                    // quindi se ora non ha il docente in compresenza
                    // si prova a vedere l'ora selezionata
                    ora = listaOre.get(selCor.interm1Giorno(), selCor.interm1Spazio());
                }

                if ( (ora != null) && (docente != null) ) {
                    if (ora.getDoc() == docente) {
                        // se in tab 1 c'è il docente in tab 2 ci sarà docCom
                        if (ora.getDocCom() != null) {
                            succTableCol.setList(ora.getDocCom(), GestOrarioApplet.TIPODOCENTE, null);
                            succTableCol.setSelCor(selCor);
                            succTableCol.setNewStatoRicerca(doc);
                        }
                        else {
                            succTableCol.resetStatoRicerca();
                        }
                    } else if (ora.getDocCom() == docente) {
                        // se in tab 1 c'è il docCom in tab 2 ci sarà docente
                        if (ora.getDoc() != null) {
                            succTableCol.setList(ora.getDoc(), GestOrarioApplet.TIPODOCENTE, null);
                            succTableCol.setSelCor(selCor);
                            succTableCol.setNewStatoRicerca(doc);
                        }
                        else {
                            succTableCol.resetStatoRicerca();
                        }
                    }
                    else {
                        succTableCol.resetStatoRicerca();
                    }

                }
                else {
                    succTableCol.resetStatoRicerca();
                }
            }
        }

        //   corr sel1 tab1 succ1
        // 5  no   no  cl     0         - invisibile
        if ( (!selCor.esisteCurrent()) 
          && (!selCor.esisteSelected())
          && (classe != null) ) {
                statoRicerca = 5;
                resetStatoRicerca();
        }
        //   corr sel1 tab1 succ1
        // 6  si   no  cl     0          - visibile senza info
        if ( (selCor.esisteCurrent()) 
          && (!selCor.esisteSelected())
          && (classe != null) ) {
//            if (statoRicerca != 6) {
                statoRicerca = 6;
                if (succTableRig != null) {
                    succTableRig.resetStatoRicerca();
                }
                if (succTableCol != null) {
                    succTableCol.resetStatoRicerca();
                }
//            }
        }
        //   corr sel1 tab1 succ1
        // 7  no   si  cl     0          - scelto prima ora senza info
        if ( (!selCor.esisteCurrent()) && (selCor.esisteSelected()) && (classe != null) ) {
//            if (statoRicerca != 7) {
                statoRicerca = 7;
                if (succTableRig != null) {
                    succTableRig.resetStatoRicerca();
                }
                OraGraph ora = listaOre.get(selCor.sel1Giorno(), selCor.sel1Spazio());
                if ( (ora != null) && (succTableCol != null) 
                  && ( (ora.getDocCom() != null) || !(ora.aula.nome.equalsIgnoreCase(ora.classe.nome)) ) ) {
                    succTableCol.setList(ora.aula, GestOrarioApplet.TIPOAULA, null);
                    succTableCol.setSelCor(selCor);
                    succTableCol.setNewStatoRicerca(doc);
                }
                else {
                    if (succTableCol != null)
                        succTableCol.resetStatoRicerca();
                }
//            }
        }
        //   corr sel1 tab1 succ1
        // 8  si   si  cl     1          - scelto prima ora visualizzo info corr
        if ( (selCor.esisteCurrent()) && (selCor.esisteSelected()) && (classe != null) ) {
//            if (statoRicerca != 8) {
                statoRicerca = 8;
                OraGraph ora = listaOre.get(selCor.curGiorno(), selCor.curSpazio());

                if ( (ora != null) && (succTableRig != null) && (ora.getDoc() != null)
                        && !(ora.getDoc().equals(doc)) ) {
                    succTableRig.setList(ora.getDoc(), GestOrarioApplet.TIPODOCENTE, null);
                    succTableRig.setSelCor(selCor);
                    succTableRig.setNewStatoRicerca(doc);
                }
                else {
                    if (succTableRig != null)
                        succTableRig.resetStatoRicerca();
                }

                OraGraph oraInterm = listaOre.get(selCor.interm1Giorno(), selCor.interm1Spazio());
                if ( (oraInterm != null) && (succTableRigInterm != null) && (oraInterm.getDoc() != null)
                        && !(oraInterm.getDoc().equals(doc)) ) {
                    succTableRigInterm.setList(oraInterm.getDoc(), GestOrarioApplet.TIPODOCENTE, null);
                    succTableRigInterm.setSelCor(selCor);
                    succTableRigInterm.setNewStatoRicerca(doc);
                }
                else {
                    if (succTableRigInterm != null)
                        succTableRigInterm.resetStatoRicerca();
                }

                // o visualizzo le ore dell'aula speciale associata all'ora corrente
                // o quelle dell'aula associata all'ora selezionata
                // o nulla
                if ( (ora != null) && (succTableCol != null) 
                    && ( (ora.getDocCom() != null) || !(ora.aula.nome.equalsIgnoreCase(ora.classe.nome)) ) ) {
                    succTableCol.setList(ora.aula, GestOrarioApplet.TIPOAULA, null);
                }
                else {
                    ora = listaOre.get(selCor.sel1Giorno(), selCor.sel1Spazio());

                    if ( (ora != null) && (succTableCol != null)
                    && ( (ora.getDocCom() != null) || !(ora.aula.nome.equalsIgnoreCase(ora.classe.nome)) ) ) {
                        succTableCol.setList(ora.aula, GestOrarioApplet.TIPOAULA, null);
                        succTableCol.setSelCor(selCor);
                        succTableCol.setNewStatoRicerca(doc);
                    }
                    else {
                        if (succTableCol != null)
                            succTableCol.resetStatoRicerca();
                    }
                }
//            }
        }

    }

    public void setStatoRicerca(int val) {
        if ((val >= 0) && (val<= 12))
            statoRicerca = val;
    }

    public int getStatoRicerca() {
        return statoRicerca;
    }

    public void setVisible(boolean val) {
        visible = val;
        if (visible == false) {
            if (succTableRig != null)
              succTableRig.setVisible(false);
            if (succTableCol != null)
              succTableCol.setVisible(false);
        }
    }

    public boolean getVisible() {
        return visible;
    }

    //   corr sel1 tab1 succ1
    // 0  no   no  doc    5          - invisibile
    // 1  no   no  doc    5          - visibile senza info
    // 2  si   no  doc    6 con corr - visibile con info corr
    // 3  no   si  doc    7 con sel  - visibile scelto prima ora visualizzo info sel
    // 4  si   si  doc    7 con sel  - visibile scelto prima ora visualizzo info sel
    // 5  no   no  cl     0          - invisibile
    // 6  si   no  cl     0          - visibile senza info
    // 7  no   si  cl     0          - scelto prima ora senza info
    // 8  si   si  cl     1          - scelto prima ora visualizzo info corr
    public void paintTabella() {

        OraGraph ora = null;
        
        switch (getStatoRicerca()) {
                case 0:
                    // 0  no   no  doc    5          - invisibile
                    setVisible(false);;
                    break;
                case 1:
                    // 1  no   no  doc    5          - visibile senza info
                    setVisible(true);
                    break;
                case 2:
                    // 2  si   no  doc    6 con corr - visibile con info corr
                    setVisible(true);
                    ora = listaOre.get(selCor.curGiorno(), selCor.curSpazio());
                    if (ora != null) {
                        ora.paintInfo(g, shiftX, shiftY + (righe * getH()) + 10, false);
                    }
                    break;
                case 3:
                    // 3  no   si  doc    7 con sel  - visibile scelto prima ora visualizzo info sel
                    setVisible(true);
//                    g.drawString("Giorno Sel: " + GestOrarioApplet.giorno2Str(selCor.selGiorno, false),
//                            getShiftX()+getB()*3, shiftY - 2*g.getFont().getSize() - 3);
//                    g.drawString("OraGraph Sel: " + selCor.selSpazio,
//                            getShiftX()+getB()*5, shiftY - 2*g.getFont().getSize() - 3);
//                    ora = selCor.selOra1();
//                    if (ora != null) {
//                        ora.setSelezionata(2);
//                    }
                    break;
                case 4:
                    // 4  si   si  doc    7 con sel  - visibile scelto prima ora visualizzo info sel
                    setVisible(true);
//                    g.drawString("Giorno Sel: " + GestOrarioApplet.giorno2Str(selCor.selGiorno, false),
//                            getShiftX()+getB()*3, shiftY - 2*g.getFont().getSize() - 3);
//                    g.drawString("OraGraph Sel: " + selCor.selSpazio,
//                            getShiftX()+getB()*5, shiftY - 2*g.getFont().getSize() - 3);
//                    ora = selCor.selOra1();
//                    if (ora != null) {
//                        ora.setSelezionata(2);
//                    }
//                    g.drawString("Giorno Cur: " + GestOrarioApplet.giorno2Str(selCor.curGiorno, false),
//                            getShiftX()+getB()*3, shiftY + (righe * getH()) + 10);
//                    g.drawString("OraGraph Cur: " + selCor.curSpazio,
//                            getShiftX()+getB()*5, shiftY + (righe * getH()) + 10);
                    ora = listaOre.get(selCor.curGiorno(), selCor.curSpazio());
                    if (ora != null) {
//                        ora.setSelezionata(1);
                        ora.paintInfo(g, shiftX, shiftY + (righe * getH()) + 10, false);
                    }
                    break;
                case 5:
                    // 5  no   no  cl     0          - invisibile
                    setVisible(false);
                    break;
                case 6:
                    // 6  si   no  cl     0          - visibile senza info
                    ora = listaOre.get(selCor.curGiorno(), selCor.curSpazio());
                    if (ora != null) {
//                        ora.setSelezionata(1);
                        ora.paintInfo(g, shiftX, shiftY + (righe * getH()) + 10, false);
                    }
                    setVisible(true);
                    break;
                case 7:
                    // 7  no   si  cl     0          - scelto prima ora senza info
                    setVisible(true);
//                    g.drawString("Giorno Sel: " + GestOrarioApplet.giorno2Str(selCor.selGiorno, false),
//                            getShiftX()+getB()*3, shiftY - 2*g.getFont().getSize() - 3);
//                    g.drawString("OraGraph Sel: " + selCor.selSpazio,
//                            getShiftX()+getB()*5, shiftY - 2*g.getFont().getSize() - 3);
                    ora = listaOre.get(selCor.sel1Giorno(), selCor.sel1Spazio());
                    if (ora != null) {
//                        ora.setSelezionata(2);
                        ora.paintInfo(g, shiftX, shiftY + (righe * getH()) + 10, false);
                    }
                    break;
                case 8:
                    // 8  si   si  cl     1          - scelto prima ora visualizzo info corr
                    setVisible(true);
//                    g.drawString("Giorno Sel: " + GestOrarioApplet.giorno2Str(selCor.selGiorno, false),
//                            getShiftX()+getB()*3, shiftY - 2*g.getFont().getSize() - 3);
//                    g.drawString("OraGraph Sel: " + selCor.selSpazio,
//                            getShiftX()+getB()*5, shiftY - 2*g.getFont().getSize() - 3);
//                    g.drawString("Giorno Cur: " + GestOrarioApplet.giorno2Str(selCor.curGiorno, false),
//                            getShiftX()+getB()*3, shiftY + (righe * getH()) + 10);
//                    g.drawString("OraGraph Cur: " + selCor.curSpazio,
//                            getShiftX()+getB()*5, shiftY + (righe * getH()) + 10);
                    ora = listaOre.get(selCor.curGiorno(), selCor.curSpazio());
                    if (ora != null) {
                        ora.paintInfo(g, shiftX, shiftY + (righe * getH()) + 10, false);
                    }
                    break;
                default:
                    break;
        }



        if (getVisible()) {
            if ((classe != null) && selCor.esisteSelected() && (idTable == 3)) {
                selCor.resetErrorMess();
                selCor.resetSolutionMess();
                if (selCor.esisteCandidated()) {
                    selCor.scambioCorretto(listaOre, gestApplet, false);
                    if (selCor.totErrMess() > 0) {
                        g.setColor(Color.RED);
                        g.drawString("*** SCAMBIO IMPOSSIBILE ***", shiftX, shiftY - g.getFont().getSize() - 3);
                    }
                    else  {
                        g.setColor(Color.GREEN);
                        g.drawString("*** SCAMBIO POSSIBILE ***", shiftX, shiftY - g.getFont().getSize() - 3);
                    }
                }
            }
            // Intestazione tabella
            g.drawString("IdTable "+idTable+"-StatoRicerca "+getStatoRicerca(), shiftX, shiftY - 2*g.getFont().getSize() - 3);
            if (classe != null) {
                // visualizzo classe Corrente in alto dopo lo stato
                classe.paint(g, getShiftX()+getB()*5,
                                shiftY - 2*g.getFont().getSize() - 3, false);
            }
            if (docente != null) {
                docente.paint(g, getShiftX(),
                                 shiftY - g.getFont().getSize() - 3, false);
            }
            if (aula != null) {
                aula.paint(g, getShiftX()+getB()*5,
                              shiftY - 3*g.getFont().getSize() - 3, false);
            }

            // disegno della tabella
            for (int i = 0; i < getRighe(); i++) {
                // intestazione colonna pari al nome del giorno
                g.setColor(Color.BLACK);
                g.drawString(" "+(i+1),
                             getShiftX() - 12,
                             i * getH() + getShiftY() + 12);
                g.drawString(GestOrarioApplet.giorno2Str(i + 1, true), 
                             i * getB() + getShiftX(),
                             getShiftY() - 2);

                // disegno la colonna delle ore di una giornata
                for (int j = 0; j < getColonne(); j++) {
                    ora = listaOre.get(i+1, j+1);
                    // colore di default delle ore senza caratteristiche
                    g.setColor(Color.GRAY);

                    if (ora == null) {
                        if (docente != null) {
                            if ((docente.getBloccata(i+1, j+1) != null) ||
                                (docente.giornoLibero == i+1))
                            // l'ora o è bloccata o si trova nel giorno libero
                                  g.setColor(Color.BLACK);
                        }

                        // se una delle ore selezionate è quella da disegnare
                        if ((i+1 == selCor.curGiorno()) &&
                            (j+1 == selCor.curSpazio())) {
                                  g.setColor(Color.RED);
                        }
                        if ((i+1 == selCor.sel1Giorno()) &&
                            (j+1 == selCor.sel1Spazio())) {
                                  g.setColor(Color.RED);
                        }
                        if ((i+1 == selCor.sel2Giorno()) &&
                            (j+1 == selCor.sel2Spazio())) {
                                  g.setColor(Color.RED);
                        }
                        if ((i+1 == selCor.can1Giorno()) &&
                            (j+1 == selCor.can1Spazio())) {
                                  g.setColor(Color.RED);
                        }
                        if ((i+1 == selCor.can2Giorno()) &&
                            (j+1 == selCor.can2Spazio())) {
                                  g.setColor(Color.RED);
                        }

                        if ((i+1 == selCor.interm1Giorno()) &&
                            (j+1 == selCor.interm1Spazio())) {
                                g.setColor(Color.RED);
                        }

                        if ((i+1 == selCor.interm2Giorno()) &&
                            (j+1 == selCor.interm2Spazio())) {
                                g.setColor(Color.RED);
                        }

                        // disegno ora vuota
                        g.fillRect(i * getB() + getShiftX(), 
                                   j * getH() + getShiftY(),
                                   getB(), getH());
                        // disegno contorno
                        g.setColor(Color.yellow);
                        g.drawRect(i * getB() + getShiftX(), 
                                   j * getH() + getShiftY(),
                                   getB(), getH());

                        if ( (idTable == 3) && (selCor.esisteSelected()) ) {
                            SelCorOre selCorTmp = new SelCorOre();
                            selCorTmp.setSelected(selCor.selOra1(), getStatoRicerca(), listaOre);
                            OraInt oraI = new OraInt(i+1, j+1);
                            selCorTmp.setCurrent(oraI, listaOre);
                            selCorTmp.scambioCorretto(listaOre, gestApplet, false);
                            if ((selCorTmp.totErrMess() == 0))
                                    OraGraph.paintScambio(g, i * getB() + getShiftX(),
                                       j * getH() + getShiftY(),
                                       getB(), getH(), OraGraph.SISCAMBIO);
                        }
                    }
                    else {
                        // l'ora esiste
                        // se una delle ore selezionate è quella da disegnare

                        boolean corrente=false,
                                selezionata=false,
                                candidata=false,
                                intermedia=false;

                        if ((i+1 == selCor.curGiorno()) &&
                            (j+1 == selCor.curSpazio())) {
                                  corrente = true;
                        }
                        if ((i+1 == selCor.sel1Giorno()) &&
                            (j+1 == selCor.sel1Spazio())) {
                                  selezionata = true;
                        }
                        if ((i+1 == selCor.sel2Giorno()) &&
                            (j+1 == selCor.sel2Spazio())) {
                                  selezionata = true;
                        }
                        if ((i+1 == selCor.can1Giorno()) &&
                            (j+1 == selCor.can1Spazio())) {
                                  candidata = true;
                        }
                        if ((i+1 == selCor.can2Giorno()) &&
                            (j+1 == selCor.can2Spazio())) {
                                  candidata = true;
                        }

                        if ((i+1 == selCor.interm1Giorno()) &&
                            (j+1 == selCor.interm1Spazio())) {
                                intermedia = true;
                        }

                        if ((i+1 == selCor.interm2Giorno()) &&
                            (j+1 == selCor.interm2Spazio())) {
                                intermedia = true;
                        }

                        int adattaScambio = OraGraph.NOSCAMBIO;
                        if ( (idTable == 3) && (selCor.esisteSelected()) ) {
                            SelCorOre selCorTmp = new SelCorOre();
                            selCorTmp.setSelected(selCor.selOra1(), getStatoRicerca(), listaOre);
                            OraInt oraI = new OraInt(i+1, j+1);
                            selCorTmp.setCurrent(oraI, listaOre);
                            selCorTmp.scambioCorretto(listaOre, gestApplet, false);
                            if ((selCorTmp.totErrMess() == 0))
                                adattaScambio = OraGraph.SISCAMBIO;
                        }
    
                        if (classe != null) {
                            ora.paint(g, (ora.giorno - 1) * getB() + getShiftX()
                                       , (ora.spazio - 1) * getH() + getShiftY()
                                       , getB(), getH()
                                       , corrente, selezionata, candidata,
                                       intermedia, adattaScambio, 0);
                        }
                        else {
                            ora.paint(g, (ora.giorno - 1) * getB() + getShiftX()
                                       , (ora.spazio - 1) * getH() + getShiftY()
                                       , getB(), getH()
                                       , corrente, selezionata, candidata,
                                       intermedia, adattaScambio, 1);
                        }
                    }
//                    int numSpaz = Math.max(selCor.numSpazCur, selCor.numSpazSel);
                }
            }
            int xIn, yIn, xFin, yFin;
            g.setColor(Color.BLACK);
            if (selCor.esisteSelected() && selCor.esisteCandidated()) {
                if ( (idTable == 1)
                  || (idTable == 2)
                  || (idTable == 3)
                  || (idTable == 4) ) {
                    xIn = (selCor.sel1Giorno() - 1) * getB() + getB()/2 + getShiftX();
                    yIn = (selCor.sel1Spazio() - 1) * getH() + getH()/2 + getShiftY();
                    xFin= (selCor.can1Giorno() - 1) * getB() + getB()/2 + getShiftX();
                    yFin= (selCor.can1Spazio() - 1) * getH() + getH()/2 + getShiftY();
                    Library.DisegnaFreccie(xIn, yIn, xFin, yFin, g);

                }
                if ( (idTable == 3)
                  || (idTable == 6)
                  || (idTable == 5) ) {
                    if (selCor.esisteIntermetied()) {
                        xIn = (selCor.can1Giorno() - 1) * getB() + getB()/2 + getShiftX();
                        yIn = (selCor.can1Spazio() - 1) * getH() + getH()/2 + getShiftY();
                        xFin  = (selCor.interm1Giorno() - 1) * getB() + getB()/2 + getShiftX();
                        yFin  = (selCor.interm1Spazio() - 1) * getH() + getH()/2 + getShiftY();
                        Library.DisegnaFreccie(xIn, yIn, xFin, yFin, g);
                    }
                    else {
                        xIn= (selCor.can1Giorno() - 1) * getB() + getB()/2 + getShiftX();
                        yIn= (selCor.can1Spazio() - 1) * getH() + getH()/2 + getShiftY();
                        xFin = (selCor.sel1Giorno() - 1) * getB() + getB()/2 + getShiftX();
                        yFin = (selCor.sel1Spazio() - 1) * getH() + getH()/2 + getShiftY();
                        Library.DisegnaFreccie(xIn, yIn, xFin, yFin, g);
                    }
                }
                if ( (idTable == 3)
                  || (idTable == 7)
                  || (idTable == 8) ) {
                    if (selCor.esisteIntermetied()) {
                        xIn = (selCor.interm1Giorno() - 1) * getB() + getB()/2 + getShiftX();
                        yIn = (selCor.interm1Spazio() - 1) * getH() + getH()/2 + getShiftY();
                        xFin  = (selCor.sel1Giorno() - 1) * getB() + getB()/2 + getShiftX();
                        yFin  = (selCor.sel1Spazio() - 1) * getH() + getH()/2 + getShiftY();
                        Library.DisegnaFreccie(xIn, yIn, xFin, yFin, g);
                    }
                }
            }
        }
    }

    private int X2Ora(int y) {
        return y / getH() + 1;
    }

    // ritorna vero se bisogna ridisegnare la tabella dato che l'ora
    // corrente è cambiata
    public boolean cambiaOraCorrente(int newCurGiorno, int newCurSpazio,
            int cx, int cy, Docente doc) {

        OraGraph ora = null;
        boolean ridipingi = false;

        curx = cx;
        cury = cy;

        if ((newCurGiorno == -1) || (newCurSpazio == -1)
                || (statoRicerca == 3) || (statoRicerca == 4)) {
            // se il mouse è fuori dall'area della tabella
            // o è nella tabella 1 con stato uguale a 3 o 4
            // ossia c'è una cella selezionata
            // si deve solo deselezionare l'ora corrente
            ora = listaOre.get(selCor.curGiorno(), selCor.curSpazio());
            if ( (ora != null) ||
                ( ((selCor.curOra() != null) &&
                   (selCor.curGiorno() >= 1) &&
                   (selCor.curGiorno() <= GestOrarioApplet.maxNumGiorni)) &&
                  ((selCor.curOra() != null) &&
                   (selCor.curSpazio() >= 1) &&
                   (selCor.curSpazio() <= GestOrarioApplet.maxNumSpazi)) ) ) {
                ridipingi = true;
            }
            selCor.resetCurrent(getStatoRicerca());
            selCor.resetCandidated();
            setLastGiorno(0);
            setLastSpazio(0);
        }
//        else if( selCor.esisteSelected() &&
//                ((getStatoRicerca() == 3) ||
//                 (getStatoRicerca() == 4)) ) {
            // se nella tabella c'è un'ora selezionata
            // e sono nello stato 3 o 4 l'ora corrente non serve
//            ridipingi = true;
//            selCor.resetCurrent(getStatoRicerca());
//        }
        else {
            // se non è l'ora corrente
            if (selCor.esisteCurrent()) {
                if ( (selCor.curGiorno() != newCurGiorno)
                  || (selCor.curSpazio() != newCurSpazio)) {

                    OraInt oraI = new OraInt(newCurGiorno, newCurSpazio);
                    selCor.setCurrent(oraI, listaOre);
                    if ( (getLastGiorno() != newCurGiorno)
                      || (getLastSpazio() != newCurSpazio)) {
                        // se è cambiato o il giorno o lo spazio o entrambi
                        // bisogna ridipindere la tabella
                        ridipingi = true;
                    }
                }
            }
            else {
                // l'ora corrente non è ancora stata impostata o
                // è un'ora libera/vuota
                OraInt oraI = new OraInt(newCurGiorno, newCurSpazio);
                selCor.setCurrent(oraI, listaOre);

                if ( (getLastGiorno() != newCurGiorno)
                  || (getLastSpazio() != newCurSpazio)) {
                    // se è cambiato o il giorno o lo spazio o entrambi
                    // bisogna ridipindere la tabella
                    ridipingi = true;
                }
            }
            setLastGiorno(newCurGiorno);
            setLastSpazio(newCurSpazio);
        }

        setNewStatoRicerca(doc);

        return (ridipingi);
    }

    private boolean selezioneCorretta(OraGraph ora) {
        // l'ora libera non può essere selezionata come prima scelta
        if (ora == null)
            return false;

        // un'ora collegata che non è la prima non può essere selezionata
        if ( (ora != null) && (ora.getPrec() != null) )
            return false;

        // quando mi trovo nello stato 4 non si possono selezionare altre ore
        // prima bisogna deselezionare l'ora
        if (getStatoRicerca() == 4)
            return false;

        return true;
    }


    private boolean isSimpleError(int num) {
        if (num < 10)
            return true;
        else
            return false;
        /****************
        if (num == 1) {
            // messsaggio numero 1
            return false;
        }

        // messaggi dal 15 al 18
        int pot = 1;
        for (int i=1; i<23; i++) {
            pot = pot * 2;
            if (num == pot) {
                switch (i) {
                    case 13:
                    case 14:
                        return false;

                    case 15:
                    case 16:
                    case 17:
                    case 18:
                        return true;

                    case 19:
                    case 20:
                        return false;
                }
                // messsaggio numero i
            }
        }
        return false;
         ******/
    }

    public void selezione(MouseEvent e, Docente doc) {

        OraGraph ora;
        int curGiorno, selGiorno;
        int curSpazio, selSpazio;

        int radius = 8;
        if (e.getButton() == MouseEvent.BUTTON1) {
            selx = (e.getX() - getShiftX()) - (e.getX() - getShiftX()) % getB();
            sely = (e.getY() - getShiftY()) - (e.getY() - getShiftY()) % getH();
            // se sono nella tabella classe il click vuol dire
            // mostrami le informazioni sullo scambio se ci sono
            selGiorno = (selx / getB()) + 1;
            selSpazio = (sely / getH()) + 1;

            if ((idTable == 3) && (selCor.selOra1() != null)) {
                selCor.resetErrorMess();
                selCor.resetSolutionMess();
                selCor.scambioCorretto(listaOre, gestApplet, true);
                if (selCor.totErrMess() > 0) {
                    JOptionPane.showMessageDialog(null, selCor.totNumToMessErr());
                }
            }

            ora = listaOre.get(selGiorno, selSpazio);
            // se è un'ora buca o
            // se non è collegata al precedente (solo la prima ora può essere selezionata)
            if ( (getStatoRicerca() != 7) && (getStatoRicerca() != 8) ) {
                if (ora != null) {
                    // se è il secondo click sulla stessa cella vuol dire che
                    // la si vuole deselezionare
                    if ( (selCor.selOra1() != null)
                      && (selCor.sel1Giorno() == selGiorno)
                      && (selCor.sel1Spazio() == selSpazio)
                      && getHasBeenClicked() ) {
//                        deselezionaOra(ora);
                        selCor.resetSelected1();
                        selCor.resetSelected2();
                        setHasBeenClicked(false);
                    }
                    // è il primo click su una nuova cella
                    // e quindi vuol dire che si vuol selezionare
                    // una nuova cella
                    //
                    else if (selezioneCorretta(ora)) {
                        selCor.resetSelected1();
                        selCor.resetSelected2();
                        OraInt oraI = new OraInt(selGiorno, selSpazio);
                        selCor.setSelected(oraI, getStatoRicerca(), listaOre);
                        setHasBeenClicked(true);
                    }
                }
            }
        }
        else if ( (e.getButton() == MouseEvent.BUTTON3) && selCor.esisteSelected()
                  && (getStatoRicerca() != 3)) {
            // only execute this block of code if the mouse has been clicked before
            // It's essential that you use e.getX() rather than just getX()
            // which will get the x coordinate of the applet, not the click!

//            int g2 = (getLastGiorno() - getShiftX()) - (getLastGiorno() - getShiftX()) % getB();
//            int s2 = (getLastSpazio() - getShiftY()) - (getLastSpazio() - getShiftY()) % getH();
            curx = (e.getX() - getShiftX()) - (e.getX() - getShiftX()) % getB();
            cury = (e.getY() - getShiftY()) - (e.getY() - getShiftY()) % getH();
            curGiorno = (curx/getB())+1;
            curSpazio = (cury/getH())+1;

            selCor.resetErrorMess();
            selCor.resetSolutionMess();
            selCor.scambioCorretto(listaOre, gestApplet, true);
            if (selCor.totErrMess() == 0) {
                scambia(selCor.sel1Giorno(), selCor.sel1Spazio(),
                        curGiorno, curSpazio);
                selCor.resetAll();
                setHasBeenClicked(false);
            }
            else {
                OraGraph o = listaOre.get(curGiorno, curSpazio);
                if (o != null) {
                    JOptionPane.showMessageDialog(null, selCor.totNumToMessErr());
                    if (selCor.totErrMess() > 0) {
                        SecondWindow secondWin = new SecondWindow();
                        JDialog.setDefaultLookAndFeelDecorated(false);
                        JDialog dialog;
                        System.out.println("ora g:"+o.giorno+" s:"+o.spazio);
                        if ( (o.docenteConProblemi != null) && !(doc.equals(o.docenteConProblemi)) ) {
                            secondWin.intiTSecondWindow(o);
                            dialog = new JDialog(ancestorFrame, o.docenteConProblemi.nome, true);
                            dialog.getContentPane().add(secondWin);
                            dialog.pack();
                            dialog.setLocationRelativeTo(null);
                            dialog.setBounds(20, 20, 800, 600);
                            dialog.setVisible(true);
                        }
                        else if (!doc.equals(o.getDoc())) {
                            secondWin.intiTSecondWindow(o);
                            dialog = new JDialog(ancestorFrame, o.getDoc().nome, true);
                            dialog.getContentPane().add(secondWin);
                            dialog.pack();
                            dialog.setLocationRelativeTo(null);
                            dialog.setBounds(20, 20, 800, 600);
                            dialog.setVisible(true);
                        }
                    }
                }
            }
        }
        else // the applet has now been clicked for the first time.
        {
        }

        setNewStatoRicerca(doc);
        // either way, store the new x and y values
        setLastGiorno(e.getX());
        setLastSpazio(e.getY());
    }

/*
    public void initColorTab(ListaOre l, ListaInfoMatDocClassAule infoMatDocAule) {
        OraGraph ora;

        listaOre = l;

        for (int i = 0; i < getRighe(); i++) {
            for (int j = 0; j < getColonne(); j++) {
                ora = listaOre.get(i + 1, j + 1);
                if (ora != null) {
                    tabOre[i][j] = ora;
                } else {
                    tabOre[i][j] = new OraGraph(i+1, j+1, null, GestOrarioApplet.d0,
                            GestOrarioApplet.m0, GestOrarioApplet.a0, GestOrarioApplet.c0);
                }
            }
        }
    }
*/
    public Color randomColor() {
        Color c;
        int num = (int) ((Math.random() * 10) + 1);

        switch (num) {
            case 1:
                c = new Color(50, 50, 50);
                break;
            case 2:
                c = new Color(100, 100, 100);
                break;
            case 3:
                c = new Color(150, 150, 150);
                break;
            case 4:
                c = new Color(200, 200, 200);
                break;
            case 5:
                c = new Color(0, 100, 150);
                break;
            case 6:
                c = new Color(0, 150, 150);
                break;
            case 7:
                c = new Color(0, 200, 150);
                break;
            case 8:
                c = new Color(0, 250, 150);
                break;
            case 9:
                c = new Color(0, 100, 0);
                break;
            case 10:
                c = new Color(0, 150, 0);
                break;
            case 11:
                c = new Color(0, 200, 0);
                break;
            case 12:
                c = new Color(0, 250, 0);
                break;
            default:
                c = Color.white;

        }
        return c;
    }



    public int getH() {
        return h;
    }

    public int getB() {
        return b;
    }

    public int getRighe() {
        return righe;
    }

    public int getColonne() {
        return colonne;
    }

    public int getShiftX() {
        return shiftX;
    }

    public int getShiftY() {
        return shiftY;
    }

    public int getLastGiorno() {
        return lastGiorno;
    }

    public int getLastSpazio() {
        return lastSpazio;
    }

    public void setLastGiorno(int pLastX) {
        lastGiorno = pLastX;
    }

    public void setLastSpazio(int pLastY) {
        lastSpazio = pLastY;
    }

    public void setHasBeenClicked(boolean bol) {
        hasBeenClicked = bol;
    }

    public boolean getHasBeenClicked() {
        return hasBeenClicked;
    }

    public int getPunteggio() {
        return punteggio;
    }

    public int getMosse() {
        return scambi;
    }

    public void punteggio() {

        punteggio = 0;
        int temp = 0;
        // control = 0;
        for (int i = 0; i < colonne; i++) {
            for (int j = 0; j < righe - 1; j++) {
                if ((listaOre.get(i+1, j+1) != null) &&
                        (listaOre.get(i+1, j+1 + 1) != null) &&
                        (listaOre.get(i+1, j+1).equals(listaOre.get(i+1, j+1 + 1)))) {
                    j++;
                    punteggio++;
                }
//                if ((tabOre[i][j] != null) &&
//                    (tabOre[i][j+1] != null) &&
//                    (tabOre[i][j].equals(tabOre[i][j+1])))
//                    temp++;
//                else if(temp!=0)
//                {
//                    if((temp%2) == 0)
//                    {
//                       if(temp == 2)
//                           punteggio++;
//                       if(temp == 4)
//                           punteggio+=2;
//
//                    }
//                    else
//                    {
//                        punteggio++;
//
//                    }
//                    temp = 0;
//                }
            }
        }
    }

    public void punteggio2() {
        for (int i = 0; i < righe; i++) {
            for (int j = 0; j < colonne - 1; j++) {
                if ((listaOre.get(j+1, i+1) != null) &&
                        (listaOre.get(j+1 + 1, i+1) != null) &&
                        (listaOre.get(j+1, i+1).equals(listaOre.get(j+1 + 1, i+1)))) {
                    punteggio++;
                    j++;
                }

            }
        }
    }

    public void scambia(int g1, int s1, int g2, int s2) {
        OraGraph o1 = listaOre.get(g1, s1);
        OraGraph o2 = listaOre.get(g2, s2);

        // se scambio due collegate con una solitaria posta appena sopra o appena sotto
        if ( (o1 != null) && (o1.getPrec() != null) )
            s1--;

        // quando l'ora corrente è quella che segue quella selezionata
        // ossia si vuol spostare in giù di un'ora due ore di lab
        if ( (o1 != null) && (o1.getPrec() == null) && (o1.getSucc() != null)
          && (g1 == g2) && (s1+1== s2) ) {
            // caso selezione 2 ore concatenate e corrente la seconda della catena
            // confronto la prima della catena con l'ora appena dopo la catena
            // catena in giù e ora in sù
            scambia2(g1, s1+1, g2, s2+1);
            scambia2(g1, s1+1, g1, s1);
        } else if ( (o1 != null) && (o1.getPrec() == null) && (o1.getSucc() != null)
          && (g1 == g2) && (s1-1== s2) ) {
            // caso selezione 2 ore concatenate e corrente appena sopra la catena
            // confronto la seconda della catena con l'ora appena prima
            // catena in sù e ora in giù
            scambia2(g1, s1,   g2, s2);
            scambia2(g1, s1+1, g1, s1);
        }
        // quando l'ora selezionata è quella che segue le due correnti
        // ossia si vuol spostare in sù l'ora selezionata e in giù le due ore di lab
        else if ( (o2 != null) && (o2.getPrec() == null) && (o2.getSucc() != null)
          && (g1 == g2) && (s2+2== s1) ) {
            // caso correnti 2 ore concatenate e selezionata la prima dopo la catena
            // catena in giù e l'ora in su
            scambia2(g1, s2+1, g2, s1+1);
            scambia2(g1, s2+1, g1, s2);
        } else if ( (o2 != null) && (o2.getPrec() == null) && (o2.getSucc() != null)
          && (g1 == g2) && (s2-1== s1) ) {
            // caso corrent 2 ore concatenate e selezionata la prima sopra la catena
            // confronto la seconda della catena con l'ora selezionata
            // catena in sù e ora in giù
            scambia2(g1, s2,   g2, s1);
            scambia2(g1, s2+1, g1, s2);
        }
        else {
            // una delle due ore può essere un'ora libera
            scambia2(g1, s1, g2, s2);

            if ( ((o1 != null) && (o1.getSucc() != null)) ||
                 ((o2 != null) && (o2.getSucc() != null)) ) {
                scambia2(g1, s1 + 1, g2, s2 + 1);
                if ( ((o1 != null) && (o1.getSucc() != null) && (o1.getSucc().getSucc() != null)) ||
                     ((o2 != null) && (o2.getSucc() != null) && (o2.getSucc().getSucc() != null)) ) {
                    scambia2(g1, s1 + 2, g2, s2 + 2);
                }
            }


            if ( ((o1 != null) && (o1.getPrec() != null)) ||
                 ((o2 != null) && (o2.getPrec() != null)) ) {
                scambia2(g1, s1 - 1, g2, s2 - 1);
                if ( ((o1 != null) && (o1.getPrec() != null) && (o1.getPrec().getPrec() != null)) ||
                     ((o2 != null) && (o2.getPrec() != null) && (o2.getPrec().getPrec() != null)) ) {
                    scambia2(g1, s1 - 2, g2, s2 - 2);
                }
            }
        }
    }

    public void scambia2(int x1, int y1, int x2, int y2) {
        OraGraph o1 = listaOre.get(x1, y1);
        OraGraph o2 = listaOre.get(x2, y2);

        /// una delle due ore può essere un'ora libera

        if ((o1 != null) && (o2 != null)) {
            int num = o1.giorno;
            o1.giorno = o2.giorno;
            o2.giorno = num;
            num = o1.spazio;
            o1.spazio = o2.spazio;
            o2.spazio = num;
        } else if (o1 != null) {
            o1.giorno = x2;
            o1.spazio = y2;
        } else if (o2 != null) {
            o2.giorno = x1;
            o2.spazio = y1;
        }

/*
        OraGraph temp = tabOre[g1][s1];
        tabOre[g1][s1] = tabOre[g2][s2];
        tabOre[g2][s2] = temp;
*/
        scambi++;
        GestOrarioApplet.loadInfo.WriteFile(scambi);
        punteggio();
        punteggio2();
    }
}
