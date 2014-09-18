package game.pieces;

import game.time.Clock;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import lab3.Point;

/**
 * This is one of several subclasses of the Piece class,
 * and is used to represent one of the boundaries of the game
 * area.
 * 
 * @author Ron Cytron, with some changes by Jon Turner, then by Silas Hsu
 *
 */
public class Boundary extends Piece implements ActionListener {
	
	private boolean alwaysBounce;
	private int timeCounter;
	private int stopAlwaysBounceTime;
	
	public Boundary(Point p, int width, int height) {
		super(new BoundingBox(p,width,height));
		alwaysBounce = false;
		timeCounter = 0;
	}
	
	public void shrink(double factor) {
		// do nothing - Boundary may not shrink
	}
	
	@Override
	public void setCenter(Point center) {
		// do nothing - Boundary may not move
	}
	
	/**
	 * @return if the boundary is set to always bounce back balls
	 */
	public boolean ballsAlwaysBounce() {
		return alwaysBounce;
	}
	
	/**
	 * Set the boundary to always bounce back balls for the specified duration in milliseconds.
	 * Does nothing if the boundary is already set to always bounce back balls.
	 * @param duration - balls will always bounce back for this many milliseconds.  If less than 0, the method does nothing.
	 */
	public void setAlwaysBounce(int duration) {
		if (!alwaysBounce) {
			alwaysBounce = true;
			stopAlwaysBounceTime = duration;
			Clock.instance().getTimer().addActionListener(this);
		}
	}
	
	public String toString() {
		return "boundary at " + getCenter();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		timeCounter += Clock.instance().getTimeQuantum();
		if (timeCounter >= stopAlwaysBounceTime) {
			alwaysBounce = false;
			timeCounter = 0;
			Clock.instance().getTimer().removeActionListener(this);
		}
	}
}
