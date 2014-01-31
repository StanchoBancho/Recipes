package appliacation.screens;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MenuFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private  JMenuBar menuBar;
	public JMenuItem addNewRecipeMenuItem;
	public JMenuItem searchRecipesMenuItem;
	public JMenuItem browseRecipesMenuItem;
	
	/**
	 * Create the frame.
	 */
	public MenuFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	
		menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        addNewRecipeMenuItem = new JMenuItem("Add New Recipe");
        fileMenu.add(addNewRecipeMenuItem);
        
        searchRecipesMenuItem = new JMenuItem("Search");
        fileMenu.add(searchRecipesMenuItem);
        
        browseRecipesMenuItem = new JMenuItem("Browse Recipes");
        fileMenu.add(browseRecipesMenuItem);
        this.setJMenuBar(menuBar);
	
	
	}

}
