/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kitmaker.finalkombat;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

import javax.microedition.lcdui.Canvas;

import com.kitmaker.finalkombat.R;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
//import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

/**
 * This is a simple Main activity that houses a single Game. It
 * demonstrates...
 * <ul>
 * <li>animating by calling invalidate() from draw()
 * <li>loading and drawing resources
 * <li>handling onPause() in an animation
 * </ul>
 */
public class ActivityMain extends Activity {

	// Android objects
	public static ActivityMain ms_vMain;
	public static Context ms_Context;
   
	// J2ME objects
    public static MidletFinalKombat ms_MIDlet;
    public static Canvas ms_Canvas;
   
    // Resources
    public static Resources ms_Resources;
    public static AssetManager ms_AssetManager;
    
    
    //////////////////////////////////////////////////////////////////////////////
    // Preferences 																//
    //////////////////////////////////////////////////////////////////////////////
    
    // Image folder
    public static final int FOLDER_RESOURCES = 0;
    public static final int FOLDER_ASSETS = 1;
    public static int BITMAP_FOLDER = FOLDER_ASSETS;
    public static final String BITMAP_FOLDER_NAME = "drawable";
    
    // Screen size
    public static final int QVGA_240X320 = 0;
	public static final int HVGA_320X480 = 1;
	public static final int WVGA_480X800 = 2;
	public static final int WXGA_800X1280 = 3;
	public static final int VGA_480x640 = 4;
	public static final int ms_iSize = HVGA_320X480;
	public static final int[][] SCREEN_SIZES = { 
		{ 240, 320 }, 
		{ 320, 480 },
		{ 480, 800 }, 
		{ 800, 1280 }, 
		{ 480, 640 }, 
	};
	

	// Image drawing
	public static final boolean DRAW_ANTIALIASING = true;
    
	
    //////////////////////////////////////////////////////////////////////////////
    // 			 																//
    //////////////////////////////////////////////////////////////////////////////
    private static final int MENU_RESUME = 0;
   	private static final int MENU_STOP = 1;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        pause();

        menu.add(0, MENU_RESUME, 0, R.string.menu_resume);
        menu.add(0, MENU_STOP, 0, R.string.menu_stop);

        return true;
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
        unpause();
    }

    /**
     * Invoked when the user selects an item from the Menu.
     * 
     * @param item the Menu entry which was selected
     * @return true if the Menu item was legit (and we consumed it), false
     *         otherwise
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_RESUME:
            	unpause();
            	return true;
            	
            case MENU_STOP:
            	SndManager.FlushSndManager();
            	ms_MIDlet.notifyDestroyed();
            	return true;
        }

        return false;
    }

    /**
     * Invoked when the Activity is created.
     * 
     * @param savedInstanceState a Bundle containing state saved from a previous
     *        execution, or null if this is a new execution
     */
    
    // configuration
    static int ms_iKeyboardType;
    static int ms_iKeyboardState;
    static int ms_iScreenTouchType;
    static int ms_iNavigationPadType;
    public static String ms_zLanguage;
    public static String ms_zAppVersion;
	public static String ms_zDeviceID = "";    
    
    public static float ms_fCanvasWidth;
    public static float ms_fCanvasHeight;
    public static float ms_fWidthPercent;
    public static float ms_fHeightPercent;

	public static void setCanvas (Canvas _vCanvas) {
		ms_Canvas = _vCanvas;
	}
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate( savedInstanceState );
		
        ms_vMain = this;
        
        // turn off the window's title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // hardware info
        Configuration vConfig = new Configuration();
        vConfig = getResources().getConfiguration();
        ms_iKeyboardType = Configuration.KEYBOARD_NOKEYS;//vConfig.keyboard;
        ms_iScreenTouchType = vConfig.touchscreen;
        ms_iNavigationPadType = vConfig.navigation;
        ms_zDeviceID = getDeviceID();

        // get device language
        ms_zLanguage = Locale.getDefault().getLanguage(); //Locale.getDefault().getDisplayLanguage();
        /*
        if (locale.getLanguage().equals("fr")) {language = Language.FRENCH;
		if (locale.getLanguage().equals("zh")) {language = Language.CHINESE;
		if (locale.getLanguage().equals("it")) {language = Language.ITALIAN;
		if (locale.getLanguage().equals("ko")) {language = Language.KOREAN;
		if (locale.getLanguage().equals("nl")) {language = Language.DUTCH;
		if (locale.getLanguage().equals("pl")) {language = Language.POLISH;
		if (locale.getLanguage().equals("ru")) {language = Language.RUSSIAN;
		if (locale.getLanguage().equals("th")) {language = Language.THAI;
		if (locale.getLanguage().equals("cs")) {language = Language.CZECH;
        */
        
        
        // get application version
        try {
        	PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
        	ms_zAppVersion = String.valueOf(info.versionName);
        } catch (Exception ex) {}
    	
        
        // screen data
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        ms_fCanvasWidth = dm.widthPixels;
        System.out.println("dm.widthPixels "+dm.widthPixels);
        ms_fCanvasHeight = dm.heightPixels;
        ms_fWidthPercent = SCREEN_SIZES[ms_iSize][0]/ms_fCanvasWidth;
        ms_fHeightPercent = SCREEN_SIZES[ms_iSize][1]/ms_fCanvasHeight;
        
        /* new code */
        ms_Context = this;
        ms_Resources = ms_Context.getResources();
        ms_AssetManager = getResources().getAssets();
        /**/
       
        
        // Open MIDlet class
		openJAD ();
		ms_MIDlet = new MidletFinalKombat();
		ms_MIDlet.startApp();
	}
    

    private static String[] ms_zJAD;	
	private static void openJAD () {
        String[] files = null;
        String zJADName = "";
        try {
            files = ms_AssetManager.list("");
        } catch (IOException e) {
            Log.e("openJAD", e.getMessage());
        }
        
        if (files != null) {
        	for (int i=0; i<files.length; i++) {
        		if (files[i].endsWith(".jad")) {
        			zJADName = files[i];
        			break;
        		}
        	}
        }
        
        if (zJADName.equals(""))
            Log.e("openJAD", ".jad file not found. Please add your J2ME game jad file to the assets folder");
        	
        
        files = null;
        int pos, lines = 0;
        StringBuffer[] sbJAD = new StringBuffer[30];
        sbJAD[0] = new StringBuffer();
        
        try {
	        if (!zJADName.equals("")) {
	        	
	        	// open JAD file
	        	InputStream isJad = ms_AssetManager.open(zJADName);
	        	InputStreamReader isrJad = new InputStreamReader(isJad);
	        	
	        	// read JAD file
	            while ((pos = isrJad.read()) != -1) {
	                if ((char) pos == '\n') {
	                    lines++;
	                    sbJAD[lines] = new StringBuffer();
	                } else {
	                	sbJAD[lines].append((char) pos);
	                }
	            }
	            
	            // create JAD file data
	            ms_zJAD = new String[lines];
	            for (byte i = 0; i < lines; i++) {
	            	ms_zJAD[i] = sbJAD[i].toString();
	            	ms_zJAD[i] = ms_zJAD[i].substring(0, ms_zJAD[i].length()-1);
	                sbJAD[i] = null;
	            }
	        }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	

	public static boolean ms_bPause = false;
	
    /**
     * Invoked when the Activity loses user focus.
     */
    @Override
    protected void onPause() {
        super.onPause();
        pause();
    }
    
    public void pause () {
        ms_bPause = true;
		SndManager.PauseMusic();
        if (ms_Canvas != null)
        	ms_Canvas.pause();    	
    }

    /**
     * Invoked when the Activity loses user focus.
     */

    @Override
    protected void onResume() {
        super.onResume();
        unpause();
    }
    
    public void unpause () {
        ms_bPause = false;
 		SndManager.UnpauseMusic();
         if (ms_Canvas != null)
         	ms_Canvas.unpause();    	
    }

    
    /**
     * Notification that something is about to happen, to give the Activity a
     * chance to save state.
     * 
     * @param outState a Bundle into which this Activity should save its state
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // just have the View's thread save its state into our Bundle
        super.onSaveInstanceState(outState);
        Log.w(this.getClass().getName(), "SIS called");
    }


   static final String STORE_NAME_PLY = "game_data";
   static final String STORE_NAME_SYS = "system_data";
   static final String STORE_NAME_REC = "record_data";

   static final int SAVE_PLY = 0;
   static final int SAVE_SYS = 1;
   static final int SAVE_REC = 2;

   static final int LOAD_PLY = 0;
   static final int LOAD_SYS = 1;
   static final int LOAD_REC = 2;

   void LoadData (int _iTarget) {

	   /*
      //////////////////////////////////////////////////////////////////////////
      if (_iTarget == LOAD_PLY) {

         SharedPreferences vGameData = getSharedPreferences(STORE_NAME_PLY, 0);
         ModeMenu.ms_iHiStage = vGameData.getInt("stage", 0);
         vGameData = null;

      //////////////////////////////////////////////////////////////////////////
      } else if (_iTarget == LOAD_SYS) {

         SharedPreferences vGameData = getSharedPreferences(STORE_NAME_SYS, 0);
         Game.ms_bVibration = vGameData.getBoolean("vibration", false);
         vGameData = null;
         
      //////////////////////////////////////////////////////////////////////////
      } else if (_iTarget == LOAD_REC) {
         SharedPreferences vGameData = getSharedPreferences(STORE_NAME_REC, 0);
         for (int i=0; i<ModeMenu.NUM_SCORETABLES; i++) {
            for (int j=0; j<ModeMenu.NUM_SCORES; j++) {
               ModeMenu.ms_iScoreChar[i][j] = vGameData.getInt("b"+i+"_"+j, ModeMenu.ms_iScoreChar[i][j]);
               ModeMenu.ms_iScores[i][j] = vGameData.getInt("i"+i+"_"+j, ModeMenu.ms_iScores[i][j]);
               ModeMenu.ms_zScores[i][j] = vGameData.getString("s"+i+"_"+j, ModeMenu.ms_zScores[i][j]);
            }
         }
         vGameData = null;
         
      }*/
   }

   void SaveData (int _iTarget) {
	   /*
      //////////////////////////////////////////////////////////////////////////
      if (_iTarget == SAVE_PLY) {

         SharedPreferences vGameData = getSharedPreferences(STORE_NAME_PLY, 0);
         SharedPreferences.Editor vDataEditor = vGameData.edit();
         vDataEditor.putInt("stage", ModeMenu.ms_iHiStage);
         vDataEditor.commit();

      //////////////////////////////////////////////////////////////////////////
      } else if (_iTarget == SAVE_SYS) {

         SharedPreferences vGameData = getSharedPreferences(STORE_NAME_PLY, 0);
         SharedPreferences.Editor vDataEditor = vGameData.edit();
         vDataEditor.putBoolean("vibration", Game.ms_bVibration);
         vDataEditor.commit();

      //////////////////////////////////////////////////////////////////////////
      } else if (_iTarget == SAVE_REC) {
         SharedPreferences vGameData = getSharedPreferences(STORE_NAME_REC, 0);
         SharedPreferences.Editor vDataEditor = vGameData.edit();
         for (int i=0; i<ModeMenu.NUM_SCORETABLES; i++) {
            for (int j=0; j<ModeMenu.NUM_SCORES; j++) {
               vDataEditor.putInt("b"+i+"_"+j, ModeMenu.ms_iScoreChar[i][j]);
               vDataEditor.putInt("i"+i+"_"+j, ModeMenu.ms_iScores[i][j]);
               vDataEditor.putString("s"+i+"_"+j, ModeMenu.ms_zScores[i][j]);
            }
         }
         vDataEditor.commit();
         
      }
      */
   }
   
	
   private String getDeviceID () {
		
	   String zDeviceID = "";
	   try {
		   // get phone ID
		   TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		   zDeviceID = tManager.getDeviceId();
		   
		   // get android ID
		   if (zDeviceID == null || zDeviceID.equals(""))
			   zDeviceID = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
        
	   } catch (Exception ex) {}
	   
	   try {
	   
		   // get pseudo-unique ID
		   if (zDeviceID == null || zDeviceID.equals("")) {
			   zDeviceID = "3579" + //we make this look like a valid IMEI
					   Build.BOARD.length()%10+ Build.BRAND.length()%10 + Build.DEVICE.length()%10 + 
					   Build.DISPLAY.length()%10 + Build.HOST.length()%10 + Build.ID.length()%10 +
					   Build.MODEL.length()%10 + Build.PRODUCT.length()%10 + Build.TAGS.length()%10 + 
					   Build.TYPE.length()%10 + Build.USER.length()%10 ; //11 digits
		   }
	   } catch (Exception ex) {}
        
	   return zDeviceID;
   }
}
