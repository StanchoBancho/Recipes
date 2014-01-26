package app;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MainFrame(){
		setSize(200, 200);
		JLabel label = new JLabel("Hello Dobby");
		Container content = getContentPane();
		content.setLayout(new FlowLayout());
		content.add(label);
		setVisible(true);
	}
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainFrame frame = new MainFrame();
		
	}

}
