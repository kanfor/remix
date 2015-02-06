package javax.microedition.lcdui;

import java.util.Locale;

import javax.microedition.Settings;
import javax.microedition.midlet.MIDlet;

import android.content.res.Configuration;
import android.graphics.Rect;
import android.view.*;

/**
 <p/>
 @author Venus-Kitmaker
 */
public class Canvas extends Displayable
{
    public static Graphics ms_vGraphics;
    public static final int DOWN = KeyEvent.KEYCODE_DPAD_DOWN;
    public static final int UP = KeyEvent.KEYCODE_DPAD_UP;
    public static final int LEFT = KeyEvent.KEYCODE_DPAD_LEFT;
    public static final int RIGHT = KeyEvent.KEYCODE_DPAD_RIGHT;
    public static final int FIRE = KeyEvent.KEYCODE_DPAD_CENTER;

    public static final int GAME_A = 96;
    public static final int GAME_B = 97;
    public static final int GAME_C = 98;
    public static final int GAME_D = 99;

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

    public static final int BUTTON_A = 96;
    public static final int BUTTON_B = 97;
    public static final int BUTTON_C = 98;
    public static final int BUTTON_X = 99;
    public static final int BUTTON_Y = 100;
    public static final int BUTTON_Z = 101;
    public static final int BUTTON_L1 = 102;
    public static final int BUTTON_R1 = 103;
    public static final int BUTTON_L2 = 104;
    public static final int BUTTON_R2 = 105;
    public static final int BUTTON_START = 108;
    public static final int BUTTON_MENU = KeyEvent.KEYCODE_MENU;
    public static final int BUTTON_BACK = KeyEvent.KEYCODE_BACK;
    public CanvasView m_View;
    public boolean m_bPause = false;

    public Canvas()
    {
        m_View = new CanvasView();
    }

    public boolean hasPointerEvents()
    {
        return (Settings.ms_iScreenTouchType != Configuration.TOUCHSCREEN_NOTOUCH);
    }

    public boolean hasPointerMotionEvents()
    {
        return (Settings.ms_iScreenTouchType != Configuration.TOUCHSCREEN_NOTOUCH);
    }

    protected void paint(Graphics _g)
    {
    }

    protected void pointerPressed(int x, int y)
    {
    }

    protected void pointerDragged(int x, int y)
    {
    }

    protected void pointerReleased(int x, int y)
    {
    }

    public void setFullScreenMode(boolean _bSetFullScreen)
    {
    }

    public String getKeyName(int keycode)
    {
        return "";
    }

   /* SINGLE_BUFFER /*
     public void repaint() {
     if (m_View != null)
     m_View.postInvalidate();
     }

     public final void repaint(int x, int y, int width, int height) {
     if (m_View != null)
     m_View.postInvalidate(x, y, x + width, y + height);
     }

     @Override
     public View getView()
     {
     return m_View;
     }

   /* DOUBLE_BUFFER /**/
    public void repaint() {

        if (m_View == null || m_View.m_SurfaceHolder == null || m_bPause)
        {
            return;
        }

        android.graphics.Canvas vCanvas = null;
        try
        {
            vCanvas = m_View.m_SurfaceHolder.lockCanvas(null);
            if(vCanvas != null) {
            if (ms_vGraphics == null)
            {
                ms_vGraphics = new Graphics(vCanvas);
            }

            paint(ms_vGraphics);
        }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            // do this in a finally so that if an exception is thrown
            // during the above, we don't leave the Surface in an
            // inconsistent state
            if (vCanvas != null)
            {
                m_View.m_SurfaceHolder.unlockCanvasAndPost(vCanvas);
            }
        }
    }

    public final void repaint(int x, int y, int width, int height)
    {

        android.graphics.Canvas vCanvas = null;
        try
        {

            vCanvas = m_View.m_SurfaceHolder.lockCanvas(new Rect(x, y, x + width, y + height));
            if (ms_vGraphics == null)
            {
                ms_vGraphics = new Graphics(vCanvas);
            }

            paint(ms_vGraphics);

        }
        catch (Exception ex)
        {
        }
        finally
        {
            // do this in a finally so that if an exception is thrown
            // during the above, we don't leave the Surface in an
            // inconsistent state
            if (vCanvas != null)
            {
                m_View.m_SurfaceHolder.unlockCanvasAndPost(vCanvas);
            }
        }
    }
    /*

     */

    public boolean isDoubleBuffered()
    {
        return true;
    }

    public void serviceRepaints()
    {
    }

    public int getGameAction(int _iKeyCode)
    {
        return _iKeyCode;
    }

    public int getKeyCode(int _iGameAction)
    {
        return _iGameAction;
    }

    protected void keyPressed(int keyCode)
    {
    }

    protected void keyReleased(int keyCode)
    {
    }

    protected void keyRepeated(int keyCode)
    {
    }

    public final int getWidth()
    {
        return (int)Settings.getWidth();
    }

    public final int getHeight()
    {
        return (int)Settings.getHeight();
    }

    public void setTitle(String s)
    {
        if (s == null)
        {
            MIDlet.ms_Activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        else
        {
            MIDlet.ms_Activity.setTitle(s);
        }
    }

    
    @Override
    public final void pause()
    {
        if (!m_bPause)
        {
            hideNotify();
            m_bPause = true;
        }
    }

    @Override
    public final void unpause()
    {
        if (m_bPause)
        {
            showNotify();
            m_bPause = false;
        }
    }

    protected void hideNotify()
    {
    }

    protected void showNotify()
    {
    }

    public void disposeDisplayable()
    {
    }

    public void initDisplayable(MIDlet midlet)
    {
    }
    // key listener
    public static int ms_iCKeyInt_Map;
    public static char ms_iCKeyChar_Map;

    public class CanvasView extends SurfaceView implements SurfaceHolder.Callback
    {
        public SurfaceHolder m_SurfaceHolder;

        public CanvasView()
        {

            super(MIDlet.ms_Activity);
            
            // register our interest in hearing about changes to our surface
            SurfaceHolder holder = getHolder();
            holder.setFixedSize(Settings.getWidth(), Settings.getHeight());
            holder.addCallback(this);

            setFocusable(true);
            setFocusableInTouchMode(true);
        }

        public void surfaceCreated(SurfaceHolder holder)
        {

            // get handles to some important objects
            m_SurfaceHolder = holder;
            try
            {
                m_SurfaceHolder.setFixedSize(Settings.getWidth(), Settings.getHeight());

            }
            catch (IllegalArgumentException e)
            {
                // TODO: handle exception
            }
        }

        public void surfaceDestroyed(SurfaceHolder holder)
        {
        }

        // (this @Override is commented because this function doesn't exist in old androids, so it will work on newers and in oldies will be just dead code)
        // @Override 
        public void onConfigurationChanged(Configuration newConfig)
        {
            // -- stomp any attempts by the OS to resize my view.
            // super.onConfigurationChanged(newConfig);
            getLayoutParams().width = (int)Settings.ms_iScreenWidth;
            getLayoutParams().height = (int)Settings.ms_iScreenHeight;
        }

        // this hass been moved to ActivityMain to be able to handle interruption not only in Canvas, but in any Displayable
        // if after some time this new configuration doesn't give any problem just delete it (14/08/2014)
        /*
        @Override
        public void onWindowFocusChanged(boolean hasWindowFocus)
        {
            if (!hasWindowFocus)
            {
                pause();
            }
            else
            {
                unpause();
            }
        }
        */

        @Override
        public boolean onKeyDown(int keyCode, KeyEvent msg)
        {

            if (msg != null)
            {
                char mapedChar = (char)msg.getUnicodeChar();
                ms_iCKeyChar_Map = ("" + mapedChar).toLowerCase(Locale.ENGLISH).charAt(0);

                /*
                 int iNumber = msg.getNumber();
                 switch (iNumber) {
                 case '0': keyCode = KeyEvent.KEYCODE_0; break;
                 case '1': keyCode = KeyEvent.KEYCODE_1; break;
                 case '2': keyCode = KeyEvent.KEYCODE_2; break;
                 case '3': keyCode = KeyEvent.KEYCODE_3; break;
                 case '4': keyCode = KeyEvent.KEYCODE_4; break;
                 case '5': keyCode = KeyEvent.KEYCODE_5; break;
                 case '6': keyCode = KeyEvent.KEYCODE_6; break;
                 case '7': keyCode = KeyEvent.KEYCODE_7; break;
                 case '8': keyCode = KeyEvent.KEYCODE_8; break;
                 case '9': keyCode = KeyEvent.KEYCODE_9; break;
                 }
                 */
            }
            if (keyCode == KeyEvent.KEYCODE_DEL)
            {
                keyCode = KeyEvent.KEYCODE_CLEAR;
            }

            if (keyCode == KeyEvent.KEYCODE_ENTER)
            {
                keyCode = KeyEvent.KEYCODE_DPAD_CENTER;
            }

            if (keyCode == BUTTON_BACK)
            {
                keyCode = KeyEvent.KEYCODE_SOFT_LEFT;
            }

            if (keyCode == KeyEvent.KEYCODE_MENU
                    || keyCode == KeyEvent.KEYCODE_HOME
                    || keyCode == KeyEvent.KEYCODE_VOLUME_UP
                    || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)
            {
                return false;
            }

            keyPressed(keyCode);
            return true;
        }

        @Override
        public boolean onKeyUp(int keyCode, KeyEvent msg)
        {

            /*
             if (msg != null) {
             int iNumber = msg.getNumber();
             switch (iNumber) {
             case '0': keyCode = KeyEvent.KEYCODE_0; break;
             case '1': keyCode = KeyEvent.KEYCODE_1; break;
             case '2': keyCode = KeyEvent.KEYCODE_2; break;
             case '3': keyCode = KeyEvent.KEYCODE_3; break;
             case '4': keyCode = KeyEvent.KEYCODE_4; break;
             case '5': keyCode = KeyEvent.KEYCODE_5; break;
             case '6': keyCode = KeyEvent.KEYCODE_6; break;
             case '7': keyCode = KeyEvent.KEYCODE_7; break;
             case '8': keyCode = KeyEvent.KEYCODE_8; break;
             case '9': keyCode = KeyEvent.KEYCODE_9; break;
             }
             }
             */
            if (keyCode == KeyEvent.KEYCODE_DEL)
            {
                keyCode = KeyEvent.KEYCODE_CLEAR;
            }

            if (keyCode == KeyEvent.KEYCODE_ENTER)
            {
                keyCode = KeyEvent.KEYCODE_DPAD_CENTER;
            }

            if (keyCode == BUTTON_BACK)
            {
                keyCode = KeyEvent.KEYCODE_SOFT_LEFT;
            }

            if (keyCode == KeyEvent.KEYCODE_MENU
                    || keyCode == KeyEvent.KEYCODE_HOME
                    || keyCode == KeyEvent.KEYCODE_VOLUME_UP
                    || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)
            {
                return false;
            }

            keyReleased(keyCode);
            return true;
        }

        @Override
        public boolean onTouchEvent(MotionEvent event)
        {

            int action = event.getAction();
            int pointerID = (event.getAction() & 65280) >> 8;

            if (action == MotionEvent.ACTION_DOWN)
            {
        		pointerPressed((int)GetRealX(event.getX()), (int)GetRealY(event.getY()));
                return true;
            }
            if (action == MotionEvent.ACTION_MOVE)
            {
        		pointerDragged((int)GetRealX(event.getX()), (int)GetRealY(event.getY()));
                return true;
            }
            if (action == MotionEvent.ACTION_UP)
            {
        		pointerReleased((int)GetRealX(event.getX()), (int)GetRealY(event.getY()));
                return true;
            }
            return false;
        }

        private float GetRealX(float _fX)
        {
            return (_fX * Settings.ms_fWidthPercent);
        }

        private float GetRealY(float _fY)
        {
            return (_fY * Settings.ms_fHeightPercent);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh)
        {
            if (Settings.ms_iDeviceOS == Settings.OS_BB10QNX)
            {
                MIDlet.ms_Activity.getWindow().setLayout(w, (int)Settings.ms_iScreenHeight);
            }
        }

        public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3)
        {
        }
    }

    @Override
    public View getView()
    {
        return m_View;
    }
}
