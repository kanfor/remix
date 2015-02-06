package javax.microedition.sensor;

public final class SensorManager
{
    public static void addSensorListener(SensorListener listener, SensorInfo info)
    {
        //compiled code
        throw new RuntimeException("Not implemented yet");
    }

    public static void addSensorListener(SensorListener listener, String quantity)
    {
        //compiled code
        throw new RuntimeException("Not implemented yet");
    }

    public static SensorInfo[] findSensors(String quantity, String contextType)
    {
        if (quantity.equals("acceleration"))
        {
            return new SensorInfo[]
                    {
                        new AccelerationDoubleSensor()
                    };
        }
        return new SensorInfo[0];
    }

    public static SensorInfo[] findSensors(String url)
    {
        //compiled code
        throw new RuntimeException("Not implemented yet");
    }

    public static void removeSensorListener(SensorListener listener)
    {
        //compiled code
        throw new RuntimeException("Not implemented yet");
    }
}
