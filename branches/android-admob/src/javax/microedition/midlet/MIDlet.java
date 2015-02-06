package javax.microedition.midlet;

import android.app.*;
import android.content.*;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Properties;
import javax.microedition.ActivityMain;
import javax.microedition.Settings;
import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.*;

public abstract class MIDlet {

    public static final String ANDROID_J2ME_VERSION = "v3.3.2";
    public static final String PROTOCOL_HTTP = "http://";
    public static final String PROTOCOL_HTTPS = "https://";
    public static final String PROTOCOL_SMS = "sms:";
    public static final String PROTOCOL_PHONE = "tel:";
    public static final String PROTOCOL_EMAIL = "email:";
    private android.view.Menu m_Menu;
    private Properties m_vPropierties = new Properties();
    private static MIDlet instance;
    public static Activity ms_Activity;
    public static AssetManager ms_AssetManager;
    public static SurfaceView mSurfaceView;
    private static View currentView;
    public static int ms_iSize;
    public static String midletName;

    static void startMidlet()
    {
        System.out.println("will start app");
        try {
            Class.forName(midletName).newInstance();
            getInstance().startApp();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void InitEngine(Activity _Activity, String pMidletName) {
        System.out.println("initing engine");
        midletName = pMidletName;
        ms_Activity = _Activity;
        ms_AssetManager = _Activity.getAssets();
        mSurfaceView = new CanvasView(_Activity);
//        Log.d("debug", "Midlet.InitEngine.Settings.initSettings - ini");
        Settings.initSettings(_Activity);
//        Log.d("debug", "Midlet.InitEngine.Settings.initSettings - end");
        setContentView(mSurfaceView);
        startMidlet();
    }

    public static void setContentView(View newCv) {
        if (newCv != currentView) {
            ms_Activity.setContentView(newCv);
        }
        currentView = newCv;
    }

    public static MIDlet getInstance() {
        return instance;
    }

    public MIDlet() {
        instance = this;
        openJAD();
    }

    private void openJAD() {
        String[] files = null;
        String zJADName = "";
        try {
            files = ms_AssetManager.list("");
        } catch (IOException e) {
            Log.e("openJAD", e.getMessage());
        }

        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].endsWith(".jad")) {
                    zJADName = files[i];
                    break;
                }
            }
        }

        if (zJADName.equals("")) {
            Log.w("openJAD", ".jad file not found. Add your J2ME game .jad file to the assets folder in case you use custom attributes");
        } else {
            try {
                int pos;
                StringBuilder sbJAD = new StringBuilder();
                // open JAD file
                InputStream isJad = ms_AssetManager.open(zJADName);
                InputStreamReader isrJad = new InputStreamReader(isJad);

                // read JAD file
                while ((pos = isrJad.read()) != -1) {
                    if ((char) pos == '\n') {
                        String zPropierty = sbJAD.toString();
                        if (zPropierty.contains(":")) {
                            int iSplit = zPropierty.indexOf(":");
                            String zName = zPropierty.substring(0, iSplit);
                            String zValue = zPropierty.substring(iSplit + 2, zPropierty.length() - 1);
                            m_vPropierties.setProperty(zName, zValue);
                        }
                        sbJAD.setLength(0);//clear the buffer
                    } else {
                        sbJAD.append((char) pos);
                    }
                }
                isrJad.close();
                isJad.close();
            } catch (Exception ex) {
            }
            }
        }

    public android.view.Menu getMenu() {
        return m_Menu;
    }

    static public void setMenu(android.view.Menu menu) {
        if (getInstance() != null) {
            getInstance().m_Menu = menu;
            if (getInstance().m_Menu != null && getInstance().m_Menu.size() > 0) {
                getInstance().m_Menu.clear();
        }

            Display display = Display.getDisplay(getInstance());
        Displayable current = display.getCurrent();

        // load the menu items
            if (current != null) {
            current.addCommandsToDisplay(display);
        }
    }
    }

    public Handler getHandler() {
        return Display.getDisplay(this).getCurrent().getView().getHandler();
    }

    protected abstract void destroyApp(boolean unconditional)
            throws MIDletStateChangeException;

    protected abstract void pauseApp()
            throws MIDletStateChangeException;

    static public void doPause() {
        try {
            if (getInstance() != null) {
                getInstance().pauseApp();
            }
        } catch (MIDletStateChangeException ex) {
        }
        if (Display.getDisplay(getInstance()).getCurrent() instanceof Canvas) {
            ((Canvas) Display.getDisplay(getInstance()).getCurrent()).pause();
        }
    }

    static public void doResume() {
        try {
            if (getInstance() != null) {
                getInstance().startApp();
            }
        } catch (MIDletStateChangeException ex) {
        }
        if (Display.getDisplay(getInstance()).getCurrent() instanceof Canvas) {
            ((Canvas) Display.getDisplay(getInstance()).getCurrent()).unpause();
        }
    }

    static public void doNotifyDestroyed() {

        if (getInstance() != null) {
            getInstance().notifyDestroyed();
        }
        }

    protected abstract void startApp()
            throws MIDletStateChangeException;

    public final void notifyDestroyed() {
        ms_Activity.finish();
        System.exit(0);
    }

    public boolean platformRequest(String url) throws ConnectionNotFoundException {
        Uri content = Uri.parse(url);
        String action;
        if (url.startsWith(PROTOCOL_PHONE)) {
            action = Intent.ACTION_DIAL;
        } else {
            action = Intent.ACTION_DEFAULT;
        }

        Intent intent = new Intent(action, content);
        ms_Activity.startActivity(intent);
        return false;
    }

    public String getAppProperty(String key) {
        if (key.equals("MIDlet-Version")) {
            // get application version
            try {
                return String.valueOf(ms_Activity.getPackageManager().getPackageInfo(ms_Activity.getPackageName(), 0).versionName);
            } catch (Exception ex) {
            }
            return "1.0";
        } else if (key.equals("AndroidJ2ME-Version")) {
            return ANDROID_J2ME_VERSION;
        } else if (key.equals("AndroidJ2ME-Size")) {
            return String.valueOf(Settings.ms_iSize);
        } else if (key.equals("AndroidJ2ME-Language")) {
            return Locale.getDefault().getLanguage() + "-" + Locale.getDefault().getISO3Country();
        } else {
            return m_vPropierties.getProperty(key);
        }
            }
}

class CanvasView extends SurfaceView implements SurfaceHolder.Callback {
        final static Object surfaceLock = new Object();

        public SurfaceHolder m_SurfaceHolder;
        public CanvasView(Context context) {
            super(context);
            System.out.println("canvas view created");
            SurfaceHolder holder = getHolder();
            holder.setFixedSize(Settings.getWidth(), Settings.getHeight());
        }

        // @Override
        public void onConfigurationChanged(Configuration newConfig) {
            // -- stomp any attempts by the OS to resize my view.
            // super.onConfigurationChanged(newConfig);
            getLayoutParams().width = (int) Settings.ms_iScreenWidth;
            getLayoutParams().height = (int) Settings.ms_iScreenHeight;
        }

        @Override
        public void onWindowFocusChanged(boolean hasWindowFocus) {
            if (Display.getDisplay(MIDlet.getInstance()).getCurrent() != null) {
                if (!hasWindowFocus) {
                    Display.getDisplay(MIDlet.getInstance()).getCurrent().pause();
                } else {
                    Display.getDisplay(MIDlet.getInstance()).getCurrent().unpause();
        }
        }
        }

        @Override
        public boolean onKeyDown(int keyCode, KeyEvent msg) {
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                keyCode = KeyEvent.KEYCODE_CLEAR;
            }

            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                keyCode = KeyEvent.KEYCODE_DPAD_CENTER;
            }

            if (keyCode == KeyEvent.KEYCODE_BACK) {
                keyCode = KeyEvent.KEYCODE_SOFT_LEFT;
            }

            if (keyCode == KeyEvent.KEYCODE_MENU
                    || keyCode == KeyEvent.KEYCODE_HOME
                    || keyCode == KeyEvent.KEYCODE_VOLUME_UP
                    || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
                return false;
            }

            Display.getDisplay(MIDlet.getInstance()).getCurrent().keyEventPressed(keyCode);
            return true;
        }

        @Override
        public boolean onKeyUp(int keyCode, KeyEvent msg) {
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                keyCode = KeyEvent.KEYCODE_CLEAR;
            }

            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                keyCode = KeyEvent.KEYCODE_DPAD_CENTER;
            }

            if (keyCode == KeyEvent.KEYCODE_BACK) {
                keyCode = KeyEvent.KEYCODE_SOFT_LEFT;
            }

            if (keyCode == KeyEvent.KEYCODE_MENU
                    || keyCode == KeyEvent.KEYCODE_HOME
                    || keyCode == KeyEvent.KEYCODE_VOLUME_UP
                    || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
                return false;
            }

            Display.getDisplay(MIDlet.getInstance()).getCurrent().keyEventReleased(keyCode);
            return true;
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            System.out.println("event: " + event);
            try {
                int action = event.getAction();
                //int pointerID = (event.getAction() & 65280) >> 8;

                if (action == MotionEvent.ACTION_DOWN) {
                    Display.getDisplay(MIDlet.getInstance()).getCurrent().pointerEventPressed(GetScaledX(event.getX()), GetScaledY(event.getY()));

                    return true;
                }
                if (action == MotionEvent.ACTION_MOVE) {
                    Display.getDisplay(MIDlet.getInstance()).getCurrent().pointerEventDragged(GetScaledX(event.getX()), GetScaledY(event.getY()));

                    return true;
                }
                if (action == MotionEvent.ACTION_UP) {
                    Display.getDisplay(MIDlet.getInstance()).getCurrent().pointerEventReleased(GetScaledX(event.getX()), GetScaledY(event.getY()));

                    return true;
                }
            } catch (Exception e) {
                System.out.println("ERROR: RECEIVED TOUCH BEFORE ENGINE IS INITIALIZED");
                System.out.println("MIDlet.getInstance(): " + MIDlet.getInstance());
                System.out.println("Display.getDisplay(MIDlet.getInstance()).getCurrent() = " + Display.getDisplay(MIDlet.getInstance()).getCurrent());
            }
            return false;
        }

        private int GetScaledX(float _fX) {
            return (int) (_fX * Settings.ms_fWidthPercent);
        }

        private int GetScaledY(float _fY) {
            return (int) (_fY * Settings.ms_fHeightPercent);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            if (Settings.ms_iDeviceOS == Settings.OS_BB10QNX) {
                MIDlet.ms_Activity.getWindow().setLayout(w, (int) Settings.ms_iScreenHeight);
            }
        }

        public void surfaceChanged(SurfaceHolder arg0, int format, int width, int height) {
            synchronized (surfaceLock) {
                if (MIDlet.getInstance() == null) {
                    MIDlet.startMidlet();
                }
                System.out.println("MIDlet.getInstance() = " + MIDlet.getInstance());
                System.out.println("Display.getDisplay(MIDlet.getInstance()) = " + Display.getDisplay(MIDlet.getInstance()));
                System.out.println("Display.getDisplay(MIDlet.getInstance()).getCurrent() = " + Display.getDisplay(MIDlet.getInstance()).getCurrent());
                Display.getDisplay(MIDlet.getInstance()).getCurrent().doSizeChanged(width, height);
            }
        }

        public void surfaceCreated(SurfaceHolder holder) {
            m_SurfaceHolder = holder;
            synchronized (surfaceLock) {
                System.out.println("open surface created");
                if (MIDlet.getInstance() == null) {
                    MIDlet.startMidlet();
                }
                holder.setFixedSize(Settings.getWidth(), Settings.getHeight());
            }
        }

        public void surfaceDestroyed(SurfaceHolder sh) {
            m_SurfaceHolder = null;
        }
    }
