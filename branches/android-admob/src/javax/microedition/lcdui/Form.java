package javax.microedition.lcdui;

//import j2ab.android.lcdui.Toolkit;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.microedition.Settings;
import javax.microedition.midlet.MIDlet;

public final class Form extends Screen
{
    private String title;
    private MIDlet midlet;

    private FrameLayout m_View;
    private ScrollView m_ScrollView;
    private LinearLayout m_ListView;
    private LinearLayout m_BarTitleView;
    private LinearLayout m_BarCommandView;
    private TextView m_vSoftKeyLeft;
    private TextView m_vSoftKeyRight;

    private List<Item> m_iItems;
    private List<Command> m_iCommands;

    private GradientDrawable m_vGradientBars;
    private GradientDrawable m_vGradientSeparator;
    private GradientDrawable m_vGradientBack;
    private GradientDrawable m_vGradientSoftKeyReleased;
    private GradientDrawable m_vGradientSoftKeyPressed;

    public Form(String title)
    {
        this.m_iItems = new ArrayList<Item>();
        this.title = title;
    }

    public Form(String title, Item[] items)
    {
    	this( title );
    	for( int i=0; i<items.length; i++ )
    	{
            append(items[i]);
    	}
    }

    public String getTitle()
    {
        return this.title;
    }

    public void setTitle( String title )
    {
        this.title = title;
    }

    public int append(Image _vImage)
    {
        // TODO no hacer append si la imagen es nula y el texto alternativo tambien
        return append(new ImageItem(null, _vImage, ImageItem.LAYOUT_DEFAULT, null));
    }

    public int append(String _zText)
    {
        return append(new StringItem(null, _zText));
    }

    public int append( Item item )
    {
        verifyItem(item);
        if (this.midlet != null)
        {
            item.init(this.midlet, m_ListView);
            m_ListView.addView(item.getView());
        }
        m_iItems.add( item );
        return m_iItems.size() - 1;
    }

    private void verifyItem(Item item) {
        // Check that we are being passed valid items
        if (item == null) {
            throw new NullPointerException("item is null");
        }
        if (item.getOwner() != null) {
            throw new IllegalStateException("item is already owned");
        }
        // All is ok make ourselves the owner
        item.setOwner(this);
    }

    public void delete(int _iItemIndex) {
        if (this.midlet != null)
        {
            m_ListView.removeView(m_iItems.get(_iItemIndex).getView());
        }
        Item item = m_iItems.remove(_iItemIndex);
        item.setOwner(null);
    }

    public void deleteAll() {
        if (this.midlet != null)
        {
            m_ListView.removeAllViews();
        }
        for(Item item : m_iItems) {
            item.setOwner(null);
        }
        m_iItems.removeAll(m_iItems);
    }

    @Override
    public void disposeDisplayable()
    {
        for( int i=0; i<this.m_iItems.size(); i++ )
        {
            Item item = this.m_iItems.get( i );
            item.dispose();
        }
        this.midlet = null;

        m_ListView = null;
        m_ScrollView = null;
        m_BarCommandView = null;
        m_BarTitleView = null;
        m_View = null;

//        MIDlet.ms_Activity.setContentView(Canvas.ms_Canvas.getView());
    }

    @Override
    public View getView()
    {
        return m_View;
    }

    @Override
    public void initDisplayable( MIDlet midlet )
    {
        this.midlet = midlet;

        m_vGradientBars = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    new int[] {0xFF616261,0xFF131313});
        m_vGradientBack = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    new int[] {0xFFeeeeee,0xFFcccccc});
        m_vGradientSeparator = new GradientDrawable(
                    GradientDrawable.Orientation.RIGHT_LEFT,
                    new int[] {0x00aaaaaa,0xFFaaaaaa,0x00aaaaaa});

        m_vGradientSoftKeyReleased = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    new int[] {0xFF616261,0xFF131313});
        m_vGradientSoftKeyPressed = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    new int[] {0xFFff9933,0xFF996633});

        m_vGradientBars.setCornerRadius(0f);
        m_vGradientBack.setCornerRadius(0f);

        // root view
        m_View = new FrameLayout(MIDlet.ms_Activity);
        m_View.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));

        // title upper bars
        m_BarTitleView = new LinearLayout(MIDlet.ms_Activity);
        m_BarTitleView.setOrientation(LinearLayout.HORIZONTAL);

        Drawable vIcon = null;
        try {
            vIcon = MIDlet.ms_Activity.getPackageManager().getApplicationIcon(MIDlet.ms_Activity.getPackageName());
        } catch (Exception ex) {
            Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (vIcon != null) {
            ImageView vImageView = new ImageView(MIDlet.ms_Activity);
            vImageView.setImageDrawable(vIcon);
            vImageView.setLayoutParams(new FrameLayout.LayoutParams(Item.COMMAND_ICON_HEIGHT, Item.COMMAND_ICON_HEIGHT, Gravity.CENTER));

            FrameLayout vContainer = new FrameLayout(MIDlet.ms_Activity);
            vContainer.setLayoutParams(new FrameLayout.LayoutParams(Item.COMMAND_BAR_HEIGHT, Item.COMMAND_BAR_HEIGHT));
            vContainer.setBackgroundDrawable(m_vGradientBars);
            vContainer.addView(vImageView);

            m_BarTitleView.addView(vContainer);
        }

        //
        TextView vEditText = new EditText(MIDlet.ms_Activity);
        vEditText.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, Item.COMMAND_BAR_HEIGHT));

        vEditText.setText(title);
        vEditText.setTypeface(Typeface.DEFAULT_BOLD);
        vEditText.setTextColor(0xffffffff);
        vEditText.setTextSize(14);
        vEditText.setPadding(5, 0, 0, 0);

        vEditText.setFocusable(false);
        vEditText.setEnabled(false);
        vEditText.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
        vEditText.setBackgroundDrawable(m_vGradientBars);

        m_BarTitleView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, Item.COMMAND_BAR_HEIGHT, Gravity.LEFT|Gravity.TOP));
        m_BarTitleView.addView(vEditText);

        // command bottom bars
        if (Settings.getVersionPlatform() < 3.0) { //Build.VERSION_CODES.HONEYCOMB)
            m_BarCommandView = new LinearLayout(MIDlet.ms_Activity);
            m_BarCommandView.setOrientation(LinearLayout.HORIZONTAL);

            if (Display.getDisplay(midlet).getCurrent().existCommandByType(Command.EXIT)) {
                m_vSoftKeyLeft = new TextView(MIDlet.ms_Activity);
                m_vSoftKeyLeft.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, Item.COMMAND_BAR_HEIGHT, Gravity.LEFT|Gravity.CENTER_VERTICAL));
                m_vSoftKeyLeft.setText("  " + Display.getDisplay(midlet).getCurrent().getCommandByType(Command.EXIT).getLabel());
                m_vSoftKeyLeft.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
                m_vSoftKeyLeft.setTypeface(Typeface.DEFAULT_BOLD);
                m_vSoftKeyLeft.setTextColor(0xffffffff);
                m_vSoftKeyLeft.setBackgroundDrawable(m_vGradientSoftKeyReleased);

                m_vSoftKeyLeft.setOnTouchListener(new View.OnTouchListener() {
                    public boolean onTouch(View arg0, MotionEvent arg1) {
                        if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                            m_vSoftKeyLeft.setBackgroundDrawable(m_vGradientSoftKeyPressed);
                        }
                        if (arg1.getAction() == MotionEvent.ACTION_UP) {
                            m_vSoftKeyLeft.setBackgroundDrawable(m_vGradientSoftKeyReleased);
                            MIDlet.getInstance().notifyDestroyed();
                        }
                        return true;
                    }
                });
                m_BarCommandView.addView(m_vSoftKeyLeft);
            }

            if ((Display.getDisplay(midlet).getCurrent().getCommands().size() == 1 &&
                !Display.getDisplay(midlet).getCurrent().existCommandByType(Command.EXIT)) ||
                !Display.getDisplay(midlet).getCurrent().getCommands().isEmpty()) {
                m_vSoftKeyRight = new TextView(MIDlet.ms_Activity);
                m_vSoftKeyRight.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, Item.COMMAND_BAR_HEIGHT, Gravity.RIGHT|Gravity.CENTER_VERTICAL));
                m_vSoftKeyRight.setText("Menu  ");
                m_vSoftKeyRight.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
                m_vSoftKeyRight.setTypeface(Typeface.DEFAULT_BOLD);
                m_vSoftKeyRight.setTextColor(0xffffffff);
                m_vSoftKeyRight.setBackgroundDrawable(m_vGradientSoftKeyReleased);

                m_vSoftKeyRight.setOnTouchListener(new View.OnTouchListener() {
                    public boolean onTouch(View arg0, MotionEvent arg1) {
                        if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                            m_vSoftKeyRight.setBackgroundDrawable(m_vGradientSoftKeyPressed);
                        }
                        if (arg1.getAction() == MotionEvent.ACTION_UP) {
                            m_vSoftKeyRight.setBackgroundDrawable(m_vGradientSoftKeyReleased);
                            MIDlet.ms_Activity.openOptionsMenu();
                        }
                        return true;
                    }
                });
                m_BarCommandView.addView(m_vSoftKeyRight);
            }

            m_BarCommandView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, Item.COMMAND_BAR_HEIGHT, Gravity.LEFT|Gravity.BOTTOM));
            m_BarCommandView.setBackgroundDrawable(m_vGradientBars);
        }

        // scroll view
        LinearLayout.LayoutParams scrollViewLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
        scrollViewLp.setMargins(0, Item.COMMAND_BAR_HEIGHT, 0, Item.COMMAND_BAR_HEIGHT);

        m_ListView = new LinearLayout(MIDlet.ms_Activity);
        m_ListView.setOrientation(LinearLayout.VERTICAL);
        m_ListView.setLayoutParams(scrollViewLp);

        m_ScrollView = new ScrollView(MIDlet.ms_Activity);
        m_ScrollView.setLayoutParams(scrollViewLp);
        m_ScrollView.setBackgroundDrawable(m_vGradientBack);
        m_ScrollView.setVerticalScrollBarEnabled(false);


        // dummy upper header
        vEditText = new TextView(MIDlet.ms_Activity);
        vEditText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT, Item.COMMAND_BAR_HEIGHT));
        m_ListView.addView(vEditText);

        // add elements to scroll view
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(Item.MARGIN, Item.MARGIN>>1, Item.MARGIN, Item.MARGIN>>1);
        Item item = null;
        for (int i = 0; i < this.m_iItems.size(); i++) {

            // add separator line
            if (i > 0) {
                ImageView lineView = new ImageView(MIDlet.ms_Activity);
                lineView.setBackgroundDrawable(m_vGradientSeparator);
                lineView.setLayoutParams(new LinearLayout.LayoutParams(layoutParams.width, 1));
                m_ListView.addView(lineView);
            }

            // concat string items
            if(item != null && item instanceof StringItem && (this.m_iItems.get(i) instanceof StringItem)) {
                ((StringItem)item).appendStringItem((StringItem)this.m_iItems.get(i));
            } else {
                item = this.m_iItems.get(i);
            item.init(midlet, m_ListView);
            View itemView = item.getView();
            itemView.setLayoutParams(
                    new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            m_ListView.addView(itemView, layoutParams);
        }
        }

        // dummy bottom header
        if (Settings.getVersionPlatform() < 3.0) { //Build.VERSION_CODES.HONEYCOMB)
            vEditText = new TextView(MIDlet.ms_Activity);
            vEditText.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT, Item.COMMAND_BAR_HEIGHT));
            m_ListView.addView(vEditText);
        }

        m_ScrollView.addView(m_ListView);

        // add all views to root view
        m_View.addView(m_ScrollView);
        m_View.addView(m_BarTitleView);

        if (Settings.getVersionPlatform() < 3.0) { //Build.VERSION_CODES.HONEYCOMB)
            m_View.addView(m_BarCommandView);
        }
    }

    public void set(int _iItemIndex, Item _vItem) {
    }

    public Item get( int pos )
    {
    	return this.m_iItems.get( pos );
    }

    List <Item> getItems() {
        return m_iItems;
    }
}
