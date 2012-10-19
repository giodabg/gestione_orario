/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorario;

import java.applet.*;
import java.awt.*;  //grafica
import java.awt.event.*; //grafica
//import javax.swing.*;
import java.awt.FileDialog;
import java.awt.Frame;

//import java.io.*;
//import java.io.FileNotFoundException;

import java.io.File;
import javax.swing.*;


/**
 *
 * @author Gio
 */
public class GestOrarioApplet extends Applet implements MouseListener, MouseMotionListener {

    private Frame       m_parent;
    private FileDialog  m_fileDialog;

    private int         altCella, larghCella;

    public  GraphTable  tabClasseDoc;
    private int         xTabClasseDoc;
    private int         yTabClasseDoc;
    
    private GraphTable  tabLabDoc;
    private int         xTabLabDoc;
    private int         yTabLabDoc;

    private GraphTable  tabDoc;
    private int         xTabDoc;
    private int         yTabDoc;

    private GraphTable  tabDocComp;
    private int         xTabDocComp;
    private int         yTabDocComp;

    private GraphTable  tabDocScam;
    private int         xTabDocScam;
    private int         yTabDocScam;

    private GraphTable  tabDocScamComp;
    private int         xTabDocScamComp;
    private int         yTabDocScamComp;

    private GraphTable  tabDocInterm;
    private int         xTabDocInterm;
    private int         yTabDocInterm;

    private GraphTable  tabDocIntermComp;
    private int         xTabDocIntermComp;
    private int         yTabDocIntermComp;

    static  int         maxNumSpazi = 6;
    static  int         maxNumGiorni = 6;
    static  Font        fSmall = new Font("Arial", Font.PLAIN, 5);
    static  Font        fMedium = new Font("Helvetica", Font.PLAIN, 11);
//    static  Font        fMedium = new Font("Helvetica", Font.ITALIC + Font.BOLD, 12);

    static  int         TIPOCLASSE = 1;
    static  int         TIPODOCENTE = 2;
    static  int         TIPOAULA = 3;

    static  SelCorOre   selCorApplet;

    static  LoadCSV loadInfo;
    static  String workDir = "C://Users//Gio//Documents//NetBeansProjects//GestOrario//";
    static  ListaInfoMatDocClassAule infoMatDocAule;
    static  ListaClassi classi;
    static  ListaAule   aule;
    public  Docente     docApplet;

    static  Aula a0;
    static  Materia m0;
    static  Docente d0;
    static  Color c0;

    /**
     * Initialization method that will be called after the applet is loaded
     * into the browser.
     */
    @Override
    public void init() {
        // TODO start asynchronous download of heavy resources
        infoMatDocAule = new ListaInfoMatDocClassAule();

/****************************
 * per eseguire il file da prompt dei comandi scrivere
 * appletviewer -J-Djava.security.policy=C:\Users\Gio\Documents\NetBeansProjects\GestOrario\applet.policy
 * C:\Users\Gio\Documents\NetBeansProjects\GestOrario\build\GestOrarioApplet.html
 *
 * Per certificare un applet leggere
 * http://java.html.it/articoli/leggi/1785/firmare-un-applet/5/
 */

        String fileName = openDialog();
        loadInfo = new LoadCSV(GestOrarioApplet.workDir+"//"+fileName);
//        loadInfo = new LoadCSV(GestOrarioApplet.workDir+"op_ITIS_2010-2011.csv");
//        loadInfo = new LoadCSV("C://Users//Gio//Documents//odef_ITIS_2009-2010.csv");
//        loadInfo = new LoadCSV("odef_ITIS_2009-2010.csv");
        
        loadInfo.initFromCSV(infoMatDocAule);
//        loadInfo.displayArrayList();

        Button cambiaDocBtn = new Button("Cambia Docente");
        cambiaDocBtn.addActionListener(new ActionListener() {

          public void actionPerformed(ActionEvent arg0) {
            cambiaDocente();
          }
        });
        add(cambiaDocBtn);

        a0    = new Aula(0, "Casa", 3, 0, 0, 0, "tutte", Color.BLACK);
        c0    = infoMatDocAule.getMateriaColor("Ora Libera");
        m0    = infoMatDocAule.getMateria("Ora Libera");
        d0    = infoMatDocAule.getDocente(" ");


//        aulaSin  = new Aula(1, "Info A1", 3, 0, 0, 0, "tutte", Color.YELLOW);
//        classeSin = new Classe (1, 1, 'A', "Informatica", 'I', aula1A, infoMatDocAule, Color.BLUE);

//        aulaDes  = new Aula(2, "Info A2", 3, 0, 0, 0, "tutte", Color.YELLOW);
//        classeDes = new Classe (2, 1, 'B', "Informatica", 'I', aula1B, infoMatDocAule, Color.GREEN);

        selCorApplet = new SelCorOre();

        larghCella        = 38;
        altCella          = 40;

        xTabDoc       = 30;
        yTabDoc       = 50;

        xTabClasseDoc = xTabDoc+300;
        yTabClasseDoc = yTabDoc;

        xTabDocScam       = xTabClasseDoc+300;
        yTabDocScam       = yTabDoc;

        xTabDocInterm       = xTabDocScam+300;
        yTabDocInterm       = yTabDoc;

        xTabDocComp       = xTabDoc;
        yTabDocComp       = 400;

        xTabLabDoc    = xTabClasseDoc;
        yTabLabDoc    = yTabDocComp;

        xTabDocScamComp   = xTabDocScam;
        yTabDocScamComp   = yTabDocComp;

        xTabDocIntermComp       = xTabDocInterm;
        yTabDocIntermComp       = yTabDocComp;


        do {
            String nome = JOptionPane.showInputDialog("Docente:");
            if ( (nome != null) && (nome.isEmpty()) ) {
                docApplet = infoMatDocAule.getDocente("Breviario");
            }
            else {
                docApplet = infoMatDocAule.getDocente(nome);
            }
        } while (docApplet == null);


        m_parent = (Frame)SwingUtilities.getAncestorOfClass(Frame.class, this);
//        JFrame.setDefaultLookAndFeelDecorated(true);
//        JFrame frameListDialog = new JFrame("Oval Sample");
//        frameListDialog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frameListDialog.setUndecorated(true);
//        frameListDialog.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
//        frameListDialog.add(listPanel);

        tabDoc           = new GraphTable(1, 6, 6, larghCella, altCella, xTabDoc,   yTabDoc,    this, 0, m_parent, selCorApplet);
        tabDocComp       = new GraphTable(2, 6, 6, larghCella, altCella, xTabDocComp,   yTabDocComp,    this, 0, m_parent, selCorApplet);

        tabClasseDoc = new GraphTable(3, 6, 6, larghCella, altCella, xTabClasseDoc,yTabClasseDoc, this, 0, m_parent, selCorApplet);

        tabLabDoc    = new GraphTable(4, 6, 6, larghCella, altCella, xTabLabDoc,yTabLabDoc, this, 0, m_parent, selCorApplet);

        tabDocScam       = new GraphTable(5, 6, 6, larghCella, altCella, xTabDocScam,   yTabDocScam,    this, 0, m_parent, selCorApplet);
        tabDocScamComp   = new GraphTable(6, 6, 6, larghCella, altCella, xTabDocScamComp,yTabDocScamComp, this, 0, m_parent, selCorApplet);

        tabDocInterm       = new GraphTable(7, 6, 6, larghCella, altCella, xTabDocInterm,   yTabDocInterm,    this, 0, m_parent, selCorApplet);
        tabDocIntermComp   = new GraphTable(8, 6, 6, larghCella, altCella, xTabDocIntermComp,yTabDocIntermComp, this, 0, m_parent, selCorApplet);

        tabDoc.setList(docApplet, TIPODOCENTE, infoMatDocAule);
        tabDoc.setVisible(true);
        tabDoc.setStatoRicerca(1);

        tabDoc.setSuccTableRig(tabClasseDoc);
        tabDoc.setSuccTableCol(tabDocComp);

        tabClasseDoc.setSuccTableRig(tabDocScam);
        tabClasseDoc.setSuccTableRigInterm(tabDocInterm);
        tabDocComp.setSuccTableRig(tabLabDoc);

        tabDocScam.setSuccTableCol(tabDocScamComp);

        tabDocInterm.setSuccTableCol(tabDocIntermComp);

        addMouseMotionListener(this);
        addMouseListener(this);

    }

    private void cambiaDocente() {
        do {
            String nome = JOptionPane.showInputDialog("Docente:");
            if ( (nome != null) && (nome.isEmpty()) ) {
                docApplet = infoMatDocAule.getDocente("Breviario");
            }
            else {
                docApplet = infoMatDocAule.getDocente(nome);
            }
        } while (docApplet == null);
        tabDoc.setList(docApplet, TIPODOCENTE, infoMatDocAule);
        tabDoc.setVisible(true);
        tabDoc.setStatoRicerca(1);
        repaint();
    }

    // TODO overwrite start(), stop() and destroy() methods
    @Override
    public void start(){
    }

    @Override
    public void paint(Graphics g){
            g.setColor(Color.RED);
            g.setFont(fMedium);
        //  g.drawString("TABELLA 6 x 6",20,40);
        //    g.drawString("Mosse: "+tabClasseDoc.getMosse(),40,35);
        //    g.drawString("Punti: "+tabClasseDoc.punteggio,140,35);
        //    g.drawString("Mosse: "+tabLabDoc.getMosse(),500,35);
        //    g.drawString("Punti: "+tabLabDoc.punteggio,600,35);
            // l'ordine di visualizzazione è importante perchè ogni tabella
            // decide se la prossima è da visualizzare
            // e tutto viene scatenato dalla prima tabella
            tabDoc.setGraphic(g);
            tabDoc.paintTabella();
            tabClasseDoc.setGraphic(g);
            tabClasseDoc.paintTabella();
            tabDocComp.setGraphic(g);
            tabDocComp.paintTabella();
            tabLabDoc.setGraphic(g);
            tabLabDoc.paintTabella();
            tabDocScam.setGraphic(g);
            tabDocScam.paintTabella();
            tabDocScamComp.setGraphic(g);
            tabDocScamComp.paintTabella();
            tabDocInterm.setGraphic(g);
            tabDocInterm.paintTabella();
            tabDocIntermComp.setGraphic(g);
            tabDocIntermComp.paintTabella();
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

    private boolean mustRepaintTab(MouseEvent e, GraphTable tab, int idTab) {
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
           if (tab.cambiaOraCorrente(newCurGiorno, newCurSpazio, curx, cury, docApplet)) {
               repaint();
               return true;
           }
        }
        return false;
    }

    public void mouseMoved(MouseEvent e) {
        if ( mustRepaintTab(e, tabDoc, 1)
          || mustRepaintTab(e, tabClasseDoc, 2)
          || mustRepaintTab(e, tabDocComp, 3)
          || mustRepaintTab(e, tabLabDoc, 4)
          || mustRepaintTab(e, tabDocScam, 5)
          || mustRepaintTab(e, tabDocScamComp, 6)
          || mustRepaintTab(e, tabDocInterm, 7)
          || mustRepaintTab(e, tabDocIntermComp, 8)) {
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
               tab.selezione(e, docApplet);
               repaint();
               return true;
            }
        }
        return false;
    }

    public void mousePressed(MouseEvent e) {
        if (inTabella(e, tabDoc))
            return;
        if (inTabella(e, tabClasseDoc))
            return;
        if (inTabella(e, tabDocComp))
            return;
        if (inTabella(e, tabLabDoc))
            return;
        if (inTabella(e, tabDocScam))
            return;
        if (inTabella(e, tabDocScamComp))
            return;
        if (inTabella(e, tabDocInterm))
            return;
        if (inTabella(e, tabDocIntermComp))
            return;
    }


// source http://forums.sun.com/thread.jspa?threadID=5436853
// source http://www.java2s.com/Code/Java/SWT-JFace-Eclipse/FileDialogExample.htm
// SOURCE: http://forums.sun.com/thread.jspa?threadID=763396

    public String openDialog()
    {

        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new MyFileFilter());
        int returnVal = fc.showDialog(m_parent, "Open ...");
        final String directory = fc.getCurrentDirectory().getAbsolutePath();
        final String returnFile = fc.getSelectedFile().getName();
        /************************
        System.out.println("Calling open dialog...");
    	if (m_parent == null)
        {
        	m_parent = new Frame();
        }
        if (m_fileDialog == null)
        {
            m_fileDialog = new FileDialog(m_parent, "Scelta file .csv", FileDialog.LOAD);
            m_fileDialog.setFilenameFilter(this);
        }

        m_fileDialog.setVisible(true);
        m_fileDialog.toFront();

        final String directory = m_fileDialog.getDirectory();
        final String returnFile = m_fileDialog.getFile();

        final File file = new File(directory+File.separator +returnFile);

        byte[] test = (byte[]) java.security.AccessController.doPrivileged(
                new java.security.PrivilegedAction() {

                    public Object run() {

                        try {
                            FileInputStream fileInputStream = new FileInputStream(file);
                            byte[] data = new byte[(int) file.length()];
                            try {
                                fileInputStream.read(data);
                                fileInputStream.close();
                                return data;
                            } catch (IOException iox) {
                                System.out.println("File read error...");
                                iox.printStackTrace();
                                return null;
                            }
                        } catch (FileNotFoundException fnf) {
                            System.out.println("File not found...");
                            fnf.printStackTrace();
                            return null;
                        }

                    }
                });

    	m_fileDialog.setVisible(false);
        m_parent.setVisible(false);
         ************/
        GestOrarioApplet.workDir = directory;
        return returnFile;
    }

    static public String giorno2Str(int x, boolean completo) {
        if (completo) {
            if (x == 1) {
                return "Lunedì";
            } else if (x == 2) {
                return "Martedì";
            } else if (x == 3) {
                return "Mercoledì";
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


