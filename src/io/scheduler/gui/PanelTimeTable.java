package io.scheduler.gui;

import io.scheduler.data.Meeting;
import io.scheduler.data.Meeting.DayofWeek;
import io.scheduler.data.SUClass;
import io.scheduler.data.Schedule;

import java.awt.Component;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import com.j256.ormlite.dao.ForeignCollection;

public class PanelTimeTable extends JPanel implements CustomComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8723788672175458756L;
	private DefaultListModel<String> TBAClasses;
	private MyTableModel modelTimeTable;
	private Schedule schedule;

	public PanelTimeTable(Schedule schedule) {
		this.schedule = schedule;
		String[][] data = { { "8:40-9:30" }, { "9:40-10:30" },
				{ "10:40-11:30" }, { "11:40-12:30" }, { "12:40-13:30" },
				{ "13:40-14:30" }, { "14:40-15:30" }, { "15:40-16:30" },
				{ "16:40-17:30" }, { "17:40-18:30" }, { "18:40-19:30" },
				{ "19:40-20:30" } };

		String[] columnNames = { "Time", "Monday", "Tuesday", "Wednesday",
				"Thursday", "Friday", "Saturday", "Sunday" };

		modelTimeTable = new MyTableModel(data, columnNames);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		JTable timeTable = new JTable(modelTimeTable);
		JScrollPane scrollPane = new JScrollPane(timeTable);
		scrollPane.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		scrollPane.setColumnHeaderView(timeTable.getTableHeader());
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane);

		TBAClasses = new DefaultListModel<String>();
		JList<String> list = new JList<String>(TBAClasses);
		JScrollPane scrollPane_1 = new JScrollPane(list);
		scrollPane_1.setAlignmentY(Component.TOP_ALIGNMENT);
		add(scrollPane_1);
		try {
			fillTable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteClass(SUClass suClass) {
		try {
			schedule.deleteSUClass(suClass);
			fillTable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addClass(SUClass suClass) {
		try {
			schedule.addSUClass(suClass);
			fillTable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void fillTable() throws SQLException {
		clearTable();
		Collection<SUClass> classes = schedule.getSUClasses();
		if (classes == null)
			return;
		for (SUClass suClass : classes) {
			ForeignCollection<Meeting> meetings = suClass.getMeetings();
			if (meetings.isEmpty()) {
				TBAClasses.addElement(suClass.getCourse().getCode());
			}
			for (Meeting meeting : meetings) {
				DayofWeek day = meeting.getDay();
				if (day == DayofWeek.TBA) {
					TBAClasses.addElement(suClass.getCode());
					break;
				}
				int column = getIndex(day);
				int startRow = getIndex(meeting.getStart());
				int endRow = getIndex(meeting.getEnd());
				endRow = Math.min(endRow, modelTimeTable.getRowCount() - 1);
				for (int i = startRow; i <= endRow; i++) {
					String value = (String) modelTimeTable
							.getValueAt(i, column);
					if (value != "") {
						value = value + ", " + suClass.getCode();
					} else {
						value = suClass.getCode();
					}
					modelTimeTable.setValueAt(value, i, column);
				}
			}
		}
	}

	private void clearTable() {
		TBAClasses.clear();
		for (int i = 0; i < modelTimeTable.getRowCount(); i++) {
			for (int j = 1; j < modelTimeTable.getColumnCount(); j++) {
				modelTimeTable.setValueAt("", i, j);
			}

		}
	}

	private int getIndex(DayofWeek day) {
		switch (day) {
		case MONDAY:
			return 1;
		case TUESDAY:
			return 2;
		case WEDNESDAY:
			return 3;
		case THURSDAY:
			return 4;
		case FRIDAY:
			return 5;
		case SATURDAY:
			return 6;
		default:
			return 0;
		}

	}

	private int getIndex(Date date) {
		Date firstHour;
		try {
			firstHour = new SimpleDateFormat("h:mm").parse("8:40");
		} catch (ParseException e) {
			firstHour = new Date();
		}
		return (int) (date.getTime() - firstHour.getTime()) / (60 * 60 * 1000);
	}

	public Collection<SUClass> getSUClasses() throws SQLException {
		return schedule.getSUClasses();
	}

	@Override
	public Object getObject() {
		return schedule;
	}
}

class MyTableModel extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6813433055563304420L;

	public MyTableModel(String rowData[][], Object columnNames[]) {
		super(rowData, columnNames);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

}