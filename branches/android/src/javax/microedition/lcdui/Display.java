package javax.microedition.lcdui;

import java.util.Hashtable;
import javax.microedition.midlet.MIDlet;

import com.kitmaker.finalkombat.ActivityMain;

import android.content.Context;
import android.os.Vibrator;

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
	
    private static final Hashtable<MIDlet, Display> DISPLAYS = new Hashtable<MIDlet, Display>( 1 );

    public static Display getDisplay( MIDlet midlet )
    {
        Display display = DISPLAYS.get( midlet );
        if( display == null )
        {
            display = new Display( midlet );
            DISPLAYS.put( midlet, display );
        }
        return display;
    }
    
    private Displayable current;
    private MIDlet midlet;

    private Display( MIDlet midlet )
    {
        this.midlet = midlet;
    }
    
    public Displayable getCurrent()
    {
        return this.current;
    }
    
    public int getColor( int colorSpecifier ) {
    	// TODO :is there any way to look this up
    	int color;
    	switch( colorSpecifier ) {
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
    
    public int getBestImageWidth( int imageType ) {
    	return 48;
    }
    
    public int getBestImageHeight( int imageType ) {
    	return 48;
    }
    
    public MIDlet getMIDlet()
    {
    	return this.midlet;
    }
    
	public void setCurrent( Alert alert, Displayable current ) {
		this.setCurrent( current );
		alert.getDialog().show();
	}
	
	public void setCurrent(final Displayable current) {
		ActivityMain.ms_vMain.setContentView(ActivityMain.ms_Canvas.getView());
	}
    
    public int numColors() {
    	return 65536;
    }
    
    public int numAlphaLevels() {
    	return 256;
    }
    
    public boolean vibrate(int duration) {
		((Vibrator) ActivityMain.ms_Context.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(duration);
    	return true;
    }
}
