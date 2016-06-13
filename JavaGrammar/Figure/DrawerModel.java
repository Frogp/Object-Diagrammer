package Figure;

import modeler.*;
import Parse.*;
import java.io.*;
import java.lang.*;
import javax.swing.*;
import java.util.*;
import javax.swing.filechooser.*;



public class DrawerModel 
{
	private transient DrawView view;
	private FigureList figures;
	private String _fileName;
	

	public DrawerModel(DrawView view)
	{
		this.view = view;
		figures = new FigureList();
	}
	public FigureList getFigures() {
		return figures;
	}
	public void addFigure(Figure ptr) {
		figures.addTail(ptr);
		System.out.println(figures.size());
		System.out.println(ptr.getClass().getName());
	}
	public void removeFigure(Figure ptr) {
		figures.removeFigure(ptr);
	}
	public void removeAllFigure(){
		for(int i = 0; i < figures.size(); i++){
			Figure ptr = (Figure)figures.get(i);
			if(ptr instanceof ClassBox){
				ClassBox cBox = (ClassBox)ptr;
				ArrayList<Line> lines = cBox.getSlaveConnectedLine();
				for(int j = 0; j < lines.size(); j++)
				{
					lines.get(j).remove();
				}
				ArrayList<Line> lines2 = cBox.getClassConnectedLine();
				for(int k = 0; k < lines2.size(); k++)
				{
					lines2.get(k).remove();
				}
				cBox.removeLabel();
			}else if(ptr instanceof Line){
				Line line = (Line)ptr;
				line.remove();
			}
		}
		figures.removeAllElements();
	}
	public void onFileSave() {
		System.out.println(_fileName);
		if (_fileName != null && _fileName.length() > 0) {
			doSave();
			return;
		}
		onFileSaveAs();
	}
	public void onFileSaveAs() {
		JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		FileNameExtensionFilter extFilter = new FileNameExtensionFilter("rcd","RCD");
		chooser.setFileFilter(extFilter);
		int returnVal = chooser.showSaveDialog(null);
		if (returnVal != JFileChooser.APPROVE_OPTION) return;
		String fileName = chooser.getSelectedFile().getPath();
		if (fileName == null) return;
		if (fileName.length() == 0) return;
		if(fileName.endsWith(".rcd"))
			_fileName = fileName;
		else
			_fileName = fileName + ".rcd";

		doSave();
    }
    public void doSave() {
		try {
			FileOutputStream  fos = new FileOutputStream(_fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			ArrayList<KClass> classes = view.getClasses();
			KClassPairList classPair = view.getClassPair();
			System.out.println(classPair + "<-----------0000000000---");
			SaveInfo info = new SaveInfo(classes, figures, classPair); 
			oos.writeObject(info);
			oos.flush();
			oos.close();
			fos.close();
			System.out.println("doSave");
		} catch(IOException ex) {
			System.out.println(ex);
		}
    }
	public void clearAll(){
		removeAllFigure();
		view.classesClear();
		view.classPairsClear();
		view.getUpTree().inintTree();
		view.getDownTree().inintTree();
	}
	public void onFileOpen() {
		JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		FileNameExtensionFilter extFilter = new FileNameExtensionFilter("rcd","RCD");
		chooser.setFileFilter(extFilter);

		int returnVal = chooser.showOpenDialog(null);
		if (returnVal != JFileChooser.APPROVE_OPTION) 
			return;
		_fileName = chooser.getSelectedFile().getPath();
		removeAllFigure();
		view.classesClear();
		//view.getClasses().clear();
		view.resetSelectedFigure();
		try {
			FileInputStream fis = new FileInputStream(_fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			//figures = (FigureList)(ois.readObject());
			SaveInfo loadedInfo = (SaveInfo)(ois.readObject());
			figures = loadedInfo.getFigures();
			for(int i = 0; i < figures.size(); i++) {
				Figure ptr = figures.getAt(i);
				ptr.doSomethingAfterLoad(view);
			}
			ArrayList<KClass> classes = loadedInfo.getClasses();
			TreeView upTree = view.getUpTree();
			UnPrintalbleClassTree downTree = view.getDownTree();
			upTree.inintTree();
			upTree.setNodes(classes);
			upTree.repaint();
			downTree.inintTree();
			downTree.setNodes(classes);
			view.setClasses(classes);
			downTree.repaint();

			KClassPairList classPair = loadedInfo.getPairList();
			view.setClassPair(classPair);

			ois.close();
			fis.close();
		} catch(ClassNotFoundException ex) {
		} catch(IOException ex) {
		}
		view.repaint();
    }
}
