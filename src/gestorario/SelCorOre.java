package gestorario;

import java.util.Vector;
import javax.swing.*; // dialog box
import java.awt.*;  //grafica
/**
 *
 * @author Gio
 */

public class SelCorOre {
    private OraInt curOra;        // ora corrente (mouse over)

    // si assume che le catene siano lunghe al massimo 2

    private int    numSelUtente;  // numero ore selezionate dall'utente
    private OraInt selOra1;       // giorno selezionato prima ora
    private OraInt selOra2;       // giorno selezionato seconda ora

    private OraInt canOra1;   // giorno candidato prima ora
    private OraInt canOra2;   // giorno candidato seconda ora

    private OraInt intermOra1;   // giorno intermedio prima ora
    private OraInt intermOra2;   // giorno intermedio seconda ora

    public Vector listOreInterm1;    // lista delle possibili ore intermedie
    public Vector listOreInterm2;    // per effettuare uno scambio

    static int MAX_NUM_MESS = 40;
    private Vector errorMess;
    private int totErrMess;

    private Vector solutionMess;
    private int totSolMess;

    SelCorOre () {
        curOra = new OraInt();
        
        numSelUtente = 0;
        selOra1 = new OraInt();
        selOra2 = new OraInt();

        canOra1 = new OraInt();
        canOra2 = new OraInt();

        intermOra1 = new OraInt();
        intermOra2 = new OraInt();

        listOreInterm1 = new Vector();
        listOreInterm2 = new Vector();

        errorMess      = new Vector();
        totErrMess     = 0;

        solutionMess   = new Vector();
        totSolMess     = 0;
    }

    public boolean setCurrent(OraInt ora, ListaOre listaOre) {

        if ((numSelUtente == 1) && (!selOra2.vuota())) {
            // se l'utente ha selezionato una sola ora
            // può darsi che nella ricerca di un'ora candidata
            // il programma abbia selezionato anche una seconda ora
            // che va resettata prima di procedere
            selOra2.reSet();
        }
        curOra.reSet();
        resetCandidated();
        resetOreInterm();

//        if ( !ora.uguale(selOra1)
//          && !ora.uguale(canOra1)
//          && !ora.uguale(canOra2) ) {
            // non è ne una delle ore selezionate nè una di quelle candidate
            if ( selOra1.vuota() ) {
                // se in tabella 1 con stato nessuna ora corrente (1) o
                // ora corrente esistente (2) imposto la nuova se è lecita
                 if (howManyCanBeSelected(listaOre.get(ora.giorno(), ora.spazio())) != 0) {
                     curOra.set(ora);
                     return true;
                 }
            }
            else if (!selOra1.vuota()) {

                boolean set = setCandidated(ora, listaOre);
                if (set) {
                    // se sono riuscito a impostare la/le ore candidate
                    // imposto anche l'ora corrente
                    curOra.set(ora);
                    return true;
                }
            }

//        }
        return false;
    }

    public boolean esisteCurrent() {
        return ( !curOra.vuota() );
    }

    // imposta l'ora/le ore selezionate in base a ora
    public boolean setSelected(OraInt ora, int statoRicerca, ListaOre listaOre) {
        if (!ora.vuota()) {
            // controllo quante ore posso selezionare in base a ora
            int numOre = howManyCanBeSelected(listaOre.get(ora.giorno(), ora.spazio()));
            if (numOre > 0) {
                // se ora è quella corrente deve essere resettata per evitare
                // che l'ora sia contemporaneamente corrente e selezionata
                if (ora.uguale(curOra))
                    resetCurrent(statoRicerca);

                if (numOre == 1) {
                    // si memorizza la scelta dell'utente
                    numSelUtente = 1;
                    selOra1.set(ora);
                    selOra2.reSet();
                }
                else {
                    // si memorizza la scelta dell'utente
                    numSelUtente = 2;
                    // è la prima di una catena per cui si seleziona la seconda
                    // assumendo che le catene siano lunghe al massimo 2
                    selOra1.set(ora);
                    selOra2.set(ora.giorno(), ora.spazio() + 1);
                }
                return true;
            }
        }
        return false;
    }

    //  questa versione non funziona perchè solitaria essendo un tipo base
    //  non viene modificata (esattamente come il titpo String)
    //  in pratica parametri in uscita non ci sono in Java
    //  public boolean howManyCanBeSelected(OraInt ora, boolean solitaria, ListaOre listaOre) {

    //  restituisce quante ore possonono essere scelte come candidate
    public int howManyCanBeSelected(OraGraph grOra) {
        // si può impostare l'ora selezionata come corrente se
        // è solitaria o se
        // la selezione è la prima di una catena
        int numOre = 0;
        if (grOra != null) {
            if ((grOra.getSucc() == null) && (grOra.getPrec() == null)) {
                // se è solitaria la seconda ora da selezionare non esiste
                numOre = 1;
            }
            else if ((grOra.getSucc() != null) && (grOra.getPrec() == null)) {
                // se è la prima di due devono selezionarle entrambe
                numOre = 2;
            }
            // se è la seconda di due non si può selezionare nulla
        }
        return numOre;
    }

    public boolean esisteSelected() {
        return (!selOra1.vuota());
    }

    public boolean esisteCandidated() {
        return (!canOra1.vuota());
    }

    public boolean esisteIntermetied() {
        return (!intermOra1.vuota());
    }

    // data la/le ore selezionate e l'ora passata determina la/le ore candidate
    // ed eventualmente modifica la/le ore selezionata

    public boolean setCandidated(OraInt ora, ListaOre listaOre) {
        if (!ora.vuota()) {
            int ok_solitaria = howManyCanBeCandidated(ora, listaOre);
            if (ok_solitaria != 0) {
                canOra1.set(ora);
                if (ok_solitaria == 1)
                    canOra2.reSet();
                else if (ok_solitaria == 2) {
                // selezione e candidate sono prime di una catena
                // si seleziona la seconda
                // assumendo che le catene siano lunghe al massimo 2
                    canOra2.set(ora.giorno(), ora.spazio() + 1);
                }
                else if (ok_solitaria == 3) {
                // selezione è solitaria, ma seguita da solitara
                // e candidata è la prima di una catena
                // il programma seleziona anche quella che segue selezione
                    selOra2.set(selOra1.giorno(), selOra1.spazio() + 1);
                    canOra2.set(ora.giorno(), ora.spazio() + 1);
                }
                else if (ok_solitaria == -3) {
                // selezione è solitaria, ma preceduta da solitara
                // e candidata è la prima di una catena
                // il programma seleziona anche quella che precede selezione
                    selOra2.set(selOra1.giorno(), selOra1.spazio() - 1);
                    canOra2.set(ora.giorno(), ora.spazio() - 1);
                }
                return true;
            }
        }
        return false;
    }
    
    public int howManyCanBeCandidated(OraInt ora, ListaOre listaOre) {
        int numOre = 0;
        OraGraph grOra = listaOre.get(ora.giorno(), ora.spazio());
        if (grOra != null) {
            // "ora" candidata è un'ora occupata
            if ((grOra.getSucc() == null) && (grOra.getPrec() == null)) {
                // se "ora" candidata è solitaria
                if (selOra2.vuota()) {
                    // se la selezione è solitaria "ora" può essere candidata e
                    // la seconda ora da selezionare non esiste
                    numOre = 1;
                }
                else {
                    // se la selezione non è solitaria
                    OraGraph sucOra = listaOre.get(ora.giorno(), ora.spazio()+1);
                    if ((sucOra != null) &&
                        (sucOra.getSucc() == null) && (sucOra.getPrec() == null)) {
                        // se la seguente a "ora" candidata è solitaria
                        // allora assieme possono essere candidate
                        numOre = 2;
                    }
                    else if ((sucOra == null) && (ora.spazio()+1 <= 6)){
                        // se la seguente a "ora" candidata è libera e non
                        // è fuori dal termine delle lezioni
                        // allora assieme possono essere candidate
                        numOre = 2;
                    }
                    else if ((selOra1.giorno() == ora.giorno()) &&
                        (selOra1.spazio() == ora.spazio()+1) ) {
                        // se "ora" candidata
                        // si trova appena sopra alla selezione
                        // allora anche la seguente è candidata
                        // creando non una quadrupla, ma una terna visto che
                        // vi è la seguente ora in comune
                        numOre = 2;
                    }
                }
            }
            else if ((grOra.getSucc() != null) && (grOra.getPrec() == null)) {
                // se "ora" candidata è la prima ora di una catena
                if (!selOra2.vuota()) {
                    // se la selezione non è solitaria "ora" può essere candidata
                    // insieme alla sua ora collegata
                    numOre = 2;
                }
                else {
                    // se la selezione è solitaria
                    OraGraph sucOra = listaOre.get(selOra1.giorno(), selOra1.spazio()+1);
                    if ((sucOra == null) && (selOra1.spazio()+1 <= 6)){
                        // se la seguente a selezione è libera e non
                        // è fuori dal termine delle lezioni
                        // allora tutte e 3 possono essere candidate
                        // e la seconda viene selezionata dal programma
                        numOre = 3;
                    }
                    else if ((sucOra != null)) {
                        // se la seguente a selezione è occupata ma solitaria
                        // allora tutte e 3 possono essere candidate
                        // e la seconda viene selezionata dal programma
                        numOre = 3;
                    }
                    else if ((selOra1.giorno() == ora.giorno()) &&
                        (selOra1.spazio()+1 == ora.spazio()) ) {
                        // se "ora" candidata è la prima di una catena
                        // e si trova appena sotto alla selezione
                        // allora tutte e 3 possono essere candidate
                        // e la seconda viene selezionata dal programma
                        // creando non una quadrupla, ma una terna visto che
                        // vi è un'ora in comune
                        numOre = -3;
                    }
                }
            }
            else if ((grOra.getSucc() == null) && (grOra.getPrec() != null)) {
                // se "ora" candidata è la seconda ora di una catena
                if (!selOra2.vuota()) {
                    // se la selezione non è solitaria
                    if ((selOra2.giorno() == ora.giorno()) &&
                        (selOra2.spazio() == ora.spazio()))
                        // "ora" potrebbe essere la seconda della selezione
                        numOre = 2;
                    else
                        // "ora" come seconda di una catena con selezione
                        // anche lei catena è un caso già analizzato
                        // quando "ora" risulta essere la prima della catena
                        numOre = 0;
                }
                else {
                    // se la selezione è solitaria
                    OraGraph precOra = listaOre.get(selOra1.giorno(), selOra1.spazio()-1);
                    if ((precOra == null) && (selOra1.spazio()-1 >= 1)){
                        // se la precedente a selezione è libera e non
                        // è fuori dal termine delle lezioni
                        // allora tutte e 3 possono essere candidate
                        // e la seconda viene selezionata dal programma
                        numOre = -3;
                    }
                    else if ((precOra != null) &&
                        (precOra.getSucc() == null) && (precOra.getPrec() == null)) {
                        // se la precedente a selezione è occupata ma solitaria
                        // allora tutte e 3 possono essere candidate
                        // e la seconda viene selezionata dal programma
                        numOre = -3;
                    }
                    else if ((selOra1.giorno() == ora.giorno()) &&
                        (selOra1.spazio()-1 == ora.spazio()) ) {
                        // se "ora" candidata è seconda di una catena
                        // e si trova appena sopra alla selezione
                        // allora tutte e 3 possono essere candidate
                        // e la seconda viene selezionata dal programma
                        // creando non una quadrupla, ma una terna visto che
                        // vi è un'ora in comune
                        numOre = -3;
                    }
                }
            }
        }
        else {
            // è un'ora libera
            if (selOra2.vuota()) {
                // se la selezione è solitaria "ora" può essere candidata e
                // la seconda ora da selezionare non esiste
                numOre = 1;
            }
            else {
                // se la selezione non è solitaria
                OraGraph sucOra = listaOre.get(ora.giorno(), ora.spazio()+1);
                if ((sucOra != null) &&
                    (sucOra.getSucc() == null) && (sucOra.getPrec() == null)) {
                    // se la seguente a "ora" candidata è solitaria
                    // allora assieme possono essere candidate
                    numOre = 2;
                }
                else if ((sucOra == null) && (ora.spazio()+1 <= 6)){
                    // se la seguente a "ora" candidata è libera e non
                    // è fuori dal termine delle lezioni
                    // allora assieme possono essere candidate
                    numOre = 2;
                }
            }
        }
        return numOre;
    }

    // si controlla se o1 può essere spostata in (cang1, cans1) senza preoccuparsi
    // del fatto che o1 o (cang1, cans1) facciano parte di una catena, perchè
    // questo è un problema gestito da scambioCorretto
    // assunzione oSorg != null
    private void scambioCorrettoSorgDestDest (OraGraph oSorg, int g, int s) {
        String mess = "";
        ListaOre oreInterm = new ListaOre();
        int numScambi = 0;
        OraGraph oDest = oSorg.classe.listaOre.get(g, s);
        // oSorg è occupata da un docente che ha l'ora (g,s) occupata

        if (oSorg.docente == null) {
            mess = "scambioCorrettoSorgDestDest: ";
            mess += "manda docente in "+GestOrarioApplet.giorno2Str(oSorg.giorno, true)+" "+oSorg.spazio + " ora.";
            JOptionPane.showMessageDialog(null, mess);
            return;
        }
        Vector oreIntermedie = oSorg.docente.oreLibere;
        // nelle ore libere di oSorg si cerca un docente
        // che ha come ora libera (g,s)
        for (int i=0; i < oreIntermedie.size(); i++) {
            OraLibera o = (OraLibera) oreIntermedie.get(i);
            OraGraph oraIntermedia = oSorg.classe.listaOre.get(o.giorno(), o.spazio());
            // nell'ora libera del docente si recupera l'ora del docente
            // intermedio appartenente alla classe
            if (oraIntermedia != null) {
                SelCorOre selCorTmp = new SelCorOre();
                selCorTmp.setSelected(canOra1, 4, oSorg.classe.listaOre);
                OraInt oraI = new OraInt(selOra1.giorno(), selOra1.spazio());
                selCorTmp.setCurrent(oraI, oSorg.classe.listaOre);
                selCorTmp.scambioCorrettoSorgDest(oraIntermedia, g, s);
                if ((selCorTmp.totErrMess() == 0)) {
                    numScambi++;
                    OraInt oraInterm = new OraInt(oraIntermedia.giorno, oraIntermedia.spazio);
                    listOreInterm1.add(oraInterm);
                    intermOra1.set(oraInterm);
                    mess  = numScambi+": ";
//                    mess += oDest.docente.nome;
//                    if (oDest.getDocCom() != null)
//                        mess += " e "+oDest.docenteCom.nome;
//                    mess += " da "+GestOrarioApplet.giorno2Str(oDest.giorno, true)+" "+oDest.spazio + " ora  ";
//                    mess += "  a "+GestOrarioApplet.giorno2Str(oSorg.giorno, true)+" "+oSorg.spazio + " ora\n";
                    mess += oSorg.docente.nome;
                    mess += " "+GestOrarioApplet.giorno2Str(oSorg.giorno, true)+" "+oSorg.spazio + " ora  ";
                    mess += " -> "+GestOrarioApplet.giorno2Str(oraIntermedia.giorno, true)+" "+oraIntermedia.spazio + " ora  -  ";
                    mess += oraIntermedia.docente.nome;
                    if (oraIntermedia.getDocCom() != null)
                        mess += " e "+oraIntermedia.docenteCom.nome;
                    mess += " "+GestOrarioApplet.giorno2Str(oraIntermedia.giorno, true)+" "+oraIntermedia.spazio + " ora  ";
                    mess += " -> "+GestOrarioApplet.giorno2Str(oDest.giorno, true)+" "+oDest.spazio + "ora.";
                    addSolErr(1, mess);
                }
            }
        }
    }


    // se per uno o entrambi i docenti di oSorg l'ora (g, s)
    // è già occupata da un'ora in
    // un'altra classe non si può fare lo scambio.
    private void oraDestNonDisponibile (OraGraph oSorg, int g, int s) {
            // recupero l'ora di destinazione del docente di oSorg
            Docente d = null;
            d = (Docente) oSorg.getDoc();
            if (d != null) {
                OraGraph oDest = d.listaOre.get(g, s);
                if (oDest != null) {
                    // l'ora di destinazione è occupata
                    if (!oDest.classe.equals(oSorg.classe)) {
                        // se docente non ha dalla stessa classe in quanto
                        // passa da lab a teo o viceversa
                        addMessErr(1,
                                GestOrarioApplet.giorno2Str(g,true)+" alla "+s+" ora "
                                +d.nome+" ha GIA' lezione nella "+oDest.classe.nome);
                        System.out.println("docenteConProblemi "+d.nome+" ass g:"+g+" o:"+s);
                        oDest.docenteConProblemi = d;
                    }
                }
                else {
                    oraBloccataGiornoLibero(d, g, s);
                }
            }
            d = (Docente) oSorg.getDocCom();
            if (d != null) {
                OraGraph oDest = d.listaOre.get(g, s);
                if (oDest != null) {
                    // l'ora di destinazione è occupata
    //                    if (!oDest.classe.equals(oSorg.classe)) {
                    addMessErr(1,
                            GestOrarioApplet.giorno2Str(g,true)+" alla "+s+" ora "
                            +d.nome+" ha GIA' lezione nella "+oDest.classe.nome);
                    System.out.println("docenteConProblemi "+d.nome+" ass g:"+g+" o:"+s);
                    oDest.docenteConProblemi = d;
                }
                else {
                    oraBloccataGiornoLibero(d, g, s);
                }
            }
    }

    // controllo per il docente
    // se l'ora di destinazione è bloccata o è un giorno libero
    private void oraBloccataGiornoLibero(Docente d, int g, int s) {
        if ( (d != null) && (d.getBloccata(g, s) != null) ) {
            addMessErr(1,
                GestOrarioApplet.giorno2Str(g,true)+" "+s+" ora è bloccata per "+d.nome);
        }
        if ( (d != null) && (d.giornoLibero == g) ) {
            addMessErr(1,
                GestOrarioApplet.giorno2Str(g,true)+" è il giorno libero di "+d.nome);
        }
    }

    private void oraDopoFineSCuola(OraGraph oSorg, int g, int s) {
        if ((oSorg != null) && (oSorg.getSucc() != null)
                && (s == GestOrarioApplet.maxNumSpazi)) {
            // oSorg è all'inizio di una catena, ma oDest è l'ultima ora del giorno
            addMessErr(1,
                    "Per effettuare lo scambio servirebbe un'ora dopo l'ultima dell'orario scolastico.");
        }
    }


    // restituisce vero se lo scambio è possibile
    // giorni e spazi partono da 1
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // ASSUNZIONE: non ci possono essere più di 3 ore collegate tra loro
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    // assunzione oSorg != null
    public void scambioCorretto(ListaOre listaOre, GestOrarioApplet window, boolean sceltaUtente) {

        // selOra1G e canOra1G possono essere vuoti
        OraGraph selOra1G = listaOre.get(selOra1.giorno(), selOra1.spazio());
        OraGraph canOra1G = listaOre.get(canOra1.giorno(), canOra1.spazio());
        OraGraph selOra2G = listaOre.get(selOra2.giorno(), selOra2.spazio());
        OraGraph canOra2G = listaOre.get(canOra2.giorno(), canOra2.spazio());

        String s = "scambioCorretto(): caso non considerato";
        s += "curOra "+GestOrarioApplet.giorno2Str(curOra.giorno(),true)+" "+curOra.spazio()+" ora\n";
        s += "numSelUtente "+numSelUtente+"\n";
        s += "\nselOra1 "+GestOrarioApplet.giorno2Str(selOra1.giorno(),true)+" "+selOra1.spazio()+" ora\n";
        if (selOra1G == null)
            s += "selOra1 è un'ora libera\n";
        s += "selOra2 "+GestOrarioApplet.giorno2Str(selOra2.giorno(),true)+" "+selOra2.spazio()+" ora\n";
        if (selOra2G == null)
            s += "selOra2 è un'ora libera\n";
        s += "\ncanOra1 "+GestOrarioApplet.giorno2Str(canOra1.giorno(),true)+" "+canOra1.spazio()+" ora\n";
        if (canOra1G == null)
            s += "canOra1 è un'ora libera\n";
        s += "canOra2 "+GestOrarioApplet.giorno2Str(canOra2.giorno(),true)+" "+canOra2.spazio()+" ora\n";
        if (canOra2G == null)
            s += "canOra2 è un'ora libera\n";

        // non dovrebbe succedere con il click del mouse,
        // ma quando la si chiama nella paintTable può succedere
        if (selOra1G == null)
            return;

        // per il controllo si deve utilizzare gli ogetti OreInt e non quelli OreGraph
        // perchè per le ore libere gli oggetti selOra2G, canOra2G sono a null
        if ( selOra2.vuota()
          && canOra2.vuota() ) {
            // caso: 2 ore solitarie
            //      (selOra1G)
            //
            //      (canOra1)
            scambioCorrettoSorgDest(selOra1G, canOra1.giorno(), canOra1.spazio());
            if (totErrMess == 0) {
                if (canOra1G != null) {
                    // canOra1 è un'ora occupata
                    //      (selOra1G)
                    //
                    //      (canOra1G)
                    scambioCorrettoSorgDest(canOra1G, selOra1.giorno(), selOra1.spazio());
                    if (totErrMess > 0) {
                        SelCorOre selCorTmp = new SelCorOre();
                        selCorTmp.setSelected(canOra1, 4, listaOre);
                        OraInt oraI = new OraInt(selOra1.giorno(), selOra1.spazio());
                        selCorTmp.setCurrent(oraI, listaOre);
                        selCorTmp.scambioCorrettoSorgDestDest(canOra1G, selOra1.giorno(), selOra1.spazio());
                        if (selCorTmp.totSolMess() > 0) {
                            Frame frameApplet = (Frame)SwingUtilities.getAncestorOfClass(Frame.class, window);
                            totErrMess = selCorTmp.totErrMess();
                            errorMess = (Vector) selCorTmp.errorMess.clone();
                            totSolMess = selCorTmp.totSolMess();
                            solutionMess = (Vector) selCorTmp.solutionMess.clone();
                            if (sceltaUtente == true) {
                                listOreInterm1 = (Vector) selCorTmp.listOreInterm1.clone();
                                listOreInterm2 = (Vector) selCorTmp.listOreInterm2.clone();

                                ListJDialog listDialog = new ListJDialog(frameApplet, true);

                                listDialog.setList(solutionMess);
                                listDialog.setSelCorOre(this);
                                listDialog.setGestApplet(window);
                                Library.positionDialogRelativeToParent(listDialog,
                                                    0.60,
                                                    0.70);
                                listDialog.setVisible(true);
                                System.out.println("selezione = "+listDialog.sel);
//                                intermOra1.set((OraInt) selCorTmp.listOreInterm1.get(listDialog.sel));
                            }
                        }
                    }
                }
                // else
                // se canOra1 è libera non c'è nulla da controllare
            }
        }
        else if ( !selOra2.vuota() ) {
            // caso: coppia selezionata di ore sovrapposte
            //      (selOra1)
            //      (selOra2)
            //
            if ( (selOra2G != null)
              && (selOra2.giorno() == canOra1.giorno())
              && (selOra2.spazio() == canOra1.spazio()) ) {
                    // caso: coppia da spostare in giù
                    //      (selOra1G)
                    //      (selOra2G)      (canOra1G)
                    //                      (canOra2)
                scambioCorrettoSorgDest(selOra1G, canOra2.giorno(), canOra2.spazio());
                if (totErrMess == 0)
                    if (canOra2G != null) 
                        scambioCorrettoSorgDest(canOra2G, selOra1.giorno(), selOra1.spazio());
            }
            else if ( (selOra1.giorno() == canOra2.giorno())
                   && (selOra1.spazio() == canOra2.spazio()) ) {
                    // caso: coppia da spostare in sù
                    //                      (canOra1)
                    //      (selOra1G)      (canOra2G)
                    //      (selOra2)
                if (selOra2G != null)
                        scambioCorrettoSorgDest(selOra2G, canOra1.giorno(), canOra1.spazio());
                if (totErrMess == 0)
                    if (canOra1G != null) 
                        scambioCorrettoSorgDest(canOra1G, selOra2.giorno(), selOra2.spazio());
            }
            else if ( (selOra1G.succ != null)
                   && (selOra1G.succ.giorno == selOra2.giorno())
                   && (selOra1G.succ.spazio == selOra2.spazio())
                   && (canOra1G != null)
                   && (canOra1G.succ != null)
                   && (canOra1G.succ.giorno == canOra2.giorno())
                   && (canOra1G.succ.spazio == canOra2.spazio()) ) {
                // caso: entrambe coppie di ore collegate
                // che quindi non posso invertire
                //      (selOra1G)
                //      (selOra2G)
                //
                //      (canOra1)
                //      (canOra2)

                // unico tentativo
                // selOra1G -> canOra1
                // selOra2G -> canOra2
                // canOra1 -> selOra1
                // canOra2 -> selOra2
                scambioCorrettoSorgDest(selOra1G, canOra1.giorno(), canOra1.spazio());
                if (totErrMess == 0) {
                    if (selOra2G != null) {
                        scambioCorrettoSorgDest(selOra2G, canOra2.giorno(), canOra2.spazio());
                    }
                    if (totErrMess == 0) {
                        if (canOra1G != null) 
                            scambioCorrettoSorgDest(canOra1G, selOra1.giorno(), selOra1.spazio());
                        if (totErrMess == 0) {
                            if (canOra2G != null) 
                                scambioCorrettoSorgDest(canOra2G, selOra2.giorno(), selOra2.spazio());
                        }
                    }
                }                
            }
            else if ( (selOra1G.succ != null)
                   && (selOra1G.succ.giorno == selOra2.giorno())
                   && (selOra1G.succ.spazio == selOra2.spazio()) ) {
                // caso: selezione fatta di ore collegate
                //       candidate fatte di ore non collegate
                //      (selOra1G)
                //      (selOra2G)
                //
                //      (canOra1)
                //      (canOra2)

                // selOra1G -> canOra1
                // selOra2G -> canOra2
                scambioCorrettoSorgDest(selOra1G, canOra1.giorno(), canOra1.spazio());
                if (totErrMess == 0) {
                    if (selOra2G != null) {
                        scambioCorrettoSorgDest(selOra2G, canOra2.giorno(), canOra2.spazio());
                    }
                    if (totErrMess == 0) {
                        // primo tentativo
                        // canOra1 -> selOra1
                        // canOra2 -> selOra2
                        if (canOra1G != null)
                            scambioCorrettoSorgDest(canOra1G, selOra1.giorno(), selOra1.spazio());
                        if (totErrMess == 0) {
                            if (canOra2G != null)
                                scambioCorrettoSorgDest(canOra2G, selOra2.giorno(), selOra2.spazio());
                        }
                        if (totErrMess != 0) {
                            // secondo tentativo
                            // canOra1 -> selOra2
                            // canOra2 -> selOra1
                            resetTotErrorMess();
                            resetSolutionMess();
                            resetOreInterm();
                            if (canOra1G != null)
                                scambioCorrettoSorgDest(canOra1G, selOra2.giorno(), selOra2.spazio());
                            if (totErrMess == 0) {
                                if (canOra2G != null)
                                    scambioCorrettoSorgDest(canOra2G, selOra1.giorno(), selOra1.spazio());
                            }
                        }
                    }
                }
            }
            else if ( (canOra1G != null)
                   && (canOra1G.succ != null)
                   && (canOra1G.succ.giorno == canOra2.giorno())
                   && (canOra1G.succ.spazio == canOra2.spazio()) ) {
                // caso: selezione fatta di ore non collegate
                //       candidate fatte di ore collegate
                //      (selOra1G)
                //      (selOra2G)
                //
                //      (canOra1)
                //      (canOra2)

                // primo tentativo
                // selOra1G -> canOra1
                // selOra2G -> canOra2
                scambioCorrettoSorgDest(selOra1G, canOra1.giorno(), canOra1.spazio());
                if (totErrMess == 0) {
                    if (selOra2G != null)
                        scambioCorrettoSorgDest(selOra2G, canOra2.giorno(), canOra2.spazio());
                }
                if (totErrMess != 0) {
                    // secondo tentativo
                    // selOra1 -> canOra2
                    // selOra2 -> canOra1
                    resetTotErrorMess();
                    resetSolutionMess();
                    resetOreInterm();
                    scambioCorrettoSorgDest(selOra1G, canOra2.giorno(), canOra2.spazio());
                    if (totErrMess == 0) {
                        if (selOra2G != null)
                            scambioCorrettoSorgDest(selOra2G, canOra1.giorno(), canOra1.spazio());
                    }
                }
                // canOra1 -> selOra1
                // canOra2 -> selOra2
                if (totErrMess == 0) {
                    if (canOra1G != null)
                        scambioCorrettoSorgDest(canOra1G, selOra1.giorno(), selOra1.spazio());
                    if (totErrMess == 0) {
                        if (canOra2G != null)
                            scambioCorrettoSorgDest(canOra2G, selOra2.giorno(), selOra2.spazio());
                    }
                }
            }
            else if ( (canOra2G != null)
                   && (canOra2G.succ != null)
                   && (canOra2G.succ.giorno == canOra1.giorno())
                   && (canOra2G.succ.spazio == canOra1.spazio()) ) {
                // caso: selezione fatta di ore non collegate
                //       candidate fatte di ore collegate
                //       in particolare è il caso in cui l'ora corrente
                //       è canOra2G e le ore 1 sono sotto e le 2 sono sopra
                //      (selOra2G)
                //      (selOra1G)
                //
                //      (canOra2)
                //      (canOra1)

                // primo tentativo
                // selOra2G -> canOra2
                // selOra1G -> canOra1
                scambioCorrettoSorgDest(selOra2G, canOra2.giorno(), canOra2.spazio());
                if (totErrMess == 0) {
                    if (selOra1G != null)
                        scambioCorrettoSorgDest(selOra1G, canOra1.giorno(), canOra1.spazio());
                }
                if (totErrMess != 0) {
                    // secondo tentativo
                    // selOra2 -> canOra1
                    // selOra1 -> canOra2
                    resetTotErrorMess();
                    resetSolutionMess();
                    resetOreInterm();
                    scambioCorrettoSorgDest(selOra2G, canOra1.giorno(), canOra1.spazio());
                    if (totErrMess == 0) {
                        if (selOra1G != null)
                            scambioCorrettoSorgDest(selOra1G, canOra2.giorno(), canOra2.spazio());
                    }
                }
                // canOra2 -> selOra2
                // canOra1 -> selOra1
                if (totErrMess == 0) {
                    if (canOra2G != null)
                        scambioCorrettoSorgDest(canOra2G, selOra2.giorno(), selOra2.spazio());
                    if (totErrMess == 0) {
                        if (canOra1G != null)
                            scambioCorrettoSorgDest(canOra1G, selOra1.giorno(), selOra1.spazio());
                    }
                }
            }
            else {
    //        JOptionPane.showMessageDialog(null, s);
                addMessErr(99,s);
            }
        }
        else {
    //        JOptionPane.showMessageDialog(null, s);
            addMessErr(99,s);
        }
    }

/*******************************************************************
        // caso (selg1, sels1) -|
        //      (selg1,sels1+1)-|   (cang1, cans1)
        //                    (cang1, cans1+1)

        // quando l'ora destinataria è quella che segue quella selezionata
        // ossia si vuol spostare in giù di un'ora le due ore di lab
        if ( (o1.getPrec() == null) && (o1.getSucc() != null)
          && (selg1 == cang1) && (sels1+1== cans1) ) {
            // caso selezione 2 ore concatenate e corrente la seconda della catena
            // confronto la prima della catena con l'ora appena dopo la catena
//            if (selez) selCor.setCurrentSpazio2(cans1+1);
            return scambioCorrettoRic(o1, selg1, sels1+1, true, totErrMess, selez);
        } else if ( (o1.getPrec() == null) && (o1.getSucc() != null)
          && (selg1 == cang1) && (sels1-1== cans1) ) {
            // caso selezione 2 ore concatenate e corrente appena sopra la catena
            // confronto la seconda della catena con l'ora appena prima
//            if (selez) selCor.setSelezioneSpazio2(sels1+1);
            return scambioCorrettoRic(o1.getSucc(), cang1, cans1, true, totErrMess, selez);
        }
        // quando l'ora selezionata è quella che segue le due correnti
        // ossia si vuol spostare in sù l'ora selezionata e in giù le due ore di lab
        else if ( (o2 != null) && (o2.getPrec() == null) && (o2.getSucc() != null)
          && (selg1 == cang1) && (cans1+2== sels1) ) {
            // caso correnti 2 ore concatenate e selezionata la prima dopo la catena
            // scambio la prima della catena con l'ora selezionata
            return scambioCorrettoRic(o1, cang1, cans1, true, totErrMess, selez);
        } else if ( (o2 != null) && (o2.getPrec() == null) && (o2.getSucc() != null)
          && (selg1 == cang1) && (cans1-1== sels1) ) {
            // caso corrent 2 ore concatenate e selezionata la prima sopra la catena
            // confronto la seconda della catena con l'ora selezionata
//            if (selez) selCor.setSelezioneSpazio2(sels1+1);
            return scambioCorrettoRic(o1.classe.listaOre.get(selg1, sels1+1), cang1, cans1, true, totErrMess, selez);
        }
        else {
            // se siamo qui i casi particolari sono esclusi e si considerano solo
            // i casi normali:

            // se l'ora corrente è in una catena ma non è la prima si trova la prima
            if ( (o2 != null) && (o2.getPrec() != null) ) {
                cans1--;
            }

            // ora le due ore sono:
            // caso 1 - entrambe ore semplici
            // caso 2 - entrambe ore iniziali di catene
            // caso 3 - solo una delle due sta all'inizio della catena
            // quindi si confrontano le due ore e poi se una o entrambe sono catene la seconda
            return scambioCorrettoRic(o1, cang1, cans1, false, totErrMess, selez);
        }

        // se la prima ora di lab in sels1 deve andare in cans1 con cans1 = sels1-1
        // (si sposta sull'ora precedente)
        // se in cans1 c'è non c'è un'ora vuota (altrimenti non c'è problema)
        // se l'ora cans1 non può andare in sels1+1 perchè in cans1 il docente ha
        // un'ora in un'altra classe
        // allora errore

        // non si dovrebbe verificare perchè già considerato in scambioCorretto()

        // in altre parole si controlla la prima con la terza!!!!

        if ( (o1 != null) && (o1.getSucc() != null) && (selg1 == cang1) && (cans1 == sels1-1)
           && (o2 != null)) {
            Docente d = (Docente) o2.getDoc();
            if (d != null) {
                OraGraph o = d.listaOre.get(o1.giorno, sels1+1);
                if ( (o != null) && (!o.classe.equals(o1.classe)) ) {
                    totErrMess = addMessErr(totErrMess, 1,
                            GestOrarioApplet.giorno2Str(o1.giorno,true)+" alla "+(sels1+1)+" ora "
                            +d.nome+" ha già lezIONE nella "+o.classe.nome);
                    System.out.println("docenteConProblemi "+d.nome+" ass g:"+o.giorno+" o:"+(sels1+1));
                    o2.docenteConProblemi = d;
                }
            }
        }

        // questo vale anche se si vuol spostare in basso
        if ( (o1 != null) && (o1.getSucc() != null) && (selg1 == cang1) && (cans1 == sels1+1)
           && (o2 != null)) {
            Docente d = (Docente) o2.getDoc();
            if (d != null) {
                OraGraph o = d.listaOre.get(o1.giorno, sels1-1);
                if ( (o != null) && (!o.classe.equals(o1.classe)) ) {
                    totErrMess = addMessErr(totErrMess, 1,
                            GestOrarioApplet.giorno2Str(o1.giorno,true)+" alla "+(sels1-1)+" ora "
                            +d.nome+" ha già lezione NELla "+o.classe.nome);
                    System.out.println("docenteConProblemi "+d.nome+" ass g:"+o.giorno+" o:"+(sels1-1));
                    o2.docenteConProblemi = d;
                }
            }
        }

        if ( (o1 != null) && (o1.getSucc()!= null) )  {
            if ( !casoParticolare ) {
            // se sels1 ha un successore e cans1 non è immediatamente sopra o sotto
//                if (selez) selCor.setSelezioneSpazio2(o1.spazio+1);
//                if (selez) selCor.setCurrentSpazio2(cans1+1);

                return scambioCorrettoRic(o1.getSucc(), cang1, cans1+1, false, totErrMess, selez);
            }
        }
        else if ( (o2 != null) && (o2.getSucc()!= null) )  {
//                if ( !( ((selg1 == cang1) && (cans1-1 != sels1)) || ((selg1 == cang1) && (cans1+2 == sels1)) ) ) {
            if ( !casoParticolare ) {
            // se cans1 ha un successore e sels1 non è immediatamente sopra
//                if (selez) selCor.setSelezioneSpazioLab(sels1+1);
//                if (selez) selCor.setCurrentSpazio2(o2.spazio+1);
                return scambioCorrettoRic(o1.classe.listaOre.get(selg1, sels1+1), cang1, cans1+1, false, totErrMess, selez);
            }
        }
        // non dovrebbe succedere, ma non si sa mai
        // però occorre controllare il caso particolare in cui si deve
        // controllare l'ora che precede una catena con l'ultima della catena
        if ( (o1 != null) && (o1.getPrec() != null) && (o1.getSucc() == null)
          && (o2 != null) && (o2.getPrec() == null) && (o2.getSucc() != null)
                && !((selg1 == cang1) && (sels1-2 == cans1)) ) {
            // o1 è la seconda della catena, o2 è la prima della catena
            // la catena di o1 e di o2 devono avere un'ora in comune?!?
            totErrMess = addMessErr(totErrMess, 11,
                "Per scambiare ore collegate selezione e corrente dovrrebbero essere allineate.");
        }
        // non dovrebbe succedere, ma non si sa mai
        if ( (o1 != null) && (o1.getPrec() != null) && (o1.getSucc() == null)
          && (o2 != null) && (o2.getPrec() == null) && (o2.getSucc() != null)
                && !((selg1 == cang1) && (cans1-2 == sels1))  ) {
            totErrMess = addMessErr(totErrMess, 11,
                "Per scambiare ore collegate selezione e corrente dovrrebbero essere allineate");
        }
      ***************************************************************/

        /***
        else if ( ((o1 != null) && (o1.getPrec() != null))
                ||((o2 != null) && (o2.getPrec() != null)) )
            if ( (prec == 0) || (prec == -1) )
                return scambioCorrettoRic(o1.giorno, o1.spazio-1, o2.giorno, o2.spazio-1, -1);
***/


    // si controlla se o1 può essere spostata in (cang1, cans1) senza preoccuparsi
    // del fatto che o1 o (cang1, cans1) facciano parte di una catena, perchè
    // questo è un problema gestito da scambioCorretto
    // assunzione o1 != null
    public void scambioCorrettoSorgDest(OraGraph o1, int g2, int s2) {
        OraGraph o2 = o1.classe.listaOre.get(g2, s2);

        if ( (g2 < 1)
          || (s2 < 1)
          || (g2 > GestOrarioApplet.maxNumGiorni)
          || (s2 > GestOrarioApplet.maxNumSpazi) ) {
            addMessErr(1,
                    "Per effettuare lo scambio servirebbe un'ora ("+g2+", "+s2+") al di fuori dell'orario scolastico.");
            return;
        }

        // due ore uguali si possono scambiare
        if (o1.equals(o2)) {
            return;
        }

        // due ore dello stesso docente/docenti nella stessa classe
        // si possono scambiare
        if (o2 != null) {
            Docente dT1 = (Docente) o1.getDoc();
            Docente dT2 = (Docente) o2.getDoc();
            Docente dL1 = (Docente) o1.getDocCom();
            Docente dL2 = (Docente) o2.getDocCom();
            if ( (dT1 == dT2) && (dL1 == dL2) && (o1.classe.equals(o2.classe))) {
                return;
            }
        }

        // se per il docente teorico e quello in compresesenza di o1
        // l'ora (cang1, cans1) è bloccata o giorno libero
        // o già occupata da un'ora in
        // un'altra classe non si può fare lo scambio.
        oraDestNonDisponibile(o1, g2, s2);

        // se (cang1, cans1) nell'aula è occupata da un'altra classe
        // non si può effettuare lo scambio
        // si deve controllare che l'ora di arrivo nell'aula uguale a quella di
        // partenza sia libera o occupata dalla stessa classe
        OraGraph o = o1.aula.listaOre.get(g2, s2);
        if ( (o != null)
          && !(o1.classe.nome.equalsIgnoreCase(o.classe.nome))) {
                // l'aula di arrivo è quella della classe che occupa l'ora di
                // partenza e quindi vuol dire che stiamo spostando l'ora di
                // lab in un'ora occupata da un'ora di teoria
                addMessErr(1,
                      GestOrarioApplet.giorno2Str(g2,true)+" alla "+s2+" ora, l'aula "
                    +o1.aula.nome+ " è occupata dalla "+o.classe.nome);
                return;
        }
    }


    public void resetAll(){
        curOra.reSet();
        numSelUtente = 0;
        selOra1.reSet();
        selOra2.reSet();

        canOra1.reSet();
        canOra2.reSet();

        intermOra1.reSet();
        intermOra2.reSet();
        listOreInterm1.clear();
        listOreInterm2.clear();
    }

    public void resetCurrent(int statoRicerca){
        curOra.reSet();
        if ( (statoRicerca == 7)
          || (statoRicerca == 8) ) {
                resetCandidated();
            }

    }

    public void resetSelected1(){
        selOra1.reSet();
        selOra2.reSet();
    }

    public void resetSelected2(){
        selOra2.reSet();
    }

    public void resetCandidated(){
        canOra1.reSet();
        canOra2.reSet();
    }

    public void resetCandidated2(){
        canOra2.reSet();
    }

    public void resetOreInterm(){
        intermOra1.reSet();
        intermOra2.reSet();
        listOreInterm1.clear();
        listOreInterm2.clear();
    }

    public OraInt curOra(){
        return curOra;
    }

    public int curGiorno(){
        return curOra.giorno();
    }

    public int curSpazio(){
        return curOra.spazio();
    }

    public OraInt selOra1(){
        return selOra1;
    }

    public int sel1Giorno(){
        return selOra1.giorno();
    }

    public int sel1Spazio(){
        return selOra1.spazio();
    }

    public OraInt selOra2(){
        return selOra2;
    }

    public int sel2Giorno(){
        return selOra2.giorno();
    }

    public int sel2Spazio(){
        return selOra2.spazio();
    }

    public int can1Giorno(){
        return canOra1.giorno();
    }

    public int can1Spazio(){
        return canOra1.spazio();
    }

    public int can2Giorno(){
        return canOra2.giorno();
    }

    public int can2Spazio(){
        return canOra2.spazio();
    }

    public int interm1Giorno(){
        return intermOra1.giorno();
    }

    public int interm1Spazio(){
        return intermOra1.spazio();
    }

    public int interm2Giorno(){
        return intermOra2.giorno();
    }

    public int interm2Spazio(){
        return intermOra2.spazio();
    }

    public void setIntermOra1(OraInt o){
        intermOra1 = o;
    }

    public void setIntermOra2(OraInt o){
        intermOra2 = o;
    }

    public int totErrMess(){
        return totErrMess;
    }

    public void resetErrorMess() {
        errorMess.clear();
        totErrMess = 0;
    }

    public void resetTotErrorMess() {
        totErrMess = 0;
    }

    public int totSolMess(){
        return totSolMess;
    }

    public void resetSolutionMess() {
        solutionMess.clear();
        totSolMess = 0;
    }

    public Vector getVectErrorMess() {
        return errorMess;
    }

    public String [] getErrorMess() {
        String[] arrayStringhe = new String[errorMess.size()];
        for(int i = 0; i < errorMess.size(); i++){
            arrayStringhe[i] = (String) errorMess.get(i);
        }
        return arrayStringhe;
    }

    private void addSolErr(int numMess, String mess) {
        solutionMess.add(mess);
        totSolMess += numMess;
    }

    private void addMessErr(int numMess, String mess) {
//        errorMess.ensureCapacity(MAX_NUM_MESS);
//        errorMess.removeElementAt(numMess);
//        errorMess.insertElementAt(mess, numMess);
        errorMess.add(mess);
        totErrMess += numMess;
//        return totErrMess+ (int) Math.pow(2, numMess);
/*****

        switch (numMess) {
            case 1:
                return totErrMess+1;
            case 2:
                return totErrMess+2;
            case 3:
                return totErrMess+4;
            case 4:
                return totErrMess+8;
            case 5:
                return totErrMess+16;
            case 6:
                return totErrMess+32;
            case 7:
                return totErrMess+64;
            case 8:
                return totErrMess+128;
            case 9:
                return totErrMess+256;
            default:
                return totErrMess;
        }
*/
    }

    public String totNumToMessErr() {
        String mess = "("+totErrMess+" problemi)\n";
        for (int i=0; i<errorMess.size(); i++) {
                mess += (String) errorMess.get(i) + "\n";
        }
        return mess;
    }

    public String totNumToMessSol() {
        String mess = "("+totSolMess+") ";
        for (int i=0; i<solutionMess.size(); i++) {
                mess += (String) solutionMess.get(i) + "\n";
        }

        /******
        if (num%2 != 0) {
            // messsaggio numero 1
            mess += (String) errorMess.get(1) + "\n";
        }

        // messaggi dal 2 in avanti
        int pot = 1;
        for (int i=1; i<23; i++) {
            pot = pot * 2;
            if ((num/pot)%2 != 0) {
                // messsaggio numero i
                mess += (String) errorMess.get(i) + "\n";
            }
        }
*/
/****

        if ((num/4)%2 != 0) {
            // messsaggio numero 3
            mess += (String) errorMess.get(2);
        }
        if ((num/8)%2 != 0) {
            // messsaggio numero 4
            mess += (String) errorMess.get(3);
        }
        if ((num/16)%2 != 0) {
            // messsaggio numero 5
            mess += (String) errorMess.get(4);
        }
        if ((num/32)%2 != 0) {
            // messsaggio numero 6
            mess += (String) errorMess.get(5);
        }
 */
        return mess;
    }

}

