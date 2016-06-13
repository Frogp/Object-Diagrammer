package Parse;

import java.util.*;
import java.io.*;

public class KMethod implements Serializable
{
    private static final long serialVersionUID = -4168144026786669300L;
	String name;
	ArrayList<String> parameter;
	String returnType;
	KMethod(String name,ArrayList<String> parameter)
	{
		this.name = name;
		this.parameter = parameter;
	}
	public void setName(String n)
	{
		name = n;
	}
	public String getName()
	{
		return name;
	}
	public void setParameter(ArrayList<String> param)
	{
		parameter = param;
	}
	public ArrayList<String> getParameter()
	{
		return parameter;
	}
	public void setReturnType(String s)
	{
		returnType = s;
	}
	public String getReturnType()
	{
		return returnType;
	}
	public String toString() {
		if(getParameter() == null){
			return getReturnType() + " " +getName()+ " " + "( )";
		}
		return getReturnType() + " " +getName()+ " " +getParameter();
	}
}
