package io.scheduler.gui;

import io.scheduler.data.Course;
import io.scheduler.data.DegreeReq;
import io.scheduler.data.Program;
import io.scheduler.data.TakenCourse;

import java.awt.Component;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

public class PanelGraduationTable extends JPanel implements CustomComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -372125371390721629L;
	private Program program;
	private ImmutableTableModel degreeReqModel;
	HashMap<String, Integer> columnCounts;

	public PanelGraduationTable(Program program) {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.program = program;
		List<String> columnNames = new ArrayList<String>();
		columnNames.add("");
		
		String[][] data = { { "Taken:" }, { "Remaining:" },
				{ "Total:" }, { "Courses:" }};
		degreeReqModel = new ImmutableTableModel(data,columnNames.toArray());
		init();
		JTable reqTable = new JTable(degreeReqModel);
		JScrollPane scrollPane = new JScrollPane(reqTable);
		scrollPane.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		scrollPane.setColumnHeaderView(reqTable.getTableHeader());
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane);
		fill();
	}

	private void init() {
		for (DegreeReq requirement : program.getRequirements()) {
			double total = requirement.getCredit();
			Object[] data = {0,total,
				total, ""};
			degreeReqModel.addColumn(requirement, data);
		}
	}

	public void update() {
		fill();
	}

	private void clear() {
		while (degreeReqModel.getRowCount() > 4) {
			degreeReqModel.removeRow(4);
		}
		columnCounts = new HashMap<String, Integer>();
		for (int i = 1; i < degreeReqModel.getColumnCount(); i++) {
			String name = degreeReqModel.getColumnName(i);
			degreeReqModel.setValueAt(0.0, 0, i);
			double total = (double) degreeReqModel.getValueAt(2, i);
			degreeReqModel.setValueAt(total, 1, i);
			columnCounts.put(name, 3);
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
				List<DegreeReq> degreeList = program.getRequirements(course);
				float credit = course.getCredit();
				for (DegreeReq degree : degreeList) {
					int column = degreeReqModel.findColumn(degree.toString());
					Integer rowCount = columnCounts.get(degree.toString());
					while (degreeReqModel.getRowCount() <= rowCount) {
						degreeReqModel.addRow(new Vector<Object>());
					}
					degreeReqModel.setValueAt(course, rowCount, column);
					columnCounts.put(degree.toString(), rowCount + 1);
					increaseTakenCourse(degree.toString(), credit);
				}		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void increaseTakenCourse(String columnName, float credit) {
		Integer column = degreeReqModel.findColumn(columnName);
		double taken = (double) degreeReqModel.getValueAt(0, column) + credit;
		double total = (double) degreeReqModel.getValueAt(2, column);
		double remaining = total - taken;
		remaining = remaining < 0 ? 0 : remaining;
		degreeReqModel.setValueAt(taken, 0, column);
		degreeReqModel.setValueAt(remaining, 1, column);
	}

}
