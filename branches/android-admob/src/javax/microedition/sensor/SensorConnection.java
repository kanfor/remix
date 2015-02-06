package javax.microedition.sensor;

import java.io.*;
import javax.microedition.io.*;

public interface SensorConnection extends Connection
{
    public static final int STATE_OPENED = 1;
    public static final int STATE_LISTENING = 2;
    public static final int STATE_CLOSED = 4;

    public Channel getChannel(ChannelInfo channelInfo);

    public Data[] getData(int bufferSize) throws IOException;

    public Data[] getData(int bufferSize, long bufferingPeriod, boolean isTimestampIncluded, boolean isUncertaintyIncluded, boolean isValidityIncluded) throws IOException;

    public int[] getErrorCodes();

    public String getErrorText(int errorCode);

    public SensorInfo getSensorInfo();

    public int getState();

    public void removeDataListener();

    public void setDataListener(DataListener listener, int bufferSize);

    public void setDataListener(DataListener listener, int bufferSize, long bufferingPeriod, boolean isTimestampIncluded, boolean isUncertaintyIncluded, boolean isValidityIncluded);
}