/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javax.microedition.lcdui.game;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

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
    public static final int GAME_A_PRESSED = Canvas.GAME_A; //(1 << Canvas.GAME_A);
    public static final int GAME_B_PRESSED = Canvas.GAME_B; //(1 << Canvas.GAME_B);
    public static final int GAME_C_PRESSED = Canvas.GAME_C; //(1 << Canvas.GAME_C);
    public static final int GAME_D_PRESSED = Canvas.GAME_D; //(1 << Canvas.GAME_D);

    private Image m_Buffer;
    private android.graphics.Canvas m_vGameCanvas;
    private boolean m_bLocked;
    public static Graphics ms_bGraphics;
        
    public GameCanvas(boolean suppressKeyEvents) {
    }

    protected Graphics getGraphics() {
        if (m_View == null ||  m_View.m_SurfaceHolder == null || m_bPause) {
                return null;
        }
        
        if (!m_bLocked) {
            
            m_vGameCanvas = null;
            m_vGameCanvas = m_View.m_SurfaceHolder.lockCanvas(null);
            m_bLocked = true;
            
            if (ms_vGraphics == null) {
                ms_vGraphics = new Graphics(m_vGameCanvas);
            }
            if (m_Buffer != null) {
                ms_bGraphics = m_Buffer.getGraphics();
            }
        }

        return ms_bGraphics != null ? ms_bGraphics : ms_vGraphics;
    }

    public int getKeyStates() {
        return ms_iCKeyInt_Map;
    }

    public void flushGraphics() {
        if (m_bLocked) {
            if (ms_bGraphics != null) {
                ms_vGraphics.drawImage(m_Buffer, 0, 0);
            }
            m_View.m_SurfaceHolder.unlockCanvasAndPost(m_vGameCanvas);
        }
        m_bLocked = false;
        //repaint();
    }

    public void flushGraphics(int x, int y, int width, int height) {
        repaint(x, y, width, height);
    }
}
