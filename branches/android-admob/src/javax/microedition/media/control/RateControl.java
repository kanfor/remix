package javax.microedition.media.control;

import javax.microedition.media.Control;

public interface RateControl extends Control {
	
	int setRate(int millirate);
	int getRate();
	int getMaxRate();
	int getMinRate();

}