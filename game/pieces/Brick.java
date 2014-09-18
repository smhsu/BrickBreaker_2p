package game.pieces;

import lab3.Point;

/**
 * This is one of several subclasses of the Piece class,
 * and is used to represent a brick in the game.
 * 
 * @author Ron Cytron, with some changes by Jon Turner
 */
public class Brick extends Piece {
	private int width;
	private int height;
	protected static int VALUE = 10; // Points gained when a ball hits this brick
	
	public Brick(Point p, int width, int height) {
		super(new BoundingBox(p,width,height));
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Shrink the brick by a specified factor and kill it if it
	 * gets too small.
	 * @param factor is multiplied by the bricks dimensions to get the
	 * new dimensions; ignored if <0 or >1.
	 */
	public void shrink(double factor) {
		if (factor < 0 || factor > 1) return;
		if (width * factor < 5.0) die();
		else {
			width = (int) (width*factor);
			height = (int) (height*factor);
			setBB(new BoundingBox(getCenter(), width, height));
		}
	}
	
	public int getValue() {
		return VALUE;
	}
	
	public String toString() {
		return "brick at " + getCenter();
	}
}
