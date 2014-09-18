package game.collision;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import lab3.Vector;
import game.*;
import game.motion.*;
import game.pieces.Ball;
import game.pieces.Paddle;
import game.time.Clock;


/**
 * Collision handler for a ball and paddle.  Also handles sticky paddle implementation.
 */
public class BallHitsPaddle extends CollisionHandler implements ActionListener {

	private Ball ball;
	private Paddle paddle;
	private PieceMover ballMover;
	private Controller controller;
	
	private int launchCounter;
	private TempText launchCounterText;
	
	
	public BallHitsPaddle(Ball ball, Paddle paddle, PieceMover ballMover, Controller controller) {
		super(ball, paddle);
		this.ball = ball;
		this.paddle = paddle;
		this.ballMover = ballMover;
		this.controller = controller;
		launchCounter = 0;
	}
	
	
	/**
	 * If the ball is stuck to a paddle, launches the ball away at a speed dependent on the
	 * owner's ballSpeedUpgrades.
	 */
	public void launchBall() {
		if (ball.isStuckOnPaddle()) {
			
			ball.unstickFromPaddle();
			paddle.collisionDetector = null;
			
			if (paddle.getOwner().getId() == 1)
				ballMover.setTrajectory(new ConstantVelocity(
						ball.getCenter(),
						new Vector(Controller.DEFAULT_BALL_SPEED + 100 * ball.getOwner().numBallSpeedUps, 0)
						)
				);
			else if (paddle.getOwner().getId() == 2)
				ballMover.setTrajectory(new ConstantVelocity(
						ball.getCenter(),
						new Vector(-Controller.DEFAULT_BALL_SPEED + -100 * ball.getOwner().numBallSpeedUps, 0)
						)
				);
			
			Clock.instance().getTimer().removeActionListener(this);
			launchCounter = 0;
			launchCounterText.remove();
			launchCounterText = null;
		}
	}
	

	@Override
	public void observeEvent(Collision e) {
		if (ball.isStuckOnPaddle())
			return;
		
		if (paddle.isSticky) {
			paddle.isSticky = false;
			paddle.collisionDetector = this;
			ballMover.redirect(new Vector(0, 0), 0);
			ball.stickToPaddle(paddle);
			Clock.instance().getTimer().addActionListener(this);
			controller.resetPaddleColor(paddle);
			if (paddle.getOwner().getId() == 1) {
				launchCounterText = controller.addMessageP1("Launch in ", 20, Controller.STICKY_DURATION, true);
			}
			if (paddle.getOwner().getId() == 2) {
				launchCounterText = controller.addMessageP2("Launch in ", 20, Controller.STICKY_DURATION, true);
			}
		}
		
		else if (ball.getCenter().getX() > paddle.getCenter().getX()) { // If the ball is hitting player 1's paddle
			double distanceFromCenterOfPaddle = Math.abs(paddle.getCenter().getY() - ball.getCenter().getY());
			double deflectionAngle = (Math.PI/2) - (Math.PI/4) * (distanceFromCenterOfPaddle/(paddle.getHeight()/2));
			Vector newDirection = null;
			if (deflectionAngle == Math.PI/2) // tan is undefined for pi/2
				newDirection = new Vector (1, 0);
			else if (paddle.getCenter().getY() - ball.getCenter().getY() > 0) // If the ball is above the paddle
				newDirection = new Vector(Math.tan(deflectionAngle), -1);
			else if (paddle.getCenter().getY() - ball.getCenter().getY() < 0) // If the ball is below the paddle
				newDirection = new Vector(Math.tan(deflectionAngle), 1);
			ballMover.redirect(newDirection, 1);
		}
		else {
			if (ball.getCenter().getX() < paddle.getCenter().getX()) { // If the ball is hitting player 2's paddle
				double distanceFromCenterOfPaddle = Math.abs(paddle.getCenter().getY() - ball.getCenter().getY());
				double deflectionAngle = (Math.PI/2) - (Math.PI/4) * (distanceFromCenterOfPaddle/(paddle.getHeight()/2));
				Vector newDirection = null;
				if (deflectionAngle == Math.PI/2) // tan is undefined for pi/2
					newDirection = new Vector (-1, 0);
				else if (paddle.getCenter().getY() - ball.getCenter().getY() > 0) // If the ball is above the paddle
					newDirection = new Vector(-Math.tan(deflectionAngle), -1);
				else if (paddle.getCenter().getY() - ball.getCenter().getY() < 0) // If the ball is below the paddle
					newDirection = new Vector(-Math.tan(deflectionAngle), 1);
				ballMover.redirect(newDirection, 1);
			}
		}
		
		/*
	    This was the behavior for a paddle at the bottom of the screen.
	  
	  	double distanceFromCenterOfPaddle = Math.abs(paddle.getCenter().getX() - ball.getCenter().getX());
		double deflectionAngle = (Math.PI/2) - (Math.PI/4) * (distanceFromCenterOfPaddle/(paddle.getWidth()/2));
		Vector newDirection = null;
		if (deflectionAngle == Math.PI/2) // tan is undefined for pi/2
			newDirection = new Vector (0, -1);
		else if (paddle.getCenter().getX() - ball.getCenter().getX() > 0) // If the ball is left of the paddle
			newDirection = new Vector(-1, -Math.tan(deflectionAngle));
		else if (paddle.getCenter().getX() - ball.getCenter().getX() < 0) // If the ball is right of the paddle
			newDirection = new Vector(1, -Math.tan(deflectionAngle));
		ballMover.redirect(newDirection, 1);
	 */
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		launchCounter += Clock.instance().getTimeQuantum();
		if (launchCounter >= Controller.STICKY_DURATION) {
			launchBall();
		}	
	}
	
}
