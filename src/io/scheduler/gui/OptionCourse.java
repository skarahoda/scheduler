package io.scheduler.gui;

import io.scheduler.data.Course;

import java.awt.Dimension;
import java.awt.ScrollPane;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

import com.google.common.collect.Ordering;

public class OptionCourse {
	private int option;
	private DefaultListModel<Course> listModelCourse;
	private JList<Course> jListCourse;

	public OptionCourse(List<Course> courses) {
		ScrollPane scrollCourse = createScrollCourse(courses);
		Object[] message = { "Course:", scrollCourse };
		option = JOptionPane.showConfirmDialog(null, message, "Courses",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
	}

	private ScrollPane createScrollCourse(List<Course> courses) {
		ScrollPane returnVal = new ScrollPane();
		returnVal.setPreferredSize(new Dimension(300, 500));
		listModelCourse = new DefaultListModel<Course>();
		Collections.sort(courses, Ordering.usingToString());
		for (Course course : courses) {
			listModelCourse.addElement(course);
		}
		jListCourse = new JList<Course>(listModelCourse);
		jListCourse.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		returnVal.add(jListCourse);
		return returnVal;
	}

	public Course get() {
		if (option != JOptionPane.OK_OPTION) {
			return null;
		}
		return jListCourse.getSelectedValue();
	}

}
