/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorario;

/**
 *
 * @author Gio
 */
public class SelCorOre {
    GraphOra curOra;        // ora corrente (mouse over)

    GraphOra selOra1;       // giorno selezionato prima ora
    GraphOra selOra2;       // giorno selezionato seconda ora

    GraphOra canOra1Sorg;   // giorno candidato prima ora
    GraphOra canOra2Sorg;   // giorno candidato seconda ora

    GraphOra canOra1Dest;   // giorno candidato prima ora
    GraphOra canOra2Dest;   // giorno candidato seconda ora

    static GraphOra canOra2SorgVuota;
    static GraphOra canOra2DestVuota;


    SelCorOre () {
        curOra           = null;
        selOra1          = null;
        selOra2          = null;
        canOra1Sorg      = null;
        canOra2Sorg      = null;
        canOra1Dest      = null;
        canOra2Dest      = null;
        canOra2SorgVuota = new GraphOra();
        canOra2DestVuota = new GraphOra();
    }

    public boolean setCurrent(GraphOra ora, ListaOre listaOre, int statoRicerca) {
        // se l'ora corrente è quella selezionata o una delle candidate non si setta
        if ((ora != null) && (ora != selOra1) && (ora != selOra2)
                && (ora != canOra1Sorg) && (ora != canOra2Sorg)
                && (ora != canOra1Dest) && (ora != canOra2Dest)) {
            resetCurrent(statoRicerca);
            curOra = ora;
            curOra.setCorrente();
            if ( (statoRicerca == 7) || (statoRicerca == 8) ) {
                setCandidatedDest(ora, listaOre, statoRicerca) ;
            }
            return true;
        }
        else {
            resetCurrent(statoRicerca);
        }

        return false;
    }

    public boolean esisteCurrent() {
        return (curOra != null);
    }

    public boolean setSelected(GraphOra ora, int statoRicerca) {
        if (ora != null) {
            if (canBeSelected(ora)) {
                // se ora è quella corrente deve essere resettata per evitare
                // che l'ora sia contemporaneamente corrente e selezionata
                if (ora == curOra)
                    resetCurrent(statoRicerca);
                resetSelected1();
                selOra1 = ora;
                selOra1.setSelezionata();
                return true;
            }
        }
        return false;
    }

    public boolean esisteSelected() {
        return (selOra1 != null);
    }

    public boolean canBeSelected(GraphOra ora) {
        // non si può impostare l'ora selezionata come corrente se
        // è solitaria o se 
        // la selezione è la prima di una catena
        boolean check = false;
        if (ora != null) {
            if ((ora.getSucc() == null) && (ora.getPrec() == null)) {
                resetSelected2();
                selOra2 = null;
                check = true;
            }
            else if ((ora.getSucc() != null) && (ora.getPrec() == null)) {
                resetSelected2();
                selOra2 = ora.getSucc();
                selOra2.setSelezionata();
                check = true;
            }
        }
        return check;
    }

    public boolean setCandidatedDest(GraphOra ora, ListaOre listaOre, int statoRicerca) {
        if (ora != null) {
            if (canBeCandidatedDest(ora, listaOre)) {
                resetCandidated1Dest();
                canOra1Dest = ora;
                canOra1Dest.setCandidata();
                return true;
            }
        }
        return false;
    }

    public boolean canBeCandidatedDest(GraphOra ora, ListaOre listaOre) {
        boolean check = false;
        if (ora != null) {
        // si può candidare se è solitaria e la selezione è solitaria
            if ((selOra2 == null) && (ora.getSucc() == null) && (ora.getPrec() == null)) {
                resetCandidated2Dest();
                canOra2Dest = null;
                check = true;
            }
            if ((selOra2 == null) && (ora.getSucc() == null) && (ora.getPrec() == null)) {
        // se la selezione è doppia e 
            }
            else if (selOra2 != null) {
        // la candidata è anch'essa la prima ora di una catena
                if ((ora.getSucc() != null) && (ora.getPrec() == null)) {
                    resetCandidated2Dest();
                    canOra2Dest = ora.getSucc();
                    canOra2Dest.setCandidata();
                    check = true;
                }
        // la candidata è anch'essa la seconda ora di una catena
                else if ((ora.getSucc() == null) && (ora.getPrec() != null)) {
                    resetCandidated2Dest();
                    canOra2Dest = ora.getPrec();
                    canOra2Dest.setCandidata();
                    check = true;
                }
        // la candidata è solitaria
                else if ((ora.getSucc() == null) && (ora.getPrec() == null)) {
                    GraphOra sucOra = listaOre.get(ora.giorno, ora.spazio+1);
        // e la sua seguente è solitaria
                    if ((sucOra != null) && (sucOra.getSucc() == null) && (sucOra.getPrec() == null)) {
                        resetCandidated2Dest();
                        canOra2Dest = sucOra;
                        canOra2Dest.setCandidata();
                        check = true;
                    }
                    else if ((sucOra == null) && (ora.spazio < 6)){
        // e la sua seguente non esiste
                        resetCandidated2Dest();
                        canOra2Dest = canOra2DestVuota;
                        canOra2DestVuota.giorno = ora.giorno;
                        canOra2DestVuota.spazio = ora.spazio;
                        canOra2Dest.setCandidata();
                        check = true;
                    }
                }
            }
        }
        return check;
    }

    public void resetAll(){
        if (curOra != null)
            curOra.resetAll();
        curOra = null;
        if (selOra1 != null)
            selOra1.resetAll();
        selOra1 = null;
        if (selOra2 != null)
            selOra2.resetAll();
        selOra2 = null;
        if (canOra1Sorg != null)
            canOra1Sorg.resetAll();
        canOra1Sorg = null;
        if (canOra2Sorg != null)
            canOra2Sorg.resetAll();
        canOra2Sorg = null;
        if (canOra1Dest != null)
            canOra1Dest.resetAll();
        canOra1Dest = null;
        if (canOra2Dest != null)
            canOra2Dest.resetAll();
        canOra2Dest = null;
    }

    public void resetCurrent(int statoRicerca){
        if (curOra != null)
            curOra.resetCorrente();
        curOra = null;
        if ( (statoRicerca == 7)
          || (statoRicerca == 8) ) {
                resetCandidated1Dest();
                resetCandidated2Dest();
            }

    }

    public void resetSelected1(){
        if (selOra1 != null)
            selOra1.resetSelezionata();
        selOra1 = null;
    }

    public void resetSelected2(){
        if (selOra2 != null)
            selOra2.resetSelezionata();
        selOra2 = null;
    }

    public void resetCandidated1Sorg(){
        if (canOra1Sorg != null)
            canOra1Sorg.resetCandidata();
        canOra1Sorg = null;
    }

    public void resetCandidated2Sorg(){
        if (canOra2Sorg != null)
            canOra2Sorg.resetCandidata();
        canOra2Sorg = null;
    }

    public void resetCandidated1Dest(){
        if (canOra1Dest != null)
            canOra1Dest.resetCandidata();
        canOra1Dest = null;
    }

    public void resetCandidated2Dest(){
        if (canOra2Dest != null)
            canOra2Dest.resetCandidata();
        canOra2Dest = null;
    }

}
