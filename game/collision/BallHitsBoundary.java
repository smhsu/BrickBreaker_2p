package game.collision;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import nip.Text;

import game.*;
import game.motion.*;
import game.pieces.Ball;
import game.pieces.Boundary;
import game.pubsub.Subscriber;
import game.time.Clock;
import game.time.ClockTick;

/**
 * Collision handler for a ball and a boundary.  Also handles generation of new balls if
 * they die.
 * 
 * @author Ron Cytron, with changes by Jon Turner, then changes by Silas Hsu
 */
public class BallHitsBoundary extends CollisionHandler implements ActionListener {
	
	private Ball ball;		// the particular ball being monitored
	private Boundary bound;	// the boundary hit by the ball
	private PieceMover ballMover;	// the object that moves the ball
	private Controller controller;
	private int timeSinceBallDeath; // in milliseconds

	/**
	 * Constructor for BallHitsBoundary
	 * 
	 * @param ball the ball being monitored
	 * @param bound the boundary hit by the ball
	 * @param ballMover the object responsible for moving the ball
	 */
	public BallHitsBoundary(Ball ball, Boundary bound, PieceMover ballMover, Controller controller) {
		super(ball, bound);
		this.ball = ball; this.bound = bound;
		this.ballMover = ballMover;
		this.controller = controller;
	}

	/**
	 * This method is called when collisions occur and responds
	 * appropriately. Uses ballMover's bounce method to change the
	 * ball's direction of motion appropriately.
	 */
	@Override
	public void observeEvent(Collision e) {
		if (bound.ballsAlwaysBounce()) { // Only the left and right boundaries are ever set to always bounce back
			ballMover.bounce(-1, 1);
			return;
		}
		
		if (bound.getWidth() > bound.getHeight() ) { // this must be the ceiling or floor (assuming reasonable geometry)
			ballMover.bounce(1, -1);
			return;
		}
		else if (bound.getCenter().getX() - bound.getWidth() < ball.getCenter().getX() ) { // Player 1's boundary
			ball.die();
			Main.player2.score += ball.VALUE;
			Main.player2.money += ball.VALUE;
			TempText text = new TempText("Player 2 +" + ball.VALUE, 2000, controller.panel);
			text.setLocation((int)ball.getCenter().getX(), (int)ball.getCenter().getY());
			text.setForeground(Main.player2.color);
		}
		else if (bound.getCenter().getX() + bound.getWidth() > ball.getCenter().getX() ) { // Player 2's boundary
			ball.die();
			Main.player1.score += ball.VALUE;
			Main.player1.money += ball.VALUE;
			TempText text = new TempText("Player 1 +" + ball.VALUE, 2000, controller.panel);
			text.setLocation((int)(ball.getCenter().getX() - text.getWidth()), (int)ball.getCenter().getY());
			text.setForeground(Main.player1.color);
		}
			
		controller.updateScores();
		
		if (ball.getOwner() == null) {
			controller.explosion(ball.getCenter(), ball.getRadius()*2, Color.GREEN);
			return;
		}
		else if (ball.getOwner().getId() == 1) {
			controller.addMessageP1("New ball ", 0, ball.RESPAWN_DELAY, true);
			controller.explosion(ball.getCenter(), ball.getRadius()*2, Main.player1.color);
		}
		else if (ball.getOwner().getId() == 2) {
			controller.addMessageP2("New ball ", 0, ball.RESPAWN_DELAY, true);
			controller.explosion(ball.getCenter(), ball.getRadius()*2, Main.player2.color);
		}
		Clock.instance().getTimer().addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		timeSinceBallDeath += Clock.instance().getTimeQuantum();
		if (timeSinceBallDeath >= ball.RESPAWN_DELAY) {
			if (ball.getOwner().getId() == 1)
				controller.genBallP1();
			else if (ball.getOwner().getId() == 2)
				controller.genBallP2();
			Clock.instance().getTimer().removeActionListener(this);
		}
	}
}
