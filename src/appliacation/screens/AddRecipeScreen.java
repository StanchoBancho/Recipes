package appliacation.screens;

import gate.GateManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextPane;

public class AddRecipeScreen extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnAddRecipe;
	private JButton btnSaveRecipe;
	private JTextPane textPane;
	private boolean isRecipeProcessed;
	private GateManager gateManager;
	private String recipeText;
	private String parsedText;

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
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("15dlu"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(219dlu;default):grow"),
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("28px"),
				FormFactory.RELATED_GAP_ROWSPEC,}));
		
		JLabel lblNewRecipeText = new JLabel("New Recipe Text");
		contentPane.add(lblNewRecipeText, "2, 2, 4, 2, center, default");
		
		textPane = new JTextPane();
		textPane.setContentType("text/html");
		contentPane.add(textPane, "2, 4, 4, 1, fill, fill");
		
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
					if(gateManager == null){
						gateManager = new GateManager();
					}
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
		contentPane.add(btnAddRecipe, "2, 7");
		
		btnSaveRecipe = new JButton("Save Recipe");
		contentPane.add(btnSaveRecipe, "5, 7");
	}

	
	private void saveProcessedRecipeText(String text){
		
	}
	
	private void processEnteredRecipeText(String text){
		
	}
}
