/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorario;

/**
 *
 * @author Gio
 * example: http://forums.sun.com/thread.jspa?threadID=5430089
 */

import java.awt.*;  //grafica
import java.awt.event.*; //grafica

import javax.swing.*;


public class SecondWindow extends JPanel implements MouseListener, MouseMotionListener {

    private GraphTable  tabClasseDocColl, tabLabDocColl, tabDocColl, tabDocComp;
    private int         xTabClasseDocColl, xTabLabDocColl, xTabDocColl, xTabDocComp;
    private int         yTabClasseDocColl, yTabLabDocColl, yTabDocColl, yTabDocComp;

    private GraphTable  tabDocScamComp, tabDocScam;
    private int         xTabDocScamComp, xTabDocScam;
    private int         yTabDocScamComp, yTabDocScam;

    private Docente     docCollegato;

    public SecondWindow() {
        JButton calcSumBtn = new JButton("Torna");
        calcSumBtn.addActionListener(new ActionListener() {

          public void actionPerformed(ActionEvent arg0) {
            actionTorna();
          }
        });
        add(calcSumBtn);
    }

    public void intiTSecondWindow(Ora o) {

            this.setSize(600, 800);

            if (o.docenteConProblemi != null)
                docCollegato = o.docenteConProblemi;
            else
                docCollegato = o.getDoc();


            xTabDocColl     =  40; xTabClasseDocColl = 380; xTabDocScam     = 720;
            yTabDocColl     =  40; yTabClasseDocColl =  40; yTabDocScam     =  40;

            xTabDocComp     =  40; xTabLabDocColl    = 380; xTabDocScamComp = 720;
            yTabDocComp     = 380; yTabLabDocColl    = 380; yTabDocScamComp = 380;


            Frame frameApplet = (Frame)SwingUtilities.getAncestorOfClass(Frame.class, this);
            tabDocColl       = new GraphTable(1, 6, 6, 50, 38, xTabDocColl,   yTabDocColl,    this, 1, frameApplet);
            tabClasseDocColl = new GraphTable(3, 6, 6, 50, 38, xTabClasseDocColl,yTabClasseDocColl, this, 1, frameApplet);

            tabDocComp       = new GraphTable(2, 6, 6, 50, 38, xTabDocComp,   yTabDocComp,    this, 1, frameApplet);
            tabLabDocColl    = new GraphTable(4, 6, 6, 50, 38, xTabLabDocColl,yTabLabDocColl, this, 1, frameApplet);

            tabDocScam       = new GraphTable(5, 6, 6, 50, 38, xTabDocScam,   yTabDocScam,    this, 1, frameApplet);
            tabDocScamComp   = new GraphTable(6, 6, 6, 50, 38, xTabDocScamComp,yTabDocScamComp, this, 1, frameApplet);

            tabDocColl.setList(docCollegato, GestOrarioApplet.TIPODOCENTE, GestOrarioApplet.infoMatDocAule);
            tabDocColl.setVisible(true);
            tabDocColl.setSelezione(o.giorno, o.spazio);
            tabDocColl.setNewStatoRicerca();

            tabDocColl.setSuccTableRig(tabClasseDocColl);
            tabDocColl.setSuccTableCol(tabDocComp);

            tabClasseDocColl.setSuccTableRig(tabDocScam);
            tabClasseDocColl.setSuccTableCol(tabLabDocColl);

            tabDocScam.setSuccTableCol(tabDocScamComp);

            addMouseMotionListener(this);
            addMouseListener(this);

      }



@Override
    public void paintComponent(Graphics g) {
            g.clearRect(0, 0, 1400, 900);
            tabDocColl.paintTabella(g);
            tabClasseDocColl.paintTabella(g);
            tabDocComp.paintTabella(g);
            tabLabDocColl.paintTabella(g);
            tabDocScam.paintTabella(g);
            tabDocScamComp.paintTabella(g);
    }

      private void actionTorna() {
        Window win = SwingUtilities.getWindowAncestor(this);
        win.dispose();
      }

    private boolean mouseInTabella(MouseEvent e, GraphTable tab) {
        int bordo = 40;

        if((e.getX() > tab.getShiftX() - bordo)
        && (e.getX() < tab.getShiftX()
                + (tab.getB() * tab.getColonne() + bordo) )
        && (e.getY() > tab.getShiftY() - bordo)
        && (e.getY() < tab.getShiftY()
                + (tab.getH() * tab.getRighe() + bordo))) {
            return true;
        }
        else
            return false;
    }

    private boolean mustRepaintTab(MouseEvent e, GraphTable tab) {
        if (tab.getVisible() && mouseInTabella(e, tab) ) {
           if (tab.cambiaOraCorrente(e)) {
               return true;
           }
        }
        return false;
    }

    public void mouseMoved(MouseEvent e) {
        if ( mustRepaintTab(e, tabDocColl)
          || mustRepaintTab(e, tabClasseDocColl)
          || mustRepaintTab(e, tabDocComp)
          || mustRepaintTab(e, tabLabDocColl)
          || mustRepaintTab(e, tabDocScam)
          || mustRepaintTab(e, tabDocScamComp)) {
           repaint();
           return;
        }
    }

    public void mouseDragged(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }

    private boolean inTabella(MouseEvent e, GraphTable tab) {
        if (tab.getVisible()) {
            if((e.getX() > tab.getShiftX())
            && (e.getX() < tab.getShiftX() + (tab.getB() * tab.getColonne()))
            && (e.getY() > tab.getShiftY())
            && (e.getY() < tab.getShiftY() + (tab.getH() * tab.getRighe()))) {
               tab.selezione(e, docCollegato);
               repaint();
               return true;
            }
        }
        return false;
    }

    public void mousePressed(MouseEvent e) {
        if (inTabella(e, tabDocColl))
            return;
        if (inTabella(e, tabClasseDocColl))
            return;
        if (inTabella(e, tabDocComp))
            return;
        if (inTabella(e, tabLabDocColl))
            return;
        if (inTabella(e, tabDocScam))
            return;
        if (inTabella(e, tabDocScamComp))
            return;
    }

    static public String giorno2Str(int x, boolean completo) {
        if (completo) {
            if (x == 1) {
                return "Lunedì";
            } else if (x == 2) {
                return "Martedì";
            } else if (x == 3) {
                return "Mercole";
            } else if (x == 4) {
                return "Giovedì";
            } else if (x == 5) {
                return "Venerdì";
            } else if (x == 6) {
                return "Sabato";
            } else if (x == 7) {
                return "Domenica";
            } else {
                return "Errore";
            }
        }
        else {
            if (x == 1) {
                return "Lun";
            } else if (x == 2) {
                return "Mar";
            } else if (x == 3) {
                return "Mer";
            } else if (x == 4) {
                return "Gio";
            } else if (x == 5) {
                return "Ven";
            } else if (x == 6) {
                return "Sab";
            } else if (x == 7) {
                return "Dom";
            } else {
                return "Errore";
            }
        }
    }

}