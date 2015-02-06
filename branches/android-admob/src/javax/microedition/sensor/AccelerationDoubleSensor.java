package javax.microedition.sensor;

/**
 *
 * @author Anton Banchev
 */
public class AccelerationDoubleSensor implements SensorInfo{

    public ChannelInfo[] getChannelInfos()
    {
        return new ChannelInfo[]{new ChannelInfoImpl()};
    }

    public int getConnectionType()
    {
        return SensorInfo.CONN_EMBEDDED;
    }

    public String getContextType()
    {
        return SensorInfo.CONTEXT_TYPE_DEVICE;
    }

    public String getDescription()
    {
        return "Android acceleration sensor";
    }

    public int getMaxBufferSize()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getModel()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String[] getPropertyNames()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object getProperty(String name)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getQuantity()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getUrl()
    {
        return "sensor://accelerometer";
    }

    public boolean isAvailable()
    {
        return true;
    }

    public boolean isAvailabilityPushSupported()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isConditionPushSupported()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
