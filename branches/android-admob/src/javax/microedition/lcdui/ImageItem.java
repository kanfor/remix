package javax.microedition.lcdui;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import javax.microedition.midlet.MIDlet;

public class ImageItem extends Item implements Runnable {

    private MIDlet midlet;
    
    private int m_iAppearanceMode;
    private int m_iLayout;
    private String m_zLabel;
    private String m_zAltText;
    private Image m_vImage;
    
    private View m_vView;
    private TextView m_vLabelView;
    private TextView m_vAltTextView;
    private ImageView m_vImageView;

    public ImageItem(String label, Image _vImage, int layout, String altText)  {
        this(label, _vImage, layout, altText, 0);
    }

    public ImageItem(String _zLabel, Image _vImage, int _iLayout, String _zAltText, int _iAppearanceMode) {
        //setLabel(label);
        m_zLabel = _zLabel;
        m_vImage = _vImage;
        m_zAltText = _zAltText;
        m_iLayout = _iLayout;
        m_iAppearanceMode = _iAppearanceMode;
    }

    public String getAltText() {
        return m_zAltText;
    }
    
    public int getAppearanceMode() {
        return m_iAppearanceMode;
    }
    
    public Image getImage() {
        return m_vImage;
    }
    
    @Override
    public String getLabel() {
        return m_zLabel;
    }

    @Override
    public int getLayout() {
        return m_iLayout;
    }
    
    public void setAltText(String _zText) {
        m_zAltText = _zText;
        if (m_vAltTextView != null) {
            midlet.getHandler().post(this);
        }
    }

    public void setImage(Image _vImage) {
        m_vImage = _vImage;
    }
    
    @Override
    public void setLayout (int _iLayout) {
        m_iLayout = _iLayout;
    }

    @Override
    public void dispose() {
        m_vView = null;
        m_vAltTextView = null;
        m_vLabelView = null;
    }
    
    @Override
    public View getView() {
        return this.m_vView;
    }

    @Override
    public void init(MIDlet midlet, ViewGroup parent) {
        this.midlet = midlet;
        
    	LinearLayout linearLay = new LinearLayout(MIDlet.ms_Activity);
        linearLay.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, 
                LinearLayout.LayoutParams.FILL_PARENT);
        
        // add label
        if (getLabel() != null) {
            m_vLabelView = new TextView(MIDlet.ms_Activity);
            m_vLabelView.setText(getLabel());
            m_vLabelView.setTextColor(0xff000000);
            m_vLabelView.setLayoutParams(layoutParams);
            linearLay.addView(m_vLabelView);
        }
        
        // add image
        if (getImage() != null) {
            m_vImageView = new ImageView(MIDlet.ms_Activity);
            m_vImageView.setImageBitmap(m_vImage.getBitmap());
            m_vImageView.setLayoutParams(layoutParams);
            linearLay.addView(m_vImageView);
            
        // if we don't have image we write alternative text instead
        } else if (getAltText() != null) {
            m_vAltTextView = new TextView(MIDlet.ms_Activity);
            m_vAltTextView.setText(this.m_zAltText);
            m_vAltTextView.setTextColor(0xff000000);
            m_vAltTextView.setLayoutParams(layoutParams);
            linearLay.addView(m_vAltTextView);
        }
        
        layoutParams.gravity = Gravity.LEFT;
        if (m_iLayout == LAYOUT_CENTER)
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        else if (m_iLayout == LAYOUT_RIGHT)
            layoutParams.gravity = Gravity.RIGHT;
        
        linearLay.setLayoutParams(layoutParams);
        linearLay.setGravity(Gravity.LEFT);
        m_vView = linearLay.getRootView();
        m_vView.setLayoutParams(layoutParams);
        
    }

//	@Override
    public void run() {
        if (this.m_vAltTextView != null) {
            this.m_vAltTextView.setText(this.m_zAltText);
        }
        if (this.m_vLabelView != null) {
            this.m_vLabelView.setText(this.getLabel());
        }
    }
}
