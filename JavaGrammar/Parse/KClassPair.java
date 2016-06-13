package Parse;

import java.util.*;
import java.io.*;

public class KClassPair implements Serializable
{
	private static final long serialVersionUID = -670138186152077012L;
	public String subClass;
	public String superClass;
	public KClassPair(String subClass,String superClass) {
		this.subClass = subClass;
		this.superClass = superClass;
	}
	public String getSuperClass() {
		return superClass;
	}
	public String getSubClass() {
		return subClass;
	}
	public String toString() {
		return subClass + " extends " + superClass;
	}
}
