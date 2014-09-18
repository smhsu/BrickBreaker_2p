package game.collision;

import game.*;
import game.motion.*;
import game.pieces.Ball;
import game.pieces.BombBrick;
import game.pieces.Brick;
import game.pieces.GenBallBrick;
import game.pieces.PenaltyBrick;
import game.pieces.ToughBrick;

import java.awt.Color;
import java.util.*;

/**
 * Collision handler for a ball and a brick.
 * 
 * @author Ron Cytron, with changes by Jon Turner
 */
public class BallHitsBrick extends CollisionHandler {
	
	Ball ball;  		// the particular ball being monitored
	Brick brick;  	// the brick being monitored
	PieceMover ballMover;  // the object that moves the ball
	Controller controller;
	
	private static int SCORE_TEXT_DURATION = 1000; // In milliseconds
	
	/**
	 * Constructor for BallHitsBrick
	 * 
	 * @param ball the ball being monitored
	 * @param brick the brick hit by the ball
	 * @param ballMover the object responsible for moving the ball
	 */
	public BallHitsBrick(Ball ball, Brick brick, PieceMover ballMover, Controller controller) {
		super(ball, brick);
		this.ball = ball; this.brick = brick;	
		this.ballMover = ballMover;
		this.controller = controller;
	}
	
	/**
	 * Adds or subtracts points to the ball's owner based on the monitored brick's value and adds text to the
	 * controller's panel telling how many points were added or subtracted.
	 */
	private void addScore() {
		ball.getOwner().score += brick.getValue();
		ball.getOwner().money += brick.getValue();
		controller.updateScores();
		
		TempText plusScore;
		if (brick.getValue() >= 0)
			plusScore = new TempText("+" + brick.getValue(), SCORE_TEXT_DURATION, controller.panel);
		else
			plusScore = new TempText("-" + -brick.getValue(), SCORE_TEXT_DURATION, controller.panel);
		plusScore.setLocation( (int)brick.getCenter().getX(), (int)brick.getCenter().getY() );
		plusScore.setForeground(ball.getOwner().color);
		controller.panel.moveToFront(plusScore);
	}
	
	/**
	 * Adds or subtracts points to the ball's owner and adds text to the
	 * controller's panel telling how many points were added or subtracted
	 * 
	 * @param score - number of points to add.  A negative value will subtract points
	 */
	private void addScore(int score) {
		ball.getOwner().score += score;
		ball.getOwner().money += score;
		controller.updateScores();
		
		TempText plusScore;
		if (score >= 0)
			plusScore = new TempText("+" + score, SCORE_TEXT_DURATION, controller.panel);
		else
			plusScore = new TempText("-" + -score, SCORE_TEXT_DURATION, controller.panel);
		plusScore.setLocation( (int)brick.getCenter().getX(), (int)brick.getCenter().getY() );
		plusScore.setForeground(ball.getOwner().color);
		controller.panel.moveToFront(plusScore);
	}
	
	/**
	 * This method is called when collisions occur and responds appropriately.
	 */
	@Override
	public void observeEvent(Collision c) {
		
		if (ball.getCenter().getX() < brick.getCenter().getX() - brick.getWidth()/2) //Left side of the brick
			ballMover.bounce(-1, 1);
		else if (ball.getCenter().getX() > brick.getCenter().getX() + brick.getWidth()/2) // Right side of the brick
			ballMover.bounce(-1, 1);
		else if (ball.getCenter().getY() > brick.getCenter().getY() + brick.getHeight()/2) // Bottom of the brick
			ballMover.bounce(1, -1);
		else if (ball.getCenter().getY() < brick.getCenter().getY() - brick.getHeight()/2) // Top of the brick
			ballMover.bounce(1, -1); 			
		
		if (brick instanceof ToughBrick) {
			brick.augmentLifeStatus(-0.5);
			addScore();
			if (brick.isDead())
				controller.countNonPenaltyBrickDeath();
		}
		else if (brick instanceof BombBrick) {
			controller.explosion(brick.getCenter(), ((BombBrick) brick).explosionRadius, Color.RED, ball.getOwner());
			brick.die();
			addScore();
			controller.countNonPenaltyBrickDeath();
		}
		else if (brick instanceof PenaltyBrick) {
			brick.die();
			if (ball.getRadius() > Controller.BALL_RADIUS) // Must be an explosionBall
				addScore(10);
			else
				addScore();
		}
		else {
			brick.die();
			addScore();
			controller.countNonPenaltyBrickDeath();
		}
	}
}
