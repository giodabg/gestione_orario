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

    ListaInfoMatDocClassAule() {
        int alphaLab = 140;
        int alpha    = 255;
        int idMat    = 0;
        int idDoc    = 0;
        Color cTeo, cLab;
        Materia mTeo, mLab;
        Docente d;

        infoMaterie   = new Vector();
        infoDocenti   = new Vector();
        infoClassi    = new Vector();
        infoAule      = new Vector();
        
        cTeo = new Color(255, 255, 255, 255);
        mTeo = new Materia(idMat++, "Ora Libera", " ", cTeo);
        d    = new Docente(idDoc++, " ", mTeo, Docente.TEORICO, 5, cTeo);
        infoDocenti.add(d);

// Area di indirizzo
        cTeo = new Color(255, 150,  25, alpha);
        mTeo = new Materia(idMat++, "Meccanica", "I02", cTeo);
        infoMaterie.add(mTeo);

        cLab = new Color(255, 150,  25, alphaLab);
        mLab = new Materia(idMat++, "Laboratorio di Meccanica", "I02", cLab);
        infoMaterie.add(mLab);

        d = new Docente(idDoc++, "Giordano", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);
        infoMaterie.add(mLab);

        d = new Docente(idDoc++, "Musto", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);
        infoMaterie.add(mLab);

        d = new Docente(idDoc++, "Curcuraci", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);
        infoMaterie.add(mLab);

        d = new Docente(idDoc++, "Estatico", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);
        infoMaterie.add(mLab);

        d = new Docente(idDoc++, "Barbazza", mLab, Docente.ITP, -1, cLab);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Rotunno", mLab, Docente.ITP, -1, cLab);
        infoDocenti.add(d);

        cTeo = new Color(255,  0,   0, alpha);
        mTeo = new Materia(idMat++, "Tecnologie informatiche", "I02", cTeo);
        infoMaterie.add(mTeo);

        cLab = new Color(255,   0,   0, alphaLab);
        mLab = new Materia(idMat++, "Laboratorio Tecnologie informatiche", "I02", cLab);
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

        d = new Docente(idDoc++, "Comparin", mLab, Docente.ITP, -1, cLab);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Mundo", mLab, Docente.ITP, -1, cLab);
        infoDocenti.add(d);

        cTeo = new Color(205,   205,   0, alpha);
        mTeo = new Materia(idMat++, "Elettronica", "I02", cTeo);
        infoMaterie.add(mTeo);

        cLab = new Color(205,   205,   0, alphaLab);
        mLab = new Materia(idMat++, "Laboratorio Elettronica", "I02", cLab);
        infoMaterie.add(mLab);

        d = new Docente(idDoc++, "Vaghi", mTeo, Docente.TEORICO, 5, cTeo);
        infoDocenti.add(d);
        d.addMateria(mLab);

        d = new Docente(idDoc++, "ITP ELE", mLab, Docente.ITP, 6, cLab);
        infoDocenti.add(d);

// Area umanistica
        cTeo = new Color( 255, 200,   0, alpha);
        mTeo = new Materia(idMat++, "Lingua e letteratura italiana", "I02", cTeo);
        infoMaterie.add(mTeo);

        d = new Docente(idDoc++, "DeSisto", mTeo, Docente.TEORICO, 6, cTeo);
        infoDocenti.add(d);

        cTeo = new Color( 255, 200, 150, alpha);
        mTeo = new Materia(idMat++, "Storia, Cittadinanza e Costituzione", "I02", cTeo);
        infoMaterie.add(mTeo);

        d = new Docente(idDoc++, "DeSisto", mTeo, Docente.TEORICO, 6, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Modica", mTeo, Docente.TEORICO, 6, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Colombo M.", mTeo, Docente.TEORICO, 6, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Fazzito", mTeo, Docente.TEORICO, 6, cTeo);
        infoDocenti.add(d);

        cTeo = new Color( 255, 150, 100, alpha);
        mTeo = new Materia(idMat++, "Diritto ed economia", "I02", cTeo);
        infoMaterie.add(mTeo);

        d = new Docente(idDoc++, "MONTI  Econ. ", mTeo, Docente.TEORICO, 6, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "LORENZINI F.-d", mTeo, Docente.TEORICO, 6, cTeo);
        infoDocenti.add(d);

        cTeo = new Color( 255, 100, 150, alpha);
        mTeo = new Materia(idMat++, "Filosofia", "I02", cTeo);
        infoMaterie.add(mTeo);

        d = new Docente(idDoc++, "Gatta", mTeo, Docente.TEORICO, 6, cTeo);
        infoDocenti.add(d);

        cTeo = new Color( 255, 200, 200, alpha);
        mTeo = new Materia(idMat++, "Lingua Inglese", "I02", cTeo);
        infoMaterie.add(mTeo);

        d = new Docente(idDoc++, "Borgonovo", mTeo, Docente.TEORICO, 6, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Arcioni", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Ballabio", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);

// Area scientifica
        cTeo = new Color(  0,   0, 255, alpha);
        mTeo = new Materia(idMat++, "Matematica", "I02", cTeo);
        infoMaterie.add(mTeo);

        d = new Docente(idDoc++, "COLOMBO S.", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Galletta", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Monti Enrica", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Genco", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Bianchi R.", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "De Filippis", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Danaro", mLab, Docente.ITP, -1, cLab);
        infoDocenti.add(d);

        cTeo = new Color(  50, 255,   0, alpha);
        mTeo = new Materia(idMat++, "Scienze integrate (Chimica)", "I02", cTeo);
        infoMaterie.add(mTeo);

        cLab = new Color(  50, 255,   0, alphaLab);
        mLab = new Materia(idMat++, "Laboratorio Scienze integrate (Chimica)", "I02", cLab);
        infoMaterie.add(mLab);

        d = new Docente(idDoc++, "Cereda", mTeo, Docente.TEORICO, 1, cTeo);
        infoDocenti.add(d);
        d.addMateria(mLab);

        d = new Docente(idDoc++, "Tyrolova", mTeo, Docente.TEORICO, 1, cTeo);
        infoDocenti.add(d);
        d.addMateria(mLab);

        d = new Docente(idDoc++, "Lorenzini F. fc", mTeo, Docente.TEORICO, 1, cTeo);
        infoDocenti.add(d);
        d.addMateria(mLab);

        d = new Docente(idDoc++, "Pagani", mTeo, Docente.TEORICO, 1, cTeo);
        infoDocenti.add(d);
        d.addMateria(mLab);

        d = new Docente(idDoc++, "DARGENIO", mLab, Docente.ITP, -1, cLab);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Marzolo", mLab, Docente.ITP, -1, cLab);
        infoDocenti.add(d);

        cTeo = new Color(  50, 155,   0, alpha);
        mTeo = new Materia(idMat++, "Scienze integrate (Scienze della Terra e Biologia)", "I02", cTeo);
        infoMaterie.add(mTeo);

        d = new Docente(idDoc++, "Rossi", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);

        cTeo = new Color(  0, 100,   0, alpha);
        mTeo = new Materia(idMat++, "Scienze integrate (Fisica)", "I02", cTeo);
        infoMaterie.add(mTeo);

        cLab = new Color(  0, 100,   0, alphaLab);
        mLab = new Materia(idMat++, "Laboratorio Scienze integrate (Fisica)", "I02", cLab);
        infoMaterie.add(mLab);

        d = new Docente(idDoc++, "Fratta", mTeo, Docente.TEORICO, -1, cTeo);
        infoDocenti.add(d);
        d.addMateria(mLab);

        d = new Docente(idDoc++, "Liveriero", mLab, Docente.ITP, -1, cLab);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Pratico'", mLab, Docente.ITP, 6, cLab);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "Scozzaro", mLab, Docente.ITP, 6, cLab);
        infoDocenti.add(d);

        d = new Docente(idDoc++, "ITP FIS", mLab, Docente.ITP, 6, cLab);
        infoDocenti.add(d);

        cTeo = new Color(  0,  50,   0, alpha);
        mTeo = new Materia(idMat++, "Tecnologie e tecniche di rappresentazione grafica", "I02", cTeo);
        infoMaterie.add(mTeo);

        cLab = new Color(  0,  50,   0, alphaLab);
        mLab = new Materia(idMat++, "Laboratorio Tecnologie e tecniche di rappresentazione grafica", "I02", cLab);
        infoMaterie.add(mLab);

        d = new Docente(idDoc++, "Arnaboldi", mTeo, Docente.TEORICO, -1, cTeo);
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
        mTeo = new Materia(idMat++, "Religione Cattolica o attività alternative", "I02", cTeo);
        infoMaterie.add(mTeo);

        d = new Docente(idDoc++, "TAGLIABUE R.", mTeo, Docente.TEORICO, -1, cTeo);
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

}
