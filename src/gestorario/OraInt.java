/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorario;

/**
 *
 * @author Gio
 */
public class OraInt {
    public int giorno;
    public int spazio;

    OraInt() {
        giorno = -1;
        spazio = -1;
    }

    public boolean uguale(OraInt o) {
        return ( (giorno == o.giorno) && (spazio == o.spazio) );
    }

    public boolean vuota() {
        return ( (giorno == -1) && (spazio == -1) );
    }

    public void set(OraInt o) {
        giorno = o.giorno;
        spazio = o.spazio;
    }

    public void reSet() {
        giorno = -1;
        spazio = -1;
    }
}
