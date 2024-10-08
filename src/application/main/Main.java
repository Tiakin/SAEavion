package application.main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import application.ui.MenuPrincipal;

/**
 * La classe Main.
 *
 * @author Killian
 */
public class Main {

	/**
	 * Le constructeur Main.
	 */
	private Main() {
	}

	/**
	 * la méthode main.
	 *
	 * @param args les arguments
	 *
	 */
	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.put("FileChooser.noPlacesBar", true);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		System.setProperty("org.graphstream.ui", "swing");

		MenuPrincipal app = new MenuPrincipal();
		app.setVisible(true);

	}

}
