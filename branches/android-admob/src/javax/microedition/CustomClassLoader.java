package javax.microedition;

import android.content.res.*;
import dalvik.system.*;
import java.io.*;

public class CustomClassLoader extends PathClassLoader {

    public AssetManager ms_AssetManager;
    public static final String OBFUSCATED_CLASSNAME = CustomClassLoader.class.getName();
    
	public CustomClassLoader(String pPath, AssetManager pAssetManager) {
        super(pPath, getSystemClassLoader());//we set the parent to be the system class loader so the loading gets done in this class
        ms_AssetManager = pAssetManager;
    }

    @Override
    public InputStream getResourceAsStream(String resName) {
        if (resName.startsWith("/")) {
            resName = resName.substring(1);
        }

        try {
            return ms_AssetManager.open(resName);//try the given path
        } catch (IOException ex) {
        }

        try {
            return ms_AssetManager.open(Settings.BITMAP_FOLDER[Settings.ms_iSize] + resName);//try appending the resolution specific path
        } catch (IOException ex) {
        }

        try {
            return ms_AssetManager.open(Settings.BITMAP_FOLDER_COMMON + resName);//try appending the common path
        } catch (IOException ex) {
        }
        int index = resName.lastIndexOf("/");
        if (index > 0) {
            return getResourceAsStream(resName.substring(index));//try appending the common path
        }
        return super.getResourceAsStream(resName);
    }

    @Override
    public String toString() {
        return OBFUSCATED_CLASSNAME;
    }
}
