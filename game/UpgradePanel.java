package game;

import game.pieces.BombBrick;

import java.awt.Image;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import javax.swing.border.LineBorder;

import nip.NIP;

import java.awt.Color;
import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This panel handles all the upgrade options for both players.  
 * 
 * Code based on WindowBuilder code, an Eclipse plugin.
 * 
 * @author Silas Hsu - December 28, 2012
 */
public class UpgradePanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	
	private final int ICON_SIZE = 50; // in pixels
	
	private final int PAD_SIZE_PRICE = 400;
	private final int PAD_SPEED_PRICE = 400;
	private final int BALL_SPEED_PRICE = 150;
	private final int INVINCI_PRICE = 200;
	private final int STICKY_PRICE = 200;
	private final int SCORE_PRICE = 300;
	private final int SCORE_BOOST_AMOUNT = 100;
	
	private JPanel upgradesP1; // Player 1's upgrade panel.  Should have a GridBagLayout.
	private JPanel upgradesP2; // Player 2's upgrade panel.  Should have a GridBagLayout.
	
	private JButton buyPadSizeUpP1;
	private JButton buyPadSizeUpP2;
	
	private JLabel scoreP1;
	private JLabel moneyP1;
	
	private JLabel scoreP2;
	private JLabel moneyP2;
	
	private JLabel numPadSizeUpsP1;
	private JLabel numPadSpeedUpsP1;
	private JLabel numBallSpeedUpsP1;
	private JLabel numInvinciUpsP1;
	private JLabel numStickyUpsP1;
	
	private JLabel numPadSizeUpsP2;
	private JLabel numPadSpeedUpsP2;
	private JLabel numBallSpeedUpsP2;
	private JLabel numInvinciUpsP2;
	private JLabel numStickyUpsP2;

	private NIP nip;

	/**
	 * Makes a new UpgradePanel that will replace the graphics panels in
	 * the specified NIP
	 * @param nip - container to which to add the UpgradePanel
	 */
	public UpgradePanel(NIP nip) {
		super();
		setLayout(new BorderLayout(0, 0));
		


		// Components that go in the northern title panel: START
			JPanel titlePanel = new JPanel();
			add(titlePanel, BorderLayout.NORTH);
			titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
	
			Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
			titlePanel.add(rigidArea);
	
			JLabel title = new JLabel("CHOOSE YOUR UPGRADES");
			title.setFont(new Font("Arial", Font.BOLD, 28));
			title.setAlignmentX(Component.CENTER_ALIGNMENT);
			titlePanel.add(title);
	
			JLabel instructions = new JLabel("Mouse over buttons for more info");
			instructions.setFont(new Font("Tahoma", Font.PLAIN, 14));
			instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
			titlePanel.add(instructions);
		//  Components that go in the northern title panel: END

		JButton startGame = new JButton("Start game!"); // Southern start game button
		startGame.setFont(new Font("Tahoma", Font.BOLD, 14));
		startGame.setToolTipText("Starts the game.  Any unspent points will be saved.");
		add(startGame, BorderLayout.SOUTH);
		startGame.setActionCommand("Start game");
		startGame.addActionListener(this);

		JPanel panel = new JPanel(); // The panel that holds both players' upgrade panels
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		upgradesP1 = new JPanel(); // Initialize Player 1's upgrade panel and its GridBagLayout
		panel.add(upgradesP1);
		GridBagLayout gbl_upgradesP1 = new GridBagLayout();
		gbl_upgradesP1.columnWidths = new int[]{30, 0, 0, 30, 0, 0, 30, 0};
		gbl_upgradesP1.rowHeights = new int[]{30, 90, 40, 0, 0, 20, 0, 0, 20, 0, 0, 30, 0};
		gbl_upgradesP1.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_upgradesP1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		upgradesP1.setLayout(gbl_upgradesP1);		

		// Components that go in Player 1's info panel: START
			JPanel infoPanelP1 = new JPanel();
			GridBagConstraints gbc_infoPanelP1 = new GridBagConstraints();
			gbc_infoPanelP1.fill = GridBagConstraints.BOTH;
			gbc_infoPanelP1.gridwidth = 5;
			gbc_infoPanelP1.insets = new Insets(0, 0, 5, 5);
			gbc_infoPanelP1.gridx = 1;
			gbc_infoPanelP1.gridy = 1;
			upgradesP1.add(infoPanelP1, gbc_infoPanelP1);
			infoPanelP1.setBorder(new LineBorder(new Color(0, 0, 0)));
			infoPanelP1.setLayout(new BoxLayout(infoPanelP1, BoxLayout.Y_AXIS));
	
			Component vertGlueP1Top = Box.createVerticalGlue();
			infoPanelP1.add(vertGlueP1Top);
	
			JLabel lblP1 = new JLabel("Player 1");
			lblP1.setFont(new Font("Arial", Font.BOLD, 24));
			lblP1.setAlignmentX(Component.CENTER_ALIGNMENT);
			infoPanelP1.add(lblP1);
	
			scoreP1 = new JLabel("Score: " + Main.player1.score);
			scoreP1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			scoreP1.setAlignmentX(Component.CENTER_ALIGNMENT);
			infoPanelP1.add(scoreP1);
	
			moneyP1 = new JLabel("Spendable points: " + Main.player1.money);
			moneyP1.setAlignmentX(Component.CENTER_ALIGNMENT);
			moneyP1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			infoPanelP1.add(moneyP1);
	
			Component vertGlueP1Bot = Box.createVerticalGlue();
			infoPanelP1.add(vertGlueP1Bot);
		// Components that go in Player 1's info panel: END

		addLabelToPanel(1, "UPGRADES", "Arial", Font.BOLD, 16, 1, 2);
		addLabelToPanel(1, "POWERUPS", "Arial", Font.BOLD, 16, 4, 2);
		addLabelToPanel(1, "Paddle size", 1, 3);
		addLabelToPanel(1, "Invincibility", 4, 3);
		
		buyPadSizeUpP1 = addButtonToPanel(
				1,
				"BUY: " + PAD_SIZE_PRICE,
				"Increase the size of your paddle.  Maximum of two upgrades.",
				"/icons/PaddleSizeUpP1.jpg",
				1, 4
				);
		if (Main.player1.numPadSizeUps >= 2) // If Player 1 has two or more paddle size upgrades, disable the button
			buyPadSizeUpP1.setEnabled(false);
		else {
			buyPadSizeUpP1.setActionCommand("P1 bought padSizeUp");
			buyPadSizeUpP1.addActionListener(this);
		}
		
		numPadSizeUpsP1 = addLabelToPanel(1, "x" + Main.player1.numPadSizeUps, "Arial", Font.BOLD, 14, Color.RED, 2, 4);
		
		JButton temp = addButtonToPanel(
				1,
				"BUY: " + INVINCI_PRICE,
				"Press ( " + (char)Main.player1.invinciKey + " ) and be immune " +
						"to losing balls for " + Controller.INVINCI_DURATION/1000 + " seconds!",
				"/icons/InvinciUpP1.jpg",
				4, 4
				);
		temp.setActionCommand("P1 bought invinci");
		String toolTipText = "Default key: ( Q ).  Be immune " +
				"to losing balls for " + Controller.INVINCI_DURATION/1000 + " seconds!";
		temp.setToolTipText(toolTipText);
		temp.addActionListener(this);
		
		numInvinciUpsP1 = addLabelToPanel(1, "x" + Main.player1.numInvinciUps, "Arial", Font.BOLD, 14, Color.RED, 5, 4);
		addLabelToPanel(1, "Paddle speed", 1, 6);
		addLabelToPanel(1, "Sticky paddle", 4, 6);
		
		temp = addButtonToPanel(
				1,
				"BUY: " + PAD_SPEED_PRICE,
				"Increase the speed of your paddle",
				"/icons/PaddleSpeedUpP1.jpg",
				1, 7
				);
		temp.setActionCommand("P1 bought padSpeedUp");
		temp.addActionListener(this);
		
		numPadSpeedUpsP1 = addLabelToPanel(1, "x" + Main.player1.numPadSpeedUps, "Arial", Font.BOLD, 14, Color.RED, 2, 7);
		
		temp = addButtonToPanel(
				1,
				"BUY: " + STICKY_PRICE,
				"Default key: ( E ).  Catch the next ball that hits your paddle.  " +
						"Then, press the hotkey again to launch the ball.  " +
						"Will launch the ball automatically after " + Controller.STICKY_DURATION/1000 + " seconds.",
				"/icons/StickyUpP1.jpg",
				4, 7
				);
		temp.setActionCommand("P1 bought sticky");
		temp.addActionListener(this);
		
		numStickyUpsP1 = addLabelToPanel(1, "x" + Main.player1.numStickyUps, "Arial", Font.BOLD, 14, Color.RED, 5, 7);
		addLabelToPanel(1, "Ball speed", 1, 9);
		addLabelToPanel(1, "Score boost", 4, 9);
		
		temp = addButtonToPanel(
				1,
				"BUY: " + BALL_SPEED_PRICE,
				"Increase the speed of your ball, allowing you to earn points faster, but making the ball harder to hit.  " +
						"It is wise to limit purchases of this upgrade!",
				"/icons/BallSpeedUpP1.jpg",
						1, 10
				);
		temp.setActionCommand("P1 bought ballSpeedUp");
		temp.addActionListener(this);
		
		numBallSpeedUpsP1 = addLabelToPanel(1, "x" + Main.player1.numBallSpeedUps, "Arial", Font.BOLD, 14, Color.RED, 2, 10);
		
		temp = addButtonToPanel(
				1,
				"BUY: " + SCORE_PRICE,
				"Spend " + SCORE_PRICE + " points to gain " + SCORE_BOOST_AMOUNT + " score.",
				"/icons/ScoreBoostP1.jpg",
				4, 10
				);
		temp.setActionCommand("P1 bought score");
		temp.addActionListener(this);

		upgradesP2 = new JPanel(); // Initialize Player 2's upgrade panel and its GridBagLayout
		panel.add(upgradesP2);
		GridBagLayout gbl_upgradesP2 = new GridBagLayout();
		gbl_upgradesP2.columnWidths = new int[]{30, 0, 0, 30, 0, 16, 30, 0};
		gbl_upgradesP2.rowHeights = new int[]{30, 90, 40, 0, 0, 20, 0, 0, 20, 0, 0, 30, 0};
		gbl_upgradesP2.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_upgradesP2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		upgradesP2.setLayout(gbl_upgradesP2);

		// Components that go in Player 2's info panel: START
			JPanel infoPanelP2 = new JPanel();
			infoPanelP2.setBorder(new LineBorder(new Color(0, 0, 0)));
			GridBagConstraints gbc_infoPanelP2 = new GridBagConstraints();
			gbc_infoPanelP2.gridwidth = 5;
			gbc_infoPanelP2.insets = new Insets(0, 0, 5, 5);
			gbc_infoPanelP2.fill = GridBagConstraints.BOTH;
			gbc_infoPanelP2.gridx = 1;
			gbc_infoPanelP2.gridy = 1;
			upgradesP2.add(infoPanelP2, gbc_infoPanelP2);
			infoPanelP2.setLayout(new BoxLayout(infoPanelP2, BoxLayout.Y_AXIS));
	
			Component vertGlueP2Top = Box.createVerticalGlue();
			infoPanelP2.add(vertGlueP2Top);
	
			JLabel lblP2 = new JLabel("Player 2");
			lblP2.setFont(new Font("Arial", Font.BOLD, 24));
			lblP2.setAlignmentX(0.5f);
			infoPanelP2.add(lblP2);
	
			scoreP2 = new JLabel("Score: " + Main.player2.score);
			scoreP2.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			scoreP2.setAlignmentX(0.5f);
			infoPanelP2.add(scoreP2);
	
			moneyP2 = new JLabel("Spendable points: " + Main.player2.money);
			moneyP2.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			moneyP2.setAlignmentX(0.5f);
			infoPanelP2.add(moneyP2);
	
			Component vertGlueP2Bot = Box.createVerticalGlue();
			infoPanelP2.add(vertGlueP2Bot);
		// Components that go in PLayer 2's info panel: END

		addLabelToPanel(2, "UPGRADES", "Arial", Font.BOLD, 16, 1, 2);
		addLabelToPanel(2, "POWERUPS", "Arial", Font.BOLD, 16, 4, 2);
		addLabelToPanel(2, "Paddle size", 1, 3);
		addLabelToPanel(2, "Invincibility", 4, 3);
		
		buyPadSizeUpP2 = addButtonToPanel(
				2,
				"BUY: " + PAD_SIZE_PRICE,
				"Increase the size of your paddle.  Maximum of two upgrades.",
				"/icons/PaddleSizeUpP2.jpg",
				1, 4
				);
		if (Main.player2.numPadSizeUps >= 2) // If Player 2 has two or more paddle size upgrades, disable the button
			buyPadSizeUpP2.setEnabled(false);
		else {
			buyPadSizeUpP2.setActionCommand("P2 bought padSizeUp");
			buyPadSizeUpP2.addActionListener(this);
		}
		
		numPadSizeUpsP2 = addLabelToPanel(2, "x" + Main.player2.numPadSizeUps, "Arial", Font.BOLD, 14, Color.RED, 2, 4);
		
		temp = addButtonToPanel(
				2,
				"BUY: " + INVINCI_PRICE,
				"Default key: ( [ ).  Be immune " +
						"to losing balls for " + Controller.INVINCI_DURATION/1000 + " seconds!",
				"/icons/InvinciUpP2.jpg",
				4, 4
				);
		temp.setActionCommand("P2 bought invinci");
		temp.addActionListener(this);
		
		numInvinciUpsP2 = addLabelToPanel(2, "x" + Main.player2.numInvinciUps, "Arial", Font.BOLD, 14, Color.RED, 5, 4);
		addLabelToPanel(2, "Paddle speed", 1, 6);
		addLabelToPanel(2, "Sticky paddle", 4, 6);
		
		temp = addButtonToPanel(
				2,
				"BUY: " + PAD_SPEED_PRICE,
				"Increase the speed of your paddle",
				"/icons/PaddleSpeedUpP2.jpg",
				1, 7
				);
		temp.setActionCommand("P2 bought padSpeedUp");
		temp.addActionListener(this);
		
		numPadSpeedUpsP2 = addLabelToPanel(2, "x" + Main.player2.numPadSpeedUps, "Arial", Font.BOLD, 14, Color.RED, 2, 7);
		
		temp = addButtonToPanel(
				2,
				"BUY: " + STICKY_PRICE,
				"Default key: ( Backslash ).  Catch the next ball that hits your paddle.  " +
						"Then, press the hotkey again to launch the ball.  " +
						"Will launch the ball automatically after " + Controller.STICKY_DURATION/1000 + " seconds.",
				"/icons/StickyUpP2.jpg",
				4, 7
				);
		temp.setActionCommand("P2 bought sticky");
		temp.addActionListener(this);
		
		numStickyUpsP2 = addLabelToPanel(2, "x" + Main.player2.numStickyUps, "Arial", Font.BOLD, 14, Color.RED, 5, 7);
		addLabelToPanel(2, "Ball speed", 1, 9);
		addLabelToPanel(2, "Score boost", 4, 9);
		
		temp = addButtonToPanel(
				2,
				"BUY: " + BALL_SPEED_PRICE,
				"Increase the speed of your ball, allowing you to earn points faster, but making the ball harder to hit.  " +
						"It is wise to limit purchases of this upgrade!",
				"/icons/BallSpeedUpP2.jpg",
				1, 10
				);
		temp.setActionCommand("P2 bought ballSpeedUp");
		temp.addActionListener(this);
		
		numBallSpeedUpsP2 = addLabelToPanel(2, "x" + Main.player2.numBallSpeedUps, "Arial", Font.BOLD, 14, Color.RED, 2, 10);
		
		temp = addButtonToPanel(
				2,
				"BUY: " + SCORE_PRICE,
				"Spend " + SCORE_PRICE + " points to gain " + SCORE_BOOST_AMOUNT + " score.",
				"/icons/ScoreBoostP2.jpg",
				4, 10
				);
		temp.setActionCommand("P2 bought score");
		temp.addActionListener(this);
		
		this.nip = nip;
		nip.addPanel(this);
	}

	private ImageIcon resize(ImageIcon icon) {
		Image image = icon.getImage();
		Image newImage = image.getScaledInstance(ICON_SIZE, ICON_SIZE, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(newImage);
	}

	private JLabel addLabelToPanel(int player, String text, int column, int row) {
		return addLabelToPanel(player, text, null, 0, 0, null, column, row);
	}

	private JLabel addLabelToPanel(int player, String text, String font, int style, int size, int column, int row) {
		return addLabelToPanel(player, text, font, style, size, null, column, row);
	}

	private JLabel addLabelToPanel(int player, String text, String font, int style, int size, Color color, int column, int row) {
		JLabel label = new JLabel(text);
		if ((font != null) && (size > 0) )
			label.setFont(new Font(font, style, size));
		if (color != null)
			label.setForeground(color);
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.gridx = column;
		gbc_label.gridy = row;
		if (player == 1)
			upgradesP1.add(label, gbc_label);
		else if (player == 2)
			upgradesP2.add(label, gbc_label);
		return label;
	}

	private JButton addButtonToPanel(int player, String text, String toolTipText, String iconLocation, int column, int row) {
		JButton button = new JButton(text);
		button.setIcon(
				resize(
						new ImageIcon(UpgradePanel.class.getResource(iconLocation))
						)
				);
		button.setHorizontalTextPosition(JLabel.CENTER);
		button.setVerticalTextPosition(JLabel.BOTTOM);
		button.setToolTipText(toolTipText);
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.gridx = column;
		gbc_button.gridy = row;
		if (player == 1)
			upgradesP1.add(button, gbc_button);
		else if (player == 2)
			upgradesP2.add(button, gbc_button);
		return button;
	}
	
	
	private void updateAllLabels() {
		scoreP1.setText("Score: " + Main.player1.score);
		moneyP1.setText("Spendable points: " + Main.player1.money);
		scoreP2.setText("Score: " + Main.player2.score);
		moneyP2.setText("Spendable points: " + Main.player2.money);
		
		numPadSizeUpsP1.setText("x" + Main.player1.numPadSizeUps);
		numPadSpeedUpsP1.setText("x" + Main.player1.numPadSpeedUps);
		numBallSpeedUpsP1.setText("x" + Main.player1.numBallSpeedUps);
		numInvinciUpsP1.setText("x" + Main.player1.numInvinciUps);
		numStickyUpsP1.setText("x" + Main.player1.numStickyUps);
		numPadSizeUpsP2.setText("x" + Main.player2.numPadSizeUps);
		numPadSpeedUpsP2.setText("x" + Main.player2.numPadSpeedUps);
		numBallSpeedUpsP2.setText("x" + Main.player2.numBallSpeedUps);
		numInvinciUpsP2.setText("x" + Main.player2.numInvinciUps);
		numStickyUpsP2.setText("x" + Main.player2.numStickyUps);
	}
	
	public void actionPerformed(ActionEvent e) {
		if ( (e.getActionCommand()).equals("Start game") ) {
			nip.removeAddedPanel();
			((Main) nip.getTool()).controller = new Controller(nip, nip.getTargetPanel(), Main.CONTROLLER_WIDTH, Main.CONTROLLER_HEIGHT);

		}
		
		else if ( (e.getActionCommand()).equals("P1 bought padSizeUp") ) {
			if (Main.player1.money >= PAD_SIZE_PRICE) { // Check if Player 1 has enough money
				Main.player1.money -= PAD_SIZE_PRICE;
				Main.player1.numPadSizeUps++;
				updateAllLabels();
				if (Main.player1.numPadSizeUps >= 2) // If Player 1 has two or more paddle size upgrades, disable the button
					buyPadSizeUpP1.setEnabled(false);
			}			
		}
		else if ( (e.getActionCommand()).equals("P1 bought padSpeedUp") ) {
			if (Main.player1.money >= PAD_SPEED_PRICE) {
				Main.player1.money -= PAD_SPEED_PRICE;
				Main.player1.numPadSpeedUps++;
				updateAllLabels();
			}
		}
		else if ( (e.getActionCommand()).equals("P1 bought ballSpeedUp") ) {
			if (Main.player1.money >= BALL_SPEED_PRICE) {
				Main.player1.money -= BALL_SPEED_PRICE;
				Main.player1.numBallSpeedUps++;
				updateAllLabels();
			}
		}
		else if ( (e.getActionCommand()).equals("P1 bought invinci") ) {
			if (Main.player1.money >= INVINCI_PRICE) {
				Main.player1.money -= INVINCI_PRICE;
				Main.player1.numInvinciUps++;
				updateAllLabels();
			}
		}
		else if ( (e.getActionCommand()).equals("P1 bought sticky") ) {
			if (Main.player1.money >= STICKY_PRICE) {
				Main.player1.money -= STICKY_PRICE;
				Main.player1.numStickyUps++;
				updateAllLabels();
			}
		}
		else if ( (e.getActionCommand()).equals("P1 bought score") ) {
			if (Main.player1.money >= SCORE_PRICE) {
				Main.player1.money -= SCORE_PRICE;
				Main.player1.score += SCORE_BOOST_AMOUNT;
				updateAllLabels();
			}
		}
		
		
		else if ( (e.getActionCommand()).equals("P2 bought padSizeUp") ) {
			if (Main.player2.money >= PAD_SIZE_PRICE) { // Check if Player 2 has enough money
				Main.player2.money -= PAD_SIZE_PRICE;
				Main.player2.numPadSizeUps++;
				updateAllLabels();
				if (Main.player2.numPadSizeUps >= 2) // If Player 2 has two or more paddle size upgrades, disable the button
					buyPadSizeUpP2.setEnabled(false);
			}
				
		}
		else if ( (e.getActionCommand()).equals("P2 bought padSpeedUp") ) {
			if (Main.player2.money >= PAD_SPEED_PRICE) {
				Main.player2.money -= PAD_SPEED_PRICE;
				Main.player2.numPadSpeedUps++;
				updateAllLabels();
			}
		}
		else if ( (e.getActionCommand()).equals("P2 bought ballSpeedUp") ) {
			if (Main.player2.money >= BALL_SPEED_PRICE) {
				Main.player2.money -= BALL_SPEED_PRICE;
				Main.player2.numBallSpeedUps++;
				updateAllLabels();
			}
		}
		else if ( (e.getActionCommand()).equals("P2 bought invinci") ) {
			if (Main.player2.money >= INVINCI_PRICE) {
				Main.player2.money -= INVINCI_PRICE;
				Main.player2.numInvinciUps++;
				updateAllLabels();
			}
		}
		else if ( (e.getActionCommand()).equals("P2 bought sticky") ) {
			if (Main.player2.money >= STICKY_PRICE) {
				Main.player2.money -= STICKY_PRICE;
				Main.player2.numStickyUps++;
				updateAllLabels();
			}
		}
		else if ( (e.getActionCommand()).equals("P2 bought score") ) {
			if (Main.player2.money >= SCORE_PRICE) {
				Main.player2.money -= SCORE_PRICE;
				Main.player2.score += SCORE_BOOST_AMOUNT;
				updateAllLabels();
			}
		}
		
	}
	

}
