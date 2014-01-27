package app;

import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Font;

public class Application{

	private JFrame frame;
	private JTextField textField;
	private DefaultListModel listModel;
	private JList list;
	private JLabel lblIngredientList;
	private JButton btnAddIngredient;
	private JButton btnSearchForRecipes;
	private JButton btnEditIngredient;
	private JButton btnDeleteIngredient;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application window = new Application();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Application() {
		initialize();
	}
	
	
	private void setupTextField(){
		textField = new JTextField();
		frame.getContentPane().add(textField, "2, 2, 9, 1, fill, default");
		textField.setColumns(10);
		textField.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
				    warn();
				  }
				  public void removeUpdate(DocumentEvent e) {
				    warn();
				  }
				  public void insertUpdate(DocumentEvent e) {
				    warn();
				  }

				  public void warn() {
				     if (textField.getText().length()==0){
							btnAddIngredient.setEnabled(false);
				     }
				     else{
							btnAddIngredient.setEnabled(true);
				     }
				  }
		});
		textField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				int[] select = {};
				list.setSelectedIndices(select);	
			}
		});
	}

	
	private void setupButtons(){
		//add button
		btnAddIngredient = new JButton("Add Ingredient");
		btnAddIngredient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String enteredText = textField.getText();
				listModel.insertElementAt(enteredText, 0);
				int[] select = {};
				list.setSelectedIndices(select);
				textField.setText("");
			}
		});
		btnAddIngredient.setEnabled(false);
		frame.getContentPane().add(btnAddIngredient, "12, 2");
	
		//delete button
		btnDeleteIngredient = new JButton("Delete Ingredient");
		frame.getContentPane().add(btnDeleteIngredient, "12, 7");
		btnDeleteIngredient.setEnabled(false);
		btnDeleteIngredient.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int selectedIndex = list.getSelectedIndex();
				listModel.remove(selectedIndex);
			}
		});
		
		//edit button
		btnEditIngredient = new JButton("Edit Ingredient");
		frame.getContentPane().add(btnEditIngredient, "12, 6");
		btnEditIngredient.setEnabled(false);
		btnEditIngredient.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 702, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(100dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.BUTTON_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.PARAGRAPH_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.PARAGRAPH_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("bottom:default"),
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.PARAGRAPH_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,}));
		
		setupTextField();
		
		listModel = new DefaultListModel();
		list = new JList(listModel);
		list.setValueIsAdjusting(true);
		frame.getContentPane().add(list, "2, 6, 9, 6, fill, fill");
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
			    if (e.getValueIsAdjusting() == false) {

			        if (list.getSelectedIndex() == -1) {
			        //No selection, disable fire button.
			            btnDeleteIngredient.setEnabled(false);
			            btnEditIngredient.setEnabled(false);
			        } else {
			        //Selection, enable the fire button.
			        	btnDeleteIngredient.setEnabled(true);
			            btnEditIngredient.setEnabled(true);
			        }
			    }
			}
		});
		
		setupButtons();
		
		lblIngredientList = new JLabel("Ingredient List");
		lblIngredientList.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		frame.getContentPane().add(lblIngredientList, "2, 4, center, default");
		
		
		btnSearchForRecipes = new JButton("Search For Recipes");
		frame.getContentPane().add(btnSearchForRecipes, "12, 12");
		btnSearchForRecipes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int[] select = {};
				list.setSelectedIndices(select);
				
			}
		});
	}
}
