import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

public class Food {
	Point loc;
	private Random r = new Random();
	private int radius = 10;

	private int width = (int) Main.size.getWidth();
	private int height = (int) Main.size.getHeight();
	
	public Food() {
		loc = new Point(r.nextInt((int) (width - 40)) + 20,
				r.nextInt((int) height - 40) + 20);
	}
	/**
	 * Draws the red circle at the point where it should be
	 * @param g
	 */
	public synchronized void paint(Graphics g) {
		g.setColor(Color.RED);
		g.fillOval(loc.x - radius, loc.y - radius, radius * 2, radius * 2);
	}

	/**
	 * Finds a new place to put the food based on the rules established
	 */
	public synchronized void move() {
		loc = new Point(r.nextInt((int) (width - 40)) + 20,
				r.nextInt((int) height - 40) + 20);
	}

}
