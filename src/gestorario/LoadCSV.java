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
                    String s = st.nextToken();
                                System.out.print("Line # " + lineNumber +
                                        ", Token # " + tokenNumber + ", Token : " + s);
                    String s1;
                    if (s.startsWith("\"")){
                        s1 = s.substring(1, s.length()-1);
                        riga.add(s1.trim());
                    }
                    else if (s.isEmpty()){
                        s1 = "";
                    }
                    else{
                        s1 = s.trim();
                        riga.add(s1);
                    }
                                System.out.println(" aggiunto Token : (" + s1 + ")");
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
        OraGraph o, oColl;
        int numRig = 100;
        int numCol = 37;
        ArrayList<String> riga1 = null, riga2 = null;
        // inizio dalla riga 4 perché si saltano le intestazioni
        for (int nRiga = 3; nRiga < nDoc; nRiga++) {
              System.out.println("**************** "+nRiga+" **********************");
//            int nRiga = 16;
            riga1 = getRiga(nRiga);
            if (nRiga < nDoc - 1) {
                riga2 = getRiga(nRiga + 1);
            }
            if ((riga2 != null) && (riga2.get(0).equalsIgnoreCase(""))) {
                // riga di un docente con compresenza/aula lab descritto nella riga che segue
                // colore di default
                Color cTeo = new Color(100, 100, 100, 100);
                // materia di default
                m = infoMatDocClassAule.getMateria("Sconosciuta");
                if (m == null) {
                    m = new Materia("Sconosciuta", "SCN", cTeo);
                    infoMatDocClassAule.infoMaterie.add(m);
                }

                // docente della riga in esame
                d = infoMatDocClassAule.getDocente(riga1.get(0));
                if (d == null) {
                    d = new Docente(riga1.get(0), m, Docente.TEORICO, cTeo);
                    infoMatDocClassAule.infoDocenti.add(d);
                    System.out.println("Creato docente teorico "+d.nome);
                }

                for (int gg = 1; gg <= 6; gg++) {
                    OraGraph precLab = null;
                                System.out.println("riga in esame:("+riga1.toString()+")");
                    for (int ss = 1; ss <= 6; ss++) {
                        Aula a;
                        // se sabato ci sono solo 5 ore si esce dal ciclo
                        // perchè 36 > 35 (considerando che in riga[0] c'è il docente
                        if (1 + ss - 1 + (gg - 1) * 6 >= riga1.size()-1)
                            break;

                        String sTeo = riga1.get(1 + ss - 1 + (gg - 1) * 6);
                        String sLab = riga2.get(1 + ss - 1 + (gg - 1) * 6);
                                System.out.print("teoria:"+sTeo + " = ");
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
                            // ora bloccata
                            d.addOraBloccata(gg, ss);
                        } else if (sTeo.equalsIgnoreCase("O")) {
                            // ora bloccata perchè fatta in un'altra scuola
                            d.addOraBloccata(gg, ss);
                        } else if (sTeo.equalsIgnoreCase("R")) {
                            // ora bloccata perchè fatta in un'altra scuola
                            d.addOraBloccata(gg, ss);
                        } else if (sTeo.equalsIgnoreCase("-")) {
                            // giorno libero
                            d.setGiornoLibero(gg);
                        } else if (sTeo.equalsIgnoreCase("D")) {
                            // ora a disposizione ma libera
                            d.addOraDisposizione(gg, ss);
                        } else if (sTeo.equalsIgnoreCase(".")) {
                            // ora libera
                            d.addOraLibera(gg, ss);
                        } else {
                            Classe cl = infoMatDocClassAule.getClasse(sTeo);
                            if (cl == null) {
                                      System.out.println("Classe da creare:" + sTeo);
                                cl = new Classe(sTeo);
                                infoMatDocClassAule.infoClassi.add(cl);
                            }
                            c = m.colore;
                            o = cl.listaOre.get(gg, ss);
                            if (o == null) {
                                // ora non ancora incontrata
                                o = new OraGraph(gg, ss, cl, m, a, c);
                                if (d.tipo == Docente.TEORICO)
                                    o.setDoc(d);
                                else
                                    o.setDocComp(d);
                            } else {
                                // ora già creata con un docente
                                        System.out.println("ora: "+cl.nome+" giorno "+gg+" ora "+ss+" aggiungo "+ d.nome);
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
                               && (precLab.spazio == ss-1)
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
                nRiga++;  // salto riga di laboratorio
                       System.out.println("Prossima riga");
            } else if (riga1 != null) {
                // riga normale di un docente senza laboratorio
                Color cTeo = new Color(100, 100, 100, 100);
                m = infoMatDocClassAule.getMateria("Sconosciuta");
                if (m == null) {
                    m = new Materia("Sconosciuta", "SCN", cTeo);
                    infoMatDocClassAule.infoMaterie.add(m);
                }
                d = infoMatDocClassAule.getDocente(riga1.get(0));
                if (d == null) {
                    d = new Docente(riga1.get(0), m, Docente.TEORICO, cTeo);
                    infoMatDocClassAule.infoDocenti.add(d);
                    System.out.println("Creato docente teorico "+d.nome);
                }
                m = d.getMateria();
                for (int gg = 1; gg <= 6; gg++) {
                    for (int ss = 1; ss <= 6; ss++) {
                        // se sabato ci sono solo 5 ore si esce dal ciclo
                        // perchè 36 > 35 (considerando che in riga[0] c'è il docente
                        if (1 + ss - 1 + (gg - 1) * 6 >= riga1.size()-1)
                            break;

                        String sTeo = riga1.get(1 + ss - 1 + (gg - 1) * 6);
                                System.out.print("Teoria: "+sTeo + " = ");
                        Aula a;
                        a = infoMatDocClassAule.getAula(sTeo);
                        if (a == null) {
                            a = new Aula(sTeo);
                            a.addMateria("tutte");
                            infoMatDocClassAule.infoAule.add(a);
                        }
                        if (sTeo.equalsIgnoreCase("")) {
                            // ora bloccata
                            d.addOraBloccata(gg, ss);
                        } else if (sTeo.equalsIgnoreCase("O")) {
                            // ora bloccata perchè fatta in un'altra scuola
                            d.addOraBloccata(gg, ss);
                        } else if (sTeo.equalsIgnoreCase("R")) {
                            // ora bloccata perchè fatta in un'altra scuola
                            d.addOraBloccata(gg, ss);
                        } else if (sTeo.equalsIgnoreCase("-")) {
                            // giorno libero
                            d.setGiornoLibero(gg);
                        } else if (sTeo.equalsIgnoreCase("D")) {
                            // ora a disposizione ma libera
                            d.addOraDisposizione(gg, ss);
                        } else if (sTeo.equalsIgnoreCase(".")) {
                            // ora libera
                            d.addOraLibera(gg, ss);
                        } else {
                            Classe cl = infoMatDocClassAule.getClasse(sTeo);
                            if (cl == null) {
                                cl = new Classe(sTeo);
                                infoMatDocClassAule.infoClassi.add(cl);
                            }
                            c = m.colore;
                            o = cl.listaOre.get(gg, ss);
                            if (o == null) {
                                // ora non ancora incontrata
                                o = new OraGraph(gg, ss, cl, m, a, c);
                                if (d.tipo == Docente.TEORICO)
                                    o.setDoc(d);
                                else
                                    o.setDocComp(d);
                            } else {
                                // ora già creata con un docente
                                      System.out.println("ora: "+cl.nome+" giorno "+gg+" ora "+ss+" aggiungo "+ d.nome);
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
