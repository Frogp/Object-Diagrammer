package modeler;

import java.util.*;
import java.awt.event.*;
import java.awt.*; 
import javax.swing.*;
import javax.swing.text.*; 
import java.io.*;
import java_cup.runtime.*;
import javax.swing.filechooser.*;

import Lex.*;
import Parse.*;
import Figure.*;


public class Modeler extends JFrame implements ActionListener{
	/** 화면 콤포넌트 객체 레퍼런스
	 	GraphicController는 BackPanel로 교체 되었음
	 */ 
	public BackPanel controller = null;
	/** 이 프레임의 패키지 이름
	 */
	public ArrayList<KClass> classes ;
	public KClassPairList classPairs;
	static public ProgressDlg TheProgressBar;
    private static final long serialVersionUID = 7518998376748716806L;
	/** 생성자이다.
	 */	
	JMenuItem saveItem = new JMenuItem("Save");
	JMenuItem saveAsItem = new JMenuItem("Save As...");

	public Modeler(String name) {
		super(name);
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		JMenuItem newItem = new JMenuItem("New");
		saveItem = new JMenuItem("Save");
		saveAsItem = new JMenuItem("Save As...");
		JMenuItem loadtem = new JMenuItem("Open");
		classes = new ArrayList<KClass>();
		JMenu toolMenu = new JMenu("Tool");
		JMenuItem reverseItem = new JMenuItem("Reverse");
		JMenuItem addClassItem = new JMenuItem("Add custom class");

		JMenu viewMenu = new JMenu("View");
		JCheckBoxMenuItem classTreeViewItem = new JCheckBoxMenuItem("Classes Tree");
		classTreeViewItem.setState(true);

		fileMenu.add(newItem);
		fileMenu.add(saveItem);
		fileMenu.add(saveAsItem);
		fileMenu.add(loadtem);
		saveItem.setEnabled(false);
		saveAsItem.setEnabled(false);
		
		toolMenu.add(addClassItem);
		toolMenu.add(reverseItem);

		viewMenu.add(classTreeViewItem);

		menuBar.add(fileMenu);
		menuBar.add(toolMenu);
		menuBar.add(viewMenu);

		setJMenuBar(menuBar);		
	
		fileMenu.addActionListener(this);
		newItem.addActionListener(this);
		reverseItem.addActionListener(this);
		saveAsItem.addActionListener(this);
		saveItem.addActionListener(this);
		loadtem.addActionListener(this);
		addClassItem.addActionListener(this);
		classTreeViewItem.addActionListener(this);
	}
	public void walk( String path , ArrayList<File> allFiles) {
		File root = new File( path );
		File[] list = root.listFiles();
		if (list == null) return;
		for ( File f : list ) {
			if ( f.isDirectory() ) {
				walk( f.getAbsolutePath(), allFiles );
				//System.out.println("Dir" + f.getAbsoluteFile() );
			}else{
				if(f.getAbsolutePath().endsWith(".java")){
					allFiles.add(f);
					//System.out.println("File" + f.getAbsoluteFile() );
				}
			}
		}
	}
	public void setClasses(ArrayList<KClass> loadedClass)
	{
		classes = loadedClass;
	}
	public void setClassPair(KClassPairList loadedClasses)
	{
		classPairs = loadedClasses;
	}
	public void classPairsClear()
	{
		classPairs.clear();
	}

	public ArrayList<String> getSuperClasses(String aClass) {
		return classPairs.getSuperClasses(aClass);
	}
	public void actionPerformed(ActionEvent e)
	{
		String arg = e.getActionCommand();
		if(arg.equals("Reverse")){
			JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
			chooser.setDialogTitle("Select a dialog to reverse");
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			//chooser.setMultiSelectionEnabled(true);
			FileNameExtensionFilter extFilter = new FileNameExtensionFilter("Java","java");
			chooser.setFileFilter(extFilter);

			int returnVal = chooser.showOpenDialog(this);
			if(returnVal != JFileChooser.APPROVE_OPTION) {
				return;
			}
			/*File inputFile = chooser.getSelectedFile();			
			//낱개의 java파일선택시 처리를 위한 코드
			if(inputFile.isFile())
			{
				inputFile = inputFile.getParentFile();
			}*/			

			FilenameFilter filter = new FilenameFilter() {
				public boolean accept(File dir,String name) {
					if (name.endsWith("java")) return true;
					else return false;
				}
			};
			String dir = chooser.getSelectedFile().getPath();
			ArrayList<File> allFiles = new ArrayList<File>();
			walk(dir,allFiles);
			TheProgressBar = new ProgressDlg(this);
			TheProgressBar.setVisible(true);
			TheProgressBar.setTitle("Parsing files ...");
			TheProgressBar.setMinimum(1);
			TheProgressBar.setMaximum( allFiles.size());
			TheProgressBar.setValue(1);

			Reader fr = null;
			Lex.Lexer lex = null;
			Parse.Grm15 g  = null;
			//parse() 로 부터 classes 를 첫번째로 받기전은 null 이다. 
			if(classes != null) 
				classes.clear();
			if (classPairs != null)
			{
				classPairs.clear();
			}
			for(int i = 0; i < allFiles.size(); i++)
			{
				//System.out.println(files[i]);
				try{

					String aFileName = allFiles.get(i).getPath();
					TheProgressBar.setString(aFileName);
					TheProgressBar.setValue(i+1);
					fr = new BufferedReader(new FileReader(aFileName));
					lex = new Lex.Lexer(fr,5);
					g = new Parse.Grm15(lex);
					g.parse();
					System.out.println(aFileName + " size = " + g.getClasses().size() + "  OK");
					classes = g.getClasses();		
					classPairs = g.getClassPairs();
					System.out.println(classPairs);
				}catch(Exception exc){
				}
			}
			for(int i = 0; i < classes.size(); i++){
					String s = classes.get(i).getName();
					System.out.println("===========" + s);
			}
			TheProgressBar.setTitle("Drawing diagrams ...");
			TheProgressBar.setValue(1);
			TheProgressBar.setVisible(false);
			TheProgressBar = null;

			controller.setNodes(classes);
			saveItem.setEnabled(true);
			saveAsItem.setEnabled(true);
			ArrayList<String> supers = classPairs.getSuperClasses("ClassBox");
			for(int i = 0; i < supers.size(); i++) {
				System.out.print(supers.get(i) + "===");
			}
		}else if(arg.equals("Classes Tree")){
			controller.toggleTree();
		}else if(arg.equals("New")){
			DrawerModel model = controller.getModel();
			model.clearAll();
		}else if(arg.equals("Save")){
			DrawerModel model = controller.getModel();
			model.onFileSave();
		}else if(arg.equals("Open")){
			DrawerModel model = controller.getModel();
			model.onFileOpen();
			saveItem.setEnabled(true);
			saveAsItem.setEnabled(true);

		}else if(arg.equals("Save As...")){
			DrawerModel model = controller.getModel();
			model.onFileSaveAs();
		}else if(arg.equals("Add custom class")){
			CustomizeClassDialog dg = new CustomizeClassDialog(this);
			dg.setLocation(500,400);
			KClass customClass = dg.showDialog() ;
			if(customClass == null){
				System.out.println("cancel");
				return;
			}


			/*
			customClass = new KClass("CUSTOM_CLASS");
			ArrayList<KField> fields = new  ArrayList<KField>();
			fields.add(new KField("MyBox","TEST!!!NAME", false));
			customClass.addField(fields.get(0));
			customClass.setArrayClass();
			customClass.setArrayLength(4);
			*/
			customClass.setCustom();
			/*
			ArrayList<KField> fields = new  ArrayList<KField>();
			fields.add(new KField("MyBox","TEST!!!NAME", false));
			for(int i = 0; i < fields.size(); i++)
			{
				customClass.addField(fields.get(i));
			}
			*/
			classes.add(customClass);
			controller.setNodes(classes);
		}
		
	}
	/** 데이타 멤버 controller에 대한 읽기 access 함수이다.
	 */
	public BackPanel controller() {
		return controller;
	}
	public ArrayList<KClass> getClasses(){
		return classes;
	}
	public KClassPairList getClassPair()
	{
		return classPairs;
	}


}
class CustomizeClassDialog extends JDialog implements ActionListener
{
	KClass customClass;
	boolean returnVlaue;
	JTextField className;
	JComboBox toMake;
	JTextField fieldType;
	JTextField fieldName;
	JTextField arrayLength;
	JList<KField> fieldList;
	JCheckBox isPrimivite;
	JButton add;
	DefaultListModel<KField> model;
	public void actionPerformed(ActionEvent evt){
		//System.out.println(evt.getSource());
		System.out.println(isPrimivite.isSelected());
		System.out.println(evt.paramString());
		if (evt.getActionCommand().equals("ADD"))
		{
			if ( !(fieldType.getText().equals("")) && !(fieldName.getText().equals(""))) {
				model.addElement(new KField(fieldType.getText(), fieldName.getText(), isPrimivite.isSelected()));
				fieldList.setModel(model);
				fieldType.setText("");
				fieldName.setText("");
				isPrimivite.setSelected(false);
			}
		}else if(evt.getActionCommand().equals("Ok")){
			if(toMake.getSelectedIndex() == 0){
				customClass = new KClass(className.getText());
			}else if(toMake.getSelectedIndex() == 1){
				KField field = new KField(fieldType.getText(), fieldName.getText(), !(isPrimivite.isSelected()));
				customClass = new KClass(className.getText());
				customClass.addField(field);
				customClass.setArrayClass();
				customClass.setArrayLength(Integer.parseInt(arrayLength.getText()));
			}else if(toMake.getSelectedIndex() == 2){
				customClass = new KClass(className.getText());
				for(int i = 0 ;i < model.getSize(); i++){
					customClass.addField(model.get(i));
				}
			}
			setVisible(false); 
		}else if(evt.getActionCommand().equals("Cancel")){
			setVisible(false); 
		}
		if(toMake.getSelectedIndex() == 0){
			fieldType.setEnabled(false);
			arrayLength.setEnabled(false);
			isPrimivite.setEnabled(false);
			fieldName.setEnabled(false);
			fieldList.setEnabled(false);
			add.setEnabled(false);
		}else if(toMake.getSelectedIndex() == 1){
			fieldType.setEnabled(true);
			arrayLength.setEnabled(true);
			fieldName.setEnabled(true);
			isPrimivite.setEnabled(true);
			fieldList.setEnabled(false);
			add.setEnabled(false);
		}else if(toMake.getSelectedIndex() == 2){
			fieldType.setEnabled(true);
			arrayLength.setEnabled(false);
			fieldName.setEnabled(true);
			isPrimivite.setEnabled(true);
			fieldList.setEnabled(true);
			add.setEnabled(true);
		}
	} 
	public CustomizeClassDialog(JFrame parent)
	{
		super(parent, "Customize Class", true);  
        JPanel p1 = new JPanel();
        p1.setLayout( new GridLayout( 1, 4 ) );  
		p1.add(new JLabel("Class Name : "));
		
		className = new JTextField();
		className.setText("");
		p1.add(className);

		p1.add(new JLabel("Class Role : "));
		String[] Tests = {"Description Only", "Array Type", "Nomal Form"};
		toMake = new JComboBox(Tests);
		toMake.addActionListener(this);
		p1.add(toMake);
		getContentPane().add(p1,"North");
		
		
		JPanel p2 = new JPanel();
        p2.setLayout( new GridLayout( 7, 10 ) ); 
		p2.add(new JLabel("Field Type : "),"North");
		fieldType = new JTextField(20);
		fieldType.setMaximumSize( fieldType.getPreferredSize() );
		p2.add(fieldType,"North");			

		
		p2.add(new JLabel("Field Name : "));
		fieldName = new JTextField(20);
		p2.add(fieldName);

		p2.add(new JLabel("ArrayLength : "));
		arrayLength = new JTextField();
		arrayLength.setDocument(new ex());
		p2.add(arrayLength);

		p2.add(new JLabel("Primitive : "));
		isPrimivite = new JCheckBox();
		p2.add(isPrimivite);
		isPrimivite.addActionListener(this);
		add = new JButton("ADD");
		p2.add(add);
		add.addActionListener(this);
		/*add.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent evt){
				System.out.println("ADD");
			} 
		} );
		*/

		getContentPane().add(p2,"Center");	

		fieldList = new JList<KField>();
		p2.add( new JScrollPane(fieldList));
		model = new DefaultListModel();



		
		JPanel p3 = new JPanel();
		JButton ok = new JButton("Ok");
		JButton cancel = new JButton("Cancel");
		p3.add(ok);
		p3.add(cancel);
		getContentPane().add(p3,"South");		
		ok.addActionListener(this);
		cancel.addActionListener(this);
		fieldType.setEnabled(false);
		arrayLength.setEnabled(false);
		arrayLength.setEnabled(false);
		isPrimivite.setEnabled(false);
		fieldName.setEnabled(false);
		fieldList.setEnabled(false);
		add.setEnabled(false);
		setSize(400, 300);
	}

	public KClass showDialog()
	{
		//customClass = this.customClass;
		setVisible(true);
		return customClass;
	}
}
class ex extends PlainDocument{
	ex(){
		super();
	}
	public void insertString(int offset, String str, AttributeSet attr)throws BadLocationException{
		if (str == null)
			return;
		if(str.charAt(0) >= '0' && str.charAt(0) <= '9')
			super.insertString(offset, str, attr);
	}
}
