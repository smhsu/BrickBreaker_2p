package game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nip.*;

import nip.Rectangle;
import game.pieces.Piece;
import game.pubsub.*;
import game.time.Clock;
import lab3.Point;

/**
 * Handle the display of rectangular Pieces.
 * 
 * @author Ron Cytron, with some changes by Jon Turner
 */
public class RectViz extends PieceViz implements Subscriber<PieceEvent>, ActionListener {

	private int width, height;
	private int timeCounter;
	private int changeColorTime;
	private Color originalColor;
	private boolean colorChanged;
	
	public RectViz(Piece p, Color c, GraphicsPanel panel) {
		super(p,c,panel);
		originalColor = c;
		setGraphic(newGraphic());
		timeCounter = 0;
		colorChanged = false;
	}
	
	/**
	 * Used to determine when the size of a RectViz has changed
	 * 
	 * @return true if the size has just changed
	 */
	protected boolean newSize() {
		return piece.getWidth() != width || piece.getHeight() != height;
	}
	
	/**
	 * Compute new graphical representation of the RectViz.
	 * 
	 * @return the new graphic
	 */
	protected Graphic newGraphic() {	
		width = piece.getWidth();
		height = piece.getHeight();	
		Graphic g = new Rectangle(0, 0, width, height);
		return g;
	}
	
	/**
	 * Change the color of this display temporarily.  Does nothing if the color
	 * is already changed.
	 * @param color - new color
	 * @param duration - time in milliseconds until color changes back.  If 0 or less, color is changed permanently.
	 */
	public void setNewColor (Color color, int duration) {
		if (duration <= 0) {
			this.color = color;
			this.originalColor = color;
			setGraphic(newGraphic());
		}
		else if (!colorChanged) {
			this.color = color;
			setGraphic(newGraphic());
			changeColorTime = duration;
			colorChanged = true;
			Clock.instance().getTimer().addActionListener(this);
		}
	}
	
	public String toString() {
		return "rectViz at " + piece.getCenter();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		timeCounter += Clock.instance().getTimeQuantum();
		if (timeCounter >= changeColorTime) {
			color = originalColor;
			setGraphic(newGraphic());
			timeCounter = 0;
			colorChanged = false;
			Clock.instance().getTimer().removeActionListener(this);
		}
		
	}
}
