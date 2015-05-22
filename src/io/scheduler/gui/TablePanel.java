package io.scheduler.gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

public class TablePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8723788672175458756L;
	private JTable table1;

	public TablePanel() {

		Object[][] data = {
				{ "Kathy", "Smith", "Snowboarding", new Integer(5),
						new Boolean(false) },
				{ "John", "Doe", "Rowing", new Integer(3), new Boolean(true) },
				{ "Sue", "Black", "Knitting", new Integer(2),
						new Boolean(false) },
				{ "Jane", "White", "Speed reading", new Integer(20),
						new Boolean(true) },
				{ "Joe", "Brown", "Pool", new Integer(10), new Boolean(false) } };

		String[] columnNames = { "Monday", "Tuesday", "Wednesday", "Thursday",
				"Friday", "Saturday", "Sunday" };

		MyTableModel mod_table = new MyTableModel(data, columnNames);

		table1 = new JTable(mod_table);
		JScrollPane scrollPane = new JScrollPane(table1);
		scrollPane.setColumnHeaderView(table1.getTableHeader());
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		add(scrollPane);

	}

}

class MyTableModel extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6813433055563304420L;

	public MyTableModel(Object rowData[][], Object columnNames[]) {
		super(rowData, columnNames);
	}

}