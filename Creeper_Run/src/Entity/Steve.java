package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Entity.Enemy2;
import Entity.Player;
import Handlers.Content;
import Main.GamePanel;
import TileMap.TileMap;

public class Steve extends Enemy {
	
	private BufferedImage[] sprites;
	private Player player;
	private boolean active;
	
	public Steve(TileMap tm, Player p) {
		
		super(tm);
		player = p;
		
		health = maxHealth = 1;
		
		width = 25;
		height = 35;
		cwidth = 25;
		cheight = 35;
		/*
		damage = 1;
		moveSpeed = 0.8;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -5;
		*/
		
		damage = 1;
		moveSpeed = 2;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -5;
		sprites = Content.Steve[0];
		
		animation.setFrames(sprites);
		animation.setDelay(4);
		
		west = false;
		facingRight = true;
		
	}
	
	private void getNextPosition() {
		if(west) dx = -moveSpeed;
		else if(east) dx = moveSpeed;
		else dx = 0;
		if(falling) {
			dy += fallSpeed;
			if(dy > maxFallSpeed) dy = maxFallSpeed;
		}
		if(jumping && !falling) {
			dy = jumpStart;
		}
	}
	
	public void update() {
		
		if(!active) {
			if(Math.abs(player.getx() - x) < GamePanel.WIDTH) active = true;
			return;
		}
		
		// check if done flinching
		if(flinching) {
			flinchCount++;
			if(flinchCount == 6) flinching = false;
		}
		
		getNextPosition();
		checkTileMapCollision();
		calculateCorners(x, ydest + 1);
		if(!bottomLeft) {
			west = true;
			east = facingRight = false;
		}
		if(!bottomRight) {
			west = false;
			east = facingRight = true;
		}
		setPosition(xtemp, ytemp);
		
		if(dx == 0) {
			//dy = jumpStart;
			jumping = true;
		}
		
		// update animation
		animation.update();
		
	}
	
	public void draw(Graphics2D g) {
		
		if(flinching) {
			if(flinchCount == 0 || flinchCount == 2) return;
		}
		
		super.draw(g);
		
	}
	
}
