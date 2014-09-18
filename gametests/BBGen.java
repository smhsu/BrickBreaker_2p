package gametests;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import game.pieces.BoundingBox;
import lab3.Point;

public class BBGen {
	
	private Random rand = new Random();

	public BBGen() {
		super();
	}
	
	/**
	 * @param lo inclusive lower bound for result
	 * @param hi exclusive upper bound for result
	 * @return lo <= ans < hi
	 */
	public int genIntInRange(int lo, int hi) {
		return lo + (int)((hi-lo)*rand.nextDouble());
	}
	

	public BoundingBox genRandomBox(int lo, int hi, int minsize,
			int maxsize) {
				int cx = genIntInRange(lo, hi);
				int cy = genIntInRange(lo, hi);
				int width = genIntInRange(minsize, maxsize);
				int height = genIntInRange(minsize ,maxsize);
				
				BoundingBox ans = new BoundingBox(new Point(cx, cy), width, height);
				assertEquals(cx, ans.getCenter().getX(), .0001);
				assertEquals(cy, ans.getCenter().getY(), .0001);
				assertEquals(width, ans.getWidth());
				assertEquals(height, ans.getHeight());
				
				return ans;
			}

}