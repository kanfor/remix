package javax.microedition.lcdui;

import android.view.View;
import android.view.ViewGroup;
import java.util.Vector;
import javax.microedition.Settings;
import javax.microedition.midlet.MIDlet;

public abstract class Item
{
    public static final int PLAIN                   = 0;
    public static final int HYPERLINK               = 1;
    public static final int BUTTON                  = 2;
    
    public static final int LAYOUT_DEFAULT          = 0;
    public static final int LAYOUT_LEFT             = 1;
    public static final int LAYOUT_RIGHT            = 2;
    public static final int LAYOUT_CENTER           = 3;
    public static final int LAYOUT_TOP              = 0x10;
    public static final int LAYOUT_BOTTOM           = 0x20;
    public static final int LAYOUT_VCENTER          = 0x30;
    public static final int LAYOUT_NEWLINE_BEFORE   = 0x100;
    public static final int LAYOUT_NEWLINE_AFTER    = 0x200;
    public static final int LAYOUT_SHRINK           = 0x400;
    public static final int LAYOUT_EXPAND           = 0x800;
    public static final int LAYOUT_VSHRINK          = 0x1000;
    public static final int LAYOUT_VEXPAND          = 0x2000;
    public static final int LAYOUT_2                = 0x4000;

    public static final int SCREEN_WITDH = (int) Settings.ms_iScreenWidth;
    public static final int MARGIN = (int) (Settings.ms_iScreenWidth/16);
    public static final int COMMAND_BAR_HEIGHT = (int) (Settings.ms_iScreenHeight/16);
    public static final int COMMAND_ICON_MARGIN = (COMMAND_BAR_HEIGHT/8);
    public static final int COMMAND_ICON_HEIGHT = COMMAND_BAR_HEIGHT - (COMMAND_ICON_MARGIN*2);
    
    private int layout;
    private String m_zLabel;
    
    Vector commands = new Vector(2,2);
    Command defaultCommand;
    ItemCommandListener commandListener;
    Screen owner;
    
    protected Item()
    {
        
    }
    
    protected Item( String label )
    {
        m_zLabel = label;
    }
    
    public String getLabel()
    {
        return m_zLabel;
    }
    
    public void setLabel( String label )
    {
        m_zLabel = label;
    }
    
    public int getLayout()
    {
        return this.layout;
    }

    public void setLayout( int layout )
    {
        this.layout = layout;
    }
    
    public abstract void init( MIDlet midlet, ViewGroup parent );
    
    public abstract void dispose();
    
    public abstract View getView();
    
    public void addCommand(Command cmd) {
        if (cmd == null) {
            throw new NullPointerException();
        }

        if (!commands.contains(cmd)) {
            // Now insert it in order
            boolean inserted = false;

            for (int i = 0; i < commands.size(); i++) {
                if (cmd.getPriority() < ((Command) commands.elementAt(i)).getPriority()) {
                    commands.insertElementAt(cmd, i);
                    inserted = true;
                    break;
                }
            }
            if (!inserted) {
                // Not inserted just place it at the end
                commands.addElement(cmd);
            }
        }

    }
    
    void setOwner(Screen owner) {
        this.owner = owner;
    }
    Screen getOwner() {
        return this.owner;
    }
    
    public void setDefaultCommand(Command pCommand) {
        this.defaultCommand = pCommand;
        if (pCommand != null) {
            addCommand(pCommand);
        }
    }
    
    public void setItemCommandListener(ItemCommandListener listener) {
        this.commandListener = listener;
    }
}
