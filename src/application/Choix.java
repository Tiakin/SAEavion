package application;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class Choix extends JFileChooser {

	public Choix(MenuPrincipal owner, boolean repertoire) {
		
		setDialogTitle("Choix...");
		
		if(repertoire) {
			setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			
		} else {
			setFileFilter(new FileFilter() {
	
				   public String getDescription() {
				       return "*.csv, *.txt";
				   }
	
				   public boolean accept(File f) {
				       if (f.isDirectory()) {
				           return false;
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