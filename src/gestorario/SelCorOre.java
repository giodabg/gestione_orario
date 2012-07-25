package gestorario;

/**
 *
 * @author Gio
 */

public class SelCorOre {
    OraInt curOra;        // ora corrente (mouse over)

    // si assume che le catene siano lunghe al massimo 2

    int    numSelUtente;  // numero ore selezionate dall'utente
    OraInt selOra1;       // giorno selezionato prima ora
    OraInt selOra2;       // giorno selezionato seconda ora

    OraInt canOra1Sorg;   // giorno candidato prima ora
    OraInt canOra2Sorg;   // giorno candidato seconda ora

    OraInt canOra1Dest;   // giorno candidato prima ora
    OraInt canOra2Dest;   // giorno candidato seconda ora

//    static OraGraph canOra2SorgVuota;
//    static OraGraph canOra2DestVuota;


    SelCorOre () {
        curOra = new OraInt();
        numSelUtente = 0;
        selOra1 = new OraInt();
        selOra2 = new OraInt();

        canOra1Sorg = new OraInt();
        canOra2Sorg = new OraInt();

        canOra1Dest = new OraInt();
        canOra2Dest = new OraInt();
}

    public boolean setCurrent(OraInt ora, int statoRicerca, ListaOre listaOre) {
        // se l'ora corrente è quella selezionata 
        // o è una delle candidate non si setta
        boolean solitaria = true;

        if ((numSelUtente == 1) && (!selOra2.vuota())) {
            // se l'utente ha selezionato una sola ora
            // può darsi che nella ricerca di un'ora candidata
            // il programma abbia selezionato anche una seconda ora
            // per cui va resettata prima di calcolare la nuova
            selOra2.reSet();
        }

        if ( !ora.uguale(selOra1) &&
             //!ora.uguale(selOra2) &&
             !ora.uguale(canOra1Sorg) &&
             //!ora.uguale(canOra2Sorg) &&
             !ora.uguale(canOra1Dest)
             //&& !ora.uguale(canOra2Dest)
             ) {
            if ( (statoRicerca <= 3) && (canBeSelected(ora, listaOre) != 0) )
                curOra.set(ora);
            else if (statoRicerca > 3) {
                boolean set = setCandidatedDest(ora, statoRicerca, listaOre);
                if (set)
                    curOra.set(ora);
                else {
                    curOra.reSet();
                    canOra1Dest.reSet();
                    canOra2Dest.reSet();
                }
            }
            else {
                curOra.reSet();
                canOra1Dest.reSet();
                canOra2Dest.reSet();
            }
            return true;
        }
        else {
            curOra.reSet();
            if (statoRicerca > 3) {
                    canOra1Dest.reSet();
                    canOra2Dest.reSet();
            }
            return false;
        }
    }

    public boolean esisteCurrent() {
        return ( !curOra.vuota() );
    }

    public boolean setSelected(OraInt ora, int statoRicerca, ListaOre listaOre) {
        if (!ora.vuota()) {
            int ok_solitaria = canBeSelected(ora, listaOre);
            if (ok_solitaria != 0) {
                // se ora è quella corrente deve essere resettata per evitare
                // che l'ora sia contemporaneamente corrente e selezionata
                if (ora.uguale(curOra))
                    resetCurrent(statoRicerca);

                selOra1.set(ora);
                if (ok_solitaria == 1) {
                    // si memorizza la scelta dell'utente
                    numSelUtente = 1;
                    selOra2.reSet();
                }
                else {
                    // si memorizza la scelta dell'utente
                    numSelUtente = 2;
                    // se è la prima di una catena si seleziona la seconda
                    // assumendo che le catene siano lunghe al massimo 2
                    selOra2.giorno = ora.giorno;
                    selOra2.spazio = ora.spazio + 1;
                }

                return true;
            }
        }
        return false;
    }

    public boolean esisteSelected() {
        return (!selOra1.vuota());
    }

    //  questa versione non funziona perchè solitaria non viene modificata
    //  in pratica parametri in uscita non ci sono in Java
    //  public boolean canBeSelected(OraInt ora, boolean solitaria, ListaOre listaOre) {
    public int canBeSelected(OraInt ora, ListaOre listaOre) {
        // si può impostare l'ora selezionata come corrente se
        // è solitaria o se 
        // la selezione è la prima di una catena
        int check = 0;
        OraGraph grOra = listaOre.get(ora.giorno, ora.spazio);
        // potrebbe essere un'ora dell'orario non utilizzata per nulla
        if (grOra != null) {
            // se è solitaria la seconda ora da selezionare non esiste
            if ((grOra.getSucc() == null) && (grOra.getPrec() == null)) {
                check = 1;
            }
            else if ((grOra.getSucc() != null) && (grOra.getPrec() == null)) {
                check = 2;
            }
        }
        return check;
    }


    public boolean setCandidatedDest(OraInt ora, int statoRicerca, ListaOre listaOre) {
        if (!ora.vuota()) {
            int ok_solitaria = canBeCandidatedDest(ora, listaOre);
            if (ok_solitaria != 0) {
                canOra1Dest.set(ora);
                if (ok_solitaria == 1)
                    canOra2Dest.reSet();
                else if (ok_solitaria == 2) {
                // selezione e candidate sono prime di una catena
                // si seleziona la seconda
                // assumendo che le catene siano lunghe al massimo 2
                    canOra2Dest.giorno = ora.giorno;
                    canOra2Dest.spazio = ora.spazio + 1;
                }
                else if (ok_solitaria == 3) {
                // selezione è solitaria, ma seguita da solitara
                // e candidata è la prima di una catena
                // il programma seleziona anche quella che segue selezione
                    selOra2.giorno = selOra1.giorno;
                    selOra2.spazio = selOra1.spazio + 1;
                    canOra2Dest.giorno = ora.giorno;
                    canOra2Dest.spazio = ora.spazio + 1;
                }
                else if (ok_solitaria == -3) {
                // selezione è solitaria, ma preceduta da solitara
                // e candidata è la prima di una catena
                // il programma seleziona anche quella che precede selezione
                    selOra2.giorno = selOra1.giorno;
                    selOra2.spazio = selOra1.spazio - 1;
                    canOra2Dest.giorno = ora.giorno;
                    canOra2Dest.spazio = ora.spazio - 1;
                }
                return true;
            }
        }
        return false;
    }
    
    public int canBeCandidatedDest(OraInt ora, ListaOre listaOre) {
        int check = 0;
        OraGraph grOra = listaOre.get(ora.giorno, ora.spazio);
        if (grOra != null) {
            // "ora" candidata è un'ora occupata
            if ((grOra.getSucc() == null) && (grOra.getPrec() == null)) {
                // se "ora" candidata è solitaria
                if (selOra2.vuota()) {
                    // se la selezione è solitaria "ora" può essere candidata e
                    // la seconda ora da selezionare non esiste
                    check = 1;
                }
                else {
                    // se la selezione non è solitaria
                    OraGraph sucOra = listaOre.get(ora.giorno, ora.spazio+1);
                    if ((sucOra != null) &&
                        (sucOra.getSucc() == null) && (sucOra.getPrec() == null)) {
                        // se la seguente a "ora" candidata è solitaria
                        // allora assieme possono essere candidate
                        check = 2;
                    }
                    else if ((sucOra == null) && (ora.spazio+1 <= 6)){
                        // se la seguente a "ora" candidata è libera e non
                        // è fuori dal termine delle lezioni
                        // allora assieme possono essere candidate
                        check = 2;
                    }
                    else if ((selOra1.giorno == ora.giorno) &&
                        (selOra1.spazio == ora.spazio+1) ) {
                        // se "ora" candidata
                        // si trova appena sopra alla selezione
                        // allora anche la seguente è candidata
                        // creando non una quadrupla, ma una terna visto che
                        // vi è la seguente ora in comune
                        check = 2;
                    }
                }
            }
            else if ((grOra.getSucc() != null) && (grOra.getPrec() == null)) {
                // se "ora" candidata è la prima ora di una catena
                if (!selOra2.vuota()) {
                    // se la selezione non è solitaria "ora" può essere candidata
                    // insieme alla sua ora collegata
                    check = 2;
                }
                else {
                    // se la selezione è solitaria
                    OraGraph sucOra = listaOre.get(selOra1.giorno, selOra1.spazio+1);
                    if ((sucOra == null) && (selOra1.spazio+1 <= 6)){
                        // se la seguente a selezione è libera e non
                        // è fuori dal termine delle lezioni
                        // allora tutte e 3 possono essere candidate
                        // e la seconda viene selezionata dal programma
                        check = 3;
                    }
                    else if ((sucOra != null)) {
                        // se la seguente a selezione è occupata ma solitaria
                        // allora tutte e 3 possono essere candidate
                        // e la seconda viene selezionata dal programma
                        check = 3;
                    }
                    else if ((selOra1.giorno == ora.giorno) &&
                        (selOra1.spazio+1 == ora.spazio) ) {
                        // se "ora" candidata è la prima di una catena
                        // e si trova appena sotto alla selezione
                        // allora tutte e 3 possono essere candidate
                        // e la seconda viene selezionata dal programma
                        // creando non una quadrupla, ma una terna visto che
                        // vi è un'ora in comune
                        check = -3;
                    }
                }
            }
            else if ((grOra.getSucc() == null) && (grOra.getPrec() != null)) {
                // se "ora" candidata è la seconda ora di una catena
                if (!selOra2.vuota()) {
                    // se la selezione non è solitaria
                    if ((selOra2.giorno == ora.giorno) &&
                        (selOra2.spazio == ora.spazio))
                        // "ora" potrebbe essere la seconda della selezione
                        check = 2;
                    else
                        // "ora" come seconda di una catena con selezione
                        // anche lei catena è un caso già analizzato
                        // quando "ora" risulta essere la prima della catena
                        check = 0;
                }
                else {
                    // se la selezione è solitaria
                    OraGraph precOra = listaOre.get(selOra1.giorno, selOra1.spazio-1);
                    if ((precOra == null) && (selOra1.spazio-1 >= 0)){
                        // se la precedente a selezione è libera e non
                        // è fuori dal termine delle lezioni
                        // allora tutte e 3 possono essere candidate
                        // e la seconda viene selezionata dal programma
                        check = -3;
                    }
                    else if ((precOra != null) &&
                        (precOra.getSucc() == null) && (precOra.getPrec() == null)) {
                        // se la precedente a selezione è occupata ma solitaria
                        // allora tutte e 3 possono essere candidate
                        // e la seconda viene selezionata dal programma
                        check = -3;
                    }
                    else if ((selOra1.giorno == ora.giorno) &&
                        (selOra1.spazio-1 == ora.spazio) ) {
                        // se "ora" candidata è seconda di una catena
                        // e si trova appena sopra alla selezione
                        // allora tutte e 3 possono essere candidate
                        // e la seconda viene selezionata dal programma
                        // creando non una quadrupla, ma una terna visto che
                        // vi è un'ora in comune
                        check = -3;
                    }
                }
            }
        }
        else {
            // è un'ora libera
            if (selOra2.vuota()) {
                // se la selezione è solitaria "ora" può essere candidata e
                // la seconda ora da selezionare non esiste
                check = 1;
            }
            else {
                // se la selezione non è solitaria
                OraGraph sucOra = listaOre.get(ora.giorno, ora.spazio+1);
                if ((sucOra != null) &&
                    (sucOra.getSucc() == null) && (sucOra.getPrec() == null)) {
                    // se la seguente a "ora" candidata è solitaria
                    // allora assieme possono essere candidate
                    check = 2;
                }
                else if ((sucOra == null) && (ora.spazio+1 <= 6)){
                    // se la seguente a "ora" candidata è libera e non
                    // è fuori dal termine delle lezioni
                    // allora assieme possono essere candidate
                    check = 2;
                }
            }
        }
        return check;
    }


    public void resetAll(){
        curOra.reSet();
        numSelUtente = 0;
        selOra1.reSet();
        selOra2.reSet();

        canOra1Sorg.reSet();
        canOra2Sorg.reSet();

        canOra1Dest.reSet();
        canOra2Dest.reSet();
    }

    public void resetCurrent(int statoRicerca){
        curOra.reSet();
        if ( (statoRicerca == 7)
          || (statoRicerca == 8) ) {
                resetCandidated1Dest();
                resetCandidated2Dest();
            }

    }

    public void resetSelected1(){
        selOra1.reSet();
    }

    public void resetSelected2(){
        selOra2.reSet();
    }

    public void resetCandidated1Sorg(){
        canOra1Sorg.reSet();
    }

    public void resetCandidated2Sorg(){
        canOra2Sorg.reSet();
    }

    public void resetCandidated1Dest(){
        canOra1Dest.reSet();
    }

    public void resetCandidated2Dest(){
        canOra2Dest.reSet();
    }

}
