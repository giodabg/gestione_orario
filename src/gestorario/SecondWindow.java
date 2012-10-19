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

    static  SelCorOre   selCorWin;

    private GraphTable  tabDocScamComp, tabDocScam;
    private int         xTabDocScamComp, xTabDocScam;
    private int         yTabDocScamComp, yTabDocScam;

    public  Docente     docWindow;

    public SecondWindow() {
        JButton calcSumBtn = new JButton("Torna");
        calcSumBtn.addActionListener(new ActionListener() {

          public void actionPerformed(ActionEvent arg0) {
            actionTorna();
          }
        });
        add(calcSumBtn);
    }

    public void intiTSecondWindow(OraGraph o) {

            this.setSize(600, 800);

            if (o.docenteConProblemi != null)
                docWindow = o.docenteConProblemi;
            else
                docWindow = o.getDoc();


            xTabDocColl     =  40; xTabClasseDocColl = 380; xTabDocScam     = 720;
            yTabDocColl     =  40; yTabClasseDocColl =  40; yTabDocScam     =  40;

            xTabDocComp     =  40; xTabLabDocColl    = 380; xTabDocScamComp = 720;
            yTabDocComp     = 380; yTabLabDocColl    = 380; yTabDocScamComp = 380;


            Frame frameApplet = (Frame)SwingUtilities.getAncestorOfClass(Frame.class, this);
//            JFrame.setDefaultLookAndFeelDecorated(true);
//            JFrame frameListDialog = new JFrame("Oval Sample");
//            frameListDialog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frameListDialog.setUndecorated(true);
//            frameListDialog.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);

//            ListJPanel listPanel = new ListJPanel();
//            frameListDialog.add(listPanel);
//            ListJDialog listDialog = new ListJDialog(frameApplet, true);


/*******************
            tabDocColl       = new GraphTable(1, 6, 6, 50, 38, xTabDocColl,   yTabDocColl,    this, 1, frameApplet, selCorWin);
            tabClasseDocColl = new GraphTable(3, 6, 6, 50, 38, xTabClasseDocColl,yTabClasseDocColl, this, 1, frameApplet, selCorWin);

            tabDocComp       = new GraphTable(2, 6, 6, 50, 38, xTabDocComp,   yTabDocComp,    this, 1, frameApplet, selCorWin);
            tabLabDocColl    = new GraphTable(4, 6, 6, 50, 38, xTabLabDocColl,yTabLabDocColl, this, 1, frameApplet, selCorWin);

            tabDocScam       = new GraphTable(5, 6, 6, 50, 38, xTabDocScam,   yTabDocScam,    this, 1, frameApplet, selCorWin);
            tabDocScamComp   = new GraphTable(6, 6, 6, 50, 38, xTabDocScamComp,yTabDocScamComp, this, 1, frameApplet, selCorWin);

            tabDocColl.setList(docWindow, GestOrarioApplet.TIPODOCENTE, GestOrarioApplet.infoMatDocAule);
            tabDocColl.setVisible(true);
            selCorWin = new SelCorOre();
            tabDocColl.setNewStatoRicerca(docWindow);

            tabDocColl.setSuccTableRig(tabClasseDocColl);
            tabDocColl.setSuccTableCol(tabDocComp);

            tabClasseDocColl.setSuccTableRig(tabDocScam);
            tabClasseDocColl.setSuccTableCol(tabLabDocColl);

            tabDocScam.setSuccTableCol(tabDocScamComp);

            repaint();
            addMouseMotionListener(this);
            addMouseListener(this);
*******************************************************/
      }



@Override
    public void paintComponent(Graphics g) {
            g.clearRect(0, 0, 1400, 900);
            tabDocColl.setGraphic(g);
            tabDocColl.paintTabella();
            tabClasseDocColl.setGraphic(g);
            tabClasseDocColl.paintTabella();
            tabDocComp.setGraphic(g);
            tabDocComp.paintTabella();
            tabLabDocColl.setGraphic(g);
            tabLabDocColl.paintTabella();
            tabDocScam.setGraphic(g);
            tabDocScam.paintTabella();
            tabDocScamComp.setGraphic(g);
            tabDocScamComp.paintTabella();
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
            int newCurGiorno;
            int newCurSpazio;
            int curx = -1;
            int cury = -1;
            if ( ((e.getX() - tab.getShiftX()) < 0)
              || ((e.getY() - tab.getShiftY()) < 0)
              || ((e.getX() - tab.getShiftX()) > (tab.getColonne()) * tab.getB())
              || ((e.getY() - tab.getShiftY()) > (tab.getRighe()) * tab.getH()) ) {
                newCurGiorno = newCurSpazio = -1;
            }
            else {
                curx = (e.getX() - tab.getShiftX()) - (e.getX() - tab.getShiftX()) % tab.getB();
                cury = (e.getY() - tab.getShiftY()) - (e.getY() - tab.getShiftY()) % tab.getH();

                newCurGiorno = (curx / tab.getB()) + 1;
                newCurSpazio = (cury / tab.getH()) + 1;
            }

           if (tab.cambiaOraCorrente(newCurGiorno, newCurSpazio, curx, cury, docWindow)) {
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
                Frame frameApplet = (Frame)SwingUtilities.getAncestorOfClass(Frame.class, this);
                tab.selezione(e, docWindow);
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
