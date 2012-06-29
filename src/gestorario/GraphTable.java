/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorario;

import java.awt.*;  //grafica
import java.awt.event.*; //grafica
import javax.swing.*; // dialog box
import java.util.Vector;

//problema scambio due ore collegate con quella appena sotto Si fa, ma le celle evidenziate
//        fanno sembrare uno scambio errato!!!!
/**
 *
 * @author Gio
 */
public class GraphTable {

//    private Ora tabOre[][];
    private Frame ancestorFrame;
    private int idTable;
    private GraphTable succTableRig;
    private GraphTable succTableCol;
    private boolean  visible;
    private Object   classeDoc;
    private int      tipoObj;
    private Classe   classe;
    private Docente  docente;
    private Aula     aula;
    private ListaOre listaOre;
    private int b;
    private int h;
    private int righe;
    private int colonne;
    int punteggio = 0;
    private int shiftX;
    private int shiftY;
    private int lastX;
    private int lastY;
    int selx;
    int sely;
    int curx;
    int cury;

    static int selGiorno;
    static int selSpazio;
    static int numSpazSel;       // numero spazi da scambiare
    static int curGiorno;
    static int curSpazio;
    static int numSpazCur;
    static int canGiorno;  // giorno candidato
    static int canSpazio;  // ora candidata
    static int numSpazCan;


    int statoRicerca;
    private int scambi = 0;
    private int control = 0;
    // has the user clicked on the applet yet? initially, no.
    private boolean hasBeenClicked = false;
    static int MAX_NUM_MESS = 40;
    private Vector errorMess;
    private int codErrorMess;

    public GraphTable(int id, int Righe2, int Colonne2, int b2, int h2,
        int shiftX, int shiftY, Object window, int objWin, Frame f) {

        ancestorFrame = f;
        idTable = id;
        succTableRig = null;
        succTableCol = null;
        visible = false;
        resetStatoRicerca();
        classe = null;
        docente = null;
        aula = null;
        righe = Righe2;
        colonne = Colonne2;
        b = b2;
        h = h2;
        selx = 0;
        sely = 0;
        curx = 0;
        cury = 0;
        resetSelezione();
        selGiorno = -1;
        selSpazio = -1;
        numSpazSel = 0;
        curGiorno = -1;
        curSpazio = -1;
        numSpazCur = 0;
        canGiorno = -1;  // giorno candidato
        canSpazio = -1;  // ora candidata
        numSpazCan = 0;
        this.shiftX = shiftX;
        this.shiftY = shiftY;
        errorMess = new Vector();
//        for (int i = 0; i < MAX_NUM_MESS; i++)
//            errorMess.add("");
    }

    private void resetErrorMess() {
        errorMess.clear();
//        for (int i = 0; i < MAX_NUM_MESS; i++)
//            errorMess.add("");

    }
    public void setSuccTableRig(GraphTable succ) {
        succTableRig = succ;
    }
    
    public void setSuccTableCol(GraphTable succ) {
        succTableCol = succ;
    }

    public void setList(Object obj, int tObj, ListaInfoMatDocClassAule materieDocenti) {

        tipoObj = tObj;
//        tabOre = new Ora[righe][colonne];
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
            if (succTableCol != null)
              succTableCol.resetStatoRicerca();
        }
        if (aula != null) {
            statoRicerca = 0;
            setVisible(false);
            if (succTableRig != null)
              succTableRig.resetStatoRicerca();
            if (succTableCol != null)
              succTableCol.resetStatoRicerca();
        }
        if (classe != null) {
            statoRicerca = 5;
            setVisible(false);
            if (succTableRig != null)
              succTableRig.resetStatoRicerca();
            if (succTableCol != null)
              succTableCol.resetStatoRicerca();
        }
    }

    public void setNewStatoRicerca() {
        //   corr sel1 tab1 succ1
        // 1  no   no  doc    5          - visibile senza info
        if ( (!esisteCurrent()) && (!esisteSelezione()) && ( (docente != null) || (aula != null) ) ) {
//            if (statoRicerca != 1) {
                statoRicerca = 1;
                if (succTableRig != null) {
                    succTableRig.resetStatoRicerca();
                }
                if (succTableCol != null) {
                    succTableCol.resetStatoRicerca();
                }
//            }
        }
        //   corr sel1 tab1 succ1
        // 2  si   no  doc    6 con corr - visibile con info corr
        if ( (esisteCurrent()) && (!esisteSelezione()) && ( (docente != null) || (aula != null) ) ) {
//            if (statoRicerca != 2) {
                statoRicerca = 2;
                Ora ora = listaOre.get(curGiorno, curSpazio);
                if (succTableRig != null) {
                    if (ora != null) {
                        succTableRig.setList(ora.classe, GestOrarioApplet.TIPOCLASSE, null);
                        succTableRig.setCurrent(curGiorno, curSpazio);
                        succTableRig.resetSelezione();
//                        succTableRig.setStatoRicerca(6);
                        succTableRig.setNewStatoRicerca();
                    }
                }
                if (succTableCol != null) {
                    if (ora != null) {
                        if ( (docente != null) && (ora.getDoc() == docente) ) {
                                if (ora.getDocCom() != null) {
                                    succTableCol.setList(ora.getDocCom(), GestOrarioApplet.TIPODOCENTE, null);
                                    succTableCol.setCurrent(curGiorno, curSpazio);
                                    succTableCol.resetSelezione();
//                                  succTableCol.setStatoRicerca(2);
                                    succTableCol.setNewStatoRicerca();
                                }
                                else {
                                    succTableCol.resetStatoRicerca();
                                }
                        } else if ( (docente != null) && (ora.getDocCom() == docente) ) {
                                if (ora.getDoc() != null) {
                                    succTableCol.setList(ora.getDoc(), GestOrarioApplet.TIPODOCENTE, null);
                                    succTableCol.setCurrent(curGiorno, curSpazio);
//                                  succTableCol.setStatoRicerca(2);
                                    succTableCol.setNewStatoRicerca();
                                }
                                else {
                                    succTableCol.resetStatoRicerca();
                                }
                        }
                    }
                }
//            }
        }
        //   corr sel1 tab1 succ1
        // 3  no   si  doc    7 con sel  - visibile scelto prima ora visualizzo info sel
        if ( (!esisteCurrent()) && (esisteSelezione()) && ( (docente != null) || (aula != null) ) ) {
//            if (statoRicerca != 3) {
                statoRicerca = 3;
                Ora ora = listaOre.get(selGiorno, selSpazio);
                if (succTableRig != null) {
                    succTableRig.setList(ora.classe, GestOrarioApplet.TIPOCLASSE, null);
                    succTableRig.resetCurrent();
                    succTableRig.setSelezione(selGiorno, selSpazio);
//                  succTableRig.setStatoRicerca(7);
                    succTableRig.setNewStatoRicerca();
                }
                if (succTableCol != null) {
                    if (ora != null) {
                        if ( (docente != null) && (ora.getDoc() == docente) ) {
                                if (ora.getDocCom() != null) {
                                    succTableCol.setList(ora.getDocCom(), GestOrarioApplet.TIPODOCENTE, null);
                                    succTableCol.resetCurrent();
                                    succTableCol.setSelezione(selGiorno, selSpazio);
                                    succTableCol.setNewStatoRicerca();
                                }
                                else {
                                    succTableCol.resetStatoRicerca();
                                }
                        } else if ( (docente != null) && (ora.getDocCom() == docente) ) {
                                if (ora.getDoc() != null) {
                                    succTableCol.setList(ora.getDoc(), GestOrarioApplet.TIPODOCENTE, null);
                                    succTableCol.resetCurrent();
                                    succTableCol.setSelezione(selGiorno, selSpazio);
                                    succTableCol.setNewStatoRicerca();
                                }
                                else {
                                    succTableCol.resetStatoRicerca();
                                }
                        }
                    }
                }
//            }
        }
        //   corr sel1 tab1 succ1
        // 4  si   si  doc    7 con sel  - visibile scelto prima ora visualizzo info sel
        if ( (esisteCurrent()) && (esisteSelezione()) && ( (docente != null) || (aula != null) ) ) {
//            if (statoRicerca != 4) {
                statoRicerca = 4;
                Ora ora = listaOre.get(curGiorno, curSpazio);
                if (succTableRig != null) {
                    succTableRig.setList(ora.classe, GestOrarioApplet.TIPOCLASSE, null);
                    succTableRig.setCurrent(curGiorno, curSpazio);
                    succTableRig.setSelezione(selGiorno, selSpazio);
//                    succTableRig.setStatoRicerca(7);
                      succTableRig.setNewStatoRicerca();
                }
                if (succTableCol != null) {
                    if (ora != null) {
                        if ( (docente != null) && (ora.getDoc() == docente) ) {
                                if (ora.getDocCom() != null) {
                                    succTableCol.setList(ora.getDocCom(), GestOrarioApplet.TIPODOCENTE, null);
                                    succTableCol.setCurrent(curGiorno, curSpazio);
                                    succTableCol.setSelezione(selGiorno, selSpazio);
                                    succTableCol.setNewStatoRicerca();
                                }
                                else {
                                    succTableCol.resetStatoRicerca();
                                }
                        } else if ( (docente != null) && (ora.getDocCom() == docente) ) {
                                if (ora.getDoc() != null) {
                                    succTableCol.setList(ora.getDoc(), GestOrarioApplet.TIPODOCENTE, null);
                                    succTableCol.setCurrent(curGiorno, curSpazio);
                                    succTableCol.setSelezione(selGiorno, selSpazio);
                                    succTableCol.setNewStatoRicerca();
                                }
                                else {
                                    succTableCol.resetStatoRicerca();
                                }
                        }
                    }
                }
//            }
        }

        //   corr sel1 tab1 succ1
        // 5  no   no  cl     0         - invisibile
        if ( (!esisteCurrent()) && (!esisteSelezione()) && (classe != null) ) {
//            if (statoRicerca != 5) {
                statoRicerca = 5;
                resetStatoRicerca();
//            }
        }
        //   corr sel1 tab1 succ1
        // 6  si   no  cl     0          - visibile senza info
        if ( (esisteCurrent()) && (!esisteSelezione()) && (classe != null) ) {
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
        if ( (!esisteCurrent()) && (esisteSelezione()) && (classe != null) ) {
//            if (statoRicerca != 7) {
                statoRicerca = 7;
                if (succTableRig != null) {
                    succTableRig.resetStatoRicerca();
                }
                Ora ora = listaOre.get(selGiorno, selSpazio);
                if ( (ora != null) && (succTableCol != null) && (ora.getDocCom() != null) ) {
                    succTableCol.setList(ora.aula, GestOrarioApplet.TIPOAULA, null);
                    succTableCol.setCurrent(curGiorno, curSpazio);
                    succTableCol.setSelezione(selGiorno, selSpazio);
                    succTableCol.setNewStatoRicerca();
                }
                else {
                    succTableCol.resetStatoRicerca();
                }
//            }
        }
        //   corr sel1 tab1 succ1
        // 8  si   si  cl     1          - scelto prima ora visualizzo info corr
        if ( (esisteCurrent()) && (esisteSelezione()) && (classe != null) ) {
//            if (statoRicerca != 8) {
                statoRicerca = 8;
                Ora ora = listaOre.get(curGiorno, curSpazio);
                if ( (ora != null) && (succTableRig != null) && (ora.getDoc() != null) ) {
                    succTableRig.setList(ora.getDoc(), GestOrarioApplet.TIPODOCENTE, null);
                    succTableRig.setCurrent(curGiorno, curSpazio);
                    succTableRig.setSelezione(selGiorno, selSpazio);
                    succTableRig.setNewStatoRicerca();
                }
                // o visualizzo le ore dell'aula speciare associata all'ora corrente
                // o quelle dell'aula associata all'ora selezionata
                // o nulla
                if ( (ora != null) && (succTableCol != null) && (ora.getDocCom() != null) ) {
                    succTableCol.setList(ora.aula, GestOrarioApplet.TIPOAULA, null);
                    succTableCol.setCurrent(curGiorno, curSpazio);
                    succTableCol.setSelezione(selGiorno, selSpazio);
                    succTableCol.setNewStatoRicerca();
                }
                else {
                    ora = listaOre.get(selGiorno, selSpazio);
                    if ( (ora != null) && (succTableCol != null) && (ora.getDocCom() != null) ) {
                        succTableCol.setList(ora.aula, GestOrarioApplet.TIPOAULA, null);
                        succTableCol.setCurrent(curGiorno, curSpazio);
                        succTableCol.setSelezione(selGiorno, selSpazio);
                        succTableCol.setNewStatoRicerca();
                    }
                    else {
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
    public void paintTabella(Graphics g) {

        Ora ora = null;
        
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
                    ora = listaOre.get(curGiorno, curSpazio);
                    if (ora != null) {
//                        ora.setSelezionata(1);
//                        ora.paintInfo(g, getShiftX(), getShiftY() + (righe * getH()) + 20, false);
                        ora.paintInfo(g, shiftX, shiftY + (righe * getH()) + 10, false);
                    }
                    break;
                case 3:
                    // 3  no   si  doc    7 con sel  - visibile scelto prima ora visualizzo info sel
                    setVisible(true);
//                    g.drawString("Giorno Sel: " + GestOrarioApplet.giorno2Str(selGiorno, false),
//                            getShiftX()+getB()*3, shiftY - 2*g.getFont().getSize() - 3);
//                    g.drawString("Ora Sel: " + selSpazio,
//                            getShiftX()+getB()*5, shiftY - 2*g.getFont().getSize() - 3);
//                    ora = listaOre.get(selGiorno, selSpazio);
//                    if (ora != null) {
//                        ora.setSelezionata(2);
//                    }
                    break;
                case 4:
                    // 4  si   si  doc    7 con sel  - visibile scelto prima ora visualizzo info sel
                    setVisible(true);
//                    g.drawString("Giorno Sel: " + GestOrarioApplet.giorno2Str(selGiorno, false),
//                            getShiftX()+getB()*3, shiftY - 2*g.getFont().getSize() - 3);
//                    g.drawString("Ora Sel: " + selSpazio,
//                            getShiftX()+getB()*5, shiftY - 2*g.getFont().getSize() - 3);
//                    ora = listaOre.get(selGiorno, selSpazio);
//                    if (ora != null) {
//                        ora.setSelezionata(2);
//                    }
//                    g.drawString("Giorno Cur: " + GestOrarioApplet.giorno2Str(curGiorno, false),
//                            getShiftX()+getB()*3, shiftY + (righe * getH()) + 10);
//                    g.drawString("Ora Cur: " + curSpazio,
//                            getShiftX()+getB()*5, shiftY + (righe * getH()) + 10);
                    ora = listaOre.get(curGiorno, curSpazio);
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
                    ora = listaOre.get(curGiorno, curSpazio);
                    if (ora != null) {
//                        ora.setSelezionata(1);
                        ora.paintInfo(g, shiftX, shiftY + (righe * getH()) + 10, false);
                    }
                    setVisible(true);
                    break;
                case 7:
                    // 7  no   si  cl     0          - scelto prima ora senza info
                    setVisible(true);
//                    g.drawString("Giorno Sel: " + GestOrarioApplet.giorno2Str(selGiorno, false),
//                            getShiftX()+getB()*3, shiftY - 2*g.getFont().getSize() - 3);
//                    g.drawString("Ora Sel: " + selSpazio,
//                            getShiftX()+getB()*5, shiftY - 2*g.getFont().getSize() - 3);
                    ora = listaOre.get(selGiorno, selSpazio);
                    if (ora != null) {
//                        ora.setSelezionata(2);
                        ora.paintInfo(g, shiftX, shiftY + (righe * getH()) + 10, false);
                    }
                    break;
                case 8:
                    // 8  si   si  cl     1          - scelto prima ora visualizzo info corr
                    setVisible(true);
//                    g.drawString("Giorno Sel: " + GestOrarioApplet.giorno2Str(selGiorno, false),
//                            getShiftX()+getB()*3, shiftY - 2*g.getFont().getSize() - 3);
//                    g.drawString("Ora Sel: " + selSpazio,
//                            getShiftX()+getB()*5, shiftY - 2*g.getFont().getSize() - 3);
//                    g.drawString("Giorno Cur: " + GestOrarioApplet.giorno2Str(curGiorno, false),
//                            getShiftX()+getB()*3, shiftY + (righe * getH()) + 10);
//                    g.drawString("Ora Cur: " + curSpazio,
//                            getShiftX()+getB()*5, shiftY + (righe * getH()) + 10);
                    ora = listaOre.get(curGiorno, curSpazio);
                    if (ora != null) {
//                        ora.setSelezionata(2);
                        ora.paintInfo(g, shiftX, shiftY + (righe * getH()) + 10, false);
                    }
//                    ora = listaOre.get(selGiorno, selSpazio);
//                    if (ora != null) {
//                        ora.setSelezionata(2);
//                    }
                    break;
                default:
                    break;
        }




/*
            g.setColor(Color.black);
            g.draw3DRect(selx + getShiftX(), sely + getShiftY(), getB(), getH(), true);
*/

        if (getVisible()) {
            // Intestazione tabella
            g.drawString("Stato"+idTable+": "+getStatoRicerca(), shiftX, shiftY - 2*g.getFont().getSize() - 3);
            if (classe != null) {
                // visualizzo classe Corrente in alto dopo lo stato
                classe.paint(g, getShiftX()+getB()*2, shiftY - 2*g.getFont().getSize() - 3, false);
            }
            if (docente != null) {
                docente.paint(g, shiftX, shiftY - g.getFont().getSize() - 3, false);
            }
            if (aula != null) {
                // visualizzo classe Corrente in alto dopo lo stato
                aula.paint(g, getShiftX()+getB()*2, shiftY - 2*g.getFont().getSize() - 3, false);
            }

            for (int i = 0; i < getRighe(); i++) {
                g.setColor(Color.BLACK);
                g.drawString(" "+(i+1), getShiftX() - 12, i * getH() + getShiftY() + 12);
                g.drawString(GestOrarioApplet.giorno2Str(i + 1, true), i * getB() + getShiftX(), getShiftY() - 2);
                for (int j = 0; j < getColonne(); j++) {
                    ora = listaOre.get(i + 1, j + 1);
                    int numSpaz = Math.max(numSpazCur, numSpazSel);
                    int tmpSelSpazio = selSpazio;
                    Ora o1 = listaOre.get(selGiorno, selSpazio);
                    Ora o2 = listaOre.get(curGiorno, curSpazio);
                    if ( (o2 != null) && (o2.getPrec() != null)
                          && (o1 != null) && (o1.getPrec() == null) && (o1.getSucc() == null)  ) {
                        tmpSelSpazio --;
                    }

                    if (ora != null) {
                        // l'ora viene deselezionata e poi si controlla se non è vero
                        ora.setSelezionata(0);

                        if ( (idTable == 3) && (esisteSelezione()) ) {
                            resetErrorMess();
                            int errorM = scambioCorretto(selGiorno, tmpSelSpazio, i+1, j+1, 0);
                            if (errorM != 0) {
                                if (isSimpleError(errorM)) {
                                    ora.setSelezionata(-2);
                                }
                                else
                                    ora.setSelezionata(-1);
                            }
                        }
                        if (i+1 == canGiorno) {
                            // controllo se l'ora corrente è tra quelle candidate
                            for (int s = 0; s < numSpazCan; s++) {
                                if (j+1==canSpazio+s) {
                                    ora.setSelezionata(3);
                                }
                            }
                        }
                        if (i+1 == selGiorno) {
                            // controllo se l'ora corrente è tra quelle selezionate
                            for (int s = 0; s < numSpaz; s++) {
                                if (j+1==tmpSelSpazio+s) {
                                    ora.setSelezionata(2);
                                }
                            }
                        }
                        if (i+1 == curGiorno) {
                            int dir = 1;
                            Ora o = listaOre.get(curGiorno, curSpazio);
                            if ( (o != null) && (o.getPrec() != null) 
                               && !((curGiorno == selGiorno) && (curSpazio == selSpazio + 1)) )
                                // se sono posizionato sulla seconda ora di una catena
                                // che non sia quella selezionata
                                dir = -1;
                            // controllo se l'ora corrente è tra quelle correnti
                            for (int s = 0; s < numSpaz; s++) {
                                if (j+1==curSpazio+(s*dir)) {
                                    ora.setSelezionata(1);
                                }
                            }
                        }
                        if (classe != null) {
                            ora.paint(g, (ora.giorno - 1) * getB() + getShiftX()
                                       , (ora.spazio - 1) * getH() + getShiftY(), getB(), getH()
                                       , 0);
                        }
                        else {
                            ora.paint(g, (ora.giorno - 1) * getB() + getShiftX()
                                       , (ora.spazio - 1) * getH() + getShiftY(), getB(), getH()
                                       , 1);
                        }
                        //                    tabOre[i][j] = ora;
                    } else {
                        // ora bloccata o inesistente
                        if (docente != null) {
                             ora = docente.getBloccata(i+1, j+1);
                        }
                        if ( (ora != null) || ((docente != null) && (docente.giornoLibero == i+1)) ) {
                             g.setColor(Color.GRAY);
                        }
                        else {
                            g.setColor(Color.WHITE);
                        }

                        if (i+1 == canGiorno) {
                            // controllo se l'ora corrente è tra quelle candidate
                            for (int s = 0; s < numSpazCan; s++) {
                                if (j+1==canSpazio+s) {
                                    g.setColor(new Color(205, 100, 0, 125));
                                }
                            }
                        }
                        if (i+1 == selGiorno) {
                            // controllo se l'ora corrente è tra quelle selezionate
                            for (int s = 0; s < numSpaz; s++) {
                                if (j+1==tmpSelSpazio+s) {
                                    g.setColor(new Color(255, 150, 0, 125));
                                }
                            }
                        }
                        if (i+1 == curGiorno) {
                            int dir = 1;
                            Ora o = listaOre.get(curGiorno, curSpazio);
                            if ( (o != null) && (o.getPrec() != null) )
                                dir = -1;
                            // controllo se l'ora corrente è tra quelle correnti
                            for (int s = 0; s < numSpaz; s++) {
                                if (j+1==curSpazio+s) {
                                    g.setColor(new Color(255, 150, 0, 125));
                                }
                            }
                        }

                        g.fillRect(i * getB() + getShiftX(), j * getH() + getShiftY(), getB(), getH());

                        g.setColor(Color.GRAY);
                        g.drawRect(i * getB() + getShiftX(), j * getH() + getShiftY(), getB(), getH());

                        if ( (idTable == 3) && (esisteSelezione()) ) {
                            resetErrorMess();
                            int errorM = scambioCorretto(selGiorno, tmpSelSpazio, i+1, j+1, 0);
                            if (errorM != 0) {
                                if (isSimpleError(errorM)) {
                                    // evidenzio la cella come non adatta allo scambio (vedi anche Ora)
                                    Ora.paintNoScambio(g, i * getB() + getShiftX(),
                                                  j * getH() + getShiftY(), getB(), getH(), -2);
                                }
                                else {
                                    Ora.paintNoScambio(g, i * getB() + getShiftX(),
                                                  j * getH() + getShiftY(), getB(), getH(), -1);
                                }
                            }
                        }
                        //                    tabOre[i][j] = new Ora(i + 1, j + 1, null, null, null, null, Color.WHITE);
                    }
                }
            }
            if ((classe != null) && esisteSelezione() && (idTable == 3)) {
                if (codErrorMess != 0) {
                    g.setColor(Color.RED);
                    g.drawString("*** SCAMBIO IMPOSSIBILE ***", shiftX, shiftY - g.getFont().getSize() - 3);
                }
                else  {
                    g.setColor(Color.GREEN);
                    g.drawString("*** SCAMBIO POSSIBILE ***", shiftX, shiftY - g.getFont().getSize() - 3);
                }
            }

        }
/*
        for (int i = 0; i < getRighe(); i++) {
            for (int j = 0; j < getColonne(); j++) {
                if (tabOre[i][j] != null) {
                    g.setColor(tabOre[i][j].colore());
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(i * getB() + getShiftX(), j * getH() + getShiftY(), getB(), getH());
            }
        }
  */
    }

    private int X2Ora(int y) {
        return y / getH() + 1;
    }

    // ritorna vero se bisogna ridisegnare la tabella dato che l'ora
    // corrent è cambiata
    public boolean cambiaOraCorrente(MouseEvent e) {

        Ora ora;
        boolean ridipingi = false;

        // se il mouse è fuori dall'area della tabella si deve solo deselezionare l'ora corrente
        if ( ((e.getX() - getShiftX()) < 0)
          || ((e.getY() - getShiftY()) < 0)
          || ((e.getX() - getShiftX()) > (colonne) * b)
          || ((e.getY() - getShiftY()) > (righe) * h) ) {
            ora = listaOre.get(curGiorno, curSpazio);
            if ( (ora != null) ||
                ( (curGiorno >= 1) && (curGiorno <= GestOrarioApplet.maxNumGiorni)
                && (curSpazio >= 1) && (curSpazio <= GestOrarioApplet.maxNumSpazi) ) ) {
//                deselezionaOra(ora);
                ridipingi = true;
            }
            resetCurrent();
        }
        else if( esisteSelezione() && ((getStatoRicerca() == 3)  || (getStatoRicerca() == 4)) ) {
            // se nella tabella c'è un'ora selezionata
            // e sono nello stato 3 o 4 l'ora corrente non serve
            ora = listaOre.get(curGiorno, curSpazio);
            if ((ora != null) && ((selGiorno != curGiorno) || (selSpazio != curSpazio))) {
                ridipingi = false;
            }
            resetCurrent();
        }
        else {
            curx = (e.getX() - getShiftX()) - (e.getX() - getShiftX()) % getB();
            cury = (e.getY() - getShiftY()) - (e.getY() - getShiftY()) % getH();

            int newCurGiorno = (curx / getB()) + 1;
            int newCurSpazio = (cury / getH()) + 1;

            // se non è l'ora corrente
            if ((curGiorno != newCurGiorno) || (curSpazio != newCurSpazio)) {
/*****
                ora = listaOre.get(newCurGiorno, newCurSpazio);

                // se esiste l'ora corrente precedente la deseleziono
                // a meno che non sia l'ora selezionata per lo scambio
                if ((ora != null) && ((selGiorno != curGiorno) || (selSpazio != curSpazio))) {
//                    deselezionaOra(ora);
                    ridipingi = true;
                }
 
                if ( (ora != null) && (ora.getPrec() != null) ) {
                    // ci si trova sull'ora collegata a quella corrente
                    // seleziono la precedente perchè la seconda ora di una coppia
                    // non può essere selezionata
                    newCurSpazio = newCurSpazio-1;
                }
*/
/******                ora = listaOre.get(newCurGiorno, newCurSpazio);


                if ( (ora != null) && (ora.getPrec() != null) && (ora.getSucc() == null)
                  && !((selGiorno == newCurGiorno) && (selSpazio+1==newCurSpazio)) ) {
                    newCurSpazio = newCurSpazio-1;
                }
*/
                if ((curGiorno != newCurGiorno) || (curSpazio != newCurSpazio)) {
                    if (esisteSelezione()) {
                        resetErrorMess();
                        codErrorMess = scambioCorretto(selGiorno, selSpazio,
                                      newCurGiorno, newCurSpazio, 0);
                    }
                    ridipingi = setCurrent(newCurGiorno, newCurSpazio);
                }
            }
        }

        setNewStatoRicerca();

        if (ridipingi) {
            return true;
        }
        else
            return false;
    }

    private boolean selezioneCorretta(Ora ora) {
        boolean corretta = true;

        // l'ora libera non può essere selezionata come prima scelta
        if (ora == null)
            return false;

        // un'ora collegata che non è la prima non può essere selezioanta
        if ( (ora != null) && (ora.getPrec() != null) )
            return false;

        // quando mi trovo nello stato 4 non si possono selezionare altre ore
        // prima bisogna deselezionare l'ora
        if (getStatoRicerca() == 4)
            return false;

        return corretta;
    }

    private int addMessErr(int totMess, int numMess, String mess) {
//        errorMess.ensureCapacity(MAX_NUM_MESS);
//        errorMess.removeElementAt(numMess);
//        errorMess.insertElementAt(mess, numMess);
        errorMess.add(mess);
        return totMess + numMess;
//        return totMess+ (int) Math.pow(2, numMess);
/*****

        switch (numMess) {
            case 1:
                return totMess+1;
            case 2:
                return totMess+2;
            case 3:
                return totMess+4;
            case 4:
                return totMess+8;
            case 5:
                return totMess+16;
            case 6:
                return totMess+32;
            case 7:
                return totMess+64;
            case 8:
                return totMess+128;
            case 9:
                return totMess+256;
            default:
                return totMess;
        }
*/
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

    private String totNumToMessErr(int num) {
        String mess = "";
        for (int i=0; i<errorMess.size(); i++) {
                mess += (String) errorMess.get(i) + "\n";
        }

        /******
        if (num%2 != 0) {
            // messsaggio numero 1
            mess += (String) errorMess.get(1) + "\n";
        }

        // messaggi dal 2 in avanti
        int pot = 1;
        for (int i=1; i<23; i++) {
            pot = pot * 2;
            if ((num/pot)%2 != 0) {
                // messsaggio numero i
                mess += (String) errorMess.get(i) + "\n";
            }
        }
*/
/****
 
        if ((num/4)%2 != 0) {
            // messsaggio numero 3
            mess += (String) errorMess.get(2);
        }
        if ((num/8)%2 != 0) {
            // messsaggio numero 4
            mess += (String) errorMess.get(3);
        }
        if ((num/16)%2 != 0) {
            // messsaggio numero 5
            mess += (String) errorMess.get(4);
        }
        if ((num/32)%2 != 0) {
            // messsaggio numero 6
            mess += (String) errorMess.get(5);
        }
 */
        return mess;
    }

    // restituisce vero se lo scambio è possibile
    // giorni e spazi partono da 1
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // ASSUNZIONE: non ci possono essere più di 3 ore collegate tra loro
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    public int scambioCorretto(int g1, int s1, int g2, int s2, int totMess) {
        // prima di tutto bisogna andare a trovare le ore poste all'inizio
        // e poi si richiama scambioCorretto(...)
        Ora o1 = listaOre.get(g1, s1);
        Ora o2 = listaOre.get(g2, s2);

        // non dovvrebbe essere il caso ma non si sa mai
        // se l'ora selezionata non è la prima della catena
        // si trova la prima
        if ( (o1 != null) && (o1.getPrec() != null) )
            s1--;

        // quando l'ora corrente è quella che segue quella selezionata
        // ossia si vuol spostare in giù o in sù di un'ora le due ore di lab
        if ( (o1 != null) && (o1.getPrec() == null) && (o1.getSucc() != null)
          && (g1 == g2) && (s1+1== s2) ) {
            // caso selezione 2 ore concatenate e corrente la seconda della catena
            // confronto la prima della catena con l'ora appena dopo la catena
            return scambioCorrettoRic(g1, s1, g2, s2+1, true, totMess);
        } else if ( (o1 != null) && (o1.getPrec() == null) && (o1.getSucc() != null)
          && (g1 == g2) && (s1-1== s2) ) {
            // caso selezione 2 ore concatenate e corrente appena sopra la catena
            // confronto la seconda della catena con l'ora appena prima
            return scambioCorrettoRic(g1, s1+1, g2, s2, true, totMess);
        }
        // quando l'ora selezionata è quella che segue le due correnti
        // ossia si vuol spostare in sù l'ora selezionata e in giù le due ore di lab
        else if ( (o2 != null) && (o2.getPrec() == null) && (o2.getSucc() != null)
          && (g1 == g2) && (s2+2== s1) ) {
            // caso correnti 2 ore concatenate e selezionata la prima dopo la catena
            // scambio la prima della catena con l'ora selezionata
            return scambioCorrettoRic(g1, s1, g2, s2, true, totMess);
        } else if ( (o2 != null) && (o2.getPrec() == null) && (o2.getSucc() != null)
          && (g1 == g2) && (s2-1== s1) ) {
            // caso corrent 2 ore concatenate e selezionata la prima sopra la catena
            // confronto la seconda della catena con l'ora selezionata
            return scambioCorrettoRic(g1, s1+1, g2, s2, true, totMess);
        }
        else {
            // se siamo qui i casi particolari sono esclusi e si considerano solo
            // i casi normali:
            // se l'ora corrente è in una catena ma non è la prima si trova la prima
            if ( (o2 != null) && (o2.getPrec() != null) )
                s1--;
            // ora le due sono:
            // entrambe ore semplici
            // entrambe ore iniziali di catene
            // solo una delle due inizi di catene
            // quindi si confrontano le due ore e poi se una o entrambe sono catene la seconda
            return scambioCorrettoRic(g1, s1, g2, s2, false, totMess);
        }
    }

    public int scambioCorrettoRic(int g1, int s1, int g2, int s2, boolean casoParticolare, int totMess) {

        if ( (g1 < 1) || (g2 < 1) || (s1 < 1) || (s2 < 1)
             || (g1 > GestOrarioApplet.maxNumGiorni)
             || (g2 > GestOrarioApplet.maxNumGiorni)
             || (s1 > GestOrarioApplet.maxNumSpazi)
             || (s2 > GestOrarioApplet.maxNumSpazi) ) {
            totMess = addMessErr(totMess, 10,
                    "Per effettuare lo scambio servirebbe un'ora al di fuori dell'orario scolastico.");
            return totMess;
        }
        Ora o1 = listaOre.get(g1, s1);
        Ora o2 = listaOre.get(g2, s2);



        if ((o1 != null) && (o1.getSucc() != null) && (s2 > GestOrarioApplet.maxNumSpazi)) {
            totMess = addMessErr(totMess, 10,
                    "Per effettuare lo scambio servirebbe un'ora al di fuori dell'orario scolastico.");
            return totMess;
        }

        if ((o2 != null) && (o2.getSucc() != null) && (s1 > GestOrarioApplet.maxNumSpazi)) {
            totMess = addMessErr(totMess, 10,
                    "Per effettuare lo scambio servirebbe un'ora al di fuori dell'orario scolastico.");
            return totMess;
        }

        // non dovrebbe succedere, ma non si sa mai
        // però occorre controllare il caso particolare in cui si deve
        // controllare l'ora che precede una catena con l'ultima della catena
        if ( (o1 != null) && (o1.getPrec() != null) && (o1.getSucc() == null)
          && (o2 != null) && (o2.getPrec() == null) && (o2.getSucc() != null)
                && !((g1 == g2) && (s1-2 == s2)) ) {
            totMess = addMessErr(totMess, 11,
                "Per scambiare ore collegate selezione e corrente dovrrebbero essere allineate.");
        }
        // non dovrebbe succedere, ma non si sa mai
        if ( (o1 != null) && (o1.getPrec() != null) && (o1.getSucc() == null)
          && (o2 != null) && (o2.getPrec() == null) && (o2.getSucc() != null)
                && !((g1 == g2) && (s2-2 == s1))  ) {
            totMess = addMessErr(totMess, 11,
                "Per scambiare ore collegate selezione e corrente dovrrebbero essere allineate");
        }

        // è inutile scambiare due ore vuote
        if ((o1 == null) && (o2 == null)) {
            totMess = addMessErr(totMess, 12,
                "E' inutile scambiare 2 ore libere.");
            return totMess;
        }

        // è inutile scambiare un'ora con se stessa
        if ((o1 != null) && (o2 != null) && (o1.equals(o2))) {
            totMess = addMessErr(totMess, 13,
                "Stessa ora.");
            return totMess;
        }

        // è inutile scambiare due ore dello stesso docente nella stessa classe
        // fa eccezione il caso (che non si dovrebbe verificare):
        // sposto in giù di 1 o su di 1 le ore di laboratorio
        // per cui si potrebbe dover controllare la prima di lab
        // con la seconda di lab
        if ( (o1 != null) && (o2 != null)
                && !((g1 == g2) && (s1+1 == s2)) ) {
            Docente dT1 = (Docente) o1.getDoc();
            Docente dT2 = (Docente) o2.getDoc();
            Docente dL1 = (Docente) o1.getDocCom();
            Docente dL2 = (Docente) o2.getDocCom();
            if ( (dT1 == dT2) && (dL1 == dL2) && (o1.classe.equals(o2.classe))) {
            totMess = addMessErr(totMess, 14,
                "Le ore da scambiare sono identiche.");
                return totMess;
            }
            /****
            for (int i = 0; i < o1.docenti.size(); i++) {
                d1 = (Docente) o1.docenti.elementAt(i);
                for (int j = 0; j < o2.docenti.size(); j++) {
                    d2 = (Docente) o2.docenti.elementAt(i);
                    if ((d1 == d2) && (o1.classe == o2.classe)) {
                        mess = "Entrambe le ore sono di "+d1.nome+".";
                        return mess;
                    }
                }
            }
             *****/
        }

        // se una delle due ore è nel giorno libero/ora bloccata del/dei
        // docente/i non si può fare (il giorno libero non dovrebbe essere
        // selezionato, ma non si sa mai)
        if (o1 != null) {
            Docente d = (Docente) o1.getDoc();
            if ( (d != null) && (d.giornoLibero == g2) ) {
                totMess = addMessErr(totMess, 15,
                    GestOrarioApplet.giorno2Str(g2,true)+" è il giorno libero di "+d.nome);
//                return mess;
            }
            if ( (d != null) && (d.getBloccata(g2, s2) != null) ) {
                totMess = addMessErr(totMess, 16,
                    GestOrarioApplet.giorno2Str(g2,true)+" "+s2+" ora è bloccata per "+d.nome);
//                return mess;
            }


            d = (Docente) o1.getDocCom();
            if ( (d != null) && (d.giornoLibero == g2) ) {
                totMess = addMessErr(totMess, 17,
                    GestOrarioApplet.giorno2Str(g2,true)+" è il giorno libero di "+d.nome);
//                return mess;
            }
            if ( (d != null) && (d.getBloccata(g2, s2) != null) ) {
                totMess = addMessErr(totMess, 18,
                    GestOrarioApplet.giorno2Str(g2,true)+" "+s2+" ora è bloccata per "+d.nome);
//                return mess;
            }
        }

        if (o2 != null) {
            Docente d = (Docente) o2.getDoc();
            if ( (d != null) && (d.giornoLibero == g1) ) {
                totMess = addMessErr(totMess, 19,
                    GestOrarioApplet.giorno2Str(g1,true)+" è il giorno libero di "+d.nome);
//                return mess;
            }
            if ( (d != null) && (d.getBloccata(g1, s1) != null) ) {
                totMess = addMessErr(totMess, 20,
                    GestOrarioApplet.giorno2Str(g1,true)+" "+s1+" ora è bloccata per "+d.nome);
//                return mess;
            }

            d = (Docente) o2.getDocCom();
            if ( (d != null) && (d.giornoLibero == g1) ) {
                totMess = addMessErr(totMess, 21,
                    GestOrarioApplet.giorno2Str(g1,true)+" è il giorno libero di "+d.nome);
//                return mess;
            }
            if ( (d != null) && (d.getBloccata(g1, s1) != null) ) {
                totMess = addMessErr(totMess, 22,
                    GestOrarioApplet.giorno2Str(g1,true)+" "+s1+" ora è bloccata per "+d.nome);
//                return mess;
            }
        }

        // se per il docente di o1 l'ora o2 è già occupata da un'ora in
        // un'altra classe non si può fare lo scambio e viceversa.
        if (o1 != null) {
            Docente d = (Docente) o1.getDoc();
            if (d != null) {
                Ora o = d.listaOre.get(g2, s2);
                if ( (o != null) && (!o.classe.equals(o1.classe)) ){
                    totMess = addMessErr(totMess, 1,
                            GestOrarioApplet.giorno2Str(g2,true)+" alla "+s2+" ora "
                            +d.nome+" ha già lezione nella "+o.classe.nome);
                    System.out.println("docenteConProblemi "+d.nome+" ass g:"+o.giorno+" o:"+o.spazio);
                    if (o2 != null)
                        o2.docenteConProblemi = d;
//                    return mess;
                }
            }
            d = (Docente) o1.getDocCom();
            if (d != null) {
                Ora o = d.listaOre.get(g2, s2);
                if ( (o != null) && (!o.classe.equals(o1.classe)) ){
                    totMess = addMessErr(totMess, 1,
                            GestOrarioApplet.giorno2Str(g2,true)+" alla "+s2+" ora "
                            +d.nome+" ha già lezione nella "+o.classe.nome);
                    System.out.println("docenteConProblemi "+d.nome+" ass g:"+o.giorno+" o:"+o.spazio);
                    if ( (o2 != null) && (o2.docenteConProblemi == null) )
                        o2.docenteConProblemi = d;
//                    return mess;
                }
            }
        }
        // se per il docente di o1 l'ora o2 è già occupata da un'ora in
        // un'altra classe non si può fare lo scambio e viceversa.
        if ((o1 != null) && (o2 != null)) {
            Docente d = (Docente) o2.getDoc();
            if (d != null) {
                Ora o = d.listaOre.get(o1.giorno, o1.spazio);
                if ( (o != null) && (!o.classe.equals(o2.classe)) ){
                    totMess = addMessErr(totMess, 1,
                            GestOrarioApplet.giorno2Str(o1.giorno,true)+" alla "+o1.spazio+" ora "
                            +d.nome+" ha già lezione nella "+o.classe.nome);
                    System.out.println("docenteConProblemi "+d.nome+" ass g:"+o.giorno+" o:"+o.spazio);
                    o2.docenteConProblemi = d;
//                    return mess;
                }
            }
            d = (Docente) o2.getDocCom();
            if (d != null) {
                Ora o = d.listaOre.get(o1.giorno, o1.spazio);
                if ( (o != null) && (!o.classe.equals(o2.classe)) ){
                    totMess = addMessErr(totMess, 1,
                            GestOrarioApplet.giorno2Str(o1.giorno,true)+" alla "+o1.spazio+" ora "
                            +d.nome+" ha già lezione nella "+o.classe.nome);
                    System.out.println("docenteConProblemi "+d.nome+" ass g:"+o.giorno+" o:"+o.spazio);
                    o2.docenteConProblemi = d;
//                    return mess;
                }
            }
        }

        // se le due ore sono in aule diverse
        // o1 in classe e o2 in lab
        // se lab in o1 è occupata da un'altra classe non si può effettuare lo scambio
        if ( (o1!= null) && (o1.classe.nome.equalsIgnoreCase(o1.aula.nome))
          && (o2!= null) && !(o2.classe.nome.equalsIgnoreCase(o2.aula.nome)) ) {
            Ora o = o2.aula.listaOre.get(o1.giorno, o1.spazio);
            if (o != null) {
                    totMess = addMessErr(totMess, 2,
                        GestOrarioApplet.giorno2Str(o1.giorno,true)+" alla "+o1.spazio+" ora, l'aula"
                        +o2.aula.nome+" è già occupata dalla "+o.classe.nome);
//                return mess;
            }
        }
        // e viceversa
        if ( (o2!= null) && (o2.classe.nome.equalsIgnoreCase(o2.aula.nome))
          && (o1!= null) && !(o1.classe.nome.equalsIgnoreCase(o1.aula.nome)) ) {
            Ora o = o1.aula.listaOre.get(o2.giorno, o2.spazio);
            if (o != null) {
                    totMess = addMessErr(totMess, 2,
                        GestOrarioApplet.giorno2Str(o2.giorno,true)+" alla "+o2.spazio+" ora, l'aula"
                        +o1.aula.nome+" è già occupata dalla "+o.classe.nome+".");
//                return mess;
            }
        }


        // se la prima ora di lab in s1 deve andare in s2 con s2 = s1-1
        // (si sposta sull'ora precedente)
        // se in s2 c'è non c'è un'ora vuota (altrimenti non c'è problema)
        // se l'ora s2 non può andare in s1+1 perchè in s2 il docente ha
        // un'ora in un'altra classe
        // allora errore

        // non si dovrebbe verificare perchè già considerato in scambioCorretto()

        // in altre parole si controlla la prima con la terza!!!!

        if ( (o1 != null) && (o1.getSucc() != null) && (g1 == g2) && (s2 == s1-1)
           && (o2 != null)) {
            Docente d = (Docente) o2.getDoc();
            if (d != null) {
                Ora o = d.listaOre.get(o1.giorno, s1+1);
                if ( (o != null) && (!o.classe.equals(o1.classe)) ) {
                    totMess = addMessErr(totMess, 1,
                            GestOrarioApplet.giorno2Str(o1.giorno,true)+" alla "+(s1+1)+" ora "
                            +d.nome+" ha già lezione nella "+o.classe.nome);
                    System.out.println("docenteConProblemi "+d.nome+" ass g:"+o.giorno+" o:"+(s1+1));
                    o2.docenteConProblemi = d;
                }
            }
        }

        // questo vale anche se si vuol spostare in basso
        if ( (o1 != null) && (o1.getSucc() != null) && (g1 == g2) && (s2 == s1+1)
           && (o2 != null)) {
            Docente d = (Docente) o2.getDoc();
            if (d != null) {
                Ora o = d.listaOre.get(o1.giorno, s1-1);
                if ( (o != null) && (!o.classe.equals(o1.classe)) ) {
                    totMess = addMessErr(totMess, 1,
                            GestOrarioApplet.giorno2Str(o1.giorno,true)+" alla "+(s1-1)+" ora "
                            +d.nome+" ha già lezione nella "+o.classe.nome);
                    System.out.println("docenteConProblemi "+d.nome+" ass g:"+o.giorno+" o:"+(s1-1));
                    o2.docenteConProblemi = d;
                }
            }
        }

        // stesso discorso a ruoli invertiti tra la selezione e l'ora corrente
        if ( (o2 != null) && (o2.getSucc() != null) && (g2 == g1) && (s1 == s2-1)
           && (o1 != null)) {
            Docente d = (Docente) o1.getDoc();
            if (d != null) {
                Ora o = d.listaOre.get(o2.giorno, s2+1);
                if ( (o != null) && (!o.classe.equals(o2.classe)) ) {
                    totMess = addMessErr(totMess, 1,
                            GestOrarioApplet.giorno2Str(o2.giorno,true)+" alla "+(s2+1)+" ora "
                            +d.nome+" ha già lezione nella "+o.classe.nome);
                    System.out.println("docenteConProblemi "+d.nome+" ass g:"+o.giorno+" o:"+(s2+1));
                    o1.docenteConProblemi = d;
                }
            }
        }

        // questo vale anche se si vuol spostare in basso
        if ( (o2 != null) && (o2.getSucc() != null) && (g2 == g1) && (s1 == s2+1)
           && (o1 != null)) {
            Docente d = (Docente) o1.getDoc();
            if (d != null) {
                Ora o = d.listaOre.get(o2.giorno, s2-1);
                if ( (o != null) && (!o.classe.equals(o2.classe)) ) {
                    totMess = addMessErr(totMess, 1,
                            GestOrarioApplet.giorno2Str(o2.giorno,true)+" alla "+(s2-1)+" ora "
                            +d.nome+" ha già lezione nella "+o.classe.nome);
                    System.out.println("docenteConProblemi "+d.nome+" ass g:"+o.giorno+" o:"+(s2-1));
                    o2.docenteConProblemi = d;
                }
            }
        }

        if ( (o1 != null) && (o1.getSucc()!= null) )  {
            if ( !casoParticolare ) {
            // se s1 ha un successore e s2 non è immediatamente sopra o sotto
                return scambioCorrettoRic(o1.giorno, o1.spazio+1, g2, s2+1, false, totMess);
            }
        }
        else if ( (o2 != null) && (o2.getSucc()!= null) )  {
//                if ( !( ((g1 == g2) && (s2-1 != s1)) || ((g1 == g2) && (s2+2 == s1)) ) ) {
            if ( !casoParticolare ) {
            // se s2 ha un successore e s1 non è immediatamente sopra
                return scambioCorrettoRic(g1, s1+1, o2.giorno, o2.spazio+1, false, totMess);
            }
        }

        /***
        else if ( ((o1 != null) && (o1.getPrec() != null))
                ||((o2 != null) && (o2.getPrec() != null)) )
            if ( (prec == 0) || (prec == -1) )
                return scambioCorrettoRic(o1.giorno, o1.spazio-1, o2.giorno, o2.spazio-1, -1);
***/

        return totMess;
    }

    public void selezione(MouseEvent e, Docente docColl) {

        Ora ora;
        int radius = 8;
        if (e.getButton() == MouseEvent.BUTTON1) {
            selx = (e.getX() - getShiftX()) - (e.getX() - getShiftX()) % getB();
            sely = (e.getY() - getShiftY()) - (e.getY() - getShiftY()) % getH();
            // se sono nella tabella classe il click vuol dire
            // mostrami le informazioni sullo scambio se ci sono
            if (idTable == 3) {
                resetErrorMess();
                codErrorMess = scambioCorretto(selGiorno, selSpazio, (selx/getB())+1, (sely/getH())+1, 0);
                if (codErrorMess != 0)
                    JOptionPane.showMessageDialog(null, totNumToMessErr(codErrorMess));
            }

            ora = listaOre.get((selx / getB()) + 1, (sely / getH()) + 1);
            // se è un'ora buca o
            // se non è collegata al precedente (solo la prima ora può essere selezionata)

            if ( selezioneCorretta(ora) 
                && ( (getStatoRicerca() != 7) && (getStatoRicerca() != 8) ) ) {
                if (ora != null) {
                    // se è il secondo click sulla stessa cella vuol dire che
                    // la si vuole deselezionare
                    if ( (selGiorno == (selx / getB()) + 1)
                      && (selSpazio == (sely / getH()) + 1)
                      && getHasBeenClicked() ) {
//                        deselezionaOra(ora);
                        resetSelezione();
                        setHasBeenClicked(false);
                    }
                    // è il primo click su una nuova cella
                    // e quindi vuol dire che si è selezionata
                    // una seconda cella per cui si deve deselezionare
                    // la vecchia (se esiste) e selezionare la nuova
                    else {
//                        ora.setSelezionata(1);
                        ora = listaOre.get(selGiorno, selSpazio);
                        if (ora != null) {
//                            deselezionaOra(ora);
                        }
                        setSelezione((selx / getB()) + 1, (sely / getH()) + 1);
                        setHasBeenClicked(true);
                    }
                }
            }
        }
        else if ( (e.getButton() == MouseEvent.BUTTON3) && esisteSelezione()
                  && (getStatoRicerca() != 3)) {
            // only execute this block of code if the mouse has been clicked before
            // It's essential that you use e.getX() rather than just getX()
            // which will get the x coordinate of the applet, not the click!
            //          g.drawLine(lastX, lastY, e.getX(), e.getY());

//            int g2 = (getLastX() - getShiftX()) - (getLastX() - getShiftX()) % getB();
//            int s2 = (getLastY() - getShiftY()) - (getLastY() - getShiftY()) % getH();
            curx = (e.getX() - getShiftX()) - (e.getX() - getShiftX()) % getB();
            cury = (e.getY() - getShiftY()) - (e.getY() - getShiftY()) % getH();
            int g = (curx/getB())+1;
            int s = (cury/getH())+1;

            ora = listaOre.get(g, s);

            if ( (ora != null) && (ora.getPrec() != null) && (ora.getSucc() == null)
              && !((selGiorno == g) && (selSpazio+1==s)) ) {
                // ci si trova sull'ora collegata a quella corrente
                // seleziono la precedente perchè la seconda ora di una coppia
                // non può essere selezionata
                s = s-1;
            }

            resetErrorMess();
            codErrorMess = scambioCorretto(selGiorno, selSpazio, g, s, 0);
            if (codErrorMess == 0) {
                scambia(selGiorno, selSpazio, g, s);
                resetSelezione();
                resetCurrent();

                //          g.setColor(Color.black);
                //          g.fillOval(e.getX()-radius, e.getY()-radius, 2*radius, 2*radius);
                setHasBeenClicked(false);

            }
            else {
                Ora o = listaOre.get((curx / getB()) + 1, (cury / getH()) + 1);
                if (o != null) {
                    JOptionPane.showMessageDialog(null, totNumToMessErr(codErrorMess));
                    SecondWindow secondWin = new SecondWindow();
                    JDialog.setDefaultLookAndFeelDecorated(false);
                    JDialog dialog;
                    System.out.println("ora g:"+o.giorno+" s:"+o.spazio);
                    if (o.docenteConProblemi != null) {
                        secondWin.intiTSecondWindow(o);
                        dialog = new JDialog(ancestorFrame, o.docenteConProblemi.nome, true);
                    }
                    else {
                        secondWin.intiTSecondWindow(o);
                        dialog = new JDialog(ancestorFrame, o.getDoc().nome, true);
                    }
                    dialog.getContentPane().add(secondWin);
                    dialog.pack();
                    dialog.setLocationRelativeTo(null);
                    dialog.setBounds(20, 20, 800, 600);
                    dialog.setVisible(true);
                }
            }
        }
        else // the applet has now been clicked for the first time.
        {
        }

        setNewStatoRicerca();
        // either way, store the new x and y values
        setLastX(e.getX());
        setLastY(e.getY());
    }

/*
    public void initColorTab(ListaOre l, ListaInfoMatDocClassAule infoMatDocAule) {
        Ora ora;

        listaOre = l;

        for (int i = 0; i < getRighe(); i++) {
            for (int j = 0; j < getColonne(); j++) {
                ora = listaOre.get(i + 1, j + 1);
                if (ora != null) {
                    tabOre[i][j] = ora;
                } else {
                    tabOre[i][j] = new Ora(i+1, j+1, null, GestOrarioApplet.d0,
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

    public int getLastX() {
        return lastX;
    }

    public int getLastY() {
        return lastY;
    }

    public void setLastX(int pLastX) {
        lastX = pLastX;
    }

    public void setLastY(int pLastY) {
        lastY = pLastY;
    }

    public void setHasBeenClicked(boolean bol) {
        hasBeenClicked = bol;
    }

    public boolean getHasBeenClicked() {
        return hasBeenClicked;
    }

    public boolean setCurrent(int giorno, int spazio) {
        // non si può impostare l'ora corrente se è solitaria,
        // la selezione è una catena e non segue l'ora corrente
        boolean change = false;
        Ora o = listaOre.get(giorno, spazio);
        Ora os = listaOre.get(giorno, spazio+1);
        if (!( esisteSelezione() && (numSpazSel > 1)
           && (o != null) && (o.getSucc() == null) && (o.getPrec() == null)
           && (os != null) && (os.getSucc() != null)
           && (giorno != selGiorno) && (spazio+1 != selSpazio) )) {
                curGiorno = giorno;
                curSpazio = spazio;
                change = true;
        }
        if ( (idTable == 1) || (idTable == 3) ) {
                // il numero di ore correnti viene deciso nella tabella del docente o della classe
                numSpazCur = 1;
                Ora ora = listaOre.get(giorno, spazio);
                if ((ora != null) && (ora.getSucc() != null)) {
                    do {
                        numSpazCur++;
                        spazio++;
                        ora = ora.getSucc();
                    } while ( (ora != null) && (ora.getSucc() != null) );
                }
                else if ((ora != null) && (ora.getPrec() != null)) {
                    do {
                        numSpazCur++;
                        spazio--;
                        ora = ora.getPrec();
                    } while ( (ora != null) && (ora.getPrec() != null) );
                }
        }
        return change;
/***
        if ( (succTableRig != null) && (succTableRig.listaOre != null) )
            succTableRig.setCurrent(giorno, spazio);
        if ( (succTableCol != null)  && (succTableCol.listaOre != null) )
            succTableCol.setCurrent(giorno, spazio);
*/
    }

    public void resetCurrent(){
        curGiorno = -1;
        curSpazio = -1;
        numSpazCur = 0;
/***
        if (succTableRig != null)
            succTableRig.resetCurrent();
        if (succTableCol != null)
            succTableCol.resetCurrent();
*/
    }

    public boolean esisteCurrent() {
        if ((curGiorno == -1) || (curSpazio == -1) )
            return false;
        else if (listaOre.get(curGiorno, curSpazio) == null)
            return false;
        else
            return true;
    }

    public void setCandidato(int giorno, int spazio) {
        canGiorno = giorno;
        canSpazio = spazio;
        numSpazCan = 1;
        Ora ora;
        while ( ((ora = listaOre.get(giorno, spazio)) != null) && (ora.getSucc() != null) ) {
            numSpazCan++;
            spazio++;
        }
/***
        if ( (succTableRig != null) && (succTableRig.listaOre != null) )
            succTableRig.setCandidato(giorno, spazio);
        if ( (succTableCol != null)  && (succTableCol.listaOre != null) )
            succTableCol.setCandidato(giorno, spazio);
*/
    }

    public void resetCandidato(){
        canGiorno = -1;
        canSpazio = -1;
        numSpazCan = 0;
/***
        if (succTableRig != null)
            succTableRig.resetCandidato();
        if (succTableCol != null)
            succTableCol.resetCandidato();
*/
    }
    public boolean esisteCandidato() {
        if ((canGiorno == -1) || (canSpazio == -1) )
            return false;
        else
            return true;
    }

    public void setSelezione(int giorno, int spazio) {
        if ( (idTable == 1) || (idTable == 3) ) {
            // il numero di ore selezionate viene deciso nella tabella del docente o della classe
            selGiorno = giorno;
            selSpazio = spazio;
            
            numSpazSel = 1;
            Ora ora = listaOre.get(giorno, spazio);
            if ( (ora != null) && (ora.getSucc() != null) ) {
                numSpazSel++;
                spazio++;
            } else if ( (ora != null) && (ora.getPrec() != null) ) {
                numSpazSel++;
                spazio--;
            }
        }
/***
        if ( (succTableRig != null) && (succTableRig.listaOre != null) )
            succTableRig.setSelezione(giorno, spazio);
        if ( (succTableCol != null)  && (succTableCol.listaOre != null) )
            succTableCol.setSelezione(giorno, spazio);
*/
    }

    public void resetSelezione(){
        selGiorno = -1;
        selSpazio = -1;
        numSpazSel = 0;
/***
        if (succTableRig != null)
            succTableRig.resetSelezione();
        if (succTableCol != null)
            succTableCol.resetSelezione();
**/
    }
    public boolean esisteSelezione() {
        if ((selGiorno == -1) || (selSpazio == -1) )
            return false;
        else
            return true;
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
        Ora o1 = listaOre.get(g1, s1);
        Ora o2 = listaOre.get(g2, s2);

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
        Ora o1 = listaOre.get(x1, y1);
        Ora o2 = listaOre.get(x2, y2);

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
        Ora temp = tabOre[g1][s1];
        tabOre[g1][s1] = tabOre[g2][s2];
        tabOre[g2][s2] = temp;
*/
        scambi++;
        GestOrarioApplet.loadInfo.WriteFile(scambi);
        punteggio();
        punteggio2();
    }
}
