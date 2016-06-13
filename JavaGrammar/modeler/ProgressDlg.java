package modeler;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
public class ProgressDlg extends JDialog {
	private JProgressBar _bar;
	private JTextField _textOutput;
    private static final long serialVersionUID = 1606232749692588337L;
	ProgressDlg(Frame f) {
		super(f);
		super.setTitle("Progress Information");
		this.setModal(false);
		
		_bar = new JProgressBar(0,100);
		_bar.setValue(0);
		_bar.setStringPainted(true);
		
		_textOutput = new JTextField(20);
		_textOutput.setEditable(false);
		
		JPanel panel1 = new JPanel(new FlowLayout());
		panel1.add(_textOutput);
		JPanel panel2 = new JPanel(new FlowLayout());
		panel2.add(_bar);
		
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());
		
		cp.add(panel1,BorderLayout.NORTH);
		cp.add(panel2,BorderLayout.CENTER);
		
		this.setSize(300,100);
		this.setLocation(100,100);
	}
	public synchronized void setString(String s) {
		_textOutput.setText(s);
	}
	public synchronized void setMinimum(int i) {
		_bar.setMinimum(i);
	}
	public synchronized void setMaximum(int i) {
		_bar.setMaximum(i);
	}
	public synchronized void setValue(int i) {
		_bar.setValue(i);
		int per = (int)(_bar.getPercentComplete() * 100);
		String val = new String() + per + "%";
		_bar.setString(val);
		update(getGraphics());
	}
}
