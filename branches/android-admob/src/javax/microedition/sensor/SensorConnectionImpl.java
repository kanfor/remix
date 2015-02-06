package javax.microedition.sensor;

import android.app.*;
import android.hardware.*;
import java.io.*;
import java.util.*;
import javax.microedition.midlet.*;

/**
 <p/>
 @author Anton Banchev
 */
public class SensorConnectionImpl implements SensorConnection, SensorEventListener
{
    DataListener listener;
    int bufferSize = 3;
    android.hardware.SensorManager nativeManager;

    void initNative()
    {
        if (nativeManager == null)
        {
            nativeManager = (android.hardware.SensorManager)MIDlet.ms_Activity.getSystemService(Activity.SENSOR_SERVICE);
        }
        nativeManager.registerListener(this, nativeManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), android.hardware.SensorManager.SENSOR_DELAY_NORMAL);
    }

    public SensorConnectionImpl()
    {
        initNative();
    }

    public Channel getChannel(ChannelInfo channelInfo)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Data[] getData(int bufferSize) throws IOException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Data[] getData(int bufferSize, long bufferingPeriod, boolean isTimestampIncluded, boolean isUncertaintyIncluded, boolean isValidityIncluded) throws IOException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int[] getErrorCodes()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getErrorText(int errorCode)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public SensorInfo getSensorInfo()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getState()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeDataListener()
    {
        this.listener = null;
    }

    public void setDataListener(DataListener pListener, int pBufferSize)
    {
        this.listener = pListener;
        this.bufferSize = pBufferSize;
    }

    public void setDataListener(DataListener pListener, int pBufferSize, long bufferingPeriod, boolean isTimestampIncluded, boolean isUncertaintyIncluded, boolean isValidityIncluded)
    {
        this.listener = pListener;
        this.bufferSize = pBufferSize;
    }

    public void close() throws IOException
    {
        if (nativeManager != null)
        {
            nativeManager.unregisterListener(this);
        }
    }

    Stack<Data> dataHolder = new Stack<Data>();

    DataImpl xdata=new DataImpl(),ydata=new DataImpl(),zdata=new DataImpl();

    public void onSensorChanged(SensorEvent event)
    {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            xdata.pushVal(event.values[0]);
            ydata.pushVal(event.values[1]);
            zdata.pushVal(event.values[2]);
            if (xdata.count == bufferSize)
            {
                if (listener != null)
                {
                    listener.dataReceived(this, new Data[]{xdata,ydata,zdata}, false);
                }
            }
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
    }
}
