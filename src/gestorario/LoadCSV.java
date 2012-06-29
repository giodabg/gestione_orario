/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorario;

/**
 *
 * @author Gio
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.io.BufferedWriter;
import java.io.FileWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.awt.*;  //grafica
import java.util.Vector;


public class LoadCSV {

    String fileName;
    static public int MAXDOC = 300;
    Object[] tabella = new Object[MAXDOC];
    int nDoc = 0;

    public LoadCSV(String FileName) {
        this.fileName = FileName;
        ReadFile();
//        displayArrayList();
    }

    public void ReadFile() {
        // ogni campo tra due virgole deve contenere degli spazi anche l'ultima colonna
        // altrimenti il tokenizer lo salta
        try {
            //storeValues.clear();//just in case this is the second call of the ReadFile Method./
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            StringTokenizer st = null;
            int lineNumber = 0, tokenNumber = 0;
            String line;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                            System.out.println(line);
                //break comma separated line using ","
                st = new StringTokenizer(line, ",");
                ArrayList<String> riga = new ArrayList<String>();
                while (st.hasMoreTokens()) {
                    tokenNumber++;
                    String s = st.nextToken().trim();
                    riga.add(s);
                                System.out.println("Line # " + lineNumber +
                                        ", Token # " + tokenNumber + ", Token : " + s);
                }
                tabella[nDoc] = riga;
                nDoc++;
                //reset token number
                tokenNumber = 0;

            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void WriteFile(int numSave) {
        BufferedWriter bufferedWriter = null;
        try {

            //Construct the BufferedWriter object
            String nomeFile = GestOrarioApplet.workDir+"\\orarioDopoScambio"+numSave+".csv";
            bufferedWriter = new BufferedWriter(new FileWriter(nomeFile));
            bufferedWriter.write("Intestazione, linea1");
            bufferedWriter.newLine();
            bufferedWriter.write("Intestazione, linea2");
            bufferedWriter.newLine();

            //Start writing to the output stream
            for (int i =0; i < GestOrarioApplet.infoMatDocAule.infoDocenti.size()-1; i++) {
                Docente d  = (Docente) GestOrarioApplet.infoMatDocAule.infoDocenti.get(i);
                bufferedWriter.write(d.exportToCSV());
                bufferedWriter.newLine();
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            //Close the BufferedWriter
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.flush();
                    bufferedWriter.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    //mutators and accesors
    public void setFileName(String newFileName) {
        this.fileName = newFileName;
    }

    public String getFileName() {
        return fileName;
    }

    public ArrayList<String> getRiga(int i) {
        return (ArrayList<String>) tabella[i];
    }

    public void displayArrayList() {
        for (int i = 0; i < nDoc; i++) {
            ArrayList<String> riga = (ArrayList<String>) tabella[i];
            for (int x = 0; x < riga.size(); x++) {
                System.out.print(riga.get(x)+" * ");
            }
            System.out.println(" **");
        }
    }

    public void initFromCSV(ListaInfoMatDocClassAule infoMatDocClassAule) {

        Aula aLab;
        Color c, cLab;
        Materia m, mLab;
        Docente d, dLab;
        GraphOra o, oColl;
        int numRig = 100;
        int numCol = 37;
        ArrayList<String> riga1 = null, riga2 = null;
        // inizio dalla riga 4 perché si saltano le intestazioni
        for (int i = 3; i < nDoc; i++) {
              System.out.println("**************** "+i+" **********************");
//            int i = 16;
            riga1 = getRiga(i);
            if (i < nDoc - 1) {
                riga2 = getRiga(i + 1);
            }
            if ((riga2 != null) && (riga2.get(0).equalsIgnoreCase(""))) {
                // riga di un docente con compresenza/aula lab descritto nella riga che segue
                Color cTeo = new Color(100, 100, 100, 100);
                Materia mTeo = infoMatDocClassAule.getMateria("Sconosciuta");
                if (mTeo == null) {
                    mTeo = new Materia("Sconosciuta", "SCN", cTeo);
                    infoMatDocClassAule.infoMaterie.add(mTeo);
                }
                d = infoMatDocClassAule.getDocente(riga1.get(0));
                if (d == null) {
                    d = new Docente(riga1.get(0), mTeo, Docente.TEORICO, cTeo);
                    infoMatDocClassAule.infoDocenti.add(d);
                    System.out.println("Creato docente teorico "+d.nome);
                }
                for (int gg = 1; gg <= 6; gg++) {
                    GraphOra precLab = null;
                    for (int oo = 1; oo <= 6; oo++) {
                        Aula a;
                        String sTeo = riga1.get(1 + oo - 1 + (gg - 1) * 6);
                        String sLab = riga2.get(1 + oo - 1 + (gg - 1) * 6);
                                System.out.print(sTeo + " = ");
                        if (sLab.equalsIgnoreCase("")) {
                            m = d.getMateria();
                            a = infoMatDocClassAule.getAula(sTeo);
                            if (a == null) {
                                a = new Aula(sTeo);
                                a.addMateria("tutte");
                                infoMatDocClassAule.infoAule.add(a);
                            }
                        } else {
                            m = d.getMateriaLab();
                            a = infoMatDocClassAule.getAula(sLab);
                            if (a == null) {
                                a = new Aula(sLab);
                                infoMatDocClassAule.infoAule.add(a);
                            }
                            a.addMateria(d.getMateriaLab().nome);
                        }
                        if (sTeo.equalsIgnoreCase("")) {
                            // bloccata
                            d.addOraBloccata(gg, oo);
                        } else if (sTeo.equalsIgnoreCase("-")) {
                            // giorno libero
                            d.setGiornoLibero(gg);
                        } else if (sTeo.equalsIgnoreCase(".")) {
                            // ora non assegnabile
                        } else {
                            Classe cl = infoMatDocClassAule.getClasse(sTeo);
                            if (cl == null) {
                                      System.out.println("Classe da creare:" + sTeo);
                                cl = new Classe(sTeo);
                                infoMatDocClassAule.infoClassi.add(cl);
                            }
                            c = m.colore;
                            o = cl.listaOre.get(gg, oo);
                            if (o == null) {
                                // ora non ancora incontrata
                                o = new GraphOra(gg, oo, cl, m, a, c);
                                if (d.tipo == Docente.TEORICO)
                                    o.setDoc(d);
                                else
                                    o.setDocComp(d);
                            } else {
                                // ora già creata con un docente
                                        System.out.println(cl.nome+" giorno "+gg+" ora "+oo+" aggiungo "+ d.nome);
                                if (d.tipo == Docente.TEORICO) {
                                    if (o.setDoc(d) == false)
                                        o.setDocComp(d);
                                }
                                else {
                                    if (o.setDocComp(d) == false)
                                        o.setDoc(d);
                                }

                            }
                            // le ore di laboratorio sono collegate a coppie
                            if ( (precLab != null) && (precLab.classe == o.classe)
                               && (precLab.aula == o.aula) && (precLab.getPrec() == null)
                               && (precLab.getDoc() == o.getDoc())
                               && (precLab.getDocCom() == o.getDocCom())
                               && (precLab.spazio == oo-1)
                               && (precLab.giorno == gg) )  {
                                precLab.setSucc(o);
                                o.setPrec(precLab);
                            }
                            if (!sLab.equalsIgnoreCase("")) {
                                precLab = o;
                            }
                            else {
                                precLab = null;
                            }
                        }
                    }
                }
                i++;  // salto riga di laboratorio
                       System.out.println("");
            } else if (riga1 != null) {
                // riga normale di un docente senza laboratorio
                Color cTeo = new Color(100, 100, 100, 100);
                Materia mTeo = infoMatDocClassAule.getMateria("Sconosciuta");
                if (mTeo == null) {
                    mTeo = new Materia("Sconosciuta", "SCN", cTeo);
                    infoMatDocClassAule.infoMaterie.add(mTeo);
                }
                d = infoMatDocClassAule.getDocente(riga1.get(0));
                if (d == null) {
                    d = new Docente(riga1.get(0), mTeo, Docente.TEORICO, cTeo);
                    infoMatDocClassAule.infoDocenti.add(d);
                    System.out.println("Creato docente teorico "+d.nome);

                }
                for (int gg = 1; gg <= 6; gg++) {
                    for (int oo = 1; oo <= 6; oo++) {
                        String sTeo = riga1.get(1 + oo - 1 + (gg - 1) * 6);
                                System.out.print(sTeo + " = ");
                        Aula a;
                        m = d.getMateria();
                        a = infoMatDocClassAule.getAula(sTeo);
                        if (a == null) {
                            a = new Aula(sTeo);
                            a.addMateria("tutte");
                            infoMatDocClassAule.infoAule.add(a);
                        }
                        if (sTeo.equalsIgnoreCase("")) {
                            // bloccata
                            d.addOraBloccata(gg, oo);
                        } else if (sTeo.equalsIgnoreCase("-")) {
                            // giorno libero
                            d.setGiornoLibero(gg);
                        } else if (sTeo.equalsIgnoreCase(".")) {
                            // ora non assegnabile
                        } else {
                            Classe cl = infoMatDocClassAule.getClasse(sTeo);
                            if (cl == null) {
                                cl = new Classe(sTeo);
                                infoMatDocClassAule.infoClassi.add(cl);
                            }
                            c = m.colore;
                            o = cl.listaOre.get(gg, oo);
                            if (o == null) {
                                // ora non ancora incontrata
                                o = new GraphOra(gg, oo, cl, m, a, c);
                                if (d.tipo == Docente.TEORICO)
                                    o.setDoc(d);
                                else
                                    o.setDocComp(d);
                            } else {
                                // ora già creata con un docente
                                      System.out.println(cl.nome+" giorno "+gg+" ora "+oo+" aggiungo "+ d.nome);
                                if (d.tipo == Docente.TEORICO) {
                                    if (o.setDoc(d) == false)
                                        o.setDocComp(d);
                                }
                                else {
                                    if (o.setDocComp(d) == false)
                                        o.setDoc(d);
                                }
                            }
                        }
                    }
                }
                        System.out.println("");
            }
        }
    }
}
