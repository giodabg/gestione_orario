/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorario;

import java.awt.*;  //grafica
import java.util.Vector;
import javax.swing.*; // dialog box
    /**
 *
 * @author Gio
 */
public class Classe {

    static int lastID = 0;
    String nome;
    int    idClasse;
    int    anno;
    char   sezione;
    int    indirizzo;
    char   istituto;
    ListaOre listaOre;
    int    hFont;
    Color  colore;

//    Classe(String s, ListaInfoMatDocClassAule materieDocenti) {
    Classe(String s) {
        nome = s;
        if (s.equalsIgnoreCase("D")) {
            nome = "Dispo";
            idClasse = lastID; lastID++;
            anno = 1;
            sezione = 'D';
            indirizzo = 'D';
        }
        else if (s.equalsIgnoreCase("O")) {
            nome = "AltraSc";
            idClasse = lastID; lastID++;
            anno = 1;
            sezione = 'O';
            indirizzo = 'O';
        }
        else {
            idClasse = lastID; lastID++;
            anno = s.charAt(0) - '0';
            sezione = s.charAt(1);
            try {
                indirizzo = indirizzo2Int(s.charAt(2));
            } catch (Exception e) {
                indirizzo = indirizzo2Int('B');
            }
        }
        istituto = 'I';
        listaOre = new ListaOre();
//        Aula aulaClasse  = new Aula(s);

//        listaOre.init(this, aulaClasse, materieDocenti);
        hFont = 10;
        colore = classeColor();
    }

    Classe(String str, int id, int a, char s, String ind, char is, Aula aula, ListaInfoMatDocClassAule materieDocenti, Color c) {
        nome = str;
        idClasse = id;
        anno = a;
        sezione = s;
        indirizzo = indirizzo2Int(ind);
        istituto = is;
        listaOre = new ListaOre();
        hFont = 20;
        colore = c;
    }

    public void addOra(GraphOra o) {
        /****
        GraphOra oo = listaOre.get(o.giorno, o.spazio);
        if (oo != null)
            listaOre.remove(oo);
        oo = null;
         */
        if (!listaOre.add(o)) {
            GraphOra oo = listaOre.get(o.giorno, o.spazio);
            String str = "Nella classe "+nome+", nel giorno "+o.giorno+" l'ora "+o.spazio+" è già assegnata a ";
            if (oo.getDoc() != null) {
                str += oo.getDoc().nome;
            }
            if (oo.getDocCom() != null) {
                str += " "+oo.getDocCom().nome;
            }
            System.out.println(str);
            JOptionPane.showMessageDialog(null, str);
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
                        + " Nome: "+anno+sezione+" "+int2Indirizzo(indirizzo, true)
                        +"Ist:"+istituto,  x, y);
        }
        else {
            g.drawString("Cl: "+anno+sezione+" "+int2Indirizzo(indirizzo, false),  x, y);
        }

    }

    public int hPaint(boolean tutte) {
        if (tutte)
            return hFont;
        else
            return hFont;
    }
}
