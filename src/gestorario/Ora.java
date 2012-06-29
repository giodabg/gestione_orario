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
    private   Ora     succ; // se != null non deve separarsi dalla successiva
    private   Ora     prec; // se != null non deve separarsi dalla precedente
    protected int     selezionata; // 0:no 1:prima 2:seconda
    protected int     adattaScambio; // -2: no scambio -1: si scambio
    static  public int     NONCALCOLATO = 0;
    static  public int     NOSCAMBIO = -2;
    static  public int     SISCAMBIO = -1;
    static  public int     NONSEL    =  0;
    static  public int     PRIMA     =  1;
    static  public int     SECONDA   =  2;
    static  public int     TERZA     =  3;

    protected Docente docenteConProblemi;

    Ora() {
        giorno  = 0;
        spazio  = 0;
        classe  = null;
        docente = null;
        docenteCom = null;
        bloccata = false;
        materia = null;
        aula    = null;
        succ    = null;
        prec    = null;
        selezionata = NONSEL;
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
        selezionata = NONSEL;
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
        selezionata = NONSEL;
        adattaScambio = NONCALCOLATO;
        docenteConProblemi = null;
    }

    public void setSelezionata(int val) {
        selezionata = val;
    }

    public int getSelezionata() {
        return selezionata;
    }

    public void setSucc(Ora o) {
        succ = o;
    }

    public void setPrec(Ora o) {
        prec = o;
    }

    public Ora getSucc() {
        return succ;
    }

    public Ora getPrec() {
        return prec;
    }
}
