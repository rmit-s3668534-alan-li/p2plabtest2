package code;

public class KeyValue<T>
{
    private int key;
    private String value;
    
    public KeyValue(int key)
    {
        this.key = key;
    }

    public KeyValue(int key, String value)
    {
        this.key = key;
        this.value = value;
    }

    public int getKey()
    {
        return key;
    }

    public String getValue()
    {
        return value;
    }

    public void setKey(int key)
    {
        this.key = key;
    }

    public void setValue(String value)
    {
        this.value = value;
    }
}