package io.scheduler.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FormPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7265300738999763226L;

	public FormPanel(){
		/*Dimension dim = getPreferredSize();
        dim.width = 350;
        setPreferredSize(dim);*/
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.weightx = 1;
        gc.weighty = 0.1;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 2;
		
		
		JTextField txtSearchCourse = new JTextField();
		txtSearchCourse.setText("Search Class");
		txtSearchCourse.setBounds(263, 6, 144, 28);
		this.add(txtSearchCourse,gc);
		txtSearchCourse.setColumns(10);
		
		
		gc.gridy = 1;
		JButton btnAddClasses = new JButton("Add Classes");
		btnAddClasses.setBounds(263, 221, 134, 29);
		this.add(btnAddClasses,gc);
		
		gc.gridy = 2;
		JButton btnRemoveClasses = new JButton("Remove Classes");
		btnRemoveClasses.setBounds(47, 221, 134, 29);
		this.add(btnRemoveClasses,gc);
		
		gc.gridy = 3;
		String[] DayStrings = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
		JComboBox<?> DayStrings1 = new JComboBox<Object>(DayStrings);	
		DayStrings1.setBounds(263, 35, 93, 27);
		this.add(DayStrings1,gc);
		
		
		
	}
	
	
	
}
