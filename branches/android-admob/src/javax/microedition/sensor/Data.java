package javax.microedition.sensor;

public interface Data
{
    public ChannelInfo getChannelInfo();

    public double[] getDoubleValues();

    public int[] getIntValues();

    public long getTimestamp(int index);

    public float getUncertainty(int index);

    public Object[] getObjectValues();

    public boolean isValid(int index);
}
