package gestorario;

/**
 *
 * @author Gio
 */

public class SelCorOre {
    OraInt curOra;        // ora corrente (mouse over)

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
        selOra1 = new OraInt();
        selOra2 = new OraInt();

        canOra1Sorg = new OraInt();
        canOra2Sorg = new OraInt();

        canOra1Dest = new OraInt();
        canOra2Dest = new OraInt();
}

    public boolean setCurrent(OraInt ora, int statoRicerca) {
        // se l'ora corrente è quella selezionata o una delle candidate non si setta
        if ( !ora.uguale(selOra1) &&
             !ora.uguale(selOra2) &&
             !ora.uguale(canOra1Sorg) &&
             !ora.uguale(canOra2Sorg) &&
             !ora.uguale(canOra1Dest) &&
             !ora.uguale(canOra2Dest) ) {
            resetCurrent(statoRicerca);
            curOra.set(ora);
            /*curOra.setCorrente();
            if ( (statoRicerca == 7) || (statoRicerca == 8) ) {
            setCandidatedDest(ora, listaOre, statoRicerca) ;*/
            return true;
        }
        else {
            resetCurrent(statoRicerca);
        }
        return false;
    }

    public boolean esisteCurrent() {
        return ( !curOra.vuota() );
    }

    public boolean setSelected(OraInt ora, int statoRicerca, ListaOre listaOre) {
        if (!ora.vuota()) {
            if (canBeSelected(ora, listaOre)) {
                // se ora è quella corrente deve essere resettata per evitare
                // che l'ora sia contemporaneamente corrente e selezionata
                if (ora.uguale(curOra))
                    resetCurrent(statoRicerca);

                //resetSelected1();
                selOra1.set(ora);
                //selOra1.setSelezionata();
                return true;
            }
        }
        return false;
    }

    public boolean esisteSelected() {
        return (!selOra1.vuota());
    }

    public boolean canBeSelected(OraInt ora, ListaOre listaOre) {
        // si può impostare l'ora selezionata come corrente se
        // è solitaria o se 
        // la selezione è la prima di una catena
        boolean check = false;
        if (!ora.vuota()) {
            OraGraph grOra = listaOre.get(ora.giorno, ora.spazio);
            // se non ha ore collegate non esiste una seconda ora da selezionare
            if ((grOra.getSucc() == null) && (grOra.getPrec() == null)) {
                //resetSelected2();
                selOra2.reSet();
                check = true;
            }
            else if ((grOra.getSucc() != null) && (grOra.getPrec() == null)) {
                //resetSelected2();
                selOra2.giorno = ora.giorno;
                selOra2.spazio = ora.spazio + 1;
                check = true;
            }
        }
        return check;
    }

    public boolean setCandidatedDest(OraInt ora, int statoRicerca, ListaOre listaOre) {
        if (!ora.vuota()) {
            OraGraph grOra = listaOre.get(ora.giorno, ora.spazio);
            // se la candidata è solitaria si può candidare
            if ((grOra.getSucc() == null) && (grOra.getPrec() == null)) {
            // se la selezione è solitaria
                if (selOra2.vuota()) {
                    canOra1Dest.set(ora);
                    canOra2Dest.reSet();
                    return true;
                }
                else {
            // se la selezione non è solitaria
                    OraGraph sucOra = listaOre.get(ora.giorno, ora.spazio+1);
           // e la seguente alla candidata è solitaria
                    if ((sucOra != null) &&
                        (sucOra.getSucc() == null) && (sucOra.getPrec() == null)) {
                        canOra2Dest.giorno = ora.giorno;
                        canOra2Dest.spazio = ora.spazio + 1;
                        return true;
                    }
                    else if ((sucOra == null) && (ora.spazio < 6)){
        // e la sua seguente non esiste
                        resetCandidated2Dest();
                        canOra2Dest.giorno = ora.giorno;
                        canOra2Dest.spazio = ora.spazio;
                        return true;
                    }
                }
            }
            // se la candidata è la prima ora di una catena
            if ((grOra.getSucc() != null) && (grOra.getPrec() == null)) {
                    canOra1Dest.set(ora);
                    canOra2Dest.giorno = ora.giorno;
                    canOra2Dest.spazio = ora.spazio + 1;
                    return true;
            }
            // la candidata è la seconda ora di una catena
            if ((grOra.getSucc() == null) && (grOra.getPrec() != null)) {
                    canOra1Dest.giorno = ora.giorno;
                    canOra1Dest.spazio = ora.spazio - 1;
                    canOra2Dest.set(ora);
                    return true;
            }
        // la candidata è solitaria
        }
        return false;
    }


    public void resetAll(){
        curOra.reSet();
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
