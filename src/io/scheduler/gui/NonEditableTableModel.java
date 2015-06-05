package io.scheduler.gui;

import javax.swing.table.DefaultTableModel;

public class NonEditableTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 6813433055563304420L;

	public NonEditableTableModel(String rowData[][], Object columnNames[]) {
		super(rowData, columnNames);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

}
