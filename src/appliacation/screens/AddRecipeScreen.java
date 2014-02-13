package appliacation.screens;

import gate.GateManager;
import interfaces.SaveRecipeListener;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.URL;
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

import java.awt.Font;

public class AddRecipeScreen extends JFrame {

	/**
	 * 
	 */
	public GateManager gateManager;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnProcessEditRecipe;
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
		setBounds(0, 0, 700, 550);
		setMinimumSize(new Dimension(550, 450));

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.LABEL_COMPONENT_GAP_COLSPEC, ColumnSpec.decode("max(120dlu;default):grow"),
				ColumnSpec.decode("default:grow"), ColumnSpec.decode("max(120dlu;min):grow"), FormFactory.RELATED_GAP_COLSPEC, }, new RowSpec[] { RowSpec.decode("10dlu"),
				FormFactory.DEFAULT_ROWSPEC, RowSpec.decode("10dlu"), RowSpec.decode("max(140dlu;default):grow"), RowSpec.decode("7dlu"), FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("28px"), RowSpec.decode("bottom:default"), RowSpec.decode("bottom:3dlu"), FormFactory.DEFAULT_ROWSPEC, RowSpec.decode("bottom:3dlu"),
				RowSpec.decode("bottom:default"), RowSpec.decode("bottom:3dlu"), RowSpec.decode("bottom:default"), RowSpec.decode("bottom:3dlu"), RowSpec.decode("bottom:default"),
				RowSpec.decode("bottom:3dlu"), RowSpec.decode("bottom:default"), }));

		JLabel lblNewRecipeText = new JLabel("New Recipe Text");
		contentPane.add(lblNewRecipeText, "2, 2, 3, 1, center, default");
		
		textPane = new JTextPane();
		textPane.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		textPane.setToolTipText("Enter Recipe Here");
		textPane.getDocument().addDocumentListener(new DocumentListener() {

			private void checkEnteredTextState() {
				recipeText = textPane.getText();
				if (recipeText == null || recipeText.isEmpty()) {
					btnSaveRecipe.setEnabled(false);
				} else {
					btnSaveRecipe.setEnabled(true);
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				checkEnteredTextState();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				checkEnteredTextState();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});

		JScrollPane jsp = new JScrollPane(textPane);
		contentPane.add(jsp, "2, 4, 3, 1, fill, fill");
		btnProcessEditRecipe = new JButton("Text Process");

		btnProcessEditRecipe.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (isRecipeProcessed) {
					btnProcessEditRecipe.setText("Text Process");
					textPane.setContentType("text/plain");
					textPane.setText(recipeText);
					textPane.updateUI();
					textPane.setEditable(true);
				} else {
					btnProcessEditRecipe.setText("Edit Recipe Text");
					textPane.setEditable(false);

					recipeText = new String(textPane.getText());

					parsedText = gateManager.processRecipe(recipeText);
					if (parsedText != null) {

						textPane.setContentType("text/html");
						textPane.setText(parsedText);
						textPane.updateUI();
					}
				}
				isRecipeProcessed = !isRecipeProcessed;
			}
		});
		contentPane.add(btnProcessEditRecipe, "2, 7");

		btnSaveRecipe = new JButton("Save Recipe");
		btnSaveRecipe.setEnabled(false);
		contentPane.add(btnSaveRecipe, "4, 7");

		lblLegend = new JLabel("Legend:");
		contentPane.add(lblLegend, "2, 10");

		lblAmount = new JLabel("Amount");
		Color ammountColor= Color.decode("#FF6666");
		lblAmount.setForeground(ammountColor);
		contentPane.add(lblAmount, "4, 12");
		Color ingredientColor= Color.decode("#A8A8A8");
		Color measureColor= Color.decode("#33CCFF");
				
						lblMeasure = new JLabel("Measure");
						
						contentPane.add(lblMeasure, "4, 14");
		
				lblIngredient = new JLabel("Ingredient");
				lblIngredient.setForeground(ingredientColor);
				contentPane.add(lblIngredient, "4, 16");
				lblIngredient.setForeground(measureColor);
		btnSaveRecipe.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (recipeText != null && !recipeText.isEmpty()) {
					initiateRecipeSavingProcess(recipeText);
				} else {
					// TO:DO present allert
				}
			}
		});
	}
	
//	public void DrawingColor()
//    {
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        getContentPane().add(new MyComponent());
//        setSize(400,400);
//        setVisible(true);
//    }

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
					// ALTERNATIVELY: remove the extension (if any) and replace
					// it with ".xml"
				}
				stream = new FileOutputStream(file);
				out = new PrintStream(stream);
				out.print(text); // This will overwrite existing contents
				URL url = file.toURI().toURL();
				gateManager.updateSearchCorpusWithNewRecipe(url);
				notifyListenersForSaveOperation();
			} catch (Exception ex) {
				// do something
			} finally {
				try {

					if (stream != null)
						stream.close();
					if (out != null)
						out.close();

				} catch (Exception ex) {
					// do something
				} finally {

					recipeText = "";
					parsedText = "";
					textPane.setText("");
					textPane.setEditable(true);
					btnProcessEditRecipe.setText("Text Process");
					isRecipeProcessed = false;
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
