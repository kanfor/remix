/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javax.microedition.lcdui;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region.Op;

import java.util.Random;

import com.kitmaker.finalkombat.ActivityMain;

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
   public static int ms_iX, ms_iY;
   public static Font ms_vCurrentFont;

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

   public static AssetManager ms_vAssetManager;
   
   public Graphics (Canvas _c) {
	   
	  if (ms_vCurrentFont == null) {
		  ms_vCurrentFont = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
	  }
	   
	  ms_vCanvas = _c;
	  m_vCanvas = _c;
	  m_vPaint = new Paint();
	  m_vPaint.setAntiAlias(ActivityMain.DRAW_ANTIALIASING);      
      
	  m_iSizeX = SIZEX = m_vCanvas.getWidth();
      m_iSizeY = SIZEY = m_vCanvas.getHeight();
   }

   public Graphics (Bitmap _vImage) {
	  if (ms_vCurrentFont == null) {
		  ms_vCurrentFont = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
	  }
	   
      m_vCanvas = new Canvas(_vImage);
      m_vPaint = new Paint(Paint.FILTER_BITMAP_FLAG);
	  m_vPaint.setAntiAlias(ActivityMain.DRAW_ANTIALIASING);
      
      m_iSizeX = m_vCanvas.getWidth();
      m_iSizeY = m_vCanvas.getHeight();
   }

   public void translate(int _iX, int _iY) {
      ms_iX = _iX;
      ms_iY = _iY;
      m_vCanvas.translate(_iX, _iY);
   }

   public int getClipX() {
   	  return m_vCanvas.getClipBounds().left;
   }
   
   public int getClipY() {
   	  return m_vCanvas.getClipBounds().top;
   }
   
   public int getClipWidth () {
	   return m_vCanvas.getClipBounds().width();
   }
   
   public int getClipHeight () {
	   return m_vCanvas.getClipBounds().height();
   }
   
   public int getTranslateX() {
      return ms_iX;
   }

   public int getTranslateY() {
      return ms_iY;
   }

   public int getRedComponent () {
      return ((m_vPaint.getColor()&0xff)<<16);
   }

   public int getGreenComponent () {
      return ((m_vPaint.getColor()&0xff)<<8);
   }

   public int getBlueComponent () {
      return ((m_vPaint.getColor()&0xff));
   }
   
   public int getColor() {
      return m_vPaint.getColor();
   }

   public int getAlpha () {
	  return m_vPaint.getAlpha();
   }
   
   public float getSizeX () {
	  return m_fSizeX;
   }
   
   public float getSizeY () {
	  return m_fSizeY;
   }

   public float getImageAngle () {
	  return m_fAngle;
   }
   
   public void setImageSize (float _iNewSizeX, float _iNewSizeY) {
	  if (_iNewSizeX < 0)
		 _iNewSizeX = 0;
	  if (_iNewSizeY < 0)
		 _iNewSizeY = 0;
	  
	  m_fSizeX = _iNewSizeX;
	  m_fSizeY = _iNewSizeY;
   }
   
   public void setImageAngle (float _fNewAngle) {
	  m_fAngle	= (Math.abs(_fNewAngle)+360f)%360f;
   }

   public void setColor (int _iColor) {
      if (((_iColor&0xff)>>24) == 0x00)
         _iColor = (255<<24) + _iColor;
      if (_iColor == 0)
    	  _iColor = (255<<24) + 0x000000;
      
      m_vPaint.setColor(_iColor);
   }

   public void setColor (int _iA, int _iR, int _iG, int _iB) {
      m_vPaint.setColor((_iA<<24)|(_iR<<16)|(_iG<<8)|_iB);
   }

   public void setColor (int _iR, int _iG, int _iB) {
      m_vPaint.setColor((255<<24)|(_iR<<16)|(_iG<<8)|_iB);
   }
   
   public void setGrayScale (boolean _bSetGrayScale) {
	  ColorMatrix vCm = new ColorMatrix();
	  vCm.setSaturation(_bSetGrayScale?0:1);
	  ColorMatrixColorFilter vCMF = new ColorMatrixColorFilter(vCm);
	  m_vPaint.setColorFilter(vCMF);
   }

   public void setAlpha (int _iAlpha) {
	  m_vPaint.setAlpha(_iAlpha);
   }
   
   public void setStrokeStyle(int _iStyle) {
      //m_vPaint.setStrokeCap(Paint.Cap.BUTT);
   }

   public void setClip (int _iX, int _iY, int _iW, int _iH) {
       m_vCanvas.clipRect(_iX, _iY, _iX+_iW, _iY+_iH, Op.REPLACE);
   }

   public void clipRect (int _iX, int _iY, int _iW, int _iH) {
       m_vCanvas.clipRect(_iX, _iY, _iX+_iW, _iY+_iH, Op.INTERSECT);
   }

   public void fillRect (int _iX, int _iY, int _iW, int _iH) {
      m_vPaint.setStyle(Paint.Style.FILL);
      m_vCanvas.drawRect(_iX, _iY, _iX + Math.max(0, _iW), _iY + Math.max(0, _iH), m_vPaint);
   }

   public void fillArc (int _iX, int _iY, int _iW, int _iH, int startAngle, int arcAngle) {
      m_vPaint.setStyle(Paint.Style.FILL);
      RectF oval = new RectF(_iX, _iY, _iX + _iW, _iY + _iH);
      m_vCanvas.drawArc(oval, startAngle, arcAngle, true, m_vPaint);
   }

   public void fillRoundRect (int _iX, int _iY, int _iSizeX, int _iSizeY) {
      m_vPaint.setStyle(Paint.Style.FILL);
      m_vCanvas.drawRoundRect(new RectF(_iX, _iY, _iX + _iSizeY, _iY + _iSizeY), _iSizeX, _iSizeY, m_vPaint);
   };

   public void fillRoundRect (int _iX, int _iY, int _iSizeX, int _iSizeY, int _iRoundX, int _iRoundY) {
      m_vPaint.setStyle(Paint.Style.FILL);
      m_vCanvas.drawRoundRect(new RectF( _iX, _iY, _iX+_iSizeX, _iY+_iSizeY ), _iRoundX, _iRoundY, m_vPaint);
   };

   public void fillCircle (int _iX, int _iY, int _iRadiusX, int _iRadiusY) {
	  m_vPaint.setStyle(Paint.Style.FILL);
      RectF rRect = new RectF(_iX - _iRadiusX, _iY - _iRadiusY, _iX + _iRadiusX, _iY + _iRadiusY);
      m_vCanvas.drawRoundRect(rRect, _iRadiusX, _iRadiusY, m_vPaint);
   };
   
   public void fillTriangle (int _x0, int _y0, int _x1, int _y1, int _x2, int _y2) {
      m_vPaint.setStyle(Paint.Style.FILL);
      Path path = new Path();
      path.moveTo(_x0, _y0);
      path.lineTo(_x1, _y1);
      path.lineTo(_x2, _y2);
      m_vCanvas.drawPath(path, m_vPaint);
   };

   public void drawRect (int _iX, int _iY, int _iW, int _iH) {
      m_vPaint.setStyle(Paint.Style.STROKE);
      m_vCanvas.drawRect(_iX, _iY, _iX + _iW, _iY + _iH, m_vPaint);
   }

   public void drawArc (int _iX, int _iY, int _iW, int _iH, int startAngle, int arcAngle) {
      m_vPaint.setStyle(Paint.Style.STROKE);
      RectF oval = new RectF(_iX, _iY, _iX + _iW, _iY + _iH);
      m_vCanvas.drawArc(oval, startAngle, arcAngle, true, m_vPaint);
   }

   public void drawRoundRect (int _iX, int _iY, int _iSizeX, int _iSizeY) {
      m_vPaint.setStyle(Paint.Style.STROKE);
      m_vCanvas.drawRoundRect(new RectF(_iX, _iY, _iX + _iSizeX, _iY + _iSizeY), _iSizeX, _iSizeY, m_vPaint);
   };

   public void drawRoundRect (int _iX, int _iY, int _iSizeX, int _iSizeY, int _iRoundX, int _iRoundY) {
      m_vPaint.setStyle(Paint.Style.STROKE);
      m_vCanvas.drawRoundRect( new RectF( _iX, _iY, _iX+_iSizeX, _iY+_iSizeY ), _iRoundX, _iRoundY, m_vPaint);
   };

   public void drawCircle (int _iX, int _iY, int _iRadiusX, int _iRadiusY) {
      m_vPaint.setStyle(Paint.Style.STROKE);
      RectF rRect = new RectF(_iX - _iRadiusX, _iY - _iRadiusY, _iX + _iRadiusX, _iY + _iRadiusY);
      m_vCanvas.drawRoundRect(rRect, _iRadiusX, _iRadiusY, m_vPaint);
   };
   
   public void drawTriangle (int _x0, int _y0, int _x1, int _y1, int _x2, int _y2) {
      m_vPaint.setStyle(Paint.Style.STROKE);
      Path path = new Path();
      path.moveTo(_x0, _y0);
      path.lineTo(_x1, _y1);
      path.lineTo(_x2, _y2);
      m_vCanvas.drawPath(path, m_vPaint);
   };

   public void drawLine (int _iX0, int _iY0, int _iX1, int _iY1) {
      m_vPaint.setStyle(Paint.Style.STROKE);
      m_vCanvas.drawLine(_iX0, _iY0, _iX1, _iY1, m_vPaint);
   }

	public void drawImage(Bitmap _vImage, int _iX, int _iY) {

		// translation / transform
		Matrix vMatrix = new Matrix();
		vMatrix.setTranslate(_iX, _iY);
		vMatrix.preScale(m_fSizeX, m_fSizeY, 0, 0);
		if (m_fAngle != 0)
			vMatrix.preRotate(m_fAngle, 0, 0);

		m_vCanvas.drawBitmap(_vImage, vMatrix, m_vPaint);
		vMatrix = null;
	}
   
   public void drawImage (Image _vImage, int _iX, int _iY) {
	  drawImage(_vImage.getBitmap(), _iX, _iY);
   }
   
	public void drawImage(Bitmap _vImage, int _iX, int _iY, int _iAnchor) {

		// translation / transform
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
		vMatrix.preScale(fSizeX, fSizeY, iExtra_X, iExtra_Y);
		if (m_fAngle != 0)
			vMatrix.preRotate(m_fAngle, iExtra_X, iExtra_Y);

		// m_vCanvas.save();
		// m_vCanvas.clipRect(m_vSetClip);
		m_vCanvas.drawBitmap(_vImage, vMatrix, m_vPaint);
		// m_vCanvas.restore();
		vMatrix = null;
	}
   
   public void drawImage (Image _vImage, int _iX, int _iY, int _iAnchor) {
	   drawImage(_vImage.getBitmap(), _iX, _iY, _iAnchor);
   }

	public void drawRegion(Bitmap _vImage, int _iSrcX, int _iSrcY, int _iSrcW,
		int _iSrcH, int _iTransform, int _iX, int _iY) {
		int dRefX = _iSrcW >> 1;
		int dRefY = _iSrcH >> 1;

		m_vCanvas.save();
		if (_iTransform == javax.microedition.lcdui.game.Sprite.TRANS_MIRROR || 
			_iTransform == javax.microedition.lcdui.game.Sprite.TRANS_MIRROR_ROT90 || 
			_iTransform == javax.microedition.lcdui.game.Sprite.TRANS_MIRROR_ROT180 || 
			_iTransform == javax.microedition.lcdui.game.Sprite.TRANS_MIRROR_ROT270) {
			m_vCanvas.scale(-1, 1, _iX + dRefX, _iY + dRefY);
		}

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
		RectF rDst = new RectF(_iX, _iY, _iX + (_iSrcW * m_fSizeX), _iY + (_iSrcH * m_fSizeY));
		m_vCanvas.drawBitmap(_vImage, rSrc, rDst, m_vPaint);
		m_vCanvas.restore();

	}

   public void drawRegion (Image _vImage, int _iSrcX, int _iSrcY, int _iSrcW, int _iSrcH, int _iTransform, int _iX, int _iY) {
	  drawRegion(_vImage.getBitmap(), _iSrcX, _iSrcY, _iSrcW, _iSrcH, _iTransform, _iX, _iY);
   }

   public void drawRegion (Bitmap _vImage, int _iSrcX, int _iSrcY, int _iSrcW, int _iSrcH, int _iTransform, int _iX, int _iY, int _iAnchor) {
      if ((_iAnchor&BOTTOM) !=0)
         _iY -= _vImage.getHeight();
      if ((_iAnchor&RIGHT) !=0)
         _iX -= _vImage.getWidth();
      if ((_iAnchor&VCENTER) !=0)
         _iY -= _vImage.getHeight()>>1;
      if ((_iAnchor&HCENTER) !=0)
         _iX -= _vImage.getWidth()>>1;

      int dRefX = _iSrcW>>1;
      int dRefY = _iSrcH>>1;
      
      m_vCanvas.save();
		if (_iTransform == javax.microedition.lcdui.game.Sprite.TRANS_MIRROR || 
			_iTransform == javax.microedition.lcdui.game.Sprite.TRANS_MIRROR_ROT90 || 
			_iTransform == javax.microedition.lcdui.game.Sprite.TRANS_MIRROR_ROT180 || 
			_iTransform == javax.microedition.lcdui.game.Sprite.TRANS_MIRROR_ROT270) {
			m_vCanvas.scale(-1, 1, _iX + dRefX, _iY + dRefY);
		}
		
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
      RectF rDst = new RectF(_iX, _iY, _iX + (_iSrcW*m_fSizeX), _iY + (_iSrcH*m_fSizeY));
      m_vCanvas.drawBitmap(_vImage, rSrc, rDst, m_vPaint);
      m_vCanvas.restore();

   }

   public void drawRegion (Image _vImage, int _iSrcX, int _iSrcY, int _iSrcW, int _iSrcH, int _iTransform, int _iX, int _iY, int _iAnchor) {
	   drawRegion(_vImage.getBitmap(), _iSrcX, _iSrcY, _iSrcW, _iSrcH, _iTransform, _iX, _iY, _iAnchor);
   }
   
   
   public void setFont(Font _vFont) {
	   ms_vCurrentFont = _vFont;
       m_vPaint.setTextSize(_vFont.getHeight());
       m_vPaint.setFakeBoldText(_vFont.isBold());
       m_vPaint.setUnderlineText(_vFont.isUnderlined());
       ms_vCurrentFont.setFontPaint(m_vPaint);
   }

   public Font getFont () {
	   return ms_vCurrentFont;	   
   }
   
   public void drawString (String _zStr, int _iX, int _iY, int _iAnchor) {
      _iY -= m_vPaint.ascent();
      if ((_iAnchor&BASELINE) !=0)
         _iY -= m_vPaint.getTextSize();
      if ((_iAnchor&BOTTOM) !=0)
         _iY -= m_vPaint.getTextSize();
      if ((_iAnchor&RIGHT) !=0)
         _iX -= m_vPaint.measureText(_zStr);
      if ((_iAnchor&VCENTER) !=0)
         _iY -= m_vPaint.getTextSize()/2;
      if ((_iAnchor&HCENTER) !=0)
         _iX -= m_vPaint.measureText(_zStr)/2;

      m_vCanvas.drawText (_zStr, _iX, _iY, m_vPaint);
   }

   public void drawSubstring (String _zStr, int _iOffset, int _iLen, int _iX, int _iY, int _iAnchor) {

      String zFinalStr = _zStr.substring(_iOffset, _iOffset + _iLen);

      if ((_iAnchor&BASELINE) !=0)
         _iY -= m_vPaint.getTextSize();
      if ((_iAnchor&BOTTOM) !=0)
         _iY -= m_vPaint.getTextSize();
      if ((_iAnchor&RIGHT) !=0)
         _iX -= m_vPaint.measureText(zFinalStr);
      if ((_iAnchor&VCENTER) !=0)
         _iY -= m_vPaint.getTextSize()/2;
      if ((_iAnchor&HCENTER) !=0)
         _iX -= m_vPaint.measureText(zFinalStr)/2;

      m_vCanvas.drawText (zFinalStr, _iX, _iY, m_vPaint);

   }

   public void drawChar (char _iChar, int _iX, int _iY, int _iAnchor) {

      String zStr = String.valueOf(_iChar);

      if ((_iAnchor&BASELINE) !=0)
         _iY -= m_vPaint.getTextSize();
      if ((_iAnchor&BOTTOM) !=0)
         _iY -= m_vPaint.getTextSize();
      if ((_iAnchor&RIGHT) !=0)
         _iX -= m_vPaint.measureText(zStr);
      if ((_iAnchor&VCENTER) !=0)
         _iY -= m_vPaint.getTextSize()/2;
      if ((_iAnchor&HCENTER) !=0)
         _iX -= m_vPaint.measureText(zStr)/2;

      m_vCanvas.drawText (zStr, _iX, _iY, m_vPaint);
   }

   public void drawChars (char[] _Data, int _iOffset, int _iLength, int _iX, int _iY, int _iAnchor) {
      //String zStr = _Data.toString();
	   String zStr = new String(_Data);
      zStr = zStr.substring(_iOffset, _iOffset + _iLength);

      if ((_iAnchor&BASELINE) !=0)
         _iY -= m_vPaint.getTextSize();
      if ((_iAnchor&BOTTOM) !=0)
         _iY -= m_vPaint.getTextSize();
      if ((_iAnchor&RIGHT) !=0)
         _iX -= m_vPaint.measureText(zStr);
      if ((_iAnchor&VCENTER) !=0)
         _iY -= m_vPaint.getTextSize()/2;
      if ((_iAnchor&HCENTER) !=0)
         _iX -= m_vPaint.measureText(zStr)/2;

      m_vCanvas.drawText (zStr, _iX, _iY, m_vPaint);
   }

   public void drawRGB (int[] _iRgbData, int _iOffset, int _iScanlength, 
      int _iX, int _iY, int _iW, int _iH, boolean _bAlpha) {
      //m_vPaint.setColor(0xffffffff);
      m_vCanvas.drawBitmap (_iRgbData, _iOffset, _iScanlength, _iX, _iY, _iW, _iH, _bAlpha, m_vPaint);
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

   };

///////////////////////////////////////////////////////////////////////////
//
///////////////////////////////////////////////////////////////////////////
   static final int THUNDER_COLOR[] = {0xffffff, 0x00aaff};
   static final byte THUNDER_SECTIONS = 8;
   static final int THUNDER_RANDOM_FACTOR = SIZEX / 8;

   public void drawThunder (int _x0, int _y0, int _x1, int _y1, int _density, int _sections) {
      int[] iX = new int[_sections + 1];
      int[] iY = new int[_sections + 1];

      m_vPaint.setAlpha(255);

      iX[0] = _x0;
      iY[0] = _y0;
      iX[_sections] = _x1;
      iY[_sections] = _y1;

      for (int j = 1; j < _sections + 1; j++) {
         iX[j] = _x0 + (((_x1 - _x0) / _sections) * j) + (-THUNDER_RANDOM_FACTOR + m_Random.nextInt(THUNDER_RANDOM_FACTOR<<1));
         iY[j] = _y0 + (((_y1 - _y0) / _sections) * j) + (-THUNDER_RANDOM_FACTOR + m_Random.nextInt(THUNDER_RANDOM_FACTOR<<1));
      }

      if (_density == 1) {
         for (int j = 0; j < _sections; j++) {
            m_vCanvas.drawLine(iX[j], iY[j], iX[j + 1], iY[j + 1], m_vPaint);
         }

      } else {
         if (Math.abs(_x0 - _x1) > Math.abs(_y0 - _y1)) {
            for (int k = 0; k < _density; k++) {
               for (int j = 0; j < _sections; j++) {
                  m_vCanvas.drawLine(iX[j], iY[j] - (_density >> 1) + k, iX[j + 1], iY[j + 1] - (_density >> 1) + k, m_vPaint);
               }
            }
         } else {
            for (int k = 0; k < _density; k++) {
               for (int j = 0; j < _sections; j++) {
                  m_vCanvas.drawLine(iX[j] - (_density >> 1) + k, iY[j], iX[j + 1] - (_density >> 1) + k, iY[j + 1], m_vPaint);
               }
            }
         }
      }
   }

   public int getBlendedColor (int _iColor1, int _iColor2, int _iQuantity) {  // Quantity = 0..255
      int iFinalColor = 0;
      int iR1 = _iColor1 & 0xFF;
      int iR2 = _iColor2 & 0xFF;
      int iG1 = (_iColor1 >> 8) & 0xFF;
      int iG2 = (_iColor2 >> 8) & 0xFF;
      int iB1 = (_iColor1 >> 16) & 0xFF;
      int iB2 = (_iColor2 >> 16) & 0xFF;

      iFinalColor |= iR1 + (((iR2 - iR1) * _iQuantity) >> 8);
      iFinalColor |= (iG1 + (((iG2 - iG1) * _iQuantity) >> 8)) << 8;
      iFinalColor |= (iB1 + (((iB2 - iB1) * _iQuantity) >> 8)) << 16;

      return iFinalColor;
   }

}
