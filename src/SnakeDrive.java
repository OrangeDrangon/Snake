import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class SnakeDrive extends JPanel {

	Food food = new Food();

	static Snake snake = new Snake((int) Main.size.getWidth() / 2, (int) Main.size.getHeight() / 2); // Creates
																										// the
																										// snake

	boolean first = true; // Checks if first loop through paint method after you
							// lose to add a small delay

	public SnakeDrive() {
		// Sets panel size
		setMinimumSize(Main.size);
		setMaximumSize(Main.size);
		setPreferredSize(Main.size);

		// Adds listeners for buttons and wht to do when that button is pressed
		ActionMap ap = getActionMap();
		InputMap ip = getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);

		ip.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "RIGHT");
		ip.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "LEFT");
		ip.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "UP");
		ip.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "DOWN");
		ip.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "ENTER");
		ip.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "ESC");

		ap.put("RIGHT", new ActionPreformed("RIGHT"));
		ap.put("LEFT", new ActionPreformed("LEFT"));
		ap.put("UP", new ActionPreformed("UP"));
		ap.put("DOWN", new ActionPreformed("DOWN"));
		ap.put("ENTER", new ActionPreformed("ENTER"));
		ap.put("ESC", new ActionPreformed("ESC"));

		// Runs in own thread moving the snake and food if the snake is alive
		// and the game is not paused
		// Calls paint method redefined down below
		Thread gameThread = new Thread() {
			public void run() {
				while (true) {
					if (snake.isAlive() && !snake.isPaused()) {
						snake.move(food);
					}
					repaint();
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		gameThread.start();
	}

	//Clears canvas then paints gray rectangle over it Paints snake and food on canvas
	//Paints score on canvas
	//If game is paused and the snake is alive draws the paused screen
	//If snake is not alive draws game over screen
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, (int) Main.size.getWidth(), (int) Main.size.getHeight());
		food.paint(g);
		snake.paint(g);
		snake.drawScore(g);
		if (snake.isPaused() && snake.isAlive()) {
			g.setColor(Color.ORANGE);
			g.setFont(new Font("Arial", Font.BOLD, 100));
			g.drawString("Paused", 325, 250);
			g.setColor(Color.YELLOW);
			g.setFont(new Font("Arial", Font.BOLD, 75));
			g.drawString("Press Enter to Resume", 75, 350);
		} else if (!snake.isAlive()) {
			if (first) {
				first = false;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			g.setColor(Color.ORANGE);
			g.setFont(new Font("Arial", Font.BOLD, 100));
			g.drawString("Game Over", 225, 250);
			g.setColor(Color.YELLOW);
			g.setFont(new Font("Arial", Font.BOLD, 75));
			g.drawString("Press Enter to Play Again", 50, 350);
		}
	}

	public class ActionPreformed extends AbstractAction {

		String cmd;

		public ActionPreformed(String cmd) {
			this.cmd = cmd;
		}

		//Takes the input figures out what key was pressed and executes action based on input
		//Executes action based conditions defined
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (cmd) {
			case "RIGHT":
				if (snake.getLook() == 1) {
					break;
				}
				snake.setLook(0);
				break;
			case "LEFT":
				if (snake.getLook() == 0) {
					break;
				}
				snake.setLook(1);
				break;
			case "UP":
				if (snake.getLook() == 3) {
					break;
				}
				snake.setLook(2);
				break;
			case "DOWN":
				if (snake.getLook() == 2) {
					break;
				}
				snake.setLook(3);
				break;
			case "ENTER":
				if (!snake.isAlive()) {
					snake.reset(food);
				} else if (snake.isPaused()) {
					snake.setPaused(false);
				}
				break;
			case "ESC":
				if (!snake.isPaused() && snake.isAlive()) {
					snake.setPaused(true);
					;
				}
				break;
			}
		}
	}
}
