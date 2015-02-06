package javax.microedition.media;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.microedition.media.control.VolumeControl;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import javax.microedition.midlet.MIDlet;

public class SoundPlayer implements Player, VolumeControl, Controllable {

	private MediaPlayer m_MediaPlayer;
	private String m_zContentType;
	private int m_iState;
	private boolean m_bPause;

	public static final String MEDIA_TEMP_FILE = "temp";
	private static int tempCount = 0;
	
	public SoundPlayer (InputStream _is, String _zType) throws IOException, MediaException {
		
		m_zContentType = _zType;
	    if (_is == null)
	      throw new NullPointerException();
	    
	    String typeName = "";
	    if (m_zContentType.indexOf("mid") != -1)
	      typeName = ".mid";
	    else if (m_zContentType.indexOf("mpeg") != -1)
	      typeName = ".mp3";
	    else if (m_zContentType.indexOf("amr") != -1)
	      typeName = ".amr";
	    else if (m_zContentType.indexOf("wav") != -1)
	      typeName = ".wav";
            else if (m_zContentType.indexOf("ogg") != -1)
                typeName = ".ogg";	    
            else
	      return;
	    
	    FileOutputStream temp = MIDlet.ms_Activity.openFileOutput(MEDIA_TEMP_FILE + tempCount + typeName, Context.MODE_WORLD_READABLE);
		
		byte buf[] = new byte[256];
		do {
			int numread = _is.read(buf);
			if (numread <= 0)
				break;
			temp.write(buf, 0, numread);
		} while (true);
		
		temp.flush();
		temp.close();
	    
	    try {
	    	m_MediaPlayer = new MediaPlayer();
	    	m_MediaPlayer.setDataSource(MIDlet.ms_Activity.getApplication().getFilesDir().getAbsolutePath() + 
                 File.separator + MEDIA_TEMP_FILE + tempCount + typeName);
	    	m_MediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			m_MediaPlayer.prepare();
	    	
	    	tempCount++;
	    	if (tempCount >= 20)
	    		tempCount = 0;
	      
	    } catch (Exception e) {
	      throw new MediaException();
	    }		
	}
	
//	@Override
	public Control getControl(String controlType) {
		return this;
	}

//	@Override
	public Control[] getControls() {
		return null;
	}

//	@Override
	public void addPlayerListener(PlayerListener playerListener) {
		// TODO Auto-generated method stub
		
	}

//	@Override
	public void close() {
		if (m_MediaPlayer != null)
   	 		m_MediaPlayer.release();
		
   	 	m_iState = Player.CLOSED;   	 	
	}

//	@Override
	public void deallocate() {
		if (m_MediaPlayer != null)
   	 		m_MediaPlayer.release();
		
   	 	m_iState = Player.CLOSED;		
	}

//	@Override
	public String getContentType() {
		return m_zContentType;
	}

//	@Override
	public long getDuration() {
		return m_MediaPlayer.getDuration();
	}

//	@Override
	public long getMediaTime() {
		return m_MediaPlayer.getCurrentPosition();
	}

//	@Override
	public int getState() {
		return (m_iState == Player.STARTED ? (!m_MediaPlayer.isPlaying() ? Player.PREFETCHED : m_iState) : m_iState);
	}

//	@Override
	public void prefetch() {
		m_iState = Player.PREFETCHED;
	}

//	@Override
	public void realize() {
		m_iState = Player.REALIZED;
	}

//	@Override
	public void removePlayerListener(PlayerListener playerListener) {
		// TODO Auto-generated method stub
		
	}

//	@Override
	public void setLoopCount(int count) {
		m_MediaPlayer.setLooping(count!=1);
	}

//	@Override
	public long setMediaTime(long now) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void start() {

		if (m_MediaPlayer.isPlaying())
			m_MediaPlayer.seekTo(0);
		else
			m_MediaPlayer.start();
		
		m_iState = Player.STARTED;
	}

	public void stop() {
		if (m_MediaPlayer.isPlaying()) {
			m_MediaPlayer.pause();
			m_MediaPlayer.seekTo(0);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
   	 	m_iState = Player.REALIZED;
	}
	
	public void pause() {
		if (m_MediaPlayer.isPlaying() && !m_bPause) {
			m_MediaPlayer.pause();
			m_bPause = true;
		}
	}

	public void unpause() {
		if (m_bPause) {
			m_MediaPlayer.start();
			m_bPause = false;
		}
	}
	
//	@Override
	public void setSequence(byte[] sequence) {

	}

	@Override
	public int getLevel() {
            // TODO Auto-generated method stub
            return 100;
	}
	
	@Override
	public boolean isMuted() {
            // TODO Auto-generated method stub
            return false;
	}
	
	@Override
	public int setLevel(int level) {
            float fVolume = (float)level/100;
            m_MediaPlayer.setVolume(fVolume, fVolume);
            return level;
	}
	
	@Override
	public void setMute(boolean mute) {
            // TODO Auto-generated method stub
		
	}

}
