/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorario;

import java.awt.*;
import java.util.Vector;

/**
 *
 * @author Gio
 */
public class ListaInfoMatDocClassAule {
    public Vector   infoMaterie;
    public Vector   infoDocenti;
    public Vector   infoClassi;
    public Vector   infoAule;
    public Vector   infoOreLibere;

    ListaInfoMatDocClassAule() {
        int alphaLab = 140;
        int alpha    = 255;
        int idMat    = 0;
        int idDoc    = 0;
        Color cTeo, cLab;
        Materia mTeo, mLab;
        Docente d;
        OraLibera ol;

        infoMaterie   = new Vector();
        infoDocenti   = new Vector();
        infoClassi    = new Vector();
        infoAule      = new Vector();

        infoOreLibere = new Vector();
        for (int g = 1; g <= GestOrarioApplet.maxNumGiorni; g++)
            for (int s = 1; s <= GestOrarioApplet.maxNumSpazi; s++) {
                ol = new OraLibera(g, s);
                infoOreLibere.add(ol);
            }

        
/************
        cTeo = new Color(255, 255, 255, 255);
        mTeo = new Materia(idMat++, "Ora Libera", " ", cTeo);
        d    = new Docente(idDoc++, " ", mTeo, Docente.TEORICO, 5, cTeo);
        infoDocenti.add(d);
*********/

// Area di indirizzo
        cTeo = new Color(255, 150,  25, alpha);
        mTeo = new Materia(idMat++, "Discipline meccaniche e Tecnologia", "A020", cTeo);
        infoMaterie.add(mTeo);

        cLab = new Color(255, 150,  25, alphaLab);
        mLab = new Materia(idMat++, "Laboratorio di Discipline meccaniche e Tecnologia", "32/C", cLab);
        infoMaterie.add(mLab);

        d = new Docente(idDoc++, "Giordano", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);
        infoMaterie.add(mLab);

        d = new Docente(idDoc++, "Musto", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);
        infoMaterie.add(mLab);

        d = new Docente(idDoc++, "Ricucci", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);
        infoMaterie.add(mLab);

        d = new Docente(idDoc++, "Pravata'", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);
        infoMaterie.add(mLab);

        d = new Docente(idDoc++, "Estatico", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);
        infoMaterie.add(mLab);

        d = new Docente(idDoc++, "Barbazza", mLab, Docente.ITP, -1, cLab);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Rotunno", mLab, Docente.ITP, -1, cLab);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Scozzaro", mLab, Docente.ITP, -1, cLab);
        infoDocenti.add(d);

        cTeo = new Color(255,  0,   0, alpha);
        mTeo = new Materia(idMat++, "Tecnologie informatiche", "A042", cTeo);
        infoMaterie.add(mTeo);

        cLab = new Color(255,   0,   0, alphaLab);
        mLab = new Materia(idMat++, "Laboratorio Tecnologie informatiche", "31/C", cLab);
        infoMaterie.add(mLab);

        d = new Docente(idDoc++, "Breviario", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);
        d.addMateria(mLab);

        d = new Docente(idDoc++, "Rizzi", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);
        d.addMateria(mLab);

        d = new Docente(idDoc++, "Longhi", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);
        d.addMateria(mLab);

        d = new Docente(idDoc++, "Pitorri", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);
        d.addMateria(mLab);

        d = new Docente(idDoc++, "Pepi", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);
        d.addMateria(mLab);

        d = new Docente(idDoc++, "Comparin", mLab, Docente.ITP, -1, cLab);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Andreacchi", mLab, Docente.ITP, -1, cLab);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Bardi", mLab, Docente.ITP, -1, cLab);
        infoDocenti.add(d);

        cTeo = new Color(205,   205,   0, alpha);
        mTeo = new Materia(idMat++, "Elettronica", "I02", cTeo);
        infoMaterie.add(mTeo);

        cLab = new Color(205,   205,   0, alphaLab);
        mLab = new Materia(idMat++, "Laboratorio Elettronica", "26/C", cLab);
        infoMaterie.add(mLab);

        d = new Docente(idDoc++, "Vaghi", mTeo, Docente.TEORICO, 5, cTeo);
        infoDocenti.add(d);
        d.addMateria(mLab);

        d = new Docente(idDoc++, "Ferraro", mLab, Docente.ITP, 6, cLab);
        infoDocenti.add(d);

// Area umanistica
        cTeo = new Color( 255, 200,   0, alpha);
        mTeo = new Materia(idMat++, "Lettere", "A050", cTeo);
        infoMaterie.add(mTeo);

        d = new Docente(idDoc++, "Modica", mTeo, Docente.TEORICO, 6, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Colombo M.", mTeo, Docente.TEORICO, 6, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Varriale", mTeo, Docente.TEORICO, 6, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Fazzito", mTeo, Docente.TEORICO, 6, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "De Sisto", mTeo, Docente.TEORICO, 6, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Della Calce", mTeo, Docente.TEORICO, 6, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "CCC", mTeo, Docente.TEORICO, 6, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "DDD", mTeo, Docente.TEORICO, 6, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "EEE", mTeo, Docente.TEORICO, 6, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "FFF", mTeo, Docente.TEORICO, 6, cTeo);
        infoDocenti.add(d);

        cTeo = new Color( 255, 150, 100, alpha);
        mTeo = new Materia(idMat++, "Discipline Economiche Aziendali", "A017", cTeo);
        infoMaterie.add(mTeo);

        d = new Docente(idDoc++, "Monti", mTeo, Docente.TEORICO, 6, cTeo);
        infoDocenti.add(d);

        cTeo = new Color( 255, 150, 100, alpha);
        mTeo = new Materia(idMat++, "Discipline Giuridiche", "A019", cTeo);
        infoMaterie.add(mTeo);

        d = new Docente(idDoc++, "Siniscalchi", mTeo, Docente.TEORICO, 6, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Lorenzini dir.", mTeo, Docente.TEORICO, 6, cTeo);
        infoDocenti.add(d);

        cTeo = new Color( 255, 100, 150, alpha);
        mTeo = new Materia(idMat++, "Filosofia", "I02", cTeo);
        infoMaterie.add(mTeo);

        d = new Docente(idDoc++, "Gatta", mTeo, Docente.TEORICO, 6, cTeo);
        infoDocenti.add(d);

        cTeo = new Color( 255, 200, 200, alpha);
        mTeo = new Materia(idMat++, "Lingua Inglese", "A046", cTeo);
        infoMaterie.add(mTeo);

        d = new Docente(idDoc++, "Borgonovo", mTeo, Docente.TEORICO, 6, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Arcioni", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Basilico", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Zoccoli", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "IngleseAAA", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);

// Area scientifica
        cTeo = new Color(  0,   0, 255, alpha);
        mTeo = new Materia(idMat++, "Matematica", "A047", cTeo);
        infoMaterie.add(mTeo);

        d = new Docente(idDoc++, "Monti", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Galletta", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Colombo S.", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Genco", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Bianchi R.", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "MatematicaAAA", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Rotunno", mLab, Docente.TEORICO, -1, cLab);
        infoDocenti.add(d);

        cTeo = new Color(  50, 255,   0, alpha);
        mTeo = new Materia(idMat++, "Scienze integrate (Chimica)", "A013", cTeo);
        infoMaterie.add(mTeo);

        cLab = new Color(  50, 255,   0, alphaLab);
        mLab = new Materia(idMat++, "Laboratorio Scienze integrate (Chimica)", "A013", cLab);
        infoMaterie.add(mLab);

        d = new Docente(idDoc++, "Tyrolova", mTeo, Docente.TEORICO, 1, cTeo);
        infoDocenti.add(d);
        d.addMateria(mLab);

        d = new Docente(idDoc++, "Lorenzini ch.", mTeo, Docente.TEORICO, 1, cTeo);
        infoDocenti.add(d);
        d.addMateria(mLab);

        d = new Docente(idDoc++, "ChimicaAAA", mTeo, Docente.TEORICO, 1, cTeo);
        infoDocenti.add(d);
        d.addMateria(mLab);

        d = new Docente(idDoc++, "ChimicaBBB", mTeo, Docente.TEORICO, 1, cTeo);
        infoDocenti.add(d);
        d.addMateria(mLab);

        d = new Docente(idDoc++, "Dargenio", mLab, Docente.ITP, -1, cLab);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Marzolo", mLab, Docente.ITP, -1, cLab);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Pozza", mLab, Docente.ITP, -1, cLab);
        infoDocenti.add(d);

        cTeo = new Color(  50, 155,   0, alpha);
        mTeo = new Materia(idMat++, "Scienze integrate (Scienze della Terra e Biologia)", "A060", cTeo);
        infoMaterie.add(mTeo);

        d = new Docente(idDoc++, "Potenzone", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Zappacosta", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "ScienzeAAA", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "ScienzeBBB", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);

        cTeo = new Color(  0, 100,   0, alpha);
        mTeo = new Materia(idMat++, "Scienze integrate (Fisica)", "A038", cTeo);
        infoMaterie.add(mTeo);

        cLab = new Color(  0, 100,   0, alphaLab);
        mLab = new Materia(idMat++, "Laboratorio Scienze integrate (Fisica)", "29/C", cLab);
        infoMaterie.add(mLab);

        d = new Docente(idDoc++, "Cereda", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);
        d.addMateria(mLab);

        d = new Docente(idDoc++, "Pangallo", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);
        d.addMateria(mLab);

        d = new Docente(idDoc++, "Fratta", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);
        d.addMateria(mLab);

        d = new Docente(idDoc++, "Agostoni", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);
        d.addMateria(mLab);

        d = new Docente(idDoc++, "ITP FIS", mLab, Docente.ITP, 6, cLab);
        infoDocenti.add(d);

        cTeo = new Color(  0,  50,   0, alpha);
        mTeo = new Materia(idMat++, "Tecnologie e tecniche di rappresentazione grafica", "A071", cTeo);
        infoMaterie.add(mTeo);

        cLab = new Color(  0,  50,   0, alphaLab);
        mLab = new Materia(idMat++, "Laboratorio Tecnologie e tecniche di rappresentazione grafica", "A071", cLab);
        infoMaterie.add(mLab);

        d = new Docente(idDoc++, "Arnaboldi", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);
        d.addMateria(mLab);

        d = new Docente(idDoc++, "DisAAA", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);
        d.addMateria(mLab);

        d = new Docente(idDoc++, "DisBBB", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);
        d.addMateria(mLab);

        d = new Docente(idDoc++, "DisCCC", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);
        d.addMateria(mLab);

        cTeo = new Color(  0,  100, 100, alpha);
        mTeo = new Materia(idMat++, "Biologia", "I02", cTeo);
        infoMaterie.add(mTeo);

        d = new Docente(idDoc++, "Privitelli", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);

// Altre        
        cTeo = new Color(150, 150, 150, alphaLab);
        mTeo = new Materia(idMat++, "Scienze motorie e sportive", "I02", cTeo);
        infoMaterie.add(mTeo);

        d = new Docente(idDoc++, "Gadina", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Imperlino", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);
        
        d = new Docente(idDoc++, "Roccella", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);


        cTeo = new Color(220, 220, 220, alpha);
        mTeo = new Materia(idMat++, "Religione Cattolica o attività alternative", "A029", cTeo);
        infoMaterie.add(mTeo);

        d = new Docente(idDoc++, "Tagliabue R.", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Nigro S.", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);

    }

    public Color getMateriaColor(String nomeMateria){
        Color c;

        if (infoMaterie.size() == 0)
            return null;
        
        int i = infoMaterie.size()-1;
        Materia m  = (Materia) infoMaterie.get(i);
        while ((i >= 0) && (m.nome.compareToIgnoreCase(nomeMateria) != 0)) {
            m  = (Materia) infoMaterie.get(i);
            i--;
        }
        if (m.nome.compareToIgnoreCase(nomeMateria) == 0) {
            return m.colore;
        }
        else
            return Color.WHITE;
    }

    public Materia getMateria(String nomeMateria){
        if (infoMaterie.size() == 0)
            return null;

        int i = infoMaterie.size()-1;
        Materia m  = (Materia) infoMaterie.get(i);
        while ((i >= 0) && (m.nome.compareToIgnoreCase(nomeMateria) != 0)) {
            m  = (Materia) infoMaterie.get(i);
            i--;
        }
        if (m.nome.compareToIgnoreCase(nomeMateria) == 0) {
            return m;
        }
        else
            return null;
    }

    public Color docenteColor(String nomeDocente){
        if (infoDocenti.size() == 0)
            return null;

        Color c;

        int i = infoDocenti.size()-1;
        Docente d  = (Docente) infoDocenti.get(i);
        while ((i >= 0) && (d.nome.compareToIgnoreCase(nomeDocente) != 0)) {
            d  = (Docente) infoDocenti.get(i);
            i--;
        }
        if (d.nome.compareToIgnoreCase(nomeDocente) == 0) {
            return d.colore;
        }
        else
            return Color.WHITE;
    }

    public Docente getDocente(String nomeDocente){
        if (infoDocenti.size() == 0)
            return null;

        int i = infoDocenti.size()-1;
        Docente d  = (Docente) infoDocenti.get(i);
        while ((i >= 0) && (d.nome.compareToIgnoreCase(nomeDocente) != 0)) {
            d  = (Docente) infoDocenti.get(i);
            i--;
        }
        if (d.nome.compareToIgnoreCase(nomeDocente)== 0) {
            return d;
        }
        else
            return null;
    }

    public Color getClasseColor(String nomeClasse){
        Color c;

        if (infoClassi.size() == 0)
            return null;

        int i = infoClassi.size()-1;
        Classe cl  = (Classe) infoClassi.get(i);
        while ((i >= 0) && (cl.nome.compareToIgnoreCase(nomeClasse) != 0)) {
            cl  = (Classe) infoClassi.get(i);
            i--;
        }
        if (cl.nome.compareToIgnoreCase(nomeClasse) == 0) {
            return cl.colore;
        }
        else
            return Color.WHITE;
    }

    public Classe getClasse(String nomeClasse){
        if (infoClassi.size() == 0)
            return null;

        int i = infoClassi.size()-1;
        Classe cl  = (Classe) infoClassi.get(i);
        while ((i >= 0) && (cl.nome.compareToIgnoreCase(nomeClasse) != 0)) {
            cl  = (Classe) infoClassi.get(i);
            i--;
        }
        if (cl.nome.compareToIgnoreCase(nomeClasse) == 0) {
            return cl;
        }
        else
            return null;
    }

    public Color getAulaColor(String nomeAula){
        Color c;

        if (infoAule.size() == 0)
            return null;

        int i = infoAule.size()-1;
        Aula a  = (Aula) infoAule.get(i);
        while ((i >= 0) && (a.nome.compareToIgnoreCase(nomeAula) != 0)) {
            a  = (Aula) infoAule.get(i);
            i--;
        }
        if (a.nome.compareToIgnoreCase(nomeAula) == 0) {
            return a.colore;
        }
        else
            return Color.WHITE;
    }

    public Aula getAula(String nomeAula){
        if (infoAule.size() == 0)
            return null;

        int i = infoAule.size()-1;
        Aula a  = (Aula) infoAule.get(i);
        while ((i >= 0) && (a.nome.compareToIgnoreCase(nomeAula) != 0)) {
            a  = (Aula) infoAule.get(i);
            i--;
        }
        if (a.nome.compareToIgnoreCase(nomeAula) == 0) {
            return a;
        }
        else
            return null;
    }

    public OraLibera getOraLibera(int g, int s){
        if (infoOreLibere.size() == 0)
            return null;

        int i = infoOreLibere.size()-1;
        OraLibera o  = (OraLibera) infoOreLibere.get(i);
        while ((i >= 0) && (o.giorno() == g) && (o.spazio() == s)) {
            o  = (OraLibera) infoOreLibere.get(i);
            i--;
        }
        if ((o.giorno() == g) && (o.spazio() == s)) {
            return o;
        }
        else
            return null;
    }

}
