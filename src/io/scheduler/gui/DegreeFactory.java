package io.scheduler.gui;

import io.scheduler.data.Program;

import java.awt.Component;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DegreeFactory implements Factory {

	@Override
	public List<?> get() {
		try {
			return Program.getAll();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<Program>();
	}

	@Override
	public Object generateObject() {
		OptionProgram option = new OptionProgram();
		return option.get();
	}

	@Override
	public Component generateComponent(Object o) {
		return new PanelGraduationTable((Program) o);
	}

	@Override
	public void removeFromDB(Object o) {
		((Program) o).removeFromDb();

	}

}
