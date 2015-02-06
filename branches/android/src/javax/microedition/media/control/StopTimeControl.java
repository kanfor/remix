package javax.microedition.media.control;

import javax.microedition.media.Control;

public interface StopTimeControl extends Control {

	static final long RESET = Long.MAX_VALUE;

	void setStopTime(long stopTime);
	long getStopTime();

}