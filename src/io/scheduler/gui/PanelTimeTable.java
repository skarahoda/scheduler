package io.scheduler.gui;

import io.scheduler.data.Meeting;
import io.scheduler.data.Meeting.DayofWeek;
import io.scheduler.data.SUClass;
import io.scheduler.data.Schedule;

import java.awt.Component;
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

public class PanelTimeTable extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8723788672175458756L;
	private DefaultListModel<String> TBAClasses;
	private MyTableModel modelTimeTable;

	public PanelTimeTable() {
		String[][] data = { { "8:40-9:30" }, { "9:40-10:30" },
				{ "10:40-11:30" }, { "11:40-12:30" }, { "12:40-13:30" },
				{ "13:40-14:30" }, { "14:40-15:30" }, { "15:40-16:30" },
				{ "16:40-17:30" }, { "17:40-18:30" }, { "18:40-19:30" } };

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
	}

	public void fillTable(Schedule s) {
		clearTable();
		Collection<SUClass> classes = s.getSUClasses();
		if (classes == null)
			return;
		for (SUClass suClass : classes) {
			ForeignCollection<Meeting> meetings = suClass.getMeetings();
			for (Meeting meeting : meetings) {
				DayofWeek day = meeting.getDay();
				if (day == DayofWeek.TBA) {
					TBAClasses.addElement(suClass.getCourse().getCode());
					break;
				}
				int column = getIndex(day);
				int startRow = getIndex(meeting.getStart());
				int endRow = getIndex(meeting.getEnd());
				for (int i = startRow; i <= endRow; i++) {
					String value = (String) modelTimeTable
							.getValueAt(i, column);
					value = value + suClass.getCourse().getCode() + "\n";
					modelTimeTable.setValueAt(value, i, column);
				}
			}
		}
	}

	private void clearTable() {
		for (int i = 0; i < modelTimeTable.getRowCount(); i++) {
			for (int j = 0; j < modelTimeTable.getColumnCount(); j++) {
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