package Figure;

import java.util.*;
import modeler.*;
import Parse.*;
import java.io.*;

public class SaveInfo implements Serializable
	{
	    private static final long serialVersionUID = 5440397401370093291L;
		public ArrayList<KClass> classes;
		public FigureList figures;
		public KClassPairList pairList;

		SaveInfo(ArrayList<KClass> classes, FigureList figures,KClassPairList pairList)
		{
			this.classes = classes;
			this.figures = figures;
			this.pairList = pairList;
		}
		public ArrayList<KClass> getClasses(){
			return classes;
		}
		public FigureList getFigures(){
			return figures;
		}
		public KClassPairList getPairList(){
			return pairList;
		}

	}
