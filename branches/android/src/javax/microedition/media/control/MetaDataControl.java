package javax.microedition.media.control;

import javax.microedition.media.Control;

public interface MetaDataControl extends Control {
  
	public static final java.lang.String AUTHOR_KEY = "author";
	public static final java.lang.String COPYRIGHT_KEY = "copyright";
	public static final java.lang.String DATE_KEY = "date";
	public static final java.lang.String TITLE_KEY = "title";
  
	public java.lang.String[] getKeys();
	public java.lang.String getKeyValue(java.lang.String key);
  
}