/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javax.microedition.lcdui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region.Op; // fix 4.3

import java.util.Random;

import javax.microedition.Settings;

/**
 *
 * @author Mr Matsusaka
 */
public class Graphics {

    public static int SIZEX;
    public static int SIZEY;
    public int m_iSizeX;
    public int m_iSizeY;

    public float m_fSizeX = 1f;
    public float m_fSizeY = 1f;
    public float m_fAngle = 0f;
    public static Canvas ms_vCanvas;
    public Canvas m_vCanvas;
    public Paint m_vPaint;
    public static Random m_Random;
    public int m_iX, m_iY;
    public Font m_vCurrentFont;
    private Rect m_iOldClip; // fix 4.3
    private Rect m_iTextRect = new Rect();

    static final float PI = 3.1415926536f;
    public static final short ANGLE_STEPS = 2048;
    public static final short ANGLE_STEPS2 = ANGLE_STEPS / 2;
    public static final short ANGLE_STEPS4 = ANGLE_STEPS / 4;
    public static final short ANGLE_STEPS8 = ANGLE_STEPS / 8;
    public static final short ANGLE_STEPS16 = ANGLE_STEPS / 16;
    public static final int SINCOS_PRECISIONBITS = 16;
    public static int[] SINCOS = {
        // 0
        0, 804, 1608, 2412, 3215, 4018, 4821, 5622, 6423, 7223, 8022, 8819, 9616, 10410, 11204, 11995, 12785, 13573, 14359, 15142, 15923, 16702, 17479, 18253, 19024, 19792, 20557, 21319, 22078, 22833, 23586, 24334,
        25079, 25820, 26557, 27291, 28020, 28745, 29465, 30181, 30893, 31600, 32302, 32999, 33692, 34379, 35061, 35738, 36409, 37075, 37736, 38390, 39039, 39682, 40319, 40950, 41575, 42194, 42806, 43412, 44011, 44603, 45189, 45768,
        46340, 46906, 47464, 48015, 48558, 49095, 49624, 50146, 50660, 51166, 51665, 52155, 52639, 53114, 53581, 54040, 54491, 54933, 55368, 55794, 56212, 56621, 57022, 57414, 57797, 58172, 58538, 58895, 59243, 59583, 59913, 60235,
        60547, 60850, 61144, 61429, 61705, 61971, 62228, 62475, 62714, 62942, 63162, 63371, 63571, 63762, 63943, 64115, 64276, 64428, 64571, 64703, 64826, 64939, 65043, 65136, 65220, 65294, 65358, 65412, 65457, 65491, 65516, 65531,
        65536, 65531, 65516, 65491, 65457, 65412, 65358, 65294, 65220, 65136, 65043, 64939, 64826, 64703, 64571, 64428, 64276, 64115, 63943, 63762, 63571, 63371, 63162, 62942, 62714, 62475, 62228, 61971, 61705, 61429, 61144, 60850,
        60547, 60235, 59913, 59583, 59243, 58895, 58538, 58172, 57797, 57414, 57022, 56621, 56212, 55794, 55368, 54933, 54491, 54040, 53581, 53114, 52639, 52155, 51665, 51166, 50660, 50146, 49624, 49095, 48558, 48015, 47464, 46906,
        46340, 45768, 45189, 44603, 44011, 43412, 42806, 42194, 41575, 40950, 40319, 39682, 39039, 38390, 37736, 37075, 36409, 35738, 35061, 34379, 33692, 32999, 32302, 31600, 30893, 30181, 29465, 28745, 28020, 27291, 26557, 25820,
        25079, 24334, 23586, 22833, 22078, 21319, 20557, 19792, 19024, 18253, 17479, 16702, 15923, 15142, 14359, 13573, 12785, 11995, 11204, 10410, 9616, 8819, 8022, 7223, 6423, 5622, 4821, 4018, 3215, 2412, 1608, 804,
        // 256
        0, -804, -1608, -2412, -3215, -4018, -4821, -5622, -6423, -7223, -8022, -8819, -9616, -10410, -11204, -11995, -12785, -13573, -14359, -15142, -15923, -16702, -17479, -18253, -19024, -19792, -20557, -21319, -22078, -22833, -23586, -24334,
        -25079, -25820, -26557, -27291, -28020, -28745, -29465, -30181, -30893, -31600, -32302, -32999, -33692, -34379, -35061, -35738, -36409, -37075, -37736, -38390, -39039, -39682, -40319, -40950, -41575, -42194, -42806, -43412, -44011, -44603, -45189, -45768,
        -46340, -46906, -47464, -48015, -48558, -49095, -49624, -50146, -50660, -51166, -51665, -52155, -52639, -53114, -53581, -54040, -54491, -54933, -55368, -55794, -56212, -56621, -57022, -57414, -57797, -58172, -58538, -58895, -59243, -59583, -59913, -60235,
        -60547, -60850, -61144, -61429, -61705, -61971, -62228, -62475, -62714, -62942, -63162, -63371, -63571, -63762, -63943, -64115, -64276, -64428, -64571, -64703, -64826, -64939, -65043, -65136, -65220, -65294, -65358, -65412, -65457, -65491, -65516, -65531,
        -65536, -65531, -65516, -65491, -65457, -65412, -65358, -65294, -65220, -65136, -65043, -64939, -64826, -64703, -64571, -64428, -64276, -64115, -63943, -63762, -63571, -63371, -63162, -62942, -62714, -62475, -62228, -61971, -61705, -61429, -61144, -60850,
        -60547, -60235, -59913, -59583, -59243, -58895, -58538, -58172, -57797, -57414, -57022, -56621, -56212, -55794, -55368, -54933, -54491, -54040, -53581, -53114, -52639, -52155, -51665, -51166, -50660, -50146, -49624, -49095, -48558, -48015, -47464, -46906,
        -46340, -45768, -45189, -44603, -44011, -43412, -42806, -42194, -41575, -40950, -40319, -39682, -39039, -38390, -37736, -37075, -36409, -35738, -35061, -34379, -33692, -32999, -32302, -31600, -30893, -30181, -29465, -28745, -28020, -27291, -26557, -25820,
        -25079, -24334, -23586, -22833, -22078, -21319, -20557, -19792, -19024, -18253, -17479, -16702, -15923, -15142, -14359, -13573, -12785, -11995, -11204, -10410, -9616, -8819, -8022, -7223, -6423, -5622, -4821, -4018, -3215, -2412, -1608, -804, // 512
    };

    public static final int HCENTER = 1;
    public static final int VCENTER = 2;
    public static final int LEFT = 4;
    public static final int RIGHT = 8;
    public static final int TOP = 16;
    public static final int BOTTOM = 32;
    public static final int BASELINE = 64;
    public static final int HFLIP = 128;
    public static final int VFLIP = 256;

    public static final int SOLID = 0;
    public static final int DOTTED = 1;

    public Graphics (Canvas _c) {
        if (m_vCurrentFont == null)
            m_vCurrentFont = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);

        ms_vCanvas = _c;
        m_vCanvas = _c;
        m_vPaint = new Paint();
        m_vPaint.setAntiAlias(Settings.DRAW_ANTIALIASING);

        m_iSizeX = SIZEX = m_vCanvas.getWidth();
        m_iSizeY = SIZEY = m_vCanvas.getHeight();
    }

    public Graphics (Bitmap _vImage) {
        if (m_vCurrentFont == null)
            m_vCurrentFont = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
	
        m_vCanvas = new Canvas(_vImage);
        m_vPaint = new Paint(Paint.FILTER_BITMAP_FLAG);
        m_vPaint.setAntiAlias(Settings.DRAW_ANTIALIASING);
        
        m_iSizeX = m_vCanvas.getWidth();
        m_iSizeY = m_vCanvas.getHeight();
    }
   
    /**
     * Gets the X coordinate of the translated origin of this graphics context.
     * @return X of current origin
     */
    public int getTranslateX() {
        return m_iX;
    }

    /**
     * Gets the Y coordinate of the translated origin of this graphics context.
     * @return Y of current origin
     */
    public int getTranslateY() {
        return m_iY;
    }

    /**
     * Translates the origin of the graphics context to the point (x, y) in the 
     * current coordinate system. All coordinates used in subsequent rendering 
     * operations on this graphics context will be relative to this new origin. <P>
     * The effect of calls to translate() are cumulative. For example, calling 
     * translate(1, 2) and then translate(3, 4) results in a translation of (4, 6). <P>
     * The application can set an absolute origin (ax, ay) using the following technique: <P>
     * g.translate(ax - g.getTranslateX(), ay - g.getTranslateY()) <P>
     * @param _iX the x coordinate of the new translation origin
     * @param _iY the y coordinate of the new translation origin
     */
   public void translate(int _iX, int _iY) {
        m_iX += _iX;
        m_iY += _iY;
        m_vCanvas.translate(m_iX, m_iY);
   }

    /**
     * Gets the X offset of the current clipping area, relative to the coordinate 
     * system origin of this graphics context. Separating the getClip operation 
     * into two methods returning integers is more performance and memory efficient 
     * than one getClip() call returning an object.
     * @return X offset of the current clipping area
     * @see clipRect(int, int, int, int)
     * @see setClip(int, int, int, int)
     * @see saveClip()
     * @see restoreClip()
     */
   public int getClipX() {
   	  return m_vCanvas.getClipBounds().left;
   }

    /**
     * Gets the Y offset of the current clipping area, relative to the coordinate 
     * system origin of this graphics context. Separating the getClip operation 
     * into two methods returning integers is more performance and memory efficient 
     * than one getClip() call returning an object.
     * @return Y offset of the current clipping area
     * @see clipRect(int, int, int, int)
     * @see setClip(int, int, int, int)
     * @see saveClip()
     * @see restoreClip()
     */
   public int getClipY() {
   	  return m_vCanvas.getClipBounds().top;
   }

    /**
     * Gets the width of the current clipping area.
     * @return width of the current clipping area
     * @see clipRect(int, int, int, int)
     * @see setClip(int, int, int, int)
     * @see saveClip()
     * @see restoreClip()
     */
    public int getClipWidth() {
	   return m_vCanvas.getClipBounds().width();
   }

    /**
     * Gets the height of the current clipping area.
     * @return height of the current clipping area.
     * @see clipRect(int, int, int, int)
     * @see setClip(int, int, int, int)
     * @see saveClip()
     * @see restoreClip()
     */
    public int getClipHeight() {
        return m_vCanvas.getClipBounds().height();
    }
   
    /**
     * Gets the red component of the current color.
     * @return integer value in range 0-255
     * @see setColor(int, int, int)
     */
    public int getRedComponent() {
        return ((m_vPaint.getColor() & 0xff) << 16);
    }

    /**
     * Gets the green component of the current color.
     * @return integer value in range 0-255
     * @see setColor(int, int, int)
     */
    public int getGreenComponent() {
        return ((m_vPaint.getColor() & 0xff) << 8);
    }

    /**
     * Gets the blue component of the current color.
     *
     * @return integer value in range 0-255
     * @see setColor(int, int, int)
     */
    public int getBlueComponent() {
        return ((m_vPaint.getColor() & 0xff));
    }

    /**
     * Gets the current color.
     * @return an integer in form 0x00RRGGBB
     * @see setColor(int, int, int)
     */
    public int getColor() {
        return m_vPaint.getColor();
    }
   
    /**
     * Gets a blended color between the two ARGB colors
     * @param _iColor1 the first color
     * @param _iColor2 the second color
     * @param _iQuantity quantity (from 0 to 255)
     * @return blended color between the first color and the second color
     */
    public int getBlendedColor(int _iColor1, int _iColor2, int _iQuantity) {
        int iFinalColor = 0;
        int iA1 = (_iColor1 >> 24) & 0xFF;
        int iA2 = (_iColor2 >> 24) & 0xFF;
        int iR1 = (_iColor1 >> 16) & 0xFF;
        int iR2 = (_iColor2 >> 16) & 0xFF;
        int iG1 = (_iColor1 >> 8) & 0xFF;
        int iG2 = (_iColor2 >> 8) & 0xFF;
        int iB1 = _iColor1 & 0xFF;
        int iB2 = _iColor2 & 0xFF;

        if (iA1 > 0 && iA2 > 0) {
            iFinalColor |= (iA1 + (((iA2 - iA1) * _iQuantity) >> 8)) << 24;
        }

        iFinalColor |= (iR1 + (((iR2 - iR1) * _iQuantity) >> 8)) << 16;
        iFinalColor |= (iG1 + (((iG2 - iG1) * _iQuantity) >> 8)) << 8;
        iFinalColor |= iB1 + (((iB2 - iB1) * _iQuantity) >> 8);

        return iFinalColor;
    }

    /**
     * Gets a blended color between the two ARGB colors
     * @param _iColor1 the first color
     * @param _iColor2 the second color
     * @param _iQuantity quantity (from 0 to 255)
     */
    public void setBlendedColor(int _iColor1, int _iColor2, int _iQuantity) {
        setColor(getBlendedColor(_iColor1, _iColor2, _iQuantity));
    }
    
    /**
     * Get Image x scaling factor
     * @return get Image x scaling factor
     */
    public float getImageSizeX() {
        return m_fSizeX;
    }

    /**
     * Get Image y scaling factor
     * @return get Image y scaling factor
     */
    public float getImageSizeY() {
        return m_fSizeY;
    }

    /**
     * Get Image angle
     * @return get Image angle
     */
    public float getImageAngle() {
        return m_fAngle;
    }

    /**
     * Set image scaling factor
     * <p>
     * The scale value ranges from 0(0%) to 512(200%). A scale value of 256 wont change the image size. 
     * @param _iScaleX scale factor in x (default value 256)
     * @param _iScaleY scale factor in y (default value 256) 
     */
    public void setImageSize (int _iScaleX, int _iScaleY) {
        if (_iScaleX < 0)
            _iScaleX = 0;
        if (_iScaleY < 0)
            _iScaleY = 0;

        m_fSizeX = ((float)_iScaleX)/256;
        m_fSizeY = ((float)_iScaleY)/256;
    }

    /**
     * Set image scaling factor
     * <p>
     * The scale value ranges from 0.0f(0%) to 2.0f(200%). A scale value of 1.0f wont change the image size. 
     * @param _fScaleX scale factor in x (default value 1f)
     * @param _fScaleY scale factor in y (default value 1f) 
     */
    public void setImageSize (float _fScaleX, float _fScaleY) {
        if (_fScaleX < 0)
            _fScaleX = 0;
        if (_fScaleY < 0)
            _fScaleY = 0;

        m_fSizeX = _fScaleX;
        m_fSizeY = _fScaleY;
    }
   
    public void setImageAngle (float _fNewAngle) {
        m_fAngle = (Math.abs(_fNewAngle)+360f)%360f;
    }

    /**
     * Sets the current color to the specified ARGB values. All subsequent rendering operations will use this specified color. 
     * The ARGB value passed in is interpreted with the least significant eight bits giving the blue component, 
     * the next eight more significant bits giving the green component, the next eight more significant bits giving the red component, 
     * and the next eight more significant bits giving the alpha component. That is to say, the color component is specified in the form of 0xAARRGGBB. 
     * If the high order byte of this value is 0 it will be ignored ignored in orther to get compatibility with the javax.microedition.lcdui.Graphic.setColor(int _iRGB)
     * @param _iColor the color being set
     * @ see getColor()
     */
    public void setColor (int _iColor) {
        if (((_iColor >> 24) & 0xFF) == 0x00)
            _iColor = (255<<24) + _iColor;
        if (_iColor == 0)
            _iColor = (255<<24) + 0x000000;
      
        m_vPaint.setColor(_iColor);
    }

    /**
     * Sets the current color to the specified ARGB values. All subsequent rendering operations will use this specified color.
     * @param _iA the alpha component of the color being set in range 0-255
     * @param _iR the red component of the color being set in range 0-255
     * @param _iG the green component of the color being set in range 0-255
     * @param _iB the blue component of the color being set in range 0-255
     * @throws IllegalArgumentException - if any of the color components are outside of range 0-255
     * @see getColor()
     */
    public void setColor (int _iA, int _iR, int _iG, int _iB) {
        m_vPaint.setColor((_iA<<24)|(_iR<<16)|(_iG<<8)|_iB);
    }

    /**
     * Sets the current color to the specified RGB values. All subsequent rendering operations will use this specified color.
     * @param _iR the red component of the color being set in range 0-255
     * @param _iG the green component of the color being set in range 0-255
     * @param _iB the blue component of the color being set in range 0-255
     * @throws IllegalArgumentException - if any of the color components are outside of range 0-255
     * @see getColor()
     */
    public void setColor (int _iR, int _iG, int _iB) {
        m_vPaint.setColor((255<<24)|(_iR<<16)|(_iG<<8)|_iB);
    }

    /**
     * Sets the current grayscale to be used for all subsequent rendering operations. 
     * For monochrome displays, the behavior is clear. For color displays, 
     * this sets the color for all subsequent drawing operations to be a gray color 
     * equivalent to the value passed in. The value must be in the range 0-255.
     * @param _iValue the desired grayscale value 
     * @throws IllegalArgumentException - if the gray value is out of range
     * @see getGrayScale()
     */
    public void setGrayScale (boolean _bSetGrayScale) {
        ColorMatrix vCm = new ColorMatrix();
        vCm.setSaturation(_bSetGrayScale?0:1);
        ColorMatrixColorFilter vCMF = new ColorMatrixColorFilter(vCm);
        m_vPaint.setColorFilter(vCMF);
    }

    /**
     * Set alpha level. <P> Alpha level in J2ME is pretty tricky. Some devices 
     * can show a wide range of alpha level, others may have only few levels, 
     * and others just can not display any alpha transparency. <P>
     * Some devices has so few alpha levels (like Motorola V9, that only have 4) 
     * that it should be better to not use it at all in some situations. <P> 
     * Please note that alpha transparency on primitives only will work on 
     * SonyEricsson & Nokia devices. Images can use alpha transparency in more brands, 
     * depending on the device. <P>
     * In the proyect's propierties you can find "UseAlpha" value, that can be true or false. 
     * @param _iA alpha leves (from 0 to 255)
     * @see getAlpha()
     */
   
    public void setAlpha(int _iAlpha) {
        m_vPaint.setAlpha(_iAlpha);
    }
   
    /**
     * Gets the alpha component of the current color.
     * @return integer value in range 0-255
     * @see setAlpha(int)
     */
    public int getAlpha() {
        return m_vPaint.getAlpha();
    }
   
    /**
     * Sets the stroke style used for drawing lines, arcs, rectangles, and rounded rectangles. This does not affect fill, text, and image operations.
     * @param _iStyle can be SOLID or DOTTED
     * @throws IllegalArgumentException if the style is illegal
     * @see getStrokeStyle()
     */
    public void setStrokeStyle(int _iStyle) {
        //m_vPaint.setStrokeCap(Paint.Cap.BUTT);
    }

    /**
     * Sets the current clip to the rectangle specified by the given coordinates. Rendering operations have no effect outside of the clipping area.
     * @param _iX the x coordinate of the new clip rectangle
     * @param _iY the y coordinate of the new clip rectangle
     * @param _iW the width of the new clip rectangle
     * @param _iH the height of the new clip rectangle
     * @see clipRect(int, int, int, int)
     * @see saveClip()
     * @see restoreClip()
     */
    public void setClip(int _iX, int _iY, int _iW, int _iH) {
        // fix 4.3
        m_vCanvas.clipRect(_iX, _iY, _iX+_iW, _iY+_iH, Op.REPLACE);
    }

    /**
     * Intersects the current clip with the specified rectangle. The resulting 
     * clipping area is the intersection of the current clipping area and the 
     * specified rectangle. This method can only be used to make the current 
     * clip smaller. To set the current clip larger, use the setClip method. 
     * Rendering operations have no effect outside of the clipping area.
     * @param _iX the x coordinate of the rectangle to intersect the clip with
     * @param _iY the y coordinate of the rectangle to intersect the clip with
     * @param _iW the width of the rectangle to intersect the clip with
     * @param _iH the height of the rectangle to intersect the clip with
     * @see setClip(int, int, int, int)
     * @see saveClip()
     * @see restoreClip()
     */
    public void clipRect(int _iX, int _iY, int _iW, int _iH) {
        // fix 4.3
        m_vCanvas.clipRect(_iX, _iY, _iX+_iW, _iY+_iH, Op.INTERSECT);
    }

    /**
     * Save current clip.
     */
    public void saveClip() {
        // fix 4.3
        m_iOldClip = m_vCanvas.getClipBounds();
    }

    /**
     * Restores previously saved clip with saveClip()
     */
    public void restoreClip() {
        // fix 4.3
        m_vCanvas.clipRect(m_iOldClip, Op.REPLACE);
    }
   
    public void fillRect(int _iX, int _iY, int _iW, int _iH) {
        m_vPaint.setStyle(Paint.Style.FILL);
        m_vCanvas.drawRect(_iX, _iY, _iX + Math.max(0, _iW), _iY + Math.max(0, _iH), m_vPaint);
    }

    public void fillArc(int _iX, int _iY, int _iW, int _iH, int startAngle, int arcAngle) {
        m_vPaint.setStyle(Paint.Style.FILL);
        RectF oval = new RectF(_iX, _iY, _iX + _iW, _iY + _iH);
        m_vCanvas.drawArc(oval, startAngle, arcAngle, true, m_vPaint);
    }

    public void fillRoundRect(int _iX, int _iY, int _iSizeX, int _iSizeY) {
        m_vPaint.setStyle(Paint.Style.FILL);
        m_vCanvas.drawRoundRect(new RectF(_iX, _iY, _iX + _iSizeX, _iY + _iSizeY), _iSizeX, _iSizeY, m_vPaint);
    }

    public void fillRoundRect(int _iX, int _iY, int _iSizeX, int _iSizeY, int _iRoundX, int _iRoundY) {
        m_vPaint.setStyle(Paint.Style.FILL);
        m_vCanvas.drawRoundRect(new RectF(_iX, _iY, _iX + _iSizeX, _iY + _iSizeY), _iRoundX>>1, _iRoundY>>1, m_vPaint);
    }

    public void fillCircle(int _iX, int _iY, int _iRadiusX, int _iRadiusY) {
        m_vPaint.setStyle(Paint.Style.FILL);
        RectF rRect = new RectF(_iX - _iRadiusX, _iY - _iRadiusY, _iX + _iRadiusX, _iY + _iRadiusY);
        m_vCanvas.drawRoundRect(rRect, _iRadiusX, _iRadiusY, m_vPaint);
    }
   
    public void fillTriangle(int _x0, int _y0, int _x1, int _y1, int _x2, int _y2) {
        m_vPaint.setStyle(Paint.Style.FILL);
        Path path = new Path();
        path.moveTo(_x0, _y0);
        path.lineTo(_x1, _y1);
        path.lineTo(_x2, _y2);
        m_vCanvas.drawPath(path, m_vPaint);
    }

    public void drawRect(int _iX, int _iY, int _iW, int _iH) {
        m_vPaint.setStyle(Paint.Style.STROKE);
        m_vCanvas.drawRect(_iX, _iY, _iX + _iW + 1, _iY + _iH + 1, m_vPaint);
    }

    public void drawArc(int _iX, int _iY, int _iW, int _iH, int startAngle, int arcAngle) {
        m_vPaint.setStyle(Paint.Style.STROKE);
        RectF oval = new RectF(_iX, _iY, _iX + _iW, _iY + _iH);
        m_vCanvas.drawArc(oval, startAngle, arcAngle, true, m_vPaint);
    }

    public void drawRoundRect(int _iX, int _iY, int _iSizeX, int _iSizeY) {
        m_vPaint.setStyle(Paint.Style.STROKE);
        m_vCanvas.drawRoundRect(new RectF(_iX, _iY, _iX + _iSizeX, _iY + _iSizeY), _iSizeX, _iSizeY, m_vPaint);
    }

    public void drawRoundRect(int _iX, int _iY, int _iSizeX, int _iSizeY, int _iRoundX, int _iRoundY) {
        m_vPaint.setStyle(Paint.Style.STROKE);
        m_vCanvas.drawRoundRect(new RectF(_iX, _iY, _iX + _iSizeX, _iY + _iSizeY), _iRoundX>>1, _iRoundY>>1, m_vPaint);
    }

    public void drawCircle(int _iX, int _iY, int _iRadiusX, int _iRadiusY) {
        m_vPaint.setStyle(Paint.Style.STROKE);
        RectF rRect = new RectF(_iX - _iRadiusX, _iY - _iRadiusY, _iX + _iRadiusX, _iY + _iRadiusY);
        m_vCanvas.drawRoundRect(rRect, _iRadiusX, _iRadiusY, m_vPaint);
    }
   
    public void drawTriangle(int _x0, int _y0, int _x1, int _y1, int _x2, int _y2) {
        m_vPaint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.moveTo(_x0, _y0);
        path.lineTo(_x1, _y1);
        path.lineTo(_x2, _y2);
        m_vCanvas.drawPath(path, m_vPaint);
    }

    public void drawLine(int _iX0, int _iY0, int _iX1, int _iY1) {
        //m_vPaint.setStyle(Paint.Style.STROKE);
        m_vPaint.setStyle(Paint.Style.FILL);        
        m_vCanvas.drawLine(_iX0, _iY0, _iX1, _iY1, m_vPaint);
    }

    public void drawImage(Bitmap _vImage, int _iX, int _iY) {

        // translation / transform
        Matrix vMatrix = new Matrix();
        vMatrix.setTranslate(_iX, _iY);
        vMatrix.preScale(m_fSizeX, m_fSizeY, 0, 0);
        if (m_fAngle != 0) {
            vMatrix.preRotate(m_fAngle, 0, 0);
        }

        m_vCanvas.drawBitmap(_vImage, vMatrix, m_vPaint);
    }

    public void drawImage(Image _vImage, int _iX, int _iY) {
        drawImage(_vImage.getBitmap(), _iX, _iY);
    }
   
    public void drawImage(Bitmap _vImage, int _iX, int _iY, int _iAnchor) {

        // anchor
        int iExtra_X = 0, iExtra_Y = 0;
        if ((_iAnchor & BOTTOM) != 0)
            iExtra_Y = _vImage.getHeight();
        if ((_iAnchor & RIGHT) != 0)
            iExtra_X = _vImage.getWidth();
        if ((_iAnchor & VCENTER) != 0)
            iExtra_Y = _vImage.getHeight() >> 1;
        if ((_iAnchor & HCENTER) != 0)
            iExtra_X = _vImage.getWidth() >> 1;

        // scale / flags
        float fSizeX = m_fSizeX, fSizeY = m_fSizeY;
        if ((_iAnchor & HFLIP) != 0)
            fSizeX = -fSizeX;
        if ((_iAnchor & VFLIP) != 0)
            fSizeY = -fSizeY;

        Matrix vMatrix = new Matrix();
        vMatrix.setTranslate(_iX - iExtra_X, _iY - iExtra_Y);
        
        if (m_fSizeX == 1f && m_fSizeY == 1f)
            vMatrix.preScale(fSizeX, fSizeY, iExtra_X, iExtra_Y);

        if (m_fAngle != 0)
            vMatrix.preRotate(m_fAngle, iExtra_X, iExtra_Y);

        m_vCanvas.drawBitmap(_vImage, vMatrix, m_vPaint);
    }
   
    public void drawImage (Image _vImage, int _iX, int _iY, int _iAnchor) {
        drawImage(_vImage.getBitmap(), _iX, _iY, _iAnchor);
    }

    public void drawRegion(Bitmap _vImage, int _iSrcX, int _iSrcY, int _iSrcW,
            int _iSrcH, int _iTransform, int _iX, int _iY) {
        int dRefX = 0;//_iSrcW >> 1;
        int dRefY = 0;//_iSrcH >> 1;

        m_vCanvas.save();
        if (_iTransform == javax.microedition.lcdui.game.Sprite.TRANS_MIRROR || 
            _iTransform == javax.microedition.lcdui.game.Sprite.TRANS_MIRROR_ROT90 || 
            _iTransform == javax.microedition.lcdui.game.Sprite.TRANS_MIRROR_ROT180 || 
            _iTransform == javax.microedition.lcdui.game.Sprite.TRANS_MIRROR_ROT270) {
            m_vCanvas.scale(-1, 1, _iX + dRefX, _iY + dRefY);
        }

        m_vCanvas.translate(_iX, _iY);
        
        switch (_iTransform) {
        case javax.microedition.lcdui.game.Sprite.TRANS_ROT90:
        case javax.microedition.lcdui.game.Sprite.TRANS_MIRROR_ROT90:
            m_vCanvas.rotate(90, _iX + dRefX, _iY + dRefY);
            break;
        case javax.microedition.lcdui.game.Sprite.TRANS_ROT180:
        case javax.microedition.lcdui.game.Sprite.TRANS_MIRROR_ROT180:
            m_vCanvas.rotate(180, _iX + dRefX, _iY + dRefY);
            break;
        case javax.microedition.lcdui.game.Sprite.TRANS_ROT270:
        case javax.microedition.lcdui.game.Sprite.TRANS_MIRROR_ROT270:
            m_vCanvas.rotate(270, _iX + dRefX, _iY + dRefY);
            break;
        }

        Rect rSrc = new Rect(_iSrcX, _iSrcY, _iSrcX + _iSrcW, _iSrcY + _iSrcH);
        RectF rDst = new RectF(0, 0, (_iSrcW * m_fSizeX), (_iSrcH * m_fSizeY));
        m_vCanvas.drawBitmap(_vImage, rSrc, rDst, m_vPaint);
        m_vCanvas.restore();

    }

    public void drawRegion(Image _vImage, int _iSrcX, int _iSrcY, int _iSrcW, int _iSrcH, int _iTransform, int _iX, int _iY) {
        drawRegion(_vImage.getBitmap(), _iSrcX, _iSrcY, _iSrcW, _iSrcH, _iTransform, _iX, _iY);
    }

    public void drawRegion (Bitmap _vImage, int _iSrcX, int _iSrcY, int _iSrcW, int _iSrcH, int _iTransform, int _iX, int _iY, int _iAnchor) {
      
        m_vCanvas.save();

        float iAnchorX = 0, iAnchorY = 0;
		
        switch (_iTransform) {
        
	        // OK
	    	case javax.microedition.lcdui.game.Sprite.TRANS_NONE:
	    	case javax.microedition.lcdui.game.Sprite.TRANS_MIRROR:
	        case javax.microedition.lcdui.game.Sprite.TRANS_ROT180:
	        case javax.microedition.lcdui.game.Sprite.TRANS_MIRROR_ROT180:
	            // anchor
	            if ((_iAnchor&BOTTOM) !=0)
	            	iAnchorY = -_iSrcH;
	            if ((_iAnchor&RIGHT) !=0)
	            	iAnchorX = -_iSrcW;
	            if ((_iAnchor&VCENTER) !=0)
	            	iAnchorY = -_iSrcH>>1;
	            if ((_iAnchor&HCENTER) !=0)
	            	iAnchorX = -_iSrcW>>1;
	            break;

	    	case javax.microedition.lcdui.game.Sprite.TRANS_ROT90:
	    	case javax.microedition.lcdui.game.Sprite.TRANS_MIRROR_ROT90:
        	case javax.microedition.lcdui.game.Sprite.TRANS_ROT270:
	        case javax.microedition.lcdui.game.Sprite.TRANS_MIRROR_ROT270:
	            // anchor
	            if ((_iAnchor&BOTTOM) !=0)
	            	iAnchorY = -_iSrcW;
	            if ((_iAnchor&RIGHT) !=0)
	            	iAnchorX = -_iSrcH;
	            if ((_iAnchor&VCENTER) !=0)
	            	iAnchorY = -_iSrcW>>1;
	            if ((_iAnchor&HCENTER) !=0)
	            	iAnchorX = -_iSrcH>>1;
	            break;
	                        
        }
        
        /*
        // anchor size adjustments
        if (m_fSizeX != 1.0f) {
            if ((_iAnchor&RIGHT) !=0) {
                float fWidth = _vImage.getWidth();
                float fExtraWidth = (fWidth * m_fSizeX) - _vImage.getWidth();
                _iX -= fExtraWidth/2;
            }
            if ((_iAnchor&HCENTER) !=0) {
                float fWidth = _vImage.getWidth();
                float fExtraWidth = (fWidth * m_fSizeX) - _vImage.getWidth();
                _iX -= fExtraWidth/4;
            }
        }

        if (m_fSizeY != 1.0f) {
            if ((_iAnchor&BOTTOM) !=0) {
                float fHeight = _vImage.getHeight();
                float fExtraHeight = (fHeight * m_fSizeY) - _vImage.getHeight();
                _iY -= fExtraHeight/2;
            }
            if ((_iAnchor&VCENTER) !=0) {
                float fHeight = _vImage.getHeight();
                float fExtraHeight = (fHeight * m_fSizeY) - _vImage.getHeight();
                _iY -= fExtraHeight/4;
            }
        }
        */

        int dRefX = _iSrcW >> 1;
        int dRefY = _iSrcH >> 1;
        
        switch (_iTransform) {

            case javax.microedition.lcdui.game.Sprite.TRANS_NONE:
                m_vCanvas.translate(_iX, _iY);
                break;
        
            case javax.microedition.lcdui.game.Sprite.TRANS_MIRROR:
                m_vCanvas.translate(_iX, _iY);
                m_vCanvas.scale(-1, 1, dRefX + iAnchorX, dRefY + iAnchorY);
                break;
                
            case javax.microedition.lcdui.game.Sprite.TRANS_ROT90:
                m_vCanvas.translate(_iX + _iSrcH, _iY);
                m_vCanvas.rotate(90, iAnchorX, iAnchorY);
                break;
                
            case javax.microedition.lcdui.game.Sprite.TRANS_MIRROR_ROT90:
                m_vCanvas.translate(_iX + _iSrcH, _iY);
                m_vCanvas.rotate(90, iAnchorY, iAnchorX);
                m_vCanvas.scale(-1, 1, dRefX + iAnchorY, dRefY + iAnchorX);
                break;

            case javax.microedition.lcdui.game.Sprite.TRANS_ROT180:
                m_vCanvas.translate(_iX, _iY);
                m_vCanvas.rotate(180, dRefX + iAnchorX, dRefY + iAnchorY);
                break;
                
            case javax.microedition.lcdui.game.Sprite.TRANS_MIRROR_ROT180:
                m_vCanvas.translate(_iX, _iY);
                m_vCanvas.scale(-1, 1, dRefX + iAnchorX, dRefY + iAnchorY);
                m_vCanvas.rotate(180, dRefX + iAnchorX, dRefY + iAnchorY);
                break;
                
            case javax.microedition.lcdui.game.Sprite.TRANS_ROT270:
                m_vCanvas.translate(_iX, _iY + _iSrcW);
                m_vCanvas.rotate(270, iAnchorX, iAnchorY);
                break;
                
            case javax.microedition.lcdui.game.Sprite.TRANS_MIRROR_ROT270:
                m_vCanvas.translate(_iX, _iY + _iSrcW);
                m_vCanvas.rotate(270, iAnchorX, iAnchorY);
                m_vCanvas.scale(-1, 1, dRefX + iAnchorX, dRefY + iAnchorY);
                break;
                
        }

        iAnchorX = (iAnchorX * m_fSizeX);
        iAnchorY = (iAnchorY * m_fSizeY);
        
        Rect rSrc = new Rect(_iSrcX, _iSrcY, _iSrcX + _iSrcW, _iSrcY + _iSrcH);
        RectF rDst = new RectF(iAnchorX, iAnchorY, iAnchorX + (_iSrcW*m_fSizeX), iAnchorY + (_iSrcH*m_fSizeY));
        
        m_vCanvas.drawBitmap(_vImage, rSrc, rDst, m_vPaint);
        m_vCanvas.restore();

    }

    public void drawRegion(Image _vImage, int _iSrcX, int _iSrcY, int _iSrcW, int _iSrcH, int _iTransform, int _iX, int _iY, int _iAnchor) {
        drawRegion(_vImage.getBitmap(), _iSrcX, _iSrcY, _iSrcW, _iSrcH, _iTransform, _iX, _iY, _iAnchor);
    }

    public void setFont(Font _vFont) {
        m_vCurrentFont = _vFont;
        m_vPaint.setTextSize(_vFont.getHeight());
        m_vPaint.setFakeBoldText(_vFont.isBold());
        m_vPaint.setUnderlineText(_vFont.isUnderlined());
        m_vCurrentFont.setFontPaint(m_vPaint);
    }

    public Font getFont() {
        return m_vCurrentFont;
    }

    public void drawString(String _zStr, int _iX, int _iY) {
        m_vCanvas.drawText(_zStr, _iX, _iY, m_vPaint);
    }
   
    public void drawString (String _zStr, int _iX, int _iY, int _iAnchor) {
        
        _iY -= m_vPaint.ascent();        
        if ((_iAnchor&BASELINE) !=0)
            _iY -= m_vPaint.getTextSize();        
        if ((_iAnchor&VCENTER) !=0)
            _iY -= m_vPaint.getTextSize()/2;
        if ((_iAnchor&BOTTOM) !=0)
            _iY -= m_vPaint.getTextSize();
        
        if ((_iAnchor&LEFT) !=0)
            m_vPaint.setTextAlign(Paint.Align.LEFT);
        if ((_iAnchor&RIGHT) !=0)
            m_vPaint.setTextAlign(Paint.Align.RIGHT);
        if ((_iAnchor&HCENTER) !=0)
            m_vPaint.setTextAlign(Paint.Align.CENTER);

        m_vPaint.setStyle(Paint.Style.FILL);
        m_vCanvas.drawText (_zStr, _iX, _iY, m_vPaint);
    }

    public void drawSubstring(String _zStr, int _iOffset, int _iLen, int _iX, int _iY, int _iAnchor) {
        this.drawString(_zStr.substring(_iOffset, _iOffset + _iLen), _iX, _iY, _iAnchor);
    }

    public void drawChar(char _iChar, int _iX, int _iY, int _iAnchor) {
        this.drawString(String.valueOf(_iChar), _iX, _iY, _iAnchor);
    }

    public void drawChars(char[] _Data, int _iOffset, int _iLength, int _iX, int _iY, int _iAnchor) {
        String zStr = new String(_Data);
        this.drawString(zStr.substring(_iOffset, _iOffset + _iLength), _iX, _iY, _iAnchor);
    }

    public void drawRGB(int[] _iRgbData, int _iOffset, int _iScanlength,
            int _iX, int _iY, int _iW, int _iH, boolean _bAlpha) {
        m_vCanvas.drawBitmap(_iRgbData, _iOffset, _iScanlength, _iX, _iY, _iW, _iH, _bAlpha, m_vPaint);
    }

    public void drawStar (int _iX, int _iY, int _iRadiusExt, int _iRadiusInt, int _iVerts, int _iFrame) {

        int iCosExt[] = new int[_iVerts];
        int iSinExt[] = new int[_iVerts];
        int iCosInt[] = new int[_iVerts];
        int iSinInt[] = new int[_iVerts];
        for (int i = 0; i < _iVerts; i++) {
            int angle = (512 * i) / 5;
            iCosExt[i] = (((-_iRadiusExt) * SINCOS[(angle + _iFrame) % 512]) >> SINCOS_PRECISIONBITS);
            iSinExt[i] = (((-_iRadiusExt) * SINCOS[(angle + _iFrame + 127) % 512]) >> (SINCOS_PRECISIONBITS));

            iCosInt[i] = (((_iRadiusInt) * SINCOS[(angle + _iFrame) % 512]) >> SINCOS_PRECISIONBITS);
            iSinInt[i] = (((_iRadiusInt) * SINCOS[(angle + _iFrame + 127) % 512]) >> (SINCOS_PRECISIONBITS));
        }

        Path path = new Path();
        path.moveTo(_iX + iCosExt[0], _iY + iSinExt[0]);
        path.lineTo(_iX + iCosInt[(1 + (_iVerts >> 1)) % _iVerts], _iY + iSinInt[(1 + (_iVerts >> 1)) % _iVerts]);

        for (int i = 1; i < _iVerts; i++) {
            path.lineTo(_iX + iCosExt[i], _iY + iSinExt[i]);
            path.lineTo(_iX + iCosInt[(i + 1 + (_iVerts >> 1)) % _iVerts], _iY + iSinInt[(i + 1 + (_iVerts >> 1)) % _iVerts]);
        }

        m_vCanvas.drawPath(path, m_vPaint);

    }
}
