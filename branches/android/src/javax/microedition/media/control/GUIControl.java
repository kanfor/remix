package javax.microedition.media.control;

import javax.microedition.media.Control;

public interface GUIControl extends Control {
  public static final int USE_GUI_PRIMITIVE = 0;
  
  public java.lang.Object initDisplayMode(int mode, java.lang.Object arg);
  
}