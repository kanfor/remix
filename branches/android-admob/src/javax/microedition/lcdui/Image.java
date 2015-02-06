package javax.microedition.lcdui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.Settings;
import javax.microedition.midlet.MIDlet;

/**
 *
 * @author Mr Matsusaka
 */
public class Image extends Object
{
    private Bitmap m_vBitmap;
    private Graphics m_vGraphics;
    private float m_fSizeX = 1.0f;
    private float m_fSizeY = 1.0f;

    private Image(String _zPath) throws IOException {
        try {
            // Create an input stream to read from the assets folder
            String zPath = Settings.BITMAP_FOLDER[Settings.ms_iSize] + _zPath.substring(1, _zPath.length());
            InputStream ins = MIDlet.ms_AssetManager.open(zPath);
            m_vBitmap = BitmapFactory.decodeStream(ins);

        } catch (Exception ex) {
            throw new IOException(_zPath + "; " + ex.toString());
        }

        if (m_vBitmap == null)
            throw new IOException();
        }

    private Image (int _iW, int _iH) {
        m_vBitmap = Bitmap.createBitmap(_iW, _iH, Bitmap.Config.ARGB_8888);
        m_vBitmap.eraseColor(0xffffff);
    }

    private Image (Image _vImage) {
        m_vBitmap = _vImage.getBitmap();
    }

    private Image(InputStream ins) throws IOException
    {
        if (ins == null)
        {
            throw new IOException();
        }
        try
        {
            m_vBitmap = BitmapFactory.decodeStream(ins);
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
    }

    private Image(Image _iImage, int _iX, int _iY, int _iW, int _iH, int _iTransform)
    {
        m_vBitmap = Bitmap.createBitmap(_iImage.m_vBitmap, _iX, _iY, _iW, _iH);
    }

    private Image(byte[] _bData, int _iOffset, int _iLength)
    {
        m_vBitmap = BitmapFactory.decodeByteArray(_bData, _iOffset, _iLength);
    }

    private Image(int[] _iRgb, int _iW, int _iH, boolean _bAlpha)
    {
        m_vBitmap = Bitmap.createBitmap(_iRgb, _iW, _iH, Bitmap.Config.ARGB_8888);
    }

    public Graphics getGraphics () {
        if (m_vGraphics == null)
            m_vGraphics = new Graphics(m_vBitmap);

        return m_vGraphics;
    }

    public Bitmap getBitmap()
    {
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

    public int getHeightHalf() {
        return m_vBitmap.getHeight()>>1;
    }

    public int getWidthHalf() {
        return m_vBitmap.getWidth()>>1;
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

    public static Image createImage(InputStream _vImage) throws IOException  {
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
