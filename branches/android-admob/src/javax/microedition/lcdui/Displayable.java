package javax.microedition.lcdui;

import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import java.util.ArrayList;
import javax.microedition.midlet.MIDlet;

public abstract class Displayable// extends SurfaceView implements SurfaceHolder.Callback
{

    protected CommandListener commandListener;
    private ArrayList<Command> m_Commands;
    private Display m_CurrentDisplay;
    private int menuItemIds;

    public Displayable() {
        m_Commands = new ArrayList<Command>();
    }

    public boolean isShown() {
        return MIDlet.ms_Activity.hasWindowFocus();
    }

    public void addCommand(Command command) {
    	boolean added = false;
        for (int i = 0; i < m_Commands.size(); i++) {
            Command found = m_Commands.get(i);
            if (found.getPriority() > command.getPriority()) {
                m_Commands.add(i, command);
    			added = true;
    			break;
    		}
    	}
        if (!added) {
            m_Commands.add(command);
        }
    	}

    public void removeCommand(Command command) {
        m_Commands.remove(command);
        if (m_CurrentDisplay != null) {
            removeCommandFromDisplay(command, m_CurrentDisplay);
        }
        }

    public Command getCommandByType(int _iCommandType) {
        for (int i=0; i<m_Commands.size(); i++) {
            if (m_Commands.get(i).getCommandType() == _iCommandType)
                return m_Commands.get(i);
        }
        return null;
    }

    public boolean existCommandByType(int _iCommandType) {
        for (int i=0; i<this.m_Commands.size(); i++) {
            if (this.m_Commands.get(i).getCommandType() == _iCommandType)
                return true;
        }
        return false;
    }

    public boolean existCommandByObject(Command _bObject) {
        return this.m_Commands.contains(_bObject);
    }

    public ArrayList<Command> getCommands() {
        return m_Commands;
    }

    public CommandListener getCommandListener() {
		return this.commandListener;
	}

    public void setCommandListener(CommandListener commandListener) {
        this.commandListener = commandListener;
    }

    public abstract void initDisplayable(MIDlet midlet);

    public abstract void disposeDisplayable();

    public abstract View getView();

    public Display getCurrentDisplay() {
        return m_CurrentDisplay;
	}

    public void setCurrentDisplay(Display _currentDisplay) {
        m_CurrentDisplay = _currentDisplay;
        menuItemIds = 0;

        if (m_CurrentDisplay != null) {

            // if there's not commands deffined, will create the resume/exit default android menu
            if (m_Commands.isEmpty()) {
                Command cmdOk = new Command("Resume", Command.BACK, 0);
                Command cmdExit = new Command("Exit", Command.EXIT, 0);

                this.addCommand(cmdOk);
                this.addCommand(cmdExit);

                this.setCommandListener(new CommandListener() {
                    public void commandAction(Command c, Displayable d) {
                        if (c.getCommandType() == Command.EXIT) {
                            MIDlet.getInstance().notifyDestroyed();
                        }
                        if (c.getCommandType() == Command.BACK) {
                            MIDlet.ms_Activity.closeOptionsMenu();
                            m_CurrentDisplay.getMIDlet().getMenu().close();
		}
	}
                });
		}

            // add commands to android menu
            //addCommandsToDisplay(_currentDisplay);
	}
    }

    private void removeCommandFromDisplay(Command command, Display display) {
		Menu menu = display.getMIDlet().getMenu();
        if (menu != null) {
			android.view.MenuItem item;
			item = command.getItem();
            menu.removeItem(item.getItemId());
		}
	}

    public void addCommandsToDisplay(Display display) {
        menuItemIds = 0;
        for (Command command : m_Commands) {
            addCommandToDisplay(command, display);
		}
	}

    private void addCommandToDisplay(final Command command, final Display display) {
		final Menu menu = display.getMIDlet().getMenu();
        if (menu != null) {

					android.view.MenuItem item;
					int menuItemId = Displayable.this.menuItemIds++;
					item = menu.add(
							Menu.NONE,
							menuItemId,
							command.getPriority(),
                    command.getLabel());

					item.setOnMenuItemClickListener(new CallbackOnMenuItemClickListener(command));
            command.setItem(item);

            if (command.getCommandType() == Command.BACK) {
						// TODO : do something here
                //item.setAlphabeticShortcut(KeyEvent.KEYCODE_BACK);
					}

		}
	}

    public void doSizeChanged(int w, int h)
    {
        
    }

    public void doRedraw()
    {

    }

    public void pause()
    {

    }

    public void unpause()
    {

    }

    public void pointerEventReleased(int GetScaledX, int GetScaledY)
    {
    }

    public void pointerEventDragged(int GetScaledX, int GetScaledY)
    {
    }

    public void pointerEventPressed(int GetScaledX, int GetScaledY)
    {
    }

    public void keyEventPressed(int keyCode)
    {
    }

    public void keyEventReleased(int keyCode)
    {
    }

    private class CallbackOnMenuItemClickListener implements OnMenuItemClickListener {

    	private Command source;

        public CallbackOnMenuItemClickListener(Command source) {
    		this.source = source;
    	}
		public boolean onMenuItemClick(MenuItem item) {
    		boolean result;
            if (Displayable.this instanceof Form) {
                Form form = (Form) Displayable.this;
                for (Item iItem : form.getItems()) {
                    if (source == iItem.defaultCommand) {
                        iItem.commandListener.commandAction(source, iItem);
                        return true;
                    }
                }
            }
            if (Displayable.this.commandListener != null) {
    			Displayable.this.commandListener.commandAction(this.source, Displayable.this);
    			result = true;
    		} else {
    			result = false;
    		}
    		return result;
		}
    }
}
