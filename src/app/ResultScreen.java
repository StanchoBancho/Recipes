package app;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JList;
import javax.swing.JTextArea;
import java.awt.Font;

public class ResultScreen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public ResultScreen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("10dlu"),
				ColumnSpec.decode("max(152dlu;pref):grow"),
				ColumnSpec.decode("max(12dlu;default)"),
				ColumnSpec.decode("default:grow"),
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("max(20dlu;default)"),
				ColumnSpec.decode("max(21dlu;default)"),
				ColumnSpec.decode("max(81dlu;default)"),
				ColumnSpec.decode("max(15dlu;default)"),},
			new RowSpec[] {
				FormFactory.UNRELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("15dlu"),
				RowSpec.decode("max(104dlu;default):grow"),
				RowSpec.decode("max(127dlu;default)"),
				RowSpec.decode("15dlu"),}));
		
		JLabel lblNewLabel = new JLabel("Found Recipes:");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		contentPane.add(lblNewLabel, "2, 2, center, default");
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		contentPane.add(lblNewLabel_1, "4, 2, 7, 1, center, default");
		
		JList list = new JList();
		contentPane.add(list, "2, 4, 1, 2, fill, fill");
		
		JTextArea textArea = new JTextArea();
		contentPane.add(textArea, "4, 4, 7, 2, fill, fill");
	}

}
