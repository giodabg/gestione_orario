/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorario;

import java.awt.*;      // grafica
import javax.swing.*;   // dialog box
import java.util.Vector;

/**
 *
 * @author Gio
 */
public class Classe {

    static int lastID = 0;
    String      nome;
    int         idClasse;
    int         anno;
    char        sezione;
    int         indirizzo;
    char        istituto;
    ListaOre    listaOre;      // lista ore di lezione
    Vector      listaDoc;      // lista docenti che insegnano nella Classe
    Vector      oreLibere;     // ore libere

    int         hFont;
    Color       colore;

//    Classe(String s, ListaInfoMatDocClassAule materieDocenti) {
    Classe(String str) {
        nome = str;
        if (nome.equalsIgnoreCase("D")) {
            nome = "Dispo";
            idClasse = lastID; lastID++;
            anno = 1;
            sezione = 'D';
            indirizzo = 'D';
        }
        // "O" indica un'ora in un'altra scuola
        else if (nome.equalsIgnoreCase("O")) {
            nome = "AltraSc";
            idClasse = lastID; lastID++;
            anno = 1;
            sezione = 'O';
            indirizzo = 'O';
        }
        // "R" indica un'ora in un'altra scuola
        else if (nome.equalsIgnoreCase("R")) {
            nome = "AltraSc";
            idClasse = lastID; lastID++;
            anno = 1;
            sezione = 'R';
            indirizzo = 'R';
        }
        else {
            idClasse = lastID; lastID++;
            anno = nome.charAt(0) - '0';
            sezione = nome.charAt(1);
            try {
                indirizzo = indirizzo2Int(nome.charAt(2));
            } catch (Exception e) {
                indirizzo = indirizzo2Int('B');
            }
        }
        istituto = 'I';
        listaOre = new ListaOre();
        // predispongo un vettore vuoto
        // e per ogni ora teoricamente possibile aggiungo l'ora libera
        oreLibere = new Vector();
        for (int g = 1; g <= GestOrarioApplet.maxNumGiorni; g++) {
            for (int s = 1; s <= GestOrarioApplet.maxNumSpazi; s++) {
                OraLibera ol;
                ol = (OraLibera) GestOrarioApplet.infoMatDocAule.infoOreLibere.get(
                        (g-1)*GestOrarioApplet.maxNumSpazi+(s-1));
                if (ol != null) {
                    ol.addClasse(this);
                    oreLibere.add(ol);
                }
                 else {
                    String str1 = nome+" non può avere nel giorno "+g+" l'ora "+s+" come libera.";
                    System.out.println(str1);
                    JOptionPane.showMessageDialog(null, str1);
                }
           }
        }

        listaDoc = new Vector();
//        Aula aulaClasse  = new Aula(s);

//        listaOre.init(this, aulaClasse, materieDocenti);
        hFont = 10;
        colore = classeColor();
    }

    Classe(String str, int id, int a, char sez, String ind, char is, Aula aula, ListaInfoMatDocClassAule materieDocenti, Color c) {
        nome = str;
        idClasse = id;
        anno = a;
        sezione = sez;
        indirizzo = indirizzo2Int(ind);
        istituto = is;
        listaOre = new ListaOre();
        // predispongo un vettore vuoto
        // e per ogni ora teoricamente possibile aggiungo l'ora libera
        oreLibere = new Vector();
        for (int g = 1; g <= GestOrarioApplet.maxNumGiorni; g++) {
            for (int s = 1; s <= GestOrarioApplet.maxNumSpazi; s++) {
                OraLibera ol;
                ol = (OraLibera) GestOrarioApplet.infoMatDocAule.infoOreLibere.get(
                        (g-1)*GestOrarioApplet.maxNumSpazi+(s-1));
                if (ol != null) {
                    ol.addClasse(this);
                    oreLibere.add(ol);
                }
                 else {
                    String str1 = nome+" non può avere nel giorno "+g+" l'ora "+s+" come libera.";
                    System.out.println(str1);
                    JOptionPane.showMessageDialog(null, str1);
                }
           }
        }
        listaDoc = new Vector();
        hFont = 20;
        colore = c;
    }

    public void addOra(OraGraph o) {
        if (!listaOre.add(o)) {
            OraGraph oo = listaOre.get(o.giorno, o.spazio);
            String str = "Nella classe "+nome+", nel giorno "+o.giorno+" l'ora "+o.spazio;
            str += " non può essere assegnata a "+o.classe.nome+" perchè c'è già ";
            if (oo.getDoc() != null) {
                str += oo.getDoc().nome;
            }
            if (oo.getDocCom() != null) {
                str += " "+oo.getDocCom().nome;
            }
            System.out.println(str);
            JOptionPane.showMessageDialog(null, str);

            if (listaDoc.indexOf(oo.docente) != -1)
                listaDoc.add(oo.docente);
            if ((oo.docenteCom != null) && (listaDoc.indexOf(oo.docenteCom) != -1))
                listaDoc.add(oo.docenteCom);

        }
        else {
             // devo rimuovere l'ora da quelle libere
            OraLibera ol;
            ol = (OraLibera) GestOrarioApplet.infoMatDocAule.infoOreLibere.get(
                    (o.giorno-1)*GestOrarioApplet.maxNumSpazi+(o.spazio-1));
            if (ol != null) {
                ol.removeClasse(this);
                oreLibere.remove(ol);
            }
             else {
                String str = nome+" nel giorno "+o.giorno+" l'ora "+o.spazio+" non può essere rimossa dalle ore libere.";
                System.out.println(str);
                JOptionPane.showMessageDialog(null, str);
            }
        }

    }

    private Color classeColor() {
        Color color = new Color(255,255,255);
        int dist = 50;
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        switch (anno) {
            case 1:
                    break;
            case 2:
                    r = Math.max((int)(color.getRed()   -dist), 0);
                    g = Math.max((int)(color.getGreen() -dist), 0);
                    b = Math.max((int)(color.getBlue()  -dist), 0);
                    break;
            case 3:
                    break;
            case 4:
                    r = Math.max((int)(color.getRed()   -dist), 0);
                    g = Math.max((int)(color.getGreen() -dist), 0);
                    b = Math.max((int)(color.getBlue()  -dist), 0);
                    break;
            case 5:
                    r = Math.max((int)(color.getRed()   -2*dist), 0);
                    g = Math.max((int)(color.getGreen() -2*dist), 0);
                    b = Math.max((int)(color.getBlue()  -2*dist), 0);
                    break;
            default:
                    r = 255;
                    g = 0;
                    b = 0;
        }

        /*****************
        switch (indirizzo) {
            case 1: color = Color.ORANGE;
                    r = color.getRed();
                    g = Math.max((int)(color.getGreen() -(anno-1)*70), 0);
                    b = color.getBlue();
                    break;
            case 2: color = Color.BLUE;
                    r = color.getRed();
                    g = Math.min((int)(color.getGreen() +(anno-1)*70), 255);
                    b = color.getBlue();
                    break;
            case 3: color = Color.GREEN;
                    r = color.getRed();
                    g = Math.max((int)(color.getGreen() -(anno-1)*70), 0);
                    b = color.getBlue();
                    break;
            case 4: color = Color.GRAY;
                    r = Math.max((int)(color.getRed()   -(anno-1)*40), 0);
                    g = Math.max((int)(color.getGreen() -(anno-1)*40), 0);
                    b = Math.max((int)(color.getBlue()  -(anno-1)*40), 0);
                    break;
            default: color = Color.BLACK;
        }
        *****************/
//        System.out.println("Classe:"+nome+"Anno="+anno+"R="+r+"G="+g+"B="+b);
	return new Color(r, g, b);
    }

    private String int2Indirizzo(int i, boolean completo) {
        if (completo) {
            switch (i) {
                case 1: return "Informatica";
                case 2: return "Meccanica";
                case 3: return "Liceo";
                case 4: return "Biennio";
                default: return "Errore";
            }
         }
        else {
            switch (i) {
                case 1: return "Inf";
                case 2: return "Mec";
                case 3: return "Lic";
                case 4: return "Bie";
                default: return "Errore";
            }
        }
     }

    private int indirizzo2Int(String ind) {
        if (ind.equalsIgnoreCase("Informatica"))
            return 1;
        else if (ind.equalsIgnoreCase("Meccanica"))
            return 2;
        else if (ind.equalsIgnoreCase("Liceo"))
            return 3;
        else if (ind.equalsIgnoreCase("Biennio"))
            return 4;
        else
            return 0;
    }

    private int indirizzo2Int(char ind) {
        if (ind == 'I')
            return 1;
        else if (ind == 'M')
            return 2;
        else if (ind == 'L')
            return 3;
        else if (ind == 'B')
            return 4;
        else
            return 0;
    }

    public void paint (Graphics g, int x, int y, boolean tutte) {
        g.setColor(Color.BLACK);
        if (tutte) {
            g.drawString("Classe ID: "+idClasse
                        + " Nome:"+anno+sezione+" "+int2Indirizzo(indirizzo, true)
                        + " Ist:"+istituto,  x, y);
        }
        else {
            g.drawString("CL: "+anno+sezione+" "+int2Indirizzo(indirizzo, false),  x, y);
        }

    }

    public int hPaint(boolean tutte) {
        if (tutte)
            return hFont;
        else
            return hFont;
    }
}
