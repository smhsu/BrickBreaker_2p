package game.pieces;

import lab3.Point;

public class PenaltyBrick extends Brick {
	
	protected static int VALUE = -50;

	public PenaltyBrick(Point p, int width, int height) {
		super(p, width, height);
	}
	
	public int getValue() {
		return VALUE;
	}

}
