package io.scheduler.gui;

import io.scheduler.data.Course;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.google.common.collect.Ordering;

public class OptionCourse {
	private int option;
	private DefaultListModel<Course> listModelCourse;
	private JList<Course> jListCourse;

	public OptionCourse(List<Course> courses) {
		JScrollPane scrollCourse = createScrollCourse(courses);
		Object[] message = { "Course:", scrollCourse };
		option = JOptionPane.showConfirmDialog(null, message, "Courses",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
	}

	private JScrollPane createScrollCourse(List<Course> courses) {
		listModelCourse = new DefaultListModel<Course>();
		Collections.sort(courses, Ordering.usingToString());
		for (Course course : courses) {
			listModelCourse.addElement(course);
		}
		jListCourse = new JList<Course>(listModelCourse);
		jListCourse
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JScrollPane returnVal = new JScrollPane(jListCourse);
		return returnVal;
	}

	public List<Course> get() {
		if (option != JOptionPane.OK_OPTION) {
			return new ArrayList<Course>();
		}
		return jListCourse.getSelectedValuesList();
	}

}
