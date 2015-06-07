package io.scheduler.gui;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.google.common.collect.Ordering;

public class OptionList<T> {
	private int option;
	private DefaultListModel<T> listModelCourse;
	private JList<T> jListCourse;

	public OptionList(List<T> list) throws InvalidParameterException {
		if (list == null || list.isEmpty())
			throw new InvalidParameterException();
		String className = list.get(0).getClass().getSimpleName();
		JScrollPane scrollCourse = createScrollCourse(list);
		Object[] message = { className + ":", scrollCourse };
		option = JOptionPane.showConfirmDialog(null, message, className,
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
	}

	private JScrollPane createScrollCourse(List<T> list) {
		listModelCourse = new DefaultListModel<T>();
		Collections.sort(list, Ordering.usingToString());
		for (T course : list) {
			listModelCourse.addElement(course);
		}
		jListCourse = new JList<T>(listModelCourse);
		jListCourse
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JScrollPane returnVal = new JScrollPane(jListCourse);
		return returnVal;
	}

	public List<T> get() {
		if (option != JOptionPane.OK_OPTION) {
			return new ArrayList<T>();
		}
		return jListCourse.getSelectedValuesList();
	}

}
