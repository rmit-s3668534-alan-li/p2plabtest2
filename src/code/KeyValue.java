package code;

public class KeyValue<T>
{
    private int key;
    private T value;
    
    public KeyValue(int key)
    {
        this.key = key;
    }

    public KeyValue(int key, T value)
    {
        this.key = key;
        this.value = value;
    }

    public int getKey()
    {
        return key;
    }

    public T getValue()
    {
        return value;
    }

    public void setKey(int key)
    {
        this.key = key;
    }

    public void setValue(T value)
    {
        this.value = value;
    }
}