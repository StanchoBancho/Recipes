package appliacation.screens;

import gate.GateManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import org.apache.commons.io.FilenameUtils;

public class AddRecipeScreen extends JFrame {
	/**
	 * 
	 */
	public GateManager gateManager;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnAddRecipe;
	private JButton btnSaveRecipe;
	private JTextPane textPane;
	private boolean isRecipeProcessed;
	private String recipeText;
	private String parsedText;
	public  JMenuBar menuBar;

	public GateManager getGateManager() {
		return gateManager;
	}

	public void setGateManager(GateManager gateManager) {
		this.gateManager = gateManager;
	}

	/**
	 * Create the frame.
	 */
	public AddRecipeScreen() {
		isRecipeProcessed = false;
		setBounds(0, 0, 700, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("max(158dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("313px:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				RowSpec.decode("10dlu"),
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("10dlu"),
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("15dlu"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(174dlu;default):grow"),
				RowSpec.decode("7dlu"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("28px"),
				RowSpec.decode("10dlu"),}));
		
		JLabel lblNewRecipeText = new JLabel("New Recipe Text");
		contentPane.add(lblNewRecipeText, "2, 2, 4, 1, center, default");
		
		textPane = new JTextPane();
		textPane.setToolTipText("Enter Recipe Here");
		textPane.setContentType("text/html");
		
		JScrollPane jsp = new JScrollPane(textPane);

		contentPane.add(jsp, "2, 4, 4, 7, fill, fill");
		
		btnAddRecipe = new JButton("Text Process");
		btnAddRecipe.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (isRecipeProcessed){
					btnAddRecipe.setText("Text Process");
					btnSaveRecipe.setEnabled(false);
					
					textPane.setText(recipeText);
					textPane.setEditable(true);
				}
				else{
					
					btnAddRecipe.setText("Edit Recipe Text");
					textPane.setEditable(false);
					
					recipeText = textPane.getText();
					parsedText = gateManager.processRecipe(recipeText);
					if(parsedText != null){
						textPane.setText(parsedText);
					}
					btnSaveRecipe.setEnabled(true);
				}
				isRecipeProcessed = !isRecipeProcessed;
			}
		});
		contentPane.add(btnAddRecipe, "2, 13");
		
		btnSaveRecipe = new JButton("Save Recipe");
		contentPane.add(btnSaveRecipe, "5, 13");
		btnSaveRecipe.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String recipeText = textPane.getText();
				initiateRecipeSavingProcess(recipeText);
			}
		});
	}
	
	private void initiateRecipeSavingProcess(String text){
		String recipeDirectory =  new File(System.getProperty("user.dir"), "recipes-list").toString(); 
		JFileChooser chooser = new JFileChooser(recipeDirectory);
		
		
		int returnVal = chooser.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
		    FileOutputStream stream = null;
		    PrintStream out = null;
		    try {
		        File file = chooser.getSelectedFile();
		        
		        if (FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("html")) {
		            // filename is OK as-is
		        } else {
		            file = new File(file.getParentFile(), FilenameUtils.getBaseName(file.getName())+".html"); // ALTERNATIVELY: remove the extension (if any) and replace it with ".xml"
		        }
		        
		        stream = new FileOutputStream(file); 
		        out = new PrintStream(stream);
		        out.print(text);                  //This will overwrite existing contents

		    } catch (Exception ex) {
		        //do something
		    } finally {
		        try {
		        	recipeText = "";
		        	parsedText = "";
		        	textPane.setText("");
		        	textPane.setEditable(true);
					btnAddRecipe.setText("Text Process");
					btnSaveRecipe.setEnabled(false);
					isRecipeProcessed = false;
		        	if(stream!=null) stream.close();
		            if(out!=null) out.close();
		            
		            
		        } catch (Exception ex) {
		            //do something
		        }
		    }
		}
	}
}
