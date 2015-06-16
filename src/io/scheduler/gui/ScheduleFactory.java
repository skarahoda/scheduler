package io.scheduler.gui;

import io.scheduler.data.Schedule;
import io.scheduler.data.User;

import java.awt.Component;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class ScheduleFactory implements Factory {

	@Override
	public List<?> get() {
		try {
			return Schedule.get(User.getCurrentTerm());
		} catch (SQLException e) {
			return new ArrayList<Schedule>();
		}
	}

	@Override
	public Object generateObject() {
		String name = JOptionPane.showInputDialog(null,
				"Please enter a schedule name", "Schedule Name",
				JOptionPane.PLAIN_MESSAGE);
		try {

			if (name != null && !name.equals("")
					&& !Schedule.exists(name, User.getCurrentTerm())) {
				Schedule schedule = Schedule.get(name, User.getCurrentTerm());
				return schedule;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	@Override
	public Component generateComponent(Object o) {
		return new PanelTimeTable((Schedule) o);
	}

	@Override
	public void removeFromDB(Object o) {
		((Schedule) o).removeFromDb();

	}

}
