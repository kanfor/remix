package javax.microedition.media.control;

import javax.microedition.media.Control;
import javax.microedition.media.MediaException;


public interface MIDIControl extends Control
{
  /**
   * Command value for Note On message (0x90, or 144).
   * To turn a note off, send a NOTE_ON message with 0
   * velocity. Alternatively, a Note Off message (0x80)
   * can be sent.
   * <P>
   * <DT><B>See Also:</B>
   * <A HREF="../../../../constant-values.html#de.enough.polish.android.media.control.MIDIControl.NOTE_ON">Constant Field Values</A></DL>
   * 
   */
  static final int NOTE_ON = 0x90;

  /**
   * Command value for Control Change message (0xB0, or 176).
   * <P>
   * <DT><B>See Also:</B>
   * <A HREF="../../../../constant-values.html#de.enough.polish.android.media.control.MIDIControl.CONTROL_CHANGE">Constant Field Values</A></DL>
   * 
   * 
   */
  static final int CONTROL_CHANGE = 0xB0;

  /**
   * Returns whether banks of the synthesizer can be queried.
   * <p>
   * If this functions returns true,
   * then the following methods can be used to query banks:
   * <ul>
   * <li><A HREF="../../../../de/enough/polish/android/media/control/MIDIControl.html#getProgram(int)"><CODE>getProgram(int)</CODE></A></li>
   * <li><A HREF="../../../../de/enough/polish/android/media/control/MIDIControl.html#getBankList(boolean)"><CODE>getBankList(boolean)</CODE></A></li>
   * <li><A HREF="../../../../de/enough/polish/android/media/control/MIDIControl.html#getProgramList(int)"><CODE>getProgramList(int)</CODE></A></li>
   * <li><A HREF="../../../../de/enough/polish/android/media/control/MIDIControl.html#getProgramName(int, int)"><CODE>getProgramName(int, int)</CODE></A></li>
   * <li><A HREF="../../../../de/enough/polish/android/media/control/MIDIControl.html#getKeyName(int, int, int)"><CODE>getKeyName(int, int, int)</CODE></A></li>
   * </ul>
   * <P>
   * 
   * 
   * @return true if this device supports querying of banks
   */
  boolean isBankQuerySupported();

  /**
   * Returns program assigned to channel. It represents the current
   * state of the channel. During playback of a MIDI file, the program
   * may change due to program change events in the MIDI file.<p>
   * To set a program for a channel,
   * use setProgram(int, int, int).<p>
   * 
   * The returned array is represented by an array {bank,program}.<p>
   * If the device has not been initialized with a MIDI file, or the MIDI file
   * does not contain a program change for this channel, an implementation
   * specific default value is returned.<p>
   * 
   * As there is no MIDI equivalent to this method, this method is
   * optional, indicated by <A HREF="../../../../de/enough/polish/android/media/control/MIDIControl.html#isBankQuerySupported()"><CODE>isBankQuerySupported</CODE></A>.
   * If it returns false, this function is not supported and throws an exception.
   * <P>
   * 
   * @param channel - 0-15
   * @return program assigned to channel, represented by array {bank,program}.
   * @throws java.lang.IllegalArgumentException - Thrown if channel is out of range.
   * @throws java.lang.IllegalStateException - Thrown if the player has not been prefetched.
   * @throws MediaException - Thrown if querying of banks is not supported.
   * @see #isBankQuerySupported()
   * @see #setProgram(int, int, int)
   */
  public int[] getProgram(int channel) throws MediaException;

  /**
   * Get volume for the given channel. The return value is
   * independent of the master volume, which is set and retrieved
   * with <A HREF="../../../../de/enough/polish/android/media/control/VolumeControl.html" title="interface in de.enough.polish.android.media.control"><CODE>VolumeControl</CODE></A>.<p>
   * 
   * As there is no MIDI equivalent to this method, the implementation
   * may not always know the current volume for a given channel. In
   * this case the return value is -1.
   * <P>
   * 
   * @param channel - 0-15
   * @return channel volume, 0-127, or -1 if not known
   * @throws java.lang.IllegalArgumentException - Thrown if channel is out of range.
   * @throws java.lang.IllegalStateException - Thrown if the player has not been prefetched.
   * @see #setChannelVolume(int, int)
   */
  public int getChannelVolume(int channel);

  /**
   * Set program of a channel. This sets the current program for the
   * channel and may be overwritten during playback by events in a MIDI sequence.<p>
   * It is a high level convenience function. Internally, these method calls are
   * executed:<p>
   * <code>
   * &nbsp;&nbsp;shortMidiEvent(CONTROL_CHANGE | channel, CONTROL_BANK_CHANGE_MSB, bank >> 7);<br>
   * &nbsp;&nbsp;shortMidiEvent(CONTROL_CHANGE | channel, CONTROL_BANK_CHANGE_LSB, bank & 0x7F);<br>
   * &nbsp;&nbsp;shortMidiEvent(PROGRAM_CHANGE | channel, program, 0);
   * </code><p>
   * 
   * In order to use the default bank (the initial bank), set the bank parameter to -1.
   * <p>
   * 
   * In order to set a program without explicitly setting the bank,
   * use the following call: <p>
   * <code>
   * &nbsp;&nbsp;shortMidiEvent(PROGRAM_CHANGE | channel, program, 0);
   * </code><p>
   * 
   * In both examples, the following constants are used:<p>
   * <code>
   * &nbsp;&nbsp;int PROGRAM_CHANGE = 0xC0;<br>
   * &nbsp;&nbsp;int CONTROL_BANK_CHANGE_MSB = 0x00;<br>
   * &nbsp;&nbsp;int CONTROL_BANK_CHANGE_LSB = 0x20;
   * </code><p>
   * <P>
   * 
   * @param channel - 0-15
   * @param bank - 0-16383, or -1 for default bank
   * @param program - 0-127
   * @throws java.lang.IllegalArgumentException - Thrown if any of the given parameters is out of range.
   * @throws java.lang.IllegalStateException - Thrown if the player has not been prefetched.
   * @see #getProgram(int)
   */
  public void setProgram(int channel, int bank, int program);

  /**
   * Set volume for the given channel. To mute, set to 0.
   * This sets the current volume for the
   * channel and may be overwritten during playback by events in a MIDI sequence.<p>
   * It is a high level convenience function. Internally, the following command
   * is executed:<p>
   * <code>
   * &nbsp;&nbsp;shortMidiEvent(CONTROL_CHANGE | channel, CONTROL_MAIN_VOLUME, 0);
   * </code><p>
   * where this constant is used:<p>
   * <code>&nbsp;&nbsp;int CONTROL_MAIN_VOLUME = 0x07</code><p>
   * 
   * The channel volume is independent of the master volume, which
   * is accessed with <A HREF="../../../../de/enough/polish/android/media/control/VolumeControl.html" title="interface in de.enough.polish.android.media.control"><CODE>VolumeControl</CODE></A>.
   * Setting the channel volume does not modify the value of the master
   * volume - and vice versa: changing the value of master volume does not
   * change any channel's volume value.<br>
   * The synthesizer
   * mixes the output of up to 16 channels, each channel with its own
   * channel volume. The master volume then controls the volume of the mix.
   * Consequently, the effective output volume of a channel is the product
   * of master volume and channel volume. <p>
   * 
   * Setting the channel volume does not generate a
   * <A HREF="../../../../de/enough/polish/android/media/PlayerListener.html#VOLUME_CHANGED"><CODE>VOLUME_CHANGED event</CODE></A>.
   * <P>
   * 
   * @param channel - 0-15
   * @param volume - 0-127
   * @throws java.lang.IllegalArgumentException - Thrown if channel or volume is out of range.
   * @throws java.lang.IllegalStateException - Thrown if the player has not been prefetched.
   * @see #getChannelVolume(int)
   */
  public void setChannelVolume(int channel, int volume);

  /**
   * Returns list of installed banks.
   * If the <code>custom</code> parameter is true, a list of custom banks is returned.
   * Otherwise, a list of all banks (custom and internal) is returned.
   * <p>
   * As there is no MIDI equivalent to this method, this method is
   * optional, indicated by <A HREF="../../../../de/enough/polish/android/media/control/MIDIControl.html#isBankQuerySupported()"><CODE>isBankQuerySupported</CODE></A>.
   * If it returns false, this function is not supported and throws an exception.
   * <P>
   * 
   * @param custom - if set to true, returns list of custom banks.
   * @return an array of all installed bank numbers. Each bank number is in the range of 0..16383
   * @throws MediaException - if this device does not support retrieval of banks
   * @throws java.lang.IllegalStateException - Thrown if the player has not been prefetched.
   * @see #isBankQuerySupported()
   */
  public int[] getBankList(boolean custom) throws MediaException;

  /**
   * Given bank, get list of program numbers. If and only if
   * this bank is not installed, an empty array is returned.<p>
   * 
   * As there is no MIDI equivalent to this method, this method is
   * optional, indicated by <A HREF="../../../../de/enough/polish/android/media/control/MIDIControl.html#isBankQuerySupported()"><CODE>isBankQuerySupported</CODE></A>.
   * If it returns false, this function is not supported and throws an exception.
   * <P>
   * 
   * @param bank - 0..16383
   * @return an array of programs defined in the given bank. Each program number is from 0..127.
   * @throws java.lang.IllegalArgumentException - Thrown if bank is out of range.
   * @throws MediaException - Thrown if the device does not support retrieval of programs.
   * @throws java.lang.IllegalStateException - Thrown if the player has not been prefetched.
   * @see #setProgram(int, int, int)
   * @see #isBankQuerySupported()
   */
  public int[] getProgramList(int bank) throws MediaException;

  /**
   * Given bank and program, get name of program.
   * For space-saving reasons, an implementation may return an empty string.
   * <p>
   * As there is no MIDI equivalent to this method, this method is
   * optional, indicated by <A HREF="../../../../de/enough/polish/android/media/control/MIDIControl.html#isBankQuerySupported()"><CODE>isBankQuerySupported</CODE></A>.
   * If it returns false, this function is not supported and throws an exception.
   * <P>
   * 
   * @param bank - 0-16383
   * @param prog - 0-127
   * @return name of the specified program, or empty string.
   * @throws java.lang.IllegalArgumentException - Thrown if bank or prog is out of range.
   * @throws MediaException - Thrown if the bank or program is not installed (internal or custom), or if this device does not support retrieval of program names
   * @throws java.lang.IllegalStateException - Thrown if the player has not been prefetched.
   * @see #isBankQuerySupported()
   */
  public java.lang.String getProgramName(int bank, int prog) throws MediaException;

  /**
   * Given bank, program and key, get name of key.
   * This method applies to key-mapped banks (i.e. percussive banks
   * or effect banks) only.
   * A return value of <code>null</code> means that the specified key
   * is not mapped to a sound. For melodic banks,
   * where each key (=note) produces the same sound at different pitch, this method
   * always returns <code>null</code>.
   * For space-saving reasons, an implementation may return an empty string
   * instead of the key name. To find out which keys in a specific program
   * are mapped to a sound, iterate through all keys (0-127) and compare
   * the return value of <code>getKeyName</code> to non-<code>null</code>.
   * <p>
   * As there is no MIDI equivalent to this method, this method is
   * optional, indicated by <A HREF="../../../../de/enough/polish/android/media/control/MIDIControl.html#isBankQuerySupported()"><CODE>isBankQuerySupported</CODE></A>.
   * If it returns false, this function is not supported and throws an exception.
   * <P>
   * 
   * @param bank - 0-16383
   * @param prog - 0-127
   * @param key - 0-127
   * @return name of the specified key, empty string, or null if the key is not mapped to a sound.
   * @throws java.lang.IllegalArgumentException - Thrown if bank, prog or key is out of range.
   * @throws MediaException - Thrown if the bank or program is not installed (internal or custom), or if this device does not support retrieval of key names
   * @throws java.lang.IllegalStateException - Thrown if the player has not been prefetched.
   * @see #isBankQuerySupported()
   */
  public java.lang.String getKeyName(int bank, int prog, int key) throws MediaException;

  /**
   * Sends a short MIDI event to the device.
   * Short MIDI events consist of 1, 2, or 3 unsigned bytes.
   * For non-realtime events, the first byte is split up into
   * status (upper nibble, 0x80-0xF0) and channel (0x00-0x0F).
   * For example, to send a <code>Note On</code> event on a given channel,
   * use this line:<p>
   * <code>&nbsp;&nbsp;shortMidiEvent(NOTE_ON | channel, note, velocity);</code><p>
   * For events with less than 3 bytes, set the remaining data bytes to 0.<p>
   * 
   * There is no guarantee that a specific
   * implementation of a MIDI device supports all event types.
   * Also, the MIDI protocol does not implement flow control and it is not
   * guaranteed that an event reaches the destination.
   * In both these cases, this method fails silently. <p>
   * 
   * Static error checking is performed on the passed parameters. They have to
   * specify a valid, complete MIDI event. Events with <code>type</code> &lt; 0x80 are
   * not valid MIDI events (-&gt; running status). When an invalid event
   * is encountered, an IllegalArgumentException is thrown.
   * <P>
   * 
   * @param type - 0x80..0xFF, excluding 0xF0 and 0xF7, which are reserved for system exclusive
   * @param data1 - for 2 and 3-byte events: first data byte, 0..127
   * @param data2 - for 3-byte events: second data byte, 0..127
   * @throws java.lang.IllegalArgumentException - Thrown if one of the parameters is out of range.
   * @throws java.lang.IllegalStateException - Thrown if the player has not been prefetched.
   */
  public void shortMidiEvent(int type, int data1, int data2);

  /**
   * Sends a long MIDI event to the device, typically a system exclusive message.
   * This method passes the data directly to the receiving device.
   * The data array's contents are not checked for validity.<p>
   * It is possible to send short events, or even a series of short events
   * with this method.<p>
   * <P>
   * 
   * @param data - array of the bytes to send
   * @param offset - start offset in data array
   * @param length - number of bytes to be sent
   * @return the number of bytes actually sent to the device or -1 if an error occurred
   * @throws java.lang.IllegalArgumentException - Thrown if any one of the given parameters is not valid.
   * @throws java.lang.IllegalStateException - Thrown if the player has not been prefetched.
   */
  public int longMidiEvent(byte[] data, int offset, int length);

}