package io.scheduler.gui;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTextField;

public class PanelSchedule extends CardPanel {

	public PanelSchedule(Container parent, String key) {
		super(parent, key);
	}

	/**
	 * auto generated serial version UID
	 */
	private static final long serialVersionUID = 8525163294114489034L;

	protected void initialize() {
		this.setBounds(12, 12, 424, 230);
		
		JTextField txtSearchCourse = new JTextField();
		txtSearchCourse.setText("Search Class");
		txtSearchCourse.setBounds(263, 6, 144, 28);
		this.add(txtSearchCourse);
		txtSearchCourse.setColumns(10);
		
		JButton btnAddClasses = new JButton("Add Classes");
		btnAddClasses.setBounds(263, 221, 134, 29);
		this.add(btnAddClasses);
		
		JButton btnRemoveClasses = new JButton("Remove Classes");
		btnRemoveClasses.setBounds(47, 221, 134, 29);
		this.add(btnRemoveClasses);
		
		String[] DayStrings = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
		JComboBox<?> DayStrings1 = new JComboBox<Object>(DayStrings);	
		DayStrings1.setBounds(263, 35, 93, 27);
		this.add(DayStrings1);
		JList<?> list = new JList<Object>();
		this.add(list);
		super.initialize();
	}

}
