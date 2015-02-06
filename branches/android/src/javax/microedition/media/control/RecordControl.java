package javax.microedition.media.control;

import javax.microedition.media.Control;
import javax.microedition.media.MediaException;

/**
 * 
 * <code>RecordControl</code> controls the recording of media from a
 * <code>Player</code>. <code>RecordControl</code> records what's currently
 * being played by the <code>Player</code>.
 * <p>
 * <h2>Example</h2>
 * <blockquote>
 * 
 * <pre>
 * try {
 * 	// Create a Player that captures live audio.
 * 	Player p = Manager.createPlayer(&quot;capture://audio&quot;);
 * 	p.realize();
 * 	// Get the RecordControl, set the record stream,
 * 	// start the Player and record for 5 seconds.
 * 	RecordControl rc = (RecordControl) p.getControl(&quot;RecordControl&quot;);
 * 	ByteArrayOutputStream output = new ByteArrayOutputStream();
 * 	rc.setRecordStream(output);
 * 	rc.startRecord();
 * 	p.start();
 * 	Thread.currentThread().sleep(5000);
 * 	rc.commit();
 * 	p.close();
 * } catch (IOException ioe) {
 * } catch (MediaException me) {
 * } catch (InterruptedException ie) {
 * }
 * </pre>
 * 
 * </blockquote>
 */
public interface RecordControl extends Control {

	void setRecordStream(java.io.OutputStream stream);

	void setRecordLocation(java.lang.String locator) throws java.io.IOException, MediaException;

	java.lang.String getContentType();

	void startRecord();

	void stopRecord();

	void commit() throws java.io.IOException;

	int setRecordSizeLimit(int size) throws MediaException;

	void reset() throws java.io.IOException;

}