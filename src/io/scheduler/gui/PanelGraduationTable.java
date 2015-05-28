package io.scheduler.gui;

import io.scheduler.data.Course;
import io.scheduler.data.DegreeReq;
import io.scheduler.data.Program;
import io.scheduler.data.TakenCourse;

import java.awt.Component;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

public class PanelGraduationTable extends JPanel implements CustomComponent {
	private Program program;
	private ImmutableTableModel degreeReqModel;
	HashMap<String, Integer> columnCounts;

	public PanelGraduationTable(Program program) {
		this.program = program;
		Object[] columnNames = program.getRequirements();
		degreeReqModel = new ImmutableTableModel(null, columnNames);
		JTable reqTable = new JTable(degreeReqModel);
		JScrollPane scrollPane = new JScrollPane(reqTable);
		scrollPane.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		scrollPane.setColumnHeaderView(reqTable.getTableHeader());
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane);
		fill();
	}

	public void update() {
		fill();
	}

	private void clear() {
		while (degreeReqModel.getRowCount() > 0) {
			degreeReqModel.removeRow(0);
		}
		columnCounts = new HashMap<String, Integer>();
		for (int i = 0; i < degreeReqModel.getColumnCount(); i++) {
			String name = degreeReqModel.getColumnName(i);
			columnCounts.put(name, 0);
		}
	}

	@Override
	public Object getObject() {
		return program;
	}

	public void fill() {
		clear();
		try {
			for (Course course : TakenCourse.getAll()) {
				DegreeReq degree;
				try {
					degree = program.getDegreeReq(course);
				} catch (SQLException e) {
					degree = null;
					e.printStackTrace();
				}
				if (degree != null) {
					int column = degreeReqModel.findColumn(degree.toString());
					Integer rowCount = columnCounts.get(degree.toString());
					while (degreeReqModel.getRowCount() <= rowCount) {
						degreeReqModel.addRow(new Vector<Object>());
					}
					degreeReqModel.setValueAt(course, rowCount, column);
					columnCounts.put(degree.toString(), rowCount + 1);
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
