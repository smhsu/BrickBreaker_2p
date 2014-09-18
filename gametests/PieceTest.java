package gametests;

import game.pieces.BoundingBox;
import game.pieces.Piece;

import static org.junit.Assert.*;
import lab3.Point;

import org.junit.Test;


public class PieceTest extends BBGen {
	
	@Test
	public void test() {
		BoundingBox bb = new BoundingBox(new Point(10, 20), 100, 200);
		Piece piece = new Piece(bb);
		assertEquals(bb.getCenter(), piece.getCenter());
		System.out.println(piece.getCenter());
		piece.setCenter(piece.getCenter());
		assertEquals(bb.getCenter(), piece.getCenter());
	}
	
	@Test
	public void testContains() {
		BoundingBox mainBB = genRandomBox(10, 20, 90, 100);
		BoundingBox insideBB = genRandomBox(20, 25, 50, 60);
		BoundingBox outsideBB = genRandomBox(120, 130, 5, 5);
		Piece main = new Piece(mainBB);
		Piece inside = new Piece(insideBB);
		Piece outside = new Piece(outsideBB);
		assertTrue(main.intersects(inside));
		assertTrue(inside.intersects(main));
		assertTrue(!outside.intersects(main));
		assertTrue(!main.intersects(outside));

	}

}
