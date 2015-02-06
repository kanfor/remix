package javax.microedition;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import com.kitmaker.ads.AdsServingActivity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.microedition.midlet.MIDlet;

public class ActivityMain extends AdsServingActivity {


    ////////////////////////////////////////////////////////////////////////////
    /**/ final String className = "com.kitmaker.finalkombat.MidletFinalKombat";           /**/
    ////////////////////////////////////////////////////////////////////////////

    // Android objects
    private static Menu ms_vMenu;
    
    // J2ME objects
    private boolean ms_bOptionsMenuActive = false;
    /////////////////////////////////////////////////////////////////////////////
    //                                                                         //
    /////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        ms_bOptionsMenuActive = true;

        ms_vMenu = menu;
        setMidletMenu(menu);

        return true;
    }

    @Override
    public boolean onMenuOpened(int _iId, Menu menu)
    {
        super.onMenuOpened(_iId, menu);
        if (menu.size() > 0) {
            MIDlet.doPause();
        }
        return true;
    }
    
    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
        ms_bOptionsMenuActive = false;
        if (!disableResumeOnOptionMenuClosed) {
            MIDlet.doResume();
        }
    }

    /**
     * Invoked when the Activity is created.
     * <p/>
     * @param savedInstanceState a Bundle containing state saved from a previous execution, or null if this is a new execution
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("debug", "ActivityMain.onCreate - ini");
        super.onCreate(savedInstanceState);
        if (!this.getClassLoader().toString().equals(CustomClassLoader.OBFUSCATED_CLASSNAME)) {
            CustomClassLoader loader = new CustomClassLoader(this.getPackageCodePath(), this.getAssets());
            Class<?> appClass;
            try {
                appClass = loader.loadClass("javax.microedition.StarterActivity");
                Intent myIntent = new Intent(getApplicationContext(), appClass);

                startActivity(myIntent);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(StarterActivity.class.getName()).log(Level.SEVERE, null, ex);
            }
            finish();
            return;
        }

        // turn off the window's title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // snd hack to prevent the music to not be played in case that media sound starts at 0
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) == 0) {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 1, 0);
        }

        if (!Settings.SCREEN_SLEEP) {
            Settings.ms_PowerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
            Settings.ms_WakeLock = Settings.ms_PowerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        }

        Log.d("debug", "ActivityMain.onCreate.Midlet.InitEngine - ini");
        try {
            MIDlet.InitEngine(this, className);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.d("debug", "ActivityMain.onCreate.Midlet.InitEngine - end");

        setMidletMenu(ms_vMenu);
        ms_vMenu = null;
        
        Log.d("debug", "ActivityMain.onCreate - end");
    }

    void setMidletMenu(Menu menu) {
        try {
            MIDlet.setMenu(menu);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        MIDlet.doNotifyDestroyed();
        super.onDestroy();
    }
    //public static boolean ms_bPause = false;
    /**
     * Invoked when the Activity loses user focus.
     */
    private boolean disableResumeOnOptionMenuClosed = false;

    @Override
    protected void onPause() {
        if (ms_bOptionsMenuActive) {
            disableResumeOnOptionMenuClosed = true;
        }
        if (!Settings.SCREEN_SLEEP && Settings.ms_WakeLock != null) {
            Settings.ms_WakeLock.release();
        }
        
        // blackberry Q5/Q10 doesn't call onWindowFocusChanged, so we need this hack
        if (Settings.ms_iDeviceOS == Settings.OS_BB10QNX)
            MIDlet.doPause();
        
        super.onPause();
    }

    @Override
    protected void onResume() {
        disableResumeOnOptionMenuClosed = false;
        if (!Settings.SCREEN_SLEEP && Settings.ms_WakeLock != null) {
            Settings.ms_WakeLock.acquire();
        }
        
        // blackberry Q5/Q10 doesn't call onWindowFocusChanged, so we need this hack
        if (Settings.ms_iDeviceOS == Settings.OS_BB10QNX)
            MIDlet.doResume();
            
        super.onResume();
    }
    
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        if (hasFocus) {
            MIDlet.doResume();
        } else {
            MIDlet.doPause();
        }

        super.onWindowFocusChanged(hasFocus);
    }
}
