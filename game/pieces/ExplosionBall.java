package game.pieces;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import game.Player;
import game.pubsub.Subscriber;
import game.time.Clock;
import game.time.ClockTick;
import lab3.Point;

/**
 * This class uses a ball to represent explosions.
 * @author Silas Hsu
 *
 */
public class ExplosionBall extends Ball implements ActionListener {
	
	private short fadeCounter;
	private final short FADE_DELAY = 5;
	
	/**
	 * Creates an explosion ball with no owner.
	 * 
	 * @param center
	 * @param radius
	 */
	public ExplosionBall(Point center, double radius) {
		this(center, radius, null);
	}
	
	/**
	 * Creates an explosion ball with specified owner <p>
	 * 
	 * @param center
	 * @param radius
	 * @param owner
	 */
	public ExplosionBall(Point center, double radius, Player owner) {
		super(center, radius, owner);
		Clock.instance().getTimer().addActionListener(this);
		fadeCounter = 1;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (fadeCounter % FADE_DELAY == 0) {
			augmentLifeStatus(-0.1);
			fadeCounter = 1;
		}
		fadeCounter++;
		if (isDead()) {
			Clock.instance().getTimer().removeActionListener(this);
		}
	}

}
