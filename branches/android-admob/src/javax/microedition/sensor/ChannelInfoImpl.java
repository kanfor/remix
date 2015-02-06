package javax.microedition.sensor;

/**
 <p/>
 @author Anton Banchev
 */
public class ChannelInfoImpl implements ChannelInfo
{
    public float getAccuracy()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getDataType()
    {
        return ChannelInfo.TYPE_DOUBLE;
    }

    public MeasurementRange[] getMeasurementRanges()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getName()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getScale()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Unit getUnit()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
