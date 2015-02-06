package javax.microedition.lcdui;

public class AlertType {

    public static final AlertType ALARM = new AlertType();
    public static final AlertType CONFIRMATION = new AlertType();
    public static final AlertType ERROR = new AlertType();
    public static final AlertType INFO = new AlertType();
    public static final AlertType WARNING = new AlertType();
    
    public int m_vIcon;

    protected AlertType() {
        if (this == ALARM) {
            m_vIcon = android.R.drawable.ic_dialog_alert;
            
        } else if (this == CONFIRMATION) {
            m_vIcon = android.R.drawable.ic_dialog_info;
            
        } else if (this == ERROR) {
            m_vIcon = android.R.drawable.ic_dialog_alert;
            
        } else if (this == INFO) {
            m_vIcon = android.R.drawable.ic_dialog_info;
            
        } else if (this == WARNING) {
            m_vIcon = android.R.drawable.ic_dialog_alert;
        }
    }

    public boolean playSound(Display display) {
        return false;
    }
}
