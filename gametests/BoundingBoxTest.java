package gametests;

import game.pieces.BoundingBox;

import static org.junit.Assert.*;

import lab3.Point;
import lab3.Vector;

import org.junit.Test;


public class BoundingBoxTest extends BBGen {
	
	
	@Test
	public void test() {
		for (int i=0; i < 10000; ++i) {
			testCorners();
			testContains();
		}
	}
	
	public void testCorners() {
		
		BoundingBox one = genRandomBox(0, 5, 10, 100);
		
		Point ul = one.getUL();
		int width = one.getWidth();
		int height = one.getHeight();
		
		for (int i=0; i < width; ++i) {
			for (int j=0; j < height; ++j) {
				assertTrue(one.contains(ul.plus(new Vector(i,j))));
			}
		}
		assertTrue(!one.contains(ul.plus(new Vector(0, height))));
		assertTrue(!one.contains(ul.plus(new Vector(width, 0))));
		assertTrue(!one.contains(ul.plus(new Vector(width, height))));
		assertTrue(!one.contains(ul.plus(new Vector(-1, -1))));
		assertTrue(!one.contains(ul.plus(new Vector(-1, 0))));
		assertTrue(!one.contains(ul.plus(new Vector(0, -1))));
	}
	
	public void testContains() {
		BoundingBox main = genRandomBox(10, 20, 90, 100);
		BoundingBox inside = genRandomBox(20, 25, 50, 60);
		BoundingBox outside = genRandomBox(120, 130, 5, 5);
		// System.out.println(main + " and inside " + inside + " and outside " + outside);
		assertTrue(main.intersects(inside));
		assertTrue(inside.intersects(main));
		assertTrue(!outside.intersects(main));
		assertTrue(!main.intersects(outside));
	}
	

}
