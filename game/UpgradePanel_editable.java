package game;

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
import java.awt.Color;
import java.awt.Component;
import javax.swing.JToggleButton;
import java.awt.BorderLayout;
import java.awt.Dimension;

/**
 * This panel handles all the upgrade options for both players.  
 * 
 * Code based on WindowBuilder code, an Eclipse plugin.  This is a special version of UpgradePanel
 * editable by WindowBuilder.  It does not have icons on its buttons, nor actions assocated with them.
 * 
 * @author Silas Hsu - December 28, 2012
 */
public class UpgradePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final int ICON_SIZE = 50; // in pixels
	private final int PAD_SIZE_PRICE = 100;
	private final int PAD_SPEED_PRICE = 200;
	private final int BALL_SPEED_PRICE = 300;
	private final int INVINCI_PRICE = 400;
	private final int STICKY_PRICE = 500;

	public UpgradePanel() {
		super();
		setLayout(new BorderLayout(0, 0));
		
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
		
		JButton startGame = new JButton("Next round!");
		startGame.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(startGame, BorderLayout.SOUTH);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JPanel upgradesP1 = new JPanel();
		panel.add(upgradesP1);
		GridBagLayout gbl_upgradesP1 = new GridBagLayout();
		gbl_upgradesP1.columnWidths = new int[]{30, 0, 0, 30, 0, 0, 30, 0};
		gbl_upgradesP1.rowHeights = new int[]{30, 90, 40, 0, 0, 20, 0, 0, 20, 0, 0, 30, 0};
		gbl_upgradesP1.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_upgradesP1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		upgradesP1.setLayout(gbl_upgradesP1);
		
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
		
		JLabel scoreP1 = new JLabel("Score: " + Player1.score);
		scoreP1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		scoreP1.setAlignmentX(Component.CENTER_ALIGNMENT);
		infoPanelP1.add(scoreP1);
		
		JLabel moneyP1 = new JLabel("Spendable points: " + Player1.money);
		moneyP1.setAlignmentX(Component.CENTER_ALIGNMENT);
		moneyP1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		infoPanelP1.add(moneyP1);
		
		Component vertGlueP1Bot = Box.createVerticalGlue();
		infoPanelP1.add(vertGlueP1Bot);
		
		JLabel lblUpgradesP1 = new JLabel("UPGRADES");
		GridBagConstraints gbc_lblUpgradesP1 = new GridBagConstraints();
		gbc_lblUpgradesP1.insets = new Insets(0, 0, 5, 5);
		gbc_lblUpgradesP1.gridx = 1;
		gbc_lblUpgradesP1.gridy = 2;
		upgradesP1.add(lblUpgradesP1, gbc_lblUpgradesP1);
		lblUpgradesP1.setFont(new Font("Arial", Font.BOLD, 15));
		
		JLabel lblPowerupsP1 = new JLabel("POWERUPS");
		GridBagConstraints gbc_lblPowerupsP1 = new GridBagConstraints();
		gbc_lblPowerupsP1.insets = new Insets(0, 0, 5, 5);
		gbc_lblPowerupsP1.gridx = 4;
		gbc_lblPowerupsP1.gridy = 2;
		upgradesP1.add(lblPowerupsP1, gbc_lblPowerupsP1);
		lblPowerupsP1.setFont(new Font("Arial", Font.BOLD, 15));
		
		JLabel lblPadSizeP1 = new JLabel("Paddle size");
		GridBagConstraints gbc_lblPadSizeP1 = new GridBagConstraints();
		gbc_lblPadSizeP1.insets = new Insets(0, 0, 5, 5);
		gbc_lblPadSizeP1.gridx = 1;
		gbc_lblPadSizeP1.gridy = 3;
		upgradesP1.add(lblPadSizeP1, gbc_lblPadSizeP1);
		
		JLabel lblInvinciP1 = new JLabel("Invincibility");
		GridBagConstraints gbc_lblInvinciP1 = new GridBagConstraints();
		gbc_lblInvinciP1.insets = new Insets(0, 0, 5, 5);
		gbc_lblInvinciP1.gridx = 4;
		gbc_lblInvinciP1.gridy = 3;
		upgradesP1.add(lblInvinciP1, gbc_lblInvinciP1);
		
		JToggleButton buyPadSizeP1 = new JToggleButton("BUY: " + PAD_SIZE_PRICE);
		GridBagConstraints gbc_buyPadSizeP1 = new GridBagConstraints();
		gbc_buyPadSizeP1.insets = new Insets(0, 0, 5, 5);
		gbc_buyPadSizeP1.gridx = 1;
		gbc_buyPadSizeP1.gridy = 4;
		upgradesP1.add(buyPadSizeP1, gbc_buyPadSizeP1);
		buyPadSizeP1.setToolTipText("Increase the size of your paddle.  Maximum of two upgrades.");
		
		JLabel numPadSizeUpP1 = new JLabel("x" + Player1.numPadSizeUps);
		GridBagConstraints gbc_numPadSizeUpP1 = new GridBagConstraints();
		gbc_numPadSizeUpP1.insets = new Insets(0, 0, 5, 5);
		gbc_numPadSizeUpP1.gridx = 2;
		gbc_numPadSizeUpP1.gridy = 4;
		upgradesP1.add(numPadSizeUpP1, gbc_numPadSizeUpP1);
		numPadSizeUpP1.setForeground(Color.RED);
		numPadSizeUpP1.setFont(new Font("Arial", Font.BOLD, 14));
		
		JButton buyInvinciP1 = new JButton("BUY: " + INVINCI_PRICE);
		GridBagConstraints gbc_buyInvinciP1 = new GridBagConstraints();
		gbc_buyInvinciP1.insets = new Insets(0, 0, 5, 5);
		gbc_buyInvinciP1.gridx = 4;
		gbc_buyInvinciP1.gridy = 4;
		upgradesP1.add(buyInvinciP1, gbc_buyInvinciP1);
		buyInvinciP1.setToolTipText("Press (Q) and be immune to losing balls for 10 seconds!");
		
		JLabel numInvinciUpP1 = new JLabel("x" + Player1.numInvinciUps);
		GridBagConstraints gbc_numInvinciUpP1 = new GridBagConstraints();
		gbc_numInvinciUpP1.insets = new Insets(0, 0, 5, 5);
		gbc_numInvinciUpP1.gridx = 5;
		gbc_numInvinciUpP1.gridy = 4;
		upgradesP1.add(numInvinciUpP1, gbc_numInvinciUpP1);
		numInvinciUpP1.setForeground(Color.RED);
		numInvinciUpP1.setFont(new Font("Arial", Font.BOLD, 14));
		
		JLabel lblPadSpeedP1 = new JLabel("Paddle speed");
		GridBagConstraints gbc_lblPadSpeedP1 = new GridBagConstraints();
		gbc_lblPadSpeedP1.insets = new Insets(0, 0, 5, 5);
		gbc_lblPadSpeedP1.gridx = 1;
		gbc_lblPadSpeedP1.gridy = 6;
		upgradesP1.add(lblPadSpeedP1, gbc_lblPadSpeedP1);
		
		JLabel lblStickyP1 = new JLabel("Sticky paddle");
		GridBagConstraints gbc_lblStickyP1 = new GridBagConstraints();
		gbc_lblStickyP1.insets = new Insets(0, 0, 5, 5);
		gbc_lblStickyP1.gridx = 4;
		gbc_lblStickyP1.gridy = 6;
		upgradesP1.add(lblStickyP1, gbc_lblStickyP1);
		
		JButton buyPadSpeedP1 = new JButton("BUY: " + PAD_SPEED_PRICE);
		GridBagConstraints gbc_buyPadSpeedP1 = new GridBagConstraints();
		gbc_buyPadSpeedP1.insets = new Insets(0, 0, 5, 5);
		gbc_buyPadSpeedP1.gridx = 1;
		gbc_buyPadSpeedP1.gridy = 7;
		upgradesP1.add(buyPadSpeedP1, gbc_buyPadSpeedP1);
		buyPadSpeedP1.setToolTipText("Increase the speed of your paddle.");
		
		JLabel numPadSpeedUpP1 = new JLabel("x" + Player1.numPadSpeedUps);
		GridBagConstraints gbc_numPadSpeedUpP1 = new GridBagConstraints();
		gbc_numPadSpeedUpP1.insets = new Insets(0, 0, 5, 5);
		gbc_numPadSpeedUpP1.gridx = 2;
		gbc_numPadSpeedUpP1.gridy = 7;
		upgradesP1.add(numPadSpeedUpP1, gbc_numPadSpeedUpP1);
		numPadSpeedUpP1.setForeground(Color.RED);
		numPadSpeedUpP1.setFont(new Font("Arial", Font.BOLD, 14));
		
		JButton buyStickyP1 = new JButton("BUY: " + STICKY_PRICE);
		GridBagConstraints gbc_buyStickyP1 = new GridBagConstraints();
		gbc_buyStickyP1.insets = new Insets(0, 0, 5, 5);
		gbc_buyStickyP1.gridx = 4;
		gbc_buyStickyP1.gridy = 7;
		upgradesP1.add(buyStickyP1, gbc_buyStickyP1);
		buyStickyP1.setToolTipText("Press (E) to catch the next ball that hits your paddle.  Then, press (E) again to launch the ball.  Will launch the ball automatically after 10 seconds.");
		
		JLabel numStickyUpP1 = new JLabel("x" + Player1.numStickyUps);
		GridBagConstraints gbc_numStickyUpP1 = new GridBagConstraints();
		gbc_numStickyUpP1.insets = new Insets(0, 0, 5, 5);
		gbc_numStickyUpP1.gridx = 5;
		gbc_numStickyUpP1.gridy = 7;
		upgradesP1.add(numStickyUpP1, gbc_numStickyUpP1);
		numStickyUpP1.setForeground(Color.RED);
		numStickyUpP1.setFont(new Font("Arial", Font.BOLD, 14));
		
		JLabel lblBallSpeedP1 = new JLabel("Ball speed");
		GridBagConstraints gbc_lblBallSpeedP1 = new GridBagConstraints();
		gbc_lblBallSpeedP1.insets = new Insets(0, 0, 5, 5);
		gbc_lblBallSpeedP1.gridx = 1;
		gbc_lblBallSpeedP1.gridy = 9;
		upgradesP1.add(lblBallSpeedP1, gbc_lblBallSpeedP1);
		
		JButton buyBallSpeedP1 = new JButton("BUY: " + BALL_SPEED_PRICE);
		GridBagConstraints gbc_buyBallSpeedP1 = new GridBagConstraints();
		gbc_buyBallSpeedP1.insets = new Insets(0, 0, 5, 5);
		gbc_buyBallSpeedP1.gridx = 1;
		gbc_buyBallSpeedP1.gridy = 10;
		upgradesP1.add(buyBallSpeedP1, gbc_buyBallSpeedP1);
		buyBallSpeedP1.setToolTipText("Increase the speed of your ball, allowing you to earn points faster, but making the ball harder to hit.  It is wise to limit purchases of this upgrade!");
		
		JLabel numBallSpeedUpP1 = new JLabel("x" + Player1.numBallSpeedUps);
		GridBagConstraints gbc_numBallSpeedUpP1 = new GridBagConstraints();
		gbc_numBallSpeedUpP1.insets = new Insets(0, 0, 5, 5);
		gbc_numBallSpeedUpP1.gridx = 2;
		gbc_numBallSpeedUpP1.gridy = 10;
		upgradesP1.add(numBallSpeedUpP1, gbc_numBallSpeedUpP1);
		numBallSpeedUpP1.setForeground(Color.RED);
		numBallSpeedUpP1.setFont(new Font("Arial", Font.BOLD, 14));
		
		JPanel upgradesP2 = new JPanel();
		panel.add(upgradesP2);
		GridBagLayout gbl_upgradesP2 = new GridBagLayout();
		gbl_upgradesP2.columnWidths = new int[]{30, 0, 0, 30, 0, 16, 30, 0};
		gbl_upgradesP2.rowHeights = new int[]{30, 90, 40, 0, 0, 20, 0, 0, 20, 0, 0, 30, 0};
		gbl_upgradesP2.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_upgradesP2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		upgradesP2.setLayout(gbl_upgradesP2);
		
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
		
		JLabel scoreP2 = new JLabel("Score: " + Player2.score);
		scoreP2.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		scoreP2.setAlignmentX(0.5f);
		infoPanelP2.add(scoreP2);
		
		JLabel moneyP2 = new JLabel("Spendable points: " + Player2.money);
		moneyP2.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		moneyP2.setAlignmentX(0.5f);
		infoPanelP2.add(moneyP2);
		
		Component vertGlueP2Bot = Box.createVerticalGlue();
		infoPanelP2.add(vertGlueP2Bot);
		
		JLabel lblUpgradesP2 = new JLabel("UPGRADES");
		lblUpgradesP2.setFont(new Font("Arial", Font.BOLD, 15));
		GridBagConstraints gbc_lblUpgradesP2 = new GridBagConstraints();
		gbc_lblUpgradesP2.insets = new Insets(0, 0, 5, 5);
		gbc_lblUpgradesP2.gridx = 1;
		gbc_lblUpgradesP2.gridy = 2;
		upgradesP2.add(lblUpgradesP2, gbc_lblUpgradesP2);
		
		JLabel lblPowerupsP2 = new JLabel("POWERUPS");
		lblPowerupsP2.setFont(new Font("Arial", Font.BOLD, 15));
		GridBagConstraints gbc_lblPowerupsP2 = new GridBagConstraints();
		gbc_lblPowerupsP2.insets = new Insets(0, 0, 5, 5);
		gbc_lblPowerupsP2.gridx = 4;
		gbc_lblPowerupsP2.gridy = 2;
		upgradesP2.add(lblPowerupsP2, gbc_lblPowerupsP2);
		
		JLabel lblPadSizeP2 = new JLabel("Paddle size");
		GridBagConstraints gbc_lblPadSizeP2 = new GridBagConstraints();
		gbc_lblPadSizeP2.insets = new Insets(0, 0, 5, 5);
		gbc_lblPadSizeP2.gridx = 1;
		gbc_lblPadSizeP2.gridy = 3;
		upgradesP2.add(lblPadSizeP2, gbc_lblPadSizeP2);
		
		JLabel lblInvinciP2 = new JLabel("Invincibility");
		GridBagConstraints gbc_lblInvinciP2 = new GridBagConstraints();
		gbc_lblInvinciP2.insets = new Insets(0, 0, 5, 5);
		gbc_lblInvinciP2.gridx = 4;
		gbc_lblInvinciP2.gridy = 3;
		upgradesP2.add(lblInvinciP2, gbc_lblInvinciP2);
		
		JToggleButton buyPadSizeP2 = new JToggleButton("BUY: " + PAD_SIZE_PRICE);
		buyPadSizeP2.setToolTipText("Increase the size of your paddle.  Maximum of two upgrades.");
		GridBagConstraints gbc_buyPadSizeP2 = new GridBagConstraints();
		gbc_buyPadSizeP2.insets = new Insets(0, 0, 5, 5);
		gbc_buyPadSizeP2.gridx = 1;
		gbc_buyPadSizeP2.gridy = 4;
		upgradesP2.add(buyPadSizeP2, gbc_buyPadSizeP2);
		
		JLabel numPadSizeUpP2 = new JLabel("x" + Player2.numPadSizeUps);
		numPadSizeUpP2.setForeground(Color.RED);
		numPadSizeUpP2.setFont(new Font("Arial", Font.BOLD, 14));
		GridBagConstraints gbc_numPadSizeUpP2 = new GridBagConstraints();
		gbc_numPadSizeUpP2.insets = new Insets(0, 0, 5, 5);
		gbc_numPadSizeUpP2.gridx = 2;
		gbc_numPadSizeUpP2.gridy = 4;
		upgradesP2.add(numPadSizeUpP2, gbc_numPadSizeUpP2);
		
		JButton buyInvinciP2 = new JButton("BUY: " + INVINCI_PRICE);
		buyInvinciP2.setToolTipText("Press (Q) and be immune to losing balls for 10 seconds!");
		GridBagConstraints gbc_buyInvinciP2 = new GridBagConstraints();
		gbc_buyInvinciP2.insets = new Insets(0, 0, 5, 5);
		gbc_buyInvinciP2.gridx = 4;
		gbc_buyInvinciP2.gridy = 4;
		upgradesP2.add(buyInvinciP2, gbc_buyInvinciP2);
		
		JLabel numInvinciUpP2 = new JLabel("x" + Player2.numInvinciUps);
		numInvinciUpP2.setForeground(Color.RED);
		numInvinciUpP2.setFont(new Font("Arial", Font.BOLD, 14));
		GridBagConstraints gbc_numInvinciUpP2 = new GridBagConstraints();
		gbc_numInvinciUpP2.insets = new Insets(0, 0, 5, 5);
		gbc_numInvinciUpP2.gridx = 5;
		gbc_numInvinciUpP2.gridy = 4;
		upgradesP2.add(numInvinciUpP2, gbc_numInvinciUpP2);
		
		JLabel lblPadSpeedP2 = new JLabel("Paddle speed");
		GridBagConstraints gbc_lblPadSpeedP2 = new GridBagConstraints();
		gbc_lblPadSpeedP2.insets = new Insets(0, 0, 5, 5);
		gbc_lblPadSpeedP2.gridx = 1;
		gbc_lblPadSpeedP2.gridy = 6;
		upgradesP2.add(lblPadSpeedP2, gbc_lblPadSpeedP2);
		
		JLabel lblStickyP2 = new JLabel("Sticky paddle");
		GridBagConstraints gbc_lblStickyP2 = new GridBagConstraints();
		gbc_lblStickyP2.insets = new Insets(0, 0, 5, 5);
		gbc_lblStickyP2.gridx = 4;
		gbc_lblStickyP2.gridy = 6;
		upgradesP2.add(lblStickyP2, gbc_lblStickyP2);
		
		JButton buyPadSpeedP2 = new JButton("BUY: " + PAD_SPEED_PRICE);
		buyPadSpeedP2.setToolTipText("Increase the speed of your paddle.");
		GridBagConstraints gbc_buyPadSpeedP2 = new GridBagConstraints();
		gbc_buyPadSpeedP2.insets = new Insets(0, 0, 5, 5);
		gbc_buyPadSpeedP2.gridx = 1;
		gbc_buyPadSpeedP2.gridy = 7;
		upgradesP2.add(buyPadSpeedP2, gbc_buyPadSpeedP2);
		
		JLabel numPadSpeedUpP2 = new JLabel("x" + Player2.numPadSpeedUps);
		numPadSpeedUpP2.setForeground(Color.RED);
		numPadSpeedUpP2.setFont(new Font("Arial", Font.BOLD, 14));
		GridBagConstraints gbc_numPadSpeedUpP2 = new GridBagConstraints();
		gbc_numPadSpeedUpP2.insets = new Insets(0, 0, 5, 5);
		gbc_numPadSpeedUpP2.gridx = 2;
		gbc_numPadSpeedUpP2.gridy = 7;
		upgradesP2.add(numPadSpeedUpP2, gbc_numPadSpeedUpP2);
		
		JButton buyStickyP2 = new JButton("BUY: " + STICKY_PRICE);
		buyStickyP2.setToolTipText("Press (E) to catch the next ball that hits your paddle.  Then, press (E) again to launch the ball.  Will launch the ball automatically after 10 seconds.");
		GridBagConstraints gbc_buyStickyP2 = new GridBagConstraints();
		gbc_buyStickyP2.insets = new Insets(0, 0, 5, 5);
		gbc_buyStickyP2.gridx = 4;
		gbc_buyStickyP2.gridy = 7;
		upgradesP2.add(buyStickyP2, gbc_buyStickyP2);
		
		JLabel numStickyUpP2 = new JLabel("x" + Player2.numStickyUps);
		numStickyUpP2.setForeground(Color.RED);
		numStickyUpP2.setFont(new Font("Arial", Font.BOLD, 14));
		GridBagConstraints gbc_numStickyUpP2 = new GridBagConstraints();
		gbc_numStickyUpP2.insets = new Insets(0, 0, 5, 5);
		gbc_numStickyUpP2.gridx = 5;
		gbc_numStickyUpP2.gridy = 7;
		upgradesP2.add(numStickyUpP2, gbc_numStickyUpP2);
		
		JLabel lblBallSpeedP2 = new JLabel("Ball speed");
		GridBagConstraints gbc_lblBallSpeedP2 = new GridBagConstraints();
		gbc_lblBallSpeedP2.insets = new Insets(0, 0, 5, 5);
		gbc_lblBallSpeedP2.gridx = 1;
		gbc_lblBallSpeedP2.gridy = 9;
		upgradesP2.add(lblBallSpeedP2, gbc_lblBallSpeedP2);
		
		JButton buyBallSpeedP2 = new JButton("BUY: " + BALL_SPEED_PRICE);
		buyBallSpeedP2.setToolTipText("Increase the speed of your ball, allowing you to earn points faster, but making the ball harder to hit.  It is wise to limit purchases of this upgrade!");
		GridBagConstraints gbc_buyBallSpeedP2 = new GridBagConstraints();
		gbc_buyBallSpeedP2.insets = new Insets(0, 0, 5, 5);
		gbc_buyBallSpeedP2.gridx = 1;
		gbc_buyBallSpeedP2.gridy = 10;
		upgradesP2.add(buyBallSpeedP2, gbc_buyBallSpeedP2);
		
		JLabel numBallSpeedUpP2 = new JLabel("x" + Player2.numBallSpeedUps);
		numBallSpeedUpP2.setForeground(Color.RED);
		numBallSpeedUpP2.setFont(new Font("Arial", Font.BOLD, 14));
		GridBagConstraints gbc_numBallSpeedUpP2 = new GridBagConstraints();
		gbc_numBallSpeedUpP2.insets = new Insets(0, 0, 5, 5);
		gbc_numBallSpeedUpP2.gridx = 2;
		gbc_numBallSpeedUpP2.gridy = 10;
		upgradesP2.add(numBallSpeedUpP2, gbc_numBallSpeedUpP2);
		
	}
	
	private ImageIcon resize(ImageIcon icon) {
		Image image = icon.getImage();
		Image newImage = image.getScaledInstance(ICON_SIZE, ICON_SIZE, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(newImage);
		
	}
}

