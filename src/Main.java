import java.awt.Dimension;

import javax.swing.JFrame;

public class Main {

	public final static Dimension size = new Dimension(1000, 1000); //Size of window

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Set up main window (using Swing's Jframe)
				JFrame frame = new JFrame("Snake");
				SnakeDrive s = new SnakeDrive();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.add(s);
				frame.setResizable(false);
				frame.pack();
				frame.setVisible(true);
			}
		});
	}

}