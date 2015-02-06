package javax.microedition.lcdui;

import javax.microedition.midlet.MIDlet;

import com.kitmaker.finalkombat.ActivityMain;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

public class Alert extends Screen {

	public static final long FOREVER = -1;
	public static final long DEFAULT_TIMEOUT = 3000;
	
	private String title;
	private String message;
	private Image image;
	
	private AlertDialog alert;
	
	public Alert( String title ) {
		this( title, null, null, null );
	}
	
	public Alert( String title, String message, Image image, AlertType type ) {
		
	}
	
	@Override
	public void disposeDisplayable() {
		this.alert = null;
	}

	@Override
	public View getView() {
		return null;
	}

	@Override
	public void initDisplayable(MIDlet midlet) {
		AlertDialog.Builder builder = new AlertDialog.Builder (ActivityMain.ms_vMain);
		if( this.title != null ) {
			builder.setTitle( this.title );
		}
		if( this.message != null ) {
			builder.setMessage( this.message );
		}
		if( this.image != null ) {
			builder.setIcon( new BitmapDrawable( this.image.getBitmap() ) );
		}
		
		// TODO : different types!
//		Toolkit toolkit = midlet.getToolkit();
//		this.alert = (AlertDialog)toolkit.inflate( toolkit.getResourceId( "" ))
		builder.setCancelable( true );
		this.alert = builder.create();
		// TODO : timeouts
	}
	
	public Dialog getDialog() {
		return this.alert;
	}

	//private long timeout = DEFAULT_TIMEOUT;
	public void setTimeout( long timeout ) {
		//this.timeout = timeout;
	}
}
