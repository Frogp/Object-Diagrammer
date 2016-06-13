package Parse;

import java.io.*;

public class KField implements Serializable
{
    private static final long serialVersionUID = 2669189241588289372L;
	String name;
	String type;
	boolean isPrimitive;
	boolean isInherited = false;
	public KField(String type, String name, boolean isPrimitive)
	{
		this.name = name;
		this.type = type;
		this.isPrimitive = isPrimitive;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String toString() {
		return getType() + " " + getName();
	}
	public boolean isPrimitive()
	{
		return isPrimitive;
	}
	public boolean isInherited()
	{
		return isInherited;
	}
	public void setInherited()
	{
		isInherited = true;
	}

}
