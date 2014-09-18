package game.pieces;

import java.util.LinkedList;

import lab3.Point;
import lab3.Vector;

/**
 * This class represents the maximum region occupied by a Piece.
 * Main role here is in facilitating intersection calculations.
 * 
 * @author Ron Cytron, with some changes by Jon Turner
 */
public class BoundingBox {

	private final Point ul;  // inclusive upper left hand corner of the box
	private final Point lr;  // inclusive lower right hand corner of the box
	protected final int width, height; // dimensions of the box

	public BoundingBox(Point center, int width, int height) {
		this.ul = new Point(center.getX()-(width/2),
							center.getY()-(height/2));
		this.lr = new Point(ul.getX()+(width-1),
							ul.getY()+(height-1));
		this.width = width;
		this.height = height;
	}

	/**
	 * @return the point at the upper left corner
	 */
	public Point getUL() { return ul; }
	
	/**
	 * @return the point at the center of the box
	 */
	public Point getCenter() {
		return new Point(ul.getX() + width/2, ul.getY() + height/2);
	}

	/**
	 * @return the width of the box
	 */
	public int getWidth() { return width; }

	/**
	 * @return the height of the box
	 */
	public int getHeight() { return height; }

	public int area() { return width * height; }

	/**
	 * Determines if a given point is in the bounding box.
	 * 
	 * @param point to be tested for containment
	 * @return true if the box contains the specified point
	 */
	public boolean contains(Point p) {
		return 
		ul.getX() <= p.getX() && p.getX() <= lr.getX()
		&& ul.getY() <= p.getY() && p.getY() <= lr.getY();
	}

	/**
	 * Returns all the points with integer coordinates that
	 * fall inside the box.
	 * 
	 * @return a list of points in the box
	 */
	public Iterable<Point> points() {
		int x = (int) ul.getX();
		int y = (int) ul.getY();
		LinkedList<Point> list = new LinkedList<Point>();
		for (int i = x; i < x+width; ++i) {
			for (int j = y; j < y+height; ++j) {
				list.add(new Point(i,j));
			}
		}
		return list;
	}

	/**
	 * Returns the corners in no particular order
	 * @return corners of this box
	 */
	public Point[] corners() {
		Point[] ans = new Point[4];
		ans[0] = ul;
		ans[1] = ul.plus(new Vector(width-1, 0));
		ans[2] = ul.plus(new Vector(0, height-1));
		ans[3] = lr;
		return ans;
	}

	/**
	 * Return true if this bounding box overlaps
	 * the other bounding box
	 *
	 * @return true if the two BBs intersect, else false
	 */
	public boolean intersects(BoundingBox other) {
		if (this.ul.getX() <= other.lr.getX() &&
			this.ul.getY() <= other.lr.getY() &&
			other.ul.getX() <= this.lr.getX() &&
			other.ul.getY() <= this.lr.getY())
			return true;
		return false;
	}
	
	/**
	 * Compares the areas of two bounding boxes
	 * @param other another bounding box
	 * @return true if this bounding box's area is larger than the other's
	 */
	public boolean isBiggerThan(BoundingBox other) {
		if (this.width * this.height > other.width * other.height)
			return true;
		return false;
	}
	
	public int perimeter() {
		return (2*width + 2*height);
	}

	public String toString() {
		return "Bounding Box ul " + ul + " width = " + width + " height = " + height;
	}

}
