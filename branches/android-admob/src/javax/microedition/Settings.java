package javax.microedition;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Locale;
import javax.microedition.midlet.MIDlet;

/**
 * Settings is a class to organize, set configurations and get diverse information from the android device
 * @author Fran Matsusaka
 */
@SuppressLint("DefaultLocale")
public class Settings {

    // Image drawing
    public static final boolean DRAW_ANTIALIASING = true;

    // Supported resolutions (there must be at leats one or it will crash)
    public static final boolean[] SUPPORTED_RESOLUTIONS = {
        false,   // 240x320
        true,   // 320x480
        false,   // 480x800
        false,  // 720x1280
    };

    //Screen sleep. Put false for never sleep while activity is running.
    public static final boolean SCREEN_SLEEP = false;

    public static PowerManager ms_PowerManager;
    public static PowerManager.WakeLock ms_WakeLock;

    // Bitmap folders (must be allocated in assets folder)
    public static String[] BITMAP_FOLDER = {
        "drawable-ldpi/",
        "drawable-mdpi/",
        "drawable-hdpi/",
        "drawable-xhdpi/",
    };
    
    // Common bitmap folder (must be allocated in assets folder)
    public static String BITMAP_FOLDER_COMMON = "drawable-common/";

    public static String[] ORIENTATION_FOLDER = {
        "landscape/",
        "portrait/",
    };

    // Screen orientation
    public static final int ORIENTATION_PORTRAIT = 1; //ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;   // Configuration.ORIENTATION_PORTRAIT;
    public static final int ORIENTATION_LANDSCAPE = 0; //ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE; // Configuration.ORIENTATION_LANDSCAPE;
    public static final int ORIENTATION_SENSOR = 4; //ActivityInfo.SCREEN_ORIENTATION_SENSOR; // Configuration.ORIENTATION_LANDSCAPE;
    public static final int ORIENTATION_NOSENSOR = 5; //ActivityInfo.SCREEN_ORIENTATION_SENSOR; // Configuration.ORIENTATION_LANDSCAPE;
    public static int DEFAULT_ORIENTATION = ORIENTATION_SENSOR;
    

    // Screen size
    public static final int QVGA_240X320 = 0;
    public static final int HVGA_320X480 = 1;
    public static final int WVGA_480X800 = 2;
    public static final int WXGA_720X1280 = 3;

    public static int ms_iOrientation;
    public static int ms_iSize = HVGA_320X480;
    public static final int[][] SCREEN_SIZES = {
        {240, 320},
        {320, 480},
        {480, 800},
        {720, 1280},
    };

    public static int ms_iScreenWidth;
    public static int ms_iScreenHeight;
    public static float ms_fWidthPercent;
    public static float ms_fHeightPercent;
    public static int ms_iCPUMaxMhzs;
    public static boolean ms_bFastDevice;

    // configuration
    public static int ms_iKeyboardType;
    public static int ms_iKeyboardState;
    public static int ms_iScreenTouchType;
    public static int ms_iNavigationPadType;
    public static String ms_zLanguage;
    public static String ms_zAppVersion;
    public static String ms_zDeviceID = "";
    public static String ms_zDeviceBrand;
    public static String ms_zDeviceModel;
    public static String ms_zDeviceProduct;
    public static boolean ms_bHasVibration;

    // os
    public static final int OS_ANDROID = 0;
    public static final int OS_BB10QNX = 1;
    public static final int OS_KINDLE = 2;
    public static final int OS_OUYA = 3;
    public static int ms_iDeviceOS = OS_ANDROID;

    private static String ms_zAndroidVersion;
    private static double ms_dAndroidVersion;
    private static int ms_iAndroidVersion;

    // bar size
    private static int ms_iStatusBarHeight;

    /**
     * Set everithing for running a J2ME application. This function must to be called before the MIDlet starts
     * @param activity current activity
     */
    public static void initSettings(Activity activity) {
        MIDlet.ms_Activity = activity;
        
        // get android platform
        if (System.getProperty("os.name").equalsIgnoreCase("qnx")) 
            ms_iDeviceOS = OS_BB10QNX;
        if (android.os.Build.MODEL.toLowerCase(Locale.ENGLISH).startsWith("kindle") ||
            android.os.Build.MODEL.toLowerCase(Locale.ENGLISH).startsWith("kf"))
            ms_iDeviceOS = OS_KINDLE;
        if (android.os.Build.MODEL.toLowerCase(Locale.ENGLISH).startsWith("ouya"))
            ms_iDeviceOS = OS_OUYA;

        // get android version
        String zNum1 = "" + android.os.Build.VERSION.RELEASE.charAt(0);
        String zNum2 = "" + android.os.Build.VERSION.RELEASE.charAt(2);
        ms_zAndroidVersion = android.os.Build.VERSION.RELEASE.substring(0, 3);
        ms_dAndroidVersion = Integer.valueOf(zNum1) + (Integer.valueOf(zNum2) * 0.1);
        ms_iAndroidVersion = getDeviceVersionAPI();
        
        // add third number if exists
        // ...sometimes the number has taglines at the end, we need to make this check to ensure we dont get incorrect number versions
        if (android.os.Build.VERSION.RELEASE.length() > 4 && android.os.Build.VERSION.RELEASE.charAt(3) == '.') {
            ms_zAndroidVersion += "." + android.os.Build.VERSION.RELEASE.charAt(4);
        }

        
        // screen data
        DisplayMetrics dm = new DisplayMetrics();
        MIDlet.ms_Activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        ms_iScreenWidth = dm.widthPixels;
        ms_iScreenHeight = dm.heightPixels;

        // BB 10 hack - the device is reporting wrong height (thanks bb for being so ass)
        if (Settings.ms_iDeviceOS == Settings.OS_BB10QNX) {
            if (ms_iScreenHeight == 620)
                ms_iScreenHeight = 720;
            else if (ms_iScreenHeight == 1180)
                ms_iScreenHeight = 1280;
        }
        
        ms_iStatusBarHeight = 0; // status bar size
        int resourceId = MIDlet.ms_Activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            ms_iStatusBarHeight = MIDlet.ms_Activity.getResources().getDimensionPixelSize(resourceId);
        }
        
        
        // screen orientation
        DEFAULT_ORIENTATION = MIDlet.ms_Activity.getRequestedOrientation();
        int iLogicOrientation = ((ms_iScreenWidth > ms_iScreenHeight) ? ORIENTATION_LANDSCAPE : ORIENTATION_PORTRAIT);
        ms_iOrientation = (DEFAULT_ORIENTATION != ORIENTATION_LANDSCAPE && DEFAULT_ORIENTATION != ORIENTATION_PORTRAIT) ? iLogicOrientation : DEFAULT_ORIENTATION;
        if (ms_iOrientation != iLogicOrientation) {
            ms_iScreenWidth = dm.heightPixels;
            ms_iScreenHeight = dm.widthPixels;
        }
        MIDlet.ms_Activity.setRequestedOrientation(ms_iOrientation);

        
        // get the size to check for the ideal resolution
        int iSize = ms_iScreenWidth;
        if (ms_iScreenWidth > ms_iScreenHeight) {
            iSize = ms_iScreenHeight + ms_iStatusBarHeight;
        }

        // graphic set
        for (int i = SCREEN_SIZES.length - 1; i >= 0; --i) {
            if (iSize >= SCREEN_SIZES[i][0]) {
                ms_iSize = i;
                break;
            }
        }

        // set is not supported,
        if (!SUPPORTED_RESOLUTIONS[ms_iSize]) {
            boolean bFound = false;

            // search for the next lower available sets
            for (int i = ms_iSize - 1; i >= 0; i--) {
                if (SUPPORTED_RESOLUTIONS[i]) {
                    ms_iSize = i;
                    bFound = true;
                    break;
                }
            }

            // not found lower available set, search for the next higher available set
            if (!bFound) {
                for (int i = ms_iSize; i < SUPPORTED_RESOLUTIONS.length; i++) {
                    if (SUPPORTED_RESOLUTIONS[i]) {
                        ms_iSize = i;
                        break;
                    }
                }
            }
        }
        
        // get the screen percentage for touch screen purpouses
        ms_fWidthPercent = (float) getWidth() / ms_iScreenWidth;
        ms_fHeightPercent = (float) getHeight() / ms_iScreenHeight;

        // hardware info
        Configuration vConfig = MIDlet.ms_Activity.getResources().getConfiguration();
        ms_iKeyboardType = vConfig.keyboard;
        ms_iScreenTouchType = vConfig.touchscreen;
        ms_iNavigationPadType = vConfig.navigation;
        ms_zDeviceID = getDeviceID();
        ms_iCPUMaxMhzs = getMaxCPUFreqMHz();
        ms_bFastDevice = ms_iCPUMaxMhzs >= 1024;

        // has vibrator
        ms_bHasVibration = !isTabletDevice();

        // get device language
        ms_zLanguage = Locale.getDefault().getLanguage();

        // get application version
        try {
            PackageInfo info = MIDlet.ms_Activity.getPackageManager().getPackageInfo(MIDlet.ms_Activity.getPackageName(), 0);
            ms_zAppVersion = String.valueOf(info.versionName);
        } catch (PackageManager.NameNotFoundException ex) {
        }
    }

    /**
     * Gets the screen width. This is a hardcode value for each family
     * @return 
     */
    public static int getWidth() {
        return SCREEN_SIZES[ms_iSize][1 - ms_iOrientation];
    }

    /**
     * Gets the screen height.
     * @return 
     */
    public static int getHeight() {
        int fH = SCREEN_SIZES[ms_iSize][ms_iOrientation];

        // android 4.0 status bar adjustment hack
        if (ms_iScreenWidth > 320) {
            fH = (int) Math.min(ms_iScreenHeight, SCREEN_SIZES[ms_iSize][ms_iOrientation]);
        }
        
        // altmirant and 480x640 devices hack
        if (ms_iSize == WVGA_480X800 && ms_iScreenHeight == 640) {
            fH = (int) ms_iScreenHeight;
        }
        
        // blackberry Q5/Q10 hack
        if (ms_iDeviceOS == OS_BB10QNX && ms_iScreenWidth == 720 && ms_iScreenHeight == 720) {
            fH = SCREEN_SIZES[ms_iSize][1 - ms_iOrientation];
        }
        return fH;
    }

    /**
     * Gets the screen set.
     * @return 
     */
    public static int getSizeSet() {
        return ms_iSize;
    }

    /**
     * Gets the screen orientation.
     * @return 
     */
    public static int getScreenOrientation() {
        return ms_iOrientation;
    }

    /**
     * Tries to get the device unique ID. If it weren't possible it will generate a pseudo-unique ID
     * @return device unique ID
     */
    private static String getDeviceID() {

        String zDeviceID = "";
        try {
            // get phone ID
            TelephonyManager tManager = (TelephonyManager) MIDlet.ms_Activity.getSystemService(Context.TELEPHONY_SERVICE);
            zDeviceID = tManager.getDeviceId();

            // get android ID
            if (zDeviceID == null || zDeviceID.equals("")) {
                zDeviceID = android.provider.Settings.Secure.getString(
                        MIDlet.ms_Activity.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            }

        } catch (Exception ex) {
        }

        try {

            // get pseudo-unique ID
            if (zDeviceID == null || zDeviceID.equals("")) {
                zDeviceID = "3579" + //we make this look like a valid IMEI
                        Build.BOARD.length() % 10 + Build.BRAND.length() % 10 + Build.DEVICE.length() % 10
                        + Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 + Build.ID.length() % 10
                        + Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10
                        + Build.TYPE.length() % 10 + Build.USER.length() % 10; //11 digits
            }
        } catch (Exception ex) {
        }

        return zDeviceID;
    }

    /**
     * Check if the running device is a phones or a tablets. please, note that the information isn't 100% reliable in all cases
     * @return true if is a tablet, false if is a phone
     */
    public static boolean isTabletDevice() {

        // is kindle?
        if (ms_iDeviceOS == OS_KINDLE) {
            return true;
        }

        // Check if has telephony service
        TelephonyManager manager = (TelephonyManager) MIDlet.ms_Activity.getSystemService(Context.TELEPHONY_SERVICE);
        int iPhoneType = manager.getPhoneType();
        if (iPhoneType != 2) {
            return false;
        }

        DisplayMetrics dm = new DisplayMetrics();
        MIDlet.ms_Activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
        double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
        return Math.sqrt(x + y) >= 7;
    }

    /**
     * get the Mhz of the device
     * @return the Mhz of the device
     */
    public static int getMaxCPUFreqMHz() {

        int maxFreq = -1;
        try {

            RandomAccessFile reader = new RandomAccessFile("/sys/devices/system/cpu/cpu0/cpufreq/stats/time_in_state", "r");

            while (true) {
                String line = reader.readLine();
                if (null == line) {
                    break;
                }
                String[] splits = line.split("\\s+");
                assert (splits.length == 2);
                int timeInState = Integer.parseInt(splits[1]);
                if (timeInState > 0) {
                    int freq = Integer.parseInt(splits[0]) / 1000;
                    if (freq > maxFreq) {
                        maxFreq = freq;
                    }
                }
            }

            reader.close();

        } catch (IOException ex) {
        }

        return maxFreq;
    }
    
    private static int getDeviceVersionAPI() {
        Class<?> c = Build.VERSION.class;
        int SDK_INT = 0;

        try {
            SDK_INT = c.getField("SDK_INT").getInt(c);
        } catch (IllegalArgumentException e) {
            //e.printStackTrace();
        } catch (IllegalAccessException e) {
            //e.printStackTrace();
        } catch (NoSuchFieldException e) {
            try {
                SDK_INT = Integer.parseInt((String) c.getField("SDK").get(c));
            } catch (Exception e1) {
                //e1.printStackTrace();
            }
        }
        return SDK_INT;
    }

    /**
     * Returns the android API Level (more reliable than getVersionPlatform())
     * @return 
     */
    public static int getVersionAPI() {
        return ms_iAndroidVersion;
    }
    
    /**
     * Returns the android version in "X.X" format (Ej: 2.4, 4.0, etcetc...). For a more reliable version use getVersionAPI instead.
     * @return android platform version
     */
    public static String getVersionPlatformString() {
        return ms_zAndroidVersion;
    }
    
    /**
     * Returns the android version in "X.X" format (Ej: 2.4, 4.0, etcetc...). For a more reliable version use getVersionAPI instead.
     * @return android platform version
     */
    public static double getVersionPlatform() {
        return ms_dAndroidVersion;
    }
    
    /**
     * Returns the android device OS
     * @return android device OS
     */
    public static int getDeviceOS() {
        return ms_iDeviceOS;
    }
    
    /**
     * A function to syncro with the android-J2ME wrapper more easily. if you you use this instead of the normal getResourcesAsStream you will be able to operate files distributed in the assets folder
     * @param _zFileName the name of the file to load
     * @return the input stream
     */
    public static InputStream getResourcesAsStream(String _zFileName) {
        if (_zFileName.startsWith("/"))
            _zFileName = _zFileName.substring(1);
        
        try {
            return MIDlet.ms_AssetManager.open(Settings.BITMAP_FOLDER[Settings.ms_iSize] + _zFileName + ".dat");
        } catch (IOException ex) {
            try {
                return MIDlet.ms_AssetManager.open("drawable-common/" + _zFileName + ".dat");
            } catch (IOException ex2) {
            }
        }
        return MIDlet.ms_Activity.getClass().getResourceAsStream(_zFileName);
    }
}
