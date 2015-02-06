package javax.microedition;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An activity which is used to setup the CustomClassLoader and
 * to avoid the issues that appear if the same is done in ActivityMain
 *
 * @author Ivaylo Kasaivanov
 */
public class StarterActivity extends Activity {

    /**
     * Called when the activity is first created.
     *
     * @param icicle
     */
    @Override
    public void onCreate(Bundle icicle) {
        Log.d("debug", "StarterActivity.onCreate - ini");
        super.onCreate(icicle);
        Settings.initSettings(this);
        CustomClassLoader loader = new CustomClassLoader(this.getPackageCodePath(), this.getAssets());
        setAPKClassLoader(loader);
        Class<?> appClass;
        try {
            appClass = loader.loadClass("javax.microedition.ActivityMain");
            Intent myIntent = new Intent(getApplicationContext(), appClass);

            startActivity(myIntent);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StarterActivity.class.getName()).log(Level.SEVERE, null, ex);
        }
        finish();
        Log.d("debug", "StarterActivity.onCreate - end");
    }

    private void setAPKClassLoader(ClassLoader loader) {
        try {
            Field mMainThread = getField(Activity.class, "mMainThread");
            Object mainThread = mMainThread.get(this);
            Class threadClass = mainThread.getClass();
            Field mPackages = getField(threadClass, "mPackages");

            Map<String, ?> map = (Map<String, ?>) mPackages.get(mainThread);
            WeakReference<?> ref = (WeakReference<?>) map.get(getPackageName());
            Object apk = ref.get();
            Class apkClass = apk.getClass();
            Field mClassLoader = getField(apkClass, "mClassLoader");

            mClassLoader.set(apk, loader);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private Field getField(Class<?> cls, String name) {
        for (Field field : cls.getDeclaredFields()) {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            if (field.getName().equals(name)) {
                return field;
            }
        }
        return null;
    }

}
