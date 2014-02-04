package appliacation.screens;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JLabel;

import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JList;
import javax.swing.JTextPane;

public class BrowseRecipesScreen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JList<String> list;
	private DefaultListModel<String> recipesListModel;
	private JTextPane textPane;

	/**
	 * Create the frame.
	 */
	public BrowseRecipesScreen() {
		setBounds(800, 0, 900, 800);
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
		recipesListModel = new DefaultListModel<String>();
		list = new JList<String>(recipesListModel);
		list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				presentSelectedRecipe(list.getSelectedIndex());
			}
		});
		contentPane.add(list, "2, 4, fill, fill");
		
		textPane = new JTextPane();
		textPane.setContentType("text/html");		
		contentPane.add(textPane, "4, 4, fill, fill");
		
		populateRecipeList();
	}
	
	
	public void populateRecipeList(){
		File folder = new File(System.getProperty("user.dir"), "recipes-list"); 
		File[] listOfFiles = folder.listFiles();
		ArrayList<String> arrayListOfRecipes = new ArrayList<String>();
		recipesListModel.removeAllElements();
		for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		    	 arrayListOfRecipes.add(listOfFiles[i].getName());
		      }
		}
		Collections.sort(arrayListOfRecipes);
		recipesListModel.removeAllElements();
		for (String string : arrayListOfRecipes) {
			recipesListModel.addElement(string);
		}
		list.updateUI();
	}
	
	static String readFile(String path, Charset encoding) throws IOException 
	{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		 return encoding.decode(ByteBuffer.wrap(encoded)).toString();
	}
	
	private void presentSelectedRecipe(int recipeIndex){
		if(0 <= recipeIndex  && recipeIndex < recipesListModel.capacity()){
			File folder = new File(System.getProperty("user.dir"), "recipes-list"); 
			String fileName = recipesListModel.elementAt(recipeIndex);
			if(fileName != null){
			File recipeFile = new File(folder, fileName);
			if(recipeFile != null){
				String content = null;
				try {
					content = readFile(recipeFile.toString(), StandardCharsets.UTF_8);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(content != null){
					textPane.setText(content);
				}
			}
			}
		}
	}
	
}
