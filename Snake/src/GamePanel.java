import java.awt.Color;
import java.awt.Dimension;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener {
	
	private static final long serialVersionUID = 1L;

	private static final int WIDTH = 500, HEIGHT = 500;
	
	private Thread thread;
	
	public int score = 0;
	
	private BodyPart bodyPart;
	private ArrayList<BodyPart> snake;
	
	private Mouse mouse;
	private ArrayList<Mouse> mice;
	
	private Random r;

	private int xCoor = 10, yCoor = 10, size = 5;
	private double ticks = 0;
	private double speed = 0.2;
	
	private boolean right = true, left = false, up = false, down = false;
	
	public boolean running;

	public GamePanel () {
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		addKeyListener(this);
		
		snake = new ArrayList<BodyPart>();
		mice = new ArrayList<Mouse>();
		
		r =  new Random();
		
	}
	
	public void start() {
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
		
	}
	
	public void stop() {
		if(!running)
			return;
		running = false;
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}		
	}
	
	public void run() {
		while (running) {
			tick();
			repaint();
		}
	}

	public void clear() {
		xCoor = 10; yCoor = 10; size = 5; score = 0;
		snake.clear();
		mice.clear();
		repaint();
	}
	
	public void paint(Graphics g) {	
		g.clearRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		for(int i = 0 ; i < WIDTH/10 ; i++) {
			g.drawLine(i * 10, 0, i * 10, HEIGHT);
		}
		
		for(int i = 0 ; i < HEIGHT/10 ; i++) {
			g.drawLine(0, i * 10, HEIGHT, i * 10);
		}
		
		for(int i = 0 ; i < snake.size(); i++) {
			snake.get(i).draw(g);
		}
		
		for(int i = 0 ; i < mice.size(); i++) {
			mice.get(i).draw(g);
		}
		
	}
	
	public void tick() {
		if(snake.size() == 0) {
			bodyPart = new BodyPart(xCoor, yCoor, 10);
			snake.add(bodyPart);
		}
		ticks = ticks + speed;
		if(ticks > 250000) {
			if(right) xCoor++;
			if(left) xCoor--;
			if(up) yCoor--;
			if(down) yCoor++;
			
			ticks = 0;
			
			bodyPart = new BodyPart(xCoor, yCoor, 10);
			snake.add(bodyPart);
			
			if(snake.size() > size) {
				snake.remove(0);
			}
		}
		
		if(mice.size() == 0) {
			int xCoor = r.nextInt(49);
			int yCoor = r.nextInt(49);
			mouse = new Mouse(xCoor, yCoor, 10);
			mice.add(mouse);
		}
		
		for(int i = 0 ; i < mice.size(); i++) {
			if(xCoor == mice.get(i).getxCoor() && yCoor == mice.get(i).getyCoor()) {
				size++;
				mice.remove(i);
				i++;
				score = score + 10;
			}
		}
		
		for(int i = 0 ; i < snake.size(); i++) {
			if(xCoor == snake.get(i).getxCoor() && yCoor == snake.get(i).getyCoor()) {
				if(i != snake.size() - 1) {
					JOptionPane.showMessageDialog(this, "Game Over! Vaše skóre: " + score);
					stop();
				}
			}
		}
		
		if (xCoor < 0 || xCoor > 49 || yCoor < 0 || yCoor > 49) {
			JOptionPane.showMessageDialog(this, "Game Over! Vaše skóre: " + score);
			stop();
		}
		
		switch(snake.size()) {
		case 10:
			speed = 0.3;
			break;
		case 15:
			speed = 0.4;
			break;
		case 20:
			speed = 0.5;
			break;
		case 25:
			speed = 0.6;
			break;
		case 30:
			speed = 0.7;
			break;
		case 35:
			speed = 0.8;
			break;
		}
		
		
	}

	@Override
	public void keyTyped(KeyEvent e) {		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_RIGHT && !left) {
			right = true;
			up = false;
			down = false;			
		}
		
		if(key == KeyEvent.VK_LEFT && !right) {
			left = true;
			up = false;
			down = false;			
		}
		
		if(key == KeyEvent.VK_UP && !down) {
			up = true;
			right = false;
			left = false;			
		}
		
		if(key == KeyEvent.VK_DOWN && !up) {			
			down = true;
			right = false;
			left = false;			
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {

		
	}
	
	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
