package javax.microedition.lcdui;

import android.content.Context;
import android.os.*;
import java.util.HashMap;
import javax.microedition.Settings;
import javax.microedition.midlet.MIDlet;

public class Display
{
	public static final int COLOR_BACKGROUND = 0;
	public static final int COLOR_FOREGROUND = 1;
	public static final int COLOR_BORDER	 = 2;
	public static final int COLOR_HIGHLIGHTED_BACKGROUND 	= 3;
	public static final int COLOR_HIGHLIGHTED_FOREGROUND 	= 4;
	public static final int COLOR_HIGHLIGHTED_BORDER 		= 5;
	public static final int LIST_ELEMENT 			= 0;
	public static final int ALERT 					= 1;
	public static final int CHOICE_GROUP_ELEMENT 	= 2;
    private static final HashMap<MIDlet, Display> DISPLAYS = new HashMap<MIDlet, Display>(1);

    public static Display getDisplay(MIDlet midlet)
    {
        Display display = DISPLAYS.get(midlet);
        if (display == null)
        {
            display = new Display(midlet);
            DISPLAYS.put(midlet, display);
        }
        return display;
    }
    private Displayable m_CurrentDisplayable;
    private MIDlet m_MIDlet;

    private Display(MIDlet midlet)
    {
        m_MIDlet = midlet;
    }

    public Displayable getCurrent()
    {
        return m_CurrentDisplayable;
    }

    public boolean flashBacklight(int duration)
    {
        return false;
    }

    public int getColor(int colorSpecifier)
    {
        // TODO :is there any way to look this up
        int color;
        switch (colorSpecifier)
        {
            case COLOR_BACKGROUND:
                color = 0x000000;
                break;
            case COLOR_FOREGROUND:
                color = 0xFFFFFF;
                break;
            case COLOR_BORDER:
                color = 0x888888;
                break;
            case COLOR_HIGHLIGHTED_BACKGROUND:
                color = 0xff8600;
                break;
            case COLOR_HIGHLIGHTED_FOREGROUND:
                color = 0x000000;
                break;
            case COLOR_HIGHLIGHTED_BORDER:
                color = 0xAAAAAA;
                break;
            default:
                color = 0xFF0000;
                break;
        }
        return color;
    }

    public MIDlet getMIDlet()
    {
        return m_MIDlet;
    }

    public void setCurrent(Alert alert, Displayable current)
    {
        this.setCurrent(current);
        alert.getDialog().show();
    }

    public void setCurrent(final Displayable current)
    {
        if (current != null)
        {
            if (current instanceof Alert) {
                current.initDisplayable(Display.this.m_MIDlet);
                
            } else {
                m_CurrentDisplayable = current;
                new Handler(Looper.getMainLooper(), new Handler.Callback() {

                    public boolean handleMessage(Message arg0) {
                        m_CurrentDisplayable.initDisplayable(Display.this.m_MIDlet);
                        m_CurrentDisplayable.setCurrentDisplay(Display.this);
                        MIDlet.setContentView(m_CurrentDisplayable.getView());
                        return true;
                    }
                }).sendEmptyMessage(0);
            }            
        }
    }

    public int numColors()
    {
        return 65536;
    }

    public int numAlphaLevels()
    {
        return 256;
    }

    public boolean vibrate(int duration)
    {
        ((Vibrator)MIDlet.ms_Activity.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(duration);
        return Settings.ms_bHasVibration;
    }
}
