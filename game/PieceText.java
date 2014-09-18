package game;

import lab3.Point;
import game.pieces.Piece;
import game.time.ClockTick;
import nip.GraphicsPanel;
import nip.Text;

/**
 * A special version of TempText that will follow a piece.  Used
 * primarily to display player message text that appears next to the paddle.
 * @author Silas Hsu
 * December 21, 2012
 */
public class PieceText extends TempText {

	private static final long serialVersionUID = 1L;
	
	private boolean removeWhenPieceIsDead;
	private Piece piece; // Piece to follow
	private Point center; // Last known center of the piece
	private int xDisplace; // X units right of the piece's center to display the text
	private int yDisplace; // Y units down of the piece's center to display the text

	/**
	 * Makes temporary text that will follow a specified piece.  Extends TempText.
	 * 
	 * @param text
	 * @param duration - duration in milliseconds
	 * @param panel
	 * @param piece
	 * @param xDisplace - X units right of the piece's center to display the text
	 * @param yDisplace - Y units down of the piece's center to display the text
	 * @param removeWhenPieceIsDead
	 */
	public PieceText(String message, int duration, GraphicsPanel panel, Piece piece, int xDisplace, int yDisplace, boolean removeWhenPieceIsDead) {
		super(message, duration, panel);
		setLocation( (int) piece.getCenter().getX() + xDisplace, (int) piece.getCenter().getY() + yDisplace);
		this.removeWhenPieceIsDead = removeWhenPieceIsDead;
		this.piece = piece;
		this.center = piece.getCenter();
		this.xDisplace = xDisplace;
		this.yDisplace = yDisplace;
	}

	public void observeEvent(ClockTick e) {
		super.observeEvent(e);
		if (piece.isDead() && removeWhenPieceIsDead)
			remove();
		setLocation( (int) piece.getCenter().getX() + xDisplace, (int) piece.getCenter().getY() + yDisplace );
		
	}

}
