package GameState;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;


//import Audio.JukeBox;
import Entity.Enemy;
import Entity.EnemyProjectile;
import Entity.EnergyParticle;
import Entity.Explosion;
import Entity.HUD;
import Entity.Player;
import Entity.NewPlayerSaveInfo;
import Entity.Steve;
import Entity.Teleport;
import Entity.Title;
import Entity.Enemies.GelPop;
import Handlers.Keys;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;


public class CaveState extends GameState {
	
	private Background temple;
	
	private Player player;
	private TileMap tileMap;
	private ArrayList<Enemy> enemies;
	private ArrayList<EnemyProjectile> eprojectiles;
	private ArrayList<EnergyParticle> energyParticles;
	private ArrayList<Explosion> explosions;
	
	private HUD hud;
	private BufferedImage hageonText;
	private Title title;
	private Title subtitle;
	private Teleport teleport;
	
	// events
	private boolean blockInput = false;
	private int eventCount = 0;
	private boolean eventStart;
	private ArrayList<Rectangle> tb;
	private boolean eventFinish;
	private boolean eventDead;
	//private boolean eventQuake;
	
	public CaveState(GameStateManager gsm) {
		super(gsm);
		init();
	}
	
	public void init() {
		
		// backgrounds
		temple = new Background("/Backgrounds/minimalist_mountains.gif", 0.5, 0);
		
		// tilemap
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/creepertileset.gif");
		tileMap.loadMap("/Maps/level1c.map");
		tileMap.setPosition(140, 0);
		tileMap.setTween(1);
		
		// player
		player = new Player(tileMap);
		player.setPosition(480, 1550);
		player.setHealth(NewPlayerSaveInfo.getHealth());
		player.setLives(NewPlayerSaveInfo.getLives());
		player.setTime(NewPlayerSaveInfo.getTime());
		
		// enemies
		enemies = new ArrayList<Enemy>();
		eprojectiles = new ArrayList<EnemyProjectile>();
		populateEnemies();
		
		// energy particle
		energyParticles = new ArrayList<EnergyParticle>();
		
		player.init(enemies, energyParticles);
		
		// explosions
		explosions = new ArrayList<Explosion>();
		
		// hud
		hud = new HUD(player);
		
		// title and subtitle
		try {
			hageonText = ImageIO.read(
				getClass().getResourceAsStream("/HUD/HageonTemple.gif")
			);
			title = new Title(hageonText.getSubimage(0, 0, 178, 20));
			title.sety(60);
			subtitle = new Title(hageonText.getSubimage(0, 33, 91, 13));
			subtitle.sety(85);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		// teleport
		teleport = new Teleport(tileMap);
		teleport.setPosition(2460, 190);
		
		// start event
		eventStart = true;
		tb = new ArrayList<Rectangle>();
		eventStart();
		
		// sfx
		/*
		JukeBox.load("/SFX/teleport.mp3", "teleport");
		JukeBox.load("/SFX/explode.mp3", "explode");
		JukeBox.load("/SFX/enemyhit.mp3", "enemyhit");
		
		//Sound track
		JukeBox.load("/Music/level3.mp3", "level3");
		JukeBox.loop("level3", 600, JukeBox.getFrames("level3")-2200); 
		*/
	}
	
	private void populateEnemies() {
		enemies.clear();
		GelPop gp;
		Steve st;
		
		st = new Steve(tileMap, player);
		st.setPosition(480, 1550);
		enemies.add(st);
		

		gp = new GelPop(tileMap, player);
		gp.setPosition(50, 100);
		enemies.add(gp);
		gp = new GelPop(tileMap, player);
		gp.setPosition(120, 80);
		enemies.add(gp);
		gp = new GelPop(tileMap, player);
		gp.setPosition(300, 80);
		enemies.add(gp);
		gp = new GelPop(tileMap, player);
		gp.setPosition(350, 80);
		enemies.add(gp);
		gp = new GelPop(tileMap, player);
		gp.setPosition(400, 80);
		enemies.add(gp);
		gp = new GelPop(tileMap, player);
		gp.setPosition(600, 930);
		enemies.add(gp);
		gp = new GelPop(tileMap, player);
		gp.setPosition(750, 100);
		enemies.add(gp);
		gp = new GelPop(tileMap, player);
		gp.setPosition(900, 150);
		enemies.add(gp);
		gp = new GelPop(tileMap, player);
		gp.setPosition(1320, 250);
		enemies.add(gp);
		gp = new GelPop(tileMap, player);
		gp.setPosition(1570, 160);
		enemies.add(gp);
		gp = new GelPop(tileMap, player);
		gp.setPosition(1590, 160);
		enemies.add(gp);
		gp = new GelPop(tileMap, player);
		gp.setPosition(2600, 370);
		enemies.add(gp);
		gp = new GelPop(tileMap, player);
		gp.setPosition(2620, 370);
		enemies.add(gp);
		gp = new GelPop(tileMap, player);
		gp.setPosition(2640, 370);
		enemies.add(gp);
	}
	
	public void update() {
		
		// check keys
		handleInput();
		/*
		// check if quake event should start
		if(player.getx() > 2175 && !tileMap.isShaking()) {
			eventQuake = blockInput = true;
		}
		*/
		// check if end of level event should start
		if(teleport.contains(player)) {
			eventFinish = blockInput = true;
		}
		
		// play events
		if(eventStart) eventStart();
		if(eventDead) eventDead();
		//if(eventQuake) eventQuake();
		if(eventFinish) eventFinish();
		
		// move title and subtitle
		if(title != null) {
			title.update();
			if(title.shouldRemove()) title = null;
		}
		if(subtitle != null) {
			subtitle.update();
			if(subtitle.shouldRemove()) subtitle = null;
		}
		
		// move backgrounds
		temple.setPosition(tileMap.getx(), tileMap.gety());
		
		// update player
		player.update();
		if(player.getHealth() == 0 || player.gety() > tileMap.getHeight()) {
			eventDead = blockInput = true;
		}
		
		// update tilemap
		tileMap.setPosition(
			GamePanel.WIDTH / 2 - player.getx(),
			GamePanel.HEIGHT / 2 - player.gety()
		);
		tileMap.update();
		tileMap.fixBounds();
		
		// update enemies
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead()) {
				enemies.remove(i);
				i--;
				explosions.add(
					new Explosion(tileMap, e.getx(), e.gety()));
			}
		}
		
		// update enemy projectiles
		for(int i = 0; i < eprojectiles.size(); i++) {
			EnemyProjectile ep = eprojectiles.get(i);
			ep.update();
			if(ep.shouldRemove()) {
				eprojectiles.remove(i);
				i--;
			}
		}
		
		// update explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove()) {
				explosions.remove(i);
				i--;
			}
		}
		
		// update teleport
		teleport.update();
		
	}
	
	public void draw(Graphics2D g) {
		
		// draw background
		temple.draw(g);
		
		// draw tilemap
		tileMap.draw(g);
		
		// draw enemies
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
		
		// draw enemy projectiles
		for(int i = 0; i < eprojectiles.size(); i++) {
			eprojectiles.get(i).draw(g);
		}
		
		// draw explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).draw(g);
		}
		
		// draw player
		player.draw(g);
		
		// draw teleport
		teleport.draw(g);
		
		// draw hud
		hud.draw(g);
		
		// draw title
		if(title != null) title.draw(g);
		if(subtitle != null) subtitle.draw(g);
		
		// draw transition boxes
		g.setColor(java.awt.Color.BLACK);
		for(int i = 0; i < tb.size(); i++) {
			g.fill(tb.get(i));
		}
		
	}
	
	public void handleInput() {
		if(Keys.isPressed(Keys.ESCAPE)) gsm.setPaused(true);
		if(blockInput || player.getHealth() == 0) return;
		//if(Keys.isPressed(Keys.MUTE)) JukeBox.stop("level2");
		player.setUp(Keys.keyState[Keys.UP]);
		player.setDown(Keys.keyState[Keys.DOWN]);
		
		player.setWest(Keys.keyState[Keys.WEST]);
		player.setEast(Keys.keyState[Keys.EAST]);
		player.setJumping(Keys.keyState[Keys.NORTH]);

		player.setDashing(Keys.keyState[Keys.BUTTON2]);
		if(Keys.isPressed(Keys.BUTTON3)) player.setAttacking();
		if(Keys.isPressed(Keys.BUTTON4)) player.setCharging();
	}

	/*
	public void handleInput() {
		if(Keys.isPressed(Keys.ESCAPE)) gsm.setPaused(true);
		if(blockInput || player.getHealth() == 0) return;
		if(Keys.isPressed(Keys.MUTE)) JukeBox.stop("level3");
		player.setUp(Keys.keyState[Keys.UP]);
		player.setLeft(Keys.keyState[Keys.LEFT]);
		player.setDown(Keys.keyState[Keys.DOWN]);
		player.setRight(Keys.keyState[Keys.RIGHT]);
		player.setJumping(Keys.keyState[Keys.BUTTON1]);
		player.setDashing(Keys.keyState[Keys.BUTTON2]);
		if(Keys.isPressed(Keys.BUTTON3)) player.setAttacking();
		if(Keys.isPressed(Keys.BUTTON4)) player.setCharging();
	}
	*/
	
///////////////////////////////////////////////////////
//////////////////// EVENTS
///////////////////////////////////////////////////////
	
	// reset level
	private void reset() {
		player.loseLife();
		player.reset();
		player.setPosition(300, 131);
		populateEnemies();
		blockInput = true;
		eventCount = 0;
		tileMap.setShaking(false, 0);
		eventStart = true;
		eventStart();
		title = new Title(hageonText.getSubimage(0, 0, 178, 20));
		title.sety(60);
		subtitle = new Title(hageonText.getSubimage(0, 33, 91, 13));
		subtitle.sety(85);
	}
	
	// level started
	private void eventStart() {
		eventCount++;
		if(eventCount == 1) {
			tb.clear();
			tb.add(new Rectangle(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
			tb.add(new Rectangle(0, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
			tb.add(new Rectangle(0, GamePanel.HEIGHT / 2, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
			tb.add(new Rectangle(GamePanel.WIDTH / 2, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
		}
		if(eventCount > 1 && eventCount < 60) {
			tb.get(0).height -= 4;
			tb.get(1).width -= 6;
			tb.get(2).y += 4;
			tb.get(3).x += 6;
		}
		if(eventCount == 30) title.begin();
		if(eventCount == 60) {
			eventStart = blockInput = false;
			eventCount = 0;
			subtitle.begin();
			tb.clear();
		}
	}
	
	// player has died
	private void eventDead() {
		eventCount++;
		if(eventCount == 1) player.setDead();
		if(eventCount == 60) {
			tb.clear();
			tb.add(new Rectangle(
				GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
		}
		else if(eventCount > 60) {
			tb.get(0).x -= 6;
			tb.get(0).y -= 4;
			tb.get(0).width += 12;
			tb.get(0).height += 8;
		}
		if(eventCount >= 120) {
			if(player.getLives() == 0) {
				gsm.setState(GameStateManager.MENUSTATE);
				//JukeBox.stop("level3");
			}
			else {
				eventDead = blockInput = false;
				eventCount = 0;
				reset();
			}
		}
	}
	/*
	// earthquake
	private void eventQuake() {
		eventCount++;
		if(eventCount == 1) {
			player.stop();
			player.setPosition(2175, player.gety());
		}
		if(eventCount == 60) {
			player.setEmote(player.CONFUSED);
		}
		if(eventCount == 120) player.setEmote(player.NONE);
		if(eventCount == 150) tileMap.setShaking(true, 10);
		if(eventCount == 180) player.setEmote(player.SURPRISED);
		if(eventCount == 300) {
			player.setEmote(player.NONE);
			eventQuake = blockInput = false;
			eventCount = 0;
		}
	}
	*/
	// finished level
	private void eventFinish() {
		eventCount++;
		if(eventCount == 1) {
			/*JukeBox.play("teleport");
			JukeBox.stop("level3");*/
			player.setTeleporting(true);
			player.stop();
		}
		else if(eventCount == 120) {
			tb.clear();
			tb.add(new Rectangle(
				GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
			//JukeBox.stop("level3");
		}
		else if(eventCount > 120) {
			tb.get(0).x -= 6;
			tb.get(0).y -= 4;
			tb.get(0).width += 12;
			tb.get(0).height += 8;
			/*JukeBox.stop("teleport");
			JukeBox.stop("level3");*/
		}
		if(eventCount == 180) {
			NewPlayerSaveInfo.setHealth(player.getHealth());
			NewPlayerSaveInfo.setLives(player.getLives());
			NewPlayerSaveInfo.setTime(player.getTime());
			gsm.setState(GameStateManager.FORESTSTATE);
			//JukeBox.stop("level3");
		}
		
	}

}