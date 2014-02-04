package appliacation.screens;

//import gate.StandAloneAnnie;
//import gate.util.GateException;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
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
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Font;

import javax.swing.JMenuItem;

public class SearchScreen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private JTextField textField;
	private DefaultListModel<String> listModel;
	private JList<String> list;
	private JLabel lblIngredientList;
	private JButton btnAddIngredient;
	private JButton btnSearchForRecipes;
	private JButton btnEditIngredient;
	private JButton btnDeleteIngredient;
	
	public  JMenuBar menuBar;
	private JMenuItem mntmNewMenuItem;
	private JMenuItem mntmNewMenuItem_1;
	private JMenuItem mntmNewMenuItem_2;

	
	private void setupTextField(){
		
		textField = new JTextField();
		contentPane.add(textField, "2, 2, 9, 1, fill, default");
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
		contentPane.add(btnAddIngredient, "12, 2");
	
		//delete button
		btnDeleteIngredient = new JButton("Delete Ingredient");
		contentPane.add(btnDeleteIngredient, "12, 7");
		btnDeleteIngredient.setEnabled(false);
		btnDeleteIngredient.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selectedIndex = list.getSelectedIndex();
				listModel.remove(selectedIndex);
			}
		});
		
		//edit button
		btnEditIngredient = new JButton("Edit Ingredient");
		contentPane.add(btnEditIngredient, "12, 6");
		btnEditIngredient.setEnabled(false);
		btnEditIngredient.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = list.getSelectedIndex();
				String ingredient = (String)listModel.get(selectedIndex);
				textField.setText(ingredient);
				listModel.remove(selectedIndex);
				textField.requestFocus();
			}
		});
	
		//search button
		btnSearchForRecipes = new JButton("Search For Recipes");
		contentPane.add(btnSearchForRecipes, "12, 12");
		btnSearchForRecipes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int[] select = {};
				list.setSelectedIndices(select);
				
				try {
					ResultScreen frame = new ResultScreen();
					frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
	}
	/**
	 * Initialize the contents of the frame.
	 */
	public SearchScreen() {
		setBounds(0, 800, 700, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);		
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
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
		
		
//		menuBar = new JMenuBar();
//        JMenu fileMenu = new JMenu("File");
//        menuBar.add(fileMenu);
//
//        mntmNewMenuItem = new JMenuItem("Add New Recipe");
//        fileMenu.add(mntmNewMenuItem);
//        
//        mntmNewMenuItem_1 = new JMenuItem("Search");
//        fileMenu.add(mntmNewMenuItem_1);
//        
//        mntmNewMenuItem_2 = new JMenuItem("Browse Recipes");
//        fileMenu.add(mntmNewMenuItem_2);
//        
//        setJMenuBar(menuBar);
        
        
		setupTextField();
		
		listModel = new DefaultListModel<String>();
		list = new JList<String>(listModel);
		list.setValueIsAdjusting(true);
		contentPane.add(list, "2, 6, 9, 6, fill, fill");
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
		contentPane.add(lblIngredientList, "2, 4, center, default");

		
//		try {
//			String[] params = {"file:docs/Untitled.txt"};//, "file:docs/eu_slap.txt", "file:docs/eu_zone.txt"};
//			StandAloneAnnie.doTest(params);
//		} catch (GateException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
	}
}
