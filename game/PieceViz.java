package game;

import java.awt.Color;
import java.awt.event.ActionListener;

import lab3.Point;
import nip.GraphicsPanel;
import nip.Graphic;
import game.pieces.Piece;
import game.pubsub.*;
import game.time.Clock;
import game.time.ClockTick;

/**
 * This class is responsible for displaying graphics associated
 * with game pieces in the graphics panel. The separation of the
 * piece's behavior from its visual representation keeps each
 * part simpler and makes things more flexible.
 * 
 * @author Ron Cytron, with some changes by Jon Turner, then by Silas Hsu
 */
public abstract class PieceViz implements Subscriber<PieceEvent> {

	public Graphic graphic;
	final protected GraphicsPanel panel;
	final protected Piece piece;
	protected Color color;		// base color of the piece
	private double status;	// last known status of the piece
	private Point center;	// last known location of the piece
	private boolean dead;
	private short delayCounter;
	final private short MOVE_DELAY = 3;
	
	/*
	 * MOVE_DELAY is the delay in number of PieceEvents before the graphic of the piece is moved.
	 * If this delay is too short, parts of the piece may be visually cut off as it moves.
	 */
	
	public PieceViz(Piece piece, Color c, GraphicsPanel panel) {
		this.piece = piece;
		this.panel = panel;
		graphic = null;
		color = c;
		status = piece.getStatus();
		center = piece.getCenter();
		dead = false;
		delayCounter = 1;

		// Listen for any changes to Piece, so that
		// observeEvent will be called when Piece changes
		piece.addSubscriber(this);	
	}
	
	/**
	 * Computes actual color based on originally specified color
	 * and the "life status" of the associated piece. Causes the
	 * peice's color to fade as it dies
	 * 
	 * @return a Color object representing the piece's color
	 */
	private Color pieceColor() {
		return new Color(
				(int) (255 - (255 - color.getRed())*status),
				(int) (255 - (255 - color.getGreen())*status),
				(int) (255 - (255 - color.getBlue())*status)
			);
	}
	
	/**
	 * Set the Shape for this visualization of a Piece.
	 * Can be changed as the program runs. This is used
	 * mainly to change the size of a piece as the game
	 * progresses.
	 * 
	 * @param graphic is the new graphic to be used to
	 * represent the piece.
	 */
	protected void setGraphic(Graphic graphic) {
		if (this.graphic != null)
			panel.remove(this.graphic);
		this.graphic = graphic;		
		graphic.setFilled(true);
		graphic.setFillColor(pieceColor());
		panel.add(graphic);
		setCenter(piece.getCenter());
	}
	
	/**
	 * Moves the center of the graphic to a specified location.
	 * 
	 * @param p is the new center point
	 */
	private void setCenter(Point p) {
		if (this.graphic != null)
			graphic.setCenter((int) p.getX(), (int) p.getY());
	}

	/**
	 * Implemented by subclasses to report whenever the piece
	 * changes in size.
	 * 
	 * @return true if the piece size has just changed
	 */
	protected abstract boolean newSize();
	
	/**
	 * Implemented by subclasses to provide a new graphic that
	 * represents the piece. Used to replace a piece which has
	 * changed in size.
	 * 
	 * @return the new graphic
	 */
	protected abstract Graphic newGraphic();

	/**
	 * Called whenever the Piece position or status changes and 
	 * updates the graphic accordingly.
	 * 
	 * @param pe - the event associated with the piece
	 */
	public void observeEvent(PieceEvent pe) {
		if (piece.isDead()) {
			panel.remove(this.graphic);
			dead = true;
		} else {
			if (newSize()) { // changing size requires changing graphic			
				setGraphic(newGraphic());
			}
			if (piece.getStatus() != status)  {
				// set fill color based on status of piece, so that
				// color fades as piece status degrades	
				status = piece.getStatus();				
				graphic.setFillColor(pieceColor());
			} 
			if (piece.getCenter() != center) { // move graphic if piece moved
				if (delayCounter % MOVE_DELAY == 0) {
					center = piece.getCenter();
					setCenter(piece.getCenter());
					delayCounter = 1;
				}
				else delayCounter++;
			}
		}
	}
	
	public boolean isDead() { return dead; }
	
	
}
