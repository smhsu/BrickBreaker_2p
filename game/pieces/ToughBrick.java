package game.pieces;

import lab3.Point;

public class ToughBrick extends Brick {
	
	protected static int VALUE = 10; // Points gained for each hit

	public ToughBrick(Point p, int width, int height) {
		super(p,width,height);
	}

	public int getValue() {
		return VALUE;
	}

}


