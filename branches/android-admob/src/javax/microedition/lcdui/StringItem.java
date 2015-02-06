package javax.microedition.lcdui;

import android.graphics.drawable.GradientDrawable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.StyleSpan;
import android.text.util.Linkify;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import javax.microedition.midlet.MIDlet;

public final class StringItem extends Item {

    private int m_iLayout;
    private View m_vView;
    private TextView m_vTextView;
    private int m_iAppearanceMode;
    
    private String m_zLabel;
    private String m_zText;
    private GradientDrawable m_vGradient1;
    private GradientDrawable m_vGradient2;

    public StringItem(String label, String text) {
        this(label, text, PLAIN);
    }

    public StringItem(String label, String text, int appearanceMode) {
        setLabel(label);
        m_zLabel = label;
        m_zText = text;
        m_iAppearanceMode = appearanceMode;
        m_iLayout = Item.LAYOUT_DEFAULT;
    }

    @Override
    public int getLayout() {
        return m_iLayout;
    }
    
    @Override
    public String getLabel() {
        return m_zLabel;
    }
    
    public String getText() {
        return Html.fromHtml(m_zText).toString();
    }

    @Override
    public void setLayout (int _iLayout) {
        m_iLayout = _iLayout;
        if (m_vView != null && m_vTextView != null) {
            
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (m_iLayout == Item.LAYOUT_DEFAULT || m_iLayout == Item.LAYOUT_LEFT) {
                layoutParams.gravity = Gravity.LEFT;
            } else if (m_iLayout == Item.LAYOUT_CENTER) {
                layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
            } else if (m_iLayout == Item.LAYOUT_RIGHT) {
                layoutParams.gravity = Gravity.RIGHT;
            }
            m_vTextView.setLayoutParams(layoutParams);
        }
    }

    @Override
    public void setLabel(String label) {
        super.setLabel(label);
        m_zLabel = label;
        if (m_vView != null && m_vTextView != null) {
            SpannableStringBuilder zText = composeText();
            m_vTextView.setText(zText);
            //this.midlet.getHandler().post(this);
        }
    }

    public void setText(String text) {
        m_zText = text;
        if (m_vView != null && m_vTextView != null) {
            SpannableStringBuilder zText = composeText();
            m_vTextView.setText(zText);
            //this.midlet.getHandler().post(this);
        }
    }
    
    @Override
    public void dispose() {
        m_vView = null;
    }

    @Override
    public View getView() {
        return m_vView;
    }

    public int getAppearanceMode() {
        return m_iAppearanceMode;
    }

    @Override
    public void init(MIDlet midlet, ViewGroup parent) {

        SpannableStringBuilder zText = composeText();
        
        // layout
    	LinearLayout linearLay = new LinearLayout(MIDlet.ms_Activity);
        linearLay.setOrientation(LinearLayout.VERTICAL);
            
        if (m_iAppearanceMode == PLAIN ) {
            m_vTextView = new TextView(MIDlet.ms_Activity);
            m_vTextView.setText(zText);
            m_vTextView.setTextColor(0xff000000);
        } else if (m_iAppearanceMode == HYPERLINK) {
            m_vTextView = new TextView(MIDlet.ms_Activity);
            
            m_vTextView.setLinksClickable(true);
            m_vTextView.setMovementMethod(LinkMovementMethod.getInstance());
            m_vTextView.setAutoLinkMask(Linkify.WEB_URLS);
            m_vTextView.setLinkTextColor(0xff000080);
            
            if(m_zText.startsWith("http")) {
                m_vTextView.setText(m_zLabel);
            } else {
                m_vTextView.setText(zText);
            }
            m_vTextView.setAutoLinkMask(Linkify.WEB_URLS);
            final Item item = this;
            m_vTextView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (defaultCommand != null && commandListener != null) {
                    commandListener.commandAction(defaultCommand, item);
                    return true;
                } else {
                    return false;
                }
            }
        });
        } else if (m_iAppearanceMode == BUTTON) {
            m_vGradient1 = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    new int[] {0xFF616261,0xFF131313});
            
            m_vGradient2 = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    new int[] {0xFF919291,0xFF434343});
            
            m_vTextView = new Button(MIDlet.ms_Activity);
            m_vTextView.setText(zText);
            m_vTextView.setTextColor(0xffffffff);
            m_vTextView.setFocusable(true);
            m_vTextView.setClickable(true);
            m_vTextView.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View arg0, MotionEvent event) {
                    
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN) {
                        m_vTextView.setBackgroundDrawable(m_vGradient2);
                    } else if (action == MotionEvent.ACTION_UP) {
                        m_vTextView.setBackgroundDrawable(m_vGradient1);
                    }
                    return false;
                }
            });
           
            m_vTextView.setBackgroundDrawable(m_vGradient1);
            
        }
        
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, 
                LinearLayout.LayoutParams.FILL_PARENT);
        
        if (m_iLayout == Item.LAYOUT_CENTER)
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        else if (m_iLayout == Item.LAYOUT_RIGHT)
            layoutParams.gravity = Gravity.RIGHT;
        
        m_vTextView.setLayoutParams(layoutParams);
        linearLay.addView(m_vTextView);
        m_vView = linearLay.getRootView();
        
    }
    
    
    private SpannableStringBuilder composeText() {
        String zLabel = "";
        String zText = "";
        
        if (getLabel() != null)
            zLabel = getLabel();
        if (getText() != null) {
            if (!zLabel.equals(""))
                zText = " ";

            zText += getText();
        }
        
        SpannableStringBuilder sb = new SpannableStringBuilder(zLabel + Html.fromHtml(zText));
        if (!zLabel.equals("")) {
            StyleSpan b = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
            sb.setSpan(b, 0, zLabel.length(), SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE); // make first 4 characters Bold
        }
        return sb;
    }

    void appendStringItem(final StringItem text) {
        String zText = text.getText();
        if(text.getAppearanceMode() == Item.HYPERLINK) {
            zText = "<font color=#000080>"+zText+ "</font>";
        }
        
        m_vTextView.append(Html.fromHtml(zText));
        
        if(text.defaultCommand != null && text.commandListener != null) {
            if(owner != null) {
                owner.addCommand(text.defaultCommand);
                // the stringItem that is being concatinated is already in the items list
//                addCommand(text.defaultCommand);
//                this.setDefaultCommand(text.defaultCommand);
//                if(this.commandListener == null) {
//                    this.setItemCommandListener(text.commandListener);
//                }
            }
            m_vTextView.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    text.commandListener.commandAction(text.defaultCommand, text);
                }
            });
        }
    }
}
