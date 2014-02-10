package appliacation.screens;

import gate.GateManager;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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
	public JMenuBar menuBar;
	private ArrayList<SaveRecipeListener> listeners = new ArrayList<SaveRecipeListener>();
	private JLabel lblLegend;
	private JLabel lblAmount;
	private JLabel lblIngredient;
	private JLabel lblMeasure;

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
		setBounds(0, 0, 700, 700);
		setMinimumSize(new Dimension(550, 450));

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("max(120dlu;default):grow"),
				ColumnSpec.decode("default:grow"),
				ColumnSpec.decode("max(120dlu;min):grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				RowSpec.decode("10dlu"),
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("10dlu"),
				RowSpec.decode("max(140dlu;default):grow"),
				RowSpec.decode("7dlu"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("28px"),
				RowSpec.decode("bottom:default"),
				RowSpec.decode("bottom:3dlu"),
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("bottom:3dlu"),
				RowSpec.decode("bottom:default"),
				RowSpec.decode("bottom:3dlu"),
				RowSpec.decode("bottom:default"),
				RowSpec.decode("bottom:3dlu"),
				RowSpec.decode("bottom:default"),
				RowSpec.decode("bottom:3dlu"),
				RowSpec.decode("bottom:default"),}));

		JLabel lblNewRecipeText = new JLabel("New Recipe Text");
		contentPane.add(lblNewRecipeText, "2, 2, 3, 1, center, default");

		textPane = new JTextPane();
		textPane.setToolTipText("Enter Recipe Here");
		textPane.setContentType("text/html");
		textPane.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				recipeText = textPane.getText();
					if(recipeText == null || recipeText.isEmpty()){
						btnSaveRecipe.setEnabled(false);
					}
					else{
						btnSaveRecipe.setEnabled(true);
					}
			}
		});
		
		JScrollPane jsp = new JScrollPane(textPane);
		contentPane.add(jsp, "2, 4, 3, 1, fill, fill");
		btnAddRecipe = new JButton("Text Process");
		btnAddRecipe.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (isRecipeProcessed) {
					btnAddRecipe.setText("Text Process");
					textPane.setText(recipeText);
					textPane.setEditable(true);
				} else {
					btnAddRecipe.setText("Edit Recipe Text");
					textPane.setEditable(false);
					recipeText = textPane.getText();
					parsedText = gateManager.processRecipe(recipeText);
					if (parsedText != null) {
						textPane.setText(parsedText);
					}
				}
				isRecipeProcessed = !isRecipeProcessed;
			}
		});
		contentPane.add(btnAddRecipe, "2, 7");

		btnSaveRecipe = new JButton("Save Recipe");
		contentPane.add(btnSaveRecipe, "4, 7");
		
		lblLegend = new JLabel("Legend:");
		contentPane.add(lblLegend, "2, 10");
		
		lblAmount = new JLabel("Amount");
		contentPane.add(lblAmount, "4, 12");
		
		lblIngredient = new JLabel("Ingredient");
		contentPane.add(lblIngredient, "4, 14");
		
		lblMeasure = new JLabel("Measure");
		contentPane.add(lblMeasure, "4, 16");
		btnSaveRecipe.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(recipeText != null && !recipeText.isEmpty()){
					initiateRecipeSavingProcess(recipeText);
				}
				else{
					//TO:DO present allert 
					
				}
			}
		});
	}

	private void initiateRecipeSavingProcess(String text) {
		String recipeDirectory = new File(System.getProperty("user.dir"), "recipes-list").toString();
		JFileChooser chooser = new JFileChooser(recipeDirectory);

		int returnVal = chooser.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			FileOutputStream stream = null;
			PrintStream out = null;
			try {
				File file = chooser.getSelectedFile();
				if (FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("txt")) {
					// filename is OK as-is
				} else {
					file = new File(file.getParentFile(), FilenameUtils.getBaseName(file.getName()) + ".txt"); 
					// ALTERNATIVELY: remove the extension (if any) and replace it with ".xml"
				}
				stream = new FileOutputStream(file);
				out = new PrintStream(stream);
				out.print(text); // This will overwrite existing contents
			} catch (Exception ex) {
				// do something
			} finally {
				try {
					recipeText = "";
					parsedText = "";
					textPane.setText("");
					textPane.setEditable(true);
					btnAddRecipe.setText("Text Process");
					isRecipeProcessed = false;
					if (stream != null)
						stream.close();
					if (out != null)
						out.close();

				} catch (Exception ex) {
					// do something
				} finally {
					notifyListenersForSaveOperation();
				}
			}
		}
	}

	private void notifyListenersForSaveOperation() {
		for (SaveRecipeListener listenerInstance : listeners) {
			listenerInstance.newRecipeSaved();
		}
	}

	public void addSaveRecipeListener(SaveRecipeListener newListener) {
		listeners.add(newListener);
	}

	public void removeSaveRecipeListener(SaveRecipeListener listener) {
		listeners.remove(listener);
	}
}
