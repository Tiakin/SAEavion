package application.ui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * La classe Choix.
 * 
 * @author Killian
 */
public class Choix extends JFileChooser {

	/**
	 * Instancie un nouveau choix.
	 *
	 * @param owner La fenêtre principale
	 * @param repertoire choisir un répertoire
	 * 
	 */
	public Choix(MenuPrincipal owner, boolean repertoire) {
		
		setDialogTitle("Choix...");
		File file = new File(System.getProperty("user.home")+"/Downloads/Data Test/Data Test/");
		if (file.exists()) {
			setCurrentDirectory(new File(System.getProperty("user.home")+"/Downloads/Data Test/Data Test/"));
		} else {
			setCurrentDirectory(new File(System.getProperty("user.home")+"/Downloads/"));
		}
		
		if(repertoire) {
			setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			
		} else {
			setFileFilter(new FileFilter() {
	
				   public String getDescription() {
				       return "*.csv, *.txt";
				   }
	
				   public boolean accept(File f) {
				       if (f.isDirectory()) {
				           return true;
				       } else {
				           String filename = f.getName().toLowerCase();
				           return filename.endsWith(".csv") || filename.endsWith(".txt") ;
				       }
				   }
				});
			
			setAcceptAllFileFilterUsed(false);
		}
	}
}