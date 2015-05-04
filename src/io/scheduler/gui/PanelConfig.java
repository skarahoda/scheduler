package io.scheduler.gui;

import io.scheduler.data.User;
import io.scheduler.data.handler.BannerParser;
import io.scheduler.data.handler.DatabaseConnector;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JButton;
import javax.swing.JComboBox;

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
		
		
		
		
		//System.out.println(currentYear);
    
		
		
		 btnSubmit.setAction(new AbstractAction("Submit") {
	            @Override
	            public void actionPerformed(final ActionEvent e) {
	            	try {
	            		
	            		int year = Integer.parseInt(textField.getText());
						int term = comboTerm.getSelectedIndex();
	            		
						User user = DatabaseConnector.getUser();
						if(user==null){
							user = new User(Integer.toString(year)+"0"+  Integer.toString(term+1));
							System.out.println(user);
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
