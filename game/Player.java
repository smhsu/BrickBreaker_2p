package game;

import java.awt.Color;
import java.awt.event.KeyEvent;

/**
 * Keeps track of the stats of a player, including score, spendable points,
 * upgrades, number of powerups, and key bindings.
 * 
 * @author Silas Hsu
 * Decemer 19, 2012
 */
public class Player {
	
	private final int id;
	
	public int score;
	public int money; // Spendable points
	public int numPadSizeUps;
	public int numPadSpeedUps;
	public int numBallSpeedUps;
	public int numStickyUps;
	public int numInvinciUps;
	
	public Color color;
	
	public int paddleUpKey;
	public int paddleDownKey;
	public int invinciKey; // The key that activates the invincibility powerup
	public int stickyKey; // The key that activates the sticky paddle powerup
	
	public static final int PAD_SIZE_INCREMENT = 25;
	public static final int PAD_SPEED_INCREMENT = 50;
	public static final int BALL_SPEED_INCREMENT = 15;
	
	/**
	 * Creates a new player with the specified identification number, 
	 * all stats 0, no key bindings, and no color
	 */
	public Player(int id) {
		reset();
		this.id = id;
	}
	
	/**
	 * Resets all of this player's stats except money to 0.  Money will be reset to 400.
	 */
	public void reset() {
		score = 0;
		money = 400;
		numPadSizeUps = 0;
		numPadSpeedUps = 0;
		numBallSpeedUps = 0;
		numStickyUps = 0;
		numInvinciUps = 0;
	}
	
	/**
	 * @return the player's identification number
	 */
	public int getId() {
		return id;
	}

}
