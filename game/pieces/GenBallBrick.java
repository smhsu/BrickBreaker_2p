package game.pieces;
import lab3.Point;

public class GenBallBrick extends Brick {
	
	protected static int VALUE = 20;
	
	public GenBallBrick(Point p, int width, int height) {
		super(p,width,height);
	}
	
	public int getValue() {
		return VALUE;
	}

}
