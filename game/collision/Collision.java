package game.collision;

import game.*;
import game.pieces.Piece;
import game.pubsub.*;

/**
 * The PubSubEvent generated upon collision of two Pieces
 */
public class Collision implements PubSubEvent {
	
	private Piece one, two;
	
	public Collision(Piece one, Piece two) {
		this.one = one; this.two = two;
	}
	
	public Piece getPiece1() { return one; }
	
	public Piece getPiece2() { return two; }
}
