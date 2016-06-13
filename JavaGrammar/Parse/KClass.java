package Parse;

import java.util.*;
import java.io.*;

public class KClass implements Serializable
{
	private static final long serialVersionUID = -8623588664262593908L;
	String name;
	ArrayList<KField> fields;
	ArrayList<KMethod> methods;
	boolean arrayFlag = false;
	int arrayLength;
	boolean isCustom = false;
	public KClass(String name)
	{
		this.name = name;
		fields = new ArrayList<KField>();
		methods = new ArrayList<KMethod>();
	}
	public KClass(KClass cClass)
	{
		this.name = cClass.getName();
		fields = new ArrayList<KField>();
		methods = new ArrayList<KMethod>();
		for(int i = 0 ; i < cClass.getFields().size(); i++)
		{
			fields.add(new KField(cClass.getFields().get(i).getType(), cClass.getFields().get(i).getName(), cClass.getFields().get(i).isPrimitive()));
		}
		if(cClass.isCustom())
			setCustom();
		if(cClass.isArrayClass()){
			setArrayClass();
			setArrayLength(cClass.getArrayLength());
		}
	}

	public boolean isCustom()
	{
		return isCustom;
	}
	public void setCustom()
	{
		isCustom = true;
	}
	public void setArrayLength(int n)
	{
		arrayLength = n;
	}
	public int getArrayLength()
	{
		return arrayLength;
	}
	public void setArrayClass()
	{
		arrayFlag = true;
	}
	public boolean isArrayClass()
	{
		return arrayFlag;
	}
	public String getName()
	{
		return name;
	}
	public ArrayList<KField> getFields()
	{
		return fields;
	}
	public ArrayList<KMethod> getMethods()
	{
		return methods;
	}
	public void addField(KField field)
	{
		fields.add(field);
	}
	public void addMethod(KMethod methode)
	{
		methods.add(methode);
	}
	public String toString() {
		return getName();
	}
	public boolean equals(Object ptr){
		if(!(ptr instanceof KClass))
			return false;
		KClass p = (KClass)ptr;
		if(p.getName().equals(name)){
			return true;
		}else{
			return false;
		}
	}
}
