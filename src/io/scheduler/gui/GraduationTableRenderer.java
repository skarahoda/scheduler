package io.scheduler.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class GraduationTableRenderer extends DefaultTableCellRenderer {

	private NonEditableTableModel degreeReqModel;

	/**
	 * 
	 */
	private static final long serialVersionUID = -6282301991092099760L;

	/**
	 * @param degreeReqModel
	 */
	public GraduationTableRenderer(NonEditableTableModel degreeReqModel) {
		super();
		this.degreeReqModel = degreeReqModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent
	 * (javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component cell = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);
		if (mustChange(column)) {
			cell.setBackground(Color.GREEN);
		} else {
			cell.setBackground(Color.WHITE);
		}
		return cell;
	}

	private boolean mustChange(int column) {
		if (degreeReqModel == null || column == 0) {
			return false;
		}
		double remainingCredit = (double) degreeReqModel.getValueAt(1, column);
		int remainingCourse = (int) degreeReqModel.getValueAt(4, column);
		boolean returnVal = remainingCredit == 0 && remainingCourse == 0;
		return returnVal;
	}

}
