package game.pubsub;

import game.pieces.Piece;

public class PieceEvent implements PubSubEvent {
	final public Piece p;
	
	public PieceEvent(Piece p) {
		this.p = p;
	}
}
