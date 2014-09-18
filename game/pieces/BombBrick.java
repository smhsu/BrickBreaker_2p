package game.pieces;

import lab3.Point;

public class BombBrick extends Brick {
	
	public final double explosionRadius;
	protected static int VALUE = 20;

	public BombBrick(Point p, int width, int height, double explosionRadius) {
		super(p, width, height);
		this.explosionRadius = explosionRadius;
	}
	
	public int getValue() {
		return VALUE;
	}
}