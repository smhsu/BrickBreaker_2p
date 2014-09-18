package game;

import game.pubsub.Subscriber;

import game.time.Clock;
import game.time.ClockTick;

import nip.GraphicsPanel;
import nip.Text;

/**
 * This is a special version of Text that will remove itself from a GraphicsPanel after a delay.
 * 
 * @author Silas Hsu
 * December 20, 2012
 */
public class TempText extends Text implements Subscriber<ClockTick> {

	private static final long serialVersionUID = 1L;
	
	private boolean hasCounter; // If true, the text also has a number telling how many seconds before it is removed
	private String originalText; // Keeps track of the text without any counters
	private GraphicsPanel panel;
	private int currentTime; // Time in seconds since the creation of the text
	private int deleteTime; // Time in seconds when the text will be removed from the GraphicsPanel
	

	/**
	 * Creates text that will add itself to the specified panel and then remove itself after
	 * the specified delay.  If duration is 0 or less, the text stays forever.
	 * @param text
	 * @param duration - number of milliseconds until the text removes itself
	 * @param panel - panel to add the text
	 */
	public TempText(String text, int duration, GraphicsPanel panel) {
		super(text);
		
		hasCounter = false;
		originalText = text;
		this.panel = panel;
		panel.add(this);
		currentTime = 0;
		deleteTime = duration;
		Clock.instance().addSubscriber(this);
	}
	
	/**
	 * Gives number of seconds until removal, rounded up.
	 * @return
	 */
	public int getSecondsUntilRemoval() {
		return ( (deleteTime - currentTime)/1000 + 1);
	}
	
	/**
	 * Adds a number to the end of the text signifying how many seconds until it is removed
	 * from the GraphicsPanel
	 */
	public void addCounter() {
		super.setText(originalText + " " + getSecondsUntilRemoval());
		hasCounter = true;
	}
	
	public void removeCounter() {
		super.setText(originalText);
		hasCounter = false;
	}
	
	/**
	 * Removes the TempText prematurely.
	 */
	public void remove() {
		panel.remove(this);
	}
	
	@Override
	public void setText(String text) {
		if (hasCounter)
			super.setText(text + " " + getSecondsUntilRemoval());
		else
			super.setText(text);
		originalText = text;
	}

	@Override
	public void observeEvent(ClockTick e) {
		currentTime = currentTime + Clock.instance().getTimeQuantum();
		if (currentTime >= deleteTime)
			panel.remove(this);
		else if (hasCounter)
			super.setText(originalText + " " + getSecondsUntilRemoval());
	}

	/**
	 * @return if the text is on the panel
	 */
	@Override
	public boolean isDead() {
		return ( (deleteTime - currentTime) <= 0);
	}

}
