package game;

import game.pieces.Piece;
import game.pubsub.*;
import java.awt.Color;
import lab3.Point;
import nip.*;

/**
 * Handle the display of circular pieces.
 * 
 * @author Ron Cytron, with some changes by Jon Turner
 */
public class CircViz extends PieceViz {
	private int radius;
	
	public CircViz(Piece p, Color c, GraphicsPanel panel) {
		super(p,c,panel);
		setGraphic(newGraphic());
	}
	
	/**
	 * Used to determine when the size of a CircViz has changed
	 * 
	 * @return true if the size has just changed
	 */
	protected boolean newSize() {
		return piece.getWidth()/2 != radius;
	}
	
	/**
	 * Compute new graphical representation of the CircViz.
	 * 
	 * @return the new graphic
	 */
	protected Graphic newGraphic() {	
		radius = piece.getWidth()/2;	
		Graphic g = new Ellipse(0, 0, 2*radius, 2*radius);
		return g;
	}
	
	public String toString() {
		return "circViz at " + piece.getCenter();
	}
}
