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
    int curGiorno;  // giorno corrente (mouse over)
    int curSpazio;  // spazio corrente (mouse over)

    int selGiorno;  // giorno selezionato
    int selSpazio;  // spazio selezionato
    int selSpazioLab; // spazio seconda ora lab selezionato
//    int selSpazio2;
//    int numSpazSel;       // numero spazi da scambiare
//    int curSpazio2;
//    int curSpazio1;
//    int numSpazCur;
    int canGiorno;  // giorno candidato
    int canSpazio;  // spazio candidato
    int canSpazioLab; // spazio seconda ora lab candidato
//    int canSpazio2;  // ora candidata
//    int numSpazCan;


    SelCorOre () {
        selGiorno  = -1;
        selSpazio  = -1;
        selSpazioLab = -1;
//        selSpazio2 = -1;
//        numSpazSel = 0;
        curGiorno  = -1;
        curSpazio  = -1;
//        curSpazioLab = -1;  // ora candidata
//        curSpazio1 = -1;
//        curSpazio2 = -1;
//        numSpazCur = 0;
        canGiorno  = -1;  // giorno candidato
        canSpazio  = -1;  // ora candidata
        canSpazioLab = -1;  // ora candidata
//        canSpazio2 = -1;  // ora candidata
//        numSpazCan = 0;
    }

    /***
    public void setCurrentSpazioLab(int spazio) {
        curSpazio1 = spazio;
    }
    public void setCurrentSpazio2(int spazio) {
        curSpazio2 = spazio;
    }
    public void setCandidatoSpazio2(int spazio) {
        canSpazio2 = spazio;
    }
    public void setSelezioneSpazio2(int spazio) {
        selSpazio2 = spazio;
    }
***/
    public void setCandidatoSpazioLab(int spazio) {
        canSpazioLab = spazio;
    }

    public void setSelezioneSpazioLab(int spazio) {
        selSpazioLab = spazio;
    }

    public boolean setCurrent(int giorno, int spazio, ListaOre listaOre) {
        // non si può impostare l'ora corrente se è solitaria,
        // la selezione è una catena e non segue l'ora corrente
        boolean change = false;
        GraphOra o = listaOre.get(giorno, spazio);
        GraphOra os = listaOre.get(giorno, spazio+1);
        if (!( esisteSelezione()
//           && (selSpazioLab != -1) && (selSpazio2 != -1)
           && (selSpazioLab != -1)
           && (o != null) && (o.getSucc() == null) && (o.getPrec() == null)
           && (os != null) && (os.getSucc() != null)
           && (giorno != selGiorno) && (spazio+1 != selSpazio) )) {
                curGiorno = giorno;
                curSpazio  = spazio;
//                curSpazio1 = spazio;
//                curSpazio2 = -1;
                change = true;
        }
//        if ( (idTable == 1) || (idTable == 3) ) {
                // il numero di ore correnti viene deciso nella tabella del docente o della classe
//                numSpazCur = 1;
//                GraphOra ora = listaOre.get(giorno, spazio);
//                if ((ora != null) && (ora.getSucc() != null)) {
//                    curSpazioCol = spazio+1;
/***
                    do {
                        numSpazCur++;
                        spazio++;
                        ora = ora.getSucc();
                    } while ( (ora != null) && (ora.getSucc() != null) );
***/
//                }
//                else if ((ora != null) && (ora.getPrec() != null)) {
//                    curSpazioCol = spazio-1;
/***

                    do {
                        numSpazCur++;
                        spazio--;
                        ora = ora.getPrec();
                    } while ( (ora != null) && (ora.getPrec() != null) );
***/
//                }
//        }
        return change;
/***
        if ( (succTableRig != null) && (succTableRig.listaOre != null) )
            succTableRig.setCurrent(giorno, spazio);
        if ( (succTableCol != null)  && (succTableCol.listaOre != null) )
            succTableCol.setCurrent(giorno, spazio);
*/
    }

    public void resetCurrent(){
        curGiorno  = -1;
        curSpazio  = -1;
//        curSpazio1 = -1;
//        curSpazio2 = -1;
//        numSpazCur = 0;
/***
        if (succTableRig != null)
            succTableRig.resetCurrent();
        if (succTableCol != null)
            succTableCol.resetCurrent();
*/
    }

    public boolean esisteCurrent(ListaOre listaOre) {
        if ((curGiorno == -1) || (curSpazio == -1) )
            return false;
        else if (listaOre.get(curGiorno, curSpazio) == null)
            return false;
        else
            return true;
    }

    public void setCandidato(int giorno, int spazio, ListaOre listaOre) {
        canGiorno  = giorno;
        canSpazio  = spazio;
//        numSpazCan = 1;
        GraphOra ora = listaOre.get(giorno, spazio);
        if ((ora != null) && (ora.getSucc() != null)) {
            canSpazioLab = spazio+1;
//                    canSpazio2 = spazio+1;
/***
                    do {
                        numSpazCan++;
                        spazio++;
                        ora = ora.getSucc();
                    } while ( (ora != null) && (ora.getSucc() != null) );
***/
        }
        else if ((ora != null) && (ora.getPrec() != null)) {
            canSpazioLab = spazio-1;
//                     canSpazio2 = spazio-1;
/***

                    do {
                        numSpazCan++;
                        spazio--;
                        ora = ora.getPrec();
                    } while ( (ora != null) && (ora.getPrec() != null) );
***/
        }
//        }
/*
        while ( ((ora = listaOre.get(giorno, spazio)) != null) && (ora.getSucc() != null) ) {
        numSpazCan++;
        spazio++;
        }
 */
/***
        if ( (succTableRig != null) && (succTableRig.listaOre != null) )
            succTableRig.setCandidato(giorno, spazio);
        if ( (succTableCol != null)  && (succTableCol.listaOre != null) )
            succTableCol.setCandidato(giorno, spazio);
*/
    }

    public void resetCandidato(){
        canGiorno  = -1;
        canSpazio  = -1;
        canSpazioLab = -1;
//        canSpazio2 = -1;
//        numSpazCan = 0;
/***
        if (succTableRig != null)
            succTableRig.resetCandidato();
        if (succTableCol != null)
            succTableCol.resetCandidato();
*/
    }
    public boolean esisteCandidato() {
        if ((canGiorno == -1) || (canSpazio == -1) )
            return false;
        else
            return true;
    }

    public void setSelezione(int giorno, int spazio) {
//        if ( (idTable == 1) || (idTable == 3) ) {
            // il numero di ore selezionate viene deciso nella tabella del docente o della classe
            selGiorno  = giorno;
            selSpazio  = spazio;
//            setSelezioneSpazioLab(spazio);

//            numSpazSel = 1;
//            GraphOra ora = listaOre.get(giorno, spazio);
//            if ( (ora != null) && (ora.getSucc() != null) ) {
//                selSpazioCol = spazio + 1;
//                numSpazSel++;
//                spazio++;
//            } else if ( (ora != null) && (ora.getPrec() != null) ) {
//                selSpazioCol = spazio - 1;
//                numSpazSel++;
//                spazio--;
//            }
//        }
/***
        if ( (succTableRig != null) && (succTableRig.listaOre != null) )
            succTableRig.setSelezione(giorno, spazio);
        if ( (succTableCol != null)  && (succTableCol.listaOre != null) )
            succTableCol.setSelezione(giorno, spazio);
*/
    }

    public void resetSelezione(){
        selGiorno  = -1;
        selSpazio  = -1;
//        setSelezioneSpazioLab(-1);
//        selSpazio2 = -1;
//        numSpazSel = 0;
/***
        if (succTableRig != null)
            succTableRig.resetSelezione();
        if (succTableCol != null)
            succTableCol.resetSelezione();
**/
    }
    public boolean esisteSelezione() {
        if ((selGiorno == -1) || (selSpazio == -1) )
            return false;
        else
            return true;
    }
}
