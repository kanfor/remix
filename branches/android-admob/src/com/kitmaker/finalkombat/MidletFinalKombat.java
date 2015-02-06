package com.kitmaker.finalkombat;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;

/**
 * @author josemariaclimentmartinez
 */
public class MidletFinalKombat extends MIDlet {


    public static MidletFinalKombat midlet;
    //Run
    
    public static MIDlet ms_vMIDlet;
    public static SuperCanvas ms_vMain;
    public static Display ms_vDisplay;
    public static Thread ms_vThread;
    //

    public void startApp() {

        if (SuperCanvas.gameStarted == false) {
            midlet = this;    //Muy importante.
            
            //Run         
            ms_vMain = new SuperCanvas();
            ms_vDisplay = Display.getDisplay(midlet);
            ms_vDisplay.setCurrent(ms_vMain);
        
            ms_vThread = new Thread (ms_vMain);
            ms_vThread.start ();
            SuperCanvas.gameStarted = true;
         
         //
         
        } else {
            ms_vMain.showNotify();
        }
        
    }
    
    public void pauseApp() {
        ms_vMain.hideNotify();
    }
    
  public static void quitApp()
  {
    midlet.destroyApp(true);
  }
    
  public void destroyApp(boolean unconditional) {
    System.out.println("SALIENDO");
    //superCanvas.stopMusica();
    System.gc();
    notifyDestroyed();
    midlet = null;
  }
    
    
}
