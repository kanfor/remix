package com.kitmaker.finalkombat;
/*
 * SndManager.java
 *
 * Created on 23 de octubre de 2006, 10:46
 */

////////////////////////////////////////////////////////////////
//
// END OF PREPROCESSOR BLOCKS
//
////////////////////////////////////////////////////////////////
import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.content.Context;

public class SndManager extends Activity {
   
   static final boolean SOUND_SUPPORTED = true;
   static boolean ms_bSound;

   public SndManager() {
   }

   private static AudioManager  ms_vAudioManager;
   private static Context ms_vContext;

   static final byte FX_NOSOUND = 0;
   static final byte FX_GOLPE = 1;
   static final byte FX_MUERTE = 2;
   static final byte FX_BOMBADESAC = 3;
   static final byte FX_SELECT = 4;
   static final byte NUM_FXS = 5;
   private static SoundPool ms_vFXPool;
   
   static final byte MUSIC_NOSOUND = -1;
   static final byte MUSIC_FINISHLEVEL = 0;
   static final byte MUSIC_GAMEPLAY1 = 1;
   static final byte MUSIC_GAMEPLAY2 = 2;
   static final byte MUSIC_GAMEPLAY3 = 3;
   static final byte MUSIC_MAINMENU = 4;
   static final byte SFX_BUTTON = 5;
   static final byte SFX_DESARMARBOMBA = 6;
   static final byte SFX_ENEMIGOMUERE = 7;
   static final byte SFX_GOLPENORMAL = 8;
   static final byte SFX_BONG = 9;
   static final byte SFX_EXPLOSION = 10;
   static final byte MUSIC_ENDING = 11;
   private static MediaPlayer ms_vMusicPlayer;
   
   public static int ms_iCurrentClip;
   public static float ms_fVolume;
   private static final int MUSIC_FILE [] = {
	   
       R.raw.finfase,
       R.raw.playing,
       R.raw.playing2,
       R.raw.playing3,
       R.raw.intromenu,
       R.raw.bong,
       R.raw.finalkombat_sfx_desarmarbomba,
       R.raw.finalkombat_sfx_enemigomuere,
       R.raw.finalkombat_sfx_golpenormal,
       R.raw.bong,
       R.raw.explosion,
       R.raw.ending,
       
   };
   
   // add hear sound effects to mantain them always loaded in memory
   static void InitSndManager(Context _vContext) {

      try {
         ms_vContext = _vContext;
	     ms_vAudioManager = (AudioManager) ms_vContext.getSystemService(Context.AUDIO_SERVICE);
	     
         float streamVolume = ms_vAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
         ms_fVolume = streamVolume / ms_vAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	     
         ms_vFXPool = new SoundPool(NUM_FXS, AudioManager.STREAM_MUSIC, 0);
         ms_vFXPool.load(ms_vContext.getApplicationContext(), R.raw.pomwavsimple, 1);
         ms_vFXPool.load(ms_vContext.getApplicationContext(), R.raw.pomwav, 1);
         ms_vFXPool.load(ms_vContext.getApplicationContext(), R.raw.desacbomba, 1);
         ms_vFXPool.load(ms_vContext.getApplicationContext(), R.raw.fxselect, 1);
         
      } catch (Exception ex) {
      }
   }


   static void PlayMusic(int _iMusicID, boolean _bLoop) {

	  if (ms_bSound) {
		 		  		 
		 try {
			System.out.println("Poniendo música");
			// if the player is already playing the song returns. if is playing other song it stops
			if (ms_vMusicPlayer != null) {
			   if (ms_iCurrentClip == _iMusicID && ms_vMusicPlayer.isPlaying())
				  return;

			   StopMusic();
			}
			
			// create, volume and finally plays a wonderful song
			ms_vMusicPlayer = MediaPlayer.create(ms_vContext, MUSIC_FILE[_iMusicID]);
            ms_vMusicPlayer.setLooping(_bLoop);
            ms_vMusicPlayer.setVolume(ms_fVolume, ms_fVolume);
    		ms_vMusicPlayer.start();

    		ms_iCurrentClip = _iMusicID;
    		
		 } catch (Exception ex) {
			ms_iCurrentClip = MUSIC_NOSOUND;
		 }
	  }	  
   }	   

   static void PauseMusic() {
	  try {
    	 if (ms_vMusicPlayer != null)
    		ms_vMusicPlayer.pause();
	  } catch (Exception ex) {}
   }

   static void UnpauseMusic() {
	  try {
    	 if (ms_vMusicPlayer != null)
    		ms_vMusicPlayer.start();
	  } catch (Exception ex) {}
   }
   
   static void StopMusic() {
	  
	  try {

    	 if (ms_vMusicPlayer != null) {
    		ms_vMusicPlayer.stop();
    		ms_vMusicPlayer.reset();
      	 }
	     ms_iCurrentClip = MUSIC_NOSOUND;
	     
	  } catch (Exception ex) {}
   }
   
   static void PlayFX(int _iFXID) {
      if (ms_bSound && _iFXID > 0) {
         ms_iCurrentClip = _iFXID;
         
         try {
           System.out.println("FX cargando");
           float streamVolume = ms_vAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
           streamVolume = streamVolume / ms_vAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
           ms_vFXPool.play(_iFXID, streamVolume, streamVolume, 1, 0, 1f);

         } catch (Exception ex) {
         }
      }
   }
   
   static void FlushSndManager () {
      try {
    	 ms_vFXPool.release();
    	 ms_vFXPool = null;
    	 ms_vMusicPlayer = null;
      } catch (Exception ex) {
      }
   }
}