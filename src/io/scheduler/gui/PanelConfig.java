package io.scheduler.gui;

import io.scheduler.data.User;
import io.scheduler.data.handler.DatabaseConnector;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 * @author skarahoda
 *
 */
public class PanelConfig extends CardPanel {

	/**
	 * auto generated serial version UID
	 */
	private static final long serialVersionUID = -8483298720242968924L;
	protected static String key;
	private JTextField textField;
	

	/**
	 * @param parent
	 *            : container that has cardlayout
	 * @param key
	 *            : key for cardlayout
	 */
	public PanelConfig(Container parent, String key) {
		super(parent, key);
		
	}

	protected void initialize() {
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JLabel lblTerm = new JLabel("Term:");
		add(lblTerm);
		
		textField = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, textField, 41, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, textField, 59, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.NORTH, lblTerm, 3, SpringLayout.NORTH, textField);
		springLayout.putConstraint(SpringLayout.EAST, lblTerm, -11, SpringLayout.WEST, textField);
		add(textField);
		textField.setColumns(10);
		
		String[] petStrings = { "Fall", "Spring", "Summer" };
		final JComboBox comboTerm = new JComboBox(petStrings);
		springLayout.putConstraint(SpringLayout.NORTH, comboTerm, 0, SpringLayout.NORTH, lblTerm);
		springLayout.putConstraint(SpringLayout.WEST, comboTerm, 59, SpringLayout.EAST, textField);
		add(comboTerm);
		
		JButton btnSubmit = new JButton("Submit");
		springLayout.putConstraint(SpringLayout.NORTH, btnSubmit, 43, SpringLayout.SOUTH, textField);
		springLayout.putConstraint(SpringLayout.WEST, btnSubmit, 0, SpringLayout.WEST, textField);
		add(btnSubmit);
		
		
		
		/*
		
		setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblCurrentTerm = new JLabel("Current Term:");
		add(lblCurrentTerm, "2, 2, right, default");
		
		
		JTextField textField= new JTextField();
		add(textField, "4, 2, left, default");
		textField.setColumns(10);
		
		
		JButton btnSubmit = new JButton("Submit");
		add(btnSubmit, "4, 4, left, default");

		String[] petStrings = { "Fall", "Spring", "Summer" };

		
		JComboBox comboTerm = new JComboBox(petStrings);
		comboTerm.setSelectedIndex(0);
		
		add(comboTerm, "4, 2, center, default");
		
		
		*/
		
		//System.out.println(currentYear);
    
		
		
		 btnSubmit.setAction(new AbstractAction("Submit") {
	            @Override
	            public void actionPerformed(final ActionEvent e) {
	            	try {
	            		System.out.println("yavuz");
	            		int year = Integer.parseInt(textField.getText());
						int term = comboTerm.getSelectedIndex();
	            		
						User user = DatabaseConnector.getUser();
						if(user==null){
							user = new User(Integer.toString(year)+"0"+  Integer.toString(term+1));
							//System.out.println(user);
						}
					    
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            	
	            	
	            	
	            	
	            	//user.setCurrentTerm(currentTerm);
	            	
	            	
	            	


	            }
	        });

		
		
		
	}
}
