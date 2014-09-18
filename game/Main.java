package game;

import game.time.Clock;

import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import lab3.Vector;

import nip.*;

/**
 * This class adapts mouse and keyboard actions and tells the Controller what to do.
 * 
 * @author Ron Cytron, with changes by Silas Hsu
 */
public class Main extends Tool implements KeyListener {

	public Controller controller;
	public static final int CONTROLLER_WIDTH = 1000;
	public static final int CONTROLLER_HEIGHT = 500;
	public static final Player player1 = new Player(1);
	public static final Player player2 = new Player(2);
	
	/**
	 * Creates an object that adapts mouse, keyboard, and menu actions
	 */
	public Main() {
		
		player1.color = Color.RED;
		player1.paddleDownKey = KeyEvent.VK_Z;
		player1.paddleUpKey = KeyEvent.VK_C;
		player1.invinciKey = KeyEvent.VK_Q;
		player1.stickyKey = KeyEvent.VK_E;
		
		player2.color = Color.BLUE;
		player2.paddleDownKey = KeyEvent.VK_SLASH;
		player2.paddleUpKey = KeyEvent.VK_SHIFT;
		player2.invinciKey = KeyEvent.VK_OPEN_BRACKET;
		player2.stickyKey = KeyEvent.VK_BACK_SLASH;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == player1.paddleUpKey) {
			controller.setPlayer1PaddleVelocity(new Vector(0, -Controller.DEFAULT_PAD_SPEED - Player.PAD_SPEED_INCREMENT * player1.numPadSpeedUps));
			return;
		}
		else if (e.getKeyCode() == player1.paddleDownKey) {
			controller.setPlayer1PaddleVelocity(new Vector(0, Controller.DEFAULT_PAD_SPEED + Player.PAD_SPEED_INCREMENT * player1.numPadSpeedUps));
			return;
		}
		else if (e.getKeyCode() == player1.invinciKey) {
			controller.startInvinciP1();
		}
		else if (e.getKeyCode() == player1.stickyKey) {
			controller.activateStickyP1();
		}
		
		
		else if (e.getKeyCode() == player2.paddleUpKey) {
			controller.setPlayer2PaddleVelocity(new Vector(0, -Controller.DEFAULT_PAD_SPEED - Player.PAD_SPEED_INCREMENT * player2.numPadSpeedUps));
			return;
		}
		else if (e.getKeyCode() == player2.paddleDownKey) {
			controller.setPlayer2PaddleVelocity(new Vector(0, Controller.DEFAULT_PAD_SPEED + Player.PAD_SPEED_INCREMENT * player1.numPadSpeedUps));
			return;
		}
		else if (e.getKeyCode() == player2.invinciKey) {
			controller.startInvinciP2();
		}
		else if (e.getKeyCode() == player2.stickyKey) {
			controller.activateStickyP2();
		}

		else if (e.getKeyCode() == KeyEvent.VK_SPACE)
			controller.togglePause();
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == player1.paddleUpKey) {
			controller.setPlayer1PaddleVelocity(new Vector(0, 0));
			return;
		}
		else if (e.getKeyCode() == player1.paddleDownKey) {
			controller.setPlayer1PaddleVelocity(new Vector(0, 0));
			return;
		}
		else if (e.getKeyCode() == player2.paddleUpKey) {
			controller.setPlayer2PaddleVelocity(new Vector(0, 0));
			return;
		}
		else if (e.getKeyCode() == player2.paddleDownKey) {
			controller.setPlayer2PaddleVelocity(new Vector(0, 0));
			return;
		}
	}

	public String toString() {
		return "game";
	}

	private void tick(int num) {
		controller.tick(num);
	}

	@Override
	public void actionNameCalled(String name) {
		if (name.equals("toggle pause")) {
			controller.togglePause();
		}
		if (name.equals("single tick"))
			tick(1);
		if (name.equals("ten ticks"))
			tick(10);
		if (name.equals("hundred ticks"))
			tick(100);
		if (name.equals("add ball")) {
			controller.genBallP1();
			controller.genBallP2();
		}

	}

	@Override
	public String[] getEventNames() {
		return new String[] { "toggle pause" , "single tick",
				"ten ticks", "hundred ticks", "add ball" };
	}

	public static void main(String args[]) {
		NIP nip = new NIP(1300, 520); // Preferred size of the UpgradePanel: 634, 686
		nip.setStatusText("");
		Main m = new Main();
		nip.setTool(m);
		
		new UpgradePanel(nip);
	}

}
