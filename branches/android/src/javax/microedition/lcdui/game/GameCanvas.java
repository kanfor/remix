/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javax.microedition.lcdui.game;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author Venus-Kitmaker
 */
public class GameCanvas extends Canvas {
	
	public static final int UP_PRESSED = (1 << Canvas.UP);
	public static final int DOWN_PRESSED = (1 << Canvas.DOWN);
	public static final int LEFT_PRESSED = (1 << Canvas.LEFT);
	public static final int RIGHT_PRESSED = (1 << Canvas.RIGHT);
	public static final int FIRE_PRESSED = (1 << Canvas.FIRE);
	public static final int GAME_A_PRESSED = (1 << Canvas.GAME_A);
	public static final int GAME_B_PRESSED = (1 << Canvas.GAME_B);
	public static final int GAME_C_PRESSED = (1 << Canvas.GAME_C);
	public static final int GAME_D_PRESSED = (1 << Canvas.GAME_D);
	
	public GameCanvas(boolean suppressKeyEvents) {
	}
	
	protected Graphics getGraphics() {
		return ms_vGraphics;
	}
	
	public int getKeyStates() {
		return ms_iKeyInt_Map;
	}
	
	public void flushGraphics() {
		repaint();
	}
	
	public void flushGraphics(int x, int y, int width, int height) {
		repaint(x, y, width, height);
	}
	
}
