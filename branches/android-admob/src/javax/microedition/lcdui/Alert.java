package javax.microedition.lcdui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import javax.microedition.midlet.MIDlet;

public class Alert extends Screen {

    public static final long FOREVER = -2;
    private String m_zTitle;
    private String m_zAlertText;
    private Image m_vImage;
    private AlertDialog m_AlertDialog;
    private FrameLayout m_vView;
    private AlertType m_AlertType;
    
    public static final int DEFAULT_TIMEOUT = 3000;
    private int m_iTimeout = DEFAULT_TIMEOUT;

    public static Command DISMISS_COMMAND;
    
    public Alert(String title) {
        this(title, null, null, null);
    }

    public Alert(String _zTitle, String _alertText, Image _image, AlertType type) {
        m_zTitle = _zTitle;
        m_zAlertText = _alertText;
        m_vImage = _image;
        m_iTimeout = 1000;
    }

    @Override
    public void disposeDisplayable() {
        m_AlertDialog = null;
        m_vView = null;
    }

    @Override
    public View getView() {
        return null;
    }

    @Override
    public void initDisplayable(MIDlet midlet) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MIDlet.ms_Activity);
        if (m_zTitle != null) {
            builder.setTitle(m_zTitle);
        }
        if (m_zAlertText != null) {
            builder.setMessage(m_zAlertText);
        }
        
        if (m_vImage != null) {
            builder.setIcon(new BitmapDrawable(m_vImage.getBitmap()));
            
        } else if (m_AlertType != null) {
            builder.setIcon(m_AlertType.m_vIcon);
        }
        
        builder.setCancelable(true);
        m_AlertDialog = builder.create();
        m_AlertDialog.show();
        
        if (m_iTimeout > 0) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {

                public void run() {
                    m_AlertDialog.cancel();
                    m_AlertDialog.dismiss();
                }
            }, m_iTimeout);
        }
        
        m_vView = new FrameLayout (MIDlet.ms_Activity);
        m_vView.setBackgroundColor(0xff888888);
        
    }

    public Dialog getDialog() {
        return m_AlertDialog;
    }

    public static class CommandComparator {
    }

    public static class AlertRunnable {
    }
    
    public int getDefaultTimeout() {
        return DEFAULT_TIMEOUT;
    }

    public int getTimeout() {
        return m_iTimeout;
    }

    public void setTimeout(int time) {
        m_iTimeout = time;
    }

    public AlertType getType() {
        return m_AlertType;
    }

    public void setType(AlertType type) {
        m_AlertType = type;
    }

    public String getString() {
        return m_zAlertText;
    }

    public void setString(String str) {
        m_zAlertText = str;
    }

    public Image getImage() {
        return m_vImage;
    }

    public void setImage(Image img) {
        m_vImage = img;
    }

    // TODO
    /*
    public native void setIndicator(Gauge indicator) {
        
    }

    public native Gauge getIndicator();
    * 
    */

    public String getTitle() {
        return m_zTitle;
    }

    public void setTitle(String title) {
        m_zTitle = title;
    }
    
}
