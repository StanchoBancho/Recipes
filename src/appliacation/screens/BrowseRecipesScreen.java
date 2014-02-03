package appliacation.screens;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JTextPane;

public class BrowseRecipesScreen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JList list;
	private JTextPane textPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BrowseRecipesScreen frame = new BrowseRecipesScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BrowseRecipesScreen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("max(186dlu;default):grow"),
				ColumnSpec.decode("max(30dlu;default)"),
				ColumnSpec.decode("max(208dlu;default):grow"),
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				RowSpec.decode("top:10dlu"),
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("max(425dlu;default):grow"),
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblRecipeslistlabel = new JLabel("Recipes List");
		lblRecipeslistlabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		contentPane.add(lblRecipeslistlabel, "2, 2, center, default");
		
		JLabel lblRecipenamelabel = new JLabel("Recipe Name Label");
		lblRecipenamelabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		contentPane.add(lblRecipenamelabel, "4, 2, center, default");
		
		list = new JList();
		contentPane.add(list, "2, 4, fill, fill");
		
		textPane = new JTextPane();
		contentPane.add(textPane, "4, 4, fill, fill");
	}
	
	
	public void populateRecipeList(){
		
	}
	
	private void presentSelectedRecipe(int recipeIndex){
		
	}
	
}
