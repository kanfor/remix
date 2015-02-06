package javax.microedition.midlet;

import java.util.Properties;

import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;

import com.kitmaker.finalkombat.ActivityMain;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;

public abstract class MIDlet
{
	public static final String PROTOCOL_HTTP 	= "http://";
	public static final String PROTOCOL_HTTPS 	= "https://";
	public static final String PROTOCOL_SMS		= "sms:";
	public static final String PROTOCOL_PHONE	= "tel:";
	public static final String PROTOCOL_EMAIL	= "email:";

	public static Properties DEFAULT_APPLICATION_PROPERTIES;

	private android.view.Menu menu;
	private Properties applicationProperties = DEFAULT_APPLICATION_PROPERTIES;
	
	public Properties getApplicationProperties() {
		return applicationProperties;
	}

	public void setApplicationProperties(Properties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}

	public android.view.Menu getMenu() {
		return menu;
	}

	public void setMenu(android.view.Menu menu) {
		this.menu = menu;
		
		Display display = Display.getDisplay( this );
		Displayable current = display.getCurrent(); 
		// load the menu items
		if( current != null )
			current.addCommandsToDisplay( display );
	}
	
	public Handler getHandler() {
		return ActivityMain.ms_Canvas.m_View.getHandler();
	}
	
	protected abstract void destroyApp( boolean unconditional )
        throws MIDletStateChangeException;
    
    protected abstract void pauseApp()
        throws MIDletStateChangeException; 
    
    protected abstract void startApp()
        throws MIDletStateChangeException;
    
    public final void notifyDestroyed() {
		Canvas.ms_vGraphics = null;
		ActivityMain.ms_Canvas.m_View = null;
		ActivityMain.ms_Canvas = null;
    	ActivityMain.ms_vMain.finish();
    	System.exit(0);
    }    

	public final void doDestroyApp(boolean unconditional) throws MIDletStateChangeException {
		this.destroyApp(unconditional);
	}

	public final void doStartApp() throws MIDletStateChangeException {
		this.startApp();
	}

	public final void doPauseApp() throws MIDletStateChangeException {
		this.pauseApp();
	}

	public boolean platformRequest(String url) throws ConnectionNotFoundException {
		Uri content = Uri.parse(url);
		String action;
		if (url.startsWith(PROTOCOL_PHONE))
			action = Intent.ACTION_DIAL;
		else
			action = Intent.ACTION_DEFAULT;
		
		Intent intent = new Intent(action, content);
		ActivityMain.ms_vMain.startActivity(intent);
		return false;
	}

	public String getAppProperty(String key) {
		return this.applicationProperties.getProperty(key);
	}
    
}
