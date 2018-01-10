import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class Snake {

	private ArrayList<Point> turns = new ArrayList<>();

	private int length = 50;
	private int look = 0;

	private int x;
	private int y;
	private int originalX;
	private int originalY;

	private int foodAte = 0;

	private boolean isAlive = true;

	private boolean paused = false;
	
	private int speed = 3;

	public Snake(int x, int y) {
		this.x = x;
		this.y = y;
		this.originalX = x;
		this.originalY = y;
		this.turns.add(new Point(this.x, this.y));
	}

	/**
	 * Draws snake by connecting first index 0 to index size-1 then draws in
	 * descending size-1 to size-2 in descending order down to index 1
	 * @param g
	 */
	public synchronized void paint(Graphics g) {
		g.setColor(Color.GREEN);
		((Graphics2D) g).setStroke(new BasicStroke(10));
		for (int i = turns.size() - 1; i > 0; i--) {
			if (i == turns.size() - 1) {
				Point p1 = turns.get(i);
				Point p2 = turns.get(0);
				g.drawLine(p1.x, p1.y, p2.x, p2.y);
			} else {
				Point p1 = turns.get(i + 1);
				Point p2 = turns.get(i);
				g.drawLine(p1.x, p1.y, p2.x, p2.y);
			}
		}
	}

	/**
	 * Draws how many food you have eaten in the top left of the screen
	 * @param g
	 */
	public synchronized void drawScore(Graphics g) {
		g.setColor(Color.ORANGE);
		g.setFont(new Font("Arial", Font.BOLD, 50));
		g.drawString("Score: " + foodAte, 10, 50);
	}

	/**
	 * Moves snake and adds length to the snake if you eat food
	 * Ends game if you hit yourself or the edge
	 * @param f Food to move if you end up eating the food
	 */
	public synchronized void move(Food f) {
		if (Math.abs(turns.get(0).x - f.loc.x) <= 10 && Math.abs(turns.get(0).y - f.loc.y) <= 10) {
			f.move();
			length += 25;
			foodAte++;
		}
		turns.add(new Point(x, y));
		if (turns.size() > length) {
			turns.remove(1);
		}
		Point p = turns.get(0);
		for (int i = 1; i < this.turns.size() - 1; i++) {
			if (Math.abs(turns.get(i).x - p.x) <= 1 && Math.abs(turns.get(i).y - p.y) <= 1) {
				isAlive = false;
			}
		}
		if (p.x >= Main.size.getWidth() || p.x <= 0 || p.y >= Main.size.getHeight() || p.y <= 0) {
			isAlive = false;
		}
		if (isAlive) {
			switch (look) {
			case 0: // Right
				this.turns.get(0).x += speed;
				x += 3;
				break;
			case 1: // Left
				this.turns.get(0).x -= speed;
				x -= 3;
				break;
			case 2: // Up
				this.turns.get(0).y -= speed;
				y -= 3;
				break;
			case 3: // Down
				this.turns.get(0).y += speed;
				y += 3;
				break;
			}
		}
	}

	public void reset(Food f) {
		this.turns.clear();
		x = originalX;
		y = originalY;
		f.move();
		foodAte = 0;
		isAlive = true;
		look = 0;
		length = 50;
		this.turns.add(new Point(x, y));

	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public int getLook() {
		return look;
	}

	public void setLook(int look) {
		this.look = look;
	}

}
