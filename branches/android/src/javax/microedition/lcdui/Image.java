/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javax.microedition.lcdui;

//import java.io.InputStream;

import java.io.IOException;
import java.io.InputStream;

import com.kitmaker.finalkombat.ActivityMain;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 *
 * @author Mr Matsusaka
 */
public class Image extends Object {
   private Bitmap m_vBitmap;
   private float m_fSizeX = 1.0f; 
   private float m_fSizeY = 1.0f; 

   public Image (String _zPath) {
	  try {
		  
          // Create an input stream to read from the assets folder
		  if (ActivityMain.BITMAP_FOLDER == ActivityMain.FOLDER_ASSETS) {
			  String zPath = ActivityMain.BITMAP_FOLDER_NAME + "/" + _zPath.substring(1, _zPath.length());
			  InputStream ins = ActivityMain.ms_AssetManager.open(zPath.toLowerCase());
			  m_vBitmap = BitmapFactory.decodeStream(ins);
			  
		  } else {
          // Create an input stream to read from the res folder
			  String zPackage = ActivityMain.ms_Context.getPackageName();
		  	  String zPath = _zPath.substring(1, _zPath.length()-4);
		  	  int resID = ActivityMain.ms_Context.getResources().getIdentifier(zPath.toLowerCase(), ActivityMain.BITMAP_FOLDER_NAME, zPackage);
		  	  m_vBitmap = BitmapFactory.decodeResource(ActivityMain.ms_Context.getResources(), resID);
		  }
	  } catch (Exception ex) {}
   }

   public Image (int _iW, int _iH) {
      m_vBitmap = Bitmap.createBitmap(_iW, _iH, Bitmap.Config.ARGB_8888);
      m_vBitmap.eraseColor(0xffffff);
   }

   public Image (Image _vImage) {
	  m_vBitmap = _vImage.getBitmap();
   }

   public Image (Image _iImage, int _iX, int _iY, int _iW, int _iH, int _iTransform) {
	  m_vBitmap = Bitmap.createBitmap(_iImage.m_vBitmap, _iX, _iY, _iW, _iH);
   }
   
   public Image (byte[] _bData, int _iOffset, int _iLength) {
      m_vBitmap = BitmapFactory.decodeByteArray(_bData, _iOffset, _iLength);
   }
   
   public Image (int[] _iRgb, int _iW, int _iH, boolean _bAlpha) {
      m_vBitmap = Bitmap.createBitmap(_iRgb, _iW, _iH, Bitmap.Config.ARGB_8888);
   }

   
   public Graphics getGraphics () {
      return new Graphics(m_vBitmap);
   }

   public Bitmap getBitmap () {
      return m_vBitmap;
   }
   
   public float getSizeX () {
	  return m_fSizeX;
   }
   public float getSizeY () {
	  return m_fSizeY;
   }

   public void setSize (float _fNewSizeX, float _fNewSizeY) {
	  m_fSizeX = _fNewSizeX;
	  m_fSizeY = _fNewSizeY;
   }
   public void setSizeX (float _fNewSizeX) {
	  m_fSizeX = _fNewSizeX;
   }
   public void setSizeY (float _fNewSizeY) {
	  m_fSizeY = _fNewSizeY;
   }
   
   public int getHeight() {
      return m_vBitmap.getHeight();
   }

   public int getWidth() {
      return m_vBitmap.getWidth();
   }

   public boolean isMutable () {
      return m_vBitmap.isMutable();
   }

   public void getRGB(int[] _iRgbData, int _iOffset, int _iScanlength, int _iX, int _iY, int _iW, int _iH) {
      m_vBitmap.getPixels(_iRgbData, _iOffset, _iScanlength, _iX, _iY, _iW, _iH);
   }

   public static Image createImage(String _zSource) throws IOException {
      return new Image(_zSource);
   }

   public static Image createImage(int _iW, int _iH) {
      return new Image(_iW, _iH);
   }

   public static Image createImage(Image _vImage) {
      return new Image(_vImage);
   }
   
   public static Image createImage(Image _vImage, int _iX, int _iY, int _iW, int _iH, int _iTransform) {
      return new Image(_vImage, _iX, _iY, _iW, _iH, _iTransform);
   }

   public static Image createImage(byte[] _bData, int _iOffset, int _iLength) {
      return new Image(_bData, _iOffset, _iLength);
   }
	      
   public static Image createRGBImage (int[] _iRgb, int _iW, int _iH, boolean _bAlpha) {
      return new Image (_iRgb, _iW, _iH, _bAlpha);
   }
}
