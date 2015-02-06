/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javax.microedition.lcdui;

import com.kitmaker.finalkombat.ActivityMain;

import javax.microedition.midlet.MIDlet;

import android.content.res.Configuration;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;

/**
 * 
 * @author Venus-Kitmaker
 */
public class Canvas extends Displayable {

	public static Graphics ms_vGraphics;

	public static final int DOWN = KeyEvent.KEYCODE_DPAD_DOWN;
	public static final int UP = KeyEvent.KEYCODE_DPAD_UP;
	public static final int LEFT = KeyEvent.KEYCODE_DPAD_LEFT;
	public static final int RIGHT = KeyEvent.KEYCODE_DPAD_RIGHT;
	public static final int FIRE = KeyEvent.KEYCODE_DPAD_CENTER;

	public static final int GAME_A = 11;
	public static final int GAME_B = 12;
	public static final int GAME_C = 13;
	public static final int GAME_D = 14;

	public static final int KEY_NUM0 = KeyEvent.KEYCODE_0;
	public static final int KEY_NUM1 = KeyEvent.KEYCODE_1;
	public static final int KEY_NUM2 = KeyEvent.KEYCODE_2;
	public static final int KEY_NUM3 = KeyEvent.KEYCODE_3;
	public static final int KEY_NUM4 = KeyEvent.KEYCODE_4;
	public static final int KEY_NUM5 = KeyEvent.KEYCODE_5;
	public static final int KEY_NUM6 = KeyEvent.KEYCODE_6;
	public static final int KEY_NUM7 = KeyEvent.KEYCODE_7;
	public static final int KEY_NUM8 = KeyEvent.KEYCODE_8;
	public static final int KEY_NUM9 = KeyEvent.KEYCODE_9;

	public static final int KEY_STAR = KeyEvent.KEYCODE_APOSTROPHE;
	public static final int KEY_POUND = KeyEvent.KEYCODE_POUND;
	
	public CanvasView m_View;

	public Canvas() {
		m_View = new CanvasView();
		ActivityMain.setCanvas(this);
	}
	
	public boolean hasPointerEvents() {
		Configuration ms_vConfig = new Configuration();
		return (ms_vConfig.touchscreen != Configuration.TOUCHSCREEN_NOTOUCH);
	}

	public boolean hasPointerMotionEvents() {
		Configuration ms_vConfig = new Configuration();
		return (ms_vConfig.touchscreen != Configuration.TOUCHSCREEN_NOTOUCH);
	}

	protected void paint(Graphics _g) {
	}
	
	protected void pointerPressed(int x, int y) {
	}

	protected void pointerDragged(int x, int y) {
	}

	protected void pointerReleased(int x, int y) {
	}

	public void setFullScreenMode(boolean _bSetFullScreen) {
	}

	public void repaint() {
		
		if (m_View.m_SurfaceHolder == null || ActivityMain.ms_bPause)
			return;
		
		android.graphics.Canvas vCanvas = null;
		try 
		{

			vCanvas = m_View.m_SurfaceHolder.lockCanvas(null);
			synchronized (m_View.m_SurfaceHolder) 
			{
				
				if (ms_vGraphics == null)
					ms_vGraphics = new Graphics(vCanvas);

				paint(ms_vGraphics);
			}
		} finally 
		{
			// do this in a finally so that if an exception is thrown
			// during the above, we don't leave the Surface in an
			// inconsistent state
			if (vCanvas != null) {
				m_View.m_SurfaceHolder.unlockCanvasAndPost(vCanvas);
			}
		}
	}

	public final void repaint(int x, int y, int width, int height) {
		//ActivityView.ms_vMainJ2ME.paint(ms_vGraphics);
	}

	public void serviceRepaints() {
	}

	public int getGameAction(int _iKeyCode) {
		if(_iKeyCode == -5)
			_iKeyCode = FIRE;
		if(_iKeyCode == -2)
			_iKeyCode = DOWN;		
		if(_iKeyCode == -1)
			_iKeyCode = UP;		
		if(_iKeyCode == -3)
			_iKeyCode = LEFT;			
		if(_iKeyCode == -4)
			_iKeyCode = RIGHT;	
		return _iKeyCode;
	}

	public int getKeyCode(int _iGameAction) {
		return _iGameAction;
	}

	protected void keyPressed(int keyCode) {
	}

	protected void keyReleased(int keyCode) {
	}

	protected void keyRepeated(int keyCode) {
	}
	
	public final int getWidth() {
		return ActivityMain.SCREEN_SIZES[ActivityMain.ms_iSize][0];
	}
	public final int getHeight() {
		return ActivityMain.SCREEN_SIZES[ActivityMain.ms_iSize][1];
	}

	public void setTitle(String s) {
		if (s == null) {
			ActivityMain.ms_vMain.requestWindowFeature(Window.FEATURE_NO_TITLE);
		} else {
			ActivityMain.ms_vMain.setTitle(s);
		}
	}
	
	
	public void pause () {
		hideNotify();
	}
	public void unpause () {
		showNotify();
	}

	protected void hideNotify() {
	}
	protected void showNotify() {
	}
	
	public void disposeDisplayable() {
	}

	public void initDisplayable(MIDlet midlet) {
	}

	// key listener
	public static int ms_iKeyInt_Map;
	public static char ms_iKeyChar_Map;
	
	public class CanvasView extends SurfaceView implements SurfaceHolder.Callback {
		public SurfaceHolder m_SurfaceHolder;
		
		public CanvasView() {
			super(ActivityMain.ms_Context);    	
	        
			// register our interest in hearing about changes to our surface
			SurfaceHolder holder = getHolder();
			String str = android.os.Build.MODEL;
			if(str=="Kindle Fire")	
				holder.setFixedSize(ActivityMain.SCREEN_SIZES[ActivityMain.ms_iSize][0], ActivityMain.SCREEN_SIZES[ActivityMain.ms_iSize][1]+10);
			else
				holder.setFixedSize(ActivityMain.SCREEN_SIZES[ActivityMain.ms_iSize][0], ActivityMain.SCREEN_SIZES[ActivityMain.ms_iSize][1]);
			
			holder.addCallback(this);
			holder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);
	        
			setFocusable(true);
			setFocusableInTouchMode(true);
		}
		
		public void surfaceCreated(SurfaceHolder holder) {
			// get handles to some important objects
			m_SurfaceHolder = holder;
			String str = android.os.Build.MODEL;
			try {
				if(str=="Kindle Fire")	
					m_SurfaceHolder.setFixedSize(ActivityMain.SCREEN_SIZES[ActivityMain.ms_iSize][0], ActivityMain.SCREEN_SIZES[ActivityMain.ms_iSize][1]+10);
				else
					m_SurfaceHolder.setFixedSize(ActivityMain.SCREEN_SIZES[ActivityMain.ms_iSize][0], ActivityMain.SCREEN_SIZES[ActivityMain.ms_iSize][1]);
				
			} catch (IllegalArgumentException e) {
				// TODO: handle exception
			}
			
			
		}
			
		public void surfaceDestroyed(SurfaceHolder holder) {
		}
		
		@Override
		public void onWindowFocusChanged(boolean hasWindowFocus) {
			if (!hasWindowFocus) {
				//ActivityMain.ms_vMain.pause();
			}
		}
		
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		}
		
		
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent msg) {

			if (msg != null) {
				char mapedChar = (char) msg.getUnicodeChar();
				ms_iKeyChar_Map = ("" + mapedChar).toLowerCase().charAt(0);
			}

			if (keyCode == KeyEvent.KEYCODE_MENU || 
				keyCode == KeyEvent.KEYCODE_HOME || 
				keyCode == KeyEvent.KEYCODE_VOLUME_UP || 
				keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)
				return false;

			keyPressed(keyCode);
			return true;
		}

		@Override
		public boolean onKeyUp(int keyCode, KeyEvent msg) {
			if (keyCode == KeyEvent.KEYCODE_MENU || 
				keyCode == KeyEvent.KEYCODE_HOME || 
				keyCode == KeyEvent.KEYCODE_VOLUME_UP || 
				keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)
				return false;

			keyReleased(keyCode);

			return true;
		}

		// @Override
		public boolean onTouchEvent(MotionEvent event) {

			int action = event.getAction();
			if (action == MotionEvent.ACTION_DOWN) {
				pointerPressed((int) GetRealX(event.getX()), (int) GetRealY(event.getY()));

				return true;
			}
			if (action == MotionEvent.ACTION_MOVE) {
				pointerDragged((int) GetRealX(event.getX()), (int) GetRealY(event.getY()));

				return true;
			}
			if (action == MotionEvent.ACTION_UP) {
				pointerReleased((int) GetRealX(event.getX()), (int) GetRealY(event.getY()));

				return true;
			}
			return false;
		}

		private float GetRealX(float _fX) {
			return (_fX * ActivityMain.ms_fWidthPercent);
		}

		private float GetRealY(float _fY) {
			return (_fY * ActivityMain.ms_fHeightPercent);
		}
	}

	@Override
	public View getView() {
		return m_View;
	}
}
