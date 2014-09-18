package game;

import game.collision.*;
import game.motion.*;
import game.pieces.*;
import game.pubsub.PieceEvent;
import game.pubsub.Subscriber;
import game.time.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import lab3.Point;
import lab3.Vector;
import nip.Graphic;
import nip.GraphicsPanel;
import nip.Image;
import nip.NIP;
import nip.Text;

/**
 * As the primary controller of the game, this class provides
 * hooks to control the game's components.  It is separated
 * from actual mouse events and such which are handled by Main.
 * 
 * @author Ron Cytron, with some changes by Jon Turner, then changes by Silas Hsu
 */
public class Controller implements ActionListener {
	
	public NIP nip;
	public GraphicsPanel panel;
	private int width, height; // Width and height of the playing field
	private boolean isRunning;
	private boolean pauseDisabled;
	
	private Paddle paddleP1, paddleP2;
	private RectViz padVizP1, padVizP2;
	private PieceMover paddleP1Mover, paddleP2Mover;
	
	private Ball ballP1, ballP2;
	
	private Boundary floor, top, left, right;
	private RectViz boundVizP1, boundVizP2;
	
	private LinkedList<Brick> bricks;
	private int numNonPenaltyBricks;
	
	private Random random;
	
	private int p1LabelBound; // X coordinate of the top left corner of Player 1's title.  Player 2's is getRightBound() + 20.
	private Text scoreP1, scoreP2;
	private Text numInvinciP1, numStickyP1, numInvinciP2, numStickyP2;
	private JPanel pausePanel;
	
	private int startCounter;
	private final static int START_TIME = 5000; // Time in milliseconds before the game starts

	public final static double BALL_RADIUS = 10.0; // In pixels
	public final static int DEFAULT_PAD_SIZE = 100; // Paddle height without any upgrades
	public final static int DEFAULT_PAD_SPEED = 300; // Paddle speed without any upgrades
	public final static int DEFAULT_BALL_SPEED = 400; // Ball speed without any upgrades
	public final static int INVINCI_DURATION = 10000; // Duration of the invincibility powerup in milliseconds
	public final static int STICKY_DURATION = 10000; // Time until a ball is launched after sticking to a paddle in milliseconds
	public final static int numBricksHoriz = 5, numBricksVert = 10;

	
	/**
	 * Creates all the game's components in the specified panel.  The playing field will be centered in
	 * the panel.  The playing field's width and height may not be larger than the panel's width and height,
	 * but player statistics may still be cut off if the panel is too small.
	 * 
	 * @param panel
	 * @param width - width of the playing field in pixels
	 * @param height - height of the playing field in pixels
	 * 
	 * @throws IllegalArgumentException if the playing field is larger than the panel
	 */
	public Controller(NIP nip, GraphicsPanel panel, int width, int height) {
		if (width > panel.getWidth() || height > panel.getHeight())
			throw new IllegalArgumentException("Playing field is too large to fit within panel");
		
		panel.clear();
		
		this.nip = nip;
		this.panel = panel; // These initializations are of upmost importance; many of the
		this.width = width; // below initializations do not work without these.
		this.height = height;
		
		numNonPenaltyBricks = 0;
		bricks = new LinkedList<Brick>();
		random = new Random();

		genBoundaries(10); makePaddles(left, right, 20);
		genBricks();
		addPlayerStats();
		
		startCounter = 0;
		isRunning = true; pauseDisabled = false;
		Clock.instance().start();
		Clock.instance().getTimer().addActionListener(this);
		addMessageP1("Game start in ", 0, START_TIME, true);
		addMessageP2("Game start in ", 0, START_TIME, true);
		
	}
	
	/**
	 * @return the Y coordinate of the top of the playing field
	 */
	public int getTopBound() { return (panel.getHeight()-height)/2; }
	
	/**
	 * @return the Y coordinate of the bottom of the playing field
	 */
	public int getBotBound() { return (panel.getHeight()+height)/2; }
	
	/**
	 * @return the X coordinate of the left of the playing field
	 */
	public int getLeftBound() { return (panel.getWidth()-width)/2; }
	
	/**
	 * @return the X coordinate of the right of the playing field
	 */
	public int getRightBound() { return (panel.getWidth()+width)/2; }

	/**
	 * Generates paddles for both players at the center of the left and right sides of the boundaries,
	 * if they exist.  Be sure to make paddleWidth greater than than the boundaries' width!
	 * 
	 * @param left - left boundary
	 * @param right - right boundary
	 * @param paddleWidth - number of pixels wide
	 */
	private void makePaddles(Boundary left, Boundary right, int paddleWidth) {
		paddleP1 = new Paddle(
				new Point(left.getCenter().getX()-left.getWidth()/2+paddleWidth/2, panel.getHeight()/2),
				paddleWidth,
				DEFAULT_PAD_SIZE + Player.PAD_SIZE_INCREMENT * Main.player1.numPadSizeUps,
				getTopBound() + top.getHeight(),
				getBotBound() - floor.getHeight(),
				Main.player1);
		padVizP1 = new RectViz(paddleP1, Main.player1.color, panel);
		panel.moveToFront(padVizP1.graphic);
		paddleP1Mover = new PieceMover(paddleP1, new Stationary( paddleP1.getCenter() ));
		
		paddleP2 = new Paddle(
				new Point(right.getCenter().getX()+right.getWidth()/2-paddleWidth/2, panel.getHeight()/2),
				paddleWidth,
				DEFAULT_PAD_SIZE + Player.PAD_SIZE_INCREMENT * Main.player2.numPadSizeUps,
				getTopBound() + top.getHeight(),
				getBotBound() - floor.getHeight(),
				Main.player2);
		padVizP2 = new RectViz(paddleP2, Main.player2.color, panel);
		panel.moveToFront(padVizP2.graphic);
		paddleP2Mover = new PieceMover(paddleP2, new Stationary( paddleP2.getCenter() ));
	}

	/**
	 * Generates the boundaries for the game.  The playing field will be centered on the GraphicsPanel
	 * @param pad - number of pixels thick
	 */
	private void genBoundaries(int pad) {
		top   = new Boundary(
				new Point(panel.getWidth()/2, getTopBound()+pad/2),
				width, pad);
		new RectViz(top, Color.darkGray, panel);
		
		floor = new Boundary(
				new Point(panel.getWidth()/2, getBotBound()-pad/2),
				width, pad);
		new RectViz(floor, Color.darkGray, panel);
		
		left  = new Boundary(
				new Point(getLeftBound()+pad/2, panel.getHeight()/2),
				pad, height);
		boundVizP1 = new RectViz(left, Color.yellow, panel);
		
		right = new Boundary(
				new Point(getRightBound()-pad/2, panel.getHeight()/2),
				pad, height);
		boundVizP2 = new RectViz(right, Color.yellow, panel);
	}
	
	/**
	 * Adds player labels and relevant player statistics to the panel.  Initializes p1LabelBound.
	 */
	private void addPlayerStats() {
		Text p1Label = new Text("");
		p1Label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
		p1Label.setText("Player  1");
		p1LabelBound = getLeftBound() - p1Label.getWidth() - 40;
		p1Label.setLocation(p1LabelBound, getTopBound() + 10);
		p1Label.setForeground(Main.player1.color);
		panel.add(p1Label);
		
		scoreP1 = new Text("");
		scoreP1.setLocation(p1LabelBound, getTopBound() + 50);
		scoreP1.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
		panel.add(scoreP1);
		
		Image iconInvinciP1 = new Image(40, 40);
		iconInvinciP1.loadImage("BrickBreaker_2p/icons/InvinciUpP1.jpg");
		iconInvinciP1.setLocation(p1LabelBound, getTopBound() + 100);
		iconInvinciP1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(iconInvinciP1);
		
		numInvinciP1 = new Text("");
		numInvinciP1.setLocation(p1LabelBound + 50, getTopBound() + 108);
		numInvinciP1.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		panel.add(numInvinciP1);
		
		Image iconStickyP1 = new Image(40, 40);
		iconStickyP1.loadImage("BrickBreaker_2p/icons/StickyUpP1.jpg");
		iconStickyP1.setLocation(p1LabelBound, getTopBound() + 160);
		iconStickyP1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(iconStickyP1);
		
		numStickyP1 = new Text("");
		numStickyP1.setLocation(p1LabelBound + 50, getTopBound() + 168);
		numStickyP1.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		panel.add(numStickyP1);
		
		Text p2Label = new Text("");
		p2Label.setLocation(getRightBound() + 20, getTopBound() + 10);
		p2Label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
		p2Label.setText("Player  2");
		p2Label.setForeground(Main.player2.color);
		panel.add(p2Label);
		
		scoreP2 = new Text("");
		scoreP2.setLocation(getRightBound() + 20, getTopBound() + 50);
		scoreP2.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
		panel.add(scoreP2);
		
		Image iconInvinciP2 = new Image(40, 40);
		iconInvinciP2.loadImage("BrickBreaker_2p/icons/InvinciUpP2.jpg");
		iconInvinciP2.setLocation(getRightBound() + 20, getTopBound() + 100);
		iconInvinciP2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(iconInvinciP2);
		
		numInvinciP2 = new Text("");
		numInvinciP2.setLocation(getRightBound() + 70, getTopBound() + 108);
		numInvinciP2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		panel.add(numInvinciP2);
		
		Image iconStickyP2 = new Image(40, 40);
		iconStickyP2.loadImage("BrickBreaker_2p/icons/StickyUpP2.jpg");
		iconStickyP2.setLocation(getRightBound() + 20, getTopBound() + 160);
		iconStickyP2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(iconStickyP2);
		
		numStickyP2 = new Text("");
		numStickyP2.setLocation(getRightBound() + 70, getTopBound() + 168);
		numStickyP2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		panel.add(numStickyP2);
		
		updateScores();
		updatePowerUps();
	}
	
	/**
	 * Updates player score counters if they are present
	 */
	public void updateScores() {
		if (scoreP1 != null)
			scoreP1.setText("Score: " + Main.player1.score);
		if (scoreP2 != null)
			scoreP2.setText("Score: " + Main.player2.score);
	}
	
	/**
	 * Updates counters for the number of powerups each player has
	 */
	public void updatePowerUps() {
		if (numInvinciP1 != null)
			numInvinciP1.setText("( " +  (char)Main.player1.invinciKey + " )   x" + Main.player1.numInvinciUps);
		if (numStickyP1 != null)
			numStickyP1.setText("( " + (char)Main.player1.stickyKey + " )   x" + Main.player1.numStickyUps);
		if (numInvinciP2 != null)
			numInvinciP2.setText("( " + (char)Main.player2.invinciKey + " )   x" + Main.player2.numInvinciUps);
		if (numStickyP2 != null)
			numStickyP2.setText("( " + (char)Main.player2.stickyKey + " )   x" + Main.player2.numStickyUps);
	}
	
	
	/**
	 * Generates a brick with a 40% chance of generating a ToughBrick and 60% chance of
	 * generating a regular brick.
	 * @param p - center of the brick to be generated
	 * @param width - width of the brick
	 * @param height - height of the brick
	 */
	private void makeRandomBrick(Point p, int width, int height) {
		Brick b = null;
		int x = random.nextInt(9);
		if (x < 3) {
			b = new ToughBrick(p,width,height);
			new RectViz(b, Color.ORANGE, panel);
			bricks.add(b);
		}
		else {
			b = new Brick(p,width,height);
			new RectViz(b, Color.RED, panel);
			bricks.add(b);
		}
	}
	
	private void makeBrick(Point p, int width, int height) {
		Brick b = new Brick(p,width,height);
		new RectViz(b, Color.RED, panel);
		bricks.add(b);
	}
	
	private void makeToughBrick(Point p, int width, int height) {
		Brick b = new ToughBrick(p,width,height);
		new RectViz(b, Color.ORANGE, panel);
		bricks.add(b);
	}
	
	private void makeBombBrick(Point p, int width, int height) {
		Brick b = new BombBrick(p,width,height,50.0);
		new RectViz(b, Color.BLACK, panel);
		bricks.add(b);
	}
	
	private void makePenaltyBrick(Point p, int width, int height) {
		Brick b = new PenaltyBrick(p, width, height);
		new RectViz(b, Color.CYAN, panel);
		bricks.add(b);
	}
	
	/**
	 * Generates a block of bricks, with ToughBricks in the middle.  Four randomly selected bricks
	 * will be a BombBricks and PenaltyBricks, one on each side of the
	 * playing field.
	 */
	private void genBricks() { //FIXME
		int bwidth = width/3 / numBricksHoriz;
		int bheight = (int) ((floor.getCenter().getY() - floor.getHeight()/2 - top.getCenter().getY()) / numBricksVert);
		int x = (int) (left.getCenter().getX()+width/3);
		
		int bombBrickIdP1 = random.nextInt(2 * numBricksVert);
		int penaltyBrickIdP1 = random.nextInt(2 * numBricksVert);
		if (bombBrickIdP1 == penaltyBrickIdP1) bombBrickIdP1 = random.nextInt(2 * numBricksVert);
		
		int bombBrickIdP2 = random.nextInt(2 * numBricksVert) + 30;
		int penaltyBrickIdP2 = random.nextInt(2 * numBricksVert) + 30;
		if (bombBrickIdP2 == penaltyBrickIdP2) bombBrickIdP2 = random.nextInt(2 * numBricksVert) + 30;
		
		int brickID = 0;
		for (int i=0; i < numBricksHoriz; ++i) {
			int y = (int) (top.getCenter().getY() + top.getHeight()/2 + bheight/2);
			for (int j=0; j < numBricksVert; ++j) {
				Point p = new Point(x,y);
				if ( i==2 ) {
					makeToughBrick(p,bwidth,bheight);
					numNonPenaltyBricks++;
				}
				else if ( (brickID == bombBrickIdP1) || (brickID == bombBrickIdP2)) {
					makeBombBrick(p,bwidth,bheight);
					numNonPenaltyBricks++;
				}
				else if ( (brickID == penaltyBrickIdP1) || (brickID == penaltyBrickIdP2)) {
					makePenaltyBrick(p,bwidth,bheight);
				}
				else {
					makeBrick(p,bwidth,bheight);
					numNonPenaltyBricks++;
				}
				y += bheight;
				brickID++;
			}
			x += bwidth;
		}
	}

	/**
	 * Generate a new Ball at Player 1's paddle that will travel straight away from it at
	 * a speed dependent on the number of ballSpeedUpgrades
	 */
	public void genBallP1() {
		ballP1 = new Ball(
				new Point(
						paddleP1.getCenter().getX() + paddleP1.getWidth()/2 + BALL_RADIUS + 5,
						paddleP1.getCenter().getY()
						),
				BALL_RADIUS,
				Main.player1);

		new CircViz(ballP1, Main.player1.color, panel);

		PieceMover ballMover = new PieceMover(
				ballP1,
				new ConstantVelocity( ballP1.getCenter(), new Vector(DEFAULT_BALL_SPEED + 100 * Main.player1.numBallSpeedUps, 0) )
				);
				

		new BallHitsBoundary(ballP1, top,  ballMover, this);
		new BallHitsBoundary(ballP1, left, ballMover, this);
		new BallHitsBoundary(ballP1, right,ballMover, this);
		new BallHitsBoundary(ballP1, floor, ballMover, this);

		new BallHitsPaddle(ballP1, paddleP1, ballMover, this);
		new BallHitsPaddle(ballP1, paddleP2, ballMover, this);

		for (Brick brick : bricks) {
			new BallHitsBrick(ballP1, brick, ballMover, this);
		}
	}
	
	/**
	 * Generate a new Ball at Player 2's paddle that will travel straight away from it at
	 * a speed dependent on the number of ballSpeedUpgrades
	 */
	public void genBallP2() {
		ballP2 = new Ball (
				new Point(
						paddleP2.getCenter().getX() - paddleP2.getWidth()/2 - BALL_RADIUS - 5,
						paddleP2.getCenter().getY()
						),
				BALL_RADIUS,
				Main.player2);

		new CircViz(ballP2, Main.player2.color, panel);

		PieceMover ballMover = new PieceMover(
				ballP2, 
				new ConstantVelocity( ballP2.getCenter(), new Vector(-DEFAULT_BALL_SPEED - 100 * Main.player2.numBallSpeedUps, 0) )
				);
				

		new BallHitsBoundary(ballP2, top,  ballMover, this);
		new BallHitsBoundary(ballP2, left, ballMover, this);
		new BallHitsBoundary(ballP2, right,ballMover, this);
		new BallHitsBoundary(ballP2, floor, ballMover, this);

		new BallHitsPaddle(ballP2, paddleP1, ballMover, this);
		new BallHitsPaddle(ballP2, paddleP2, ballMover, this);

		for (Brick brick : bricks) {
			new BallHitsBrick(ballP2, brick, ballMover, this);
		}
	}
	
	
	/**
	 * Generates a ball that will not collide with any bricks.  This method does nothing for now...
	 * 
	 * @param center - starting point of the ball
	 * @param player - used to determine the speed of the ball, depending on the player's ballSpeedUpgrades
	 * @param degreeRange
	 */
	/*
	public void genPhaseBall(Point center, Player player, int degreeRange) { // FIXME
		
		boolean degreeRangeIsNegative = (degreeRange < 0);
		if (degreeRangeIsNegative)
			degreeRange = degreeRange * -1; //This is so random.nextInt will accept degreeRange.
		
		int angle = random.nextInt(degreeRange);
		if (random.nextBoolean())
			angle = angle * -1; // Randomly assign the angle to be above or below the x axis
		double vertComponent = Math.tan(angle * (Math.PI/180));
		
		Vector newDirection;
		if (degreeRangeIsNegative)
			newDirection = new Vector(-1, vertComponent);
		else
			newDirection = new Vector(1, vertComponent);
		newDirection.rescale(DEFAULT_BALL_SPEED + 100 * player.numBallSpeedUps);
		

		Ball ball = new Ball (
				center,
				BALL_RADIUS,
				null);
		
		new PieceMover( ball, new ConstantAcceleration(center, newDirection, new Vector(0,0)));					
	}
	*/
	
	public void genNewBall(Point center, boolean goesRight) {
		Vector direction;
		if (goesRight)
			direction = new Vector(DEFAULT_BALL_SPEED + 200, 0);
		else
			direction = new Vector(-DEFAULT_BALL_SPEED - 200, 0);
		
		Ball ball = new Ball (
				center,
				BALL_RADIUS,
				null);
		
		PieceMover ballMover = new PieceMover( ball, new ConstantVelocity(center, direction) );
		new CircViz( ball, Color.GREEN, panel);
		
		new BallHitsBoundary(ball, top,  ballMover, this);
		new BallHitsBoundary(ball, left, ballMover, this);
		new BallHitsBoundary(ball, right,ballMover, this);
		new BallHitsBoundary(ball, floor, ballMover, this);

		new BallHitsPaddle(ball, paddleP1, ballMover, this);
		new BallHitsPaddle(ball, paddleP2, ballMover, this);
		
			
	}
	
	/**
	 * Adds a temporary message next to Player 1's paddle
	 * @param message
	 * @param yDisplaceFromCenter - pixels from the center of the paddle.  Negative values
	 * move the text up; positive values move the text down
	 * @param duration - duration in milliseconds
	 * @param hasCounter
	 * 
	 * @return the text that was added to the controller's graphics panel
	 */
	public TempText addMessageP1(String message, int yDisplaceFromCenter, int duration, boolean hasCounter) {
		TempText text = new PieceText(message, duration, panel, paddleP1,
				paddleP1.getWidth()/2 + 20, yDisplaceFromCenter, false);
		if (hasCounter)
			text.addCounter();
		text.setForeground(Main.player1.color);
		return text;
	}
	
	/**
	 * Adds a temporary message next to Player 2's paddle
	 * @param message
	 * @param yDisplaceFromCenter - pixels from the center of the paddle.  Negative values
	 * move the text up; positive values move the text down
	 * @param duration - duration in milliseconds
	 * @param hasCounter
	 * 
	 * @return the text that was added to the controller's graphics panel
	 */
	public TempText addMessageP2(String message, int yDisplaceFromCenter, int duration, boolean hasCounter) {
		Text temp = new Text(message);
		TempText text = new PieceText(message, duration, panel, paddleP2,
				-paddleP2.getWidth()/2 - 20 - temp.getWidth(), yDisplaceFromCenter, false);
		if (hasCounter)
			text.addCounter();
		text.setForeground(Main.player2.color);
		return text;
	}
	

	/**
	 * Generates an explosion ball which will fade away.  Will not kill any bricks.
	 */
	public void explosion(Point center, double explosionRadius, Color color) {
		Ball explosion = new ExplosionBall(center, explosionRadius);
		new CircViz(explosion, color, panel);
	}
	
	/**
	 * Generates an explosion ball which will fade away and kill any bricks it touches,
	 * awarding any points from those bricks to the specified owner.
	 * 
	 * @param center
	 * @param explosionRadius
	 * @param color - color of the explosion
	 * @param owner - the player that will recieve points from any bricks that die
	 */
	public void explosion(Point center, double explosionRadius, Color color, Player owner) {
		Ball explosion = new ExplosionBall(center, explosionRadius, owner);
		
		new CircViz(explosion, color, panel);
		
		PieceMover ballMover = new PieceMover(
				explosion, 
				new ConstantVelocity(
					explosion.getCenter(), 
					new Vector(0,1)
					)
				);
		
		for (Brick brick : bricks)
			new BallHitsBrick(explosion, brick, ballMover, this);
	}
	
	/**
	 * Sets a constant velocity for Player 1's paddle
	 * @param v
	 */
	public void setPlayer1PaddleVelocity(Vector v) {
		paddleP1Mover.setTrajectory(new ConstantVelocity
				(paddleP1.getCenter(), v)
		);
	}
	
	/**
	 * Sets a constant velocity for Player 2's paddle
	 * @param v
	 */
	public void setPlayer2PaddleVelocity(Vector v) {
		paddleP2Mover.setTrajectory(new ConstantVelocity
				(paddleP2.getCenter(), v)
		);
	}
	
	/**
	 * Stops the clock's timer and adds a pause panel to the GraphicsPanel, or removes the panel
	 * and resumes the timer.
	 */
	public void togglePause() {
		if (pauseDisabled)
			return;
		
		if (pausePanel == null) { // Initialize the pausePanel if it has not been before
			pausePanel = new JPanel();
			pausePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
			pausePanel.setLayout(new BoxLayout(pausePanel, BoxLayout.Y_AXIS));
			
			pausePanel.add(Box.createVerticalGlue());
			
			Text pausedText = new Text("PAUSED");
			pausedText.setFont(new Font("Arial", Font.BOLD, 20));
			pausedText.setAlignmentX(Component.CENTER_ALIGNMENT);
			pausePanel.add(pausedText);
			
			Text continueText = new Text("Press the spacebar to continue");
			continueText.setFont(new Font("Arial", Font.PLAIN, 14));
			continueText.setAlignmentX(Component.CENTER_ALIGNMENT);
			pausePanel.add(continueText);
			
			pausePanel.add(Box.createVerticalGlue());
			
			int width = (int) pausePanel.getPreferredSize().getWidth();
			int height = (int) pausePanel.getPreferredSize().getHeight();
			
			pausePanel.setPreferredSize( new Dimension(width + 20, height + 20) );
			pausePanel.setSize(pausePanel.getPreferredSize());
			pausePanel.setLocation(panel.getWidth()/2 - (width+20)/2, panel.getHeight()/2 - (height+20)/2);
			
			pausePanel.setOpaque(true);
			pausePanel.setVisible(true);
		}
		
		if (isRunning) {
			panel.add(pausePanel);
			panel.moveToFront(pausePanel);
			Clock.instance().stop();
			isRunning = false;
		}
		else {
			panel.remove(pausePanel);
			Clock.instance().start();
			isRunning = true;
		}
	}
	
	/**
	 * If Player 1 has enough invincibility powerups, sets Player 1's
	 * boundary to be magenta and to always bounce back balls for a time specified by <code>INVINCI_DURATION</code>.
	 */
	public void startInvinciP1 () {
		if ( (!left.ballsAlwaysBounce()) && (Main.player1.numInvinciUps > 0) ) {
			TempText counter = new TempText("", INVINCI_DURATION, panel);
			counter.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
			counter.addCounter();
			counter.setLocation( (int)left.getCenter().getX() - left.getWidth()/2 - counter.getWidth() - 10, (int)left.getCenter().getY() - counter.getHeight()/2);
			Main.player1.numInvinciUps--;
			updatePowerUps();
			left.setAlwaysBounce(INVINCI_DURATION);
			boundVizP1.setNewColor(Color.MAGENTA, INVINCI_DURATION);
		}

	}
	
	/**
	 * If Player 2 has enough invincibility powerups, sets Player 2's
	 * boundary to be magenta and to always bounce back balls for a time specified by <code>INVINCI_DURATION</code>.
	 */
	public void startInvinciP2 () {
		if ( (!right.ballsAlwaysBounce()) && (Main.player2.numInvinciUps > 0) ) {
			TempText counter = new TempText("", INVINCI_DURATION, panel);
			counter.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
			counter.addCounter();
			counter.setLocation( (int)right.getCenter().getX() + right.getWidth()/2 + 10, (int)right.getCenter().getY() - counter.getHeight()/2);
			Main.player2.numInvinciUps--;
			updatePowerUps();
			right.setAlwaysBounce(INVINCI_DURATION);
			boundVizP2.setNewColor(Color.MAGENTA, INVINCI_DURATION);
		}

	}
	
	/**
	 * Makes player 1's paddle catch balls, or launch a caught ball.
	 */
	public void activateStickyP1 () {
		if (paddleP1.hasBallStuck())
			paddleP1.collisionDetector.launchBall();
		else if ( (!paddleP1.isSticky) && (Main.player1.numStickyUps > 0) ) {
			paddleP1.isSticky = true;
			padVizP1.setNewColor(Color.MAGENTA, 0);
			panel.moveToFront(padVizP1.graphic);
			Main.player1.numStickyUps--;
			updatePowerUps();
		}
	}
	
	/**
	 * Makes player 2's paddle catch balls, or launch a caught ball.
	 */
	public void activateStickyP2 () {
		if (paddleP2.hasBallStuck())
			paddleP2.collisionDetector.launchBall();
		if ( (!paddleP2.isSticky) && (Main.player2.numStickyUps > 0) ) {
			paddleP2.isSticky = true;
			padVizP2.setNewColor(Color.MAGENTA, 0);
			panel.moveToFront(padVizP2.graphic);
			Main.player2.numStickyUps--;
			updatePowerUps();
		}
	}
	
	/**
	 * Resets a paddle's color to the default color (red for player 1, blue for player 2)
	 * @param paddle - the paddle whose color to reset
	 */
	public void resetPaddleColor(Paddle paddle) {
		if (paddle.getOwner().getId() == 1) {
			padVizP1.setNewColor(Main.player1.color, 0);
			panel.moveToFront(padVizP1.graphic);
		}
		else if (paddle.getOwner().getId() == 2) {
			padVizP2.setNewColor(Main.player2.color, 0);
			panel.moveToFront(padVizP2.graphic);
		}
	}
	
	/**
	 * Subtracts one from this controller's counter of non-penalty bricks left.
	 * If all of them are dead, brings up an option to continue to the
	 * upgrade screen.
	 */
	public void countNonPenaltyBrickDeath() {
		numNonPenaltyBricks--;
		
		if (numNonPenaltyBricks <= 0) {
			JPanel continuePanel = new JPanel();
			continuePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
			continuePanel.setLayout(new BoxLayout(continuePanel, BoxLayout.Y_AXIS));
			
			continuePanel.add(Box.createVerticalGlue());
			
			Text text = new Text("Level complete");
			text.setFont(new Font("Arial", Font.PLAIN, 22));
			text.setAlignmentX(Component.CENTER_ALIGNMENT);
			continuePanel.add(text);
			
			continuePanel.add(Box.createRigidArea(new Dimension(10, 10)));
			
			JButton button = new JButton("Continue");
			button.setAlignmentX(Component.CENTER_ALIGNMENT);
			button.setActionCommand("Upgrade screen");
			button.addActionListener(this);
			continuePanel.add(button);
			
			continuePanel.add(Box.createVerticalGlue());
			
			int width = (int) continuePanel.getPreferredSize().getWidth();
			int height = (int) continuePanel.getPreferredSize().getHeight();
			
			continuePanel.setPreferredSize( new Dimension(width + 20, height + 20) );
			continuePanel.setSize(continuePanel.getPreferredSize());
			continuePanel.setLocation(panel.getWidth()/2 - (width+20)/2, panel.getHeight()/2 - (height+20)/2);
			
			continuePanel.setOpaque(true);
			continuePanel.setVisible(true);
			
			pauseDisabled = true;
			Clock.instance().stop();
			panel.add(continuePanel);
			panel.moveToFront(continuePanel);
		}
	}

	/**
	 * Kills all boundaries, paddles, balls, and bricks
	 */
	private void killAll() {
		top.die(); floor.die(); right.die(); left.die();
		paddleP1.die(); paddleP2.die();
		ballP1.die(); ballP2.die();
		for (Brick brick : bricks) {
			brick.die();
		}
	}
	
	/**
	 * Steps the game some number of ticks.
	 * @param numTicks the number of ticks to step the game
	 */
	public void tick(int numTicks) {
		for (int i=0; i < numTicks; ++i) {
			Clock.instance().tick();
		}
	}

	/**
	 * Starts the game after a delay, or moves the game to the upgrade screen
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Upgrade screen") {
			new UpgradePanel(nip);
			killAll();
		}
		else {
			startCounter += Clock.instance().getTimeQuantum();
			if (startCounter >= START_TIME) {
				genBallP1(); genBallP2();
				Clock.instance().getTimer().removeActionListener(this);
				startCounter = 0;
			}
		}
		
		
	}
	
}
