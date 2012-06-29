/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorario;

/**
 *
 * @author breviario.giovanni
 */
import java.io.File;
import javax.swing.filechooser.FileFilter;

public class MyFileFilter extends FileFilter {
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
  public boolean accept(File f) {
    if (f.isDirectory())
      return true;
    String s = f.getName();
    int i = s.lastIndexOf('.');

    if (i > 0 && i < s.length() - 1)
      if (s.substring(i + 1).toLowerCase().equals("csv"))
        return true;

    return false;
  }

  public String getDescription() {
    return "Accetta solo csv file.";
  }
}

