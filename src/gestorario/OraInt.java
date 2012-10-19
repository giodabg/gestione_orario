package gestorario;

/**
 *
 * @author Gio
 */

public class OraInt {
    private int giorno;
    private int spazio;

    OraInt() {
        giorno = -1;
        spazio = -1;
    }

    OraInt(int g, int s) {
        giorno = g;
        spazio = s;
    }

    public int giorno(){
        return giorno;
    }

    public int spazio(){
        return spazio;
    }

    public boolean uguale(OraInt o) {
        return ( (giorno == o.giorno) && (spazio == o.spazio) );
    }

    public boolean uguale(int g, int s) {
        return ( (giorno == g) && (spazio == s) );
    }

    public boolean vuota() {
        return ( (giorno == -1) && (spazio == -1) );
    }

    public void set(OraInt o) {
        giorno = o.giorno;
        spazio = o.spazio;
    }

    public boolean set(int g, int s) {
        if ( (g >= 0) && (s >= 0) ) {
            giorno = g;
            spazio = s;
            return true;
        }
        else
            return false;
    }

    public void reSet() {
        giorno = -1;
        spazio = -1;
    }
}
