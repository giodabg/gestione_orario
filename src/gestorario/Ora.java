/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorario;

/**
 *
 * @author Gio
 */
public class Ora {
    protected int     giorno;
    protected int     spazio;  // 8-9 = 1°spazio
    protected Classe  classe;
    protected Docente  docente;
    protected Docente  docenteCom;
    protected boolean bloccata;
    protected Materia materia;
    protected Aula    aula;
    protected Ora     succ; // se != null non deve separarsi dalla successiva
    protected Ora     prec; // se != null non deve separarsi dalla precedente
    protected boolean candidata;
    protected boolean corrente;
    protected boolean selezionata;
    protected int     adattaScambio; // -2: no scambio -1: si scambio
    static  public int     NONCALCOLATO = 0;
    static  public int     NOSCAMBIO    = -1;
    static  public int     SISCAMBIO    = 1;

    protected Docente docenteConProblemi;

    Ora() {
        giorno      = 0;
        spazio      = 0;
        classe      = null;
        docente     = null;
        docenteCom  = null;
        bloccata    = false;
        materia     = null;
        aula        = null;
        succ        = null;
        prec        = null;
        candidata   = false;
        corrente    = false;
        selezionata = false;
        adattaScambio = NONCALCOLATO;
        docenteConProblemi = null;
    }

    Ora(int g, int sp, boolean blocc) {
        giorno  = g;
        spazio  = sp;
        classe  = null;
        docente = null;
        docenteCom = null;
        bloccata = blocc;
        materia = null;
        aula    = null;
        succ    = null;
        prec    = null;
        candidata = false;
        corrente = false;
        selezionata = false;
        adattaScambio = NONCALCOLATO;
        docenteConProblemi = null;
    }

    Ora(int g, int sp, Classe cl, Materia m, Aula a) {
        String str;
        giorno  = g;
        spazio  = sp;
        classe  = cl;
//        classe.addOra(this);
        docente = null;
        docenteCom = null;
        bloccata = false;
        materia = m;
        aula    = a;
//        aula.addOra(this);
        succ    = null;
        prec    = null;
        candidata = false;
        corrente = false;
        selezionata = false;
        adattaScambio = NONCALCOLATO;
        docenteConProblemi = null;
    }

    public void setCandidata() {
        candidata = true;
    }
    public void resetCandidata() {
        candidata = false;
    }
    public boolean isCandidata() {
        return candidata;
    }

    public void setCorrente() {
        corrente = true;
    }
    public void resetCorrente() {
        corrente = false;
    }
    public boolean isCorrente() {
        return corrente;
    }

    public void setSelezionata() {
        selezionata = true;
    }
    public void resetSelezionata() {
        selezionata = false;
    }
    public boolean isSelezionata() {
        return selezionata;
    }

    public void resetAll() {
        candidata = false;
        corrente = false;
        selezionata = false;
    }

    public void setScambio(int val) {
        adattaScambio = val;
    }
    public int getScambio() {
        return adattaScambio;
    }

    public Classe getClasse() {
        return classe;
    }
    public void setClasse(Classe val) {
        classe = val;
    }

    public void setSucc(Ora o) {
        succ = o;
    }
    public Ora getSucc() {
        return succ;
    }

    public void setPrec(Ora o) {
        prec = o;
    }
    public Ora getPrec() {
        return prec;
    }
}
