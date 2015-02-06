
package javax.microedition.lcdui;

import com.kitmaker.finalkombat.ActivityMain;

import android.graphics.Paint;

/**
 *
 * @author Venus-Kitmaker
 */
public class Font {

   public static final int STYLE_PLAIN = 0;
   public static final int STYLE_BOLD = 1;
   public static final int STYLE_ITALIC = 2;
   public static final int STYLE_UNDERLINED = 4;
   
   public static final int SIZE_SMALL = 8;
   public static final int SIZE_MEDIUM = 0;
   public static final int SIZE_LARGE = 16;

   public static final int FACE_SYSTEM = 0;
   public static final int FACE_MONOSPACE = 32;
   public static final int FACE_PROPORTIONAL = 64;

   public static final int FONT_STATIC_TEXT = 0;
   public static final int FONT_INPUT_TEXT = 1;

   public static int FNT_SIZE [] = { 16, 24, 32 };
   private int m_iFontFace, m_iFontStyle, m_iFontSize;
   private int m_iFontHeight;

   private Paint m_vPaint;   

   public Font (int _iFontSpecifier) {
      m_vPaint = new Paint();
	  m_vPaint.setAntiAlias(ActivityMain.DRAW_ANTIALIASING);
   }
   
   public Font(int _iFace, int _iStyle, int _iSize) {
      m_iFontFace = _iFace;
      m_iFontStyle = _iStyle;
      m_iFontSize = _iSize;

      m_iFontHeight = FNT_SIZE[0];
      if (m_iFontSize == SIZE_MEDIUM)
         m_iFontHeight = FNT_SIZE[1];
      if (m_iFontSize == SIZE_LARGE)
         m_iFontHeight = FNT_SIZE[2];
      
      m_vPaint = new Paint();
	  m_vPaint.setAntiAlias(ActivityMain.DRAW_ANTIALIASING);
   }


   public static Font getFont(int _iFontSpecifier) {
      return new Font (_iFontSpecifier);
   }
   public static Font getFont(int _iFace, int _iStyle, int _iSize) {
      return new Font (_iFace, _iStyle, _iSize);
   }

   public static Font getDefaultFont() {
	  return Graphics.ms_vCurrentFont;
   }

   public int getStyle() {
      return m_iFontStyle;
   }
   public int getSize() {
      return m_iFontSize;
   }
   public int getFace() {
      return m_iFontFace;
   }
   public boolean isPlain() {
      return ((m_iFontStyle&STYLE_BOLD) == 0);
   }
   public boolean isBold() {
      return ((m_iFontStyle&STYLE_BOLD) != 0);
   }
   public boolean isItalic() {
      return ((m_iFontStyle&STYLE_ITALIC) != 0);
   }
   public boolean isUnderlined() {
      return ((m_iFontStyle&STYLE_UNDERLINED) != 0);
   }
   public int getHeight() {
      return m_iFontHeight;
   }
   public int getBaselinePosition() {
	   m_vPaint.setTextSize(this.getHeight());
	   m_vPaint.setFakeBoldText(this.isBold());
	   m_vPaint.setUnderlineText(this.isUnderlined());
       return (int) (-m_vPaint.ascent());
   }
   public int charWidth(char ch) {
	   m_vPaint.setTextSize(this.getHeight());
	   m_vPaint.setFakeBoldText(this.isBold());
	   m_vPaint.setUnderlineText(this.isUnderlined());
       String zChar = String.valueOf(ch);
       return (int) m_vPaint.measureText(zChar);
   }
   public int stringWidth (String _zStr) {
	   m_vPaint.setTextSize(this.getHeight());
	   m_vPaint.setFakeBoldText(this.isBold());
	   m_vPaint.setUnderlineText(this.isUnderlined());
       return (int) m_vPaint.measureText(_zStr);
   }
   public int substringWidth (String _zStr, int _iOffset, int _iLen) {
	   m_vPaint.setTextSize(this.getHeight());
	   m_vPaint.setFakeBoldText(this.isBold());
	   m_vPaint.setUnderlineText(this.isUnderlined());
       String zStr = _zStr.substring(_iOffset, _iLen);
       return (int) m_vPaint.measureText(zStr);
   }
   
   public void setFontPaint (Paint _p) {
	   m_vPaint = _p;
	   m_vPaint.setAntiAlias(ActivityMain.DRAW_ANTIALIASING);
   }
}
