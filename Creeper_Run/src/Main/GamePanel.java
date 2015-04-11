package Main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.Toolkit;

import javax.swing.JPanel;

import GameState.GameStateManager;
import Handlers.Keys;


@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener{
	
	// dimensions
	
	public static final int WIDTH = 600;
	public static final int HEIGHT = 350;
	public static final int SCALE = 1;
	
	public static final int SCALEDWIDTH = WIDTH * SCALE;
	public static final int SCALEDHEIGHT = HEIGHT * SCALE;
	
	// game thread
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;
	
	// image
	private BufferedImage image;
	private Graphics2D g;
	
	// game state manager
	private GameStateManager gsm;
	
	// other
	private boolean recording = false;
	private int recordingCount = 0;
	private boolean screenshot;
	private int cx, cy;

		
		//These two methods get the height and width of the screen
		public static int SCREENWIDTH() {
		    return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
		}

		public static int SCREENHEIGHT() {
		    return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
		}
		
		public void centergame(){
			cx  = (SCREENWIDTH() / 2 - SCALEDWIDTH / 2);
			cy =  (SCREENHEIGHT() / 2 - SCALEDHEIGHT / 2);
		}
		
		public GamePanel() {
		//setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize())); //sets panel to size of screen
		setFocusable(true);
		requestFocus();
		
	}
	
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}
	
	private void init() {
		
		//image = new BufferedImage(cx, cy, BufferedImage.TYPE_INT_RGB);
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		/*g.setRenderingHint(
			RenderingHints.KEY_TEXT_ANTIALIASING,
			RenderingHints.VALUE_TEXT_ANTIALIAS_ON
		);*/
		
		
		running = true;
		
		gsm = new GameStateManager();
		
	}
	
	public void run() {
		init();
		
		System.out.println(WIDTH + " " + HEIGHT);
		//System.out.println(cx + " " + cy);
		
		long start;
		long elapsed;
		long wait;
		
		// game loop
		while(running) {
			
			start = System.nanoTime();
			
			update();
			draw();
			drawToScreen();
			
			elapsed = System.nanoTime() - start;
			
			wait = targetTime - elapsed / 1000000;
			if(wait < 0) wait = 5;
			
			try {
				Thread.sleep(wait);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	private void update() {
		gsm.update();
		Keys.update();
	}
	private void draw() {
		gsm.draw(g);
	}
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, SCALEDWIDTH, SCALEDHEIGHT, null);
		g2.dispose();
	}
	
	public void keyTyped(KeyEvent key) {}
	public void keyPressed(KeyEvent key) {
		if(key.isControlDown()) {
			if(key.getKeyCode() == KeyEvent.VK_R) {
				recording = !recording;
				return;
			}
			if(key.getKeyCode() == KeyEvent.VK_S) {
				screenshot = true;
				return;
			}
		}
		Keys.keySet(key.getKeyCode(), true);
	}
	public void keyReleased(KeyEvent key) {
		Keys.keySet(key.getKeyCode(), false);
	}

}