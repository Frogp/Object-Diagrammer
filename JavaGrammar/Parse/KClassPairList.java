package Parse;

import java.util.*;
import java.io.*;

public class KClassPairList implements Serializable
{
    private static final long serialVersionUID = 2762782305144102729L;
	public ArrayList<KClassPair> pairs;
	public KClassPairList() {
		pairs = new ArrayList<KClassPair>();
	}
	public void clear() {
		pairs.clear();
	}
	public void add(KClassPair pair) {
		if (isIn(pair))
		{
			return;
		}
		pairs.add(pair);
	}
	public boolean isIn(KClassPair pair) {
		for (int i = 0; i < pairs.size(); i++)
		{
			String s1 = pairs.get(i).getSubClass();
			String s2 = pairs.get(i).getSuperClass();
			if (s1.equals(pair.getSubClass()) && s2.equals(pair.getSuperClass()))
			{
				return true;
			}
		}
		return false;
	}
	public ArrayList<String> getSuperClasses(String aClass) {
		ArrayList<String> supers = new ArrayList<String>();
		if (pairs.size() == 0)
		{
			return supers;
		}
		String subClass = aClass;
		while(true) {
			boolean found = false;
			for (int i = 0; i < pairs.size(); i++)
			{
				KClassPair pair = pairs.get(i);
				if (subClass.equals(pair.getSubClass())) {
					supers.add(pair.getSuperClass());
					subClass = pair.getSuperClass();
					found = true;
					break;
				}
			}
			if (!found) break;
		}
		return supers;
	}
	public String toString() {
		String s = "";
		for (int i = 0; i < pairs.size(); i++)
		{
			s = s + pairs.get(i) + "\n";
		}
		return s;
	}
}
