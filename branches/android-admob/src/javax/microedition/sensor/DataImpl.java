package javax.microedition.sensor;

/**
 *
 * @author Anton Banchev
 */
public class DataImpl implements Data{

    final double[] values = new double[256];
    int count = 0;
    public DataImpl()
    {
    }

    void pushVal(float pValue)
    {
        values[count++] = pValue;
    }

    void reset()
    {
        count = 0;
    }


    public ChannelInfo getChannelInfo()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double[] getDoubleValues()
    {
        double[] res = new double[count];
        System.arraycopy(values, 0, res, 0, count);
        count = 0;
        return res;
    }

    public int[] getIntValues()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public long getTimestamp(int index)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public float getUncertainty(int index)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object[] getObjectValues()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isValid(int index)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
