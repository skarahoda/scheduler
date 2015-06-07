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
	private NonEditableTableModel degreeReqModel;
	HashMap<String, Integer> columnCounts;

	public PanelGraduationTable(Program program) {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.program = program;
		List<String> columnNames = new ArrayList<String>();
		columnNames.add("");

		String[][] data = { { "Taken Credit:" }, { "Remaining Credit:" },
				{ "Total Credit:" }, { "Taken Courses:" },
				{ "Remaining Courses:" }, { "Total Courses:" }, { "Courses:" } };
		degreeReqModel = new NonEditableTableModel(data, columnNames.toArray());
		init();
		JTable reqTable = new JTable(degreeReqModel);
		GraduationTableRenderer renderer = new GraduationTableRenderer(
				degreeReqModel);
		reqTable.setDefaultRenderer(Object.class, renderer);
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
			double totalCredit = requirement.getCredit();
			int courseNum = requirement.getCourseNum();
			Object[] data = { 0, totalCredit, totalCredit, 0, courseNum,
					courseNum, "" };
			degreeReqModel.addColumn(requirement, data);
		}
	}

	public void update() {
		fill();
	}

	private void clear() {
		while (degreeReqModel.getRowCount() > 7) {
			degreeReqModel.removeRow(7);
		}
		columnCounts = new HashMap<String, Integer>();
		for (int i = 1; i < degreeReqModel.getColumnCount(); i++) {
			String name = degreeReqModel.getColumnName(i);
			degreeReqModel.setValueAt(0.0, 0, i);
			double totalCredit = (double) degreeReqModel.getValueAt(2, i);
			degreeReqModel.setValueAt(totalCredit, 1, i);
			degreeReqModel.setValueAt(0, 3, i);
			int totalCourses = (int) degreeReqModel.getValueAt(5, i);
			degreeReqModel.setValueAt(totalCourses, 4, i);
			degreeReqModel.setValueAt("", 6, i);
			columnCounts.put(name, 6);
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
					increaseTakenCourse(column, credit);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void increaseTakenCourse(int column, float credit) {
		increaseCredit(column, credit);
		increaseCourse(column);

	}

	private void increaseCourse(Integer column) {
		int takenCourse = (int) degreeReqModel.getValueAt(3, column) + 1;
		int totalCourse = (int) degreeReqModel.getValueAt(5, column);
		int remainingCourse = totalCourse - takenCourse;
		remainingCourse = remainingCourse < 0 ? 0 : remainingCourse;
		degreeReqModel.setValueAt(takenCourse, 3, column);
		degreeReqModel.setValueAt(remainingCourse, 4, column);
	}

	private void increaseCredit(Integer column, float credit) {
		double takenCredit = (double) degreeReqModel.getValueAt(0, column)
				+ credit;
		double totalCredit = (double) degreeReqModel.getValueAt(2, column);
		double remainingCredit = totalCredit - takenCredit;
		remainingCredit = remainingCredit < 0 ? 0 : remainingCredit;
		degreeReqModel.setValueAt(takenCredit, 0, column);
		degreeReqModel.setValueAt(remainingCredit, 1, column);

	}

}
