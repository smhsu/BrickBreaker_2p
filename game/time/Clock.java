package game.time;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import game.pubsub.*;

/**
 * This class implements a clock that can be used to drive a discrete
 * time simulation. The design prevents the use of more of more than
 * one Clock object in any program.
 *
 * @author Ron Cytron, with some changes by Silas Hsu
 */
public class Clock extends BasePublisher<ClockTick> implements ActionListener {
	
	private static Clock clock = null;
	private int now;
	final private static int TIMEQUANTUM=5; // 200 ticks = 1 second
	final private Timer timer;
	
	/**
	 * The constructor is private, which is unusual, but this prevents
	 * any outside class from asking for an instantiation of this class.
	 * Instead, objects get to a Clock instance using the static instance() method.
	 *   
	 * This pattern of object-oriented design is called Singleton.
	 * 
	 * Exactly one instance is made when instance() is called.
	 * Thus, all objects see the same Clock.
	 * 
	 * The swing Timer is used to make the Clock tick.  The amount of real
	 * time advanced by each clock tick is TIMEQUANTUM.  Changing the resolution of
	 * the swing Timer will not affect when things happen, but will change the granularity
	 * of the game's simulation.
	 */
	private Clock() {
		super();
		now = 0;
		//
		// To slow the game down, specify some multiple of TIMEQUANTUM below
		//
		timer = new Timer(TIMEQUANTUM, this);
	}
	
	/**
	 * @return the real time in milliseconds advanced by each clock tick
	 */
	public int getTimeQuantum() {
		return TIMEQUANTUM;
	}
	
	/**
	 * Start up the timer, picking up where it left off.
	 */
	public void start() { timer.start(); }
	
	/**
	 * Stop the timer.
	 */
	public void stop(){ timer.stop(); }
	
	/**
	 * Other parts of the program refer to the Clock using
	 * the notation Clock.instance(), which returns a reference
	 * to the single Clock object. Using this reference, other
	 * parts of the program can invoke any of the object's other methods.
	 * 
	 * @return the clock
	 */
	public static Clock instance() {
		if (clock == null) clock = new Clock();
		return clock;
	}
	
	/**
	 * Called to signal one clock tick.  Notify all subscribers that it happened.
	 */
	public void tick() {
		now = now + TIMEQUANTUM;
		notifySubscribers(new ClockTick(currentTime()));
	}
	
	/**
	 * Returns the Timer used by the Clock.  Use with caution, since improperly using the
	 * Timer can break the game.
	 * @return the Timer used by the Clock
	 */
	public Timer getTimer() { return timer; }
	
	
	/**
	 * Returns the current time.
	 * @return Time when this method is called
	 */
	public Time currentTime() {
		return new Time(now);
	}

	/**
	 * Called by the swing Timer on each of its Timer expirations,
	 * this method simply calls tick()
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		tick();
	}
}
